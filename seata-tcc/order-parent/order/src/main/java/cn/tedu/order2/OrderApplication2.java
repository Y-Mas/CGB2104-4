package cn.tedu.order2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("cn.tedu.order2.mapper")
@EnableFeignClients
public class OrderApplication2 {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication2.class, args);
    }

}
