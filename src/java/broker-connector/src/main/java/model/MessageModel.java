package model;

public class MessageModel {
    private String connector;
    private String target;
    private Object payload;

    public MessageModel(String connector, String target, Object payload) {
        this.connector = connector;
        this.target = target;
        this.payload = payload;
    }

    public String getConnector() {
        return connector;
    }

    public String getTarget() {
        return target;
    }

    public Object getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "MessageModel{" +
                "connector='" + connector + '\'' +
                ", target='" + target + '\'' +
                ", payload=" + payload +
                '}';
    }
}
