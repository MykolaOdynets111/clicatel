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

    static {
        Client_Product_Insert_Timestamp = getProperty("Client_Product_Insert_Timestamp");
        Vendor_Product_Insert_Timestamp = getProperty("Vendor_Product_Insert_Timestamp");
        vendorDiscountPercentageId = getProperty("vendorDiscountPercentageId");
        value_0point1 = getProperty("value_0point1");
        valid_from_TimeStamp = getProperty("valid_from_TimeStamp");
        valid_to_TimeStamp = getProperty("valid_to_TimeStamp");
        created_TimeStamp = getProperty("created_TimeStamp");
        validFrom_TimeStamp = getProperty("validFrom_TimeStamp");
        dateAndTime = getProperty("dateAndTime");
    }

    public static Response getFinancialTermDetails(Port port, int clientId, int productId, int purchaseAmount) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s:%s/financialTerms",financialTerms,EnvPort))
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
                        .setBaseUri(String.format("%s/financialTerms",financialTerms))
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

}
