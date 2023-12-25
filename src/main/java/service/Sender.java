package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

public class Sender extends MessageService {
    private final static Logger log = LoggerFactory.getLogger(Sender.class);

    private int deliveryMode;
    private String messageText;

    public Sender(String brokerUrl, String queueName, int modeSession, boolean isTransacted, int deliveryMode) {
        super(brokerUrl, queueName, modeSession, isTransacted);
        this.deliveryMode = deliveryMode;
    }

    public Sender(String brokerUrl, String queueName, int modeSession, boolean isTransacted, int deliveryMode, String messageText) {
        super(brokerUrl, queueName, modeSession, isTransacted);
        this.deliveryMode = deliveryMode;
        this.messageText = messageText;
    }

    @Override
    public void run() {
        send();
    }

    public void send() {
        log.info("start 'send' method");
        Session session = null;
        try {
            session = getSession();

            Queue queue = session.createQueue(getQueueName());
            MessageProducer producer = session.createProducer(queue);
            producer.setDeliveryMode(deliveryMode);

            TextMessage message = session.createTextMessage(messageText);

            producer.send(message);

            if(isTransacted()) {
                session.commit();
            }
            log.info("Sent the message: " + message.getText());

            session.close();
            closeConnection();
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createTextMessage(String messageText) {
        this.messageText = messageText;
    }
}
