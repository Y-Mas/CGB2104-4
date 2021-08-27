package cn.tedu.order2.service;

import cn.tedu.order2.entity.Order;
import cn.tedu.order2.feign.AccountClient;
import cn.tedu.order2.feign.EasyIdClient;
import cn.tedu.order2.feign.StorageClient;
import cn.tedu.order2.mapper.OrderMapper;
import cn.tedu.order2.tcc.OrderTccAction;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderTccAction orderTccAction;

    @Autowired
    private EasyIdClient easyIdClient;
    @Autowired
    private StorageClient storageClient;
    @Autowired
    private AccountClient accountClient;

    @GlobalTransactional
    @Override
    public void create(Order order) {
        //TODO: 远程调用ID发号器,获取订单id
        Long id = Long.valueOf(easyIdClient.nextId("order_business"));
        order.setId(id);
        //不直接完成业务数据操作,而是调用第一阶段的方法
//        orderMapper.create(order);

        orderTccAction.prepare(
                null ,
                order.getId(),
                order.getUserId(),
                order.getProductId(),
                order.getCount(),
                order.getMoney());

        //TODO: 远程调用库存,减少库存
        storageClient.decrease(order.getProductId(), order.getCount());
        //TODO: 远程调用账户,扣减账户
        accountClient.decrease(order.getUserId(), order.getMoney());
    }
}
