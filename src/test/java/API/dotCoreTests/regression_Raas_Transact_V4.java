/**
 * TestSuite: TransactV4 Regression test suite.
 * Includes all end to end api tests for Transact V4
 * Author: Juan-Claude Botha
 */

package API.dotCoreTests;

import api.testUtilities.testConfig;
import com.jcraft.jsch.JSchException;
import io.qameta.allure.Allure;
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

        // Financial Terms Calculate response assertions
        Allure.step("Step.1 --> Financial Terms Calculate response assertions --> Validate clientId field exists (not empty)");
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "");

        Allure.step("Step.2 --> Financial Terms Calculate response assertions --> Validate clientId field exists (not null)");
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "null");

        Allure.step("Step.3 --> Financial Terms Calculate response assertions --> Validate clientId field is correct");
        Assert.assertEquals(finTermsCalculateResponse.path("clientId").toString(), clientId);

        Allure.step("Step.4 --> Financial Terms Calculate response assertions --> Validate productId field is correct");
        Assert.assertEquals(finTermsCalculateResponse.path("productId").toString(), productId);

        Allure.step("Step.5 --> Financial Terms Calculate response assertions --> Validate purchaseAmount field is correct");
        Assert.assertEquals(finTermsCalculateResponse.path("purchaseAmount").toString(), purchaseAmount);

        // Transact V4 response assertions - purchase
        Allure.step("Step.6 --> Transact V4 REST response assertions --> Validate responseCode field is correct");
        Assert.assertEquals(TransactV4response.path("responseCode"), expectedRaasResponseCode);

        Allure.step("Step.7 --> Transact V4 REST response assertions --> Validate responseMessage field is correct");
        Assert.assertEquals(TransactV4response.path("responseMessage"), expectedMessage);

        Allure.step("Step.8 --> Transact V4 REST response assertions --> Validate raasTxnRef field exists");
        Assert.assertNotNull(TransactV4response.path("raasTxnRef"));

        Allure.step("Step.9 --> Transact V4 REST response assertions --> Validate HTTPResponseCode is correct");
        Assert.assertEquals(TransactV4response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // raas db assertions
        //Transaction_log
        Thread.sleep(10000);
        Allure.step("Step.10 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV4response.path("raasTxnRef"));

        Allure.step("Step.11 --> RAAS DB assertions --> Table: transaction_log --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.12 --> RAAS DB assertions --> Table: transaction_log --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);

        Allure.step("Step.13 --> RAAS DB assertions --> Table: transaction_log --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.14 --> RAAS DB assertions --> Table: transaction_log --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.15 --> RAAS DB assertions --> Table: transaction_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.16 --> RAAS DB assertions --> Table: transaction_log --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.17 --> RAAS DB assertions --> Table: transaction_log --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.18 --> RAAS DB assertions --> Table: transaction_log --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.19 --> RAAS DB assertions --> Table: transaction_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.20 --> RAAS DB assertions --> Table: transaction_log --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.21 --> RAAS DB assertions --> Table: transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.22 --> RAAS DB assertions --> Table: transaction_log --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.23 --> RAAS DB assertions --> Table: transaction_log --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.24 --> RAAS DB assertions --> Table: transaction_log --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.25 --> RAAS DB assertions --> Table: transaction_log --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.26 --> RAAS DB assertions --> Table: transaction_log --> Validate reserve_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);

        Allure.step("Step.27 --> RAAS DB assertions --> Table: transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.28 --> RAAS DB assertions --> Table: transaction_log --> Validate fee_amount field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.29 --> RAAS DB assertions --> Table: transaction_log --> Validate client_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.30 --> RAAS DB assertions --> Table: transaction_log --> Validate settlement_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.31 --> RAAS DB assertions --> Table: transaction_log --> Validate vend_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);

        Allure.step("Step.32 --> RAAS DB assertions --> Table: transaction_log --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "vendor_share_amount"));

        Allure.step("Step.33 --> RAAS DB assertions --> Table: transaction_log --> Validate status field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);

        Allure.step("Step.34 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_request_created"));

        Allure.step("Step.35 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");
        Allure.step("Step.36 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_request_created"));

        Allure.step("Step.37 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_response_created"));

        Allure.step("Step.38 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);

        Allure.step("Step.39 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");
        Allure.step("Step.40 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_request_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);

        Allure.step("Step.41 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");

        Allure.step("Step.42 --> RAAS DB assertions --> Table: transaction_log --> Validate currency_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'","currency_code"), currencyCode);

        Allure.step("Step.43 --> RAAS DB assertions --> Table: transaction_log --> Validate funding_source_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "funding_source_id"), fundingSourceId);

        Allure.step("Step.44 --> RAAS DB assertions --> Table: transaction_log --> Validate additional_data_financial_calculations field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Raas_Request
        Allure.step("Step.45 --> RAAS DB assertions --> Table: raas_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", TransactV4response.path("raasTxnRef")), TransactV4response.path("raasTxnRef"));

        Allure.step("Step.46 --> RAAS DB assertions --> Table: raas_request --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.47 --> RAAS DB assertions --> Table: raas_request --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);

        Allure.step("Step.48 --> RAAS DB assertions --> Table: raas_request --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.49 --> RAAS DB assertions --> Table: raas_request --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.50 --> RAAS DB assertions --> Table: raas_request --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.51 --> RAAS DB assertions --> Table: raas_request --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.52 --> RAAS DB assertions --> Table: raas_request --> Validate created field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.53 --> RAAS DB assertions --> Table: raas_request --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.54 --> RAAS DB assertions --> Table: raas_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.55 --> RAAS DB assertions --> Table: raas_request --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.56 --> RAAS DB assertions --> Table: raas_request --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.57 --> RAAS DB assertions --> Table: raas_request --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.58 --> RAAS DB assertions --> Table: raas_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.59 --> RAAS DB assertions --> Table: raas_request --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.60 --> RAAS DB assertions --> Table: raas_request --> Validate cdc_update_timestamp field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.61 --> RAAS DB assertions --> Table: raas_request --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.62 --> RAAS DB assertions --> Table: raas_request --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.63 --> RAAS DB assertions --> Table: raas_request --> Validate api_call field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "api_call"), "transact-v4");

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response
        Allure.step("Step.64 --> RAAS DB assertions --> Table: raas_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", TransactV4response.path("raasTxnRef")), TransactV4response.path("raasTxnRef"));

        Allure.step("Step.65 --> RAAS DB assertions --> Table: raas_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.66 --> RAAS DB assertions --> Table: raas_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "event_type"));

        // CTX DB assertions
        Thread.sleep(5000);
        Allure.step("Step.67 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionResponseCode field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);

        Allure.step("Step.68 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionId field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionId"));

        Allure.step("Step.69 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate clientStan field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "clientStan"));

        Allure.step("Step.70 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originId field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);

        Allure.step("Step.71 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originatingService field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "originatingService"));

        Allure.step("Step.72 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate purchaseAmount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "purchaseAmount"), purchaseAmount);

        Allure.step("Step.73 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionState field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionState"), "C");

        Allure.step("Step.74 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionType field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionType"), "P");

        Allure.step("Step.75 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "client_id"), clientId);

        Allure.step("Step.76 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        // Transaction_result_request
        Allure.step("Step.77 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);

        Allure.step("Step.78 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.79 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.80 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.81 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV4response.path("raasTxnRef"));

        Allure.step("Step.82 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.83 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.84 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate reserve_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);

        Allure.step("Step.85 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate fee_amount field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.86 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate client_share_amount field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.87 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate settlement_amount field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.88 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate vend_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);

        Allure.step("Step.89 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // Transaction_result_response
        Allure.step("Step.90 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);

        Allure.step("Step.91 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV4response.path("raasTxnRef"));

        Allure.step("Step.92 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.93 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.94 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), "202");

        Allure.step("Step.95 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate cdc_update_timestamp field is correct");
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

        // Financial Terms Calculate response assertions
        Allure.step("Step.1 --> Financial Terms Calculate response assertions --> Validate clientId field exists (not empty)");
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "");

        Allure.step("Step.2 --> Financial Terms Calculate response assertions --> Validate clientId field exists (not null)");
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "null");

        Allure.step("Step.3 --> Financial Terms Calculate response assertions --> Validate clientId field is correct");
        Assert.assertEquals(finTermsCalculateResponse.path("clientId").toString(), clientId);

        Allure.step("Step.4 --> Financial Terms Calculate response assertions --> Validate productId field is correct");
        Assert.assertEquals(finTermsCalculateResponse.path("productId").toString(), productId);

        Allure.step("Step.5 --> Financial Terms Calculate response assertions --> Validate purchaseAmount field is correct");
        Assert.assertEquals(finTermsCalculateResponse.path("purchaseAmount").toString(), purchaseAmount);

        // Transact V4 response assertions - purchase
        Allure.step("Step.6 --> Transact V4 REST response assertions --> Validate responseCode field is correct");
        Assert.assertEquals(TransactV4response.path("responseCode"), expectedRaasResponseCode);

        Allure.step("Step.7 --> Transact V4 REST response assertions --> Validate responseMessage field is correct");
        Assert.assertEquals(TransactV4response.path("responseMessage"), expectedMessage);

        Allure.step("Step.8 --> Transact V4 REST response assertions --> Validate raasTxnRef field exists");
        Assert.assertNotNull(TransactV4response.path("raasTxnRef"));

        Allure.step("Step.9 --> Transact V4 REST response assertions --> Validate HTTPResponseCode is correct");
        Assert.assertEquals(TransactV4response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // CTX DB assertions
        Thread.sleep(5000);

        Allure.step("Step.10 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionResponseCode field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);

        Allure.step("Step.11 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionId field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionId"));

        Allure.step("Step.12 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate clientStan field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "clientStan"));

        Allure.step("Step.13 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originId field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);

        Allure.step("Step.14 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originatingService field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "originatingService"));

        Allure.step("Step.15 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate purchaseAmount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "purchaseAmount"), purchaseAmount);

        Allure.step("Step.16 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionState field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionState"), "C");

        Allure.step("Step.17 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionType field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionType"), "P");

        Allure.step("Step.18 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "client_id"), clientId);

        Allure.step("Step.19 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        // raas db assertions
        // RAAS Transaction_log assertions
        Allure.step("Step.20 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.transaction_log","raas_txn_ref", "=", TransactV4response.path("raasTxnRef")), TransactV4response.path("raasTxnRef"));

        // raas.raas_request assertions
        Allure.step("Step.21 --> RAAS DB assertions --> Table: raas.raas_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", TransactV4response.path("raasTxnRef")), TransactV4response.path("raasTxnRef"));

        Allure.step("Step.22 --> RAAS DB assertions --> Table: raas.raas_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", TransactV4response.path("raasTxnRef")), TransactV4response.path("raasTxnRef"));

        Allure.step("Step.23 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);

        Allure.step("Step.24 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate response_code field is correct");
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

                  {testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelNameNullNegativeTest","accountIdentifier"),
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
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","channelNameNullNegativeTest","simulatorResetState")},

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

                 {testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","sourceIdentifierNullNegativeTest","accountIdentifier"),
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
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","sourceIdentifierNullNegativeTest","simulatorResetState")},

                 {testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","accountIdentifier"),
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
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","simulatorResetState")},

                {testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","accountIdentifier"),
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
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","targetIdentifierNullNegativeTest","simulatorResetState")},

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
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","timestampNullNegativeTest","simulatorResetState")}

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
        Allure.step("Step.1 --> Transact V4 REST response assertions --> Validate responseCode field is correct");
        Assert.assertEquals(TransactV4response.path("responseCode"), expectedRaasResponseCode);

        Allure.step("Step.2 --> Transact V4 REST response assertions --> Validate responseMessage field is correct");
        Assert.assertEquals(TransactV4response.path("responseMessage"), expectedMessage);

        Allure.step("Step.3 --> Transact V4 REST response assertions --> Validate HTTPResponseCode is correct");
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

        Allure.step("Step.1 --> Financial Terms Calculate response assertions --> Validate clientId field exists (not empty)");
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "");

        Allure.step("Step.2 --> Financial Terms Calculate response assertions --> Validate clientId field exists (not null)");
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "null");

        Allure.step("Step.3 --> Financial Terms Calculate response assertions --> Validate clientId field is correct");
        Assert.assertEquals(finTermsCalculateResponse.path("clientId").toString(), clientId);

        Allure.step("Step.4 --> Financial Terms Calculate response assertions --> Validate productId field is correct");
        Assert.assertEquals(finTermsCalculateResponse.path("productId").toString(), productId);

        Allure.step("Step.5 --> Financial Terms Calculate response assertions --> Validate purchaseAmount field is correct");
        Assert.assertEquals(finTermsCalculateResponse.path("purchaseAmount").toString(), purchaseAmount);

        // Transact V4 response assertions - purchase

        Allure.step("Step.6 --> Transact V4 REST response assertions --> Validate responseCode field is correct");
        Assert.assertEquals(TransactV4response.path("responseCode"), expectedRaasResponseCode);

        Allure.step("Step.7 --> Transact V4 REST response assertions --> Validate responseMessage field is correct");
        Assert.assertEquals(TransactV4response.path("responseMessage"), expectedMessage);

        Allure.step("Step.8 --> Transact V4 REST response assertions --> Validate raasTxnRef field exists");
        Assert.assertNotNull(TransactV4response.path("raasTxnRef"));

        Allure.step("Step.9 --> Transact V4 REST response assertions --> Validate HTTPResponseCode is correct");
        Assert.assertEquals(TransactV4response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // raas db assertions
        //Transaction_log
        Thread.sleep(15000);

        Allure.step("Step.10 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV4response.path("raasTxnRef"));

        Allure.step("Step.11 --> RAAS DB assertions --> Table: transaction_log --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.12 --> RAAS DB assertions --> Table: transaction_log --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);

        Allure.step("Step.13 --> RAAS DB assertions --> Table: transaction_log --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.14 --> RAAS DB assertions --> Table: transaction_log --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.15 --> RAAS DB assertions --> Table: transaction_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.16 --> RAAS DB assertions --> Table: transaction_log --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.17 --> RAAS DB assertions --> Table: transaction_log --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.18 --> RAAS DB assertions --> Table: transaction_log --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.19 --> RAAS DB assertions --> Table: transaction_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.20 --> RAAS DB assertions --> Table: transaction_log --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.21 --> RAAS DB assertions --> Table: transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.22 --> RAAS DB assertions --> Table: transaction_log --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.23 --> RAAS DB assertions --> Table: transaction_log --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.24 --> RAAS DB assertions --> Table: transaction_log --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.25 --> RAAS DB assertions --> Table: transaction_log --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.26 --> RAAS DB assertions --> Table: transaction_log --> Validate reserve_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);

        Allure.step("Step.27 --> RAAS DB assertions --> Table: transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.28 --> RAAS DB assertions --> Table: transaction_log --> Validate fee_amount field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.29 --> RAAS DB assertions --> Table: transaction_log --> Validate client_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.30 --> RAAS DB assertions --> Table: transaction_log --> Validate settlement_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.31 --> RAAS DB assertions --> Table: transaction_log --> Validate vend_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);

        Allure.step("Step.32 --> RAAS DB assertions --> Table: transaction_log --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "vendor_share_amount"));

        //Allure.step("Step.33 --> RAAS DB assertions --> Table: transaction_log --> Validate status field is correct");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);

        Allure.step("Step.33 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_request_created"));

        Allure.step("Step.34 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");
        Allure.step("Step.35 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_request_created"));

        Allure.step("Step.36 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_response_created"));

        Allure.step("Step.37 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);

        Allure.step("Step.38 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");
        Allure.step("Step.39 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_request_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);

        Allure.step("Step.40 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");

        Allure.step("Step.41 --> RAAS DB assertions --> Table: transaction_log --> Validate currency_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'","currency_code"), currencyCode);

        Allure.step("Step.42 --> RAAS DB assertions --> Table: transaction_log --> Validate funding_source_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "funding_source_id"), fundingSourceId);

        Allure.step("Step.43 --> RAAS DB assertions --> Table: transaction_log --> Validate additional_data_financial_calculations field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));


        // Raas_Request
        Allure.step("Step.44 --> RAAS DB assertions --> Table: raas_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", TransactV4response.path("raasTxnRef")), TransactV4response.path("raasTxnRef"));

        Allure.step("Step.45 --> RAAS DB assertions --> Table: raas_request --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.46 --> RAAS DB assertions --> Table: raas_request --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);

        Allure.step("Step.47 --> RAAS DB assertions --> Table: raas_request --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.48 --> RAAS DB assertions --> Table: raas_request --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.49 --> RAAS DB assertions --> Table: raas_request --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.50 --> RAAS DB assertions --> Table: raas_request --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.51 --> RAAS DB assertions --> Table: raas_request --> Validate created field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.52 --> RAAS DB assertions --> Table: raas_request --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.53 --> RAAS DB assertions --> Table: raas_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.54 --> RAAS DB assertions --> Table: raas_request --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.55 --> RAAS DB assertions --> Table: raas_request --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.56 --> RAAS DB assertions --> Table: raas_request --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.57 --> RAAS DB assertions --> Table: raas_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.58 --> RAAS DB assertions --> Table: raas_request --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.59 --> RAAS DB assertions --> Table: raas_request --> Validate cdc_update_timestamp field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.60 --> RAAS DB assertions --> Table: raas_request --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.61 --> RAAS DB assertions --> Table: raas_request --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.62 --> RAAS DB assertions --> Table: raas_request --> Validate api_call field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "api_call"), "transact-v4");


        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response
        Allure.step("Step.63 --> RAAS DB assertions --> Table: raas_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", TransactV4response.path("raasTxnRef")), TransactV4response.path("raasTxnRef"));

        Allure.step("Step.64 --> RAAS DB assertions --> Table: raas_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.65 --> RAAS DB assertions --> Table: raas_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "event_type"));

        // CTX DB assertions
        Thread.sleep(5000);

        Allure.step("Step.66 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionResponseCode field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);

        Allure.step("Step.67 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionId field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionId"));

        Allure.step("Step.68 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate clientStan field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "clientStan"));

        Allure.step("Step.69 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originId field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);

        Allure.step("Step.70 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originatingService field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "originatingService"));

        Allure.step("Step.71 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate purchaseAmount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "purchaseAmount"), purchaseAmount);

        Allure.step("Step.72 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionState field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionState"), "C");

        Allure.step("Step.73 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionType field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionType"), "P");

        Allure.step("Step.74 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "client_id"), clientId);

        Allure.step("Step.75 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        // Transaction_result_request
        Allure.step("Step.76 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);

        Allure.step("Step.77 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.78 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.79 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.80 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV4response.path("raasTxnRef"));

        Allure.step("Step.81 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.82 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.83 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate reserve_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);

        Allure.step("Step.84 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate fee_amount field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.85 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate client_share_amount field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.86 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate settlement_amount field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.87 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate vend_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);

        Allure.step("Step.88 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // Transaction_result_response
        Allure.step("Step.89 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);

        Allure.step("Step.90 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV4response.path("raasTxnRef"));

        Allure.step("Step.91 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.92 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.93 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), "202");

        Allure.step("Step.94 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate cdc_update_timestamp field is correct");
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
        Thread.sleep(20000);
        // Assertions

        // Finance Terms Calculate response assertions

        Allure.step("Step.1 --> Financial Terms Calculate response assertions --> Validate clientId field exists (not empty)");
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "");

        Allure.step("Step.2 --> Financial Terms Calculate response assertions --> Validate clientId field exists (not null)");
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "null");

        Allure.step("Step.3 --> Financial Terms Calculate response assertions --> Validate clientId field is correct");
        Assert.assertEquals(finTermsCalculateResponse.path("clientId").toString(), clientId);

        Allure.step("Step.4 --> Financial Terms Calculate response assertions --> Validate productId field is correct");
        Assert.assertEquals(finTermsCalculateResponse.path("productId").toString(), productId);

        Allure.step("Step.5 --> Financial Terms Calculate response assertions --> Validate purchaseAmount field is correct");
        Assert.assertEquals(finTermsCalculateResponse.path("purchaseAmount").toString(), purchaseAmount);

        // Transact V4 response assertions - purchase

        Allure.step("Step.6 --> Transact V4 REST response assertions --> Validate responseCode field is correct");
        Assert.assertEquals(TransactV4response.path("responseCode"), expectedRaasResponseCode);

        Allure.step("Step.7 --> Transact V4 REST response assertions --> Validate responseMessage field is correct");
        Assert.assertEquals(TransactV4response.path("responseMessage"), expectedMessage);

        Allure.step("Step.8 --> Transact V4 REST response assertions --> Validate raasTxnRef field exists");
        Assert.assertNotNull(TransactV4response.path("raasTxnRef"));

        Allure.step("Step.9 --> Transact V4 REST response assertions --> Validate HTTPResponseCode is correct");
        Assert.assertEquals(TransactV4response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // raas db assertions
        //Transaction_log
        Thread.sleep(15000);

        Allure.step("Step.10 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV4response.path("raasTxnRef"));

        Allure.step("Step.11 --> RAAS DB assertions --> Table: transaction_log --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.12 --> RAAS DB assertions --> Table: transaction_log --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);

        Allure.step("Step.13 --> RAAS DB assertions --> Table: transaction_log --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.14 --> RAAS DB assertions --> Table: transaction_log --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.15 --> RAAS DB assertions --> Table: transaction_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.16 --> RAAS DB assertions --> Table: transaction_log --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.17 --> RAAS DB assertions --> Table: transaction_log --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.18 --> RAAS DB assertions --> Table: transaction_log --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.19 --> RAAS DB assertions --> Table: transaction_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.20 --> RAAS DB assertions --> Table: transaction_log --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.21 --> RAAS DB assertions --> Table: transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.22 --> RAAS DB assertions --> Table: transaction_log --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.23 --> RAAS DB assertions --> Table: transaction_log --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.24 --> RAAS DB assertions --> Table: transaction_log --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.25 --> RAAS DB assertions --> Table: transaction_log --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.26 --> RAAS DB assertions --> Table: transaction_log --> Validate reserve_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);

        Allure.step("Step.27 --> RAAS DB assertions --> Table: transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.28 --> RAAS DB assertions --> Table: transaction_log --> Validate fee_amount field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.29 --> RAAS DB assertions --> Table: transaction_log --> Validate client_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.30 --> RAAS DB assertions --> Table: transaction_log --> Validate settlement_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.31 --> RAAS DB assertions --> Table: transaction_log --> Validate vend_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);

        Allure.step("Step.32 --> RAAS DB assertions --> Table: transaction_log --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "vendor_share_amount"));

        //Allure.step("Step.33 --> RAAS DB assertions --> Table: transaction_log --> Validate status field is correct");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);

        Allure.step("Step.33 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_request_created"));

        Allure.step("Step.34 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");
        Allure.step("Step.35 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_request_created"));

        Allure.step("Step.36 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_response_created"));

        Allure.step("Step.37 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);

        Allure.step("Step.38 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");
        Allure.step("Step.39 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_request_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);

        Allure.step("Step.40 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");

        Allure.step("Step.41 --> RAAS DB assertions --> Table: transaction_log --> Validate currency_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'","currency_code"), currencyCode);

        Allure.step("Step.42 --> RAAS DB assertions --> Table: transaction_log --> Validate funding_source_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "funding_source_id"), fundingSourceId);

        Allure.step("Step.43 --> RAAS DB assertions --> Table: transaction_log --> Validate additional_data_financial_calculations field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));


        // Raas_Request
        Allure.step("Step.44 --> RAAS DB assertions --> Table: raas_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", TransactV4response.path("raasTxnRef")), TransactV4response.path("raasTxnRef"));

        Allure.step("Step.45 --> RAAS DB assertions --> Table: raas_request --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.46 --> RAAS DB assertions --> Table: raas_request --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);

        Allure.step("Step.47 --> RAAS DB assertions --> Table: raas_request --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.48 --> RAAS DB assertions --> Table: raas_request --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.49 --> RAAS DB assertions --> Table: raas_request --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.50 --> RAAS DB assertions --> Table: raas_request --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.51 --> RAAS DB assertions --> Table: raas_request --> Validate created field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.52 --> RAAS DB assertions --> Table: raas_request --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.53 --> RAAS DB assertions --> Table: raas_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.54 --> RAAS DB assertions --> Table: raas_request --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.55 --> RAAS DB assertions --> Table: raas_request --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.56 --> RAAS DB assertions --> Table: raas_request --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.57 --> RAAS DB assertions --> Table: raas_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.58 --> RAAS DB assertions --> Table: raas_request --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.59 --> RAAS DB assertions --> Table: raas_request --> Validate cdc_update_timestamp field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.60 --> RAAS DB assertions --> Table: raas_request --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.61 --> RAAS DB assertions --> Table: raas_request --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.62 --> RAAS DB assertions --> Table: raas_request --> Validate api_call field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "api_call"), "transact-v4");


        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response
        Allure.step("Step.63 --> RAAS DB assertions --> Table: raas_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", TransactV4response.path("raasTxnRef")), TransactV4response.path("raasTxnRef"));

        Allure.step("Step.64 --> RAAS DB assertions --> Table: raas_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.65 --> RAAS DB assertions --> Table: raas_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "event_type"));

        // CTX DB assertions
        Thread.sleep(5000);

        Allure.step("Step.66 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionResponseCode field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);

        Allure.step("Step.67 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionId field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionId"));

        Allure.step("Step.68 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate clientStan field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "clientStan"));

        Allure.step("Step.69 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originId field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);

        Allure.step("Step.70 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originatingService field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "originatingService"));

        Allure.step("Step.71 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate purchaseAmount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "purchaseAmount"), purchaseAmount);

        Allure.step("Step.72 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionState field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionState"), "C");

        Allure.step("Step.73 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionType field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionType"), "P");

        Allure.step("Step.74 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "client_id"), clientId);

        Allure.step("Step.75 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        // Transaction_result_request
        Allure.step("Step.76 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);

        Allure.step("Step.77 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.78 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.79 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.80 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV4response.path("raasTxnRef"));

        Allure.step("Step.81 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.82 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.83 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate reserve_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);

        Allure.step("Step.84 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate fee_amount field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.85 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate client_share_amount field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.86 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate settlement_amount field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.87 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate vend_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);

        Allure.step("Step.88 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // Transaction_result_response
        Allure.step("Step.89 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);

        Allure.step("Step.90 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV4response.path("raasTxnRef"));

        Allure.step("Step.91 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.92 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.93 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), "202");

        Allure.step("Step.94 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate cdc_update_timestamp field is correct");
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
        Thread.sleep(20000);

        // Assertions

        // Finance Terms Calculate response assertions

        Allure.step("Step.1 --> Financial Terms Calculate response assertions --> Validate clientId field exists (not empty)");
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "");

        Allure.step("Step.2 --> Financial Terms Calculate response assertions --> Validate clientId field exists (not null)");
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "null");

        Allure.step("Step.3 --> Financial Terms Calculate response assertions --> Validate clientId field is correct");
        Assert.assertEquals(finTermsCalculateResponse.path("clientId").toString(), clientId);

        Allure.step("Step.4 --> Financial Terms Calculate response assertions --> Validate productId field is correct");
        Assert.assertEquals(finTermsCalculateResponse.path("productId").toString(), productId);

        Allure.step("Step.5 --> Financial Terms Calculate response assertions --> Validate purchaseAmount field is correct");
        Assert.assertEquals(finTermsCalculateResponse.path("purchaseAmount").toString(), purchaseAmount);

        // Transact V4 response assertions - purchase

        Allure.step("Step.6 --> Transact V4 REST response assertions --> Validate responseCode field is correct");
        Assert.assertEquals(TransactV4response.path("responseCode"), expectedRaasResponseCode);

        Allure.step("Step.7 --> Transact V4 REST response assertions --> Validate responseMessage field is correct");
        Assert.assertEquals(TransactV4response.path("responseMessage"), expectedMessage);

        Allure.step("Step.8 --> Transact V4 REST response assertions --> Validate raasTxnRef field exists");
        Assert.assertNotNull(TransactV4response.path("raasTxnRef"));

        Allure.step("Step.9 --> Transact V4 REST response assertions --> Validate HTTPResponseCode is correct");
        Assert.assertEquals(TransactV4response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // Raas DB assertions 1

        // Raas_Request

        Allure.step("Step.10 --> RAAS DB assertions --> Table: raas_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", TransactV4response.path("raasTxnRef")), TransactV4response.path("raasTxnRef"));

        Allure.step("Step.11 --> RAAS DB assertions --> Table: raas_request --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.12 --> RAAS DB assertions --> Table: raas_request --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);

        Allure.step("Step.13 --> RAAS DB assertions --> Table: raas_request --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.14 --> RAAS DB assertions --> Table: raas_request --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.15 --> RAAS DB assertions --> Table: raas_request --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.16 --> RAAS DB assertions --> Table: raas_request --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.17 --> RAAS DB assertions --> Table: raas_request --> Validate created field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.18 --> RAAS DB assertions --> Table: raas_request --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.19 --> RAAS DB assertions --> Table: raas_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.20 --> RAAS DB assertions --> Table: raas_request --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.21 --> RAAS DB assertions --> Table: raas_request --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.22 --> RAAS DB assertions --> Table: raas_request --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.23 --> RAAS DB assertions --> Table: raas_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.24 --> RAAS DB assertions --> Table: raas_request --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.25 --> RAAS DB assertions --> Table: raas_request --> Validate cdc_update_timestamp field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.26 --> RAAS DB assertions --> Table: raas_request --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.27 --> RAAS DB assertions --> Table: raas_request --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.28 --> RAAS DB assertions --> Table: raas_request --> Validate api_call field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "api_call"), "transact-v4");


        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response
        Allure.step("Step.29 --> RAAS DB assertions --> Table: raas_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", TransactV4response.path("raasTxnRef")), TransactV4response.path("raasTxnRef"));

        Allure.step("Step.30 --> RAAS DB assertions --> Table: raas_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.31 --> RAAS DB assertions --> Table: raas_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "event_type"));


        // SQL - UPDATE run recon
        sqlDataAccess.verifyPostgreCustomSql("update cpgtx.tran_log set transactionResponseCode = 2201, transactionType = 'PCH' where transactionResponseCode in (2236, 2240) and clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref");
        Thread.sleep(10000);

        // CTX DB assertions (CTX - Lookup Check for success)

        Thread.sleep(5000);

        Allure.step("Step.32 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionResponseCode field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);

        Allure.step("Step.33 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionId field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionId"));

        Allure.step("Step.34 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate clientStan field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "clientStan"));

        Allure.step("Step.35 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originId field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);

        Allure.step("Step.36 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originatingService field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "originatingService"));

        Allure.step("Step.37 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate purchaseAmount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "purchaseAmount"), purchaseAmount);

        Allure.step("Step.38 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionState field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionState"), "C");

        Allure.step("Step.39 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionType field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionType"), "P");

        Allure.step("Step.40 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "client_id"), clientId);

        Allure.step("Step.41 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        Thread.sleep(10000);

        // Transaction_result_request

        Allure.step("Step.42 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);

        Allure.step("Step.43 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.44 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.45 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.46 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV4response.path("raasTxnRef"));

        Allure.step("Step.47 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.48 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.49 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate reserve_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);

        Allure.step("Step.50 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate fee_amount field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.51 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate client_share_amount field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.52 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate settlement_amount field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.53 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate vend_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);

        Allure.step("Step.54 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // Transaction_result_response
        Allure.step("Step.55 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);

        Allure.step("Step.56 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV4response.path("raasTxnRef"));

        Allure.step("Step.57 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.58 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.59 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), "202");

        Allure.step("Step.60 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate cdc_update_timestamp field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas DB assertions 2

        //Transaction_log

        Thread.sleep(15000);

        Allure.step("Step.61 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV4response.path("raasTxnRef"));

        Allure.step("Step.62 --> RAAS DB assertions --> Table: transaction_log --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.63 --> RAAS DB assertions --> Table: transaction_log --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);

        Allure.step("Step.64 --> RAAS DB assertions --> Table: transaction_log --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.65 --> RAAS DB assertions --> Table: transaction_log --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.66 --> RAAS DB assertions --> Table: transaction_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.67 --> RAAS DB assertions --> Table: transaction_log --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.68 --> RAAS DB assertions --> Table: transaction_log --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.69 --> RAAS DB assertions --> Table: transaction_log --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.70 --> RAAS DB assertions --> Table: transaction_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.71 --> RAAS DB assertions --> Table: transaction_log --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.72 --> RAAS DB assertions --> Table: transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.73 --> RAAS DB assertions --> Table: transaction_log --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.74 --> RAAS DB assertions --> Table: transaction_log --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.75 --> RAAS DB assertions --> Table: transaction_log --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.76 --> RAAS DB assertions --> Table: transaction_log --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.77 --> RAAS DB assertions --> Table: transaction_log --> Validate reserve_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);

        Allure.step("Step.78 --> RAAS DB assertions --> Table: transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.79 --> RAAS DB assertions --> Table: transaction_log --> Validate fee_amount field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.80 --> RAAS DB assertions --> Table: transaction_log --> Validate client_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.81 --> RAAS DB assertions --> Table: transaction_log --> Validate settlement_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.82 --> RAAS DB assertions --> Table: transaction_log --> Validate vend_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);

        Allure.step("Step.83 --> RAAS DB assertions --> Table: transaction_log --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "vendor_share_amount"));

        //Allure.step("Step.33 --> RAAS DB assertions --> Table: transaction_log --> Validate status field is correct");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);

        Allure.step("Step.84 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_request_created"));

        Allure.step("Step.85 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");
        Allure.step("Step.86 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_request_created"));

        Allure.step("Step.87 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_response_created"));

        Allure.step("Step.88 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);

        Allure.step("Step.89 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");
        Allure.step("Step.90 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_request_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);

        Allure.step("Step.91 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");

        Allure.step("Step.92 --> RAAS DB assertions --> Table: transaction_log --> Validate currency_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'","currency_code"), currencyCode);

        Allure.step("Step.93 --> RAAS DB assertions --> Table: transaction_log --> Validate funding_source_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "funding_source_id"), fundingSourceId);

        Allure.step("Step.94 --> RAAS DB assertions --> Table: transaction_log --> Validate additional_data_financial_calculations field exists");
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
    @Test(dataProvider = "transactV4PendingToStuckInProcessingTestcases", priority = 5)
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
        Thread.sleep(20000);

        // Assertions

        // Finance Terms Calculate response assertions

        Allure.step("Step.1 --> Financial Terms Calculate response assertions --> Validate clientId field exists (not empty)");
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "");

        Allure.step("Step.2 --> Financial Terms Calculate response assertions --> Validate clientId field exists (not null)");
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "null");

        Allure.step("Step.3 --> Financial Terms Calculate response assertions --> Validate clientId field is correct");
        Assert.assertEquals(finTermsCalculateResponse.path("clientId").toString(), clientId);

        Allure.step("Step.4 --> Financial Terms Calculate response assertions --> Validate productId field is correct");
        Assert.assertEquals(finTermsCalculateResponse.path("productId").toString(), productId);

        Allure.step("Step.5 --> Financial Terms Calculate response assertions --> Validate purchaseAmount field is correct");
        Assert.assertEquals(finTermsCalculateResponse.path("purchaseAmount").toString(), purchaseAmount);

        // Transact V4 response assertions - purchase

        Allure.step("Step.6 --> Transact V4 REST response assertions --> Validate responseCode field is correct");
        Assert.assertEquals(TransactV4response.path("responseCode"), expectedRaasResponseCode);

        Allure.step("Step.7 --> Transact V4 REST response assertions --> Validate responseMessage field is correct");
        Assert.assertEquals(TransactV4response.path("responseMessage"), expectedMessage);

        Allure.step("Step.8 --> Transact V4 REST response assertions --> Validate raasTxnRef field exists");
        Assert.assertNotNull(TransactV4response.path("raasTxnRef"));

        Allure.step("Step.9 --> Transact V4 REST response assertions --> Validate HTTPResponseCode is correct");
        Assert.assertEquals(TransactV4response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));


        // Raas DB assertions 1

        // Raas_Request

        Allure.step("Step.10 --> RAAS DB assertions --> Table: raas_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", TransactV4response.path("raasTxnRef")), TransactV4response.path("raasTxnRef"));

        Allure.step("Step.11 --> RAAS DB assertions --> Table: raas_request --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.12 --> RAAS DB assertions --> Table: raas_request --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);

        Allure.step("Step.13 --> RAAS DB assertions --> Table: raas_request --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.14 --> RAAS DB assertions --> Table: raas_request --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.15 --> RAAS DB assertions --> Table: raas_request --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.16 --> RAAS DB assertions --> Table: raas_request --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.17 --> RAAS DB assertions --> Table: raas_request --> Validate created field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.18 --> RAAS DB assertions --> Table: raas_request --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.19 --> RAAS DB assertions --> Table: raas_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.20 --> RAAS DB assertions --> Table: raas_request --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.21 --> RAAS DB assertions --> Table: raas_request --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.22 --> RAAS DB assertions --> Table: raas_request --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.23 --> RAAS DB assertions --> Table: raas_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.24 --> RAAS DB assertions --> Table: raas_request --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.25 --> RAAS DB assertions --> Table: raas_request --> Validate cdc_update_timestamp field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.26 --> RAAS DB assertions --> Table: raas_request --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.27 --> RAAS DB assertions --> Table: raas_request --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.28 --> RAAS DB assertions --> Table: raas_request --> Validate api_call field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "api_call"), "transact-v4");

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response

        Allure.step("Step.29 --> RAAS DB assertions --> Table: raas_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", TransactV4response.path("raasTxnRef")), TransactV4response.path("raasTxnRef"));

        Allure.step("Step.30 --> RAAS DB assertions --> Table: raas_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.31 --> RAAS DB assertions --> Table: raas_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "event_type"));

        // SQL - UPDATE response code
        sqlDataAccess.verifyPostgreCustomSql("update cpgtx.tran_log set transactionResponseCode = 2603, transactionType = 'P' where transactionResponseCode in (2236, 2240) and clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref");
        Thread.sleep(10000);

        // CTX DB assertions (CTX - Lookup Check for success)

        Thread.sleep(5000);

        Allure.step("Step.32 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionResponseCode field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);

        Allure.step("Step.33 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionId field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionId"));

        Allure.step("Step.34 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate clientStan field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "clientStan"));

        Allure.step("Step.35 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originId field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);

        Allure.step("Step.36 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originatingService field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "originatingService"));

        Allure.step("Step.37 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate purchaseAmount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "purchaseAmount"), purchaseAmount);

        Allure.step("Step.38 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionState field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionState"), "C");

        Allure.step("Step.39 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionType field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionType"), "P");

        Allure.step("Step.40 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "client_id"), clientId);

        Allure.step("Step.41 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        Thread.sleep(10000);

        // Transaction_result_request

        Allure.step("Step.42 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);

        Allure.step("Step.43 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.44 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.45 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.46 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV4response.path("raasTxnRef"));

        Allure.step("Step.47 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.48 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.49 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate reserve_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);

        Allure.step("Step.50 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate fee_amount field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.51 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate client_share_amount field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.52 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate settlement_amount field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.53 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate vend_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);

        Allure.step("Step.54 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // Transaction_result_response
        Allure.step("Step.55 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);

        Allure.step("Step.56 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV4response.path("raasTxnRef"));

        Allure.step("Step.57 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.58 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.59 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), "202");

        Allure.step("Step.60 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate cdc_update_timestamp field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas DB assertions 2

        //Transaction_log

        Allure.step("Step.61 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV4response.path("raasTxnRef"));

        Allure.step("Step.62 --> RAAS DB assertions --> Table: transaction_log --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.63 --> RAAS DB assertions --> Table: transaction_log --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);

        Allure.step("Step.64 --> RAAS DB assertions --> Table: transaction_log --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.65 --> RAAS DB assertions --> Table: transaction_log --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.66 --> RAAS DB assertions --> Table: transaction_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.67 --> RAAS DB assertions --> Table: transaction_log --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.68 --> RAAS DB assertions --> Table: transaction_log --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.69 --> RAAS DB assertions --> Table: transaction_log --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.70 --> RAAS DB assertions --> Table: transaction_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.71 --> RAAS DB assertions --> Table: transaction_log --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.72 --> RAAS DB assertions --> Table: transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.73 --> RAAS DB assertions --> Table: transaction_log --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.74 --> RAAS DB assertions --> Table: transaction_log --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.75 --> RAAS DB assertions --> Table: transaction_log --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.76 --> RAAS DB assertions --> Table: transaction_log --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.77 --> RAAS DB assertions --> Table: transaction_log --> Validate reserve_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);

        Allure.step("Step.78 --> RAAS DB assertions --> Table: transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.79 --> RAAS DB assertions --> Table: transaction_log --> Validate fee_amount field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.80 --> RAAS DB assertions --> Table: transaction_log --> Validate client_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.81 --> RAAS DB assertions --> Table: transaction_log --> Validate settlement_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.82 --> RAAS DB assertions --> Table: transaction_log --> Validate vend_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);

        Allure.step("Step.83 --> RAAS DB assertions --> Table: transaction_log --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "vendor_share_amount"));

        //Allure.step("Step.33 --> RAAS DB assertions --> Table: transaction_log --> Validate status field is correct");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);

        Allure.step("Step.84 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_request_created"));

        Allure.step("Step.85 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");
        Allure.step("Step.86 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_request_created"));

        Allure.step("Step.87 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_response_created"));

        Allure.step("Step.88 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);

        Allure.step("Step.89 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");
        Allure.step("Step.90 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_request_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);

        Allure.step("Step.91 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");

        Allure.step("Step.92 --> RAAS DB assertions --> Table: transaction_log --> Validate currency_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'","currency_code"), currencyCode);

        Allure.step("Step.93 --> RAAS DB assertions --> Table: transaction_log --> Validate funding_source_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "funding_source_id"), fundingSourceId);

        Allure.step("Step.94 --> RAAS DB assertions --> Table: transaction_log --> Validate additional_data_financial_calculations field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Reset simulator to success
        startSim.SimulatorScenario(simulatorResetState);

    }

    // Data staging for use in test
    @DataProvider(name = "transactV4PendingToRetryableDeclineToDeclineTestcases", parallel = false)
    public Object[] transactV4PendingToRetryableDeclineToDeclineTestcases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","accountIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","purchaseAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","channelId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","channelName"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","channelSessionId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","clientId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","clientTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","productId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","sourceIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","targetIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","timestamp"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","feeAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","currencyCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","fundingSourceId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","expectedMessage"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","simulatorScenario"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","simulatorResetState")},


        };
    }


    @Step("7. POST Vendor Simulator, Pending to Retryable Decline To Decline scenarios")
    @Test(dataProvider = "transactV4PendingToRetryableDeclineToDeclineTestcases", priority = 6)
    public void transactV4VendorPendingToRetryableDeclineToDeclineTests(String accountIdentifier,
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

        Allure.step("Step.1 --> Financial Terms Calculate response assertions --> Validate clientId field exists (not empty)");
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "");

        Allure.step("Step.2 --> Financial Terms Calculate response assertions --> Validate clientId field exists (not null)");
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "null");

        Allure.step("Step.3 --> Financial Terms Calculate response assertions --> Validate clientId field is correct");
        Assert.assertEquals(finTermsCalculateResponse.path("clientId").toString(), clientId);

        Allure.step("Step.4 --> Financial Terms Calculate response assertions --> Validate productId field is correct");
        Assert.assertEquals(finTermsCalculateResponse.path("productId").toString(), productId);

        Allure.step("Step.5 --> Financial Terms Calculate response assertions --> Validate purchaseAmount field is correct");
        Assert.assertEquals(finTermsCalculateResponse.path("purchaseAmount").toString(), purchaseAmount);

        // Transact V4 response assertions - purchase

        Allure.step("Step.6 --> Transact V4 REST response assertions --> Validate responseCode field is correct");
        Assert.assertEquals(TransactV4response.path("responseCode"), expectedRaasResponseCode);

        Allure.step("Step.7 --> Transact V4 REST response assertions --> Validate responseMessage field is correct");
        Assert.assertEquals(TransactV4response.path("responseMessage"), expectedMessage);

        Allure.step("Step.8 --> Transact V4 REST response assertions --> Validate raasTxnRef field exists");
        Assert.assertNotNull(TransactV4response.path("raasTxnRef"));

        Allure.step("Step.9 --> Transact V4 REST response assertions --> Validate HTTPResponseCode is correct");
        Assert.assertEquals(TransactV4response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // Raas DB assertions 1

        // Raas request

        Allure.step("Step.10 --> RAAS DB assertions --> Table: raas_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", TransactV4response.path("raasTxnRef")), TransactV4response.path("raasTxnRef"));

        Allure.step("Step.11 --> RAAS DB assertions --> Table: raas_request --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.12 --> RAAS DB assertions --> Table: raas_request --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);

        Allure.step("Step.13 --> RAAS DB assertions --> Table: raas_request --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.14 --> RAAS DB assertions --> Table: raas_request --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.15 --> RAAS DB assertions --> Table: raas_request --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.16 --> RAAS DB assertions --> Table: raas_request --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.17 --> RAAS DB assertions --> Table: raas_request --> Validate created field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.18 --> RAAS DB assertions --> Table: raas_request --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.19 --> RAAS DB assertions --> Table: raas_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.20 --> RAAS DB assertions --> Table: raas_request --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.21 --> RAAS DB assertions --> Table: raas_request --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.22 --> RAAS DB assertions --> Table: raas_request --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.23 --> RAAS DB assertions --> Table: raas_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.24 --> RAAS DB assertions --> Table: raas_request --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.25 --> RAAS DB assertions --> Table: raas_request --> Validate cdc_update_timestamp field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.26 --> RAAS DB assertions --> Table: raas_request --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.27 --> RAAS DB assertions --> Table: raas_request --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.28 --> RAAS DB assertions --> Table: raas_request --> Validate api_call field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "api_call"), "transact-v4");

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response

        Allure.step("Step.29 --> RAAS DB assertions --> Table: raas_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", TransactV4response.path("raasTxnRef")), TransactV4response.path("raasTxnRef"));

        Allure.step("Step.30 --> RAAS DB assertions --> Table: raas_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.31 --> RAAS DB assertions --> Table: raas_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "event_type"));

        // SQL - UPDATE run recon
        sqlDataAccess.verifyPostgreCustomSql("update cpgtx.tran_log set transactionResponseCode = 2201, transactionType = 'PCH' where transactionResponseCode in (2236, 2240) and clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref");
        Thread.sleep(10000);

        // CTX DB assertions (CTX - Lookup Check for success)

        Allure.step("Step.32 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionResponseCode field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);

        Allure.step("Step.33 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionId field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionId"));

        Allure.step("Step.34 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate clientStan field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "clientStan"));

        Allure.step("Step.35 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originId field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);

        Allure.step("Step.36 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originatingService field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "originatingService"));

        Allure.step("Step.37 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate purchaseAmount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "purchaseAmount"), purchaseAmount);

        Allure.step("Step.38 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionState field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionState"), "C");

        Allure.step("Step.39 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionType field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionType"), "P");

        Allure.step("Step.40 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "client_id"), clientId);

        Allure.step("Step.41 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        Thread.sleep(10000);
        // Transaction_result_request
        /*Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);
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
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp")); */

        // Raas DB assertions 2

        //Transaction_log

        Allure.step("Step.42 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV4response.path("raasTxnRef"));

        Allure.step("Step.43 --> RAAS DB assertions --> Table: transaction_log --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.44 --> RAAS DB assertions --> Table: transaction_log --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);

        Allure.step("Step.45 --> RAAS DB assertions --> Table: transaction_log --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.46 --> RAAS DB assertions --> Table: transaction_log --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.47 --> RAAS DB assertions --> Table: transaction_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.48 --> RAAS DB assertions --> Table: transaction_log --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.49 --> RAAS DB assertions --> Table: transaction_log --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.50 --> RAAS DB assertions --> Table: transaction_log --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.51 --> RAAS DB assertions --> Table: transaction_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.52 --> RAAS DB assertions --> Table: transaction_log --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.53 --> RAAS DB assertions --> Table: transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.54 --> RAAS DB assertions --> Table: transaction_log --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.55 --> RAAS DB assertions --> Table: transaction_log --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.56 --> RAAS DB assertions --> Table: transaction_log --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.57 --> RAAS DB assertions --> Table: transaction_log --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.58 --> RAAS DB assertions --> Table: transaction_log --> Validate reserve_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);

        Allure.step("Step.59 --> RAAS DB assertions --> Table: transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.60 --> RAAS DB assertions --> Table: transaction_log --> Validate fee_amount field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.61 --> RAAS DB assertions --> Table: transaction_log --> Validate client_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.62 --> RAAS DB assertions --> Table: transaction_log --> Validate settlement_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.63 --> RAAS DB assertions --> Table: transaction_log --> Validate vend_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);

        Allure.step("Step.64 --> RAAS DB assertions --> Table: transaction_log --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "vendor_share_amount"));

        //Allure.step("Step.33 --> RAAS DB assertions --> Table: transaction_log --> Validate status field is correct");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);

        Allure.step("Step.65 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_request_created"));

        Allure.step("Step.66 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");
        Allure.step("Step.67 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_request_created"));

        Allure.step("Step.68 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_response_created"));

        Allure.step("Step.69 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);

        Allure.step("Step.70 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");
        Allure.step("Step.71 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_request_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);

        Allure.step("Step.72 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");

        Allure.step("Step.73 --> RAAS DB assertions --> Table: transaction_log --> Validate currency_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'","currency_code"), currencyCode);

        Allure.step("Step.74 --> RAAS DB assertions --> Table: transaction_log --> Validate funding_source_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "funding_source_id"), fundingSourceId);

        Allure.step("Step.75 --> RAAS DB assertions --> Table: transaction_log --> Validate additional_data_financial_calculations field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Reset simulator to success
        startSim.SimulatorScenario("MTNNG SUCCESS");

    }

    // Data staging for use in test
    @DataProvider(name = "transactV4PendingToRetryableDeclineToNonRetryableDeclineTestcases", parallel = false)
    public Object[] transactV4PendingToRetryableDeclineToNonRetryableDeclineTestcases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","accountIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","purchaseAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","channelId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","channelName"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","channelSessionId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","clientId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","clientTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","productId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","sourceIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","targetIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","timestamp"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","feeAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","currencyCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","fundingSourceId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","expectedMessage"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","simulatorScenario"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Decline","simulatorResetState")},


        };
    }


    @Step("8. POST Vendor Simulator, Pending to Retryable Decline To Non Retryable Decline scenarios")
    @Test(dataProvider = "transactV4PendingToRetryableDeclineToNonRetryableDeclineTestcases", priority = 7)
    public void transactV4VendorPendingToRetryableDeclineToNonRetryableDeclineTests(String accountIdentifier,
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

        Allure.step("Step.1 --> Financial Terms Calculate response assertions --> Validate clientId field exists (not empty)");
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "");

        Allure.step("Step.2 --> Financial Terms Calculate response assertions --> Validate clientId field exists (not null)");
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "null");

        Allure.step("Step.3 --> Financial Terms Calculate response assertions --> Validate clientId field is correct");
        Assert.assertEquals(finTermsCalculateResponse.path("clientId").toString(), clientId);

        Allure.step("Step.4 --> Financial Terms Calculate response assertions --> Validate productId field is correct");
        Assert.assertEquals(finTermsCalculateResponse.path("productId").toString(), productId);

        Allure.step("Step.5 --> Financial Terms Calculate response assertions --> Validate purchaseAmount field is correct");
        Assert.assertEquals(finTermsCalculateResponse.path("purchaseAmount").toString(), purchaseAmount);

        // Transact V4 response assertions - purchase

        Allure.step("Step.6 --> Transact V4 REST response assertions --> Validate responseCode field is correct");
        Assert.assertEquals(TransactV4response.path("responseCode"), expectedRaasResponseCode);

        Allure.step("Step.7 --> Transact V4 REST response assertions --> Validate responseMessage field is correct");
        Assert.assertEquals(TransactV4response.path("responseMessage"), expectedMessage);

        Allure.step("Step.8 --> Transact V4 REST response assertions --> Validate raasTxnRef field exists");
        Assert.assertNotNull(TransactV4response.path("raasTxnRef"));

        Allure.step("Step.9 --> Transact V4 REST response assertions --> Validate HTTPResponseCode is correct");
        Assert.assertEquals(TransactV4response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // Raas DB assertions 1

        // Raas_Request

        Allure.step("Step.10 --> RAAS DB assertions --> Table: raas_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", TransactV4response.path("raasTxnRef")), TransactV4response.path("raasTxnRef"));

        Allure.step("Step.11 --> RAAS DB assertions --> Table: raas_request --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.12 --> RAAS DB assertions --> Table: raas_request --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);

        Allure.step("Step.13 --> RAAS DB assertions --> Table: raas_request --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.14 --> RAAS DB assertions --> Table: raas_request --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.15 --> RAAS DB assertions --> Table: raas_request --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.16 --> RAAS DB assertions --> Table: raas_request --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.17 --> RAAS DB assertions --> Table: raas_request --> Validate created field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.18 --> RAAS DB assertions --> Table: raas_request --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.19 --> RAAS DB assertions --> Table: raas_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.20 --> RAAS DB assertions --> Table: raas_request --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.21 --> RAAS DB assertions --> Table: raas_request --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.22 --> RAAS DB assertions --> Table: raas_request --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.23 --> RAAS DB assertions --> Table: raas_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.24 --> RAAS DB assertions --> Table: raas_request --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.25 --> RAAS DB assertions --> Table: raas_request --> Validate cdc_update_timestamp field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.26 --> RAAS DB assertions --> Table: raas_request --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.27 --> RAAS DB assertions --> Table: raas_request --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.28 --> RAAS DB assertions --> Table: raas_request --> Validate api_call field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "api_call"), "transact-v4");

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response

        Allure.step("Step.29 --> RAAS DB assertions --> Table: raas_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", TransactV4response.path("raasTxnRef")), TransactV4response.path("raasTxnRef"));

        Allure.step("Step.30 --> RAAS DB assertions --> Table: raas_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.31 --> RAAS DB assertions --> Table: raas_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "event_type"));

        // SQL - UPDATE run recon
        sqlDataAccess.verifyPostgreCustomSql("update cpgtx.tran_log set transactionResponseCode = 2201, transactionType = 'PCH' where transactionResponseCode in (2236, 2240, 2213) and clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref");
        Thread.sleep(10000);

        // CTX DB assertions (CTX - Lookup Check for success)

        Allure.step("Step.32 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionResponseCode field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);

        Allure.step("Step.33 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionId field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionId"));

        Allure.step("Step.34 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate clientStan field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "clientStan"));

        Allure.step("Step.35 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originId field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);

        Allure.step("Step.36 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originatingService field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "originatingService"));

        Allure.step("Step.37 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate purchaseAmount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "purchaseAmount"), purchaseAmount);

        Allure.step("Step.38 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionState field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionState"), "C");

        Allure.step("Step.39 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionType field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionType"), "P");

        Allure.step("Step.40 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "client_id"), clientId);

        Allure.step("Step.41 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        Thread.sleep(10000);
        // Transaction_result_request
        /*Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);
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
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp")); */

        // Raas DB assertions 2

        //Transaction_log

        Allure.step("Step.42 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV4response.path("raasTxnRef"));

        Allure.step("Step.43 --> RAAS DB assertions --> Table: transaction_log --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.44 --> RAAS DB assertions --> Table: transaction_log --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);

        Allure.step("Step.45 --> RAAS DB assertions --> Table: transaction_log --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.46 --> RAAS DB assertions --> Table: transaction_log --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.47 --> RAAS DB assertions --> Table: transaction_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.48 --> RAAS DB assertions --> Table: transaction_log --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.49 --> RAAS DB assertions --> Table: transaction_log --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.50 --> RAAS DB assertions --> Table: transaction_log --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.51 --> RAAS DB assertions --> Table: transaction_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.52 --> RAAS DB assertions --> Table: transaction_log --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.53 --> RAAS DB assertions --> Table: transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.54 --> RAAS DB assertions --> Table: transaction_log --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.55 --> RAAS DB assertions --> Table: transaction_log --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.56 --> RAAS DB assertions --> Table: transaction_log --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.57 --> RAAS DB assertions --> Table: transaction_log --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.58 --> RAAS DB assertions --> Table: transaction_log --> Validate reserve_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);

        Allure.step("Step.59 --> RAAS DB assertions --> Table: transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.60 --> RAAS DB assertions --> Table: transaction_log --> Validate fee_amount field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.61 --> RAAS DB assertions --> Table: transaction_log --> Validate client_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.62 --> RAAS DB assertions --> Table: transaction_log --> Validate settlement_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.63 --> RAAS DB assertions --> Table: transaction_log --> Validate vend_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);

        Allure.step("Step.64 --> RAAS DB assertions --> Table: transaction_log --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "vendor_share_amount"));

        //Allure.step("Step.33 --> RAAS DB assertions --> Table: transaction_log --> Validate status field is correct");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);

        Allure.step("Step.65 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_request_created"));

        Allure.step("Step.66 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");
        Allure.step("Step.67 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_request_created"));

        Allure.step("Step.68 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_response_created"));

        Allure.step("Step.69 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);

        Allure.step("Step.70 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");
        Allure.step("Step.71 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_request_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);

        Allure.step("Step.72 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");

        Allure.step("Step.73 --> RAAS DB assertions --> Table: transaction_log --> Validate currency_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'","currency_code"), currencyCode);

        Allure.step("Step.74 --> RAAS DB assertions --> Table: transaction_log --> Validate funding_source_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "funding_source_id"), fundingSourceId);

        Allure.step("Step.75 --> RAAS DB assertions --> Table: transaction_log --> Validate additional_data_financial_calculations field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Reset simulator to success
        startSim.SimulatorScenario("MTNNG SUCCESS");

    }

    // Data staging for use in test
    @DataProvider(name = "transactV4TransactionInProgressTestcases", parallel = false)
    public Object[] transactV4TransactionInProgressTestcases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Transaction_In_Progress","accountIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Transaction_In_Progress","purchaseAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Transaction_In_Progress","channelId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Transaction_In_Progress","channelName"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Transaction_In_Progress","channelSessionId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Transaction_In_Progress","clientId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Transaction_In_Progress","clientTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Transaction_In_Progress","productId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Transaction_In_Progress","sourceIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Transaction_In_Progress","targetIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Transaction_In_Progress","timestamp"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Transaction_In_Progress","reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Transaction_In_Progress","feeAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Transaction_In_Progress","currencyCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Transaction_In_Progress","fundingSourceId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Transaction_In_Progress","expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Transaction_In_Progress","expectedMessage"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Transaction_In_Progress","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Transaction_In_Progress","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Transaction_In_Progress","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Transaction_In_Progress","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Transaction_In_Progress","simulatorScenario"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Transaction_In_Progress","simulatorResetState")},


        };
    }


    @Step("8. POST Vendor Simulator, Pending to Retryable Decline To Non Retryable Decline scenarios")
    @Test(dataProvider = "transactV4TransactionInProgressTestcases", priority = 8)
    public void transactV4VendorTransactionInProgressTests(String accountIdentifier,
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

        Allure.step("Step.1 --> Financial Terms Calculate response assertions --> Validate clientId field exists (not empty)");
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "");

        Allure.step("Step.2 --> Financial Terms Calculate response assertions --> Validate clientId field exists (not null)");
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "null");

        Allure.step("Step.3 --> Financial Terms Calculate response assertions --> Validate clientId field is correct");
        Assert.assertEquals(finTermsCalculateResponse.path("clientId").toString(), clientId);

        Allure.step("Step.4 --> Financial Terms Calculate response assertions --> Validate productId field is correct");
        Assert.assertEquals(finTermsCalculateResponse.path("productId").toString(), productId);

        Allure.step("Step.5 --> Financial Terms Calculate response assertions --> Validate purchaseAmount field is correct");
        Assert.assertEquals(finTermsCalculateResponse.path("purchaseAmount").toString(), purchaseAmount);

        // Transact V4 response assertions - purchase

        Allure.step("Step.6 --> Transact V4 REST response assertions --> Validate responseCode field is correct");
        Assert.assertEquals(TransactV4response.path("responseCode"), expectedRaasResponseCode);

        Allure.step("Step.7 --> Transact V4 REST response assertions --> Validate responseMessage field is correct");
        Assert.assertEquals(TransactV4response.path("responseMessage"), expectedMessage);

        Allure.step("Step.8 --> Transact V4 REST response assertions --> Validate raasTxnRef field exists");
        Assert.assertNotNull(TransactV4response.path("raasTxnRef"));

        Allure.step("Step.9 --> Transact V4 REST response assertions --> Validate HTTPResponseCode is correct");
        Assert.assertEquals(TransactV4response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // Raas DB assertions 1

        // Raas_Request

        Allure.step("Step.10 --> RAAS DB assertions --> Table: raas_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", TransactV4response.path("raasTxnRef")), TransactV4response.path("raasTxnRef"));

        Allure.step("Step.11 --> RAAS DB assertions --> Table: raas_request --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.12 --> RAAS DB assertions --> Table: raas_request --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);

        Allure.step("Step.13 --> RAAS DB assertions --> Table: raas_request --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.14 --> RAAS DB assertions --> Table: raas_request --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.15 --> RAAS DB assertions --> Table: raas_request --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.16 --> RAAS DB assertions --> Table: raas_request --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.17 --> RAAS DB assertions --> Table: raas_request --> Validate created field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.18 --> RAAS DB assertions --> Table: raas_request --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.19 --> RAAS DB assertions --> Table: raas_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.20 --> RAAS DB assertions --> Table: raas_request --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.21 --> RAAS DB assertions --> Table: raas_request --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.22 --> RAAS DB assertions --> Table: raas_request --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.23 --> RAAS DB assertions --> Table: raas_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.24 --> RAAS DB assertions --> Table: raas_request --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.25 --> RAAS DB assertions --> Table: raas_request --> Validate cdc_update_timestamp field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.26 --> RAAS DB assertions --> Table: raas_request --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.27 --> RAAS DB assertions --> Table: raas_request --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.28 --> RAAS DB assertions --> Table: raas_request --> Validate api_call field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "api_call"), "transact-v4");

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response

        Allure.step("Step.29 --> RAAS DB assertions --> Table: raas_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", TransactV4response.path("raasTxnRef")), TransactV4response.path("raasTxnRef"));

        Allure.step("Step.30 --> RAAS DB assertions --> Table: raas_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.31 --> RAAS DB assertions --> Table: raas_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "event_type"));

        // SQL - UPDATE run recon
        sqlDataAccess.verifyPostgreCustomSql("update cpgtx.tran_log set transactionState = 'P' where clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref");
        Thread.sleep(10000);

        // CTX DB assertions (CTX - Lookup Check for success)

        Allure.step("Step.32 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionResponseCode field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);

        Allure.step("Step.33 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionId field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionId"));

        Allure.step("Step.34 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate clientStan field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "clientStan"));

        Allure.step("Step.35 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originId field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);

        Allure.step("Step.36 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originatingService field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "originatingService"));

        Allure.step("Step.37 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate purchaseAmount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "purchaseAmount"), purchaseAmount);

        Allure.step("Step.38 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionState field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionState"), "C");

        Allure.step("Step.39 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionType field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "transactionType"), "P");

        Allure.step("Step.40 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "client_id"), clientId);

        Allure.step("Step.41 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        Thread.sleep(10000);

        // Transaction_result_request

        Allure.step("Step.42 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);

        Allure.step("Step.43 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.44 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.45 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.46 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV4response.path("raasTxnRef"));

        Allure.step("Step.47 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.48 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.49 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate reserve_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);

        Allure.step("Step.50 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate fee_amount field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.51 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate client_share_amount field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.52 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate settlement_amount field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.53 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate vend_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);

        Allure.step("Step.54 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // Transaction_result_response

        Allure.step("Step.55 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);

        Allure.step("Step.56 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV4response.path("raasTxnRef"));

        Allure.step("Step.57 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.58 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.59 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "response_code"), "202");

        Allure.step("Step.60 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate cdc_update_timestamp field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas DB assertions 2

        //Transaction_log

        Allure.step("Step.61 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV4response.path("raasTxnRef"));

        Allure.step("Step.62 --> RAAS DB assertions --> Table: transaction_log --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.63 --> RAAS DB assertions --> Table: transaction_log --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);

        Allure.step("Step.64 --> RAAS DB assertions --> Table: transaction_log --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.65 --> RAAS DB assertions --> Table: transaction_log --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.66 --> RAAS DB assertions --> Table: transaction_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.67 --> RAAS DB assertions --> Table: transaction_log --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.68 --> RAAS DB assertions --> Table: transaction_log --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.69 --> RAAS DB assertions --> Table: transaction_log --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.70 --> RAAS DB assertions --> Table: transaction_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.71 --> RAAS DB assertions --> Table: transaction_log --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.72 --> RAAS DB assertions --> Table: transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.73 --> RAAS DB assertions --> Table: transaction_log --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.74 --> RAAS DB assertions --> Table: transaction_log --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.75 --> RAAS DB assertions --> Table: transaction_log --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.76 --> RAAS DB assertions --> Table: transaction_log --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.77 --> RAAS DB assertions --> Table: transaction_log --> Validate reserve_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);

        Allure.step("Step.78 --> RAAS DB assertions --> Table: transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.79 --> RAAS DB assertions --> Table: transaction_log --> Validate fee_amount field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.80 --> RAAS DB assertions --> Table: transaction_log --> Validate client_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.81 --> RAAS DB assertions --> Table: transaction_log --> Validate settlement_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.82 --> RAAS DB assertions --> Table: transaction_log --> Validate vend_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);

        Allure.step("Step.83 --> RAAS DB assertions --> Table: transaction_log --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "vendor_share_amount"));

        //Allure.step("Step.33 --> RAAS DB assertions --> Table: transaction_log --> Validate status field is correct");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);

        Allure.step("Step.84 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_request_created"));

        Allure.step("Step.85 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");
        Allure.step("Step.86 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_request_created"));

        Allure.step("Step.87 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_response_created"));

        Allure.step("Step.88 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);

        Allure.step("Step.89 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");
        Allure.step("Step.90 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_request_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);

        Allure.step("Step.91 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");

        Allure.step("Step.92 --> RAAS DB assertions --> Table: transaction_log --> Validate currency_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'","currency_code"), currencyCode);

        Allure.step("Step.93 --> RAAS DB assertions --> Table: transaction_log --> Validate funding_source_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "funding_source_id"), fundingSourceId);

        Allure.step("Step.94 --> RAAS DB assertions --> Table: transaction_log --> Validate additional_data_financial_calculations field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Reset simulator to success
        startSim.SimulatorScenario("MTNNG SUCCESS");

    }
}
