package data;

import Broker.Connector;
import org.json.JSONObject;

public class testor {
    public static void main(String[] args) {
        Connector connector = Connector.getInstance();
        try {
            connector.connect("tcp://localhost:61616");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("action", "GetUser");
            JSONObject payload = new JSONObject();
            payload.put("id", "GG");
            jsonObject.put("payload", payload);
            connector.emit("Data-Controller", jsonObject.toString(), messageModel -> {
                System.out.println(messageModel.toString());
                System.out.println(messageModel.getPayload().toString());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
