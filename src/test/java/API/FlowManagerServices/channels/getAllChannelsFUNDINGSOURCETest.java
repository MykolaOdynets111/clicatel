package API.FlowManagerServices.channels;

import api.testUtilities.testConfig;
import org.testng.annotations.Test;
import api.testUtilities.authentication.keyCloakProvider;

import static io.restassured.RestAssured.given;


public class getAllChannelsFUNDINGSOURCETest extends testConfig {

    @Test
    public void getAllChannelsFUNDINGSOURCESTest() {

        keyCloakProvider keyCloakProvider = new keyCloakProvider();
        String key = keyCloakProvider.getAccessToken();
        String header = "Bearer " + key;

        given()
                .spec(FM_getChannels_FUNDING_SOURCE)
                .header("Authorization", header)
                .log().ifValidationFails()
                .when()
                .get()
                .then()
                .log().ifValidationFails()
                .assertThat()
                .statusCode(200);


    }
}

