package m1;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.Scanner;

public class Producer {
    public static void main(String[] args) throws Exception{
        //创建生产者
        DefaultMQProducer p = new DefaultMQProducer("producer1");
        //设置name server
        p.setNamesrvAddr("192.168.126.141:9876");
        //启动连接服务器
        p.start();
        //消息封装成message 对象
        //发送消息
        while (true){
            System.out.println("输入消息: ");
            String s = new Scanner(System.in).nextLine();
            /*
            import org.apache.rocketmq.common.message.Message;
            Topic -- 相当于一级分类
            Tag  -- 相当于二级分类
             */
            Message message = new Message("Topic1", "Tag1", s.getBytes());
            message.setDelayTimeLevel(3);
            SendResult send = p.send(message);
            System.out.println("----"+send+"-----");
        }
    }
}
