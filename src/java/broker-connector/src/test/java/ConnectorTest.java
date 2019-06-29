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
            Connector brokerConnector = new Connector("tcp://localhost:61616");
            brokerConnector.disconnect();
        } catch (Exception e) {
            fail("Unable to connect");
            e.printStackTrace();
        }
    }
}