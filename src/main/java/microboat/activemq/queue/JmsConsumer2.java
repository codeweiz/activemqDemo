package microboat.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @author zhouwei
 */
public class JmsConsumer2 {

    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";

    public static void main(String[] args) throws JMSException, IOException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = factory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("queue01");

        MessageConsumer consumer = session.createConsumer(queue);

        consumer.setMessageListener(message -> {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage)message;
                try {
                    System.out.println(textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        // 阻塞等待
        System.in.read();

        consumer.close();
        session.close();
        connection.close();

        System.out.println("消息接收完毕！");
    }
}
