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

//TECH-60258: 30444 :: Transaction Lookup Portal :: smoke

@Listeners(allureApiTestListener.class)
public class smoke_Transaction_Lookup_Portal {
    // Create properties object in order to inject environment specific variables upon build
    Properties properties = configWrapper.loadPropertiesFile("config.properties");

    // Data staging for use in test
    @DataProvider(name = "TransactionLookupPortalTestCase", parallel = true)

    public Object[] TransactionLookupPortalTestData() throws IOException, ParseException {
        System.out.println("----- TransactionLookupPortalTestCase -----");
        return new String[][]{
                {testDataFactory.getTestData("transactionLookupPortalDataSource.json","transactionLookupPortalTestSuite","successcase","clientId"),
                        testDataFactory.getTestData("transactionLookupPortalDataSource.json","transactionLookupPortalTestSuite","successcase","currentPage"),
                        testDataFactory.getTestData("transactionLookupPortalDataSource.json","transactionLookupPortalTestSuite","successcase","dateRangeFrom"),
                        testDataFactory.getTestData("transactionLookupPortalDataSource.json","transactionLookupPortalTestSuite","successcase","dateRangeTo"),
                        testDataFactory.getTestData("transactionLookupPortalDataSource.json","transactionLookupPortalTestSuite","successcase","pageSize"),
                        testDataFactory.getTestData("transactionLookupPortalDataSource.json","transactionLookupPortalTestSuite","successcase","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("transactionLookupPortalDataSource.json","transactionLookupPortalTestSuite","successcase","expectedSolutionType"),
                        },
        };
    }

    @Step("Transaction Lookup Portal Success")
    @Test(dataProvider = "TransactionLookupPortalTestCase")
    public void TransactionLookupPortalSuccessTest(String clientId,
                                                   String currentPage,
                                                   String dateRangeFrom,
                                                   String dateRangeTo,
                                                   String pageSize,
                                                   String expectedHTTPResponseCode,
                                                   String expectedSolutionType){

        // GET request is sent for the transaction lookup
        Response transactionLookupPortalResponse =
                given()
                        .param("clientId", clientId)
                        .param("currentPage", currentPage)
                        .param("dateRangeFrom", dateRangeFrom)
                        .param("dateRangeTo", dateRangeTo)
                        .param("pageSize", pageSize)
                        .when()
                        .get(properties.getProperty("QA_MINION") + ":" + properties.getProperty("CORE_Transaction_Lookup_Portal_RequestSpec_Port") + properties.getProperty("CORE_Transaction_Lookup_Portal_RequestSpec_BasePath"))
                        .then()
                        .extract()
                        .response();

        // assertions: Request / input values
        Assert.assertNotEquals(transactionLookupPortalResponse.path("clientId"), "");
        Assert.assertNotEquals(transactionLookupPortalResponse.path("clientId"), "null");
        Assert.assertNotEquals(transactionLookupPortalResponse.path("currentPage"), "");
        Assert.assertNotEquals(transactionLookupPortalResponse.path("currentPage"), "null");
        Assert.assertNotEquals(transactionLookupPortalResponse.path("dateRangeFrom"), "");
        Assert.assertNotEquals(transactionLookupPortalResponse.path("dateRangeFrom"), "null");
        Assert.assertNotEquals(transactionLookupPortalResponse.path("dateRangeTo"), "");
        Assert.assertNotEquals(transactionLookupPortalResponse.path("dateRangeTo"), "null");
        Assert.assertNotEquals(transactionLookupPortalResponse.path("pageSize"), "");
        Assert.assertNotEquals(transactionLookupPortalResponse.path("pageSize"), "null");

        // assertions:
        Assert.assertEquals(transactionLookupPortalResponse.statusCode(), Integer.parseInt(expectedHTTPResponseCode));
        Assert.assertNotNull(transactionLookupPortalResponse.path("pageSize"));
        Assert.assertNotNull(transactionLookupPortalResponse.path("currentPage"));
        Assert.assertEquals(transactionLookupPortalResponse.path("solutionType"), expectedSolutionType);
        Assert.assertNotNull(transactionLookupPortalResponse.path("transactionLookupResponse"));
    }
}