package data;

import Broker.Connector;
import data.model.Entity.DevicesEntity;
import data.model.Entity.MetricsEntity;
import data.model.Entity.UsersEntity;
import data.model.Message.*;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Routes {
    GET_DEVICES("GetDevices", params -> {
        return new JSONArray(QueryExecutor.getInstance().getDevices());
    }),
    GET_USERS("GetUsers", params -> {
        return new JSONArray(QueryExecutor.getInstance().getUsers());
    }),
    GET_USER_DEVICES("GetUserDevices", payload -> {
        return new JSONArray(QueryExecutor.getInstance().getUserDevices(GetterWithIdMessage.parse(payload).getObjectId()));
    }),
    GET_DEVICE("GetDevice", payload -> QueryExecutor.getInstance().getDevice(GetterWithIdMessage.parse(payload).getObjectId()).toJsonString()),
    GET_USER("GetUser", payload -> QueryExecutor.getInstance().getUser(GetterUserWithUidMessage.parse(payload).getUid()).toJsonString()),
    GET_DEVEICE_METRICS("GetDeviceMetrics", payload -> {
        GetterMetricsMessage params = GetterMetricsMessage.parse(payload);
        log(params.toString());
        return new JSONArray(QueryExecutor.getInstance().getMetrics(params.getDeviceId(), params.getStart(), params.getEnd()));
    }),
    GET_METRICS("GetMetrics", payload -> {
        GetterMetricsMessage params = GetterMetricsMessage.parse(payload);
        return new JSONArray(QueryExecutor.getInstance().getMetrics(params.getStart(), params.getEnd()));
    }),
    ADD_METRICS("telemetry", payload -> {
        AddMetricMessage params = AddMetricMessage.parse(payload);
        log(params.toString());
        QueryExecutor.getInstance().addMetric(params.getMetricValue(), params.getMetricDate(), params.getMacAddress(), params.getDeviceType(), params.getDeviceName(), params.getDeviceId());
        return null;
    }),
    ADD_DEVICE("registerDevice", payload -> {
        AddDeviceMessage params = AddDeviceMessage.parse(payload);
        log(params.toString());
        QueryExecutor.getInstance().addDevice(params.getDeviceMac(), params.getDeviceModel(), params.getDeviceName(), params.getDeviceUid());
        return null;
    }),
    LOGIN("login", payload -> {
        LoginUserMessage params = LoginUserMessage.parse(payload);
        log(params.toString());
        QueryExecutor.getInstance().login(params);
        return null;
    }),
    LINK_DEVICE_USER("LinkDevUser", payload -> {
        QueryExecutor.getInstance().linkDeviceToUser(LinkDeviceUserMessage.parse(payload).getUserUid(), LinkDeviceUserMessage.parse(payload).getDeviceId());
        return null;
    }),
    NO_ROUTE("", params -> "");

    static Logger logger = LoggerFactory.getLogger(Routes.class);

    private String name;
    private Function<Object, Object> function;

    Routes(String routeName, Function<Object, Object> func) {
        this.name = routeName;
        this.function = func;
    }

    private static void log(String message) {
        logger.info(message);
    }

    public Object execQuery(Object payload) {
        try {
            return this.function.apply(payload);
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
