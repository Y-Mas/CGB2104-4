package cn.tedu.order2.controller;

import cn.tedu.order2.entity.Order;
import cn.tedu.order2.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;
    @GetMapping("/create")
    public String create(Order order){
        log.info("创建订单");
        orderService.create(order);
        return "创建订单成功";
    }
}
