package cn.tedu.rabbitmqspringboot.m1;

import org.springframework.amqp.core.Queue;
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
    private Produer p;

    @Bean
    public Queue helloworldQueue(){
        //把队列的参数封装到queue实例
        //在自动配置类中,会连接服务器,使用这个里的参数来创建队列
        return new Queue("helloworld",false);//非持久队列,默认是true
    }
    @PostConstruct
    public void test(){
        p.send();
    }
}
