package data;

import Broker.Connector;
import model.MessageModel;
import org.json.JSONArray;
import org.json.JSONObject;
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
        System.out.println("dispatch");
        Routes route = Routes.findRoute(message.getAction());
        System.out.println("route founded");
        Object result = route.execQuery(message.getPayload());
        System.out.println("after result");
        JSONObject res = new JSONObject();
        if (result != null) {
            if (result instanceof JSONObject) {
                res.put("payload", (JSONObject)result);
            } else if (result instanceof JSONArray){
                res.put("payload", (JSONArray) result);
            } else {
                res.put("payload", "");
            }
        } else {
            res.put("payload", "");
        }
        res.put("action", "reply");
        if (!message.getCallback().equals("")) {
            try {
                message.respond(brokerconnector, res.toString());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
