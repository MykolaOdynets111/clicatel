package api.testUtilities.ussd;

import io.restassured.http.ContentType;
import api.requestLibary.USSD.ussdGeneric;
import api.testUtilities.EndPoints.ussdTestEndpoints;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ussdGenericProvider {


    public ussdGeneric getGenericUSSD(String msg, String type, String network, String resType, String resMsg){
        ussdGeneric payload = new ussdGeneric(msg,type,network,resType,resMsg);

        given()
                .contentType(ContentType.XML)
                .log().ifValidationFails()
                .body(payload)
                .when()
                .post(ussdTestEndpoints.USSD)
                .then()
                .log().ifValidationFails()
                .assertThat()
                .body("ussd.type",equalTo(resType))
                .body("ussd.msg",equalTo(resMsg))
                .statusCode(200)
                .assertThat();

        return payload;
    }
}
