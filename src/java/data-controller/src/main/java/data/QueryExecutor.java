package data;

import data.model.Entity.DevicesEntity;
import data.model.Entity.MetricsEntity;
import data.model.Entity.UsersEntity;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

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

    public List<DevicesEntity> getDevices() {
        Query query = session.createQuery("from DevicesEntity");
        return query.list();
    }

    public List<DevicesEntity> getUserDevices(int userId) {
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

    public UsersEntity getUser(String uid) {
        return (UsersEntity) session.createQuery("from UsersEntity user where user.uid='"+uid+"'").list().get(0);
    }

    public List getMetrics(int deviceId) {
        return session.createQuery("from MetricsEntity metric where metric.devicesByIdDevices="+deviceId).list();

    }

    public List getMetrics(int deviceId, String start) {
        return session.createQuery("from MetricsEntity metric where metric.date >= '"+start+"' and metric.devicesByIdDevices="+deviceId).list();

    }

    public List<MetricsEntity> getMetrics(int deviceId, String start, String end) {
        return session.createQuery("from MetricsEntity metric where metric.date >= '"+start+"' and metric.date <='"+end+"' and metric.devicesByIdDevices="+deviceId).list();
    }

    public List<MetricsEntity> getMetrics(String start, String end) {
        return session.createQuery("from MetricsEntity metric where metric.date >= '"+start+"' and metric.date <='"+end+"'").list();
    }

    public void createUser(UsersEntity user) {
        if (!session.getTransaction().isActive()) session.getTransaction().begin();
        session.save(user);
        session.getTransaction().commit();
    }

    public DevicesEntity addDevice(String deviceMac, String deviceModel, String deviceName) {
        if (!session.getTransaction().isActive()) session.getTransaction().begin();
        DevicesEntity device = new DevicesEntity();
        device.setMacAddress(deviceMac);
        device.setModel(deviceModel);
        session.save(device);
        session.flush();
        session.getTransaction().commit();
        return (DevicesEntity) session.createQuery(String.format("FROM DevicesEntity D WHERE D.macAddress = '%s'", deviceMac)).uniqueResult();
    }

    public MetricsEntity addMetric(double value, String date, String deviceMac, String deviceModel, String deviceName){
        MetricsEntity metric = new MetricsEntity();
        metric.setDate(date);
        metric.setValue(value);
        DevicesEntity metricDevice;
        List device = session.createQuery("from DevicesEntity device where device.macAddress='"+deviceMac+"'").list();
        if (device.size() == 0) {
            metricDevice = this.addDevice(deviceMac, deviceModel, deviceName);
        } else {
            metricDevice = (DevicesEntity) device.get(0);
        }
        if (!session.getTransaction().isActive()) session.getTransaction().begin();
        metric.setDevicesByIdDevices(metricDevice);
        session.save(metric);
        session.flush();
        session.getTransaction().commit();
        return metric;
    }

    public void linkDeviceToUser(int userId, int deviceId) {
        if (!session.getTransaction().isActive()) session.getTransaction().begin();
        DevicesEntity device = session.get(DevicesEntity.class, deviceId);
        device.setOwner(session.get(UsersEntity.class, userId));
        session.update(device);
        session.getTransaction().commit();
    }

    public void close() {
        session.close();
    }
}
