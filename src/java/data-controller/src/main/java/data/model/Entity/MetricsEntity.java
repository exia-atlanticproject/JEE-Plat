package data.model.Entity;

import data.model.Response;
import org.json.JSONObject;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "Metrics", schema = "atlantis")
public class MetricsEntity implements Response {
    private int id;
    private double value;
    private String date;
    private DevicesEntity devicesByIdDevices;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "value", nullable = false)
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Basic
    @Column(name = "date", nullable = false)
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MetricsEntity that = (MetricsEntity) o;
        return id == that.id &&
                value == that.value &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, date);
    }

    @ManyToOne
    @JoinColumn(name = "id_Devices", referencedColumnName = "id", nullable = false)
    public DevicesEntity getDevicesByIdDevices() {
        return devicesByIdDevices;
    }

    public void setDevicesByIdDevices(DevicesEntity devicesByIdDevices) {
        this.devicesByIdDevices = devicesByIdDevices;
    }

    @Override
    public String toJsonString() {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("value", this.value);
        json.put("date", this.date);
        json.put("device", this.devicesByIdDevices.getId());
        return json.toString();
    }
}
