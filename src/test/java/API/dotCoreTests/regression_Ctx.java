/* Regression test suite for CTX
 *
 * Author: Juan-Claude Botha
 */

package API.dotCoreTests;

import api.testUtilities.sqlDataAccessLayer.sqlDataAccess;
import api.testUtilities.testConfig;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jcraft.jsch.JSchException;
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

import api.testUtilities.Simulators.startSimulator;

import static io.restassured.RestAssured.*;

@Listeners(allureApiTestListener.class)
public class regression_Ctx extends testConfig {

    // Create properties object in order to inject environment specific variables upon build
    Properties properties = configWrapper.loadPropertiesFile("config.properties");

    // create new vendor simulator instance
    startSimulator startsim = new startSimulator();

    // Random number generation
    String randomnumbers = RandomCharGenerator.getRandomNumbers(1000000000);
    String randomnumbers1 = RandomCharGenerator.getRandomNumbers(1000000000);
    String randomnumbers22 = RandomCharGenerator.getRandomNumbers(1000000000);
    String randomnumbers3 = RandomCharGenerator.getRandomNumbers(1000000000);
    String randomnumbers4 = RandomCharGenerator.getRandomNumbers(1000000000);
    String randomnumbers5 = RandomCharGenerator.getRandomNumbers(1000000000);
    String randomnumbers6 = RandomCharGenerator.getRandomNumbers(1000000000);
    String randomnumbers7 = RandomCharGenerator.getRandomNumbers(1000000000);
    String randomnumbers11 = RandomCharGenerator.getRandomNumbers(1000000000);
    String randomnumbers12 = RandomCharGenerator.getRandomNumbers(1000000000);
    String randomnumbers13 = RandomCharGenerator.getRandomNumbers(1000000000);
    String randomnumbers14 = RandomCharGenerator.getRandomNumbers(1000000000);

    // Data staging for use in test
    @DataProvider(name = "Ctxtestcases", parallel = false)
    public Object[] createTransactTestData() throws IOException, ParseException {


        return new String[][]{

                {testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseSuccess","sourceId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseSuccess","clientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseSuccess","channelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseSuccess","productId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseSuccess","clientTransactionId")  + randomnumbers + randomnumbers1 + "-0000",
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
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseSuccess","expectedCtxResponseCode"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseSuccess","simulatorScenario")},

                {testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseSuccess","sourceId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseSuccess","clientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseSuccess","channelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseSuccess","productId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseSuccess","clientTransactionId")  + randomnumbers1 + randomnumbers1 + "-0000",
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
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseSuccess","expectedCtxResponseCode"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseSuccess","simulatorScenario")},

                {testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseSuccess","sourceId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseSuccess","clientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseSuccess","channelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseSuccess","productId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseSuccess","clientTransactionId")  + randomnumbers22 + randomnumbers + "-0000",
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
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseSuccess","expectedCtxResponseCode"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseSuccess","simulatorScenario")},

                {testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseSuccess","sourceId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseSuccess","clientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseSuccess","channelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseSuccess","productId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseSuccess","clientTransactionId")  + randomnumbers + randomnumbers22 + "-0000",
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
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseSuccess","expectedCtxResponseCode"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseSuccess","simulatorScenario")},

        };
    }

    @Step("CTX POST Success")
    @Test(dataProvider = "Ctxtestcases", priority = 0)
    public void CtxSuccessScenarios(
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
                                          String expectedCtxResponseCode,
                                          String simulatorScenario) throws IOException, InterruptedException, ParserConfigurationException, SAXException, JSchException {

        // Start simulator and set all scenarios for vendors to return success
        startsim.SimulatorScenario(simulatorScenario);
        Thread.sleep(5000);

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

        // CTX response assertions
        Assert.assertEquals(getString("purchaseAmount", document.getDocumentElement()), expectedPurchaseAmount);
        Assert.assertEquals(getString("originId", document.getDocumentElement()), expectedOriginId);
        Assert.assertEquals(getString("productId", document.getDocumentElement()), expectedProductId);
        Assert.assertEquals(getString("channelIndicator", document.getDocumentElement()), expectedChannelIndicator);
        Assert.assertEquals(getString("responseCode", document.getDocumentElement()), expectedCtxResponseCode);
        Assert.assertEquals(getString("clientTransactionId", document.getDocumentElement()), clientTransactionId);
        Assert.assertNotNull(getString("timeLocalTransaction", document.getDocumentElement()));
        Assert.assertNotNull(getString("dateLocalTransaction", document.getDocumentElement()));

        // CTX DB assertions
        Thread.sleep(5000);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + clientTransactionId + "'", "transactionResponseCode"), expectedCtxResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + clientTransactionId + "'", "transactionId"), getString("vendorReferenceNo", document.getDocumentElement()));
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + clientTransactionId + "'", "clientStan"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + clientTransactionId + "'", "originId"), getString("originId", document.getDocumentElement()));
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + clientTransactionId + "'", "originatingService"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + clientTransactionId + "'", "purchaseAmount"), expectedPurchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + clientTransactionId + "'", "transactionState"), "C");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + clientTransactionId + "'", "transactionType"), "P");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + clientTransactionId + "'", "vendorReference"));
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + clientTransactionId + "'", "vendorResponseCode"));
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + clientTransactionId + "'", "vendorStan"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + clientTransactionId + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + clientTransactionId + "'", "product_id"), productId);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + clientTransactionId + "'", "systemResponseCode"), "0");
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + clientTransactionId + "'", "endDate"));
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + clientTransactionId + "'", "endVendorDate"));
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + clientTransactionId + "'", "startDate"));


    }

    // Data staging for use in test
    @DataProvider(name = "CTXDuplicateTransactionTestScenarios", parallel = false)
    public Object[] CTXDuplicateTransactionTestScenarios() throws IOException, ParseException {


        return new String[][]{

                {testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseDuplicateTransactionId","sourceId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseDuplicateTransactionId","clientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseDuplicateTransactionId","channelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseDuplicateTransactionId","productId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseDuplicateTransactionId","clientTransactionId")  + randomnumbers + randomnumbers1 + "-0000",
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseDuplicateTransactionId","purchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseDuplicateTransactionId","alternateClientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseDuplicateTransactionId","timeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseDuplicateTransactionId","xmlns"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseDuplicateTransactionId","apiToken"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseDuplicateTransactionId","originId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseDuplicateTransactionId","channelSessionId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseDuplicateTransactionId","dateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseDuplicateTransactionId","expectedPurchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseDuplicateTransactionId","expectedTimeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseDuplicateTransactionId","expectedDateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseDuplicateTransactionId","expectedOriginId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseDuplicateTransactionId","expectedProductId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseDuplicateTransactionId","expectedChannelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseDuplicateTransactionId","expectedCtxResponseCode"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseDuplicateTransactionId","simulatorScenario")},

                {testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseDuplicateTransactionId","sourceId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseDuplicateTransactionId","clientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseDuplicateTransactionId","channelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseDuplicateTransactionId","productId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseDuplicateTransactionId","clientTransactionId")  + randomnumbers1 + randomnumbers1 + "-0000",
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseDuplicateTransactionId","purchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseDuplicateTransactionId","alternateClientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseDuplicateTransactionId","timeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseDuplicateTransactionId","xmlns"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseDuplicateTransactionId","apiToken"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseDuplicateTransactionId","originId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseDuplicateTransactionId","channelSessionId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseDuplicateTransactionId","dateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseDuplicateTransactionId","expectedPurchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseDuplicateTransactionId","expectedTimeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseDuplicateTransactionId","expectedDateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseDuplicateTransactionId","expectedOriginId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseDuplicateTransactionId","expectedProductId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseDuplicateTransactionId","expectedChannelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseDuplicateTransactionId","expectedCtxResponseCode"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseDuplicateTransactionId","simulatorScenario")},

                {testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseDuplicateTransactionId","sourceId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseDuplicateTransactionId","clientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseDuplicateTransactionId","channelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseDuplicateTransactionId","productId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseDuplicateTransactionId","clientTransactionId")  + randomnumbers22 + randomnumbers + "-0000",
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseDuplicateTransactionId","purchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseDuplicateTransactionId","alternateClientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseDuplicateTransactionId","timeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseDuplicateTransactionId","xmlns"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseDuplicateTransactionId","apiToken"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseDuplicateTransactionId","originId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseDuplicateTransactionId","channelSessionId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseDuplicateTransactionId","dateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseDuplicateTransactionId","expectedPurchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseDuplicateTransactionId","expectedTimeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseDuplicateTransactionId","expectedDateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseDuplicateTransactionId","expectedOriginId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseDuplicateTransactionId","expectedProductId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseDuplicateTransactionId","expectedChannelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseDuplicateTransactionId","expectedCtxResponseCode"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseDuplicateTransactionId","simulatorScenario")},

                {testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseDuplicateTransactionId","sourceId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseDuplicateTransactionId","clientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseDuplicateTransactionId","channelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseDuplicateTransactionId","productId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseDuplicateTransactionId","clientTransactionId")  + randomnumbers + randomnumbers22 + "-0000",
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseDuplicateTransactionId","purchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseDuplicateTransactionId","alternateClientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseDuplicateTransactionId","timeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseDuplicateTransactionId","xmlns"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseDuplicateTransactionId","apiToken"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseDuplicateTransactionId","originId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseDuplicateTransactionId","channelSessionId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseDuplicateTransactionId","dateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseDuplicateTransactionId","expectedPurchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseDuplicateTransactionId","expectedTimeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseDuplicateTransactionId","expectedDateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseDuplicateTransactionId","expectedOriginId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseDuplicateTransactionId","expectedProductId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseDuplicateTransactionId","expectedChannelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseDuplicateTransactionId","expectedCtxResponseCode"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseDuplicateTransactionId","simulatorScenario")},

        };
    }

    @Step("CTX test duplicate transactions per vendor")
    @Test(dataProvider = "CTXDuplicateTransactionTestScenarios", priority = 1)
    public void CTXDuplicateTransactionsTests(
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
            String expectedCtxResponseCode,
            String simulatorScenario) throws IOException, InterruptedException, ParserConfigurationException, SAXException, JSchException {

        // Start simulator and set all scenarios for vendors to return success
        startsim.SimulatorScenario(simulatorScenario);
        Thread.sleep(5000);

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

        // CTX response assertions
        Assert.assertEquals(getString("purchaseAmount", document.getDocumentElement()), expectedPurchaseAmount);
        Assert.assertEquals(getString("originId", document.getDocumentElement()), expectedOriginId);
        Assert.assertEquals(getString("productId", document.getDocumentElement()), expectedProductId);
        Assert.assertEquals(getString("channelIndicator", document.getDocumentElement()), expectedChannelIndicator);
        Assert.assertEquals(getString("responseCode", document.getDocumentElement()), expectedCtxResponseCode);
        Assert.assertEquals(getString("clientTransactionId", document.getDocumentElement()), clientTransactionId);
        Assert.assertNotNull(getString("timeLocalTransaction", document.getDocumentElement()));
        Assert.assertNotNull(getString("dateLocalTransaction", document.getDocumentElement()));

    }

    @DataProvider(name = "CtxVendorSimNegativeTestCases", parallel = false)
    public Object[] CtxVendorSimNegativeTestCases() throws IOException, ParseException {



        return new String[][]{

                {testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNAdhocPurchaseFailTimeOut","sourceId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNAdhocPurchaseFailTimeOut","clientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNAdhocPurchaseFailTimeOut","channelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNAdhocPurchaseFailTimeOut","productId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNAdhocPurchaseFailTimeOut","clientTransactionId")  + randomnumbers6 + randomnumbers7 + "-0000",
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNAdhocPurchaseFailTimeOut","purchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNAdhocPurchaseFailTimeOut","alternateClientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNAdhocPurchaseFailTimeOut","timeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNAdhocPurchaseFailTimeOut","xmlns"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNAdhocPurchaseFailTimeOut","apiToken"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNAdhocPurchaseFailTimeOut","originId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNAdhocPurchaseFailTimeOut","channelSessionId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNAdhocPurchaseFailTimeOut","dateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNAdhocPurchaseFailTimeOut","expectedPurchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNAdhocPurchaseFailTimeOut","expectedTimeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNAdhocPurchaseFailTimeOut","expectedDateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNAdhocPurchaseFailTimeOut","expectedOriginId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNAdhocPurchaseFailTimeOut","expectedProductId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNAdhocPurchaseFailTimeOut","expectedChannelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNAdhocPurchaseFailTimeOut","expectedTransmissionDateTime"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNAdhocPurchaseFailTimeOut","expectedVendorReferenceNo"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNAdhocPurchaseFailTimeOut","expectedCtxResponseCode"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNAdhocPurchaseFailTimeOut","expectedFaultString"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNAdhocPurchaseFailTimeOut","simulatorScenario"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNAdhocPurchaseFailTimeOut","simulatorResetState")},

                {testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailTimeOut","sourceId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailTimeOut","clientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailTimeOut","channelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailTimeOut","productId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailTimeOut","clientTransactionId")  + randomnumbers6 + randomnumbers4 + "-0000",
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailTimeOut","purchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailTimeOut","alternateClientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailTimeOut","timeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailTimeOut","xmlns"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailTimeOut","apiToken"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailTimeOut","originId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailTimeOut","channelSessionId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailTimeOut","dateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailTimeOut","expectedPurchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailTimeOut","expectedTimeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailTimeOut","expectedDateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailTimeOut","expectedOriginId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailTimeOut","expectedProductId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailTimeOut","expectedChannelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailTimeOut","expectedTransmissionDateTime"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailTimeOut","expectedVendorReferenceNo"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailTimeOut","expectedCtxResponseCode"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailTimeOut","expectedFaultString"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailTimeOut","simulatorScenario"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailTimeOut","simulatorResetState")},

                {testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailTimeOut","sourceId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailTimeOut","clientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailTimeOut","channelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailTimeOut","productId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailTimeOut","clientTransactionId")  + randomnumbers3 + randomnumbers4 + "-0000",
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailTimeOut","purchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailTimeOut","alternateClientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailTimeOut","timeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailTimeOut","xmlns"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailTimeOut","apiToken"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailTimeOut","originId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailTimeOut","channelSessionId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailTimeOut","dateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailTimeOut","expectedPurchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailTimeOut","expectedTimeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailTimeOut","expectedDateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailTimeOut","expectedOriginId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailTimeOut","expectedProductId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailTimeOut","expectedChannelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailTimeOut","expectedTransmissionDateTime"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailTimeOut","expectedVendorReferenceNo"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailTimeOut","expectedCtxResponseCode"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailTimeOut","expectedFaultString"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailTimeOut","simulatorScenario"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailTimeOut","simulatorResetState")},

                {testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseFailTimeOut","sourceId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseFailTimeOut","clientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseFailTimeOut","channelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseFailTimeOut","productId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseFailTimeOut","clientTransactionId")  + randomnumbers3 + randomnumbers6 + "-0000",
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseFailTimeOut","purchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseFailTimeOut","alternateClientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseFailTimeOut","timeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseFailTimeOut","xmlns"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseFailTimeOut","apiToken"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseFailTimeOut","originId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseFailTimeOut","channelSessionId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseFailTimeOut","dateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseFailTimeOut","expectedPurchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseFailTimeOut","expectedTimeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseFailTimeOut","expectedDateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseFailTimeOut","expectedOriginId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseFailTimeOut","expectedProductId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseFailTimeOut","expectedChannelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseFailTimeOut","expectedTransmissionDateTime"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseFailTimeOut","expectedVendorReferenceNo"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseFailTimeOut","expectedCtxResponseCode"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseFailTimeOut","expectedFaultString"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseFailTimeOut","simulatorScenario"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseFailTimeOut","simulatorResetState")},


                {testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailErrorInvalidMSISDN","sourceId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailErrorInvalidMSISDN","clientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailErrorInvalidMSISDN","channelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailErrorInvalidMSISDN","productId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailErrorInvalidMSISDN","clientTransactionId")  + randomnumbers6 + randomnumbers5 + "-0000",
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailErrorInvalidMSISDN","purchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailErrorInvalidMSISDN","alternateClientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailErrorInvalidMSISDN","timeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailErrorInvalidMSISDN","xmlns"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailErrorInvalidMSISDN","apiToken"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailErrorInvalidMSISDN","originId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailErrorInvalidMSISDN","channelSessionId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailErrorInvalidMSISDN","dateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailErrorInvalidMSISDN","expectedPurchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailErrorInvalidMSISDN","expectedTimeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailErrorInvalidMSISDN","expectedDateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailErrorInvalidMSISDN","expectedOriginId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailErrorInvalidMSISDN","expectedProductId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailErrorInvalidMSISDN","expectedChannelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailErrorInvalidMSISDN","expectedTransmissionDateTime"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailErrorInvalidMSISDN","expectedVendorReferenceNo"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailErrorInvalidMSISDN","expectedCtxResponseCode"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailErrorInvalidMSISDN","expectedFaultString"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailErrorInvalidMSISDN","simulatorScenario"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailErrorInvalidMSISDN","simulatorResetState")},

                {testDataFactory.getTestData("CtxDataSource.json","ctxsuite","CellCPurchaseFailException","sourceId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","CellCPurchaseFailException","clientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","CellCPurchaseFailException","channelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","CellCPurchaseFailException","productId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","CellCPurchaseFailException","clientTransactionId")  + randomnumbers6 + randomnumbers3 + "-0000",
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","CellCPurchaseFailException","purchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","CellCPurchaseFailException","alternateClientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","CellCPurchaseFailException","timeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","CellCPurchaseFailException","xmlns"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","CellCPurchaseFailException","apiToken"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","CellCPurchaseFailException","originId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","CellCPurchaseFailException","channelSessionId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","CellCPurchaseFailException","dateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","CellCPurchaseFailException","expectedPurchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","CellCPurchaseFailException","expectedTimeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","CellCPurchaseFailException","expectedDateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","CellCPurchaseFailException","expectedOriginId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","CellCPurchaseFailException","expectedProductId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","CellCPurchaseFailException","expectedChannelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","CellCPurchaseFailException","expectedTransmissionDateTime"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","CellCPurchaseFailException","expectedVendorReferenceNo"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","CellCPurchaseFailException","expectedCtxResponseCode"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","CellCPurchaseFailException","expectedFaultString"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","CellCPurchaseFailException","simulatorScenario"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","CellCPurchaseFailException","simulatorResetState")},

                {testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailInvalidMSISDN","sourceId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailInvalidMSISDN","clientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailInvalidMSISDN","channelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailInvalidMSISDN","productId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailInvalidMSISDN","clientTransactionId")  + randomnumbers4 + randomnumbers4 + "-0000",
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailInvalidMSISDN","purchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailInvalidMSISDN","alternateClientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailInvalidMSISDN","timeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailInvalidMSISDN","xmlns"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailInvalidMSISDN","apiToken"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailInvalidMSISDN","originId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailInvalidMSISDN","channelSessionId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailInvalidMSISDN","dateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailInvalidMSISDN","expectedPurchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailInvalidMSISDN","expectedTimeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailInvalidMSISDN","expectedDateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailInvalidMSISDN","expectedOriginId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailInvalidMSISDN","expectedProductId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailInvalidMSISDN","expectedChannelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailInvalidMSISDN","expectedTransmissionDateTime"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailInvalidMSISDN","expectedVendorReferenceNo"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailInvalidMSISDN","expectedCtxResponseCode"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailInvalidMSISDN","expectedFaultString"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailInvalidMSISDN","simulatorScenario"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseFailInvalidMSISDN","simulatorResetState")}


        };
    }

    @Step("CTX POST Vendor Sim negative test cases")
    @Test(dataProvider = "CtxVendorSimNegativeTestCases", priority = 2)
    public void CTXVendorSimNegativeTests(
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
            String expectedCtxResponseCode,
            String expectedFaultString,
            String simulatorScenario,
            String simulatorResetState) throws IOException, InterruptedException, ParserConfigurationException, SAXException, JSchException {

        // Start simulator and set all scenarios for vendors to return failures
        startsim.SimulatorScenario(simulatorScenario);

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
        // CTX response assertions
        Assert.assertEquals(getString("purchaseAmount", document.getDocumentElement()), expectedPurchaseAmount);
        Assert.assertEquals(getString("originId", document.getDocumentElement()), expectedOriginId);
        Assert.assertEquals(getString("productId", document.getDocumentElement()), expectedProductId);
        Assert.assertEquals(getString("channelIndicator", document.getDocumentElement()), expectedChannelIndicator);
        Assert.assertEquals(getString("responseCode", document.getDocumentElement()), expectedCtxResponseCode);
        Assert.assertEquals(getString("clientTransactionId", document.getDocumentElement()), clientTransactionId);
        Assert.assertNotNull(getString("timeLocalTransaction", document.getDocumentElement()));
        Assert.assertNotNull(getString("dateLocalTransaction", document.getDocumentElement()));

        // CTX DB assertions
        Thread.sleep(5000);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + clientTransactionId + "'", "transactionResponseCode"), expectedCtxResponseCode);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + clientTransactionId + "'", "transactionId"), getString("vendorReferenceNo", document.getDocumentElement()));
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + clientTransactionId + "'", "clientStan"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + clientTransactionId + "'", "originId"), getString("originId", document.getDocumentElement()));
        Assert.assertNotNull(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + clientTransactionId + "'", "originatingService"));
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + clientTransactionId + "'", "purchaseAmount"), expectedPurchaseAmount);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + clientTransactionId + "'", "transactionState"), "C");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + clientTransactionId + "'", "transactionType"), "P");
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + clientTransactionId + "'", "client_id"), clientId);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + clientTransactionId + "'", "product_id"), productId);

        // reset simulator behaviour to success
        startsim.SimulatorScenario(simulatorResetState);
        Thread.sleep(5000);
    }

    @DataProvider(name = "CtxNullFieldsNegativeTestCases", parallel = true)
    public Object[] CtxNullFieldsNegativeTestCases() throws IOException, ParseException {




        return new String[][]{

                {testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullPurchaseAmount","sourceId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullPurchaseAmount","clientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullPurchaseAmount","channelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullPurchaseAmount","productId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullPurchaseAmount","clientTransactionId")  + randomnumbers11 + randomnumbers12 + "-0000",
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullPurchaseAmount","purchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullPurchaseAmount","alternateClientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullPurchaseAmount","timeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullPurchaseAmount","xmlns"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullPurchaseAmount","apiToken"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullPurchaseAmount","originId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullPurchaseAmount","channelSessionId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullPurchaseAmount","dateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullPurchaseAmount","expectedPurchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullPurchaseAmount","expectedTimeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullPurchaseAmount","expectedDateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullPurchaseAmount","expectedOriginId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullPurchaseAmount","expectedProductId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullPurchaseAmount","expectedChannelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullPurchaseAmount","expectedTransmissionDateTime"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullPurchaseAmount","expectedVendorReferenceNo"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullPurchaseAmount","expectedCtxResponseCode"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullPurchaseAmount","expectedFaultString")},

                {testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullapiToken","sourceId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullapiToken","clientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullapiToken","channelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullapiToken","productId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullapiToken","clientTransactionId")  + randomnumbers13 + randomnumbers14 + "-0000",
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullapiToken","purchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullapiToken","alternateClientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullapiToken","timeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullapiToken","xmlns"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullapiToken","apiToken"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullapiToken","originId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullapiToken","channelSessionId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullapiToken","dateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullapiToken","expectedPurchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullapiToken","expectedTimeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullapiToken","expectedDateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullapiToken","expectedOriginId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullapiToken","expectedProductId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullapiToken","expectedChannelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullapiToken","expectedTransmissionDateTime"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullapiToken","expectedVendorReferenceNo"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullapiToken","expectedCtxResponseCode"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullapiToken","expectedFaultString")},

                {testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientId","sourceId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientId","clientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientId","channelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientId","productId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientId","clientTransactionId")  + randomnumbers13 + randomnumbers14 + "-0000",
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientId","purchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientId","alternateClientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientId","timeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientId","xmlns"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientId","apiToken"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientId","originId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientId","channelSessionId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientId","dateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientId","expectedPurchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientId","expectedTimeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientId","expectedDateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientId","expectedOriginId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientId","expectedProductId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientId","expectedChannelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientId","expectedTransmissionDateTime"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientId","expectedVendorReferenceNo"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientId","expectedCtxResponseCode"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientId","expectedFaultString")},

                {testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullTimeLocalTransaction","sourceId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullTimeLocalTransaction","clientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullTimeLocalTransaction","channelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullTimeLocalTransaction","productId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullTimeLocalTransaction","clientTransactionId")  + randomnumbers11 + randomnumbers14 + "-0000",
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullTimeLocalTransaction","purchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullTimeLocalTransaction","alternateClientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullTimeLocalTransaction","timeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullTimeLocalTransaction","xmlns"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullTimeLocalTransaction","apiToken"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullTimeLocalTransaction","originId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullTimeLocalTransaction","channelSessionId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullTimeLocalTransaction","dateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullTimeLocalTransaction","expectedPurchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullTimeLocalTransaction","expectedTimeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullTimeLocalTransaction","expectedDateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullTimeLocalTransaction","expectedOriginId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullTimeLocalTransaction","expectedProductId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullTimeLocalTransaction","expectedChannelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullTimeLocalTransaction","expectedTransmissionDateTime"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullTimeLocalTransaction","expectedVendorReferenceNo"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullTimeLocalTransaction","expectedCtxResponseCode"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullTimeLocalTransaction","expectedFaultString")},

                {testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullDateLocalTransaction","sourceId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullDateLocalTransaction","clientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullDateLocalTransaction","channelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullDateLocalTransaction","productId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullDateLocalTransaction","clientTransactionId")  + randomnumbers11 + randomnumbers13 + "-0000",
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullDateLocalTransaction","purchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullDateLocalTransaction","alternateClientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullDateLocalTransaction","timeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullDateLocalTransaction","xmlns"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullDateLocalTransaction","apiToken"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullDateLocalTransaction","originId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullDateLocalTransaction","channelSessionId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullDateLocalTransaction","dateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullDateLocalTransaction","expectedPurchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullDateLocalTransaction","expectedTimeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullDateLocalTransaction","expectedDateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullDateLocalTransaction","expectedOriginId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullDateLocalTransaction","expectedProductId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullDateLocalTransaction","expectedChannelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullDateLocalTransaction","expectedTransmissionDateTime"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullDateLocalTransaction","expectedVendorReferenceNo"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullDateLocalTransaction","expectedCtxResponseCode"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullDateLocalTransaction","expectedFaultString")},

                {testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","sourceId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","clientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","channelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","productId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","clientTransactionId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","purchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","alternateClientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","timeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","xmlns"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","apiToken"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","originId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","channelSessionId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","dateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","expectedPurchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","expectedTimeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","expectedDateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","expectedOriginId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","expectedProductId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","expectedChannelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","expectedTransmissionDateTime"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","expectedVendorReferenceNo"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","expectedCtxResponseCode"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","expectedFaultString")},

                {testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientTransactionId","sourceId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientTransactionId","clientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientTransactionId","channelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientTransactionId","productId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientTransactionId","clientTransactionId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientTransactionId","purchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientTransactionId","alternateClientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientTransactionId","timeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientTransactionId","xmlns"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientTransactionId","apiToken"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientTransactionId","originId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientTransactionId","channelSessionId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientTransactionId","dateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientTransactionId","expectedPurchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientTransactionId","expectedTimeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientTransactionId","expectedDateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientTransactionId","expectedOriginId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientTransactionId","expectedProductId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientTransactionId","expectedChannelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientTransactionId","expectedTransmissionDateTime"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientTransactionId","expectedVendorReferenceNo"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientTransactionId","expectedCtxResponseCode"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullClientTransactionId","expectedFaultString")},

                {testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","sourceId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","clientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","channelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","productId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","clientTransactionId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","purchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","alternateClientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","timeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","xmlns"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","apiToken"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","originId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","channelSessionId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","dateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","expectedPurchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","expectedTimeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","expectedDateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","expectedOriginId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","expectedProductId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","expectedChannelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","expectedTransmissionDateTime"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","expectedVendorReferenceNo"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","expectedCtxResponseCode"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullOriginId","expectedFaultString")},

                {testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullProductId","sourceId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullProductId","clientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullProductId","channelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullProductId","productId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullProductId","clientTransactionId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullProductId","purchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullProductId","alternateClientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullProductId","timeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullProductId","xmlns"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullProductId","apiToken"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullProductId","originId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullProductId","channelSessionId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullProductId","dateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullProductId","expectedPurchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullProductId","expectedTimeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullProductId","expectedDateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullProductId","expectedOriginId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullProductId","expectedProductId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullProductId","expectedChannelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullProductId","expectedTransmissionDateTime"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullProductId","expectedVendorReferenceNo"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullProductId","expectedCtxResponseCode"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullProductId","expectedFaultString")},

                {testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelIndicator","sourceId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelIndicator","clientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelIndicator","channelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelIndicator","productId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelIndicator","clientTransactionId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelIndicator","purchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelIndicator","alternateClientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelIndicator","timeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelIndicator","xmlns"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelIndicator","apiToken"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelIndicator","originId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelIndicator","channelSessionId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelIndicator","dateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelIndicator","expectedPurchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelIndicator","expectedTimeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelIndicator","expectedDateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelIndicator","expectedOriginId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelIndicator","expectedProductId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelIndicator","expectedChannelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelIndicator","expectedTransmissionDateTime"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelIndicator","expectedVendorReferenceNo"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelIndicator","expectedCtxResponseCode"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelIndicator","expectedFaultString")},

                {testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullAlternateClientId","sourceId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullAlternateClientId","clientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullAlternateClientId","channelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullAlternateClientId","productId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullAlternateClientId","clientTransactionId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullAlternateClientId","purchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullAlternateClientId","alternateClientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullAlternateClientId","timeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullAlternateClientId","xmlns"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullAlternateClientId","apiToken"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullAlternateClientId","originId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullAlternateClientId","channelSessionId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullAlternateClientId","dateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullAlternateClientId","expectedPurchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullAlternateClientId","expectedTimeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullAlternateClientId","expectedDateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullAlternateClientId","expectedOriginId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullAlternateClientId","expectedProductId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullAlternateClientId","expectedChannelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullAlternateClientId","expectedTransmissionDateTime"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullAlternateClientId","expectedVendorReferenceNo"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullAlternateClientId","expectedCtxResponseCode"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullAlternateClientId","expectedFaultString")},

                {testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullSourceId","sourceId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullSourceId","clientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullSourceId","channelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullSourceId","productId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullSourceId","clientTransactionId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullSourceId","purchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullSourceId","alternateClientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullSourceId","timeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullSourceId","xmlns"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullSourceId","apiToken"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullSourceId","originId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullSourceId","channelSessionId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullSourceId","dateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullSourceId","expectedPurchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullSourceId","expectedTimeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullSourceId","expectedDateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullSourceId","expectedOriginId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullSourceId","expectedProductId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullSourceId","expectedChannelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullSourceId","expectedTransmissionDateTime"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullSourceId","expectedVendorReferenceNo"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullSourceId","expectedCtxResponseCode"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullSourceId","expectedFaultString")},

                {testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelSessionId","sourceId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelSessionId","clientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelSessionId","channelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelSessionId","productId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelSessionId","clientTransactionId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelSessionId","purchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelSessionId","alternateClientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelSessionId","timeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelSessionId","xmlns"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelSessionId","apiToken"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelSessionId","originId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelSessionId","channelSessionId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelSessionId","dateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelSessionId","expectedPurchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelSessionId","expectedTimeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelSessionId","expectedDateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelSessionId","expectedOriginId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelSessionId","expectedProductId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelSessionId","expectedChannelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelSessionId","expectedTransmissionDateTime"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelSessionId","expectedVendorReferenceNo"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelSessionId","expectedCtxResponseCode"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailNullChannelSessionId","expectedFaultString")}


        };
    }

    @Step("CTX POST Null Fields negative test cases")
    @Test(dataProvider = "CtxNullFieldsNegativeTestCases", priority = 3)
    public void CTXNullFieldsNegativeTests(
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
            String expectedCtxResponseCode,
            String expectedFaultString) throws IOException, InterruptedException, ParserConfigurationException, SAXException, JSchException {

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
        // CTX response assertions
        Assert.assertEquals(getString("faultstring", document.getDocumentElement()), expectedFaultString);

        // CTX DB assertions
        Thread.sleep(5000);
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + clientTransactionId + "'", "transactionId"), "null");

    }

    // Function to get values from CTX response document body
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
