package api.user_transaction_lookup;

import api.clients.UserTransactionLookupClient;
import api.enums.Port;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import util.base_test.BaseApiTest;

import java.util.Hashtable;
import java.util.Map;

import static api.clients.UserTransactionLookupClient.getUserTransactions;
import static org.apache.http.HttpStatus.SC_OK;

public class UserTransactionLookupTest extends BaseApiTest {

    @Test
    @Description("30065 :: profile-management :: public internal :: GET /userTransactions :: User Transaction Lookup API (1.0)")
    @TmsLink("TECH-54469")
    public void testUserTransactionLookupSuccess() {
        Map <String, String> queryParams = new Hashtable<>();
        queryParams.put("userIdentifier", UserTransactionLookupClient.UserIdentifier);
        queryParams.put("clientId","3");

        getUserTransactions(Port.USER_TRANSACTIONS, queryParams)
                .then().assertThat().statusCode(SC_OK)
                .body("[0].targetIdentifier", Matchers.containsString(UserTransactionLookupClient.UserIdentifier))
                .body("[0].clientId", Matchers.is(3));
    }

}

