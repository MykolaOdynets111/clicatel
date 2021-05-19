package api.clients;

import api.enums.Port;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import lombok.Getter;

import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;

@Getter
public class FinancialTermsLookupClient extends BasedAPIClient {

    public static Response getFinancialTermDetails(Port port, int clientId, int productId, int purchaseAmount) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s:%d/financialTerms",financialTerms,port.getPort()))
                        .addQueryParam("clientId",clientId)
                        .addQueryParam("productId",productId)
                        .addQueryParam("purchaseAmount",purchaseAmount)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

}
