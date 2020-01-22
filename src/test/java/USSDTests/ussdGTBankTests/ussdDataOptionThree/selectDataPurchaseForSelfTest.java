package USSDTests.ussdGTBankTests.ussdDataOptionThree;


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

public class selectDataPurchaseForSelfTest extends baseUSSD {

    @DataProvider(name = "ussdData", parallel = true)
    public Object[] createUSSDTestData() {

        return new String[][]{

                {getRandomDoubleBetweenRange(), UUID.randomUUID().toString(), "1", "1", "*737#", "3", "1", "1"},
                {getRandomDoubleBetweenRange(), UUID.randomUUID().toString(), "1", "1", "*737#", "3", "1", "2"},
                {getRandomDoubleBetweenRange(), UUID.randomUUID().toString(), "1", "1", "*737#", "3", "1", "3"},
                {getRandomDoubleBetweenRange(), UUID.randomUUID().toString(), "1", "1", "*737#", "3", "1", "4"},
                {getRandomDoubleBetweenRange(), UUID.randomUUID().toString(), "1", "1", "*737#", "3", "1", "5"},
                {getRandomDoubleBetweenRange(), UUID.randomUUID().toString(), "1", "1", "*737#", "3", "1", "6"},
        };
    }

    @Test(dataProvider = "ussdData")
    public void selectDataPurchaseForSelfTest(String msisdn, String sessionID, String network, String type, String msg, String dataOption, String msgPurchaseSelf, String dataSelection) {

        createUSSDData x = new createUSSDData();
        ussd payLoad;
        payLoad = x.getUSSDPayload(msisdn, sessionID, network, type, msg);
        payLoad.setMsg(dataOption);
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
                .body("ussd.msg", containsString("Purchase Data for:"))
                .body("ussd.msg", containsString("1. Self"))
                .body("ussd.msg", containsString("2. 3rd Party"))
                .body("ussd.type", equalTo("2"));


        payLoad.setMsg(msgPurchaseSelf);
        given()
                .contentType(ContentType.XML)
                .log().ifValidationFails()
                .body(payLoad)
                .when()
                .post(ussdTestEndpoints.USSD)
                .then()
                .log().ifValidationFails()
                .assertThat()
                .body("ussd.msg", containsString("1. 1GB 30days N1000"))
                .body("ussd.msg", containsString("2. 2.5GB 30days N2000"))
                .body("ussd.msg", containsString("3. 40MB 1day N100"))
                .body("ussd.msg", containsString("4. 150MB 7days N200"))
                .body("ussd.msg", containsString("5. 1.5GB 30days N1200"))
                .body("ussd.msg", containsString("6. 11GB 30days N8000"))
                .body("ussd.type", equalTo("2"))
                .statusCode(200);


        payLoad.setMsg(dataSelection);
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
