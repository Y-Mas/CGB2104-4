package cn.tedu.sp04.service;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.pojo.Order;
import cn.tedu.sp01.pojo.User;
import cn.tedu.sp01.service.OrderService;
import cn.tedu.sp04.feign.ItemClient;
import cn.tedu.sp04.feign.UserClient;
import cn.tedu.web.util.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ItemClient itemClient;
    @Autowired
    private UserClient userClient;

    @Override
    public Order getOrder(String orderId) {
        log.info("获取订单,orderId="+orderId);
        //TODO: 远程调用商品,获取商品列表
        JsonResult<List<Item>> items = itemClient.getItems(orderId);
        //TODO: 远程调用用户,获取用户列表
        JsonResult<User> user = userClient.getUser(5);

        Order order = new Order();
        order.setId(orderId);
        order.setItems(items.getData());
        order.setUser(user.getData());
        return order;
    }

    @Override
    public void addOrder(Order order) {
        log.info("添加订单,order="+order);
        //TODO: 远程调用商品,减少商品库存
        itemClient.decreaseNumber(order.getItems());
        //TODO: 远程调用用户,增加积分
        userClient.addScore(order.getUser().getId(),500);
    }
}
