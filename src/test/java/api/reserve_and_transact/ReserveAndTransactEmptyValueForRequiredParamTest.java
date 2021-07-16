package api.reserve_and_transact;

import api.clients.ReserveAndTransactClient;
import api.clients.TransactClient;
import api.domains.reserve_and_transact.model.ReserveAndTransactResponse;
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
import static api.domains.reserve_and_transact.repo.ReserveAndTransactRequestRepo.*;
import static api.enums.ChannelName.USSD;
import static api.enums.CurrencyCode.NGN;
import static org.apache.http.HttpStatus.*;

public class ReserveAndTransactEmptyValueForRequiredParamTest {
    /* @Test
     @Description("30100 :: payd-raas-gateway :: v3/reserveAndTransact :: empty value for required parameters\t")
     @TmsLink("TECH-93359")
     public void testReserveAndTransactV4EmptyValue() throws InterruptedException {
         val jsonBody = setUpReserveAndTransactV4DataWithEmptyValueCSID(ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier, "");

         val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                 .then().assertThat().statusCode(SC_BAD_REQUEST)
                 .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                 .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageCSIDAlphaNumeric))
                 .body("raasTxnRef", Matchers.nullValue())
                 .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
     }*/
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
    @Description("30100 :: payd-raas-gateway :: v3/reserveAndTransact :: empty value for required parameters\t")
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
    @Description("30100 :: payd-raas-gateway :: v3/reserveAndTransact :: empty value for required parameters\t")
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

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/reserveAndTransact :: empty value for required parameters\t")
    @TmsLink("TECH-93359")
    public void testReserveAndTransactV3EmptyValueTI() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV3DataWithEmptyValueTI(ReserveAndTransactClient.TestClient3, ChannelName.MOBILE, ChannelId.MOBILE, ReserveAndTransactClient.ProductAirtel_917, "");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageTargetIdentifier))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }
}
