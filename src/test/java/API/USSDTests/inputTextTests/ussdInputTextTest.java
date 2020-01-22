package API.USSDTests.inputTextTests;

import api.testUtilities.baseUSSD;
import io.restassured.http.ContentType;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import api.requestLibary.USSD.ussdGeneric;
import api.testUtilities.EndPoints.ussdTestEndpoints;
import api.testUtilities.ussd.ussdGenericProvider;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ussdInputTextTest extends baseUSSD {

    @DataProvider(name = "ussdInputTextData", parallel = true)
    public Object[] createUSSDTestData() {
        String message = "AskForInput/InputText";
        String respType = "2";
        String respMsg = "Enter your name";
        String name = "CerebroAutomation";
        return new String[][]{

                {message, "1", "1", respType, respMsg, name},
                {message, "1", "1", respType, respMsg, name},
                {message, "1", "1", respType, respMsg, name},
                {message, "1", "1", respType, respMsg, name},
                {message, "1", "1", respType, respMsg, name},


        };
    }


    @Test(dataProvider = "ussdInputTextData")
    public void ussdInputTextTest(String message, String type, String network, String respType, String respMsg, String name) {


        //Step 2
        String msg2 = "Welcome " + name;
        String network2 = "3";


        ussdGenericProvider x = new ussdGenericProvider();
        ussdGeneric payLoad;
        payLoad = x.getGenericUSSD(message, type, network, respType, respMsg);
        payLoad.setMsg(name);

        payLoad.setMsg(name);

        given()
                .contentType(ContentType.XML)
                .body(payLoad)
                .when()
                .log().ifValidationFails()
                .post(ussdTestEndpoints.USSD)
                .then()
                .log().ifValidationFails()
                .assertThat()
                .body("ussd.msg", equalTo(msg2))
                .body("ussd.type", equalTo(network2))
                .statusCode(200);

    }

}