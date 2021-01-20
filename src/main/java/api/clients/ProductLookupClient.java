package api.clients;

import io.restassured.builder.RequestSpecBuilder;
import api.enums.Port;
import api.enums.Version;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import lombok.Getter;

import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;

@Getter
public class ProductLookupClient extends BasedAPIClient {

    public static Response getProductInfo(Port port, Version version) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s:%d/public/%s/productInfo",baseUrl,port.getPort(),version.getVersion()))
                        //.setBasePath(String.format(":%d/raas/%s/reserveAndTransact",port.getPort(),version.getVersion()))
                        //.setBasePath("32000/public/v2/productInfo")
                        .addQueryParam("clientId","3")
                        .addQueryParam("productId","130")
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }
}
