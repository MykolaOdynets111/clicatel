package API.pwmTest;

import api.testUtilities.testConfig;
import io.restassured.http.ContentType;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import api.requestLibary.PWM.pwmLookupBehaviourPOJO;
import api.requestLibary.PWM.pwmReserveFundPOJO;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class reserveFundsBehaviourChange extends testConfig {

    @DataProvider(name = "pwmReserveFundsBehaviour", parallel = false)
    public Object[] createUSSDTestData() {

        return new String[][]{

                {"432", "7", "USSD", "5hfg-apr8-skgm-4t56", "0", "sdfdsdgsdgf", "0", "917", "5", "10000", "A5BD651282F", "10000", "10000", "0000000000001", "0000000000002", "2019-08-28T01:37:55.359+02:00", "1000", "5001", "Ref"},
                {"432", "7", "USSD", "5hfg-apr8-skgm-4t56", "0", "sdfdsdgsdgf", "0", "917", "5", "10000", "A5BD651282F", "10000", "10000", "0000000000001", "0000000000002", "2019-08-28T01:37:55.359+02:00", "1000", "5002", "Ref"},
                {"432", "7", "USSD", "5hfg-apr8-skgm-4t56", "0", "sdfdsdgsdgf", "0", "917", "5", "10000", "A5BD651282F", "10000", "10000", "0000000000001", "0000000000002", "2019-08-28T01:37:55.359+02:00", "1000", "5003", "Ref"},
                {"432", "7", "USSD", "5hfg-apr8-skgm-4t56", "0", "sdfdsdgsdgf", "0", "917", "5", "10000", "A5BD651282F", "10000", "10000", "0000000000001", "0000000000002", "2019-08-28T01:37:55.359+02:00", "1000", "5004", "Ref"},
                {"432", "7", "USSD", "5hfg-apr8-skgm-4t56", "0", "sdfdsdgsdgf", "0", "917", "5", "10000", "A5BD651282F", "10000", "10000", "0000000000001", "0000000000002", "2019-08-28T01:37:55.359+02:00", "1000", "5005", "Ref"},


        };
    }

    @Test(dataProvider = "pwmReserveFundsBehaviour")
    public void basicReserveFundTest(String authCode, String channelID, String channelName, String channelSessionID, String clientShareAmount, String clientTNXRef,
                                     String feeAmount, String productId, String productType, String purchaseAmount, String raasTnxRef, String reserveAmount,
                                     String settlementAmount, String sourceIdentifier, String targetIdentifier, String timeStamp,
                                     String delayMS, String reserveResponseCode, String reserveTransactionReference) {

        pwmLookupBehaviourPOJO behaviourPayload = new pwmLookupBehaviourPOJO(delayMS, reserveTransactionReference, targetIdentifier, sourceIdentifier, reserveResponseCode);

        given()
                .spec(PWM_ReserveFunds_Behaviour_RequestSpec)
                .contentType(ContentType.JSON)
                .body(behaviourPayload)
                .log().ifValidationFails()
                .post()
                .then()
                .assertThat()
                .statusCode(200);

        pwmReserveFundPOJO payload = new pwmReserveFundPOJO(authCode, sourceIdentifier, productId, clientShareAmount, reserveAmount, raasTnxRef, purchaseAmount, feeAmount, targetIdentifier, clientTNXRef, channelSessionID,
                channelName, settlementAmount, channelID, productType, timeStamp);

        given().log().ifValidationFails().
                spec(PWM_ReserveFunds_RequestSpec)
                .contentType(ContentType.JSON)
                .body(payload)
                .log().ifValidationFails()
                .when()
                .post()
                .then()
                .assertThat()
                .body("responseCode", equalTo(reserveResponseCode))
                .log().ifValidationFails()
                .statusCode(200);

    }


}
