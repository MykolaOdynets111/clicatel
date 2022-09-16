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
    @TmsLink("MKP-491")
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
    @TmsLink("MKP-360")
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
    @TmsLink("MKP-516")
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
    @TmsLink("MKP-375")
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
    @TmsLink("MKP-476")
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
    @TmsLink("MKP-408")
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
    @TmsLink("MKP-450")
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
    @TmsLink("MKP-459")
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
    @TmsLink("MKP-448")
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
    @TmsLink("MKP-401")
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
    @TmsLink("MKP-380")
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
    @TmsLink("MKP-396")
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
    @TmsLink("MKP-452")
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
    @TmsLink("MKP-406")
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
    @TmsLink("MKP-483")
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
    @TmsLink("MKP-356")
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
    @TmsLink("MKP-462")
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
    @TmsLink("MKP-474")
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
    @TmsLink("MKP-486")
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
    @TmsLink("MKP-372")
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
    @TmsLink("MKP-370")
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
    @TmsLink("MKP-451")
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
    @TmsLink("MKP-376")
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
    @TmsLink("MKP-348")
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
    @TmsLink("MKP-444")
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
    @TmsLink("MKP-515")
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
    @TmsLink("MKP-469")
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
    @TmsLink("MKP-460")
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
    @TmsLink("MKP-645")
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
    @TmsLink("MKP-488")
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
    @TmsLink("MKP-398")
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
    @TmsLink("MKP-402")
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
    @TmsLink("MKP-482")
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
    @TmsLink("MKP-493")
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
    @TmsLink("MKP-453")
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
    @TmsLink("MKP-421")
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
    @TmsLink("MKP-351")
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
    @TmsLink("MKP-461")
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
    @TmsLink("MKP-507")
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
    @TmsLink("MKP-425")
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
    @TmsLink("MKP-433")
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
    @TmsLink("MKP-509")
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
    @TmsLink("MKP-438")
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
    @TmsLink("MKP-492")
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
    @TmsLink("MKP-468")
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
    @TmsLink("MKP-504")
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
    @TmsLink("MKP-437")
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
    @TmsLink("MKP-353")
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
    @TmsLink("MKP-389")
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
    @TmsLink("MKP-494")
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
    @TmsLink("MKP-501")
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
    @TmsLink("MKP-412")
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
    @TmsLink("MKP-347")
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
    @TmsLink("MKP-456")
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
    @TmsLink("MKP-417")
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
    @TmsLink("MKP-495")
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
    @TmsLink("MKP-394")
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
    @TmsLink("MKP-381")
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
    @TmsLink("MKP-386")
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
