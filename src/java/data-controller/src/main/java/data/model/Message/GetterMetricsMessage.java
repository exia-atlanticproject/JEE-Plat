package data.model.Message;

import org.json.JSONObject;

import java.sql.Date;

public class GetterMetricsMessage {
    private int deviceId;
    private String start;
    private String end;
    private String deviceModel;
    private String deviceName;

    public GetterMetricsMessage(int deviceId, String start, String end) {
        this.deviceId = deviceId;
        this.start = start;
        this.end = end;
    }

    public GetterMetricsMessage(int deviceId, String start, String end, String deviceModel, String deviceName) {
        this.deviceId = deviceId;
        this.deviceModel = deviceModel;
        this.start = start;
        this.end = end;
        this.deviceName = deviceName;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public static GetterMetricsMessage parse(Object payload) {
        JSONObject jsonObject = ((JSONObject) payload);
        if (jsonObject.has("id")) return new GetterMetricsMessage(jsonObject.getInt("id"), jsonObject.getString("start"),  jsonObject.getString("end"));
        return new GetterMetricsMessage(0, ((JSONObject) payload).getString("start"),  ((JSONObject) payload).getString("end"));
    }

    public static GetterMetricsMessage parseCalculation(Object payload) {
        JSONObject jsonObject = ((JSONObject) payload);
        if (jsonObject.has("id")) return new GetterMetricsMessage(jsonObject.getInt("id"), jsonObject.getString("startJob"),  jsonObject.getString("endJob"));
        return new GetterMetricsMessage(0, jsonObject.getString("startJob"),  jsonObject.getString("endJob"));
    }

    public static GetterMetricsMessage parseWithInsert(Object payload) {
        JSONObject jsonObject = ((JSONObject) payload);
        return new GetterMetricsMessage(0, jsonObject.getString("startJob"),  jsonObject.getString("endJob"), jsonObject.getString("deviceType"), jsonObject.getString("deviceName"));
    }
}
