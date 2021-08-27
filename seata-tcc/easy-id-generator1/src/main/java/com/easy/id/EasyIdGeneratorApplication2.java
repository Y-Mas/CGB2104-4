package com.easy.id;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class EasyIdGeneratorApplication2 {
    public static void main(String[] args) {
        SpringApplication.run(EasyIdGeneratorApplication2.class, args);
    }
}
