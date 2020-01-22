package API.FlowManagerServices.flows;

import api.testUtilities.testConfig;
import org.testng.annotations.Test;
import api.testUtilities.authentication.keyCloakProvider;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class getFlowByCLIENTTest extends testConfig {


    @Test
    public void getFlowsbyClient() {

        keyCloakProvider keyCloakProvider = new keyCloakProvider();
        String key = keyCloakProvider.getAccessToken();
        String header = "Bearer " + key;

        given()
                .spec(FM_GetFlows_CLIENTS)
                .header("Authorization", header)
                .log().ifValidationFails()
                .when()
                .get()
                .then()
                .log().ifValidationFails()
                .assertThat()
                .body("[3].name",equalTo("UBA_MAIN_MENU"))
                .body("[4].name",equalTo("DEV"))
                .body("[4].children[0].path",equalTo("DEV/TEST/Ask3"))
                .body("[248].id",equalTo(578))
                .statusCode(200);


    }


}


