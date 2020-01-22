package USSDTests.ussdMenuTests;

import api.testUtilities.baseUSSD;
import io.restassured.http.ContentType;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import requestLibary.USSD.ussd;
import testUtilities.EndPoints.ussdTestEndpoints;
import testUtilities.ussd.createUSSDData;

import java.util.UUID;

import static io.restassured.RestAssured.given;

public class ussdMenuWithKeyWordsTestOptionsTwo extends baseUSSD {


    @DataProvider(name = "ussdMenuWithKeyWords", parallel = true)
    public Object[] createUSSDTestData() {
        String message = "menuWithKeyWords";
        return new String[][]{

                {getRandomDoubleBetweenRange(), UUID.randomUUID().toString(), "1", "1", message, "display"},
//                {getRandomDoubleBetweenRange(), UUID.randomUUID().toString(), "1", "1", message, "two"},
//                {getRandomDoubleBetweenRange(), UUID.randomUUID().toString(), "1", "1", message, "text"},



        };
    }

    @Test(dataProvider = "ussdMenuWithKeyWords")
    public static void testUsingKeyWordsMenuItemTwo(String msisdn, String sessionID, String network, String type, String msg, String key1) {

        createUSSDData x = new createUSSDData();
        ussd payLoad;
        payLoad = x.simpleMenuUSSD(msisdn, sessionID, network, type, msg);
        payLoad.setMsg(key1);

        given()
                .contentType(ContentType.XML)
                .log().ifValidationFails()
                .body(payLoad)
                .when()
                .post(ussdTestEndpoints.USSD)
                .then()
                .log().ifValidationFails()
                .statusCode(200);
    }


}
