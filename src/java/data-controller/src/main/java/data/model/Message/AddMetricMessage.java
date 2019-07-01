package data.model.Message;

import org.json.JSONObject;

public class AddMetricMessage {
    private String deviceId;
    private String metricDate;
    private double metricValue;
    private String deviceType;
    private String macAddress;
    private String deviceName;

    public AddMetricMessage(String deviceId, String metricDate, double metricValue, String deviceType, String macAddress, String deviceName) {
        this.deviceId = deviceId;
        this.metricDate = metricDate;
        this.metricValue = metricValue;
        this.deviceType = deviceType;
        this.macAddress = macAddress;
        this.deviceName = deviceName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getMetricDate() {
        return metricDate;
    }

    public double getMetricValue() {
        return metricValue;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public static AddMetricMessage parse(Object payload) {
        JSONObject jsonObject = new JSONObject(payload);
        return new AddMetricMessage(
                jsonObject.getString("deviceId"),
                jsonObject.getString("metricDate"),
                jsonObject.getDouble("metricValue"),
                jsonObject.getString("deviceType"),
                jsonObject.getString("macAddress"),
                jsonObject.getString("deviceName")
        );
    }
}
