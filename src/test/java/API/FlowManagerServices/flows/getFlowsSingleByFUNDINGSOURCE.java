package API.FlowManagerServices.flows;

import api.testUtilities.testConfig;
import org.testng.annotations.Test;
import api.testUtilities.authentication.keyCloakProvider;

import static io.restassured.RestAssured.given;

public class getFlowsSingleByFUNDINGSOURCE extends testConfig {

    @Test
    public void getFlowSingByFundingSource() {

        keyCloakProvider keyCloakProvider = new keyCloakProvider();
        String key = keyCloakProvider.getAccessToken();
        String header = "Bearer " + key;
        //todo add validation
        given()
                .spec(FM_GetFlowSingle_FUNDING_SOURCE)
                .header("Authorization", header)
                .log().ifValidationFails()
                .when()
                .get()
                .then()
                .log().ifValidationFails()
                .assertThat().statusCode(200);


    }
}
