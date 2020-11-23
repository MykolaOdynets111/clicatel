/**
 * TestSuite: TransactV4 Smoke test suite.
 */

package API.smokeTests;

import api.requestLibary.CORE.coreTransactV4POJO;
import api.testUtilities.Simulators.startSimulator;
import api.testUtilities.dataBuilders.testDataFactory;
import api.testUtilities.propertyConfigWrapper.configWrapper;
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
import util.Listeners.allureApiTestListener;

import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

//TECH-60220: 30100 :: v4/transact :: SUCCESS :: smoke

@Listeners(allureApiTestListener.class)
public class smoke_Transaction_Lookup_Portal extends testConfig {

    // Create properties object in order to inject environment specific variables upon build
    Properties properties = configWrapper.loadPropertiesFile("config.properties");
    startSimulator startSim = new startSimulator();

    // Data staging for use in test
    @DataProvider(name = "transactV4SuccessSmokeTestcase", parallel = false)
    public Object[] transactV4SuccessSmokeTestcase() throws IOException, ParseException {
        System.out.println("----- transactV4SuccessSmokeTestcase -----");
        return new String[][]{

                {testDataFactory.getTestData("TransactV4datasource.json","transactv4smoketestsuite","PurchaseSuccessTest","accountIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4smoketestsuite","PurchaseSuccessTest","amount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4smoketestsuite","PurchaseSuccessTest","channelId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4smoketestsuite","PurchaseSuccessTest","channelName"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4smoketestsuite","PurchaseSuccessTest","channelSessionId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4smoketestsuite","PurchaseSuccessTest","clientId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4smoketestsuite","PurchaseSuccessTest","clientTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4smoketestsuite","PurchaseSuccessTest","productId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4smoketestsuite","PurchaseSuccessTest","sourceIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4smoketestsuite","PurchaseSuccessTest","targetIdentifier"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4smoketestsuite","PurchaseSuccessTest","timestamp"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4smoketestsuite","PurchaseSuccessTest","reserveFundsTxnRef"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4smoketestsuite","PurchaseSuccessTest","feeAmount"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4smoketestsuite","PurchaseSuccessTest","currencyCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4smoketestsuite","PurchaseSuccessTest","fundingSourceId"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4smoketestsuite","PurchaseSuccessTest","expectedRaasResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4smoketestsuite","PurchaseSuccessTest","expectedMessage"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4smoketestsuite","PurchaseSuccessTest","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4smoketestsuite","PurchaseSuccessTest","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4smoketestsuite","PurchaseSuccessTest","expectedRaasStatus"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4smoketestsuite","PurchaseSuccessTest","simulatorScenario"),
                        testDataFactory.getTestData("TransactV4datasource.json","transactv4smoketestsuite","PurchaseSuccessTest","simulatorResetState")},

        };
    }


    @Step("POST vendor sim success transact V4 call")
    @Test(dataProvider = "transactV4SuccessSmokeTestcase", priority = 0)
    public void TransactV4SuccessSmokeTest(String accountIdentifier,
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
                                String feeAmount,
                                String currencyCode,
                                String fundingSourceId,
                                String expectedRaasResponseCode,
                                String expectedMessage,
                                String expectedHTTPResponseCode,
                                String expectedCTXTransactionResponseCode,
                                String expectedRaasStatus,
                                String simulatorScenario,
                                String simulatorResetState) throws IOException, InterruptedException, JSchException {

        Allure.step("Action Test Step 1 : Set Vendor simulator to SUCCESS");
        startSim.SimulatorScenario(simulatorScenario);

        Allure.step("Action Test Step 2 : Wait 5 seconds for simulator to run");
        Thread.sleep(5000);

        // Financial Terms Calculate GET method call
        Allure.step("Action Test Step 3 : Execute Fin Terms Calculate REST Request");
        Response finTermsCalculateResponse =
                given(CORE_getEndPoints_FinancialTermsCalculate)
                .param("clientId",clientId)
                .param("productId", productId)
                .param("purchaseAmount", amount)
                .when()
                .get()
                .then()
                .extract()
                .response();

        // Create transactV4 payload object - contains transactV4 request body
        coreTransactV4POJO TransactV4payload = new coreTransactV4POJO(accountIdentifier,
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
                reserveFundsTxnRef,
                feeAmount,
                currencyCode,
                fundingSourceId);

        // Create transactV4 response body object - contains api response data for use in assertions or other calls
        Allure.step("Action Test Step 4 : Execute Transact V4 REST Request");
        Response TransactV4response =
        given(CORE_getEndPoints_TransactV4)
        .contentType(ContentType.JSON)
        .body(TransactV4payload)
        .when()
        .post()
        .then()
        .extract()
        .response();

        System.out.println("raasTxnRef for TransactV4SuccessSmokeTest: " + TransactV4response.path("raasTxnRef").toString());

        // ASSERTIONS
        // Transact V4 response assertions for successful purchase:

        //Expected responseCode
        Allure.step("Step.1 --> Transact V4 REST response assertions --> Validate responseCode field is correct");
        Assert.assertEquals(TransactV4response.path("responseCode"), expectedRaasResponseCode);

        //Expected responseMessage
        Allure.step("Step.2 --> Transact V4 REST response assertions --> Validate responseMessage field is correct");
        Assert.assertEquals(TransactV4response.path("responseMessage"), expectedMessage);

        //raasTxnRef is not null
        Allure.step("Step.3 --> Transact V4 REST response assertions --> Validate raasTxnRef is generated");
        Assert.assertNotNull(TransactV4response.path("raasTxnRef"));

        //Valid HTTPResponseCode
        Allure.step("Step.4 --> Transact V4 REST response assertions --> Validate HTTPResponseCode is correct");
        Assert.assertEquals(TransactV4response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // raas db assertions
        //Transaction_log table
        Allure.step("Action Test Step 1 : Wait 5 seconds before executing RAAS Transaction_Log DB Assertions");
        Thread.sleep(5000);

        //Validate raas_txn_ref field is correct
        Allure.step("Step.5 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), TransactV4response.path("raasTxnRef"));

        // validate transaction status is "SUCCESS"
        Allure.step("Step.6 --> RAAS DB assertions --> Table: transaction_log --> Validate status field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "status"), expectedRaasStatus);

        Allure.step("Step.7 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);

        Allure.step("Step.8 --> RAAS DB assertions --> Table: transaction_log --> Validate raas_response_response_message field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);

        Allure.step("Step.9 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_request_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);

        Allure.step("Step.10 --> RAAS DB assertions --> Table: transaction_log --> Validate transaction_result_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");

        //Validate ctx response code is successful
        Allure.step("Step.11 --> RAAS DB assertions --> Table: ctx_response --> Validate ctx response code is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.ctx_response WHERE client_transaction_id = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "response_code"), "0");

        //Validate successful transaction result is sent
        Allure.step("Step.12 --> RAAS DB assertions --> Table: transaction_result_request --> Validate transaction_result_request is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), "0000");

        //Validate funds were not reserved by .core - no records in DB
        Allure.step("Step.13 --> RAAS DB assertions --> Table: reserve_fund_response --> Validate funds were not reserved by .core");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + TransactV4response.path("raasTxnRef") + "'", "response_code"), "null");

        //Validate transaction was not retried - no records in DB
        Allure.step("Step.14 --> RAAS DB assertions --> Table: ctx_response --> Validate transaction was not retried");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.ctx_response WHERE client_transaction_id = " + "'" + TransactV4response.path("raasTxnRef") + "-0001'", "response_code"), "null");
        //Assert.assertNull(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.ctx_response WHERE client_transaction_id = " + "'" + TransactV4response.path("raasTxnRef") + "-0001'", "response_code"));

        //Validate transaction was not pending - no records in DB
        Allure.step("Step.15 --> RAAS DB assertions --> Table: ctx_lookup_response --> Validate transaction was not pending");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.ctx_lookup_response WHERE client_transaction_id = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "response_code"), "null");
    }
}
