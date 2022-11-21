package api.simulators;

import api.clients.ProductLookupClient;
import api.clients.ReserveAndTransactClient;
import api.clients.VendorRoutingServiceClient;
import api.domains.reserve_and_transact.model.ReserveAndTransactResponse;
import api.enums.ChannelId;
import api.enums.Port;
import api.enums.Version;
import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.apache.xerces.impl.xpath.regex.Match;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

import static api.clients.ReserveAndTransactClient.executeReserveAndTransact;
import static api.clients.SimulatorClient.addMtnTestCases;
import static api.clients.SimulatorClient.removeAllMtnTestCases;
import static api.clients.SupportUiClient.getRaasFlow;
import static api.clients.TransactionLookupClient.findTransaction;
import static api.clients.VendorRoutingServiceClient.*;
import static api.domains.reserve_and_transact.repo.ReserveAndTransactRequestRepo.setUpReserveAndTransactV4Data;
import static api.domains.simulator.repo.SimulatorRequestRepo.setUpMtnSimData;
import static api.domains.simulator.repo.VendorRoutingServiceRequestRepo.GetMinScenario;
import static api.enums.ChannelName.USSD;
import static api.enums.CurrencyCode.ZAR;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;

public class MTN {

    @Test
    @Description("31943 :: mtn-simulator :: happy path")
    @TmsLink("MKP-661")
    public void testMtnSimulatorHappyPath() throws InterruptedException {
        //add test cases
        val addTestCase1 = setUpMtnSimData("9313", "27837640171", "virtual_recharge", 200);
        val addTestCase2 = setUpMtnSimData("9313", "27837640171", "bundle_recharge", 200);
        val addTestCase3 = setUpMtnSimData("9313", "27837640171", "repeat_virtual_recharge", 200);
        val addTestCase4 = setUpMtnSimData("9313", "27837640171", "repeat_bundle_recharge", 200);

        addMtnTestCases(Arrays.asList(addTestCase1, addTestCase2, addTestCase3, addTestCase4), Port.MTN_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

        //perform R&T - purchase mtn product
        val jsonBody = setUpReserveAndTransactV4Data("2", ZAR, USSD, ChannelId.USSD, "400", "10000", "0", "27837640171");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
        Thread.sleep(240000);

        //Verify transaction status is "FAILED"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, 2, queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString("FAILED"));

        //Verify against support tool API
        getRaasFlow(Port.RAAS_FLOW, raasTxnRef)
                .then().assertThat().statusCode(SC_OK)
                //THEN "raas_request" parameter isn't empty
                .body("raas_request.raasTxnRef", Matchers.is(raasTxnRef))
                //"responseCode" in the "raas_response" equals to "0000"
                .body("raas_response.responseCode", Matchers.is(ReserveAndTransactClient.responseCode0000))
                //"reserve_fund_request" parameter isn't empty
                .body("reserve_fund_request.raasTxnRef", Matchers.is(raasTxnRef))
                //AND "responseCode" in the "reserve_fund_response" equals to "0000"
                .body("reserve_fund_response.responseCode", Matchers.is(ReserveAndTransactClient.responseCode0000))
                //AND the only one objects exists in the "ctx_request" array with "clientTransactionId" is "{transactionId}-0000"
                .body("ctx_request.clientTransactionId", Matchers.hasItem(raasTxnRef.concat("-0000")))
                //AND the only one object exists in the "ctx_response" array
                //AND "responseCode" for "clientTransactionId": "{transactionId}-0000" object equals to "2236"
                .body("ctx_response.responseCode", Matchers.hasItem(2213));

        //set simulator to the default state (delete simulator tests)
        removeAllMtnTestCases(Port.MTN_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);
    }

    @Test
    @Description("31943 :: mtn-simulator :: GET /mtn/test-scenarios :: happy path")
    @TmsLink("MKP-1048")
    public void testMtnSimulatorGetAllScenarios() throws InterruptedException
    {
        GetAllScenarios()
                .then().assertThat().statusCode(SC_OK)
                .body("scenarioId", Matchers.hasItem("sample"));

    }
    @Test
    @Description("31943 :: mtn-simulator :: GET /mtn/current-test-scenario :: happy path")
    @TmsLink("MKP-1031")
    public void testMtnGetCurrentScenario() throws InterruptedException
    {
        Map<String,Object> CurrentScenario_sample= new LinkedHashMap<>();
        CurrentScenario_sample.put("currentScenarioId",Scenario_sample);

        PutCurrentScenario(CurrentScenario_sample)
                .then().assertThat().statusCode(SC_OK);
        GetCurrentScenarios()
                .then().assertThat().statusCode(SC_OK)
                .body("currentScenarioId", Matchers.is(Scenario_sample));


    }
    @Test
    @Description("31943 :: mtn-simulator :: PUT /mtn/current-test-scenario :: happy path")
    @TmsLink("MKP-951")
    public void testMtnPutCurrentScenario() throws InterruptedException
    {
        Map<String,Object> CurrentScenario_sample= new LinkedHashMap<>();
        CurrentScenario_sample.put("currentScenarioId",Scenario_sample);

        PutCurrentScenario(CurrentScenario_sample)
                .then().assertThat().statusCode(SC_OK);
        GetCurrentScenarios()
                .then().assertThat().statusCode(SC_OK)
                .body("currentScenarioId", Matchers.is(Scenario_sample));


    }

    @Test
    @Description("31943 :: mtn-simulator :: GET /mtn/test-scenario :: happy path")
    @TmsLink("MKP-1102")
    public void testGetTestScenarioHappyPath() {

        DeleteScenario("min")
                .then().assertThat().statusCode(SC_OK);

        Map Scenarios = GetMinScenario();

        PostScenario(Scenarios)
                .then().assertThat().statusCode(SC_CREATED)
                .body("scenarioId", Matchers.is(Scenario_min))
                .body("scenarios.subscriberResponse.payload.vas.vasServices.saeeId[0]", Matchers.is(Arrays.asList(Arrays.asList(externalId_DATA_001_min))))
                .body("scenarios.subscriberResponse.payload.vas.vasServices.bundleDescription[0]", Matchers.is(Arrays.asList(Arrays.asList(MtnProductMinScenario))));

        GetSpecificScenario(Scenario_min)
                .then().assertThat().statusCode(SC_OK)
                .body("scenarioId", Matchers.is(Scenario_min))
                .body("scenarios.subscriberResponse.payload.vas.vasServices.saeeId[0]", Matchers.is(Arrays.asList(Arrays.asList(externalId_DATA_001_min))))
                .body("scenarios.subscriberResponse.payload.vas.vasServices.bundleDescription[0]", Matchers.is(Arrays.asList(Arrays.asList(MtnProductMinScenario))));

        DeleteScenario(Scenario_min)
                .then().assertThat().statusCode(SC_OK);

        Map<String, Object> CurrentScenarioPost = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_sample);

        PutCurrentScenario(CurrentScenarioPost)
                .then().assertThat().statusCode(SC_OK);

    }

    @Test
    @Description("31943 :: mtn-simulator :: GET /application-management/bundle-catalogue/enterprise/proxy/api/v6/vas :: happy path ")
    @TmsLink("MKP-993")
    public void testMtnGetApplicationManagementBundleV6() throws InterruptedException
    {
        Map<String,Object> CurrentScenario_sample= new LinkedHashMap<>();
        CurrentScenario_sample.put("currentScenarioId",Scenario_sample);

        PutCurrentScenario(CurrentScenario_sample)
                .then().assertThat().statusCode(SC_OK);
        GetCurrentScenarios()
                .then().assertThat().statusCode(SC_OK)
                .body("currentScenarioId", Matchers.is(Scenario_sample));

        Map<String, String> map = new Hashtable<>();
        map.put("channel", "MTNChat");
        map.put("bundleState", "All");
        map.put("topSeller", "No");
        map.put("sourceIdentifier", "MTNChat");
        map.put("vasType", "Standard");
        map.put("purchaseMedium", "Card");
        map.put("transactionId", "80278254907");
        map.put("subscriberType", "Hybrid");
        map.put("platformType", "Self%20Service");

        GetApplicationManagementV6(map)
                .then().assertThat().statusCode(SC_OK);


    }
}