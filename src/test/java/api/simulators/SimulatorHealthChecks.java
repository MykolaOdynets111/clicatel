package api.simulators;

import api.clients.CustomerAccountValidationClient;
import api.clients.ReserveAndTransactClient;
import api.domains.reserve_and_transact.model.ReserveAndTransactResponse;
import api.enums.ChannelId;
import api.enums.Port;
import api.enums.Version;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Hashtable;
import java.util.Map;

import static api.clients.BulkLoaderClient.getCampaigns;
import static api.clients.CustomerAccountValidationClient.validateCustomerAccountV2;
import static api.clients.DistributedDistributionClient.getMessageDistributionTemplates;
import static api.clients.FundingSourceProxyClient.*;
import static api.clients.NotificationClient.Identifier_6;
import static api.clients.NotificationClient.getSMSStatusCallback;
import static api.clients.PaydFundingSourceConfigClient.getFundingSource;
import static api.clients.ProductLookupClient.GetVendorCtxProductById;
import static api.clients.ProductLookupClient.ProductAirtel_130;
import static api.clients.ReserveAndTransactClient.*;
import static api.clients.ReserveAndTransactDBChecksClient.settlementAmount;
import static api.clients.SimulatorsClient.*;
import static api.clients.SupportUiClient.getSupportUIRaasInteractions;
import static api.clients.TransactionLookupClient.*;
import static api.clients.UsersClient.getUser;
import static api.clients.VelocityClients.getVelocityRaasReload;
import static api.clients.VendorConfigClient.getVendor;
import static api.domains.customer_account_validation.repo.CustomerAccountValidationRequestRepo.setUpCustomerAccountDataV2;
import static api.domains.fundingSourceProxy.repo.fundingSourceProxyRepo.setUpConfigureFundingSourceV1Data;
import static api.domains.reserve_and_transact.repo.ReserveAndTransactRequestRepo.setUpReserveAndTransactV4Data;
import static api.domains.reserve_and_transact.repo.ReserveAndTransactRequestRepo.setUpReserveAndTransactV4DataAdditionalData;
import static api.domains.simulator.repo.SimulatorRequestRepo.setUPMTNProxyData;
import static api.enums.ChannelName.USSD;
import static api.enums.CurrencyCode.NGN;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;

public class SimulatorHealthChecks {

    @Test(groups = {"healthCheckTest"})
    @Description("30113 :: 3line-simulator :: GET /ping")
    @TmsLink("MKP-338")
    public void testPing3LineSimulator() {
        Ping3Line()
                .then().assertThat().statusCode(SC_OK);
    }

    @Test(groups = {"healthCheckTest"})
    @Description("32755-bank-simulators-v3 :: health-check")
    @TmsLink("MKP-1009")
    public void testPingRaasBankV3Simulator() {
        PingRaasV3Simulator()
                .then().assertThat().statusCode(SC_OK);
    }

    @Test(groups = {"healthCheckTest"})
    @Description("30117 :: airtel-proxy :: GET /ping")
    @TmsLink("MKP-1071")
    public void testPingAirTelProxySimulator() {
        PingAirTelProxySimulator()
                .then().assertThat().statusCode(SC_OK);
    }

    @Test
    @Description("32051-mwm-simulator :: health-check")
    @TmsLink("MKP-1025")
    public void testmwmTestResponse() {
        PingMWMSimulator(Identifier_18)
                .then().assertThat().statusCode(SC_OK);
    }
    @Test(groups = {"healthCheckTest"})
    @Description("30091-magtipon3lineng-rest :: GET \u200B/magtipon3lineng\u200B/ping :: happy path")
    @TmsLink("MKP-667")
    public void testMagtipon3LinengResponse() {
        Pingmagtipon3linengSimulator()
                .then().assertThat().statusCode(SC_OK);
    }

    @Test(groups = {"healthCheckTest"})
    @Description("30433-transaction-lookup :: health-check")
    @TmsLink("MKP-3128")
    public void testTransactionLookupResponse() throws InterruptedException {

        val jsonBody = setUpReserveAndTransactV4DataAdditionalData(ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundsReserved))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        Thread.sleep(10000);
        findInternalTransaction(Port.TRANSACTION_LOOKUP_SERVICE, Integer.parseInt(ReserveAndTransactClient.TestClient3), queryParams)
                .then().assertThat().statusCode(SC_OK);
    }

    @Test(groups = {"healthCheckTest"})
    @Description("32050-vendor-routing-service :: health-check")
    @TmsLink("MKP-3126")
    public void testVendorRoutingServiceHealth(){

        val body = setUpCustomerAccountDataV2(Integer.parseInt(ReserveAndTransactClient.TestClient3), Integer.parseInt(ReserveAndTransactClient.ProductAirtel_917), CustomerAccountValidationClient.Identifier_8, Integer.parseInt(ReserveAndTransactClient.PurchaseAmount10000));

        validateCustomerAccountV2(body,Port.CUSTOMER_ACCOUNT_VALIDATION_V2)
                .then().assertThat().statusCode(SC_OK);

    }
    @Test(groups = {"healthCheckTest"})
    @Description("30116-message-notifier :: health-check")
    @TmsLink("MKP-3123")
    public void testMessageNotifierResponse(){

        getSMSStatusCallback()
                .then().assertThat().statusCode(SC_ACCEPTED);
    }

    @Test(groups = {"healthCheckTest"})
    @Description("30101-payd-funding-source-proxy :: health-check")
    @TmsLink("MKP-3121")
    public void testPaydFundingSourceProxyResponse(){

        val jsonBody = setUpReserveAndTransactV4Data(ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, Identifier_6);
        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundsReserved))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        val body = setUpConfigureFundingSourceV1Data(PurchaseAmount1000,ReserveAndTransactClient.clientTxnRef,ReserveAndTransactClient.FeeAmount0,raasTxnRef,PurchaseAmount10000,ReserveFundsTxnRef2,responseCode0000,settlementAmount,fundingSourcProxyTimeStamp,mwmStatus,PurchaseAmount10000,FeeAmount0);

        PostFundingSourceConfigV1(body,TestClient3)
                .then().assertThat().statusCode(SC_OK);
    }

    @Test(groups = {"healthCheckTest"})
    @Description("32000-payd-product-lookup :: health-check")
    @TmsLink("MKP-2200")
    public void testProductLookupResponse(){

        Map<String, String> map = new Hashtable<>();
        map.put("id", ProductAirtel_130);
        GetVendorCtxProductById(map)
                .then().assertThat().statusCode(SC_OK);

    }

    @Test(groups = {"healthCheckTest"})
    @Description("31000-support-ui :: health-check")
    @TmsLink("MKP-523")
    public void testSupportUIResponse(){

        getSupportUIRaasInteractions()
                .then().assertThat().statusCode(SC_OK);

    }

    @Test(groups = {"healthCheckTest"})
    @Description("30926-distributed-distribution :: health-check")
    @TmsLink("MKP-1319")
    public void testDistributedDistributionResponse(){

        getMessageDistributionTemplates()
                .then().assertThat().statusCode(SC_OK);

    }

    @Test(groups = {"healthCheckTest"})
    @Description("30785-mtn-proxy :: health-check")
    @TmsLink("MKP-1612")
    public void testMTNProxyResponse(){

        val body = setUPMTNProxyData(ReserveAndTransactClient.TestClient3);
        PingMTNProxy(body)
                .then().assertThat().statusCode(SC_OK);

    }

    @Test(groups = {"healthCheckTest"})
    @Description("31995-vendor-config :: health-check")
    @TmsLink("MKP-2198")
    public void testVendorConfigResponse(){
        getVendor()
                .then().assertThat().statusCode(SC_OK);
    }

    @Test
    @Description("30444-clickatell-portal-transaction-lookup :: health-check")
    @TmsLink("MKP-1424")
    public void testClickatellPortalTransactionLookupHealth(){
        portalTransactionLookupHealthCheck()
                .then().assertThat().statusCode(SC_OK);
    }

    @Test(groups = {"healthCheckTest"})
    @Description("30141-payd-funding-source-config :: health-check")
    @TmsLink("MKP-1434")
    public void testPaydFundingSourceConfigHealth(){
        getFundingSource(ReserveAndTransactClient.TestClient3)
                .then().assertThat().statusCode(SC_OK);
    }

    @Test(groups = {"healthCheckTest"})
    @Description("30156-payd-velocity :: health-check")
    @TmsLink("MKP-1400")
    public void testPaydVelocityHealth(){

        getVelocityRaasReload()
                .then().assertThat().statusCode(SC_OK);
    }

    @Test(groups = {"healthCheckTest"})
    @Description("30065-profile-management :: health-check")
    @TmsLink("MKP-998")
    public void testProfileManagementHealth(){
        Map<String, String> map = new Hashtable<>();
        map.put("msisdn",Identifier_21);
        getUser(map)
                .then().assertThat().statusCode(SC_OK);
    }

    @Test
    @Description("30228-bulkloader :: health-check")
    @TmsLink("MKP-3671")
    public void testBulkLoaderHealth(){
        // Currently not working for UAT as service is not deployed there
        Map<String, String> map = new Hashtable<>();
        map.put("clientId", TestClient3);
        getCampaigns(map)
                .then().assertThat().statusCode(SC_OK);
    }
}

