package api.clients;

import api.domains.inflight_transaction.model.InFlightTransactionRequest;
import api.enums.Port;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import lombok.Getter;

import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;
import static util.readers.PropertiesReader.getProperty;

@Getter
public class InFlightTransactionLookupClient extends BasedAPIClient {
    public static String ResponseCode_200;
    public static String ResponseCode_500;
    public static String AirTel_lookup;
    public static String AirTel_purchase;

    static {
        ResponseCode_200 = getProperty("ResponseCode_200");
        ResponseCode_500 = getProperty("ResponseCode_500");
        AirTel_lookup = getProperty("AirTel_lookup");
        AirTel_purchase = getProperty("AirTel_purchase");
    }

    public static Response lookupPendingTransactions(InFlightTransactionRequest body, Port port) {
        return basedAPIClient.get()
                .post(new RequestSpecBuilder()
                        .setUrlEncodingEnabled(false)
                        .setBaseUri(String.format("%s:%d/lookupservice/pendingTransactions",lookupTransactionUrl,port.getPort()))
                        .setBody(body)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }
}
