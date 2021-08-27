package m3;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class Consumer {
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer c = new DefaultMQPushConsumer("consumer3");
        c.setNamesrvAddr("192.168.126.141:9876");
        c.subscribe("Topic3", "*");
        c.setMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                for (MessageExt msg:msgs) {
                    String s = new String(msg.getBody());
                    System.out.println("收到: "+s);
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        c.start();
    }
}
