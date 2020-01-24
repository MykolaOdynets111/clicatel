/* Regression test suite for CTX
 *
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
import api.requestLibary.CORE.CtxPOJO;

import api.testUtilities.dataBuilders.testDataFactory;
import api.testUtilities.dataBuilders.RandomCharGenerator;

import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

import api.testUtilities.propertyConfigWrapper.configWrapper;

public class regression_Ctx {

    // Create properties object in order to inject environment specific variables upon build
    Properties properties = configWrapper.loadPropertiesFile("config.properties");

    // Data staging for use in test
    @DataProvider(name = "Ctxtestcases", parallel = true)
    public Object[] createTransactTestData() throws IOException, ParseException {

        String randomnumbers = RandomCharGenerator.getRandomNumbers(1000);
        return new String[][]{

                {testDataFactory.getTestData("CtxDataSource.json","ctxsuite","successcase1","sourceId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","successcase1","clientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","successcase1","channelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","successcase1","productId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","successcase1","clientTransactionId")  + randomnumbers,
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","successcase1","purchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","successcase1","alternateClientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","successcase1","timeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","successcase1","xmlns"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","successcase1","apiToken"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","successcase1","originId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","successcase1","channelSessionId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","successcase1","dateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","successcase1","expectedRaasResponseCode"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","successcase1","expectedMessage"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","successcase1","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","successcase1","expectedRaasResultRequestResponseCode"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","successcase1","expectedRaasResultResponseResponseCode"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","successcase1","expectedCTXTransactionResponseCode")},

        };
    }

    @Test(dataProvider = "Ctxtestcases")
    public void basicCtx(
                                          String sourceId,
                                          String clientId,
                                          String channelIndicator,
                                          String productId,
                                          String clientTransactionId,
                                          String purchaseAmount,
                                          String alternateClientId,
                                          String timeLocalTransaction,
                                          String xmlns,
                                          String apiToken,
                                          String originId,
                                          String channelSessionId,
                                          String dateLocalTransaction,
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



        // Create CTX payload object - contains CTX request body
        CtxPOJO CtxPayload = new CtxPOJO(
                sourceId,
                clientId,
                channelIndicator,
                productId,
                clientTransactionId,
                purchaseAmount,
                alternateClientId,
                timeLocalTransaction,
                xmlns,
                apiToken,
                originId,
                channelSessionId,
                dateLocalTransaction);

        // Create transactV4 response body object - contains api response data for use in assertions or other calls
        Response Ctxresponse =
                given()
                        .contentType(ContentType.JSON)
                        .body(CtxPayload)
                        .when()
                        .post(properties.getProperty("CORE_CTX_QA")+":"+properties.getProperty("CORE_CTX_RequestSpec_Port")+properties.getProperty("CORE_CTX_RequestSpec_BasePath"))
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
        //Assert.assertEquals(ReserveAndTransactV3response.path("responseCode"), expectedRaasResponseCode);
        //Assert.assertEquals(ReserveAndTransactV3response.path("responseMessage"), expectedMessage);
        //Assert.assertEquals(ReserveAndTransactV3response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // CTX DB assertions
        //Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + Ctxresponse.path("raasTxnRef") + "-0000'", "transactionResponseCode"), expectedCTXTransactionResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "clientTransactionId"), TransactV4response.path("raasTxnRef") + "-0000");
        //Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'" , "product_id"), productId);
        Assert.assertEquals(clientId, sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = '" + Ctxresponse.path("clientTransactionId"), "client_id"));

        // raas db assertions
        //Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.transaction_log", "raas_txn_ref", "=", ReserveAndTransactV3response.path("raasTxnRef")), ReserveAndTransactV3response.path("raasTxnRef"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_request", "raas_txn_ref", "=", ReserveAndTransactV3response.path("raasTxnRef")), ReserveAndTransactV3response.path("raasTxnRef"));
        //Assert.assertEquals(sqlDataAccess.verifyPostgreDb("raas.raas_response", "raas_txn_ref", "=", ReserveAndTransactV3response.path("raasTxnRef")), ReserveAndTransactV3response.path("raasTxnRef"));

    }


}
