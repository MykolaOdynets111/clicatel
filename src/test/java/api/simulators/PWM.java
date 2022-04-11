package api.simulators;

import api.clients.*;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import static api.clients.SimulatorsClient.*;
import static api.clients.SimulatorsClient.mwmSuccess;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;

public class PWM {
    @Test
    @Description("31990 :: pwm-simulator :: happy path")
    @TmsLink("TECH-153375")
    public void testValidRequestHappyPath() {

        Map<String,Object> jsonObjectPayload = new LinkedHashMap<>();
        jsonObjectPayload.put("channelId", ReserveAndTransactClient.FeeAmount0);
        jsonObjectPayload.put("clientId", ReserveAndTransactClient.FeeAmount0);
        jsonObjectPayload.put("clientTransactionReference", UsersClient.firstName);
        jsonObjectPayload.put("fundingsourceId", ReserveAndTransactClient.FeeAmount0);
        jsonObjectPayload.put("initializationTime", "2021-12-23T07:06:29.635Z");
        jsonObjectPayload.put("optionalReference1", UsersClient.firstName);
        jsonObjectPayload.put("optionalReference2", UsersClient.firstName);
        jsonObjectPayload.put("optionalReference3", UsersClient.firstName);
        jsonObjectPayload.put("productId", ReserveAndTransactClient.FeeAmount0);
        jsonObjectPayload.put("raasTransactionReference", UsersClient.firstName);
        jsonObjectPayload.put("sourceIdentifier", UsersClient.firstName);
        jsonObjectPayload.put("targetIdentifier", UsersClient.firstName);
        jsonObjectPayload.put("value", ReserveAndTransactClient.FeeAmount0);

        PostPWMSimulator(jsonObjectPayload)
                .then().assertThat().statusCode(SC_ACCEPTED)
                .body("raasTransactionReference",Matchers.containsString(UsersClient.firstName))
                .body("reserveFundsReference",Matchers.notNullValue())
                .body("reserveFundsStatus",Matchers.containsString(Successful));


    }
}
