package api.clients;

import api.domains.reserve_and_transact.model.ReserveAndTransactRequest;
import io.restassured.builder.RequestSpecBuilder;
import api.enums.Port;
import api.enums.Version;
import io.restassured.response.Response;
import lombok.Getter;

import java.util.Map;

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
    }

    public static Response getProductInfo(Port port, Map <String,String> queryParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s:%d/public/productInfo",productLookupUrl,port.getPort()))
                        .addQueryParams(queryParams)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

    public static Response getProductInfo(Port port, Version version, Map <String,String> queryParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s:%d/public/%s/productInfo",productLookupUrl,port.getPort(),version.getVersion()))
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
                        .setBaseUri(String.format("%s:%d/product_management/productDetails",productLookupUrl,port.getPort()))
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

}
