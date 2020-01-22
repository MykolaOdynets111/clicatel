/**
 * This class is to to test all the permutations of the OPTION 2 of the USSD String
 * Author: Adam Bethlehem
 */

package USSDTests.ussdGTBankTests.AirtimeOthersOptionTwo;

import api.testUtilities.baseUSSD;;
import io.restassured.http.ContentType;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import requestLibary.USSD.ussd;
import testUtilities.EndPoints.ussdTestEndpoints;
import testUtilities.ussd.createUSSDData;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class ussdAirtimeOthersTest extends baseUSSD {


    @DataProvider(name = "ussdAirtimeOthersTestData", parallel = true)
    public Object[] createUSSDTestData() {

        return new String[][]{

                {getRandomDoubleBetweenRange(), UUID.randomUUID().toString(), "1", "1", "*737#","2","0000"},
                {getRandomDoubleBetweenRange(), UUID.randomUUID().toString(), "1", "1", "*737#","2","0000"},
                {getRandomDoubleBetweenRange(), UUID.randomUUID().toString(), "1", "1", "*737#","2","0000"},
                {getRandomDoubleBetweenRange(), UUID.randomUUID().toString(), "1", "1", "*737#","2","0000"},
                {getRandomDoubleBetweenRange(), UUID.randomUUID().toString(), "1", "1", "*737#","2","0000"},

        };
    }

    @Test(dataProvider = "ussdAirtimeOthersTestData")
    public void startSession(String msisdn, String sessionID, String network, String type, String msg, String msgAirtimeOthers,
                             String msgAmount) {
        createUSSDData x = new createUSSDData();
        ussd payLoad;
        payLoad = x.getUSSDPayload(msisdn, sessionID, network, type, msg);
        payLoad.setMsg(msgAirtimeOthers);
        given()
                .contentType(ContentType.XML)
                .log().ifValidationFails()
                .body(payLoad)
                .when()
                .post(ussdTestEndpoints.USSD)
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .assertThat()
                .body("ussd.msg", containsString("Please enter amount"))
                .body("ussd.type", equalTo("2"))
                .body("ussd.msisdn", equalTo(payLoad.getMsisdn()));


        payLoad.setMsg(msgAmount);
        given()
                .contentType(ContentType.XML)
                .log().ifValidationFails()
                .body(payLoad)
                .when()
                .post(ussdTestEndpoints.USSD)
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("ussd.msg", containsString("Please enter 3rd party mobile number:"))
                .body("ussd.type", equalTo("2"));

        payLoad.setMsg(payLoad.getMsisdn());
        given()
                .contentType(ContentType.XML)
                .body(payLoad)
                .log().ifValidationFails()
                .when()
                .post(ussdTestEndpoints.USSD)
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .assertThat()
                .body("ussd.msisdn", equalTo(payLoad.getMsisdn()))
                .body("ussd.sessionId", equalTo(payLoad.getSessionid()))
                .body("ussd.msg", equalTo("vtu/GTBank_Airtime:step38"))
                .body("ussd.type", equalTo("5"));


    }


}
