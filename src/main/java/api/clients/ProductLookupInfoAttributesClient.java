package api.clients;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;

public class ProductLookupInfoAttributesClient extends BasedAPIClient {

    public static Response GetAttributesByAttributeId (String AttributeId) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(productLookupUrl+"/attributes/distinctAttributeValues/" + AttributeId)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());

    }
    public static Response GetAttributesByAttributeId (String AttributeId, Map map) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(productLookupUrl+"/attributes/distinctAttributeValues/" + AttributeId)
                        .addQueryParams(map)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());

    }
}
