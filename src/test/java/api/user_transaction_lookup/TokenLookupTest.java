package api.user_transaction_lookup;

import api.clients.TokenLookupClient;
import api.enums.Port;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import util.base_test.BaseApiTest;
import api.enums.ChannelId;

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
        queryParams.put("sourceIdentifier", TokenLookupClient.Identifier_1);
        queryParams.put("clientId",TokenLookupClient.AccessBankID);
        queryParams.put("channelId",TokenLookupClient.UssdID);
        queryParams.put("productTypeId",TokenLookupClient.IflixSubscriptionID);
        queryParams.put("limit",TokenLookupClient.Limit_1);

        getUserTokens(Port.USER_TRANSACTIONS, queryParams)
                .then().assertThat().statusCode(SC_OK)
                .body("userTokens[0].targetIdentifier", Matchers.is(TokenLookupClient.Identifier_2))
                .body("userTokens[0].clientId", Matchers.is(Integer.parseInt(TokenLookupClient.AccessBankID)))
                .body("userTokens[0].channelId", Matchers.is(Integer.parseInt(TokenLookupClient.UssdID)))
                .body("userTokens[0].productTypeId", Matchers.is(Integer.parseInt(TokenLookupClient.IflixSubscriptionID)));
    }

}

