import Broker.Connector;
import org.junit.jupiter.api.Test;

import javax.jms.JMSException;

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
            Connector brokerConnector = Connector.getInstance();
            brokerConnector.connect("mqtt://localhost:1883");
            brokerConnector.disconnect();
        } catch (Exception e) {
            fail("Unable to connect");
            e.printStackTrace();
        }
    }
}