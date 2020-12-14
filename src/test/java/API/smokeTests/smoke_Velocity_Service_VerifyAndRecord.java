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

//TECH-60245: 30156 :: Velocity Service :: smoke

@Listeners(allureApiTestListener.class)
public class smoke_Velocity_Service_VerifyAndRecord extends testConfig {

    // Create properties object in order to inject environment specific variables upon build
    Properties properties = configWrapper.loadPropertiesFile("config.properties");

    // Data staging for use in test
    @DataProvider(name = "VelocityServiceVerifyAndRecordTestCase", parallel = true)
    public Object[] VelocityServiceVerifyAndRecordTestData() throws IOException, ParseException {
        System.out.println("----- VelocityServiceVerifyAndRecordTestCase -----");
        return new String[][]{

                {testDataFactory.getTestData("velocityServiceVerifyAndRecordDataSource.json","velocityServiceVerifyAndRecordTestSuite","successcase","fundingSourceId"),
                        testDataFactory.getTestData("velocityServiceVerifyAndRecordDataSource.json","velocityServiceVerifyAndRecordTestSuite","successcase","purchaseAmount"),
                        testDataFactory.getTestData("velocityServiceVerifyAndRecordDataSource.json","velocityServiceVerifyAndRecordTestSuite","successcase","sourceMsisdn"),
                        testDataFactory.getTestData("velocityServiceVerifyAndRecordDataSource.json","velocityServiceVerifyAndRecordTestSuite","successcase","transactionId"),
                        testDataFactory.getTestData("velocityServiceVerifyAndRecordDataSource.json","velocityServiceVerifyAndRecordTestSuite","successcase","expectedHTTPResponseCode"),
                },
        };
    }

    @Step("Velocity Service VerifyAndRecord Success")
    @Test(dataProvider = "VelocityServiceVerifyAndRecordTestCase")
    public void VelocityServiceVerifyAndRecordSuccessTest(String fundingSourceId, String purchaseAmount,
                                                 String sourceMsisdn,
                                                 String transactionId,
                                                 String expectedHTTPResponseCode){

        // Delete existing test cases
        Response removeAllTestCases =
                given()
                        .contentType(ContentType.JSON)
                        .when()
                        .delete(properties.getProperty("QA_MINION") + ":" + properties.getProperty("CORE_Remove_All_Testcases_RequestSpec_Port") + properties.getProperty("CORE_Remove_All_Testcases_RequestSpec_BasePath"))
                        .then()
                        .extract()
                        .response();

        //Assert
        Assert.assertEquals(removeAllTestCases.statusCode(), Integer.parseInt(expectedHTTPResponseCode));

        // Validate Purchase Request POST method call :: Validate a user's account before purchase
        Response validatePurchaseRequestResponse =
                given()
                        .contentType(ContentType.JSON)
                        //.body(body)
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
        //Assert.assertEquals(validatePurchaseRequestResponse.path("status"), expectedStatus);
        //Assert.assertEquals(validatePurchaseRequestResponse.path("responseCode"), expectedResponseCode);
    }
}
