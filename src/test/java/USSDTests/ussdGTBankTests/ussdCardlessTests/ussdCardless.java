package USSDTests.ussdGTBankTests.ussdCardlessTests;

import api.testUtilities.baseUSSD;
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

public class ussdCardless extends baseUSSD {



    @DataProvider(name = "createUSSDTestData", parallel = true)
    public Object[] createUSSDTestData() {

        return new String[][]{
                {getRandomDoubleBetweenRange(), UUID.randomUUID().toString(), "1", "1", "*737#"},
                {getRandomDoubleBetweenRange(), UUID.randomUUID().toString(), "1", "1", "*737#"},
                {getRandomDoubleBetweenRange(), UUID.randomUUID().toString(), "1", "1", "*737#"},
                {getRandomDoubleBetweenRange(), UUID.randomUUID().toString(), "1", "1", "*737#"},
                {getRandomDoubleBetweenRange(), UUID.randomUUID().toString(), "1", "1", "*737#"},

        };
    }

    @Test(dataProvider = "createUSSDTestData")
    public void selectCardlessTest(String msisdn, String sessionID, String network, String type, String msg) {
        createUSSDData x = new createUSSDData();
        ussd payLoad;
        payLoad = x.getUSSDPayload(msisdn, sessionID, network, type, msg);
        payLoad.setMsg("7");


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
                .body("ussd.msg", containsString("GT Rescue"))
                .body("ussd.msg", containsString("Please enter amount:"))
                .body("ussd.type", equalTo("2"));


        payLoad.setMsg("20000");
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
                .body("ussd.msg", containsString("Please enter PIN or the 4 digits on your GTBank debit card to allow null to withdraw NGN"))
                .body("ussd.type", equalTo("2"));
    }

}


