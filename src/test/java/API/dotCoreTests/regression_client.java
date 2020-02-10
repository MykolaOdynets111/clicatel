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
                                  ) throws IOException {

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

        // Data stage conversions
        String rnd = randomnumbers.toString();
        Integer country = Integer.parseInt(countryCode);
        Integer ctx = 1;
        Integer control = 2;
        Integer core = 3;
        Integer timezone = Integer.parseInt(timezoneId);
        Boolean activeclient = Boolean.getBoolean(active);
        Boolean clientencryption =  Boolean.getBoolean(encryption);

        // Assertions

        // Client response assertions
        Assert.assertEquals(clientPostResponse.path("clientId"), randomnumbers);
        Assert.assertEquals(clientPostResponse.path("clickatellAccountId"), randomnumbers.toString());
        Assert.assertEquals(clientPostResponse.path("clientName"), clientName + randomnumbers.toString());
        Assert.assertEquals(clientPostResponse.path("countryCode"), country);
        Assert.assertEquals(clientPostResponse.path("timezoneId"), timezone);
        Assert.assertEquals(clientPostResponse.path("active"), activeclient);
        Assert.assertEquals(clientPostResponse.path("properties.whatsapp.integrationId"), integrationId);
        Assert.assertEquals(clientPostResponse.path("properties.whatsapp.apiKey"), apiKey);
        Assert.assertEquals(clientPostResponse.path("properties.whatsapp.encryption"), clientencryption);
        Assert.assertEquals(clientPostResponse.path("properties.whatsapp.encryptionKeys.inbound"), inbound);
        Assert.assertEquals(clientPostResponse.path("properties.whatsapp.encryptionKeys.outbound"), outbound);
        Assert.assertEquals(clientPostResponse.path("clickatellSystems[0].id"), ctx);
        Assert.assertEquals(clientPostResponse.path("clickatellSystems[0].name"), "CTX");
        Assert.assertEquals(clientPostResponse.path("clickatellSystems[1].id"), control);
        Assert.assertEquals(clientPostResponse.path("clickatellSystems[1].name"), ".CONTROL");
        Assert.assertEquals(clientPostResponse.path("clickatellSystems[2].id"), core);
        Assert.assertEquals(clientPostResponse.path("clickatellSystems[2].name"), ".CORE");


        // Client DB assertions
        // Raas.payd_common

        Assert.assertEquals(sqlDataAccess.verifyPostgreDb("payd_common.client", "client_Id", "=", clientPostResponse.path("clientId").toString()), clientPostResponse.path("clientId").toString());

        // cpgtx.client
        Assert.assertEquals(sqlDataAccess.verifyMySQLCustomSql("SELECT * FROM cpgtx.client WHERE id = " + "'" + clientPostResponse.path("clientId") + "'", "id"), rnd);
    }



}
