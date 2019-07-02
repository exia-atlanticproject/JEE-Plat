package data.model.Message;

import org.apache.openjpa.persistence.jest.JSON;
import org.json.JSONObject;

import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;

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
        JSONObject jsonObject = (JSONObject)payload;
        SimpleDateFormat parser = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
//        SimpleDateFormat parser = new SimpleDateFormat("MM/dd/yy HH:mm:ss a");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = parser.parse(jsonObject.getString("metricDate"));
            return new AddMetricMessage(
                    jsonObject.getString("deviceId"),
                    formatter.format(date),
                    jsonObject.getDouble("metricValue"),
                    jsonObject.getString("deviceType"),
                    jsonObject.getString("macAddress"),
                    jsonObject.getString("deviceName")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "AddMetricMessage{" +
                "deviceId='" + deviceId + '\'' +
                ", metricDate='" + metricDate + '\'' +
                ", metricValue=" + metricValue +
                ", deviceType='" + deviceType + '\'' +
                ", macAddress='" + macAddress + '\'' +
                ", deviceName='" + deviceName + '\'' +
                '}';
    }
}
