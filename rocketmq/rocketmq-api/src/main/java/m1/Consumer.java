package m1;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class Consumer {
    public static void main(String[] args) throws MQClientException {
        //创建消费者
        DefaultMQPushConsumer c = new DefaultMQPushConsumer("consumer1");
        //设置 nameserver
        c.setNamesrvAddr("192.168.126.141:9876");
        //指定位置订阅消息
        c.subscribe("Topic1", "Tag1");
        //处理消息的监听器
        //Concurrently 会启动多个线程,并发的处理消息
        c.setMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list,
                                                            ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (MessageExt msg: list){
                    String s = new String(msg.getBody());
                    System.out.println("收到: "+s);
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;

            }
        });
        //启动消费者
        c.start();
    }
}
