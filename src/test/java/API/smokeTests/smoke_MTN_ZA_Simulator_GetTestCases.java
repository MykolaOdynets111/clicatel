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

//TECH-60272: 31943 :: mtn za simulator :: MTN_ZA simulator :: smoke

@Listeners(allureApiTestListener.class)
public class smoke_MTN_ZA_Simulator_GetTestCases {

    // Create properties object in order to inject environment specific variables upon build
    Properties properties = configWrapper.loadPropertiesFile("config.properties");

    // Data staging for use in test
    @DataProvider(name = "MTN_ZASimulatorGetTestCasesTestCase", parallel = true)
    public Object[] MTN_ZASimulatorGetTestCasesTestData() throws IOException, ParseException {
        System.out.println("----- MTN_ZASimulatorGetTestCasesTestCase -----");

        return new String[][]{
                {testDataFactory.getTestData("MTN_ZASimulatorGetTestCasesDataSource.json","MTN_ZASimulatorGetTestCasesTestSuite","successcase","expectedHTTPResponseCode"),
                },
        };
    }

    @Step("MTN_ZA Simulator GetTestCases Success")
    @Test(dataProvider = "MTN_ZASimulatorGetTestCasesTestCase")
    public void MTN_ZASimulatorGetTestCasesSuccessTest(String expectedHTTPResponseCode){

        // MTN_ZA Simulator GetTestCases method call
        Response MTN_ZASimulatorGetTestCasesResponse =
                given()
                        .when()
                        .relaxedHTTPSValidation()
                        .get(properties.getProperty("QA_MINION_SECURE") + ":" + properties.getProperty("CORE_MTN_ZA_Simulator_GetTestCases_RequestSpec_Port") + properties.getProperty("CORE_MTN_ZA_Simulator_GetTestCases_RequestSpec_BasePath"))
                        .then()
                        .extract()
                        .response();

        // assertions:
        Assert.assertEquals(MTN_ZASimulatorGetTestCasesResponse.statusCode(), Integer.parseInt(expectedHTTPResponseCode));
    }
}
