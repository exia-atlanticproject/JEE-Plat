package data;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class QueryRouterTest {

    QueryRouter router = new QueryRouter();

    QueryRouterTest() throws Exception {
    }

    @Test
    void simpleExec() {
//        String res = router.dispatch("GetDevices");
//        System.out.println(res);
    }

}