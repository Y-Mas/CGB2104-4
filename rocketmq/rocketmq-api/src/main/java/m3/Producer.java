package m3;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.Scanner;

public class Producer {
    public static void main(String[] args) throws MQClientException {
        //创建生产者
        TransactionMQProducer p = new TransactionMQProducer("Producer3");
        //设置nameserver
        p.setNamesrvAddr("192.168.126.141:9876");
        //设置事务消息的监听器
        p.setTransactionListener(new TransactionListener() {
            @Override
            public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                System.out.println("模拟网络中断");
                if (Math.random()<1){
                    //执行本地事务中一般不会返回unknown
                    //此处只为了测试
                    return LocalTransactionState.UNKNOW;
                }
                System.out.println("执行本地事务: "+arg);
                if (Math.random()<0.5){
                    System.out.println("本地事务执行成功");
                    return LocalTransactionState.COMMIT_MESSAGE;
                }else {
                    System.out.println("本地事务执行失败");
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }
            }
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                System.out.println("处理服务器的事务状态回查");
                System.out.println("模拟网络中断");
                    return LocalTransactionState.UNKNOW;
                }
        });
        //启动
        p.start();
        //发送事务消息,触发监听器来执行本地事务
        while (true){
            System.out.println("输入消息: ");
            String s = new Scanner(System.in).nextLine();
            Message message = new Message("Topic3", s.getBytes());
            p.sendMessageInTransaction(message, "执行本地事务的数据");
        }
    }
}
