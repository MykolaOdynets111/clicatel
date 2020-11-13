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

//TECH-60265: 32051 :: MWM simulator :: mwm-simulator-controller :: smoke

@Listeners(allureApiTestListener.class)
public class smoke_MWM_Simulator_Delete {

    // Create properties object in order to inject environment specific variables upon build
    Properties properties = configWrapper.loadPropertiesFile("config.properties");

    // Data staging for use in test
    @DataProvider(name = "mwmSimulatorDeleteTestCase", parallel = true)
    public Object[] MWMSimulatorDeleteTestData() throws IOException, ParseException {
        System.out.println("----- mwmSimulatorDeleteTestCase -----");

        return new String[][]{
                {testDataFactory.getTestData("mwmSimulatorDeleteDataSource.json","mwmSimulatorDeleteTestSuite","successcase","expectedHTTPResponseCode"),
                },
        };
    }

    @Step("MWM Simulator Delete Success")
    @Test(dataProvider = "mwmSimulatorDeleteTestCase")
    public void MWMSimulatorDeleteSuccessTest(String expectedHTTPResponseCode){

        // MWM Simulator DELETE method call
        Response mwmSimulatorDeleteResponse =
                given()
                        .when()
                        .delete(properties.getProperty("QA_MINION") + ":" + properties.getProperty("CORE_MWM_Simulator_Delete_RequestSpec_Port") + properties.getProperty("CORE_MWM_Simulator_Delete_RequestSpec_BasePath"))
                        .then()
                        .extract()
                        .response();

        // assertions:
        Assert.assertEquals(mwmSimulatorDeleteResponse.statusCode(), Integer.parseInt(expectedHTTPResponseCode));
    }
}
