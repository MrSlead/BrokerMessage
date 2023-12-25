package service;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.ConnectionFactory;
import javax.jms.*;

public abstract class MessageService implements Runnable {
    private final static Logger log = LoggerFactory.getLogger(MessageService.class);

    private ConnectionFactory connectionFactory;
    private Connection connection;
    private String brokerUrl;
    private String queueName;
    private int modelSession;
    private boolean isTransacted;

    public MessageService(String brokerUrl, String queueName, int modeSession, boolean isTransacted) {
        this.brokerUrl = brokerUrl;
        this.queueName = queueName;
        this.modelSession = modeSession;
        this.isTransacted = isTransacted;
        connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER, ActiveMQConnectionFactory.DEFAULT_PASSWORD, this.brokerUrl);
        log.info("Created connection factory to " + brokerUrl);
    }

    public Session getSession() throws Exception {
        try {
            if(connection == null) {
                connection = connectionFactory.createConnection();
                connection.start();
                log.info("Created connection");
            }
            Session session = connection.createSession(isTransacted(), getModelSession());
            log.info("Created session");

            return session;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new Exception("Dont create connection for" + this.getClass().getName());
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


    public String getBrokerUrl() {
        return brokerUrl;
    }

    public String getQueueName() {
        return queueName;
    }

    public int getModelSession() {
        return modelSession;
    }

    public boolean isTransacted() {
        return isTransacted;
    }
}
