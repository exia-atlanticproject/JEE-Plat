package Broker;

import model.MessageModel;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class Connector implements MessageListener, ExceptionListener {
    private Logger logger = LoggerFactory.getLogger(Connector.class);

    private Map<String, Consumer<MessageModel>> listeners;
    private String id = UUID.randomUUID().toString();

    private Connection connection;
    private Session session;
    private ConnectorStatus status = ConnectorStatus.DISCONNECTED;

    private Consumer<MessageModel> onMessageReceived;

    private static Connector connector = new Connector();

    private Connector() {
    }

    public static Connector getInstance() {
        return connector;
    }

    public void connect(String url, boolean test) throws Exception {
        if (this.status == ConnectorStatus.CONNECTED) return;
        if (test) this.id = "TEST";
        this.init(url);
    }

    public void connect(String url) throws Exception {
        if (this.status == ConnectorStatus.CONNECTED) return;
        this.init(url);
    }

    public void connect(String url, String name) throws Exception {
        this.id = name;
        this.init(url);
    }


    private void init(String url) throws Exception {
        listeners = new HashMap<>();
        logger.info("Connection...");
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        connection = connectionFactory.createConnection("service", "safepw");
        connection.setExceptionListener(this);

        connection.setClientID(id);
        logger.info(String.format("Broker.Connector id = %s", id));
        connection.setExceptionListener(this);
        connection.start();
        logger.info(String.format("Connected to %S", url));
        this.status = ConnectorStatus.CONNECTED;
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createTopic(id);
        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(this);
    }

    public String getId() {
        return this.id;
    }

    public ConnectorStatus getStatus() {
        return this.status;
    }

    public void disconnect() throws JMSException {
        connection.close();
        this.status = ConnectorStatus.DISCONNECTED;
    }

    public void emit(String topic, String message) throws JMSException {
        emit(topic, message, null);
    }

    public void respond(String callback, String source, String message) throws JMSException {
        sendMessage(callback, source, message);
    }
//
//    public void sendComputeData(String message) throws JMSException {
//        sendMessage("calculation", "", message);
//    }

    private void sendMessage(String callback, String source, String message) throws JMSException {
        MessageProducer producer = session.createProducer(session.createTopic(callback));
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        TextMessage textMessage = session.createTextMessage();
        JSONObject json = new JSONObject(message);
        json.put("callback", this.id);
        json.put("source", source);
        textMessage.setText(json.toString());
        producer.send(textMessage);
    }

    public void emit(String topic, String message, Consumer<MessageModel> callback) throws JMSException {

        String source = UUID.randomUUID().toString();

        sendMessage(topic, source, message);
        this.once(source, callback);
    }

    public void once(String topic, Consumer<MessageModel> callback) {
        this.listeners.put(topic, callback);
    }

    public void setOnMessageReceived(Consumer<MessageModel> onMessage) {
        this.onMessageReceived = onMessage;
    }

    public void removeListener(String topic) {
        this.listeners.remove(topic);
    }

    private void messageHandler(String message) {
        try {
            JSONObject jsonMsg = new JSONObject(message);
            Consumer<MessageModel> call = this.listeners.get(jsonMsg.getString("source"));
            if (onMessageReceived != null) onMessageReceived.accept(new MessageModel(jsonMsg.getString("source"), jsonMsg.getString("action"), jsonMsg.getString("callback"), jsonMsg.get("payload")));
            if (call != null) {
                call.accept(new MessageModel(jsonMsg.getString("source"), jsonMsg.getString("action"), jsonMsg.getString("callback"), jsonMsg.get("payload")));
                this.removeListener(jsonMsg.getString("source"));
            }
        } catch (Throwable e) {
            logger.error(String.format("Malformed message: %s", message));
        }

    }


    @Override
    public void onException(JMSException e) {
        logger.error(String.format("Error occured in the broker: %s %s", e.getErrorCode(), e.getMessage()));
    }

    @Override
    public void onMessage(Message message) {
        try {
            String text = ((TextMessage)message).getText();
            message.acknowledge();
            this.messageHandler(text);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    enum ConnectorStatus {
        CONNECTED,
        DISCONNECTED
    }
}