package api.clients;

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

    static {
        ProductAirtel_130 = getProperty("ProductAirtel_130");
        ProductTypeAirtime_3 = getProperty("ProductTypeAirtime_3");
        ProductTypeNameAirtime = getProperty("ProductTypeNameAirtime");
        DataAirtelProductTypeId = getProperty("DataAirtelProductTypeId");
        AirTimeAirtelProductTypeId = getProperty("AirTimeAirtelProductTypeId");
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
                        //.addQueryParam("clientId","3")
                        //.addQueryParam("productId","130")
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
}
