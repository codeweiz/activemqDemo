package microboat.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 运行前替换 ACTIVEMQ_URL
 * 建议用 docker 部署一个本地 activemq
 * @author zhouwei
 */
public class JmsConsumer {

    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";

    public static void main(String[] args) throws JMSException {
        // 1、创建连接工厂，分析 ActiveMQConnectionFactory得到参数
        // 使用默认的用户名和密码
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        // 2、通过连接工厂 factory 获得 Connection，然后开启连接 connection
        Connection connection = factory.createConnection();
        connection.start();

        // 3、通过连接创建会话 session
        // 参数：是否开启事务、签收
        // true表示开启事务，false表示关闭事务
        // Session.AUTO_ACKNOWLEDGE 表示自动确认
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // 4、通过会话 session 创建目的地（具体是队列还是topic）
        Queue queue = session.createQueue("queue01");

        // 5、根据目的地 queue，使用会话 session 创建消息的消费者 consumer
        MessageConsumer consumer = session.createConsumer(queue);

        while (true) {
            // consumer.receive(); 一直等待
            // consumer.receive(long time); 超时就不等了，单位 ms
            TextMessage receive = (TextMessage)consumer.receive();
            if (receive != null) {
                System.out.println("消息接收者收到消息：" + receive.getText());
            } else {
                break;
            }
        }

        // 6、关闭资源
        consumer.close();
        session.close();
        connection.close();

        System.out.println("消息接收完毕！");
    }
}
