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
    public static String countryCallingCode_27;
    public static String ResponseCode_9010;
    public static String responseMessageIncorrectMsisdnCountryCombination;
    public static String countryCallingCode_999;
    public static String responseMessageUnknownCountryCode;
    public static String ResponseCode_9090;
    public static String ValidMsisdn;
    public static String CountryCode_ZA;
    public static String CountryCodeNGN;
    public static String OperatorCode_SOUMFB;
    public static String OperatorName_MTN;
    public static String error_BadRequest;
    public static String ValidMsisdnVodacom;
    public static String OperatorCode_SOUVXP;
    public static String OperatorName_Vodacom;
    public static String ValidMsisdnAirTel;
    public static String countryCallingCode_234;
    public static String OperatorCode_AIRTEL;
    public static String OperatorName_BhartiAirtelTelecommunicationsCompany;
    public static String ValidMsisdnMTNNG;
    public static String OperatorCode_MTNNG;
    public static String OperatorName_MobileTelecommunicationsNetworkNigeria;
    public static String ValidMsisdn9Mobile;
    public static String OperatorCode_9MOBILE;
    public static String OperatorName_9MobileTelecoms;
    public static String ValidMsisdnGLO;
    public static String OperatorCode_GLO;
    public static String OperatorName_GlobaComTelecommunicationsCompany;

    static {
        Nigeria_CC = getProperty("Nigeria_CC");
        OperatorCode_MTN_NG= getProperty("OperatorCode_MTN_NG");
        OperatorName_Nigeria= getProperty("OperatorName_Nigeria");
        Nigeria= getProperty("Nigeria");
        InValidMnoMsisdn= getProperty("InValidMnoMsisdn");
        ResponseCode_9020= getProperty("ResponseCode_9020");
        responseMessageInvalidMsisdnMnoLookup= getProperty("responseMessageInvalidMsisdnMnoLookup");
        countryCallingCode_27= getProperty("countryCallingCode_27");
        ResponseCode_9010= getProperty("ResponseCode_9010");
        responseMessageIncorrectMsisdnCountryCombination= getProperty("responseMessageIncorrectMsisdnCountryCombination");
        countryCallingCode_999= getProperty("countryCallingCode_999");
        responseMessageUnknownCountryCode= getProperty("responseMessageUnknownCountryCode");
        ResponseCode_9090= getProperty("ResponseCode_9090");
        ValidMsisdn= getProperty("ValidMsisdn");
        CountryCode_ZA= getProperty("CountryCode_ZA");
        CountryCodeNGN=getProperty("CountryCodeNGN");
        OperatorCode_SOUMFB= getProperty("OperatorCode_SOUMFB");
        OperatorName_MTN= getProperty("OperatorName_MTN");
        error_BadRequest= getProperty("error_BadRequest");
        ValidMsisdnVodacom= getProperty("ValidMsisdnVodacom");
        OperatorName_Vodacom= getProperty("OperatorName_Vodacom");
        OperatorCode_SOUVXP= getProperty("OperatorCode_SOUVXP");
        ValidMsisdnAirTel= getProperty("ValidMsisdnAirTel");
        countryCallingCode_234= getProperty("countryCallingCode_234");
        OperatorCode_AIRTEL= getProperty("OperatorCode_AIRTEL");
        OperatorName_BhartiAirtelTelecommunicationsCompany= getProperty("OperatorName_BhartiAirtelTelecommunicationsCompany");
        ValidMsisdnMTNNG= getProperty("ValidMsisdnMTNNG");
        OperatorCode_MTNNG= getProperty("OperatorCode_MTNNG");
        OperatorName_MobileTelecommunicationsNetworkNigeria= getProperty("OperatorName_MobileTelecommunicationsNetworkNigeria");
        ValidMsisdn9Mobile= getProperty("ValidMsisdn9Mobile");
        OperatorCode_9MOBILE= getProperty("OperatorCode_9MOBILE");
        OperatorName_9MobileTelecoms= getProperty("OperatorName_9MobileTelecoms");
        ValidMsisdnGLO= getProperty("ValidMsisdnGLO");
        OperatorCode_GLO= getProperty("OperatorCode_GLO");
        OperatorName_GlobaComTelecommunicationsCompany= getProperty("OperatorName_GlobaComTelecommunicationsCompany");
    }

    public static Response getMnoInfo(Port port, Map <String,String> queryParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s:%s/mnp/mnpLookup",mnoLookupUrl,EnvPort))
                        .addQueryParams(queryParams)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }
    public static Response getMnoCountryInfo(Port port, Map <String,String> queryParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s:%s/mnp/lookupCountryPrefix",mnoLookupUrl,EnvPort))
                        .addQueryParams(queryParams)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

}
