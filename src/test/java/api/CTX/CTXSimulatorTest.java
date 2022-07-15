package api.CTX;

import api.clients.BasedAPIClient;
import api.clients.CTXSimulatorClient;
import io.qameta.allure.TmsLink;
import io.restassured.path.xml.XmlPath;
import lombok.val;
import org.springframework.context.annotation.Description;
import org.testng.annotations.Test;

import static api.clients.CTX.*;
import static api.clients.CTXSimulatorClient.*;
import static api.clients.NotificationClient.Identifier_6;
import static api.clients.ReserveAndTransactClient.*;
import static api.domains.CTX.CTX.CreateCTXQueryTransactionBody;
import static api.domains.CTX.CTX.CtxCreateBody;
import static io.restassured.path.xml.config.XmlPathConfig.xmlPathConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CTXSimulatorTest extends BasedAPIClient {

    @Test
    @Description("ctx-simulator :: lookup :: MTNNG SUCCESS 0")
    @TmsLink("TECH-193458")
    public void testMTNNGLookupSuccessCode0 () throws Exception {
        CTXSimulatorClient.setSimulatorExpectedState(vendorMTNNGException);
        String ExceptionRequest = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ExceptionResponse = validateCustomerAccount(ExceptionRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ExceptionResponse.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ExceptionResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2240));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);


        setSimulatorExpectedState(vendorMTNNGSuccess);
        Thread.sleep(30000);
        String QueryLookupRequest = CreateCTXQueryTransactionBody(TestClient3,alternateClientId,ClientTransactionID);
        val QueryTransactionResponse = queryTransactionRequest(QueryLookupRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(QueryTransactionResponse.getBody().asString());
        XmlPath xmlPathQueryLookup = new XmlPath(QueryTransactionResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathQueryLookup.getString("soapenv:Envelope.soapenv:Body.queryTransactionResponse.responseCode"), is(responseCode0));
    }

    @Test
    @Description("ctx-simulator :: lookup :: MTNG ERROR_NO_TRX 2201")
    @TmsLink("TECH-193475")
    public void testMTNNGLookupErrorNoTRX2201() throws Exception {
        CTXSimulatorClient.setSimulatorExpectedState(vendorMTNNGException);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2240));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        CTXSimulatorClient.setSimulatorExpectedState(vendorMTNNGErrorNO_TRX);
        Thread.sleep(30000);
        String QueryLookupRequest = CreateCTXQueryTransactionBody(TestClient3,alternateClientId,ClientTransactionID);
        val QueryTransactionResponse = queryTransactionRequest(QueryLookupRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(QueryTransactionResponse.getBody().asString());
        XmlPath xmlPathQueryLookup = new XmlPath(QueryTransactionResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathQueryLookup.getString("soapenv:Envelope.soapenv:Body.queryTransactionResponse.responseCode"), is(responseCode2201));

        setSimulatorExpectedState(vendorMTNNGSuccess);
    }

    @Test
    @Description("ctx-simulator :: lookup :: MTNNG ERROR_FORMAT 2201")
    @TmsLink("TECH-193474")
    public void testMTNNGLookupErrorFormat2201() throws Exception {
        CTXSimulatorClient.setSimulatorExpectedState(vendorMTNNGException);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2240));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        CTXSimulatorClient.setSimulatorExpectedState(vendorMTNNGErrorFORMAT);
        Thread.sleep(30000);
        String QueryLookupRequest = CreateCTXQueryTransactionBody(TestClient3,alternateClientId,ClientTransactionID);
        val QueryTransactionResponse = queryTransactionRequest(QueryLookupRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(QueryTransactionResponse.getBody().asString());
        XmlPath xmlPathQueryLookup = new XmlPath(QueryTransactionResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathQueryLookup.getString("soapenv:Envelope.soapenv:Body.queryTransactionResponse.responseCode"), is(responseCode2201));

        setSimulatorExpectedState(vendorMTNNGSuccess);
    }

    @Test
    @Description("ctx-simulator :: lookup :: MTNNG ERROR_TEMP_INV_MSISDN 2213")
    @TmsLink("TECH-193473")
    public void testMTNNGLookupErrorTempINVMSISDN2213() throws Exception {
        CTXSimulatorClient.setSimulatorExpectedState(vendorMTNNGException);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2240));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        CTXSimulatorClient.setSimulatorExpectedState(vendorMTNNGErrorTempINVMSISDN);
        Thread.sleep(30000);
        String QueryLookupRequest = CreateCTXQueryTransactionBody(TestClient3,alternateClientId,ClientTransactionID);
        val QueryTransactionResponse = queryTransactionRequest(QueryLookupRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(QueryTransactionResponse.getBody().asString());
        XmlPath xmlPathQueryLookup = new XmlPath(QueryTransactionResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathQueryLookup.getString("soapenv:Envelope.soapenv:Body.queryTransactionResponse.responseCode"), is(responseCode2213));

        setSimulatorExpectedState(vendorMTNNGSuccess);
    }

    @Test
    @Description("ctx-simulator :: lookup :: MTNNG ERROR_INV_MSISDN 2213")
    @TmsLink("TECH-193472")
    public void testMTNNGLookupErrorINVMSISDN2213() throws Exception{

        setSimulatorExpectedState(vendorMTNNGException);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2240));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        CTXSimulatorClient.setSimulatorExpectedState(vendorMTNNGErrorINVMSISDN);
        Thread.sleep(30000);
        String QueryLookupRequest = CreateCTXQueryTransactionBody(TestClient3,alternateClientId,ClientTransactionID);
        val QueryTransactionResponse = queryTransactionRequest(QueryLookupRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(QueryTransactionResponse.getBody().asString());
        XmlPath xmlPathQueryLookup = new XmlPath(QueryTransactionResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathQueryLookup.getString("soapenv:Envelope.soapenv:Body.queryTransactionResponse.responseCode"), is(responseCode2213));

        setSimulatorExpectedState(vendorMTNNGSuccess);
    }

    @Test
    @Description("ctx-simulator :: lookup :: MTNNG ERROR_MSISDN_BARRED 2201")
    @TmsLink("TECH-193471")
    public void testMTNNGLookupErrorMSISDNBarred2201() throws Exception{

        setSimulatorExpectedState(vendorMTNNGException);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2240));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        CTXSimulatorClient.setSimulatorExpectedState(vendorMTNNGErrorMsisdnBarred);
        Thread.sleep(30000);
        String QueryLookupRequest = CreateCTXQueryTransactionBody(TestClient3,alternateClientId,ClientTransactionID);
        val QueryTransactionResponse = queryTransactionRequest(QueryLookupRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(QueryTransactionResponse.getBody().asString());
        XmlPath xmlPathQueryLookup = new XmlPath(QueryTransactionResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathQueryLookup.getString("soapenv:Envelope.soapenv:Body.queryTransactionResponse.responseCode"), is(responseCode2201));

        setSimulatorExpectedState(vendorMTNNGSuccess);
    }

    @Test
    @Description("ctx-simulator :: lookup :: MTNNG ERROR_PPAS_NO_CONNECT 2201")
    @TmsLink("TECH-193470")
    public void testMTNNGLookupErrorPPASNoConnect2201() throws Exception{

        setSimulatorExpectedState(vendorMTNNGException);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2240));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        CTXSimulatorClient.setSimulatorExpectedState(vendorMTNNGErrorPPASNoConnect);
        Thread.sleep(30000);
        String QueryLookupRequest = CreateCTXQueryTransactionBody(TestClient3,alternateClientId,ClientTransactionID);
        val QueryTransactionResponse = queryTransactionRequest(QueryLookupRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(QueryTransactionResponse.getBody().asString());
        XmlPath xmlPathQueryLookup = new XmlPath(QueryTransactionResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathQueryLookup.getString("soapenv:Envelope.soapenv:Body.queryTransactionResponse.responseCode"), is(responseCode2201));

        setSimulatorExpectedState(vendorMTNNGSuccess);
    }


    @Test
    @Description("ctx-simulator :: lookup :: MTNNG ERROR_PPAS_FAIL 2201")
    @TmsLink("TECH-193469")
    public void testMTNNGLookupErrorPPASFail2201() throws Exception{

        setSimulatorExpectedState(vendorMTNNGException);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2240));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        CTXSimulatorClient.setSimulatorExpectedState(vendorMTNNGErrorPpasFail);
        Thread.sleep(30000);
        String QueryLookupRequest = CreateCTXQueryTransactionBody(TestClient3,alternateClientId,ClientTransactionID);
        val QueryTransactionResponse = queryTransactionRequest(QueryLookupRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(QueryTransactionResponse.getBody().asString());
        XmlPath xmlPathQueryLookup = new XmlPath(QueryTransactionResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathQueryLookup.getString("soapenv:Envelope.soapenv:Body.queryTransactionResponse.responseCode"), is(responseCode2201));

        setSimulatorExpectedState(vendorMTNNGSuccess);
    }

    @Test
    @Description("ctx-simulator :: lookup :: MTNNG ERROR_DEF_PPAS_MSG 2201")
    @TmsLink("TECH-193468")
    public void testMTNNGLookupErrorDEFPPASMSG2201() throws Exception{

        setSimulatorExpectedState(vendorMTNNGException);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2240));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);


        setSimulatorExpectedState(vendorMTNNGErrorDEFPPASMSG);
        Thread.sleep(30000);
        String QueryLookupRequest = CreateCTXQueryTransactionBody(TestClient3,alternateClientId,ClientTransactionID);
        val QueryTransactionResponse = queryTransactionRequest(QueryLookupRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(QueryTransactionResponse.getBody().asString());
        XmlPath xmlPathQueryLookup = new XmlPath(QueryTransactionResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathQueryLookup.getString("soapenv:Envelope.soapenv:Body.queryTransactionResponse.responseCode"), is(responseCode2201));

        setSimulatorExpectedState(vendorMTNNGSuccess);
    }

    @Test
    @Description("ctx-simulator :: lookup :: MTNNG ERROR_INV_DEST_ACC 2201")
    @TmsLink("TECH-193464")
    public void testMTNNGLookupErrorINVDestACC2201() throws Exception{

        setSimulatorExpectedState(vendorMTNNGException);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2240));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorMTNNGErrorINVDestAcc);
        Thread.sleep(30000);
        String QueryLookupRequest = CreateCTXQueryTransactionBody(TestClient3,alternateClientId,ClientTransactionID);
        val QueryTransactionResponse = queryTransactionRequest(QueryLookupRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(QueryTransactionResponse.getBody().asString());
        XmlPath xmlPathQueryLookup = new XmlPath(QueryTransactionResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathQueryLookup.getString("soapenv:Envelope.soapenv:Body.queryTransactionResponse.responseCode"), is(responseCode2201));

        setSimulatorExpectedState(vendorMTNNGSuccess);
    }

    @Test
    @Description("ctx-simulator :: lookup :: MTNNG ERROR_INV_ORIG_ACC 2201")
    @TmsLink("TECH-193463")
    public void testMTNNGLookupErrorINVORIGACC2201() throws Exception{

        setSimulatorExpectedState(vendorMTNNGException);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2240));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorMTNNGErrorINVORIGACC);
        Thread.sleep(30000);
        String QueryLookupRequest = CreateCTXQueryTransactionBody(TestClient3,alternateClientId,ClientTransactionID);
        val QueryTransactionResponse = queryTransactionRequest(QueryLookupRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(QueryTransactionResponse.getBody().asString());
        XmlPath xmlPathQueryLookup = new XmlPath(QueryTransactionResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathQueryLookup.getString("soapenv:Envelope.soapenv:Body.queryTransactionResponse.responseCode"), is(responseCode2201));

        setSimulatorExpectedState(vendorMTNNGSuccess);
    }

    @Test
    @Description("ctx-simulator :: lookup :: MTNNG ERROR_INSUF_AIRTIME 2237")
    @TmsLink("TECH-193462")
    public void testMTNNGLookupErrorINSUFAirtime2237() throws Exception{

        setSimulatorExpectedState(vendorMTNNGException);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2240));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorMTNNGErrorINSUFAirtime);
        Thread.sleep(30000);
        String QueryLookupRequest = CreateCTXQueryTransactionBody(TestClient3,alternateClientId,ClientTransactionID);
        val QueryTransactionResponse = queryTransactionRequest(QueryLookupRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(QueryTransactionResponse.getBody().asString());
        XmlPath xmlPathQueryLookup = new XmlPath(QueryTransactionResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathQueryLookup.getString("soapenv:Envelope.soapenv:Body.queryTransactionResponse.responseCode"), is(ResponseCode_2237));

        setSimulatorExpectedState(vendorMTNNGSuccess);
    }

    @Test
    @Description("ctx-simulator :: lookup :: MTNNG EXCEPTION 2240")
    @TmsLink("TECH-193461")
    public void testMTNNGLookupException2240() throws Exception{

        setSimulatorExpectedState(vendorMTNNGException);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2240));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        String QueryLookupRequest = CreateCTXQueryTransactionBody(TestClient3,alternateClientId,ClientTransactionID);
        val QueryTransactionResponse = queryTransactionRequest(QueryLookupRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(QueryTransactionResponse.getBody().asString());
        XmlPath xmlPathQueryLookup = new XmlPath(QueryTransactionResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathQueryLookup.getString("soapenv:Envelope.soapenv:Body.queryTransactionResponse.responseCode"), is(responseCode2240));

        setSimulatorExpectedState(vendorMTNNGSuccess);
    }

    @Test
    @Description("ctx-simulator :: lookup :: MTNNG ERROR_SERVER 2228")
    @TmsLink("TECH-193459")
    public void testMTNNGLookupErrorServer2228() throws Exception{

        setSimulatorExpectedState(vendorMTNNGException);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2240));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorMTNNGErrorServer);
        Thread.sleep(30000);
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
    @Description("ctx-simulator :: purchase :: MTNNG ERROR_VENDOR_SYSTEM 2201")
    @TmsLink("TECH-193417")
    public void testMTNNGPurchaseErrorVendorSystem2201() throws Exception{

        setSimulatorExpectedState(vendorMTNNGErrorVendorSystem);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2201));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorMTNNGSuccess);
    }

    @Test
    @Description("ctx-simulator :: purchase :: MTNNG ERROR_FORMAT 2201")
    @TmsLink("TECH-193416")
    public void testMTNNGPurchaseErrorFormat2201() throws Exception{

        setSimulatorExpectedState(vendorMTNNGErrorFORMAT);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2201));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorMTNNGSuccess);
    }

    @Test
    @Description("ctx-simulator :: purchase :: MTNNG ERROR_NO_TRX 2240")
    @TmsLink("TECH-193413")
    public void testMTNNGPurchaseErrorNoTrx2240() throws Exception{

        setSimulatorExpectedState(vendorMTNNGErrorNO_TRX);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2240));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorMTNNGSuccess);
    }

    @Test
    @Description("ctx-simulator :: purchase :: MTNNG ERROR_TEMP_INV_MSISDN 2213")
    @TmsLink("TECH-193412")
    public void testMTNNGPurchaseErrorTempINVMSISDN2213() throws Exception{

        setSimulatorExpectedState(vendorMTNNGErrorTempINVMSISDN);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2213));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorMTNNGSuccess);
    }

    @Test
    @Description("ctx-simulator :: purchase :: MTNNG ERROR_INV_MSISDN 2213")
    @TmsLink("TECH-193411")
    public void testMTNNGPurchaseErrorINVMSISDN2213() throws Exception{

        setSimulatorExpectedState(vendorMTNNGErrorINVMSISDN);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2213));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorMTNNGSuccess);
    }

    @Test
    @Description("ctx-simulator :: purchase :: MTNNG ERROR_MSISDN_BARRED 2201")
    @TmsLink("TECH-193409")
    public void testMTNNGPurchaseErrorMSISDNBARRED2201() throws Exception{

        setSimulatorExpectedState(vendorMTNNGErrorMsisdnBarred);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2201));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorMTNNGSuccess);
    }

    @Test
    @Description("ctx-simulator :: purchase :: MTNNG ERROR_PPAS_NO_CONNECT 2201")
    @TmsLink("TECH-193408")
    public void testMTNNGPurchaseErrorPPASNoConnect2201() throws Exception{

        setSimulatorExpectedState(vendorMTNNGErrorPPASNoConnect);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2201));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorMTNNGSuccess);
    }

    @Test
    @Description("ctx-simulator :: purchase :: MTNNG ERROR_PPAS_FAIL 2201")
    @TmsLink("TECH-193407")
    public void testMTNNGPurchaseErrorPPASFail2201() throws Exception{

        setSimulatorExpectedState(vendorMTNNGErrorPpasFail);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2201));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorMTNNGSuccess);
    }

    @Test
    @Description("ctx-simulator :: purchase :: MTNNG ERROR_DEF_PPAS_MSG 2201")
    @TmsLink("TECH-193405")
    public void testMTNNGPurchaseErrorDEFPPASMSG2201() throws Exception{

        setSimulatorExpectedState(vendorMTNNGErrorDEFPPASMSG);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2201));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorMTNNGSuccess);
    }

    @Test
    @Description("ctx-simulator :: purchase :: MTNNG ERROR_INV_TAR_CLS 2210")
    @TmsLink("TECH-193403")
    public void testMTNNGPurchaseErrorINVTARCLS2210() throws Exception{

        setSimulatorExpectedState(vendorMTNNGErrorINVTARCLS);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2210));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorMTNNGSuccess);
    }

    @Test
    @Description("ctx-simulator :: purchase :: MTNNG ERROR_INV_DEST_ACC 2201")
    @TmsLink("TECH-193402")
    public void testMTNNGPurchaseErrorINVDESTAcc2201() throws Exception{

        setSimulatorExpectedState(vendorMTNNGErrorINVDestAcc);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2201));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorMTNNGSuccess);
    }

    @Test
    @Description("ctx-simulator :: purchase :: MTNNG ERROR_INV_ORIG_ACC 2201")
    @TmsLink("TECH-193401")
    public void testMTNNGPurchaseErrorINVORIGAcc2201() throws Exception{

        setSimulatorExpectedState(vendorMTNNGErrorINVORIGACC);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2201));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorMTNNGSuccess);
    }

    @Test
    @Description("ctx-simulator :: purchase :: MTNNG ERROR_INSUF_AIRTIME 2237")
    @TmsLink("TECH-193400")
    public void testMTNNGPurchaseErrorINSUFAirtime2237() throws Exception{

        setSimulatorExpectedState(vendorMTNNGErrorINSUFAirtime);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2237));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorMTNNGSuccess);
    }

    @Test
    @Description("ctx-simulator :: purchase :: MTNNG ERROR_SERVER 2228")
    @TmsLink("TECH-193399")
    public void testMTNNGPurchaseErrorServer2228() throws Exception{

        setSimulatorExpectedState(vendorMTNNGErrorServer);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2228));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorMTNNGSuccess);
    }

    @Test
    @Description("ctx-simulator :: SMOKE :: purchase :: MTNNG SUCCESS 0")
    @TmsLink("TECH-160866")
    public void testMTNNGPurchaseSuccess0() throws Exception{

        setSimulatorExpectedState(vendorMTNNGSuccess);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode0));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);
    }

    @Test
    @Description("ctx-simulator :: purchase :: MTNNG EXCEPTION 2240")
    @TmsLink("TECH-193396")
    public void testMTNNGPurchaseMTNNGException2240() throws Exception{

        setSimulatorExpectedState(vendorMTNNGException);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2240));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorMTNNGSuccess);
    }

    @Test
    @Description("ctx-simulator :: purchase :: MTNNG DELAY 2201")
    @TmsLink("TECH-193398")
    public void testMTNNGPurchaseMTNNGDelay2201() throws Exception{

        setSimulatorExpectedState(vendorMTNNGDelay5000);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,ProductAirtel_100,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2201));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorMTNNGSuccess);
    }



    @Test
    @Description("ctx-simulator :: SMOKE :: purchase :: GLO SUCCESS 0")
    @TmsLink("TECH-193418")
    public void testPurchaseGLOSuccess0() throws Exception{

        setSimulatorExpectedState(vendorGLOSuccess);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,Product_Glo_110,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode0));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);
    }

    @Test
    @Description("ctx-simulator :: purchase :: GLO EXCEPTION 2240")
    @TmsLink("TECH-193419")
    public void testPurchaseGLOException2240() throws Exception{

        setSimulatorExpectedState(vendorGLOException);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,Product_Glo_110,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2240));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorGLOSuccess);
    }

    @Test
    @Description("ctx-simulator :: purchase :: GLO DELAY 2240")
    @TmsLink("TECH-193420")
    public void testPurchaseGLODelay2240() throws Exception{

        setSimulatorExpectedState(vendorGLODelay5000);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,Product_Glo_110,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2240));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorGLOSuccess);
    }

    @Test
    @Description("ctx-simulator :: purchase :: GLO ERROR_PROTOCOL 2201")
    @TmsLink("TECH-193421")
    public void testPurchaseGLOErrorProtocol2201() throws Exception{

        setSimulatorExpectedState(vendorGLOErrorProtocol);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,Product_Glo_110,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2201));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorGLOSuccess);
    }

    @Test
    @Description("ctx-simulator :: purchase :: GLO ERROR_DUP_TRAN_APROV 2222")
    @TmsLink("TECH-193422")
    public void testPurchaseGLOErrorDUPTRANAPROV2222() throws Exception{

        setSimulatorExpectedState(vendorGLOErrorDUPTRANAPROV);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,Product_Glo_110,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2222));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorGLOSuccess);
    }

    @Test
    @Description("ctx-simulator :: purchase :: GLO ERROR_INV_TRAN 2226")
    @TmsLink("TECH-193423")
    public void testPurchaseGLOErrorINVTRAN2226() throws Exception{

        setSimulatorExpectedState(vendorGLOErrorINVTRAN);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,Product_Glo_110,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2226));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorGLOSuccess);
    }


    @Test
    @Description("ctx-simulator :: purchase :: GLO ERROR_INV_AMT 2212")
    @TmsLink("TECH-193424")
    public void testPurchaseGLOErrorINVAMT2212() throws Exception{

        setSimulatorExpectedState(vendorGLOErrorINVAMT);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,Product_Glo_110,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2212));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorGLOSuccess);
    }

    @Test
    @Description("ctx-simulator :: purchase :: GLO ERROR_VER_FAILED 2223")
    @TmsLink("TECH-193425")
    public void testPurchaseGLOErrorVerFailed2223() throws Exception{

        setSimulatorExpectedState(vendorGLOErrorVERFailed);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,Product_Glo_110,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2223));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorGLOSuccess);
    }

    @Test
    @Description("ctx-simulator :: purchase :: GLO ERROR_SUB_LIMITS 2219")
    @TmsLink("TECH-193426")
    public void testPurchaseGLOErrorSUBLIMITS2219() throws Exception{

        setSimulatorExpectedState(vendorGLOErrorSubLimits);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,Product_Glo_110,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2219));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorGLOSuccess);
    }

    @Test
    @Description("ctx-simulator :: purchase :: GLO ERROR_DUP_TRAN_DECL 2222")
    @TmsLink("TECH-193427")
    public void testPurchaseGLOErrorDUPTranDECL2222() throws Exception{

        setSimulatorExpectedState(vendorGLOErrorDUPTranDECL);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,Product_Glo_110,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2222));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorGLOSuccess);
    }

    @Test
    @Description("ctx-simulator :: purchase :: GLO ERROR_TRAN_PROC 2226")
    @TmsLink("TECH-193428")
    public void testPurchaseGLOErrorTranPROC2226() throws Exception{

        setSimulatorExpectedState(vendorGLOErrorTranPROC);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,Product_Glo_110,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2226));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorGLOSuccess);
    }

    @Test
    @Description("ctx-simulator :: purchase :: GLO ERROR_TRAN_LIMIT 2218")
    @TmsLink("TECH-193429")
    public void testPurchaseGLOErrorTranLimit2218() throws Exception{

        setSimulatorExpectedState(vendorGLOErrorTRANLimit);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,Product_Glo_110,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2218));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorGLOSuccess);
    }

    @Test
    @Description("ctx-simulator :: purchase :: GLO ERROR_INV_MSISDN 2213")
    @TmsLink("TECH-193430")
    public void testPurchaseGLOErrorINVMSISDN2213() throws Exception{

        setSimulatorExpectedState(vendorGLOErrorINVMSISDN);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,Product_Glo_110,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2213));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorGLOSuccess);
    }

    @Test
    @Description("ctx-simulator :: purchase :: GLO ERROR_INV_STATUS 2217")
    @TmsLink("TECH-193431")
    public void testPurchaseGLOErrorINVStatus2217() throws Exception{

        setSimulatorExpectedState(vendorGLOErrorINVStatus);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,Product_Glo_110,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2217));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorGLOSuccess);
    }

    @Test
    @Description("ctx-simulator :: purchase :: ETISALAT ERROR_INV_MSISDN 2213")
    @TmsLink("TECH-193448")
    public void testPurchaseETISALATErrorINVMSISDN2213() throws Exception{

        setSimulatorExpectedState(vendorETISALATErrorINVMSISDN);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,Product_Etisalat_120,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2213));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorETISALATSuccess);
    }

    @Test
    @Description("ctx-simulator :: purchase :: ETISALAT ERROR_INV_AMT 2212")
    @TmsLink("TECH-193447")
    public void testPurchaseETISALATErrorINVAMT2212() throws Exception{

        setSimulatorExpectedState(vendorETISALATErrorINVAMT);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,Product_Etisalat_120,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2212));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorETISALATSuccess);
    }

    @Test
    @Description("ctx-simulator :: purchase :: ETISALAT ERROR_SERV_PROV 2227")
    @TmsLink("TECH-193446")
    public void testPurchaseETISALATErrorSERVPROV2227() throws Exception{

        setSimulatorExpectedState(vendorETISALATErrorSERVPROV);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,Product_Etisalat_120,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2227));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorETISALATSuccess);
    }

    @Test
    @Description("ctx-simulator :: purchase :: ETISALAT ERROR_RECHARGE_NOT_ALLOW 2214")
    @TmsLink("TECH-193444")
    public void testPurchaseETISALATErrorRechargeNotAllow2214() throws Exception{

        setSimulatorExpectedState(vendorETISALATErrorRechargeNotAllow);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,Product_Etisalat_120,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2214));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorETISALATSuccess);
    }

    @Test
    @Description("ctx-simulator :: purchase :: ETISALAT ERROR_AUTH_FAILED 2223")
    @TmsLink("TECH-193443")
    public void testPurchaseETISALATErrorAUTHFAILED2223() throws Exception{

        setSimulatorExpectedState(vendorETISALATErrorAuthFailed);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,Product_Etisalat_120,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2223));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorETISALATSuccess);
    }

    @Test
    @Description("ctx-simulator :: purchase :: ETISALAT ERROR_NO_RECHARGE 2214")
    @TmsLink("TECH-193442")
    public void testPurchaseETISALATErrorNoRecharge2214() throws Exception{

        setSimulatorExpectedState(vendorETISALATErrorNoRecharge);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,Product_Etisalat_120,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2214));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorETISALATSuccess);
    }

    @Test
    @Description("ctx-simulator :: purchase :: ETISALAT ERROR_NO_FUNDS 2221")
    @TmsLink("TECH-193441")
    public void testPurchaseETISALATErrorNoFunds2221() throws Exception{

        setSimulatorExpectedState(vendorETISALATErrorNoFunds);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,Product_Etisalat_120,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2221));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorETISALATSuccess);
    }

    @Test
    @Description("ctx-simulator :: purchase :: ETISALAT ERROR_RECHARGE_NOT_AUTH 2214")
    @TmsLink("TECH-193440")
    public void testPurchaseETISALATErrorRechargeNotAuth2214() throws Exception{

        setSimulatorExpectedState(vendorETISALATErrorRechargeNotAuth);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,Product_Etisalat_120,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2214));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorETISALATSuccess);
    }

    @Test
    @Description("ctx-simulator :: purchase :: ETISALAT ERROR_TRAN_DUP 2222")
    @TmsLink("TECH-193438")
    public void testPurchaseETISALATErrorTRANDUP2222() throws Exception{

        setSimulatorExpectedState(vendorETISALATErrorTRANDUP);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,Product_Etisalat_120,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2222));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorETISALATSuccess);
    }

    @Test
    @Description("ctx-simulator :: purchase :: ETISALAT ERROR_SYS_VOL 2225")
    @TmsLink("TECH-193437")
    public void testPurchaseETISALATErrorSYSVOL2225() throws Exception{

        setSimulatorExpectedState(vendorETISALATErrorSYSVOL);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,Product_Etisalat_120,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2225));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorETISALATSuccess);
    }

    @Test
    @Description("ctx-simulator :: purchase :: ETISALAT ERROR_TIME_OUT 2224")
    @TmsLink("TECH-193435")
    public void testPurchaseETISALATErrorTimeOUT2224() throws Exception{

        setSimulatorExpectedState(vendorETISALATErrorTimeOUT);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,Product_Etisalat_120,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2224));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorETISALATSuccess);
    }

    @Test
    @Description("ctx-simulator :: purchase :: ETISALAT DELAY 0")
    @TmsLink("TECH-193434")
    public void testPurchaseETISALATDelay0() throws Exception{

        setSimulatorExpectedState(vendorETISALATDelay5000);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,Product_Etisalat_120,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode0));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorETISALATSuccess);
    }

    @Test
    @Description("ctx-simulator :: purchase :: ETISALAT EXCEPTION 2240")
    @TmsLink("TECH-193433")
    public void testPurchaseETISALATException2240() throws Exception{

        setSimulatorExpectedState(vendorETISALATException);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,Product_Etisalat_120,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2240));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        setSimulatorExpectedState(vendorETISALATSuccess);
    }

    @Test
    @Description("ctx-simulator :: SMOKE :: purchase :: ETISALAT SUCCESS 0")
    @TmsLink("TECH-193432")
    public void testPurchaseETISALATSUCCESS0() throws Exception{

        setSimulatorExpectedState(vendorETISALATSuccess);
        String requestException = CtxCreateBody(TestClient3,PurchaseAmount10000,Identifier_6,Product_Etisalat_120,ChannelID_07,alternateClientId,Identifier_6,channelSessionId_3133827176);
        val ResponseException = validateCustomerAccount(requestException)
                .then().statusCode(200)
                .extract().response();
        System.out.println(ResponseException.getBody().asString());
        XmlPath xmlPathException = new XmlPath(ResponseException.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathException.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode0));
        String ClientTransactionID = xmlPathException.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);
    }











































}
