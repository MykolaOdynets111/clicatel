//package USSDTests;
//
//import baseTest.baseUSSD;
//import io.restassured.http.ContentType;
//import org.testng.annotations.Test;
//import testUtilities.EndPoints.testEndpoints;
//
//import static io.restassured.RestAssured.given;
//
//public class ussdOpenAccount extends baseUSSD {
//    //TODO: carry on writing test as there is an error
//    @Test(priority = 2)
//    public void ussdOpenAccountTest() {
//        baseUSSDRequest.setMsg("9");
//
//        given()
//                .contentType(ContentType.XML)
//                .body(baseUSSDRequest).
//                when()
//                .post(testEndpoints.USSD).
//                then()
//                                .log().ifValidationFails()
//                .statusCode(200);
//
//
//    }
//
//
//}
