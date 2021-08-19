package m2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

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
        c.queueDeclare("task_queue",true,false,false,null);
        while (true){
            System.out.println("xxx??: ");
            String s = new Scanner(System.in).nextLine();
            c.basicPublish("", "task_queue", MessageProperties.PERSISTENT_TEXT_PLAIN, s.getBytes());
        }
    }
}
