package cn.tedu.rabbitmqspringboot.m4;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,//随机命名,非持久,独占,自动删除
            exchange = @Exchange(name = "direct_logs",declare = "false"),//declare false不新建交换机,只引用已经存在的
            key = {"error"}
    ))
    public void receive1(String msg){
        System.out.println("消费者1收到: "+msg);
    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,//随机命名,非持久,独占,自动删除
            exchange = @Exchange(name = "direct_logs",declare = "false"),//declare false不新建交换机,只引用已经存在的
            key = {"error","info","warning"}
    ))
    public void receive2(String msg){
        System.out.println("消费者2收到: "+msg);
    }
}
