package API.dotCoreTests;

import api.testUtilities.dataBuilders.RandomCharGenerator;
import api.testUtilities.sqlDataAccessLayer.sqlDataAccess;
import com.google.gson.JsonObject;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import api.requestLibary.CORE.corePostClientPOJO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import api.testUtilities.dataBuilders.testDataFactory;

import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

import api.testUtilities.propertyConfigWrapper.configWrapper;

import javax.sql.DataSource;

public class regression_client {

    // Create properties object in order to inject environment specific variables upon build
    Properties clientProperties = configWrapper.loadPropertiesFile("config.properties");

    // Random number for Id generation
    Integer randomnumbers = Integer.parseInt(RandomCharGenerator.getRandomNumbers(10001));

    @DataProvider(name = "successPOSTClientTestCases", parallel = true)
    public Object[] successPOSTClientTestData() throws IOException, ParseException {

        return new String[][]{

                //Client successful post test cases
                {testDataFactory.getTestData("ClientDataSource.json","clienttestsuite","successcase1createNewZAClient","active"),
                        testDataFactory.getTestData("ClientDataSource.json","clienttestsuite","successcase1createNewZAClient","clientName"),
                        testDataFactory.getTestData("ClientDataSource.json","clienttestsuite","successcase1createNewZAClient","countryCode"),
                        testDataFactory.getTestData("ClientDataSource.json","clienttestsuite","successcase1createNewZAClient","ctxLimitTotal"),
                        testDataFactory.getTestData("ClientDataSource.json","clienttestsuite","successcase1createNewZAClient","apiKey"),
                        testDataFactory.getTestData("ClientDataSource.json","clienttestsuite","successcase1createNewZAClient","encryption"),
                        testDataFactory.getTestData("ClientDataSource.json","clienttestsuite","successcase1createNewZAClient","inbound"),
                        testDataFactory.getTestData("ClientDataSource.json","clienttestsuite","successcase1createNewZAClient","outbound"),
                        testDataFactory.getTestData("ClientDataSource.json","clienttestsuite","successcase1createNewZAClient","integrationId"),
                        testDataFactory.getTestData("ClientDataSource.json","clienttestsuite","successcase1createNewZAClient","timezoneId")
                },

        };
    }

    @Test(dataProvider = "successPOSTClientTestCases")
    public void successPOSTClient(String active,
                                  String clientName,
                                  String countryCode,
                                  String ctxLimitTotal,
                                  String apiKey,
                                  String encryption,
                                  String inbound,
                                  String outbound,
                                  String integrationId,
                                  String timezoneId
                                  ){

        JSONObject clientPostPayload = new JSONObject();
        clientPostPayload.put("active", Boolean.getBoolean(active));
        clientPostPayload.put("clickatellAccountId", randomnumbers);
        JSONArray clickatellSystem = new JSONArray();
        clickatellSystem.add(1);
        clickatellSystem.add(2);
        clickatellSystem.add(3);
        clientPostPayload.put("clickatellSystem", clickatellSystem);
        clientPostPayload.put("clientId", randomnumbers);
        clientPostPayload.put("clientName", clientName + randomnumbers);
        clientPostPayload.put("countryCode", Integer.parseInt(countryCode));
        clientPostPayload.put("ctxLimitTotal", Integer.parseInt(ctxLimitTotal));
        JSONObject properties = new JSONObject();
        JSONObject whatsapp = new JSONObject();
        whatsapp.put("apiKey", apiKey);
        whatsapp.put("encryption", Boolean.getBoolean(encryption));
        JSONObject encryptionKeys = new JSONObject();
        encryptionKeys.put("inbound", inbound);
        encryptionKeys.put("outbound", outbound);
        whatsapp.put("encryptionKeys", encryptionKeys);
        whatsapp.put("integrationId",integrationId);
        properties.put("whatsapp", whatsapp);
        clientPostPayload.put("properties", properties);
        clientPostPayload.put("timezoneId", Integer.parseInt(timezoneId));

        //System.out.println(clientPostPayload);

        // Create client payload pojo object
        /*corePostClientPOJO clientPOSTPayload = new corePostClientPOJO(active,
                clickatellAccountId,
                clickatellSystem,
                clientId,
                clientName,
                countryCode,
                ctxLimitTotal,
                properties,
                timezoneId);*/

        // Act - Create client POST response object
        Response clientPostResponse =
                given()
                        .contentType(ContentType.JSON)
                        .body(clientPostPayload)
                        .when()
                        .post(clientProperties.getProperty("QA_MINION") + ":" + clientProperties.getProperty("CORE_Client_Port") + clientProperties.getProperty("CORE_Client_BasePath"))
                        .then()
                        .extract()
                        .response();

        // Assertions

        // Client response assertions
        Assert.assertEquals(clientPostResponse.path("clientId"), randomnumbers);


    }


}
