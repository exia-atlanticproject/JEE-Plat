package data.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Devices", schema = "atlantis")
public class DevicesEntity {
    private int id;
    private String model;
    private String macAddress;
    private UsersEntity usersEntity;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "model", nullable = false, length = 50)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Basic
    @Column(name = "mac_address", nullable = false, length = 50, unique = true)
    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DevicesEntity that = (DevicesEntity) o;
        return id == that.id &&
                Objects.equals(model, that.model) &&
                Objects.equals(macAddress, that.macAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model, macAddress);
    }

    @Override
    public String toString() {
        return String.format(" %d %s %s", this.id, this.macAddress, this.model);
    }

    @ManyToOne
    @JoinColumn(name = "id_Users", referencedColumnName = "id", nullable = false)
    public UsersEntity getOwner() {
        return usersEntity;
    }

    public void setOwner(UsersEntity user) {
        this.usersEntity = user;
    }
}
