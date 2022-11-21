package api.clients;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;

import java.util.Map;

import static api.clients.BasedAPIClient.basedAPIClient;
import static api.clients.BasedAPIClient.bulkLoaderURL;
import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;

public class BulkLoaderClient {
     public static Response getCampaigns(Map<String,String> queryParams){
         return basedAPIClient.get()
                 .get(new RequestSpecBuilder()
                         .setBaseUri(String.format("%s/searchCampaigns",bulkLoaderURL))
                         .setContentType(JSON)
                         .addQueryParams(queryParams)
                         .setRelaxedHTTPSValidation()
                         .log(ALL)
                         .build());
     }
}