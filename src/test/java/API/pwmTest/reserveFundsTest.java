package API.pwmTest;

import api.testUtilities.testConfig;
import io.restassured.http.ContentType;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import api.requestLibary.PWM.pwmReserveFundPOJO;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class reserveFundsTest extends testConfig {

    @DataProvider(name = "pwmReserveFunds", parallel = true)
    public Object[] createUSSDTestData() {

        return new String[][]{

                {"432","7","USSD","5hfg-apr8-skgm-4t56","0","sdfdsdgsdgf","0","917","5","10000","A5BD651282F","10000","10000","0000000000022","0000000000022","2019-08-28T01:37:55.359+02:00"},
                {"432","7","USSD","5hfg-apr8-skgm-4t56","0","sdfdsdgsdgf","0","917","5","10000","A5BD651282F","10000","10000","0000000000003","0000000000004","2019-08-28T01:37:55.359+02:00"},
                {"432","7","USSD","5hfg-apr8-skgm-4t56","0","sdfdsdgsdgf","0","917","5","10000","A5BD651282F","10000","10000","0000000000005","0000000000006","2019-08-28T01:37:55.359+02:00"},
                {"432","7","USSD","5hfg-apr8-skgm-4t56","0","sdfdsdgsdgf","0","917","5","10000","A5BD651282F","10000","10000","0000000000007","0000000000008","2019-08-28T01:37:55.359+02:00"},
                {"432","7","USSD","5hfg-apr8-skgm-4t56","0","sdfdsdgsdgf","0","917","5","10000","A5BD651282F","10000","10000","0000000000009","0000000000010","2019-08-28T01:37:55.359+02:00"},




        };
    }

    @Test(dataProvider = "pwmReserveFunds")
    public void basicReserveFundTest(String authCode, String channelID, String channelName, String channelSessionID, String clientShareAmount, String clientTNXRef,
                                     String feeAmount, String productId, String productType,String purchaseAmount,String raasTnxRef, String reserveAmount,
                                     String settlementAmount, String sourceIdentifier, String targetIdentofier, String timeStamp){

        pwmReserveFundPOJO payload = new pwmReserveFundPOJO(authCode,sourceIdentifier,productId,clientShareAmount,reserveAmount,raasTnxRef,purchaseAmount,feeAmount,targetIdentofier,clientTNXRef,channelSessionID,
                channelName,settlementAmount,channelID,productType,timeStamp);

        given().log().ifValidationFails().
                spec(PWM_ReserveFunds_RequestSpec)
                .contentType(ContentType.JSON)
                .body(payload)
                .log().ifValidationFails()
                .post()
                .then()
                .assertThat()
                .body("responseCode",equalTo("0000"))
                .log().ifValidationFails()
                .statusCode(200);

    }

}
