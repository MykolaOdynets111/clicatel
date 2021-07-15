package api.clients;

import api.domains.reserve_and_transact.model.ReserveAndTransactRequest;
import api.enums.Port;
import api.enums.Version;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import lombok.Getter;


import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;
import static util.readers.PropertiesReader.getProperty;

@Getter
public class ReserveAndTransactClient extends BasedAPIClient {
    public static String AccountIdentifier;
    public static String clientTxnRef;
    public static String channelSessionId;
    public static String fundingSourceId;
    public static String TestClient3;
    public static String PurchaseAmount1000;
    public static String FeeAmount0;
    public static String Identifier;
    public static String responseMessageFundsReserved;
    public static String responseCode0000;
    public static String Success;
    public static String responseCode0;
    public static String responseCode202;
    public static String FirstTransactionCode;
    public static String ProductAirtel_100;
    public static String ProductAirtel_917;
    public static String AccountIdentifierV3;
    public static String clientTxnRefV3;
    public static String channelSessionIdV3;
    public static String PurchaseAmount10000;
    public static String IdentifierV3;
    public static String ZeroTransactionCode;
    public static String responseMessageProcessingRequest;
    public static String AccountIdentifierV2;
    public static String clientTxnRefV2;
    public static String channelSessionIdV2;
    public static String IdentifierV2;
    public static String clientTxnRefV1;
    public static String channelSessionIdV1;
    public static String PurchaseAmount20000;
    public static String IdentifierV1;
    public static String Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2;
    public static String ProductCellC_60;
    public static String Identifier_3;
    public static String ProductMTN_ZA_400;
    public static String Identifier_4;
    public static String ProductVodacom_ZA_40;
    public static String Identifier_5;
    public static String ProductTelkom_5_50;
    public static String Product_3line_15_505;
    public static String PurchaseAmount50000;
    public static String PurchaseAmount200;
    public static String Identifier_9;
    public static String Product_MTN_ZA_Clickatell_30;
    public static String Product_Glo_110;
    public static String Product_Etisalat_120;
    public static String Product_Airtel_189;
    public static String PurchaseAmount9900;
    public static String ResponseCode_17017;
    public static String Product_Airtel_130;
    public static String Failed;
    public static String responseCode2213;
    public static String responseCode2240;
    public static String ResponseCode_2238;
    public static String StartTransactionCode;
    public static String responseCode2201;
    public static String ResponseCode_206;
    public static String Airtel_delay_3000;
    public static String ResponseCode_2510;
    public static String PurchaseAmount1000000001;
    public static String PurchaseAmount99;
    public static String ResponseCode_2509;
    public static String PurchaseAmount10001;
    public static String ResponseCode_2511;
    public static String FeeAmount10;
    public static String ResponseCode_2055;
    public static String responseMessageInvalidAmount;
    public static String PurchaseAmountInvalid;
    public static String ResponseCode_4000;
    public static String responseMessageInvalidJsonBody;
    public static String Product_919;
    public static String ResponseCode_2506;
    public static String responseMessageReserveFundsTransaction;
    public static String responseMessageFundingResourceNotLinked;
    public static String Product_1201;
    public static String Identifier_11;
    public static String Identifier_12;
    public static String Product_1585;
    public static String responseMessagePIDNotFound;
    public static String Product_11;
    public static String responseMessagePID11NotFound;
    public static String AccountIdentifier1;
    public static String fundingSourceID_50;
    public static String AccountIdentifierV2MaxLimit;
    public static String clientTxnRefV2MaxLimit;
    public static String responseMessageClientTxnRef;
    public static String responseMessageChannelSessionID;
    public static String channelSessionIdV2MaxLimit;
    public static String authCodeV2MaxLimit;
    public static String responseMessageAuthCode;
    public static String timeStampMaxLimit;
    public static String ClientIdInvalid;
    public static String Product_Invalid;
    public static String PurchaseAmountMaxLimit;
    public static String responseMessagePurchaseAmountMaxLimit;
    public static String responseMessageChannelIDMaxLimit;
    public static String responseMessageSourceIdentifierMaxLimit;
    public static String responseMessageTargetIdentifierMaxLimit;
    public static String SourceIdentifierMaxLimit;
    public static String TargetIdentifierMaxLimit;
    public static String fundingSourceId_4;
    public static String responseMessageCurrencyCodeMaxLimitCurrency;

    static {
        AccountIdentifier = getProperty("AccountIdentifier");
        clientTxnRef = getProperty("clientTxnRef");
        channelSessionId = getProperty("channelSessionId");
        fundingSourceId = getProperty("fundingSourceId");
        TestClient3= getProperty("TestClient3");
        PurchaseAmount1000= getProperty("PurchaseAmount1000");
        FeeAmount0= getProperty("FeeAmount0");
        Identifier= getProperty("Identifier");
        responseMessageFundsReserved= getProperty("responseMessageFundsReserved");
        responseCode0000= getProperty("responseCode0000");
        Success= getProperty("Success");
        responseCode0= getProperty("responseCode0");
        responseCode202= getProperty("responseCode202");
        FirstTransactionCode= getProperty("FirstTransactionCode");
        ProductAirtel_100= getProperty("ProductAirtel_100");
        ProductAirtel_917= getProperty("ProductAirtel_917");
        AccountIdentifierV3= getProperty("AccountIdentifierV3");
        clientTxnRefV3= getProperty("clientTxnRefV3");
        channelSessionIdV3= getProperty("channelSessionIdV3");
        PurchaseAmount10000= getProperty("PurchaseAmount10000");
        IdentifierV3= getProperty("IdentifierV3");
        ZeroTransactionCode= getProperty("ZeroTransactionCode");
        responseMessageProcessingRequest= getProperty("responseMessageProcessingRequest");
        AccountIdentifierV2= getProperty("AccountIdentifierV2");
        clientTxnRefV2= getProperty("clientTxnRefV2");
        channelSessionIdV2= getProperty("channelSessionIdV2");
        IdentifierV2= getProperty("IdentifierV2");
        clientTxnRefV1= getProperty("clientTxnRefV1");
        channelSessionIdV1= getProperty("channelSessionIdV1");
        PurchaseAmount20000= getProperty("PurchaseAmount20000");
        IdentifierV1= getProperty("IdentifierV1");
        Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2= getProperty("Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2");
        ProductCellC_60=getProperty("ProductCellC_60");
        Identifier_3= getProperty("Identifier_3");
        ProductMTN_ZA_400= getProperty("ProductMTN_ZA_400");
        Identifier_4 = getProperty("Identifier_4");
        ProductVodacom_ZA_40= getProperty("ProductVodacom_ZA_40");
        Identifier_5= getProperty("Identifier_5");
        ProductTelkom_5_50= getProperty("ProductTelkom_5_50");
        Product_3line_15_505= getProperty("Product_3line_15_505");
        PurchaseAmount50000= getProperty("PurchaseAmount50000");
        PurchaseAmount200= getProperty("PurchaseAmount200");
        Identifier_9= getProperty("Identifier_9");
        Product_MTN_ZA_Clickatell_30= getProperty("Product_MTN_ZA_Clickatell_30");
        Product_Glo_110= getProperty("Product_Glo_110");
        Product_Etisalat_120= getProperty("Product_Etisalat_120");
        Product_Airtel_189= getProperty("Product_Airtel_189");
        PurchaseAmount9900= getProperty("PurchaseAmount9900");
        ResponseCode_17017= getProperty("ResponseCode_17017");
        Product_Airtel_130= getProperty("Product_Airtel_130");
        Failed= getProperty("Failed");
        responseCode2213= getProperty("responseCode2213");
        responseCode2240= getProperty("responseCode2240");
        ResponseCode_2238= getProperty("ResponseCode_2238");
        StartTransactionCode= getProperty("StartTransactionCode");
        responseCode2201= getProperty("responseCode2201");
        ResponseCode_206= getProperty("ResponseCode_206");
        Airtel_delay_3000= getProperty("Airtel_delay_3000");
        ResponseCode_2510= getProperty("ResponseCode_2510");
        PurchaseAmount1000000001= getProperty("PurchaseAmount1000000001");
        PurchaseAmount99= getProperty("PurchaseAmount99");
        ResponseCode_2509= getProperty("ResponseCode_2509");
        PurchaseAmount10001= getProperty("PurchaseAmount10001");
        ResponseCode_2511= getProperty("ResponseCode_2511");
        FeeAmount10= getProperty("FeeAmount10");
        ResponseCode_2055= getProperty("ResponseCode_2055");
        responseMessageInvalidAmount= getProperty("responseMessageInvalidAmount");
        PurchaseAmountInvalid= getProperty("PurchaseAmountInvalid");
        ResponseCode_4000= getProperty("ResponseCode_4000");
        responseMessageInvalidJsonBody= getProperty("responseMessageInvalidJsonBody");
        Product_919= getProperty("Product_919");
        ResponseCode_2506= getProperty("ResponseCode_2506");
        responseMessageReserveFundsTransaction= getProperty("responseMessageReserveFundsTransaction");
        responseMessageFundingResourceNotLinked= getProperty("responseMessageFundingResourceNotLinked");
        Product_1201= getProperty("Product_1201");
        Identifier_11= getProperty("Identifier_11");
        Identifier_12= getProperty("Identifier_12");
        Product_1585= getProperty("Product_1585");
        responseMessagePIDNotFound= getProperty("responseMessagePIDNotFound");
        Product_11= getProperty("Product_11");
        responseMessagePID11NotFound= getProperty("responseMessagePID11NotFound");
        AccountIdentifier1= getProperty("AccountIdentifier1");
        fundingSourceID_50= getProperty("fundingSourceID_50");
        AccountIdentifierV2MaxLimit= getProperty("AccountIdentifierV2MaxLimit");
        clientTxnRefV2MaxLimit= getProperty("clientTxnRefV2MaxLimit");
        responseMessageClientTxnRef= getProperty("responseMessageClientTxnRef");
        channelSessionIdV2MaxLimit= getProperty("channelSessionIdV2MaxLimit");
        responseMessageChannelSessionID= getProperty("responseMessageChannelSessionID");
        authCodeV2MaxLimit= getProperty("authCodeV2MaxLimit");
        responseMessageAuthCode= getProperty("responseMessageAuthCode");
        timeStampMaxLimit= getProperty("timeStampMaxLimit");
        ClientIdInvalid= getProperty("ClientIdInvalid");
        Product_Invalid= getProperty("Product_Invalid");
        PurchaseAmountMaxLimit= getProperty("PurchaseAmountMaxLimit");
        responseMessagePurchaseAmountMaxLimit= getProperty("responseMessagePurchaseAmountMaxLimit");
        responseMessageChannelIDMaxLimit= getProperty("responseMessageChannelIDMaxLimit");
        responseMessageSourceIdentifierMaxLimit= getProperty("responseMessageSourceIdentifierMaxLimit");
        responseMessageTargetIdentifierMaxLimit= getProperty("responseMessageTargetIdentifierMaxLimit");
        SourceIdentifierMaxLimit= getProperty("SourceIdentifierMaxLimit");
        TargetIdentifierMaxLimit= getProperty("TargetIdentifierMaxLimit");
        fundingSourceId_4= getProperty("fundingSourceId_4");
        responseMessageCurrencyCodeMaxLimitCurrency= getProperty("responseMessageCurrencyCodeMaxLimitCurrency");

    }


    public static Response executeReserveAndTransact(ReserveAndTransactRequest body, Port port, Version version) {
        return basedAPIClient.get()
                .post(new RequestSpecBuilder()
                        .setUrlEncodingEnabled(false)
                        .setBaseUri(String.format("%s:%d/raas/%s/reserveAndTransact",baseUrl,port.getPort(),version.getVersion()))
                        .setBody(body)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

    public static Response executeReserveAndTransactWithSignature(String body, Port port, Version version, String signature) {
        return basedAPIClient.get()
                .post(new RequestSpecBuilder()
                        .setContentType(JSON)
                        .setBaseUri(String.format("%s:%d/raas/%s/reserveAndTransact",baseUrl,port.getPort(),version.getVersion()))
                        .addHeader("Signature", signature)
                        .setBody(body)
                        .log(ALL)
                        .build());
    }

}

