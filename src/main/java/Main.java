import javax.jms.Session;

public class Main {
    private static final String URL_TCP = "tcp://localhost:61616";
    private static final String URL_VM = "vm://broker";
    private static final String QUEUE_NAME = "queue";
    private static final boolean IS_TRANSACTED = true;
    private static final int MODEL_SESSION = Session.CLIENT_ACKNOWLEDGE;
    private static final String TEXT_MESSAGE = "Hello, world!";

    public static void main(String[] args) {
        Sender sender = new Sender(QUEUE_NAME, IS_TRANSACTED, URL_VM, MODEL_SESSION, TEXT_MESSAGE);
        Thread threadSender = new Thread(sender, "SenderThread");

        Receiver receiver = new Receiver(QUEUE_NAME, IS_TRANSACTED, URL_VM, MODEL_SESSION);
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
