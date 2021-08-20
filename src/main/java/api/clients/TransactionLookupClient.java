package api.clients;
import api.enums.Port;
import api.enums.Version;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import lombok.Getter;
import java.util.Map;
import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;
import static util.readers.PropertiesReader.getProperty;

@Getter
public class TransactionLookupClient extends BasedAPIClient {

    public static String TestClient143;
    public static String ClientTxnID;
    public static String DateRangeFrom;
    public static String DateRangeTo;
    public static String Limit_1;

    static {
        TestClient143 = getProperty("TestClient143");
        ClientTxnID = getProperty("ClientTxnID");
        DateRangeFrom = getProperty("DateRangeFrom");
        DateRangeTo = getProperty("DateRangeTo");
        Limit_1 = getProperty("Limit_1");
    }

    public static Response findTransaction(Port port, Map<String, String> queryParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s:%d/lookupservice/transaction", transactionUrl, port.getPort()))
                        .addQueryParams(queryParams)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

    public static Response findTransaction(Port port, int clientId, Map<String, String> queryParams, Version version) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s:%d/lookupservice/transaction/%s", transactionUrl, port.getPort(), version.getVersion()))
                        .addQueryParam("clientId", clientId)
                        .addQueryParams(queryParams)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

    public static Response getProductInfo(Port port, Version version, Map<String, String> queryParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s:%d/public/%s/productInfo", productLookupUrl, port.getPort(), version.getVersion()))
                        .addQueryParams(queryParams)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }


    public static Response getPortalTransactionLookup(Map<String, String> queryParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/lookupservice/transaction-lookup-core", PortalTransactionLookup))
                        .addQueryParams(queryParams)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }


    public static Response getPortalTransactionLookupValidDateRanges() {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/lookupservice/validDateRanges", PortalTransactionLookup))
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

}

