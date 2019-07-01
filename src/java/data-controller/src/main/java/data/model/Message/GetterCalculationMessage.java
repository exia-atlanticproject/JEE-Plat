package data.model.Message;

import org.json.JSONObject;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GetterCalculationMessage {
    private Date start;
    private Date end;
    private int userId;
    private String nameJob;


    public GetterCalculationMessage(Date start, Date end, int userId, String nameJob) {
        this.start = start;
        this.end = end;
        this.userId = userId;
        this.nameJob = nameJob;
    }

    public static GetterCalculationMessage parse(Object payload) {
        JSONObject jsonObject = ((JSONObject)payload);
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        try {
            start.setTime(dateFormat.parse(jsonObject.getString("startJob")));
            end.setTime(dateFormat.parse(jsonObject.getString("endJob")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new GetterCalculationMessage(new Date(start.getTime().getTime()), new Date(end.getTime().getTime()), jsonObject.getInt("userId"), jsonObject.getString("nameJob"));
    }
}
