/**
 * TestSuite: ReserveAndTransactV3 Regression test suite.
 * Includes all end to end api tests for Reserve and Transact V3
 * Author: Juan-Claude Botha
 */

package API.dotCoreTests;

import api.requestLibary.CORE.coreReserveAndTransactV4POJO;
import api.testUtilities.sqlDataAccessLayer.sqlDataAccess;
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
import api.requestLibary.CORE.coreReserveAndTransactV3POJO;

import api.testUtilities.dataBuilders.testDataFactory;

import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

import api.testUtilities.propertyConfigWrapper.configWrapper;
import util.Listeners.allureApiTestListener;
import api.testUtilities.Simulators.startSimulator;

@Listeners(allureApiTestListener.class)
public class regression_Raas_Reserve_and_Transact_V3 extends testConfig {

    // Create properties object in order to inject environment specific variables upon build
    Properties properties = configWrapper.loadPropertiesFile("config.properties");

    // Instantiate simulator instance
    startSimulator startSim = new startSimulator();

    // Data staging for use in test
    @DataProvider(name = "ReserveAndTransactV3StandardSuccessTestCases", parallel = false)
    public Object[] ReserveAndTransactV3StandardSuccessTestCases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNGPurchaseSuccessTest","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNGPurchaseSuccessTest","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNGPurchaseSuccessTest","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNGPurchaseSuccessTest","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNGPurchaseSuccessTest","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNGPurchaseSuccessTest","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNGPurchaseSuccessTest","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNGPurchaseSuccessTest","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNGPurchaseSuccessTest","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNGPurchaseSuccessTest","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNGPurchaseSuccessTest","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNGPurchaseSuccessTest","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNGPurchaseSuccessTest","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNGPurchaseSuccessTest","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNGPurchaseSuccessTest","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNGPurchaseSuccessTest","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNGPurchaseSuccessTest","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNGPurchaseSuccessTest","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNGPurchaseSuccessTest","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNGPurchaseSuccessTest","simulatorScenario"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNGPurchaseSuccessTest","simulatorResetState")},

        };
    }

    @Step("Reserve and Transact V3 Standard Success test cases")
    @Test(dataProvider = "ReserveAndTransactV3StandardSuccessTestCases", priority = 0)
    public void ReserveAndTransactV3StandardSuccessTests(String accountIdentifier,
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
                                                         String feeAmount,
                                                         String currencyCode,
                                                         String expectedRaasResponseCode,
                                                         String expectedMessage,
                                                         String expectedHTTPResponseCode,
                                                         String expectedRaasResultRequestResponseCode,
                                                         String expectedRaasResultResponseResponseCode,
                                                         String expectedCTXTransactionResponseCode,
                                                         String simulatorScenario,
                                                         String simulatorResetState) throws IOException, InterruptedException, JSchException {

        // Set simulator to success
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

        // Create ReserveAndtransactV3 payload object - contains transactV3 request body
        coreReserveAndTransactV3POJO ReserveAndTransactV3Payload = new coreReserveAndTransactV3POJO(
                accountIdentifier,
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
                feeAmount);

        // Create transactV3 response body object - contains api response data for use in assertions or other calls
        Response ReserveAndTransactV3response =
                given(CORE_getEndPoints_ReserveAndTransactV3)
                        .contentType(ContentType.JSON)
                        .body(ReserveAndTransactV3Payload)
                        .when()
                        .post()
                        .then()
                        .extract()
                        .response();

        // Assertions

        // Finance Terms Calculate response assertions
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "");
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "null");
        Assert.assertEquals(finTermsCalculateResponse.path("clientId").toString(), clientId);
        Assert.assertEquals(finTermsCalculateResponse.path("productId").toString(), productId);
        Assert.assertEquals(finTermsCalculateResponse.path("purchaseAmount").toString(), purchaseAmount);

        // Reserve and Transact V4 response assertions - purchase
        Assert.assertEquals(ReserveAndTransactV3response.path("responseCode"), expectedRaasResponseCode);
        Assert.assertEquals(ReserveAndTransactV3response.path("responseMessage"), expectedMessage);
        Assert.assertNotNull(ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertEquals(ReserveAndTransactV3response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // raas db assertions
        //Transaction_log
        Thread.sleep(10000);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "timestamp"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vendor_share_amount"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_request_created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "transaction_result_request_created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "transaction_result_response_created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'","currency_code"), currencyCode);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Raas_Request
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", ReserveAndTransactV3response.path("raasTxnRef")), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "timestamp"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "api_call"), "reserveAndTransact-v3");

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", ReserveAndTransactV3response.path("raasTxnRef")), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"));

        // CTX DB assertions
        Thread.sleep(5000);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "transactionId"));
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "clientStan"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "originatingService"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "purchaseAmount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "transactionState"), "C");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "transactionType"), "P");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        // raas reserve_fund_request
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_REQUEST_EVENT");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "product_type"), "3");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // raas reserve_fund_response
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_RESPONSE_EVENT");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Transaction_result_request
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // Transaction_result_response
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "response_code"), "202");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

    }

    // Data staging for use in test
    @DataProvider(name = "ReserveAndTransactV3NullFieldTestCases", parallel = true)
    public Object[] ReserveAndTransactV3NullFieldTestCases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_PurchaseAmount_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_PurchaseAmount_Test","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_PurchaseAmount_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_PurchaseAmount_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_PurchaseAmount_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_PurchaseAmount_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_PurchaseAmount_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_PurchaseAmount_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_PurchaseAmount_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_PurchaseAmount_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_PurchaseAmount_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_PurchaseAmount_Test","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_PurchaseAmount_Test","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_PurchaseAmount_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_PurchaseAmount_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_PurchaseAmount_Test","expectedHTTPResponseCode")},

                {testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelId_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelId_Test","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelId_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelId_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelId_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelId_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelId_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelId_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelId_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelId_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelId_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelId_Test","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelId_Test","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelId_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelId_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelId_Test","expectedHTTPResponseCode")},

                {testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelName_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelName_Test","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelName_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelName_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelName_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelName_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelName_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelName_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelName_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelName_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelName_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelName_Test","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelName_Test","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelName_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelName_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelName_Test","expectedHTTPResponseCode")},

                {testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelSessionId_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelSessionId_Test","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelSessionId_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelSessionId_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelSessionId_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelSessionId_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelSessionId_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelSessionId_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelSessionId_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelSessionId_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelSessionId_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelSessionId_Test","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelSessionId_Test","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelSessionId_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelSessionId_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_channelSessionId_Test","expectedHTTPResponseCode")},

                {testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_clientId_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_clientId_Test","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_clientId_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_clientId_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_clientId_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_clientId_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_clientId_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_clientId_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_clientId_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_clientId_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_clientId_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_clientId_Test","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_clientId_Test","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_clientId_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_clientId_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_clientId_Test","expectedHTTPResponseCode")},

                {testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_productId_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_productId_Test","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_productId_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_productId_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_productId_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_productId_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_productId_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_productId_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_productId_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_productId_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_productId_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_productId_Test","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_productId_Test","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_productId_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_productId_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_productId_Test","expectedHTTPResponseCode")},

                {testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_sourceIdentifier_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_sourceIdentifier_Test","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_sourceIdentifier_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_sourceIdentifier_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_sourceIdentifier_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_sourceIdentifier_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_sourceIdentifier_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_sourceIdentifier_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_sourceIdentifier_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_sourceIdentifier_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_sourceIdentifier_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_sourceIdentifier_Test","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_sourceIdentifier_Test","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_sourceIdentifier_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_sourceIdentifier_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_sourceIdentifier_Test","expectedHTTPResponseCode")},

                {testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_targetIdentifier_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_targetIdentifier_Test","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_targetIdentifier_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_targetIdentifier_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_targetIdentifier_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_targetIdentifier_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_targetIdentifier_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_targetIdentifier_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_targetIdentifier_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_targetIdentifier_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_targetIdentifier_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_targetIdentifier_Test","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_targetIdentifier_Test","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_targetIdentifier_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_targetIdentifier_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_targetIdentifier_Test","expectedHTTPResponseCode")},

                {testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_timestamp_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_timestamp_Test","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_timestamp_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_timestamp_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_timestamp_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_timestamp_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_timestamp_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_timestamp_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_timestamp_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_timestamp_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_timestamp_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_timestamp_Test","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_timestamp_Test","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_timestamp_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_timestamp_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_timestamp_Test","expectedHTTPResponseCode")},

                {testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_feeAmount_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_feeAmount_Test","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_feeAmount_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_feeAmount_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_feeAmount_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_feeAmount_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_feeAmount_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_feeAmount_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_feeAmount_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_feeAmount_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_feeAmount_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_feeAmount_Test","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_feeAmount_Test","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_feeAmount_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_feeAmount_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_feeAmount_Test","expectedHTTPResponseCode")},

                {testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_currencyCode_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_currencyCode_Test","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_currencyCode_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_currencyCode_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_currencyCode_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_currencyCode_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_currencyCode_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_currencyCode_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_currencyCode_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_currencyCode_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_currencyCode_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_currencyCode_Test","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_currencyCode_Test","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_currencyCode_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_currencyCode_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Null_currencyCode_Test","expectedHTTPResponseCode")},


        };
    }

    @Step("Reserve and Transact V3 null field negative tests")
    @Test(dataProvider = "ReserveAndTransactV3NullFieldTestCases", priority = 2)
    public void ReserveAndTransactV3NullFieldNegativeTests(String accountIdentifier,
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
                                                           String feeAmount,
                                                           String currencyCode,
                                                           String expectedRaasResponseCode,
                                                           String expectedMessage,
                                                           String expectedHTTPResponseCode) throws IOException, InterruptedException {

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

        // Create ReserveAndtransactV3 payload object - contains transactV3 request body
        coreReserveAndTransactV3POJO ReserveAndTransactV3Payload = new coreReserveAndTransactV3POJO(
                accountIdentifier,
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
                feeAmount);

        // Create transactV3 response body object - contains api response data for use in assertions or other calls
        Response ReserveAndTransactV3response =
                given(CORE_getEndPoints_ReserveAndTransactV3)
                        .contentType(ContentType.JSON)
                        .body(ReserveAndTransactV3Payload)
                        .when()
                        .post()
                        .then()
                        .extract()
                        .response();

        // Transact V3 response assertions - purchase
        Assert.assertEquals(ReserveAndTransactV3response.path("responseCode"), expectedRaasResponseCode);
        Assert.assertEquals(ReserveAndTransactV3response.path("responseMessage"), expectedMessage);
        Assert.assertEquals(ReserveAndTransactV3response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

    }

    // Data staging for use in test
    @DataProvider(name = "ReserveAndTransactV3ResponseCodesNegativeTestCases", parallel = false)
    public Object[] ReserveAndTransactV3ResponseCodesNegativeTestCases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Timeout_Negative_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Timeout_Negative_Test","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Timeout_Negative_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Timeout_Negative_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Timeout_Negative_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Timeout_Negative_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Timeout_Negative_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Timeout_Negative_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Timeout_Negative_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Timeout_Negative_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Timeout_Negative_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Timeout_Negative_Test","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Timeout_Negative_Test","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Timeout_Negative_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Timeout_Negative_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Timeout_Negative_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Timeout_Negative_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Timeout_Negative_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Timeout_Negative_Test","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Timeout_Negative_Test","simulatorScenario"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Timeout_Negative_Test","simulatorResetState")},

                {testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Invalid_MSISDN_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Invalid_MSISDN_Test","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Invalid_MSISDN_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Invalid_MSISDN_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Invalid_MSISDN_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Invalid_MSISDN_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Invalid_MSISDN_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Invalid_MSISDN_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Invalid_MSISDN_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Invalid_MSISDN_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Invalid_MSISDN_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Invalid_MSISDN_Test","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Invalid_MSISDN_Test","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Invalid_MSISDN_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Invalid_MSISDN_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Invalid_MSISDN_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Invalid_MSISDN_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Invalid_MSISDN_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Invalid_MSISDN_Test","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Invalid_MSISDN_Test","simulatorScenario"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Vendor_Invalid_MSISDN_Test","simulatorResetState")},

        };
    }

    @Step("Reserve and Transact V3: Response codes: Negative test cases")
    @Test(dataProvider = "ReserveAndTransactV3ResponseCodesNegativeTestCases", priority = 1)
    public void ReserveAndTransactV3ResponseCodesNegativeTests(String accountIdentifier,
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
                                                               String feeAmount,
                                                               String currencyCode,
                                                               String expectedRaasResponseCode,
                                                               String expectedMessage,
                                                               String expectedHTTPResponseCode,
                                                               String expectedRaasResultRequestResponseCode,
                                                               String expectedRaasResultResponseResponseCode,
                                                               String expectedCTXTransactionResponseCode,
                                                               String simulatorScenario,
                                                               String simulatorResetState) throws IOException, InterruptedException, JSchException {

        // Set simulator to failure
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

        // Create ReserveAndtransactV3 payload object - contains transactV3 request body
        coreReserveAndTransactV3POJO ReserveAndTransactV3Payload = new coreReserveAndTransactV3POJO(
                accountIdentifier,
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
                feeAmount);

        // Create transactV3 response body object - contains api response data for use in assertions or other calls
        Response ReserveAndTransactV3response =
                given(CORE_getEndPoints_ReserveAndTransactV3)
                        .contentType(ContentType.JSON)
                        .body(ReserveAndTransactV3Payload)
                        .when()
                        .post()
                        .then()
                        .extract()
                        .response();

        // Assertions

        // Finance Terms Calculate response assertions
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "");
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "null");
        Assert.assertEquals(finTermsCalculateResponse.path("clientId").toString(), clientId);
        Assert.assertEquals(finTermsCalculateResponse.path("productId").toString(), productId);
        Assert.assertEquals(finTermsCalculateResponse.path("purchaseAmount").toString(), purchaseAmount);

        // Reserve and Transact V4 response assertions - purchase
        Assert.assertEquals(ReserveAndTransactV3response.path("responseCode"), expectedRaasResponseCode);
        Assert.assertEquals(ReserveAndTransactV3response.path("responseMessage"), expectedMessage);
        Assert.assertNotNull(ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertEquals(ReserveAndTransactV3response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // raas db assertions
        //Transaction_log
        Thread.sleep(10000);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "timestamp"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vendor_share_amount"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_request_created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_request_created"));
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'","currency_code"), currencyCode);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Raas_Request
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", ReserveAndTransactV3response.path("raasTxnRef")), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "timestamp"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "api_call"), "reserveAndTransact-v3");

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", ReserveAndTransactV3response.path("raasTxnRef")), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"));

        // CTX DB assertions
        Thread.sleep(5000);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "transactionId"));
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "clientStan"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "originatingService"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "purchaseAmount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "transactionState"), "C");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "transactionType"), "P");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        // raas reserve_fund_request
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_REQUEST_EVENT");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "product_type"), "3");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // raas reserve_fund_response
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_RESPONSE_EVENT");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Transaction_result_request
       /* Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "vendor_share_amount")); */



        // Transaction_result_response
        /* Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "response_code"), "202");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp")); */

        // Reset simulator
        startSim.SimulatorScenario(simulatorResetState);
        Thread.sleep(5000);

    }

    // Data staging for use in test
    @DataProvider(name = "ReserveAndTransactV3LookupBackOffTestCases", parallel = false)
    public Object[] ReserveAndTransactV3LookupBackOffTestCases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Lookup_And_Backoff_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Lookup_And_Backoff_Test","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Lookup_And_Backoff_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Lookup_And_Backoff_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Lookup_And_Backoff_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Lookup_And_Backoff_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Lookup_And_Backoff_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Lookup_And_Backoff_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Lookup_And_Backoff_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Lookup_And_Backoff_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Lookup_And_Backoff_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Lookup_And_Backoff_Test","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Lookup_And_Backoff_Test","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Lookup_And_Backoff_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Lookup_And_Backoff_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Lookup_And_Backoff_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Lookup_And_Backoff_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Lookup_And_Backoff_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Lookup_And_Backoff_Test","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Lookup_And_Backoff_Test","simulatorScenario"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Lookup_And_Backoff_Test","simulatorResetState")},


        };
    }

    @Step("Reserve and Transact V3: LookupBackOff test cases")
    @Test(dataProvider = "ReserveAndTransactV3LookupBackOffTestCases", priority = 3)
    public void ReserveAndTransactV3LookupBackOffTests(String accountIdentifier,
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
                                                       String feeAmount,
                                                       String currencyCode,
                                                       String expectedRaasResponseCode,
                                                       String expectedMessage,
                                                       String expectedHTTPResponseCode,
                                                       String expectedRaasResultRequestResponseCode,
                                                       String expectedRaasResultResponseResponseCode,
                                                       String expectedCTXTransactionResponseCode,
                                                       String simulatorScenario,
                                                       String simulatorResetState) throws IOException, InterruptedException, JSchException {

        // Set simulator to failure
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

        // Create ReserveAndtransactV3 payload object - contains transactV3 request body
        coreReserveAndTransactV3POJO ReserveAndTransactV3Payload = new coreReserveAndTransactV3POJO(
                accountIdentifier,
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
                feeAmount);

        // Create transactV3 response body object - contains api response data for use in assertions or other calls
        Response ReserveAndTransactV3response =
                given(CORE_getEndPoints_ReserveAndTransactV3)
                        .contentType(ContentType.JSON)
                        .body(ReserveAndTransactV3Payload)
                        .when()
                        .post()
                        .then()
                        .extract()
                        .response();


        // Assertions

        // Finance Terms Calculate response assertions
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "");
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "null");
        Assert.assertEquals(finTermsCalculateResponse.path("clientId").toString(), clientId);
        Assert.assertEquals(finTermsCalculateResponse.path("productId").toString(), productId);
        Assert.assertEquals(finTermsCalculateResponse.path("purchaseAmount").toString(), purchaseAmount);

        // Reserve and Transact V4 response assertions - purchase
        Assert.assertEquals(ReserveAndTransactV3response.path("responseCode"), expectedRaasResponseCode);
        Assert.assertEquals(ReserveAndTransactV3response.path("responseMessage"), expectedMessage);
        Assert.assertNotNull(ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertEquals(ReserveAndTransactV3response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // Reset simulator
        startSim.SimulatorScenario(simulatorResetState);
        Thread.sleep(5000);

        // raas db assertions
        //Transaction_log
        Thread.sleep(15000);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "timestamp"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vendor_share_amount"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_request_created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_request_created"));
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'","currency_code"), currencyCode);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Raas_Request
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", ReserveAndTransactV3response.path("raasTxnRef")), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "timestamp"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "api_call"), "reserveAndTransact-v3");

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", ReserveAndTransactV3response.path("raasTxnRef")), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"));

        // CTX DB assertions
        Thread.sleep(15000);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "transactionId"));
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "clientStan"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "originatingService"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "purchaseAmount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "transactionState"), "C");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "transactionType"), "P");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        // raas reserve_fund_request
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_REQUEST_EVENT");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "product_type"), "3");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // raas reserve_fund_response
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_RESPONSE_EVENT");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Transaction_result_request
       /* Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "vendor_share_amount")); */



        // Transaction_result_response
        /* Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "response_code"), "202");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp")); */

        startSim.SimulatorScenario(simulatorResetState);

    }

    // Data staging for use in test
    @DataProvider(name = "ReserveAndTransactV3BankTestCases", parallel = false)
    public Object[] ReserveAndTransactV3BankTestCases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Bank_101_Positive_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Bank_101_Positive_Test","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Bank_101_Positive_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Bank_101_Positive_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Bank_101_Positive_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Bank_101_Positive_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Bank_101_Positive_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Bank_101_Positive_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Bank_101_Positive_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Bank_101_Positive_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Bank_101_Positive_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Bank_101_Positive_Test","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Bank_101_Positive_Test","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Bank_101_Positive_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Bank_101_Positive_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Bank_101_Positive_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Bank_101_Positive_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Bank_101_Positive_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Bank_101_Positive_Test","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Bank_101_Positive_Test","simulatorScenario"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Bank_101_Positive_Test","simulatorResetState")},

        };
    }

    @Step("Reserve and Transact V3 Bank test cases")
    @Test(dataProvider = "ReserveAndTransactV3BankTestCases", priority = 4)
    public void ReserveAndTransactV3BankTests(String accountIdentifier,
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
                                              String feeAmount,
                                              String currencyCode,
                                              String expectedRaasResponseCode,
                                              String expectedMessage,
                                              String expectedHTTPResponseCode,
                                              String expectedRaasResultRequestResponseCode,
                                              String expectedRaasResultResponseResponseCode,
                                              String expectedCTXTransactionResponseCode,
                                              String simulatorScenario,
                                              String simulatorResetState) throws IOException, InterruptedException, JSchException {

        // Set simulator to success
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

        // Create ReserveAndtransactV3 payload object - contains transactV3 request body
        coreReserveAndTransactV3POJO ReserveAndTransactV3Payload = new coreReserveAndTransactV3POJO(
                accountIdentifier,
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
                feeAmount);

        // Create transactV3 response body object - contains api response data for use in assertions or other calls
        Response ReserveAndTransactV3response =
                given(CORE_getEndPoints_ReserveAndTransactV3)
                        .contentType(ContentType.JSON)
                        .body(ReserveAndTransactV3Payload)
                        .when()
                        .post()
                        .then()
                        .extract()
                        .response();

        // Assertions

        // Finance Terms Calculate response assertions
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "");
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "null");
        Assert.assertEquals(finTermsCalculateResponse.path("clientId").toString(), clientId);
        Assert.assertEquals(finTermsCalculateResponse.path("productId").toString(), productId);
        Assert.assertEquals(finTermsCalculateResponse.path("purchaseAmount").toString(), purchaseAmount);

        // Reserve and Transact V4 response assertions - purchase
        Assert.assertEquals(ReserveAndTransactV3response.path("responseCode"), expectedRaasResponseCode);
        Assert.assertEquals(ReserveAndTransactV3response.path("responseMessage"), expectedMessage);
        Assert.assertNotNull(ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertEquals(ReserveAndTransactV3response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // raas db assertions
        //Transaction_log
        Thread.sleep(10000);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "timestamp"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vendor_share_amount"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_request_created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "transaction_result_request_created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "transaction_result_response_created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'","currency_code"), currencyCode);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Raas_Request
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", ReserveAndTransactV3response.path("raasTxnRef")), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "timestamp"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "api_call"), "reserveAndTransact-v3");

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", ReserveAndTransactV3response.path("raasTxnRef")), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"));

        // CTX DB assertions
        Thread.sleep(5000);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "transactionId"));
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "clientStan"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "originatingService"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "purchaseAmount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "transactionState"), "C");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "transactionType"), "P");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        // raas reserve_fund_request
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_REQUEST_EVENT");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "product_type"), "3");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // raas reserve_fund_response
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_RESPONSE_EVENT");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Transaction_result_request
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // Transaction_result_response
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "response_code"), "202");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        startSim.SimulatorScenario(simulatorResetState);
    }

    // Data staging for use in test
    @DataProvider(name = "ReserveAndTransactV3RetryableDeclineToOKTestCases", parallel = false)
    public Object[] ReserveAndTransactV3RetryableDeclineToOKTestCases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_OK_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_OK_Test","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_OK_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_OK_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_OK_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_OK_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_OK_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_OK_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_OK_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_OK_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_OK_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_OK_Test","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_OK_Test","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_OK_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_OK_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_OK_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_OK_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_OK_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_OK_Test","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_OK_Test","simulatorScenario"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_OK_Test","simulatorResetState")},

        };
    }

    @Step("Reserve and Transact V3 Retryable Decline To OK test cases")
    @Test(dataProvider = "ReserveAndTransactV3RetryableDeclineToOKTestCases", priority = 5)
    public void ReserveAndTransactV3RetryableDeclineToOKTests(String accountIdentifier,
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
                                                              String feeAmount,
                                                              String currencyCode,
                                                              String expectedRaasResponseCode,
                                                              String expectedMessage,
                                                              String expectedHTTPResponseCode,
                                                              String expectedRaasResultRequestResponseCode,
                                                              String expectedRaasResultResponseResponseCode,
                                                              String expectedCTXTransactionResponseCode,
                                                              String simulatorScenario,
                                                              String simulatorResetState) throws IOException, InterruptedException, JSchException {

        // Set simulator to fail (RD)
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

        // Create ReserveAndtransactV3 payload object - contains transactV3 request body
        coreReserveAndTransactV3POJO ReserveAndTransactV3Payload = new coreReserveAndTransactV3POJO(
                accountIdentifier,
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
                feeAmount);

        // Create transactV4 response body object - contains api response data for use in assertions or other calls
        Response ReserveAndTransactV3response =
                given(CORE_getEndPoints_ReserveAndTransactV3)
                        .contentType(ContentType.JSON)
                        .body(ReserveAndTransactV3Payload)
                        .when()
                        .post()
                        .then()
                        .extract()
                        .response();

        // Set simulator to fail (RD)
        startSim.SimulatorScenario(simulatorResetState);
        Thread.sleep(5000);

        // Assertions

        // Finance Terms Calculate response assertions
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "");
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "null");
        Assert.assertEquals(finTermsCalculateResponse.path("clientId").toString(), clientId);
        Assert.assertEquals(finTermsCalculateResponse.path("productId").toString(), productId);
        Assert.assertEquals(finTermsCalculateResponse.path("purchaseAmount").toString(), purchaseAmount);

        // Reserve and Transact V4 response assertions - purchase
        Assert.assertEquals(ReserveAndTransactV3response.path("responseCode"), expectedRaasResponseCode);
        Assert.assertEquals(ReserveAndTransactV3response.path("responseMessage"), expectedMessage);
        Assert.assertNotNull(ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertEquals(ReserveAndTransactV3response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // raas db assertions
        //Transaction_log
        Thread.sleep(10000);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "timestamp"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vendor_share_amount"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_request_created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "transaction_result_request_created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "transaction_result_response_created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'","currency_code"), currencyCode);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Raas_Request
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", ReserveAndTransactV3response.path("raasTxnRef")), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "timestamp"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "api_call"), "reserveAndTransact-v3");

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", ReserveAndTransactV3response.path("raasTxnRef")), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"));

        // CTX DB assertions
        Thread.sleep(15000);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0001'", "transactionResponseCode"), expectedCTXTransactionResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0001'", "transactionId"));
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0001'", "clientStan"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0001'", "originId"), sourceIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0001'", "originatingService"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0001'", "purchaseAmount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0001'", "transactionState"), "C");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0001'", "transactionType"), "P");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0001'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0001'", "product_id"), productId);

        // raas reserve_fund_request
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_REQUEST_EVENT");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "product_type"), "3");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // raas reserve_fund_response
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_RESPONSE_EVENT");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Transaction_result_request
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // Transaction_result_response
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "response_code"), "202");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        startSim.SimulatorScenario(simulatorResetState);
    }

    // Data staging for use in test
    @DataProvider(name = "ReserveAndTransactV3RetryableDeclineToNonRetryableDeclineTestCases", parallel = false)
    public Object[] ReserveAndTransactV3RetryableDeclineToNonRetryableDeclineTestCases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_Non_Retryable_Decline_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_Non_Retryable_Decline_Test","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_Non_Retryable_Decline_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_Non_Retryable_Decline_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_Non_Retryable_Decline_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_Non_Retryable_Decline_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_Non_Retryable_Decline_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_Non_Retryable_Decline_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_Non_Retryable_Decline_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_Non_Retryable_Decline_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_Non_Retryable_Decline_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_Non_Retryable_Decline_Test","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_Non_Retryable_Decline_Test","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_Non_Retryable_Decline_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_Non_Retryable_Decline_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_Non_Retryable_Decline_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_Non_Retryable_Decline_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_Non_Retryable_Decline_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_Non_Retryable_Decline_Test","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_Non_Retryable_Decline_Test","simulatorScenario"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Retryable_Decline_To_Non_Retryable_Decline_Test","simulatorResetState")},

        };
    }

    @Step("Reserve and Transact V3 Retryable Decline To Non Retryable Decline test cases")
    @Test(dataProvider = "ReserveAndTransactV3RetryableDeclineToNonRetryableDeclineTestCases", priority = 6)
    public void ReserveAndTransactV3RetryableDeclineToNonRetryableDeclineTests(String accountIdentifier,
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
                                                                               String feeAmount,
                                                                               String currencyCode,
                                                                               String expectedRaasResponseCode,
                                                                               String expectedMessage,
                                                                               String expectedHTTPResponseCode,
                                                                               String expectedRaasResultRequestResponseCode,
                                                                               String expectedRaasResultResponseResponseCode,
                                                                               String expectedCTXTransactionResponseCode,
                                                                               String simulatorScenario,
                                                                               String simulatorResetState) throws IOException, InterruptedException, JSchException {

        // Set simulator to fail (RD)
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

        // Create ReserveAndtransactV3 payload object - contains transactV3 request body
        coreReserveAndTransactV3POJO ReserveAndTransactV3Payload = new coreReserveAndTransactV3POJO(
                accountIdentifier,
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
                feeAmount);

        // Create transactV4 response body object - contains api response data for use in assertions or other calls
        Response ReserveAndTransactV3response =
                given(CORE_getEndPoints_ReserveAndTransactV3)
                        .contentType(ContentType.JSON)
                        .body(ReserveAndTransactV3Payload)
                        .when()
                        .post()
                        .then()
                        .extract()
                        .response();

        // Set simulator to fail (RD)
        startSim.SimulatorScenario(simulatorResetState);
        Thread.sleep(5000);

        // Assertions

        // Finance Terms Calculate response assertions
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "");
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "null");
        Assert.assertEquals(finTermsCalculateResponse.path("clientId").toString(), clientId);
        Assert.assertEquals(finTermsCalculateResponse.path("productId").toString(), productId);
        Assert.assertEquals(finTermsCalculateResponse.path("purchaseAmount").toString(), purchaseAmount);

        // Reserve and Transact V4 response assertions - purchase
        Assert.assertEquals(ReserveAndTransactV3response.path("responseCode"), expectedRaasResponseCode);
        Assert.assertEquals(ReserveAndTransactV3response.path("responseMessage"), expectedMessage);
        Assert.assertNotNull(ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertEquals(ReserveAndTransactV3response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // raas db assertions
        //Transaction_log
        Thread.sleep(10000);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "timestamp"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vendor_share_amount"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_request_created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "transaction_result_request_created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "transaction_result_response_created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedCTXTransactionResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'","currency_code"), currencyCode);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Raas_Request
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", ReserveAndTransactV3response.path("raasTxnRef")), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "timestamp"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "api_call"), "reserveAndTransact-v3");

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", ReserveAndTransactV3response.path("raasTxnRef")), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"));

        // CTX DB assertions
        Thread.sleep(15000);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0001'", "transactionResponseCode"), expectedCTXTransactionResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0001'", "transactionId"));
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0001'", "clientStan"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0001'", "originId"), sourceIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0001'", "originatingService"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0001'", "purchaseAmount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0001'", "transactionState"), "C");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0001'", "transactionType"), "P");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0001'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0001'", "product_id"), productId);

        // raas reserve_fund_request
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_REQUEST_EVENT");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "product_type"), "3");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // raas reserve_fund_response
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_RESPONSE_EVENT");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Transaction_result_request
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // Transaction_result_response
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "response_code"), "202");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        startSim.SimulatorScenario("MTNNG SUCCESS");
    }

    // Data staging for use in test
    @DataProvider(name = "ReserveAndTransactV3PendingToRetryableDeclineToNonRetryableDeclineTestCases", parallel = false)
    public Object[] ReserveAndTransactV3PendingToRetryableDeclineToNonRetryableDeclineTestCases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline_Test","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline_Test","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline_Test","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline_Test","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline_Test","simulatorScenario"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline_Test","simulatorResetState")},

        };
    }

    @Step("Reserve and Transact V3 Pending To Retryable Decline To Non Retryable Decline test cases")
    @Test(dataProvider = "ReserveAndTransactV3PendingToRetryableDeclineToNonRetryableDeclineTestCases", priority = 7)
    public void ReserveAndTransactV3PendingToRetryableDeclineToNonRetryableDeclineTests(String accountIdentifier,
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
                                                                                        String feeAmount,
                                                                                        String currencyCode,
                                                                                        String expectedRaasResponseCode,
                                                                                        String expectedMessage,
                                                                                        String expectedHTTPResponseCode,
                                                                                        String expectedRaasResultRequestResponseCode,
                                                                                        String expectedRaasResultResponseResponseCode,
                                                                                        String expectedCTXTransactionResponseCode,
                                                                                        String simulatorScenario,
                                                                                        String simulatorResetState) throws IOException, InterruptedException, JSchException {

        // Set simulator to fail (RD)
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

        // Create ReserveAndtransactV3 payload object - contains transactV3 request body
        coreReserveAndTransactV3POJO ReserveAndTransactV3Payload = new coreReserveAndTransactV3POJO(
                accountIdentifier,
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
                feeAmount);

        // Create transactV3 response body object - contains api response data for use in assertions or other calls
        Response ReserveAndTransactV3response =
                given(CORE_getEndPoints_ReserveAndTransactV3)
                        .contentType(ContentType.JSON)
                        .body(ReserveAndTransactV3Payload)
                        .when()
                        .post()
                        .then()
                        .extract()
                        .response();

        // Set simulator to fail (RD)
        startSim.SimulatorScenario(simulatorResetState);
        Thread.sleep(5000);

        // Assertions

        // Finance Terms Calculate response assertions
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "");
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "null");
        Assert.assertEquals(finTermsCalculateResponse.path("clientId").toString(), clientId);
        Assert.assertEquals(finTermsCalculateResponse.path("productId").toString(), productId);
        Assert.assertEquals(finTermsCalculateResponse.path("purchaseAmount").toString(), purchaseAmount);

        // Reserve and Transact V4 response assertions - purchase
        Assert.assertEquals(ReserveAndTransactV3response.path("responseCode"), expectedRaasResponseCode);
        Assert.assertEquals(ReserveAndTransactV3response.path("responseMessage"), expectedMessage);
        Assert.assertNotNull(ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertEquals(ReserveAndTransactV3response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // raas db assertions
        //Transaction_log
        Thread.sleep(10000);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "timestamp"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vendor_share_amount"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_request_created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_request_created"));
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_response_created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedCTXTransactionResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'","currency_code"), currencyCode);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Raas_Request
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", ReserveAndTransactV3response.path("raasTxnRef")), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "timestamp"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "api_call"), "reserveAndTransact-v3");

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", ReserveAndTransactV3response.path("raasTxnRef")), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"));

        // CTX DB assertions
        Thread.sleep(20000);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "transactionId"));
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "clientStan"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "originatingService"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "purchaseAmount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "transactionState"), "C");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "transactionType"), "P");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        // SQL - UPDATE run recon
        sqlDataAccess.verifyPostgreCustomSql("update cpgtx.tran_log set transactionResponseCode = 2201, transactionType = 'PCH' where transactionResponseCode in (2236, 2240, 2213) and clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_txn_ref");
        Thread.sleep(10000);

        // CTX DB (Recheck after update)
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "transactionId"));
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "clientStan"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "originatingService"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "purchaseAmount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "transactionState"), "C");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "transactionType"), "P");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        // raas reserve_fund_request
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_REQUEST_EVENT");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "product_type"), "3");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // raas reserve_fund_response
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_RESPONSE_EVENT");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Transaction_result_request
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // Transaction_result_response
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "response_code"), "202");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        startSim.SimulatorScenario("MTNNG SUCCESS");
    }

    // Data staging for use in test
    @DataProvider(name = "ReserveAndTransactV3PendingToOKTestCases", parallel = false)
    public Object[] ReserveAndTransactV3PendingToOKTestCases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_OK_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_OK_Test","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_OK_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_OK_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_OK_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_OK_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_OK_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_OK_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_OK_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_OK_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_OK_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_OK_Test","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_OK_Test","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_OK_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_OK_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_OK_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_OK_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_OK_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_OK_Test","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_OK_Test","simulatorScenario"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","MTNNG_Pending_To_OK_Test","simulatorResetState")},

        };
    }

    @Step("Reserve and Transact V3 Pending To OK test cases")
    @Test(dataProvider = "ReserveAndTransactV3PendingToOKTestCases", priority = 8)
    public void ReserveAndTransactV3PendingToOKTests(String accountIdentifier,
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
                                                     String feeAmount,
                                                     String currencyCode,
                                                     String expectedRaasResponseCode,
                                                     String expectedMessage,
                                                     String expectedHTTPResponseCode,
                                                     String expectedRaasResultRequestResponseCode,
                                                     String expectedRaasResultResponseResponseCode,
                                                     String expectedCTXTransactionResponseCode,
                                                     String simulatorScenario,
                                                     String simulatorResetState) throws IOException, InterruptedException, JSchException {

        // Set simulator to fail (RD)
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

        // Create ReserveAndtransactV3 payload object - contains transactV3 request body
        coreReserveAndTransactV3POJO ReserveAndTransactV3Payload = new coreReserveAndTransactV3POJO(
                accountIdentifier,
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
                feeAmount);

        // Create transactV3 response body object - contains api response data for use in assertions or other calls
        Response ReserveAndTransactV3response =
                given(CORE_getEndPoints_ReserveAndTransactV3)
                        .contentType(ContentType.JSON)
                        .body(ReserveAndTransactV3Payload)
                        .when()
                        .post()
                        .then()
                        .extract()
                        .response();

        // Set simulator to fail (RD)
        startSim.SimulatorScenario(simulatorResetState);
        Thread.sleep(5000);

        // Assertions

        // Finance Terms Calculate response assertions
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "");
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "null");
        Assert.assertEquals(finTermsCalculateResponse.path("clientId").toString(), clientId);
        Assert.assertEquals(finTermsCalculateResponse.path("productId").toString(), productId);
        Assert.assertEquals(finTermsCalculateResponse.path("purchaseAmount").toString(), purchaseAmount);

        // Reserve and Transact V4 response assertions - purchase
        Assert.assertEquals(ReserveAndTransactV3response.path("responseCode"), expectedRaasResponseCode);
        Assert.assertEquals(ReserveAndTransactV3response.path("responseMessage"), expectedMessage);
        Assert.assertNotNull(ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertEquals(ReserveAndTransactV3response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // raas db assertions
        //Transaction_log
        Thread.sleep(10000);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "timestamp"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vendor_share_amount"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_request_created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_request_created"));
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_response_created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedCTXTransactionResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'","currency_code"), currencyCode);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Raas_Request
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", ReserveAndTransactV3response.path("raasTxnRef")), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "timestamp"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "api_call"), "reserveAndTransact-v3");

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", ReserveAndTransactV3response.path("raasTxnRef")), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"));

        // CTX DB assertions
        Thread.sleep(20000);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "transactionId"));
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "clientStan"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "originatingService"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "purchaseAmount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "transactionState"), "C");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "transactionType"), "P");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        // SQL - UPDATE run recon
        sqlDataAccess.verifyPostgreCustomSql("update cpgtx.tran_log set transactionResponseCode = 2201, transactionType = 'PCH' where transactionResponseCode in (2236, 2240) and clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_txn_ref");
        Thread.sleep(10000);

        // CTX DB (Recheck after update)
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "transactionId"));
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "clientStan"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "originatingService"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "purchaseAmount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "transactionState"), "C");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "transactionType"), "P");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        // raas reserve_fund_request
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_REQUEST_EVENT");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "product_type"), "3");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // raas reserve_fund_response
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_RESPONSE_EVENT");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Transaction_result_request
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // Transaction_result_response
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "response_code"), "202");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));


    }
}