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
        //TODO transform Object payload into map
        String result = Routes.findRoute(message.getAction()).execQuery(null);
        if (!message.getCallback().equals("")) {
            try {
                brokerconnector.emit(message.getCallback(), result);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
