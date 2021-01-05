package api.clients;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import lombok.Getter;

import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;

@Getter
public class ProductLookupClient extends BasedAPIClient {

    public static Response getProductInfo() {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(baseUrl)
                        .setBasePath("public/v2/productInfo")
                        .addQueryParam("clientId","3")
                        .addQueryParam("productId","130")
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }
}
