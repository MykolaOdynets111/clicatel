package api.user_transaction_lookup;

import api.clients.ReserveAndTransactClient;
import api.clients.TokenLookupClient;
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
    @TmsLink("MKP-1054")
    public void testUserTransactionLookupSuccess() {
        Map <String, String> queryParams = new Hashtable<>();
        queryParams.put("userIdentifier", TokenLookupClient.Identifier_1);
        queryParams.put("clientId", TokenLookupClient.AccessBankID);

        getUserTransactions(Port.USER_TRANSACTIONS, queryParams)
                .then().assertThat().statusCode(SC_OK)
                .body("[0].channelId", Matchers.is(Integer.parseInt(TokenLookupClient.UssdID)))
                .body("[0].targetIdentifier", Matchers.containsString(TokenLookupClient.Identifier_10))
                .body("[0].productId", Matchers.is(Integer.parseInt(UserTransactionLookupClient.Product_Iflix_571)))
                .body("[0].purchaseAmount", Matchers.is(Integer.parseInt(UserTransactionLookupClient.PurchaseAmount79900)))
                .body("[0].description", Matchers.containsString(UserTransactionLookupClient.UserTransactionProductDesc))
                .body("[0].clientId", Matchers.is(Integer.parseInt(TokenLookupClient.AccessBankID)));
    }

}

