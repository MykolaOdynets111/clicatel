package api.clients;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;
import static util.readers.PropertiesReader.getProperty;

public class TokenServicesClient extends BasedAPIClient
{
    public static String raasTxnRefForRaasTokenService;
    public static String RaasTokenServiceID;
    public static String RaasTokenServiceToken;

    static
    {
        raasTxnRefForRaasTokenService = getProperty("raasTxnRefForRaasTokenService");
        RaasTokenServiceID = getProperty("RaasTokenServiceID");
        RaasTokenServiceToken = getProperty("RaasTokenServiceToken");
    }

    public static Response GetRaasTokenByRaasTxnRef(Map<String,String> queryParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/tokenservice/lookup",RaasTokenService))
                        .addQueryParams(queryParams)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }
}
