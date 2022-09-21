package api.transact;

import api.clients.ReserveAndTransactClient;
import api.clients.TransactClient;
import api.domains.transact.model.TransactResponse;
import api.enums.*;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.hamcrest.Matchers;
import org.hamcrest.core.CombinableMatcher;
import org.testng.annotations.Test;

import static api.clients.TransactClient.executeTransact;
import static api.domains.transact.repo.TransactRequestRepo.*;
import static api.enums.ChannelName.USSD;
import static org.apache.http.HttpStatus.*;

public class TransactNullValueForRequiredParamTest {
    //V4 Transact NULL Cases
    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: null value for required parameters")
    @TmsLink("MKP-1000")
    public void testTransactV4WithChannelSessionIDNull() throws InterruptedException {
        val jsonBody = setUpTransactV4DataWithCSIDNull(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageNullCSID))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: null value for required parameters")
    @TmsLink("MKP-768")
    public void testTransactV4WithTimeStampNull() throws InterruptedException {
        val jsonBody = setUpTransactV4DataWithTSNull(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageTimeStampNotNull))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: null value for required parameters")
    @TmsLink("MKP-750")
    public void testTransactV4WithReserveFundsTxnRefNull() throws InterruptedException {
        val jsonBody = setUpTransactV4DataWithRFTRNull(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageRFTRMandatory))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: null value for required parameters")
    @TmsLink("MKP-875")
    public void testTransactV4WithReserveClientIDNull() throws InterruptedException {
        val jsonBody = setUpTransactV4DataWithCINull(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageServiceTUnavailable))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: null value for required parameters")
    @TmsLink("MKP-883")
    public void testTransactV4WithReserveFundingSourceIDNull() throws InterruptedException {
        val jsonBody = setUpTransactV4DataWithFSNull(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(TransactClient.ResponseCode_0001))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageFundingSrouce))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: null value for required parameters")
    @TmsLink("MKP-858")
    public void testTransactV4WithReserveProductIDNull() throws InterruptedException {
        val jsonBody = setUpTransactV4DataWithPIDNull(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageProductID))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: null value for required parameters")
    @TmsLink("MKP-832")
    public void testTransactV4WithReservePurchaseAmountNull() throws InterruptedException {
        val jsonBody = setUpTransactV4DataWithPANull(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessagePurchaseAmount))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: null value for required parameters")
    @TmsLink("MKP-747")
    public void testTransactV4WithReserveFeeAmountNull() throws InterruptedException {
        val jsonBody = setUpTransactV4DataWithFANull(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_2055))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageFeeAmount))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: null value for required parameters")
    @TmsLink("MKP-913")
    public void testTransactV4WithReserveCurrencyCodeNull() throws InterruptedException {
        val jsonBody = setUpTransactV4DataWithCCNull(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageCurrencyCode))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }


    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: null value for required parameters")
    @TmsLink("MKP-842")
    public void testTransactV4WithtReserveChannelIDNull() throws InterruptedException {
        val jsonBody = setUpTransactV4DataWithCIDnull(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageChannelID))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }


    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: null value for required parameters")
    @TmsLink("MKP-918")
    public void testTransactV4WithReserveChannelNameNull() throws InterruptedException {
        val jsonBody = setUpTransactV4DataWithCNNull(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN,null, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageChannelNameNotNull))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: null value for required parameters")
    @TmsLink("MKP-839")
    public void testTransactV4WithReserveSourceIdentifierNull() throws InterruptedException {
        val jsonBody = setUpTransactV4DataWithSINull(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageSourceIdentifierNotNull))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v4/transact :: null value for required parameters")
    @TmsLink("MKP-855")
    public void testTransactV4WithReserveTargetIdentifierNull() throws InterruptedException {
        val jsonBody = setUpTransactV4DataWithCTINull(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageTargetIdentifierNotNull))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }
    @Test
    @Description("POST /raas/v4/transact :: null value for the clientTxnRef")
    @TmsLink("MKP-903")
    public void testTransactV4WithReserveClientTxnRefNull() throws InterruptedException {
        val jsonBody = setUpTransactV4DataClientTxnRefMaxLimit(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917, null);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageProcessingRequest))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }
    @Test
    @Description("POST /raas/v4/transact :: null value for the authCode")
    @TmsLink("MKP-737")
    public void testTransactV4WithReserveAuthCodeNull() throws InterruptedException {
        val jsonBody = setUpTransactV4DataAuthCodeMaxLimit(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917, null);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageProcessingRequest))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }
    @Test
    @Description("POST /raas/v4/transact :: null value for the accountIdentifier")
    @TmsLink("MKP-787")
    public void testTransactV4WithReserveAccountIdentifierNull() throws InterruptedException {
        val jsonBody = setUpTransactV4DataWithAuthCodeNull(ReserveAndTransactClient.TestClient3, CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917, null);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageProcessingRequest))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    //V3 Transact Null Casses
    @Test
    @Description("30100 :: payd-raas-gateway :: v3/transact :: null value for required parameters")
    @TmsLink("MKP-1037")
    public void testTransactV3SuccessWithChannelSessionIDNull() throws InterruptedException {
        val jsonBody = SetupBodyForTransactV3WithChannelSessionIDNull(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917,null);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageNullCSID))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/transact :: null value for required parameters")
    @TmsLink("MKP-824")
    public void testTransactV3SuccessWitTimeStampIDNull() throws InterruptedException {
        val jsonBody = SetupBodyForTransactV3WitTimeStampIDNull(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917, null);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageTimeStampNotNull))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/transact :: null value for required parameters")
    @TmsLink("MKP-733")
    public void testTransactV3SuccessWithCreserveFundsTxnRefNull() throws InterruptedException {
        val jsonBody = SetupBodyForTransactV3WithreserveFundsTxnRefNull(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917, null);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageRFTRMandatory))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/transact :: null value for required parameters")
    @TmsLink("MKP-899")
    public void testTransactV3SuccessWithclientIdNull() throws InterruptedException {
        val jsonBody = SetupBodyForTransactV3WithclientIdNull(null, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageServiceTUnavailable))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }
    @Test
    @Description("30100 :: payd-raas-gateway :: v3/transact :: null value for required parameters")
    @TmsLink("MKP-853")
    public void testTransactV3SuccessWithproductIdNull() throws InterruptedException {
        val jsonBody = SetupBodyForTransactV3WithproductIdNull(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, null);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageProductID))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/transact :: null value for required parameters")
    @TmsLink("MKP-922")
    public void testTransactV3SuccessWithpurchaseAmountNull() throws InterruptedException {
        val jsonBody = SetupBodyForTransactV3WithpurchaseAmountNull(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917, null);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessagePurchaseAmount))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/transact :: null value for required parameters")
    @TmsLink("MKP-908")
    public void testTransactV3SuccessWithfeeAmountNull() throws InterruptedException {
        val jsonBody = SetupBodyForTransactV3WithfeeAmountNull(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917, null);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_2055))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageFeeAmount))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/transact :: null value for required parameters")
    @TmsLink("MKP-582")
    public void testTransactV3SuccessWithchannelIdisNull() throws InterruptedException {
        val jsonBody = SetupBodyForTransactV3WithchannelIdisNull(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, null, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageChannelID))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }
    @Test
    @Description("30100 :: payd-raas-gateway :: v3/transact :: null value for required parameters")
    @TmsLink("MKP-658")
    public void testTransactV3SuccessWithchannelNameNull() throws InterruptedException {
        val jsonBody = SetupBodyForTransactV3WithchannelNameNull(ReserveAndTransactClient.TestClient3, null, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageChannelNameNotNull))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v3/transact :: null value for required parameters")
    @TmsLink("MKP-710")
    public void testTransactV3SuccessWithsourceIdentifierNull() throws InterruptedException {
        val jsonBody = SetupBodyForTransactV3WithsourceIdentifierNull(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917, null);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageSourceIdentifierNotNull))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }
    @Test
    @Description("30100 :: payd-raas-gateway :: v3/transact :: null value for required parameters")
    @TmsLink("MKP-561")
    public void testTransactV3SuccessWithtargetIdentifierNull() throws InterruptedException {
        val jsonBody = SetupBodyForTransactV3WithtargetIdentifierNull(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917,null);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageTargetIdentifierNotNull))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }
    @Test
    @Description("POST /raas/v3/transact :: null value for the clientTxnRef")
    @TmsLink("MKP-693")
    public void testTransactV3SuccessWithClientTxnRefNull() throws InterruptedException {
        val jsonBody = setUpTransactV3DataClientTxnRefMaxLimit(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917,null);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageProcessingRequest))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }
    @Test
    @Description("POST /raas/v3/transact :: null value for the authCode")
    @TmsLink("MKP-585")
    public void testTransactV3SuccessWithAuthCodeNull() throws InterruptedException {
        val jsonBody = setUpTransactV3DataAuthCodeMaxLimit(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917,null);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageProcessingRequest))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }
    @Test
    @Description("POST /raas/v3/transact :: null value for the accountIdentifier")
    @TmsLink("MKP-532")
    public void testTransactV3Success() throws InterruptedException {
        val jsonBody = setUpTransactV3Data(ReserveAndTransactClient.TestClient3, ChannelName.INTERNET, ChannelId.INTERNET, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageProcessingRequest))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    // V2 Transact Null test cases

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/transact :: null value for required parameters")
    @TmsLink("MKP-977")
    public void testTransactV2WithChannelSessionIDNull() throws InterruptedException {
        val jsonBody = setUpTransactV2DataWithChannelSessionIDEmpty(ReserveAndTransactClient.TestClient3, ChannelName.USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, null);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageNullCSID))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/transact :: null value for required parameters")
    @TmsLink("MKP-702")
    public void testTransactV2WithTimestampNull() throws InterruptedException {
        val jsonBody = setUpTransactV2DataWithtimestampEmpty(ReserveAndTransactClient.TestClient3, ChannelName.USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917,null);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageTimeStampNotNull))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }
    @Test
    @Description("30100 :: payd-raas-gateway :: v2/transact :: null value for required parameters")
    @TmsLink("MKP-592")
    public void testTransactV2WithReserveFundsTxnRefNull() throws InterruptedException {
        val jsonBody = setUpTransactV2DataWithreserveFundsTxnRefEmpty(ReserveAndTransactClient.TestClient3, ChannelName.USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, null);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageRFTRMandatory))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }
    @Test
    @Description("30100 :: payd-raas-gateway :: v2/transact :: null value for required parameters")
    @TmsLink("MKP-550")
    public void testTransactV2WithClientIdNull() throws InterruptedException {
        val jsonBody = setUpTransactV2DataWithclientIdEmpty(null, ChannelName.USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageServiceTUnavailable))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }
    @Test
    @Description("30100 :: payd-raas-gateway :: v2/transact :: null value for required parameters")
    @TmsLink("MKP-526")
    public void testTransactV2WithProductIdNull() throws InterruptedException {
        val jsonBody = setUpTransactV2DataWithproductIdEmpty(ReserveAndTransactClient.TestClient3, ChannelName.USSD, ChannelId.USSD, null);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageProductID))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }
    @Test
    @Description("30100 :: payd-raas-gateway :: v2/transact :: null value for required parameters")
    @TmsLink("MKP-639")
    public void testTransactV2WithPurchaseAmountNull() throws InterruptedException {
        val jsonBody = setUpTransactV2DatapurchaseAmountEmpty(ReserveAndTransactClient.TestClient3, ChannelName.USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, null);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessagePurchaseAmount))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }
    @Test
    @Description("30100 :: payd-raas-gateway :: v2/transact :: null value for required parameters")
    @TmsLink("MKP-576")
    public void testTransactV2WithChannelIdisNull() throws InterruptedException {
        val jsonBody = setUpTransactV2DataWithchannelIdisEmpty(ReserveAndTransactClient.TestClient3, ChannelName.USSD, null, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageChannelID))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }
    @Test
    @Description("30100 :: payd-raas-gateway :: v2/transact :: null value for required parameters")
    @TmsLink("MKP-713")
    public void testTransactV2WithChannelNameNull() throws InterruptedException {
        val jsonBody = setUpTransactV2DataWithchannelNameEmpty(ReserveAndTransactClient.TestClient3, null, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageChannelNameNotNull))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v2/transact :: null value for required parameters (targetIdentifierEmpty)")
    @TmsLink("MKP-801")
    public void testTransactV2WithTargetIdentifierNull() throws InterruptedException {
        val jsonBody = setUpTransactV2DataWithTargetIdentifierEmpty(ReserveAndTransactClient.TestClient3, ChannelName.USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, null);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageTargetIdentifierNotNull))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }
    @Test
    @Description("30100 :: payd-raas-gateway :: v2/transact :: null value for required parameters (sourceIdentifierEmpty)")
    @TmsLink("MKP-952")
    public void testTransactV2WithSourceIdentifierNull() throws InterruptedException {
        val jsonBody = setUpTransactV2DataWithsourceIdentifierEmpty(ReserveAndTransactClient.TestClient3, ChannelName.USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, null);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", CombinableMatcher.either(Matchers.containsString(TransactClient.responseMessageSourceIdentifierNotNull)).or(Matchers.containsString(ReserveAndTransactClient.responseMessageSourceIdentifierMaxLimit)))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }
    @Test
    @Description("POST /raas/v2/transact :: null value for clientTxnRef")
    @TmsLink("MKP-616")
    public void testTransactV2WithClientTxnRefNull() throws InterruptedException {
        val jsonBody = setUpTransactV2DataClientTxnRefMaxLimit(ReserveAndTransactClient.TestClient3, ChannelName.USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, null);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageProcessingRequest))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }
    @Test
    @Description("POST /raas/v2/transact :: null value for authCode")
    @TmsLink("MKP-631")
    public void testTransactV2WithAuthCodeNull() throws InterruptedException {
        val jsonBody = setUpTransactV2DataAuthCodeIdMaxLimit(ReserveAndTransactClient.TestClient3, ChannelName.USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, null);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageProcessingRequest))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }
    @Test
    @Description("POST /raas/v2/transact :: null value for accountIdentifier")
    @TmsLink("MKP-656")
    public void testTransactV2WithAccIdentifierNull() throws InterruptedException {
        val jsonBody = setUpTransactV2DataAccountIdentifierMaxLimit(ReserveAndTransactClient.TestClient3, ChannelName.USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, null);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageProcessingRequest))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }
}

  /*  // V1 TRANSACT NULL CASES
    @Test
    @Description("30100 :: payd-raas-gateway :: v1/transact :: null value for required parameters")
    @TmsLink("TECH-93354")
    public void testTransactV1WithClientIDNull() throws InterruptedException {
        val jsonBody = setUpTransactV1DataWithClientIDNull(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageProcessingRequest))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v1/transact :: null value for required parameters")
    @TmsLink("TECH-93354")
    public void testTransactV1WithProductIDNull() throws InterruptedException {
        val jsonBody = setUpTransactV1DataWithProductIDNull(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageProcessingRequest))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v1/transact :: null value for required parameters")
    @TmsLink("TECH-93354")
    public void testTransactV1WithpurchaseAmountNull() throws InterruptedException {
        val jsonBody = setUpTransactV1DataWithpurchaseAmountNull(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageProcessingRequest))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v1/transact :: null value for required parameters")
    @TmsLink("TECH-93354")
    public void testTransactV1WithchannelIdisNull() throws InterruptedException {
        val jsonBody = setUpTransactV1DataWithchannelIdisNull(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageProcessingRequest))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }

    @Test
    @Description("30100 :: payd-raas-gateway :: v1/transact :: null value for required parameters")
    @TmsLink("TECH-93354")
    public void testTransactV1WithtargetIdentifierNull() throws InterruptedException {
        val jsonBody = setUpTransactV1DataWithtargetIdentifierNull(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageProcessingRequest))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }*/


