package api.transaction_lookup;

import api.clients.ReserveAndTransactClient;
import api.clients.TokenLookupClient;
import api.domains.reserve_and_transact.model.ReserveAndTransactRequest;
import api.domains.reserve_and_transact.model.ReserveAndTransactResponse;
import api.domains.transaction_lookup.model.TransactionLookupResponse;
import api.enums.ChannelId;
import api.enums.ChannelName;
import api.enums.Port;
import api.enums.Version;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import util.base_test.BaseApiTest;
import java.util.Hashtable;
import java.util.Map;
import static api.clients.TransactionLookupClient.findTransaction;
import static api.clients.ReserveAndTransactClient.executeReserveAndTransact;
import static api.domains.reserve_and_transact.repo.ReserveAndTransactRequestRepo.setUpReserveAndTransactV4Data;
import static api.enums.ChannelName.USSD;
import static api.enums.CurrencyCode.NGN;
import static org.apache.http.HttpStatus.SC_OK;

public class TransactionLookupTest extends BaseApiTest {
    private ReserveAndTransactRequest jsonBody;

    @Test
    @Description("30433 :: transaction-lookup :: public internal :: GET /lookupservice/transaction :: Transaction Lookup API (1.0)")
    @TmsLink("TECH-54420")
    public void testLookupTransactionsApiV1Success() throws InterruptedException {
        // Perform purchase
        jsonBody = setUpReserveAndTransactV4Data(ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_100, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundsReserved))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        //Get transaction details from lookup service
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId", ReserveAndTransactClient.TestClient3);
        queryParams.put("raasTxnRef", raasTxnRef);
        Thread.sleep(10000);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, queryParams)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("amount", Matchers.comparesEqualTo(Integer.parseInt(ReserveAndTransactClient.PurchaseAmount10000)))
                .body("productId", Matchers.comparesEqualTo(Integer.parseInt(ReserveAndTransactClient.ProductAirtel_100)))
                .body("clientId", Matchers.is(Integer.parseInt(ReserveAndTransactClient.TestClient3)))
                .body("transactionStatus", Matchers.containsString(ReserveAndTransactClient.Success))
                .body("reserveFundsResponseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("sourceIdentifier", Matchers.comparesEqualTo(ReserveAndTransactClient.Identifier))
                .body("targetIdentifier", Matchers.comparesEqualTo(ReserveAndTransactClient.Identifier))
                .body("channelName", Matchers.containsString(String.valueOf(ChannelName.USSD)))
                .body("channelId", Matchers.is(Integer.parseInt(TokenLookupClient.UssdID)))
                .extract().body().as(TransactionLookupResponse.class).getLookupRef();

    }


    @Test
    @Description("30433 :: transaction-lookup :: public internal :: GET / lookupservice/transaction/v2 :: Transaction Lookup API (2.0)")
    @TmsLink("TECH-54422")
    public void testLookupTransactionsApiV2Success() throws InterruptedException {
        // Perform purchase
        jsonBody = setUpReserveAndTransactV4Data(ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_100, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundsReserved))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        //Get transaction details from lookup service v2
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        Thread.sleep(10000);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, Integer.parseInt(ReserveAndTransactClient.TestClient3), queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("clientId", Matchers.is(Integer.parseInt(ReserveAndTransactClient.TestClient3)))
                .body("channelId", Matchers.is(Integer.parseInt(TokenLookupClient.UssdID)))
                .body("channelName", Matchers.containsString(String.valueOf(ChannelName.USSD)))
                .body("feeAmount", Matchers.is(Integer.parseInt(ReserveAndTransactClient.FeeAmount0)))
                .body("purchaseAmount", Matchers.comparesEqualTo(Integer.parseInt(ReserveAndTransactClient.PurchaseAmount10000)))
                .body("productId", Matchers.comparesEqualTo(Integer.parseInt(ReserveAndTransactClient.ProductAirtel_100)))
                .body("sourceIdentifier", Matchers.comparesEqualTo(ReserveAndTransactClient.Identifier))
                .body("targetIdentifier", Matchers.comparesEqualTo(ReserveAndTransactClient.Identifier))
                .body("transactionStatus", Matchers.containsString(ReserveAndTransactClient.Success))
                .body("reserveFundsResponseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("transactResultResponseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .extract().body().as(TransactionLookupResponse.class).getLookupRef();
    }
}