package m2;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.List;

public class Producer {
    //模拟三组数据
    static String[] msgs = {
            "15103111039,创建",
            "15103111065,创建",
            "15103111039,付款",
            "15103117235,创建",
            "15103111065,付款",
            "15103117235,付款",
            "15103111065,完成",
            "15103111039,推送",
            "15103117235,完成",
            "15103111039,完成"
    };

    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        //生产者
        DefaultMQProducer p = new DefaultMQProducer("Produce2");
        //设置nameserver
        p.setNamesrvAddr("192.168.126.141:9876");
        //启动
        p.start();
        //遍历订单消息,发送消息,设置队列选择器
        for (String s : msgs) {
            // s ------
            Long orderId = Long.valueOf(s.split(",")[0]);
            Message msg = new Message("Topic2", s.getBytes());

            SendResult r =
                    p.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message message, Object arg) {
                    Long orderId = (Long) arg;
                    int index = (int) (orderId % mqs.size());
                    return mqs.get(index);
                }
            }, orderId);
            System.out.println(orderId+"----> "+r.getMessageQueue().getQueueId());
        }


    }
}
