package api.reserve_and_transact;

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
import static api.clients.SimulatorClient.*;
import static api.clients.SupportUiClient.getRaasFlow;
import static api.domains.simulator.repo.SimulatorRequestRepo.setUpAirtelSimData;
import static api.domains.reserve_and_transact.repo.ReserveAndTransactRequestRepo.*;
import static api.domains.simulator.repo.SimulatorRequestRepo.setUpMtnSimData;
import static api.enums.ChannelName.USSD;
import static api.enums.CurrencyCode.NGN;
import static api.enums.CurrencyCode.ZAR;
import static db.clients.HibernateBaseClient.executeCustomQueryAndReturnValue;
import static db.custom_queries.ReserveAndTransactQueries.GET_TRANSACTION_STATUS;
import static db.enums.Sessions.POSTGRES_SQL;
import static java.lang.String.format;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

public class ReserveAndTransactRetryableDeclineTest extends BaseApiTest {

    //PENDING :: RD :: NRD
    @Test
    @Description("30100 :: payd-raas-gateway :: NonRetryableDecline (airtel)")
    @TmsLink("TECH-57167")
    public void testReserveAndTransactNonRetryableDecline() {
        //add test case to simulate a NON_RETRYABLE_DECLINE
        val addTestCase = setUpAirtelSimData("17017", "purchase");

        addAirtelTestCases(Arrays.asList(addTestCase))
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

        //raas db check --- transaction status is "FAILED"
        val status = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(GET_TRANSACTION_STATUS, raasTxnRef));
        assertThat(status)
                .as("Postgres SQL query result incorrect")
                .contains("FAILED");

        //Verify against support tool API
        getRaasFlow(Port.RAAS_FLOW, raasTxnRef)
                .then().assertThat().statusCode(SC_OK)
            //Verify response code matches "TransactionResponseCode" mapped to "Airtel Response Code"(2213)
                .body("ctx_response[0].responseCode", Matchers.is(2213))
            //AND transaction result is sent with valid ctx response code (2213)
                .body("transaction_result_request.responseCode", Matchers.is("2213"))
            //AND success response code is received from the funding source
                .body("transaction_result_response.responseCode", Matchers.is("202"))
            //AND transaction wasn't pending (no records found in the db)
                .body("ctx_lookup_response.clientTransactionId", Matchers.not(raasTxnRef.concat("-0000")))
            //AND transaction wasn't retried (no records found in the db)
                .body("ctx_response.clientTransactionId", Matchers.not(raasTxnRef.concat("-0001")));
    }


    @Test
    @Description("30100 :: payd-raas-gateway :: Pending To SUCCESS (airtel)")
    @TmsLink("TECH-46759")
    public void testReserveAndTransactPendingToSuccess() {
        //add test cases
        val addTestCase1 = setUpAirtelSimData("500", "purchase");
        val addTestCase2 = setUpAirtelSimData("200", "lookup");

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

        //raas db check --- transaction status is "FAILED"
        val status = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(GET_TRANSACTION_STATUS, raasTxnRef));
        assertThat(status)
                .as("Postgres SQL query result incorrect")
                .contains("SUCCESS");

        //Verify against support tool API
        getRaasFlow(Port.RAAS_FLOW, raasTxnRef)
                .then().assertThat().statusCode(SC_OK)
            //Verify response code matches "TransactionResponseCode" mapped to "Airtel Response Code"(2240) for PENDING
                .body("ctx_response[0].responseCode", Matchers.is(2240))
            //AND transaction was pending (ctx lookup with response code 2240)
                .body("ctx_lookup_response.clientTransactionId", Matchers.is(raasTxnRef.concat("-0000")))
            //AND last lookup is successful in the db (ctx lookup with response code (0))
                .body("ctx_lookup_response.clientTransactionId", Matchers.is(raasTxnRef.concat("-0000")))
            //AND transaction result is sent with valid code mapped to ctx response code (0000)
                .body("transaction_result_request.responseCode", Matchers.is(0000))
            //AND success response code is received from the funding source
                .body("transaction_result_response.responseCode", Matchers.is("202"))
            //AND transaction wasn't retried (no records found in the db)
                .body("ctx_response.clientTransactionId", Matchers.not(raasTxnRef.concat("-0001")));
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: Pending To NonRetryableDecline (mtn_za)")
    @TmsLink("TECH-57302")
    public void testReserveAndTransactPendingToNonRetryableDecline() {
        //add test cases
        val addTestCase1 = setUpMtnSimData("9318", "27837640171", "virtual_recharge", 200);
        val addTestCase2 = setUpMtnSimData("9313", "27837640171", "repeat_virtual_recharge", 200);

        addMtnTestCases(Arrays.asList(addTestCase1, addTestCase2), Port.MTN_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

        //perform R&T - purchase mtn product
        val jsonBody = setUpReserveAndTransactV4Data("2", ZAR, USSD, ChannelId.USSD, "400", "10000", "0", "27837640171");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        //set simulator to the default state (delete simulator tests)
        removeAllAirtelTestCases()
                .then().assertThat().statusCode(SC_OK);

        //raas db check --- transaction status is "FAILED"
        val status = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(GET_TRANSACTION_STATUS, raasTxnRef));
        assertThat(status)
                .as("Postgres SQL query result incorrect")
                .contains("FAILED");

        //Verify against support tool API
        getRaasFlow(Port.RAAS_FLOW, raasTxnRef)
                .then().assertThat().statusCode(SC_OK)
            //Verify response code matches "TransactionResponseCode" mapped to "Airtel Response Code"(2240) for PENDING
                .body("ctx_response[0].responseCode", Matchers.is(2240))
            //AND transaction was pending (ctx lookup with response code 2240)
                .body("ctx_lookup_response.clientTransactionId", Matchers.is(raasTxnRef.concat("-0000")))
            //AND last lookup is successful in the db (ctx lookup with response code (0))
                .body("ctx_lookup_response.clientTransactionId", Matchers.is(raasTxnRef.concat("-0000")))
            //AND transaction result is sent with valid code mapped to ctx response code (0000)
                .body("transaction_result_request.responseCode", Matchers.is(0000))
            //AND success response code is received from the funding source
                .body("transaction_result_response.responseCode", Matchers.is("202"))
            //AND transaction wasn't retried (no records found in the db)
                .body("ctx_response.clientTransactionId", Matchers.not(raasTxnRef.concat("-0001")));
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: RetryableDecline to SUCCESS (airtel)")
    @TmsLink("TECH-57171")
    public void testReserveAndTransactRetryableDeclineToSuccess() {
        //add test cases
        val addTestCase1 = setUpAirtelSimData("2238", "purchase");
        val addTestCase2 = setUpAirtelSimData("200", "lookup");

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

        //Set up testcase where action is purchase success
        val addTestCase3 = setUpAirtelSimData("200", "purchase");
        addAirtelTestCases(Arrays.asList(addTestCase3))
                .then().assertThat().statusCode(SC_OK);

        //set simulator to the default state (delete simulator tests)
        removeAllAirtelTestCases()
                .then().assertThat().statusCode(SC_OK);

        //raas db check --- transaction status is "FAILED"
        val status = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(GET_TRANSACTION_STATUS, raasTxnRef));
        assertThat(status)
                .as("Postgres SQL query result incorrect")
                .contains("SUCCESS");

        //Verify against support tool API
        getRaasFlow(Port.RAAS_FLOW, raasTxnRef)
                .then().assertThat().statusCode(SC_OK)
            //Verify first ctx request response matches "TransactionResponseCode" mapped to "Airtel Response Code" (2201) for RD
                .body("ctx_response[1].responseCode", Matchers.is(2201))
            //AND response code for the LAST retry matches "TransactionResponseCode" mapped to "Airtel Response Code"(0)
                    //TODO: add this check
            //AND transaction result is sent with valid code mapped to ctx response code (0000)
                .body("transaction_result_request.responseCode", Matchers.is(0000))
            //AND success response code is received from the funding source
                .body("transaction_result_response.responseCode", Matchers.is("202"))
            //AND transaction wasn't pending (no records in ctx ctx lookup table)
                .body("ctx_lookup_response.clientTransactionId", Matchers.is(raasTxnRef.concat("-0000")));
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: RetryableDecline to NonRetryableDecline (airtel)")
    @TmsLink("TECH-57170")
    public void testReserveAndTransactRetryableDeclineToNonRetryableDecline() {
        //add test cases
        val addTestCase1 = setUpAirtelSimData("2238", "purchase");
        val addTestCase2 = setUpAirtelSimData("200", "lookup");

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

        //Set up testcase where action is purchase non retryable decline
        val addTestCase3 = setUpAirtelSimData("17017", "purchase");
        addAirtelTestCases(Arrays.asList(addTestCase3))
                .then().assertThat().statusCode(SC_OK);

        //set simulator to the default state (delete simulator tests)
        removeAllAirtelTestCases()
                .then().assertThat().statusCode(SC_OK);

        //raas db check --- transaction status is "FAILED"
        val status = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(GET_TRANSACTION_STATUS, raasTxnRef));
        assertThat(status)
                .as("Postgres SQL query result incorrect")
                .contains("FAILED");

        //Verify against support tool API
        getRaasFlow(Port.RAAS_FLOW, raasTxnRef)
                .then().assertThat().statusCode(SC_OK)
            //Verify first ctx request response matches "TransactionResponseCode" mapped to "Airtel Response Code" (2201) for RD
                .body("ctx_response[1].responseCode", Matchers.is(2201))
            //AND transaction result is sent with valid code mapped to ctx response code (0000)
                .body("transaction_result_request.responseCode", Matchers.is(0000))
            //AND success response code is received from the funding source
                .body("transaction_result_response.responseCode", Matchers.is("202"))
            //AND transaction wasn't pending (no records in ctx ctx lookup table)
                .body("ctx_lookup_response.clientTransactionId", Matchers.is(raasTxnRef.concat("-0000")));
    }
}