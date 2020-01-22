package API.FlowManagerServices.flows;

import api.testUtilities.testConfig;
import org.testng.annotations.Test;
import api.testUtilities.authentication.keyCloakProvider;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class getFlowsByVendorTest extends testConfig {

    @Test
    public void getFlowsbyVendor() {

        keyCloakProvider keyCloakProvider = new keyCloakProvider();
        String key = keyCloakProvider.getAccessToken();
        String header = "Bearer " + key;
        given()
                .spec(FM_GetFlows_VENDORS)
                .header("Authorization", header)
                .log().ifValidationFails()
                .when()
                .get()
                .then()
                .log().ifValidationFails()
                .assertThat()
                .body("[1].path",equalTo("vendorPing"))
                .statusCode(200);


    }
}
