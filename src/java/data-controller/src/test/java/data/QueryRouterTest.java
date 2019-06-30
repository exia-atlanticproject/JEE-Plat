package data;

import Model.MessageModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class QueryRouterTest {

    QueryRouter router;

    {
        try {
            router = new QueryRouter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void simpleExec() {
        String res = router.dispatch(new MessageModel("connectorid", "target", "aabb", "GetDevices", null));
        System.out.println(res);
    }
}