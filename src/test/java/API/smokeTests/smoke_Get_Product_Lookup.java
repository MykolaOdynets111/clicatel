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

//TECH-60228: 32000 :: Product Lookup :: Smoke

@Listeners(allureApiTestListener.class)
public class smoke_Get_Product_Lookup {

    // Create properties object in order to inject environment specific variables upon build
    Properties properties = configWrapper.loadPropertiesFile("config.properties");

    // Data staging for use in test
    @DataProvider(name = "GetProductLookupTestCase", parallel = true)
    public Object[] GetProductLookupTestData() throws IOException, ParseException {
        System.out.println("----- GetProductLookupTestCase -----");
        return new String[][]{

                {testDataFactory.getTestData("getProductLookupDataSource.json","getProductLookupTestSuite","successcase","clientId"),
                        testDataFactory.getTestData("getProductLookupDataSource.json","getProductLookupTestSuite","successcase","productId"),
                        testDataFactory.getTestData("getProductLookupDataSource.json","getProductLookupTestSuite","successcase","expectedHttpResponseCode"),
                },
        };
    }

    @Step("Get Product Lookup Success")
    @Test(dataProvider = "GetProductLookupTestCase")
    public void GetProductLookupSuccessTest(String clientId,
                                         String productId,
                                         String expectedHttpResponseCode){

        // Product Lookup GET method call
        Response getProductLookupResponse =
                given()
                        .param("clientId", clientId)
                        .param("productId", productId)
                        .when()
                        .get(properties.getProperty("QA_MINION") + ":" + properties.getProperty("CORE_Get_Product_Lookup_RequestSpec_Port") + properties.getProperty("CORE_Get_Product_Lookup_RequestSpec_BasePath"))
                        .then()
                        .extract()
                        .response();

        // assertions: Request / input values
        Assert.assertNotEquals(getProductLookupResponse.path("clientId"), "");
        Assert.assertNotEquals(getProductLookupResponse.path("clientId"), "null");
        Assert.assertNotEquals(getProductLookupResponse.path("productId"), "");
        Assert.assertNotEquals(getProductLookupResponse.path("productId"), "null");

        // assertions:
        Assert.assertEquals(getProductLookupResponse.statusCode(), Integer.parseInt(expectedHttpResponseCode));
    }
}
