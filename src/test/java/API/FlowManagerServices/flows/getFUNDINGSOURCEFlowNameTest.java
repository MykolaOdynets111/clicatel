package API.FlowManagerServices.flows;

import api.testUtilities.testConfig;
import org.testng.annotations.Test;
import api.testUtilities.authentication.keyCloakProvider;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class getFUNDINGSOURCEFlowNameTest extends testConfig {

    @Test
    public void getFUNDINGSOURCEFlowNameTest() {

        keyCloakProvider keyCloakProvider = new keyCloakProvider();
        String key = keyCloakProvider.getAccessToken();
        String header = "Bearer " + key;
        given()
                .spec(FM_getFlowName_FUNDINGSOURCE)
                .header("Authorization", header)
                .log().ifValidationFails()
                .when()
                .get("/fundingSourceConfirm")
                .then()
                .log().ifValidationFails()
                .assertThat()
                .body("start.type",equalTo("start"))
                .body("step22.action.name",equalTo("SaveHttpStatus"))
                .statusCode(200);
    }
}
