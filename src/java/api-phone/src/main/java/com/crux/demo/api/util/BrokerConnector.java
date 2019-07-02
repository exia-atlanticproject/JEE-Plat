package com.crux.demo.api.util;

import Broker.Connector;
import org.json.JSONObject;

public class BrokerConnector {

    private static Connector connector;
    public static final String DataController = "Data-Controller";
    public static final String Calculation = "Calculationr";

    static {
        try {
            connector = Connector.getInstance();
            connector.connect("tcp://192.168.0.32:61616");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connector getConnector() {
        return connector;
    }

    public static String createMessage(String action, JSONObject payload) {
        JSONObject message = new JSONObject();
        message.put("action", action);
        message.put("payload", payload);
        return message.toString();
    }
}
