package data.model.Message;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetterCalculationMessage {
    private String start;
    private String end;
    private int userId;
    private String nameJob;


    public GetterCalculationMessage(String start, String end, int userId, String nameJob) {
        this.start = start;
        this.end = end;
        this.userId = userId;
        this.nameJob = nameJob;
    }

    public static GetterCalculationMessage parse(Object payload) {
        JSONObject jsonObject = ((JSONObject)payload);
        SimpleDateFormat parser = new SimpleDateFormat("MM/dd/yy HH:mm:ss a");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date start = null;
        Date end = null;
        try {
            start = parser.parse(jsonObject.getString("startJob"));
            end = parser.parse(jsonObject.getString("endJob"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new GetterCalculationMessage(
                formatter.format(start),
                formatter.format(end),
                jsonObject.getInt("userId"),
                jsonObject.getString("nameJob")
        );
    }

    @Override
    public String toString() {
        return "GetterCalculationMessage{" +
                "start=" + start +
                ", end=" + end +
                ", userId=" + userId +
                ", nameJob='" + nameJob + '\'' +
                '}';
    }
}
