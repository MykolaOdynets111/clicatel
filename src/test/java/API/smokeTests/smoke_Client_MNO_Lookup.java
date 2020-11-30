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

//TECH-60233: 30049 :: mno/mnp lookup :: smoke

@Listeners(allureApiTestListener.class)
public class smoke_Client_MNO_Lookup {

    // Create properties object in order to inject environment specific variables upon build
    Properties properties = configWrapper.loadPropertiesFile("config.properties");

    // Data staging for use in test
    @DataProvider(name = "ClientMNOLookupTestCase", parallel = true)
    public Object[] GetClientMNOLookupTestData() throws IOException, ParseException {
        System.out.println("----- ClientMNOLookupTestCase -----");
        return new String[][]{

                {testDataFactory.getTestData("clientMNOLookupDataSource.json","clientMNOLookupTestSuite","successcase","clientId"),
                        testDataFactory.getTestData("clientMNOLookupDataSource.json","clientMNOLookupTestSuite","successcase","msisdn"),
                        testDataFactory.getTestData("clientMNOLookupDataSource.json","clientMNOLookupTestSuite","successcase","countryCallingCode"),
                        testDataFactory.getTestData("clientMNOLookupDataSource.json","clientMNOLookupTestSuite","successcase","expectedMsisdn"),
                        testDataFactory.getTestData("clientMNOLookupDataSource.json","clientMNOLookupTestSuite","successcase","expectedCountryCallingCode"),
                        testDataFactory.getTestData("clientMNOLookupDataSource.json","clientMNOLookupTestSuite","successcase","expectedOperatorCode"),
                        testDataFactory.getTestData("clientMNOLookupDataSource.json","clientMNOLookupTestSuite","successcase","expectedOperatorName"),
                        testDataFactory.getTestData("clientMNOLookupDataSource.json","clientMNOLookupTestSuite","successcase","expectedResponseCode"),
                        testDataFactory.getTestData("clientMNOLookupDataSource.json","clientMNOLookupTestSuite","successcase","expectedResponseMessage"),
                },
        };
    }

    @Step("Client MNO Lookup Success")
    @Test(dataProvider = "ClientMNOLookupTestCase")
    public void ClientMNOLookupSuccessTest(String clientId,
                                         String msisdn,
                                         String countryCallingCode,
                                         String expectedMsisdn,
                                         String expectedCountryCallingCode,
                                         String expectedOperatorCode,
                                         String expectedOperatorName,
                                         String expectedResponseCode,
                                         String expectedResponseMessage){

        // Client MNO Lookup GET method call
        Response clientMNOLookupResponse =
                given()
                        .param("clientId", clientId)
                        .param("msisdn", msisdn)
                        .param("countryCallingCode", countryCallingCode)
                        .when()
                        .get(properties.getProperty("QA_MINION") + ":" + properties.getProperty("CORE_Client_MNO_Lookup_RequestSpec_Port") + properties.getProperty("CORE_Client_MNO_Lookup_RequestSpec_BasePath"))
                                //+ "?clientId="
                                //+ clientId + "&msisdn="
                                //+ msisdn
                                //+ "&countryCallingCode="
                                //+ countryCallingCode)
                        .then()
                        .extract()
                        .response();

//System.out.println("clientMNOLookupResponse: " + clientMNOLookupResponse.toString());

        // assertions: Request / input values
        Assert.assertNotEquals(clientMNOLookupResponse.path("clientId"), "");
        Assert.assertNotEquals(clientMNOLookupResponse.path("clientId"), "null");
        Assert.assertNotEquals(clientMNOLookupResponse.path("msisdn"), "");
        Assert.assertNotEquals(clientMNOLookupResponse.path("msisdn"), "null");
        Assert.assertNotEquals(clientMNOLookupResponse.path("countryCallingCode"), "");
        Assert.assertNotEquals(clientMNOLookupResponse.path("countryCallingCode"), "null");

        // assertions:
        Assert.assertEquals(clientMNOLookupResponse.statusCode(), Integer.parseInt("200"));
        Assert.assertEquals(clientMNOLookupResponse.path("msisdn"), expectedMsisdn);
        Assert.assertEquals(clientMNOLookupResponse.path("countryCallingCode"), expectedCountryCallingCode);
        Assert.assertEquals(clientMNOLookupResponse.path("operatorCode"), expectedOperatorCode);
        Assert.assertEquals(clientMNOLookupResponse.path("operatorName"), expectedOperatorName);
        Assert.assertEquals(clientMNOLookupResponse.path("responseCode"), expectedResponseCode);
        Assert.assertEquals(clientMNOLookupResponse.path("responseMessage"), expectedResponseMessage);
    }
}
