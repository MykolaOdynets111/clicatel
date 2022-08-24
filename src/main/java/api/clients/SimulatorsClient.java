package api.clients;

import api.domains.customer_account_validation.model.CustomerAccountValidationRequest;
import api.enums.Port;
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
    public static String Identifier_18;
    public static String mwm_description;
    public static String Vendor_NG_21;
    public static String mwmStatus;
    public static String mwmStatusCode;
    public static String mwmMessage;
    public static String mwmSuccess;
    public static String Successful;
    public static String Identifier_19;


    static {
        ValidAirtelMsisdnWithProducts = getProperty("ValidAirtelMsisdnWithProducts");
        responseMessageGetOfferAirTel = getProperty("responseMessageGetOfferAirTel");
        ValidMsisdnType = getProperty("ValidMsisdnType");
        ValidAirtelMsisdnWithoutProducts = getProperty("ValidAirtelMsisdnWithoutProducts");
        InValidAirtelMsisdnWithoutProducts = getProperty("InValidAirtelMsisdnWithoutProducts");
        ResponseCode_9007 = getProperty("ResponseCode_9007");
        responseMessageGetOfferAirTelInvalidMsisdn = getProperty("responseMessageGetOfferAirTelInvalidMsisdn");
        Identifier_18 = getProperty("Identifier_18");
        mwm_description = getProperty("mwm_description");
        Vendor_NG_21 = getProperty("Vendor_NG_21");
        mwmStatus = getProperty("mwmStatus");
        mwmStatusCode = getProperty("mwmStatusCode");
        mwmMessage = getProperty("mwmMessage");
        mwmSuccess = getProperty("mwmSuccess");
        Successful = getProperty("Successful");
        Identifier_19=getProperty("Identifier_19");
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

    public static Response PingMWMSimulator(String Identifier) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/mwmsim/testresponse/"+Identifier,MwmSimulator))
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }
    public static Response Pingmagtipon3linengSimulator() {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setUrlEncodingEnabled(false)
                        .setBaseUri(String.format("%s/magtipon3lineng/ping", cAccountValidation))
                        .setContentType(JSON)
                        .log(ALL)
                        .build());

    }
    public static Response PostMWMSimulator(Map map, String Identifier) {
        return basedAPIClient.get()
                .post(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/mwmsim/testresponse/"+Identifier,MwmSimulator))
                        .setBody(map)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }
    public static Response PostMWMSimulatorValidatorRequest(Map map) {
        return basedAPIClient.get()
                .post(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/mwmsim/validaterequest/",MwmSimulator))
                        .setBody(map)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }
    public static Response PostMWMSimulatorDelete(String Identifier) {
        return basedAPIClient.get()
                .delete(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/mwmsim/testresponse/"+Identifier,MwmSimulator))
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }
    public static Response PostPWMSimulator(Map map) {
        return basedAPIClient.get()
                .post(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/pwmSimulator/statusLookup",PwmSimulator))
                        .setBody(map)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }
    public static Response PostControlApiBehaviour(Map map, String VendorId, String Port) {
        return basedAPIClient.get()
                .post(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s:%s/api/VENDOR/"+VendorId+"/apiBehaviour/"+VendorApiBehavior+"/set/vend",ControlApiAdaptor, Port))
                        .setBody(map)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

    public static Response PostControlApiBehaviourLookup(Map map, String VendorId, String Port) {
        return basedAPIClient.get()
                .post(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s:%s/api/VENDOR/"+VendorId+"/apiBehaviour/"+VendorApiBehavior+"/set/lookup",ControlApiAdaptor, Port))
                        .setBody(map)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

}
