package api.transact;

import api.clients.ReserveAndTransactClient;
import api.clients.TransactClient;
import api.domains.transact.model.TransactResponse;
import api.enums.*;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import util.base_test.BaseApiTest;

import static api.clients.TransactClient.executeTransact;
import static api.domains.transact.repo.TransactRequestRepo.setUpTransactV3DataReserveFundTxRefMaxLimit;
import static api.domains.transact.repo.TransactRequestRepo.*;
import static api.enums.ChannelName.USSD;
import static org.apache.http.HttpStatus.*;

public class TransactCharLimitForRequestParamTest {

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93950")
    public void testTransactV3ReserveFundsTxnRefMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV3DataReserveFundTxRefMaxLimit(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.clientTxnRefV2MaxLimit);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageProcessingRequest))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93950")
    public void testTransactV3AccountIdentifierMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV3DataAccIdentifierMaxLimit(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.AccountIdentifierV2MaxLimit);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageServiceTUnavailable))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93950")
    public void testTransactV3ClientTxnRefMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV3DataClientTxnRefMaxLimit(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.clientTxnRefV2MaxLimit);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageClientTxnRef))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93950")
    public void testTransactV3ChannelSessionIdRefMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV3DataChannelSessionIdMaxLimit(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.channelSessionIdV2MaxLimit);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageAlphaNumericCSID))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93950")
    public void testTransactV3AuthCodeMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV3DataAuthCodeMaxLimit(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.authCodeV2MaxLimit);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageAuthCode))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93950")
    public void testTransactV3TimeStampMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV3DataTimeStampMaxLimit(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.timeStampMaxLimit);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageInvalidJsonBody))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93950")
    public void testTransactV3ClientIdMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV3Data(ReserveAndTransactClient.ClientIdInvalid, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageInvalidJsonBody))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93950")
    public void testTransactV3ProductIdMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV3Data(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.Product_Invalid);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageInvalidJsonBody))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93950")
    public void testTransactV3PurchaseAmountMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV3DataPurchaseAmountMaxLimit(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmountMaxLimit);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessagePurchaseAmountMaxLimit))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93950")
    public void testTransactV3FeeAmountMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV3DataFeeAmountMaxLimit(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.clientTxnRefV2MaxLimit);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageInvalidJsonBody))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93950")
    public void testTransactV3ChannelIdMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV3Data(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INVALID, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageChannelIDMaxLimit))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93950")
    public void testTransactV3ChannelNameMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV3Data(ReserveAndTransactClient.TestClient3, ChannelName.INVALID, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageChannelName))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93950")
    public void testTransactV3SourceIdentifierMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV3DataSourceIdentifierMaxLimit(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917,ReserveAndTransactClient.SourceIdentifierMaxLimit );

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageSourceIdentifierMaxLimit))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93950")
    public void testTransactV3TargetIdentifierMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV3DataTargetIdentifierMaxLimit(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917,ReserveAndTransactClient.TargetIdentifierMaxLimit );

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageTargetIdentifierMaxLimit))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93946")
    public void testTransactV4ReserveFundTxnRefMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV4DataReserveFundTxnMaxLimit(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.clientTxnRefV2MaxLimit);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageProcessingRequest))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93946")
    public void testTransactV4AccIdentifierMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV4DataAccIdentifierMaxLimit(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.AccountIdentifierV2MaxLimit);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageServiceTUnavailable))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93946")
    public void testTransactV4clientTxnRefMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV4DataClientTxnRefMaxLimit(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.clientTxnRefV2MaxLimit);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageClientTxnRef))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93946")
    public void testTransactV4channelSessionIdMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV4DataChannelSessionIdMaxLimit(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.channelSessionIdV2MaxLimit);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageAlphaNumericCSID))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93946")
    public void testTransactV4authCodeMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV4DataAuthCodeMaxLimit(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.authCodeV2MaxLimit);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageAuthCode))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93946")
    public void testTransactV4timestampMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV4DataTimestampMaxLimit(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.timeStampMaxLimit);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageInvalidJsonBody))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93946")
    public void testTransactV4ClientIdMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV4Data(ReserveAndTransactClient.ClientIdInvalid, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageInvalidJsonBody))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93946")
    public void testTransactV4FundingSourceIdMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV4DataFundingSourceIdMaxLimit(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.ClientIdInvalid);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageInvalidJsonBody))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93946")
    public void testTransactV4productIdMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV4Data(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.Product_Invalid);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageInvalidJsonBody))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93946")
    public void testTransactV4purchaseAmountMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV4DataPurchaseAmountMaxLimit(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmountMaxLimit);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessagePurchaseAmountMaxLimit))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93946")
    public void testTransactV4FeeAmountMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV4DataFeeAmountMaxLimit(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.clientTxnRefV2MaxLimit);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageInvalidJsonBody))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93946")
    public void testTransactV4CurrencyCodeMaxLimit() throws InterruptedException {
            val jsonBody = setUpTransactV4Data(ReserveAndTransactClient.TestClient3, CurrencyCode.INVALIDCC, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

            val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                    .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                    .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                    .body("responseMessage", Matchers.containsString(TransactClient.responseMessageCurrencyCodeMaxLimitCurrency_Transact))
                    .body("raasTxnRef", Matchers.nullValue())
                    .extract().body().as(TransactResponse.class).getRaasTxnRef();

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93946")
    public void testTransactV4ChannelIdMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV4Data(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INVALID, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageChannelIDMaxLimit))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93946")
    public void testTransactV4ChannelNameMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV4Data(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INVALID, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageChannelName))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93946")
    public void testTransactV4SourceIdentifierMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV4DataSourceIdentifierMaxLimit(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.SourceIdentifierMaxLimit);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageSourceIdentifierMaxLimit))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93946")
    public void testTransactV4TargetIdentifierMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV4DataTargetIdentifierMaxLimit(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.TargetIdentifierMaxLimit);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageTargetIdentifierMaxLimit))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93955")
    public void testTransactV2ReserveFundsTxnRefMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV2DataReserveFundsTxnRefMaxLimit(ReserveAndTransactClient.TestClient3, ChannelName.USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.clientTxnRefV2MaxLimit);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageProcessingRequest))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93955")
    public void testTransactV2AccountIdentifierMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV2DataAccountIdentifierMaxLimit(ReserveAndTransactClient.TestClient3, ChannelName.USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.AccountIdentifierV2MaxLimit);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageServiceTUnavailable))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93955")
    public void testTransactV2ClientTxnRefMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV2DataClientTxnRefMaxLimit(ReserveAndTransactClient.TestClient3, ChannelName.USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.clientTxnRefV2MaxLimit);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageClientTxnRef))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93955")
    public void testTransactV2ChannelSessionIdMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV2DataChannelSessionIdMaxLimit(ReserveAndTransactClient.TestClient3, ChannelName.USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.channelSessionIdV2MaxLimit);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageCSIDAlphaNumeric))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93955")
    public void testTransactV2authCodeMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV2DataAuthCodeIdMaxLimit(ReserveAndTransactClient.TestClient3, ChannelName.USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.authCodeV2MaxLimit);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageAuthCode))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93955")
    public void testTransactV2TimestampMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV2DataTimestampMaxLimit(ReserveAndTransactClient.TestClient3, ChannelName.USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.timeStampMaxLimit);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageInvalidJsonBody))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93955")
    public void testTransactV2ClientIdMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV2Data(ReserveAndTransactClient.ClientIdInvalid, ChannelName.USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageInvalidJsonBody))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93955")
    public void testTransactV2ProductIdMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV2Data(ReserveAndTransactClient.TestClient3, ChannelName.USSD, ChannelId.USSD, ReserveAndTransactClient.Product_Invalid);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageInvalidJsonBody))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93955")
    public void testTransactV2PurchaseAmountMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV2DataPurchaseAmountMaxLimit(ReserveAndTransactClient.TestClient3, ChannelName.USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmountMaxLimit);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessagePurchaseAmountMaxLimit))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93955")
    public void testTransactV2ChannelIdMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV2Data(ReserveAndTransactClient.TestClient3, ChannelName.USSD, ChannelId.INVALID, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageChannelIDMaxLimit))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93955")
    public void testTransactV2ChannelNameMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV2Data(ReserveAndTransactClient.TestClient3, ChannelName.INVALID, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageChannelName))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93955")
    public void testTransactV2SourceIdentifierMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV2DataSourceIdentifierMaxLimit(ReserveAndTransactClient.TestClient3, ChannelName.USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.SourceIdentifierMaxLimit);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageSourceIdentifierMaxLimit))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93955")
    public void testTransactV2TargetIdentifierMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV2DataTargetIdentifierMaxLimit(ReserveAndTransactClient.TestClient3, ChannelName.USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917,ReserveAndTransactClient.TargetIdentifierMaxLimit );

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageTargetIdentifierMaxLimit))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v1/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93956")
    public void testTransactV1ReserveFundsTxnRefMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV1DataReserveFundsTxnRefMaxLimit(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.clientTxnRefV2MaxLimit);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageProcessingRequest))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v1/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93956")
    public void testTransactV1AccountIdentifierMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV1DataAccIdentifier(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.AccountIdentifierV2MaxLimit);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageServiceTUnavailable))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v1/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93956")
    public void testTransactV1ClientTxnRefMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV1DataClientTxnRefMaxLimit(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.clientTxnRefV2MaxLimit);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageServiceTUnavailable))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v1/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93956")
    public void testTransactV1ChannelSessionIdMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV1DataChannelSessionIdMaxLimit(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.channelSessionIdV2MaxLimit);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageServiceTUnavailable))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v1/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93956")
    public void testTransactV1TimeStampMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV1DataTimeStampIdMaxLimit(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.timeStampMaxLimit);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageInvalidJsonBody))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v1/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93956")
    public void testTransactV1ClientIdMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV1Data(ReserveAndTransactClient.ClientIdInvalid, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageInvalidJsonBody))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v1/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93956")
    public void testTransactV1ProductIdMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV1Data(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.Product_Invalid);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageInvalidJsonBody))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    }

//    @Test
//    @Description("30100 :: payd-raas-gateway :: v1/transact :: char limit exceeded value for request parameters")
//    @TmsLink("TECH-93956")
//    public void testTransactV1PurchaseAmountMaxLimit() throws InterruptedException {
//        val jsonBody = setUpTransactV1DataPurchaseAmountMaxLimit(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.channelSessionIdV2MaxLimit);
//
//        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
//                .then().assertThat().statusCode(SC_BAD_REQUEST)
//                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
//                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageInvalidJsonBody))
//                .body("raasTxnRef", Matchers.nullValue())
//                .extract().body().as(TransactResponse.class).getRaasTxnRef();
//
//    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v1/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93956")
    public void testTransactV1ChannelIdMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV1Data(ReserveAndTransactClient.TestClient3, USSD, ChannelId.MAXLIMIT, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageInvalidJsonBody))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v1/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93956")
    public void testTransactV1ChannelNameMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV1Data(ReserveAndTransactClient.TestClient3, ChannelName.INVALID, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageServiceTUnavailable))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v1/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93956")
    public void testTransactV1SourceIdentifierMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV1DataSourceIdentifierMaxLimit(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.SourceIdentifierMaxLimit);
        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageServiceTUnavailable))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v1/transact :: char limit exceeded value for request parameters")
    @TmsLink("TECH-93956")
    public void testTransactV1TargetIdentifierMaxLimit() throws InterruptedException {
        val jsonBody = setUpTransactV1DataTargetIdentifierMaxLimit(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.TargetIdentifierMaxLimit);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageServiceTUnavailable))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    }



}
