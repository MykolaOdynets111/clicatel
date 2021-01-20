package api.clients;

import api.domains.transact.model.TransactRequest;
import api.enums.Port;
import api.enums.Version;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import lombok.Getter;

import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;

@Getter
public class TransactClient extends BasedAPIClient {

    public static Response executeTransact(TransactRequest body, Port port, Version version) {
        return basedAPIClient.get()
                .post(new RequestSpecBuilder()
                        .setUrlEncodingEnabled(false)
                        .setBaseUri(String.format("%s:%d/raas/%s/transact",baseUrl,port.getPort(),version.getVersion()))
                        .setBody(body)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }
}

