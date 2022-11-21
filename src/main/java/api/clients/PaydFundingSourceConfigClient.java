package api.clients;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;

import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;

public class PaydFundingSourceConfigClient extends BasedAPIClient {
    public static Response getFundingSource(String fundingSourceID)
    {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(paydFundingSourceConfig+"/fundingSource/"+fundingSourceID)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }
}