/**
 * TestSuite: TransactV4 Regression test suite.
 * Includes all end to end api tests for Reserve and Transact V4
 * Author: Juan-Claude Botha
 */

package API.dotCoreTests;

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
import api.requestLibary.CORE.coreReserveAndTransactV4POJO;

import api.testUtilities.dataBuilders.testDataFactory;

import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

import api.testUtilities.propertyConfigWrapper.configWrapper;
import util.Listeners.allureApiTestListener;
import api.testUtilities.Simulators.startSimulator;

@Listeners(allureApiTestListener.class)
public class regression_Raas_Reserve_and_Transact_V4 extends testConfig {

    // Create properties object in order to inject environment specific variables upon build
    Properties properties = configWrapper.loadPropertiesFile("config.properties");

    // Instantiate simulator instance
    startSimulator startSim = new startSimulator();

    // Data staging for use in test
    @DataProvider(name = "ReserveAndTransactV4StandardSuccessTestCases", parallel = false)
    public Object[] ReserveAndTransactV4StandardSuccessTestCases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNGPurchaseSuccessTest","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNGPurchaseSuccessTest","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNGPurchaseSuccessTest","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNGPurchaseSuccessTest","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNGPurchaseSuccessTest","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNGPurchaseSuccessTest","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNGPurchaseSuccessTest","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNGPurchaseSuccessTest","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNGPurchaseSuccessTest","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNGPurchaseSuccessTest","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNGPurchaseSuccessTest","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNGPurchaseSuccessTest","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNGPurchaseSuccessTest","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNGPurchaseSuccessTest","fundingSourceId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNGPurchaseSuccessTest","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNGPurchaseSuccessTest","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNGPurchaseSuccessTest","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNGPurchaseSuccessTest","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNGPurchaseSuccessTest","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNGPurchaseSuccessTest","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNGPurchaseSuccessTest","simulatorScenario"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNGPurchaseSuccessTest","simulatorResetState")},

        };
    }

    @Step("Reserve and Transact V4 Standard Success test cases")
    @Test(dataProvider = "ReserveAndTransactV4StandardSuccessTestCases", priority = 0)
    public void ReserveAndTransactV4StandardSuccessTests(String accountIdentifier,
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
                                String fundingSourceId,
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

        // Create ReserveAndtransactV4 payload object - contains transactV4 request body
        coreReserveAndTransactV4POJO ReserveAndTransactV4Payload = new coreReserveAndTransactV4POJO(
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
                feeAmount,
                currencyCode,
                fundingSourceId);

        // Create transactV4 response body object - contains api response data for use in assertions or other calls
        Response ReserveAndTransactV4response =
                given(CORE_getEndPoints_ReserveAndTransactV4)
                .contentType(ContentType.JSON)
                .body(ReserveAndTransactV4Payload)
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
        Assert.assertEquals(ReserveAndTransactV4response.path("responseCode"), expectedRaasResponseCode);
        Assert.assertEquals(ReserveAndTransactV4response.path("responseMessage"), expectedMessage);
        Assert.assertNotNull(ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertEquals(ReserveAndTransactV4response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // raas db assertions
        //Transaction_log
        Thread.sleep(10000);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "timestamp"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "vendor_share_amount"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_request_created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_request_created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_response_created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'","currency_code"), currencyCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "funding_source_id"), fundingSourceId);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Raas_Request
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", ReserveAndTransactV4response.path("raasTxnRef")), ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "timestamp"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "api_call"), "reserveAndTransact-v4");

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", ReserveAndTransactV4response.path("raasTxnRef")), ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "event_type"));

        // CTX DB assertions
        Thread.sleep(5000);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "transactionId"));
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "clientStan"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "originatingService"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "purchaseAmount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "transactionState"), "C");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "transactionType"), "P");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        // raas reserve_fund_request
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_REQUEST_EVENT");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "product_type"), "3");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // raas reserve_fund_response
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_RESPONSE_EVENT");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Transaction_result_request
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "event_type"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // Transaction_result_response
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "response_code"), "202");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

    }

    // Data staging for use in test
    @DataProvider(name = "ReserveAndTransactV4NullFieldTestCases", parallel = true)
    public Object[] ReserveAndTransactV4NullFieldTestCases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_PurchaseAmount_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_PurchaseAmount_Test","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_PurchaseAmount_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_PurchaseAmount_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_PurchaseAmount_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_PurchaseAmount_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_PurchaseAmount_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_PurchaseAmount_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_PurchaseAmount_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_PurchaseAmount_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_PurchaseAmount_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_PurchaseAmount_Test","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_PurchaseAmount_Test","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_PurchaseAmount_Test","fundingSourceId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_PurchaseAmount_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_PurchaseAmount_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_PurchaseAmount_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_PurchaseAmount_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_PurchaseAmount_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_PurchaseAmount_Test","expectedCTXTransactionResponseCode")},

                {testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelId_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelId_Test","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelId_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelId_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelId_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelId_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelId_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelId_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelId_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelId_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelId_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelId_Test","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelId_Test","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelId_Test","fundingSourceId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelId_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelId_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelId_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelId_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelId_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelId_Test","expectedCTXTransactionResponseCode")},

                {testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelName_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelName_Test","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelName_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelName_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelName_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelName_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelName_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelName_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelName_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelName_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelName_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelName_Test","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelName_Test","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelName_Test","fundingSourceId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelName_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelName_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelName_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelName_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelName_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelName_Test","expectedCTXTransactionResponseCode")},

                {testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelSessionId_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelSessionId_Test","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelSessionId_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelSessionId_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelSessionId_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelSessionId_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelSessionId_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelSessionId_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelSessionId_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelSessionId_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelSessionId_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelSessionId_Test","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelSessionId_Test","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelSessionId_Test","fundingSourceId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelSessionId_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelSessionId_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelSessionId_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelSessionId_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelSessionId_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_channelSessionId_Test","expectedCTXTransactionResponseCode")},

                {testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_clientId_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_clientId_Test","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_clientId_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_clientId_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_clientId_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_clientId_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_clientId_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_clientId_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_clientId_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_clientId_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_clientId_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_clientId_Test","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_clientId_Test","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_clientId_Test","fundingSourceId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_clientId_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_clientId_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_clientId_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_clientId_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_clientId_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_clientId_Test","expectedCTXTransactionResponseCode")},

                {testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_productId_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_productId_Test","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_productId_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_productId_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_productId_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_productId_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_productId_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_productId_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_productId_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_productId_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_productId_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_productId_Test","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_productId_Test","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_productId_Test","fundingSourceId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_productId_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_productId_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_productId_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_productId_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_productId_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_productId_Test","expectedCTXTransactionResponseCode")},

               /* {testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_sourceIdentifier_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_sourceIdentifier_Test","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_sourceIdentifier_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_sourceIdentifier_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_sourceIdentifier_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_sourceIdentifier_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_sourceIdentifier_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_sourceIdentifier_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_sourceIdentifier_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_sourceIdentifier_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_sourceIdentifier_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_sourceIdentifier_Test","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_sourceIdentifier_Test","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_sourceIdentifier_Test","fundingSourceId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_sourceIdentifier_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_sourceIdentifier_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_sourceIdentifier_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_sourceIdentifier_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_sourceIdentifier_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_sourceIdentifier_Test","expectedCTXTransactionResponseCode")},

                {testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_targetIdentifier_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_targetIdentifier_Test","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_targetIdentifier_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_targetIdentifier_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_targetIdentifier_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_targetIdentifier_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_targetIdentifier_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_targetIdentifier_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_targetIdentifier_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_targetIdentifier_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_targetIdentifier_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_targetIdentifier_Test","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_targetIdentifier_Test","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_targetIdentifier_Test","fundingSourceId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_targetIdentifier_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_targetIdentifier_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_targetIdentifier_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_targetIdentifier_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_targetIdentifier_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_targetIdentifier_Test","expectedCTXTransactionResponseCode")}, */

                {testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_timestamp_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_timestamp_Test","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_timestamp_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_timestamp_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_timestamp_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_timestamp_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_timestamp_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_timestamp_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_timestamp_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_timestamp_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_timestamp_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_timestamp_Test","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_timestamp_Test","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_timestamp_Test","fundingSourceId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_timestamp_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_timestamp_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_timestamp_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_timestamp_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_timestamp_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_timestamp_Test","expectedCTXTransactionResponseCode")},

                {testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_feeAmount_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_feeAmount_Test","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_feeAmount_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_feeAmount_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_feeAmount_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_feeAmount_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_feeAmount_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_feeAmount_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_feeAmount_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_feeAmount_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_feeAmount_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_feeAmount_Test","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_feeAmount_Test","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_feeAmount_Test","fundingSourceId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_feeAmount_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_feeAmount_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_feeAmount_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_feeAmount_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_feeAmount_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_feeAmount_Test","expectedCTXTransactionResponseCode")},

                {testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_currencyCode_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_currencyCode_Test","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_currencyCode_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_currencyCode_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_currencyCode_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_currencyCode_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_currencyCode_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_currencyCode_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_currencyCode_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_currencyCode_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_currencyCode_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_currencyCode_Test","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_currencyCode_Test","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_currencyCode_Test","fundingSourceId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_currencyCode_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_currencyCode_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_currencyCode_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_currencyCode_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_currencyCode_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_currencyCode_Test","expectedCTXTransactionResponseCode")},

                {testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_fundingSourceId_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_fundingSourceId_Test","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_fundingSourceId_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_fundingSourceId_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_fundingSourceId_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_fundingSourceId_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_fundingSourceId_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_fundingSourceId_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_fundingSourceId_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_fundingSourceId_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_fundingSourceId_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_fundingSourceId_Test","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_fundingSourceId_Test","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_fundingSourceId_Test","fundingSourceId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_fundingSourceId_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_fundingSourceId_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_fundingSourceId_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_fundingSourceId_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_fundingSourceId_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Null_fundingSourceId_Test","expectedCTXTransactionResponseCode")},

        };
    }

    @Step("Reserve and Transact V4 null field negative tests")
    @Test(dataProvider = "ReserveAndTransactV4NullFieldTestCases", priority = 1)
    public void ReserveAndTransactV4NullFieldNegativeTests(String accountIdentifier,
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
                                              String fundingSourceId,
                                              String expectedRaasResponseCode,
                                              String expectedMessage,
                                              String expectedHTTPResponseCode,
                                              String expectedRaasResultRequestResponseCode,
                                              String expectedRaasResultResponseResponseCode,
                                              String expectedCTXTransactionResponseCode) throws IOException, InterruptedException {

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

        // Create ReserveAndtransactV4 payload object - contains transactV4 request body
        coreReserveAndTransactV4POJO ReserveAndTransactV4Payload = new coreReserveAndTransactV4POJO(
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
                feeAmount,
                currencyCode,
                fundingSourceId);

        // Create transactV4 response body object - contains api response data for use in assertions or other calls
        Response ReserveAndTransactV4response =
                given(CORE_getEndPoints_ReserveAndTransactV4)
                        .contentType(ContentType.JSON)
                        .body(ReserveAndTransactV4Payload)
                        .when()
                        .post()
                        .then()
                        .extract()
                        .response();

        // Transact V4 response assertions - purchase
        Assert.assertEquals(ReserveAndTransactV4response.path("responseCode"), expectedRaasResponseCode);
        Assert.assertEquals(ReserveAndTransactV4response.path("responseMessage"), expectedMessage);
        Assert.assertEquals(ReserveAndTransactV4response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

    }

    // Data staging for use in test
    @DataProvider(name = "ReserveAndTransactV4ResponseCodesNegativeTestCases", parallel = false)
    public Object[] ReserveAndTransactV4ResponseCodesNegativeTestCases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","fundingSourceId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","simulatorScenario"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","simulatorResetState")},

                {testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","fundingSourceId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","simulatorScenario"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","simulatorResetState")},

        };
    }

    @Step("Reserve and Transact V4: Response codes: Negative test cases")
    @Test(dataProvider = "ReserveAndTransactV4ResponseCodesNegativeTestCases", priority = 2)
    public void ReserveAndTransactV4ResponseCodesNegativeTests(String accountIdentifier,
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
                                                          String fundingSourceId,
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

        // Create ReserveAndtransactV4 payload object - contains transactV4 request body
        coreReserveAndTransactV4POJO ReserveAndTransactV4Payload = new coreReserveAndTransactV4POJO(
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
                feeAmount,
                currencyCode,
                fundingSourceId);

        // Create transactV4 response body object - contains api response data for use in assertions or other calls
        Response ReserveAndTransactV4response =
                given(CORE_getEndPoints_ReserveAndTransactV4)
                        .contentType(ContentType.JSON)
                        .body(ReserveAndTransactV4Payload)
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
        Assert.assertEquals(ReserveAndTransactV4response.path("responseCode"), expectedRaasResponseCode);
        Assert.assertEquals(ReserveAndTransactV4response.path("responseMessage"), expectedMessage);
        Assert.assertNotNull(ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertEquals(ReserveAndTransactV4response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // raas db assertions
        //Transaction_log
        Thread.sleep(10000);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "timestamp"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "vendor_share_amount"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_request_created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_request_created"));
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'","currency_code"), currencyCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "funding_source_id"), fundingSourceId);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Raas_Request
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", ReserveAndTransactV4response.path("raasTxnRef")), ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "timestamp"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "api_call"), "reserveAndTransact-v4");

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", ReserveAndTransactV4response.path("raasTxnRef")), ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "event_type"));

        // CTX DB assertions
        Thread.sleep(5000);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "transactionId"));
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "clientStan"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "originatingService"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "purchaseAmount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "transactionState"), "C");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "transactionType"), "P");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        // raas reserve_fund_request
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_REQUEST_EVENT");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "product_type"), "3");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // raas reserve_fund_response
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_RESPONSE_EVENT");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

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
    @DataProvider(name = "ReserveAndTransactV4NonRetryableDeclineTestCases", parallel = false)
    public Object[] ReserveAndTransactV4NonRetryableDeclineTestCases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","fundingSourceId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","simulatorScenario"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Timeout_Negative_Test","simulatorResetState")},

                {testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","fundingSourceId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","simulatorScenario"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Vendor_Invalid_MSISDN_Test","simulatorResetState")},

        };
    }

    @Step("Reserve and Transact V4: Non Retryable Decline test cases")
    @Test(dataProvider = "ReserveAndTransactV4NonRetryableDeclineTestCases", priority = 3)
    public void ReserveAndTransactV4NonRetryableDeclineTests(String accountIdentifier,
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
                                                             String fundingSourceId,
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

        // Create ReserveAndtransactV4 payload object - contains transactV4 request body
        coreReserveAndTransactV4POJO ReserveAndTransactV4Payload = new coreReserveAndTransactV4POJO(
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
                feeAmount,
                currencyCode,
                fundingSourceId);

        // Create transactV4 response body object - contains api response data for use in assertions or other calls
        Response ReserveAndTransactV4response =
                given(CORE_getEndPoints_ReserveAndTransactV4)
                        .contentType(ContentType.JSON)
                        .body(ReserveAndTransactV4Payload)
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
        Assert.assertEquals(ReserveAndTransactV4response.path("responseCode"), expectedRaasResponseCode);
        Assert.assertEquals(ReserveAndTransactV4response.path("responseMessage"), expectedMessage);
        Assert.assertNotNull(ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertEquals(ReserveAndTransactV4response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // raas db assertions
        //Transaction_log
        Thread.sleep(10000);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "timestamp"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "vendor_share_amount"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_request_created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_request_created"));
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'","currency_code"), currencyCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "funding_source_id"), fundingSourceId);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Raas_Request
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", ReserveAndTransactV4response.path("raasTxnRef")), ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "timestamp"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "api_call"), "reserveAndTransact-v4");

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", ReserveAndTransactV4response.path("raasTxnRef")), ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "event_type"));

        // CTX DB assertions
        Thread.sleep(5000);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "transactionId"));
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "clientStan"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "originatingService"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "purchaseAmount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "transactionState"), "C");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "transactionType"), "P");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        // raas reserve_fund_request
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_REQUEST_EVENT");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "product_type"), "3");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // raas reserve_fund_response
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_RESPONSE_EVENT");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

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
    @DataProvider(name = "ReserveAndTransactV4LookupBackOffTestCases", parallel = false)
    public Object[] ReserveAndTransactV4LookupBackOffTestCases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Lookup_And_Backoff_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Lookup_And_Backoff_Test","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Lookup_And_Backoff_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Lookup_And_Backoff_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Lookup_And_Backoff_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Lookup_And_Backoff_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Lookup_And_Backoff_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Lookup_And_Backoff_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Lookup_And_Backoff_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Lookup_And_Backoff_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Lookup_And_Backoff_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Lookup_And_Backoff_Test","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Lookup_And_Backoff_Test","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Lookup_And_Backoff_Test","fundingSourceId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Lookup_And_Backoff_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Lookup_And_Backoff_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Lookup_And_Backoff_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Lookup_And_Backoff_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Lookup_And_Backoff_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Lookup_And_Backoff_Test","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Lookup_And_Backoff_Test","simulatorScenario"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Lookup_And_Backoff_Test","simulatorResetState")},


        };
    }

    @Step("Reserve and Transact V4: LookupBackOff test cases")
    @Test(dataProvider = "ReserveAndTransactV4LookupBackOffTestCases", priority = 4)
    public void ReserveAndTransactV4LookupBackOffTests(String accountIdentifier,
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
                                                       String fundingSourceId,
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

        // Create ReserveAndtransactV4 payload object - contains transactV4 request body
        coreReserveAndTransactV4POJO ReserveAndTransactV4Payload = new coreReserveAndTransactV4POJO(
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
                feeAmount,
                currencyCode,
                fundingSourceId);

        // Create transactV4 response body object - contains api response data for use in assertions or other calls
        Response ReserveAndTransactV4response =
                given(CORE_getEndPoints_ReserveAndTransactV4)
                        .contentType(ContentType.JSON)
                        .body(ReserveAndTransactV4Payload)
                        .when()
                        .post()
                        .then()
                        .extract()
                        .response();

        // Reset simulator
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
        Assert.assertEquals(ReserveAndTransactV4response.path("responseCode"), expectedRaasResponseCode);
        Assert.assertEquals(ReserveAndTransactV4response.path("responseMessage"), expectedMessage);
        Assert.assertNotNull(ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertEquals(ReserveAndTransactV4response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // raas db assertions
        //Transaction_log
        Thread.sleep(15000);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "timestamp"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "vendor_share_amount"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_request_created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_request_created"));
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'","currency_code"), currencyCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "funding_source_id"), fundingSourceId);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Raas_Request
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", ReserveAndTransactV4response.path("raasTxnRef")), ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "timestamp"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "api_call"), "reserveAndTransact-v4");

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", ReserveAndTransactV4response.path("raasTxnRef")), ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "event_type"));

        // CTX DB assertions
        Thread.sleep(15000);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "transactionId"));
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "clientStan"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "originatingService"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "purchaseAmount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "transactionState"), "C");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "transactionType"), "P");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        // raas reserve_fund_request
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_REQUEST_EVENT");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "product_type"), "3");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // raas reserve_fund_response
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_RESPONSE_EVENT");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

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


    }

    // Data staging for use in test
    @DataProvider(name = "ReserveAndTransactV4BankTestCases", parallel = false)
    public Object[] ReserveAndTransactV4BankTestCases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Bank_101_Positive_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Bank_101_Positive_Test","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Bank_101_Positive_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Bank_101_Positive_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Bank_101_Positive_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Bank_101_Positive_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Bank_101_Positive_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Bank_101_Positive_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Bank_101_Positive_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Bank_101_Positive_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Bank_101_Positive_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Bank_101_Positive_Test","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Bank_101_Positive_Test","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Bank_101_Positive_Test","fundingSourceId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Bank_101_Positive_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Bank_101_Positive_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Bank_101_Positive_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Bank_101_Positive_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Bank_101_Positive_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Bank_101_Positive_Test","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Bank_101_Positive_Test","simulatorScenario"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Bank_101_Positive_Test","simulatorResetState")},

        };
    }

    @Step("Reserve and Transact V4 Bank test cases")
    @Test(dataProvider = "ReserveAndTransactV4BankTestCases", priority = 5)
    public void ReserveAndTransactV4BankTests(String accountIdentifier,
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
                                                         String fundingSourceId,
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

        // Create ReserveAndtransactV4 payload object - contains transactV4 request body
        coreReserveAndTransactV4POJO ReserveAndTransactV4Payload = new coreReserveAndTransactV4POJO(
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
                feeAmount,
                currencyCode,
                fundingSourceId);

        // Create transactV4 response body object - contains api response data for use in assertions or other calls
        Response ReserveAndTransactV4response =
                given(CORE_getEndPoints_ReserveAndTransactV4)
                        .contentType(ContentType.JSON)
                        .body(ReserveAndTransactV4Payload)
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
        Assert.assertEquals(ReserveAndTransactV4response.path("responseCode"), expectedRaasResponseCode);
        Assert.assertEquals(ReserveAndTransactV4response.path("responseMessage"), expectedMessage);
        Assert.assertNotNull(ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertEquals(ReserveAndTransactV4response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // raas db assertions
        //Transaction_log
        Thread.sleep(10000);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "timestamp"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "vendor_share_amount"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_request_created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_request_created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_response_created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'","currency_code"), currencyCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "funding_source_id"), fundingSourceId);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Raas_Request
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", ReserveAndTransactV4response.path("raasTxnRef")), ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "timestamp"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "api_call"), "reserveAndTransact-v4");

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", ReserveAndTransactV4response.path("raasTxnRef")), ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "event_type"));

        // CTX DB assertions
        Thread.sleep(5000);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "transactionId"));
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "clientStan"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "originatingService"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "purchaseAmount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "transactionState"), "C");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "transactionType"), "P");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        // raas reserve_fund_request
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_REQUEST_EVENT");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "product_type"), "3");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // raas reserve_fund_response
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_RESPONSE_EVENT");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Transaction_result_request
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "event_type"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // Transaction_result_response
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "response_code"), "202");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

    }

    // Data staging for use in test
    @DataProvider(name = "ReserveAndTransactV4RetryableDeclineToOKTestCases", parallel = false)
    public Object[] ReserveAndTransactV4RetryableDeclineToOKTestCases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Retryable_Decline_To_OK_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Retryable_Decline_To_OK_Test","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Retryable_Decline_To_OK_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Retryable_Decline_To_OK_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Retryable_Decline_To_OK_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Retryable_Decline_To_OK_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Retryable_Decline_To_OK_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Retryable_Decline_To_OK_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Retryable_Decline_To_OK_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Retryable_Decline_To_OK_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Retryable_Decline_To_OK_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Retryable_Decline_To_OK_Test","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Retryable_Decline_To_OK_Test","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Retryable_Decline_To_OK_Test","fundingSourceId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Retryable_Decline_To_OK_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Retryable_Decline_To_OK_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Retryable_Decline_To_OK_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Retryable_Decline_To_OK_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Retryable_Decline_To_OK_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Retryable_Decline_To_OK_Test","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Retryable_Decline_To_OK_Test","simulatorScenario"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4suite","MTNNG_Retryable_Decline_To_OK_Test","simulatorResetState")},

        };
    }

    @Step("Reserve and Transact V4 Retryable Decline To OK test cases")
    @Test(dataProvider = "ReserveAndTransactV4RetryableDeclineToOKTestCases", priority = 6)
    public void ReserveAndTransactV4RetryableDeclineToOKTests(String accountIdentifier,
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
                                              String fundingSourceId,
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

        // Create ReserveAndtransactV4 payload object - contains transactV4 request body
        coreReserveAndTransactV4POJO ReserveAndTransactV4Payload = new coreReserveAndTransactV4POJO(
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
                feeAmount,
                currencyCode,
                fundingSourceId);

        // Create transactV4 response body object - contains api response data for use in assertions or other calls
        Response ReserveAndTransactV4response =
                given(CORE_getEndPoints_ReserveAndTransactV4)
                        .contentType(ContentType.JSON)
                        .body(ReserveAndTransactV4Payload)
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
        Assert.assertEquals(ReserveAndTransactV4response.path("responseCode"), expectedRaasResponseCode);
        Assert.assertEquals(ReserveAndTransactV4response.path("responseMessage"), expectedMessage);
        Assert.assertNotNull(ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertEquals(ReserveAndTransactV4response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // raas db assertions
        //Transaction_log
        Thread.sleep(10000);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "timestamp"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "vendor_share_amount"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_request_created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_request_created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_response_created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'","currency_code"), currencyCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "funding_source_id"), fundingSourceId);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Raas_Request
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", ReserveAndTransactV4response.path("raasTxnRef")), ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "timestamp"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "api_call"), "reserveAndTransact-v4");

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", ReserveAndTransactV4response.path("raasTxnRef")), ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "event_type"));

        // CTX DB assertions
        Thread.sleep(15000);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0001'", "transactionResponseCode"), expectedCTXTransactionResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0001'", "transactionId"));
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0001'", "clientStan"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0001'", "originId"), sourceIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0001'", "originatingService"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0001'", "purchaseAmount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0001'", "transactionState"), "C");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0001'", "transactionType"), "P");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0001'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0001'", "product_id"), productId);

        // raas reserve_fund_request
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_REQUEST_EVENT");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "product_type"), "3");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // raas reserve_fund_response
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_RESPONSE_EVENT");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Transaction_result_request
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "event_type"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // Transaction_result_response
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV4response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "response_code"), "202");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

    }
}
