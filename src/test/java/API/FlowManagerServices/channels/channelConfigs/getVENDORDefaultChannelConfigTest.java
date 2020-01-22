package API.FlowManagerServices.channels.channelConfigs;

import api.testUtilities.testConfig;
import org.testng.annotations.Test;
import api.testUtilities.authentication.keyCloakProvider;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class getVENDORDefaultChannelConfigTest extends testConfig {

    @Test
    public void getVENDORWhatsAppChannelConfigTest() {

        keyCloakProvider keyCloakProvider = new keyCloakProvider();
        String key = keyCloakProvider.getAccessToken();
        String header = "Bearer " + key;

        given()
                .spec(FM_getChannelConfig_VENDOR)
                .header("Authorization", header)
                .log().ifValidationFails()
                .when()
                .get("/0")
                .then()
                .log().ifValidationFails()
                .assertThat()
                .body("channel-id",equalTo(0))
                .body("channel-name", equalTo("DEFAULT")).statusCode(200);


    }

}
