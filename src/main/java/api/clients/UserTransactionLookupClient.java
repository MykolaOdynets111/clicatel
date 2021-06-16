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
public class UserTransactionLookupClient extends BasedAPIClient {
    public static String Product_Iflix_571;
    public static String UserTransactionProductDesc;
    public static String PurchaseAmount79900;

    static {
        Product_Iflix_571 = getProperty("Product_Iflix_571");
        UserTransactionProductDesc = getProperty("UserTransactionProductDesc");
        PurchaseAmount79900 = getProperty("PurchaseAmount79900");
    }

    public static Response getUserTransactions(Port port, Map <String,String> queryParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s:%d/userTransactions",userTransactionUrl,port.getPort()))
                        .addQueryParams(queryParams)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

}
