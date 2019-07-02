package data;

import data.model.Entity.DevicesEntity;
import data.model.Entity.MetricsEntity;
import data.model.Entity.UserRoles;
import data.model.Entity.UsersEntity;
import data.model.Message.LoginUserMessage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.json.JSONArray;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class QueryExecutor {
    private Session session;
    private SessionFactory factory;
    private static QueryExecutor instance = new QueryExecutor();

    private QueryExecutor() {
        session = new Configuration().configure().buildSessionFactory().openSession();
        factory = new Configuration().configure().buildSessionFactory();
    }

    public static QueryExecutor getInstance(){
        return instance;
    }

    public String getDevices() {
        Query query = session.createQuery("from DevicesEntity");
        return "["+query.list().stream().map(device -> ((DevicesEntity)device).toJsonString()).collect(Collectors.joining(", "))+"]";
    }

    public String getUserDevices(int userId) {
        Query query = session.createQuery("from DevicesEntity device where device.owner.id="+userId);
        return "["+query.list().stream().map(device -> ((DevicesEntity)device).toJsonString()).collect(Collectors.joining(", "))+"]";
    }

    public String getDevice(int id) {
        DevicesEntity device = session.get(DevicesEntity.class, id);
        return device.toJsonString();
    }

    public String getUsers() {
        Query query = session.createQuery("from UsersEntity");
        return "["+query.list().stream().map(device -> ((UsersEntity)device).toJsonString()).collect(Collectors.joining(", "))+"]";
    }

    public String getUser(int id) {
        UsersEntity user = session.get(UsersEntity.class, id);
        return user.toJsonString();
    }

    public String getUser(String uid) {
        UsersEntity user = (UsersEntity) session.createQuery("from UsersEntity user where user.uid='"+uid+"'").uniqueResult();
        return user.toJsonString();
    }

    public String getMetrics(int deviceId) {
        List list = session.createQuery("from MetricsEntity metric where metric.devicesByIdDevices="+deviceId).list();
        return "["+list.stream().map(device -> ((MetricsEntity)device).toJsonString()).collect(Collectors.joining(", "))+"]";


    }

    public String getMetrics(int deviceId, String start) {
        List list = session.createQuery("from MetricsEntity metric where metric.date >= '"+start+"' and metric.devicesByIdDevices="+deviceId).list();
        return "["+list.stream().map(device -> ((MetricsEntity)device).toJsonString()).collect(Collectors.joining(", "))+"]";


    }

    public String getMetrics(int deviceId, String start, String end) {
        List list = session.createQuery("from MetricsEntity metric where metric.date >= '"+start+"' and metric.date <='"+end+"' and metric.devicesByIdDevices="+deviceId).list();
        return "["+list.stream().map(device -> ((MetricsEntity)device).toJsonString()).collect(Collectors.joining(", "))+"]";
    }

    public String getMetrics(String start, String end) {
        List list = session.createQuery("from MetricsEntity metric where metric.date >= '"+start+"' and metric.date <='"+end+"'").list();
        return "["+list.stream().map(device -> ((MetricsEntity)device).toJsonString()).collect(Collectors.joining(", "))+"]";
    }

    public String login(LoginUserMessage message) {
        Object user =  session.createQuery("from UsersEntity user where user.uid='"+message.getUid()+"'").uniqueResult();
        if (user == null) {
            UsersEntity newUser = new UsersEntity();
            newUser.setEmail(message.getEmail());
            newUser.setName(message.getName());
            newUser.setSurname(message.getSurname());
            newUser.setUid(message.getUid());
            newUser.setRole(UserRoles.CLIENT);
            session.getTransaction().begin();
            session.save(newUser);
            session.flush();
            session.getTransaction().commit();
        }
        return "";
    }

    private void createUser(UsersEntity user) {
        if (!session.getTransaction().isActive()) session.getTransaction().begin();
        session.save(user);
        session.getTransaction().commit();
    }

    public DevicesEntity addDevice(String deviceMac, String deviceModel, String deviceName, String deviceUid, Session sess) {
        DevicesEntity device = new DevicesEntity();
        device.setMacAddress(deviceMac);
        device.setModel(deviceModel);
        device.setUid(deviceUid);
        device.setName(deviceName);
        sess.save(device);
        sess.flush();
//        sess.getTransaction().commit();
//        sess.clear();
        return (DevicesEntity) sess.createQuery(String.format("FROM DevicesEntity D WHERE D.macAddress = '%s'", deviceMac)).uniqueResult();
    }

    public MetricsEntity addMetric(double value, String date, String deviceMac, String deviceModel, String deviceName, String deviceId, Session sess){
        MetricsEntity metric = new MetricsEntity();
        metric.setDate(date);
        metric.setValue(value);
        DevicesEntity metricDevice = (DevicesEntity) sess.createQuery("from DevicesEntity device where device.uid='"+deviceId+"'").uniqueResult();
        if (metricDevice == null) {
            metricDevice = this.addDevice(deviceMac, deviceModel, deviceName, deviceId, sess);
        } else {
            if (!metricDevice.getMacAddress().equals("") && !deviceMac.equals("")) {
                metricDevice.setMacAddress(deviceMac);
            }
        }
        metric.setDevicesByIdDevices(metricDevice);
        sess.save(metric);
        sess.flush();
        sess.clear();
        return metric;
    }

    public void linkDeviceToUser(int userId, int deviceId, Session sess) {
        Transaction transaction = sess.getTransaction();
        transaction.begin();
        DevicesEntity device = sess.get(DevicesEntity.class, deviceId);
        device.setOwner(sess.get(UsersEntity.class, userId));
        sess.update(device);
        sess.flush();
        sess.clear();
        transaction.commit();
    }

    public void close() {
        session.close();
    }
}
