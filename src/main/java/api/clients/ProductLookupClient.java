package api.clients;

import api.domains.reserve_and_transact.model.ReserveAndTransactRequest;
import io.restassured.builder.RequestSpecBuilder;
import api.enums.Port;
import api.enums.Version;
import io.restassured.response.Response;
import lombok.Getter;

import java.util.Map;

import static api.clients.ReserveAndTransactClient.ProductAirtel_917;
import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;
import static util.readers.PropertiesReader.getProperty;

@Getter
public class ProductLookupClient extends BasedAPIClient {
    public static String ProductAirtel_130;
    public static String ProductTypeAirtime_3;
    public static String ProductTypeNameAirtime;
    public static String DataAirtelProductTypeId;
    public static String AirTimeAirtelProductTypeId;
    public static String TestClient250;
    public static String Identifier_13;
    public static String Vendor100;
    public static String Product_854;
    public static String Product_130;
    public static String Identifier_14;
    public static String Identifier_15;
    public static String Vendor103;
    public static String Vendor_MTN_NG;
    public static String NewClientName;
    public static String ctxLimitTotal;
    public static String responseMessageInvalidProductIDProductInfoV3;
    public static String responseMessageClientIdNotProvidedInProductInfo;
    public static String CountryCode_710;
    public static String clickatellAccountId_TouchFlowClient;
    public static String ChannelID_8;
    public static String TimeZone_UTC;
    public static String responseMessageInvalidCountryCodeTouchClient;
    public static String responseMessageInvalidTimeZoneTouchClient;
    public static String responseMessageInvalidChannelIdTouchClient;
    public static String responseMessageNullClientNameTouchClient;
    public static String responseMessageNullTimeZoneITouchClient;
    public static String responseMessageNullCountryCodeTouchClient;
    public static String responseMessageNullChannelIdTouchClient;
    public static String ChannelID_7;
    public static String Product_1600;
    public static String ProductType_Single;
    public static String SupportToken_False;
    public static String IsTopSellerFalse;
    public static String Product_1601;
    public static String ProductType_1603;
    public static String Product_1602;
    public static String responseMessageInvalidTouchFlowClientCreation;
    public static String responseMessageInvalidTouchFlowChannelId;
    public static String Identifier_16;
    public static String Identifier_17;
    public static String responseMessageInvalidProductTypeIDProductInfoV3;
    public static String fundingSourceId_1206;
    public static String confirmationWindowSizeSeconds;
    public static String fundingSourceName;
    public static String reservationTimeout;
    public static String reserveFundsEndpoint;
    public static String retryDelay;
    public static String serviceWindowSizeSeconds;
    public static String transactionEndpoint;
    public static String fundingSourceNameUpdate;
    public static String Client_1206;
    public static String ClickatellAccountId;
    public static String Client_Role_1206;
    public static String Client_1206_update;
    public static String ctxLimitTotal_Update;
    public static String Product_Desc_206;
    public static String Product_Insert_Timestamp;




    static {
        ProductAirtel_130 = getProperty("ProductAirtel_130");
        ProductTypeAirtime_3 = getProperty("ProductTypeAirtime_3");
        ProductTypeNameAirtime = getProperty("ProductTypeNameAirtime");
        DataAirtelProductTypeId = getProperty("DataAirtelProductTypeId");
        AirTimeAirtelProductTypeId = getProperty("AirTimeAirtelProductTypeId");
        TestClient250 = getProperty("TestClient250");
        Identifier_13= getProperty("Identifier_13");
        Vendor100= getProperty("Vendor100");
        Identifier_14= getProperty("Identifier_14");
        Identifier_15= getProperty("Identifier_15");
        Vendor103= getProperty("Vendor103");
        Product_130= getProperty("Product_130");
        Product_854= getProperty("Product_854");
        Vendor_MTN_NG= getProperty("Vendor_MTN_NG");
        NewClientName= getProperty("NewClientName");
        ctxLimitTotal= getProperty("ctxLimitTotal");
        responseMessageInvalidProductIDProductInfoV3= getProperty("responseMessageInvalidProductIDProductInfoV3");
        responseMessageClientIdNotProvidedInProductInfo= getProperty("responseMessageClientIdNotProvidedInProductInfo");
        CountryCode_710= getProperty("CountryCode_710");
        clickatellAccountId_TouchFlowClient= getProperty("clickatellAccountId_TouchFlowClient");
        ChannelID_8= getProperty("ChannelID_8");
        TimeZone_UTC= getProperty("TimeZone_UTC");
        responseMessageInvalidCountryCodeTouchClient= getProperty("responseMessageInvalidCountryCodeTouchClient");
        responseMessageInvalidTimeZoneTouchClient= getProperty("responseMessageInvalidTimeZoneTouchClient");
        responseMessageInvalidChannelIdTouchClient= getProperty("responseMessageInvalidChannelIdTouchClient");
        responseMessageNullClientNameTouchClient= getProperty("responseMessageNullClientNameTouchClient");
        responseMessageNullTimeZoneITouchClient= getProperty("responseMessageNullTimeZoneITouchClient");
        responseMessageNullCountryCodeTouchClient= getProperty("responseMessageNullCountryCodeTouchClient");
        responseMessageNullChannelIdTouchClient= getProperty("responseMessageNullChannelIdTouchClient");
        ChannelID_7= getProperty("ChannelID_7");
        Product_1600= getProperty("Product_1600");
        ProductType_Single= getProperty("ProductType_Single");
        SupportToken_False= getProperty("SupportToken_False");
        IsTopSellerFalse= getProperty("IsTopSellerFalse");
        Product_1601= getProperty("Product_1601");
        ProductType_1603= getProperty("ProductType_1603");
        Product_1602= getProperty("Product_1602");
        responseMessageInvalidTouchFlowClientCreation= getProperty("responseMessageInvalidTouchFlowClientCreation");
        responseMessageInvalidTouchFlowChannelId= getProperty("responseMessageInvalidTouchFlowChannelId");
        Identifier_16= getProperty("Identifier_16");
        Identifier_17= getProperty("Identifier_17");
        responseMessageInvalidProductTypeIDProductInfoV3= getProperty("responseMessageInvalidProductTypeIDProductInfoV3");
        fundingSourceId_1206= getProperty("fundingSourceId_1206");
        confirmationWindowSizeSeconds= getProperty("confirmationWindowSizeSeconds");
        fundingSourceName= getProperty("fundingSourceName");
        reservationTimeout= getProperty("reservationTimeout");
        reserveFundsEndpoint= getProperty("reserveFundsEndpoint");
        retryDelay= getProperty("retryDelay");
        serviceWindowSizeSeconds= getProperty("serviceWindowSizeSeconds");
        transactionEndpoint= getProperty("transactionEndpoint");
        fundingSourceNameUpdate= getProperty("fundingSourceNameUpdate");
        Client_1206= getProperty("Client_1206");
        ClickatellAccountId= getProperty("ClickatellAccountId");
        Client_Role_1206= getProperty("Client_Role_1206");
        Client_1206_update= getProperty("Client_1206_update");
        ctxLimitTotal_Update= getProperty("ctxLimitTotal_Update");
        Product_Desc_206= getProperty("Product_Desc_206");
        Product_Insert_Timestamp= getProperty("Product_Insert_Timestamp");
    }

    public static Response getProductInfo(Port port, Map <String,String> queryParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s:%s/public/productInfo",productLookupUrl,EnvPort))
                        .addQueryParams(queryParams)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

    public static Response getProductInfo(Port port, Version version, Map <String,String> queryParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s:%s/public/%s/productInfo",productLookupUrl,EnvPort,version.getVersion()))
                        .addQueryParams(queryParams)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

    public static Response getProductInfoWithSecretValue(ReserveAndTransactRequest body) {
        return basedAPIClient.get()
                .post(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/signature/sign",productLookupUrl))
                        .setBody(body)
                        .addQueryParam("secretValue","zaqwsxcderfv123")
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

    public static Response getProductDetails(Port port, Version version, Map <String,String> queryParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s:%s/product_management/productDetails",productLookupUrl,EnvPort))
                        .addQueryParams(queryParams)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }


    public static Response GetVendorByIdExpanded(Map<String,String> queryParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/vendors/vendorByIdExpanded",productLookupUrl))
                        .addQueryParams(queryParams)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

    public static Response GetVendorProductTypes() {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/vendorProductType/listAll",productLookupUrl))
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

    public static Response GetVendorCtxProductById(Map<String,String> queryParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/ctx/vendorProduct",productLookupUrl))
                        .addQueryParams(queryParams)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

    public static Response getClients()
    {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/clients",productLookupUrl))
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }
    public static Response PostNewClient(Map body) {
        return basedAPIClient.get()
                .post(new RequestSpecBuilder()
                        .setUrlEncodingEnabled(false)
                        .setBaseUri(String.format("%s/clients",productLookupUrl))
                        .setBody(body)
                        .setContentType(JSON)
                        .addHeader("Authorization","bearer")
                        .log(ALL)
                        .build());
    }
    public static Response PostTouchFlowClient(Map map) {
        return basedAPIClient.get()
                .post(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/clients/createClientForTouchFlow",productLookupUrl))
                        .setBody(map)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }
    public static Response PostUpdateClient(Map body, String ClientId) {
        return basedAPIClient.get()
                .put(new RequestSpecBuilder()
                        .setUrlEncodingEnabled(false)
                        .setBaseUri(String.format("%s/clients/"+ClientId,productLookupUrl))
                        .setBody(body)
                        .setContentType(JSON)
                        .addHeader("Authorization","bearer")
                        .log(ALL)
                        .build());
    }
    public static Response PostChannelForTouchFlowClient(Map map) {
        return basedAPIClient.get()
                .post(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/clients/addNewTouchflowClientChannels",productLookupUrl))
                        .setBody(map)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }
    public static Response PutUpdateProduct(Map body) {
        return basedAPIClient.get()
                .put(new RequestSpecBuilder()
                        .setUrlEncodingEnabled(false)
                        .setBaseUri(String.format("%s/product_management/updateWithResult",productLookupUrl))
                        .setBody(body)
                        .setContentType(JSON)
                        .addHeader("Authorization","bearer")
                        .log(ALL)
                        .build());
    }
    public static Response getClientsFromBackend(String ClientID)
    {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/client/CLIENT/"+ClientID,ControlBackend))
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

    public static Response GetProductsClientVendor(Map <String, String> pathParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .addPathParams(pathParams)
                        .setBaseUri(productLookupUrl+"/products/")
                        .setContentType(JSON)
                        .log(ALL)
                        .build(), "{clientId}/{vendorId}");
    }

    public static Response GetProductsdetailsClientIdProductId (Map <String, String> pathParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .addPathParams(pathParams)
                        .setBaseUri(productLookupUrl+"/productsdetails/client/")
                        .setContentType(JSON)
                        .log(ALL)
                        .build(), "{clientId}/product/{productId}");
    }

    public static Response GetVendorClientId (Map <String, String> pathParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .addPathParams(pathParams)
                        .setBaseUri(productLookupUrl+"/vendor/")
                        .setContentType(JSON)
                        .log(ALL)
                        .build(), "{clientId}");
    }

    public static Response GetProductTypeProductId (Map <String, String> pathParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .addPathParams(pathParams)
                        .setBaseUri(productLookupUrl+"/productType/")
                        .setContentType(JSON)
                        .log(ALL)
                        .build(), "{productId}");
    }

    public static Response GetActiveProductsByCountryCode(Map<String,String> queryParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/getActiveProductsByCountryCode", productLookupUrl))
                        .addQueryParams(queryParams)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

    public static Response GetProducts () {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/products/",productLookupUrl))
                        .addQueryParam("productIds", ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.ProductAirtel_100)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

    public static Response GetProductByClientIdProductId(Map <String, String> pathParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .addPathParams(pathParams)
                        .setBaseUri(productLookupUrl+"/productbyclientidproductid/")
                        .setContentType(JSON)
                        .log(ALL)
                        .build(), "{clientId},{productId}");
    }

    public static Response GetToppickClientIdVendorId(Map <String, String> pathParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .addPathParams(pathParams)
                        .setBaseUri(productLookupUrl+"/toppick/")
                        .setContentType(JSON)
                        .log(ALL)
                        .build(), "{clientId},{vendorId}");
    }


    public static Response GetGetProduct(Map<String,String> queryParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/getProduct",productLookupUrl))
                        .addQueryParams(queryParams)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

    public static Response GetProductsClientIdVendorIdProductId (Map <String, String> pathParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .addPathParams(pathParams)
                        .setBaseUri(productLookupUrl+"/product/")
                        .setContentType(JSON)
                        .log(ALL)
                        .build(), "{clientId},{vendorId},{productId}");
    }

    public static Response GetVendorsMap () {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(productLookupUrl+"/vendorsMap")
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

    public static Response GetClientsMap () {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(productLookupUrl+"/clientsMap")
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

    public static Response GetProductsProductTypeIDClientIdVendorId (Map <String, String> pathParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .addPathParams(pathParams)
                        .setBaseUri(productLookupUrl+"/products/productType/")
                        .setContentType(JSON)
                        .log(ALL)
                        .build(), "{productTypeId}/{clientId}/{vendorId}");
    }

    public static Response GetProductsProductTypeId (Map <String, String> pathParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .addPathParams(pathParams)
                        .setBaseUri(productLookupUrl+"/products/")
                        .setContentType(JSON)
                        .log(ALL)
                        .build(), "{productTypeId}");
    }

    public static Response GetProductsVendorProductIdsVendorId (Map <String, String> pathParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .addPathParams(pathParams)
                        .setBaseUri(productLookupUrl+"/products/vendorProductIds/")
                        .setContentType(JSON)
                        .log(ALL)
                        .build(), "{vendorId}");
    }

    public static Response GetVendorProductsByProductType (Map <String, String> pathParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .addPathParams(pathParams)
                        .setBaseUri(productLookupUrl+"/products/vendorProductsByProductType/")
                        .setContentType(JSON)
                        .log(ALL)
                        .build(), "{vendorId}/{productTypeId}/");
    }

    public static Response GetClientProductAssociationCountryCode (Map <String, String> pathParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .addPathParams(pathParams)
                        .setBaseUri(productLookupUrl+"/products/clientProductAssociation/")
                        .setContentType(JSON)
                        .log(ALL)
                        .build(), "{countryCode}/{clientId}");
    }

    public static Response GetProductsClientVendorProductIds (Map <String, String> pathParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .addPathParams(pathParams)
                        .setBaseUri(productLookupUrl+"/products/clientVendorProductIds/")
                        .setContentType(JSON)
                        .log(ALL)
                        .build(), "{clientId}/{vendorId}");
    }

    public static Response GetProductsdetailsClientIdVendorId (Map <String, String> pathParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .addPathParams(pathParams)
                        .setBaseUri(productLookupUrl+"/productsdetails/")
                        .setContentType(JSON)
                        .log(ALL)
                        .build(), "{clientId},{vendorId}");
    }

    public static Response GetClientProducts (Map <String, String> pathParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .addPathParams(pathParams)
                        .setBaseUri(productLookupUrl+"/clientProducts/")
                        .setContentType(JSON)
                        .log(ALL)
                        .build(), "{clientId}");
    }

    public static Response GetProductsdetailsClientIdCendorIdProductId (Map <String, String> pathParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .addPathParams(pathParams)
                        .setBaseUri(productLookupUrl+"/productsdetails/client/")
                        .setContentType(JSON)
                        .log(ALL)
                        .build(), "{clientId}/product/{vendorId}/product/{productId}");
    }
    public static Response executePostCreateFundingSource(Map body) {
        return basedAPIClient.get()
                .post(new RequestSpecBuilder()
                        .setUrlEncodingEnabled(false)
                        .setBaseUri(productLookupUrl+"/fundingSources/create")
                        .setBody(body)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }
    public static Response GetFundingSourceByFundingSourceID (String FundingSourceId) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(productLookupUrl+"/fundingSources/" + FundingSourceId)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());

}
    public static Response executePostUpdateFundingSource(Map body) {
        return basedAPIClient.get()
                .put(new RequestSpecBuilder()
                        .setUrlEncodingEnabled(false)
                        .setBaseUri(productLookupUrl+"/fundingSources/update")
                        .setBody(body)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }
    public static Response getClientsByClientId(String ClientId)
    {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/clients/"+ClientId,productLookupUrl))
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }
    public static Response DeleteProductAssociationWithClient(String ClientId, String ProductId) {
        return basedAPIClient.get()
                .delete(new RequestSpecBuilder()
                        .setUrlEncodingEnabled(false)
                        .setBaseUri(String.format("%s/clients/"+ClientId +"/product/"+ProductId,productLookupUrl))
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }
    public static Response PostClientProductAssociation (Map <Object, Object> body, String ClientID) {
        return basedAPIClient.get()
                .post(new RequestSpecBuilder()
                        .setUrlEncodingEnabled(false)
                        .setBaseUri(productLookupUrl+"/clients/"+ClientID+"/productAssociation")
                        .setBody(body)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

    public static Response PostFundingSourceByClientId (String ClientID, String FundingSourceId) {
        return basedAPIClient.get()
                .post(new RequestSpecBuilder()
                        .setUrlEncodingEnabled(false)
                        .setBaseUri(productLookupUrl+"/clients/"+ClientID+"/fundingSource/"+FundingSourceId)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }
    }
