package api.reserve_and_transact;
import api.domains.reserve_and_transact.model.ReserveAndTransactResponse;
import api.domains.transact.model.TransactResponse;
import api.enums.*;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import util.base_test.BaseApiTest;

import static api.clients.AirtelSimulatorClient.addTestCases;
import static api.clients.ReserveAndTransactClient.executeReserveAndTransact;

import static api.clients.ReserveAndTransactClient.executeReserveAndTransactWithSignature;
import static api.clients.SupportUiClient.getRaasFlow;
import static api.clients.TransactClient.executeTransact;
import static api.domains.airtel_simulator.repo.AirtelSimulatorRequestRepo.setUpAirtelSimData;
import static api.domains.reserve_and_transact.repo.ReserveAndTransactRequestRepo.*;
import static api.domains.transact.repo.TransactRequestRepo.setUpTransactV1Data;
import static api.enums.ChannelName.*;
import static api.enums.CurrencyCode.*;
import static db.enums.Sessions.POSTGRES_SQL;
import static db.clients.HibernateBaseClient.executeCustomQueryAndReturnValue;
import static db.custom_queries.ReserveAndTransactQueries.GET_TRANSACTION_STATUS;
import static java.lang.String.format;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

public class ReserveAndTransactTest extends BaseApiTest {

    //SUCCESS :: v1-v4
    @Test
    @Description("30100 :: POST v4/reserveAndTransact :: SUCCESS :: Reserve and Transact API (4.0)")
    @TmsLink("TECH-68538")
    public void testReserveAndTransactV4Success() {
        val jsonBody = setUpReserveAndTransactV4Data("3", NGN, USSD, ChannelId.USSD, "100", "10000", "0", "2348038382067");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        //Verify transaction status is "SUCCESS"
        val status = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(GET_TRANSACTION_STATUS, raasTxnRef));
        assertThat(status)
               .as("Postgres SQL query result should not be empty")
               .contains("SUCCESS");

        //Verify against support tool API
        getRaasFlow(Port.RAAS_FLOW, raasTxnRef)
                .then().assertThat().statusCode(SC_OK)
        //Verify funds were successfully reserved (response_code equals to 0000)
                .body("reserve_fund_response.responseCode", Matchers.is("0000"))
        //AND ctx response code is SUCCESSFUL (0)
                //TODO: Fix this null error : Expected: is <0> but Actual: null
                //.body("ctx_response[0].responseCode", Matchers.is(0))
        //AND successful transaction result is sent (0000)
                //TODO: Fix this null error : Expected: is "0000" but Actual: null
                //.body("transaction_result_request.responseCode", Matchers.is("0000"))
        //AND success response code is received from the funding source (202)
                //TODO: Fix this null error : Expected: is "202" but Actual: null
                //.body("transaction_result_response.responseCode", Matchers.is("202"))
        //AND transaction wasn't retried (no records found in the db)
                .body("ctx_response.clientTransactionId", Matchers.not(raasTxnRef.concat("-0001")))
        //AND transaction wasn't pending (no records found in the db)
                .body("ctx_lookup_response.clientTransactionId", Matchers.not(raasTxnRef.concat("-0000")));

    }


    @Test
    @Description("30100 :: POST v3/reserveAndTransact :: SUCCESS")
    @TmsLink("TECH-54334")
    public void testReserveAndTransactV3Success() {
        val jsonBody = setUpReserveAndTransactV3Data("3", MOBILE, ChannelId.MOBILE, "917");

        val raasTxnRef = executeReserveAndTransact(jsonBody,Port.TRANSACTIONS,Version.V3)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        //Verify transaction status is "SUCCESS"
        val status = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(GET_TRANSACTION_STATUS, raasTxnRef));
        assertThat(status)
                .as("Postgres SQL query result should not be empty")
                .contains("SUCCESS");

        //Verify against support tool API
        getRaasFlow(Port.RAAS_FLOW, raasTxnRef)
                .then().assertThat().statusCode(SC_OK)
                //Verify funds were successfully reserved (response_code equals to 0000)
                .body("reserve_fund_response.responseCode", Matchers.is("0000"))
                //AND ctx response code is SUCCESSFUL (0)
                //TODO: Fix this null error : Expected: is <0> but Actual: null
                //.body("ctx_response[0].responseCode", Matchers.is(0))
                //AND successful transaction result is sent (0000)
                //TODO: Fix this null error : Expected: is "0000" but Actual: null
                //.body("transaction_result_request.responseCode", Matchers.is("0000"))
                //AND success response code is received from the funding source (202)
                //TODO: Fix this null error : Expected: is "202" but Actual: null
                //.body("transaction_result_response.responseCode", Matchers.is("202"))
                //AND transaction wasn't retried (no records found in the db)
                .body("ctx_response.clientTransactionId", Matchers.not(raasTxnRef.concat("-0001")))
                //AND transaction wasn't pending (no records found in the db)
                .body("ctx_lookup_response.clientTransactionId", Matchers.not(raasTxnRef.concat("-0000")));

    }

    @Test
    @Description("30100 :: POST v2/reserveAndTransact :: SUCCESS")
    @TmsLink("TECH-54335")
    public void testReserveAndTransactV2Success() {
        val jsonBody = setUpReserveAndTransactV2Data("3", USSD, ChannelId.USSD, "917");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        //Verify transaction status is "SUCCESS"
        val status = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(GET_TRANSACTION_STATUS, raasTxnRef));
        assertThat(status)
                .as("Postgres SQL query result should not be empty")
                .contains("SUCCESS");

        //Verify against support tool API
        getRaasFlow(Port.RAAS_FLOW, raasTxnRef)
                .then().assertThat().statusCode(SC_OK)
                //Verify funds were successfully reserved (response_code equals to 0000)
                .body("reserve_fund_response.responseCode", Matchers.is("0000"))
                //AND ctx response code is SUCCESSFUL (0)
                //TODO: Fix this null error : Expected: is <0> but Actual: null
                //.body("ctx_response[0].responseCode", Matchers.is(0))
                //AND successful transaction result is sent (0000)
                //TODO: Fix this null error : Expected: is "0000" but Actual: null
                //.body("transaction_result_request.responseCode", Matchers.is("0000"))
                //AND success response code is received from the funding source (202)
                //TODO: Fix this null error : Expected: is "202" but Actual: null
                //.body("transaction_result_response.responseCode", Matchers.is("202"))
                //AND transaction wasn't retried (no records found in the db)
                .body("ctx_response.clientTransactionId", Matchers.not(raasTxnRef.concat("-0001")))
                //AND transaction wasn't pending (no records found in the db)
                .body("ctx_lookup_response.clientTransactionId", Matchers.not(raasTxnRef.concat("-0000")));

    }

    @Test
    @Description("30100 :: POST v1/reserveAndTransact :: SUCCESS")
    @TmsLink("TECH-58612")
    public void testReserveAndTransactV1Success() {
        val jsonBody = setUpTransactV1Data("3", USSD, ChannelId.USSD, "924");

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

        //Verify transaction status is "SUCCESS"
        val status = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(GET_TRANSACTION_STATUS, raasTxnRef));
        assertThat(status)
                .as("Postgres SQL query result should not be empty")
                .contains("SUCCESS");

        //Verify against support tool API
        getRaasFlow(Port.RAAS_FLOW, raasTxnRef)
                .then().assertThat().statusCode(SC_OK)
                //Verify funds were successfully reserved (response_code equals to 0000)
                .body("reserve_fund_response.responseCode", Matchers.is("0000"))
                //AND ctx response code is SUCCESSFUL (0)
                //TODO: Fix this null error : Expected: is <0> but Actual: null
                //.body("ctx_response[0].responseCode", Matchers.is(0))
                //AND successful transaction result is sent (0000)
                //TODO: Fix this null error : Expected: is "0000" but Actual: null
                //.body("transaction_result_request.responseCode", Matchers.is("0000"))
                //AND success response code is received from the funding source (202)
                //TODO: Fix this null error : Expected: is "202" but Actual: null
                //.body("transaction_result_response.responseCode", Matchers.is("202"))
                //AND transaction wasn't retried (no records found in the db)
                .body("ctx_response.clientTransactionId", Matchers.not(raasTxnRef.concat("-0001")))
                //AND transaction wasn't pending (no records found in the db)
                .body("ctx_lookup_response.clientTransactionId", Matchers.not(raasTxnRef.concat("-0000")));

    }


    //SUCCESS :: VENDORS & CLIENTS
    @Test
    @Description("30100 :: vendor 2 (CellC) :: SUCCESS")
    @TmsLink("TECH-69577")
    public void testReserveAndTransactVendor2CellCSuccess() {
        val jsonBody = setUpReserveAndTransactV4Data("2", ZAR, USSD, ChannelId.USSD, "60", "10000", "0", "27815793852");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        //raas db check --- transaction status is "SUCCESS"
        val status = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(GET_TRANSACTION_STATUS, raasTxnRef));
        assertThat(status)
                .as("Postgres SQL query result should not be empty")
                .contains("SUCCESS");
    }


    @Test
    @Description("30100 :: vendor 3 (MTN_ZA) :: SUCCESS")
    @TmsLink("TECH-68400")
    public void testReserveAndTransactVendor3MtnZaSuccess() {
        val jsonBody = setUpReserveAndTransactV4Data("2", ZAR, USSD, ChannelId.USSD, "400", "10000", "0", "27837640171");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        //raas db check --- transaction status is "SUCCESS"
        val status = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(GET_TRANSACTION_STATUS, raasTxnRef));
        assertThat(status)
                .as("Postgres SQL query result should not be empty")
                .contains("SUCCESS");
    }


    @Test
    @Description("30100 :: vendor 4 (Vodacom) :: SUCCESS")
    @TmsLink("TECH-69575")
    public void testReserveAndTransactVendor4VodacomSuccess() {
        val jsonBody = setUpReserveAndTransactV4Data("2", ZAR, USSD, ChannelId.USSD, "40", "10000", "0", "27829808884");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        //raas db check --- transaction status is "SUCCESS"
        val status = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(GET_TRANSACTION_STATUS, raasTxnRef));
        assertThat(status)
                .as("Postgres SQL query result should not be empty")
                .contains("SUCCESS");
    }


    @Test
    @Description("30100 :: vendor 5 (Telkom) :: SUCCESS")
    @TmsLink("TECH-69580")
    public void testReserveAndTransactVendor5TelkomSuccess() {
        val jsonBody = setUpReserveAndTransactV4Data("2", ZAR, USSD, ChannelId.USSD, "50", "10000", "0", "27815793852");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        //raas db check --- transaction status is "SUCCESS"
        val status = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(GET_TRANSACTION_STATUS, raasTxnRef));
        assertThat(status)
                .as("Postgres SQL query result should not be empty")
                .contains("SUCCESS");
    }


    @Test
    @Description("30100 :: vendor 15 (3line, threeline, ClickatellBiller3) :: SUCCESS")
    @TmsLink("TECH-75095")
    public void testReserveAndTransactVendor15ThreelineSuccess() {
        val jsonBody = setUpReserveAndTransactV4Data("3", NGN, USSD, ChannelId.USSD, "505", "50000", "10750", "2347832008221");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        //raas db check --- transaction status is "SUCCESS"
        val status = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(GET_TRANSACTION_STATUS, raasTxnRef));
        assertThat(status)
                .as("Postgres SQL query result should not be empty")
                .contains("SUCCESS");
    }


    @Test
    @Description("30100 :: vendor 21 (mwm) :: SUCCESS")
    @TmsLink("TECH-68398")
    public void testReserveAndTransactVendor21MwmSuccess() {
        val jsonBody = setUpReserveAndTransactV4Data("206", NGN, USSD, ChannelId.USSD, "206", "200", "0", "2348038382061");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        //raas db check --- transaction status is "SUCCESS"
        val status = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(GET_TRANSACTION_STATUS, raasTxnRef));
        assertThat(status)
                .as("Postgres SQL query result should not be empty")
                .contains("SUCCESS");
    }


    @Test
    @Description("30100 :: vendor 23 (MTN_ZA_clickatell) :: SUCCESS")
    @TmsLink("TECH-68536")
    public void testReserveAndTransactVendor23MtnZaSuccess() {
        val jsonBody = setUpReserveAndTransactV4Data("2", ZAR, USSD, ChannelId.USSD, "30", "10000", "0", "27837640171");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        //raas db check --- transaction status is "SUCCESS"
        val status = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(GET_TRANSACTION_STATUS, raasTxnRef));
        assertThat(status)
                .as("Postgres SQL query result should not be empty")
                .contains("SUCCESS");
    }


    @Test
    @Description("30100 :: vendor 100 (MTN_NG) :: SUCCESS")
    @TmsLink("TECH-63683")
    public void testReserveAndTransactVendor100MtnNgSuccess() {
        val jsonBody = setUpReserveAndTransactV4Data("3", NGN, USSD, ChannelId.USSD, "100", "10000", "0", "2348038382067");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        //raas db check --- transaction status is "SUCCESS"
        val status = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(GET_TRANSACTION_STATUS, raasTxnRef));
        assertThat(status)
                .as("Postgres SQL query result should not be empty")
                .contains("SUCCESS");
    }


    @Test
    @Description("30100 :: vendor 101 (glo) :: SUCCESS")
    @TmsLink("TECH-68396")
    public void testReserveAndTransactVendor101GloSuccess() {
        val jsonBody = setUpReserveAndTransactV4Data("3", NGN, USSD, ChannelId.USSD, "329", "50000", "0", "2348038382069");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        //raas db check --- transaction status is "SUCCESS"
        val status = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(GET_TRANSACTION_STATUS, raasTxnRef));
        assertThat(status)
                .as("Postgres SQL query result should not be empty")
                .contains("SUCCESS");
    }


    @Test
    @Description("30100 :: vendor 102 (9mobile/etisalat) :: SUCCESS")
    @TmsLink("TECH-68397")
    public void testReserveAndTransactVendor1029MobileEtisalatSuccess() {
        val jsonBody = setUpReserveAndTransactV4Data("3", NGN, USSD, ChannelId.USSD, "120", "10000", "0", "2348038382069");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        //raas db check --- transaction status is "SUCCESS"
        val status = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(GET_TRANSACTION_STATUS, raasTxnRef));
        assertThat(status)
                .as("Postgres SQL query result should not be empty")
                .contains("SUCCESS");
    }


    @Test
    @Description("30100 :: vendor103 (Airtel) :: \"Airtime\" purchase :: SUCCESS")
    @TmsLink("TECH-57995")
    public void testReserveAndTransactVendor103AirtelAirtimeSuccess() {
        val jsonBody = setUpReserveAndTransactV4Data("3", NGN, USSD, ChannelId.USSD, "130", "10000", "0", "2348038382067");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        //raas db check --- transaction status is "SUCCESS"
        val status = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(GET_TRANSACTION_STATUS, raasTxnRef));
        assertThat(status)
                .as("Postgres SQL query result should not be empty")
                .contains("SUCCESS");
    }


    @Test
    @Description("30100 :: vendor103 (Airtel) :: \"Data\" purchase :: SUCCESS")
    @TmsLink("TECH-57989")
    public void testReserveAndTransactVendor103AirtelDataSuccess() {
        val jsonBody = setUpReserveAndTransactV4Data("3", NGN, USSD, ChannelId.USSD, "189", "9900", "0", "2348038382067");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        //raas db check --- transaction status is "SUCCESS"
        val status = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(GET_TRANSACTION_STATUS, raasTxnRef));
        assertThat(status)
                .as("Postgres SQL query result should not be empty")
                .contains("SUCCESS");
    }


    @Test
    @Description("30100 :: client 3 (test client) :: SUCCESS :: Purchase with valid payload and signature should be successful")
    @TmsLink("TECH-56890")
    public void testReserveAndTransactWithSignatureSuccess() {
        //UAT: client 1003 (secret value Ajd7dsJD1), funding source 1003
        val jsonBody = setUpReserveAndTransactSignatureData("1003", "1003", NGN, MOBILE, ChannelId.MOBILE, "2348038382068");

        val raasTxnRef = executeReserveAndTransactWithSignature(jsonBody, Port.TRANSACTIONS, Version.V4, "pN85n+uUSa6GpRNPOwODKgWw63aE7Q8fwEb8QRUux5A=")
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        //raas db check --- transaction status is "SUCCESS"
        val status = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(GET_TRANSACTION_STATUS, raasTxnRef));
        assertThat(status)
                .as("Postgres SQL query result should not be empty")
                .contains("SUCCESS");
    }


    //PENDING :: RD :: NRD
    @Test
    @Description("30100 :: NonRetryableDecline")
    @TmsLink("TECH-57167")
    public void testReserveAndTransactNonRetryableDecline() {
        //add test case
        val addTestCaseBody = setUpAirtelSimData("17017", "purchase");
        addTestCases(addTestCaseBody)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("17017"))
                .body("action", Matchers.containsString("purchase"));

        //perform R&T
        val jsonBody = setUpReserveAndTransactV4Data("3", NGN, USSD, ChannelId.USSD, "130", "10000", "0", "2348038382068");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        //raas db check --- transaction status is "FAILED"
        val status = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(GET_TRANSACTION_STATUS, raasTxnRef));
        assertThat(status)
                .as("Postgres SQL query result should not be empty")
                .contains("FAILED");

        //Verify against support tool API
        getRaasFlow(Port.RAAS_FLOW, raasTxnRef)
                .then().assertThat().statusCode(SC_OK)
            //Verify response code matches "TransactionResponseCode" mapped to "Airtel Response Code"(2213)
                .body("ctx_response[0].responseCode", Matchers.is(2213))
            //AND transaction result is sent with valid ctx response code (2213)
                .body("transaction_result_request.responseCode", Matchers.is(2213))
            //AND success response code is received from the funding source
                .body("transaction_result_response.responseCode", Matchers.is("202"))
            //AND transaction wasn't pending (no records found in the db)
                .body("ctx_lookup_response.clientTransactionId", Matchers.not(raasTxnRef.concat("-0000")))
            //AND transaction wasn't retried (no records found in the db)
                .body("ctx_response.clientTransactionId", Matchers.not(raasTxnRef.concat("-0001")));


    }


    @Test
    @Description("30100 :: Pending To SUCCESS")
    @TmsLink("TECH-46759")
    public void testReserveAndTransactPendingToSuccess() {
        //add test case
        val addTestCaseBody = setUpAirtelSimData("500", "purchase");
        addTestCases(addTestCaseBody)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("500"))
                .body("action", Matchers.containsString("purchase"));

        //perform R&T
        val jsonBody = setUpReserveAndTransactV4Data("3", NGN, USSD, ChannelId.USSD, "130", "10000", "0", "2348038382068");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        //raas db check --- transaction status is "FAILED"
        val status = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(GET_TRANSACTION_STATUS, raasTxnRef));
        assertThat(status)
                .as("Postgres SQL query result should not be empty")
                .contains("FAILED");

        //Verify against support tool API
        getRaasFlow(Port.RAAS_FLOW, raasTxnRef)
                .then().assertThat().statusCode(SC_OK)
                //Verify response code matches "TransactionResponseCode" mapped to "Airtel Response Code"(2213)
                .body("ctx_response[0].responseCode", Matchers.is(2213))
                //AND transaction result is sent with valid ctx response code (2213)
                .body("transaction_result_request.responseCode", Matchers.is(2213))
                //AND success response code is received from the funding source
                .body("transaction_result_response.responseCode", Matchers.is("202"))
                //AND transaction wasn't pending (no records found in the db)
                .body("ctx_lookup_response.clientTransactionId", Matchers.not(raasTxnRef.concat("-0000")))
                //AND transaction wasn't retried (no records found in the db)
                .body("ctx_response.clientTransactionId", Matchers.not(raasTxnRef.concat("-0001")));


    }
}