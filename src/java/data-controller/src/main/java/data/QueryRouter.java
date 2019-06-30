package data;

import Broker.Connector;
import Model.MessageModel;

import javax.jms.JMSException;

public class QueryRouter {

    Connector brokerConnector = Connector.getInstance();

    public QueryRouter() throws Exception {
        brokerConnector.connect("tcp://localhost:61616");
        brokerConnector.setOnAll(this::respond);

    }

    private void respond(MessageModel message) {
        String result = dispatch(message);
        if (!message.getCallback().equals("")) {
            try {
                brokerConnector.emit(message.getCallback(), result);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    String dispatch(MessageModel message) {
        return Routes.findRoute(message.getAction()).execQuery(message.getPayload());
    }
}
