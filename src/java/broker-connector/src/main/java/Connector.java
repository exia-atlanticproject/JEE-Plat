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

    public Connector(String url) throws JMSException {
        this.init(url);
    }

    public Connector(String url, boolean test) throws JMSException {
        this.id = "TEST";
        this.init(url);
    }

    private void init(String url) throws JMSException {
        listeners = new HashMap<>();
        logger.info("Connection...");
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        connection = connectionFactory.createConnection();
        connection.setClientID(id);
        logger.info(String.format("Connector id = %s", id));
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

    public void emit(String topic, JSONObject message) throws JMSException {
        MessageProducer producer = session.createProducer(session.createTopic(topic));
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        TextMessage textMessage = session.createTextMessage();
        message.put("origin", this.id);
        textMessage.setText(message.toString());
        producer.send(textMessage);
    }

    public void once(String topic, Consumer<MessageModel> callback) {
        this.listeners.put(topic, callback);
    }

    public void removeListener(String topic) {
        this.listeners.remove(topic);
    }

    private void messageHandler(String message) {
        try {
            JSONObject jsonMsg = new JSONObject(message);
            Consumer<MessageModel> call = this.listeners.get(jsonMsg.getString("target"));
            if (call != null) {
                call.accept(new MessageModel(this.id, jsonMsg.getString("target"), jsonMsg.get("payload")));
                this.removeListener(jsonMsg.getString("target"));
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