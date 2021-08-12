package cn.tedu.sp01.service;

import cn.tedu.sp01.pojo.Item;

import java.util.List;

public interface ItemService {
    //获取订单商品列表
    List<Item> getItems(String orderId) ;


    //减少库存
    void decreaseNumber(List<Item> items);
}
