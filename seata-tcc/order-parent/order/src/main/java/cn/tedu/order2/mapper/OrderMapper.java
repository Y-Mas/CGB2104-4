package cn.tedu.order2.mapper;

import cn.tedu.order2.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface OrderMapper extends BaseMapper<Order> {
    //创建订单
    void create(Order order);
    //修改状态
    void updateStatus(Long id, Integer status);
    //删除订单,使用继承的deleteById
}
