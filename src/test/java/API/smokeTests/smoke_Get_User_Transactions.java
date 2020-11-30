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

//TECH-60241: 30065 :: user profile management :: smoke

@Listeners(allureApiTestListener.class)
public class smoke_Get_User_Transactions {

    // Create properties object in order to inject environment specific variables upon build
    Properties properties = configWrapper.loadPropertiesFile("config.properties");

    // Data staging for use in test
    @DataProvider(name = "GetUserTransactionsTestCase", parallel = true)
    public Object[] GetUserTransactionsTestData() throws IOException, ParseException {
        System.out.println("----- GetUserTransactionsTestCase -----");

        return new String[][]{

                {testDataFactory.getTestData("getUserTransactionsDataSource.json","getUserTransactionsTestSuite","successcase","userIdentifier"),
                        testDataFactory.getTestData("getUserTransactionsDataSource.json","getUserTransactionsTestSuite","successcase","clientId"),
                        testDataFactory.getTestData("getUserTransactionsDataSource.json","getUserTransactionsTestSuite","successcase","expectedChannelId"),
                        testDataFactory.getTestData("getUserTransactionsDataSource.json","getUserTransactionsTestSuite","successcase","expectedClientId"),
                        testDataFactory.getTestData("getUserTransactionsDataSource.json","getUserTransactionsTestSuite","successcase","expectedProductId"),
                        testDataFactory.getTestData("getUserTransactionsDataSource.json","getUserTransactionsTestSuite","successcase","expectedDescription"),
                        testDataFactory.getTestData("getUserTransactionsDataSource.json","getUserTransactionsTestSuite","successcase","expectedPurchaseAmount"),
                        testDataFactory.getTestData("getUserTransactionsDataSource.json","getUserTransactionsTestSuite","successcase","expectedTargetIdentifier"),
                        testDataFactory.getTestData("getUserTransactionsDataSource.json","getUserTransactionsTestSuite","successcase","expectedHTTPResponseCode"),
                },
        };
    }

    @Step("Get User Transactions Success")
    @Test(dataProvider = "GetUserTransactionsTestCase")
    public void GetUserTransactionsSuccessTest(String userIdentifier,
                                         String clientId,
                                         String expectedChannelId,
                                         String expectedClientId,
                                         String expectedProductId,
                                         String expectedDescription,
                                         String expectedPurchaseAmount,
                                         String expectedTargetIdentifier,
                                         String expectedHTTPResponseCode){

        // User Transactions GET method call
        Response getUserTransactionsResponse =
                given()
                        .param("userIdentifier", userIdentifier)
                        .param("clientId", clientId)
                        .when()
                        .get(properties.getProperty("QA_MINION") + ":" + properties.getProperty("CORE_Get_User_Transactions_RequestSpec_Port") + properties.getProperty("CORE_Get_User_Transactions_RequestSpec_BasePath"))
                        .then()
                        .extract()
                        .response();

        //System.out.println("getUserTransactionsResponse: " + getUserTransactionsResponse.toString());
        System.out.println("raasTxnRef for GetUserTransactionsSuccessTest: " + getUserTransactionsResponse.path("raasTxnRef").toString());

        // assertions: Request / input values
        Assert.assertNotEquals(getUserTransactionsResponse.path("clientId"), "");
        Assert.assertNotEquals(getUserTransactionsResponse.path("clientId"), "null");
        Assert.assertNotEquals(getUserTransactionsResponse.path("userIdentifier"), "");
        Assert.assertNotEquals(getUserTransactionsResponse.path("userIdentifier"), "null");

        // assertions:
        Assert.assertEquals(getUserTransactionsResponse.statusCode(), Integer.parseInt(expectedHTTPResponseCode));
        Assert.assertNotNull(getUserTransactionsResponse.path("channelId"), expectedChannelId);
        Assert.assertNotNull(getUserTransactionsResponse.path("clientId"), expectedClientId);
        Assert.assertNotNull(getUserTransactionsResponse.path("productId"), expectedProductId);
        Assert.assertNotNull(getUserTransactionsResponse.path("description"), expectedDescription);
        Assert.assertNotNull(getUserTransactionsResponse.path("purchaseAmount"), expectedPurchaseAmount);
        Assert.assertNotNull(getUserTransactionsResponse.path("targetIdentifier"), expectedTargetIdentifier);
        Assert.assertNotNull(getUserTransactionsResponse.path("raasTxnRef"));
        Assert.assertNotNull(getUserTransactionsResponse.path("transactionDate"));
    }
}
