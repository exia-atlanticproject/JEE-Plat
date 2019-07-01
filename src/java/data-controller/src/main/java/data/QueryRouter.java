package data;

import Broker.Connector;
import model.MessageModel;

import javax.jms.JMSException;

public class QueryRouter {

    private Connector brokerconnector = Connector.getInstance();

    public QueryRouter() throws Exception {
        brokerconnector.connect("tcp://localhost:61616", "Data-Controller");
        brokerconnector.setOnMessageReceived(this::dispatch);
    }

    void dispatch(MessageModel message) {
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
