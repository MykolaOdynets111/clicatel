/**
 * TestSuite: TransactV4 Regression test suite.
 * Includes all end to end api tests for Transact V4
 * Author: Juan-Claude Botha
 */

package API.dotCoreTests;

import api.testUtilities.testConfig;
import com.jcraft.jsch.JSchException;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import api.requestLibary.CORE.coreTransactV4POJO;

import api.testUtilities.dataBuilders.testDataFactory;
import api.testUtilities.sqlDataAccessLayer.sqlDataAccess;

import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

import api.testUtilities.propertyConfigWrapper.configWrapper;
import util.Listeners.allureApiTestListener;
import api.testUtilities.Simulators.startSimulator;

@Listeners(allureApiTestListener.class)
public class regression_Raas_Transact_V4 extends testConfig {

    // Create properties object in order to inject environment specific variables upon build
    Properties properties = configWrapper.loadPropertiesFile("config.properties");

    startSimulator startSim = new startSimulator();

    // Data staging for use in test
    @DataProvider(name = "transactV4SuccessTestcases", parallel = false)
    public Object[] transactV4SuccessTestcases() throws IOException, ParseException { //expectedRaasStatus

        return new String[][]{

                {testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGAdhocPurchaseSuccessTest","accountIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGAdhocPurchaseSuccessTest","purchaseAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGAdhocPurchaseSuccessTest","channelId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGAdhocPurchaseSuccessTest","channelName"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGAdhocPurchaseSuccessTest","channelSessionId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGAdhocPurchaseSuccessTest","clientId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGAdhocPurchaseSuccessTest","clientTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGAdhocPurchaseSuccessTest","productId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGAdhocPurchaseSuccessTest","sourceIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGAdhocPurchaseSuccessTest","targetIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGAdhocPurchaseSuccessTest","timestamp"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGAdhocPurchaseSuccessTest","reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGAdhocPurchaseSuccessTest","feeAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGAdhocPurchaseSuccessTest","currencyCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGAdhocPurchaseSuccessTest","fundingSourceId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGAdhocPurchaseSuccessTest","expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGAdhocPurchaseSuccessTest","expectedMessage"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGAdhocPurchaseSuccessTest","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGAdhocPurchaseSuccessTest","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGAdhocPurchaseSuccessTest","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGAdhocPurchaseSuccessTest","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGAdhocPurchaseSuccessTest","expectedRaasStatus"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGAdhocPurchaseSuccessTest","simulatorScenario"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGAdhocPurchaseSuccessTest","simulatorResetState")},

                {testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloPurchaseSuccessTest","accountIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloPurchaseSuccessTest","purchaseAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloPurchaseSuccessTest","channelId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloPurchaseSuccessTest","channelName"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloPurchaseSuccessTest","channelSessionId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloPurchaseSuccessTest","clientId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloPurchaseSuccessTest","clientTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloPurchaseSuccessTest","productId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloPurchaseSuccessTest","sourceIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloPurchaseSuccessTest","targetIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloPurchaseSuccessTest","timestamp"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloPurchaseSuccessTest","reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloPurchaseSuccessTest","feeAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloPurchaseSuccessTest","currencyCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloPurchaseSuccessTest","fundingSourceId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloPurchaseSuccessTest","expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloPurchaseSuccessTest","expectedMessage"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloPurchaseSuccessTest","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloPurchaseSuccessTest","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloPurchaseSuccessTest","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloPurchaseSuccessTest","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloPurchaseSuccessTest","expectedRaasStatus"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloPurchaseSuccessTest","simulatorScenario"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloPurchaseSuccessTest","simulatorResetState")},

                {testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","EtisalatPurchaseSuccessTest","accountIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","EtisalatPurchaseSuccessTest","purchaseAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","EtisalatPurchaseSuccessTest","channelId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","EtisalatPurchaseSuccessTest","channelName"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","EtisalatPurchaseSuccessTest","channelSessionId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","EtisalatPurchaseSuccessTest","clientId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","EtisalatPurchaseSuccessTest","clientTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","EtisalatPurchaseSuccessTest","productId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","EtisalatPurchaseSuccessTest","sourceIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","EtisalatPurchaseSuccessTest","targetIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","EtisalatPurchaseSuccessTest","timestamp"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","EtisalatPurchaseSuccessTest","reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","EtisalatPurchaseSuccessTest","feeAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","EtisalatPurchaseSuccessTest","currencyCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","EtisalatPurchaseSuccessTest","fundingSourceId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","EtisalatPurchaseSuccessTest","expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","EtisalatPurchaseSuccessTest","expectedMessage"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","EtisalatPurchaseSuccessTest","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","EtisalatPurchaseSuccessTest","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","EtisalatPurchaseSuccessTest","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","EtisalatPurchaseSuccessTest","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","EtisalatPurchaseSuccessTest","expectedRaasStatus"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","EtisalatPurchaseSuccessTest","simulatorScenario"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","EtisalatPurchaseSuccessTest","simulatorResetState")},

                {testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","AirtelPurchaseSuccessTest","accountIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","AirtelPurchaseSuccessTest","purchaseAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","AirtelPurchaseSuccessTest","channelId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","AirtelPurchaseSuccessTest","channelName"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","AirtelPurchaseSuccessTest","channelSessionId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","AirtelPurchaseSuccessTest","clientId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","AirtelPurchaseSuccessTest","clientTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","AirtelPurchaseSuccessTest","productId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","AirtelPurchaseSuccessTest","sourceIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","AirtelPurchaseSuccessTest","targetIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","AirtelPurchaseSuccessTest","timestamp"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","AirtelPurchaseSuccessTest","reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","AirtelPurchaseSuccessTest","feeAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","AirtelPurchaseSuccessTest","currencyCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","AirtelPurchaseSuccessTest","fundingSourceId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","AirtelPurchaseSuccessTest","expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","AirtelPurchaseSuccessTest","expectedMessage"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","AirtelPurchaseSuccessTest","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","AirtelPurchaseSuccessTest","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","AirtelPurchaseSuccessTest","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","AirtelPurchaseSuccessTest","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","AirtelPurchaseSuccessTest","expectedRaasStatus"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","AirtelPurchaseSuccessTest","simulatorScenario"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","AirtelPurchaseSuccessTest","simulatorResetState")},


        };
    }


    @Step("1. POST vendor sim success transact V4 call")
    @Test(dataProvider = "transactV4SuccessTestcases", priority = 0)
    public void TransactV4SuccessTests(String accountIdentifier,
                                String purchaseAmount,
                                String channelId,
                                String channelName,
                                String channelSessionId,
                                String clientId,
                                String clientTxnRef,
                                String productId,
                                String sourceIdentifier,
                                String targetIdentifier,
                                String timeStamp,
                                String reserveFundsTxnRef,
                                String feeAmount,
                                String currencyCode,
                                String fundingSourceId,
                                String expectedRaasResponseCode,
                                String expectedMessage,
                                String expectedHTTPResponseCode,
                                String expectedRaasResultRequestResponseCode,
                                String expectedRaasResultResponseResponseCode,
                                String expectedCTXTransactionResponseCode,
                                String expectedRaasStatus,
                                String simulatorScenario,
                                String simulatorResetState) throws IOException, InterruptedException, JSchException {

        startSim.SimulatorScenario(simulatorScenario);
        Thread.sleep(5000);

        // Financial Terms Calculate GET method call
        Response finTermsCalculateResponse =
                given(CORE_getEndPoints_FinancialTermsCalculate)
                .param("clientId",clientId)
                .param("productId", productId)
                .param("purchaseAmount", purchaseAmount)
                .when()
                .get()
                .then()
                .extract()
                .response();

        // Create transactV4 payload object - contains transactV4 request body
        coreTransactV4POJO TransactV4payload = new coreTransactV4POJO(accountIdentifier,
                purchaseAmount,
                channelId,
                channelName,
                channelSessionId,
                clientId,
                clientTxnRef,
                productId,
                sourceIdentifier,
                targetIdentifier,
                timeStamp,
                reserveFundsTxnRef,
                feeAmount,
                currencyCode,
                fundingSourceId);

        // Create transactV4 response body object - contains api response data for use in assertions or other calls
        Response TransactV4response =
        given(CORE_getEndPoints_TransactV4)
        .contentType(ContentType.JSON)
        .body(TransactV4payload)
        .when()
        .post()
        .then()
        .extract()
        .response();

        System.out.println(TransactV4response.toString());

        // Assertions

        // Finance Terms Calculate response assertions
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "");
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "null");
        Assert.assertEquals(finTermsCalculateResponse.path("clientId").toString(), clientId);
        Assert.assertEquals(finTermsCalculateResponse.path("productId").toString(), productId);
        Assert.assertEquals(finTermsCalculateResponse.path("purchaseAmount").toString(), purchaseAmount);

        // Transact V4 response assertions - purchase
        Assert.assertEquals(TransactV4response.path("responseCode"), expectedRaasResponseCode);
        Assert.assertEquals(TransactV4response.path("responseMessage"), expectedMessage);
        Assert.assertNotNull(TransactV4response.path("raasTxnRef"));
        Assert.assertEquals(TransactV4response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // raas db assertions
        //Transaction_log
        Thread.sleep(10000);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV4response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "vendor_share_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_request_created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_request_created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_response_created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'","currency_code"), currencyCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "funding_source_id"), fundingSourceId);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Raas_Request
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", TransactV4response.path("raasTxnRef")), TransactV4response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "api_call"), "transact-v4");

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", TransactV4response.path("raasTxnRef")), TransactV4response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "event_type"));

        // CTX DB assertions
        Thread.sleep(5000);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionId"));
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "clientStan"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "originatingService"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "purchaseAmount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionState"), "C");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionType"), "P");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        // Transaction_result_request
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV4response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // Transaction_result_response
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV4response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), "202");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

    }


    // Data staging for use in test
    @DataProvider(name = "transactV4VendorSimNegativeTestcases", parallel = false)
    public Object[] transactV4VendorSimNegativeTestcases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGVendorTimeoutNegativeTest","accountIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGVendorTimeoutNegativeTest","purchaseAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGVendorTimeoutNegativeTest","channelId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGVendorTimeoutNegativeTest","channelName"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGVendorTimeoutNegativeTest","channelSessionId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGVendorTimeoutNegativeTest","clientId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGVendorTimeoutNegativeTest","clientTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGVendorTimeoutNegativeTest","productId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGVendorTimeoutNegativeTest","sourceIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGVendorTimeoutNegativeTest","targetIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGVendorTimeoutNegativeTest","timestamp"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGVendorTimeoutNegativeTest","reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGVendorTimeoutNegativeTest","feeAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGVendorTimeoutNegativeTest","currencyCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGVendorTimeoutNegativeTest","fundingSourceId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGVendorTimeoutNegativeTest","expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGVendorTimeoutNegativeTest","expectedMessage"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGVendorTimeoutNegativeTest","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGVendorTimeoutNegativeTest","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGVendorTimeoutNegativeTest","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGVendorTimeoutNegativeTest","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGVendorTimeoutNegativeTest","simulatorScenario"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGVendorTimeoutNegativeTest","simulatorResetState")},

                {testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGErrorInvalidMSISDNNegativeTest","accountIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGErrorInvalidMSISDNNegativeTest","purchaseAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGErrorInvalidMSISDNNegativeTest","channelId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGErrorInvalidMSISDNNegativeTest","channelName"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGErrorInvalidMSISDNNegativeTest","channelSessionId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGErrorInvalidMSISDNNegativeTest","clientId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGErrorInvalidMSISDNNegativeTest","clientTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGErrorInvalidMSISDNNegativeTest","productId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGErrorInvalidMSISDNNegativeTest","sourceIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGErrorInvalidMSISDNNegativeTest","targetIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGErrorInvalidMSISDNNegativeTest","timestamp"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGErrorInvalidMSISDNNegativeTest","reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGErrorInvalidMSISDNNegativeTest","feeAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGErrorInvalidMSISDNNegativeTest","currencyCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGErrorInvalidMSISDNNegativeTest","fundingSourceId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGErrorInvalidMSISDNNegativeTest","expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGErrorInvalidMSISDNNegativeTest","expectedMessage"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGErrorInvalidMSISDNNegativeTest","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGErrorInvalidMSISDNNegativeTest","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGErrorInvalidMSISDNNegativeTest","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGErrorInvalidMSISDNNegativeTest","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGErrorInvalidMSISDNNegativeTest","simulatorScenario"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNGErrorInvalidMSISDNNegativeTest","simulatorResetState")},

               /* {testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATVendorTimeoutNegativeTest","accountIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATVendorTimeoutNegativeTest","purchaseAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATVendorTimeoutNegativeTest","channelId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATVendorTimeoutNegativeTest","channelName"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATVendorTimeoutNegativeTest","channelSessionId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATVendorTimeoutNegativeTest","clientId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATVendorTimeoutNegativeTest","clientTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATVendorTimeoutNegativeTest","productId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATVendorTimeoutNegativeTest","sourceIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATVendorTimeoutNegativeTest","targetIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATVendorTimeoutNegativeTest","timestamp"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATVendorTimeoutNegativeTest","reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATVendorTimeoutNegativeTest","feeAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATVendorTimeoutNegativeTest","currencyCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATVendorTimeoutNegativeTest","fundingSourceId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATVendorTimeoutNegativeTest","expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATVendorTimeoutNegativeTest","expectedMessage"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATVendorTimeoutNegativeTest","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATVendorTimeoutNegativeTest","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATVendorTimeoutNegativeTest","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATVendorTimeoutNegativeTest","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATVendorTimeoutNegativeTest","simulatorScenario"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATVendorTimeoutNegativeTest","simulatorResetState")}, */

              /*  {testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloVendorTimeoutNegativeTest","accountIdentifier"),
                testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloVendorTimeoutNegativeTest","purchaseAmount"),
                testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloVendorTimeoutNegativeTest","channelId"),
                testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloVendorTimeoutNegativeTest","channelName"),
                testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloVendorTimeoutNegativeTest","channelSessionId"),
                testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloVendorTimeoutNegativeTest","clientId"),
                testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloVendorTimeoutNegativeTest","clientTxnRef"),
                testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloVendorTimeoutNegativeTest","productId"),
                testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloVendorTimeoutNegativeTest","sourceIdentifier"),
                testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloVendorTimeoutNegativeTest","targetIdentifier"),
                testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloVendorTimeoutNegativeTest","timestamp"),
                testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloVendorTimeoutNegativeTest","reserveFundsTxnRef"),
                testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloVendorTimeoutNegativeTest","feeAmount"),
                testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloVendorTimeoutNegativeTest","currencyCode"),
                testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloVendorTimeoutNegativeTest","fundingSourceId"),
                testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloVendorTimeoutNegativeTest","expectedRaasResponseCode"),
                testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloVendorTimeoutNegativeTest","expectedMessage"),
                testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloVendorTimeoutNegativeTest","expectedHTTPResponseCode"),
                testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloVendorTimeoutNegativeTest","expectedRaasResultRequestResponseCode"),
                testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloVendorTimeoutNegativeTest","expectedRaasResultResponseResponseCode"),
                testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloVendorTimeoutNegativeTest","expectedCTXTransactionResponseCode"),
                testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloVendorTimeoutNegativeTest","simulatorScenario"),
                testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","GloVendorTimeoutNegativeTest","simulatorResetState")}, */

              /*  {testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATErrorInvalidMSISDNNegativeTest","accountIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATErrorInvalidMSISDNNegativeTest","purchaseAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATErrorInvalidMSISDNNegativeTest","channelId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATErrorInvalidMSISDNNegativeTest","channelName"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATErrorInvalidMSISDNNegativeTest","channelSessionId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATErrorInvalidMSISDNNegativeTest","clientId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATErrorInvalidMSISDNNegativeTest","clientTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATErrorInvalidMSISDNNegativeTest","productId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATErrorInvalidMSISDNNegativeTest","sourceIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATErrorInvalidMSISDNNegativeTest","targetIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATErrorInvalidMSISDNNegativeTest","timestamp"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATErrorInvalidMSISDNNegativeTest","reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATErrorInvalidMSISDNNegativeTest","feeAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATErrorInvalidMSISDNNegativeTest","currencyCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATErrorInvalidMSISDNNegativeTest","fundingSourceId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATErrorInvalidMSISDNNegativeTest","expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATErrorInvalidMSISDNNegativeTest","expectedMessage"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATErrorInvalidMSISDNNegativeTest","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATErrorInvalidMSISDNNegativeTest","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATErrorInvalidMSISDNNegativeTest","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATErrorInvalidMSISDNNegativeTest","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATErrorInvalidMSISDNNegativeTest","simulatorScenario"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","ETISALATErrorInvalidMSISDNNegativeTest","simulatorResetState")}, */


        };
    }


    @Step("2. POST Vendor Simulator, once off fail against sim failure: Negative testing transact V4 call")
    @Test(dataProvider = "transactV4VendorSimNegativeTestcases", priority = 1)
    public void transactV4VendorSimNegativeTests(String accountIdentifier,
                                String purchaseAmount,
                                String channelId,
                                String channelName,
                                String channelSessionId,
                                String clientId,
                                String clientTxnRef,
                                String productId,
                                String sourceIdentifier,
                                String targetIdentifier,
                                String timeStamp,
                                String reserveFundsTxnRef,
                                String feeAmount,
                                String currencyCode,
                                String fundingSourceId,
                                String expectedRaasResponseCode,
                                String expectedMessage,
                                String expectedHTTPResponseCode,
                                String expectedRaasResultRequestResponseCode,
                                String expectedRaasResultResponseResponseCode,
                                String expectedCTXTransactionResponseCode,
                                String simulatorScenario,
                                String simulatorResetState) throws IOException, InterruptedException, JSchException {

        startSim.SimulatorScenario(simulatorScenario);
        Thread.sleep(5000);

        // Financial Terms Calculate GET method call
        Response finTermsCalculateResponse =
                given(CORE_getEndPoints_FinancialTermsCalculate)
                        .param("clientId",clientId)
                        .param("productId", productId)
                        .param("purchaseAmount", purchaseAmount)
                        .when()
                        .get()
                        .then()
                        .extract()
                        .response();

        // Create transactV4 payload object - contains transactV4 request body
        coreTransactV4POJO TransactV4payload = new coreTransactV4POJO(accountIdentifier,
                purchaseAmount,
                channelId,
                channelName,
                channelSessionId,
                clientId,
                clientTxnRef,
                productId,
                sourceIdentifier,
                targetIdentifier,
                timeStamp,
                reserveFundsTxnRef,
                feeAmount,
                currencyCode,
                fundingSourceId);

        // Create transactV4 response body object - contains api response data for use in assertions or other calls
        Response TransactV4response =
                given(CORE_getEndPoints_TransactV4)
                        .contentType(ContentType.JSON)
                        .body(TransactV4payload)
                        .when()
                        .post()
                        .then()
                        .extract()
                        .response();

        System.out.println(TransactV4response.toString());

        // Assertions

        // Finance Terms Calculate response assertions
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "");
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "null");
        Assert.assertEquals(finTermsCalculateResponse.path("clientId").toString(), clientId);
        Assert.assertEquals(finTermsCalculateResponse.path("productId").toString(), productId);
        Assert.assertEquals(finTermsCalculateResponse.path("purchaseAmount").toString(), purchaseAmount);

        // Transact V4 response assertions - purchase
        Assert.assertEquals(TransactV4response.path("responseCode"), expectedRaasResponseCode);
        Assert.assertEquals(TransactV4response.path("responseMessage"), expectedMessage);
        Assert.assertNotNull(TransactV4response.path("raasTxnRef"));
        Assert.assertEquals(TransactV4response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // CTX DB assertions
        Thread.sleep(5000);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionId"));
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "clientStan"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "originatingService"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "purchaseAmount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionState"), "C");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionType"), "P");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        // raas db assertions
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.transaction_log","raas_txn_ref", "=", TransactV4response.path("raasTxnRef")), TransactV4response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", TransactV4response.path("raasTxnRef")), TransactV4response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", TransactV4response.path("raasTxnRef")), TransactV4response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);

        startSim.SimulatorScenario(simulatorResetState);
    }

    // Data staging for use in test
    @DataProvider(name = "transactV4NullFieldNegativeTestCases", parallel = false)
    public Object[] transactV4NullFieldNegativeTestCases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","reserveFundsTxnRefNullNegativeTest","accountIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","reserveFundsTxnRefNullNegativeTest","purchaseAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","reserveFundsTxnRefNullNegativeTest","channelId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","reserveFundsTxnRefNullNegativeTest","channelName"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","reserveFundsTxnRefNullNegativeTest","channelSessionId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","reserveFundsTxnRefNullNegativeTest","clientId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","reserveFundsTxnRefNullNegativeTest","clientTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","reserveFundsTxnRefNullNegativeTest","productId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","reserveFundsTxnRefNullNegativeTest","sourceIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","reserveFundsTxnRefNullNegativeTest","targetIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","reserveFundsTxnRefNullNegativeTest","timestamp"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","reserveFundsTxnRefNullNegativeTest","reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","reserveFundsTxnRefNullNegativeTest","feeAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","reserveFundsTxnRefNullNegativeTest","currencyCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","reserveFundsTxnRefNullNegativeTest","fundingSourceId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","reserveFundsTxnRefNullNegativeTest","expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","reserveFundsTxnRefNullNegativeTest","expectedMessage"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","reserveFundsTxnRefNullNegativeTest","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","reserveFundsTxnRefNullNegativeTest","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","reserveFundsTxnRefNullNegativeTest","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","reserveFundsTxnRefNullNegativeTest","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","reserveFundsTxnRefNullNegativeTest","expectedRaasStatus"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","reserveFundsTxnRefNullNegativeTest","simulatorScenario"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","reserveFundsTxnRefNullNegativeTest","simulatorResetState")},

                {testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","purchaseAmountNullNegativeTest","accountIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","purchaseAmountNullNegativeTest","purchaseAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","purchaseAmountNullNegativeTest","channelId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","purchaseAmountNullNegativeTest","channelName"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","purchaseAmountNullNegativeTest","channelSessionId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","purchaseAmountNullNegativeTest","clientId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","purchaseAmountNullNegativeTest","clientTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","purchaseAmountNullNegativeTest","productId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","purchaseAmountNullNegativeTest","sourceIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","purchaseAmountNullNegativeTest","targetIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","purchaseAmountNullNegativeTest","timestamp"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","purchaseAmountNullNegativeTest","reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","purchaseAmountNullNegativeTest","feeAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","purchaseAmountNullNegativeTest","currencyCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","purchaseAmountNullNegativeTest","fundingSourceId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","purchaseAmountNullNegativeTest","expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","purchaseAmountNullNegativeTest","expectedMessage"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","purchaseAmountNullNegativeTest","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","purchaseAmountNullNegativeTest","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","purchaseAmountNullNegativeTest","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","purchaseAmountNullNegativeTest","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","purchaseAmountNullNegativeTest","expectedRaasStatus"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","purchaseAmountNullNegativeTest","simulatorScenario"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","purchaseAmountNullNegativeTest","simulatorResetState")},

                {testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelIdNullNegativeTest","accountIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelIdNullNegativeTest","purchaseAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelIdNullNegativeTest","channelId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelIdNullNegativeTest","channelName"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelIdNullNegativeTest","channelSessionId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelIdNullNegativeTest","clientId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelIdNullNegativeTest","clientTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelIdNullNegativeTest","productId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelIdNullNegativeTest","sourceIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelIdNullNegativeTest","targetIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelIdNullNegativeTest","timestamp"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelIdNullNegativeTest","reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelIdNullNegativeTest","feeAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelIdNullNegativeTest","currencyCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelIdNullNegativeTest","fundingSourceId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelIdNullNegativeTest","expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelIdNullNegativeTest","expectedMessage"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelIdNullNegativeTest","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelIdNullNegativeTest","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelIdNullNegativeTest","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelIdNullNegativeTest","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelIdNullNegativeTest","expectedRaasStatus"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelIdNullNegativeTest","simulatorScenario"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelIdNullNegativeTest","simulatorResetState")},

                /* {testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelNameNullNegativeTest","accountIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelNameNullNegativeTest","purchaseAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelNameNullNegativeTest","channelId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelNameNullNegativeTest","channelName"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelNameNullNegativeTest","channelSessionId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelNameNullNegativeTest","clientId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelNameNullNegativeTest","clientTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelNameNullNegativeTest","productId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelNameNullNegativeTest","sourceIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelNameNullNegativeTest","targetIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelNameNullNegativeTest","timestamp"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelNameNullNegativeTest","reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelNameNullNegativeTest","feeAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelNameNullNegativeTest","currencyCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelNameNullNegativeTest","fundingSourceId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelNameNullNegativeTest","expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelNameNullNegativeTest","expectedMessage"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelNameNullNegativeTest","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelNameNullNegativeTest","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelNameNullNegativeTest","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelNameNullNegativeTest","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelNameNullNegativeTest","expectedRaasStatus"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelNameNullNegativeTest","simulatorScenario"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelNameNullNegativeTest","simulatorResetState")}, */

                {testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelSessionIdNullNegativeTest","accountIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelSessionIdNullNegativeTest","purchaseAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelSessionIdNullNegativeTest","channelId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelSessionIdNullNegativeTest","channelName"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelSessionIdNullNegativeTest","channelSessionId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelSessionIdNullNegativeTest","clientId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelSessionIdNullNegativeTest","clientTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelSessionIdNullNegativeTest","productId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelSessionIdNullNegativeTest","sourceIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelSessionIdNullNegativeTest","targetIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelSessionIdNullNegativeTest","timestamp"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelSessionIdNullNegativeTest","reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelSessionIdNullNegativeTest","feeAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelSessionIdNullNegativeTest","currencyCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelSessionIdNullNegativeTest","fundingSourceId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelSessionIdNullNegativeTest","expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelSessionIdNullNegativeTest","expectedMessage"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelSessionIdNullNegativeTest","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelSessionIdNullNegativeTest","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelSessionIdNullNegativeTest","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelSessionIdNullNegativeTest","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelSessionIdNullNegativeTest","expectedRaasStatus"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelSessionIdNullNegativeTest","simulatorScenario"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelSessionIdNullNegativeTest","simulatorResetState")},

                {testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientIdNullNegativeTest","accountIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientIdNullNegativeTest","purchaseAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientIdNullNegativeTest","channelId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientIdNullNegativeTest","channelName"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientIdNullNegativeTest","channelSessionId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientIdNullNegativeTest","clientId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientIdNullNegativeTest","clientTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientIdNullNegativeTest","productId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientIdNullNegativeTest","sourceIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientIdNullNegativeTest","targetIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientIdNullNegativeTest","timestamp"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientIdNullNegativeTest","reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientIdNullNegativeTest","feeAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientIdNullNegativeTest","currencyCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientIdNullNegativeTest","fundingSourceId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientIdNullNegativeTest","expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientIdNullNegativeTest","expectedMessage"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientIdNullNegativeTest","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientIdNullNegativeTest","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientIdNullNegativeTest","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientIdNullNegativeTest","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientIdNullNegativeTest","expectedRaasStatus"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientIdNullNegativeTest","simulatorScenario"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientIdNullNegativeTest","simulatorResetState")},

                {testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientTxnRefNullNegativeTest","accountIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientTxnRefNullNegativeTest","purchaseAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientTxnRefNullNegativeTest","channelId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientTxnRefNullNegativeTest","channelName"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientTxnRefNullNegativeTest","channelSessionId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientTxnRefNullNegativeTest","clientId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientTxnRefNullNegativeTest","clientTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientTxnRefNullNegativeTest","productId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientTxnRefNullNegativeTest","sourceIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientTxnRefNullNegativeTest","targetIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientTxnRefNullNegativeTest","timestamp"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientTxnRefNullNegativeTest","reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientTxnRefNullNegativeTest","feeAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientTxnRefNullNegativeTest","currencyCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientTxnRefNullNegativeTest","fundingSourceId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientTxnRefNullNegativeTest","expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientTxnRefNullNegativeTest","expectedMessage"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientTxnRefNullNegativeTest","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientTxnRefNullNegativeTest","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientTxnRefNullNegativeTest","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientTxnRefNullNegativeTest","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientTxnRefNullNegativeTest","expectedRaasStatus"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientTxnRefNullNegativeTest","simulatorScenario"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","clientTxnRefNullNegativeTest","simulatorResetState")},

                {testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","productIdNullNegativeTest","accountIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","productIdNullNegativeTest","purchaseAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","productIdNullNegativeTest","channelId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","productIdNullNegativeTest","channelName"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","productIdNullNegativeTest","channelSessionId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","productIdNullNegativeTest","clientId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","productIdNullNegativeTest","clientTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","productIdNullNegativeTest","productId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","productIdNullNegativeTest","sourceIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","productIdNullNegativeTest","targetIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","productIdNullNegativeTest","timestamp"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","productIdNullNegativeTest","reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","productIdNullNegativeTest","feeAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","productIdNullNegativeTest","currencyCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","productIdNullNegativeTest","fundingSourceId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","productIdNullNegativeTest","expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","productIdNullNegativeTest","expectedMessage"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","productIdNullNegativeTest","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","productIdNullNegativeTest","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","productIdNullNegativeTest","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","productIdNullNegativeTest","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","productIdNullNegativeTest","expectedRaasStatus"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","productIdNullNegativeTest","simulatorScenario"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","productIdNullNegativeTest","simulatorResetState")},

                /* {testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","sourceIdentifierNullNegativeTest","accountIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","sourceIdentifierNullNegativeTest","purchaseAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","sourceIdentifierNullNegativeTest","channelId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","sourceIdentifierNullNegativeTest","channelName"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","sourceIdentifierNullNegativeTest","channelSessionId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","sourceIdentifierNullNegativeTest","clientId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","sourceIdentifierNullNegativeTest","clientTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","sourceIdentifierNullNegativeTest","productId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","sourceIdentifierNullNegativeTest","sourceIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","sourceIdentifierNullNegativeTest","targetIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","sourceIdentifierNullNegativeTest","timestamp"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","sourceIdentifierNullNegativeTest","reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","sourceIdentifierNullNegativeTest","feeAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","sourceIdentifierNullNegativeTest","currencyCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","sourceIdentifierNullNegativeTest","fundingSourceId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","sourceIdentifierNullNegativeTest","expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","sourceIdentifierNullNegativeTest","expectedMessage"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","sourceIdentifierNullNegativeTest","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","sourceIdentifierNullNegativeTest","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","sourceIdentifierNullNegativeTest","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","sourceIdentifierNullNegativeTest","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","sourceIdentifierNullNegativeTest","expectedRaasStatus"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","sourceIdentifierNullNegativeTest","simulatorScenario"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","sourceIdentifierNullNegativeTest","simulatorResetState")}, */

                /* {testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","accountIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","purchaseAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","channelId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","channelName"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","channelSessionId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","clientId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","clientTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","productId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","sourceIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","targetIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","timestamp"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","feeAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","currencyCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","fundingSourceId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","expectedMessage"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","expectedRaasStatus"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","simulatorScenario"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","simulatorResetState")}, */

                /*{testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","accountIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","purchaseAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","channelId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","channelName"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","channelSessionId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","clientId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","clientTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","productId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","sourceIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","targetIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","timestamp"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","feeAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","currencyCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","fundingSourceId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","expectedMessage"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","expectedRaasStatus"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","simulatorScenario"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","simulatorResetState")}, */

                {testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","timestampNullNegativeTest","accountIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","timestampNullNegativeTest","purchaseAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","timestampNullNegativeTest","channelId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","timestampNullNegativeTest","channelName"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","timestampNullNegativeTest","channelSessionId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","timestampNullNegativeTest","clientId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","timestampNullNegativeTest","clientTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","timestampNullNegativeTest","productId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","timestampNullNegativeTest","sourceIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","timestampNullNegativeTest","targetIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","timestampNullNegativeTest","timestamp"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","timestampNullNegativeTest","reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","timestampNullNegativeTest","feeAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","timestampNullNegativeTest","currencyCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","timestampNullNegativeTest","fundingSourceId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","timestampNullNegativeTest","expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","timestampNullNegativeTest","expectedMessage"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","timestampNullNegativeTest","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","timestampNullNegativeTest","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","timestampNullNegativeTest","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","timestampNullNegativeTest","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","timestampNullNegativeTest","expectedRaasStatus"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","timestampNullNegativeTest","simulatorScenario"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","timestampNullNegativeTest","simulatorResetState")},

        };
    }


    @Step("3. POST transact V4 null field test cases, testing all fields with negative values")
    @Test(dataProvider = "transactV4NullFieldNegativeTestCases", priority = 2)
    public void transactV4NullFieldNegativeTestCase(String accountIdentifier,
                                       String purchaseAmount,
                                       String channelId,
                                       String channelName,
                                       String channelSessionId,
                                       String clientId,
                                       String clientTxnRef,
                                       String productId,
                                       String sourceIdentifier,
                                       String targetIdentifier,
                                       String timeStamp,
                                       String reserveFundsTxnRef,
                                       String feeAmount,
                                       String currencyCode,
                                       String fundingSourceId,
                                       String expectedRaasResponseCode,
                                       String expectedMessage,
                                       String expectedHTTPResponseCode,
                                       String expectedRaasResultRequestResponseCode,
                                       String expectedRaasResultResponseResponseCode,
                                       String expectedCTXTransactionResponseCode,
                                       String expectedRaasStatus,
                                       String simulatorScenario,
                                       String simulatorResetState) throws IOException, InterruptedException, JSchException {


        // Create transactV4 payload object - contains transactV4 request body
        coreTransactV4POJO TransactV4payload = new coreTransactV4POJO(accountIdentifier,
                purchaseAmount,
                channelId,
                channelName,
                channelSessionId,
                clientId,
                clientTxnRef,
                productId,
                sourceIdentifier,
                targetIdentifier,
                timeStamp,
                reserveFundsTxnRef,
                feeAmount,
                currencyCode,
                fundingSourceId);

        // Create transactV4 response body object - contains api response data for use in assertions or other calls
        Response TransactV4response =
                given(CORE_getEndPoints_TransactV4)
                        .contentType(ContentType.JSON)
                        .body(TransactV4payload)
                        .when()
                        .post()
                        .then()
                        .extract()
                        .response();

        System.out.println(TransactV4response.toString());

        // Assertions

        // Transact V4 response assertions - purchase
        Assert.assertEquals(TransactV4response.path("responseCode"), expectedRaasResponseCode);
        Assert.assertEquals(TransactV4response.path("responseMessage"), expectedMessage);
        Assert.assertEquals(TransactV4response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // raas db assertions
        //Transaction_log -- add more details if required..


    }

    // Data staging for use in test
    @DataProvider(name = "transactV4RetryableDeclineToOKTestcases", parallel = false)
    public Object[] transactV4RetryableDeclineToOKTestcases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","accountIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","purchaseAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","channelId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","channelName"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","channelSessionId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","clientId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","clientTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","productId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","sourceIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","targetIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","timestamp"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","feeAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","currencyCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","fundingSourceId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","expectedMessage"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","simulatorScenario"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","simulatorResetState")},


        };
    }


    @Step("4. POST Vendor Simulator, retryable decline to ok scenarios")
    @Test(dataProvider = "transactV4RetryableDeclineToOKTestcases", priority = 3)
    public void transactV4VendorRetryableDeclineToOKTests(String accountIdentifier,
                                                 String purchaseAmount,
                                                 String channelId,
                                                 String channelName,
                                                 String channelSessionId,
                                                 String clientId,
                                                 String clientTxnRef,
                                                 String productId,
                                                 String sourceIdentifier,
                                                 String targetIdentifier,
                                                 String timeStamp,
                                                 String reserveFundsTxnRef,
                                                 String feeAmount,
                                                 String currencyCode,
                                                 String fundingSourceId,
                                                 String expectedRaasResponseCode,
                                                 String expectedMessage,
                                                 String expectedHTTPResponseCode,
                                                 String expectedRaasResultRequestResponseCode,
                                                 String expectedRaasResultResponseResponseCode,
                                                 String expectedCTXTransactionResponseCode,
                                                 String simulatorScenario,
                                                 String simulatorResetState) throws IOException, InterruptedException, JSchException {

        startSim.SimulatorScenario(simulatorScenario);
        Thread.sleep(5000);

        // Financial Terms Calculate GET method call
        Response finTermsCalculateResponse =
                given(CORE_getEndPoints_FinancialTermsCalculate)
                        .param("clientId",clientId)
                        .param("productId", productId)
                        .param("purchaseAmount", purchaseAmount)
                        .when()
                        .get()
                        .then()
                        .extract()
                        .response();

        // Create transactV4 payload object - contains transactV4 request body
        coreTransactV4POJO TransactV4payload = new coreTransactV4POJO(accountIdentifier,
                purchaseAmount,
                channelId,
                channelName,
                channelSessionId,
                clientId,
                clientTxnRef,
                productId,
                sourceIdentifier,
                targetIdentifier,
                timeStamp,
                reserveFundsTxnRef,
                feeAmount,
                currencyCode,
                fundingSourceId);

        // Create transactV4 response body object - contains api response data for use in assertions or other calls
        Response TransactV4response =
                given(CORE_getEndPoints_TransactV4)
                        .contentType(ContentType.JSON)
                        .body(TransactV4payload)
                        .when()
                        .post()
                        .then()
                        .extract()
                        .response();

        // Reset simulator to OK (success) for vendor to test ok scenario
        startSim.SimulatorScenario(simulatorResetState);
        Thread.sleep(15000);
        // Assertions

        // Finance Terms Calculate response assertions
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "");
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "null");
        Assert.assertEquals(finTermsCalculateResponse.path("clientId").toString(), clientId);
        Assert.assertEquals(finTermsCalculateResponse.path("productId").toString(), productId);
        Assert.assertEquals(finTermsCalculateResponse.path("purchaseAmount").toString(), purchaseAmount);

        // Transact V4 response assertions - purchase
        Assert.assertEquals(TransactV4response.path("responseCode"), expectedRaasResponseCode);
        Assert.assertEquals(TransactV4response.path("responseMessage"), expectedMessage);
        Assert.assertNotNull(TransactV4response.path("raasTxnRef"));
        Assert.assertEquals(TransactV4response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // raas db assertions
        //Transaction_log
        Thread.sleep(5000);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV4response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "vendor_share_amount"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_request_created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_request_created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_response_created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'","currency_code"), currencyCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "funding_source_id"), fundingSourceId);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Raas_Request
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", TransactV4response.path("raasTxnRef")), TransactV4response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "api_call"), "transact-v4");

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", TransactV4response.path("raasTxnRef")), TransactV4response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "event_type"));

        // CTX DB assertions
        Thread.sleep(5000);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0001'", "transactionResponseCode"), expectedCTXTransactionResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0001'", "transactionId"));
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0001'", "clientStan"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0001'", "originId"), sourceIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0001'", "originatingService"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0001'", "purchaseAmount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0001'", "transactionState"), "C");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0001'", "transactionType"), "P");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0001'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0001'", "product_id"), productId);

        // Transaction_result_request
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV4response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // Transaction_result_response
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV4response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), "202");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
    }


    // Data staging for use in test
    @DataProvider(name = "transactV4PendingToOKTestcases", parallel = false)
    public Object[] transactV4PendingToOKTestcases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","accountIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","purchaseAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","channelId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","channelName"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","channelSessionId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","clientId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","clientTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","productId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","sourceIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","targetIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","timestamp"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","feeAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","currencyCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","fundingSourceId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","expectedMessage"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","simulatorScenario"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Retryable_Decline_To_OK","simulatorResetState")},


        };
    }


    @Step("4. POST Vendor Simulator, Pending to ok scenarios")
    @Test(dataProvider = "transactV4PendingToOKTestcases", priority = 3)
    public void transactV4VendorPendingToOKTests(String accountIdentifier,
                                                          String purchaseAmount,
                                                          String channelId,
                                                          String channelName,
                                                          String channelSessionId,
                                                          String clientId,
                                                          String clientTxnRef,
                                                          String productId,
                                                          String sourceIdentifier,
                                                          String targetIdentifier,
                                                          String timeStamp,
                                                          String reserveFundsTxnRef,
                                                          String feeAmount,
                                                          String currencyCode,
                                                          String fundingSourceId,
                                                          String expectedRaasResponseCode,
                                                          String expectedMessage,
                                                          String expectedHTTPResponseCode,
                                                          String expectedRaasResultRequestResponseCode,
                                                          String expectedRaasResultResponseResponseCode,
                                                          String expectedCTXTransactionResponseCode,
                                                          String simulatorScenario,
                                                          String simulatorResetState) throws IOException, InterruptedException, JSchException {

        startSim.SimulatorScenario(simulatorScenario);
        Thread.sleep(5000);

        // Financial Terms Calculate GET method call
        Response finTermsCalculateResponse =
                given(CORE_getEndPoints_FinancialTermsCalculate)
                        .param("clientId",clientId)
                        .param("productId", productId)
                        .param("purchaseAmount", purchaseAmount)
                        .when()
                        .get()
                        .then()
                        .extract()
                        .response();

        // Create transactV4 payload object - contains transactV4 request body
        coreTransactV4POJO TransactV4payload = new coreTransactV4POJO(accountIdentifier,
                purchaseAmount,
                channelId,
                channelName,
                channelSessionId,
                clientId,
                clientTxnRef,
                productId,
                sourceIdentifier,
                targetIdentifier,
                timeStamp,
                reserveFundsTxnRef,
                feeAmount,
                currencyCode,
                fundingSourceId);

        // Create transactV4 response body object - contains api response data for use in assertions or other calls
        Response TransactV4response =
                given(CORE_getEndPoints_TransactV4)
                        .contentType(ContentType.JSON)
                        .body(TransactV4payload)
                        .when()
                        .post()
                        .then()
                        .extract()
                        .response();

        // Reset simulator to OK (success) for vendor to test ok scenario
        startSim.SimulatorScenario(simulatorResetState);
        Thread.sleep(15000);
        // Assertions

        // Finance Terms Calculate response assertions
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "");
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "null");
        Assert.assertEquals(finTermsCalculateResponse.path("clientId").toString(), clientId);
        Assert.assertEquals(finTermsCalculateResponse.path("productId").toString(), productId);
        Assert.assertEquals(finTermsCalculateResponse.path("purchaseAmount").toString(), purchaseAmount);

        // Transact V4 response assertions - purchase
        Assert.assertEquals(TransactV4response.path("responseCode"), expectedRaasResponseCode);
        Assert.assertEquals(TransactV4response.path("responseMessage"), expectedMessage);
        Assert.assertNotNull(TransactV4response.path("raasTxnRef"));
        Assert.assertEquals(TransactV4response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // raas db assertions
        //Transaction_log
        Thread.sleep(5000);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV4response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "vendor_share_amount"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_request_created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_request_created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_response_created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'","currency_code"), currencyCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "funding_source_id"), fundingSourceId);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Raas_Request
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", TransactV4response.path("raasTxnRef")), TransactV4response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "api_call"), "transact-v4");

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", TransactV4response.path("raasTxnRef")), TransactV4response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "event_type"));

        // CTX DB assertions
        Thread.sleep(5000);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0001'", "transactionResponseCode"), expectedCTXTransactionResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0001'", "transactionId"));
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0001'", "clientStan"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0001'", "originId"), sourceIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0001'", "originatingService"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0001'", "purchaseAmount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0001'", "transactionState"), "C");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0001'", "transactionType"), "P");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0001'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0001'", "product_id"), productId);

        // Transaction_result_request
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV4response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // Transaction_result_response
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV4response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), "202");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
    }

    // Data staging for use in test
    @DataProvider(name = "transactV4PendingToROKTestcases", parallel = false)
    public Object[] transactV4PendingToROKTestcases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_OK","accountIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_OK","purchaseAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_OK","channelId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_OK","channelName"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_OK","channelSessionId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_OK","clientId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_OK","clientTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_OK","productId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_OK","sourceIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_OK","targetIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_OK","timestamp"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_OK","reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_OK","feeAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_OK","currencyCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_OK","fundingSourceId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_OK","expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_OK","expectedMessage"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_OK","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_OK","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_OK","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_OK","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_OK","simulatorScenario"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_OK","simulatorResetState")},


        };
    }


    @Step("5. POST Vendor Simulator, Pending to retryable ok scenarios")
    @Test(dataProvider = "transactV4PendingToROKTestcases", priority = 4)
    public void transactV4VendorPendingToROKTests(String accountIdentifier,
                                                 String purchaseAmount,
                                                 String channelId,
                                                 String channelName,
                                                 String channelSessionId,
                                                 String clientId,
                                                 String clientTxnRef,
                                                 String productId,
                                                 String sourceIdentifier,
                                                 String targetIdentifier,
                                                 String timeStamp,
                                                 String reserveFundsTxnRef,
                                                 String feeAmount,
                                                 String currencyCode,
                                                 String fundingSourceId,
                                                 String expectedRaasResponseCode,
                                                 String expectedMessage,
                                                 String expectedHTTPResponseCode,
                                                 String expectedRaasResultRequestResponseCode,
                                                 String expectedRaasResultResponseResponseCode,
                                                 String expectedCTXTransactionResponseCode,
                                                 String simulatorScenario,
                                                 String simulatorResetState) throws IOException, InterruptedException, JSchException {

        startSim.SimulatorScenario(simulatorScenario);
        Thread.sleep(5000);

        // Financial Terms Calculate GET method call
        Response finTermsCalculateResponse =
                given(CORE_getEndPoints_FinancialTermsCalculate)
                        .param("clientId",clientId)
                        .param("productId", productId)
                        .param("purchaseAmount", purchaseAmount)
                        .when()
                        .get()
                        .then()
                        .extract()
                        .response();

        // Create transactV4 payload object - contains transactV4 request body
        coreTransactV4POJO TransactV4payload = new coreTransactV4POJO(accountIdentifier,
                purchaseAmount,
                channelId,
                channelName,
                channelSessionId,
                clientId,
                clientTxnRef,
                productId,
                sourceIdentifier,
                targetIdentifier,
                timeStamp,
                reserveFundsTxnRef,
                feeAmount,
                currencyCode,
                fundingSourceId);

        // Create transactV4 response body object - contains api response data for use in assertions or other calls
        Response TransactV4response =
                given(CORE_getEndPoints_TransactV4)
                        .contentType(ContentType.JSON)
                        .body(TransactV4payload)
                        .when()
                        .post()
                        .then()
                        .extract()
                        .response();

        // Reset simulator to OK (success) for vendor to test ok scenario
        startSim.SimulatorScenario(simulatorResetState);
        Thread.sleep(15000);

        // Assertions

        // Finance Terms Calculate response assertions
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "");
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "null");
        Assert.assertEquals(finTermsCalculateResponse.path("clientId").toString(), clientId);
        Assert.assertEquals(finTermsCalculateResponse.path("productId").toString(), productId);
        Assert.assertEquals(finTermsCalculateResponse.path("purchaseAmount").toString(), purchaseAmount);

        // Transact V4 response assertions - purchase
        Assert.assertEquals(TransactV4response.path("responseCode"), expectedRaasResponseCode);
        Assert.assertEquals(TransactV4response.path("responseMessage"), expectedMessage);
        Assert.assertNotNull(TransactV4response.path("raasTxnRef"));
        Assert.assertEquals(TransactV4response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // Raas DB assertions 1

        // Raas_Request
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", TransactV4response.path("raasTxnRef")), TransactV4response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "api_call"), "transact-v4");

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", TransactV4response.path("raasTxnRef")), TransactV4response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "event_type"));

        // SQL - UPDATE run recon
        sqlDataAccess.verifyPostgreCustomSql("update cpgtx.tran_log set transactionResponseCode = 2201, transactionType = 'PCH' where transactionResponseCode in (2236, 2240) and clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref");
        Thread.sleep(10000);

        // CTX DB assertions (CTX - Lookup Check for success)
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionId"));
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "clientStan"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "originatingService"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "purchaseAmount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionState"), "C");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionType"), "P");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        Thread.sleep(10000);
        // Transaction_result_request
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV4response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // Transaction_result_response
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV4response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), "202");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas DB assertions 2

        //Transaction_log
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV4response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "vendor_share_amount"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_request_created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_request_created"));
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_response_created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'","currency_code"), currencyCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "funding_source_id"), fundingSourceId);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Reset simulator to success
        startSim.SimulatorScenario(simulatorResetState);

    }

    // Data staging for use in test
    @DataProvider(name = "transactV4PendingToStuckInProcessingTestcases", parallel = false)
    public Object[] transactV4PendingToStuckInProcessingTestcases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Stuck_In_Processing","accountIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Stuck_In_Processing","purchaseAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Stuck_In_Processing","channelId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Stuck_In_Processing","channelName"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Stuck_In_Processing","channelSessionId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Stuck_In_Processing","clientId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Stuck_In_Processing","clientTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Stuck_In_Processing","productId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Stuck_In_Processing","sourceIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Stuck_In_Processing","targetIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Stuck_In_Processing","timestamp"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Stuck_In_Processing","reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Stuck_In_Processing","feeAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Stuck_In_Processing","currencyCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Stuck_In_Processing","fundingSourceId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Stuck_In_Processing","expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Stuck_In_Processing","expectedMessage"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Stuck_In_Processing","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Stuck_In_Processing","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Stuck_In_Processing","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Stuck_In_Processing","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Stuck_In_Processing","simulatorScenario"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Stuck_In_Processing","simulatorResetState")},


        };
    }


    @Step("6. POST Vendor Simulator, Pending to stuck in processing scenarios")
    @Test(dataProvider = "transactV4PendingToStuckInProcessingTestcases", priority = 4)
    public void transactV4VendorPendingToStuckInProcessingTests(String accountIdentifier,
                                                  String purchaseAmount,
                                                  String channelId,
                                                  String channelName,
                                                  String channelSessionId,
                                                  String clientId,
                                                  String clientTxnRef,
                                                  String productId,
                                                  String sourceIdentifier,
                                                  String targetIdentifier,
                                                  String timeStamp,
                                                  String reserveFundsTxnRef,
                                                  String feeAmount,
                                                  String currencyCode,
                                                  String fundingSourceId,
                                                  String expectedRaasResponseCode,
                                                  String expectedMessage,
                                                  String expectedHTTPResponseCode,
                                                  String expectedRaasResultRequestResponseCode,
                                                  String expectedRaasResultResponseResponseCode,
                                                  String expectedCTXTransactionResponseCode,
                                                  String simulatorScenario,
                                                  String simulatorResetState) throws IOException, InterruptedException, JSchException {

        startSim.SimulatorScenario(simulatorScenario);
        Thread.sleep(5000);

        // Financial Terms Calculate GET method call
        Response finTermsCalculateResponse =
                given(CORE_getEndPoints_FinancialTermsCalculate)
                        .param("clientId",clientId)
                        .param("productId", productId)
                        .param("purchaseAmount", purchaseAmount)
                        .when()
                        .get()
                        .then()
                        .extract()
                        .response();

        // Create transactV4 payload object - contains transactV4 request body
        coreTransactV4POJO TransactV4payload = new coreTransactV4POJO(accountIdentifier,
                purchaseAmount,
                channelId,
                channelName,
                channelSessionId,
                clientId,
                clientTxnRef,
                productId,
                sourceIdentifier,
                targetIdentifier,
                timeStamp,
                reserveFundsTxnRef,
                feeAmount,
                currencyCode,
                fundingSourceId);

        // Create transactV4 response body object - contains api response data for use in assertions or other calls
        Response TransactV4response =
                given(CORE_getEndPoints_TransactV4)
                        .contentType(ContentType.JSON)
                        .body(TransactV4payload)
                        .when()
                        .post()
                        .then()
                        .extract()
                        .response();

        // Reset simulator to OK (success) for vendor to test ok scenario
        startSim.SimulatorScenario(simulatorResetState);
        Thread.sleep(15000);

        // Assertions

        // Finance Terms Calculate response assertions
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "");
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "null");
        Assert.assertEquals(finTermsCalculateResponse.path("clientId").toString(), clientId);
        Assert.assertEquals(finTermsCalculateResponse.path("productId").toString(), productId);
        Assert.assertEquals(finTermsCalculateResponse.path("purchaseAmount").toString(), purchaseAmount);

        // Transact V4 response assertions - purchase
        Assert.assertEquals(TransactV4response.path("responseCode"), expectedRaasResponseCode);
        Assert.assertEquals(TransactV4response.path("responseMessage"), expectedMessage);
        Assert.assertNotNull(TransactV4response.path("raasTxnRef"));
        Assert.assertEquals(TransactV4response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // Raas DB assertions 1

        // Raas_Request
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", TransactV4response.path("raasTxnRef")), TransactV4response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "api_call"), "transact-v4");

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", TransactV4response.path("raasTxnRef")), TransactV4response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "event_type"));

        // SQL - UPDATE run recon
        sqlDataAccess.verifyPostgreCustomSql("update cpgtx.tran_log set transactionResponseCode = 2603, transactionType = 'P' where transactionResponseCode in (2236, 2240) and clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref");
        Thread.sleep(10000);

        // CTX DB assertions (CTX - Lookup Check for success)
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionId"));
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "clientStan"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "originatingService"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "purchaseAmount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionState"), "C");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionType"), "P");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        Thread.sleep(10000);
        // Transaction_result_request
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV4response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // Transaction_result_response
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV4response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), "202");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas DB assertions 2

        //Transaction_log
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV4response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "vendor_share_amount"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_request_created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_request_created"));
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_response_created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'","currency_code"), currencyCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "funding_source_id"), fundingSourceId);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Reset simulator to success
        startSim.SimulatorScenario(simulatorResetState);

    }
}
