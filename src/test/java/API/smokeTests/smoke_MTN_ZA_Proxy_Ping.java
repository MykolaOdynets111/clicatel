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

//TECH-60273: 30785 :: MTN_ZA Proxy :: mtn za proxy :: smoke

@Listeners(allureApiTestListener.class)
public class smoke_MTN_ZA_Proxy_Ping {

    // Create properties object in order to inject environment specific variables upon build
    Properties properties = configWrapper.loadPropertiesFile("config.properties");

    // Data staging for use in test
    @DataProvider(name = "MTN_ZAProxyPingTestCase", parallel = true)
    public Object[] MTN_ZAProxyPingTestData() throws IOException, ParseException {
        System.out.println("----- MTN_ZAProxyPingTestCase -----");

        return new String[][]{
                {testDataFactory.getTestData("MTN_ZAProxyPingDataSource.json","MTN_ZAProxyPingTestSuite","successcase","vendorId"),
                testDataFactory.getTestData("MTN_ZAProxyPingDataSource.json","MTN_ZAProxyPingTestSuite","successcase","expectedHTTPResponseCode"),
                },
        };
    }

    @Step("MTN_ZA Proxy Ping Success")
    @Test(dataProvider = "MTN_ZAProxyPingTestCase")
    public void MTN_ZAProxyPingSuccessTest(String vendorId,
                                           String expectedHTTPResponseCode){

        // MTN_ZA Proxy Ping GET method call
        Response MTN_ZAProxyPingResponse =
                given()
                        .contentType(ContentType.JSON)
                        .body(vendorId)
                        .when()
                        .post(properties.getProperty("QA_MINION") + ":" + properties.getProperty("CORE_MTN_ZA_Proxy_Ping_RequestSpec_Port") + properties.getProperty("CORE_MTN_ZA_Proxy_Ping_RequestSpec_BasePath"))
                        .then()
                        .extract()
                        .response();

        // assertions:
        Assert.assertEquals(MTN_ZAProxyPingResponse.statusCode(), Integer.parseInt(expectedHTTPResponseCode));
    }
}
