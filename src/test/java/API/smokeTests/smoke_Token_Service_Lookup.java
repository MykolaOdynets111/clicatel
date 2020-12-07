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

//TECH-60260: 31926 :: Token Service :: smoke

@Listeners(allureApiTestListener.class)
public class smoke_Token_Service_Lookup {
    smoke_Reserve_and_Transact_V4 transaction = new smoke_Reserve_and_Transact_V4();

    // Create properties object in order to inject environment specific variables upon build
    Properties properties = configWrapper.loadPropertiesFile("config.properties");

    // Data staging for use in test
    @DataProvider(name = "TokenServiceLookupTestCase", parallel = true)

    public Object[] TokenServiceLookupTestData() throws IOException, ParseException {
        System.out.println("----- TokenServiceLookupTestCase -----");
        return new String[][]{
                {testDataFactory.getTestData("tokenServiceLookupDataSource.json","tokenServiceLookupTestSuite","successcase","expectedHTTPResponseCode"),
                },
        };
    }

    @Step("Token Service Lookup Success")
    @Test(dataProvider = "TokenServiceLookupTestCase")
    public void TokenServiceLookupSuccessTest(String expectedHTTPResponseCode){
        //Perform R&Tv4 transaction
        //transaction.reserveAndTransactV4SuccessSmokeTestcase();
        //System.out.println("----- transaction -----: " + transaction);

        //hardcoding for now
        String raasTxnRef = "p4y272nwj5dmrnsoiqd7njg6";

        // GET request is sent for the token transaction
        Response tokenServiceLookupResponse =
                given()
                        .contentType(ContentType.JSON)
                        .param("raasTxnRef", raasTxnRef)
                        .when()
                        .get(properties.getProperty("QA_MINION") + ":" + properties.getProperty("CORE_Token_Service_Lookup_RequestSpec_Port") + properties.getProperty("CORE_Token_Service_Lookup_RequestSpec_BasePath"))
                        .then()
                        .extract()
                        .response();

        // assertions:
        Assert.assertEquals(tokenServiceLookupResponse.statusCode(), Integer.parseInt(expectedHTTPResponseCode));
        Assert.assertNotNull(tokenServiceLookupResponse.path("raasTxnRef"));

    }
}
