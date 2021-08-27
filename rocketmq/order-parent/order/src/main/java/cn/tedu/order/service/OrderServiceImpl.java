package cn.tedu.order.service;

import cn.tedu.order.entity.AccountMessage;
import cn.tedu.order.entity.Order;
import cn.tedu.order.entity.TxInfo;
import cn.tedu.order.feign.AccountClient;
import cn.tedu.order.feign.EasyIdClient;
import cn.tedu.order.feign.StorageClient;
import cn.tedu.order.mapper.OrderMapper;
import cn.tedu.order.mapper.TxMapper;
import cn.tedu.order.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.UUID;

@Service
@RocketMQTransactionListener
@Slf4j
public class OrderServiceImpl implements OrderService, RocketMQLocalTransactionListener {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private EasyIdClient easyIdClient;
//    @Autowired
//    private StorageClient storageClient;
//    @Autowired
//    private AccountClient accountClient;
    @Autowired
    private RocketMQTemplate t;
    @Autowired
    private TxMapper txMapper;

    @Override
    public void create(Order order){
        String xid = UUID.randomUUID().toString().replace("-", "");
        AccountMessage am = new AccountMessage(order.getUserId(), order.getMoney(), xid);
        String json = JsonUtil.to(am);
        Message<String> message = MessageBuilder.withPayload(json).build();
        t.sendMessageInTransaction("orderTopic",message,order);
    }

    //不用直接执行业务代码
    public void doCreate(Order order) {
        //TODO: 远程调用ID发号器,获取订单id
        Long id = Long.valueOf(easyIdClient.nextId("order_business"));
        order.setId(id);
        orderMapper.create(order);
    }
    @Transactional
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        RocketMQLocalTransactionState state;
         int status;
        try {
            Order order = (Order)o;
            doCreate(order);
            state = RocketMQLocalTransactionState.COMMIT;
            status = 0;
        }catch (Exception e){
            log.info("存储订单失败");
            state = RocketMQLocalTransactionState.ROLLBACK;
            status = 1;
        }
        String json = new String((byte[]) message.getPayload());
        String xid = JsonUtil.getString(json, "xid");
        TxInfo txInfo = new TxInfo(xid,status, System.currentTimeMillis());
        txMapper.insert(txInfo);
        return state;
    }
    @Transactional
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        String json = new String((byte[]) message.getPayload());
        String xid = JsonUtil.getString(json, "xid");
        TxInfo txInfo = txMapper.selectById(xid);
        if (txInfo == null){
            return RocketMQLocalTransactionState.UNKNOWN;
        }
        switch (txInfo.getStatus()){
            case 0: return RocketMQLocalTransactionState.COMMIT;
            case 1: return RocketMQLocalTransactionState.ROLLBACK;
            default: return RocketMQLocalTransactionState.UNKNOWN;
        }
    }
}
