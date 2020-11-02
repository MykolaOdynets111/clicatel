/**
 * TestSuite: TransactV2 Regression test suite.
 * Includes all end to end api tests for Transact V2
 * Author: Juan-Claude Botha
 */

package API.dotCoreTests;

import api.requestLibary.CORE.coreTransactV2POJO;
import api.requestLibary.CORE.coreTransactV3POJO;
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
public class regression_Raas_Transact_V2 extends testConfig {

    // Create properties object in order to inject environment specific variables upon build
    Properties properties = configWrapper.loadPropertiesFile("config.properties");

    // Initialize the vendor simulator
    startSimulator startSim = new startSimulator();

    // Data staging for use in test
    @DataProvider(name = "transactV2Successtestcases", parallel = false)
    public Object[] transactV2Successtestcases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "successcase1", "accountIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "successcase1", "amount"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "successcase1", "channelId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "successcase1", "channelName"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "successcase1", "channelSessionId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "successcase1", "clientId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "successcase1", "clientTxnRef"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "successcase1", "productId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "successcase1", "sourceIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "successcase1", "targetIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "successcase1", "timestamp"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "successcase1", "reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "successcase1", "expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "successcase1", "expectedMessage"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "successcase1", "expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "successcase1", "expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "successcase1", "expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "successcase1", "expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "successcase1", "simulatorScenario"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "successcase1", "simulatorResetState")},

                {testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Success_TestCase", "accountIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Success_TestCase", "amount"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Success_TestCase", "channelId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Success_TestCase", "channelName"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Success_TestCase", "channelSessionId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Success_TestCase", "clientId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Success_TestCase", "clientTxnRef"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Success_TestCase", "productId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Success_TestCase", "sourceIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Success_TestCase", "targetIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Success_TestCase", "timestamp"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Success_TestCase", "reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Success_TestCase", "expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Success_TestCase", "expectedMessage"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Success_TestCase", "expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Success_TestCase", "expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Success_TestCase", "expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Success_TestCase", "expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Success_TestCase", "simulatorScenario"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Success_TestCase", "simulatorResetState")}
        };

    }

    // Action step
    @Step("Transact V2 Success test cases")
    @Description("Transact V2 Success tests")
    @Test(dataProvider = "transactV2Successtestcases", priority = 0)
    public void TransactV2StandardSuccessTests(String accountIdentifier,
                                               String amount,
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
                                               String expectedRaasResponseCode,
                                               String expectedMessage,
                                               String expectedHTTPResponseCode,
                                               String expectedRaasResultRequestResponseCode,
                                               String expectedRaasResultResponseResponseCode,
                                               String expectedCTXTransactionResponseCode,
                                               String simulatorScenario,
                                               String simulatorResetState) throws IOException, InterruptedException, JSchException {

        Allure.step("Action Test Step 1 : Set Vendor simulator to SUCCESS");
        startSim.SimulatorScenario(simulatorScenario);

        // Financial Terms Calculate GET method call
        Allure.step("Action Test Step 2 : Execute Fin Terms Calculate REST Request");
        Response finTermsCalculateResponse =
                given(CORE_getEndPoints_FinancialTermsCalculate)
                        .param("clientId", clientId)
                        .param("productId", productId)
                        .param("purchaseAmount", amount)
                        .when()
                        .get()
                        .then()
                        .extract()
                        .response();

        // Create transactV2 payload object - contains transactV2 request body
        coreTransactV2POJO TransactV2payload = new coreTransactV2POJO(accountIdentifier,
                amount,
                channelId,
                channelName,
                channelSessionId,
                clientId,
                clientTxnRef,
                productId,
                sourceIdentifier,
                targetIdentifier,
                timeStamp,
                reserveFundsTxnRef);

        // Create transactV2 response body object - contains api response data for use in assertions or other calls
        Allure.step("Action Test Step 3 : Execute Transact V2 REST Request");
        Response TransactV2response =
                given(CORE_getEndPoints_TransactV2)
                        .contentType(ContentType.JSON)
                        .body(TransactV2payload)
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
        Assert.assertEquals(finTermsCalculateResponse.path("purchaseAmount").toString(), amount);

        // Transact V2 response assertions - purchase

        Allure.step("Step.6 --> Transact V2 REST response assertions --> Validate responseCode field is correct");
        Assert.assertEquals(TransactV2response.path("responseCode"), expectedRaasResponseCode);

        Allure.step("Step.7 --> Transact V2 REST response assertions --> Validate responseMessage field is correct");
        Assert.assertEquals(TransactV2response.path("responseMessage"), expectedMessage);

        Allure.step("Step.8 --> Transact V2 REST response assertions --> Validate raasTxnRef field exists");
        Assert.assertNotNull(TransactV2response.path("raasTxnRef"));

        Allure.step("Step.9 --> Transact V2 REST response assertions --> Validate HTTPResponseCode field exists");
        Assert.assertEquals(TransactV2response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // raas db assertions
        //Transaction_log

        Allure.step("Action Test Step 4 : Wait 15 seconds before executing RAAS Transaction_Log DB assertions");
        Thread.sleep(15000);

        Allure.step("Step.10 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV2response.path("raasTxnRef"));

        Allure.step("Step.11 --> RAAS DB assertions --> Table: transaction_log --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.12 --> RAAS DB assertions --> Table: transaction_log --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "amount"), amount);

        Allure.step("Step.13 --> RAAS DB assertions --> Table: transaction_log --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.14 --> RAAS DB assertions --> Table: transaction_log --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.15 --> RAAS DB assertions --> Table: transaction_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.16 --> RAAS DB assertions --> Table: transaction_log --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.17 --> RAAS DB assertions --> Table: transaction_log --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.18 --> RAAS DB assertions --> Table: transaction_log --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.19 --> RAAS DB assertions --> Table: transaction_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.20 --> RAAS DB assertions --> Table: transaction_log --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.21 --> RAAS DB assertions --> Table: transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.22 --> RAAS DB assertions --> Table: transaction_log --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.23 --> RAAS DB assertions --> Table: transaction_log --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.24 --> RAAS DB assertions --> Table: transaction_log --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.25 --> RAAS DB assertions --> Table: transaction_log --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.26 --> RAAS DB assertions --> Table: transaction_log --> Validate reserve_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "reserve_amount"), amount);

        Allure.step("Step.27 --> RAAS DB assertions --> Table: transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.28 --> RAAS DB assertions --> Table: transaction_log --> Validate fee_amount field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.29 --> RAAS DB assertions --> Table: transaction_log --> Validate client_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.30 --> RAAS DB assertions --> Table: transaction_log --> Validate settlement_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.31 --> RAAS DB assertions --> Table: transaction_log --> Validate vend_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "vend_amount"), amount);

        Allure.step("Step.32 --> RAAS DB assertions --> Table: transaction_log --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "vendor_share_amount"));

        //Allure.step("Step.33 --> RAAS DB assertions --> Table: transaction_log --> Validate status field is correct");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);

        Allure.step("Step.34 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "raas_request_created"));

        Allure.step("Step.35 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "raas_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");
        Allure.step("Step.36 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "transaction_result_request_created"));

        Allure.step("Step.37 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "transaction_result_response_created"));

        Allure.step("Step.38 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);

        Allure.step("Step.39 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");
        Allure.step("Step.40 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_request_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);

        Allure.step("Step.41 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");

        Allure.step("Step.43 --> RAAS DB assertions --> Table: transaction_log --> Validate funding_source_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "funding_source_id"), clientId);

        Allure.step("Step.44 --> RAAS DB assertions --> Table: transaction_log --> Validate additional_data_financial_calculations field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response

        Allure.step("Step.45 --> RAAS DB assertions --> Table: raas.raas_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", TransactV2response.path("raasTxnRef")), TransactV2response.path("raasTxnRef"));

        Allure.step("Step.46 --> RAAS DB assertions --> Table: raas.raas_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.47 --> RAAS DB assertions --> Table: raas.raas_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "event_type"));

        // CTX DB assertions

        Allure.step("Action Test Step 4 : Wait 5 seconds before executing CTX Tran_Log DB assertions");
        Thread.sleep(5000);

        Allure.step("Step.48 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate transactionResponseCode field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV2response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);

        Allure.step("Step.49 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate transactionId field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV2response.path("raasTxnRef") + "-0000'", "transactionId"));

        Allure.step("Step.50 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate clientStan field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV2response.path("raasTxnRef") + "-0000'", "clientStan"));

        Allure.step("Step.51 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate originId field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV2response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);

        Allure.step("Step.52 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate originatingService field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV2response.path("raasTxnRef") + "-0000'", "originatingService"));

        Allure.step("Step.53 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate purchaseAmount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV2response.path("raasTxnRef") + "-0000'", "purchaseAmount"), amount);

        Allure.step("Step.54 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate transactionState field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV2response.path("raasTxnRef") + "-0000'", "transactionState"), "C");

        Allure.step("Step.55 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate transactionType field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV2response.path("raasTxnRef") + "-0000'", "transactionType"), "P");

        Allure.step("Step.56 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV2response.path("raasTxnRef") + "-0000'", "client_id"), clientId);

        Allure.step("Step.57 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV2response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        // Transaction_result_request

        Allure.step("Step.58 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);

        Allure.step("Step.59 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.60 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.61 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.62 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV2response.path("raasTxnRef"));

        Allure.step("Step.63 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.64 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.65 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate reserve_amount field exists");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "reserve_amount"), amount);

        Allure.step("Step.66 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.67 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate client_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.68 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate settlement_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.69 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate vend_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "vend_amount"), amount);

        Allure.step("Step.70 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // Transaction_result_response

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);

        Allure.step("Step.71 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV2response.path("raasTxnRef"));

        Allure.step("Step.72 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.73 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "event_type"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "response_code"), "202");

        Allure.step("Step.74 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

    }

    // Data staging for use in test
    @DataProvider(name = "transactV2NullFieldtestcases", parallel = false)
    public Object[] transactV2NullFieldtestcases() throws IOException, ParseException {

        return new String[][]{


                {testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_PurchaseAmount_TestCase", "accountIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_PurchaseAmount_TestCase", "amount"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_PurchaseAmount_TestCase", "channelId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_PurchaseAmount_TestCase", "channelName"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_PurchaseAmount_TestCase", "channelSessionId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_PurchaseAmount_TestCase", "clientId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_PurchaseAmount_TestCase", "clientTxnRef"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_PurchaseAmount_TestCase", "productId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_PurchaseAmount_TestCase", "sourceIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_PurchaseAmount_TestCase", "targetIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_PurchaseAmount_TestCase", "timestamp"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_PurchaseAmount_TestCase", "reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_PurchaseAmount_TestCase", "expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_PurchaseAmount_TestCase", "expectedMessage"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_PurchaseAmount_TestCase", "expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_PurchaseAmount_TestCase", "expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_PurchaseAmount_TestCase", "expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_PurchaseAmount_TestCase", "expectedCTXTransactionResponseCode")},

                {testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelId_TestCase", "accountIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelId_TestCase", "amount"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelId_TestCase", "channelId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelId_TestCase", "channelName"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelId_TestCase", "channelSessionId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelId_TestCase", "clientId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelId_TestCase", "clientTxnRef"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelId_TestCase", "productId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelId_TestCase", "sourceIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelId_TestCase", "targetIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelId_TestCase", "timestamp"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelId_TestCase", "reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelId_TestCase", "expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelId_TestCase", "expectedMessage"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelId_TestCase", "expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelId_TestCase", "expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelId_TestCase", "expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelId_TestCase", "expectedCTXTransactionResponseCode")},

                {testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelName_TestCase", "accountIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelName_TestCase", "amount"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelName_TestCase", "channelId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelName_TestCase", "channelName"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelName_TestCase", "channelSessionId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelName_TestCase", "clientId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelName_TestCase", "clientTxnRef"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelName_TestCase", "productId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelName_TestCase", "sourceIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelName_TestCase", "targetIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelName_TestCase", "timestamp"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelName_TestCase", "reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelName_TestCase", "expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelName_TestCase", "expectedMessage"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelName_TestCase", "expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelName_TestCase", "expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelName_TestCase", "expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelName_TestCase", "expectedCTXTransactionResponseCode")},

                {testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelSessionId_TestCase", "accountIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelSessionId_TestCase", "amount"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelSessionId_TestCase", "channelId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelSessionId_TestCase", "channelName"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelSessionId_TestCase", "channelSessionId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelSessionId_TestCase", "clientId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelSessionId_TestCase", "clientTxnRef"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelSessionId_TestCase", "productId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelSessionId_TestCase", "sourceIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelSessionId_TestCase", "targetIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelSessionId_TestCase", "timestamp"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelSessionId_TestCase", "reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelSessionId_TestCase", "expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelSessionId_TestCase", "expectedMessage"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelSessionId_TestCase", "expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelSessionId_TestCase", "expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelSessionId_TestCase", "expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ChannelSessionId_TestCase", "expectedCTXTransactionResponseCode")},

                {testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ClientId_TestCase", "accountIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ClientId_TestCase", "amount"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ClientId_TestCase", "channelId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ClientId_TestCase", "channelName"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ClientId_TestCase", "channelSessionId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ClientId_TestCase", "clientId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ClientId_TestCase", "clientTxnRef"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ClientId_TestCase", "productId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ClientId_TestCase", "sourceIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ClientId_TestCase", "targetIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ClientId_TestCase", "timestamp"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ClientId_TestCase", "reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ClientId_TestCase", "expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ClientId_TestCase", "expectedMessage"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ClientId_TestCase", "expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ClientId_TestCase", "expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ClientId_TestCase", "expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_ClientId_TestCase", "expectedCTXTransactionResponseCode")},

                {testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_clientTxnRef_TestCase", "accountIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_clientTxnRef_TestCase", "amount"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_clientTxnRef_TestCase", "channelId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_clientTxnRef_TestCase", "channelName"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_clientTxnRef_TestCase", "channelSessionId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_clientTxnRef_TestCase", "clientId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_clientTxnRef_TestCase", "clientTxnRef"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_clientTxnRef_TestCase", "productId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_clientTxnRef_TestCase", "sourceIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_clientTxnRef_TestCase", "targetIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_clientTxnRef_TestCase", "timestamp"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_clientTxnRef_TestCase", "reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_clientTxnRef_TestCase", "expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_clientTxnRef_TestCase", "expectedMessage"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_clientTxnRef_TestCase", "expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_clientTxnRef_TestCase", "expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_clientTxnRef_TestCase", "expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_clientTxnRef_TestCase", "expectedCTXTransactionResponseCode")},

                {testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_productId_TestCase", "accountIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_productId_TestCase", "amount"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_productId_TestCase", "channelId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_productId_TestCase", "channelName"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_productId_TestCase", "channelSessionId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_productId_TestCase", "clientId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_productId_TestCase", "clientTxnRef"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_productId_TestCase", "productId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_productId_TestCase", "sourceIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_productId_TestCase", "targetIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_productId_TestCase", "timestamp"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_productId_TestCase", "reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_productId_TestCase", "expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_productId_TestCase", "expectedMessage"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_productId_TestCase", "expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_productId_TestCase", "expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_productId_TestCase", "expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_productId_TestCase", "expectedCTXTransactionResponseCode")},

                {testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_sourceIdentifier_TestCase", "accountIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_sourceIdentifier_TestCase", "amount"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_sourceIdentifier_TestCase", "channelId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_sourceIdentifier_TestCase", "channelName"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_sourceIdentifier_TestCase", "channelSessionId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_sourceIdentifier_TestCase", "clientId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_sourceIdentifier_TestCase", "clientTxnRef"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_sourceIdentifier_TestCase", "productId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_sourceIdentifier_TestCase", "sourceIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_sourceIdentifier_TestCase", "targetIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_sourceIdentifier_TestCase", "timestamp"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_sourceIdentifier_TestCase", "reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_sourceIdentifier_TestCase", "expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_sourceIdentifier_TestCase", "expectedMessage"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_sourceIdentifier_TestCase", "expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_sourceIdentifier_TestCase", "expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_sourceIdentifier_TestCase", "expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_sourceIdentifier_TestCase", "expectedCTXTransactionResponseCode")},

                {testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_targetIdentifier_TestCase", "accountIdentifier"),
                        testDataFactory.getTestData("TransactV3datasource.json", "transactv3suite", "MTNNG_Null_Field_targetIdentifier_TestCase", "amount"),
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

                {testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_Timestamp_TestCase", "accountIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_Timestamp_TestCase", "amount"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_Timestamp_TestCase", "channelId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_Timestamp_TestCase", "channelName"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_Timestamp_TestCase", "channelSessionId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_Timestamp_TestCase", "clientId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_Timestamp_TestCase", "clientTxnRef"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_Timestamp_TestCase", "productId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_Timestamp_TestCase", "sourceIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_Timestamp_TestCase", "targetIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_Timestamp_TestCase", "timestamp"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_Timestamp_TestCase", "reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_Timestamp_TestCase", "expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_Timestamp_TestCase", "expectedMessage"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_Timestamp_TestCase", "expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_Timestamp_TestCase", "expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_Timestamp_TestCase", "expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Null_Field_Timestamp_TestCase", "expectedCTXTransactionResponseCode")}
        };

    }


    // Action step
    @Step("Transact V2 Null Field Negative Test Cases")
    @Description("Transact V2 Null Field tests")
    @Test(dataProvider = "transactV2NullFieldtestcases", priority = 1)
    public void TransactV2NullFieldTests(String accountIdentifier,
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
                                         String expectedRaasResponseCode,
                                         String expectedMessage,
                                         String expectedHTTPResponseCode,
                                         String expectedRaasResultRequestResponseCode,
                                         String expectedRaasResultResponseResponseCode,
                                         String expectedCTXTransactionResponseCode) throws IOException, InterruptedException {


        // Create transactV2 payload object - contains transactV4 request body
        coreTransactV2POJO TransactV2payload = new coreTransactV2POJO(accountIdentifier,
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
                reserveFundsTxnRef);

        // Create transactV2 response body object - contains api response data for use in assertions or other calls
        Allure.step("Action Test Step 1 : Execute Transact V2 REST Request");
        Response TransactV2response =
                given(CORE_getEndPoints_TransactV2)
                        .contentType(ContentType.JSON)
                        .body(TransactV2payload)
                        .when()
                        .post()
                        .then()
                        .extract()
                        .response();

        // Assertions

        // Transact V2 response assertions - purchase

        Allure.step("Step.1 --> Transact V2 REST response assertions --> Validate responseCode field is correct");
        Assert.assertEquals(TransactV2response.path("responseCode"), expectedRaasResponseCode);

        Allure.step("Step.2 --> Transact V2 REST response assertions --> Validate responseMessage field is correct");
        Assert.assertEquals(TransactV2response.path("responseMessage"), expectedMessage);

        Allure.step("Step.3 --> Transact V2 REST response assertions --> Validate HTTPResponseCode field is correct");
        Assert.assertEquals(TransactV2response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

    }

    // Data staging for use in test
    @DataProvider(name = "transactV2RetryableDeclineToOKTestcases", parallel = false)
    public Object[] transactV2RetryableDeclineToOKTestcases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Retryable_Decline_To_OK", "accountIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Retryable_Decline_To_OK", "amount"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Retryable_Decline_To_OK", "channelId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Retryable_Decline_To_OK", "channelName"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Retryable_Decline_To_OK", "channelSessionId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Retryable_Decline_To_OK", "clientId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Retryable_Decline_To_OK", "clientTxnRef"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Retryable_Decline_To_OK", "productId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Retryable_Decline_To_OK", "sourceIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Retryable_Decline_To_OK", "targetIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Retryable_Decline_To_OK", "timestamp"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Retryable_Decline_To_OK", "reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Retryable_Decline_To_OK", "expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Retryable_Decline_To_OK", "expectedMessage"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Retryable_Decline_To_OK", "expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Retryable_Decline_To_OK", "expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Retryable_Decline_To_OK", "expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Retryable_Decline_To_OK", "expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Retryable_Decline_To_OK", "simulatorScenario"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Retryable_Decline_To_OK", "simulatorResetState")}
        };

    }

    // Action step
    @Step("Transact V2 Retryable Decline To OK test cases")
    @Description("Transact V2 Retryable Decline To OK tests")
    @Test(dataProvider = "transactV2RetryableDeclineToOKTestcases", priority = 2)
    public void TransactV2RetryableDeclineToOKTests(String accountIdentifier,
                                               String amount,
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
                                               String expectedRaasResponseCode,
                                               String expectedMessage,
                                               String expectedHTTPResponseCode,
                                               String expectedRaasResultRequestResponseCode,
                                               String expectedRaasResultResponseResponseCode,
                                               String expectedCTXTransactionResponseCode,
                                               String simulatorScenario,
                                               String simulatorResetState) throws IOException, InterruptedException, JSchException {

        Allure.step("Action Test Step 1 : Set Vendor simulator to fail (MTNNG ERROR ERROR_VENDOR_SYSTEM)");
        startSim.SimulatorScenario(simulatorScenario);

        Allure.step("Action Test Step 2 : Wait 5 seconds for simulator to run");
        Thread.sleep(5000);

        // Financial Terms Calculate GET method call
        Allure.step("Action Test Step 3 : Execute Fin Terms Calculate REST Request");
        Response finTermsCalculateResponse =
                given(CORE_getEndPoints_FinancialTermsCalculate)
                        .param("clientId", clientId)
                        .param("productId", productId)
                        .param("purchaseAmount", amount)
                        .when()
                        .get()
                        .then()
                        .extract()
                        .response();

        // Create transactV2 payload object - contains transactV2 request body
        coreTransactV2POJO TransactV2payload = new coreTransactV2POJO(accountIdentifier,
                amount,
                channelId,
                channelName,
                channelSessionId,
                clientId,
                clientTxnRef,
                productId,
                sourceIdentifier,
                targetIdentifier,
                timeStamp,
                reserveFundsTxnRef);

        // Create transactV2 response body object - contains api response data for use in assertions or other calls
        Allure.step("Action Test Step 4 : Execute Transact V2 REST Request");
        Response TransactV2response =
                given(CORE_getEndPoints_TransactV2)
                        .contentType(ContentType.JSON)
                        .body(TransactV2payload)
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
        Assert.assertEquals(finTermsCalculateResponse.path("purchaseAmount").toString(), amount);

        // Transact V2 response assertions - purchase

        Allure.step("Step.6 --> Transact V2 REST response assertions --> Validate responseCode field is correct");
        Assert.assertEquals(TransactV2response.path("responseCode"), expectedRaasResponseCode);

        Allure.step("Step.7 --> Transact V2 REST response assertions --> Validate responseMessage field is correct");
        Assert.assertEquals(TransactV2response.path("responseMessage"), expectedMessage);

        Allure.step("Step.8 --> Transact V2 REST response assertions --> Validate raasTxnRef field exists");
        Assert.assertNotNull(TransactV2response.path("raasTxnRef"));

        Allure.step("Step.9 --> Transact V2 REST response assertions --> Validate HTTPResponseCode field exists");
        Assert.assertEquals(TransactV2response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // raas db assertions
        //Transaction_log

        Allure.step("Action Test Step 7 : Wait 15 seconds before executing RAAS Transaction_Log DB assertions");
        Thread.sleep(15000);

        Allure.step("Step.10 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV2response.path("raasTxnRef"));

        Allure.step("Step.11 --> RAAS DB assertions --> Table: transaction_log --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.12 --> RAAS DB assertions --> Table: transaction_log --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "amount"), amount);

        Allure.step("Step.13 --> RAAS DB assertions --> Table: transaction_log --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.14 --> RAAS DB assertions --> Table: transaction_log --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.15 --> RAAS DB assertions --> Table: transaction_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.16 --> RAAS DB assertions --> Table: transaction_log --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.17 --> RAAS DB assertions --> Table: transaction_log --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.18 --> RAAS DB assertions --> Table: transaction_log --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.19 --> RAAS DB assertions --> Table: transaction_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.20 --> RAAS DB assertions --> Table: transaction_log --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.21 --> RAAS DB assertions --> Table: transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.22 --> RAAS DB assertions --> Table: transaction_log --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.23 --> RAAS DB assertions --> Table: transaction_log --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.24 --> RAAS DB assertions --> Table: transaction_log --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.25 --> RAAS DB assertions --> Table: transaction_log --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.26 --> RAAS DB assertions --> Table: transaction_log --> Validate reserve_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "reserve_amount"), amount);

        Allure.step("Step.27 --> RAAS DB assertions --> Table: transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.28 --> RAAS DB assertions --> Table: transaction_log --> Validate fee_amount field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.29 --> RAAS DB assertions --> Table: transaction_log --> Validate client_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.30 --> RAAS DB assertions --> Table: transaction_log --> Validate settlement_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.31 --> RAAS DB assertions --> Table: transaction_log --> Validate vend_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "vend_amount"), amount);

        Allure.step("Step.32 --> RAAS DB assertions --> Table: transaction_log --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "vendor_share_amount"));

        //Allure.step("Step.33 --> RAAS DB assertions --> Table: transaction_log --> Validate status field is correct");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);

        Allure.step("Step.34 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "raas_request_created"));

        Allure.step("Step.35 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "raas_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");
        Allure.step("Step.36 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "transaction_result_request_created"));

        Allure.step("Step.37 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "transaction_result_response_created"));

        Allure.step("Step.38 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);

        Allure.step("Step.39 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");
        Allure.step("Step.40 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_request_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);

        Allure.step("Step.41 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");

        Allure.step("Step.43 --> RAAS DB assertions --> Table: transaction_log --> Validate funding_source_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "funding_source_id"), clientId);

        Allure.step("Step.44 --> RAAS DB assertions --> Table: transaction_log --> Validate additional_data_financial_calculations field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response

        Allure.step("Step.45 --> RAAS DB assertions --> Table: raas.raas_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", TransactV2response.path("raasTxnRef")), TransactV2response.path("raasTxnRef"));

        Allure.step("Step.46 --> RAAS DB assertions --> Table: raas.raas_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.47 --> RAAS DB assertions --> Table: raas.raas_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "event_type"));

        // CTX DB assertions

        Allure.step("Action Test Step 8 : Wait 5 seconds before executing CTX Tran_Log DB assertions");
        Thread.sleep(5000);

        Allure.step("Step.48 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate transactionResponseCode field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV2response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);

        Allure.step("Step.49 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate transactionId field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV2response.path("raasTxnRef") + "-0000'", "transactionId"));

        Allure.step("Step.50 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate clientStan field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV2response.path("raasTxnRef") + "-0000'", "clientStan"));

        Allure.step("Step.51 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate originId field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV2response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);

        Allure.step("Step.52 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate originatingService field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV2response.path("raasTxnRef") + "-0000'", "originatingService"));

        Allure.step("Step.53 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate purchaseAmount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV2response.path("raasTxnRef") + "-0000'", "purchaseAmount"), amount);

        Allure.step("Step.54 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate transactionState field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV2response.path("raasTxnRef") + "-0000'", "transactionState"), "C");

        Allure.step("Step.55 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate transactionType field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV2response.path("raasTxnRef") + "-0000'", "transactionType"), "P");

        Allure.step("Step.56 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV2response.path("raasTxnRef") + "-0000'", "client_id"), clientId);

        Allure.step("Step.57 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV2response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        // Transaction_result_request

        Allure.step("Step.58 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);

        Allure.step("Step.59 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.60 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.61 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.62 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV2response.path("raasTxnRef"));

        Allure.step("Step.63 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.64 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.65 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate reserve_amount field exists");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "reserve_amount"), amount);

        Allure.step("Step.66 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.67 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate client_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.68 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate settlement_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.69 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate vend_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "vend_amount"), amount);

        Allure.step("Step.70 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // Transaction_result_response

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);

        Allure.step("Step.71 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV2response.path("raasTxnRef"));

        Allure.step("Step.72 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.73 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "event_type"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "response_code"), "202");

        Allure.step("Step.74 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

    }

    // Data staging for use in test
    @DataProvider(name = "transactV2PendingToOKTestcases", parallel = false)
    public Object[] transactV2PendingToOKTestcases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Pending_To_OK_Test", "accountIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Pending_To_OK_Test", "amount"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Pending_To_OK_Test", "channelId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Pending_To_OK_Test", "channelName"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Pending_To_OK_Test", "channelSessionId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Pending_To_OK_Test", "clientId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Pending_To_OK_Test", "clientTxnRef"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Pending_To_OK_Test", "productId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Pending_To_OK_Test", "sourceIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Pending_To_OK_Test", "targetIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Pending_To_OK_Test", "timestamp"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Pending_To_OK_Test", "reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Pending_To_OK_Test", "expectedMessage"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Pending_To_OK_Test", "expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Pending_To_OK_Test", "expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Pending_To_OK_Test", "expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Pending_To_OK_Test", "expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Pending_To_OK_Test", "simulatorScenario"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Pending_To_OK_Test", "simulatorResetState")}
        };

    }

    // Action step
    @Step("Transact V2 Pending To OK test cases")
    @Description("Transact V2 Pending To OK tests")
    @Test(dataProvider = "transactV2PendingToOKTestcases", priority = 3)
    public void TransactV2transactV2PendingToOKTests(String accountIdentifier,
                                                    String amount,
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
                                                    String expectedRaasResponseCode,
                                                    String expectedMessage,
                                                    String expectedHTTPResponseCode,
                                                    String expectedRaasResultRequestResponseCode,
                                                    String expectedRaasResultResponseResponseCode,
                                                    String expectedCTXTransactionResponseCode,
                                                    String simulatorScenario,
                                                    String simulatorResetState) throws IOException, InterruptedException, JSchException {

        Allure.step("Action Test Step 1 : Set Vendor simulator to fail (MTNNG ERROR ERROR_VENDOR_SYSTEM)");
        startSim.SimulatorScenario(simulatorScenario);

        Allure.step("Action Test Step 2 : Wait 5 seconds for simulator to run");
        Thread.sleep(5000);

        // Financial Terms Calculate GET method call
        Allure.step("Action Test Step 3 : Execute Fin Terms Calculate REST Request");
        Response finTermsCalculateResponse =
                given(CORE_getEndPoints_FinancialTermsCalculate)
                        .param("clientId", clientId)
                        .param("productId", productId)
                        .param("purchaseAmount", amount)
                        .when()
                        .get()
                        .then()
                        .extract()
                        .response();

        // Create transactV2 payload object - contains transactV2 request body
        coreTransactV2POJO TransactV2payload = new coreTransactV2POJO(accountIdentifier,
                amount,
                channelId,
                channelName,
                channelSessionId,
                clientId,
                clientTxnRef,
                productId,
                sourceIdentifier,
                targetIdentifier,
                timeStamp,
                reserveFundsTxnRef);

        // Create transactV2 response body object - contains api response data for use in assertions or other calls
        Allure.step("Action Test Step 4 : Execute Transact V2 REST Request");
        Response TransactV2response =
                given(CORE_getEndPoints_TransactV2)
                        .contentType(ContentType.JSON)
                        .body(TransactV2payload)
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
        Assert.assertEquals(finTermsCalculateResponse.path("purchaseAmount").toString(), amount);

        // Transact V2 response assertions - purchase

        Allure.step("Step.6 --> Transact V2 REST response assertions --> Validate responseCode field is correct");
        Assert.assertEquals(TransactV2response.path("responseCode"), expectedRaasResponseCode);

        Allure.step("Step.7 --> Transact V2 REST response assertions --> Validate responseMessage field is correct");
        Assert.assertEquals(TransactV2response.path("responseMessage"), expectedMessage);

        Allure.step("Step.8 --> Transact V2 REST response assertions --> Validate raasTxnRef field exists");
        Assert.assertNotNull(TransactV2response.path("raasTxnRef"));

        Allure.step("Step.9 --> Transact V2 REST response assertions --> Validate HTTPResponseCode field exists");
        Assert.assertEquals(TransactV2response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // raas db assertions
        //Transaction_log

        Allure.step("Action Test Step 7 : Wait 15 seconds before executing RAAS Transaction_Log DB assertions");
        Thread.sleep(15000);

        Allure.step("Step.10 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV2response.path("raasTxnRef"));

        Allure.step("Step.11 --> RAAS DB assertions --> Table: transaction_log --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.12 --> RAAS DB assertions --> Table: transaction_log --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "amount"), amount);

        Allure.step("Step.13 --> RAAS DB assertions --> Table: transaction_log --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.14 --> RAAS DB assertions --> Table: transaction_log --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.15 --> RAAS DB assertions --> Table: transaction_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.16 --> RAAS DB assertions --> Table: transaction_log --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.17 --> RAAS DB assertions --> Table: transaction_log --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.18 --> RAAS DB assertions --> Table: transaction_log --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.19 --> RAAS DB assertions --> Table: transaction_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.20 --> RAAS DB assertions --> Table: transaction_log --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.21 --> RAAS DB assertions --> Table: transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.22 --> RAAS DB assertions --> Table: transaction_log --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.23 --> RAAS DB assertions --> Table: transaction_log --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.24 --> RAAS DB assertions --> Table: transaction_log --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.25 --> RAAS DB assertions --> Table: transaction_log --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.26 --> RAAS DB assertions --> Table: transaction_log --> Validate reserve_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "reserve_amount"), amount);

        Allure.step("Step.27 --> RAAS DB assertions --> Table: transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.28 --> RAAS DB assertions --> Table: transaction_log --> Validate fee_amount field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.29 --> RAAS DB assertions --> Table: transaction_log --> Validate client_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.30 --> RAAS DB assertions --> Table: transaction_log --> Validate settlement_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.31 --> RAAS DB assertions --> Table: transaction_log --> Validate vend_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "vend_amount"), amount);

        Allure.step("Step.32 --> RAAS DB assertions --> Table: transaction_log --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "vendor_share_amount"));

        //Allure.step("Step.33 --> RAAS DB assertions --> Table: transaction_log --> Validate status field is correct");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);

        Allure.step("Step.34 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "raas_request_created"));

        Allure.step("Step.35 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "raas_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");
        Allure.step("Step.36 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "transaction_result_request_created"));

        Allure.step("Step.37 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "transaction_result_response_created"));

        Allure.step("Step.38 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);

        Allure.step("Step.39 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");
        Allure.step("Step.40 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_request_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);

        Allure.step("Step.41 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");

        Allure.step("Step.43 --> RAAS DB assertions --> Table: transaction_log --> Validate funding_source_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "funding_source_id"), clientId);

        Allure.step("Step.44 --> RAAS DB assertions --> Table: transaction_log --> Validate additional_data_financial_calculations field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response

        Allure.step("Step.45 --> RAAS DB assertions --> Table: raas.raas_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", TransactV2response.path("raasTxnRef")), TransactV2response.path("raasTxnRef"));

        Allure.step("Step.46 --> RAAS DB assertions --> Table: raas.raas_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.47 --> RAAS DB assertions --> Table: raas.raas_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "event_type"));

        // CTX DB assertions

        Allure.step("Action Test Step 8 : Wait 5 seconds before executing CTX Tran_Log DB assertions");
        Thread.sleep(5000);

        Allure.step("Step.48 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate transactionResponseCode field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV2response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);

        Allure.step("Step.49 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate transactionId field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV2response.path("raasTxnRef") + "-0000'", "transactionId"));

        Allure.step("Step.50 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate clientStan field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV2response.path("raasTxnRef") + "-0000'", "clientStan"));

        Allure.step("Step.51 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate originId field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV2response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);

        Allure.step("Step.52 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate originatingService field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV2response.path("raasTxnRef") + "-0000'", "originatingService"));

        Allure.step("Step.53 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate purchaseAmount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV2response.path("raasTxnRef") + "-0000'", "purchaseAmount"), amount);

        Allure.step("Step.54 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate transactionState field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV2response.path("raasTxnRef") + "-0000'", "transactionState"), "C");

        Allure.step("Step.55 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate transactionType field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV2response.path("raasTxnRef") + "-0000'", "transactionType"), "P");

        Allure.step("Step.56 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV2response.path("raasTxnRef") + "-0000'", "client_id"), clientId);

        Allure.step("Step.57 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV2response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        // Transaction_result_request

        Allure.step("Step.58 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);

        Allure.step("Step.59 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.60 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.61 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.62 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV2response.path("raasTxnRef"));

        Allure.step("Step.63 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.64 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.65 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate reserve_amount field exists");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "reserve_amount"), amount);

        Allure.step("Step.66 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.67 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate client_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.68 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate settlement_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.69 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate vend_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "vend_amount"), amount);

        Allure.step("Step.70 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // Transaction_result_response

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);

        Allure.step("Step.71 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV2response.path("raasTxnRef"));

        Allure.step("Step.72 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.73 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "event_type"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "response_code"), "202");

        Allure.step("Step.74 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

    }

    // Data staging for use in test
    @DataProvider(name = "transactV2PendingToROKTestcases", parallel = false)
    public Object[] transactV2PendingToROKTestcases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Pending_To_Retryable_OK", "accountIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Pending_To_Retryable_OK", "amount"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Pending_To_Retryable_OK", "channelId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Pending_To_Retryable_OK", "channelName"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Pending_To_Retryable_OK", "channelSessionId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Pending_To_Retryable_OK", "clientId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Pending_To_Retryable_OK", "clientTxnRef"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Pending_To_Retryable_OK", "productId"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Pending_To_Retryable_OK", "sourceIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Pending_To_Retryable_OK", "targetIdentifier"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Pending_To_Retryable_OK", "timestamp"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Pending_To_Retryable_OK", "reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Pending_To_Retryable_OK", "expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Pending_To_Retryable_OK", "expectedMessage"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Pending_To_Retryable_OK", "expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Pending_To_Retryable_OK", "expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Pending_To_Retryable_OK", "expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Pending_To_Retryable_OK", "expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Pending_To_Retryable_OK", "simulatorScenario"),
                        testDataFactory.getTestData("TransactV2datasource.json", "transactv2suite", "MTNNG_Pending_To_Retryable_OK", "simulatorResetState")}
        };

    }

    // Action step
    @Step("Transact V2 Pending To ROK test cases")
    @Description("Transact V2 Pending To ROK tests")
    @Test(dataProvider = "transactV2PendingToROKTestcases", priority = 4)
    public void TransactV2transactV2PendingToROKTests(String accountIdentifier,
                                                     String amount,
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
        Allure.step("Action Test Step 3 : Execute Fin Terms Calculate REST Request");
        Response finTermsCalculateResponse =
                given(CORE_getEndPoints_FinancialTermsCalculate)
                        .param("clientId", clientId)
                        .param("productId", productId)
                        .param("purchaseAmount", amount)
                        .when()
                        .get()
                        .then()
                        .extract()
                        .response();

        // Create transactV2 payload object - contains transactV2 request body
        coreTransactV2POJO TransactV2payload = new coreTransactV2POJO(accountIdentifier,
                amount,
                channelId,
                channelName,
                channelSessionId,
                clientId,
                clientTxnRef,
                productId,
                sourceIdentifier,
                targetIdentifier,
                timeStamp,
                reserveFundsTxnRef);

        // Create transactV2 response body object - contains api response data for use in assertions or other calls
        Allure.step("Action Test Step 4 : Execute Transact V2 REST Request");
        Response TransactV2response =
                given(CORE_getEndPoints_TransactV2)
                        .contentType(ContentType.JSON)
                        .body(TransactV2payload)
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
        Assert.assertEquals(finTermsCalculateResponse.path("purchaseAmount").toString(), amount);

        // Transact V2 response assertions - purchase

        Allure.step("Step.6 --> Transact V2 REST response assertions --> Validate responseCode field is correct");
        Assert.assertEquals(TransactV2response.path("responseCode"), expectedRaasResponseCode);

        Allure.step("Step.7 --> Transact V2 REST response assertions --> Validate responseMessage field is correct");
        Assert.assertEquals(TransactV2response.path("responseMessage"), expectedMessage);

        Allure.step("Step.8 --> Transact V2 REST response assertions --> Validate raasTxnRef field exists");
        Assert.assertNotNull(TransactV2response.path("raasTxnRef"));

        Allure.step("Step.9 --> Transact V2 REST response assertions --> Validate HTTPResponseCode field exists");
        Assert.assertEquals(TransactV2response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // raas db assertions
        //Transaction_log

        Allure.step("Action Test Step 7 : Wait 15 seconds before executing RAAS Transaction_Log DB assertions");
        Thread.sleep(15000);

        Allure.step("Step.10 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV2response.path("raasTxnRef"));

        Allure.step("Step.11 --> RAAS DB assertions --> Table: transaction_log --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.12 --> RAAS DB assertions --> Table: transaction_log --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "amount"), amount);

        Allure.step("Step.13 --> RAAS DB assertions --> Table: transaction_log --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.14 --> RAAS DB assertions --> Table: transaction_log --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.15 --> RAAS DB assertions --> Table: transaction_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.16 --> RAAS DB assertions --> Table: transaction_log --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.17 --> RAAS DB assertions --> Table: transaction_log --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.18 --> RAAS DB assertions --> Table: transaction_log --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.19 --> RAAS DB assertions --> Table: transaction_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.20 --> RAAS DB assertions --> Table: transaction_log --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.21 --> RAAS DB assertions --> Table: transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.22 --> RAAS DB assertions --> Table: transaction_log --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.23 --> RAAS DB assertions --> Table: transaction_log --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.24 --> RAAS DB assertions --> Table: transaction_log --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.25 --> RAAS DB assertions --> Table: transaction_log --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.26 --> RAAS DB assertions --> Table: transaction_log --> Validate reserve_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "reserve_amount"), amount);

        Allure.step("Step.27 --> RAAS DB assertions --> Table: transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.28 --> RAAS DB assertions --> Table: transaction_log --> Validate fee_amount field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.29 --> RAAS DB assertions --> Table: transaction_log --> Validate client_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.30 --> RAAS DB assertions --> Table: transaction_log --> Validate settlement_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.31 --> RAAS DB assertions --> Table: transaction_log --> Validate vend_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "vend_amount"), amount);

        Allure.step("Step.32 --> RAAS DB assertions --> Table: transaction_log --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "vendor_share_amount"));

        //Allure.step("Step.33 --> RAAS DB assertions --> Table: transaction_log --> Validate status field is correct");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV3response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);

        Allure.step("Step.34 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "raas_request_created"));

        Allure.step("Step.35 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "raas_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");
        Allure.step("Step.36 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "transaction_result_request_created"));

        Allure.step("Step.37 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "transaction_result_response_created"));

        Allure.step("Step.38 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);

        Allure.step("Step.39 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");
        Allure.step("Step.40 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_request_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);

        Allure.step("Step.41 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");

        Allure.step("Step.43 --> RAAS DB assertions --> Table: transaction_log --> Validate funding_source_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "funding_source_id"), clientId);

        Allure.step("Step.44 --> RAAS DB assertions --> Table: transaction_log --> Validate additional_data_financial_calculations field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response

        Allure.step("Step.45 --> RAAS DB assertions --> Table: raas.raas_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", TransactV2response.path("raasTxnRef")), TransactV2response.path("raasTxnRef"));

        Allure.step("Step.46 --> RAAS DB assertions --> Table: raas.raas_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.47 --> RAAS DB assertions --> Table: raas.raas_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV2response.path("raasTxnRef") + "'", "event_type"));

        // CTX DB assertions

        Allure.step("Action Test Step 8 : Wait 5 seconds before executing CTX Tran_Log DB assertions");
        Thread.sleep(5000);

        Allure.step("Step.48 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate transactionResponseCode field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV2response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);

        Allure.step("Step.49 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate transactionId field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV2response.path("raasTxnRef") + "-0000'", "transactionId"));

        Allure.step("Step.50 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate clientStan field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV2response.path("raasTxnRef") + "-0000'", "clientStan"));

        Allure.step("Step.51 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate originId field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV2response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);

        Allure.step("Step.52 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate originatingService field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV2response.path("raasTxnRef") + "-0000'", "originatingService"));

        Allure.step("Step.53 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate purchaseAmount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV2response.path("raasTxnRef") + "-0000'", "purchaseAmount"), amount);

        Allure.step("Step.54 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate transactionState field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV2response.path("raasTxnRef") + "-0000'", "transactionState"), "C");

        Allure.step("Step.55 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate transactionType field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV2response.path("raasTxnRef") + "-0000'", "transactionType"), "P");

        Allure.step("Step.56 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV2response.path("raasTxnRef") + "-0000'", "client_id"), clientId);

        Allure.step("Step.57 --> RAAS DB assertions --> Table: cpgtx.tran_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV2response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        // Transaction_result_request

        Allure.step("Step.58 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);

        Allure.step("Step.59 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.60 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.61 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate reserve_funds_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.62 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV2response.path("raasTxnRef"));

        Allure.step("Step.63 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.64 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.65 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate reserve_amount field exists");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "reserve_amount"), amount);

        Allure.step("Step.66 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.67 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate client_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.68 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate settlement_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.69 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate vend_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "vend_amount"), amount);

        Allure.step("Step.70 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // Transaction_result_response

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);

        Allure.step("Step.71 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV2response.path("raasTxnRef"));

        Allure.step("Step.72 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.73 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "event_type"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV3response.path("raasTxnRef") + "'", "response_code"), "202");

        Allure.step("Step.74 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + TransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

    }


}
