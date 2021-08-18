package m1;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.net.CacheRequest;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory f = new ConnectionFactory();
        f.setHost("192.168.126.140");
        f.setPort(5672);
        f.setUsername("admin");
        f.setPassword("admin");
        Connection con = f.newConnection();
        Channel c = con.createChannel();

        c.queueDeclare("helloWorld", false, false,false , null);

        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String ss, Delivery delivery) throws IOException {
                byte[] body = delivery.getBody();
                String s = new String(body);
                System.out.println("收到： "+s);
            }
        };
        CancelCallback cancelCallback = new CancelCallback() {
            @Override
            public void handle(String s) throws IOException {

            }
        };

        c.basicConsume("helloWorld", true, deliverCallback,cancelCallback);
    }
}
