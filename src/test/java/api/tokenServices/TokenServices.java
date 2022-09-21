package api.tokenServices;

import api.clients.InFlightTransactionLookupClient;
import api.clients.SimulatorsClient;
import api.clients.TokenServicesClient;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import java.util.Hashtable;
import java.util.Map;
import static api.clients.SimulatorsClient.GetDataOffer;
import static api.clients.TokenServicesClient.GetRaasTokenByRaasTxnRef;
import static org.apache.http.HttpStatus.SC_OK;

public class TokenServices {

    @Test
    @Description("31926 :: payd-raas-token-service :: GET /tokenservice/lookup")
    @TmsLink("MKP-968")
    public void testRaasTokenServiceLookup() {

        Map<String, String> map = new Hashtable<>();
        map.put("raasTxnRef", TokenServicesClient.raasTxnRefForRaasTokenService);
        GetRaasTokenByRaasTxnRef(map)
                .then().assertThat().statusCode(SC_OK)
                .body("id", Matchers.is(Integer.parseInt(TokenServicesClient.RaasTokenServiceID)))
                .body("raasTxnRef", Matchers.is(TokenServicesClient.raasTxnRefForRaasTokenService))
                .body("token", Matchers.is(TokenServicesClient.RaasTokenServiceToken));
    }
}
