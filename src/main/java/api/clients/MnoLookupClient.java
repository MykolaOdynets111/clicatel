package api.clients;

import api.enums.Port;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import lombok.Getter;

import java.util.Map;

import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;
import static util.readers.PropertiesReader.getProperty;

@Getter
public class MnoLookupClient extends BasedAPIClient {
    public static String Nigeria_CC;

    static {
        Nigeria_CC = getProperty("Nigeria_CC");
    }

    public static Response getMnoInfo(Port port, Map <String,String> queryParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s:%d/mnp/mnpLookup",mnoLookupUrl,port.getPort()))
                        .addQueryParams(queryParams)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

}
