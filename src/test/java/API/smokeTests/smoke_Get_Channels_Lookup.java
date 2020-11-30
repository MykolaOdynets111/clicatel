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

//TECH-60246: 30254 :: channel lookup :: Smoke

@Listeners(allureApiTestListener.class)
public class smoke_Get_Channels_Lookup {

    // Create properties object in order to inject environment specific variables upon build
    Properties properties = configWrapper.loadPropertiesFile("config.properties");

    // Data staging for use in test
    @DataProvider(name = "GetChannelsLookupTestCase", parallel = true)
    public Object[] GetChannelsLookupTestData() throws IOException, ParseException {
        System.out.println("----- GetChannelsLookupTestCase -----");
        return new String[][]{

                {testDataFactory.getTestData("getChannelsLookupDataSource.json","getChannelsLookupTestSuite","successcase","expectedHttpResponseCode"),
                },
        };
    }

    @Step("Get Channels Lookup Success")
    @Test(dataProvider = "GetChannelsLookupTestCase")
    public void GetChannelsLookupSuccessTest(String expectedHttpResponseCode){

        // Channels Lookup GET method call
        Response getChannelsLookupResponse =
                given()
                        .when()
                        .get(properties.getProperty("QA_MINION") + ":" + properties.getProperty("CORE_Get_Channels_Lookup_RequestSpec_Port") + properties.getProperty("CORE_Get_Channels_Lookup_RequestSpec_BasePath"))
                        .then()
                        .extract()
                        .response();

        // assertions:
        Assert.assertEquals(getChannelsLookupResponse.statusCode(), Integer.parseInt(expectedHttpResponseCode));
        Assert.assertNotNull(getChannelsLookupResponse.body());
    }
}
