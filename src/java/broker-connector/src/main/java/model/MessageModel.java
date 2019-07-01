package model;

public class MessageModel {

    private String source;
    private String callback;
    private String action;
    private Object payload;

    public MessageModel(String source, String action, String callback, Object payload) {
        this.payload = payload;
        this.callback = callback;
        this.source = source;
        this.action = action;
    }

    public String getSource() {
        return source;
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
                ", payload=" + payload +
                ", callback=" + callback +
                ", action=" + action +
                '}';
    }
}
