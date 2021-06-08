package api.clients;

import api.domains.reserve_and_transact.model.ReserveAndTransactRequest;
import api.enums.Port;
import api.enums.Version;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import lombok.Getter;


import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;
import static util.readers.PropertiesReader.getProperty;

@Getter
public class ReserveAndTransactClient extends BasedAPIClient {
    public static String AccountIdentifier;
    public static String clientTxnRef;
    public static String channelSessionId;
    public static String fundingSourceId;

    static {
        AccountIdentifier = getProperty("AccountIdentifier");
        clientTxnRef = getProperty("clientTxnRef");
        channelSessionId = getProperty("channelSessionId");
        fundingSourceId = getProperty("fundingSourceId");
    }


    public static Response executeReserveAndTransact(ReserveAndTransactRequest body, Port port, Version version) {
        return basedAPIClient.get()
                .post(new RequestSpecBuilder()
                        .setUrlEncodingEnabled(false)
                        .setBaseUri(String.format("%s:%d/raas/%s/reserveAndTransact",baseUrl,port.getPort(),version.getVersion()))
                        .setBody(body)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

    public static Response executeReserveAndTransactWithSignature(String body, Port port, Version version, String signature) {
        return basedAPIClient.get()
                .post(new RequestSpecBuilder()
                        .setContentType(JSON)
                        .setBaseUri(String.format("%s:%d/raas/%s/reserveAndTransact",baseUrl,port.getPort(),version.getVersion()))
                        .addHeader("Signature", signature)
                        .setBody(body)
                        .log(ALL)
                        .build());
    }

}

