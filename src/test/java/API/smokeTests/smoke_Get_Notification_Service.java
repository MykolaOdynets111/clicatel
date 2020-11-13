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

//TECH-60243: 30116 :: message-notifier :: DDD :: smoke

@Listeners(allureApiTestListener.class)
public class smoke_Get_Notification_Service {

    // Create properties object in order to inject environment specific variables upon build
    Properties properties = configWrapper.loadPropertiesFile("config.properties");

    // Data staging for use in test
    @DataProvider(name = "GetNotificationServiceTestCase", parallel = true)
    public Object[] GetNotificationServiceTestData() throws IOException, ParseException {
        System.out.println("----- GetNotificationServiceTestCase -----");

        return new String[][]{
                {testDataFactory.getTestData("getNotificationServiceDataSource.json","getNotificationServiceTestSuite","successcase","channelId"),
                        testDataFactory.getTestData("getNotificationServiceDataSource.json","getNotificationServiceTestSuite","successcase","expectedHTTPResponseCode"),
                },
        };
    }

    @Step("Get Notification Service Success")
    @Test(dataProvider = "GetNotificationServiceTestCase")
    public void GetNotificationServiceSuccessTest(String channelId,
                                         String expectedHTTPResponseCode){

        // Notification Service GET method call
        Response getNotificationServiceResponse =
                given()
                        .param("channelId", channelId)
                        .when()
                        .get(properties.getProperty("QA_MINION") + ":" + properties.getProperty("CORE_Get_Notification_Service_RequestSpec_Port") + properties.getProperty("CORE_Get_Notification_Service_RequestSpec_BasePath"))
                        .then()
                        .extract()
                        .response();

        // assertions: Request / input values
        Assert.assertNotEquals(getNotificationServiceResponse.path("channelId"), "");
        Assert.assertNotEquals(getNotificationServiceResponse.path("channelId"), "null");

        // assertions:
        Assert.assertEquals(getNotificationServiceResponse.statusCode(), Integer.parseInt(expectedHTTPResponseCode));
    }
}
