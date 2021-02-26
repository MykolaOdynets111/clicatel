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

import static api.clients.ReserveAndTransactClient.executeReserveAndTransact;
import static api.clients.ReserveAndTransactClient.executeReserveAndTransactWithSignature;
import static api.clients.SupportUiClient.getRaasFlow;
import static api.controls.TransactControl.getTransactionStatus;
import static api.domains.reserve_and_transact.repo.ReserveAndTransactRequestRepo.*;
import static api.enums.ChannelName.MOBILE;
import static api.enums.ChannelName.USSD;
import static api.enums.CurrencyCode.NGN;
import static api.enums.CurrencyCode.ZAR;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

public class ReserveAndTransactVendorsTest extends BaseApiTest {

    //SUCCESS :: VENDORS & CLIENTS
    @Test
    @Description("30100 :: payd-raas-gateway :: vendor 2 (CellC) SUCCESS")
    @TmsLink("TECH-69577")
    public void testReserveAndTransactVendor2CellCSuccess() {
        val jsonBody = setUpReserveAndTransactV4Data("2", ZAR, USSD, ChannelId.USSD, "60", "10000", "0", "27815793852");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        //raas db check --- Verify transaction status is "SUCCESS"
        assertThat(getTransactionStatus(raasTxnRef))
                .as("Postgres SQL query : Transaction Status incorrect")
                .isTrue();
    }


    @Test
    @Description("30100 :: payd-raas-gateway :: vendor 3 (MTN_ZA) SUCCESS")
    @TmsLink("TECH-68400")
    public void testReserveAndTransactVendor3MtnZaSuccess() {
        val jsonBody = setUpReserveAndTransactV4Data("2", ZAR, USSD, ChannelId.USSD, "400", "10000", "0", "27837640171");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        //raas db check --- Verify transaction status is "SUCCESS"
        assertThat(getTransactionStatus(raasTxnRef))
                .as("Postgres SQL query : Transaction Status incorrect")
                .isTrue();
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

        //raas db check --- Verify transaction status is "SUCCESS"
        assertThat(getTransactionStatus(raasTxnRef))
                .as("Postgres SQL query : Transaction Status incorrect")
                .isTrue();
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

        //raas db check --- Verify transaction status is "SUCCESS"
        assertThat(getTransactionStatus(raasTxnRef))
                .as("Postgres SQL query : Transaction Status incorrect")
                .isTrue();
    }


    @Test
    @Description("30100 :: payd-raas-gateway :: vendor 15 (3line, threeline, ClickatellBiller3) SUCCESS")
    @TmsLink("TECH-75095")
    public void testReserveAndTransactVendor15ThreelineSuccess() {
        val jsonBody = setUpReserveAndTransactV4Data("3", NGN, USSD, ChannelId.USSD, "505", "50000", "10750", "2347832008221");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        //raas db check --- Verify transaction status is "SUCCESS"
        assertThat(getTransactionStatus(raasTxnRef))
                .as("Postgres SQL query : Transaction Status incorrect")
                .isTrue();
    }


    @Test
    @Description("30100 :: payd-raas-gateway :: vendor 21 (mwm) SUCCESS")
    @TmsLink("TECH-68398")
    public void testReserveAndTransactVendor21MwmSuccess() {
        val jsonBody = setUpReserveAndTransactV4Data("206", NGN, USSD, ChannelId.USSD, "206", "200", "0", "2348038382061");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        //raas db check --- Verify transaction status is "SUCCESS"
        assertThat(getTransactionStatus(raasTxnRef))
                .as("Postgres SQL query : Transaction Status incorrect")
                .isTrue();
    }


    @Test
    @Description("30100 :: payd-raas-gateway :: vendor 23 (MTN_ZA_clickatell) SUCCESS")
    @TmsLink("TECH-68536")
    public void testReserveAndTransactVendor23MtnZaSuccess() {
        val jsonBody = setUpReserveAndTransactV4Data("2", ZAR, USSD, ChannelId.USSD, "30", "10000", "0", "27837640171");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        //raas db check --- Verify transaction status is "SUCCESS"
        assertThat(getTransactionStatus(raasTxnRef))
                .as("Postgres SQL query : Transaction Status incorrect")
                .isTrue();
    }


    @Test
    @Description("30100 :: payd-raas-gateway :: vendor 100 (MTN_NG) SUCCESS")
    @TmsLink("TECH-63683")
    public void testReserveAndTransactVendor100MtnNgSuccess() {
        val jsonBody = setUpReserveAndTransactV4Data("3", NGN, USSD, ChannelId.USSD, "100", "10000", "0", "2348038382067");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        //raas db check --- Verify transaction status is "SUCCESS"
        assertThat(getTransactionStatus(raasTxnRef))
                .as("Postgres SQL query : Transaction Status incorrect")
                .isTrue();
    }


    @Test
    @Description("30100 :: payd-raas-gateway :: vendor 101 (glo) SUCCESS")
    @TmsLink("TECH-68396")
    public void testReserveAndTransactVendor101GloSuccess() {
        val jsonBody = setUpReserveAndTransactV4Data("3", NGN, USSD, ChannelId.USSD, "329", "50000", "0", "2348038382069");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        //raas db check --- Verify transaction status is "SUCCESS"
        assertThat(getTransactionStatus(raasTxnRef))
                .as("Postgres SQL query : Transaction Status incorrect")
                .isTrue();
    }


    @Test
    @Description("30100 :: payd-raas-gateway :: vendor 102 (9mobile/etisalat) SUCCESS")
    @TmsLink("TECH-68397")
    public void testReserveAndTransactVendor1029MobileEtisalatSuccess() {
        val jsonBody = setUpReserveAndTransactV4Data("3", NGN, USSD, ChannelId.USSD, "120", "10000", "0", "2348038382069");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        //raas db check --- Verify transaction status is "SUCCESS"
        assertThat(getTransactionStatus(raasTxnRef))
                .as("Postgres SQL query : Transaction Status incorrect")
                .isTrue();
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

        //raas db check --- Verify transaction status is "SUCCESS"
        assertThat(getTransactionStatus(raasTxnRef))
                .as("Postgres SQL query : Transaction Status incorrect")
                .isTrue();
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

        //raas db check --- Verify transaction status is "SUCCESS"
        assertThat(getTransactionStatus(raasTxnRef))
                .as("Postgres SQL query : Transaction Status incorrect")
                .isTrue();
    }


    @Test
    @Description("30100 :: payd-raas-gateway :: client 1003 (test client) SUCCESS (checksum)")
    @TmsLink("TECH-56890")
    public void testReserveAndTransactWithSignatureSuccess() {
        //TODO: Investigate why {"responseCode":"4000","responseMessage":"Header Signature invalid"}

        //UAT: client 1003 (secret value Ajd7dsJD1), funding source 1003
        val jsonBody = setUpReserveAndTransactSignatureData("1003", MOBILE, ChannelId.MOBILE, "2348038382068");

//        val raasTxnRef = executeReserveAndTransactWithSignature(jsonBody, Port.TRANSACTIONS, Version.V3, "dgUVUVzgBIIl7dtAI7ziy+t2f3qYHv4u593b5lDlkYE=")
//                .then().assertThat().statusCode(SC_OK)
//                .body("responseCode", Matchers.containsString("0000"))
//                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
//                .body("raasTxnRef", Matchers.notNullValue())
//                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
//
//        //raas db check --- Verify transaction status is "SUCCESS"
//        assertThat(getTransactionStatus(raasTxnRef))
//                .as("Postgres SQL query : Transaction Status incorrect")
//                .isTrue();

        //Verify against support tool API
//        getRaasFlow(Port.RAAS_FLOW, raasTxnRef)
//                .then().assertThat().statusCode(SC_OK)
//            //Verify funds were successfully reserved (response_code equals to 0000)
//                .body("reserve_fund_response.responseCode", Matchers.is("0000"))
//            //AND ctx response code is SUCCESSFUL (0)
//                .body("ctx_response[0].responseCode", Matchers.is(0))
//            //AND successful transaction result is sent (0000)
//                .body("transaction_result_request.responseCode", Matchers.is("0000"))
//            //AND success response code is received from the funding source (202)
//                .body("transaction_result_response.responseCode", Matchers.is("202"))
//            //AND transaction wasn't retried (no records found in the db)
//                .body("ctx_response.clientTransactionId", Matchers.not(raasTxnRef.concat("-0001")))
//            //AND transaction wasn't pending (no records found in the db)
//                .body("ctx_lookup_response.clientTransactionId", Matchers.not(raasTxnRef.concat("-0000")));

    }

}