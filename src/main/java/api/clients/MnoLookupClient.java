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
    public static String OperatorCode_MTN_NG;
    public static String OperatorName_Nigeria;
    public static String Nigeria;
    public static String InValidMnoMsisdn;
    public static String ResponseCode_9020;
    public static String responseMessageInvalidMsisdnMnoLookup;


    static {
        Nigeria_CC = getProperty("Nigeria_CC");
        OperatorCode_MTN_NG= getProperty("OperatorCode_MTN_NG");
        OperatorName_Nigeria= getProperty("OperatorName_Nigeria");
        Nigeria= getProperty("Nigeria");
        InValidMnoMsisdn= getProperty("InValidMnoMsisdn");
        ResponseCode_9020= getProperty("ResponseCode_9020");
        responseMessageInvalidMsisdnMnoLookup= getProperty("responseMessageInvalidMsisdnMnoLookup");
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
    public static Response getMnoCountryInfo(Port port, Map <String,String> queryParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s:%d/mnp/lookupCountryPrefix",mnoLookupUrl,port.getPort()))
                        .addQueryParams(queryParams)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

}
