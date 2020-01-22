/**
 * TestSuite: ReserveAndTransactV3 Regression test suite.
 * Includes all end to end api tests for Reserve and Transact V3
 * Author: Juan-Claude Botha
 */

package API.dotCoreTests;

import api.testUtilities.sqlDataAccessLayer.sqlDataAccess;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import api.requestLibary.CORE.coreReserveAndTransactV3POJO;

import api.testUtilities.dataBuilders.testDataFactory;

import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

import api.testUtilities.propertyConfigWrapper.configWrapper;

public class regression_Raas_Reserve_and_Transact_V3 {

    // Create properties object in order to inject environment specific variables upon build
    Properties properties = configWrapper.loadPropertiesFile("config.properties");

    // Data staging for use in test
    @DataProvider(name = "ReserveAndTransactV3testcases", parallel = true)
    public Object[] createTransactTestData() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","successcase1","accountIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","successcase1","purchaseAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","successcase1","channelId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","successcase1","channelName"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","successcase1","channelSessionId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","successcase1","clientId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","successcase1","clientTxnRef"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","successcase1","productId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","successcase1","sourceIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","successcase1","targetIdentifier"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","successcase1","timestamp"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","successcase1","feeAmount"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","successcase1","currencyCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","successcase1","fundingSourceId"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","successcase1","expectedRaasResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","successcase1","expectedMessage"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","successcase1","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","successcase1","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","successcase1","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("ReserveAndTransactV3datasource.json","reserveandtransactv3suite","successcase1","expectedCTXTransactionResponseCode")},

        };
    }

    @Test(dataProvider = "ReserveAndTransactV3testcases")
    public void basicReserveAndTransactV3(String accountIdentifier,
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
                given()
                        .param("clientId",clientId)
                        .param("productId", productId)
                        .param("purchaseAmount", purchaseAmount)
                        .when()
                        .get(properties.getProperty("QA_MINION")+":"+properties.getProperty("CORE_FinTermsCalc_Port")+properties.getProperty("CORE_FinTermsCalc_BasePath"))
                        .then()
                        .extract()
                        .response();

        // Create ReserveAndtransactV4 payload object - contains transactV4 request body
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
                feeAmount,
                currencyCode,
                fundingSourceId);

        // Create transactV4 response body object - contains api response data for use in assertions or other calls
        Response ReserveAndTransactV3response =
                given()
                        .contentType(ContentType.JSON)
                        .body(ReserveAndTransactV3Payload)
                        .when()
                        .post(properties.getProperty("QA_MINION")+":"+properties.getProperty("CORE_Reserve_And_Transact_V3_RequestSpec_Port")+properties.getProperty("CORE_Reserve_And_Transact_V3_RequestSpec_BasePath"))
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
        Assert.assertEquals(ReserveAndTransactV3response.path("responseCode"), expectedRaasResponseCode);
        Assert.assertEquals(ReserveAndTransactV3response.path("responseMessage"), expectedMessage);
        Assert.assertEquals(ReserveAndTransactV3response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // CTX DB assertions
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + ReserveAndTransactV3response.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "clientTransactionId"), TransactV4response.path("raasTxnRef") + "-0000");
        //Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'" , "product_id"), productId);
        //Assert.assertEquals(clientId, sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = '" + TransactV4response.path("raasTxnRef") + "-0000", "client_id"));

        // raas db assertions
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.transaction_log", "raas_txn_ref", "=", ReserveAndTransactV3response.path("raasTxnRef")), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", ReserveAndTransactV3response.path("raasTxnRef")), ReserveAndTransactV3response.path("raasTxnRef"));
        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", ReserveAndTransactV3response.path("raasTxnRef")), ReserveAndTransactV3response.path("raasTxnRef"));

    }


}
