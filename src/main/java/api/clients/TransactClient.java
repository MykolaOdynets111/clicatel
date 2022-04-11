package api.clients;

import api.domains.transact.model.TransactRequest;
import api.enums.Port;
import api.enums.Version;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import lombok.Getter;
import static util.readers.PropertiesReader.getProperty;
import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;

@Getter
public class TransactClient extends BasedAPIClient {
    public static String responseMessageAlphaNumericCSID;
    public static String responseMessageTimeStampNotNull;
    public static String responseMessageRFTRMandatory;
    public static String responseMessageServiceTUnavailable;
    public static String responseMessageFundingSrouce;
    public static String ResponseCode_0001;
    public static String responseMessageProductID;
    public static String responseMessagePurchaseAmount;
    public static String responseMessageFeeAmount;
    public static String responseMessageCurrencyCode;
    public static String responseMessageChannelID;
    public static String responseMessageChannelName;
    public static String responseMessageSourceIdentifier;
    public static String responseMessageTargetIdentifier;
    public static String responseMessageTargetIdentifierV1;
    public static String responseMessageChannelIDV1;
    public static String responseMessageNullCSID;
    public static String responseMessageSourceIdentifierNotNull;
    public static String responseMessageTargetIdentifierNotNull;
    public static String responseMessageChannelNameNotNull;
    public static String responseMessageCurrencyCodeMaxLimitCurrency_Transact;

    static {
        responseMessageAlphaNumericCSID= getProperty("responseMessageAlphaNumericCSID");
        responseMessageTimeStampNotNull= getProperty("responseMessageTimeStampNotNull");
        responseMessageRFTRMandatory= getProperty("responseMessageRFTRMandatory");
        responseMessageServiceTUnavailable= getProperty("responseMessageServiceTUnavailable");
        responseMessageFundingSrouce= getProperty("responseMessageFundingSrouce");
        ResponseCode_0001= getProperty("ResponseCode_0001");
        responseMessageProductID= getProperty("responseMessageProductID");
        responseMessagePurchaseAmount= getProperty("responseMessagePurchaseAmount");
        responseMessageFeeAmount= getProperty("responseMessageFeeAmount");
        responseMessageCurrencyCode= getProperty("responseMessageCurrencyCode");
        responseMessageChannelID= getProperty("responseMessageChannelID");
        responseMessageChannelName= getProperty("responseMessageChannelName");
        responseMessageSourceIdentifier= getProperty("responseMessageSourceIdentifier");
        responseMessageTargetIdentifier= getProperty("responseMessageTargetIdentifier");
        responseMessageTargetIdentifierV1= getProperty("responseMessageTargetIdentifierV1");
        responseMessageChannelIDV1= getProperty("responseMessageChannelIDV1");
        responseMessageNullCSID= getProperty("responseMessageNullCSID");
        responseMessageSourceIdentifierNotNull= getProperty("responseMessageSourceIdentifierNotNull");
        responseMessageTargetIdentifierNotNull= getProperty("responseMessageTargetIdentifierNotNull");
        responseMessageChannelNameNotNull= getProperty("responseMessageChannelNameNotNull");
        responseMessageCurrencyCodeMaxLimitCurrency_Transact= getProperty("responseMessageCurrencyCodeMaxLimitCurrency_Transact");
    }
    public static Response executeTransact(TransactRequest body, Port port, Version version) {
        return basedAPIClient.get()
                .post(new RequestSpecBuilder()
                        .setUrlEncodingEnabled(false)
                        .setBaseUri(String.format("%s/raas/%s/transact",baseUrl,version.getVersion()))
                        .setBody(body)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }
}

