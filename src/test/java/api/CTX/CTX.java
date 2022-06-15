package api.CTX;

import api.clients.BasedAPIClient;
import api.clients.ProductLookupClient;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import io.restassured.RestAssured;
import io.restassured.config.XmlConfig;
import io.restassured.path.xml.XmlPath;
import io.restassured.path.xml.config.XmlPathConfig;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import static api.clients.CTX.*;
import static api.clients.InFlightTransactionLookupClient.ResponseCode_200;
import static api.clients.ProductLookupClient.*;
import static api.clients.ReserveAndTransactClient.*;
import static api.clients.ReserveAndTransactClient.FeeAmount0;
import static api.clients.SimulatorsClient.PostControlApiBehaviour;
import static api.clients.SimulatorsClient.mwmSuccess;
import static api.clients.UsersClient.getRandomNumbers;
import static api.clients.VendorManagementClient.Vendor21;
import static api.clients.VendorRoutingServiceClient.Pending;
import static api.clients.VendorRoutingServiceClient.vendorTransactionReference;
import static api.domains.CTX.CTX.CtxCreateBody;
import static api.domains.simulator.repo.VendorRoutingServiceRequestRepo.SetupSetVendData;
import static db.clients.HibernateBaseClient.executeCustomQueryAndReturnValue;
import static db.custom_queries.CTXQueries.*;
import static db.custom_queries.ClientQueries.SELECT_ACCOUNT;
import static db.enums.Sessions.MY_SQL;
import static io.restassured.path.xml.config.XmlPathConfig.xmlPathConfig;
import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CTX extends BasedAPIClient {
    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_VENDOR_QUEUE_INACTIVE\" for Transaction code 2520")
    @TmsLink("TECH-179527")
    public void testVerifyCTxCode2520() {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0, ResponseCode_2520, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2520));

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_INVALID_CHANNEL_ID\" for Transaction code 2523")
    @TmsLink("TECH-179578")
    public void testVerifyCTxCode2523() {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0, ResponseCode_2523, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2523));

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_CLIENT_ADVICE_RESERVED\" for Transaction code 2521")
    @TmsLink("TECH-179576")
    public void testVerifyCTxCode2521() {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0, ResponseCode_2521, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2521));

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_% CMT_CLIENT\" for Transaction code 2519")
    @TmsLink("TECH-179526")
    public void testVerifyCTxCode2519() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0, ResponseCode_2519, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        Thread.sleep(2000);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2519));

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_PRICE_PACK_TIERS\" for Transaction code 2515")
    @TmsLink("TECH-179522")
    public void testVerifyCTxCode2515() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0, ResponseCode_2515, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2515));

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_PRICE_PACK_CLIENT\" for Transaction code 2514")
    @TmsLink("TECH-179521")
    public void testVerifyCTxCode2514() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0, ResponseCode_2514, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2514));

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_VENDOR_INACTIVE\" for Transaction code 2518")
    @TmsLink("TECH-179525")
    public void testVerifyCTxCode2518() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0, ResponseCode_2518, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2518));

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_CLIENT_PRICE_PACK_INACTIVE\" for Transaction code 2516")
    @TmsLink("TECH-179523")
    public void testVerifyCTxCode2516() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0, ResponseCode_2516, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2516));

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_CLIENT_INACTIVE\" for Transaction code 2517")
    @TmsLink("TECH-179524")
    public void testVerifyCTxCode2517() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0, ResponseCode_2517, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2517));

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_PRODUCT_CLIENT\" for Transaction code 2513")
    @TmsLink("TECH-179520")
    public void testVerifyCTxCode2513() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0, ResponseCode_2513, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2513));

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_INVALID_AMOUNT_INCREMENT\" for Transaction code 2511")
    @TmsLink("TECH-179518")
    public void testVerifyCTxCode2511() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2511 , FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2511));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_PRODUCT_INACTIVE\" for Transaction code 2512")
    @TmsLink("TECH-179519")
    public void testVerifyCTxCode2512() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2512 , FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2512));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_CLIENT_ADVICE_NOT_YET_RECIEVED\" for Transaction code 2522")
    @TmsLink("TECH-179577")
    public void testVerifyCTxCode2522() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2522 , FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2522));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_INVALID_CLIENT\" for Transaction code 2524")
    @TmsLink("TECH-179579")
    public void testVerifyCTxCode2524() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2524 , FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2524));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_CANNOT_TRADE_AS_OTHER_CLIENT\" for Transaction code 2525")
    @TmsLink("TECH-179580")
    public void testVerifyCTxCode2525() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2525 , FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2525));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_TOKEN_GENERATION_ERROR\" for Transaction code 2605")
    @TmsLink("TECH-179586")
    public void testVerifyCTxCode2605() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2605 , FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2605));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_INVALID_API_TOKEN\" for Transaction code 2608")
    @TmsLink("TECH-179589")
    public void testVerifyCTxCode2608() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2608 , FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2608));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR\" for Transaction code 2201")
    @TmsLink("TECH-179590")
    public void testVerifyCTxCode2201() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2201 , FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2201));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_SUCCESS\" for Transaction code 0")
    @TmsLink("TECH-179591")
    public void testVerifyCTxCode0() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,FeeAmount0 , FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(FeeAmount0));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_UNABLE_TO_LOOKUP_ORIGINAL_TXN\" for Transaction code 2607")
    @TmsLink("TECH-179588")
    public void testVerifyCTxCode2607() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2607 , FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2607));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_TOKEN_DECRYPTION_ERROR\" for Transaction code 2606")
    @TmsLink("TECH-179587")
    public void testVerifyCTxCode2606() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2606 , FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2606));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"INCOMPLETE_VENDER_INSTRUCTION_POSSIBLY_SENT\" for Transaction code 2603")
    @TmsLink("TECH-179584")
    public void testVerifyCTxCode2603() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2603 , FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2603));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"ISO_VALIDATION_ERROR_DUPLICATE_CLIENT_TRANSACTION_ID\" for Transaction code 2604")
    @TmsLink("TECH-179585")
    public void testVerifyCTxCode2604() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2604 , FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2604));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"INCOMPLETE_VENDOR_INSTRUCTION_SENT\" for Transaction code 2601")
    @TmsLink("TECH-179582")
    public void testVerifyCTxCode2601() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2601 , FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2601));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"INCOMPLETE_VENDER_INSTRUCTION_NOT_SENT\" for Transaction code 2602")
    @TmsLink("TECH-179583")
    public void testVerifyCTxCode2602() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2602 , FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2602));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_DATA\" for Transaction code 2502")
    @TmsLink("TECH-179502")
    public void testVerifyCTxCode2502() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2502 , FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2502));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_CONNECT_TIMEOUT\" for Transaction code 2239")
    @TmsLink("TECH-179379")
    public void testVerifyCTxCode2239() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2239 , FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2239));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_LAW\" for Transaction code 2232")
    @TmsLink("TECH-179372")
    public void testVerifyCTxCode2232() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2232 , FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2232));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_VOLUMES_EXCEEDED\" for Transaction code 2225")
    @TmsLink("TECH-178945")
    public void testVerifyCTxCode2225() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2225,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2225));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_LIMIT_EXCEEDED_CLIENT\" for Transaction code 2407")
    @TmsLink("TECH-179391")
    public void testVerifyCTxCode2407() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2407,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2407));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR\" for Transaction code 2501")
    @TmsLink("TECH-179501")
    public void testVerifyCTxCode2501() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2501,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2501));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_INVALID_STATUS\" for Transaction code 2217")
    @TmsLink("TECH-178937")
    public void testVerifyCTxCode2217() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2217,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2217));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_STOCK_ITEM_TRANSACTION_FAILED\" for Transaction code 2253")
    @TmsLink("TECH-179386")
    public void testVerifyCTxCode2253() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2253,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2253));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_STOCK_ITEM_STATUS_NOT_RESERVED\" for Transaction code 2252")
    @TmsLink("TECH-179385")
    public void testVerifyCTxCode2252() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2252,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2252));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_DUPLICATE_TRANSACTION\" for Transaction code 2222")
    @TmsLink("TECH-178942")
    public void testVerifyCTxCode2222() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2222,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2222));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_MERCHANT_BLOCKED\" for Transaction code 2230")
    @TmsLink("TECH-178950")
    public void testVerifyCTxCode2230() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2230,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2230));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_AMOUNT\" for Transaction code 2504")
    @TmsLink("TECH-179504")
    public void testVerifyCTxCode2504() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2504,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2504));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_BUSINESS_RULE\" for Transaction code 2233")
    @TmsLink("TECH-179373")
    public void testVerifyCTxCode2233() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2233,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2233));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_SUBSCRIBER_UNVERIFIED\" for Transaction code 2229")
    @TmsLink("TECH-178949")
    public void testVerifyCTxCode2229() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2229,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2229));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_TRANSACTION_PROCESSING\" for Transaction code 2226")
    @TmsLink("TECH-178946")
    public void testVerifyCTxCode2226() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2226,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2226));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_LIMIT_EXCEEDED_VENDOR\" for Transaction code 2406")
    @TmsLink("TECH-179390")
    public void testVerifyCTxCode2406() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2406,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2406));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_EXEC_BUSY\" for Transaction code 2241")
    @TmsLink("TECH-179381")
    public void testVerifyCTxCode2241() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2241,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2241));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_INVALID_AMOUNT\" for Transaction code 2212")
    @TmsLink("TECH-178931")
    public void testVerifyCTxCode2212() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2212,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2212));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_INVALID_MERCHANT\" for Transaction code 2220")
    @TmsLink("TECH-178940")
    public void testVerifyCTxCode2220() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2212,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2212));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_STOCK_ITEM_NOT_FOUND\" for Transaction code 2250")
    @TmsLink("TECH-179383")
    public void testVerifyCTxCode2250() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2250,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2250));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_MAX_ATPT_MSISDN\" for Transaction code 2242")
    @TmsLink("TECH-179382")
    public void testVerifyCTxCode2242() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2242,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2242));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_MERCHANT_LIMIT\" for Transaction code 2218")
    @TmsLink("TECH-178938")
    public void testVerifyCTxCode2218() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2218,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2218));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_INVALID_AMOUNT_MAX\" for Transaction code 2510")
    @TmsLink("TECH-179512")
    public void testVerifyCTxCode2510() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2510,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2510));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_INVALID_ACC_TYPE\" for Transaction code 2216")
    @TmsLink("TECH-178936")
    public void testVerifyCTxCode2216() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2216,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2216));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_MSISDN_BLACKLISTED\" for Transaction code 2508")
    @TmsLink("TECH-179509")
    public void testVerifyCTxCode2508() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2508,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2508));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_INVALID_REQUEST\" for Transaction code 2211")
    @TmsLink("TECH-178932")
    public void testVerifyCTxCode2211() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2211,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2211));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_INVALID_AMOUNT\" for Transaction code 2507")
    @TmsLink("TECH-179507")
    public void testVerifyCTxCode2507() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2507,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2507));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_SUBSCRIBER_BLOCKED\" for Transaction code 2231")
    @TmsLink("TECH-179370")
    public void testVerifyCTxCode2231() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2231,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2231));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_FINANCIAL\" for Transaction code 2235")
    @TmsLink("TECH-179375")
    public void testVerifyCTxCode2235() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2235,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2235));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_STOCK_ITEM_INVALID_RESERVARTION_ID\" for Transaction code 2251")
    @TmsLink("TECH-179384")
    public void testVerifyCTxCode2251() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2251,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2251));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_INVALID_MSISDN\" for Transaction code 2213")
    @TmsLink("TECH-178930")
    public void testVerifyCTxCode2213() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2213,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2213));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_INVALID_REQUEST\" for Transaction code 2210")
    @TmsLink("TECH-178933")
    public void testVerifyCTxCode2210() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2210,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2210));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_BACKEND_SYSTEM\" for Transaction code 2228")
    @TmsLink("TECH-178948")
    public void testVerifyCTxCode2228() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2228,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2228));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_CONNECT\" for Transaction code 2238")
    @TmsLink("TECH-179377")
    public void testVerifyCTxCode2238() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2228,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2228));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_LIMIT_EXCEEDED_MONTHLY_ORIGIN\" for Transaction code 2403")
    @TmsLink("TECH-179387")
    public void testVerifyCTxCode2403() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2403,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2403));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_EXEC_UNKNOWN\" for Transaction code 2240")
    @TmsLink("TECH-179380")
    public void testVerifyCTxCode2240() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2240,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2240));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_MERCHANT_AUTH_FAILED\" for Transaction code 2223")
    @TmsLink("TECH-178943")
    public void testVerifyCTxCode2223() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2223,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2223));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_VENDOR\" for Transaction code 2505")
    @TmsLink("TECH-179505")
    public void testVerifyCTxCode2505() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2505,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2505));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_INVALID_SERVICE_PROVIDER\" for Transaction code 2227")
    @TmsLink("TECH-178947")
    public void testVerifyCTxCode2227() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2227,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2227));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_INVALID_PRODUCT\" for Transaction code 2215")
    @TmsLink("TECH-178935")
    public void testVerifyCTxCode2215() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2215,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2215));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_MERCHANT_INSUFFICIENT_FUNDS\" for Transaction code 2221")
    @TmsLink("TECH-178941")
    public void testVerifyCTxCode2221() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2221,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2221));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_CLIENT_LIMIT_NOT_CONFIGURED\" for Transaction code 2405")
    @TmsLink("TECH-179389")
    public void testVerifyCTxCode2405() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2405,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2405));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_INVALID_RECHARGE\" for Transaction code 2214")
    @TmsLink("TECH-178934")
    public void testVerifyCTxCode2214() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2214,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2214));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_INVALID_AMOUNT_MIN\" for Transaction code 2509")
    @TmsLink("TECH-179511")
    public void testVerifyCTxCode2509() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2509,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2509));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_SUBSCRIBER_LIMIT\" for Transaction code 2219")
    @TmsLink("TECH-178939")
    public void testVerifyCTxCode2219() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2219,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2219));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_LIMIT_EXCEEDED\" for Transaction code 2237")
    @TmsLink("TECH-179376")
    public void testVerifyCTxCode2237() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2237,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2237));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_TRANSACTION_TIMEOUT\" for Transaction code 2224")
    @TmsLink("TECH-178944")
    public void testVerifyCTxCode2224() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2224,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2224));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_PRODUCT\" for Transaction code 2503")
    @TmsLink("TECH-179503")
    public void testVerifyCTxCode2503() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2503,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2503));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_INVALID_FORMAT\" for Transaction code 2234")
    @TmsLink("TECH-179374")
    public void testVerifyCTxCode2234() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2234,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2234));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_PRODUCT_AMOUNT\" for Transaction code 2506")
    @TmsLink("TECH-179506")
    public void testVerifyCTxCode2506() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,ResponseCode_2506,FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2506));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: RetryableDecline : Verify \"COMPLETED_VENDOR_COMMUNICATION_TIMEOUT_ERROR\" for System code \"2203\"")
    @TmsLink("TECH-180043")
    public void testVerifyCTxCode2203() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,ResponseCode_2203,ResponseCode_2236,FeeAmount0, FeeAmount0,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2236));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        val SELECT_SYSTEM_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_SYSTEM_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID , ClientTransactionID));
        Assertions.assertThat(SELECT_SYSTEM_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_VALUE)
                .contains(ResponseCode_2203);
        val SELECT_TRANSACTION_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_TRANSACTION_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID , ClientTransactionID));
        Assertions.assertThat(SELECT_TRANSACTION_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_VALUE)
                .contains(ResponseCode_2236);
        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: RetryableDecline : Verify \"COMPLETED_VENDOR_COMMUNICATION_ERROR\" for System code \"2202\"")
    @TmsLink("TECH-180044")
    public void testVerifyCTxCode2202() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,ResponseCode_2202,ResponseCode_2238,FeeAmount0, FeeAmount0,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2238));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        val SELECT_SYSTEM_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_SYSTEM_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID , ClientTransactionID));
        Assertions.assertThat(SELECT_SYSTEM_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_VALUE)
                .contains(ResponseCode_2202);
        val SELECT_TRANSACTION_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_TRANSACTION_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID , ClientTransactionID));
        Assertions.assertThat(SELECT_TRANSACTION_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_VALUE)
                .contains(ResponseCode_2238);
//        val SELECT_VENDOR_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_VENDOR_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID , ClientTransactionID));
//        Assertions.assertThat(SELECT_VENDOR_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_VALUE)
//                .contains(ResponseCode_200);

        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Success :: Verify \"COMPLETED_SUCCESS\" for System code 0")
    @TmsLink("TECH-179788")
    public void testVerifyCTxCode02201() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0,responseCode2201,FeeAmount0, FeeAmount0,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3,PurchaseAmount10000,Identifier,ProductAirtel_917,ChannelID_07,alternateClientId,Identifier,channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode2201));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        val SELECT_SYSTEM_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_SYSTEM_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID , ClientTransactionID));
        Assertions.assertThat(SELECT_SYSTEM_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_VALUE)
                .contains(FeeAmount0);
        val SELECT_TRANSACTION_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_TRANSACTION_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID , ClientTransactionID));
        Assertions.assertThat(SELECT_TRANSACTION_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_VALUE)
                .contains(FeeAmount0);
        val SELECT_VENDOR_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_VENDOR_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID , ClientTransactionID));
        Assertions.assertThat(SELECT_VENDOR_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_VALUE)
                .contains(FeeAmount0);
        Map body_Success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
}