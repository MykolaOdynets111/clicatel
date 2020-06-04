/**
 * TestSuite: TransactV3 Regression test suite.
 * Includes all end to end api tests for Transact V3
 * Author: Juan-Claude Botha
 */

package API.dotCoreTests;

import api.requestLibary.CORE.coreTransactV3POJO;
import api.testUtilities.dataBuilders.testDataFactory;
import api.testUtilities.propertyConfigWrapper.configWrapper;
import api.testUtilities.sqlDataAccessLayer.sqlDataAccess;
import api.testUtilities.testConfig;

import com.jcraft.jsch.JSchException;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import util.Listeners.allureApiTestListener;

import api.testUtilities.Simulators.startSimulator;

import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

@Listeners(allureApiTestListener.class)
public class regression_Raas_Transact_V3 extends testConfig {

    // Create properties object in order to inject environment specific variables upon build
    Properties properties = configWrapper.loadPropertiesFile("config.properties");

    // Initialize the vendor simulator
    startSimulator startSim = new startSimulator();

    // Data staging for use in test
    @DataProvider(name = "transactV3Successtestcases", parallel = false)
    public Object[] transactV3Successtestcases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "successcase1", "accountIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "successcase1", "purchaseAmount"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "successcase1", "channelId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "successcase1", "channelName"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "successcase1", "channelSessionId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "successcase1", "clientId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "successcase1", "clientTxnRef"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "successcase1", "productId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "successcase1", "sourceIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "successcase1", "targetIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "successcase1", "timestamp"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "successcase1", "reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "successcase1", "feeAmount"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "successcase1", "currencyCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "successcase1", "expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "successcase1", "expectedMessage"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "successcase1", "expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "successcase1", "expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "successcase1", "expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "successcase1", "expectedCTXTransactionResponseCode")},

                {testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Success_TestCase", "accountIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Success_TestCase", "purchaseAmount"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Success_TestCase", "channelId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Success_TestCase", "channelName"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Success_TestCase", "channelSessionId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Success_TestCase", "clientId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Success_TestCase", "clientTxnRef"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Success_TestCase", "productId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Success_TestCase", "sourceIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Success_TestCase", "targetIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Success_TestCase", "timestamp"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Success_TestCase", "reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Success_TestCase", "feeAmount"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Success_TestCase", "currencyCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Success_TestCase", "expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Success_TestCase", "expectedMessage"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Success_TestCase", "expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Success_TestCase", "expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Success_TestCase", "expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Success_TestCase", "expectedCTXTransactionResponseCode")}
        };

    }


    // Action step
    @Step("Transact V3 Success")
    @Description("Transact V3 Success test")
    @Test(dataProvider = "transactV3Successtestcases", priority = 0)
    public void TransactV3StandardSuccessTests(String accountIdentifier,
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
                                String expectedRaasResponseCode,
                                String expectedMessage,
                                String expectedHTTPResponseCode,
                                String expectedRaasResultRequestResponseCode,
                                String expectedRaasResultResponseResponseCode,
                                String expectedCTXTransactionResponseCode) throws IOException, InterruptedException, JSchException {

        startSim.SimulatorScenario("MTNNG SUCCESS");

        // Financial Terms Calculate GET method call
        Response finTermsCalculateResponse =
                given(CORE_getEndPoints_FinancialTermsCalculate)
                        .param("clientId", clientId)
                        .param("productId", productId)
                        .param("purchaseAmount", purchaseAmount)
                        .when()
                        .get()
                        .then()
                        .extract()
                        .response();

        // Create transactV4 payload object - contains transactV4 request body
        coreTransactV3POJO TransactV3payload = new coreTransactV3POJO(accountIdentifier,
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
                currencyCode);

        // Create transactV4 response body object - contains api response data for use in assertions or other calls
        Response TransactV3response =
                given(CORE_getEndPoints_TransactV3)
                        .contentType(ContentType.JSON)
                        .body(TransactV3payload)
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

        // Transact V4 response assertions - purchase
        Assert.assertEquals(TransactV3response.path("responseCode"), expectedRaasResponseCode);
        Assert.assertEquals(TransactV3response.path("responseMessage"), expectedMessage);
        Assert.assertNotNull(TransactV3response.path("raasTxnRef"));
        Assert.assertEquals(TransactV3response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // raas db assertions
        //Transaction_log
        Thread.sleep(15000);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV3response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "vendor_share_amount"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_request_created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "transaction_result_request_created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "transaction_result_response_created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'","currency_code"), currencyCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "funding_source_id"), fundingSourceId);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Raas_Request
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", TransactV3response.path("raasTxnRef")), TransactV3response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "api_call"), "transact-v3");

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", TransactV3response.path("raasTxnRef")), TransactV3response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "event_type"));

        // CTX DB assertions
        Thread.sleep(5000);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "transactionId"));
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "clientStan"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "originatingService"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "purchaseAmount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "transactionState"), "C");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "transactionType"), "P");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        // Transaction_result_request
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV3response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // Transaction_result_response
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV3response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "event_type"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "response_code"), "202");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

    }

    // Data staging for use in test
    @DataProvider(name = "transactV3NullFieldtestcases", parallel = false)
    public Object[] transactV3NullFieldtestcases() throws IOException, ParseException {

        return new String[][]{


                {testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_PurchaseAmount_TestCase", "accountIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_PurchaseAmount_TestCase", "purchaseAmount"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_PurchaseAmount_TestCase", "channelId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_PurchaseAmount_TestCase", "channelName"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_PurchaseAmount_TestCase", "channelSessionId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_PurchaseAmount_TestCase", "clientId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_PurchaseAmount_TestCase", "clientTxnRef"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_PurchaseAmount_TestCase", "productId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_PurchaseAmount_TestCase", "sourceIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_PurchaseAmount_TestCase", "targetIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_PurchaseAmount_TestCase", "timestamp"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_PurchaseAmount_TestCase", "reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_PurchaseAmount_TestCase", "feeAmount"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_PurchaseAmount_TestCase", "currencyCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_PurchaseAmount_TestCase", "expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_PurchaseAmount_TestCase", "expectedMessage"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_PurchaseAmount_TestCase", "expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_PurchaseAmount_TestCase", "expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_PurchaseAmount_TestCase", "expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_PurchaseAmount_TestCase", "expectedCTXTransactionResponseCode")},

                {testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelId_TestCase", "accountIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelId_TestCase", "purchaseAmount"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelId_TestCase", "channelId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelId_TestCase", "channelName"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelId_TestCase", "channelSessionId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelId_TestCase", "clientId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelId_TestCase", "clientTxnRef"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelId_TestCase", "productId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelId_TestCase", "sourceIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelId_TestCase", "targetIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelId_TestCase", "timestamp"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelId_TestCase", "reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelId_TestCase", "feeAmount"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelId_TestCase", "currencyCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelId_TestCase", "expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelId_TestCase", "expectedMessage"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelId_TestCase", "expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelId_TestCase", "expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelId_TestCase", "expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelId_TestCase", "expectedCTXTransactionResponseCode")},

                {testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelName_TestCase", "accountIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelName_TestCase", "purchaseAmount"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelName_TestCase", "channelId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelName_TestCase", "channelName"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelName_TestCase", "channelSessionId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelName_TestCase", "clientId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelName_TestCase", "clientTxnRef"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelName_TestCase", "productId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelName_TestCase", "sourceIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelName_TestCase", "targetIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelName_TestCase", "timestamp"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelName_TestCase", "reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelName_TestCase", "feeAmount"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelName_TestCase", "currencyCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelName_TestCase", "expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelName_TestCase", "expectedMessage"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelName_TestCase", "expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelName_TestCase", "expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelName_TestCase", "expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelName_TestCase", "expectedCTXTransactionResponseCode")},

                {testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelSessionId_TestCase", "accountIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelSessionId_TestCase", "purchaseAmount"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelSessionId_TestCase", "channelId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelSessionId_TestCase", "channelName"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelSessionId_TestCase", "channelSessionId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelSessionId_TestCase", "clientId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelSessionId_TestCase", "clientTxnRef"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelSessionId_TestCase", "productId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelSessionId_TestCase", "sourceIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelSessionId_TestCase", "targetIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelSessionId_TestCase", "timestamp"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelSessionId_TestCase", "reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelSessionId_TestCase", "feeAmount"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelSessionId_TestCase", "currencyCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelSessionId_TestCase", "expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelSessionId_TestCase", "expectedMessage"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelSessionId_TestCase", "expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelSessionId_TestCase", "expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelSessionId_TestCase", "expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ChannelSessionId_TestCase", "expectedCTXTransactionResponseCode")},

                {testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ClientId_TestCase", "accountIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ClientId_TestCase", "purchaseAmount"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ClientId_TestCase", "channelId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ClientId_TestCase", "channelName"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ClientId_TestCase", "channelSessionId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ClientId_TestCase", "clientId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ClientId_TestCase", "clientTxnRef"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ClientId_TestCase", "productId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ClientId_TestCase", "sourceIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ClientId_TestCase", "targetIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ClientId_TestCase", "timestamp"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ClientId_TestCase", "reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ClientId_TestCase", "feeAmount"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ClientId_TestCase", "currencyCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ClientId_TestCase", "expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ClientId_TestCase", "expectedMessage"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ClientId_TestCase", "expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ClientId_TestCase", "expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ClientId_TestCase", "expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_ClientId_TestCase", "expectedCTXTransactionResponseCode")},

                {testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_clientTxnRef_TestCase", "accountIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_clientTxnRef_TestCase", "purchaseAmount"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_clientTxnRef_TestCase", "channelId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_clientTxnRef_TestCase", "channelName"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_clientTxnRef_TestCase", "channelSessionId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_clientTxnRef_TestCase", "clientId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_clientTxnRef_TestCase", "clientTxnRef"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_clientTxnRef_TestCase", "productId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_clientTxnRef_TestCase", "sourceIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_clientTxnRef_TestCase", "targetIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_clientTxnRef_TestCase", "timestamp"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_clientTxnRef_TestCase", "reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_clientTxnRef_TestCase", "feeAmount"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_clientTxnRef_TestCase", "currencyCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_clientTxnRef_TestCase", "expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_clientTxnRef_TestCase", "expectedMessage"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_clientTxnRef_TestCase", "expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_clientTxnRef_TestCase", "expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_clientTxnRef_TestCase", "expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_clientTxnRef_TestCase", "expectedCTXTransactionResponseCode")},

                {testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_productId_TestCase", "accountIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_productId_TestCase", "purchaseAmount"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_productId_TestCase", "channelId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_productId_TestCase", "channelName"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_productId_TestCase", "channelSessionId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_productId_TestCase", "clientId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_productId_TestCase", "clientTxnRef"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_productId_TestCase", "productId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_productId_TestCase", "sourceIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_productId_TestCase", "targetIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_productId_TestCase", "timestamp"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_productId_TestCase", "reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_productId_TestCase", "feeAmount"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_productId_TestCase", "currencyCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_productId_TestCase", "expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_productId_TestCase", "expectedMessage"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_productId_TestCase", "expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_productId_TestCase", "expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_productId_TestCase", "expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_productId_TestCase", "expectedCTXTransactionResponseCode")},

                {testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_sourceIdentifier_TestCase", "accountIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_sourceIdentifier_TestCase", "purchaseAmount"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_sourceIdentifier_TestCase", "channelId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_sourceIdentifier_TestCase", "channelName"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_sourceIdentifier_TestCase", "channelSessionId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_sourceIdentifier_TestCase", "clientId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_sourceIdentifier_TestCase", "clientTxnRef"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_sourceIdentifier_TestCase", "productId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_sourceIdentifier_TestCase", "sourceIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_sourceIdentifier_TestCase", "targetIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_sourceIdentifier_TestCase", "timestamp"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_sourceIdentifier_TestCase", "reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_sourceIdentifier_TestCase", "feeAmount"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_sourceIdentifier_TestCase", "currencyCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_sourceIdentifier_TestCase", "expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_sourceIdentifier_TestCase", "expectedMessage"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_sourceIdentifier_TestCase", "expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_sourceIdentifier_TestCase", "expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_sourceIdentifier_TestCase", "expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_sourceIdentifier_TestCase", "expectedCTXTransactionResponseCode")},

                {testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_targetIdentifier_TestCase", "accountIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_targetIdentifier_TestCase", "purchaseAmount"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_targetIdentifier_TestCase", "channelId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_targetIdentifier_TestCase", "channelName"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_targetIdentifier_TestCase", "channelSessionId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_targetIdentifier_TestCase", "clientId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_targetIdentifier_TestCase", "clientTxnRef"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_targetIdentifier_TestCase", "productId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_targetIdentifier_TestCase", "sourceIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_targetIdentifier_TestCase", "targetIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_targetIdentifier_TestCase", "timestamp"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_targetIdentifier_TestCase", "reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_targetIdentifier_TestCase", "feeAmount"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_targetIdentifier_TestCase", "currencyCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_targetIdentifier_TestCase", "expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_targetIdentifier_TestCase", "expectedMessage"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_targetIdentifier_TestCase", "expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_targetIdentifier_TestCase", "expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_targetIdentifier_TestCase", "expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_targetIdentifier_TestCase", "expectedCTXTransactionResponseCode")},

                {testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_Timestamp_TestCase", "accountIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_Timestamp_TestCase", "purchaseAmount"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_Timestamp_TestCase", "channelId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_Timestamp_TestCase", "channelName"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_Timestamp_TestCase", "channelSessionId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_Timestamp_TestCase", "clientId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_Timestamp_TestCase", "clientTxnRef"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_Timestamp_TestCase", "productId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_Timestamp_TestCase", "sourceIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_Timestamp_TestCase", "targetIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_Timestamp_TestCase", "timestamp"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_Timestamp_TestCase", "reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_Timestamp_TestCase", "feeAmount"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_Timestamp_TestCase", "currencyCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_Timestamp_TestCase", "expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_Timestamp_TestCase", "expectedMessage"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_Timestamp_TestCase", "expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_Timestamp_TestCase", "expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_Timestamp_TestCase", "expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_Timestamp_TestCase", "expectedCTXTransactionResponseCode")}
        };

    }


    // Action step
    @Step("Transact V3 Null Field Negative Test Cases")
    @Description("Transact V3 Null Field tests")
    @Test(dataProvider = "transactV3NullFieldtestcases", priority = 1)
    public void TransactV3NullFieldTests(String accountIdentifier,
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
                                               String expectedRaasResponseCode,
                                               String expectedMessage,
                                               String expectedHTTPResponseCode,
                                               String expectedRaasResultRequestResponseCode,
                                               String expectedRaasResultResponseResponseCode,
                                               String expectedCTXTransactionResponseCode) throws IOException, InterruptedException {


        // Create transactV4 payload object - contains transactV4 request body
        coreTransactV3POJO TransactV3payload = new coreTransactV3POJO(accountIdentifier,
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
                currencyCode);

        // Create transactV4 response body object - contains api response data for use in assertions or other calls
        Response TransactV3response =
                given(CORE_getEndPoints_TransactV3)
                        .contentType(ContentType.JSON)
                        .body(TransactV3payload)
                        .when()
                        .post()
                        .then()
                        .extract()
                        .response();

        // Assertions

        // Transact V4 response assertions - purchase
        Assert.assertEquals(TransactV3response.path("responseCode"), expectedRaasResponseCode);
        Assert.assertEquals(TransactV3response.path("responseMessage"), expectedMessage);
        Assert.assertEquals(TransactV3response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

    }

    // Data staging for use in test
    @DataProvider(name = "transactV3RetryableDeclineToOktestcases", parallel = false)
    public Object[] transactV3RetryableDeclineToOktestcases() throws IOException, ParseException {

        return new String[][]{


                {testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_OK", "accountIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_OK", "purchaseAmount"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_OK", "channelId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_OK", "channelName"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_OK", "channelSessionId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_OK", "clientId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_OK", "clientTxnRef"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_OK", "productId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_OK", "sourceIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_OK", "targetIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_OK", "timestamp"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_OK", "reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_OK", "feeAmount"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_OK", "currencyCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_OK", "expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_OK", "expectedMessage"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_OK", "expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_OK", "expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_OK", "expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_OK", "expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_OK", "simulatorScenario"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_OK", "simulatorResetState")}
        };

    }


    // Action step
    @Step("Transact V3 Retryable Decline To Ok")
    @Description("Transact V3 Success test")
    @Test(dataProvider = "transactV3RetryableDeclineToOktestcases", priority = 2)
    public void TransactV3RetryableDeclineToOkTests(String accountIdentifier,
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
                                               String expectedRaasResponseCode,
                                               String expectedMessage,
                                               String expectedHTTPResponseCode,
                                               String expectedRaasResultRequestResponseCode,
                                               String expectedRaasResultResponseResponseCode,
                                               String expectedCTXTransactionResponseCode,
                                               String simulatorScenario,
                                               String simulatorResetState) throws IOException, InterruptedException, JSchException {

        // Set simulator RD to OK simulation
        startSim.SimulatorScenario(simulatorScenario);
        Thread.sleep(5000);

        // Financial Terms Calculate GET method call
        Response finTermsCalculateResponse =
                given(CORE_getEndPoints_FinancialTermsCalculate)
                        .param("clientId", clientId)
                        .param("productId", productId)
                        .param("purchaseAmount", purchaseAmount)
                        .when()
                        .get()
                        .then()
                        .extract()
                        .response();

        // Create transactV4 payload object - contains transactV4 request body
        coreTransactV3POJO TransactV3payload = new coreTransactV3POJO(accountIdentifier,
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
                currencyCode);

        // Create transactV4 response body object - contains api response data for use in assertions or other calls
        Response TransactV3response =
                given(CORE_getEndPoints_TransactV3)
                        .contentType(ContentType.JSON)
                        .body(TransactV3payload)
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
        Assert.assertEquals(TransactV3response.path("responseCode"), expectedRaasResponseCode);
        Assert.assertEquals(TransactV3response.path("responseMessage"), expectedMessage);
        Assert.assertNotNull(TransactV3response.path("raasTxnRef"));
        Assert.assertEquals(TransactV3response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // raas db assertions
        //Transaction_log
        Thread.sleep(20000);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV3response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "vendor_share_amount"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_request_created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "transaction_result_request_created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "transaction_result_response_created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'","currency_code"), currencyCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "funding_source_id"), fundingSourceId);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Raas_Request
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", TransactV3response.path("raasTxnRef")), TransactV3response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "amount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_id"), channelId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_name"), channelName);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "api_call"), "transact-v3");

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", TransactV3response.path("raasTxnRef")), TransactV3response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "event_type"));

        // CTX DB assertions
        Thread.sleep(5000);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "transactionId"));
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "clientStan"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "originatingService"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "purchaseAmount"), purchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "transactionState"), "C");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "transactionType"), "P");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        // Transaction_result_request
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV3response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "timestamp"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "fee_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "client_share_amount"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "settlement_amount"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // Transaction_result_response
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV3response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "event_type"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "response_code"), "202");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));


    }
}
