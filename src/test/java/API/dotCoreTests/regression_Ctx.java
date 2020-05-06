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

    // Data staging for use in test
    @DataProvider(name = "Ctxtestcases", parallel = true)
    public Object[] createTransactTestData() throws IOException, ParseException {

        String randomnumbers = RandomCharGenerator.getRandomNumbers(1000111111);
        String randomnumbers1 = RandomCharGenerator.getRandomNumbers(1000111111);
        return new String[][]{

                {testDataFactory.getTestData("CtxDataSource.json","ctxsuite","successcase1","sourceId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","successcase1","clientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","successcase1","channelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","successcase1","productId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","successcase1","clientTransactionId")  + randomnumbers + randomnumbers1 + "-0000",
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
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","airtelPurchaseSuccess","expectedCtxResponseCode")},

                {testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseSuccess","sourceId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseSuccess","clientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseSuccess","channelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseSuccess","productId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","etisalatPurchaseSuccess","clientTransactionId")  + randomnumbers + randomnumbers1 + "-0000",
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
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","GloPurchaseSuccess","clientTransactionId")  + randomnumbers + randomnumbers1 + "-0000",
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
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseSuccess","clientTransactionId")  + randomnumbers + randomnumbers1 + "-0000",
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
                                          String expectedCtxResponseCode) throws IOException, InterruptedException, ParserConfigurationException, SAXException, JSchException {

        // Start simulator and set all scenarios for vendors to return success
        // note to self -- add only one sim scenario and use test data source to set values to use for scenarios
        startsim.SimulatorScenario("MTNNG SUCCESS");
        startsim.SimulatorScenario("GLO SUCCESS");
        startsim.SimulatorScenario("AIRTEL SUCCESS");
        startsim.SimulatorScenario("ETISALAT SUCCESS");
        startsim.SimulatorScenario("CELLC SUCCESS");
        startsim.SimulatorScenario("TELKOM SUCCESS");

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


    // Data staging for use in test - negative test cases - this needs to be reworked using a simulator
    // The simulator will need to be updated with scenarios and we will need to do assertions based on the scenario for the
    // simulation
    @DataProvider(name = "CtxNegativeTestCases", parallel = true)
    public Object[] CtxNegativeTestCases() throws IOException, ParseException {

        String randomnumbers3 = RandomCharGenerator.getRandomNumbers(1000111111);
        String randomnumbers4 = RandomCharGenerator.getRandomNumbers(1000111111);
        return new String[][]{

                {testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailInvalidAmount","sourceId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailInvalidAmount","clientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailInvalidAmount","channelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailInvalidAmount","productId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailInvalidAmount","clientTransactionId")  + randomnumbers3 + randomnumbers4 + "-0000",
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailInvalidAmount","purchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailInvalidAmount","alternateClientId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailInvalidAmount","timeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailInvalidAmount","xmlns"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailInvalidAmount","apiToken"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailInvalidAmount","originId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailInvalidAmount","channelSessionId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailInvalidAmount","dateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailInvalidAmount","expectedPurchaseAmount"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailInvalidAmount","expectedTimeLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailInvalidAmount","expectedDateLocalTransaction"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailInvalidAmount","expectedOriginId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailInvalidAmount","expectedProductId"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailInvalidAmount","expectedChannelIndicator"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailInvalidAmount","expectedTransmissionDateTime"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailInvalidAmount","expectedVendorReferenceNo"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailInvalidAmount","expectedCtxResponseCode"),
                        testDataFactory.getTestData("CtxDataSource.json","ctxsuite","MTNPurchaseFailInvalidAmount","expectedFaultString")},


        };
    }

    @Step("CTX POST negative test cases")
    @Test(dataProvider = "CtxNegativeTestCases")
    public void CTXNegativeTests(
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



            startsim.SimulatorScenario("MTNNG DELAY 15000");


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
        Assert.assertEquals(getString("responseCode", document.getDocumentElement()), "2236");

        // CTX DB assertions
        /* Thread.sleep(5000);
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
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.tran_log WHERE clientTransactionId = " + "'" + clientTransactionId + "'", "systemResponseCode"), "0"); */


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
