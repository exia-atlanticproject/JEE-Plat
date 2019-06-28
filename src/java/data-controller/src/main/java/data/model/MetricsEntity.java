package data.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "Metrics", schema = "atlantis", catalog = "")
public class MetricsEntity {
    private int id;
    private int value;
    private Date date;
    private DevicesEntity devicesByIdDevices;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "value", nullable = false)
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Basic
    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
}
