package cn.tedu.rabbitmqspringboot.m5;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;


@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
    /*
    注入生产者,调用生产者的run()方法发送信息

    消费者是自动创建实例,自动注册成为消费者,自动启动开始接收消息,无需手动调用
     */
    @Autowired
   private Produer produer;

    //创建交换机
    @Bean
    public TopicExchange logsExchange(){
        //false 非持久,不删除
        return new TopicExchange("topic_logs",false,false);
    }

    @PostConstruct
    public void test(){
        //lambda 表达式,对匿名内部类的语法格式简化
        new Thread(()-> produer.send()).start();

    }
}
