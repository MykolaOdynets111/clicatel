package api.clients;

import api.enums.Port;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import lombok.Getter;

import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;

@Getter
public class SupportUiClient extends BasedAPIClient {

    public static Response getRaasFlow(Port port, String raasTxnRef) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s:%d/supportui/api/getRaasFlow",supportUrl,port.getPort()))
                        .addQueryParam("raasTxnRef", raasTxnRef)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }
}