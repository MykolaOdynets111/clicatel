package api.clients;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;

public class VendorConfigClient extends BasedAPIClient {

    public static Response getVendor() {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/vendor/21",vendorConfigURL))
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }
}