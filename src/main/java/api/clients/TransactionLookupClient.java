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
    public static String TestClient113;
    public static String TestClient101;
    public static String DateRangeFrom_transaction_lookup_ctx;
    public static String DateRangeTo_transaction_lookup_ctx;

    static {
        TestClient143 = getProperty("TestClient143");
        ClientTxnID = getProperty("ClientTxnID");
        DateRangeFrom = getProperty("DateRangeFrom");
        DateRangeTo = getProperty("DateRangeTo");
        Limit_1 = getProperty("Limit_1");
        TestClient113 = getProperty("TestClient113");
        TestClient101 = getProperty("TestClient101");
        DateRangeFrom_transaction_lookup_ctx = getProperty("DateRangeFrom_transaction_lookup_ctx");
        DateRangeTo_transaction_lookup_ctx = getProperty("DateRangeTo_transaction_lookup_ctx");

    }

    public static Response findTransaction(Port port, Map<String, String> queryParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/lookupservice/transaction", transactionUrl))
                        .addQueryParams(queryParams)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

    public static Response findTransaction(Port port, int clientId, Map<String, String> queryParams, Version version) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/lookupservice/transaction/%s", transactionUrl, version.getVersion()))
                        .addQueryParam("clientId", clientId)
                        .addQueryParams(queryParams)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }
    public static Response findInternalTransactionV2(Port port, int clientId, Map<String, String> queryParams, Version version) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/lookupservice/internalTransactionLookup/%s", transactionUrl, version.getVersion()))
//                        .addQueryParam("clientId", clientId)
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
    public static Response getPortalTransactionLookupListAwsFiles(Map<String, String> queryParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/lookupservice/list-aws-files", PortalTransactionLookup))
                        .addQueryParams(queryParams)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }
    public static Response getPortalTransactionLookupDownloadAwsFiles(Map<String, String> queryParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/lookupservice/download-aws-file-stream", PortalTransactionLookup))
                        .addQueryParams(queryParams)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }
    public static Response getPortalTransactionLookupChannels() {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/lookupservice/channels", PortalTransactionLookup))
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }
    public static Response getPortalTransactionLookupCtx(Map map) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/lookupservice/transaction-lookup-ctx", PortalTransactionLookup))
                        .setContentType(JSON)
                        .addQueryParams(map)
                        .log(ALL)
                        .build());
    }

}

