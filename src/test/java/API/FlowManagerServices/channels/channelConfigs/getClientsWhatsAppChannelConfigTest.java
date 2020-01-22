package API.FlowManagerServices.channels.channelConfigs;

import api.testUtilities.testConfig;
import org.testng.annotations.Test;
import api.testUtilities.authentication.keyCloakProvider;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class getClientsWhatsAppChannelConfigTest extends testConfig {


    @Test
    public void getClientsWhatsAppChannelConfigTest() {

        keyCloakProvider keyCloakProvider = new keyCloakProvider();
        String key = keyCloakProvider.getAccessToken();
        String header = "Bearer " + key;

        given()
                .spec(FM_getChannelConfig_CLIENT)
                .header("Authorization", header)
                .log().ifValidationFails()
                .when()
                .get("/8")
                .then()
                .log().ifValidationFails()
                .assertThat()
                .body("channel-name", equalTo("WhatsApp"))
                .body("channel-id",equalTo(8))
                .statusCode(200);


    }


}
