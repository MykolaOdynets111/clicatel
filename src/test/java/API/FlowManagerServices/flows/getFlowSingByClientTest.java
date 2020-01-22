package API.FlowManagerServices.flows;

import api.testUtilities.testConfig;
import org.testng.annotations.Test;
import api.testUtilities.authentication.keyCloakProvider;

import static io.restassured.RestAssured.given;

public class getFlowSingByClientTest extends testConfig {


    @Test
    public void getFlowSingByClientTest() {
        //TODO Fix vlaidation
        keyCloakProvider keyCloakProvider = new keyCloakProvider();
        String key = keyCloakProvider.getAccessToken();
        String header = "Bearer " + key;
        given()
                .spec(FM_GetFlowSingle_CLIENTS)
                .header("Authorization", header)
                .log().ifValidationFails()
                .when()
                .get()
                .then()
                .log().ifValidationFails()
                .assertThat()
              //  .body("[264].name",equalTo("askForInputTest"))
                .statusCode(200);


    }
}
