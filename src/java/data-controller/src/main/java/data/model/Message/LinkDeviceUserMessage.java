package data.model.Message;

import org.json.JSONObject;

public class LinkDeviceUserMessage {
    private int userUid;
    private int deviceId;


    public LinkDeviceUserMessage(int userUid, int userId) {
        this.userUid = userUid;
        this.deviceId = userId;
    }

    public int getUserUid() {
        return userUid;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public static LinkDeviceUserMessage parse(Object payload) {
        return new LinkDeviceUserMessage(((JSONObject)payload).getInt("userId"), ((JSONObject)payload).getInt("deviceId"));
    }

    @Override
    public String toString() {
        return "LinkDeviceUserMessage{" +
                "userUid=" + userUid +
                ", deviceId=" + deviceId +
                '}';
    }
}
