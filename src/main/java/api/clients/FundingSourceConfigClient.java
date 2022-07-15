package api.clients;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;

public class FundingSourceConfigClient extends BasedAPIClient{

    public static Response GetFundingSources()
    {
       return basedAPIClient.get()
               .get(new RequestSpecBuilder()
                       .setBaseUri(fundingSourceConfig+"/fundingSources")
                       .setContentType(JSON)
                       .log(ALL)
                       .build());
    }
}
