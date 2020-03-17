/* Regression test suite for CTX
 *
 * Author: Juan-Claude Botha
 */

package API.dotCoreTests;

import api.testUtilities.sqlDataAccessLayer.sqlDataAccess;
import api.testUtilities.testConfig;
import com.google.gson.JsonObject;
import groovy.util.XmlSlurper;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.path.xml.element.NodeChildren;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import api.requestLibary.CORE.CtxPOJO;

import api.testUtilities.dataBuilders.testDataFactory;
import api.testUtilities.dataBuilders.RandomCharGenerator;

import java.io.IOException;
import java.util.Properties;

import api.testUtilities.propertyConfigWrapper.configWrapper;
import org.xml.sax.SAXException;
import util.Listeners.allureApiTestListener;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.parsers.ParserConfigurationException;

import static io.restassured.RestAssured.*;

@Listeners(allureApiTestListener.class)
public class regression_Ctx extends testConfig {

    // Create properties object in order to inject environment specific variables upon build
    Properties properties = configWrapper.loadPropertiesFile("config.properties");

    // Data staging for use in test
    @DataProvider(name = "Ctxtestcases", parallel = true)
    public Object[] createTransactTestData() throws IOException, ParseException {

        String randomnumbers = RandomCharGenerator.getRandomNumbers(10001);
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
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","successcase1","expectedPurchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","successcase1","expectedTimeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","successcase1","expectedDateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","successcase1","expectedOriginId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","successcase1","expectedProductId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","successcase1","expectedChannelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","successcase1","expectedTransmissionDateTime"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","successcase1","expectedVendorReferenceNo"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","successcase1","expectedCtxResponseCode")},

        };
    }

    @Step("CTX POST Success")
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
                                          String expectedPurchaseAmount,
                                          String expectedTimeLocalTransaction,
                                          String expectedDateLocalTransaction,
                                          String expectedOriginId,
                                          String expectedProductId,
                                          String expectedChannelIndicator,
                                          String expectedTransmissionDateTime,
                                          String expectedVendorReferenceNo,
                                          String expectedCtxResponseCode) throws IOException, InterruptedException, ParserConfigurationException, SAXException {

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
        /*CtxPOJO CtxPayload = new CtxPOJO(
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
                dateLocalTransaction);*/


        // Stage ctx test data and prepare payload
        String CtxPayload = "<?xml version=\"1.0\" ?>\r\n" +
                "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n" +
                "<S:Body>\r\n" +
                "<purchase xmlns=\"http://clickatell.com/types\">\r\n" +
                "<clientId>"+clientId+"</clientId>\r\n" +
                "<apiToken>"+apiToken+"</apiToken>\r\n" +
                "<purchaseAmount>"+purchaseAmount+"</purchaseAmount>\r\n" +
                "<timeLocalTransaction>"+timeLocalTransaction+"</timeLocalTransaction>\r\n" +
                "<dateLocalTransaction>"+dateLocalTransaction+"</dateLocalTransaction>\r\n" +
                "<originId>"+originId+"</originId>\r\n" +
                "<clientTransactionId>"+clientTransactionId+"</clientTransactionId>\r\n" +
                "<productId>"+productId+"</productId>\r\n" +
                "<channelIndicator>"+channelIndicator+"</channelIndicator>\r\n" +
                "<alternateClientId>"+alternateClientId+"</alternateClientId>\r\n" +
                "<sourceId>"+sourceId+"</sourceId>\r\n" +
                "<channelSessionId>"+channelSessionId+"</channelSessionId>\r\n" +
                "</purchase>\r\n" +
                "</S:Body>\r\n" +
                "</S:Envelope>";



        // Create transactV4 response body object - contains api response data for use in assertions or other calls
        Response Ctxresponse =
                given(CORE_getEndPoints_CTX)
                        .contentType(ContentType.XML)
                        .accept(ContentType.XML)
                        .body(CtxPayload)
                        .when()
                        .post()
                        .then()
                        .extract()
                        .response();



        Ctxresponse.prettyPrint();

        String stringResponse = Ctxresponse.asString();
        XmlPath ctxXmlPath = new XmlPath(stringResponse);
        //String trnId = ctxXmlPath.get("clientTransactionId[0]");
        //System.out.println(trnId);

        //String Ctxresponse1 = get("/purchaseResponse").asString();
        //System.out.println(Ctxresponse1.path("purchaseAmount").toString());
        //NodeChildren children = ctxXmlPath.getNode("purchaseAmount").children();

        //String childPurchase = children.get("purchaseAmount").toString();
        //String ret = ctxXmlPath.getString("purchaseAmount");

        //System.out.println(children.get("purchaseAmount").toString());
        //System.out.println((char[]) ctxXmlPath.get("purchaseResponse.purchaseAmount"));

        //String ctxClientTranResp = Ctxresponse.xmlPath().getString("soapenv:Body.purchaseResponse.clientTransactionId");
        //System.out.println(ctxClientTranResp);
        //System.out.println(clientTransactionId);
        //String ctxClientTransactionIdResponse = get("/purchase").xmlPath().get("purchase.clientTransactionId");
        //System.out.println(ctxClientTransactionIdResponse);
        //String xmlClientTran = get("/service").xmlPath().getString("purchaseResponse.clientTransactionId");
        //System.out.println(xmlClientTran);

        //Object testResponse  = Ctxresponse.path("test");
        //System.out.println(testResponse);
        //String response = Ctxresponse.path("purchaseAmount").toString();
        //String response = Ctxresponse.path("purchaseAmount").toString();

        // Assertions
        // Finance Terms Calculate response assertions
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "");
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "null");
        Assert.assertEquals(finTermsCalculateResponse.path("clientId").toString(), clientId);
        Assert.assertEquals(finTermsCalculateResponse.path("productId").toString(), productId);
        Assert.assertEquals(finTermsCalculateResponse.path("purchaseAmount").toString(), purchaseAmount);

        // CTX response assertions
        //Assert.assertEquals(Ctxresponse.xmlPath(), expectedPurchaseAmount);
        //Assert.assertEquals(XmlPath.from(Ctxresponse1).get("purchaseAmount"), expectedPurchaseAmount);
        //Assert.assertEquals(ctxXmlPath.get("Envelope.body.purchaseResponse.purchaseAmount").toString(), expectedPurchaseAmount);
        //Assert.assertEquals(ReserveAndTransactV3response.path("responseMessage"), expectedMessage);
        //Assert.assertEquals(ReserveAndTransactV3response.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // CTX DB assertions
        Thread.sleep(5000);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + clientTransactionId + "'", "transactionResponseCode"), expectedCtxResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + Ctxresponse.path("clientTransactionId").toString(), "transactionResponseCode"), expectedCTXTransactionResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + Ctxresponse.path("clientTransactionId"), "transactionResponseCode"), expectedCTXTransactionResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "clientTransactionId"), TransactV4response.path("raasTxnRef") + "-0000");
        //Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'" , "product_id"), productId);
        //Assert.assertEquals(clientId, sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = '" + Ctxresponse.path("clientTransactionId"), "client_id"));



    }


}
