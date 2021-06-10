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

