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

//TECH-60261: 31995 :: vendor config :: smoke

@Listeners(allureApiTestListener.class)
public class smoke_Get_Vendor_Config {

    // Create properties object in order to inject environment specific variables upon build
    Properties properties = configWrapper.loadPropertiesFile("config.properties");

    // Data staging for use in test
    @DataProvider(name = "GetVendorConfigTestCase", parallel = true)
    public Object[] GetVendorConfigTestData() throws IOException, ParseException {
        System.out.println("----- GetVendorConfigTestCase -----");

        return new String[][]{

                {testDataFactory.getTestData("getVendorConfigDataSource.json","getVendorConfigTestSuite","successcase","vendorId"),
                        testDataFactory.getTestData("getVendorConfigDataSource.json","getVendorConfigTestSuite","successcase","expectedName"),
                        testDataFactory.getTestData("getVendorConfigDataSource.json","getVendorConfigTestSuite","successcase","expectedId"),
                        testDataFactory.getTestData("getVendorConfigDataSource.json","getVendorConfigTestSuite","successcase","expectedActive"),
                        testDataFactory.getTestData("getVendorConfigDataSource.json","getVendorConfigTestSuite","successcase","expectedMaxRetries"),
                        testDataFactory.getTestData("getVendorConfigDataSource.json","getVendorConfigTestSuite","successcase","expectedRetryDelay"),
                        testDataFactory.getTestData("getVendorConfigDataSource.json","getVendorConfigTestSuite","successcase","expectedBackoff"),
                        testDataFactory.getTestData("getVendorConfigDataSource.json","getVendorConfigTestSuite","successcase","expectedRetryDelayMax"),
                        testDataFactory.getTestData("getVendorConfigDataSource.json","getVendorConfigTestSuite","successcase","expectedMaximumRequestsPerPeriod"),
                        testDataFactory.getTestData("getVendorConfigDataSource.json","getVendorConfigTestSuite","successcase","expectedRequestsPeriod"),
                        testDataFactory.getTestData("getVendorConfigDataSource.json","getVendorConfigTestSuite","successcase","expectedCurrencyId"),
                        testDataFactory.getTestData("getVendorConfigDataSource.json","getVendorConfigTestSuite","successcase","expectedHTTPResponseCode"),
                },
        };
    }

    @Step("Get Vendor Config Success")
    @Test(dataProvider = "GetVendorConfigTestCase")
    public void GetVendorConfigSuccessTest(String id,
                                         String expectedName,
                                         String expectedId,
                                         String expectedActive,
                                         String expectedMaxRetries,
                                         String expectedRetryDelay,
                                         String expectedBackoff,
                                         String expectedRetryDelayMax,
                                         String expectedMaximumRequestsPerPeriod,
                                         String expectedRequestsPeriod,
                                         String expectedCurrencyId,
                                         String expectedHTTPResponseCode){

        // Vendor Config GET method call
        Response getVendorConfigResponse =
                given()
                        .when()
                        .get(properties.getProperty("QA_MINION") + ":" + properties.getProperty("CORE_Get_Vendor_Config_RequestSpec_Port") + properties.getProperty("CORE_Get_Vendor_Config_RequestSpec_BasePath") + id)
                        .then()
                        .extract()
                        .response();

        // assertions: Request / input values
        Assert.assertNotEquals(getVendorConfigResponse.path("vendorId"), "");
        Assert.assertNotEquals(getVendorConfigResponse.path("vendorId"), "null");

        // assertions:
        Assert.assertEquals(getVendorConfigResponse.statusCode(), Integer.parseInt(expectedHTTPResponseCode));
        Assert.assertNotNull(getVendorConfigResponse.path("name"), expectedName);
        Assert.assertNotNull(getVendorConfigResponse.path("id"), expectedId);
        Assert.assertNotNull(getVendorConfigResponse.path("active"), expectedActive);
        Assert.assertNotNull(getVendorConfigResponse.path("maxRetries"), expectedMaxRetries);
        Assert.assertNotNull(getVendorConfigResponse.path("retryDelay"), expectedRetryDelay);
        Assert.assertNotNull(getVendorConfigResponse.path("backoff"), expectedBackoff);
        Assert.assertNotNull(getVendorConfigResponse.path("retryDelayMax"));
        Assert.assertNotNull(getVendorConfigResponse.path("maximumRequestsPerPeriod"));
        Assert.assertNotNull(getVendorConfigResponse.path("requestsPeriod"));
        Assert.assertNotNull(getVendorConfigResponse.path("currencyId"));
    }
}
