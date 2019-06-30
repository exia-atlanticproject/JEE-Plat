package data;

import java.util.HashMap;
import java.util.Map;


public class QueryRouter {

    public QueryRouter() {
    }

    public String dispatch(String action) {
        Map<String, String> params = new HashMap<>();
        return Routes.findRoute(action).execQuery(params);
    }
}
