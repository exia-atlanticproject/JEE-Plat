import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.jupiter.api.Test;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageNotWriteableException;
import javax.jms.TextMessage;

import static org.junit.jupiter.api.Assertions.*;

class ConnectorTest {

    private Connector brokerconnector;

    {
        try {
            brokerconnector = new Connector(ActiveMQConnection.DEFAULT_BROKER_URL);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    ;
    @org.junit.jupiter.api.BeforeEach
    void setUp() throws JMSException {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }
}