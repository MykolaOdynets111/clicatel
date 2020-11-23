package API.smokeTests;

import api.testUtilities.dataBuilders.testDataFactory;
import api.testUtilities.propertyConfigWrapper.configWrapper;
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

//TECH-60266: 32050 :: Routing Service :: smoke

@Listeners(allureApiTestListener.class)
public class smoke_Routing_Service_Request {

    // Create properties object in order to inject environment specific variables upon build
    Properties properties = configWrapper.loadPropertiesFile("config.properties");

    // Data staging for use in test
    @DataProvider(name = "RoutingServiceRequestTestCase", parallel = true)
    public Object[] RoutingServiceRequestTestData() throws IOException, ParseException {
        System.out.println("----- RoutingServiceRequestTestCase -----");
        return new String[][]{

                {testDataFactory.getTestData("validatePurchaseRequestDataSource.json","validatePurchaseRequestTestSuite","successcase","body"),
                        testDataFactory.getTestData("validatePurchaseRequestDataSource.json","validatePurchaseRequestTestSuite","successcase","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("validatePurchaseRequestDataSource.json","validatePurchaseRequestTestSuite","successcase","expectedStatus"),
                        testDataFactory.getTestData("validatePurchaseRequestDataSource.json","validatePurchaseRequestTestSuite","successcase","expectedResponseCode"),
                },
        };
    }

    @Step("Routing Service Request Success")
    @Test(dataProvider = "RoutingServiceRequestTestCase")
    public void RoutingServiceRequestSuccessTest(String body,
                                         String expectedHTTPResponseCode,
                                         String expectedStatus,
                                         String expectedResponseCode){

        // Validate Purchase Request POST method call :: Validate a user's account before purchase
        Response validatePurchaseRequestResponse =
                given()
                        .contentType(ContentType.JSON)
                        .body(body)
                        .when()
                        .post(properties.getProperty("QA_MINION") + ":" + properties.getProperty("CORE_Routing_Service_Request_RequestSpec_Port") + properties.getProperty("CORE_Routing_Service_Request_RequestSpec_BasePath"))
                        .then()
                        .extract()
                        .response();

        // assertions: Request / input values
        Assert.assertNotEquals(validatePurchaseRequestResponse.path("clientId"), "");
        Assert.assertNotEquals(validatePurchaseRequestResponse.path("clientId"), "null");
        Assert.assertNotEquals(validatePurchaseRequestResponse.path("productId"), "");
        Assert.assertNotEquals(validatePurchaseRequestResponse.path("productId"), "null");
        Assert.assertNotEquals(validatePurchaseRequestResponse.path("purchaseAmount"), "");
        Assert.assertNotEquals(validatePurchaseRequestResponse.path("purchaseAmount"), "null");
        Assert.assertNotEquals(validatePurchaseRequestResponse.path("sourceIdentifier"), "");
        Assert.assertNotEquals(validatePurchaseRequestResponse.path("sourceIdentifier"), "null");
        Assert.assertNotEquals(validatePurchaseRequestResponse.path("targetIdentifier"), "");
        Assert.assertNotEquals(validatePurchaseRequestResponse.path("targetIdentifier"), "null");
        Assert.assertNotEquals(validatePurchaseRequestResponse.path("timestamp"), "");
        Assert.assertNotEquals(validatePurchaseRequestResponse.path("timestamp"), "null");

        // assertions:
        Assert.assertEquals(validatePurchaseRequestResponse.statusCode(), Integer.parseInt(expectedHTTPResponseCode));
        Assert.assertEquals(validatePurchaseRequestResponse.path("status"), expectedStatus);
        Assert.assertEquals(validatePurchaseRequestResponse.path("responseCode"), expectedResponseCode);
    }
}
