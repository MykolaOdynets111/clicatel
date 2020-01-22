package API.FlowManagerServices.flows;

import api.testUtilities.testConfig;
import org.testng.annotations.Test;
import api.testUtilities.authentication.keyCloakProvider;

import static io.restassured.RestAssured.given;

public class getVENDORFlowName extends testConfig {

    @Test
    public void getVendorFlowNameTest() {


        keyCloakProvider keyCloakProvider = new keyCloakProvider();
        String key = keyCloakProvider.getAccessToken();
        String header = "Bearer " + key;

        given()
                .spec(FM_getFlowName_VENDOR)
                .header("Authorization", header)
                .log().ifValidationFails()
                .when()
                .get("/nestedSetBehaviourField")
                .then()
                .log().ifValidationFails()
                .assertThat().statusCode(200);
    }
}
