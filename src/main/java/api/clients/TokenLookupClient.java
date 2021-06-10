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
public class TokenLookupClient extends BasedAPIClient {
    public static String AccessBankID;
    public static String Identifier_1;
    public static String UssdID;
    public static String IflixSubscriptionID;
    public static String Limit_1;
    public static String Identifier_2;

    static {
        AccessBankID = getProperty("AccessBankID");
        Identifier_1 = getProperty("Identifier_1");
        UssdID = getProperty("UssdID");
        IflixSubscriptionID = getProperty("IflixSubscriptionID");
        Limit_1 = getProperty("Limit_1");
        Identifier_2 = getProperty("Identifier_2");
    }

    public static Response getUserTokens(Port port, Map <String,String> queryParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s:%d/user/tokens",userTransactionUrl,port.getPort()))
                        .addQueryParams(queryParams)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

}
