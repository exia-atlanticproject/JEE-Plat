package io.swagger.sample.util;

import Broker.Connector;

public class BrokerConnector {
    private static Connector instance = null;

    private BrokerConnector() {
    }

    public static Connector getInstance() {
        if (instance == null) {
            try {
                instance = new Connector("tcp://localhost:61616");
                return instance;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }
}
