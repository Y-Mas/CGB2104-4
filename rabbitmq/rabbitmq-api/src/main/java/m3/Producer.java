package m3;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory f = new ConnectionFactory();
        f.setHost("192.168.126.140");
        f.setPort(5672);
        f.setUsername("admin");
        f.setPassword("admin");
        Connection con = f.newConnection();
        Channel c = con.createChannel();
        c.exchangeDeclare("logs", BuiltinExchangeType.FANOUT);
        while (true){
            System.out.println("输入消息: ");
            String s = new Scanner(System.in).next();
            //第二个参数对于Fanout交换机无效
            c.basicPublish("logs", "",null , s.getBytes());
        }
    }
}
