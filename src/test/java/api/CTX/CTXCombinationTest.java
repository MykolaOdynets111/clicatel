package api.CTX;

import api.clients.BasedAPIClient;
import api.clients.InFlightTransactionLookupClient;
import api.clients.ReserveAndTransactClient;
import api.enums.Port;
import io.qameta.allure.TmsLink;
import io.restassured.path.xml.XmlPath;
import lombok.val;
import org.springframework.context.annotation.Description;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Map;

import static api.clients.CTX.*;
import static api.clients.CTX.validateCustomerAccount;
import static api.clients.CTXSimulatorClient.*;
import static api.clients.InFlightTransactionLookupClient.*;
import static api.clients.NotificationClient.Identifier_6;
import static api.clients.ProductLookupClient.ProductAirtel_130;
import static api.clients.ProductLookupClient.ProductTypeAirtime_3;
import static api.clients.ReserveAndTransactClient.*;
import static api.clients.SimulatorClient.*;
import static api.clients.SimulatorsClient.*;
import static api.clients.VendorManagementClient.Vendor21;
import static api.clients.VendorRoutingServiceClient.vendorTransactionRefLookup;
import static api.clients.VendorRoutingServiceClient.vendorTransactionReference;
import static api.domains.CTX.CTX.*;
import static api.domains.CTX.CTX.LookupCtxCreateBody;
import static api.domains.simulator.repo.SimulatorRequestRepo.setUpAirtelSimData;
import static api.domains.simulator.repo.SimulatorRequestRepo.setUpMtnSimData;
import static api.domains.simulator.repo.VendorRoutingServiceRequestRepo.SetupSetVendData;
import static io.restassured.path.xml.config.XmlPathConfig.xmlPathConfig;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CTXCombinationTest extends BasedAPIClient{

    @Test
    @Description("CTX :: Purchase : SUCCESS scenario (Vendor 103; Airtel)")
    @TmsLink("MKP-367")
    public void testAirtelSuccessScenario(){

        val addTestCase1 = setUpAirtelSimData(ResponseCode_200, InFlightTransactionLookupClient.AirTel_purchase);
        val addTestCase2 = setUpAirtelSimData(ResponseCode_200,AirTel_lookup);

        addAirtelTestCases(Arrays.asList(addTestCase1,addTestCase2), Port.AIRTEL_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

        String request = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_19,ProductAirtel_130,ChannelID_07,alternateClientId,Identifier_19,channelSessionId_3133827170);
        val response = validateCustomerAccount(request)
                .then().statusCode(200)
                .extract().response();
        System.out.println(response.getBody().asString());
        XmlPath xmlPath = new XmlPath(response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode0));
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        removeAllAirtelTestCases(Port.AIRTEL_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);
    }

    @Test
    @Description("CTX :: Purchase : NonRetryableDecline scenario (Vendor 103; Airtel)")
    @TmsLink("MKP-405")
    public void testAirtelNonretryableDeclineScenario(){

        val addTestCase = setUpAirtelSimData(ReserveAndTransactClient.ResponseCode_17017, InFlightTransactionLookupClient.AirTel_purchase);
        addAirtelTestCases(Arrays.asList(addTestCase), Port.AIRTEL_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

        String request = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_19,ProductAirtel_130,ChannelID_07,alternateClientId,Identifier_19,channelSessionId_3133827170);
        val response = validateCustomerAccount(request)
                .then().statusCode(200)
                .extract().response();
        System.out.println(response.getBody().asString());
        XmlPath xmlPath = new XmlPath(response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2213));
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        removeAllAirtelTestCases(Port.AIRTEL_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);
    }

    @Test
    @Description("CTX :: Purchase : Pending To SUCCESS (Vendor 103; Airtel)")
    @TmsLink("MKP-478")
    public void testAirtelPendingToSuccessScenario() throws InterruptedException {

        val addTestCase1 = setUpAirtelSimData(ResponseCode_500, InFlightTransactionLookupClient.AirTel_purchase);
        val addTestCase2 = setUpAirtelSimData(ResponseCode_200,AirTel_lookup);

        addAirtelTestCases(Arrays.asList(addTestCase1,addTestCase2), Port.AIRTEL_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

        String request = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_19,ProductAirtel_130,ChannelID_07,alternateClientId,Identifier_19,channelSessionId_3133827170);
        val response = validateCustomerAccount(request)
                .then().statusCode(200)
                .extract().response();
        System.out.println(response.getBody().asString());
        XmlPath xmlPath = new XmlPath(response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2240));
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        Thread.sleep(120000);

        String QueryLookupRequest = CreateCTXQueryTransactionBody(TestClient3,alternateClientId,ClientTransactionID);
        val QueryTransactionResponse = queryTransactionRequest(QueryLookupRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(QueryTransactionResponse.getBody().asString());
        XmlPath xmlPathQueryLookup = new XmlPath(QueryTransactionResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathQueryLookup.getString("soapenv:Envelope.soapenv:Body.queryTransactionResponse.responseCode"), is(responseCode0));

        removeAllAirtelTestCases(Port.AIRTEL_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);
    }

    @Test
    @Description("CTX :: Purchase : Pending To RetryableDecline To SUCCESS scenario (Vendor 103; Airtel)")
    @TmsLink("MKP-391")
    public void testAirtelPendingToRDtoSuccessScenario() throws InterruptedException{

        val addTestCase1 = setUpAirtelSimData(ResponseCode_500, InFlightTransactionLookupClient.AirTel_purchase);
        val addTestCase2 = setUpAirtelSimData(ResponseCode_206,AirTel_lookup);

        addAirtelTestCases(Arrays.asList(addTestCase1,addTestCase2), Port.AIRTEL_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

        String request = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_19,ProductAirtel_130,ChannelID_07,alternateClientId,Identifier_19,channelSessionId_3133827170);
        val response = validateCustomerAccount(request)
                .then().statusCode(200)
                .extract().response();
        System.out.println(response.getBody().asString());
        XmlPath xmlPath = new XmlPath(response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2240));
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        Thread.sleep(120000);

        String QueryLookupRequest = CreateCTXQueryTransactionBody(TestClient3,alternateClientId,ClientTransactionID);
        val QueryTransactionResponse = queryTransactionRequest(QueryLookupRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(QueryTransactionResponse.getBody().asString());
        XmlPath xmlPathQueryLookup = new XmlPath(QueryTransactionResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathQueryLookup.getString("soapenv:Envelope.soapenv:Body.queryTransactionResponse.responseCode"), is(responseCode2201));

        val addTestCase3 = setUpAirtelSimData(ResponseCode_200, InFlightTransactionLookupClient.AirTel_purchase);
        val addTestCase4 = setUpAirtelSimData(ResponseCode_206,AirTel_lookup);

        addAirtelTestCases(Arrays.asList(addTestCase3,addTestCase4), Port.AIRTEL_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

        char ch='1';
        int pos = 19;

        StringBuilder sb = new StringBuilder(ClientTransactionID);

        sb.setCharAt(pos,ch);
        String ClientTransactionIDLookup=sb.toString();
        System.out.println(ClientTransactionIDLookup);

        String lookupPurchaseRequest = LookupCtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_19,ClientTransactionIDLookup,ProductAirtel_130,ChannelID_07,alternateClientId,Identifier_19,channelSessionId_3133827170);
        val lookupPurchaseResponse = validateCustomerAccount(lookupPurchaseRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(lookupPurchaseResponse.getBody().asString());
        XmlPath lookupPurchaseXmlPath = new XmlPath(lookupPurchaseResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(lookupPurchaseXmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode0));

        removeAllAirtelTestCases(Port.AIRTEL_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);
    }

    @Test
    @Description("CTX :: Purchase : RetryableDecline To SUCCESS (Vendor 103; Airtel)")
    @TmsLink("MKP-519")
    public void testAirtelRDtoSuccessScenario(){

        val addTestCase1 = setUpAirtelSimData(ResponseCode_503, InFlightTransactionLookupClient.AirTel_purchase);
        val addTestCase2 = setUpAirtelSimData(ResponseCode_200,AirTel_lookup);

        addAirtelTestCases(Arrays.asList(addTestCase1,addTestCase2), Port.AIRTEL_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

        String request = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_19,ProductAirtel_130,ChannelID_07,alternateClientId,Identifier_19,channelSessionId_3133827170);
        val response = validateCustomerAccount(request)
                .then().statusCode(200)
                .extract().response();
        System.out.println(response.getBody().asString());
        XmlPath xmlPath = new XmlPath(response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2238));
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        removeAllAirtelTestCases(Port.AIRTEL_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

        char ch='1';
        int pos = 19;

        StringBuilder sb = new StringBuilder(ClientTransactionID);

        sb.setCharAt(pos,ch);
        String ClientTransactionIDLookup=sb.toString();
        System.out.println(ClientTransactionIDLookup);

        String lookupPurchaseRequest = LookupCtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_19,ClientTransactionIDLookup,ProductAirtel_130,ChannelID_07,alternateClientId,Identifier_19,channelSessionId_3133827170);
        val lookupPurchaseResponse = validateCustomerAccount(lookupPurchaseRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(lookupPurchaseResponse.getBody().asString());
        XmlPath lookupPurchaseXmlPath = new XmlPath(lookupPurchaseResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(lookupPurchaseXmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode0));
    }

    @Test
    @Description("CTX :: Purchase : RetryableDecline to NonRetryableDecline scenario (Vendor 103; Airtel)")
    @TmsLink("MKP-369")
    public void testAirtelRDtoNonRetryableDeclineScenario(){

        val addTestCase1 = setUpAirtelSimData(ResponseCode_2238, InFlightTransactionLookupClient.AirTel_purchase);
        val addTestCase2 = setUpAirtelSimData(ResponseCode_200,AirTel_lookup);

        addAirtelTestCases(Arrays.asList(addTestCase1,addTestCase2), Port.AIRTEL_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

        String request = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_19,ProductAirtel_130,ChannelID_07,alternateClientId,Identifier_19,channelSessionId_3133827170);
        val response = validateCustomerAccount(request)
                .then().statusCode(200)
                .extract().response();
        System.out.println(response.getBody().asString());
        XmlPath xmlPath = new XmlPath(response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2201));
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        val addTestCase3 = setUpAirtelSimData(ResponseCode_17017, InFlightTransactionLookupClient.AirTel_purchase);
        val addTestCase4 = setUpAirtelSimData(ResponseCode_200,AirTel_lookup);

        addAirtelTestCases(Arrays.asList(addTestCase3,addTestCase4), Port.AIRTEL_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

        char ch='1';
        int pos = 19;
        StringBuilder sb = new StringBuilder(ClientTransactionID);
        sb.setCharAt(pos,ch);
        String ClientTransactionIDLookup=sb.toString();
        System.out.println(ClientTransactionIDLookup);

        String lookupPurchaseRequest = LookupCtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_19,ClientTransactionIDLookup,ProductAirtel_130,ChannelID_07,alternateClientId,Identifier_19,channelSessionId_3133827170);
        val lookupPurchaseResponse = validateCustomerAccount(lookupPurchaseRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(lookupPurchaseResponse.getBody().asString());
        XmlPath lookupPurchaseXmlPath = new XmlPath(lookupPurchaseResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(lookupPurchaseXmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2213));

        removeAllAirtelTestCases(Port.AIRTEL_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);
    }

    @Test
    @Description("CTX :: Purchase : RetryableDecline To Pending To Success scenario (vendor 103; Airtel)")
    @TmsLink("MKP-377")
    public void testAirtelRDToPendingToSuccessScenario() throws InterruptedException {

        val addTestCase1 = setUpAirtelSimData(ResponseCode_2238, InFlightTransactionLookupClient.AirTel_purchase);
        val addTestCase2 = setUpAirtelSimData(ResponseCode_200,AirTel_lookup);

        addAirtelTestCases(Arrays.asList(addTestCase1,addTestCase2), Port.AIRTEL_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

        String request = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_19,ProductAirtel_130,ChannelID_07,alternateClientId,Identifier_19,channelSessionId_3133827170);
        val response = validateCustomerAccount(request)
                .then().statusCode(200)
                .extract().response();
        System.out.println(response.getBody().asString());
        XmlPath xmlPath = new XmlPath(response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2201));
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        val addTestCase3 = setUpAirtelSimData(ResponseCode_500, InFlightTransactionLookupClient.AirTel_purchase);
        val addTestCase4 = setUpAirtelSimData(ResponseCode_200,AirTel_lookup);

        addAirtelTestCases(Arrays.asList(addTestCase3,addTestCase4), Port.AIRTEL_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

        char ch='1';
        int pos = 19;

        StringBuilder sb = new StringBuilder(ClientTransactionID);

        sb.setCharAt(pos,ch);
        String ClientTransactionIDLookup=sb.toString();
        System.out.println(ClientTransactionIDLookup);

        String lookupPurchaseRequest = LookupCtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_19,ClientTransactionIDLookup,ProductAirtel_130,ChannelID_07,alternateClientId,Identifier_19,channelSessionId_3133827170);
        val lookupPurchaseResponse = validateCustomerAccount(lookupPurchaseRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(lookupPurchaseResponse.getBody().asString());
        XmlPath lookupPurchaseXmlPath = new XmlPath(lookupPurchaseResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(lookupPurchaseXmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2240));

        Thread.sleep(120000);

        String QueryLookupRequest = CreateCTXQueryTransactionBody(TestClient3,alternateClientId,ClientTransactionIDLookup);
        val QueryTransactionResponse = queryTransactionRequest(QueryLookupRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(QueryTransactionResponse.getBody().asString());
        XmlPath xmlPathQueryLookup = new XmlPath(QueryTransactionResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathQueryLookup.getString("soapenv:Envelope.soapenv:Body.queryTransactionResponse.responseCode"), is(responseCode0));

        removeAllAirtelTestCases(Port.AIRTEL_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);
    }

    @Test
    @Description("CTX :: Purchase : NonRetryableDecline scenario (Vendor 21; mwm)")
    @TmsLink("MKP-354")
    public void testMWMNRDScenario(){
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,responseCode2213 , FeeAmount0, ResponseCode_200,Failed,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2213));

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Purchase : SUCCESS scenario (Vendor 21; mwm)")
    @TmsLink("MKP-484")
    public void testMWMSuccessScenario(){
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,FeeAmount0 , FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode0));
    }

    @Test
    @Description("CTX :: Purchase : Pending To Success scenario (Vendor 21; mwm)")
    @TmsLink("MKP-429")
    public void testMWMPendingToSuccessScenario() throws InterruptedException {

        Map bodyVend = SetupSetVendData(Identifier, Identifier,FeeAmount0,responseCode2240 , FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(bodyVend, Vendor21, EnvPort)
                .then().statusCode(200);
        Map bodyLookup = SetupSetVendData(Identifier, Identifier,FeeAmount0,FeeAmount0 , FeeAmount0, FeeAmount0,mwmSuccess,vendorTransactionRefLookup);
        PostControlApiBehaviourLookup(bodyLookup,Vendor21, EnvPort)
                .then().statusCode(200);

        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2240));
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        Thread.sleep(120000);

        String QueryLookupRequest = CreateCTXQueryTransactionBody(TestClient3,alternateClientId,ClientTransactionID);
        val QueryTransactionResponse = queryTransactionRequest(QueryLookupRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(QueryTransactionResponse.getBody().asString());
        XmlPath xmlPathQueryLookup = new XmlPath(QueryTransactionResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathQueryLookup.getString("soapenv:Envelope.soapenv:Body.queryTransactionResponse.responseCode"), is(responseCode0));

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Purchase : Pending to NRD scenario (Vendor 21; mwm)")
    @TmsLink("MKP-411")
    public void testMWMPendingToNRDScenario() throws InterruptedException {

        Map bodyVend = SetupSetVendData(Identifier, Identifier,FeeAmount0,responseCode2240 , FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(bodyVend, Vendor21, EnvPort)
                .then().statusCode(200);
        Map bodyLookup = SetupSetVendData(Identifier, Identifier,FeeAmount0,responseCode2213 , FeeAmount0, FeeAmount0,mwmSuccess,vendorTransactionRefLookup);
        PostControlApiBehaviourLookup(bodyLookup,Vendor21, EnvPort)
                .then().statusCode(200);

        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2240));
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        Thread.sleep(120000);

        String QueryLookupRequest = CreateCTXQueryTransactionBody(TestClient3,alternateClientId,ClientTransactionID);
        val QueryTransactionResponse = queryTransactionRequest(QueryLookupRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(QueryTransactionResponse.getBody().asString());
        XmlPath xmlPathQueryLookup = new XmlPath(QueryTransactionResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathQueryLookup.getString("soapenv:Envelope.soapenv:Body.queryTransactionResponse.responseCode"), is(responseCode2213));

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);

        Map body_Lookup = SetupSetVendData(Identifier, Identifier,FeeAmount0,FeeAmount0 , FeeAmount0, FeeAmount0,mwmSuccess,vendorTransactionRefLookup);
        PostControlApiBehaviourLookup(body_Lookup,Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Purchase : Pending to RetryableDecline to Success scenario (Vendor 21; mwm)")
    @TmsLink("MKP-387")
    public void testMWMPendingToRDToSuccessScenario() throws InterruptedException {

        Map bodyVend = SetupSetVendData(Identifier, Identifier,FeeAmount0,responseCode2240 , FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(bodyVend, Vendor21, EnvPort)
                .then().statusCode(200);
        Map bodyLookup = SetupSetVendData(Identifier, Identifier,FeeAmount0,responseCode2201 , FeeAmount0, FeeAmount0,mwmSuccess,vendorTransactionRefLookup);
        PostControlApiBehaviourLookup(bodyLookup,Vendor21, EnvPort)
                .then().statusCode(200);

        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2240));
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        Thread.sleep(120000);

        String QueryLookupRequest = CreateCTXQueryTransactionBody(TestClient3,alternateClientId,ClientTransactionID);
        val QueryTransactionResponse = queryTransactionRequest(QueryLookupRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(QueryTransactionResponse.getBody().asString());
        XmlPath xmlPathQueryLookup = new XmlPath(QueryTransactionResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathQueryLookup.getString("soapenv:Envelope.soapenv:Body.queryTransactionResponse.responseCode"), is(responseCode2201));

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);

        char ch='1';
        int pos = 19;

        StringBuilder sb = new StringBuilder(ClientTransactionID);

        sb.setCharAt(pos,ch);
        String ClientTransactionIDLookup=sb.toString();
        System.out.println(ClientTransactionIDLookup);

        String lookupPurchaseRequest = LookupCtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ClientTransactionIDLookup,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val lookupPurchaseResponse = validateCustomerAccount(lookupPurchaseRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(lookupPurchaseResponse.getBody().asString());
        XmlPath lookupPurchaseXmlPath = new XmlPath(lookupPurchaseResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(lookupPurchaseXmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode0));

        Map body_Lookup = SetupSetVendData(Identifier, Identifier,FeeAmount0,FeeAmount0 , FeeAmount0, FeeAmount0,mwmSuccess,vendorTransactionRefLookup);
        PostControlApiBehaviourLookup(body_Lookup,Vendor21, EnvPort)
                .then().statusCode(200);

    }

    @Test
    @Description("CTX :: Purchase : RetryableDecline to Success scenario(Vendor 21; mwm)")
    @TmsLink("MKP-390")
    public void testMWMRDToSuccessScenario(){

        Map bodyVend = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2238, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(bodyVend, Vendor21, EnvPort)
                .then().statusCode(200);

        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2238));
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        Map Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(Success, Vendor21, EnvPort)
                .then().statusCode(200);

        char ch='1';
        int pos = 19;

        StringBuilder sb = new StringBuilder(ClientTransactionID);

        sb.setCharAt(pos,ch);
        String ClientTransactionIDLookup=sb.toString();
        System.out.println(ClientTransactionIDLookup);

        String lookupPurchaseRequest = LookupCtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ClientTransactionIDLookup,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val lookupPurchaseResponse = validateCustomerAccount(lookupPurchaseRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(lookupPurchaseResponse.getBody().asString());
        XmlPath lookupPurchaseXmlPath = new XmlPath(lookupPurchaseResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(lookupPurchaseXmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode0));
    }

    @Test
    @Description("CTX :: Purchase : RetryableDecline to NonRetryableDecline scenario (Vendor 21; mwm)")
    @TmsLink("MKP-497")
    public void testMWMRDToNRDScenario(){

        Map bodyVend = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2238, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(bodyVend, Vendor21, EnvPort)
                .then().statusCode(200);

        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2238));
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        Map bodyNonRetryabaleDecline = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2213, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(bodyNonRetryabaleDecline, Vendor21, EnvPort)
                .then().statusCode(200);

        char ch='1';
        int pos = 19;

        StringBuilder sb = new StringBuilder(ClientTransactionID);

        sb.setCharAt(pos,ch);
        String ClientTransactionIDLookup=sb.toString();
        System.out.println(ClientTransactionIDLookup);

        String lookupPurchaseRequest = LookupCtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ClientTransactionIDLookup,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val lookupPurchaseResponse = validateCustomerAccount(lookupPurchaseRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(lookupPurchaseResponse.getBody().asString());
        XmlPath lookupPurchaseXmlPath = new XmlPath(lookupPurchaseResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(lookupPurchaseXmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2213));

        Map Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Purchase : RetryableDecline to Pending to Success scenario (Vendor 21; mwm)")
    @TmsLink("MKP-359")
    public void testMWMRDToPendingToSuccessScenario() throws InterruptedException {

        Map bodyVend = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2238, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(bodyVend, Vendor21, EnvPort)
                .then().statusCode(200);

        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2238));
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        Map bodyVendPending = SetupSetVendData(Identifier, Identifier,FeeAmount0,responseCode2240 , FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(bodyVendPending, Vendor21, EnvPort)
                .then().statusCode(200);
        Map bodyLookup = SetupSetVendData(Identifier, Identifier,FeeAmount0,FeeAmount0 , FeeAmount0, FeeAmount0,mwmSuccess,vendorTransactionRefLookup);
        PostControlApiBehaviourLookup(bodyLookup,Vendor21, EnvPort)
                .then().statusCode(200);

        char ch='1';
        int pos = 19;

        StringBuilder sb = new StringBuilder(ClientTransactionID);

        sb.setCharAt(pos,ch);
        String ClientTransactionIDLookup=sb.toString();
        System.out.println(ClientTransactionIDLookup);

        String lookupPurchaseRequest = LookupCtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ClientTransactionIDLookup,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val lookupPurchaseResponse = validateCustomerAccount(lookupPurchaseRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(lookupPurchaseResponse.getBody().asString());
        XmlPath lookupPurchaseXmlPath = new XmlPath(lookupPurchaseResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(lookupPurchaseXmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2240));

        Thread.sleep(120000);

        String QueryLookupRequest = CreateCTXQueryTransactionBody(TestClient3,alternateClientId,ClientTransactionIDLookup);
        val QueryTransactionResponse = queryTransactionRequest(QueryLookupRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(QueryTransactionResponse.getBody().asString());
        XmlPath xmlPathQueryLookup = new XmlPath(QueryTransactionResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathQueryLookup.getString("soapenv:Envelope.soapenv:Body.queryTransactionResponse.responseCode"), is(responseCode0));

        Map Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(Success, Vendor21, EnvPort)
                .then().statusCode(200);

    }

    @Test
    @Description("CTX :: Purchase :: Success scenario (vendor 100; MTN-NG)")
    @TmsLink("MKP-436")
    public void testMTNNGSuccessScenario() throws Exception {

        setSimulatorExpectedState(vendorMTNNGSuccess);
        String request = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val Response = validateCustomerAccount(request)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());
        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode0));
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);
    }

    @Test
    @Description("CTX :: Purchase :: Non-Retryable decline scenario (vendor 100; MTN-NG)")
    @TmsLink("MKP-475")
    public void testMTNNGNRDScenario() throws Exception{

        setSimulatorExpectedState(vendorMTNNGErrorServer);
        String request = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val Response = validateCustomerAccount(request)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());
        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2228));
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorMTNNGSuccess);
    }

    @Test
    @Description("CTX :: Purchase :: Pending To Success scenario (vendor 100; MTN-NG)")
    @TmsLink("MKP-485")
    public void testMTNNGPendingToSuccessScenario() throws Exception {

        setSimulatorExpectedState(vendorMTNNGException);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2240));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorMTNNGSuccess);
        Thread.sleep(60000);
        String QueryLookupRequest = CreateCTXQueryTransactionBody(TestClient3,alternateClientId,ClientTransactionID);
        val QueryTransactionResponse = queryTransactionRequest(QueryLookupRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(QueryTransactionResponse.getBody().asString());
        XmlPath xmlPathQueryLookup = new XmlPath(QueryTransactionResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathQueryLookup.getString("soapenv:Envelope.soapenv:Body.queryTransactionResponse.responseCode"), is(responseCode0));
    }



    @Test
    @Description("CTX :: Purchase :: Pending To NonRetryableDecline scenario (vendor 100; MTN-NG)")
    @TmsLink("MKP-358")
    public void testMTNNGPendingToNRDScenario() throws Exception{

        setSimulatorExpectedState(vendorMTNNGException);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2240));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorMTNNGErrorServer);

        Thread.sleep(60000);
        String QueryLookupRequest = CreateCTXQueryTransactionBody(TestClient3,alternateClientId,ClientTransactionID);
        val QueryTransactionResponse = queryTransactionRequest(QueryLookupRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(QueryTransactionResponse.getBody().asString());
        XmlPath xmlPathQueryLookup = new XmlPath(QueryTransactionResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathQueryLookup.getString("soapenv:Envelope.soapenv:Body.queryTransactionResponse.responseCode"), is(ResponseCode_2228));

        setSimulatorExpectedState(vendorMTNNGSuccess);
    }

    @Test
    @Description("CTX :: Purchase :: RetryableDecline to Success scenario (vendor 100; MTN-NG)")
    @TmsLink("MKP-364")
    public void testMTNNGRDToSuccessScenario() throws Exception {

        setSimulatorExpectedState(vendorMTNNGErrorINVORIGACC);
        String request = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val Response = validateCustomerAccount(request)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());
        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2201));
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorMTNNGSuccess);

        char ch='1';
        int pos = 19;

        StringBuilder sb = new StringBuilder(ClientTransactionID);

        sb.setCharAt(pos,ch);
        String ClientTransactionIDLookup=sb.toString();
        System.out.println(ClientTransactionIDLookup);

        String lookupPurchaseRequest = LookupCtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ClientTransactionIDLookup,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val lookupPurchaseResponse = validateCustomerAccount(lookupPurchaseRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(lookupPurchaseResponse.getBody().asString());
        XmlPath lookupPurchaseXmlPath = new XmlPath(lookupPurchaseResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(lookupPurchaseXmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode0));

    }

    @Test
    @Description("CTX :: Purchase :: RetryableDecline to NonRetryableDecline scenario (vendor 100; MTN-NG)")
    @TmsLink("MKP-363")
    public void testMTNNGRDToNRDScenario() throws Exception {

        setSimulatorExpectedState(vendorMTNNGErrorINVORIGACC);
        String request = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val Response = validateCustomerAccount(request)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());
        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2201));
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorMTNNGErrorServer);

        char ch='1';
        int pos = 19;

        StringBuilder sb = new StringBuilder(ClientTransactionID);

        sb.setCharAt(pos,ch);
        String ClientTransactionIDLookup=sb.toString();
        System.out.println(ClientTransactionIDLookup);

        String lookupPurchaseRequest = LookupCtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ClientTransactionIDLookup,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val lookupPurchaseResponse = validateCustomerAccount(lookupPurchaseRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(lookupPurchaseResponse.getBody().asString());
        XmlPath lookupPurchaseXmlPath = new XmlPath(lookupPurchaseResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(lookupPurchaseXmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2228));
        setSimulatorExpectedState(vendorMTNNGSuccess);
    }

    @Test
    @Description("CTX :: Purchase :: RetryableDecline>>Pending>>Success scenario (vendor 100; MTN-NG)")
    @TmsLink("MKP-399")
    public void testMTNNGRDToPendingToSuccessScenario() throws Exception {

        setSimulatorExpectedState(vendorMTNNGErrorINVORIGACC);
        String request = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val Response = validateCustomerAccount(request)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());
        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2201));
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorMTNNGException);

        char ch='1';
        int pos = 19;

        StringBuilder sb = new StringBuilder(ClientTransactionID);

        sb.setCharAt(pos,ch);
        String ClientTransactionIDLookup=sb.toString();
        System.out.println(ClientTransactionIDLookup);
        String lookupPurchaseRequest = LookupCtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ClientTransactionIDLookup,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val lookupPurchaseResponse = validateCustomerAccount(lookupPurchaseRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(lookupPurchaseResponse.getBody().asString());
        XmlPath lookupPurchaseXmlPath = new XmlPath(lookupPurchaseResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(lookupPurchaseXmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2240));

        setSimulatorExpectedState(vendorMTNNGSuccess);
        char ch2='2';
        int pos2 = 19;

        StringBuilder sb2 = new StringBuilder(ClientTransactionIDLookup);

        sb2.setCharAt(pos2,ch2);
        String ClientTransactionIDLookup2=sb2.toString();
        System.out.println(ClientTransactionIDLookup2);
        String lookupPurchaseRequest2 = LookupCtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ClientTransactionIDLookup2,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val lookupPurchaseResponse2 = validateCustomerAccount(lookupPurchaseRequest2)
                .then().statusCode(200)
                .extract().response();

        System.out.println(lookupPurchaseResponse2.getBody().asString());
        XmlPath lookupPurchase2XmlPath = new XmlPath(lookupPurchaseResponse2.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(lookupPurchase2XmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode0));
    }

    @Test
    @Description("CTX :: Success scenario (vendor 101; glo)")
    @TmsLink("MKP-355")
    public void testGLOSuccessScenario() throws Exception {

        setSimulatorExpectedState(vendorGLOSuccess);
        String request = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,Product_Glo_110,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val Response = validateCustomerAccount(request)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());
        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode0));
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);
    }

    @Test
    @Description("CTX :: Non-Retryable Decline Scenario (vendor 101; glo)")
    @TmsLink("MKP-415")
    public void testGLONRDScenario() throws Exception {

        setSimulatorExpectedState(vendorGLOErrorDUPTRANAPROV);
        String request = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,Product_Glo_110,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val Response = validateCustomerAccount(request)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());
        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2222));
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorGLOSuccess);
    }

    @Test
    @Description("CTX :: RetryableDecline to Success scenario (Vendor 101; GLO)")
    @TmsLink("MKP-385")
    public void testGLORDToSuccessScenario() throws Exception {

        setSimulatorExpectedState(vendorGLOErrorProtocol);
        String request = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,Product_Glo_110,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val Response = validateCustomerAccount(request)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());
        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2201));
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorGLOSuccess);

        char ch='1';
        int pos = 19;

        StringBuilder sb = new StringBuilder(ClientTransactionID);

        sb.setCharAt(pos,ch);
        String ClientTransactionIDLookup=sb.toString();
        System.out.println(ClientTransactionIDLookup);

        String lookupPurchaseRequest = LookupCtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ClientTransactionIDLookup,Product_Glo_110,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val lookupPurchaseResponse = validateCustomerAccount(lookupPurchaseRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(lookupPurchaseResponse.getBody().asString());
        XmlPath lookupPurchaseXmlPath = new XmlPath(lookupPurchaseResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(lookupPurchaseXmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode0));
    }

    @Test
    @Description("CTX :: RetryableDecline to Non-Retryable decline scenario (Vendor 101; GLO)")
    @TmsLink("MKP-410")
    public void testGLORDToNRDScenario() throws Exception {

        setSimulatorExpectedState(vendorGLOErrorProtocol);
        String request = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,Product_Glo_110,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val Response = validateCustomerAccount(request)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());
        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2201));
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorGLOErrorDUPTRANAPROV);
        char ch='1';
        int pos = 19;

        StringBuilder sb = new StringBuilder(ClientTransactionID);

        sb.setCharAt(pos,ch);
        String ClientTransactionIDLookup=sb.toString();
        System.out.println(ClientTransactionIDLookup);

        String lookupPurchaseRequest = LookupCtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ClientTransactionIDLookup,Product_Glo_110,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val lookupPurchaseResponse = validateCustomerAccount(lookupPurchaseRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(lookupPurchaseResponse.getBody().asString());
        XmlPath lookupPurchaseXmlPath = new XmlPath(lookupPurchaseResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(lookupPurchaseXmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2222));

        setSimulatorExpectedState(vendorGLOSuccess);
    }

    @Test
    @Description("CTX :: Success scenario (vendor 102; 9mobile; etisalat)")
    @TmsLink("MKP-403")
    public void testETISALATSuccessScenario() throws Exception {
        setSimulatorExpectedState(vendorETISALATSuccess);
        String request = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,Product_Etisalat_120,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val Response = validateCustomerAccount(request)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());
        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode0));
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);
    }

    @Test
    @Description("CTX :: Non-Retryable Decline Scenario (vendor 102; 9mobile; etisalat)")
    @TmsLink("TECH-181989")
    public void testETISALATNRDScenario() throws Exception {
        setSimulatorExpectedState(vendorETISALATErrorTimeOUT);
        String request = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,Product_Etisalat_120,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val Response = validateCustomerAccount(request)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());
        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2224));
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorETISALATSuccess);
    }

    @Test
    @Description("CTX :: RetryableDecline to Success Scenario (vendor 102; 9mobile; etisalat)")
    @TmsLink("MKP-428")
    public void testETISALATRDToSuccessScenario() throws Exception {
        setSimulatorExpectedState(vendorETISALATErrorSYSVOL);
        String request = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,Product_Etisalat_120,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val Response = validateCustomerAccount(request)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());
        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2225));
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorETISALATSuccess);
        char ch='1';
        int pos = 19;

        StringBuilder sb = new StringBuilder(ClientTransactionID);

        sb.setCharAt(pos,ch);
        String ClientTransactionIDLookup=sb.toString();
        System.out.println(ClientTransactionIDLookup);

        String lookupPurchaseRequest = LookupCtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ClientTransactionIDLookup,Product_Etisalat_120,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val lookupPurchaseResponse = validateCustomerAccount(lookupPurchaseRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(lookupPurchaseResponse.getBody().asString());
        XmlPath lookupPurchaseXmlPath = new XmlPath(lookupPurchaseResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(lookupPurchaseXmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode0));

    }

    @Test
    @Description("CTX :: RetryableDecline to Non-RetryableDecline Scenario (vendor 102; 9mobile; etisalat)")
    @TmsLink("MKP-344")
    public void testETISALATRDToNRDScenario() throws Exception {

        setSimulatorExpectedState(vendorETISALATErrorSYSVOL);
        String request = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,Product_Etisalat_120,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val Response = validateCustomerAccount(request)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());
        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2225));
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorETISALATErrorTimeOUT);
        char ch='1';
        int pos = 19;

        StringBuilder sb = new StringBuilder(ClientTransactionID);

        sb.setCharAt(pos,ch);
        String ClientTransactionIDLookup=sb.toString();
        System.out.println(ClientTransactionIDLookup);

        String lookupPurchaseRequest = LookupCtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ClientTransactionIDLookup,Product_Etisalat_120,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val lookupPurchaseResponse = validateCustomerAccount(lookupPurchaseRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(lookupPurchaseResponse.getBody().asString());
        XmlPath lookupPurchaseXmlPath = new XmlPath(lookupPurchaseResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(lookupPurchaseXmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2224));

        setSimulatorExpectedState(vendorETISALATSuccess);
    }

    @Test
    @Description("CTX :: Success scenario (vendor 3; MTNZA)")
    @TmsLink("MKP-365")
    public void testMTNZASuccessScenario(){

        val addTestCase1 = setUpMtnSimData("0", "27837640171", "virtual_recharge", 200);
        val addTestCase2 = setUpMtnSimData("0", "27837640171", "bundle_recharge", 200);
        val addTestCase3 = setUpMtnSimData("0", "27837640171", "repeat_virtual_recharge", 200);
        val addTestCase4 = setUpMtnSimData("0", "27837640171", "repeat_bundle_recharge", 200);

        addMtnTestCases(Arrays.asList(addTestCase1, addTestCase2, addTestCase3, addTestCase4), Port.MTN_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

        String request = CtxCreateBodyClient2(TestClient2,PurchaseAmount10000,Identifier_4,ProductMTN_ZA_400,ChannelID_07,alternateClientId,Identifier_4,channelSessionId_3133827176);
        val response = validateCustomerAccount(request)
                .then().statusCode(200)
                .extract().response();
        System.out.println(response.getBody().asString());
        XmlPath xmlPath = new XmlPath(response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode0));
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);
    }

    @Test
    @Description("CTX :: Non-Retryable Decline scenario (vendor 3; MTNZA)")
    @TmsLink("MKP-383")
    public void testMTNZA_NRDScenario(){

        val addTestCase1 = setUpMtnSimData("9313", "27837640171", "virtual_recharge", 200);
        val addTestCase2 = setUpMtnSimData("9313", "27837640171", "bundle_recharge", 200);
        val addTestCase3 = setUpMtnSimData("0", "27837640171", "repeat_virtual_recharge", 200);
        val addTestCase4 = setUpMtnSimData("0", "27837640171", "repeat_bundle_recharge", 200);

        addMtnTestCases(Arrays.asList(addTestCase1, addTestCase2, addTestCase3, addTestCase4), Port.MTN_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

        String request = CtxCreateBodyClient2(TestClient2,PurchaseAmount10000,Identifier_4,ProductMTN_ZA_400,ChannelID_07,alternateClientId,Identifier_4,channelSessionId_3133827176);
        val response = validateCustomerAccount(request)
                .then().statusCode(200)
                .extract().response();
        System.out.println(response.getBody().asString());
        XmlPath xmlPath = new XmlPath(response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2213));
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        removeAllMtnTestCases(Port.MTN_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);
    }

    @Test
    @Description("CTX :: Pending to Success scenario (vendor 3; MTNZA)")
    @TmsLink("MKP-373")
    public void testMTNZAPendingToSuccessScenario() throws InterruptedException {

        val addTestCase1 = setUpMtnSimData("9318", "27837640171", "virtual_recharge", 200);
        val addTestCase2 = setUpMtnSimData("9318", "27837640171", "bundle_recharge", 200);
        val addTestCase3 = setUpMtnSimData("0", "27837640171", "repeat_virtual_recharge", 200);
        val addTestCase4 = setUpMtnSimData("0", "27837640171", "repeat_bundle_recharge", 200);

        addMtnTestCases(Arrays.asList(addTestCase1, addTestCase2, addTestCase3, addTestCase4), Port.MTN_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

        String request = CtxCreateBodyClient2(TestClient2,PurchaseAmount10000,Identifier_4,ProductMTN_ZA_400,ChannelID_07,alternateClientId,Identifier_4,channelSessionId_3133827176);
        val response = validateCustomerAccount(request)
                .then().statusCode(200)
                .extract().response();
        System.out.println(response.getBody().asString());
        XmlPath xmlPath = new XmlPath(response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2236));
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        Thread.sleep(120000);
        String QueryLookupRequest = CreateCTXQueryTransactionBody(TestClient2,alternateClientId,ClientTransactionID);
        val QueryTransactionResponse = queryTransactionRequest(QueryLookupRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(QueryTransactionResponse.getBody().asString());
        XmlPath xmlPathQueryLookup = new XmlPath(QueryTransactionResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathQueryLookup.getString("soapenv:Envelope.soapenv:Body.queryTransactionResponse.responseCode"), is(responseCode0));

        removeAllMtnTestCases(Port.MTN_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

    }

    @Test
    @Description("CTX :: Pending to NonRetryableDecline scenario (vendor 3; MTNZA)")
    @TmsLink("MKP-487")
    public void testMTNZAPendingToNRDScenario() throws InterruptedException {

        val addTestCase1 = setUpMtnSimData("9318", "27837640171", "virtual_recharge", 200);
        val addTestCase2 = setUpMtnSimData("9318", "27837640171", "bundle_recharge", 200);
        val addTestCase3 = setUpMtnSimData("9313", "27837640171", "repeat_virtual_recharge", 200);
        val addTestCase4 = setUpMtnSimData("9313", "27837640171", "repeat_bundle_recharge", 200);

        addMtnTestCases(Arrays.asList(addTestCase1, addTestCase2, addTestCase3, addTestCase4), Port.MTN_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

        String request = CtxCreateBodyClient2(TestClient2,PurchaseAmount10000,Identifier_4,ProductMTN_ZA_400,ChannelID_07,alternateClientId,Identifier_4,channelSessionId_3133827176);
        val response = validateCustomerAccount(request)
                .then().statusCode(200)
                .extract().response();
        System.out.println(response.getBody().asString());
        XmlPath xmlPath = new XmlPath(response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2236));
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        Thread.sleep(120000);
        String QueryLookupRequest = CreateCTXQueryTransactionBody(TestClient2,alternateClientId,ClientTransactionID);
        val QueryTransactionResponse = queryTransactionRequest(QueryLookupRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(QueryTransactionResponse.getBody().asString());
        XmlPath xmlPathQueryLookup = new XmlPath(QueryTransactionResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathQueryLookup.getString("soapenv:Envelope.soapenv:Body.queryTransactionResponse.responseCode"), is(responseCode2213));

        removeAllMtnTestCases(Port.MTN_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);
    }

    @Test
    @Description("CTX :: Pending to RetryableDecline to Success scenario (vendor 3; MTNZA)")
    @TmsLink("MKP-510")
    public void testMTNZAPendingToRDToSuccessScenario() throws InterruptedException {

        val addTestCase1 = setUpMtnSimData("9318", "27837640171", "virtual_recharge", 200);
        val addTestCase2 = setUpMtnSimData("9318", "27837640171", "bundle_recharge", 200);
        val addTestCase3 = setUpMtnSimData("3803", "27837640171", "repeat_virtual_recharge", 200);
        val addTestCase4 = setUpMtnSimData("3803", "27837640171", "repeat_bundle_recharge", 200);

        addMtnTestCases(Arrays.asList(addTestCase1, addTestCase2, addTestCase3, addTestCase4), Port.MTN_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

        String request = CtxCreateBodyClient2(TestClient2,PurchaseAmount10000,Identifier_4,ProductMTN_ZA_400,ChannelID_07,alternateClientId,Identifier_4,channelSessionId_3133827176);
        val response = validateCustomerAccount(request)
                .then().statusCode(200)
                .extract().response();
        System.out.println(response.getBody().asString());
        XmlPath xmlPath = new XmlPath(response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2236));
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        Thread.sleep(120000);
        String QueryLookupRequest = CreateCTXQueryTransactionBody(TestClient2,alternateClientId,ClientTransactionID);
        val QueryTransactionResponse = queryTransactionRequest(QueryLookupRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(QueryTransactionResponse.getBody().asString());
        XmlPath xmlPathQueryLookup = new XmlPath(QueryTransactionResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathQueryLookup.getString("soapenv:Envelope.soapenv:Body.queryTransactionResponse.responseCode"), is(responseCode2201));

        val addTestCase5 = setUpMtnSimData("0", "27837640171", "virtual_recharge", 200);
        val addTestCase6 = setUpMtnSimData("0", "27837640171", "bundle_recharge", 200);
        val addTestCase7 = setUpMtnSimData("3803", "27837640171", "repeat_virtual_recharge", 200);
        val addTestCase8 = setUpMtnSimData("3803", "27837640171", "repeat_bundle_recharge", 200);

        addMtnTestCases(Arrays.asList(addTestCase5, addTestCase6, addTestCase7, addTestCase8), Port.MTN_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

        char ch='1';
        int pos = 28;

        StringBuilder sb = new StringBuilder(ClientTransactionID);

        sb.setCharAt(pos,ch);
        String ClientTransactionIDLookup=sb.toString();
        System.out.println(ClientTransactionIDLookup);

        String lookupPurchaseRequest = LookupCtxCreateBody(TestClient2,PurchaseAmount10000,Identifier_4,ClientTransactionIDLookup,ProductMTN_ZA_400,ChannelID_07,alternateClientId,Identifier_4,channelSessionId_3133827176);
        val lookupPurchaseResponse = validateCustomerAccount(lookupPurchaseRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(lookupPurchaseResponse.getBody().asString());
        XmlPath lookupPurchaseXmlPath = new XmlPath(lookupPurchaseResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(lookupPurchaseXmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode0));

        removeAllMtnTestCases(Port.MTN_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);
    }

    @Test
    @Description("CTX :: Retryable Decline to Success scenario (vendor 3; MTNZA)")
    @TmsLink("MKP-454")
    public void testMTNZARDToSuccessScenario(){

        val addTestCase1 = setUpMtnSimData("9306", "27837640171", "virtual_recharge", 200);
        val addTestCase2 = setUpMtnSimData("9306", "27837640171", "bundle_recharge", 200);
        val addTestCase3 = setUpMtnSimData("0", "27837640171", "repeat_virtual_recharge", 200);
        val addTestCase4 = setUpMtnSimData("0", "27837640171", "repeat_bundle_recharge", 200);

        addMtnTestCases(Arrays.asList(addTestCase1, addTestCase2, addTestCase3, addTestCase4), Port.MTN_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

        String request = CtxCreateBodyClient2(TestClient2,PurchaseAmount10000,Identifier_4,ProductMTN_ZA_400,ChannelID_07,alternateClientId,Identifier_4,channelSessionId_3133827176);
        val response = validateCustomerAccount(request)
                .then().statusCode(200)
                .extract().response();
        System.out.println(response.getBody().asString());
        XmlPath xmlPath = new XmlPath(response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2238));
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        removeAllMtnTestCases(Port.MTN_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

        char ch='1';
        int pos = 28;

        StringBuilder sb = new StringBuilder(ClientTransactionID);

        sb.setCharAt(pos,ch);
        String ClientTransactionIDLookup=sb.toString();
        System.out.println(ClientTransactionIDLookup);

        String lookupPurchaseRequest = LookupCtxCreateBody(TestClient2,PurchaseAmount10000,Identifier_4,ClientTransactionIDLookup,ProductMTN_ZA_400,ChannelID_07,alternateClientId,Identifier_4,channelSessionId_3133827176);
        val lookupPurchaseResponse = validateCustomerAccount(lookupPurchaseRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(lookupPurchaseResponse.getBody().asString());
        XmlPath lookupPurchaseXmlPath = new XmlPath(lookupPurchaseResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(lookupPurchaseXmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode0));
    }

    @Test
    @Description("CTX :: Retryable Decline to Non-RetryableDeclinescenario (vendor 3; MTNZA)")
    @TmsLink("MKP-466")
    public void testMTNZARDToNRDScenario(){

        val addTestCase1 = setUpMtnSimData("9306", "27837640171", "virtual_recharge", 200);
        val addTestCase2 = setUpMtnSimData("9306", "27837640171", "bundle_recharge", 200);
        val addTestCase3 = setUpMtnSimData("0", "27837640171", "repeat_virtual_recharge", 200);
        val addTestCase4 = setUpMtnSimData("0", "27837640171", "repeat_bundle_recharge", 200);

        addMtnTestCases(Arrays.asList(addTestCase1, addTestCase2, addTestCase3, addTestCase4), Port.MTN_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

        String request = CtxCreateBodyClient2(TestClient2,PurchaseAmount10000,Identifier_4,ProductMTN_ZA_400,ChannelID_07,alternateClientId,Identifier_4,channelSessionId_3133827176);
        val response = validateCustomerAccount(request)
                .then().statusCode(200)
                .extract().response();
        System.out.println(response.getBody().asString());
        XmlPath xmlPath = new XmlPath(response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2238));
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        val addTestCase5 = setUpMtnSimData("9313", "27837640171", "virtual_recharge", 200);
        val addTestCase6 = setUpMtnSimData("9313", "27837640171", "bundle_recharge", 200);
        val addTestCase7 = setUpMtnSimData("0", "27837640171", "repeat_virtual_recharge", 200);
        val addTestCase8 = setUpMtnSimData("0", "27837640171", "repeat_bundle_recharge", 200);

        addMtnTestCases(Arrays.asList(addTestCase5, addTestCase6, addTestCase7, addTestCase8), Port.MTN_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

        char ch='1';
        int pos = 28;

        StringBuilder sb = new StringBuilder(ClientTransactionID);

        sb.setCharAt(pos,ch);
        String ClientTransactionIDLookup=sb.toString();
        System.out.println(ClientTransactionIDLookup);

        String lookupPurchaseRequest = LookupCtxCreateBody(TestClient2,PurchaseAmount10000,Identifier_4,ClientTransactionIDLookup,ProductMTN_ZA_400,ChannelID_07,alternateClientId,Identifier_4,channelSessionId_3133827176);
        val lookupPurchaseResponse = validateCustomerAccount(lookupPurchaseRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(lookupPurchaseResponse.getBody().asString());
        XmlPath lookupPurchaseXmlPath = new XmlPath(lookupPurchaseResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(lookupPurchaseXmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2213));

        removeAllMtnTestCases(Port.MTN_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);
    }

    @Test
    @Description("CTX :: RetryableDecline to Pending to Success scenario (vendor 3; MTNZA)")
    @TmsLink("MKP-512")
    public void testMTNZARDToPendingToSuccessScenario() throws InterruptedException {
        val addTestCase1 = setUpMtnSimData("3803", "27837640171", "virtual_recharge", 200);
        val addTestCase2 = setUpMtnSimData("3803", "27837640171", "bundle_recharge", 200);
        val addTestCase3 = setUpMtnSimData("3803", "27837640171", "repeat_virtual_recharge", 200);
        val addTestCase4 = setUpMtnSimData("3803", "27837640171", "repeat_bundle_recharge", 200);

        addMtnTestCases(Arrays.asList(addTestCase1, addTestCase2, addTestCase3, addTestCase4), Port.MTN_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

        String request = CtxCreateBodyClient2(TestClient2,PurchaseAmount10000,Identifier_4,ProductMTN_ZA_400,ChannelID_07,alternateClientId,Identifier_4,channelSessionId_3133827176);
        val response = validateCustomerAccount(request)
                .then().statusCode(200)
                .extract().response();
        System.out.println(response.getBody().asString());
        XmlPath xmlPath = new XmlPath(response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2201));
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);


        val addTestCase5 = setUpMtnSimData("9318", "27837640171", "virtual_recharge", 200);
        val addTestCase6 = setUpMtnSimData("9318", "27837640171", "bundle_recharge", 200);
        val addTestCase7 = setUpMtnSimData("0", "27837640171", "repeat_virtual_recharge", 200);
        val addTestCase8 = setUpMtnSimData("0", "27837640171", "repeat_bundle_recharge", 200);

        addMtnTestCases(Arrays.asList(addTestCase5, addTestCase6, addTestCase7, addTestCase8), Port.MTN_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);


        char ch='1';
        int pos = 28;

        StringBuilder sb = new StringBuilder(ClientTransactionID);

        sb.setCharAt(pos,ch);
        String ClientTransactionIDLookup=sb.toString();
        System.out.println(ClientTransactionIDLookup);

        String lookupPurchaseRequest = LookupCtxCreateBody(TestClient2,PurchaseAmount10000,Identifier_4,ClientTransactionIDLookup,ProductMTN_ZA_400,ChannelID_07,alternateClientId,Identifier_4,channelSessionId_3133827176);
        val lookupPurchaseResponse = validateCustomerAccount(lookupPurchaseRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(lookupPurchaseResponse.getBody().asString());
        XmlPath lookupPurchaseXmlPath = new XmlPath(lookupPurchaseResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(lookupPurchaseXmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2236));

        removeAllMtnTestCases(Port.MTN_SIMULATOR)
                .then().assertThat().statusCode(SC_OK);

        Thread.sleep(120000);

        String QueryLookupRequest = CreateCTXQueryTransactionBody(TestClient2,alternateClientId,ClientTransactionIDLookup);
        val QueryTransactionResponse = queryTransactionRequest(QueryLookupRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(QueryTransactionResponse.getBody().asString());
        XmlPath xmlPathQueryLookup = new XmlPath(QueryTransactionResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathQueryLookup.getString("soapenv:Envelope.soapenv:Body.queryTransactionResponse.responseCode"), is(responseCode0));

    }















}




