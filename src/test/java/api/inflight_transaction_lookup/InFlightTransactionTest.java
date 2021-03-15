package api.inflight_transaction_lookup;

import api.domains.reserve_and_transact.model.ReserveAndTransactResponse;
import api.enums.ChannelId;
import api.enums.Port;
import api.enums.Version;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import util.base_test.BaseApiTest;

import java.util.Arrays;

import static api.clients.InFlightTransactionLookupClient.lookupPendingTransactions;
import static api.clients.ReserveAndTransactClient.executeReserveAndTransact;
import static api.clients.SimulatorClient.addAirtelTestCases;
import static api.clients.SimulatorClient.removeAllAirtelTestCases;
import static api.domains.inflight_transaction.repo.InFlightTransactionRequestRepo.setUpInFlightTransactionData;
import static api.domains.reserve_and_transact.repo.ReserveAndTransactRequestRepo.setUpReserveAndTransactV4Data;
import static api.domains.simulator.repo.SimulatorRequestRepo.setUpAirtelSimData;
import static api.enums.ChannelName.USSD;
import static api.enums.CurrencyCode.NGN;
import static org.apache.http.HttpStatus.SC_OK;


public class InFlightTransactionTest extends BaseApiTest {

    @Test
    @Description("30433 :: transaction-lookup :: public internal :: POST /lookupservice/pendingTransactions :: In-Flight Transaction Lookup API (1.0)")
    @TmsLink("TECH-54418")
    public void testLookupPendingTransactionsSuccess() {
        //add test cases
        val addTestCase1 = setUpAirtelSimData("200", "lookup");
        val addTestCase2 = setUpAirtelSimData("500", "purchase");

        addAirtelTestCases(Arrays.asList(addTestCase1, addTestCase2))
                .then().assertThat().statusCode(SC_OK);

        //perform R&T - purchase airtel product
        val jsonBody = setUpReserveAndTransactV4Data("3", NGN, USSD, ChannelId.USSD, "130", "10000", "0", "2348038382067");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        //perform lookup service for pending transactions
        val lookupBody = setUpInFlightTransactionData("2348038382067", 3, 130, 10000);

        lookupPendingTransactions(lookupBody,Port.TRANSACTION_LOOKUP_SERVICE)
                .then().assertThat().statusCode(SC_OK)
                .body("hasPendingTransactions", Matchers.is(true));

        //set simulator to the default state (delete simulator tests)
        removeAllAirtelTestCases()
                .then().assertThat().statusCode(SC_OK);
    }

}
