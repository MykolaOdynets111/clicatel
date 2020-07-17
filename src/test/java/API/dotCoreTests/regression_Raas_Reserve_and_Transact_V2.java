package API.dotCoreTests;

import api.testUtilities.sqlDataAccessLayer.sqlDataAccess;
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
import api.requestLibary.CORE.coreReserveAndTransactV2POJO;

import api.testUtilities.dataBuilders.testDataFactory;

import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

import api.testUtilities.propertyConfigWrapper.configWrapper;
import util.Listeners.allureApiTestListener;
import api.testUtilities.Simulators.startSimulator;

@Listeners(allureApiTestListener.class)
public class regression_Raas_Reserve_and_Transact_V2 extends testConfig {

    // Create properties object in order to inject environment specific variables upon build
    Properties properties = configWrapper.loadPropertiesFile("config.properties");

    // Instantiate simulator instance
    startSimulator startSim = new startSimulator();

    // Data staging for use in test
    @DataProvider(name = "ReserveAndTransactV2StandardSuccessTestCases", parallel = false)
    public Object[] ReserveAndTransactV2StandardSuccessTestCases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNGPurchaseSuccessTest", "accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNGPurchaseSuccessTest", "amount"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNGPurchaseSuccessTest", "channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNGPurchaseSuccessTest", "channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNGPurchaseSuccessTest", "channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNGPurchaseSuccessTest", "clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNGPurchaseSuccessTest", "clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNGPurchaseSuccessTest", "productId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNGPurchaseSuccessTest", "sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNGPurchaseSuccessTest", "targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNGPurchaseSuccessTest", "timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNGPurchaseSuccessTest", "expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNGPurchaseSuccessTest", "expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNGPurchaseSuccessTest", "expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNGPurchaseSuccessTest", "expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNGPurchaseSuccessTest", "expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNGPurchaseSuccessTest", "expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNGPurchaseSuccessTest", "simulatorScenario"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNGPurchaseSuccessTest", "simulatorResetState")},

        };
    }

    @Step("Reserve and Transact V2 Standard Success test cases")
    @Test(dataProvider = "ReserveAndTransactV2StandardSuccessTestCases", priority = 0)
    public void ReserveAndTransactV2StandardSuccessTests(String accountIdentifier,
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
                                                         String expectedRaasResponseCode,
                                                         String expectedMessage,
                                                         String expectedHTTPResponseCode,
                                                         String expectedRaasResultRequestResponseCode,
                                                         String expectedRaasResultResponseResponseCode,
                                                         String expectedCTXTransactionResponseCode,
                                                         String simulatorScenario,
                                                         String simulatorResetState) throws IOException, InterruptedException, JSchException {

        // Set simulator to success
        Allure.step("Action Test Step 1 : Set Vendor simulator to SUCCESS");
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

        // Create ReserveAndtransactV2 payload object - contains transactV4 request body
        coreReserveAndTransactV2POJO ReserveAndTransactV2Payload = new coreReserveAndTransactV2POJO(
                accountIdentifier,
                amount,
                channelId,
                channelName,
                channelSessionId,
                clientId,
                clientTxnRef,
                productId,
                sourceIdentifier,
                targetIdentifier,
                timeStamp);

        // Create reserve and transact V2 response body object - contains api response data for use in assertions or other calls
        Allure.step("Action Test Step 4 : Execute Reserve and Transact V2 REST Request");
        Response ReserveAndTransactV2response =
                given(CORE_getEndPoints_ReserveAndTransactV2)
                        .contentType(ContentType.JSON)
                        .body(ReserveAndTransactV2Payload)
                        .when()
                        .post()
                        .then()
                        .extract()
                        .response();

        System.out.println(ReserveAndTransactV2response);

        Thread.sleep(10000);

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
        Assert.assertEquals(finTermsCalculateResponse.path("purchaseAmount").toString(), amount);

        // Reserve and Transact V4 response assertions - purchase

        Allure.step("Step.6 --> Reserve and Transact V2 REST response assertions --> Validate responseCode field is correct");
        Assert.assertEquals(ReserveAndTransactV2response.path("responseCode"), expectedRaasResponseCode);

        Allure.step("Step.7 --> Reserve and Transact V2 REST response assertions --> Validate responseMessage field is correct");
        Assert.assertEquals(ReserveAndTransactV2response.path("responseMessage"), expectedMessage);

        Allure.step("Step.8 --> Reserve and Transact V2 REST response assertions --> Validate raasTxnRef field exists");
        Assert.assertNotNull(ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.9 --> Reserve and Transact V2 REST response assertions --> Validate HTTPResponseCode field is correct");
        Assert.assertEquals(ReserveAndTransactV2response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // raas db assertions
        //Transaction_log

        Allure.step("Action Test Step 5 : Wait 10 seconds before executing the RAAS Transaction_Log DB Assertions");
        Thread.sleep(10000);

        Allure.step("Step.10 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.11 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.12 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "amount"), amount);

        Allure.step("Step.13 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.14 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.15 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.16 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.17 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.18 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.19 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.20 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.21 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.22 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "timestamp"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.23 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.24 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.25 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "reserve_amount"), amount);

        Allure.step("Step.26 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.27 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.28 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.29 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate settlement_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.30 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate settlement_amount field exists");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vend_amount"), amount);

        Allure.step("Step.31 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vendor_share_amount"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);

        Allure.step("Step.32 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_request_created"));

        Allure.step("Step.33 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_response_created"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");

        Allure.step("Step.34 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate transaction_result_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "transaction_result_request_created"));

        Allure.step("Step.35 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate transaction_result_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "transaction_result_response_created"));

        Allure.step("Step.36 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);

        Allure.step("Step.37 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_response_message field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");

        Allure.step("Step.38 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate transaction_result_request_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);

        Allure.step("Step.39 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate transaction_result_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");

        Allure.step("Step.40 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate additional_data_financial_calculations field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Raas_Request

        Allure.step("Step.42 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", ReserveAndTransactV2response.path("raasTxnRef")), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.43 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.44 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "amount"), amount);

        Allure.step("Step.45 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.46 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.47 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.48 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.49 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.50 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.51 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.52 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.53 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.54 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.55 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "timestamp"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.56 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.57 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_name field exists");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.59 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate api_call field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "api_call"), "reserveAndTransact-v2");

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response

        Allure.step("Step.60 --> RAAS DB assertions --> Table: raas.raas_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", ReserveAndTransactV2response.path("raasTxnRef")), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.61 --> RAAS DB assertions --> Table: raas.raas_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.62 --> RAAS DB assertions --> Table: raas.raas_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"));

        // CTX DB assertions

        Allure.step("Action Test Step 6 : Wait 5 seconds before executing the CTX DB Assertions");
        Thread.sleep(5000);

        Allure.step("Step.63 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionResponseCode field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);

        Allure.step("Step.64 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionId field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "transactionId"));

        Allure.step("Step.65 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate clientStan field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "clientStan"));

        Allure.step("Step.66 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originId field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);

        Allure.step("Step.67 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originatingService field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "originatingService"));

        Allure.step("Step.68 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate purchaseAmount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "purchaseAmount"), amount);

        Allure.step("Step.69 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionState field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "transactionState"), "C");

        Allure.step("Step.70 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionType field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "transactionType"), "P");

        Allure.step("Step.71 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "client_id"), clientId);

        Allure.step("Step.72 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        // raas reserve_fund_request

        Allure.step("Step.73 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.74 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "amount"), amount);

        Allure.step("Step.75 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.76 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.77 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.78 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.79 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.80 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate event_type field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_REQUEST_EVENT");

        Allure.step("Step.81 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate event_type field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.82 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate product_type field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "product_type"), "3");

        Allure.step("Step.83 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.84 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.85 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.86 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        //Allure.step("Step.87 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate reserve_amount field is correct");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "reserve_amount"), amount);

        //Allure.step("Step.88 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate fee_amount field exists");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "fee_amount"));

        //Allure.step("Step.89 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate client_share_amount field exists");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_share_amount"));

        //Allure.step("Step.90 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate settlement_amount field exists");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "settlement_amount"));

        //Allure.step("Step.91 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate vend_amount field is correct");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vend_amount"), amount);

        //Allure.step("Step.92 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate vendor_share_amount field exists");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // raas reserve_fund_response

        Allure.step("Step.93 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.94 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.95 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate created field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_RESPONSE_EVENT");

        Allure.step("Step.96 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate reserve_funds_txn_ref field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"));

        Allure.step("Step.97 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);

        Allure.step("Step.98 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Transaction_result_request

        Allure.step("Step.99 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);

        Allure.step("Step.100 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.101 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.102 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.103 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.104 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.105 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate cdc_update_timestamp field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "reserve_amount"), amount);

        Allure.step("Step.106 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.107 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate client_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.108 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate settlement_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.109 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate vend_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vend_amount"), amount);

        Allure.step("Step.110 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // Transaction_result_response

        Allure.step("Step.111 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);

        Allure.step("Step.112 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.113 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.114 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.115 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "response_code"), "202");

        Allure.step("Step.116 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

    }

    // Data staging for use in test
    @DataProvider(name = "ReserveAndTransactV2NullFieldTestCases", parallel = true)
    public Object[] ReserveAndTransactV2NullFieldTestCases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_PurchaseAmount_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_PurchaseAmount_Test","amount"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_PurchaseAmount_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_PurchaseAmount_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_PurchaseAmount_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_PurchaseAmount_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_PurchaseAmount_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_PurchaseAmount_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_PurchaseAmount_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_PurchaseAmount_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_PurchaseAmount_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_PurchaseAmount_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_PurchaseAmount_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_PurchaseAmount_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_PurchaseAmount_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_PurchaseAmount_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_PurchaseAmount_Test","expectedCTXTransactionResponseCode")},

                {testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelId_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelId_Test","amount"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelId_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelId_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelId_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelId_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelId_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelId_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelId_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelId_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelId_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelId_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelId_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelId_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelId_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelId_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelId_Test","expectedCTXTransactionResponseCode")},

                {testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelName_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelName_Test","amount"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelName_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelName_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelName_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelName_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelName_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelName_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelName_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelName_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelName_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelName_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelName_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelName_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelName_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelName_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelName_Test","expectedCTXTransactionResponseCode")},

                {testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelSessionId_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelSessionId_Test","amount"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelSessionId_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelSessionId_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelSessionId_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelSessionId_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelSessionId_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelSessionId_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelSessionId_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelSessionId_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelSessionId_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelSessionId_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelSessionId_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelSessionId_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelSessionId_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelSessionId_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_channelSessionId_Test","expectedCTXTransactionResponseCode")},

                {testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_clientId_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_clientId_Test","amount"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_clientId_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_clientId_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_clientId_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_clientId_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_clientId_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_clientId_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_clientId_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_clientId_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_clientId_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_clientId_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_clientId_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_clientId_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_clientId_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_clientId_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_clientId_Test","expectedCTXTransactionResponseCode")},

                {testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_productId_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_productId_Test","amount"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_productId_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_productId_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_productId_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_productId_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_productId_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_productId_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_productId_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_productId_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_productId_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_productId_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_productId_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_productId_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_productId_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_productId_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_productId_Test","expectedCTXTransactionResponseCode")},

                {testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_sourceIdentifier_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_sourceIdentifier_Test","amount"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_sourceIdentifier_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_sourceIdentifier_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_sourceIdentifier_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_sourceIdentifier_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_sourceIdentifier_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_sourceIdentifier_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_sourceIdentifier_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_sourceIdentifier_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_sourceIdentifier_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_sourceIdentifier_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_sourceIdentifier_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_sourceIdentifier_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_sourceIdentifier_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_sourceIdentifier_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_sourceIdentifier_Test","expectedCTXTransactionResponseCode")},

                {testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_targetIdentifier_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_targetIdentifier_Test","amount"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_targetIdentifier_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_targetIdentifier_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_targetIdentifier_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_targetIdentifier_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_targetIdentifier_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_targetIdentifier_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_targetIdentifier_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_targetIdentifier_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_targetIdentifier_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_targetIdentifier_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_targetIdentifier_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_targetIdentifier_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_targetIdentifier_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_targetIdentifier_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_targetIdentifier_Test","expectedCTXTransactionResponseCode")},

                {testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_timestamp_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_timestamp_Test","amount"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_timestamp_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_timestamp_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_timestamp_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_timestamp_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_timestamp_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_timestamp_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_timestamp_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_timestamp_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_timestamp_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_timestamp_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_timestamp_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_timestamp_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_timestamp_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_timestamp_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Null_timestamp_Test","expectedCTXTransactionResponseCode")},


        };
    }

    @Step("Reserve and Transact V2 Standard Null Field test cases")
    @Test(dataProvider = "ReserveAndTransactV2NullFieldTestCases", priority = 1)
    public void ReserveAndTransactV2NullFieldTests(String accountIdentifier,
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
                                                  String expectedRaasResponseCode,
                                                  String expectedMessage,
                                                  String expectedHTTPResponseCode,
                                                  String expectedRaasResultRequestResponseCode,
                                                  String expectedRaasResultResponseResponseCode,
                                                  String expectedCTXTransactionResponseCode) throws IOException, InterruptedException, JSchException {


        // Create Reserve And transact V2 payload object - contains transactV2 request body
        coreReserveAndTransactV2POJO ReserveAndTransactV2Payload = new coreReserveAndTransactV2POJO(
                accountIdentifier,
                amount,
                channelId,
                channelName,
                channelSessionId,
                clientId,
                clientTxnRef,
                productId,
                sourceIdentifier,
                targetIdentifier,
                timeStamp);

        // Create reserve and transact V2 response body object - contains api response data for use in assertions or other calls
        Allure.step("Action Test Step 1 : Execute Reserve and Transact V2 REST Request");
        Response ReserveAndTransactV2response =
                given(CORE_getEndPoints_ReserveAndTransactV2)
                        .contentType(ContentType.JSON)
                        .body(ReserveAndTransactV2Payload)
                        .when()
                        .post()
                        .then()
                        .extract()
                        .response();

        // Assertions

        // Reserve and Transact V2 response assertions - purchase

        Allure.step("Step.1 --> Reserve and Transact V2 REST response assertions --> Validate responseCode field is correct");
        Assert.assertEquals(ReserveAndTransactV2response.path("responseCode"), expectedRaasResponseCode);

        Allure.step("Step.2 --> Reserve and Transact V2 REST response assertions --> Validate responseMessage field is correct");
        Assert.assertEquals(ReserveAndTransactV2response.path("responseMessage"), expectedMessage);

        Allure.step("Step.3 --> Reserve and Transact V2 REST response assertions --> Validate HTTPResponseCode field is correct");
        Assert.assertEquals(ReserveAndTransactV2response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

    }

    // Data staging for use in test
    @DataProvider(name = "ReserveAndTransactV2ResponseCodesNegativeTestCases", parallel = false)
    public Object[] ReserveAndTransactV2ResponseCodesNegativeTestCases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Timeout_Negative_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Timeout_Negative_Test","amount"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Timeout_Negative_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Timeout_Negative_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Timeout_Negative_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Timeout_Negative_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Timeout_Negative_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Timeout_Negative_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Timeout_Negative_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Timeout_Negative_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Timeout_Negative_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Timeout_Negative_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Timeout_Negative_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Timeout_Negative_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Timeout_Negative_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Timeout_Negative_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Timeout_Negative_Test","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Vendor_Timeout_Negative_Test", "simulatorScenario"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Vendor_Timeout_Negative_Test", "simulatorResetState")},

                {testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Invalid_MSISDN_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Invalid_MSISDN_Test","amount"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Invalid_MSISDN_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Invalid_MSISDN_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Invalid_MSISDN_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Invalid_MSISDN_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Invalid_MSISDN_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Invalid_MSISDN_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Invalid_MSISDN_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Invalid_MSISDN_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Invalid_MSISDN_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Invalid_MSISDN_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Invalid_MSISDN_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Invalid_MSISDN_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Invalid_MSISDN_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Invalid_MSISDN_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Invalid_MSISDN_Test","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Vendor_Invalid_MSISDN_Test", "simulatorScenario"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Vendor_Invalid_MSISDN_Test", "simulatorResetState")},



        };
    }

    @Step("Reserve and Transact V2: Response codes: Negative test cases")
    @Test(dataProvider = "ReserveAndTransactV2ResponseCodesNegativeTestCases", priority = 2)
    public void ReserveAndTransactV2ResponseCodesNegativeTests(String accountIdentifier,
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
                                                               String expectedRaasResponseCode,
                                                               String expectedMessage,
                                                               String expectedHTTPResponseCode,
                                                               String expectedRaasResultRequestResponseCode,
                                                               String expectedRaasResultResponseResponseCode,
                                                               String expectedCTXTransactionResponseCode,
                                                               String simulatorScenario,
                                                               String simulatorResetState) throws IOException, InterruptedException, JSchException {

        // Set simulator to success
        Allure.step("Action Test Step 1 : Set Vendor simulator to SUCCESS");
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


        // Create Reserve And transact V2 payload object - contains transactV2 request body
        coreReserveAndTransactV2POJO ReserveAndTransactV2Payload = new coreReserveAndTransactV2POJO(
                accountIdentifier,
                amount,
                channelId,
                channelName,
                channelSessionId,
                clientId,
                clientTxnRef,
                productId,
                sourceIdentifier,
                targetIdentifier,
                timeStamp);

        // Create reserve and transact V2 response body object - contains api response data for use in assertions or other calls
        Allure.step("Action Test Step 4 : Execute Reserve and Transact V2 REST Request");
        Response ReserveAndTransactV2response =
                given(CORE_getEndPoints_ReserveAndTransactV2)
                        .contentType(ContentType.JSON)
                        .body(ReserveAndTransactV2Payload)
                        .when()
                        .post()
                        .then()
                        .extract()
                        .response();

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
        Assert.assertEquals(finTermsCalculateResponse.path("purchaseAmount").toString(), amount);

        // Reserve and Transact V4 response assertions - purchase

        Allure.step("Step.6 --> Reserve and Transact V4 REST response assertions --> Validate responseCode field is correct");
        Assert.assertEquals(ReserveAndTransactV2response.path("responseCode"), expectedRaasResponseCode);

        Allure.step("Step.7 --> Reserve and Transact V4 REST response assertions --> Validate responseMessage field is correct");
        Assert.assertEquals(ReserveAndTransactV2response.path("responseMessage"), expectedMessage);

        Allure.step("Step.8 --> Reserve and Transact V4 REST response assertions --> Validate raasTxnRef field exists");
        Assert.assertNotNull(ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.9 --> Reserve and Transact V4 REST response assertions --> Validate HTTPResponseCode field exists");
        Assert.assertEquals(ReserveAndTransactV2response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // raas db assertions
        //Transaction_log

        Allure.step("Action Test Step 5 : Wait 10 seconds before executing RAAS Transaction_Log DB Assertions");
        Thread.sleep(10000);

        Allure.step("Step.10 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.11 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.12 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "amount"), amount);

        Allure.step("Step.13 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.14 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.15 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.16 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.17 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.18 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate event_type field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.19 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.20 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.21 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.22 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "timestamp"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.23 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.24 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.25 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate reserve_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "reserve_amount"), amount);

        Allure.step("Step.26 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.27 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.28 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.29 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate settlement_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.30 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate vend_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vend_amount"), amount);

        Allure.step("Step.31 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vendor_share_amount"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);

        Allure.step("Step.32 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_request_created"));

        Allure.step("Step.33 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_response_created"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_request_created"));
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");

        //Allure.step("Step.34 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate currency_code field is correct");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'","currency_code"), currencyCode);

        //Allure.step("Step.35 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate funding_source_id field is correct");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "funding_source_id"), fundingSourceId);

        Allure.step("Step.34 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate additional_data_financial_calculations field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Raas_Request

        Allure.step("Step.35 --> RAAS DB assertions --> Table: raas.raas_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", ReserveAndTransactV2response.path("raasTxnRef")), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.36 --> RAAS DB assertions --> Table: raas.raas_request --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.37 --> RAAS DB assertions --> Table: raas.raas_request --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "amount"), amount);

        Allure.step("Step.38 --> RAAS DB assertions --> Table: raas.raas_request --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.39 --> RAAS DB assertions --> Table: raas.raas_request --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.40 --> RAAS DB assertions --> Table: raas.raas_request --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.41 --> RAAS DB assertions --> Table: raas.raas_request --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.42 --> RAAS DB assertions --> Table: raas.raas_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.43 --> RAAS DB assertions --> Table: raas.raas_request --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.44 --> RAAS DB assertions --> Table: raas.raas_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.45 --> RAAS DB assertions --> Table: raas.raas_request --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.46 --> RAAS DB assertions --> Table: raas.raas_request --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.47 --> RAAS DB assertions --> Table: raas.raas_request --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.48 --> RAAS DB assertions --> Table: raas.raas_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "timestamp"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.49 --> RAAS DB assertions --> Table: raas.raas_request --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.50 --> RAAS DB assertions --> Table: raas.raas_request --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_name"), channelName);

        //Allure.step("Step.51 --> RAAS DB assertions --> Table: raas.raas_request --> Validate fee_amount field exists");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.51 --> RAAS DB assertions --> Table: raas.raas_request --> Validate api_call field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "api_call"), "reserveAndTransact-v2");

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response

        Allure.step("Step.52 --> RAAS DB assertions --> Table: raas.raas_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", ReserveAndTransactV2response.path("raasTxnRef")), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.53 --> RAAS DB assertions --> Table: raas.raas_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.54 --> RAAS DB assertions --> Table: raas.raas_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"));

        // CTX DB assertions

        Allure.step("Action Test Step 6 : Wait 5 seconds before executing CTX DB Assertions");
        Thread.sleep(5000);

        Allure.step("Step.55 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionResponseCode field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);

        Allure.step("Step.56 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionId field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "transactionId"));

        Allure.step("Step.57 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate clientStan field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "clientStan"));

        Allure.step("Step.58 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originId field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);

        Allure.step("Step.59 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originatingService field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "originatingService"));

        Allure.step("Step.60 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate purchaseAmount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "purchaseAmount"), amount);

        Allure.step("Step.61 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionState field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "transactionState"), "C");

        Allure.step("Step.62 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionType field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "transactionType"), "P");

        Allure.step("Step.63 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "client_id"), clientId);

        Allure.step("Step.64 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        // raas reserve_fund_request

        Allure.step("Step.65 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.66 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "amount"), amount);

        Allure.step("Step.67 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.68 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.69 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.70 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.71 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.72 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate event_type field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_REQUEST_EVENT");

        Allure.step("Step.73 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.74 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate product_type field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "product_type"), "3");

        Allure.step("Step.75 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.76 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.77 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.78 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        //Allure.step("Step.80 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate cdc_update_timestamp field exists");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "reserve_amount"), amount);

        //Allure.step("Step.81 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate fee_amount field exists");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "fee_amount"));

        //Allure.step("Step.82 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate client_share_amount field exists");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_share_amount"));

        //Allure.step("Step.83 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate settlement_amount field exists");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "settlement_amount"));

        //Allure.step("Step.84 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate vend_amount field is correct");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vend_amount"), amount);

        //Allure.step("Step.85 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate vendor_share_amount field exists");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // raas reserve_fund_response

        Allure.step("Step.79 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.80 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.81 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate event_type field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_RESPONSE_EVENT");

        Allure.step("Step.82 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate reserve_funds_txn_ref field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"));

        Allure.step("Step.83 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);

        Allure.step("Step.84 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));


        // Reset simulator
        Allure.step("Action Test Step 7 : Reset vendor simulator to SUCCESS");
        startSim.SimulatorScenario(simulatorResetState);

        Allure.step("Action Test Step 8 : Wait 5 seconds for vendor simulator to reset to SUCCESS");
        Thread.sleep(5000);
    }

    // Data staging for use in test
    @DataProvider(name = "ReserveAndTransactV2NonRetryableDeclineTestCases", parallel = false)
    public Object[] ReserveAndTransactV2NonRetryableDeclineTestCases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Timeout_Negative_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Timeout_Negative_Test","amount"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Timeout_Negative_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Timeout_Negative_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Timeout_Negative_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Timeout_Negative_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Timeout_Negative_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Timeout_Negative_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Timeout_Negative_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Timeout_Negative_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Timeout_Negative_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Timeout_Negative_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Timeout_Negative_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Timeout_Negative_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Timeout_Negative_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Timeout_Negative_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Timeout_Negative_Test","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Vendor_Timeout_Negative_Test", "simulatorScenario"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Vendor_Timeout_Negative_Test", "simulatorResetState")},

                {testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Invalid_MSISDN_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Invalid_MSISDN_Test","amount"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Invalid_MSISDN_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Invalid_MSISDN_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Invalid_MSISDN_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Invalid_MSISDN_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Invalid_MSISDN_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Invalid_MSISDN_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Invalid_MSISDN_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Invalid_MSISDN_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Invalid_MSISDN_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Invalid_MSISDN_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Invalid_MSISDN_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Invalid_MSISDN_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Invalid_MSISDN_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Invalid_MSISDN_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Vendor_Invalid_MSISDN_Test","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Vendor_Invalid_MSISDN_Test", "simulatorScenario"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Vendor_Invalid_MSISDN_Test", "simulatorResetState")},



        };
    }

    @Step("Reserve and Transact V2: Non Retryable Decline test cases")
    @Test(dataProvider = "ReserveAndTransactV2NonRetryableDeclineTestCases", priority = 3)
    public void ReserveAndTransactV2NonRetryableDeclineTests(String accountIdentifier,
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
                                                             String expectedRaasResponseCode,
                                                             String expectedMessage,
                                                             String expectedHTTPResponseCode,
                                                             String expectedRaasResultRequestResponseCode,
                                                             String expectedRaasResultResponseResponseCode,
                                                             String expectedCTXTransactionResponseCode,
                                                             String simulatorScenario,
                                                             String simulatorResetState) throws IOException, InterruptedException, JSchException {

        // Set simulator to success
        Allure.step("Action Test Step 1 : Set Vendor simulator to " + simulatorScenario);
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


        // Create Reserve And transact V2 payload object - contains transactV2 request body
        coreReserveAndTransactV2POJO ReserveAndTransactV2Payload = new coreReserveAndTransactV2POJO(
                accountIdentifier,
                amount,
                channelId,
                channelName,
                channelSessionId,
                clientId,
                clientTxnRef,
                productId,
                sourceIdentifier,
                targetIdentifier,
                timeStamp);

        // Create reserve and transact V2 response body object - contains api response data for use in assertions or other calls
        Allure.step("Action Test Step 4 : Execute Reserve and Transact V2 REST Request");
        Response ReserveAndTransactV2response =
                given(CORE_getEndPoints_ReserveAndTransactV2)
                        .contentType(ContentType.JSON)
                        .body(ReserveAndTransactV2Payload)
                        .when()
                        .post()
                        .then()
                        .extract()
                        .response();

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
        Assert.assertEquals(finTermsCalculateResponse.path("purchaseAmount").toString(), amount);

        // Reserve and Transact V4 response assertions - purchase

        Allure.step("Step.6 --> Reserve and Transact V4 REST response assertions --> Validate responseCode field is correct");
        Assert.assertEquals(ReserveAndTransactV2response.path("responseCode"), expectedRaasResponseCode);

        Allure.step("Step.7 --> Reserve and Transact V4 REST response assertions --> Validate responseMessage field is correct");
        Assert.assertEquals(ReserveAndTransactV2response.path("responseMessage"), expectedMessage);

        Allure.step("Step.8 --> Reserve and Transact V4 REST response assertions --> Validate raasTxnRef field exists");
        Assert.assertNotNull(ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.9 --> Reserve and Transact V4 REST response assertions --> Validate HTTPResponseCode field exists");
        Assert.assertEquals(ReserveAndTransactV2response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // raas db assertions
        //Transaction_log

        Allure.step("Action Test Step 5 : Wait 10 seconds before executing RAAS Transaction_Log DB Assertions");
        Thread.sleep(10000);

        Allure.step("Step.10 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.11 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.12 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "amount"), amount);

        Allure.step("Step.13 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.14 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.15 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.16 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.17 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.18 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate event_type field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.19 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.20 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.21 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.22 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "timestamp"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.23 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.24 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.25 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate reserve_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "reserve_amount"), amount);

        Allure.step("Step.26 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.27 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.28 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.29 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate settlement_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.30 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate vend_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vend_amount"), amount);

        Allure.step("Step.31 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vendor_share_amount"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);

        Allure.step("Step.32 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_request_created"));

        Allure.step("Step.33 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_response_created"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_request_created"));
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");

        //Allure.step("Step.34 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate currency_code field is correct");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'","currency_code"), currencyCode);

        //Allure.step("Step.35 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate funding_source_id field is correct");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "funding_source_id"), fundingSourceId);

        Allure.step("Step.34 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate additional_data_financial_calculations field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Raas_Request

        Allure.step("Step.35 --> RAAS DB assertions --> Table: raas.raas_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", ReserveAndTransactV2response.path("raasTxnRef")), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.36 --> RAAS DB assertions --> Table: raas.raas_request --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.37 --> RAAS DB assertions --> Table: raas.raas_request --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "amount"), amount);

        Allure.step("Step.38 --> RAAS DB assertions --> Table: raas.raas_request --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.39 --> RAAS DB assertions --> Table: raas.raas_request --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.40 --> RAAS DB assertions --> Table: raas.raas_request --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.41 --> RAAS DB assertions --> Table: raas.raas_request --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.42 --> RAAS DB assertions --> Table: raas.raas_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.43 --> RAAS DB assertions --> Table: raas.raas_request --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.44 --> RAAS DB assertions --> Table: raas.raas_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.45 --> RAAS DB assertions --> Table: raas.raas_request --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.46 --> RAAS DB assertions --> Table: raas.raas_request --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.47 --> RAAS DB assertions --> Table: raas.raas_request --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.48 --> RAAS DB assertions --> Table: raas.raas_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "timestamp"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.49 --> RAAS DB assertions --> Table: raas.raas_request --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.50 --> RAAS DB assertions --> Table: raas.raas_request --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_name"), channelName);

        //Allure.step("Step.51 --> RAAS DB assertions --> Table: raas.raas_request --> Validate fee_amount field exists");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.51 --> RAAS DB assertions --> Table: raas.raas_request --> Validate api_call field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "api_call"), "reserveAndTransact-v2");

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response

        Allure.step("Step.52 --> RAAS DB assertions --> Table: raas.raas_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", ReserveAndTransactV2response.path("raasTxnRef")), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.53 --> RAAS DB assertions --> Table: raas.raas_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.54 --> RAAS DB assertions --> Table: raas.raas_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"));

        // CTX DB assertions

        Allure.step("Action Test Step 6 : Wait 5 seconds before executing CTX DB Assertions");
        Thread.sleep(5000);

        Allure.step("Step.55 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionResponseCode field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);

        Allure.step("Step.56 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionId field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "transactionId"));

        Allure.step("Step.57 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate clientStan field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "clientStan"));

        Allure.step("Step.58 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originId field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);

        Allure.step("Step.59 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originatingService field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "originatingService"));

        Allure.step("Step.60 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate purchaseAmount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "purchaseAmount"), amount);

        Allure.step("Step.61 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionState field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "transactionState"), "C");

        Allure.step("Step.62 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionType field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "transactionType"), "P");

        Allure.step("Step.63 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "client_id"), clientId);

        Allure.step("Step.64 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        // raas reserve_fund_request

        Allure.step("Step.65 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.66 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "amount"), amount);

        Allure.step("Step.67 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.68 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.69 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.70 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.71 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.72 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate event_type field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_REQUEST_EVENT");

        Allure.step("Step.73 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.74 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate product_type field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "product_type"), "3");

        Allure.step("Step.75 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.76 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.77 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.78 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        //Allure.step("Step.80 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate cdc_update_timestamp field exists");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "reserve_amount"), amount);

        //Allure.step("Step.81 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate fee_amount field exists");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "fee_amount"));

        //Allure.step("Step.82 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate client_share_amount field exists");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_share_amount"));

        //Allure.step("Step.83 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate settlement_amount field exists");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "settlement_amount"));

        //Allure.step("Step.84 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate vend_amount field is correct");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vend_amount"), amount);

        //Allure.step("Step.85 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate vendor_share_amount field exists");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // raas reserve_fund_response

        Allure.step("Step.79 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.80 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.81 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate event_type field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_RESPONSE_EVENT");

        Allure.step("Step.82 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate reserve_funds_txn_ref field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"));

        Allure.step("Step.83 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);

        Allure.step("Step.84 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));


        // Reset simulator
        Allure.step("Action Test Step 7 : Reset vendor simulator to SUCCESS");
        startSim.SimulatorScenario(simulatorResetState);

        Allure.step("Action Test Step 8 : Wait 5 seconds for vendor simulator to reset to SUCCESS");
        Thread.sleep(5000);
    }

    // Data staging for use in test
    @DataProvider(name = "ReserveAndTransactV2LookupBackOffTestCases", parallel = false)
    public Object[] ReserveAndTransactV2LookupBackOffTestCases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Lookup_And_Backoff_Test","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Lookup_And_Backoff_Test","amount"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Lookup_And_Backoff_Test","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Lookup_And_Backoff_Test","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Lookup_And_Backoff_Test","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Lookup_And_Backoff_Test","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Lookup_And_Backoff_Test","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Lookup_And_Backoff_Test","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Lookup_And_Backoff_Test","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Lookup_And_Backoff_Test","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Lookup_And_Backoff_Test","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Lookup_And_Backoff_Test","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Lookup_And_Backoff_Test","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Lookup_And_Backoff_Test","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Lookup_And_Backoff_Test","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Lookup_And_Backoff_Test","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json","reserveandtransactv2suite","MTNNG_Lookup_And_Backoff_Test","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Lookup_And_Backoff_Test", "simulatorScenario"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Lookup_And_Backoff_Test", "simulatorResetState")},



        };
    }

    @Step("Reserve and Transact V2: Lookup BackOff test cases")
    @Test(dataProvider = "ReserveAndTransactV2LookupBackOffTestCases", priority = 4)
    public void ReserveAndTransactV2LookupBackOffTests(String accountIdentifier,
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
                                                       String expectedRaasResponseCode,
                                                       String expectedMessage,
                                                       String expectedHTTPResponseCode,
                                                       String expectedRaasResultRequestResponseCode,
                                                       String expectedRaasResultResponseResponseCode,
                                                       String expectedCTXTransactionResponseCode,
                                                       String simulatorScenario,
                                                       String simulatorResetState) throws IOException, InterruptedException, JSchException {

        // Set simulator to fail
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


        // Create Reserve And transact V2 payload object - contains transactV2 request body
        coreReserveAndTransactV2POJO ReserveAndTransactV2Payload = new coreReserveAndTransactV2POJO(
                accountIdentifier,
                amount,
                channelId,
                channelName,
                channelSessionId,
                clientId,
                clientTxnRef,
                productId,
                sourceIdentifier,
                targetIdentifier,
                timeStamp);

        // Create reserve and transact V2 response body object - contains api response data for use in assertions or other calls
        Allure.step("Action Test Step 4 : Execute Reserve and Transact V2 REST Request");
        Response ReserveAndTransactV2response =
                given(CORE_getEndPoints_ReserveAndTransactV2)
                        .contentType(ContentType.JSON)
                        .body(ReserveAndTransactV2Payload)
                        .when()
                        .post()
                        .then()
                        .extract()
                        .response();

        // Reset simulator
        Allure.step("Action Test Step 5 : Set Vendor simulator to SUCCESS");
        startSim.SimulatorScenario(simulatorResetState);

        Allure.step("Action Test Step 6 : Wait 5 seconds for simulator to run and transaction data to process");
        Thread.sleep(5000);

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
        Assert.assertEquals(finTermsCalculateResponse.path("purchaseAmount").toString(), amount);

        // Reserve and Transact V4 response assertions - purchase

        Allure.step("Step.6 --> Reserve and Transact V4 REST response assertions --> Validate responseCode field is correct");
        Assert.assertEquals(ReserveAndTransactV2response.path("responseCode"), expectedRaasResponseCode);

        Allure.step("Step.7 --> Reserve and Transact V4 REST response assertions --> Validate responseMessage field is correct");
        Assert.assertEquals(ReserveAndTransactV2response.path("responseMessage"), expectedMessage);

        Allure.step("Step.8 --> Reserve and Transact V4 REST response assertions --> Validate raasTxnRef field exists");
        Assert.assertNotNull(ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.9 --> Reserve and Transact V4 REST response assertions --> Validate HTTPResponseCode field exists");
        Assert.assertEquals(ReserveAndTransactV2response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // raas db assertions
        //Transaction_log

        Allure.step("Action Test Step 5 : Wait 10 seconds before executing RAAS Transaction_Log DB Assertions");
        Thread.sleep(15000);

        Allure.step("Step.10 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.11 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.12 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "amount"), amount);

        Allure.step("Step.13 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.14 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.15 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.16 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.17 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.18 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate event_type field is correct");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.19 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.20 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.21 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.22 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "timestamp"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.23 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.24 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.25 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate reserve_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "reserve_amount"), amount);

        Allure.step("Step.26 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.27 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.28 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.29 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate settlement_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.30 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate vend_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vend_amount"), amount);

        Allure.step("Step.31 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vendor_share_amount"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);

        Allure.step("Step.32 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_request_created"));

        Allure.step("Step.33 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_response_created"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_request_created"));
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_response_created"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");

        //Allure.step("Step.34 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate currency_code field is correct");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'","currency_code"), currencyCode);

        //Allure.step("Step.35 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate funding_source_id field is correct");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "funding_source_id"), fundingSourceId);

        Allure.step("Step.34 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate additional_data_financial_calculations field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Raas_Request

        Allure.step("Step.35 --> RAAS DB assertions --> Table: raas.raas_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", ReserveAndTransactV2response.path("raasTxnRef")), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.36 --> RAAS DB assertions --> Table: raas.raas_request --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.37 --> RAAS DB assertions --> Table: raas.raas_request --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "amount"), amount);

        Allure.step("Step.38 --> RAAS DB assertions --> Table: raas.raas_request --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.39 --> RAAS DB assertions --> Table: raas.raas_request --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.40 --> RAAS DB assertions --> Table: raas.raas_request --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.41 --> RAAS DB assertions --> Table: raas.raas_request --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.42 --> RAAS DB assertions --> Table: raas.raas_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.43 --> RAAS DB assertions --> Table: raas.raas_request --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.44 --> RAAS DB assertions --> Table: raas.raas_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.45 --> RAAS DB assertions --> Table: raas.raas_request --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.46 --> RAAS DB assertions --> Table: raas.raas_request --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.47 --> RAAS DB assertions --> Table: raas.raas_request --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.48 --> RAAS DB assertions --> Table: raas.raas_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "timestamp"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.49 --> RAAS DB assertions --> Table: raas.raas_request --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.50 --> RAAS DB assertions --> Table: raas.raas_request --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_name"), channelName);

        //Allure.step("Step.51 --> RAAS DB assertions --> Table: raas.raas_request --> Validate fee_amount field exists");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.51 --> RAAS DB assertions --> Table: raas.raas_request --> Validate api_call field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "api_call"), "reserveAndTransact-v2");

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response

        Allure.step("Step.52 --> RAAS DB assertions --> Table: raas.raas_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", ReserveAndTransactV2response.path("raasTxnRef")), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.53 --> RAAS DB assertions --> Table: raas.raas_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.54 --> RAAS DB assertions --> Table: raas.raas_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"));

        // CTX DB assertions

        Allure.step("Action Test Step 6 : Wait 5 seconds before executing CTX DB Assertions");
        Thread.sleep(15000);

        Allure.step("Step.55 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionResponseCode field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);

        Allure.step("Step.56 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionId field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "transactionId"));

        Allure.step("Step.57 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate clientStan field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "clientStan"));

        Allure.step("Step.58 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originId field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);

        Allure.step("Step.59 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originatingService field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "originatingService"));

        Allure.step("Step.60 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate purchaseAmount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "purchaseAmount"), amount);

        Allure.step("Step.61 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionState field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "transactionState"), "C");

        Allure.step("Step.62 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionType field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "transactionType"), "P");

        Allure.step("Step.63 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "client_id"), clientId);

        Allure.step("Step.64 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        // raas reserve_fund_request

        Allure.step("Step.65 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.66 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "amount"), amount);

        Allure.step("Step.67 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.68 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.69 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.70 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.71 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.72 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate event_type field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_REQUEST_EVENT");

        Allure.step("Step.73 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.74 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate product_type field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "product_type"), "3");

        Allure.step("Step.75 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.76 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.77 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.78 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        //Allure.step("Step.80 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate cdc_update_timestamp field exists");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "reserve_amount"), amount);

        //Allure.step("Step.81 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate fee_amount field exists");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "fee_amount"));

        //Allure.step("Step.82 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate client_share_amount field exists");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_share_amount"));

        //Allure.step("Step.83 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate settlement_amount field exists");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "settlement_amount"));

        //Allure.step("Step.84 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate vend_amount field is correct");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vend_amount"), amount);

        //Allure.step("Step.85 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate vendor_share_amount field exists");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // raas reserve_fund_response

        Allure.step("Step.79 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.80 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.81 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate event_type field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_RESPONSE_EVENT");

        Allure.step("Step.82 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate reserve_funds_txn_ref field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"));

        Allure.step("Step.83 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);

        Allure.step("Step.84 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

    }

    // Data staging for use in test
    @DataProvider(name = "ReserveAndTransactV2BankTestCases", parallel = false)
    public Object[] ReserveAndTransactV2BankTestCases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Bank_101_Positive_Test", "accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Bank_101_Positive_Test", "amount"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Bank_101_Positive_Test", "channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Bank_101_Positive_Test", "channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Bank_101_Positive_Test", "channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Bank_101_Positive_Test", "clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Bank_101_Positive_Test", "clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Bank_101_Positive_Test", "productId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Bank_101_Positive_Test", "sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Bank_101_Positive_Test", "targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Bank_101_Positive_Test", "timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Bank_101_Positive_Test", "expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Bank_101_Positive_Test", "expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Bank_101_Positive_Test", "expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Bank_101_Positive_Test", "expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Bank_101_Positive_Test", "expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Bank_101_Positive_Test", "expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Bank_101_Positive_Test", "simulatorScenario"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Bank_101_Positive_Test", "simulatorResetState")},

        };
    }

    @Step("Reserve and Transact V2 Standard Success test cases")
    @Test(dataProvider = "ReserveAndTransactV2BankTestCases", priority = 6)
    public void ReserveAndTransactV2BankSuccessTests(String accountIdentifier,
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
                                                         String expectedRaasResponseCode,
                                                         String expectedMessage,
                                                         String expectedHTTPResponseCode,
                                                         String expectedRaasResultRequestResponseCode,
                                                         String expectedRaasResultResponseResponseCode,
                                                         String expectedCTXTransactionResponseCode,
                                                         String simulatorScenario,
                                                         String simulatorResetState) throws IOException, InterruptedException, JSchException {

        // Set simulator to success
        Allure.step("Action Test Step 1 : Set Vendor simulator to SUCCESS");
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

        // Create ReserveAndtransactV2 payload object - contains transactV4 request body
        coreReserveAndTransactV2POJO ReserveAndTransactV2Payload = new coreReserveAndTransactV2POJO(
                accountIdentifier,
                amount,
                channelId,
                channelName,
                channelSessionId,
                clientId,
                clientTxnRef,
                productId,
                sourceIdentifier,
                targetIdentifier,
                timeStamp);

        // Create reserve and transact V2 response body object - contains api response data for use in assertions or other calls
        Allure.step("Action Test Step 4 : Execute Reserve and Transact V2 REST Request");
        Response ReserveAndTransactV2response =
                given(CORE_getEndPoints_ReserveAndTransactV2)
                        .contentType(ContentType.JSON)
                        .body(ReserveAndTransactV2Payload)
                        .when()
                        .post()
                        .then()
                        .extract()
                        .response();

        System.out.println(ReserveAndTransactV2response);

        Thread.sleep(10000);

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
        Assert.assertEquals(finTermsCalculateResponse.path("purchaseAmount").toString(), amount);

        // Reserve and Transact V4 response assertions - purchase

        Allure.step("Step.6 --> Reserve and Transact V2 REST response assertions --> Validate responseCode field is correct");
        Assert.assertEquals(ReserveAndTransactV2response.path("responseCode"), expectedRaasResponseCode);

        Allure.step("Step.7 --> Reserve and Transact V2 REST response assertions --> Validate responseMessage field is correct");
        Assert.assertEquals(ReserveAndTransactV2response.path("responseMessage"), expectedMessage);

        Allure.step("Step.8 --> Reserve and Transact V2 REST response assertions --> Validate raasTxnRef field exists");
        Assert.assertNotNull(ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.9 --> Reserve and Transact V2 REST response assertions --> Validate HTTPResponseCode field is correct");
        Assert.assertEquals(ReserveAndTransactV2response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // raas db assertions
        //Transaction_log

        Allure.step("Action Test Step 5 : Wait 10 seconds before executing the RAAS Transaction_Log DB Assertions");
        Thread.sleep(10000);

        Allure.step("Step.10 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.11 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.12 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "amount"), amount);

        Allure.step("Step.13 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.14 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.15 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.16 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.17 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.18 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.19 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.20 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.21 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.22 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "timestamp"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.23 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.24 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.25 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "reserve_amount"), amount);

        Allure.step("Step.26 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.27 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.28 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.29 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate settlement_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.30 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate settlement_amount field exists");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vend_amount"), amount);

        Allure.step("Step.31 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vendor_share_amount"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);

        Allure.step("Step.32 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_request_created"));

        Allure.step("Step.33 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_response_created"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");

        Allure.step("Step.34 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate transaction_result_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "transaction_result_request_created"));

        Allure.step("Step.35 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate transaction_result_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "transaction_result_response_created"));

        Allure.step("Step.36 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);

        Allure.step("Step.37 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_response_message field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");

        Allure.step("Step.38 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate transaction_result_request_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);

        Allure.step("Step.39 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate transaction_result_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");

        Allure.step("Step.40 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate additional_data_financial_calculations field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Raas_Request

        Allure.step("Step.42 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", ReserveAndTransactV2response.path("raasTxnRef")), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.43 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.44 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "amount"), amount);

        Allure.step("Step.45 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.46 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.47 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.48 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.49 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.50 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.51 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.52 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.53 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.54 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.55 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "timestamp"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.56 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.57 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_name field exists");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.59 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate api_call field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "api_call"), "reserveAndTransact-v2");

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response

        Allure.step("Step.60 --> RAAS DB assertions --> Table: raas.raas_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", ReserveAndTransactV2response.path("raasTxnRef")), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.61 --> RAAS DB assertions --> Table: raas.raas_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.62 --> RAAS DB assertions --> Table: raas.raas_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"));

        // CTX DB assertions

        Allure.step("Action Test Step 6 : Wait 5 seconds before executing the CTX DB Assertions");
        Thread.sleep(5000);

        Allure.step("Step.63 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionResponseCode field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);

        Allure.step("Step.64 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionId field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "transactionId"));

        Allure.step("Step.65 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate clientStan field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "clientStan"));

        Allure.step("Step.66 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originId field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);

        Allure.step("Step.67 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originatingService field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "originatingService"));

        Allure.step("Step.68 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate purchaseAmount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "purchaseAmount"), amount);

        Allure.step("Step.69 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionState field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "transactionState"), "C");

        Allure.step("Step.70 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionType field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "transactionType"), "P");

        Allure.step("Step.71 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "client_id"), clientId);

        Allure.step("Step.72 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        // raas reserve_fund_request

        Allure.step("Step.73 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.74 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "amount"), amount);

        Allure.step("Step.75 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.76 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.77 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.78 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.79 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.80 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate event_type field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_REQUEST_EVENT");

        Allure.step("Step.81 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate event_type field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.82 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate product_type field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "product_type"), "3");

        Allure.step("Step.83 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.84 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.85 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.86 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        //Allure.step("Step.87 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate reserve_amount field is correct");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "reserve_amount"), amount);

        //Allure.step("Step.88 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate fee_amount field exists");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "fee_amount"));

        //Allure.step("Step.89 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate client_share_amount field exists");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_share_amount"));

        //Allure.step("Step.90 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate settlement_amount field exists");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "settlement_amount"));

        //Allure.step("Step.91 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate vend_amount field is correct");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vend_amount"), amount);

        //Allure.step("Step.92 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate vendor_share_amount field exists");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // raas reserve_fund_response

        Allure.step("Step.93 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.94 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.95 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate created field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_RESPONSE_EVENT");

        Allure.step("Step.96 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate reserve_funds_txn_ref field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"));

        Allure.step("Step.97 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);

        Allure.step("Step.98 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Transaction_result_request

        Allure.step("Step.99 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);

        Allure.step("Step.100 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.101 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.102 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.103 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.104 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.105 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate cdc_update_timestamp field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "reserve_amount"), amount);

        Allure.step("Step.106 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.107 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate client_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.108 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate settlement_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.109 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate vend_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vend_amount"), amount);

        Allure.step("Step.110 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // Transaction_result_response

        Allure.step("Step.111 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);

        Allure.step("Step.112 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.113 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.114 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.115 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "response_code"), "202");

        Allure.step("Step.116 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

    }

    // Data staging for use in test
    @DataProvider(name = "ReserveAndTransactV2RetryableDeclineToOkTestCases", parallel = false)
    public Object[] ReserveAndTransactV2RetryableDeclineToOkTestCases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Retryable_Decline_To_OK_Test", "accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Retryable_Decline_To_OK_Test", "amount"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Retryable_Decline_To_OK_Test", "channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Retryable_Decline_To_OK_Test", "channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Retryable_Decline_To_OK_Test", "channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Retryable_Decline_To_OK_Test", "clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Retryable_Decline_To_OK_Test", "clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Retryable_Decline_To_OK_Test", "productId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Retryable_Decline_To_OK_Test", "sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Retryable_Decline_To_OK_Test", "targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Retryable_Decline_To_OK_Test", "timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Retryable_Decline_To_OK_Test", "expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Retryable_Decline_To_OK_Test", "expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Retryable_Decline_To_OK_Test", "expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Retryable_Decline_To_OK_Test", "expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Retryable_Decline_To_OK_Test", "expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Retryable_Decline_To_OK_Test", "expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Retryable_Decline_To_OK_Test", "simulatorScenario"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Retryable_Decline_To_OK_Test", "simulatorResetState")},

        };
    }

    @Step("Reserve and Transact V2 Retryable Decline To Ok test cases")
    @Test(dataProvider = "ReserveAndTransactV2RetryableDeclineToOkTestCases", priority = 7)
    public void ReserveAndTransactV2RetryableDeclineToOkTests(String accountIdentifier,
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
                                                     String expectedRaasResponseCode,
                                                     String expectedMessage,
                                                     String expectedHTTPResponseCode,
                                                     String expectedRaasResultRequestResponseCode,
                                                     String expectedRaasResultResponseResponseCode,
                                                     String expectedCTXTransactionResponseCode,
                                                     String simulatorScenario,
                                                     String simulatorResetState) throws IOException, InterruptedException, JSchException {

        // Set simulator to fail (RD)
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

        // Create ReserveAndtransactV2 payload object - contains transactV4 request body
        coreReserveAndTransactV2POJO ReserveAndTransactV2Payload = new coreReserveAndTransactV2POJO(
                accountIdentifier,
                amount,
                channelId,
                channelName,
                channelSessionId,
                clientId,
                clientTxnRef,
                productId,
                sourceIdentifier,
                targetIdentifier,
                timeStamp);

        // Create reserve and transact V2 response body object - contains api response data for use in assertions or other calls
        Allure.step("Action Test Step 4 : Execute Reserve and Transact V2 REST Request");
        Response ReserveAndTransactV2response =
                given(CORE_getEndPoints_ReserveAndTransactV2)
                        .contentType(ContentType.JSON)
                        .body(ReserveAndTransactV2Payload)
                        .when()
                        .post()
                        .then()
                        .extract()
                        .response();

        System.out.println(ReserveAndTransactV2response);

        // Set simulator to success
        Allure.step("Action Test Step 5 : Set Vendor simulator to SUCCESS");
        startSim.SimulatorScenario(simulatorResetState);

        Allure.step("Action Test Step 6 : Wait 5 seconds for simulator to run and transaction data to process");
        Thread.sleep(5000);

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
        Assert.assertEquals(finTermsCalculateResponse.path("purchaseAmount").toString(), amount);

        // Reserve and Transact V4 response assertions - purchase

        Allure.step("Step.6 --> Reserve and Transact V2 REST response assertions --> Validate responseCode field is correct");
        Assert.assertEquals(ReserveAndTransactV2response.path("responseCode"), expectedRaasResponseCode);

        Allure.step("Step.7 --> Reserve and Transact V2 REST response assertions --> Validate responseMessage field is correct");
        Assert.assertEquals(ReserveAndTransactV2response.path("responseMessage"), expectedMessage);

        Allure.step("Step.8 --> Reserve and Transact V2 REST response assertions --> Validate raasTxnRef field exists");
        Assert.assertNotNull(ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.9 --> Reserve and Transact V2 REST response assertions --> Validate HTTPResponseCode field is correct");
        Assert.assertEquals(ReserveAndTransactV2response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // raas db assertions
        //Transaction_log

        Allure.step("Action Test Step 7 : Wait 10 seconds before executing the RAAS Transaction_Log DB Assertions");
        Thread.sleep(10000);

        Allure.step("Step.10 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.11 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.12 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "amount"), amount);

        Allure.step("Step.13 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.14 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.15 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.16 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.17 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.18 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.19 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.20 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.21 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.22 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "timestamp"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.23 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.24 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.25 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "reserve_amount"), amount);

        Allure.step("Step.26 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.27 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.28 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.29 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate settlement_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.30 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate settlement_amount field exists");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vend_amount"), amount);

        Allure.step("Step.31 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vendor_share_amount"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);

        Allure.step("Step.32 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_request_created"));

        Allure.step("Step.33 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_response_created"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");

        Allure.step("Step.34 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate transaction_result_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "transaction_result_request_created"));

        Allure.step("Step.35 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate transaction_result_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "transaction_result_response_created"));

        Allure.step("Step.36 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);

        Allure.step("Step.37 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_response_message field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");

        Allure.step("Step.38 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate transaction_result_request_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);

        Allure.step("Step.39 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate transaction_result_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");

        Allure.step("Step.40 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate additional_data_financial_calculations field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Raas_Request

        Allure.step("Step.42 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", ReserveAndTransactV2response.path("raasTxnRef")), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.43 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.44 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "amount"), amount);

        Allure.step("Step.45 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.46 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.47 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.48 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.49 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.50 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.51 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.52 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.53 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.54 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.55 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "timestamp"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.56 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.57 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_name field exists");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.59 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate api_call field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "api_call"), "reserveAndTransact-v2");

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response

        Allure.step("Step.60 --> RAAS DB assertions --> Table: raas.raas_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", ReserveAndTransactV2response.path("raasTxnRef")), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.61 --> RAAS DB assertions --> Table: raas.raas_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.62 --> RAAS DB assertions --> Table: raas.raas_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"));

        // CTX DB assertions

        Allure.step("Action Test Step 8 : Wait 15 seconds before executing the CTX DB Assertions");
        Thread.sleep(15000);

        Allure.step("Step.63 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionResponseCode field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0001'", "transactionResponseCode"), expectedCTXTransactionResponseCode);

        Allure.step("Step.64 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionId field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0001'", "transactionId"));

        Allure.step("Step.65 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate clientStan field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0001'", "clientStan"));

        Allure.step("Step.66 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originId field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0001'", "originId"), sourceIdentifier);

        Allure.step("Step.67 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originatingService field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0001'", "originatingService"));

        Allure.step("Step.68 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate purchaseAmount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0001'", "purchaseAmount"), amount);

        Allure.step("Step.69 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionState field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0001'", "transactionState"), "C");

        Allure.step("Step.70 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionType field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0001'", "transactionType"), "P");

        Allure.step("Step.71 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0001'", "client_id"), clientId);

        Allure.step("Step.72 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0001'", "product_id"), productId);

        // raas reserve_fund_request

        Allure.step("Step.73 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.74 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "amount"), amount);

        Allure.step("Step.75 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.76 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.77 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.78 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.79 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.80 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate event_type field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_REQUEST_EVENT");

        Allure.step("Step.81 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate event_type field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.82 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate product_type field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "product_type"), "3");

        Allure.step("Step.83 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.84 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.85 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.86 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        //Allure.step("Step.87 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate reserve_amount field is correct");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "reserve_amount"), amount);

        //Allure.step("Step.88 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate fee_amount field exists");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "fee_amount"));

        //Allure.step("Step.89 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate client_share_amount field exists");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_share_amount"));

        //Allure.step("Step.90 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate settlement_amount field exists");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "settlement_amount"));

        //Allure.step("Step.91 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate vend_amount field is correct");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vend_amount"), amount);

        //Allure.step("Step.92 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate vendor_share_amount field exists");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // raas reserve_fund_response

        Allure.step("Step.93 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.94 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.95 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate created field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_RESPONSE_EVENT");

        Allure.step("Step.96 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate reserve_funds_txn_ref field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"));

        Allure.step("Step.97 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);

        Allure.step("Step.98 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Transaction_result_request

        Allure.step("Step.99 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);

        Allure.step("Step.100 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.101 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.102 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.103 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.104 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.105 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate cdc_update_timestamp field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "reserve_amount"), amount);

        Allure.step("Step.106 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.107 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate client_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.108 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate settlement_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.109 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate vend_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vend_amount"), amount);

        Allure.step("Step.110 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // Transaction_result_response

        Allure.step("Step.111 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);

        Allure.step("Step.112 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.113 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.114 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.115 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "response_code"), "202");

        Allure.step("Step.116 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

    }

    // Data staging for use in test
    @DataProvider(name = "ReserveAndTransactV2RetryableDeclineToNonRetryableDeclineTestCases", parallel = false)
    public Object[] ReserveAndTransactV2RetryableDeclineToNonRetryableDeclineTestCases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Retryable_Decline_To_Non_Retryable_Decline_Test", "accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Retryable_Decline_To_Non_Retryable_Decline_Test", "amount"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Retryable_Decline_To_Non_Retryable_Decline_Test", "channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Retryable_Decline_To_Non_Retryable_Decline_Test", "channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Retryable_Decline_To_Non_Retryable_Decline_Test", "channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Retryable_Decline_To_Non_Retryable_Decline_Test", "clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Retryable_Decline_To_Non_Retryable_Decline_Test", "clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Retryable_Decline_To_Non_Retryable_Decline_Test", "productId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Retryable_Decline_To_Non_Retryable_Decline_Test", "sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Retryable_Decline_To_Non_Retryable_Decline_Test", "targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Retryable_Decline_To_Non_Retryable_Decline_Test", "timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Retryable_Decline_To_Non_Retryable_Decline_Test", "expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Retryable_Decline_To_Non_Retryable_Decline_Test", "expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Retryable_Decline_To_Non_Retryable_Decline_Test", "expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Retryable_Decline_To_Non_Retryable_Decline_Test", "expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Retryable_Decline_To_Non_Retryable_Decline_Test", "expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Retryable_Decline_To_Non_Retryable_Decline_Test", "expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Retryable_Decline_To_Non_Retryable_Decline_Test", "simulatorScenario"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Retryable_Decline_To_Non_Retryable_Decline_Test", "simulatorResetState")},

        };
    }

    @Step("Reserve and Transact V2 Retryable Decline To Non Retryable Decline test cases")
    @Test(dataProvider = "ReserveAndTransactV2RetryableDeclineToNonRetryableDeclineTestCases", priority = 8)
    public void ReserveAndTransactV2RetryableDeclineToNonRetryableDeclineTests(String accountIdentifier,
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
                                                              String expectedRaasResponseCode,
                                                              String expectedMessage,
                                                              String expectedHTTPResponseCode,
                                                              String expectedRaasResultRequestResponseCode,
                                                              String expectedRaasResultResponseResponseCode,
                                                              String expectedCTXTransactionResponseCode,
                                                              String simulatorScenario,
                                                              String simulatorResetState) throws IOException, InterruptedException, JSchException {

        // Set simulator to fail (RD)
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

        // Create ReserveAndtransactV2 payload object - contains transactV4 request body
        coreReserveAndTransactV2POJO ReserveAndTransactV2Payload = new coreReserveAndTransactV2POJO(
                accountIdentifier,
                amount,
                channelId,
                channelName,
                channelSessionId,
                clientId,
                clientTxnRef,
                productId,
                sourceIdentifier,
                targetIdentifier,
                timeStamp);

        // Create reserve and transact V2 response body object - contains api response data for use in assertions or other calls
        Allure.step("Action Test Step 4 : Execute Reserve and Transact V2 REST Request");
        Response ReserveAndTransactV2response =
                given(CORE_getEndPoints_ReserveAndTransactV2)
                        .contentType(ContentType.JSON)
                        .body(ReserveAndTransactV2Payload)
                        .when()
                        .post()
                        .then()
                        .extract()
                        .response();

        System.out.println(ReserveAndTransactV2response);

        // Set simulator to fail (RD)
        Allure.step("Action Test Step 5 : Set Vendor simulator to fail (MTNNG ERROR ERROR_INV_MSISDN)");
        startSim.SimulatorScenario(simulatorResetState);

        Allure.step("Action Test Step 6 : Wait 5 seconds for simulator to run and transaction data to process");
        Thread.sleep(5000);

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
        Assert.assertEquals(finTermsCalculateResponse.path("purchaseAmount").toString(), amount);

        // Reserve and Transact V4 response assertions - purchase

        Allure.step("Step.6 --> Reserve and Transact V2 REST response assertions --> Validate responseCode field is correct");
        Assert.assertEquals(ReserveAndTransactV2response.path("responseCode"), expectedRaasResponseCode);

        Allure.step("Step.7 --> Reserve and Transact V2 REST response assertions --> Validate responseMessage field is correct");
        Assert.assertEquals(ReserveAndTransactV2response.path("responseMessage"), expectedMessage);

        Allure.step("Step.8 --> Reserve and Transact V2 REST response assertions --> Validate raasTxnRef field exists");
        Assert.assertNotNull(ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.9 --> Reserve and Transact V2 REST response assertions --> Validate HTTPResponseCode field is correct");
        Assert.assertEquals(ReserveAndTransactV2response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // raas db assertions
        //Transaction_log

        Allure.step("Action Test Step 7 : Wait 10 seconds before executing the RAAS Transaction_Log DB Assertions");
        Thread.sleep(10000);

        Allure.step("Step.10 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.11 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.12 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "amount"), amount);

        Allure.step("Step.13 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.14 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.15 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.16 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.17 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.18 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.19 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.20 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.21 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.22 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "timestamp"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.23 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.24 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.25 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "reserve_amount"), amount);

        Allure.step("Step.26 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.27 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.28 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.29 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate settlement_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.30 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate settlement_amount field exists");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vend_amount"), amount);

        Allure.step("Step.31 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vendor_share_amount"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);

        Allure.step("Step.32 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_request_created"));

        Allure.step("Step.33 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_response_created"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");

        Allure.step("Step.34 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate transaction_result_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "transaction_result_request_created"));

        Allure.step("Step.35 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate transaction_result_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "transaction_result_response_created"));

        Allure.step("Step.36 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);

        Allure.step("Step.37 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_response_message field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");

        Allure.step("Step.38 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate transaction_result_request_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedCTXTransactionResponseCode);

        Allure.step("Step.39 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate transaction_result_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");

        Allure.step("Step.40 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate additional_data_financial_calculations field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Raas_Request

        Allure.step("Step.42 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", ReserveAndTransactV2response.path("raasTxnRef")), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.43 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.44 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "amount"), amount);

        Allure.step("Step.45 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.46 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.47 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.48 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.49 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.50 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.51 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.52 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.53 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.54 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.55 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "timestamp"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.56 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.57 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_name field exists");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.59 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate api_call field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "api_call"), "reserveAndTransact-v2");

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response

        Allure.step("Step.60 --> RAAS DB assertions --> Table: raas.raas_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", ReserveAndTransactV2response.path("raasTxnRef")), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.61 --> RAAS DB assertions --> Table: raas.raas_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.62 --> RAAS DB assertions --> Table: raas.raas_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"));

        // CTX DB assertions

        Allure.step("Action Test Step 8 : Wait 15 seconds before executing the CTX DB Assertions");
        Thread.sleep(15000);

        Allure.step("Step.63 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionResponseCode field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0001'", "transactionResponseCode"), expectedCTXTransactionResponseCode);

        Allure.step("Step.64 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionId field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0001'", "transactionId"));

        Allure.step("Step.65 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate clientStan field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0001'", "clientStan"));

        Allure.step("Step.66 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originId field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0001'", "originId"), sourceIdentifier);

        Allure.step("Step.67 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originatingService field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0001'", "originatingService"));

        Allure.step("Step.68 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate purchaseAmount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0001'", "purchaseAmount"), amount);

        Allure.step("Step.69 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionState field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0001'", "transactionState"), "C");

        Allure.step("Step.70 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionType field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0001'", "transactionType"), "P");

        Allure.step("Step.71 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0001'", "client_id"), clientId);

        Allure.step("Step.72 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0001'", "product_id"), productId);

        // raas reserve_fund_request

        Allure.step("Step.73 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.74 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "amount"), amount);

        Allure.step("Step.75 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.76 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.77 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.78 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.79 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.80 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate event_type field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_REQUEST_EVENT");

        Allure.step("Step.81 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate event_type field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.82 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate product_type field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "product_type"), "3");

        Allure.step("Step.83 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.84 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.85 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.86 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        //Allure.step("Step.87 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate reserve_amount field is correct");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "reserve_amount"), amount);

        //Allure.step("Step.88 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate fee_amount field exists");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "fee_amount"));

        //Allure.step("Step.89 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate client_share_amount field exists");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_share_amount"));

        //Allure.step("Step.90 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate settlement_amount field exists");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "settlement_amount"));

        //Allure.step("Step.91 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate vend_amount field is correct");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vend_amount"), amount);

        //Allure.step("Step.92 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate vendor_share_amount field exists");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // raas reserve_fund_response

        Allure.step("Step.93 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.94 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.95 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate created field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_RESPONSE_EVENT");

        Allure.step("Step.96 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate reserve_funds_txn_ref field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"));

        Allure.step("Step.97 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);

        Allure.step("Step.98 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Transaction_result_request

        Allure.step("Step.99 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);

        Allure.step("Step.100 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.101 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.102 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.103 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.104 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.105 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate cdc_update_timestamp field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "reserve_amount"), amount);

        Allure.step("Step.106 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.107 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate client_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.108 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate settlement_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.109 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate vend_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vend_amount"), amount);

        Allure.step("Step.110 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // Transaction_result_response

        Allure.step("Step.111 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);

        Allure.step("Step.112 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.113 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.114 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.115 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "response_code"), "202");

        Allure.step("Step.116 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Action Test Step 9 : Reset vendor simulator to SUCCESS");
        startSim.SimulatorScenario("MTNNG SUCCESS");

        Allure.step("Action Test Step 10 : Wait 5 seconds for vendor simulator to reset to SUCCESS");
        Thread.sleep(5000);

    }

    // Data staging for use in test
    @DataProvider(name = "ReserveAndTransactV2PendingToRetryableDeclineToNonRetryableDeclineTestCases", parallel = false)
    public Object[] ReserveAndTransactV2PendingToRetryableDeclineToNonRetryableDeclineTestCases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline_Test", "accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline_Test", "amount"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline_Test", "channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline_Test", "channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline_Test", "channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline_Test", "clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline_Test", "clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline_Test", "productId"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline_Test", "sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline_Test", "targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline_Test", "timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline_Test", "expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline_Test", "expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline_Test", "expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline_Test", "expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline_Test", "expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline_Test", "expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline_Test", "simulatorScenario"),
                        testDataFactory.getTestData("ReserveAndTransactV2datasource.json", "reserveandtransactv2suite", "MTNNG_Pending_To_Retryable_Decline_To_Non_Retryable_Decline_Test", "simulatorResetState")},

        };
    }

    @Step("Reserve and Transact V2 Pending To Retryable Decline To Non Retryable Decline test cases")
    @Test(dataProvider = "ReserveAndTransactV2PendingToRetryableDeclineToNonRetryableDeclineTestCases", priority = 9)
    public void ReserveAndTransactV2PendingToRetryableDeclineToNonRetryableDeclineTests(String accountIdentifier,
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
                                                                               String expectedRaasResponseCode,
                                                                               String expectedMessage,
                                                                               String expectedHTTPResponseCode,
                                                                               String expectedRaasResultRequestResponseCode,
                                                                               String expectedRaasResultResponseResponseCode,
                                                                               String expectedCTXTransactionResponseCode,
                                                                               String simulatorScenario,
                                                                               String simulatorResetState) throws IOException, InterruptedException, JSchException {

        // Set simulator to fail (RD)
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

        // Create ReserveAndtransactV2 payload object - contains transactV4 request body
        coreReserveAndTransactV2POJO ReserveAndTransactV2Payload = new coreReserveAndTransactV2POJO(
                accountIdentifier,
                amount,
                channelId,
                channelName,
                channelSessionId,
                clientId,
                clientTxnRef,
                productId,
                sourceIdentifier,
                targetIdentifier,
                timeStamp);

        // Create reserve and transact V2 response body object - contains api response data for use in assertions or other calls
        Allure.step("Action Test Step 4 : Execute Reserve and Transact V2 REST Request");
        Response ReserveAndTransactV2response =
                given(CORE_getEndPoints_ReserveAndTransactV2)
                        .contentType(ContentType.JSON)
                        .body(ReserveAndTransactV2Payload)
                        .when()
                        .post()
                        .then()
                        .extract()
                        .response();

        System.out.println(ReserveAndTransactV2response);

        // Set simulator to fail (RD)
        Allure.step("Action Test Step 5 : Set Vendor simulator to fail (MTNNG ERROR ERROR_INV_MSISDN)");
        startSim.SimulatorScenario(simulatorResetState);

        Allure.step("Action Test Step 6 : Wait 5 seconds for simulator to run and transaction data to process");
        Thread.sleep(5000);

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
        Assert.assertEquals(finTermsCalculateResponse.path("purchaseAmount").toString(), amount);

        // Reserve and Transact V2 response assertions - purchase

        Allure.step("Step.6 --> Reserve and Transact V2 REST response assertions --> Validate responseCode field is correct");
        Assert.assertEquals(ReserveAndTransactV2response.path("responseCode"), expectedRaasResponseCode);

        Allure.step("Step.7 --> Reserve and Transact V2 REST response assertions --> Validate responseMessage field is correct");
        Assert.assertEquals(ReserveAndTransactV2response.path("responseMessage"), expectedMessage);

        Allure.step("Step.8 --> Reserve and Transact V2 REST response assertions --> Validate raasTxnRef field exists");
        Assert.assertNotNull(ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.9 --> Reserve and Transact V2 REST response assertions --> Validate HTTPResponseCode field is correct");
        Assert.assertEquals(ReserveAndTransactV2response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // raas db assertions
        //Transaction_log

        Allure.step("Action Test Step 7 : Wait 10 seconds before executing the RAAS Transaction_Log DB Assertions");
        Thread.sleep(10000);

        Allure.step("Step.10 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.11 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.12 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "amount"), amount);

        Allure.step("Step.13 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.14 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.15 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.16 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.17 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.18 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.19 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.20 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.21 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.22 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "timestamp"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.23 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.24 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.25 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "reserve_amount"), amount);

        Allure.step("Step.26 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.27 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.28 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.29 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate settlement_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.30 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate settlement_amount field exists");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vend_amount"), amount);

        Allure.step("Step.31 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vendor_share_amount"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);

        Allure.step("Step.32 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_request_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_request_created"));

        Allure.step("Step.33 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_response_created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_response_created"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_request_created"), "null");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_created"), "null");

        //Allure.step("Step.34 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate transaction_result_request_created field exists");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "transaction_result_request_created"));

        //Allure.step("Step.35 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate transaction_result_response_created field exists");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "transaction_result_response_created"));

        Allure.step("Step.36 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);

        Allure.step("Step.37 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_response_message field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "reserve_fund_response_code"), "null");

        //Allure.step("Step.38 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate transaction_result_request_response_code field is correct");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedCTXTransactionResponseCode);

        //Allure.step("Step.39 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate transaction_result_response_response_code field is correct");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");

        Allure.step("Step.40 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate additional_data_financial_calculations field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "additional_data_financial_calculations"));

        // Raas_Request

        Allure.step("Step.42 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", ReserveAndTransactV2response.path("raasTxnRef")), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.43 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate account_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "account_identifier"), accountIdentifier);

        Allure.step("Step.44 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "amount"), amount);

        Allure.step("Step.45 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.46 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.47 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_id"), clientId);

        Allure.step("Step.48 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.49 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.50 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.51 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.52 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.53 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.54 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.55 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "timestamp"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.56 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.57 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate channel_name field exists");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.59 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate api_call field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "api_call"), "reserveAndTransact-v2");

        // Raas_request
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_message"), expectedMessage);
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Raas_Response

        Allure.step("Step.60 --> RAAS DB assertions --> Table: raas.raas_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", ReserveAndTransactV2response.path("raasTxnRef")), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.61 --> RAAS DB assertions --> Table: raas.raas_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.62 --> RAAS DB assertions --> Table: raas.raas_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.raas_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"));

        // CTX DB assertions

        Allure.step("Step.63 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionResponseCode field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);

        Allure.step("Step.64 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionId field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "transactionId"));

        Allure.step("Step.65 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate clientStan field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "clientStan"));

        Allure.step("Step.66 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originId field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);

        Allure.step("Step.67 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originatingService field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "originatingService"));

        Allure.step("Step.68 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate purchaseAmount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "purchaseAmount"), amount);

        Allure.step("Step.69 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionState field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "transactionState"), "C");

        Allure.step("Step.70 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionType field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "transactionType"), "P");

        Allure.step("Step.71 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "client_id"), clientId);

        Allure.step("Step.72 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        // SQL - UPDATE run recon
        Allure.step("Action Test Step 8 : update cpgtx.tran_log set transactionResponseCode = 2201, transactionType = 'PCH' where transactionResponseCode in (2236, 2240, 2213) and clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'");
        sqlDataAccess.verifyPostgreCustomSql("update cpgtx.tran_log set transactionResponseCode = 2201, transactionType = 'PCH' where transactionResponseCode in (2236, 2240, 2213) and clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_txn_ref");

        Allure.step("Action Test Step 9 : Wait 15 seconds for pending transaction to process and before executing the CTX DB Assertions");
        Thread.sleep(15000);

        // CTX DB assertions (Re-Check after update)

        Allure.step("Step.73 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionResponseCode field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);

        Allure.step("Step.74 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionId field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "transactionId"));

        Allure.step("Step.75 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate clientStan field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "clientStan"));

        Allure.step("Step.76 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originId field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "originId"), sourceIdentifier);

        Allure.step("Step.77 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate originatingService field exists");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "originatingService"));

        Allure.step("Step.78 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate purchaseAmount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "purchaseAmount"), amount);

        Allure.step("Step.79 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionState field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "transactionState"), "C");

        Allure.step("Step.80 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate transactionType field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "transactionType"), "P");

        Allure.step("Step.81 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate client_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "client_id"), clientId);

        Allure.step("Step.82 --> CTX DB assertions --> Table: cpgtx.tran_log --> Validate product_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "-0000'", "product_id"), productId);

        // raas reserve_fund_request

        Allure.step("Step.83 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.84 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "amount"), amount);

        Allure.step("Step.85 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate channel_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_id"), channelId);

        Allure.step("Step.86 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate channel_name field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_name"), channelName);

        Allure.step("Step.87 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate channel_session_id field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "channel_session_id"), channelSessionId);

        Allure.step("Step.88 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate client_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_txn_ref"), clientTxnRef);

        Allure.step("Step.89 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.90 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate event_type field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_REQUEST_EVENT");

        Allure.step("Step.91 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate event_type field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "product_id"), productId);

        Allure.step("Step.92 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate product_type field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "product_type"), "3");

        Allure.step("Step.93 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate source_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "source_identifier"), sourceIdentifier);

        Allure.step("Step.94 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate target_identifier field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "target_identifier"), targetIdentifier);

        Allure.step("Step.95 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.96 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        //Allure.step("Step.87 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate reserve_amount field is correct");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "reserve_amount"), amount);

        //Allure.step("Step.88 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate fee_amount field exists");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "fee_amount"));

        //Allure.step("Step.89 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate client_share_amount field exists");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_share_amount"));

        //Allure.step("Step.90 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate settlement_amount field exists");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "settlement_amount"));

        //Allure.step("Step.91 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate vend_amount field is correct");
        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vend_amount"), amount);

        //Allure.step("Step.92 --> RAAS DB assertions --> Table: raas.reserve_fund_request --> Validate vendor_share_amount field exists");
        //Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // raas reserve_fund_response

        Allure.step("Step.97 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.98 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.99 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate created field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"), "RESERVE_FUND_RESPONSE_EVENT");

        Allure.step("Step.100 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate reserve_funds_txn_ref field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"));

        Allure.step("Step.101 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);

        Allure.step("Step.102 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        // Transaction_result_request

        Allure.step("Step.103 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultRequestResponseCode);

        Allure.step("Step.104 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.105 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"));

        //Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "reserve_funds_txn_ref"), reserveFundsTxnRef);

        Allure.step("Step.106 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.107 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "timestamp"));

        Allure.step("Step.108 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Step.109 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate cdc_update_timestamp field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "reserve_amount"), amount);

        Allure.step("Step.110 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate fee_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "fee_amount"));

        Allure.step("Step.111 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate client_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "client_share_amount"));

        Allure.step("Step.112 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate settlement_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "settlement_amount"));

        Allure.step("Step.113 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate vend_amount field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vend_amount"), amount);

        Allure.step("Step.114 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate vendor_share_amount field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "vendor_share_amount"));

        // Transaction_result_response

        Allure.step("Step.115 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "response_code"), expectedRaasResultResponseResponseCode);

        Allure.step("Step.116 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV2response.path("raasTxnRef"));

        Allure.step("Step.117 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate created field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "created"));

        Allure.step("Step.118 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate event_type field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "event_type"));

        Allure.step("Step.119 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "response_code"), "202");

        Allure.step("Step.120 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate cdc_update_timestamp field exists");
        Assert.assertNotNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV2response.path("raasTxnRef") + "'", "cdc_update_timestamp"));

        Allure.step("Action Test Step 10 : Reset vendor simulator to SUCCESS");
        startSim.SimulatorScenario("MTNNG SUCCESS");

        Allure.step("Action Test Step 11 : Wait 5 seconds for vendor simulator to reset to SUCCESS");
        Thread.sleep(5000);

    }


}
