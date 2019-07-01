package data.model.Message;

import org.json.JSONObject;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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

    private static SimpleDateFormat parser = new SimpleDateFormat("MM/dd/yy HH:mm:ss a");
    static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public static GetterMetricsMessage parse(Object payload) {
        JSONObject jsonObject = ((JSONObject) payload);
        if (jsonObject.has("id")) {
            try {
                return new GetterMetricsMessage(
                        jsonObject.getInt("id"),
                        formatter.format(parser.parse(jsonObject.getString("start"))),
                        formatter.format(parser.parse(jsonObject.getString("end")))
                );
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        try {
            return new GetterMetricsMessage(
                    0,
                    formatter.format(parser.parse(jsonObject.getString("start"))),
                    formatter.format(parser.parse(jsonObject.getString("end")))
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static GetterMetricsMessage parseCalculation(Object payload) {
        JSONObject jsonObject = ((JSONObject) payload);
        if (jsonObject.has("id")) {
            try {
                return new GetterMetricsMessage(
                        jsonObject.getInt("id"),
                        formatter.format(parser.parse(jsonObject.getString("startJob"))),
                        formatter.format(parser.parse(jsonObject.getString("endJob")))
                );
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        try {
            return new GetterMetricsMessage(
                    0,
                    formatter.format(parser.parse(jsonObject.getString("startJob"))),
                    formatter.format(parser.parse(jsonObject.getString("endJob")))
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static GetterMetricsMessage parseWithInsert(Object payload) {
        JSONObject jsonObject = ((JSONObject) payload);
        try {
            return new GetterMetricsMessage(
                    0,
                    formatter.format(parser.parse(jsonObject.getString("startJob"))),
                    formatter.format(parser.parse(jsonObject.getString("endJob"))),
                    jsonObject.getString("deviceType"),
                    jsonObject.getString("deviceName")
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "GetterMetricsMessage{" +
                "deviceId=" + deviceId +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", deviceModel='" + deviceModel + '\'' +
                ", deviceName='" + deviceName + '\'' +
                '}';
    }
}
