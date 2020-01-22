package API.FlowManagerServices.flows;

import api.testUtilities.testConfig;
import org.testng.annotations.Test;
import api.testUtilities.authentication.keyCloakProvider;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class getFlowSingleByVENDOR extends testConfig {


    @Test
    public void getFlowSingByVendorTest() {

        keyCloakProvider keyCloakProvider = new keyCloakProvider();
        String key = keyCloakProvider.getAccessToken();
        String header = "Bearer " + key;
        given()
                .spec(FM_GetFlowSingle_VENDOR)
                .header("Authorization", header)
                .log().ifValidationFails()
                .when()
                .get()
                .then()
                .log().ifValidationFails()
                .assertThat()
                .body("[0].name",equalTo("nestedSetBehaviourField"))
                .statusCode(200);


    }
}
