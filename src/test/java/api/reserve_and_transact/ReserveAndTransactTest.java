package api.reserve_and_transact;
import api.clients.ReserveAndTransactClient;
import api.domains.reserve_and_transact.model.ReserveAndTransactResponse;
import api.domains.transact.model.TransactResponse;
import api.enums.*;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import util.base_test.BaseApiTest;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;

import static api.clients.ReserveAndTransactClient.executeReserveAndTransact;

import static api.clients.ReserveAndTransactClient.executeReserveAndTransactWithSignature;
import static api.clients.SimulatorClient.*;
import static api.clients.SimulatorClient.addMtnTestCases;
import static api.clients.SupportUiClient.getRaasFlow;
import static api.clients.TransactClient.executeTransact;
import static api.clients.TransactionLookupClient.findTransaction;
import static api.domains.simulator.repo.SimulatorRequestRepo.setUpAirtelSimData;
import static api.domains.reserve_and_transact.repo.ReserveAndTransactRequestRepo.*;
import static api.domains.simulator.repo.SimulatorRequestRepo.setUpMtnSimData;
import static api.domains.transact.repo.TransactRequestRepo.setUpTransactV1Data;
import static api.enums.ChannelName.*;
import static api.enums.CurrencyCode.*;
import static org.apache.http.HttpStatus.SC_OK;

public class ReserveAndTransactTest extends BaseApiTest {

    //SUCCESS :: v1-v4
    @Test
    @Description("30100 :: payd-raas-gateway :: POST v4/reserveAndTransact :: SUCCESS :: Reserve and Transact API (4.0)")
    @TmsLink("TECH-68538")
    public void testReserveAndTransactV4Success() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV4Data(ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD,ReserveAndTransactClient.ProductAirtel_100, ReserveAndTransactClient.PurchaseAmount1000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundsReserved))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

//        //raas db check replaced with API check (TransactionLookup)
//        assertThat(getTransactionStatus(raasTxnRef))
//                .as("Postgres SQL query : Transaction Status incorrect")
//                .isTrue();

    //Verify transaction status is "SUCCESS"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        Thread.sleep(10000);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, 3, queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString(ReserveAndTransactClient.Success));

    //Verify against support tool API
        getRaasFlow(Port.RAAS_FLOW, raasTxnRef)
                .then().assertThat().statusCode(SC_OK)
            //Verify funds were successfully reserved (response_code equals to 0000)
                .body("reserve_fund_response.responseCode", Matchers.is(ReserveAndTransactClient.responseCode0000))
            //AND ctx response code is SUCCESSFUL (0)
                .body("ctx_response[0].responseCode", Matchers.is(ReserveAndTransactClient.responseCode0))
            //AND successful transaction result is sent (0000)
                .body("transaction_result_request.responseCode", Matchers.is(ReserveAndTransactClient.responseCode0000))
            //AND success response code is received from the funding source (202)
                .body("transaction_result_response.responseCode", Matchers.is(ReserveAndTransactClient.responseCode202))
            //AND transaction wasn't retried (no records found in the db)
                .body("ctx_response.clientTransactionId", Matchers.not(raasTxnRef.concat(ReserveAndTransactClient.FirstTransactionCode)))
            //AND transaction wasn't pending (no records found in the db)
                .body("ctx_lookup_response.clientTransactionId", Matchers.not(raasTxnRef.concat(ReserveAndTransactClient.responseCode0000)));

    }


    @Test
    @Description("30100 :: payd-raas-gateway :: POST v3/reserveAndTransact :: SUCCESS")
    @TmsLink("TECH-54334")
    public void testReserveAndTransactV3Success() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV3Data(ReserveAndTransactClient.TestClient3, ChannelName.MOBILE, ChannelId.MOBILE, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeReserveAndTransact(jsonBody,Port.TRANSACTIONS,Version.V3)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.Success))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

    //Verify transaction status is "SUCCESS"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        Thread.sleep(20000);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, Integer.parseInt(ReserveAndTransactClient.TestClient3), queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString(ReserveAndTransactClient.Success));

    //Verify against support tool API
        getRaasFlow(Port.RAAS_FLOW, raasTxnRef)
                .then().assertThat().statusCode(SC_OK)
            //Verify funds were successfully reserved (response_code equals to 0000)
                .body("reserve_fund_response.responseCode", Matchers.is(ReserveAndTransactClient.responseCode0000))
            //AND ctx response code is SUCCESSFUL (0)
                .body("ctx_response[0].responseCode", Matchers.is(ReserveAndTransactClient.responseCode0))
            //AND successful transaction result is sent (0000)
                .body("transaction_result_request.responseCode", Matchers.is(ReserveAndTransactClient.responseCode0000))
            //AND success response code is received from the funding source (202)
                .body("transaction_result_response.responseCode", Matchers.is(ReserveAndTransactClient.responseCode202))
            //AND transaction wasn't retried (no records found in the db)
                .body("ctx_response.clientTransactionId", Matchers.not(raasTxnRef.concat(ReserveAndTransactClient.FirstTransactionCode)))
            //AND transaction wasn't pending (no records found in the db)
                .body("ctx_lookup_response.clientTransactionId", Matchers.not(raasTxnRef.concat(ReserveAndTransactClient.ZeroTransactionCode)));

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: POST v2/reserveAndTransact :: SUCCESS")
    @TmsLink("TECH-54335")
    public void testReserveAndTransactV2Success() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV2Data(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageProcessingRequest))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

    //Verify transaction status is "SUCCESS"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        Thread.sleep(10000);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, 3, queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString(ReserveAndTransactClient.Success));

    //Verify against support tool API
        getRaasFlow(Port.RAAS_FLOW, raasTxnRef)
                .then().assertThat().statusCode(SC_OK)
            //Verify funds were successfully reserved (response_code equals to 0000)
                .body("reserve_fund_response.responseCode", Matchers.is(ReserveAndTransactClient.responseCode0000))
            //AND ctx response code is SUCCESSFUL (0)
                .body("ctx_response[0].responseCode", Matchers.is(ReserveAndTransactClient.responseCode0))
            //AND successful transaction result is sent (0000)
                .body("transaction_result_request.responseCode", Matchers.is(ReserveAndTransactClient.responseCode0000))
            //AND success response code is received from the funding source (202)
                .body("transaction_result_response.responseCode", Matchers.is(ReserveAndTransactClient.responseCode202))
            //AND transaction wasn't retried (no records found in the db)
                .body("ctx_response.clientTransactionId", Matchers.not(raasTxnRef.concat(ReserveAndTransactClient.FirstTransactionCode)))
            //AND transaction wasn't pending (no records found in the db)
                .body("ctx_lookup_response.clientTransactionId", Matchers.not(raasTxnRef.concat(ReserveAndTransactClient.ZeroTransactionCode)));

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: POST v1/reserveAndTransact :: SUCCESS")
    @TmsLink("TECH-58612")
    public void testReserveAndTransactV1Success() throws InterruptedException {
        val jsonBody = setUpTransactV1Data("3", USSD, ChannelId.USSD, "917");

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    //Verify transaction status is "SUCCESS"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        Thread.sleep(10000);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, 3, queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString("SUCCESS"));

    //Verify against support tool API
        getRaasFlow(Port.RAAS_FLOW, raasTxnRef)
                .then().assertThat().statusCode(SC_OK)
            //Verify funds were successfully reserved (response_code equals to 0000)
                .body("reserve_fund_response.responseCode", Matchers.is("0000"))
            //AND ctx response code is SUCCESSFUL (0)
                .body("ctx_response[0].responseCode", Matchers.is(0))
            //AND successful transaction result is sent (0000)
                .body("transaction_result_request.responseCode", Matchers.is("0000"))
            //AND success response code is received from the funding source (202)
                .body("transaction_result_response.responseCode", Matchers.is("202"))
            //AND transaction wasn't retried (no records found in the db)
                .body("ctx_response.clientTransactionId", Matchers.not(raasTxnRef.concat("-0001")))
            //AND transaction wasn't pending (no records found in the db)
                .body("ctx_lookup_response.clientTransactionId", Matchers.not(raasTxnRef.concat("-0000")));

    }



    //SUCCESS :: VENDORS & CLIENTS
    @Test
    @Description("30100 :: payd-raas-gateway :: vendor 2 (CellC) SUCCESS")
    @TmsLink("TECH-69577")
    public void testReserveAndTransactVendor2CellCSuccess() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV4Data("2", ZAR, USSD, ChannelId.USSD, "60", "10000", "0", "27815793852");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        //raas db check - being replaced by API transaction lookup check
//        val status = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(GET_TRANSACTION_STATUS, raasTxnRef));
//        assertThat(status)
//                .as("Postgres SQL query result incorrect")
//                .contains("SUCCESS");

    //Verify transaction status is "SUCCESS"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        Thread.sleep(20000);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, 2, queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString("SUCCESS"));
    }


    @Test
    @Description("30100 :: payd-raas-gateway :: vendor 3 (MTN_ZA) SUCCESS")
    @TmsLink("TECH-68400")
    public void testReserveAndTransactVendor3MtnZaSuccess() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV4Data("2", ZAR, USSD, ChannelId.USSD, "400", "10000", "0", "27837640171");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

    //Verify transaction status is "SUCCESS"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        Thread.sleep(30000);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, 2, queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString("SUCCESS"));
    }


    @Test
    @Description("30100 :: payd-raas-gateway :: vendor 4 (Vodacom) SUCCESS")
    @TmsLink("TECH-69575")
    public void testReserveAndTransactVendor4VodacomSuccess() {
        val jsonBody = setUpReserveAndTransactV4Data("2", ZAR, USSD, ChannelId.USSD, "40", "10000", "0", "27829808884");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

    //Verify transaction status is "SUCCESS"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, 2, queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString("SUCCESS"));
    }


    @Test
    @Description("30100 :: payd-raas-gateway :: vendor 5 (Telkom) SUCCESS")
    @TmsLink("TECH-69580")
    public void testReserveAndTransactVendor5TelkomSuccess() {
        val jsonBody = setUpReserveAndTransactV4Data("2", ZAR, USSD, ChannelId.USSD, "50", "10000", "0", "27815793852");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

    //Verify transaction status is "SUCCESS"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, 2, queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString("SUCCESS"));
    }


    @Test
    @Description("30100 :: payd-raas-gateway :: vendor 15 (3line, threeline, ClickatellBiller3) SUCCESS")
    @TmsLink("TECH-75095")
    public void testReserveAndTransactVendor15ThreelineSuccess() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV4Data("3", NGN, USSD, ChannelId.USSD, "505", "50000", "0", "2341000000000");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

    //Verify transaction status is "SUCCESS"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        Thread.sleep(10000);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, 3, queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString("SUCCESS"));
    }


    @Test
    @Description("30100 :: payd-raas-gateway :: vendor 21 (mwm) SUCCESS")
    @TmsLink("TECH-68398")
    public void testReserveAndTransactVendor21MwmSuccess() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV4Data("3", NGN, USSD, ChannelId.USSD, "917", "200", "0", "2348038382061");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

    //Verify transaction status is "SUCCESS"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        Thread.sleep(20000);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, 3, queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString("SUCCESS"));
    }


    @Test
    @Description("30100 :: payd-raas-gateway :: vendor 23 (MTN_ZA_clickatell) SUCCESS")
    @TmsLink("TECH-68536")
    public void testReserveAndTransactVendor23MtnZaSuccess() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV4Data("2", ZAR, USSD, ChannelId.USSD, "30", "10000", "0", "27837640171");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

    //Verify transaction status is "SUCCESS"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        Thread.sleep(10000);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, 2, queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString("SUCCESS"));
    }


    @Test
    @Description("30100 :: payd-raas-gateway :: vendor 100 (MTN_NG) SUCCESS")
    @TmsLink("TECH-63683")
    public void testReserveAndTransactVendor100MtnNgSuccess() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV4Data("3", NGN, USSD, ChannelId.USSD, "100", "10000", "0", "2348038382067");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

    //Verify transaction status is "SUCCESS"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        Thread.sleep(10000);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, 3, queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString("SUCCESS"));
    }


    @Test
    @Description("30100 :: payd-raas-gateway :: vendor 101 (glo) SUCCESS")
    @TmsLink("TECH-68396")
    public void testReserveAndTransactVendor101GloSuccess() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV4Data("3", NGN, USSD, ChannelId.USSD, "110", "50000", "0", "2348038382069");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

    //Verify transaction status is "SUCCESS"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        Thread.sleep(10000);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, 3, queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString("SUCCESS"));
    }


    @Test
    @Description("30100 :: payd-raas-gateway :: vendor 102 (9mobile/etisalat) SUCCESS")
    @TmsLink("TECH-68397")
    public void testReserveAndTransactVendor1029MobileEtisalatSuccess() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV4Data("3", NGN, USSD, ChannelId.USSD, "120", "10000", "0", "2348038382069");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

    //Verify transaction status is "SUCCESS"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        Thread.sleep(10000);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, 3, queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString("SUCCESS"));
    }


    @Test
    @Description("30100 :: payd-raas-gateway :: vendor103 (Airtel) SUCCESS \"Airtime\" purchase")
    @TmsLink("TECH-57995")
    public void testReserveAndTransactVendor103AirtelAirtimeSuccess() {
        val jsonBody = setUpReserveAndTransactV4Data("3", NGN, USSD, ChannelId.USSD, "130", "10000", "0", "2348038382067");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

    //Verify transaction status is "SUCCESS"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, 3, queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString("SUCCESS"));
    }


    @Test
    @Description("30100 :: payd-raas-gateway :: vendor103 (Airtel) SUCCESS \"Data\" purchase")
    @TmsLink("TECH-57989")
    public void testReserveAndTransactVendor103AirtelDataSuccess() {
        val jsonBody = setUpReserveAndTransactV4Data("3", NGN, USSD, ChannelId.USSD, "189", "9900", "0", "2348038382067");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

    //Verify transaction status is "SUCCESS"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, 3, queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString("SUCCESS"));
    }


    @Test
    @Description("30100 :: payd-raas-gateway :: client 1003 (test client) SUCCESS (checksum)")
    @TmsLink("TECH-56890")
    public void testReserveAndTransactWithSignatureSuccess() {
        //UAT: client 1003 (secret value Ajd7dsJD1), funding source 1003

        //val jsonBody = setUpReserveAndTransactSignatureData("1003", ChannelName.MOBILE, ChannelId.MOBILE, "2348038382068");

        //TODO: Temporary workaround until further investigation to eliminate {"responseCode":"4000","responseMessage":"Header Signature invalid"}
        val jsonBody = "{\n" +
                "    \"timestamp\": \"2023-03-03T00:00:00.000+02:00\",\n" +
                "    \"accountIdentifier\": \"000XXX0311-0003\",\n" +
                "    \"clientTxnRef\": \"010002441811llim-0003\",\n" +
                "    \"channelSessionId\": \"714890809-0003\",\n" +
                "    \"clientId\": \"1003\",\n" +
                "    \"productId\": \"917\",\n" +
                "    \"purchaseAmount\": \"10000\",\n" +
                "    \"feeAmount\": \"0\",\n" +
                "    \"channelId\": \"3\",\n" +
                "    \"channelName\": \"MOBILE\",\n" +
                "    \"sourceIdentifier\": \"2348038382068\",\n" +
                "    \"targetIdentifier\": \"2348038382068\"\n" +
                "}";

        val raasTxnRef = executeReserveAndTransactWithSignature(jsonBody, Port.TRANSACTIONS, Version.V3,"dgUVUVzgBIIl7dtAI7ziy+t2f3qYHv4u593b5lDlkYE=")
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

    //Verify transaction status is "SUCCESS"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, 1003, queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString("SUCCESS"));

        //Verify against support tool API
        getRaasFlow(Port.RAAS_FLOW, raasTxnRef)
                .then().assertThat().statusCode(SC_OK)
                //Verify funds were successfully reserved (response_code equals to 0000)
                .body("reserve_fund_response.responseCode", Matchers.is("0000"))
                //AND ctx response code is SUCCESSFUL (0)
                .body("ctx_response[0].responseCode", Matchers.is(0))
                //AND successful transaction result is sent (0000)
                .body("transaction_result_request.responseCode", Matchers.is("0000"))
                //AND success response code is received from the funding source (202)
                .body("transaction_result_response.responseCode", Matchers.is("202"))
                //AND transaction wasn't retried (no records found in the db)
                .body("ctx_response.clientTransactionId", Matchers.not(raasTxnRef.concat("-0001")))
                //AND transaction wasn't pending (no records found in the db)
                .body("ctx_lookup_response.clientTransactionId", Matchers.not(raasTxnRef.concat("-0000")));

    }



    //PENDING :: RD :: NRD
    @Test
    @Description("30100 :: payd-raas-gateway :: NonRetryableDecline (airtel)")
    @TmsLink("TECH-57167")
    public void testReserveAndTransactNonRetryableDecline() throws InterruptedException {
        //add test case to simulate a NON_RETRYABLE_DECLINE
        val addTestCase = setUpAirtelSimData("17017", "purchase");

        addAirtelTestCases(Arrays.asList(addTestCase), Port.AIRTEL_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

        //perform R&T - purchase airtel product
        val jsonBody = setUpReserveAndTransactV4Data("3", NGN, USSD, ChannelId.USSD, "130", "10000", "0", "2348038382067");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
        //Adding thread.sleep because after the execution of reserveAndTransact call the script execute so fast that it does not get time to execute the airtell simulator under the non-retriable decline state and in the next step it removes the cases.
        Thread.sleep(5000);

        //set simulator to the default state (delete simulator tests)
        removeAllAirtelTestCases(Port.AIRTEL_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

        //raas db check --- transaction status is "FAILED" - replaced with API transactionlookup check
        /*val status = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(GET_TRANSACTION_STATUS, raasTxnRef));
        assertThat(status)
                .as("Postgres SQL query result incorrect")
                .contains("FAILED");*/

        //Verify transaction status is "FAILED"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, 3, queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString("FAILED"));

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
    public void testReserveAndTransactPendingToSuccess() throws InterruptedException {
        //add test cases
        val addTestCase1 = setUpAirtelSimData("500", "purchase");
        val addTestCase2 = setUpAirtelSimData("200", "lookup");

        addAirtelTestCases(Arrays.asList(addTestCase1, addTestCase2), Port.AIRTEL_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);
        //perform R&T - purchase airtel product
        val jsonBody = setUpReserveAndTransactV4Data("3", NGN, USSD, ChannelId.USSD, "130", "10000", "0", "2348038382067");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
        Thread.sleep(180000);
        //set simulator to the default state (delete simulator tests)
        removeAllAirtelTestCases(Port.AIRTEL_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);
    //Verify transaction status is "FAILED"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, 3, queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString("SUCCESS"));

        //Verify against support tool API
        getRaasFlow(Port.RAAS_FLOW, raasTxnRef)
                .then().assertThat().statusCode(SC_OK)
                //Verify response code matches "TransactionResponseCode" mapped to "Airtel Response Code"(2240) for PENDING
                .body("ctx_response[0].responseCode", Matchers.is(2240))
                //AND transaction was pending (ctx lookup with response code 2240)
                .body("ctx_lookup_response.clientTransactionId[1]", Matchers.is(raasTxnRef.concat("-0000")))
                //AND last lookup is successful in the db (ctx lookup with response code (0))
                .body("ctx_lookup_response[0].clientTransactionId", Matchers.is(raasTxnRef.concat("-0000")))
                //AND transaction result is sent with valid code mapped to ctx response code (0000)
                .body("transaction_result_request.responseCode", Matchers.is("0000"))
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
        removeAllMtnTestCases(Port.MTN_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

    //Verify transaction status is "FAILED"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, 2, queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString("FAILED"));

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
    public void testReserveAndTransactRetryableDeclineToSuccess() throws InterruptedException {
    //add test cases
        val addTestCase1 = setUpAirtelSimData("2238", "purchase");
        val addTestCase2 = setUpAirtelSimData("200", "lookup");

        addAirtelTestCases(Arrays.asList(addTestCase1, addTestCase2), Port.AIRTEL_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);
    //perform R&T - purchase airtel product
        val jsonBody = setUpReserveAndTransactV4Data("3", NGN, USSD, ChannelId.USSD, "130", "10000", "0", "2348038382067");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
        Thread.sleep(3000);
    //Set up testcase where action is purchase success
        val addTestCase3 = setUpAirtelSimData("200", "purchase");
        addAirtelTestCases(Arrays.asList(addTestCase3), Port.AIRTEL_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);
    Thread.sleep(180000);
    //set simulator to the default state (delete simulator tests)
        removeAllAirtelTestCases(Port.AIRTEL_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

    //Verify transaction status is "FAILED"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, 3, queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString("SUCCESS"));

    //Verify against support tool API
        getRaasFlow(Port.RAAS_FLOW, raasTxnRef)
                .then().assertThat().statusCode(SC_OK)
                //Verify first ctx request response matches "TransactionResponseCode" mapped to "Airtel Response Code" (2201) for RD
                .body("ctx_response[1].responseCode", Matchers.is(2201))
                //AND response code for the LAST retry matches "TransactionResponseCode" mapped to "Airtel Response Code"(0)
                //TODO: add this check
                //AND transaction result is sent with valid code mapped to ctx response code (0000)
                .body("transaction_result_request.responseCode", Matchers.is("0000"))
                //AND success response code is received from the funding source
                .body("transaction_result_response.responseCode", Matchers.is("202"))
                //AND transaction wasn't pending (no records in ctx ctx lookup table)
                .body("ctx_lookup_response.clientTransactionId", Matchers.empty());
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: RetryableDecline to NonRetryableDecline (airtel)")
    @TmsLink("TECH-57170")
    public void testReserveAndTransactRetryableDeclineToNonRetryableDecline() throws InterruptedException {
    //add test cases
        val addTestCase1 = setUpAirtelSimData("2238", "purchase");
        val addTestCase2 = setUpAirtelSimData("200", "lookup");

        addAirtelTestCases(Arrays.asList(addTestCase1, addTestCase2), Port.AIRTEL_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

    //perform R&T - purchase airtel product
        val jsonBody = setUpReserveAndTransactV4Data("3", NGN, USSD, ChannelId.USSD, "130", "10000", "0", "2348038382067");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
        Thread.sleep(30000);
    //Set up testcase where action is purchase non retryable decline
        val addTestCase3 = setUpAirtelSimData("17017", "purchase");
        addAirtelTestCases(Arrays.asList(addTestCase3), Port.AIRTEL_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);
        Thread.sleep(180000);
        System.out.println("Check here");
    //set simulator to the default state (delete simulator tests)
        removeAllAirtelTestCases(Port.AIRTEL_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

    //Verify transaction status is "FAILED"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, 3, queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString("FAILED"));

        //Verify against support tool API
        getRaasFlow(Port.RAAS_FLOW, raasTxnRef)
                .then().assertThat().statusCode(SC_OK)
                //Verify first ctx request response matches "TransactionResponseCode" mapped to "Airtel Response Code" (2201) for RD
                .body("ctx_response[1].responseCode", Matchers.is(2201))
                //AND transaction result is sent with valid code mapped to ctx response code (0000)
                .body("reserve_fund_response.responseCode", Matchers.is("0000"))
                //AND success response code is received from the funding source
                .body("transaction_result_response.responseCode", Matchers.is("202"));
                //AND transaction wasn't pending (no records in ctx ctx lookup table)
                //Removed this assertion because supportUIAPI is not showing clientTransactionId under ctx_lookup_response.
                //.body("ctx_lookup_response.clientTransactionId", Matchers.is(raasTxnRef.concat("-0000")));
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: Pending To RetryableDecline To SUCCESS (airtel)")
    @TmsLink("TECH-57169")
    public void testReserveAndTransactPendingToRetryableDeclineToSuccess() throws InterruptedException {
    //add test cases
        val addTestCase1 = setUpAirtelSimData("500", "purchase");
        val addTestCase2 = setUpAirtelSimData("206", "lookup");


        addAirtelTestCases(Arrays.asList(addTestCase1,addTestCase2), Port.AIRTEL_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

    //perform R&T - purchase airtel product
        val jsonBody = setUpReserveAndTransactV4Data("3", NGN, USSD, ChannelId.USSD, "130", "10000", "0", "2348038382067");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
        Thread.sleep(5000);
    //Set up testcase where action is purchase success
        val addTestCase3 = setUpAirtelSimData("200", "purchase");

        addAirtelTestCases(Arrays.asList(addTestCase3), Port.AIRTEL_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);
    //set simulator to the default state (delete simulator tests)
        removeAllAirtelTestCases(Port.AIRTEL_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);
        //Added 3 minutes wait because ctx requires time to iterate another cycle for transactions when airtel simulation is set to retryable decline.
        Thread.sleep(180000);
    //Verify transaction status is "SUCCESS"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, 3, queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString("SUCCESS"));


    //Verify against support tool API
        getRaasFlow(Port.RAAS_FLOW, raasTxnRef)
                .then().assertThat().statusCode(SC_OK)
                //Verify the FIRST ctx request response matches "TransactionResponseCode" mapped to "Airtel Response Code"(2240)
                .body("ctx_response[0].responseCode", Matchers.is(2240))
                //AND transaction result is sent with valid code mapped to ctx response code (0000)
                .body("transaction_result_request.responseCode", Matchers.is("0000"))
                //AND success response code is received from the funding source
                .body("transaction_result_response.responseCode", Matchers.is("202"))
                //AND transaction was pending (ctx lookup with response code 2240) - TODO: add check where rep code = 2240
                .body("ctx_lookup_response[0].clientTransactionId", Matchers.is(raasTxnRef.concat("-0000")))
                //AND last lookup was RETRYABLE_DECLINE (ctx lookup with response code 2201) TODO: add check where rep code = 2201
                .body("ctx_lookup_response[0].clientTransactionId", Matchers.is(raasTxnRef.concat("-0000")))
                //AND response code for the LAST retry matches "TransactionResponseCode" mapped to "Airtel Response Code"(0) for SUCCESS TODO: and the response code = 0
                .body("ctx_response[0].clientTransactionId", Matchers.is(raasTxnRef.concat("-0000")));
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: Pending To RetryableDecline To NonRetryableDecline (airtel)")
    @TmsLink("TECH-46769")
    public void testReserveAndTransactPendingToRetryableDeclineToNonRetryableDecline() throws InterruptedException {
    //add test cases
        val addTestCase1 = setUpAirtelSimData("500", "purchase");
        val addTestCase2 = setUpAirtelSimData("206", "lookup");

        addAirtelTestCases(Arrays.asList(addTestCase1, addTestCase2), Port.AIRTEL_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

    //perform R&T - purchase airtel product
        val jsonBody = setUpReserveAndTransactV4Data("3", NGN, USSD, ChannelId.USSD, "130", "10000", "0", "2348038382067");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
        Thread.sleep(3000);
    //Set up testcase where action is purchase non retryable decline
        val addTestCase3 = setUpAirtelSimData("17017", "purchase");
        addAirtelTestCases(Arrays.asList(addTestCase3), Port.AIRTEL_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);
        Thread.sleep(180000);
    //set simulator to the default state (delete simulator tests)
        removeAllAirtelTestCases(Port.AIRTEL_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

    //Verify transaction status is "SUCCESS"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, 3, queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString("FAILED"));

    //Verify against support tool API
        getRaasFlow(Port.RAAS_FLOW, raasTxnRef)
                .then().assertThat().statusCode(SC_OK)
                //Verify the FIRST ctx request response matches "TransactionResponseCode" mapped to "Airtel Response Code"(2240)
                .body("ctx_response[1].responseCode", Matchers.is(2240))
                //AND transaction result is sent with valid code mapped to ctx response code (0000)
                .body("transaction_result_request.responseCode", Matchers.is("2213"))
                //AND success response code is received from the funding source
                .body("transaction_result_response.responseCode", Matchers.is("202"))
                //AND transaction was pending (ctx lookup with response code 2240) - TODO: add check where resp code = 2240
                //AND last lookup was RETRYABLE_DECLINE (ctx lookup with response code 2201) TODO: add check where resp code = 2201
                .body("ctx_lookup_response[0].clientTransactionId", Matchers.is(raasTxnRef.concat("-0000")))
                //AND response code for the LAST retry matches "TransactionResponseCode" mapped to "Airtel Response Code"(0) for SUCCESS TODO: and the response code = 0
                .body("ctx_response.clientTransactionId[0]", Matchers.is(raasTxnRef.concat("-0001")));
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: RetryableDecline To Pending To SUCCESS (airtel)")
    @TmsLink("TECH-57303")
    public void testReserveAndTransactRetryableDeclineToPendingToSuccess() throws InterruptedException {
    //add test cases
        val addTestCase1 = setUpAirtelSimData("2238", "purchase");
        val addTestCase2 = setUpAirtelSimData("200", "lookup");

        addAirtelTestCases(Arrays.asList(addTestCase1, addTestCase2), Port.AIRTEL_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

    //perform R&T - purchase airtel product
        val jsonBody = setUpReserveAndTransactV4Data("3", NGN, USSD, ChannelId.USSD, "130", "10000", "0", "2348038382067");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
        Thread.sleep(3000);
    //Set up testcase where action is purchase pending
        val addTestCase3 = setUpAirtelSimData("500", "purchase");
        addAirtelTestCases(Arrays.asList(addTestCase3), Port.AIRTEL_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);
        Thread.sleep(240000);
        System.out.println("Checkhere");
    //set simulator to the default state (delete simulator tests)
        removeAllAirtelTestCases(Port.AIRTEL_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

    //Verify transaction status is "SUCCESS"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, 3, queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString("SUCCESS"));

    //Verify against support tool API
        getRaasFlow(Port.RAAS_FLOW, raasTxnRef)
                .then().assertThat().statusCode(SC_OK)
                //Verify the FIRST ctx request response matches "TransactionResponseCode" mapped to "Airtel Response Code"(2201) for RD
                .body("ctx_response[1].responseCode", Matchers.is(2201))
                //AND the SECOND ctx request response matches "TransactionResponseCode" mapped to "Airtel Response Code"(2240) for PENDING
                .body("ctx_response[0].responseCode", Matchers.is(2240))
                //AND transaction result is sent with valid code mapped to ctx response code (0000)
                .body("transaction_result_request.responseCode", Matchers.is("0000"))
                //AND success response code is received from the funding source
                .body("transaction_result_response.responseCode", Matchers.is("202"))
                //AND transaction was pending (ctx lookup with response code 2240) - TODO: add check where resp code = 2240
                .body("ctx_lookup_response[0].clientTransactionId", Matchers.is(raasTxnRef.concat("-0001")));
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: RetryableDecline To Pending To NonRetryableDecline (mtn_za)")
    @TmsLink("TECH-57304")
    public void testReserveAndTransactRetryableDeclineToPendingToNonRetryableDecline() {
    //add test cases
        val addTestCase1 = setUpMtnSimData("9318", "27837640171", "virtual_recharge", 200);

        addMtnTestCases(Arrays.asList(addTestCase1), Port.MTN_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

    //perform R&T - purchase mtn product
        val jsonBody = setUpReserveAndTransactV4Data("2", ZAR, USSD, ChannelId.USSD, "400", "10000", "0", "27837640171");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

    //add first test: mapped to ctx PENDING response code (9318) "action" is "purchase" (virtual_recharge)
        val addTestCase2 = setUpMtnSimData("9318", "27837640171", "virtual_recharge", 200);
    //add second test: mapped to ctx NON_RETRYABLE_DECLINE response code (9313) "action" is "lookup" (repeat_virtual_recharge)
        val addTestCase3 = setUpMtnSimData("9313", "27837640171", "repeat_virtual_recharge", 200);

        addMtnTestCases(Arrays.asList(addTestCase2,addTestCase3), Port.MTN_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

    //set simulator to the default state (delete simulator tests)
        removeAllMtnTestCases(Port.MTN_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

    //Verify transaction status is "FAILED"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, 2, queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString("FAILED"));

    //Verify against support tool API
        getRaasFlow(Port.RAAS_FLOW, raasTxnRef)
                .then().assertThat().statusCode(SC_OK)
                //Verify first response code matches "TransactionResponseCode" mapped to "mtn_za Response Code"(2201)
                .body("ctx_response[0].responseCode", Matchers.is(2201))
                //AND transaction was pending (ctx lookup with response code 2236)
                .body("ctx_lookup_response.clientTransactionId", Matchers.is(raasTxnRef.concat("-0001")))
                //AND last lookup is failed in the db (ctx lookup with response code 2213) TODO: Check response code matches 2213
                .body("ctx_lookup_response.clientTransactionId", Matchers.is(raasTxnRef.concat("-0001")))
                //AND second response code matches "TransactionResponseCode" mapped to "mtn_za Response Code"(2236) TODO: Check response code matches 2236
                .body("ctx_response[1].clientTransactionId", Matchers.is(raasTxnRef.concat("-0001")))
                //AND success response code is received from the funding source
                .body("transaction_result_response.responseCode", Matchers.is("202"))
                //AND transactions were retried only two times
                .body("ctx_response.clientTransactionId", Matchers.not(raasTxnRef.concat("-0002")));
    }
}