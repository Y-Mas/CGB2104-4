package cn.tedu.storage2.controller;

import cn.tedu.storage2.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StorageController {
    @Autowired
    private StorageService storageService;
    @GetMapping("/decrease")
    public String decrease(Long productId,Integer count) throws Exception {
        storageService.decrease(productId, count);
        return "减少库存成功";
    }

}
