package cn.tedu.sp02.service;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
public class ItemServiceImpl implements ItemService {

    @Override
    public List<Item> getItems(String orderId) {
        log.info("获取商品列表, order="+orderId);
        ArrayList<Item> list = new ArrayList<Item>();
        list.add(new Item(1, "商品 1",1));
        list.add(new Item(2, "商品 2",2));
        list.add(new Item(3, "商品 3",3));
        list.add(new Item(4, "商品 4",4));
        list.add(new Item(5, "商品 5",5));
        return list;
    }

    @Override
    public void decreaseNumber(List<Item> items) {
        //减少商品库存
        for (Item item : items) {
            log.info("减少商品库存,item="+item);
        }
    }
}
