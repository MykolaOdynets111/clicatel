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
import io.qameta.allure.TmsLinks;
import lombok.val;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static api.clients.ReserveAndTransactClient.executeReserveAndTransact;
import static api.clients.TransactClient.executeTransact;
import static api.domains.reserve_and_transact.repo.ReserveAndTransactRequestRepo.*;
import static api.domains.transact.repo.TransactRequestRepo.*;
import static api.enums.ChannelName.USSD;
import static api.enums.CurrencyCode.NGN;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.apache.http.HttpStatus.*;

public class ReserveAndTransactNullValueForRequiredParamTest {
    //V1 Null
    @Test
    @Description("30100 :: payd-raas-gateway :: v1/reserveAndTransact :: null value for required parameters")
    @TmsLink("MKP-936")

    public void V1testReserveClientIDNull() throws InterruptedException {
        val jsonBody = setUpTransactV1DataWithCIDNull(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageServiceTUnavailable))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v1/reserveAndTransact :: null value for required parameters")
    @TmsLink("MKP-569")
    public void V1testReserveProductIDNull() throws InterruptedException {
        val jsonBody = setUpTransactV1DataWithPIDNull(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageServiceTUnavailable))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v1/reserveAndTransact :: null value for required parameters")
    @TmsLink("MKP-643")
    public void V1testReservePANull() throws InterruptedException {
        val jsonBody = setUpTransactV1DataWithPANull(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageServiceTUnavailable))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v1/reserveAndTransact :: null value for required parameters")
    @TmsLink("MKP-536")
    public void V1testReserveChannelIDNull() throws InterruptedException {
        val jsonBody = setUpTransactV1DataWithChannelIDNull(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(TransactClient.ResponseCode_0001))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageChannelIDV1))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v1/reserveAndTransact :: null value for required parameters")
    @TmsLink("MKP-692")
    public void V1testTargetIdentifierNull() throws InterruptedException {
        val jsonBody = setUpTransactV1DataWithTargetIdentifierNull(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageServiceTUnavailable))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }
    @Test
    @Description("POST /raas/v1/transact :: null value for timestamp")
    @TmsLink("MKP-672")
    public void V1testTimeStampNull() throws InterruptedException {
        val jsonBody = setUpTransactV1DataTimeStamp(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, null);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageProcessingRequest))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }
    @Test
    @Description("POST /raas/v1/transact :: null value for sourceIdentifier")
    @TmsLink("MKP-547")
    public void V1testSourceIdentifierNull() throws InterruptedException {
        val jsonBody = setUpTransactV1DataWithSourceIdentifierNull(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageProcessingRequest))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }
    @Test
    @Description("POST /raas/v1/transact :: null value for clientTxnRef")
    @TmsLink("MKP-659")
    public void V1testClientTxnRefNull() throws InterruptedException {
        val jsonBody = setUpTransactV1DataClientTxnRefId(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917,null);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageProcessingRequest))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }
    @Test
    @Description("POST /raas/v1/transact :: null value for clientTxnRef")
    @TmsLink("MKP-583")
    public void V1testChannelSessionIdNull() throws InterruptedException {
        val jsonBody = setUpTransactV1DataChannelSessionId(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917,null);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageProcessingRequest))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }
    @Test
    @Description("POST /raas/v1/transact :: null value for channelName")
    @TmsLink("MKP-581")
    public void V1testChannelNameNull() throws InterruptedException {
        val jsonBody = setUpTransactV1DataWithChannelNameNull(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageProcessingRequest))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }
    @Test
    @Description("POST /raas/v1/transact :: null value for authCode")
    @TmsLink("MKP-689")
    public void V1testAuthCodeNull() throws InterruptedException {
        val jsonBody = setUpTransactV1DataReserveFundsTxnRefMaxLimit(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, null);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageProcessingRequest))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }
    @Test
    @Description("POST /raas/v1/transact :: null value for accountIdentifier")
    @TmsLink("MKP-717")
    public void V1testAccIDentifierNull() throws InterruptedException {
        val jsonBody = setUpTransactV1DataAccIdentifier(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, null);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageProcessingRequest))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    // V2 Null
    @Test
    @Description("30100 :: payd-raas-gateway :: v2/reserveAndTransact :: null value for required parameters (CSIDNull)")
    @TmsLink("MKP-1026")
    public void testReserveAndTransactV2SuccessCSIDNull() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV2DataWithCSIDNULL(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, null);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageNullCSID))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }


    @Test
    @Description("30100 :: payd-raas-gateway :: v2/reserveAndTransact :: null value for required parameters")
    @TmsLink("MKP-868")
    public void testReserveAndTransactV2SuccessTSNull() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV2DataWithTSNULL(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, null);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageTimeStampNotNull))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/reserveAndTransact :: null value for required parameters")
    @TmsLink("MKP-555")
    public void testReserveAndTransactV2SuccessClientIDNull() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV2DataWithClientIDNULL(null, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageServiceTUnavailable))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/reserveAndTransact :: null value for required parameters")
    @TmsLink("MKP-531")
    public void testReserveAndTransactV2SuccessPIDNull() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV2DataWithProductIDNULL(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, null);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageProductID))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/reserveAndTransact :: null value for required parameters")
    @TmsLink("MKP-599")
    public void testReserveAndTransactV2SuccessPurchaseAmountNull() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV2DataWithPurchaseAmountNULL(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, null);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessagePurchaseAmount))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/reserveAndTransact :: null value for required parameters")
    @TmsLink("MKP-650")
    public void testReserveAndTransactV2SuccessChannelIDNull() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV2DataWithChannelIDNULL(ReserveAndTransactClient.TestClient3, USSD, null, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageChannelID))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/reserveAndTransact :: null value for required parameters")
    @TmsLink("MKP-625")
    public void testReserveAndTransactV2SuccessChannelNameNull() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV2DataWithChannelNameNULL(ReserveAndTransactClient.TestClient3, null, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageChannelNameNotNull))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/reserveAndTransact :: null value for required parameters (SourceIdentifierNull)")
    @TmsLink("MKP-844")
    public void testReserveAndTransactV2SuccessSourceIdentifierNull() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV2DataWithSourceIdentifierNULL(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, null);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageSourceIdentifierNotNull))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/reserveAndTransact :: null value for required parameters")
    @TmsLink("MKP-618")
    public void testReserveAndTransactV2SuccessTargetIdentifierNull() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV2DataWithTargetIdentifierNULL(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, null);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageTargetIdentifierNotNull))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }
    @Test
    @Description("POST /raas/v2/reserveAndTransact :: null value for clientTxnRef")
    @TmsLink("MKP-666")
    public void testReserveAndTransactV2SuccessClientTxnRefNull() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV2DataClientTxnRefMaxLimit(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, null);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageProcessingRequest))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }
    @Test
    @Description("POST /raas/v2/reserveAndTransact :: null value for authCode")
    @TmsLink("MKP-568")
    public void testReserveAndTransactV2SuccessAuthCodeNull() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV2DataAuthCodeMaxLimit(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, null);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageProcessingRequest))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }
    @Test
    @Description("POST /raas/v2/reserveAndTransact :: null value for accountIdentifier")
    @TmsLink("MKP-657")
    public void testReserveAndTransactV2SuccessAccIdentifierNull() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV2DataAccIdentifierMaxLimit(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, null);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageProcessingRequest))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    //V3 R&T NULL
    @Test
    @Description("30100 :: payd-raas-gateway :: v3/reserveAndTransact :: null value for required parameters")
    @TmsLink("MKP-995")
    public void testReserveAndTransactV3SuccessWithChannelSessionIDNull() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV3DataChannelSessionIDNull(ReserveAndTransactClient.TestClient3, ChannelName.MOBILE, ChannelId.MOBILE, ReserveAndTransactClient.ProductAirtel_917, null);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageNullCSID))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/reserveAndTransact :: null value for required parameters")
    @TmsLink("MKP-920")
    public void testReserveAndTransactV3SuccessWithChannelTimeStampNull() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV3DataTimeStampNull(ReserveAndTransactClient.TestClient3, ChannelName.MOBILE, ChannelId.MOBILE, ReserveAndTransactClient.ProductAirtel_917, null);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageTimeStampNotNull))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/reserveAndTransact :: null value for required parameters")
    @TmsLink("MKP-924")
    public void testReserveAndTransactV3SuccessWithClientIDNull() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV3DataClientIDNull(null, ChannelName.MOBILE, ChannelId.MOBILE, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageServiceTUnavailable))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/reserveAndTransact :: null value for required parameters")
    @TmsLink("MKP-818")
    public void testReserveAndTransactV3SuccessWithProductIDNull() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV3DataProductIDNull(ReserveAndTransactClient.TestClient3, ChannelName.MOBILE, ChannelId.MOBILE, null);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageProductID))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/reserveAndTransact :: null value for required parameters")
    @TmsLink("MKP-781")
    public void testReserveAndTransactV3SuccessWithPurchaseAmountNull() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV3DataPurchaseAmountNull(ReserveAndTransactClient.TestClient3, ChannelName.MOBILE, ChannelId.MOBILE, ReserveAndTransactClient.ProductAirtel_917, null);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessagePurchaseAmount))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/reserveAndTransact :: null value for required parameters")
    @TmsLink("MKP-767")
    public void testReserveAndTransactV3SuccessWithFeeAmountNull() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV3DataFeeAmountNull(ReserveAndTransactClient.TestClient3, ChannelName.MOBILE, ChannelId.MOBILE, ReserveAndTransactClient.ProductAirtel_917, null);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_2055))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageFeeAmount))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/reserveAndTransact :: null value for required parameters")
    @TmsLink("MKP-784")
    public void testReserveAndTransactV3SuccessWithChannelIDNull() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV3DataChannelIDNull(ReserveAndTransactClient.TestClient3, ChannelName.MOBILE, null, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageChannelID))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/reserveAndTransact :: null value for required parameters")
    @TmsLink("MKP-726")
    public void testReserveAndTransactV3SuccessWithChannelNameNull() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV3DataChannelNameNull(ReserveAndTransactClient.TestClient3, null, ChannelId.MOBILE, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageChannelNameNotNull))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/reserveAndTransact :: null value for required parameters")
    @TmsLink("MKP-809")
    public void testReserveAndTransactV3SuccessWithSourceIdentifierNull() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV3DataSourceIdentifierNull(ReserveAndTransactClient.TestClient3, ChannelName.MOBILE, ChannelId.MOBILE, ReserveAndTransactClient.ProductAirtel_917, null);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageSourceIdentifierNotNull))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/reserveAndTransact :: null value for required parameters")
    @TmsLink("MKP-803")
    public void testReserveAndTransactV3SuccessWithTargetIdentifierNull() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV3DataTargetIdentifierNull(ReserveAndTransactClient.TestClient3, ChannelName.MOBILE, ChannelId.MOBILE, ReserveAndTransactClient.ProductAirtel_917, null);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageTargetIdentifierNotNull))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }
    @Test
    @Description("POST /raas/v3/reserveAndTransact :: null value for the clientTxnRef")
    @TmsLink("MKP-796")
    public void testReserveAndTransactV3SuccessWithClientTxnRefNull() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV3DataClientTxnRef(ReserveAndTransactClient.TestClient3, ChannelName.MOBILE, ChannelId.MOBILE, ReserveAndTransactClient.ProductAirtel_917, null);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundsReserved))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }
    @Test
    @Description("POST /raas/v3/reserveAndTransact :: null value for the authCode")
    @TmsLink("MKP-729")
    public void testReserveAndTransactV3SuccessWithAuthCodeNull() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV3DataAuthCode(ReserveAndTransactClient.TestClient3, ChannelName.MOBILE, ChannelId.MOBILE, ReserveAndTransactClient.ProductAirtel_917, null);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundsReserved))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }
    @Test
    @Description("POST /raas/v3/reserveAndTransact :: null value for the accountIdentifier")
    @TmsLink("MKP-880")
    public void testReserveAndTransactV3SuccessWithAccountIdentifierNull() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV3DataAccIdentifier(ReserveAndTransactClient.TestClient3, ChannelName.MOBILE, ChannelId.MOBILE, ReserveAndTransactClient.ProductAirtel_917, null);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundsReserved))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    //V4 R&T Null
    @Test
    @Description("30100 :: payd-raas-gateway :: v4/reserveAndTransact :: null value for required parameters (ChannelSessionIDNull)")
    @TmsLink("MKP-740")
    public void testReserveAndTransactV4WithChannelSessionIDNull() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV4DataWithChannelSessionIDNull(ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier,null);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageNullCSID))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/reserveAndTransact :: null value for required parameters")
    @TmsLink("MKP-985")
    public void testReserveAndTransactV4WithtimestampIDNull() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV4DataWithtimestampIDNull(ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier, null);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageTimeStampNotNull))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/reserveAndTransact :: null value for required parameters")
    @TmsLink("MKP-793")
    public void testReserveAndTransactV4WithclientIdNull() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV4DataWithclientIdNull(null, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageServiceTUnavailable))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/reserveAndTransact :: null value for required parameters")
    @TmsLink("MKP-735")
    public void testReserveAndTransactV4WithfundingSourceIdNull() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV4DataWithfundingSourceIdNull(ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier,null);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundingSourceMandatory))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/reserveAndTransact :: null value for required parameters")
    @TmsLink("MKP-760")
    public void testReserveAndTransactV4WithproductIdNull() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV4DataWithproductIdNull(ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, null, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageProductID))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/reserveAndTransact :: null value for required parameters")
    @TmsLink("MKP-916")
    public void testReserveAndTransactV4WithpurchaseAmountNull() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV4DataWithpurchaseAmountNull(ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, null, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessagePurchaseAmount))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/reserveAndTransact :: null value for required parameters")
    @TmsLink("MKP-792")
    public void testReserveAndTransactV4WithfeeAmountNull() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV4DataWithfeeAmountNull(ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, null, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_2055))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageFeeAmount))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/reserveAndTransact :: null value for required parameters")
    @TmsLink("MKP-766")
    public void testReserveAndTransactV4WithCurrencyCodeNull() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV4DataWithcurrencyCodeNull(ReserveAndTransactClient.TestClient3, null, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageCurrencyCode))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/reserveAndTransact :: null value for required parameters")
    @TmsLink("MKP-814")
    public void testReserveAndTransactV4WithchannelIdisNull() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV4DataWithchannelIdisNull(ReserveAndTransactClient.TestClient3, NGN, USSD, null, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageChannelID))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/reserveAndTransact :: null value for required parameters")
    @TmsLink("MKP-914")
    public void testReserveAndTransactV4WithchannelNameNull() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV4DataWithchannelNameNull(ReserveAndTransactClient.TestClient3, NGN, null, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageChannelNameNotNull))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/reserveAndTransact :: null value for required parameters")
    @TmsLink("MKP-889")
    public void testReserveAndTransactV4WithsourceIdentifierNull() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV4DataWithsourceIdentifierNull(ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier,null);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageSourceIdentifierNotNull))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/reserveAndTransact :: null value for required parameters")
    @TmsLink("MKP-850")
    public void testReserveAndTransactV4WithTargetIdentifierNull() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV4DataWithTargetIdentifierNull(ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier, null);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageTargetIdentifierNotNull))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }
    @Test
    @Description("POST /raas/v4/reserveAndTransact :: null value for the authCode")
    @TmsLink("MKP-886")
    public void testReserveAndTransactV4WithAuthCodeNull() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV4DataTargetIdentifier(ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundsReserved))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }
    @Test
    @Description("POST /raas/v4/reserveAndTransact :: null value for the clientTxnRef")
    @TmsLink("MKP-786")
    public void testReserveAndTransactV4WithClientTxnRefNull() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV4DataClientTxnRef(ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier, null);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundsReserved))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }
    @Test
    @Description("POST /raas/v4/reserveAndTransact :: null value for the accountIdentifier")
    @TmsLink("MKP-816")
    public void testReserveAndTransactV4WithAccountIdentifierNull() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV4DataAccountIdentifier(ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier, null);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundsReserved))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }
}

