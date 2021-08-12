package cn.tedu.sp02.controller;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.service.ItemService;
import cn.tedu.web.util.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class ItemController {
    @Autowired
    private ItemService itemService;
    //获取订单的商品列表
    @GetMapping("/{orderId}")
    public JsonResult<List<Item>> getItems(@PathVariable String orderId){
        List<Item> items = itemService.getItems(orderId);
        return JsonResult.ok().data(items);
    }

    //减少商品库存,使用postman 测试请求
    @PostMapping("/decreaseNumber")
    public JsonResult<?> decreaseNumber(@RequestBody List<Item> items){
                itemService.decreaseNumber(items);
                return JsonResult.ok().msg("减少商品库存成功");
    }
}
