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
public class FinancialTermsLookupClient extends BasedAPIClient {

    public static String Vendor_Product_Insert_Timestamp;
    public static String Client_Product_Insert_Timestamp;
    public static String vendorDiscountPercentageId;
    public static String value_0point1;
    public static String valid_from_TimeStamp;
    public static String valid_to_TimeStamp;
    public static String created_TimeStamp;
    public static String validFrom_TimeStamp;
    public static String dateAndTime;
    public static String financialTermsValue10;
    public static String Vendor_1206;
    public static String validTo_TimeStamp;
    public static String validFrom_Response;

    static {
        Client_Product_Insert_Timestamp = getProperty("Client_Product_Insert_Timestamp");
        Vendor_Product_Insert_Timestamp = getProperty("Vendor_Product_Insert_Timestamp");
        vendorDiscountPercentageId = getProperty("vendorDiscountPercentageId");
        value_0point1 = getProperty("value_0point1");
        valid_from_TimeStamp = getProperty("valid_from_TimeStamp");
        valid_to_TimeStamp = getProperty("valid_to_TimeStamp");
        created_TimeStamp = getProperty("created_TimeStamp");
        validFrom_TimeStamp = getProperty("validFrom_TimeStamp");
        validFrom_Response=getProperty("validFrom_Response");
        dateAndTime = getProperty("dateAndTime");
        financialTermsValue10=getProperty("financialTermsValue10");
        Vendor_1206=getProperty("Vendor_1206");
        validTo_TimeStamp=getProperty("validTo_TimeStamp");
    }

    public static Response getFinancialTermDetails(int clientId, int productId, int purchaseAmount) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/financialTerms",financialTerms))
                        .addQueryParam("clientId",clientId)
                        .addQueryParam("productId",productId)
                        .addQueryParam("purchaseAmount",purchaseAmount)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

    public static Response PostConfigureFinTermsForClient (Map body) {
        return basedAPIClient.get()
                .post(new RequestSpecBuilder()
                        .setUrlEncodingEnabled(false)
                        .setBody(body)
                        .setBaseUri(financialTerms+"/financial-terms/financialModels/configureFinTermsForClient")
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }
    public static Response getFinancialTermsCalculateAtTime(String productId, String clientId, String purchaseAmount, String dateAndTime) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/financial-terms/calculateAtTime",financialTerms))
                        .addQueryParam("productId",productId)
                        .addQueryParam("clientId",clientId)
                        .addQueryParam("purchaseAmount",purchaseAmount)
                        .addQueryParam("dateAndTime",dateAndTime)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }
    public static Response getFinancialTermsCalculate(String productId, String clientId, String purchaseAmount) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/financial-terms/calculate",financialTerms))
                        .addQueryParam("productId",productId)
                        .addQueryParam("clientId",clientId)
                        .addQueryParam("purchaseAmount",purchaseAmount)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

    public static Response getTerms(String clientId,String productId){
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/terms",financialTerms))
                        .addQueryParam("clientId",clientId)
                        .addQueryParam("productId",productId)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());

    }

    public static Response postFinancialTermsVendorDiscount(Map body){
        return basedAPIClient.get()
                .post(new RequestSpecBuilder()
                        .setUrlEncodingEnabled(false)
                        .setBody(body)
                        .setBaseUri(String.format("%s/financial-terms/vendorDiscount",financialTerms))
                        .setContentType(JSON)
                        .log(ALL)
                        .build());

    }
}
