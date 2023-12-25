package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

public class Receiver extends MessageService {
    private final static Logger log = LoggerFactory.getLogger(Receiver.class);

    public Receiver(String brokerUrl, String queueName, int modeSession, boolean isTransacted) {
        super(brokerUrl, queueName, modeSession, isTransacted);
    }

    @Override
    public void run() {
        receiveMessage();
    }

    public void receiveMessage() {
        log.info("start 'receiveMessage' method");
        Session session = null;
        try {
            session = getSession();

            Queue queue = session.createQueue(getQueueName());
            MessageConsumer consumer = session.createConsumer(queue);
            long startTime = System.currentTimeMillis();
            Message message = consumer.receive();

            TextMessage responseMessage = (TextMessage) message;
            log.info("Message: " + responseMessage.getText());

            if(getModelSession() == Session.CLIENT_ACKNOWLEDGE) {
                message.acknowledge();
            }
            if(isTransacted() == true) {
                session.commit();
            }

            System.out.println(System.currentTimeMillis()  - startTime);

            session.close();
            closeConnection();
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
