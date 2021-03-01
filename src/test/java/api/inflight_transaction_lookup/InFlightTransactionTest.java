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

import static api.clients.ReserveAndTransactClient.executeReserveAndTransact;
import static api.clients.SimulatorClient.addAirtelTestCases;
import static api.clients.SimulatorClient.removeAllAirtelTestCases;
import static api.clients.SupportUiClient.getRaasFlow;
import static api.domains.reserve_and_transact.repo.ReserveAndTransactRequestRepo.setUpReserveAndTransactV4Data;
import static api.domains.simulator.repo.SimulatorRequestRepo.setUpAirtelSimData;
import static api.enums.ChannelName.USSD;
import static api.enums.CurrencyCode.NGN;
import static db.clients.HibernateBaseClient.executeCustomQueryAndReturnValue;
import static db.custom_queries.ReserveAndTransactQueries.GET_TRANSACTION_STATUS;
import static db.enums.Sessions.POSTGRES_SQL;
import static java.lang.String.format;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

public class InFlightTransactionTest extends BaseApiTest {

    @Test
    @Description("30433 :: transaction-lookup :: public internal :: POST /lookupservice/pendingTransactions :: In-Flight Transaction Lookup API (1.0)")
    @TmsLink("TECH-54418")
    public void testLookupPendingTransactionsSuccess() {
        //add test cases
        val addTestCase1 = setUpAirtelSimData("17017", "lookup");
        val addTestCase2 = setUpAirtelSimData("17017", "purchase");

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

        //set simulator to the default state (delete simulator tests)
        removeAllAirtelTestCases()
                .then().assertThat().statusCode(SC_OK);

        //raas db check --- transaction status is "SUCCESS"
        val status = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(GET_TRANSACTION_STATUS, raasTxnRef));
        assertThat(status)
                .as("Postgres SQL query result incorrect")
                .contains("SUCCESS");

        //Verify against support tool API
        getRaasFlow(Port.RAAS_FLOW, raasTxnRef)
                .then().assertThat().statusCode(SC_OK)
            //Verify transaction result is sent with valid code mapped to ctx response code (0000)
                .body("transaction_result_request.responseCode", Matchers.is(0000))
            //AND success response code is received from the funding source
                .body("transaction_result_response.responseCode", Matchers.is("202"))
            //AND last lookup is successful in the db (ctx lookup with response code (0))
                .body("ctx_lookup_response.clientTransactionId", Matchers.is(raasTxnRef.concat("-0000")));
    }

}
