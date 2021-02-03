package api.reserve_and_transact;
import api.domains.reserve_and_transact.model.ReserveAndTransactRequest;
import api.domains.reserve_and_transact.model.ReserveAndTransactResponse;
import api.domains.transact.model.TransactResponse;
import api.enums.*;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import util.base_test.BaseApiTest;

import static api.clients.ReserveAndTransactClient.executeReserveAndTransact;

import static api.clients.TransactClient.executeTransact;
import static api.domains.reserve_and_transact.repo.ReserveAndTransactRequestRepo.*;
import static api.domains.transact.repo.TransactRequestRepo.setUpTransactV1Data;
import static api.enums.ChannelName.*;
import static api.enums.CurrencyCode.NGN;
import static api.enums.CurrencyCode.ZAR;
import static db.enums.Sessions.POSTGRES_SQL;
import static db.clients.HibernateBaseClient.executeCustomQueryAndReturnValue;
import static db.custom_queries.ReserveAndTransactQueries.GET_TRANSACTION_STATUS;
import static java.lang.String.format;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;
import static util.DateProvider.getCurrentIsoDateTime;

public class ReserveAndTransactTest extends BaseApiTest {

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

        //raas db checks --- TODO: to complete the rest as below or verify against support tool API
//        val status = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(GET_TRANSACTION_STATUS, raasTxnRef));
//        assertThat(status)
//                .as("Postgres SQL query result should not be empty")
//                .contains("SUCCESS");
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

        //TODO: Verify against support tool API
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

        //TODO: raas db checks or verify against support tool API
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

        //TODO: raas db checks or verify against support tool API

    }


    //VENDORS
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

}