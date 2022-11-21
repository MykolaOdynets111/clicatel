package api.clients;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;

public class DistributedDistributionClient extends BasedAPIClient{

    public static Response getMessageDistributionTemplates() {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/message-distribution/reloadTemplates", distributedDistributionURL))
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }


}