package api.simulators;

import api.clients.InFlightTransactionLookupClient;
import api.clients.ReserveAndTransactClient;
import api.clients.SimulatorsClient;
import api.domains.reserve_and_transact.model.ReserveAndTransactResponse;
import api.enums.ChannelId;
import api.enums.Port;
import api.enums.Version;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;

import static api.clients.ReserveAndTransactClient.*;
import static api.clients.SimulatorClient.*;
import static api.clients.SimulatorsClient.GetDataOffer;
import static api.clients.SupportUiClient.getRaasFlow;
import static api.clients.TransactionLookupClient.findTransaction;
import static api.domains.reserve_and_transact.repo.ReserveAndTransactRequestRepo.setUpReserveAndTransactV4Data;
import static api.domains.simulator.repo.SimulatorRequestRepo.setUpAirtelSimData;
import static api.enums.ChannelName.USSD;
import static api.enums.CurrencyCode.NGN;
import static org.apache.http.HttpStatus.SC_OK;

public class AirTel {

    @Test
    @Description("GET /dataoffer :: if valid airtle msisdn is provided as request parameter then hardcoded airtel product should be returned in the response")
    @TmsLink("MKP-979")
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
    @TmsLink("MKP-960")
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
    @TmsLink("MKP-975")
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
    @Test(groups = {"healthCheckTest"})
    @Description("30118-airtel-simulator :: GET /responsecodes :: happy path")
    @TmsLink("MKP-626")
    public void testGetResponseCodesAirtelSimulator() {
        GetAirTelSimulatorResponseCodes()
                .then().assertThat().statusCode(SC_OK);
    }
    @Test
    @Description("30118-airtel-simulator :: happy path")
    @TmsLink("MKP-715")
    public void testAirtelSimulatorHappyPath() throws InterruptedException {

        val addTestCase = setUpAirtelSimData(ReserveAndTransactClient.ResponseCode_17017, InFlightTransactionLookupClient.AirTel_purchase);

        addAirtelTestCases(Arrays.asList(addTestCase), Port.AIRTEL_SIMULATOR)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.contains(ReserveAndTransactClient.ResponseCode_17017))
                .body("id", Matchers.contains(""))
                .body("action", Matchers.contains(InFlightTransactionLookupClient.AirTel_purchase))
                .body("delay", Matchers.contains(Integer.parseInt(ReserveAndTransactClient.Airtel_delay_3000)))
                .body("httpStatusCode", Matchers.contains(Integer.parseInt(ReserveAndTransactClient.responseCode0)))
                .body("fieldName", Matchers.contains(""));

        //perform R&T - purchase airtel product
        val jsonBody = setUpReserveAndTransactV4Data(ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.Product_Airtel_130, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundsReserved))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
        //Adding thread.sleep because after the execution of reserveAndTransact call the script execute so fast that it does not get time to execute the airtell simulator under the non-retriable decline state and in the next step it removes the cases.
        Thread.sleep(5000);

        //set simulator to the default state (delete simulator tests)
        removeAllAirtelTestCases(Port.AIRTEL_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

//        Map<String, String> queryParams = new Hashtable<>();
//        queryParams.put("raasTxnRef", raasTxnRef);
//        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, Integer.parseInt(ReserveAndTransactClient.TestClient3), queryParams, Version.V2)
//                .then().assertThat().statusCode(SC_OK)
//                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
//                .body("transactionStatus", Matchers.containsString(Failed));

        //Verify against support tool API
        getRaasFlow(Port.RAAS_FLOW, raasTxnRef)
                .then().assertThat().statusCode(SC_OK)
                //"raas_request" parameter isn't empty
                .body("raas_request.raasTxnRef", Matchers.is(raasTxnRef))
                //"responseCode" in the "raas_response" equals to "0000"
                .body("raas_response.responseCode", Matchers.is(ReserveAndTransactClient.responseCode0000))
                //"reserve_fund_request" parameter isn't empty
                .body("reserve_fund_request.raasTxnRef", Matchers.is(raasTxnRef))
                // "responseCode" in the "reserve_fund_response" equals to "0000"
                .body("reserve_fund_response.responseCode", Matchers.is(ReserveAndTransactClient.responseCode0000))
                //only one value exist in the "ctx_request" array
                // "clientTransactionId" in "ctx_request" parameter is "rassTxnRef-0000"
                .body("ctx_response.clientTransactionId", Matchers.not(raasTxnRef.concat(FirstTransactionCode)))
                //"responseCode" in the "ctx_response" equals to "2213" for object with "clientTransactionId": "raasTxnRef-0000"
                .body("ctx_response[0].responseCode", Matchers.is(Integer.parseInt(ReserveAndTransactClient.responseCode2213)))
                .body("ctx_response.clientTransactionId", Matchers.not(raasTxnRef.concat(FirstTransactionCode)))
                //AND "ctx_lookup_request" array is empty
                .body("ctx_lookup_request", Matchers.empty())
                //AND "ctx_lookup_response" array is empty
                .body("ctx_lookup_response", Matchers.empty())
                //  "responseCode" in "transaction_result_request" parameter is "2213"
                .body("transaction_result_request.responseCode", Matchers.is(ReserveAndTransactClient.responseCode2213))
                // "responseCode" in the "transaction_result_response" equals to "202"
                .body("transaction_result_response.responseCode", Matchers.is(responseCode202));
    }
}
