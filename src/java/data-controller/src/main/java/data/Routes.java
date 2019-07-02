package data;

import Broker.Connector;
import data.model.Entity.DevicesEntity;
import data.model.Entity.MetricsEntity;
import data.model.Entity.UsersEntity;
import data.model.Message.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Routes {
    GET_DEVICES("GetDevices", params -> {
        return QueryExecutor.getInstance().getDevices();
    }),
    GET_USERS("GetUsers", params -> {
        return QueryExecutor.getInstance().getUsers();
    }),
    GET_USER_DEVICES("GetUserDevices", payload -> {
        return QueryExecutor.getInstance().getUserDevices(GetterWithIdMessage.parse(payload).getObjectId());
    }),
    GET_DEVICE("GetDevice", payload -> QueryExecutor.getInstance().getDevice(GetterWithIdMessage.parse(payload).getObjectId())),
    GET_USER("GetUser", payload -> QueryExecutor.getInstance().getUser(GetterUserWithUidMessage.parse(payload).getUid())),
    GET_DEVEICE_METRICS("GetDeviceMetrics", payload -> {
        GetterMetricsMessage params = GetterMetricsMessage.parse(payload);
        log(params.toString());
        return QueryExecutor.getInstance().getMetrics(params.getDeviceId(), params.getStart(), params.getEnd());
    }),
    GET_METRICS("GetMetrics", payload -> {
        GetterMetricsMessage params = GetterMetricsMessage.parse(payload);
        return QueryExecutor.getInstance().getMetrics(params.getStart(), params.getEnd());
    }),
    ADD_METRICS("telemetry", payload -> {
        Session session = createSession();
        session.getTransaction().begin();
        AddMetricMessage params = AddMetricMessage.parse(payload);
        log(params.toString());
        System.out.println(params);
        QueryExecutor.getInstance().addMetric(params.getMetricValue(), params.getMetricDate(), params.getMacAddress(), params.getDeviceType(), params.getDeviceName(), params.getDeviceId(), session);
        session.getTransaction().commit();
        return null;
    }),
    ADD_DEVICE("registerDevice", payload -> {
        Session session = createSession();
        session.getTransaction().begin();
        AddDeviceMessage params = AddDeviceMessage.parse(payload);
        log(params.toString());
        QueryExecutor.getInstance().addDevice(params.getDeviceMac(), params.getDeviceModel(), params.getDeviceName(), params.getDeviceUid(), session);
        session.getTransaction().commit();
        return null;
    }),
    LOGIN("login", payload -> {
        LoginUserMessage params = LoginUserMessage.parse(payload);
        log(params.toString());
        QueryExecutor.getInstance().login(params);
        return null;
    }),
    LINK_DEVICE_USER("LinkDevUser", payload -> {
        Session session = createSession();
        QueryExecutor.getInstance().linkDeviceToUser(LinkDeviceUserMessage.parse(payload).getUserUid(), LinkDeviceUserMessage.parse(payload).getDeviceId(), session);
        return null;
    }),
    NO_ROUTE("", params -> "");

    static SessionFactory factory = new Configuration().configure().buildSessionFactory();

    private static Session createSession() {
        return factory.openSession();
    }
    static Logger logger = LoggerFactory.getLogger(Routes.class);

    private String name;
    private Function<Object, String> function;

    Routes(String routeName, Function<Object, String> func) {
        this.name = routeName;
        this.function = func;
    }

    private static void log(String message) {
        logger.info(message);
    }

    public Object execQuery(Object payload) {
        try {
            String res = this.function.apply(payload);
            return res;
        } catch (Exception e){
            e.printStackTrace();
        };
        return "";
    }

    public static Routes findRoute(String route) {
        for (Routes value : Routes.values()) {
            if (value.name.equals(route)) {
                logger.info("Execute route "+value.name);
                return value;
            }
        }
        return NO_ROUTE;
    }
}
