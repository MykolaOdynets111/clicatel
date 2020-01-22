package API.FlowManagerServices.flows;

import api.testUtilities.testConfig;
import org.testng.annotations.Test;
import api.testUtilities.authentication.keyCloakProvider;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class getClientFlowName extends testConfig {

    @Test
    public void getClientFlowNameTest() {


        keyCloakProvider keyCloakProvider = new keyCloakProvider();
        String key = keyCloakProvider.getAccessToken();
        String header = "Bearer " + key;

        given()
                .spec(FM_getFlowName_CLIENT)
                .header("Authorization", header)
                .log().ifValidationFails()
                .when()
                .get("/access_bank,Access_Account_Opening")
                .then()
                .log().ifValidationFails()
                .assertThat()
                .body("step1.action.name",equalTo("IsSessionParam1Empty"))
                .body("step2.menu.menuType",equalTo("menuOption"))
                .body("step38.menu.menuType",equalTo("displayMenu"))
                .statusCode(200);


    }
}
