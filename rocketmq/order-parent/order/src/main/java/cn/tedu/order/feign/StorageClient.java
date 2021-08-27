package cn.tedu.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "storage")
public interface StorageClient {
    @GetMapping("decrease")
    String decrease(@RequestParam("productId") Long productId,
                    @RequestParam("count")Integer count);
}
