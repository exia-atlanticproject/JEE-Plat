package data;

import data.model.DevicesEntity;
import data.model.MetricsEntity;
import data.model.UsersEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QueryExecutor {
    private Session session;
    private static QueryExecutor instance = new QueryExecutor();

    private QueryExecutor() {
        session = new Configuration().configure().buildSessionFactory().openSession();
    }

    public static QueryExecutor getInstance(){
        return instance;
    }

    public List getDevices() {
        Query query = session.createQuery("from DevicesEntity");
        return query.list();
    }

    public List getUserDevices(int userId) {
        Query query = session.createQuery("from DevicesEntity device where device.owner.id="+userId);
        return query.list();
    }

    public DevicesEntity getDevice(int id) {
        return session.get(DevicesEntity.class, id);
    }

    public List<UsersEntity> getUsers() {
        Query query = session.createQuery("from UsersEntity");
        return query.list();
    }

    public UsersEntity getUser(int id) {
        return session.get(UsersEntity.class, id);
    }

    public UsersEntity getUser(String email) {
        return session.get(UsersEntity.class, email);
    }

    public List<MetricsEntity> getMetrics(int deviceId) {
        return this.getMetrics(deviceId, null);
    }

    public List<MetricsEntity> getMetrics(int deviceId, Date start) {
        return this.getMetrics(deviceId, start, null);
    }

    public List<MetricsEntity> getMetrics(int deviceId, Date start, Date end) {
        return null;
    }

    public void createUser(UsersEntity user) {
        session.getTransaction().begin();
        session.save(user);
        session.getTransaction().commit();
    }

    public DevicesEntity addDevice(String model, String mac) {
        session.getTransaction().begin();
        DevicesEntity device = new DevicesEntity();
        device.setMacAddress(mac);
        device.setModel(model);
        session.save(device);
        session.getTransaction().commit();
        return (DevicesEntity) session.createQuery(String.format("FROM DevicesEntity D WHERE D.macAddress = '%s'", mac)).uniqueResult();
    }

    public MetricsEntity addMetric(int value, java.sql.Date date, String deviceMac){
        MetricsEntity metric = new MetricsEntity();
        metric.setDate(date);
        metric.setValue(value);
        metric.setDevicesByIdDevices(session.get(DevicesEntity.class, deviceMac));
        session.save(metric);
        return metric;
    }

    public void linkDeviceToUser(int userId, int deviceId) {
        session.getTransaction().begin();
        DevicesEntity device = session.get(DevicesEntity.class, deviceId);
        device.setOwner(session.get(UsersEntity.class, userId));
        session.update(device);
        session.getTransaction().commit();
    }

    public void close() {
        session.close();
    }
}
