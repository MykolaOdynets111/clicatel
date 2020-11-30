/**
 * TestSuite: ReserveAndTransactV4 Smoke test suite.
 */

package API.smokeTests;

import api.requestLibary.CORE.coreReserveAndTransactV4POJO;
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

//TECH-60219: 30100 :: v4/reserveAndTransact :: SUCCESS :: smoke

@Listeners(allureApiTestListener.class)
public class smoke_Reserve_and_Transact_V4 extends testConfig {

    // Create properties object in order to inject environment specific variables upon build
    Properties properties = configWrapper.loadPropertiesFile("config.properties");

    // Instantiate simulator instance
    startSimulator startSim = new startSimulator();

    // Data staging for use in test
    @DataProvider(name = "reserveAndTransactV4SuccessSmokeTestcase", parallel = false)
    public Object[] reserveAndTransactV4SuccessSmokeTestcase() throws IOException, ParseException {
        System.out.println("----- reserveAndTransactV4SuccessSmokeTestcase -----");
        return new String[][]{

                {testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4smoketestsuite","PurchaseSuccessTestR&T","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4smoketestsuite","PurchaseSuccessTestR&T","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4smoketestsuite","PurchaseSuccessTestR&T","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4smoketestsuite","PurchaseSuccessTestR&T","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4smoketestsuite","PurchaseSuccessTestR&T","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4smoketestsuite","PurchaseSuccessTestR&T","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4smoketestsuite","PurchaseSuccessTestR&T","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4smoketestsuite","PurchaseSuccessTestR&T","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4smoketestsuite","PurchaseSuccessTestR&T","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4smoketestsuite","PurchaseSuccessTestR&T","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4smoketestsuite","PurchaseSuccessTestR&T","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4smoketestsuite","PurchaseSuccessTestR&T","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4smoketestsuite","PurchaseSuccessTestR&T","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4smoketestsuite","PurchaseSuccessTestR&T","fundingSourceId"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4smoketestsuite","PurchaseSuccessTestR&T","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4smoketestsuite","PurchaseSuccessTestR&T","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4smoketestsuite","PurchaseSuccessTestR&T","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4smoketestsuite","PurchaseSuccessTestR&T","expectedCTXTransactionResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4smoketestsuite","PurchaseSuccessTestR&T","simulatorScenario"),
                        testDataFactory.getTestData("ReserveAndTransactV4datasource.json","reserveandtransactv4smoketestsuite","PurchaseSuccessTestR&T","simulatorResetState")},

        };
    }

    @Step("POST vendor sim success reserve and transact V4 call")
    @Test(dataProvider = "reserveAndTransactV4SuccessSmokeTestcase", priority = 0)
    public void ReserveAndTransactV4SuccessSmokeTest(String accountIdentifier,
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
                                String expectedCTXTransactionResponseCode,
                                String simulatorScenario,
                                String simulatorResetState
                                ) throws IOException, InterruptedException, JSchException {

        // Set simulator to success
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
                .param("purchaseAmount", purchaseAmount)
                .when()
                .get()
                .then()
                .extract()
                .response();

        // Create ReserveAndtransactV4 payload object - contains ReserveAndtransactV4 request body
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

        // Create ReserveAndtransactV4 response body object - contains api response data for use in assertions or other calls
        Allure.step("Action Test Step 4 : Execute Reserve and Transact V4 REST Request");
        Response ReserveAndTransactV4response =
                given(CORE_getEndPoints_ReserveAndTransactV4)
                .contentType(ContentType.JSON)
                .body(ReserveAndTransactV4Payload)
                .when()
                .post()
                .then()
                .extract()
                .response();

        System.out.println("raasTxnRef for ReserveAndTransactV4SuccessSmokeTest: " + ReserveAndTransactV4response.path("raasTxnRef").toString());

        // ASSERTIONS
        // ReserveAndTransact V4 response assertions for successful purchase:

        //Expected responseCode
        Allure.step("Step.1 --> Reserve and Transact V4 REST response assertions --> Validate responseCode field is correct");
        Assert.assertEquals(ReserveAndTransactV4response.path("responseCode"), expectedRaasResponseCode);

        //Expected responseMessage
        Allure.step("Step.2 --> Reserve and Transact V4 REST response assertions --> Validate responseMessage field is correct");
        Assert.assertEquals(ReserveAndTransactV4response.path("responseMessage"), expectedMessage);

        //raasTxnRef is not null
        Allure.step("Step.3 --> Reserve and Transact V4 REST response assertions --> Validate raasTxnRef field exists");
        Assert.assertNotNull(ReserveAndTransactV4response.path("raasTxnRef"));

        //Valid HTTPResponseCode
        Allure.step("Step.4 --> Reserve and Transact V4 REST response assertions --> Validate HTTPResponseCode field is correct");
        Assert.assertEquals(ReserveAndTransactV4response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // raas db assertions
        //Transaction_log
        Allure.step("Action Test Step 5 : Wait 5 seconds before executing the RAAS Transaction_Log DB Assertions");
        Thread.sleep(5000);

        //Validate raas_txn_ref field is correct
        Allure.step("Step.5 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_txn_ref field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_txn_ref"), ReserveAndTransactV4response.path("raasTxnRef"));

        // validate transaction status is "SUCCESS"
        Allure.step("Step.6 --> RAAS DB assertions --> Table: transaction_log --> Validate status field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "status"), "SUCCESS");

        Allure.step("Step.7 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_response_response_code"), expectedRaasResponseCode);

        Allure.step("Step.8 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate raas_response_message field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "raas_response_message"), expectedMessage);

        Allure.step("Step.9 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate transaction_result_request_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_request_response_code"), expectedRaasResponseCode);

        Allure.step("Step.10 --> RAAS DB assertions --> Table: raas.transaction_log --> Validate transaction_result_response_response_code field is correct");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_log WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "transaction_result_response_response_code"), "202");

        //Validate funds were successfully reserved (response_code equals to 0000)
        Allure.step("Step.11 --> RAAS DB assertions --> Table: raas.reserve_fund_response --> Validate funds were successfully reserved (response_code equals to 0000)");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.reserve_fund_response WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "response_code"), expectedRaasResponseCode);

        //Validate ctx response code is SUCCESSFUL (0)
        Allure.step("Step.12 --> RAAS DB assertions --> Table: raas.ctx_response --> Validate ctx response code is SUCCESSFUL (0)");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.ctx_response WHERE client_transaction_id = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "response_code"), "0");

        //Validate successful transaction result is sent (0000)
        Allure.step("Step.13 --> RAAS DB assertions --> Table: raas.transaction_result_request --> Validate successful transaction result is sent (0000)");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_request WHERE raas_txn_ref = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "response_code"), "0000");

        //Validate success response code is received from the funding source (202)
        Allure.step("Step.14 --> RAAS DB assertions --> Table: raas.transaction_result_response --> Validate success response code is received from the funding source (202)");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.transaction_result_response WHERE raas_txn_ref = '" + ReserveAndTransactV4response.path("raasTxnRef") + "'", "response_code"), "202");

        //Validate transaction wasn't retried (no records found in the db)
        Allure.step("Step.15 --> RAAS DB assertions --> Table: raas.ctx_response --> Validate transaction wasn't retried (no records found in the db)");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.ctx_response WHERE client_transaction_id = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0001'", "response_code"), "null");

        //Validate transaction wasn't pending (no records found in the db)
        Allure.step("Step.16 --> RAAS DB assertions --> Table: raas.ctx_lookup_response --> Validate transaction wasn't pending (no records found in the db)");
        Assert.assertEquals(sqlDataAccess.verifyPostgreCustomSql("SELECT * FROM raas.ctx_lookup_response WHERE client_transaction_id = " + "'" + ReserveAndTransactV4response.path("raasTxnRef") + "-0000'", "response_code"), "null");

    }
}
