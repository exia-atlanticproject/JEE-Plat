package data.model.Message;

import org.json.JSONObject;

public class GetterWithIdMessage {
    private int objectId;

    public GetterWithIdMessage(int objectId) {
        this.objectId = objectId;
    }

    public int getObjectId() {
        return objectId;
    }

    public static GetterWithIdMessage parse(Object object) {
        return new GetterWithIdMessage(((JSONObject) object).getInt("id"));
    }

    @Override
    public String toString() {
        return "GetterWithIdMessage{" +
                "objectId=" + objectId +
                '}';
    }
}
