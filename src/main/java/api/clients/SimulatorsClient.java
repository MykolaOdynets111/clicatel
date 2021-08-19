package api.clients;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;

import java.util.Map;

import static api.clients.BasedAPIClient.*;
import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;
import static util.readers.PropertiesReader.getProperty;

public class SimulatorsClient {

    public static String ValidAirtelMsisdnWithProducts;
    public static String responseMessageGetOfferAirTel;
    public static String ValidMsisdnType;
    public static String ValidAirtelMsisdnWithoutProducts;
    public static String InValidAirtelMsisdnWithoutProducts;
    public static String ResponseCode_9007;
    public static String responseMessageGetOfferAirTelInvalidMsisdn;


    static {
        ValidAirtelMsisdnWithProducts = getProperty("ValidAirtelMsisdnWithProducts");
        responseMessageGetOfferAirTel = getProperty("responseMessageGetOfferAirTel");
        ValidMsisdnType = getProperty("ValidMsisdnType");
        ValidAirtelMsisdnWithoutProducts = getProperty("ValidAirtelMsisdnWithoutProducts");
        InValidAirtelMsisdnWithoutProducts = getProperty("InValidAirtelMsisdnWithoutProducts");
        ResponseCode_9007 = getProperty("ResponseCode_9007");
        responseMessageGetOfferAirTelInvalidMsisdn = getProperty("responseMessageGetOfferAirTelInvalidMsisdn");
    }

    public static Response Ping3Line() {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/threeline/ping",LineSimulator))
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
}

    public static Response PingRaasV3Simulator() {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/raasBankSimulatorsV3/health",RaasBankSimulator))
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

    public static Response PingAirTelProxySimulator() {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/airtel/ping",AirTelSimulator))
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

    public static Response GetDataOffer(Map<String,String> queryParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/airtel/dataoffer",AirTelSimulator))
                        .addQueryParams(queryParams)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

    public static Response PingMWMSimulator() {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/mwmsim/testresponse",MwmSimulator))
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }


}
