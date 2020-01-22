package testUtilities.ussd;

import io.restassured.http.ContentType;
import requestLibary.USSD.ussd;
import testUtilities.EndPoints.ussdTestEndpoints;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class createUSSDData {
    public createUSSDData() {
    }

    public createUSSDData(String randomDoubleBetweenRange, String toString, String s, String s1, String s2) {
    }

    public ussd getUSSDPayload(String msisdn, String sessioID, String network, String type, String msg) {


        ussd payload = new ussd(msisdn, sessioID, network, type, msg);
        given()
                .contentType(ContentType.XML)
                .log().ifValidationFails()
                .body(payload)
                .when()
                .post(ussdTestEndpoints.USSD)
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .assertThat();

        return payload;

    }
    public ussd getUSSDPayloadPlainText(String msisdn, String sessioID, String network, String type, String msg) {


        ussd payload = new ussd(msisdn, sessioID, network, type, msg);
        given()
                .contentType(ContentType.XML)
                .log().ifValidationFails()
                .body(payload)
                .when()
                .post(ussdTestEndpoints.USSD)
                .then()
                .log().ifValidationFails()
                .assertThat()
                .body("ussd.msg", equalTo("Enter your name"))
                .statusCode(200);


        return payload;

    }
    public ussd getUSSDPayloadSimpleMenu(String msisdn, String sessioID, String network, String type, String msg) {


        ussd payload = new ussd(msisdn, sessioID, network, type, msg);
        given()
                .contentType(ContentType.XML)
                .log().ifValidationFails()
                .body(payload)
                .when()
                .post(ussdTestEndpoints.USSD)
                .then()
                .log().ifValidationFails()
                .assertThat()
                .body("ussd.msg", containsString("Welcome Menu\n" +
                        "1#Pieter\n" +
                        "2#Susan"))
                .statusCode(200);


        return payload;

    }
    public ussd getIsEmptySimpleUSSD(String msisdn, String sessioID, String network, String type, String msg) {


        ussd payload = new ussd(msisdn, sessioID, network, type, msg);
        given()
                .contentType(ContentType.XML)
                .log().ifValidationFails()
                .body(payload)
                .when()
                .post(ussdTestEndpoints.USSD)
                .then()
                .log().ifValidationFails()
                .assertThat()
                .body("ussd.msg", equalTo("It is empty"))
                .body("ussd.type", equalTo("3"))
                .statusCode(200);


        return payload;

    }
    public ussd getIsNullySimpleUSSD(String msisdn, String sessioID, String network, String type, String msg) {


        ussd payload = new ussd(msisdn, sessioID, network, type, msg);
        given()
                .contentType(ContentType.XML)
                .log().ifValidationFails()
                .body(payload)
                .when()
                .post(ussdTestEndpoints.USSD)
                .then()
                .log().ifValidationFails()
                .assertThat()
                .body("ussd.msg", equalTo("It is Null"))
                .body("ussd.type", equalTo("3"))
                .statusCode(200);


        return payload;

    }
    public ussd askForInputUSSD(String msisdn, String sessioID, String network, String type, String msg) {


        ussd payload = new ussd(msisdn, sessioID, network, type, msg);
        given()
                .contentType(ContentType.XML)
                .log().ifValidationFails()
                .body(payload)
                .when()
                .post(ussdTestEndpoints.USSD)
                .then()
                .log().ifValidationFails()
                .assertThat()
                .body("ussd.msg", equalTo("Enter your name"))
                .body("ussd.type", equalTo("2"))
                .statusCode(200);


        return payload;

    }
    public ussd ussdPlainText(String msisdn, String sessioID, String network, String type, String msg) {


        ussd payload = new ussd(msisdn, sessioID, network, type, msg);
        given()
                .contentType(ContentType.XML)
                .log().ifValidationFails()
                .body(payload)
                .when()
                .post(ussdTestEndpoints.USSD)
                .then()
                .log().ifValidationFails()
                .assertThat()
                .body("ussd.msg", equalTo("How are you today"))
                .body("ussd.type", equalTo("3"))
                .statusCode(200);


        return payload;

    }
    public ussd askForInputRedirectUSSD(String msisdn, String sessioID, String network, String type, String msg) {


        ussd payload = new ussd(msisdn, sessioID, network, type, msg);
        given()
                .contentType(ContentType.XML)
                .log().ifValidationFails()
                .body(payload)
                .when()
                .post(ussdTestEndpoints.USSD)
                .then()
                .log().ifValidationFails()
                .assertThat()
                .body("ussd.msg", equalTo("Enter your name"))
                .body("ussd.type", equalTo("2"))
                .statusCode(200);


        return payload;

    }
    public ussd askForInputSensitiveUSSD(String msisdn, String sessioID, String network, String type, String msg) {


        ussd payload = new ussd(msisdn, sessioID, network, type, msg);
        given()
                .contentType(ContentType.XML)
                .log().ifValidationFails()
                .body(payload)
                .when()
                .post(ussdTestEndpoints.USSD)
                .then()
                .log().ifValidationFails()
                .assertThat()
                .body("ussd.msg", equalTo("Enter your name"))
                .body("ussd.type", equalTo("2"))
                .statusCode(200);


        return payload;

    }
    public ussd displayTextInputUSSD(String msisdn, String sessioID, String network, String type, String msg) {


        ussd payload = new ussd(msisdn, sessioID, network, type, msg);
        given()
                .contentType(ContentType.XML)
                .log().ifValidationFails()
                .body(payload)
                .when()
                .post(ussdTestEndpoints.USSD)
                .then()
                .log().ifValidationFails()
                .assertThat()
                .body("ussd.msg", equalTo("Enter your name"))
                .body("ussd.type", equalTo("2"))
                .statusCode(200);


        return payload;

    }
    public ussd simpleMenuUSSD(String msisdn, String sessioID, String network, String type, String msg) {


        ussd payload = new ussd(msisdn, sessioID, network, type, msg);
        given()
                .contentType(ContentType.XML)
                .log().ifValidationFails()
                .body(payload)
                .when()
                .post(ussdTestEndpoints.USSD)
                .then()
                .log().ifValidationFails()
                .assertThat()
                .body("ussd.msg", containsString("Welcome"))

                .body("ussd.type", equalTo("2"))
                .statusCode(200);


        return payload;

    }
}
