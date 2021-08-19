package m4;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //连接
        ConnectionFactory f = new ConnectionFactory();
        f.setHost("192.168.126.140");
        f.setPort(5672);
        f.setUsername("admin");
        f.setPassword("admin");
        Connection con = f.newConnection();//连接
        Channel c = con.createChannel();//通信的通道
        //创建交换机
        c.exchangeDeclare("t_logs", BuiltinExchangeType.TOPIC);
        //给交换机发送消息,携带关键词(路由键)
        while (true){
            System.out.println("输入: ");
            String s = new Scanner(System.in).nextLine();
            System.out.println("输入路由键: ");
            String k = new Scanner(System.in).nextLine();
            /*
            对 ""默认交换机,路由键就是队列的名称
             */
            c.basicPublish("t_logs", k, null, s.getBytes());
        }
    }
}
