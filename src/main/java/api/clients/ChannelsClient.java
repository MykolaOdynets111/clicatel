package api.clients;

import api.enums.Port;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;

import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;

public class ChannelsClient extends BasedAPIClient {

    public static Response getChannels() {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/channels",ChannelLookup))
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

}
