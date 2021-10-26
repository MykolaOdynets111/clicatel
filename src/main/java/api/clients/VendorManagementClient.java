package api.clients;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;

import java.util.Map;

import static api.clients.BasedAPIClient.basedAPIClient;
import static api.clients.BasedAPIClient.productLookupUrl;
import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;
import static util.readers.PropertiesReader.getProperty;

public class VendorManagementClient {
    public static String ProductTypeData_5;
    public static String responseMessageSameVendorProductType;
    public static String Vendor21;
    public static String UpdatedClientName;

    static {
        ProductTypeData_5 = getProperty("ProductTypeData_5");
        responseMessageSameVendorProductType = getProperty("responseMessageSameVendorProductType");
        Vendor21 = getProperty("Vendor21");
        UpdatedClientName = getProperty("UpdatedClientName");
    }
    public static Response GetVendorProductTypeGetByInternalTypeId(Map body) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/vendorProductType/getByInternalProductTypeId",productLookupUrl))
                        .addFormParams(body)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }
    public static Response PostVendorProductType(Map body) {
        return basedAPIClient.get()
                .post(new RequestSpecBuilder()
                        .setUrlEncodingEnabled(false)
                        .setBaseUri(String.format("%s/vendorProductType/",productLookupUrl))
                        .setBody(body)
                        .setContentType(JSON)
                        .addHeader("Authorization","bearer")
                        .log(ALL)
                        .build());
    }
    public static Response GetVendorProductTypesByInternalProductTypeId(Map map) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/vendorProductType/getByInternalProductTypeId",productLookupUrl))
                        .setContentType(JSON)
                        .addParams(map)
                        .log(ALL)
                        .build());
    }
    public static Response PutVendorProductType(Map body) {
        return basedAPIClient.get()
                .put(new RequestSpecBuilder()
                        .setUrlEncodingEnabled(false)
                        .setBaseUri(String.format("%s/vendorProductType/",productLookupUrl))
                        .setBody(body)
                        .setContentType(JSON)
                        .addHeader("Authorization","bearer")
                        .log(ALL)
                        .build());
    }
    public static Response DeleteVendorProductType(Map body) {
        return basedAPIClient.get()
                .delete(new RequestSpecBuilder()
                        .setUrlEncodingEnabled(false)
                        .setBaseUri(String.format("%s/vendorProductType/",productLookupUrl))
                        .addQueryParams(body)
                        .setContentType(JSON)
                        .addHeader("Authorization","bearer")
                        .log(ALL)
                        .build());
    }

}
