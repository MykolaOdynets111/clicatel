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

//TECH-60236: 30091 :: Magtipon 3lineng controller :: smoke

@Listeners(allureApiTestListener.class)
public class smoke_Magtipon3lineng_Validate_Transaction {

    // Create properties object in order to inject environment specific variables upon build
    Properties properties = configWrapper.loadPropertiesFile("config.properties");

    // Data staging for use in test
    @DataProvider(name = "magtipon3linengValidateTransactionTestCase", parallel = true)
    public Object[] Magtipon3linengValidateTransactionTestData() throws IOException, ParseException {
        System.out.println("----- magtipon3linengValidateTransactionTestCase -----");

        return new String[][]{

                {testDataFactory.getTestData("Magtipon3linengValidateTransactionDataSource.json","Magtipon3linengValidateTransactionTestSuite","successcase","body"),
                        testDataFactory.getTestData("Magtipon3linengValidateTransactionDataSource.json","Magtipon3linengValidateTransactionTestSuite","successcase","expectedCustomerName"),
                        testDataFactory.getTestData("Magtipon3linengValidateTransactionDataSource.json","Magtipon3linengValidateTransactionTestSuite","successcase","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("Magtipon3linengValidateTransactionDataSource.json","Magtipon3linengValidateTransactionTestSuite","successcase","expectedResponseCode"),
                        testDataFactory.getTestData("Magtipon3linengValidateTransactionDataSource.json","Magtipon3linengValidateTransactionTestSuite","successcase","expectedResponseMessage"),
                },
        };
    }

    @Step("Magtipon 3line NG Validate Transaction Success")
    @Test(dataProvider = "magtipon3linengValidateTransactionTestCase")
    public void Magtipon3linengValidateTransactionSuccessTest(
                                         String body,
                                         String expectedCustomerName,
                                         String expectedHTTPResponseCode,
                                         String expectedResponseCode,
                                         String expectedResponseMessage){

        // Magtipon 3line NG Validate Transaction POST method call
        Response Magtipon3linengValidateTransactionResponse =
                given()
                        .contentType(ContentType.JSON)
                        .body(body)
                        .when()
                        .post(properties.getProperty("QA_MINION") + ":" + properties.getProperty("CORE_3line_Validate_Transaction_RequestSpec_Port") + properties.getProperty("CORE_3line_Validate_Transaction_RequestSpec_BasePath"))
                        .then()
                        .extract()
                        .response();

        // assertions: Request / input values
        Assert.assertNotEquals(Magtipon3linengValidateTransactionResponse.path("clientId"), "");
        Assert.assertNotEquals(Magtipon3linengValidateTransactionResponse.path("clientId"), "null");
        Assert.assertNotEquals(Magtipon3linengValidateTransactionResponse.path("productId"), "");
        Assert.assertNotEquals(Magtipon3linengValidateTransactionResponse.path("productId"), "null");
        Assert.assertNotEquals(Magtipon3linengValidateTransactionResponse.path("purchaseAmount"), "");
        Assert.assertNotEquals(Magtipon3linengValidateTransactionResponse.path("purchaseAmount"), "null");
        Assert.assertNotEquals(Magtipon3linengValidateTransactionResponse.path("targetIdentifier"), "");
        Assert.assertNotEquals(Magtipon3linengValidateTransactionResponse.path("targetIdentifier"), "null");

        // assertions:
        Assert.assertEquals(Magtipon3linengValidateTransactionResponse.statusCode(), Integer.parseInt(expectedHTTPResponseCode));
        Assert.assertEquals(Magtipon3linengValidateTransactionResponse.path("customerName"), expectedCustomerName);
        Assert.assertNotNull(Magtipon3linengValidateTransactionResponse.path("customerInfo"));
        Assert.assertEquals(Magtipon3linengValidateTransactionResponse.path("responseCode"), expectedResponseCode);
        Assert.assertEquals(Magtipon3linengValidateTransactionResponse.path("responseMessage"), expectedResponseMessage);
    }
}
