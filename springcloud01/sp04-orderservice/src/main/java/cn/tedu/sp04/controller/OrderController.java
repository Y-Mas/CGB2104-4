package cn.tedu.sp04.controller;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.pojo.Order;
import cn.tedu.sp01.pojo.User;
import cn.tedu.sp01.service.OrderService;
import cn.tedu.web.util.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;
    @GetMapping("/{orderId}")
    public JsonResult<Order> getOrder(@PathVariable String orderId){
        Order order = orderService.getOrder(orderId);
        return JsonResult.ok().data(order);
    }
    @PostMapping("/add")
    public JsonResult<?> addOrder(){
        Order order = new Order();
        order.setId("hfajsfb444");
        order.setUser(new User(66,null,null));
        order.setItems(Arrays.asList(new Item[]{
                new Item(1,"商品1",1),
                new Item(2,"商品2",2),
                new Item(3,"商品3",5)
        }));
        orderService.addOrder(order);
        return JsonResult.ok().msg("添加订单成功");
    }
    @GetMapping("/favicon.ico")
    public void ico(){
    }
}
