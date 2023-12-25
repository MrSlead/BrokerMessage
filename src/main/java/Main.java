import service.MessageService;
import service.Receiver;
import service.Sender;

import javax.jms.DeliveryMode;
import javax.jms.Session;

public class Main {
    private static final String URL_TCP = "tcp://localhost:61616";
    private static final String URL_VM = "vm://broker";
    private static final String QUEUE_NAME = "queue";
    private static final boolean IS_TRANSACTED = false;
    private static final int MODEL_SESSION = Session.CLIENT_ACKNOWLEDGE;
    private static final int DELIVERY_MODE = DeliveryMode.NON_PERSISTENT;
    private static final String TEXT_MESSAGE = "Hello, world!";

    public static void main(String[] args) {
        MessageService sender = new Sender(URL_VM, QUEUE_NAME, MODEL_SESSION, IS_TRANSACTED, DELIVERY_MODE, TEXT_MESSAGE);
        Thread threadSender = new Thread(sender, "SenderThread");

        MessageService receiver = new Receiver(URL_VM, QUEUE_NAME, MODEL_SESSION, IS_TRANSACTED);
        Thread threadReceiver = new Thread(receiver, "ReceiverThread");

        Broker embeddedBroker = new Broker(true, URL_VM, "broker");
        embeddedBroker.run();
        try {
            threadSender.start();
            threadSender.join();

            threadReceiver.start();
            threadReceiver.join();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
