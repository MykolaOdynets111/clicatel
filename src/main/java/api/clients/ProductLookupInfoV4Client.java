package api.clients;

import api.enums.Port;
import api.enums.Version;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;

public class ProductLookupInfoV4Client extends BasedAPIClient {

    public static Response putProductInfo(Port port, Version version, Map<String,String> queryParams, Map<String,Object> body) {
        return basedAPIClient.get()
                .put(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s:%s/public/%s/productInfo",productLookupUrl,EnvPort,version.getVersion()))
                        .addQueryParams(queryParams)
                        .setContentType(JSON)
                        .setBody(body)
                        .log(ALL)
                        .build());
    }
    public static Response putProductInfo(Port port, Version version, Map<String,String> queryParams) {
        return basedAPIClient.get()
                .put(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s:%s/public/%s/productInfo",productLookupUrl,EnvPort,version.getVersion()))
                        .addQueryParams(queryParams)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }
}
