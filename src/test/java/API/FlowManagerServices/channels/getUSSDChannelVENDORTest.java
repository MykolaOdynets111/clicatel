package API.FlowManagerServices.channels;

import api.testUtilities.testConfig;
import org.testng.annotations.Test;
import api.testUtilities.authentication.keyCloakProvider;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class getUSSDChannelVENDORTest extends testConfig {
    @Test
    public void getChannelsByIDVENDORUSSDTest() {

        keyCloakProvider keyCloakProvider = new keyCloakProvider();
        String key = keyCloakProvider.getAccessToken();
        String header = "Bearer " + key;

        given()
                .spec(FM_getChannels_VENDOR)
                .header("Authorization", header)
                .log().ifValidationFails()
                .when()
                .get("/0")
                .then()
                .log().ifValidationFails()
                .assertThat()
                .body("config.channel-name", equalTo("DEFAULT")).statusCode(200);


    }

}

