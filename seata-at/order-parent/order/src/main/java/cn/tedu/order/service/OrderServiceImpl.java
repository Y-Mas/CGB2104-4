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
        Long orderId = Long.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        order.setId(orderId);
        orderMapper.create(order);
    }
}
