
//TODO Fix this test for some reason 500 instead of 202

package API.pwmTest;

import api.testUtilities.testConfig;
import io.restassured.http.ContentType;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import api.requestLibary.PWM.pwmConfirmationPOJO;

import static io.restassured.RestAssured.given;

public class Confirm extends testConfig {

    @DataProvider(name = "confirm", parallel = true)
    public Object[] createUSSDTestData() {

        return new String[][]{


                {"sdfdsdgsdgf", "A5BD651282F", "10000", "84238385A257E16A", "0000", "2019-08-28T01:37:55.359+02:00"},


        };
    }

    @Test(dataProvider = "confirm")
    public void confirmTest(String clientTnxRef, String raasTnx, String reserveAmount,String reserveFundsTnxRef,String responseCode, String timeStamp){


        pwmConfirmationPOJO payload = new pwmConfirmationPOJO(reserveFundsTnxRef,clientTnxRef,reserveAmount,raasTnx,responseCode,timeStamp);

        given()
                .spec(PWM_Confirmation_RequestSpec)
                .contentType(ContentType.JSON)
                .body(payload)
                .log().ifValidationFails()
                .when().post().then().log().ifValidationFails().statusCode(202);


    }
}
