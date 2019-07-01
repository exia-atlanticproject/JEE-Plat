package data.model.Message;

import org.json.JSONObject;

public class AddDeviceMessage {
    private String deviceMac;
    private String deviceModel;
    private String deviceName;
    private String deviceUid;

    public AddDeviceMessage(String deviceMac, String deviceModel, String deviceName, String deviceUid) {
        this.deviceMac = deviceMac;
        this.deviceModel = deviceModel;
        this.deviceName = deviceName;
        this.deviceUid = deviceUid;
    }

    public String getDeviceMac() {
        return deviceMac;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getDeviceUid() {
        return deviceUid;
    }

    public static AddDeviceMessage parse(Object payload) {
        JSONObject jsonObject = (JSONObject)payload;
        return new AddDeviceMessage(
                jsonObject.has("macAddress") ? jsonObject.getString("macAddress") : "",
                jsonObject.getString("deviceType"),
                jsonObject.getString("deviceName"),
                jsonObject.getString("deviceId")
        );
    }

    @Override
    public String toString() {
        return "AddDeviceMessage{" +
                "deviceMac='" + deviceMac + '\'' +
                ", deviceModel='" + deviceModel + '\'' +
                ", deviceName='" + deviceName + '\'' +
                '}';
    }
}
