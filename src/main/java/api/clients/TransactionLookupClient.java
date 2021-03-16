package api.clients;
import api.domains.transact.model.TransactRequest;
import api.enums.Port;
import api.enums.Version;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import lombok.Getter;
import java.util.Map;
import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;

@Getter
public class TransactionLookupClient extends BasedAPIClient {
    public static Response getTransactionInfo(Port port, Map<String, String> queryParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s:%d/lookupservice/transaction", baseUrl, port.getPort()))
                        .addQueryParams(queryParams)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

    public static Response getTransactionInfoV2(Port port, Map<String, String> queryParams, Version version) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s:%d/lookupservice/transaction", baseUrl, port.getPort(), version.getVersion()))
                        .addQueryParams(queryParams)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }
}

