package cn.tedu.rabbitmqspringboot.m1;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Produer {
    @Autowired
    private AmqpTemplate template;

    public void send(){
        template.convertAndSend("helloworld","Hello World");
    }
}
