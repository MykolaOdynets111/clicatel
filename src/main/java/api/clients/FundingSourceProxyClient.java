package api.clients;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;

import java.util.Map;
import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;
import static util.readers.PropertiesReader.getProperty;

public class FundingSourceProxyClient extends BasedAPIClient{

    public static String ReserveFundsTxnRef2;
    public static String fundingSourcProxyTimeStamp;

    static {
        ReserveFundsTxnRef2= getProperty("ReserveFundsTxnRef2");
        fundingSourcProxyTimeStamp=getProperty("fundingSourcProxyTimeStamp");

    }

    public static Response PostFundingSourceConfigV1 (Map body,String paymentProviderId) {
        return basedAPIClient.get()
                .post(new RequestSpecBuilder()
                        .setBody(body)
                        .setBaseUri(paydFundingSourceProxy + "/funding-source/v1/api/transactResult")
                        .addQueryParam("paymentProviderId", paymentProviderId)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }
}
