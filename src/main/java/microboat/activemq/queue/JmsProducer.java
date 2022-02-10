package microboat.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author zhouwei
 */
public class JmsProducer {

    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";

    public static void main(String[] args) throws JMSException {
        // 1、创建连接工厂，分析ActiveMQConnectionFactory得到参数
        // 使用默认的用户名和密码
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        // 2、通过连接工厂获得 Connection 连接，然后开启连接
        Connection connection = factory.createConnection();
        connection.start();

        // 3、创建会话 Session
        // 参数：事务、签收
        // false：表示不使用事务；true：表示使用事务
        // Session.AUTO_ACKNOWLEDGE：表示自动确认
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // 4、创建目的地（具体是队列还是topic）
        Queue queue = session.createQueue("queue01");

        // 5、创建消息的生产者
        MessageProducer producer = session.createProducer(queue);

        // 6、通过生产者 producer 循环产生三条消息，发送到MQ的队列中
        for (int i = 0; i < 6; i++) {
            // 7、创建消息
            TextMessage textMessage = session.createTextMessage("msg=>" + i);
            // 8、通过生产者发送
            producer.send(textMessage);
        }
        // 9、关闭资源
        producer.close();
        session.close();
        connection.close();

        System.out.println("消息发布到MQ完成！");
    }
}
