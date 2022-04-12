package api.inflight_transaction_lookup;

import api.clients.InFlightTransactionLookupClient;
import api.clients.ProductLookupClient;
import api.clients.ReserveAndTransactClient;
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

    @Test(groups = {"smokeTest"})
    @Description("30433 :: transaction-lookup :: public internal :: POST /lookupservice/pendingTransactions :: In-Flight Transaction Lookup API (1.0)")
    @TmsLink("TECH-54418")
    public void testLookupPendingTransactionsSuccess() {
        //add test cases
        val addTestCase1 = setUpAirtelSimData(InFlightTransactionLookupClient.ResponseCode_200, InFlightTransactionLookupClient.AirTel_lookup);
        val addTestCase2 = setUpAirtelSimData(InFlightTransactionLookupClient.ResponseCode_500, InFlightTransactionLookupClient.AirTel_purchase);

        addAirtelTestCases(Arrays.asList(addTestCase1, addTestCase2), Port.AIRTEL_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

        //perform R&T - purchase airtel product
        val jsonBody = setUpReserveAndTransactV4Data(ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ProductLookupClient.ProductAirtel_130, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ZeroTransactionCode))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundsReserved))
                .body("raasTxnRef", Matchers.notNullValue());

        //perform lookup service for pending transactions
        val lookupBody = setUpInFlightTransactionData(ReserveAndTransactClient.Identifier, Integer.parseInt(ReserveAndTransactClient.TestClient3), Integer.parseInt(ProductLookupClient.ProductAirtel_130), Integer.parseInt(ReserveAndTransactClient.PurchaseAmount10000));

        lookupPendingTransactions(lookupBody,Port.TRANSACTION_LOOKUP_SERVICE)
                .then().assertThat().statusCode(SC_OK)
                .body("hasPendingTransactions", Matchers.is(true));

        //set simulator to the default state (delete simulator tests)
        removeAllAirtelTestCases(Port.AIRTEL_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);
    }

}
