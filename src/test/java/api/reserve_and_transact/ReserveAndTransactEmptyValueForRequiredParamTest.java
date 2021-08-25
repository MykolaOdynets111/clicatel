package api.reserve_and_transact;

import api.clients.ReserveAndTransactClient;
import api.clients.TransactClient;
import api.domains.reserve_and_transact.model.ReserveAndTransactResponse;
import api.domains.transact.model.TransactResponse;
import api.enums.ChannelId;
import api.enums.ChannelName;
import api.enums.Port;
import api.enums.Version;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static api.clients.ReserveAndTransactClient.executeReserveAndTransact;
import static api.clients.TransactClient.executeTransact;
import static api.domains.reserve_and_transact.repo.ReserveAndTransactRequestRepo.*;
import static api.domains.transact.repo.TransactRequestRepo.*;
import static api.domains.transact.repo.TransactRequestRepo.setUpTransactV1DataWithCIDEmpty;
import static api.domains.transact.repo.TransactRequestRepo.setUpTransactV1DataWithChannelIDEmpty;
import static api.domains.transact.repo.TransactRequestRepo.setUpTransactV1DataWithPAEmpty;
import static api.domains.transact.repo.TransactRequestRepo.setUpTransactV1DataWithPIDEmpty;
import static api.domains.transact.repo.TransactRequestRepo.setUpTransactV1DataWithTargetIdentifierEmpty;
import static api.enums.CurrencyCode.*;
import static api.enums.ChannelName.USSD;
import static org.apache.http.HttpStatus.*;

public class ReserveAndTransactEmptyValueForRequiredParamTest {
    //V4 R&T Empty Value test cases
     @Test
     @Description("30100 :: payd-raas-gateway :: v4/reserveAndTransact :: empty value for required parameters")
     @TmsLink("TECH-93358")
     public void testReserveAndTransactV4EmptyValueCSID() throws InterruptedException {
         val jsonBody = setUpReserveAndTransactV4DataWithEmptyChannelSessionID(ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier, "");

         val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                 .then().assertThat().statusCode(SC_BAD_REQUEST)
                 .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                 .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageCSIDAlphaNumeric))
                 .body("raasTxnRef", Matchers.nullValue())
                 .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
     }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/reserveAndTransact :: empty value for required parameters")
    @TmsLink("TECH-93358")
    public void testReserveAndTransactV4EmptyValueTS() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV4DataWithEmptyTS(ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier, "");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageTimeStampNotNull))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/reserveAndTransact :: empty value for required parameters")
    @TmsLink("TECH-93358")
    public void testReserveAndTransactV4EmptyValueCID() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV4DataWithEmptyCID("", NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageServiceTUnavailable))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/reserveAndTransact :: empty value for required parameters")
    @TmsLink("TECH-93358")
    public void testReserveAndTransactV4EmptyValueFSID() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV4DataWithEmptyFSID(ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier,"");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundingSourceMandatory))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/reserveAndTransact :: empty value for required parameters")
    @TmsLink("TECH-93358")
    public void testReserveAndTransactV4EmptyValuePID() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV4DataWithEmptyPID(ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, "", ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageProductID))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/reserveAndTransact :: empty value for required parameters")
    @TmsLink("TECH-93358")
    public void testReserveAndTransactV4EmptyValuePA() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV4DataWithEmptyPA(ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, "", ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessagePurchaseAmount))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/reserveAndTransact :: empty value for required parameters")
    @TmsLink("TECH-93358")
    public void testReserveAndTransactV4EmptyValueFA() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV4DataWithEmptyFA(ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, "", ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_2055))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageFeeAmount))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/reserveAndTransact :: empty value for required parameters")
    @TmsLink("TECH-93358")
    public void testReserveAndTransactV4EmptyValueCC() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV4DataWithEmptyCC(ReserveAndTransactClient.TestClient3, "", USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageCurrencyCode))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/reserveAndTransact :: empty value for required parameters")
    @TmsLink("TECH-93358")
    public void testReserveAndTransactV4EmptyValueChannelID() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV4DataWithEmptyChannelID(ReserveAndTransactClient.TestClient3, NGN, USSD, "", ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageChannelID))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/reserveAndTransact :: empty value for required parameters")
    @TmsLink("TECH-93358")
    public void testReserveAndTransactV4EmptyValueChannelN() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV4DataWithEmptyChannelN(ReserveAndTransactClient.TestClient3, NGN, "", ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageChannelNameAlphanumeric))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/reserveAndTransact :: empty value for required parameters")
    @TmsLink("TECH-93358")
    public void testReserveAndTransactV4EmptyValueSI() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV4DataWithEmptySI(ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier, "");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageSourceIdentifier))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/reserveAndTransact :: empty value for required parameters")
    @TmsLink("TECH-93358")
    public void testReserveAndTransactV4EmptyValueTI() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV4DataWithEmptyTI(ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier, "");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageTargetIdentifier))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    //V3 R&T Empty Value test cases
    @Test
    @Description("30100 :: payd-raas-gateway :: v3/reserveAndTransact :: empty value for required parameters\t")
    @TmsLink("TECH-93359")

    public void testReserveAndTransactV3EmptyValueCSID() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV3DataWithEmptyValueCSID(ReserveAndTransactClient.TestClient3, ChannelName.MOBILE, ChannelId.MOBILE, ReserveAndTransactClient.ProductAirtel_917, "");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageCSIDAlphaNumeric))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/reserveAndTransact :: empty value for required parameters\t")
    @TmsLink("TECH-93359")

    public void testReserveAndTransactV3EmptyValueTS() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV3DataWithEmptyValueTS(ReserveAndTransactClient.TestClient3, ChannelName.MOBILE, ChannelId.MOBILE, ReserveAndTransactClient.ProductAirtel_917, "");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageTimeStampNotNull))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/reserveAndTransact :: empty value for required parameters\t")
    @TmsLink("TECH-93359")

    public void testReserveAndTransactV3EmptyValueClientID() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV3Data("", ChannelName.MOBILE, ChannelId.MOBILE, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageServiceTUnavailable))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/reserveAndTransact :: empty value for required parameters\t")
    @TmsLink("TECH-93359")
    public void testReserveAndTransactV3EmptyValuePID() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV3Data(ReserveAndTransactClient.TestClient3, ChannelName.MOBILE, ChannelId.MOBILE, "");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageProductID))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/reserveAndTransact :: empty value for required parameters\t")
    @TmsLink("TECH-93359")
    public void testReserveAndTransactV3EmptyValuePA() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV3DataWithEmptyValuePA(ReserveAndTransactClient.TestClient3, ChannelName.MOBILE, ChannelId.MOBILE, ReserveAndTransactClient.ProductAirtel_917, "");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessagePurchaseAmount))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/reserveAndTransact :: empty value for required parameters\t")
    @TmsLink("TECH-93359")
    public void testReserveAndTransactV3EmptyValueFA() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV3DataWithEmptyValueFA(ReserveAndTransactClient.TestClient3, ChannelName.MOBILE, ChannelId.MOBILE, ReserveAndTransactClient.ProductAirtel_917, "");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_2055))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageFeeAmount))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/reserveAndTransact :: empty value for required parameters\t")
    @TmsLink("TECH-93359")
    public void testReserveAndTransactV3EmptyValueChannelID() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV3DataWithEmptyValueChannelID(ReserveAndTransactClient.TestClient3, ChannelName.MOBILE, "", ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageChannelID))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/reserveAndTransact :: empty value for required parameters")
    @TmsLink("TECH-93359")
    public void testReserveAndTransactV3EmptyValueChannelName() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV3DataWithEmptyValueChannelName(ReserveAndTransactClient.TestClient3, "", ChannelId.MOBILE, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageChannelNameAlphanumeric))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/reserveAndTransact :: empty value for required parameters")
    @TmsLink("TECH-93359")
    public void testReserveAndTransactV3EmptyValueTI() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV3DataWithEmptyValueTI(ReserveAndTransactClient.TestClient3, ChannelName.MOBILE, ChannelId.MOBILE, ReserveAndTransactClient.ProductAirtel_917, "");
        Thread.sleep(3000);
        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageTargetIdentifierMaxLimit))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }
    @Test
    @Description("30100 :: payd-raas-gateway :: v3/reserveAndTransact :: empty value for required parameters")
    @TmsLink("TECH-93359")
    public void testReserveAndTransactV3EmptyValueSI() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV3DataWithEmptyValueSI(ReserveAndTransactClient.TestClient3, ChannelName.MOBILE, ChannelId.MOBILE, ReserveAndTransactClient.ProductAirtel_917, "", "2023-03-03T00:00:00.000+02:00");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageSourceIdentifier))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    //V2
    @Test
    @Description("30100 :: payd-raas-gateway :: v2/reserveAndTransact :: empty value for required parameters")
    @TmsLink("TECH-93361")
    public void testReserveAndTransactV2EmptyValueCSID() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV2DataWithEmptyValueCSID(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, "");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageCSIDAlphaNumeric))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/reserveAndTransact :: empty value for required parameters")
    @TmsLink("TECH-93361")
    public void testReserveAndTransactV2EmptyValueTS() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV2DataWithEmptyValueTS(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, "");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageTimeStampNotNull))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/reserveAndTransact :: empty value for required parameters")
    @TmsLink("TECH-93361")
    public void testReserveAndTransactV2EmptyValueCID() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV2DataWithEmptyValueCID("", USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageServiceTUnavailable))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/reserveAndTransact :: empty value for required parameters")
    @TmsLink("TECH-93361")
    public void testReserveAndTransactV2EmptyValuePID() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV2Data(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, "");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageProductID))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/reserveAndTransact :: empty value for required parameters")
    @TmsLink("TECH-93361")
    public void testReserveAndTransactV2EmptyValuePA() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV2DataWithEmptyValuePA(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessagePurchaseAmount))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/reserveAndTransact :: empty value for required parameters")
    @TmsLink("TECH-93361")
    public void testReserveAndTransactV2EmptyValueChannelID() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV2DataWithEmptyValueChannelID(ReserveAndTransactClient.TestClient3, USSD, "", ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageChannelID))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/reserveAndTransact :: empty value for required parameters")
    @TmsLink("TECH-93361")
    public void testReserveAndTransactV2EmptyValueChannelName() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV2DataWithEmptyValueChannelName(ReserveAndTransactClient.TestClient3, "", ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageChannelNameAlphanumeric))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/reserveAndTransact :: empty value for required parameters")
    @TmsLink("TECH-93361")
    public void testReserveAndTransactV2EmptyValueSourceIdentifier() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV2DataWithEmptyValueSI(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, "");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageSourceIdentifierMaxLimit))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/reserveAndTransact :: empty value for required parameters")
    @TmsLink("TECH-93361")
    public void testReserveAndTransactV2EmptyValueTargetIdentifier() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV2DataWithEmptyValueTI(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, "");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageTargetIdentifierMaxLimit))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    //v1
    @Test
    @Description("30100 :: payd-raas-gateway :: v1/transact :: empty value for required parameters")
    @TmsLink("TECH-93363")
    public void V1testReserveClientIDEmpty() throws InterruptedException {
        val jsonBody = setUpTransactV1DataWithCIDEmpty("", ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageServiceTUnavailable))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }


    @Test
    @Description("30100 :: payd-raas-gateway :: v1/transact :: empty value for required parameters")
    @TmsLink("TECH-93363")
    public void V1testReserveProductIDEmpty() throws InterruptedException {
        val jsonBody = setUpTransactV1DataWithPIDEmpty(ReserveAndTransactClient.TestClient3, ChannelId.INTERNET, "");

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageServiceTUnavailable))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v1/transact :: empty value for required parameters")
    @TmsLink("TECH-93363")
    public void V1testReservePurchaseAmountEmpty() throws InterruptedException {
        val jsonBody = setUpTransactV1DataWithPAEmpty(ReserveAndTransactClient.TestClient3, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917,"");

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageServiceTUnavailable))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v1/transact :: empty value for required parameters")
    @TmsLink("TECH-93363")
    public void V1testReserveChannelIDEmpty() throws InterruptedException {
        val jsonBody = setUpTransactV1DataWithChannelIDEmpty(ReserveAndTransactClient.TestClient3, "", ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(TransactClient.ResponseCode_0001))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageChannelIDV1))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v1/transact :: empty value for required parameters")
    @TmsLink("TECH-93363")
    public void V1testReserveTargetIdentifierEmpty() throws InterruptedException {
        val jsonBody = setUpTransactV1DataWithTargetIdentifierEmpty(ReserveAndTransactClient.TestClient3, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917, "");

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageTargetIdentifierV1))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }
}
