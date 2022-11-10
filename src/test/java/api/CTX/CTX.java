package api.CTX;

import api.clients.BasedAPIClient;
import api.clients.ProductLookupClient;
import com.google.common.base.Functions;
import com.google.common.collect.Lists;
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

import java.util.*;

import static api.clients.CTX.*;
import static api.clients.InFlightTransactionLookupClient.ResponseCode_200;
import static api.clients.ProductLookupClient.*;
import static api.clients.ReserveAndTransactClient.*;
import static api.clients.ReserveAndTransactClient.FeeAmount0;
import static api.clients.ReserveAndTransactDBChecksClient.CommissionModel;
import static api.clients.SimulatorsClient.*;
import static api.clients.UsersClient.getRandomNumbers;
import static api.clients.VendorManagementClient.Vendor21;
import static api.clients.VendorRoutingServiceClient.Pending;
import static api.clients.VendorRoutingServiceClient.vendorTransactionReference;
import static api.domains.CTX.CTX.*;
import static api.domains.simulator.repo.VendorRoutingServiceRequestRepo.SetupSetVendData;
import static db.clients.HibernateBaseClient.executeCustomQueryAndReturnValue;
import static db.clients.HibernateBaseClient.executeCustomQueryAndReturnValues;
import static db.custom_queries.CTXQueries.*;
import static db.custom_queries.CTXQueries.SELECT_CLIENT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG;
import static db.custom_queries.CTXQueries.SELECT_CLIENT_STAN_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG;
import static db.custom_queries.CTXQueries.SELECT_END_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG;
import static db.custom_queries.CTXQueries.SELECT_END_VENDOR_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG;
import static db.custom_queries.CTXQueries.SELECT_FEE_AMOUNT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG;
import static db.custom_queries.CTXQueries.SELECT_ORIGIN_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG;
import static db.custom_queries.CTXQueries.SELECT_PRODUCT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG;
import static db.custom_queries.CTXQueries.SELECT_PURCHASE_AMOUNT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG;
import static db.custom_queries.CTXQueries.SELECT_QUANTITY_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG;
import static db.custom_queries.CTXQueries.SELECT_RECON_NOTE_VENDOR_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG;
import static db.custom_queries.CTXQueries.SELECT_RECON_STATE_CLIENT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG;
import static db.custom_queries.CTXQueries.SELECT_RECON_STATE_VENDOR_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG;
import static db.custom_queries.CTXQueries.SELECT_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG;
import static db.custom_queries.CTXQueries.SELECT_VENDOR_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG;
import static db.custom_queries.CTXQueries.SELECT_VENDOR_STAN_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG;
import static db.custom_queries.ClientQueries.SELECT_ACCOUNT;
import static db.custom_queries.ReserveAndTransactQueries.*;
import static db.enums.Sessions.MY_SQL;
import static io.restassured.path.xml.config.XmlPathConfig.xmlPathConfig;
import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CTX extends BasedAPIClient {
    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_VENDOR_QUEUE_INACTIVE\" for Transaction code 2520")
    @TmsLink("MKP-440")
    public void testVerifyCTxCode2520() {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2520, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2520));

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_INVALID_CHANNEL_ID\" for Transaction code 2523")
    @TmsLink("MKP-371")
    public void testVerifyCTxCode2523() {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2523, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2523));

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_CLIENT_ADVICE_RESERVED\" for Transaction code 2521")
    @TmsLink("MKP-520")
    public void testVerifyCTxCode2521() {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2521, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2521));

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }
    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_% CMT_CLIENT\" for Transaction code 2519")
    @TmsLink("MKP-471")
    public void testVerifyCTxCode2519() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2519, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        Thread.sleep(2000);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2519));

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_PRICE_PACK_TIERS\" for Transaction code 2515")
    @TmsLink("MKP-434")
    public void testVerifyCTxCode2515() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2515, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2515));

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_PRICE_PACK_CLIENT\" for Transaction code 2514")
    @TmsLink("MKP-449")
    public void testVerifyCTxCode2514() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2514, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2514));

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_VENDOR_INACTIVE\" for Transaction code 2518")
    @TmsLink("MKP-442")
    public void testVerifyCTxCode2518() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2518, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2518));

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_CLIENT_PRICE_PACK_INACTIVE\" for Transaction code 2516")
    @TmsLink("MKP-343")
    public void testVerifyCTxCode2516() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2516, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2516));

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_CLIENT_INACTIVE\" for Transaction code 2517")
    @TmsLink("MKP-472")
    public void testVerifyCTxCode2517() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2517, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2517));

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_PRODUCT_CLIENT\" for Transaction code 2513")
    @TmsLink("MKP-473")
    public void testVerifyCTxCode2513() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2513, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2513));

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_INVALID_AMOUNT_INCREMENT\" for Transaction code 2511")
    @TmsLink("MKP-362")
    public void testVerifyCTxCode2511() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2511, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2511));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_PRODUCT_INACTIVE\" for Transaction code 2512")
    @TmsLink("MKP-441")
    public void testVerifyCTxCode2512() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2512, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2512));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_CLIENT_ADVICE_NOT_YET_RECIEVED\" for Transaction code 2522")
    @TmsLink("MKP-505")
    public void testVerifyCTxCode2522() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2522, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2522));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_INVALID_CLIENT\" for Transaction code 2524")
    @TmsLink("MKP-419")
    public void testVerifyCTxCode2524() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2524, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2524));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_CANNOT_TRADE_AS_OTHER_CLIENT\" for Transaction code 2525")
    @TmsLink("MKP-339")
    public void testVerifyCTxCode2525() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2525, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2525));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_TOKEN_GENERATION_ERROR\" for Transaction code 2605")
    @TmsLink("MKP-455")
    public void testVerifyCTxCode2605() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2605, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2605));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_INVALID_API_TOKEN\" for Transaction code 2608")
    @TmsLink("MKP-413")
    public void testVerifyCTxCode2608() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2608, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2608));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR\" for Transaction code 2201")
    @TmsLink("MKP-458")
    public void testVerifyCTxCode2201() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2201, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2201));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_SUCCESS\" for Transaction code 0")
    @TmsLink("MKP-404")
    public void testVerifyCTxCode0() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(FeeAmount0));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_UNABLE_TO_LOOKUP_ORIGINAL_TXN\" for Transaction code 2607")
    @TmsLink("MKP-498")
    public void testVerifyCTxCode2607() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2607, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2607));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_TOKEN_DECRYPTION_ERROR\" for Transaction code 2606")
    @TmsLink("MKP-382")
    public void testVerifyCTxCode2606() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2606, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2606));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"INCOMPLETE_VENDER_INSTRUCTION_POSSIBLY_SENT\" for Transaction code 2603")
    @TmsLink("MKP-357")
    public void testVerifyCTxCode2603() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2603, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2603));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"ISO_VALIDATION_ERROR_DUPLICATE_CLIENT_TRANSACTION_ID\" for Transaction code 2604")
    @TmsLink("MKP-378")
    public void testVerifyCTxCode2604() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2604, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2604));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"INCOMPLETE_VENDOR_INSTRUCTION_SENT\" for Transaction code 2601")
    @TmsLink("MKP-346")
    public void testVerifyCTxCode2601() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2601, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2601));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"INCOMPLETE_VENDER_INSTRUCTION_NOT_SENT\" for Transaction code 2602")
    @TmsLink("MKP-414")
    public void testVerifyCTxCode2602() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2602, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2602));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_DATA\" for Transaction code 2502")
    @TmsLink("MKP-350")
    public void testVerifyCTxCode2502() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2502, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2502));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_CONNECT_TIMEOUT\" for Transaction code 2239")
    @TmsLink("MKP-368")
    public void testVerifyCTxCode2239() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2239, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2239));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_LAW\" for Transaction code 2232")
    @TmsLink("MKP-517")
    public void testVerifyCTxCode2232() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2232, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2232));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_VOLUMES_EXCEEDED\" for Transaction code 2225")
    @TmsLink("MKP-524")
    public void testVerifyCTxCode2225() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2225, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2225));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_LIMIT_EXCEEDED_CLIENT\" for Transaction code 2407")
    @TmsLink("MKP-431")
    public void testVerifyCTxCode2407() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2407, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2407));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR\" for Transaction code 2501")
    @TmsLink("MKP-446")
    public void testVerifyCTxCode2501() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2501, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2501));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_INVALID_STATUS\" for Transaction code 2217")
    @TmsLink("MKP-392")
    public void testVerifyCTxCode2217() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2217, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2217));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_STOCK_ITEM_TRANSACTION_FAILED\" for Transaction code 2253")
    @TmsLink("MKP-340")
    public void testVerifyCTxCode2253() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2253, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2253));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_STOCK_ITEM_STATUS_NOT_RESERVED\" for Transaction code 2252")
    @TmsLink("MKP-499")
    public void testVerifyCTxCode2252() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2252, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2252));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_DUPLICATE_TRANSACTION\" for Transaction code 2222")
    @TmsLink("MKP-522")
    public void testVerifyCTxCode2222() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2222, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2222));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_MERCHANT_BLOCKED\" for Transaction code 2230")
    @TmsLink("MKP-430")
    public void testVerifyCTxCode2230() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2230, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2230));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_AMOUNT\" for Transaction code 2504")
    @TmsLink("MKP-361")
    public void testVerifyCTxCode2504() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2504, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2504));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_BUSINESS_RULE\" for Transaction code 2233")
    @TmsLink("MKP-426")
    public void testVerifyCTxCode2233() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2233, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2233));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_SUBSCRIBER_UNVERIFIED\" for Transaction code 2229")
    @TmsLink("MKP-447")
    public void testVerifyCTxCode2229() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2229, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2229));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_TRANSACTION_PROCESSING\" for Transaction code 2226")
    @TmsLink("MKP-388")
    public void testVerifyCTxCode2226() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2226, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2226));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_LIMIT_EXCEEDED_VENDOR\" for Transaction code 2406")
    @TmsLink("MKP-480")
    public void testVerifyCTxCode2406() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2406, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2406));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_EXEC_BUSY\" for Transaction code 2241")
    @TmsLink("MKP-420")
    public void testVerifyCTxCode2241() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2241, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2241));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_INVALID_AMOUNT\" for Transaction code 2212")
    @TmsLink("MKP-393")
    public void testVerifyCTxCode2212() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2220, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2220));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_INVALID_MERCHANT\" for Transaction code 2220")
    @TmsLink("MKP-366")
    public void testVerifyCTxCode2220() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2220, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2220));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_STOCK_ITEM_NOT_FOUND\" for Transaction code 2250")
    @TmsLink("MKP-477")
    public void testVerifyCTxCode2250() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2250, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2250));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_MAX_ATPT_MSISDN\" for Transaction code 2242")
    @TmsLink("MKP-352")
    public void testVerifyCTxCode2242() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2242, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2242));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_MERCHANT_LIMIT\" for Transaction code 2218")
    @TmsLink("MKP-443")
    public void testVerifyCTxCode2218() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2218, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2218));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_INVALID_AMOUNT_MAX\" for Transaction code 2510")
    @TmsLink("MKP-490")
    public void testVerifyCTxCode2510() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2510, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2510));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_INVALID_ACC_TYPE\" for Transaction code 2216")
    @TmsLink("MKP-463")
    public void testVerifyCTxCode2216() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2216, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2216));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_MSISDN_BLACKLISTED\" for Transaction code 2508")
    @TmsLink("MKP-521")
    public void testVerifyCTxCode2508() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2508, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2508));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_INVALID_REQUEST\" for Transaction code 2211")
    @TmsLink("MKP-496")
    public void testVerifyCTxCode2211() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2211, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2211));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_INVALID_AMOUNT\" for Transaction code 2507")
    @TmsLink("MKP-409")
    public void testVerifyCTxCode2507() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2507, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2507));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_SUBSCRIBER_BLOCKED\" for Transaction code 2231")
    @TmsLink("MKP-511")
    public void testVerifyCTxCode2231() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2231, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2231));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_FINANCIAL\" for Transaction code 2235")
    @TmsLink("MKP-514")
    public void testVerifyCTxCode2235() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2235, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2235));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_STOCK_ITEM_INVALID_RESERVARTION_ID\" for Transaction code 2251")
    @TmsLink("MKP-427")
    public void testVerifyCTxCode2251() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2251, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2251));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_INVALID_MSISDN\" for Transaction code 2213")
    @TmsLink("MKP-397")
    public void testVerifyCTxCode2213() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2213, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2213));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_INVALID_REQUEST\" for Transaction code 2210")
    @TmsLink("MKP-349")
    public void testVerifyCTxCode2210() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2210, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2210));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_BACKEND_SYSTEM\" for Transaction code 2228")
    @TmsLink("MKP-341")
    public void testVerifyCTxCode2228() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2228, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2228));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_CONNECT\" for Transaction code 2238")
    @TmsLink("MKP-508")
    public void testVerifyCTxCode2238() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2228, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2228));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_LIMIT_EXCEEDED_MONTHLY_ORIGIN\" for Transaction code 2403")
    @TmsLink("MKP-481")
    public void testVerifyCTxCode2403() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2403, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2403));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_EXEC_UNKNOWN\" for Transaction code 2240")
    @TmsLink("MKP-513")
    public void testVerifyCTxCode2240() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2240, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2240));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_MERCHANT_AUTH_FAILED\" for Transaction code 2223")
    @TmsLink("MKP-379")
    public void testVerifyCTxCode2223() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2223, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2223));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_VENDOR\" for Transaction code 2505")
    @TmsLink("MKP-503")
    public void testVerifyCTxCode2505() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2505, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2505));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_INVALID_SERVICE_PROVIDER\" for Transaction code 2227")
    @TmsLink("MKP-435")
    public void testVerifyCTxCode2227() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2227, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2227));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_INVALID_PRODUCT\" for Transaction code 2215")
    @TmsLink("MKP-423")
    public void testVerifyCTxCode2215() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2215, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2215));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_MERCHANT_INSUFFICIENT_FUNDS\" for Transaction code 2221")
    @TmsLink("MKP-432")
    public void testVerifyCTxCode2221() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2221, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2221));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_CLIENT_LIMIT_NOT_CONFIGURED\" for Transaction code 2405")
    @TmsLink("MKP-506")
    public void testVerifyCTxCode2405() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2405, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2405));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_INVALID_RECHARGE\" for Transaction code 2214")
    @TmsLink("MKP-424")
    public void testVerifyCTxCode2214() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2214, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2214));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_INVALID_AMOUNT_MIN\" for Transaction code 2509")
    @TmsLink("MKP-445")
    public void testVerifyCTxCode2509() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2509, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2509));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_SUBSCRIBER_LIMIT\" for Transaction code 2219")
    @TmsLink("MKP-467")
    public void testVerifyCTxCode2219() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2219, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2219));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_LIMIT_EXCEEDED\" for Transaction code 2237")
    @TmsLink("MKP-407")
    public void testVerifyCTxCode2237() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2237, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2237));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_TRANSACTION_TIMEOUT\" for Transaction code 2224")
    @TmsLink("MKP-395")
    public void testVerifyCTxCode2224() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2224, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2224));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_PRODUCT\" for Transaction code 2503")
    @TmsLink("MKP-422")
    public void testVerifyCTxCode2503() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2503, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2503));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VENDOR_ERROR_INVALID_FORMAT\" for Transaction code 2234")
    @TmsLink("MKP-457")
    public void testVerifyCTxCode2234() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2234, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2234));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_VALIDATION_ERROR_PRODUCT_AMOUNT\" for Transaction code 2506")
    @TmsLink("MKP-479")
    public void testVerifyCTxCode2506() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2506, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2506));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: RetryableDecline : Verify \"COMPLETED_VENDOR_COMMUNICATION_TIMEOUT_ERROR\" for System code \"2203\"")
    @TmsLink("MKP-1299")
    public void testVerifyCTxCode2203() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, ResponseCode_2203, ResponseCode_2236, FeeAmount0, FeeAmount0, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2236));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        val SELECT_SYSTEM_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_SYSTEM_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID, ClientTransactionID));
        Assertions.assertThat(SELECT_SYSTEM_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_VALUE)
                .contains(ResponseCode_2203);
        val SELECT_TRANSACTION_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_TRANSACTION_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID, ClientTransactionID));
        Assertions.assertThat(SELECT_TRANSACTION_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_VALUE)
                .contains(ResponseCode_2236);
        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: RetryableDecline : Verify \"COMPLETED_VENDOR_COMMUNICATION_ERROR\" for System code \"2202\"")
    @TmsLink("MKP-489")
    public void testVerifyCTxCode2202() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, ResponseCode_2202, ResponseCode_2238, FeeAmount0, FeeAmount0, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2238));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        val SELECT_SYSTEM_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_SYSTEM_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID, ClientTransactionID));
        Assertions.assertThat(SELECT_SYSTEM_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_VALUE)
                .contains(ResponseCode_2202);
        val SELECT_TRANSACTION_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_TRANSACTION_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID, ClientTransactionID));
        Assertions.assertThat(SELECT_TRANSACTION_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_VALUE)
                .contains(ResponseCode_2238);
//        val SELECT_VENDOR_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_VENDOR_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID , ClientTransactionID));
//        Assertions.assertThat(SELECT_VENDOR_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_VALUE)
//                .contains(ResponseCode_200);

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Success :: Verify \"COMPLETED_SUCCESS\" for System code 0")
    @TmsLink("MKP-374")
    public void testVerifySystemCode0() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(FeeAmount0));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        val SELECT_SYSTEM_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_SYSTEM_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID, ClientTransactionID));
        Assertions.assertThat(SELECT_SYSTEM_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_VALUE)
                .contains(FeeAmount0);
        val SELECT_TRANSACTION_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_TRANSACTION_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID, ClientTransactionID));
        Assertions.assertThat(SELECT_TRANSACTION_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_VALUE)
                .contains(FeeAmount0);
        val SELECT_VENDOR_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_VENDOR_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID, ClientTransactionID));
        Assertions.assertThat(SELECT_VENDOR_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_VALUE)
                .contains(FeeAmount0);
        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX :: Verify \"COMPLETED_LIMIT_EXCEEDED_DAILY_ORIGIN\" for Transaction code 2404")
    @TmsLink("MKP-1429")
    public void testVerifyCTxCode2404() throws InterruptedException {
        Map body = SetupSetVendData(Identifier, Identifier, FeeAmount0, ResponseCode_2404, FeeAmount0, FeeAmount0, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2404));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);
    }

    @Test
    @Description("CTX : Verify response code 2604 when duplicate client ID is sent in request")
    @TmsLink("MKP-1349")
    public void testVerifyCTxCode2604DuplicateClientID() {

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);

        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());
        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(responseCode0));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        String lookupPurchaseRequest = LookupCtxCreateBody(TestClient3, PurchaseAmount10000, Identifier, ClientTransactionID, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827170);
        val lookupPurchaseResponse = validateCustomerAccount(lookupPurchaseRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(lookupPurchaseResponse.getBody().asString());
        XmlPath lookupPurchaseXmlPath = new XmlPath(lookupPurchaseResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(lookupPurchaseXmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2604));
    }

    @Test
    @Description("Transact :: CTX : DB validation Query Transaction ")
    @TmsLink("MKP-1359")
    public void testDBValidationForQueryTransaction() {

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);

        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());
        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        val responseCode = xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode");
        assertThat(responseCode, is(responseCode0));
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        String QueryLookupRequest = CreateCTXQueryTransactionBody(TestClient3, alternateClientId, ClientTransactionID);
        val QueryTransactionResponse = queryTransactionRequest(QueryLookupRequest)
                .then().statusCode(200)
                .extract().response();
        System.out.println(QueryTransactionResponse.getBody().asString());
        XmlPath xmlPathQueryLookup = new XmlPath(QueryTransactionResponse.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPathQueryLookup.getString("soapenv:Envelope.soapenv:Body.queryTransactionResponse.responseCode"), is(responseCode));
        val responsePurchaseAmount = xmlPathQueryLookup.getString("soapenv:Envelope.soapenv:Body.queryTransactionResponse.purchaseAmount");
        assertThat(responsePurchaseAmount, notNullValue());
        assertThat(xmlPathQueryLookup.getString("soapenv:Envelope.soapenv:Body.queryTransactionResponse.timeLocalTransaction"), notNullValue());
        assertThat(xmlPathQueryLookup.getString("soapenv:Envelope.soapenv:Body.queryTransactionResponse.dateLocalTransaction"), notNullValue());
        val responseOriginID = xmlPathQueryLookup.getString("soapenv:Envelope.soapenv:Body.queryTransactionResponse.originId");
        assertThat(responseOriginID, notNullValue());
        assertThat(xmlPathQueryLookup.getString("soapenv:Envelope.soapenv:Body.queryTransactionResponse.clientTransactionId"), is(ClientTransactionID));
        val responseProductID = xmlPathQueryLookup.getString("soapenv:Envelope.soapenv:Body.queryTransactionResponse.productId");
        assertThat(responseProductID, notNullValue());
        val responseChannelID = xmlPathQueryLookup.getString("soapenv:Envelope.soapenv:Body.queryTransactionResponse.channelIndicator");
        assertThat(responseChannelID, notNullValue());
        assertThat(xmlPathQueryLookup.getString("soapenv:Envelope.soapenv:Body.queryTransactionResponse.transmissionDateTime"), notNullValue());
        val responseVendorRefNumber = xmlPathQueryLookup.getString("soapenv:Envelope.soapenv:Body.queryTransactionResponse.vendorReferenceNo");
        assertThat(responseVendorRefNumber, notNullValue());

        val SELECT_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, ClientTransactionID));
        System.out.println(SELECT_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        Assertions.assertThat(SELECT_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE).contains(responseVendorRefNumber);

        val SELECT_ORIGIN_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_ORIGIN_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, ClientTransactionID));
        System.out.println(SELECT_ORIGIN_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        List<String> SELECT_ORIGIN_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = Lists.transform(SELECT_ORIGIN_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE, Functions.toStringFunction());
        Assertions.assertThat(SELECT_ORIGIN_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING).contains(responseOriginID);

        val SELECT_PURCHASE_AMOUNT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_PURCHASE_AMOUNT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, ClientTransactionID));
        System.out.println(SELECT_PURCHASE_AMOUNT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        List<String> SELECT_PURCHASE_AMOUNT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = Lists.transform(SELECT_PURCHASE_AMOUNT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE, Functions.toStringFunction());
        Assertions.assertThat(SELECT_PURCHASE_AMOUNT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING).contains(responsePurchaseAmount);

        val SELECT_TXN_RES_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_TRANSACTION_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, ClientTransactionID));
        List<String> SELECT_TXN_RES_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = Lists.transform(SELECT_TXN_RES_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE, Functions.toStringFunction());
        System.out.println(SELECT_TXN_RES_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING);
        Assertions.assertThat(SELECT_TXN_RES_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING).contains(responseCode);


        val SELECT_PRODUCT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_PRODUCT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, ClientTransactionID));
        System.out.println(SELECT_PRODUCT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        List<String> SELECT_PRODUCT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = Lists.transform(SELECT_PRODUCT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE, Functions.toStringFunction());
        Assertions.assertThat(SELECT_PRODUCT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING).contains(responseProductID);

        val SELECT_Channel_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_CHANNEL_ID_BY_CLIENT_TRANSACTION_ID_FROM_CTX_CGPTX_TRAN_LOG, ClientTransactionID));
        System.out.println(SELECT_Channel_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        List<String> SELECT_Channel_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = Lists.transform(SELECT_Channel_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE, Functions.toStringFunction());
        Assertions.assertThat(SELECT_Channel_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING).contains(responseChannelID);
    }

    @Test
    @Description("CTX :: RetryableDecline : Verify \"COMPLETED_VENDOR_CONNECTION_TIMEOUT_ERROR\" for System code \"2204\"")
    @TmsLink("MKP-1369")
    public void testVerifyCTxCode2204() {

        Map body = SetupSetVendData(Identifier, Identifier, ResponseCode_2204, ResponseCode_2239, FeeAmount0, FeeAmount0, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, EnvPort)
                .then().statusCode(200);
        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier, channelSessionId_3133827176);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();
        System.out.println(Response.getBody().asString());

        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode"), is(ResponseCode_2239));
        assertThat(xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId"), notNullValue());
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);

        val SELECT_SYSTEM_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_SYSTEM_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID, ClientTransactionID));
        Assertions.assertThat(SELECT_SYSTEM_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_VALUE)
                .contains(ResponseCode_2204);
        val SELECT_TRANSACTION_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_TRANSACTION_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID, ClientTransactionID));
        Assertions.assertThat(SELECT_TRANSACTION_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_VALUE)
                .contains(ResponseCode_2239);

        Map body_Success = SetupSetVendData(Identifier, Identifier, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);

    }

    @Test
    @Description("Transact :: CTX : DB Validation for Successful purchase")
    @TmsLink("MKP-1304")
    public void testDBValidationForSuccessfulPurchase() throws InterruptedException {

        Map body_Success = SetupSetVendData(Identifier_20, Identifier_20, FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200, mwmSuccess, vendorTransactionReference);
        PostControlApiBehaviour(body_Success, Vendor21, EnvPort)
                .then().statusCode(200);

        String requestBody = CtxCreateBody(ProductTypeAirtime_3, PurchaseAmount10000, Identifier_20, ProductAirtel_917, ChannelID_07, alternateClientId, Identifier_20, channelSessionId_3133827170);
        val Response = validateCustomerAccount(requestBody)
                .then().statusCode(200)
                .extract().response();

        System.out.println(Response.getBody().asString());
        Thread.sleep(10000);
        XmlPath xmlPath = new XmlPath(Response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        String ClientTransactionID = xmlPath.get("soapenv:Envelope.soapenv:Body.purchaseResponse.clientTransactionId");
        System.out.println(ClientTransactionID);
        val responseCode = xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.responseCode");
        assertThat(responseCode, is(responseCode0));
        val responseTransactionID = xmlPath.getString("soapenv:Envelope.soapenv:Body.purchaseResponse.vendorReferenceNo");
        assertThat(responseTransactionID, notNullValue());

        val SELECT_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, ClientTransactionID));
        System.out.println(SELECT_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        Assertions.assertThat(SELECT_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE).contains(responseTransactionID);

        val SELECT_CLIENT_STAN_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_CLIENT_STAN_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, ClientTransactionID));
        System.out.println(SELECT_CLIENT_STAN_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        Assertions.assertThat(SELECT_CLIENT_STAN_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE).isNotNull();

        val SELECT_END_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_END_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, ClientTransactionID));
       System.out.println(SELECT_END_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        Assertions.assertThat(SELECT_END_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE).isNotNull();

        val SELECT_END_VENDOR_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_END_VENDOR_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, ClientTransactionID));
        System.out.println(SELECT_END_VENDOR_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        Assertions.assertThat(SELECT_END_VENDOR_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE).isNotNull();

        val SELECT_INTERNAL_RESPONSE_REASON_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_INTERNAL_RESPONSE_REASON_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, ClientTransactionID));
        System.out.println(SELECT_INTERNAL_RESPONSE_REASON_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        Assertions.assertThat(SELECT_INTERNAL_RESPONSE_REASON_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE).containsNull();

        val SELECT_MESSAGE_IDENTIFIER_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_MESSAGE_IDENTIFIER_FROM_CGPTX_TRAN_LOG, ClientTransactionID));
        System.out.println(SELECT_MESSAGE_IDENTIFIER_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        List<String> SELECT_MESSAGE_IDENTIFIER_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = Lists.transform(SELECT_MESSAGE_IDENTIFIER_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE, Functions.toStringFunction());
        Assertions.assertThat(SELECT_MESSAGE_IDENTIFIER_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING).contains(FeeAmount0);

        val SELECT_ORIGIN_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_ORIGIN_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, ClientTransactionID));
        System.out.println(SELECT_ORIGIN_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        Assertions.assertThat(SELECT_ORIGIN_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE).contains(Identifier_20);

        val SELECT_ORIGINATING_SERVICE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_ORIGINATING_SERVICE_FROM_CGPTX_TRAN_LOG, ClientTransactionID));
        System.out.println(SELECT_ORIGINATING_SERVICE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        Assertions.assertThat(SELECT_ORIGINATING_SERVICE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE).isNotNull();

        val SELECT_Purchase_Amount_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_CTX_PURCHASE_AMOUNT_FROM_CGPTX_TRAN_LOG, ClientTransactionID));
        System.out.println(SELECT_Purchase_Amount_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        List<String> SELECT_Purchase_Amount_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = Lists.transform(SELECT_Purchase_Amount_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE, Functions.toStringFunction());
        Assertions.assertThat(SELECT_Purchase_Amount_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING).contains(PurchaseAmount10000);

        val SELECT_QUANTITY_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_QUANTITY_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, ClientTransactionID));
        System.out.println(SELECT_QUANTITY_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        Assertions.assertThat(SELECT_QUANTITY_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE).containsNull();

        val SELECT_TXN_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_TRANSACTION_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID, ClientTransactionID));
        System.out.println(SELECT_TXN_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        List<String> SELECT_TXN_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = Lists.transform(SELECT_TXN_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE, Functions.toStringFunction());
        Assertions.assertThat(SELECT_TXN_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING).contains(responseCode);

        val SELECT_REVERSAL_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_REVERSAL_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, ClientTransactionID));
        System.out.println(SELECT_REVERSAL_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        Assertions.assertThat(SELECT_REVERSAL_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE).containsNull();

        val SELECT_START_ACCOUNTING_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_START_ACCOUNTING_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, ClientTransactionID));
        System.out.println(SELECT_START_ACCOUNTING_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        Assertions.assertThat(SELECT_START_ACCOUNTING_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE).containsNull();

       val SELECT_START_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_CTX_START_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, ClientTransactionID));
       System.out.println(SELECT_START_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
       Assertions.assertThat(SELECT_START_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE).isNotNull();

        val SELECT_START_VENDOR_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_CTX_START_VENDOR_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, ClientTransactionID));
        System.out.println(SELECT_START_VENDOR_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        Assertions.assertThat(SELECT_START_VENDOR_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE).isNotNull();

        val SELECT_TRANSACTION_STATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_TRANSACTION_STATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, ClientTransactionID));
        System.out.println(SELECT_TRANSACTION_STATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        Assertions.assertThat(SELECT_TRANSACTION_STATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE).contains("C");

        val SELECT_TRANSACTION_TYPE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_TRANSACTION_TYPE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, ClientTransactionID));
        System.out.println(SELECT_TRANSACTION_TYPE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        Assertions.assertThat(SELECT_TRANSACTION_TYPE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE).contains("P");

        val SELECT_VENDOR_REFERENCE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_VENDOR_REFERENCE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, ClientTransactionID));
        System.out.println(SELECT_VENDOR_REFERENCE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        Assertions.assertThat(SELECT_VENDOR_REFERENCE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE).isNotNull();

        val SELECT_VENDOR_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_VENDOR_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, ClientTransactionID));
        System.out.println(SELECT_VENDOR_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        List<String> SELECT_VENDOR_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = Lists.transform(SELECT_VENDOR_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE, Functions.toStringFunction());
        Assertions.assertThat(SELECT_VENDOR_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING).contains(ResponseCode_200);

        val SELECT_VENDOR_STAN_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_VENDOR_STAN_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, ClientTransactionID));
        System.out.println(SELECT_VENDOR_STAN_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        Assertions.assertThat(SELECT_VENDOR_STAN_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE).isNotNull();

        val SELECT_CLIENT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_CLIENT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, ClientTransactionID));
        System.out.println(SELECT_CLIENT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        List<String> SELECT_CLIENT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = Lists.transform(SELECT_CLIENT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE, Functions.toStringFunction());
        Assertions.assertThat(SELECT_CLIENT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING).contains(ProductTypeAirtime_3);

        val SELECT_PRODUCT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_PRODUCT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, ClientTransactionID));
        System.out.println(SELECT_PRODUCT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        List<String> SELECT_PRODUCT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = Lists.transform(SELECT_PRODUCT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE, Functions.toStringFunction());
        Assertions.assertThat(SELECT_PRODUCT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING).contains(ProductAirtel_917);

        val SELECT_VENDOR_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_VENDOR_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, ClientTransactionID));
        System.out.println(SELECT_VENDOR_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        List<String> SELECT_VENDOR_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = Lists.transform(SELECT_VENDOR_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE, Functions.toStringFunction());
        Assertions.assertThat(SELECT_VENDOR_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING).contains(Vendor21);

      val SELECT_SYSTEM_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_SYSTEM_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, ClientTransactionID));
       System.out.println(SELECT_SYSTEM_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
      List<String> SELECT_SYSTEM_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = Lists.transform(SELECT_SYSTEM_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE, Functions.toStringFunction());
      Assertions.assertThat(SELECT_SYSTEM_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING).contains(FeeAmount0);

        val SELECT_RESPONSE_CODE_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, ClientTransactionID));
        System.out.println(SELECT_RESPONSE_CODE_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        Assertions.assertThat(SELECT_RESPONSE_CODE_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE).containsNull();

        val SELECT_FEE_AMOUNT_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_FEE_AMOUNT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, ClientTransactionID));
        System.out.println(SELECT_FEE_AMOUNT_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        Assertions.assertThat(SELECT_FEE_AMOUNT_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE).containsNull();

        val SELECT_RECON_STATE_CLIENT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_RECON_STATE_CLIENT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, ClientTransactionID));
        System.out.println(SELECT_RECON_STATE_CLIENT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        Assertions.assertThat(SELECT_RECON_STATE_CLIENT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE).containsNull();

        val SELECT_RECON_STATE_VENDOR_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_RECON_STATE_VENDOR_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, ClientTransactionID));
        System.out.println(SELECT_RECON_STATE_VENDOR_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        Assertions.assertThat(SELECT_RECON_STATE_VENDOR_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE).containsNull();

        val SELECT_RECON_NOTE_VENDOR_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_RECON_NOTE_VENDOR_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, ClientTransactionID));
        System.out.println(SELECT_RECON_NOTE_VENDOR_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        Assertions.assertThat(SELECT_RECON_NOTE_VENDOR_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE).containsNull();

        val SELECT_LAST_UPDATED_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_CTX_LAST_UPDATED_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, ClientTransactionID));
        System.out.println(SELECT_LAST_UPDATED_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        Assertions.assertThat(SELECT_LAST_UPDATED_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE).isNotNull();

        val SELECT_COMM_MODEL_TYPE_BY_CLIENT_VENDOR_PROD_TYPE_ID_FROM_COMM_MODEL_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_COMM_MODEL_TYPE_BY_CLIENT_VENDOR_PROD_TYPE_ID_FROM_COMM_MODEL, TestClient3, Vendor21, TestClient3));
        List<String> SELECT_COMM_MODEL_TYPE_BY_CLIENT_VENDOR_PROD_TYPE_ID_FROM_COMM_MODEL_VALUE_STRING = Lists.transform(SELECT_COMM_MODEL_TYPE_BY_CLIENT_VENDOR_PROD_TYPE_ID_FROM_COMM_MODEL_VALUE, Functions.toStringFunction());
        System.out.println(SELECT_COMM_MODEL_TYPE_BY_CLIENT_VENDOR_PROD_TYPE_ID_FROM_COMM_MODEL_VALUE_STRING);
        Assertions.assertThat(SELECT_COMM_MODEL_TYPE_BY_CLIENT_VENDOR_PROD_TYPE_ID_FROM_COMM_MODEL_VALUE_STRING)
                .contains(CommissionModel);

        val SELECT_COMM_AMOUNT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_Commission_Amount_VENDOR_BY_CLIENT_TRANSACTION_ID_CTX_FROM_CGPTX_TRAN_LOG, ClientTransactionID));
        System.out.println(SELECT_COMM_AMOUNT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        List<String> SELECT_COMM_AMOUNT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = Lists.transform(SELECT_COMM_AMOUNT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE, Functions.toStringFunction());
        Assertions.assertThat(SELECT_COMM_AMOUNT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING)
                .contains(FeeAmount0);

       val SELECT_GLOBAL_REASON_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_CTX_Global_Reason_VENDOR_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG,ClientTransactionID ));
        System.out.println(SELECT_GLOBAL_REASON_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        Assertions.assertThat(SELECT_GLOBAL_REASON_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE)
                .containsNull();

        val SELECT_CHANNEL_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_CHANNEL_ID_BY_CLIENT_TRANSACTION_ID_FROM_CTX_CGPTX_TRAN_LOG, ClientTransactionID));
        System.out.println(SELECT_CHANNEL_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        List<String> SELECT_CHANNEL_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = Lists.transform(SELECT_CHANNEL_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE, Functions.toStringFunction());
        Assertions.assertThat(SELECT_CHANNEL_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING)
                .contains(ChannelID_7);

        val SELECT_API_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_CTX_API_ID_VENDOR_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, ClientTransactionID));
        System.out.println(SELECT_API_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        List<String> SELECT_API_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = Lists.transform(SELECT_API_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE, Functions.toStringFunction());
        Assertions.assertThat(SELECT_API_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING)
                .contains(Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2);

        val SELECT_COMM_AMOUNT_INTERNAL_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_CTX_Commission_Amount_Internal_VENDOR_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, ClientTransactionID));
        System.out.println(SELECT_COMM_AMOUNT_INTERNAL_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        List<String> SELECT_COMM_AMOUNT_INTERNAL_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = Lists.transform(SELECT_COMM_AMOUNT_INTERNAL_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE, Functions.toStringFunction());
        Assertions.assertThat(SELECT_COMM_AMOUNT_INTERNAL_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING)
                .contains(FeeAmount0);
    }
}