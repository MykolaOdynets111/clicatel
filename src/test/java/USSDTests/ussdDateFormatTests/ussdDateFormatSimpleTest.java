package USSDTests.ussdDateFormatTests;

import api.testUtilities.baseUSSD;
import io.restassured.http.ContentType;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import requestLibary.USSD.ussd;
import testUtilities.EndPoints.ussdTestEndpoints;
import testUtilities.ussd.createUSSDData;

import java.util.UUID;

import static io.restassured.RestAssured.given;

public class ussdDateFormatSimpleTest extends baseUSSD {

//TODO this is not working
    @DataProvider(name = "ussdDateFormatTestData", parallel = true)
    public Object[] createUSSDTestData() {
        String message ="DateFormat/SimpleTest";

        return new String[][]{

                {getRandomDoubleBetweenRange(), UUID.randomUUID().toString(), "1", "1", message,"170119860000"},
//                {getRandomDoubleBetweenRange(), UUID.randomUUID().toString(), "1", "1", message},
//                {getRandomDoubleBetweenRange(), UUID.randomUUID().toString(), "1", "1", message},
//                {getRandomDoubleBetweenRange(), UUID.randomUUID().toString(), "1", "1", message},
//                {getRandomDoubleBetweenRange(), UUID.randomUUID().toString(), "1", "1", message},




        };
    }

    @Test(dataProvider = "ussdDateFormatTestData", enabled = false)
    public static void IsNull_SimpleTest(String msisdn, String sessionID, String network, String type, String msg,String nonFormattedDate) {
        createUSSDData x = new createUSSDData();
        ussd payLoad;
        payLoad = x.askForInputSensitiveUSSD(msisdn, sessionID, network, type, msg);

        payLoad.setMsg(msg);
        given()
                .contentType(ContentType.XML)
                .body(payLoad)
                .when()
                .log().ifValidationFails()
                .post(ussdTestEndpoints.USSD)
                .then()
                .log().ifValidationFails()
                .assertThat()
                .statusCode(200);

        payLoad.setMsg(nonFormattedDate);
        given()
                .contentType(ContentType.XML)
                .body(payLoad)
                .when()
                .log().ifValidationFails()
                .post(ussdTestEndpoints.USSD)
                .then()
                .log().ifValidationFails()
                .assertThat()
                .statusCode(200);


    }
}
