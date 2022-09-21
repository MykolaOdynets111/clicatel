package api.ctxHistory;

import api.clients.NotificationClient;
import api.clients.ProductLookupClient;
import api.clients.ReserveAndTransactClient;
import api.clients.UsersClient;
import api.domains.reserve_and_transact.model.ReserveAndTransactResponse;
import api.enums.ChannelId;
import api.enums.Port;
import api.enums.Version;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import java.util.Hashtable;
import java.util.Map;

import static api.clients.CtxHistory.*;
import static api.clients.ReserveAndTransactClient.executeReserveAndTransact;
import static api.domains.reserve_and_transact.repo.ReserveAndTransactRequestRepo.setUpReserveAndTransactV4Data;
import static api.enums.ChannelName.USSD;
import static api.enums.CurrencyCode.NGN;
import static org.apache.http.HttpStatus.SC_OK;

public class CtxHistory {

    @Test
    @Description("30177 :: ctx-history :: GET /findSuccessful")
    @TmsLink("MKP-1076")
    public void testFindSuccessful() {

        Map<String, String> map = new Hashtable<>();
        map.put("clientId", ReserveAndTransactClient.TestClient3);
        map.put("targetId", NotificationClient.Identifier_6);
        GetFindSuccessful(map)
                .then().assertThat().statusCode(SC_OK);
    }
    @Test
    @Description("30177 :: ctx-history :: GET /findByClientTransactionIdAndClientId")
    @TmsLink("MKP-565")
    public void testFindByClientTransactionIdAndClient() {

        val jsonBody = setUpReserveAndTransactV4Data(ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundsReserved))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
        Map<String, String> map = new Hashtable<>();
        map.put("clientId", ReserveAndTransactClient.TestClient3);
        map.put("clientTransactionId", raasTxnRef + "-0000");

        GetFindByTransactionAndClientId(map)
                .then().assertThat().statusCode(SC_OK)
                .body("purchaseAmount", Matchers.is(Integer.parseInt(ReserveAndTransactClient.PurchaseAmount10000)))
                .body("productId", Matchers.is(Integer.parseInt(ReserveAndTransactClient.ProductAirtel_917)))
                .body("clientId", Matchers.is(Integer.parseInt(ReserveAndTransactClient.TestClient3)))
                .body("clientTransactionId", Matchers.containsString(raasTxnRef + "-0000"))
                .body("originId", Matchers.containsString(ReserveAndTransactClient.Identifier))
                .body("transactionId", Matchers.notNullValue())
                .body("transactionResponseCode", Matchers.notNullValue())
                .body("startDate", Matchers.notNullValue())
                .body("clientStan", Matchers.notNullValue())
                .body("endDate", Matchers.notNullValue())
                .body("endVendorDate", Matchers.notNullValue())
                .body("messageIdentifier", Matchers.notNullValue())
                .body("originatingService", Matchers.notNullValue())
                .body("startVendorDate", Matchers.notNullValue())
                .body("transactionState", Matchers.notNullValue())
                .body("transactionType", Matchers.notNullValue())
                .body("vendorReference", Matchers.notNullValue())
                .body("vendorResponseCode", Matchers.notNullValue())
                .body("vendorId", Matchers.notNullValue())
                .body("systemResponseCode", Matchers.notNullValue())
                .body("last_updated", Matchers.notNullValue())
                .body("commissionModel", Matchers.notNullValue())
                .body("commissionAmount", Matchers.notNullValue())
                .body("channel_id", Matchers.notNullValue())
                .body("api_id", Matchers.notNullValue())
                .body("commissionAmountInternal", Matchers.notNullValue());
    }
    @Test
    @Description("30177 :: ctx-history :: GET /findLastSuccessful")
    @TmsLink("MKP-1076")
    public void testFindLastSuccessful() {

        val jsonBody = setUpReserveAndTransactV4Data(ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundsReserved))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        Map<String, String> map = new Hashtable<>();
        map.put("clientId", ReserveAndTransactClient.TestClient3);
        map.put("clientTransactionId", raasTxnRef + "-0000");

        GetFindByTransactionAndClientId(map)
                .then().assertThat().statusCode(SC_OK)
                .body("transactionId", Matchers.notNullValue());

        Map<String, String> mapFindLastSucc = new Hashtable<>();
        mapFindLastSucc.put("clientId", ReserveAndTransactClient.TestClient3);
        mapFindLastSucc.put("targetId", ReserveAndTransactClient.Identifier);

        GetFindByLastSuccessful(mapFindLastSucc)
                .then().assertThat().statusCode(SC_OK)
                .body("[0].purchaseAmount", Matchers.is(Integer.parseInt(ReserveAndTransactClient.PurchaseAmount10000)))
                .body("[0].productId", Matchers.is(Integer.parseInt(ReserveAndTransactClient.ProductAirtel_917)))
                .body("[0].clientId", Matchers.is(Integer.parseInt(ReserveAndTransactClient.TestClient3)))
                .body("[0].targetId", Matchers.containsString(ReserveAndTransactClient.Identifier))
                .body("[0].sourceId", Matchers.containsString(ReserveAndTransactClient.Identifier))
                .body("[0].transactionResponseCode", Matchers.notNullValue())
                .body("[0].startDate", Matchers.notNullValue());
    }
}
