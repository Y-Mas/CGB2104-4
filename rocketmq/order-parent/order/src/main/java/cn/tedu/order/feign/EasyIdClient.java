package cn.tedu.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "easy-id-generator")
public interface EasyIdClient {
    @GetMapping("/segment/ids/next_id")
    String nextId(@RequestParam("businessType") String businessType);
}
