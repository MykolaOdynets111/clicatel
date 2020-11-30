package API.smokeTests;

import api.testUtilities.dataBuilders.testDataFactory;
import api.testUtilities.propertyConfigWrapper.configWrapper;
import io.qameta.allure.Step;
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

//TECH-60270: 30113 :: 3line simulator :: smoke

@Listeners(allureApiTestListener.class)
public class smoke_3line_Simulator_Ping {

    // Create properties object in order to inject environment specific variables upon build
    Properties properties = configWrapper.loadPropertiesFile("config.properties");

    // Data staging for use in test
    @DataProvider(name = "3lineSimulatorPingTestCase", parallel = true)
    public Object[] Get3lineSimulatorPingTestData() throws IOException, ParseException {
        System.out.println("----- 3lineSimulatorPingTestCase -----");

        return new String[][]{

                {testDataFactory.getTestData("3lineSimulatorPingDataSource.json","3lineSimulatorPingTestSuite","successcase","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("3lineSimulatorPingDataSource.json","3lineSimulatorPingTestSuite","successcase","expectedHTTPResponseBody"),
                },
        };
    }

    @Step("3line Simulator Ping Success")
    @Test(dataProvider = "3lineSimulatorPingTestCase")
    public void Get3lineSimulatorPingSuccessTest(
                                         String expectedHTTPResponseCode,
                                         String expectedHTTPResponseBody){

        // 3line Simulator Ping GET method call
        Response get3lineSimulatorPingResponse =
                given()
                        .when()
                        .get(properties.getProperty("QA_MINION") + ":" + properties.getProperty("CORE_3line_Simulator_Ping_RequestSpec_Port") + properties.getProperty("CORE_3line_Simulator_Ping_RequestSpec_BasePath"))
                        .then()
                        .extract()
                        .response();

        // assertions:
        Assert.assertEquals(get3lineSimulatorPingResponse.statusCode(), Integer.parseInt(expectedHTTPResponseCode));
        Assert.assertNotNull(get3lineSimulatorPingResponse.body());
    }
}
