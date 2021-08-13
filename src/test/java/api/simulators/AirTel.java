package api.simulators;

import api.clients.InFlightTransactionLookupClient;
import api.clients.SimulatorsClient;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Hashtable;
import java.util.Map;

import static api.clients.SimulatorsClient.GetDataOffer;
import static org.apache.http.HttpStatus.SC_OK;

public class AirTel {

    @Test
    @Description("GET /dataoffer :: if valid airtle msisdn is provided as request parameter then hardcoded airtel product should be returned in the response")
    @TmsLink("TECH-116731")
    public void testValidMsisdnAirtelWithProducts() {

        Map<String, String> map = new Hashtable<>();
        map.put("msisdn", SimulatorsClient.ValidAirtelMsisdnWithProducts);
        GetDataOffer(map)
                .then().assertThat().statusCode(SC_OK)
                .body("TYPE", Matchers.is(SimulatorsClient.ValidMsisdnType))
                .body("TXNSTATUS", Matchers.is(InFlightTransactionLookupClient.ResponseCode_200))
                .body("MESSAGE", Matchers.is(SimulatorsClient.responseMessageGetOfferAirTel))
                .body("OFFERDETAILS", Matchers.notNullValue());
    }

    @Test
    @Description("GET /dataoffer :: if airtel msisdn without products is provided as request parameter then none of products should be returned in the response")
    @TmsLink("TECH-116732")
    public void testValidMsisdnAirtelWithoutProducts() {

        Map<String, String> map = new Hashtable<>();
        map.put("msisdn", SimulatorsClient.ValidAirtelMsisdnWithoutProducts);
        GetDataOffer(map)
                .then().assertThat().statusCode(SC_OK)
                .body("TYPE", Matchers.is(SimulatorsClient.ValidMsisdnType))
                .body("TXNSTATUS", Matchers.is(InFlightTransactionLookupClient.ResponseCode_200))
                .body("MESSAGE", Matchers.is(SimulatorsClient.responseMessageGetOfferAirTel))
                .body("OFFERDETAILS", Matchers.nullValue());
    }

    @Test
    @Description("GET /dataoffer :: if not airtel msisdn is provided as request parameter then none of products should be returned in the response")
    @TmsLink("TECH-116733")
    public void testInValidMsisdnAirtelWithoutProducts() {

        Map<String, String> map = new Hashtable<>();
        map.put("msisdn", SimulatorsClient.InValidAirtelMsisdnWithoutProducts);
        GetDataOffer(map)
                .then().assertThat().statusCode(SC_OK)
                .body("TYPE", Matchers.is(SimulatorsClient.ValidMsisdnType))
                .body("TXNSTATUS", Matchers.is(SimulatorsClient.ResponseCode_9007))
                .body("MESSAGE", Matchers.is(SimulatorsClient.responseMessageGetOfferAirTelInvalidMsisdn))
                .body("OFFERDETAILS", Matchers.nullValue());
    }
}
