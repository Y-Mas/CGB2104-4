package cn.tedu.rabbitmqspringboot.m5;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Produer {
    @Autowired
    private AmqpTemplate template;

    public void send(){
       while (true){
           System.out.println("输入: ");
           String s = new Scanner(System.in).nextLine();
           System.out.println("输入路由键: ");
           String k = new Scanner(System.in).nextLine();
           template.convertAndSend("topic_logs",k,s);
       }
    }
}
