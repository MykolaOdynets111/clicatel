package api.clients;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;
import static util.readers.PropertiesReader.getProperty;

public class VelocityClients extends BasedAPIClient {

    public static String sourceMsisdn;
    public static String transactionId;
    public static String success;

    static {
        sourceMsisdn = getProperty("sourceMsisdn");
        transactionId = getProperty("transactionId");
        success = getProperty("success");

    }
    public static Response executeRaasAndVerifyRecord(Map body) {
        return basedAPIClient.get()
                .post(new RequestSpecBuilder()
                        .setUrlEncodingEnabled(false)
                        .setBaseUri(String.format("%s/raas/verifyAndRecord",PaydVelocity))
                        .setBody(body)
                        .setContentType(JSON)
                        .addHeader("Authorization","bearer")
                        .log(ALL)
                        .build());
    }
}
