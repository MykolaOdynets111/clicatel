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
    public Object[] transactV4SuccessTestcases() throws IOException, ParseException {

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
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","AirtelPurchaseSuccessTest","simulatorScenario"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","AirtelPurchaseSuccessTest","simulatorResetState")},


        };
    }


    @Step("2. POST vendor sim negative transact V4 call")
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
        //.log().all()
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
        Thread.sleep(10000);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);

        // raas db assertions
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.transaction_log","raas_txn_ref", "=", TransactV4response.path("raasTxnRef")), TransactV4response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", TransactV4response.path("raasTxnRef")), TransactV4response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", TransactV4response.path("raasTxnRef")), TransactV4response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);

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


        };
    }


    @Step("2. POST Vendor Simulator Negative testing transact V4 call")
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
                        //.log().all()
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
        Thread.sleep(10000);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);

        // raas db assertions
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.transaction_log","raas_txn_ref", "=", TransactV4response.path("raasTxnRef")), TransactV4response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", TransactV4response.path("raasTxnRef")), TransactV4response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", TransactV4response.path("raasTxnRef")), TransactV4response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);

        startSim.SimulatorScenario(simulatorResetState);
    }
}
