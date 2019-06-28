import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.jupiter.api.Test;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageNotWriteableException;
import javax.jms.TextMessage;

import static org.junit.jupiter.api.Assertions.*;

class ConnectorTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() throws JMSException {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @Test
    void connection() {
        try {
            Connector brokerConnector = new Connector("ssl://localhost:61714");
            brokerConnector.disconnect();
        } catch (Exception e) {
            fail("Unable to connect");
            e.printStackTrace();
        }
    }
}