package cn.tedu.order.service;

import cn.tedu.order.entity.Order;
import cn.tedu.order.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public void create(Order order) {
        //TODO: 远程调用ID发号器,获取订单id

        //临时随机产生id
        Long orderId = Long.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        order.setId(orderId);
        orderMapper.create(order);

        //TODO: 远程调用库存,减少库存
        //TODO: 远程调用账户,扣减账户
    }
}
