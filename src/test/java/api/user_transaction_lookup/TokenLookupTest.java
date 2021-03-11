package api.user_transaction_lookup;

import api.enums.Port;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import util.base_test.BaseApiTest;

import java.util.Hashtable;
import java.util.Map;

import static api.clients.TokenLookupClient.getUserTokens;
import static org.apache.http.HttpStatus.SC_OK;

public class TokenLookupTest extends BaseApiTest {

    @Test
    @Description("30065 :: profile-management :: public internal :: GET /user/tokens :: Token Lookup API (1.0)")
    @TmsLink("TECH-54465")
    public void testTokenLookupSuccess() {
        Map <String, String> queryParams = new Hashtable<>();
        queryParams.put("sourceIdentifier","2341111111111");
        queryParams.put("clientId","3");
        queryParams.put("channelId","7");
        queryParams.put("productTypeId","17");
        queryParams.put("limit","1");

        getUserTokens(Port.USER_TRANSACTIONS, queryParams)
                .then().assertThat().statusCode(SC_OK)
                .body("userTokens[0].targetIdentifier", Matchers.containsString("2341111111111"))
                .body("userTokens[0].clientId", Matchers.is(3))
                .body("userTokens[0].channelId", Matchers.is(7))
                .body("userTokens[0].productTypeId", Matchers.is(17));
    }

}

