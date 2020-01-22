package API.FlowManagerServices.management;

import api.testUtilities.testConfig;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import api.testUtilities.authentication.keyCloakProvider;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class getAllClientCLIENTTest extends testConfig {

    @Test
    public void getallCLIENTS() {

        keyCloakProvider keyCloakProvider = new keyCloakProvider();
        String key = keyCloakProvider.getAccessToken();
        String header = "Bearer " + key;

        given()
                .spec(FM_GetClients_CLIENTS)
                .contentType(ContentType.JSON)
                .header("Authorization", header).log().ifValidationFails()
                .when().get().then().log().ifValidationFails().assertThat().body("99", equalTo("Automated Testing")).statusCode(200);


    }
}
