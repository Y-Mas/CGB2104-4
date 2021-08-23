package cn.tedu.rabbitmqspringboot.m2;

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
   private Produer produer;

    @Bean
    public Queue task_queueQueue(){
        //把队列的参数封装到queue实例
        //在自动配置类中,会连接服务器,使用这个里的参数来创建队列
        return new Queue("task_queueQueue",true);//非持久队列,默认是true
    }
    @PostConstruct
    public void test(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                produer.send();
//            }
//        }).start();
        //lambda 表达式,对匿名内部类的语法格式简化
        new Thread(()-> produer.send()).start();

    }
}
