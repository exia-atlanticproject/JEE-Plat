package data.model.Message;

import org.json.JSONObject;

public class GetterUserWithUidMessage {
    private String uid;

    public GetterUserWithUidMessage(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public static GetterUserWithUidMessage parse(Object object) {
        return new GetterUserWithUidMessage(((JSONObject) object).getString("id"));
    }

    @Override
    public String toString() {
        return "GetterUserWithUidMessage{" +
                "uid='" + uid + '\'' +
                '}';
    }
}
