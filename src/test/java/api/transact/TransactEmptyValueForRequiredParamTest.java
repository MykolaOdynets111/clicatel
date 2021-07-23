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
import static api.domains.transact.repo.TransactRequestRepo.*;
import static api.enums.ChannelName.USSD;
import static org.apache.http.HttpStatus.*;

public class TransactEmptyValueForRequiredParamTest extends BaseApiTest {
    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: empty value for required parameters")
    @TmsLink("TECH-93364")
    public void testChannelSessionIDEmpty() throws InterruptedException {
        val jsonBody = setUpTransactV4DataWithCSIDEmpty(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageAlphaNumericCSID))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: empty value for required parameters")
    @TmsLink("TECH-93364")
    public void testTimeStampEmpty() throws InterruptedException {
        val jsonBody = setUpTransactV4DataWithTSEmpty(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageTimeStampNotNull))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: empty value for required parameters")
    @TmsLink("TECH-93364")
    public void testReserveFundasTxnRefEmpty() throws InterruptedException {
        val jsonBody = setUpTransactV4DataWithRFTREmpty(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageRFTRMandatory))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: empty value for required parameters")
    @TmsLink("TECH-93364")
    public void testReserveClientIDEmpty() throws InterruptedException {
        val jsonBody = setUpTransactV4DataWithRCIEmpty(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageServiceTUnavailable))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: empty value for required parameters")
    @TmsLink("TECH-93364")
    public void testReserveFundingSourceIDEmpty() throws InterruptedException {
        val jsonBody = setUpTransactV4DataWithFSEmpty(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(TransactClient.ResponseCode_0001))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageFundingSrouce))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: empty value for required parameters")
    @TmsLink("TECH-93364")
    public void testReserveProductIDEmpty() throws InterruptedException {
        val jsonBody = setUpTransactV4DataWithPIDEmpty(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageProductID))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: empty value for required parameters")
    @TmsLink("TECH-93364")
    public void testReservePurchaseAmountEmpty() throws InterruptedException {
        val jsonBody = setUpTransactV4DataWithPAEmpty(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessagePurchaseAmount))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: empty value for required parameters")
    @TmsLink("TECH-93364")
    public void testReserveFeeAmountEmpty() throws InterruptedException {
        val jsonBody = setUpTransactV4DataWithFAEmpty(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_2055))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageFeeAmount))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: empty value for required parameters")
    @TmsLink("TECH-93364")
    public void testReserveCurrencyCodeEmpty() throws InterruptedException {
        val jsonBody = setUpTransactV4DataWithCCEmpty(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageCurrencyCode))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }


    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: empty value for required parameters")
    @TmsLink("TECH-93364")
    public void testReserveChannelIDEmpty() throws InterruptedException {
        val jsonBody = setUpTransactV4DataWithCIDEmpty(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageChannelID))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }


    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: empty value for required parameters")
    @TmsLink("TECH-93364")
    public void testReserveChannelNameEmpty() throws InterruptedException {
        val jsonBody = setUpTransactV4DataWithCNEmpty(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageChannelName))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: empty value for required parameters")
    @TmsLink("TECH-93364")
    public void testReserveSourceIdentifierEmpty() throws InterruptedException {
        val jsonBody = setUpTransactV4DataWithSIEmpty(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageSourceIdentifier))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: empty value for required parameters")
    @TmsLink("TECH-93364")
    public void testReserveTargetIdentifierEmpty() throws InterruptedException {
        val jsonBody = setUpTransactV4DataWithCTIEmpty(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageTargetIdentifier))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    // v2

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/transact :: empty value for required parameters")
    @TmsLink("TECH-93366")
    public void V2testChannelSessionIDEmpty() throws InterruptedException {
        val jsonBody = setUpTransactV2DataWithCSIDEmpty(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageAlphaNumericCSID))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/transact :: empty value for required parameters")
    @TmsLink("TECH-93366")
    public void V2testTimeStampEmpty() throws InterruptedException {
        val jsonBody = setUpTransactV2DataWithTSEmpty(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageTimeStampNotNull))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/transact :: empty value for required parameters")
    @TmsLink("TECH-93366")
    public void V2testReserveFundasTxnRefEmpty() throws InterruptedException {
        val jsonBody = setUpTransactV2DataWithRFTREmpty(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageRFTRMandatory))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/transact :: empty value for required parameters")
    @TmsLink("TECH-93366")
    public void V2testReserveClientIDEmpty() throws InterruptedException {
        val jsonBody = setUpTransactV2DataWithRCIDEmpty(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageServiceTUnavailable))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/transact :: empty value for required parameters")
    @TmsLink("TECH-93366")
    public void V2testReserveProductIDEmpty() throws InterruptedException {
        val jsonBody = setUpTransactV2DataWithPIDEmpty(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageProductID))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/transact :: empty value for required parameters")
    @TmsLink("TECH-93366")
    public void V2testReservePurchaseAmountEmpty() throws InterruptedException {
        val jsonBody = setUpTransactV2DataWithPAEmpty(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessagePurchaseAmount))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/transact :: empty value for required parameters")
    @TmsLink("TECH-93366")
    public void V2testReserveChannelIDEmpty() throws InterruptedException {
        val jsonBody = setUpTransactV2DataWithCIDEmpty(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageChannelID))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/transact :: empty value for required parameters")
    @TmsLink("TECH-93366")
    public void V2testReserveChannelNameEmpty() throws InterruptedException {
        val jsonBody = setUpTransactV2DataWithCNEmpty(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageChannelName))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/transact :: empty value for required parameters")
    @TmsLink("TECH-93366")
    public void V2testReserveSourceIdentifierEmpty() throws InterruptedException {
        val jsonBody = setUpTransactV2DataWithSIEmpty(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageSourceIdentifier))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/transact :: empty value for required parameters")
    @TmsLink("TECH-93366")
    public void V2testReserveTargetIdentifierEmpty() throws InterruptedException {
        val jsonBody = setUpTransactV2DataWithCTIEmpty(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageTargetIdentifier))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    //V3

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/transact :: empty value for required parameters")
    @TmsLink("TECH-93365")
    public void V3testChannelSessionIDEmpty() throws InterruptedException {
        val jsonBody = setUpTransactV3DataWithCSIDEmpty(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageAlphaNumericCSID))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/transact :: empty value for required parameters")
    @TmsLink("TECH-93365")
    public void V3testTimeStampEmpty() throws InterruptedException {
        val jsonBody = setUpTransactV3DataWithTSEmpty(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageTimeStampNotNull))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/transact :: empty value for required parameters")
    @TmsLink("TECH-93365")
    public void V3testReserveFundasTxnRefEmpty() throws InterruptedException {
        val jsonBody = setUpTransactV3DataWithRFTREmpty(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageRFTRMandatory))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/transact :: empty value for required parameters")
    @TmsLink("TECH-93365")
    public void V3testReserveClientIDEmpty() throws InterruptedException {
        val jsonBody = setUpTransactV3DataWithRCIEmpty(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageServiceTUnavailable))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }


    @Test
    @Description("30100 :: payd-raas-gateway :: v3/transact :: empty value for required parameters")
    @TmsLink("TECH-93365")
    public void V3testReserveProductIDEmpty() throws InterruptedException {
        val jsonBody = setUpTransactV3DataWithPIDEmpty(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageProductID))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/transact :: empty value for required parameters")
    @TmsLink("TECH-93365")
    public void V3testReservePurchaseAmountEmpty() throws InterruptedException {
        val jsonBody = setUpTransactV3DataWithPAEmpty(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessagePurchaseAmount))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/transact :: empty value for required parameters")
    @TmsLink("TECH-93365")
    public void V3testReserveFeeAmountEmpty() throws InterruptedException {
        val jsonBody = setUpTransactV3DataWithFAEmpty(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_2055))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageFeeAmount))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/transact :: empty value for required parameters")
    @TmsLink("TECH-93365")
    public void V3testReserveChannelIDEmpty() throws InterruptedException {
        val jsonBody = setUpTransactV3DataWithCIDEmpty(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageChannelID))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }


    @Test
    @Description("30100 :: payd-raas-gateway :: v3/transact :: empty value for required parameters")
    @TmsLink("TECH-93365")
    public void V3testReserveChannelNameEmpty() throws InterruptedException {
        val jsonBody = setUpTransactV3DataWithCNEmpty(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageChannelName))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/transact :: empty value for required parameters")
    @TmsLink("TECH-93365")
    public void V3testReserveSourceIdentifierEmpty() throws InterruptedException {
        val jsonBody = setUpTransactV3DataWithSIEmpty(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageSourceIdentifier))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/transact :: empty value for required parameters")
    @TmsLink("TECH-93365")
    public void V3testReserveTargetIdentifierEmpty() throws InterruptedException {
        val jsonBody = setUpTransactV3DataWithCTIEmpty(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageTargetIdentifier))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    //V1 Empty

    @Test
    @Description("30100 :: payd-raas-gateway :: v1/transact :: empty value for required parameters")
    @TmsLink("TECH-93367")
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
    @TmsLink("TECH-93367")
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
    @TmsLink("TECH-93367")
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
    @TmsLink("TECH-93367")
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
    @TmsLink("TECH-93367")
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
