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

//TECH-60248: 30309 :: Financial Terms Lookup :: smoke

@Listeners(allureApiTestListener.class)
public class smoke_Financial_Terms_Lookup {

    // Create properties object in order to inject environment specific variables upon build
    Properties properties = configWrapper.loadPropertiesFile("config.properties");

    // Data staging for use in test
    @DataProvider(name = "FinancialTermsLookupTestCase", parallel = true)
    public Object[] FinancialTermsLookupTestData() throws IOException, ParseException {
        System.out.println("----- FinancialTermsLookupTestCase -----");
        return new String[][]{

                {testDataFactory.getTestData("financialTermsLookupDataSource.json","financialTermsLookupTestSuite","successcase","clientId"),
                        testDataFactory.getTestData("financialTermsLookupDataSource.json","financialTermsLookupTestSuite","successcase","productId"),
                        testDataFactory.getTestData("financialTermsLookupDataSource.json","financialTermsLookupTestSuite","successcase","purchaseAmount"),
                        testDataFactory.getTestData("financialTermsLookupDataSource.json","financialTermsLookupTestSuite","successcase","expectedHttpResponseCode"),
                },
        };
    }

    @Step("Financial Terms Lookup Success")
    @Test(dataProvider = "FinancialTermsLookupTestCase")
    public void FinancialTermsLookupSuccessTest(String clientId,
                                            String productId,
                                            String purchaseAmount,
                                            String expectedHttpResponseCode){

        // Financial Terms GET method call
        Response financialTermsLookupResponse =
                given()
                        .param("clientId", clientId)
                        .param("productId", productId)
                        .param("purchaseAmount", purchaseAmount)
                        .when()
                        .get(properties.getProperty("QA_MINION") + ":" + properties.getProperty("CORE_Financial_Terms_Lookup_RequestSpec_Port") + properties.getProperty("CORE_Financial_Terms_Lookup_RequestSpec_BasePath"))
                        .then()
                        .extract()
                        .response();

        // assertions: Request / input values
        Assert.assertNotEquals(financialTermsLookupResponse.path("clientId"), "");
        Assert.assertNotEquals(financialTermsLookupResponse.path("clientId"), "null");
        Assert.assertNotEquals(financialTermsLookupResponse.path("productId"), "");
        Assert.assertNotEquals(financialTermsLookupResponse.path("productId"), "null");
        Assert.assertNotEquals(financialTermsLookupResponse.path("purchaseAmount"), "");
        Assert.assertNotEquals(financialTermsLookupResponse.path("purchaseAmount"), "null");

        // assertions:
        Assert.assertEquals(financialTermsLookupResponse.statusCode(), Integer.parseInt(expectedHttpResponseCode));
        Assert.assertNotNull(financialTermsLookupResponse.path("clientId"));
        Assert.assertNotNull(financialTermsLookupResponse.path("productId"));
        Assert.assertNotNull(financialTermsLookupResponse.path("purchaseAmount"));
    }
}
