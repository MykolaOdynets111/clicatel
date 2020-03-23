/* Regression test suite for CTX
 *
 * Author: Juan-Claude Botha
 */

package API.dotCoreTests;

import api.testUtilities.sqlDataAccessLayer.sqlDataAccess;
import api.testUtilities.testConfig;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import groovy.util.XmlSlurper;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.path.xml.element.NodeChildren;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import api.requestLibary.CORE.CtxPOJO;

import api.testUtilities.dataBuilders.testDataFactory;
import api.testUtilities.dataBuilders.RandomCharGenerator;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

import api.testUtilities.propertyConfigWrapper.configWrapper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import util.Listeners.allureApiTestListener;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.json.XML;

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

                {testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseSuccess","sourceId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseSuccess","clientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseSuccess","channelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseSuccess","productId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseSuccess","clientTransactionId")  + randomnumbers,
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseSuccess","purchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseSuccess","alternateClientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseSuccess","timeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseSuccess","xmlns"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseSuccess","apiToken"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseSuccess","originId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseSuccess","channelSessionId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseSuccess","dateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseSuccess","expectedPurchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseSuccess","expectedTimeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseSuccess","expectedDateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseSuccess","expectedOriginId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseSuccess","expectedProductId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseSuccess","expectedChannelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseSuccess","expectedTransmissionDateTime"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseSuccess","expectedVendorReferenceNo"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseSuccess","expectedCtxResponseCode")},

                {testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseSuccess","sourceId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseSuccess","clientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseSuccess","channelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseSuccess","productId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseSuccess","clientTransactionId")  + randomnumbers,
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseSuccess","purchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseSuccess","alternateClientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseSuccess","timeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseSuccess","xmlns"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseSuccess","apiToken"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseSuccess","originId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseSuccess","channelSessionId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseSuccess","dateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseSuccess","expectedPurchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseSuccess","expectedTimeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseSuccess","expectedDateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseSuccess","expectedOriginId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseSuccess","expectedProductId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseSuccess","expectedChannelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseSuccess","expectedTransmissionDateTime"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseSuccess","expectedVendorReferenceNo"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseSuccess","expectedCtxResponseCode")},

                {testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseSuccess","sourceId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseSuccess","clientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseSuccess","channelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseSuccess","productId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseSuccess","clientTransactionId")  + randomnumbers,
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseSuccess","purchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseSuccess","alternateClientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseSuccess","timeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseSuccess","xmlns"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseSuccess","apiToken"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseSuccess","originId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseSuccess","channelSessionId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseSuccess","dateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseSuccess","expectedPurchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseSuccess","expectedTimeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseSuccess","expectedDateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseSuccess","expectedOriginId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseSuccess","expectedProductId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseSuccess","expectedChannelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseSuccess","expectedTransmissionDateTime"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseSuccess","expectedVendorReferenceNo"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseSuccess","expectedCtxResponseCode")},

                {testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseSuccess","sourceId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseSuccess","clientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseSuccess","channelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseSuccess","productId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseSuccess","clientTransactionId")  + randomnumbers,
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseSuccess","purchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseSuccess","alternateClientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseSuccess","timeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseSuccess","xmlns"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseSuccess","apiToken"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseSuccess","originId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseSuccess","channelSessionId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseSuccess","dateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseSuccess","expectedPurchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseSuccess","expectedTimeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseSuccess","expectedDateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseSuccess","expectedOriginId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseSuccess","expectedProductId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseSuccess","expectedChannelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseSuccess","expectedTransmissionDateTime"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseSuccess","expectedVendorReferenceNo"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseSuccess","expectedCtxResponseCode")},

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

        String xmlCTX = Ctxresponse.asString();

        System.out.println(xmlCTX);

       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
       DocumentBuilder builder = factory.newDocumentBuilder();
       Document document = builder.parse(new InputSource(new StringReader(xmlCTX)));

        // Assertions
        // Finance Terms Calculate response assertions
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "");
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "null");
        Assert.assertEquals(finTermsCalculateResponse.path("clientId").toString(), clientId);
        Assert.assertEquals(finTermsCalculateResponse.path("productId").toString(), productId);
        Assert.assertEquals(finTermsCalculateResponse.path("purchaseAmount").toString(), purchaseAmount);

        // CTX response assertions
        Assert.assertEquals(getString("purchaseAmount", document.getDocumentElement()), expectedPurchaseAmount);
        Assert.assertEquals(getString("originId", document.getDocumentElement()), expectedOriginId);
        Assert.assertEquals(getString("productId", document.getDocumentElement()), expectedProductId);
        Assert.assertEquals(getString("channelIndicator", document.getDocumentElement()), expectedChannelIndicator);

        // CTX DB assertions
        Thread.sleep(5000);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + clientTransactionId + "'", "transactionResponseCode"), expectedCtxResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + clientTransactionId + "'", "transactionId"), getString("vendorReferenceNo", document.getDocumentElement()));
        //Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + Ctxresponse.path("clientTransactionId").toString(), "transactionResponseCode"), expectedCTXTransactionResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + Ctxresponse.path("clientTransactionId"), "transactionResponseCode"), expectedCTXTransactionResponseCode);
        //Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'", "clientTransactionId"), TransactV4response.path("raasTxnRef") + "-0000");
        //Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + TransactV4response.path("raasTxnRef") + "-0000'" , "product_id"), productId);
        //Assert.assertEquals(clientId, sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = '" + Ctxresponse.path("clientTransactionId"), "client_id"));

    }

    protected String getString(String tagName, Element element) {
        NodeList list = element.getElementsByTagName(tagName);
        if (list != null && list.getLength() > 0) {
            NodeList subList = list.item(0).getChildNodes();

            if (subList != null && subList.getLength() > 0) {
                return subList.item(0).getNodeValue();
            }
        }

        return null;
    }

}
