package cn.tedu.order.service;

import cn.tedu.order.entity.Order;
import cn.tedu.order.feign.AccountClient;
import cn.tedu.order.feign.EasyIdClient;
import cn.tedu.order.feign.StorageClient;
import cn.tedu.order.mapper.OrderMapper;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private EasyIdClient easyIdClient;
    @Autowired
    private StorageClient storageClient;
    @Autowired
    private AccountClient accountClient;

    @Transactional //本地事务
    @GlobalTransactional  //开启全局事务
    @Override
    public void create(Order order) {
        //TODO: 远程调用ID发号器,获取订单id
        Long id = Long.valueOf(easyIdClient.nextId("order_business"));
        order.setId(id);
        orderMapper.create(order);

        //TODO: 远程调用库存,减少库存
        storageClient.decrease(order.getProductId(), order.getCount());
        //TODO: 远程调用账户,扣减账户
        accountClient.decrease(order.getUserId(), order.getMoney());
    }
}
