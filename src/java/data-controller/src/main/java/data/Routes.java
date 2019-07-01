package data;

import data.model.DevicesEntity;
import data.model.UsersEntity;
import org.json.JSONArray;

import java.util.List;
import java.util.Map;
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
    GET_USER_DEVICES("GetUserDevices", params -> "[{\"id\": 1, \"mode\": \"test\", \"mac_address\": \"abcdefg55\"}]"),
    GET_DEVICE("GetDevice", params -> "{\"id\": 1, \"mode\": \"test\", \"mac_address\": \"abcdefg55\"}"),
    GET_USER("GetUser", params -> "{\"id\": 1, \"username\": \"test\", \"name\": \"test\", \"email\": \"test@email.com\", \"role\": \"CLIENT\"}"),
    GET_METRICS("GetMetrics", params -> "[{\"id\": 1, \"value\": \"42\", \"date\": 1561909009, \"device\": 1}]"),
    GET_METRIC("GetMetric", params -> "{\"id\": 1, \"value\": \"42\", \"date\": 1561909009, \"device\": 1}"),
    LINK_DEVICE_USER("LinkDevUser", params -> ""),
    NO_ROUTE("", params -> "");

    private String name;
    private Function<Map, String> function;

    public String getName() {
        return name;
    }

    Routes(String routeName, Function<Map, String> func) {
        this.name = routeName;
        this.function = func;
    }

    public String execQuery(Map params) {
        return this.function.apply(params);
    }

    public static Routes findRoute(String route) {
        for (Routes value : Routes.values()) {
            if (value.name.equals(route)) return value;
        }
        return NO_ROUTE;
    }
}
