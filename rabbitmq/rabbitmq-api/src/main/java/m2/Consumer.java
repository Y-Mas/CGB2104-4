package m2;

import com.rabbitmq.client.*;

import java.io.IOException;
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
        c.queueDeclare("task_queue",true,false,false,null);

        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                String s = new String(message.getBody());
                System.out.println("收到"+s);
                //模拟处理器耗时消息
                //遍历字符,没找到一个".",暂停一秒
                for (int i = 0; i<s.length();i++){
                    if (s.charAt(i) == '.'){
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                c.basicAck(message.getEnvelope().getDeliveryTag(),false);
                System.out.println("**********消息处理完成**********");
            }
        };
        CancelCallback cancelCallback = new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {

            }
        };
        c.basicQos(1);
        c.basicConsume("task_queue", false, deliverCallback, cancelCallback);
    }
}
