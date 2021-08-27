package cn.tedu.storage2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.tedu.storage2.mapper")
public class StorageApplication2 {

    public static void main(String[] args) {
        SpringApplication.run(StorageApplication2.class, args);
    }

}
