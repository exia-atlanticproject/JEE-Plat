package data;

import data.model.Entity.DevicesEntity;
import data.model.Entity.MetricsEntity;
import data.model.Entity.UsersEntity;
import data.model.Message.*;
import org.json.JSONArray;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Routes {
    GET_DEVICES("GetDevices", params -> {
        List list = QueryExecutor.getInstance().getDevices().stream()
                .map(DevicesEntity::toJsonString).collect(Collectors.toList());
        return new JSONArray(list).toString();
    }),
    GET_USERS("GetUsers", params -> {
        List list = QueryExecutor.getInstance().getUsers().stream()
                .map(UsersEntity::toJsonString).collect(Collectors.toList());
        return new JSONArray(list).toString();
    }),
    GET_USER_DEVICES("GetUserDevices", payload -> {
        List list = QueryExecutor.getInstance().getUserDevices(GetterWithIdMessage.parse(payload).getObjectId()).stream().map(DevicesEntity::toJsonString).collect(Collectors.toList());
        return new JSONArray(list).toString();
    }),
    GET_DEVICE("GetDevice", payload -> QueryExecutor.getInstance().getDevice(GetterWithIdMessage.parse(payload).getObjectId()).toJsonString()),
    GET_USER("GetUser", payload -> QueryExecutor.getInstance().getUser(GetterUserWithUidMessage.parse(payload).getUid()).toJsonString()),
    GET_DEVEICE_METRICS("GetDeviceMetrics", payload -> {
        GetterMetricsMessage params = GetterMetricsMessage.parse(payload);
        List list = QueryExecutor.getInstance().getMetrics(params.getDeviceId(), params.getStart(), params.getEnd()).stream().map(MetricsEntity::toJsonString).collect(Collectors.toList());
        return new JSONArray(list).toString();
    }),
    GET_METRICS("GetMetrics", payload -> {
        GetterMetricsMessage params = GetterMetricsMessage.parse(payload);
        List list = QueryExecutor.getInstance().getMetrics(params.getStart(), params.getEnd()).stream().map(MetricsEntity::toJsonString).collect(Collectors.toList());
        return new JSONArray(list).toString();
    }),
    ADD_METRICS("PostMetrics", payload -> {
        AddMetricMessage params = AddMetricMessage.parse(payload);
        QueryExecutor.getInstance().addMetric(params.getMetricValue(), params.getMetricDate(), params.getMacAddress(), params.getDeviceType(), params.getDeviceName());
        return "";
    }),
    LINK_DEVICE_USER("LinkDevUser", payload -> {
        QueryExecutor.getInstance().linkDeviceToUser(LinkDeviceUserMessage.parse(payload).getUserUid(), LinkDeviceUserMessage.parse(payload).getDeviceId());
        return "";
    }),
    NO_ROUTE("", params -> "");

    private String name;
    private Function<Object, String> function;

    Routes(String routeName, Function<Object, String> func) {
        this.name = routeName;
        this.function = func;
    }

    public String execQuery(Object payload) {
        try {
            return this.function.apply(payload);
        } catch (Exception e){};
        return "";
    }

    public static Routes findRoute(String route) {
        for (Routes value : Routes.values()) {
            if (value.name.equals(route)) return value;
        }
        return NO_ROUTE;
    }
}
