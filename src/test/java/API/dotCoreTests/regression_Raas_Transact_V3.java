/**
 * TestSuite: TransactV3 Regression test suite.
 * Includes all end to end api tests for Transact V3
 * Author: Juan-Claude Botha
 */

package API.dotCoreTests;

import api.requestLibary.CORE.coreTransactV3POJO;
import api.requestLibary.CORE.coreTransactV4POJO;
import api.testUtilities.dataBuilders.testDataFactory;
import api.testUtilities.propertyConfigWrapper.configWrapper;
import api.testUtilities.sqlDataAccessLayer.sqlDataAccess;
import api.testUtilities.testConfig;

import com.jcraft.jsch.JSchException;
import io.qameta.allure.Allure;
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

        Allure.step("Action Test Step 1 : Set Vendor simulator to SUCCESS");
        startSim.SimulatorScenario("MTNNG SUCCESS");

        // Financial Terms Calculate GET method call
        Allure.step("Action Test Step 2 : Execute Fin Terms Calculate REST Request");
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

        // Create transactV3 payload object - contains transactV3 request body
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

        // Create transactV3 response body object - contains api response data for use in assertions or other calls
        Allure.step("Action Test Step 3 : Execute Transact V3 REST Request");
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

        // Transact V3 response assertions - purchase

        Allure.step("Step.6 --> Transact V3 REST response assertions --> Validate responseCode field is correct");
        Assert.assertEquals(TransactV3response.path("responseCode"), expectedRaasResponseCode);

        Allure.step("Step.7 --> Transact V3 REST response assertions --> Validate responseMessage field is correct");
        Assert.assertEquals(TransactV3response.path("responseMessage"), expectedMessage);

        Allure.step("Step.8 --> Transact V3 REST response assertions --> Validate raasTxnRef field exists");
        Assert.assertNotNull(TransactV3response.path("raasTxnRef"));

        Allure.step("Step.9 --> Transact V3 REST response assertions --> Validate HTTPResponseCode field exists");
        Assert.assertEquals(TransactV3response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // raas db assertions
        //Transaction_log

        Allure.step("Action Test Step 4 : Wait 15 seconds before executing RAAS Transaction_Log DB assertions");
        Thread.sleep(15000);

        Allure.step("Step.10 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV3response.path("raasTxnRef"));

        Allure.step("Step.11 --> RAAS DB assertions --> Table: transaction_log --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.12 --> RAAS DB assertions --> Table: transaction_log --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "amount"), purchaseAmount);

        Allure.step("Step.13 --> RAAS DB assertions --> Table: transaction_log --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.14 --> RAAS DB assertions --> Table: transaction_log --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.15 --> RAAS DB assertions --> Table: transaction_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.16 --> RAAS DB assertions --> Table: transaction_log --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.17 --> RAAS DB assertions --> Table: transaction_log --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.18 --> RAAS DB assertions --> Table: transaction_log --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.19 --> RAAS DB assertions --> Table: transaction_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.20 --> RAAS DB assertions --> Table: transaction_log --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.21 --> RAAS DB assertions --> Table: transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.22 --> RAAS DB assertions --> Table: transaction_log --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.23 --> RAAS DB assertions --> Table: transaction_log --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.24 --> RAAS DB assertions --> Table: transaction_log --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.25 --> RAAS DB assertions --> Table: transaction_log --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.26 --> RAAS DB assertions --> Table: transaction_log --> Validate reserve_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);

        Allure.step("Step.27 --> RAAS DB assertions --> Table: transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.28 --> RAAS DB assertions --> Table: transaction_log --> Validate fee_amount field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.29 --> RAAS DB assertions --> Table: transaction_log --> Validate client_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.30 --> RAAS DB assertions --> Table: transaction_log --> Validate settlement_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.31 --> RAAS DB assertions --> Table: transaction_log --> Validate vend_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);

        Allure.step("Step.32 --> RAAS DB assertions --> Table: transaction_log --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "vendor_share_amount"));

        //Allure.step("Step.33 --> RAAS DB assertions --> Table: transaction_log --> Validate status field is correct");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);

        Allure.step("Step.34 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_request_created"));

        Allure.step("Step.35 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");
        Allure.step("Step.36 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "transaction_result_request_created"));

        Allure.step("Step.37 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "transaction_result_response_created"));

        Allure.step("Step.38 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);

        Allure.step("Step.39 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");
        Allure.step("Step.40 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_request_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);

        Allure.step("Step.41 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");

        Allure.step("Step.42 --> RAAS DB assertions --> Table: transaction_log --> Validate currency_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'","currency_code"), currencyCode);

        Allure.step("Step.43 --> RAAS DB assertions --> Table: transaction_log --> Validate funding_source_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "funding_source_id"), clientId);

        Allure.step("Step.44 --> RAAS DB assertions --> Table: transaction_log --> Validate additional_data_financial_calculations field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response

        Allure.step("Step.45 --> RAAS DB assertions --> Table: raas.raas_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", TransactV3response.path("raasTxnRef")), TransactV3response.path("raasTxnRef"));

        Allure.step("Step.46 --> RAAS DB assertions --> Table: raas.raas_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.47 --> RAAS DB assertions --> Table: raas.raas_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "event_type"));

        // CTX DB assertions

        Allure.step("Action Test Step 4 : Wait 5 seconds before executing CTX Tran_Log DB assertions");
        Thread.sleep(5000);

        Allure.step("Step.48 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate transactionResponseCode field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);

        Allure.step("Step.49 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate transactionId field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "transactionId"));

        Allure.step("Step.50 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate clientStan field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "clientStan"));

        Allure.step("Step.51 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate originId field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);

        Allure.step("Step.52 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate originatingService field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "originatingService"));

        Allure.step("Step.53 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate purchaseAmount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "purchaseAmount"), purchaseAmount);

        Allure.step("Step.54 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate transactionState field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "transactionState"), "C");

        Allure.step("Step.55 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate transactionType field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "transactionType"), "P");

        Allure.step("Step.56 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "client_id"), clientId);

        Allure.step("Step.57 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        // Transaction_result_request

        Allure.step("Step.58 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);

        Allure.step("Step.59 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.60 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.61 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.62 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV3response.path("raasTxnRef"));

        Allure.step("Step.63 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.64 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.65 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate reserve_amount field exists");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);

        Allure.step("Step.66 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.67 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate client_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.68 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate settlement_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.69 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate vend_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);

        Allure.step("Step.70 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // Transaction_result_response

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);

        Allure.step("Step.71 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV3response.path("raasTxnRef"));

        Allure.step("Step.72 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.73 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "event_type"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "response_code"), "202");

        Allure.step("Step.74 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate cdc_update_timestamp field exists");
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


        // Create transactV3 payload object - contains transactV3 request body
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

        // Create transactV3 response body object - contains api response data for use in assertions or other calls
        Allure.step("Action Test Step 1 : Execute Transact V3 REST Request");
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

        // Transact V3 response assertions - purchase

        Allure.step("Step.1 --> Transact V3 REST response assertions --> Validate responseCode field is correct");
        Assert.assertEquals(TransactV3response.path("responseCode"), expectedRaasResponseCode);

        Allure.step("Step.2 --> Transact V3 REST response assertions --> Validate responseMessage field is correct");
        Assert.assertEquals(TransactV3response.path("responseMessage"), expectedMessage);

        Allure.step("Step.3 --> Transact V3 REST response assertions --> Validate HTTPResponseCode field is correct");
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
    @Description("Transact V3 Retryable Decline To Ok test")
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
        Allure.step("Action Test Step 1 : Set Vendor simulator to fail (MTNNG EXCEPTION)");
        startSim.SimulatorScenario(simulatorScenario);

        Allure.step("Action Test Step 2 : Wait 5 seconds for simulator to run");
        Thread.sleep(5000);

        // Financial Terms Calculate GET method call
        Allure.step("Action Test Step 3 : Execute Fin Terms Calculate REST Request");
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
        Allure.step("Action Test Step 4 : Execute Transact V3 REST Request");
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
        Allure.step("Action Test Step 5 : Set Vendor simulator to SUCCESS");
        startSim.SimulatorScenario(simulatorResetState);

        Allure.step("Action Test Step 6 : Wait 15 seconds for simulator to run and transaction data to process");
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

        // Transact V3 response assertions - purchase

        Allure.step("Step.6 --> Transact V3 REST response assertions --> Validate responseCode field is correct");
        Assert.assertEquals(TransactV3response.path("responseCode"), expectedRaasResponseCode);

        Allure.step("Step.7 --> Transact V3 REST response assertions --> Validate responseMessage field is correct");
        Assert.assertEquals(TransactV3response.path("responseMessage"), expectedMessage);

        Allure.step("Step.8 --> Transact V3 REST response assertions --> Validate raasTxnRef field exists");
        Assert.assertNotNull(TransactV3response.path("raasTxnRef"));

        Allure.step("Step.9 --> Transact V3 REST response assertions --> Validate HTTPResponseCode field exists");
        Assert.assertEquals(TransactV3response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // raas db assertions
        //Transaction_log

        Allure.step("Action Test Step 7 : Wait 20 seconds before executing RAAS DB assertions");
        Thread.sleep(20000);

        Allure.step("Step.10 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV3response.path("raasTxnRef"));

        Allure.step("Step.11 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.12 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "amount"), purchaseAmount);

        Allure.step("Step.13 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.14 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.15 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.16 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.17 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.18 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.19 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.20 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.21 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.22 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.23 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.24 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.25 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.26 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate reserve_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);

        Allure.step("Step.27 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.28 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.29 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.30 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate settlement_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.31 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate vend_amount field exists");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);

        Allure.step("Step.32 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "vendor_share_amount"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);

        Allure.step("Step.33 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_request_created"));

        Allure.step("Step.34 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_response_created"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");

        Allure.step("Step.35 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate transaction_result_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "transaction_result_request_created"));

        Allure.step("Step.36 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate transaction_result_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "transaction_result_response_created"));

        Allure.step("Step.37 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);

        Allure.step("Step.38 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_response_message field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");

        Allure.step("Step.39 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate transaction_result_request_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");

        Allure.step("Step.40 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate currency_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'","currency_code"), currencyCode);

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "funding_source_id"), fundingSourceId);

        Allure.step("Step.41 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate additional_data_financial_calculations field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Raas_Request

        Allure.step("Step.42 --> RAAS DB assertions --> Table: raas.raas_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", TransactV3response.path("raasTxnRef")), TransactV3response.path("raasTxnRef"));

        Allure.step("Step.43 --> RAAS DB assertions --> Table: raas.raas_request --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.44 --> RAAS DB assertions --> Table: raas.raas_request --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "amount"), purchaseAmount);

        Allure.step("Step.45 --> RAAS DB assertions --> Table: raas.raas_request --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.46 --> RAAS DB assertions --> Table: raas.raas_request --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.47 --> RAAS DB assertions --> Table: raas.raas_request --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.48 --> RAAS DB assertions --> Table: raas.raas_request --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.49 --> RAAS DB assertions --> Table: raas.raas_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.50 --> RAAS DB assertions --> Table: raas.raas_request --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.51 --> RAAS DB assertions --> Table: raas.raas_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.52 --> RAAS DB assertions --> Table: raas.raas_request --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.53 --> RAAS DB assertions --> Table: raas.raas_request --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.54 --> RAAS DB assertions --> Table: raas.raas_request --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.55 --> RAAS DB assertions --> Table: raas.raas_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.56 --> RAAS DB assertions --> Table: raas.raas_request --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.57 --> RAAS DB assertions --> Table: raas.raas_request --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.58 --> RAAS DB assertions --> Table: raas.raas_request --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.59 --> RAAS DB assertions --> Table: raas.raas_request --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.60 --> RAAS DB assertions --> Table: raas.raas_request --> Validate api_call field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "api_call"), "transact-v3");

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response

        Allure.step("Step.61 --> RAAS DB assertions --> Table: raas.raas_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", TransactV3response.path("raasTxnRef")), TransactV3response.path("raasTxnRef"));

        Allure.step("Step.62 --> RAAS DB assertions --> Table: raas.raas_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.63 --> RAAS DB assertions --> Table: raas.raas_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "event_type"));

        // CTX DB assertions

        Allure.step("Action Test Step 8 : Wait 15 seconds before executing CTX DB assertions");
        Thread.sleep(15000);

        Allure.step("Step.64 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionResponseCode field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);

        Allure.step("Step.65 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionId field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "transactionId"));

        Allure.step("Step.66 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate clientStan field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "clientStan"));

        Allure.step("Step.67 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originId field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);

        Allure.step("Step.68 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originatingService field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "originatingService"));

        Allure.step("Step.69 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate purchaseAmount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "purchaseAmount"), purchaseAmount);

        Allure.step("Step.70 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionState field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "transactionState"), "C");

        Allure.step("Step.71 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionType field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "transactionType"), "P");

        Allure.step("Step.72 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "client_id"), clientId);

        Allure.step("Step.73 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        // Transaction_result_request

        Allure.step("Step.74 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);

        Allure.step("Step.75 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.76 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.77 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.78 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV3response.path("raasTxnRef"));

        Allure.step("Step.79 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.80 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.81 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate reserve_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);

        Allure.step("Step.82 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.83 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate client_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.84 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate settlement_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.85 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate vend_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);

        Allure.step("Step.86 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // Transaction_result_response

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);

        Allure.step("Step.87 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV3response.path("raasTxnRef"));

        Allure.step("Step.88 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.89 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "event_type"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "response_code"), "202");

        Allure.step("Step.90 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Action Test Step 9 : Set Vendor simulator to SUCCESS");
        startSim.SimulatorScenario(simulatorResetState);

        Allure.step("Action Test Step 10 : Wait 5 seconds for simulator to reset");
        Thread.sleep(5000);

    }

    // Data staging for use in test
    @DataProvider(name = "transactV3PendingToRetryableOktestcases", parallel = false)
    public Object[] transactV3PendingToRetryableOktestcases() throws IOException, ParseException {

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
    @Step("Transact V3 Pending To Retryable Ok")
    @Description("Transact V3 Pending To Retryable Ok test")
    @Test(dataProvider = "transactV3PendingToRetryableOktestcases", priority = 3)
    public void TransactV3PendingToRetryableOkTests(String accountIdentifier,
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
        Allure.step("Action Test Step 1 : Set Vendor simulator to fail (MTNNG EXCEPTION)");
        startSim.SimulatorScenario(simulatorScenario);

        Allure.step("Action Test Step 2 : Wait 5 seconds for simulator to run");
        Thread.sleep(5000);

        // Financial Terms Calculate GET method call
        Allure.step("Action Test Step 3 : Execute Fin Terms Calculate REST Request");
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
        Allure.step("Action Test Step 4 : Execute Transact V3 REST Request");
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
        Allure.step("Action Test Step 5 : Set Vendor simulator to SUCCESS");
        startSim.SimulatorScenario(simulatorResetState);

        Allure.step("Action Test Step 6 :  Wait 20 seconds for simulator to run and data processing to complete");
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

        // Transact V3 response assertions - purchase

        Allure.step("Step.6 --> Transact V3 REST response assertions --> Validate responseCode field is correct");
        Assert.assertEquals(TransactV3response.path("responseCode"), expectedRaasResponseCode);

        Allure.step("Step.7 --> Transact V3 REST response assertions --> Validate responseMessage field is correct");
        Assert.assertEquals(TransactV3response.path("responseMessage"), expectedMessage);

        Allure.step("Step.8 --> Transact V3 REST response assertions --> Validate raasTxnRef field exists");
        Assert.assertNotNull(TransactV3response.path("raasTxnRef"));

        Allure.step("Step.9 --> Transact V3 REST response assertions --> Validate HTTPResponseCode field exists");
        Assert.assertEquals(TransactV3response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // raas db assertions
        //Transaction_log

        Allure.step("Action Test Step 7 : Wait 20 seconds before executing CTX DB assertions");
        Thread.sleep(20000);

        Allure.step("Step.10 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV3response.path("raasTxnRef"));

        Allure.step("Step.11 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.12 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "amount"), purchaseAmount);

        Allure.step("Step.13 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.14 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.15 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.16 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.17 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.18 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.19 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.20 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.21 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.22 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.23 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.24 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.25 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.26 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate reserve_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);

        Allure.step("Step.27 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.28 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.29 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.30 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate settlement_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.31 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate vend_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);

        Allure.step("Step.32 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "vendor_share_amount"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);

        Allure.step("Step.33 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_request_created"));

        Allure.step("Step.34 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_response_created"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");

        Allure.step("Step.35 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate transaction_result_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "transaction_result_request_created"));

        Allure.step("Step.36 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate transaction_result_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "transaction_result_response_created"));

        Allure.step("Step.37 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);

        Allure.step("Step.38 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_response_message field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");

        Allure.step("Step.39 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate transaction_result_request_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");

        Allure.step("Step.40 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate currency_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'","currency_code"), currencyCode);

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "funding_source_id"), fundingSourceId);

        Allure.step("Step.41 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate additional_data_financial_calculations field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Raas_Request

        Allure.step("Step.42 --> RAAS DB assertions --> Table: raas.raas_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", TransactV3response.path("raasTxnRef")), TransactV3response.path("raasTxnRef"));

        Allure.step("Step.43 --> RAAS DB assertions --> Table: raas.raas_request --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.44 --> RAAS DB assertions --> Table: raas.raas_request --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "amount"), purchaseAmount);

        Allure.step("Step.45 --> RAAS DB assertions --> Table: raas.raas_request --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.46 --> RAAS DB assertions --> Table: raas.raas_request --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.47 --> RAAS DB assertions --> Table: raas.raas_request --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.48 --> RAAS DB assertions --> Table: raas.raas_request --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.49 --> RAAS DB assertions --> Table: raas.raas_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.50 --> RAAS DB assertions --> Table: raas.raas_request --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.51 --> RAAS DB assertions --> Table: raas.raas_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.52 --> RAAS DB assertions --> Table: raas.raas_request --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.53 --> RAAS DB assertions --> Table: raas.raas_request --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.54 --> RAAS DB assertions --> Table: raas.raas_request --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.55 --> RAAS DB assertions --> Table: raas.raas_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.56 --> RAAS DB assertions --> Table: raas.raas_request --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.57 --> RAAS DB assertions --> Table: raas.raas_request --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.58 --> RAAS DB assertions --> Table: raas.raas_request --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.59 --> RAAS DB assertions --> Table: raas.raas_request --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.60 --> RAAS DB assertions --> Table: raas.raas_request --> Validate api_call field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "api_call"), "transact-v3");

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response

        Allure.step("Step.61 --> RAAS DB assertions --> Table: raas.raas_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", TransactV3response.path("raasTxnRef")), TransactV3response.path("raasTxnRef"));

        Allure.step("Step.62 --> RAAS DB assertions --> Table: raas.raas_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.63 --> RAAS DB assertions --> Table: raas.raas_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "event_type"));

        // SQL - UPDATE run recon
        Allure.step("Action Test Step 8 : update cpgtx.tran_log set transactionResponseCode = 2201, transactionType = 'PCH' where transactionResponseCode in (2236, 2240) and clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "'");
        sqlDataAccess.verifyMySQLUpdateSql("update cpgtx.tran_log set transactionResponseCode = 2201, transactionType = 'PCH' where transactionResponseCode in (2236, 2240) and clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_txn_ref");

        Allure.step("Action Test Step 9 : Wait 10 seconds for SQL Update to take affect");
        Thread.sleep(10000);

        // CTX DB assertions

        Allure.step("Action Test Step 10 : Wait 5 seconds before executing CTX DB assertions");
        Thread.sleep(5000);

        Allure.step("Step.64 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionResponseCode field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);

        Allure.step("Step.65 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionId field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "transactionId"));

        Allure.step("Step.66 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate clientStan field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "clientStan"));

        Allure.step("Step.67 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originId field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);

        Allure.step("Step.68 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originatingService field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "originatingService"));

        Allure.step("Step.69 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate purchaseAmount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "purchaseAmount"), purchaseAmount);

        Allure.step("Step.70 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionState field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "transactionState"), "C");

        Allure.step("Step.71 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionType field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "transactionType"), "P");

        Allure.step("Step.72 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "client_id"), clientId);

        Allure.step("Step.73 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        // Transaction_result_request

        Allure.step("Step.74 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);

        Allure.step("Step.75 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.76 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.77 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.78 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV3response.path("raasTxnRef"));

        Allure.step("Step.79 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.80 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.81 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate reserve_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);

        Allure.step("Step.82 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.83 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate client_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.84 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate settlement_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.85 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate vend_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);

        Allure.step("Step.86 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // Transaction_result_response

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);

        Allure.step("Step.87 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV3response.path("raasTxnRef"));

        Allure.step("Step.88 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate raas_txn_ref field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.89 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "event_type"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "response_code"), "202");

        Allure.step("Step.90 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

    }

    // Data staging for use in test
    @DataProvider(name = "transactV3PendingToRetryableDeclinetoDeclineTestcases", parallel = false)
    public Object[] transactV3PendingToRetryableDeclinetoDeclineTestcases() throws IOException, ParseException {

        return new String[][]{


                {testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_Decline_To_Decline", "accountIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_Decline_To_Decline", "purchaseAmount"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_Decline_To_Decline", "channelId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_Decline_To_Decline", "channelName"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_Decline_To_Decline", "channelSessionId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_Decline_To_Decline", "clientId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_Decline_To_Decline", "clientTxnRef"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_Decline_To_Decline", "productId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_Decline_To_Decline", "sourceIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_Decline_To_Decline", "targetIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_Decline_To_Decline", "timestamp"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_Decline_To_Decline", "reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_Decline_To_Decline", "feeAmount"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_Decline_To_Decline", "currencyCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_Decline_To_Decline", "expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_Decline_To_Decline", "expectedMessage"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_Decline_To_Decline", "expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_Decline_To_Decline", "expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_Decline_To_Decline", "expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_Decline_To_Decline", "expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_Decline_To_Decline", "simulatorScenario"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Retryable_Decline_To_Decline", "simulatorResetState")}
        };

    }


    // Action step
    @Step("Transact V3 Pending To Retryable Decline to Decline")
    @Description("Transact V3 Pending To Retryable Decline to Declinetest")
    @Test(dataProvider = "transactV3PendingToRetryableDeclinetoDeclineTestcases", priority = 4)
    public void TransactV3PendingToRetryableDeclinetoDeclineTests(String accountIdentifier,
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

        // Set simulator to Exception simulation
        Allure.step("Action Test Step 1 : Set Vendor simulator to fail (MTNNG EXCEPTION)");
        startSim.SimulatorScenario(simulatorScenario);

        Allure.step("Action Test Step 2 : Wait 5 seconds for simulator to run");
        Thread.sleep(5000);

        // Financial Terms Calculate GET method call
        Allure.step("Action Test Step 3 : Execute Financial Terms Calculate REST Request");
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
        Allure.step("Action Test Step 4 : Execute Transact V3 REST Request");
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
        Allure.step("Action Test Step 5 : Set Vendor simulator to Success");
        startSim.SimulatorScenario(simulatorResetState);

        Allure.step("Action Test Step 6 : Wait 20 seconds before executing assertions");
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


        // Transact V3 response assertions - purchase

        Allure.step("Step.6 --> Transact V3 REST response assertions --> Validate responseCode field is correct");
        Assert.assertEquals(TransactV3response.path("responseCode"), expectedRaasResponseCode);

        Allure.step("Step.7 --> Transact V3 REST response assertions --> Validate responseMessage field is correct");
        Assert.assertEquals(TransactV3response.path("responseMessage"), expectedMessage);

        Allure.step("Step.8 --> Transact V3 REST response assertions --> Validate raasTxnRef field exists");
        Assert.assertNotNull(TransactV3response.path("raasTxnRef"));

        Allure.step("Step.9 --> Transact V3 REST response assertions --> Validate HTTPResponseCode field exists");
        Assert.assertEquals(TransactV3response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // Raas DB assertions 1

        // Raas_Request

        Allure.step("Step.10 --> Raas DB assertions --> Table: raas.raas_request --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", TransactV3response.path("raasTxnRef")), TransactV3response.path("raasTxnRef"));

        Allure.step("Step.11 --> Raas DB assertions --> Table: raas.raas_request --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.12 --> Raas DB assertions --> Table: raas.raas_request --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "amount"), purchaseAmount);

        Allure.step("Step.13 --> Raas DB assertions --> Table: raas.raas_request --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.14 --> Raas DB assertions --> Table: raas.raas_request --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.15 --> Raas DB assertions --> Table: raas.raas_request --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.16 --> Raas DB assertions --> Table: raas.raas_request --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.17 --> Raas DB assertions --> Table: raas.raas_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.18 --> Raas DB assertions --> Table: raas.raas_request --> Validate event_type field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.19 --> Raas DB assertions --> Table: raas.raas_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.20 --> Raas DB assertions --> Table: raas.raas_request --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.21 --> Raas DB assertions --> Table: raas.raas_request --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.22 --> Raas DB assertions --> Table: raas.raas_request --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.23 --> Raas DB assertions --> Table: raas.raas_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.24 --> Raas DB assertions --> Table: raas.raas_request --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.25 --> Raas DB assertions --> Table: raas.raas_request --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.26 --> Raas DB assertions --> Table: raas.raas_request --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.27 --> Raas DB assertions --> Table: raas.raas_request --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.28 --> Raas DB assertions --> Table: raas.raas_request --> Validate api_call field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "api_call"), "transact-v3");

        // Raas_Response

        Allure.step("Step.29 --> Raas DB assertions --> Table: raas.raas_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", TransactV3response.path("raasTxnRef")), TransactV3response.path("raasTxnRef"));

        Allure.step("Step.30 --> Raas DB assertions --> Table: raas.raas_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.31 --> Raas DB assertions --> Table: raas.raas_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "event_type"));

        // SQL - UPDATE run recon
        Allure.step("Action Test Step 7 : update cpgtx.tran_log set transactionResponseCode = 2201, transactionType = 'PCH' where transactionResponseCode in (2236, 2240) and clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef"));
        sqlDataAccess.verifyPostgreCustomSql("update cpgtx.tran_log set transactionResponseCode = 2201, transactionType = 'PCH' where transactionResponseCode in (2236, 2240) and clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_txn_ref");

        Allure.step("Action Test Step 8 : Wait 10 seconds for the CPGTX.Tran_Log processing to retry the pending transaction");
        Thread.sleep(10000);

        // CTX DB assertions (CTX - Lookup Check for success)

        Allure.step("Step.32 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionResponseCode field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);

        Allure.step("Step.33 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionId field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "transactionId"));

        Allure.step("Step.34 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate clientStan field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "clientStan"));

        Allure.step("Step.35 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originId field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);

        Allure.step("Step.36 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originatingService field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "originatingService"));

        Allure.step("Step.37 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate purchaseAmount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "purchaseAmount"), purchaseAmount);

        Allure.step("Step.38 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionState field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "transactionState"), "C");

        Allure.step("Step.39 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionType field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "transactionType"), "P");

        Allure.step("Step.40 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "client_id"), clientId);

        Allure.step("Step.41 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "product_id"), productId);

       /* Thread.sleep(15000);
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
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV3response.path("raasTxnRef"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "created"));
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "event_type"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "response_code"), "202");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp")); */

        // Raas DB assertions 2

        //Transaction_log

        Allure.step("Step.42 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV3response.path("raasTxnRef"));

        Allure.step("Step.43 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.44 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "amount"), purchaseAmount);

        Allure.step("Step.45 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.46 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.47 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.48 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.49 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.50 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.51 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.52 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.53 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.54 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.55 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.56 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.57 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.58 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate reserve_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);

        Allure.step("Step.59 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.60 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.61 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.62 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate settlement_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.63 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate vend_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);

        Allure.step("Step.64 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "vendor_share_amount"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);

        Allure.step("Step.65 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_request_created"));

        Allure.step("Step.66 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_response_created"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_request_created"));
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_response_created"));

        Allure.step("Step.67 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);

        Allure.step("Step.68 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_response_message field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");

        Allure.step("Step.69 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate currency_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'","currency_code"), currencyCode);

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "funding_source_id"), clientId);

        Allure.step("Step.70 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate additional_data_financial_calculations field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Reset simulator to success
        Allure.step("Action Test Step 9 : Set Vendor simulator to SUCCESS");
        startSim.SimulatorScenario("MTNNG SUCCESS");

        Allure.step("Action Test Step 10 : Wait 5 seconds for vendor simulator to reset to SUCCESS");
        Thread.sleep(5000);

    }

    // Data staging for use in test
    @DataProvider(name = "transactV3PendingToStuckInProcessingTestcases", parallel = false)
    public Object[] transactV3PendingToStuckInProcessingTestcases() throws IOException, ParseException {

        return new String[][]{


                {testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Stuck_In_Processing", "accountIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Stuck_In_Processing", "purchaseAmount"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Stuck_In_Processing", "channelId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Stuck_In_Processing", "channelName"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Stuck_In_Processing", "channelSessionId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Stuck_In_Processing", "clientId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Stuck_In_Processing", "clientTxnRef"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Stuck_In_Processing", "productId"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Stuck_In_Processing", "sourceIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Stuck_In_Processing", "targetIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Stuck_In_Processing", "timestamp"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Stuck_In_Processing", "reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Stuck_In_Processing", "feeAmount"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Stuck_In_Processing", "currencyCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Stuck_In_Processing", "expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Stuck_In_Processing", "expectedMessage"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Stuck_In_Processing", "expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Stuck_In_Processing", "expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Stuck_In_Processing", "expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Stuck_In_Processing", "expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Stuck_In_Processing", "simulatorScenario"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Pending_To_Stuck_In_Processing", "simulatorResetState")}
        };

    }

    // Action step
    @Step("Transact V3 Pending To Stuck In Processing Ok")
    @Description("Transact V3 Pending To Stuck In Processing test")
    @Test(dataProvider = "transactV3PendingToStuckInProcessingTestcases", priority = 5)
    public void TransactV3PendingToStuckInProcessingTests(String accountIdentifier,
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
        Allure.step("Action Test Step 1 : Set Vendor simulator to fail (MTNNG EXCEPTION)");
        startSim.SimulatorScenario(simulatorScenario);

        Allure.step("Action Test Step 2 : Wait 5 seconds for simulator to run");
        Thread.sleep(5000);

        // Financial Terms Calculate GET method call
        Allure.step("Action Test Step 3 : Execute Financial Terms Calculate REST Request");
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
        Allure.step("Action Test Step 4 : Execute Transact V3 REST Request");
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
        Allure.step("Action Test Step 5 : Set Vendor simulator to SUCCESS");
        startSim.SimulatorScenario(simulatorResetState);

        Allure.step("Action Test Step 6 : Wait 20 seconds for data to process before executing assertions");
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

        // Transact V3 response assertions - purchase

        Allure.step("Step.6 --> Transact V3 REST response assertions --> Validate responseCode field is correct");
        Assert.assertEquals(TransactV3response.path("responseCode"), expectedRaasResponseCode);

        Allure.step("Step.7 --> Transact V3 REST response assertions --> Validate responseMessage field is correct");
        Assert.assertEquals(TransactV3response.path("responseMessage"), expectedMessage);

        Allure.step("Step.8 --> Transact V3 REST response assertions --> Validate raasTxnRef field exists");
        Assert.assertNotNull(TransactV3response.path("raasTxnRef"));

        Allure.step("Step.9 --> Transact V3 REST response assertions --> Validate HTTPResponseCode field exists");
        Assert.assertEquals(TransactV3response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // Raas DB assertions 1

        // Raas_Request

        Allure.step("Step.10 --> RAAS DB assertions --> Table: raas.raas_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", TransactV3response.path("raasTxnRef")), TransactV3response.path("raasTxnRef"));

        Allure.step("Step.11 --> RAAS DB assertions --> Table: raas.raas_request --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.12 --> RAAS DB assertions --> Table: raas.raas_request --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "amount"), purchaseAmount);

        Allure.step("Step.13 --> RAAS DB assertions --> Table: raas.raas_request --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.14 --> RAAS DB assertions --> Table: raas.raas_request --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.15 --> RAAS DB assertions --> Table: raas.raas_request --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.16 --> RAAS DB assertions --> Table: raas.raas_request --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.17 --> RAAS DB assertions --> Table: raas.raas_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.18 --> RAAS DB assertions --> Table: raas.raas_request --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.19 --> RAAS DB assertions --> Table: raas.raas_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.20 --> RAAS DB assertions --> Table: raas.raas_request --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.21 --> RAAS DB assertions --> Table: raas.raas_request --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.22 --> RAAS DB assertions --> Table: raas.raas_request --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.23 --> RAAS DB assertions --> Table: raas.raas_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.24 --> RAAS DB assertions --> Table: raas.raas_request --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.25 --> RAAS DB assertions --> Table: raas.raas_request --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.26 --> RAAS DB assertions --> Table: raas.raas_request --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.27 --> RAAS DB assertions --> Table: raas.raas_request --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.28 --> RAAS DB assertions --> Table: raas.raas_request --> Validate api_call field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "api_call"), "transact-v3");

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response

        Allure.step("Step.29 --> RAAS DB assertions --> Table: raas.raas_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", TransactV3response.path("raasTxnRef")), TransactV3response.path("raasTxnRef"));

        Allure.step("Step.30 --> RAAS DB assertions --> Table: raas.raas_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.31 --> RAAS DB assertions --> Table: raas.raas_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "event_type"));

        // SQL - UPDATE response code
        Allure.step("Action Test Step 7 : update cpgtx.tran_log set transactionResponseCode = 2603, transactionType = 'P' where transactionResponseCode in (2236, 2240) and clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef"));
        sqlDataAccess.verifyMySQLUpdateSql("update cpgtx.tran_log set transactionResponseCode = 2603, transactionType = 'P' where transactionResponseCode in (2236, 2240) and clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_txn_ref");

        Allure.step("Action Test Step 8 : Wait 10 seconds for the CPGTX.Tran_Log processing to retry the pending transaction");
        Thread.sleep(10000);

        // CTX DB assertions (CTX - Lookup Check for success)

        Allure.step("Step.32 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate event_type field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);

        Allure.step("Step.33 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionId field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "transactionId"));

        Allure.step("Step.34 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate clientStan field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "clientStan"));

        Allure.step("Step.35 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originId field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);

        Allure.step("Step.36 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originatingService field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "originatingService"));

        Allure.step("Step.37 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate purchaseAmount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "purchaseAmount"), purchaseAmount);

        Allure.step("Step.38 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionState field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "transactionState"), "C");

        Allure.step("Step.39 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionType field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "transactionType"), "P");

        Allure.step("Step.40 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "client_id"), clientId);

        Allure.step("Step.41 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        Allure.step("Action Test Step 9 : Wait 10 seconds before executing the Transaction_result_request table assertions");
        Thread.sleep(10000);

        // Transaction_result_request

        Allure.step("Step.42 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);

        Allure.step("Step.43 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.44 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.45 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.46 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV3response.path("raasTxnRef"));

        Allure.step("Step.47 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.48 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.49 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate reserve_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);

        Allure.step("Step.50 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.51 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate client_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.52 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate settlement_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.53 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate vend_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);

        Allure.step("Step.54 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // Transaction_result_response

        Allure.step("Step.55 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);

        Allure.step("Step.56 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV3response.path("raasTxnRef"));

        Allure.step("Step.57 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.58 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.59 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "response_code"), "202");

        Allure.step("Step.60 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas DB assertions 2

        //Transaction_log

        Allure.step("Step.61 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV3response.path("raasTxnRef"));

        Allure.step("Step.62 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.63 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "amount"), purchaseAmount);

        Allure.step("Step.64 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.65 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.66 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.67 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.68 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.69 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.70 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.71 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.72 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.73 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.74 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.75 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.76 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.77 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate reserve_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);

        Allure.step("Step.78 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.79 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.80 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.81 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate settlement_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.82 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate vend_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);

        Allure.step("Step.83 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "vendor_share_amount"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);

        Allure.step("Step.84 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_request_created"));

        Allure.step("Step.85 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_response_created"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_request_created"));
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_response_created"));

        Allure.step("Step.86 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);

        Allure.step("Step.87 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_response_message field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");

        Allure.step("Step.88 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate transaction_result_request_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);

        Allure.step("Step.89 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate transaction_result_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");

        Allure.step("Step.90 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate currency_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'","currency_code"), currencyCode);

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "funding_source_id"), clientId);

        Allure.step("Step.90 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate additional_data_financial_calculations field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Reset simulator to success
        Allure.step("Action Test Step 10 : Reset the vendor simulator to SUCCESS");
        startSim.SimulatorScenario(simulatorResetState);

    }

    // Data staging for use in test
    @DataProvider(name = "transactV3PendingToRetryableDeclineToNonRetryableDeclineTestcases", parallel = false)
    public Object[] transactV3PendingToRetryableDeclineToNonRetryableDeclineTestcases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline","accountIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline","purchaseAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline","channelId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline","channelName"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline","channelSessionId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline","clientId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline","clientTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline","productId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline","sourceIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline","targetIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline","timestamp"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline","reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline","feeAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline","currencyCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline","fundingSourceId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline","expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline","expectedMessage"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline","simulatorScenario"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4suite","MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline","simulatorResetState")},


        };
    }


    @Step("POST Vendor Simulator, Pending to Retryable Decline To Non Retryable Decline scenarios")
    @Test(dataProvider = "transactV3PendingToRetryableDeclineToNonRetryableDeclineTestcases", priority = 6)
    public void transactV3VendorPendingToRetryableDeclineToNonRetryableDeclineTests(String accountIdentifier,
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

        Allure.step("Action Test Step 1 : Set Vendor simulator to fail (MTNNG EXCEPTION)");
        startSim.SimulatorScenario(simulatorScenario);

        Allure.step("Action Test Step 2 : Wait 5 seconds for simulator to run");
        Thread.sleep(5000);

        // Financial Terms Calculate GET method call
        Allure.step("Action Test Step 3 : Execute Financial Terms Calculate REST Request");
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
        Allure.step("Action Test Step 4 : Execute Transact V3 REST Request");
        Response TransactV3response =
                given(CORE_getEndPoints_TransactV3)
                        .contentType(ContentType.JSON)
                        .body(TransactV4payload)
                        .when()
                        .post()
                        .then()
                        .extract()
                        .response();

        // Reset simulator to OK (success) for vendor to test ok scenario
        Allure.step("Action Test Step 5 : Set Vendor simulator to fail (MTNNG ERROR ERROR_INV_MSISDN)");
        startSim.SimulatorScenario(simulatorResetState);

        Allure.step("Action Test Step 6 : Wait 15 seconds for simulator to run and data processing to finish");
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

        // Transact V3 response assertions - purchase

        Allure.step("Step.6 --> Transact V3 REST response assertions --> Validate responseCode field is correct");
        Assert.assertEquals(TransactV3response.path("responseCode"), expectedRaasResponseCode);

        Allure.step("Step.7 --> Transact V3 REST response assertions --> Validate responseMessage field is correct");
        Assert.assertEquals(TransactV3response.path("responseMessage"), expectedMessage);

        Allure.step("Step.8 --> Transact V3 REST response assertions --> Validate raasTxnRef field exists");
        Assert.assertNotNull(TransactV3response.path("raasTxnRef"));

        Allure.step("Step.9 --> Transact V3 REST response assertions --> Validate HTTPResponseCode field exists");
        Assert.assertEquals(TransactV3response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));


        // Raas DB assertions 1

        // Raas_Request

        Allure.step("Step.10 --> RAAS DB assertions --> Table: raas.raas_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", TransactV3response.path("raasTxnRef")), TransactV3response.path("raasTxnRef"));

        Allure.step("Step.11 --> RAAS DB assertions --> Table: raas.raas_request --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.12 --> RAAS DB assertions --> Table: raas.raas_request --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "amount"), purchaseAmount);

        Allure.step("Step.13 --> RAAS DB assertions --> Table: raas.raas_request --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.14 --> RAAS DB assertions --> Table: raas.raas_request --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.15 --> RAAS DB assertions --> Table: raas.raas_request --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.16 --> RAAS DB assertions --> Table: raas.raas_request --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.17 --> RAAS DB assertions --> Table: raas.raas_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.18 --> RAAS DB assertions --> Table: raas.raas_request --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.19 --> RAAS DB assertions --> Table: raas.raas_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.20 --> RAAS DB assertions --> Table: raas.raas_request --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.21 --> RAAS DB assertions --> Table: raas.raas_request --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.22 --> RAAS DB assertions --> Table: raas.raas_request --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.23 --> RAAS DB assertions --> Table: raas.raas_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.24 --> RAAS DB assertions --> Table: raas.raas_request --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.25 --> RAAS DB assertions --> Table: raas.raas_request --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.26 --> RAAS DB assertions --> Table: raas.raas_request --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.27 --> RAAS DB assertions --> Table: raas.raas_request --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.28 --> RAAS DB assertions --> Table: raas.raas_request --> Validate api_call field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "api_call"), "transact-v3");

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response

        Allure.step("Step.29 --> RAAS DB assertions --> Table: raas.raas_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", TransactV3response.path("raasTxnRef")), TransactV3response.path("raasTxnRef"));

        Allure.step("Step.30 --> RAAS DB assertions --> Table: raas.raas_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.31 --> RAAS DB assertions --> Table: raas.raas_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "event_type"));

        // SQL - UPDATE run recon
        Allure.step("Action Test Step 7 : update cpgtx.tran_log set transactionResponseCode = 2201, transactionType = 'PCH' where transactionResponseCode in (2236, 2240, 2213) and clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef"));
        sqlDataAccess.verifyMySQLUpdateSql("update cpgtx.tran_log set transactionResponseCode = 2201, transactionType = 'PCH' where transactionResponseCode in (2236, 2240, 2213) and clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_txn_ref");

        Allure.step("Action Test Step 8 : Wait 10 seconds for the pending transaction to process then continue with CTX DB assertion steps");
        Thread.sleep(10000);

        // CTX DB assertions (CTX - Lookup Check for success)

        Allure.step("Step.32 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionResponseCode field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);

        Allure.step("Step.33 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionId field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "transactionId"));

        Allure.step("Step.34 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate clientStan field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "clientStan"));

        Allure.step("Step.35 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originId field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);

        Allure.step("Step.36 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originatingService field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "originatingService"));

        Allure.step("Step.37 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate purchaseAmount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "purchaseAmount"), purchaseAmount);

        Allure.step("Step.38 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionState field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "transactionState"), "C");

        Allure.step("Step.39 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionType field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "transactionType"), "P");

        Allure.step("Step.40 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "client_id"), clientId);

        Allure.step("Step.41 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV3response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        Allure.step("Action Test Step 9 : Wait 10 seconds before executing raas.transaction_log assertions");
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

        Allure.step("Step.42 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV3response.path("raasTxnRef"));

        Allure.step("Step.43 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.44 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "amount"), purchaseAmount);

        Allure.step("Step.45 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.46 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.47 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.48 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.49 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.50 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.51 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.52 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.53 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.54 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.55 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.56 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.57 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.58 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate reserve_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "reserve_amount"), purchaseAmount);

        Allure.step("Step.59 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate reserve_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.60 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.61 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.62 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate settlement_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.63 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate vend_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "vend_amount"), purchaseAmount);

        Allure.step("Step.64 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "vendor_share_amount"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);

        Allure.step("Step.65 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_request_created"));

        Allure.step("Step.66 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_response_created"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_request_created"));
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_response_created"));

        Allure.step("Step.67 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);

        Allure.step("Step.68 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_response_message field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");

        Allure.step("Step.67 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate currency_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'","currency_code"), currencyCode);

        Allure.step("Step.68 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate funding_source_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "funding_source_id"), fundingSourceId);

        Allure.step("Step.69 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate additional_data_financial_calculations field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Reset simulator to success
        Allure.step("Action Test Step 10 : Reset vendor simulator to SUCCESS");
        startSim.SimulatorScenario("MTNNG SUCCESS");

        Allure.step("Wait 5 seconds for vendor simulator to run");
        Thread.sleep(5000);

    }

}
