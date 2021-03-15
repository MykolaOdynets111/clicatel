package api.transaction_lookup;

import api.domains.mno_lookup.model.MnoLookupResponse;
import api.domains.reserve_and_transact.model.ReserveAndTransactRequest;
import api.domains.reserve_and_transact.model.ReserveAndTransactResponse;
import api.domains.transaction_lookup.model.TransactionLookupResponse;
import api.enums.ChannelId;
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

import static api.clients.MnoLookupClient.getMnoInfo;
import static api.clients.TransactionLookupClient.getTransactionInfo;
import static api.clients.TransactionLookupClient.getTransactionInfoV2;
import static api.clients.SimulatorClient.removeAllAirtelTestCases;
import static api.clients.ReserveAndTransactClient.executeReserveAndTransact;
import static api.domains.reserve_and_transact.repo.ReserveAndTransactRequestRepo.setUpReserveAndTransactV4Data;
import static api.enums.ChannelName.USSD;
import static api.enums.CurrencyCode.NGN;
import static db.clients.HibernateBaseClient.executeCustomQueryAndReturnValue;
import static db.custom_queries.MnoLookupQueries.GET_LOOKUP_RESPONSE_CODE;
import static db.enums.Sessions.POSTGRES_SQL;
import static java.lang.String.format;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

public class transactionLookupTest extends BaseApiTest {
    private ReserveAndTransactRequest jsonBody;

    @Test
    @Description("30433 :: transaction-lookup :: public internal :: GET /lookupservice/transaction :: Transaction Lookup API (1.0)")
    @TmsLink("TECH-54420")
    public void testLookupTransactionsAPIv1() {
        // Peform purchase
        jsonBody = setUpReserveAndTransactV4Data("3", NGN, USSD, ChannelId.USSD, "100", "10000", "0", "2348038382067");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        //Get transaction details from lookup service
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId", "3");
        queryParams.put("raasTxnRef", raasTxnRef);
        getTransactionInfo(Port.TRANSACTION_LOOKUP_SERVICE, queryParams)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("amount", Matchers.comparesEqualTo(10000))
                .body("productId", Matchers.comparesEqualTo(100))
                .body("transactionStatus", Matchers.containsString("SUCCESS"))
                .body("reserveFundsResponseCode", Matchers.containsString("0000"))
                .extract().body().as(TransactionLookupResponse.class).getLookupRef();

    }


    @Test
    @Description("30433 :: transaction-lookup :: public internal :: GET / lookupservice/transaction/v2 :: Transaction Lookup API (2.0)")
    @TmsLink("TECH-54422")
    public void testLookupTransactionsAPIv2() {
        // Peform purchase
        jsonBody = setUpReserveAndTransactV4Data("3", NGN, USSD, ChannelId.USSD, "100", "10000", "0", "2348038382067");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
        //Get transaction details from lookup service v2
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId", "3");
        queryParams.put("raasTxnRef", raasTxnRef);
        getTransactionInfoV2(Port.TRANSACTION_LOOKUP_SERVICE, queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("amount", Matchers.comparesEqualTo(10000))
                .body("productId", Matchers.comparesEqualTo(100))
                .body("transactionStatus", Matchers.containsString("SUCCESS"))
                .body("reserveFundsResponseCode", Matchers.containsString("0000"))
                .extract().body().as(TransactionLookupResponse.class).getLookupRef();
    }
}