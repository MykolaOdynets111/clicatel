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

//TECH-62214: 30117 :: Airtel Proxy :: smoke test

@Listeners(allureApiTestListener.class)
public class smoke_Airtel_Proxy_Ping {

    // Create properties object in order to inject environment specific variables upon build
    Properties properties = configWrapper.loadPropertiesFile("config.properties");

    // Data staging for use in test
    @DataProvider(name = "airtelProxyPingTestCase", parallel = true)
    public Object[] AirtelProxyPingTestData() throws IOException, ParseException {
        System.out.println("----- airtelProxyPingTestCase -----");

        return new String[][]{
                {testDataFactory.getTestData("airtelProxyPingDataSource.json","airtelProxyPingTestSuite","successcase","expectedHTTPResponseCode"),
                },
        };
    }

    @Step("Airtel Proxy Ping Success")
    @Test(dataProvider = "airtelProxyPingTestCase")
    public void AirtelProxyPingSuccessTest(String expectedHTTPResponseCode){

        // airtel Proxy Ping GET method call
        Response airtelProxyPingResponse =
                given()
                        .when()
                        .get(properties.getProperty("QA_MINION") + ":" + properties.getProperty("CORE_Airtel_Proxy_Ping_RequestSpec_Port") + properties.getProperty("CORE_Airtel_Proxy_Ping_RequestSpec_BasePath"))
                        .then()
                        .extract()
                        .response();

        // assertions:
        Assert.assertEquals(airtelProxyPingResponse.statusCode(), Integer.parseInt(expectedHTTPResponseCode));
    }
}
