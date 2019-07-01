package Model;

public class MessageModel {
    private String connector;
    private String target;
    private String callback;
    private String action;
    private Object payload;

    public MessageModel(String connector, String target, String action, String callback, Object payload) {
        this.connector = connector;
        this.target = target;
        this.payload = payload;
        this.callback = callback;
        this.action = action;
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

    public String getCallback() {
        return callback;
    }

    public String getAction() {
        return action;
    }

    @Override
    public String toString() {
        return "MessageModel{" +
                "connector='" + connector + '\'' +
                ", target='" + target + '\'' +
                ", payload=" + payload +
                ", callback=" + callback +
                ", action=" + action +
                '}';
    }
}
