package api.clients;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import java.util.Map;
import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;

public class CtxHistory extends BasedAPIClient {

    public static Response GetFindSuccessful(Map<String,String> queryParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/findSuccessful",CtxHistory))
                        .addQueryParams(queryParams)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }
    public static Response GetFindByTransactionAndClientId(Map<String,String> queryParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/findByClientTransactionIdAndClientId",CtxHistory))
                        .addQueryParams(queryParams)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }
    public static Response GetFindByLastSuccessful(Map<String,String> queryParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/findLastSuccessful",CtxHistory))
                        .addQueryParams(queryParams)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }
}
