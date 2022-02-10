package microboat.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author zhouwei
 */
public class JmsTopicProducer {
    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = factory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("topic-microboat");

        MessageProducer producer = session.createProducer(topic);

        for (int i = 0; i < 6; i++) {
            TextMessage textMessage = session.createTextMessage("topic-msg=>" + i);
            producer.send(textMessage);
        }

        producer.close();
        session.close();
        connection.close();

        System.out.println("topic消息发布完毕！");
    }
}
