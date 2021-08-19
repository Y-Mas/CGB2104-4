package m4;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //连接
        ConnectionFactory f = new ConnectionFactory();
        f.setHost("192.168.126.140");
        f.setPort(5672);
        f.setUsername("admin");
        f.setPassword("admin");
        Connection con = f.newConnection();//连接
        Channel c = con.createChannel();//通信的通道
        //创建交换机 创建随机队列 绑定指定关键词
        c.exchangeDeclare("t_logs", BuiltinExchangeType.TOPIC);
        String queue = UUID.randomUUID().toString();
        c.queueDeclare(queue, false, true, true, null);
        System.out.println("输入绑定键,空格隔开: ");
        String s = new Scanner(System.in).nextLine();
        String[] split = s.split("\\s+");//\s是空白字符  "+"表示1到多个
        for (String k : split) {
            c.queueBind(queue, "t_logs", k);
        }
        //接收消息
        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                String s1 = new String(message.getBody());
                String key = message.getEnvelope().getRoutingKey();
                System.out.println(key+" -- "+s1);
            }
        };
        CancelCallback cancelCallback = new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {
            }
        };
        c.basicConsume(queue, true, deliverCallback,cancelCallback);
    }
}
