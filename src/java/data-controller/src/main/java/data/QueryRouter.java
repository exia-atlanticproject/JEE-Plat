package data;

import Broker.Connector;
import model.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;

public class QueryRouter {

    private Logger logger = LoggerFactory.getLogger(QueryRouter.class);
    private Connector brokerconnector = Connector.getInstance();

    public QueryRouter() throws Exception {
        brokerconnector.connect("tcp://localhost:61616", "Data-Controller");
        brokerconnector.setOnMessageReceived(this::dispatch);
    }

    void dispatch(MessageModel message) {
        logger.info(String.format("New message %s %s %s", message.getAction(), message.getCallback(), message.getSource()));
        String result = Routes.findRoute(message.getAction()).execQuery(message.getPayload());
        if (!message.getCallback().equals("")) {
            try {
                message.respond(brokerconnector, result);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
