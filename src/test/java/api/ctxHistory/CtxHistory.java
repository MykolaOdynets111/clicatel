package api.ctxHistory;

import api.clients.NotificationClient;
import api.clients.ProductLookupClient;
import api.clients.ReserveAndTransactClient;
import api.clients.UsersClient;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import java.util.Hashtable;
import java.util.Map;
import static api.clients.CtxHistory.GetFindSuccessful;
import static org.apache.http.HttpStatus.SC_OK;

public class CtxHistory {

    @Test
    @Description("30177 :: ctx-history :: GET /findSuccessful")
    @TmsLink("TECH-78538")
    public void testFindSuccessful() {

        Map<String, String> map = new Hashtable<>();
        map.put("clientId", ReserveAndTransactClient.TestClient3);
        map.put("targetId", NotificationClient.Identifier_6);
        GetFindSuccessful(map)
                .then().assertThat().statusCode(SC_OK);
    }
}
