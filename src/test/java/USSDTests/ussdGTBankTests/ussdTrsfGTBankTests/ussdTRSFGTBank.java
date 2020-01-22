package USSDTests.ussdGTBankTests;

import api.testUtilities.baseUSSD;
import io.restassured.http.ContentType;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import requestLibary.USSD.ussd;
import testUtilities.EndPoints.ussdTestEndpoints;
import testUtilities.ussd.createUSSDData;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class ussdTRSFGTBank extends baseUSSD {

    @DataProvider(name = "trsfGTBankData", parallel = true)
    public Object[] createUSSDTestData() {

        return new String[][]{

                {getRandomDoubleBetweenRange(), UUID.randomUUID().toString(), "1", "1", "*737#", "4", "50000", "0000000000"},
//                {getRandomDoubleBetweenRange(), UUID.randomUUID().toString(), "1", "1", "*737#", "4", "50000", "0000000000"},

        };
    }

    @Test(dataProvider = "trsfGTBankData")
    public void ussdTrsfGTBankTest(String msisdn, String sessionID, String network, String type, String msg, String trnsfGTBankOption, String amount, String nuban) {
        createUSSDData x = new createUSSDData();
        ussd payLoad;
        payLoad = x.getUSSDPayload(msisdn, sessionID, network, type, msg);
        payLoad.setMsg(trnsfGTBankOption);

        given()
                .contentType(ContentType.XML)
                .body(payLoad).
                when()
                .post(ussdTestEndpoints.USSD)
                .then()
                .log().ifValidationFails()
                .assertThat()
                .body("ussd.msg", equalTo("Please enter amount:"))
                .statusCode(200);


        payLoad.setMsg(amount);
        given()
                .contentType(ContentType.XML)
                .body(payLoad).
                when()
                .post(ussdTestEndpoints.USSD)
                .then()
                .log().ifValidationFails()
                .assertThat()
                .body("ussd.msg", equalTo("Please enter recipient's NUBAN:"))
                .statusCode(200);


        payLoad.setMsg(nuban);
        given()
                .contentType(ContentType.XML)
                .body(payLoad)
                .when()
                .post(ussdTestEndpoints.USSD)
                .then()
                .log().ifValidationFails()
                .statusCode(200);
    }
}
