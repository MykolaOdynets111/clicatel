package api.reserve_and_transact;

import api.clients.ReserveAndTransactClient;
import api.domains.reserve_and_transact.model.ReserveAndTransactResponse;
import api.enums.ChannelId;
import api.enums.Port;
import api.enums.Version;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.hamcrest.Matchers;
import org.springframework.context.annotation.Description;
import org.testng.annotations.Test;

import java.util.Hashtable;
import java.util.Map;

import static api.clients.CTX.*;
import static api.clients.CTXSimulatorClient.*;
import static api.clients.ReserveAndTransactClient.*;
import static api.clients.SupportUiClient.getRaasFlow;
import static api.clients.TransactionLookupClient.findTransaction;
import static api.domains.reserve_and_transact.repo.ReserveAndTransactRequestRepo.setUpReserveAndTransactV4Data;
import static api.enums.ChannelName.USSD;
import static api.enums.CurrencyCode.NGN;
import static org.apache.http.HttpStatus.SC_OK;


public class ReserveAndTransactCombinationTestCases {

    @Test
    @Description("30100-payd-raas-gateway :: POST /v4/reserveAndTransact :: NonRetryableDecline scenario (vendor 100; MTN_NG)")
    @TmsLink("MKP-1435")
    public void testReserveAndTransactMTNNGNRDScenario() throws Exception{

        setSimulatorExpectedState(vendorMTNNGErrorServer);

        val jsonBody = setUpReserveAndTransactV4Data(ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_100, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundsReserved))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        Thread.sleep(10000);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, Integer.parseInt(ReserveAndTransactClient.TestClient3), queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString(ReserveAndTransactClient.Failed));


        getRaasFlow(Port.RAAS_FLOW, raasTxnRef)
                .then().assertThat().statusCode(SC_OK)
                .body("raas_request.raasTxnRef", Matchers.is(raasTxnRef))
                .body("raas_response.raasTxnRef",Matchers.is(raasTxnRef))
                .body("raas_response.responseCode",Matchers.is(ReserveAndTransactClient.responseCode0000))
                .body("reserve_fund_request.raasTxnRef",Matchers.is(raasTxnRef))
                .body("reserve_fund_response.raasTxnRef",Matchers.is(raasTxnRef))
                .body("reserve_fund_response.responseCode",Matchers.is(ReserveAndTransactClient.responseCode0000))
                .body("ctx_request.clientTransactionId", Matchers.not(raasTxnRef.concat(ReserveAndTransactClient.FirstTransactionCode)))
                .body("ctx_response.clientTransactionId", Matchers.not(raasTxnRef.concat(ReserveAndTransactClient.FirstTransactionCode)))
                .body("ctx_response[0].responseCode", Matchers.is(Integer.parseInt(ResponseCode_2228)))
                .body("ctx_lookup_request", Matchers.empty())
                .body("ctx_lookup_response", Matchers.empty())
                .body("transaction_result_request.raasTxnRef",Matchers.is(raasTxnRef))
                .body("transaction_result_request.responseCode", Matchers.is(ReserveAndTransactClient.responseCode2201))
                .body("transaction_result_response.raasTxnRef",Matchers.is(raasTxnRef))
                .body("transaction_result_response.responseCode", Matchers.is(ReserveAndTransactClient.responseCode202));

        setSimulatorExpectedState(vendorMTNNGSuccess);
    }

    @Test
    @Description("30100-payd-raas-gateway :: POST /v4/reserveAndTransact :: Pending To Success scenario (Vendor 100; MTN_NG)")
    @TmsLink("MKP-1433")
    public void testReserveAndTransactMTNNGPendingToSuccessScenario() throws Exception{

        setSimulatorExpectedState(vendorMTNNGException);

        val jsonBody = setUpReserveAndTransactV4Data(ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_100, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundsReserved))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, Integer.parseInt(ReserveAndTransactClient.TestClient3), queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString(TxnStatusPending));

        setSimulatorExpectedState(vendorMTNNGSuccess);

        Thread.sleep(60000);

        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, Integer.parseInt(ReserveAndTransactClient.TestClient3), queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString(Success));

        getRaasFlow(Port.RAAS_FLOW, raasTxnRef)
                .then().assertThat().statusCode(SC_OK)
                .body("raas_request.raasTxnRef", Matchers.is(raasTxnRef))
                .body("raas_response.raasTxnRef",Matchers.is(raasTxnRef))
                .body("raas_response.responseCode",Matchers.is(ReserveAndTransactClient.responseCode0000))
                .body("reserve_fund_request.raasTxnRef",Matchers.is(raasTxnRef))
                .body("reserve_fund_response.raasTxnRef",Matchers.is(raasTxnRef))
                .body("reserve_fund_response.responseCode",Matchers.is(ReserveAndTransactClient.responseCode0000))
                .body("ctx_request[0].clientTransactionId", Matchers.is(raasTxnRef.concat(ReserveAndTransactClient.StartTransactionCode)))
                .body("ctx_response[0].clientTransactionId", Matchers.is(raasTxnRef.concat(ReserveAndTransactClient.StartTransactionCode)))
                .body("ctx_response[0].responseCode", Matchers.is(Integer.parseInt(ResponseCode_2240)))
                .body("ctx_lookup_request[0].clientTransactionId", Matchers.is(raasTxnRef.concat(ReserveAndTransactClient.StartTransactionCode)))
                .body("ctx_lookup_request[1].clientTransactionId", Matchers.is(raasTxnRef.concat(ReserveAndTransactClient.StartTransactionCode)))
                .body("ctx_lookup_response[0].clientTransactionId", Matchers.is(raasTxnRef.concat(ReserveAndTransactClient.StartTransactionCode)))
                .body("ctx_lookup_response[1].clientTransactionId", Matchers.is(raasTxnRef.concat(ReserveAndTransactClient.StartTransactionCode)))
                .body("transaction_result_request.raasTxnRef",Matchers.is(raasTxnRef))
                .body("transaction_result_request.responseCode", Matchers.is(ReserveAndTransactClient.responseCode0000))
                .body("transaction_result_response.raasTxnRef",Matchers.is(raasTxnRef))
                .body("transaction_result_response.responseCode", Matchers.is(ReserveAndTransactClient.responseCode202));

    }

    @Test
    @Description("30100-payd-raas-gateway :: POST /v4/reserveAndTransact :: RetryableDecline to NonRetryableDecline scenario (Vendor 100; MTN_NG)")
    @TmsLink("MKP-1306")
    public void testReserveAndTransactMTNNGRDToNRDScenario() throws Exception{

        setSimulatorExpectedState(vendorMTNNGErrorINVORIGACC);

        val jsonBody = setUpReserveAndTransactV4Data(ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_100, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundsReserved))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        setSimulatorExpectedState(vendorMTNNGErrorServer);

        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        Thread.sleep(60000);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, Integer.parseInt(ReserveAndTransactClient.TestClient3), queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString(ReserveAndTransactClient.Failed));

        getRaasFlow(Port.RAAS_FLOW, raasTxnRef)
                .then().assertThat().statusCode(SC_OK)
                .body("raas_request.raasTxnRef", Matchers.is(raasTxnRef))
                .body("raas_response.raasTxnRef",Matchers.is(raasTxnRef))
                .body("raas_response.responseCode",Matchers.is(ReserveAndTransactClient.responseCode0000))
                .body("reserve_fund_request.raasTxnRef",Matchers.is(raasTxnRef))
                .body("reserve_fund_response.raasTxnRef",Matchers.is(raasTxnRef))
                .body("reserve_fund_response.responseCode",Matchers.is(ReserveAndTransactClient.responseCode0000))
                .body("ctx_request[0].clientTransactionId", Matchers.is(raasTxnRef.concat(ReserveAndTransactClient.FirstTransactionCode)))
                .body("ctx_request[1].clientTransactionId", Matchers.is(raasTxnRef.concat(ReserveAndTransactClient.StartTransactionCode)))
                .body("ctx_response[0].responseCode", Matchers.is(Integer.parseInt(ResponseCode_2228)))
                .body("ctx_response[0].clientTransactionId", Matchers.is(raasTxnRef.concat(ReserveAndTransactClient.FirstTransactionCode)))
                .body("ctx_response[1].clientTransactionId", Matchers.is(raasTxnRef.concat(ReserveAndTransactClient.StartTransactionCode)))
                .body("ctx_response[1].responseCode", Matchers.is(Integer.parseInt(ResponseCode_2201)))
                .body("ctx_lookup_request", Matchers.empty())
                .body("ctx_lookup_response", Matchers.empty())
                .body("transaction_result_request.raasTxnRef",Matchers.is(raasTxnRef))
                .body("transaction_result_request.responseCode", Matchers.is(ReserveAndTransactClient.responseCode2201))
                .body("transaction_result_response.raasTxnRef",Matchers.is(raasTxnRef))
                .body("transaction_result_response.responseCode", Matchers.is(ReserveAndTransactClient.responseCode202));

        setSimulatorExpectedState(vendorMTNNGSuccess);
    }

    @Test
    @Description("30100-payd-raas-gateway :: POST /v4/reserveAndTransact :: Pending To NonRetryableDecline scenario (Vendor 100; MTN_NG)")
    @TmsLink("MKP-1348")
    public void testReserveAndTransactMTNNGPendingToNRDScenario() throws Exception{

        setSimulatorExpectedState(vendorMTNNGException);

        val jsonBody = setUpReserveAndTransactV4Data(ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_100, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundsReserved))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        setSimulatorExpectedState(vendorMTNNGErrorServer);

        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        Thread.sleep(60000);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, Integer.parseInt(ReserveAndTransactClient.TestClient3), queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString(ReserveAndTransactClient.Failed));

        getRaasFlow(Port.RAAS_FLOW, raasTxnRef)
                .then().assertThat().statusCode(SC_OK)
                .body("raas_request.raasTxnRef", Matchers.is(raasTxnRef))
                .body("raas_response.raasTxnRef",Matchers.is(raasTxnRef))
                .body("raas_response.responseCode",Matchers.is(ReserveAndTransactClient.responseCode0000))
                .body("reserve_fund_request.raasTxnRef",Matchers.is(raasTxnRef))
                .body("reserve_fund_response.raasTxnRef",Matchers.is(raasTxnRef))
                .body("reserve_fund_response.responseCode",Matchers.is(ReserveAndTransactClient.responseCode0000))
                .body("ctx_request[0].clientTransactionId", Matchers.is(raasTxnRef.concat(ReserveAndTransactClient.StartTransactionCode)))
                .body("ctx_response[0].responseCode", Matchers.is(Integer.parseInt(ResponseCode_2240)))
                .body("ctx_response[0].clientTransactionId", Matchers.is(raasTxnRef.concat(ReserveAndTransactClient.StartTransactionCode)))
                .body("ctx_lookup_request[0].clientTransactionId", Matchers.is(raasTxnRef.concat(ReserveAndTransactClient.StartTransactionCode)))
                .body("ctx_lookup_request[1].clientTransactionId", Matchers.is(raasTxnRef.concat(ReserveAndTransactClient.StartTransactionCode)))
                .body("ctx_lookup_response[0].clientTransactionId", Matchers.is(raasTxnRef.concat(ReserveAndTransactClient.StartTransactionCode)))
                .body("ctx_lookup_response[1].clientTransactionId", Matchers.is(raasTxnRef.concat(ReserveAndTransactClient.StartTransactionCode)))
                .body("ctx_lookup_response[0].responseCode", Matchers.is(Integer.parseInt(ResponseCode_2228)))
                .body("ctx_lookup_response[1].responseCode", Matchers.is(Integer.parseInt(ResponseCode_2240)))
                .body("transaction_result_request.raasTxnRef",Matchers.is(raasTxnRef))
                .body("transaction_result_request.responseCode", Matchers.is(ReserveAndTransactClient.responseCode2201))
                .body("transaction_result_response.raasTxnRef",Matchers.is(raasTxnRef))
                .body("transaction_result_response.responseCode", Matchers.is(ReserveAndTransactClient.responseCode202));


        setSimulatorExpectedState(vendorMTNNGSuccess);
    }

    @Test
    @Description("30100-payd-raas-gateway :: POST /v4/reserveAndTransact :: RetryableDecline to Success scenario (Vendor 100; MTN_NG)")
    @TmsLink("MKP-1383")
    public void testReserveAndTransactMTNNGRDToSuccessScenario() throws Exception{

        setSimulatorExpectedState(vendorMTNNGErrorINVORIGACC);

        val jsonBody = setUpReserveAndTransactV4Data(ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_100, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundsReserved))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        setSimulatorExpectedState(vendorMTNNGSuccess);

        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        Thread.sleep(60000);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, Integer.parseInt(ReserveAndTransactClient.TestClient3), queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString(Success));

        getRaasFlow(Port.RAAS_FLOW, raasTxnRef)
                .then().assertThat().statusCode(SC_OK)
                .body("raas_request.raasTxnRef", Matchers.is(raasTxnRef))
                .body("raas_response.raasTxnRef",Matchers.is(raasTxnRef))
                .body("raas_response.responseCode",Matchers.is(ReserveAndTransactClient.responseCode0000))
                .body("reserve_fund_request.raasTxnRef",Matchers.is(raasTxnRef))
                .body("reserve_fund_response.raasTxnRef",Matchers.is(raasTxnRef))
                .body("reserve_fund_response.responseCode",Matchers.is(ReserveAndTransactClient.responseCode0000))
                .body("ctx_request[0].clientTransactionId", Matchers.is(raasTxnRef.concat(ReserveAndTransactClient.FirstTransactionCode)))
                .body("ctx_request[1].clientTransactionId", Matchers.is(raasTxnRef.concat(ReserveAndTransactClient.StartTransactionCode)))
                .body("ctx_response[0].clientTransactionId", Matchers.is(raasTxnRef.concat(ReserveAndTransactClient.FirstTransactionCode)))
                .body("ctx_response[1].clientTransactionId", Matchers.is(raasTxnRef.concat(ReserveAndTransactClient.StartTransactionCode)))
                .body("ctx_response[0].responseCode", Matchers.is(Integer.parseInt(responseCode0)))
                .body("ctx_response[1].responseCode", Matchers.is(Integer.parseInt(ResponseCode_2201)))
                .body("ctx_lookup_request", Matchers.empty())
                .body("ctx_lookup_response", Matchers.empty())
                .body("transaction_result_request.raasTxnRef",Matchers.is(raasTxnRef))
                .body("transaction_result_request.responseCode", Matchers.is(ReserveAndTransactClient.responseCode0000))
                .body("transaction_result_response.raasTxnRef",Matchers.is(raasTxnRef))
                .body("transaction_result_response.responseCode", Matchers.is(ReserveAndTransactClient.responseCode202));
    }

    @Test
    @Description("30100-payd-raas-gateway :: POST /v4/reserveAndTransact :: Pending To RetryableDecline To Success scenario (Vendor 100; MTN_NG)")
    @TmsLink("MKP-1362")
    public void testReserveAndTransactMTNNGPendingToRDToSuccessScenario() throws Exception{

        setSimulatorExpectedState(vendorMTNNGException);

        val jsonBody = setUpReserveAndTransactV4Data(ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_100, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundsReserved))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        setSimulatorExpectedState(vendorMTNNGErrorINVORIGACC);
        Thread.sleep(60000);
        setSimulatorExpectedState(vendorMTNNGSuccess);

        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        Thread.sleep(60000);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, Integer.parseInt(ReserveAndTransactClient.TestClient3), queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString(Success));

        getRaasFlow(Port.RAAS_FLOW, raasTxnRef)
                .then().assertThat().statusCode(SC_OK)
                .body("raas_request.raasTxnRef", Matchers.is(raasTxnRef))
                .body("raas_response.raasTxnRef",Matchers.is(raasTxnRef))
                .body("raas_response.responseCode",Matchers.is(ReserveAndTransactClient.responseCode0000))
                .body("reserve_fund_request.raasTxnRef",Matchers.is(raasTxnRef))
                .body("reserve_fund_response.raasTxnRef",Matchers.is(raasTxnRef))
                .body("reserve_fund_response.responseCode",Matchers.is(ReserveAndTransactClient.responseCode0000))
                .body("ctx_request[0].clientTransactionId", Matchers.is(raasTxnRef.concat(ReserveAndTransactClient.SecondTransactionCode)))
                .body("ctx_request[1].clientTransactionId", Matchers.is(raasTxnRef.concat(ReserveAndTransactClient.FirstTransactionCode)))
                .body("ctx_request[2].clientTransactionId", Matchers.is(raasTxnRef.concat(ReserveAndTransactClient.StartTransactionCode)))
                .body("ctx_response[0].clientTransactionId", Matchers.is(raasTxnRef.concat(ReserveAndTransactClient.SecondTransactionCode)))
                .body("ctx_response[1].clientTransactionId", Matchers.is(raasTxnRef.concat(ReserveAndTransactClient.FirstTransactionCode)))
                .body("ctx_request[2].clientTransactionId", Matchers.is(raasTxnRef.concat(ReserveAndTransactClient.StartTransactionCode)))
                .body("ctx_response[0].responseCode", Matchers.is(Integer.parseInt(responseCode0)))
                .body("ctx_response[1].responseCode", Matchers.is(Integer.parseInt(ResponseCode_2201)))
                .body("ctx_response[2].responseCode", Matchers.is(Integer.parseInt(ResponseCode_2240)))
                .body("ctx_lookup_request[0].clientTransactionId", Matchers.is(raasTxnRef.concat(ReserveAndTransactClient.StartTransactionCode)))
                .body("ctx_lookup_request[1].clientTransactionId", Matchers.is(raasTxnRef.concat(ReserveAndTransactClient.StartTransactionCode)))
                .body("ctx_lookup_response[0].clientTransactionId", Matchers.is(raasTxnRef.concat(ReserveAndTransactClient.StartTransactionCode)))
                .body("ctx_lookup_response[1].clientTransactionId", Matchers.is(raasTxnRef.concat(ReserveAndTransactClient.StartTransactionCode)))
                .body("ctx_lookup_response[0].responseCode", Matchers.is(Integer.parseInt(ResponseCode_2201)))
                .body("ctx_lookup_response[1].responseCode", Matchers.is(Integer.parseInt(ResponseCode_2240)))
                .body("transaction_result_request.raasTxnRef",Matchers.is(raasTxnRef))
                .body("transaction_result_request.responseCode", Matchers.is(ReserveAndTransactClient.responseCode0000))
                .body("transaction_result_response.raasTxnRef",Matchers.is(raasTxnRef))
                .body("transaction_result_response.responseCode", Matchers.is(ReserveAndTransactClient.responseCode202));
    }


    @Test
    @Description("30100-payd-raas-gateway :: POST /v4/reserveAndTransact :: RetryableDecline To Pending To Success scenario (vendor 100; MTN_NG)")
    @TmsLink("MKP-1296")
    public void testReserveAndTransactMTNNGRDToPendingToSuccessScenario() throws Exception{

        setSimulatorExpectedState(vendorMTNNGErrorINVORIGACC);

        val jsonBody = setUpReserveAndTransactV4Data(ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_100, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundsReserved))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        setSimulatorExpectedState(vendorMTNNGException);

        Thread.sleep(60000);

        setSimulatorExpectedState(vendorMTNNGSuccess);

        Thread.sleep(60000);

        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, Integer.parseInt(ReserveAndTransactClient.TestClient3), queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString(Success));

        getRaasFlow(Port.RAAS_FLOW, raasTxnRef)
                .then().assertThat().statusCode(SC_OK)
                .body("raas_request.raasTxnRef", Matchers.is(raasTxnRef))
                .body("raas_response.raasTxnRef",Matchers.is(raasTxnRef))
                .body("raas_response.responseCode",Matchers.is(ReserveAndTransactClient.responseCode0000))
                .body("reserve_fund_request.raasTxnRef",Matchers.is(raasTxnRef))
                .body("reserve_fund_response.raasTxnRef",Matchers.is(raasTxnRef))
                .body("reserve_fund_response.responseCode",Matchers.is(ReserveAndTransactClient.responseCode0000))
                .body("ctx_request[0].clientTransactionId", Matchers.is(raasTxnRef.concat(ReserveAndTransactClient.FirstTransactionCode)))
                .body("ctx_request[1].clientTransactionId", Matchers.is(raasTxnRef.concat(ReserveAndTransactClient.StartTransactionCode)))
                .body("ctx_response[0].clientTransactionId", Matchers.is(raasTxnRef.concat(ReserveAndTransactClient.FirstTransactionCode)))
                .body("ctx_response[1].clientTransactionId", Matchers.is(raasTxnRef.concat(ReserveAndTransactClient.StartTransactionCode)))
                .body("ctx_response[0].responseCode", Matchers.is(Integer.parseInt(ResponseCode_2240)))
                .body("ctx_response[1].responseCode", Matchers.is(Integer.parseInt(ResponseCode_2201)))
                .body("ctx_lookup_request[0].clientTransactionId", Matchers.is(raasTxnRef.concat(ReserveAndTransactClient.FirstTransactionCode)))
                .body("ctx_lookup_request[1].clientTransactionId", Matchers.is(raasTxnRef.concat(ReserveAndTransactClient.FirstTransactionCode)))
                .body("ctx_lookup_response[0].clientTransactionId", Matchers.is(raasTxnRef.concat(ReserveAndTransactClient.FirstTransactionCode)))
                .body("ctx_lookup_response[1].clientTransactionId", Matchers.is(raasTxnRef.concat(ReserveAndTransactClient.FirstTransactionCode)))
                .body("ctx_lookup_response[0].responseCode", Matchers.is(Integer.parseInt(responseCode0)))
                .body("ctx_lookup_response[1].responseCode", Matchers.is(Integer.parseInt(ResponseCode_2240)))
                .body("transaction_result_request.raasTxnRef",Matchers.is(raasTxnRef))
                .body("transaction_result_request.responseCode", Matchers.is(ReserveAndTransactClient.responseCode0000))
                .body("transaction_result_response.raasTxnRef",Matchers.is(raasTxnRef))
                .body("transaction_result_response.responseCode", Matchers.is(ReserveAndTransactClient.responseCode202));

    }
}
