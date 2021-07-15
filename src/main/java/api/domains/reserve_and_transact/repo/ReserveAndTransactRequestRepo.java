package api.domains.reserve_and_transact.repo;

import api.clients.ReserveAndTransactClient;
import api.domains.reserve_and_transact.model.ReserveAndTransactRequest;
import api.domains.transact.model.TransactRequest;
import api.enums.ChannelId;
import api.enums.ChannelName;
import api.enums.CurrencyCode;
import api.clients.*;

import static util.DateProvider.getCurrentIsoDateTime;


public class ReserveAndTransactRequestRepo {
    //V4
    public static ReserveAndTransactRequest setUpReserveAndTransactV4Data(String clientId, CurrencyCode currencyCode, ChannelName channelName,
                                                                          ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String identifier) {
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.AccountIdentifier)
                .authCode(null)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRef)
                .channelSessionId(ReserveAndTransactClient.channelSessionId)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .fundingSourceId(ReserveAndTransactClient.fundingSourceId)
                .productId(productId) //100
                .purchaseAmount(purchaseAmount) // 10000
                .feeAmount(feeAmount) //0
                .currencyCode(currencyCode.getCurrencyCode()) //NGN
                .channelId(channelId.getChannelId()) //7
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier(identifier)
                .targetIdentifier(identifier)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV4DataSourceIdentifier(String clientId, CurrencyCode currencyCode, ChannelName channelName,
                                                                          ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String sourceidentifier) {
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.AccountIdentifier)
                .authCode(null)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRef)
                .channelSessionId(ReserveAndTransactClient.channelSessionId)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .fundingSourceId(ReserveAndTransactClient.fundingSourceId)
                .productId(productId) //100
                .purchaseAmount(purchaseAmount) // 10000
                .feeAmount(feeAmount) //0
                .currencyCode(currencyCode.getCurrencyCode()) //NGN
                .channelId(channelId.getChannelId()) //7
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier(sourceidentifier)
                .targetIdentifier(ReserveAndTransactClient.Identifier)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV4DataTargetIdentifier(String clientId, CurrencyCode currencyCode, ChannelName channelName,
                                                                                          ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String targetidentifier) {
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.AccountIdentifier)
                .authCode(null)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRef)
                .channelSessionId(ReserveAndTransactClient.channelSessionId)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .fundingSourceId(ReserveAndTransactClient.fundingSourceId)
                .productId(productId) //100
                .purchaseAmount(purchaseAmount) // 10000
                .feeAmount(feeAmount) //0
                .currencyCode(currencyCode.getCurrencyCode()) //NGN
                .channelId(channelId.getChannelId()) //7
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier(ReserveAndTransactClient.Identifier)
                .targetIdentifier(targetidentifier)
                .build();
    }



    public static ReserveAndTransactRequest setUpReserveAndTransactV4DataAccountIdentifier(String clientId, CurrencyCode currencyCode, ChannelName channelName,
                                                                          ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String identifier, String accountIdentifier) {
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(accountIdentifier)
                .authCode(null)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRef)
                .channelSessionId(ReserveAndTransactClient.channelSessionId)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .fundingSourceId(ReserveAndTransactClient.fundingSourceId)
                .productId(productId) //100
                .purchaseAmount(purchaseAmount) // 10000
                .feeAmount(feeAmount) //0
                .currencyCode(currencyCode.getCurrencyCode()) //NGN
                .channelId(channelId.getChannelId()) //7
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier(identifier)
                .targetIdentifier(identifier)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV4DataClientTxnRef(String clientId, CurrencyCode currencyCode, ChannelName channelName,
                                                                                           ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String identifier, String clientTxnRef) {
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.AccountIdentifier)
                .authCode(null)
                .clientTxnRef(clientTxnRef)
                .channelSessionId(ReserveAndTransactClient.channelSessionId)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .fundingSourceId(ReserveAndTransactClient.fundingSourceId)
                .productId(productId) //100
                .purchaseAmount(purchaseAmount) // 10000
                .feeAmount(feeAmount) //0
                .currencyCode(currencyCode.getCurrencyCode()) //NGN
                .channelId(channelId.getChannelId()) //7
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier(identifier)
                .targetIdentifier(identifier)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV4DataChannelSessionId(String clientId, CurrencyCode currencyCode, ChannelName channelName,
                                                                                      ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String identifier, String channelSessionID) {
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.AccountIdentifier)
                .authCode(null)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRef)
                .channelSessionId(channelSessionID)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .fundingSourceId(ReserveAndTransactClient.fundingSourceId)
                .productId(productId) //100
                .purchaseAmount(purchaseAmount) // 10000
                .feeAmount(feeAmount) //0
                .currencyCode(currencyCode.getCurrencyCode()) //NGN
                .channelId(channelId.getChannelId()) //7
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier(identifier)
                .targetIdentifier(identifier)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV4DataAuthCode(String clientId, CurrencyCode currencyCode, ChannelName channelName,
                                                                                          ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String identifier, String authCode) {
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.AccountIdentifier)
                .authCode(authCode)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRef)
                .channelSessionId(ReserveAndTransactClient.channelSessionId)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .fundingSourceId(ReserveAndTransactClient.fundingSourceId)
                .productId(productId) //100
                .purchaseAmount(purchaseAmount) // 10000
                .feeAmount(feeAmount) //0
                .currencyCode(currencyCode.getCurrencyCode()) //NGN
                .channelId(channelId.getChannelId()) //7
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier(identifier)
                .targetIdentifier(identifier)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV4DataTimeStamp(String clientId, CurrencyCode currencyCode, ChannelName channelName,
                                                                                  ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String identifier, String timeStamp) {
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.AccountIdentifier)
                .authCode(null)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRef)
                .channelSessionId(ReserveAndTransactClient.channelSessionId)
                .timestamp(timeStamp)
                .clientId(clientId) //3
                .fundingSourceId(ReserveAndTransactClient.fundingSourceId)
                .productId(productId) //100
                .purchaseAmount(purchaseAmount) // 10000
                .feeAmount(feeAmount) //0
                .currencyCode(currencyCode.getCurrencyCode()) //NGN
                .channelId(channelId.getChannelId()) //7
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier(identifier)
                .targetIdentifier(identifier)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV4Data(String clientId, CurrencyCode currencyCode, ChannelName channelName,
                                                                          ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String identifier, String ReserveFundTxn) {
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.AccountIdentifier)
                .authCode(null)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRef)
                .channelSessionId(ReserveAndTransactClient.channelSessionId)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .fundingSourceId(ReserveAndTransactClient.fundingSourceId)
                .productId(productId) //100
                .purchaseAmount(purchaseAmount) // 10000
                .feeAmount(feeAmount) //0
                .currencyCode(currencyCode.getCurrencyCode()) //NGN
                .channelId(channelId.getChannelId()) //7
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier(identifier)
                .targetIdentifier(identifier)
                .reserveFundsTxnRef(ReserveFundTxn)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV4Data(String clientId, CurrencyCode currencyCode, ChannelName channelName,
                                                                          ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String identifier, String ReserveFundTxn, String fundingSourceId) {
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.AccountIdentifier)
                .authCode(null)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRef)
                .channelSessionId(ReserveAndTransactClient.channelSessionId)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .fundingSourceId(fundingSourceId)
                .productId(productId) //100
                .purchaseAmount(purchaseAmount) // 10000
                .feeAmount(feeAmount) //0
                .currencyCode(currencyCode.getCurrencyCode()) //NGN
                .channelId(channelId.getChannelId()) //7
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier(identifier)
                .targetIdentifier(identifier)
                .reserveFundsTxnRef(ReserveFundTxn)
                .build();
    }

    //V4
    public static ReserveAndTransactRequest setUpReserveAndTransactSignatureData(String clientId, ChannelName channelName,
                                                                                 ChannelId channelId, String identifier) {
        return ReserveAndTransactRequest.builder()
                .timestamp("2023-03-03T00:00:00.000+02:00")
                .accountIdentifier("000XXX0311-0003")
                .channelSessionId("714890809-0003")
                .clientTxnRef("010002441811llim-0003")
                .clientId(clientId)
                .productId("917")
                .purchaseAmount("10000")
                .feeAmount("0")
                .channelId(channelId.getChannelId())
                .channelName(channelName.getChannelName())
                .sourceIdentifier(identifier)
                .targetIdentifier(identifier)
                .build();
    }

    //V3
    public static ReserveAndTransactRequest setUpReserveAndTransactV3Data(String clientId, ChannelName channelName,
                                                                          ChannelId channelId, String productId) {
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.AccountIdentifierV3)
                .authCode(null)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRefV3)
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV3)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .productId(productId) //917
                .purchaseAmount(ReserveAndTransactClient.PurchaseAmount10000)
                .feeAmount(ReserveAndTransactClient.FeeAmount0)
                .channelId(channelId.getChannelId()) //3
                .channelName(channelName.getChannelName()) //internet
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV3)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV3)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV3Data(String clientId, ChannelName channelName,
                                                                          ChannelId channelId, String productId, String ReserveFundTxn) {
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.AccountIdentifierV3)
                .authCode(null)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRefV3)
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV3)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .productId(productId) //917
                .purchaseAmount(ReserveAndTransactClient.PurchaseAmount10000)
                .feeAmount(ReserveAndTransactClient.FeeAmount0)
                .channelId(channelId.getChannelId()) //3
                .channelName(channelName.getChannelName()) //internet
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV3)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV3)
                .reserveFundsTxnRef(ReserveFundTxn)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV3DataAccIdentifier(String clientId, ChannelName channelName,
                                                                          ChannelId channelId, String productId,String accIdentifier){
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(accIdentifier)
                .authCode(null)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRefV3)
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV3)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .productId(productId) //917
                .purchaseAmount(ReserveAndTransactClient.PurchaseAmount10000)
                .feeAmount(ReserveAndTransactClient.FeeAmount0)
                .channelId(channelId.getChannelId()) //3
                .channelName(channelName.getChannelName()) //internet
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV3)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV3)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV3DataClientTxnRef(String clientId, ChannelName channelName,
                                                                                       ChannelId channelId, String productId,String clientTxnRef){
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.AccountIdentifierV3)
                .authCode(null)
                .clientTxnRef(clientTxnRef)
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV3)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .productId(productId) //917
                .purchaseAmount(ReserveAndTransactClient.PurchaseAmount10000)
                .feeAmount(ReserveAndTransactClient.FeeAmount0)
                .channelId(channelId.getChannelId()) //3
                .channelName(channelName.getChannelName()) //internet
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV3)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV3)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV3DataChannelSessionId(String clientId, ChannelName channelName,
                                                                                      ChannelId channelId, String productId,String channelSessionID){
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.AccountIdentifierV3)
                .authCode(null)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRefV3)
                .channelSessionId(channelSessionID)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .productId(productId) //917
                .purchaseAmount(ReserveAndTransactClient.PurchaseAmount10000)
                .feeAmount(ReserveAndTransactClient.FeeAmount0)
                .channelId(channelId.getChannelId()) //3
                .channelName(channelName.getChannelName()) //internet
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV3)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV3)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV3DataAuthCode(String clientId, ChannelName channelName,
                                                                                          ChannelId channelId, String productId,String authCode){
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.AccountIdentifierV3)
                .authCode(null)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRefV3)
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV3)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .productId(productId) //917
                .purchaseAmount(ReserveAndTransactClient.PurchaseAmount10000)
                .feeAmount(ReserveAndTransactClient.FeeAmount0)
                .channelId(channelId.getChannelId()) //3
                .channelName(channelName.getChannelName()) //internet
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV3)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV3)
                .authCode(authCode)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV3DataTimestamp(String clientId, ChannelName channelName,
                                                                                  ChannelId channelId, String productId,String timeStamp){
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.AccountIdentifierV3)
                .authCode(null)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRefV3)
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV3)
                .timestamp(timeStamp)
                .clientId(clientId) //3
                .productId(productId) //917
                .purchaseAmount(ReserveAndTransactClient.PurchaseAmount10000)
                .feeAmount(ReserveAndTransactClient.FeeAmount0)
                .channelId(channelId.getChannelId()) //3
                .channelName(channelName.getChannelName()) //internet
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV3)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV3)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV3DataPurchaseAmount(String clientId, ChannelName channelName,
                                                                          ChannelId channelId, String productId, String purchaseAmount) {
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.AccountIdentifierV3)
                .authCode(null)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRefV3)
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV3)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .productId(productId) //917
                .purchaseAmount(purchaseAmount)
                .feeAmount(ReserveAndTransactClient.FeeAmount0)
                .channelId(channelId.getChannelId()) //3
                .channelName(channelName.getChannelName()) //internet
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV3)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV3)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV3DataFeeAmount(String clientId, ChannelName channelName,
                                                                                        ChannelId channelId, String productId, String feeAmount) {
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.AccountIdentifierV3)
                .authCode(null)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRefV3)
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV3)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .productId(productId) //917
                .purchaseAmount(ReserveAndTransactClient.PurchaseAmount10000)
                .feeAmount(feeAmount)
                .channelId(channelId.getChannelId()) //3
                .channelName(channelName.getChannelName()) //internet
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV3)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV3)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV3DataSourceIdentifier(String clientId, ChannelName channelName,
                                                                          ChannelId channelId, String productId, String sourceIdentifier) {
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.AccountIdentifierV3)
                .authCode(null)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRefV3)
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV3)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .productId(productId) //917
                .purchaseAmount(ReserveAndTransactClient.PurchaseAmount10000)
                .feeAmount(ReserveAndTransactClient.FeeAmount0)
                .channelId(channelId.getChannelId()) //3
                .channelName(channelName.getChannelName()) //internet
                .sourceIdentifier(sourceIdentifier)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV3)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV3DataTargetIdentifier(String clientId, ChannelName channelName,
                                                                                          ChannelId channelId, String productId, String targetIdentifier) {
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.AccountIdentifierV3)
                .authCode(null)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRefV3)
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV3)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .productId(productId) //917
                .purchaseAmount(ReserveAndTransactClient.PurchaseAmount10000)
                .feeAmount(ReserveAndTransactClient.FeeAmount0)
                .channelId(channelId.getChannelId()) //3
                .channelName(channelName.getChannelName()) //internet
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV3)
                .targetIdentifier(targetIdentifier)
                .build();
    }


    //V2
    public static ReserveAndTransactRequest setUpReserveAndTransactV2Data(String clientId, ChannelName channelName,
                                                                          ChannelId channelId, String productId) {
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.IdentifierV2)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRefV2)
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV2)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .productId(productId) //917
                .amount(ReserveAndTransactClient.PurchaseAmount10000)
                .channelId(channelId.getChannelId()) //7
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV2)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV2)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV2DataSourceIdentifier(String clientId, ChannelName channelName,
                                                                          ChannelId channelId, String productId, String sourceIdentifier){
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.IdentifierV2)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRefV2)
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV2)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .productId(productId) //917
                .amount(ReserveAndTransactClient.PurchaseAmount10000)
                .channelId(channelId.getChannelId()) //7
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier(sourceIdentifier)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV2)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV2DataTargetIdentifier(String clientId, ChannelName channelName,
                                                                          ChannelId channelId, String productId, String targetIdentifier){
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.IdentifierV2)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRefV2)
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV2)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .productId(productId) //917
                .amount(ReserveAndTransactClient.PurchaseAmount10000)
                .channelId(channelId.getChannelId()) //7
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV2)
                .targetIdentifier(targetIdentifier)
                .build();
    }


    public static ReserveAndTransactRequest setUpReserveAndTransactV2DataPurchaseAmountMaxLimit(String clientId, ChannelName channelName,
                                                                          ChannelId channelId, String productId, String purchaseAmount){
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.IdentifierV2)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRefV2)
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV2)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .productId(productId) //917
                .amount(purchaseAmount)
                .channelId(channelId.getChannelId()) //7
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV2)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV2)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV2DataAccIdentifierMaxLimit(String clientId, ChannelName channelName,
                                                                          ChannelId channelId, String productId, String accountIdentifier){
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(accountIdentifier)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRefV2)
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV2)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .productId(productId) //917
                .amount(ReserveAndTransactClient.PurchaseAmount10000)
                .channelId(channelId.getChannelId()) //7
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV2)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV2)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV2DataClientTxnRefMaxLimit(String clientId, ChannelName channelName,
                                                                                               ChannelId channelId, String productId, String clientTxnRef){
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.IdentifierV2)
                .clientTxnRef(clientTxnRef)
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV2)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .productId(productId) //917
                .amount(ReserveAndTransactClient.PurchaseAmount10000)
                .channelId(channelId.getChannelId()) //7
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV2)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV2)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV2DataChannelSessionIDMaxLimit(String clientId, ChannelName channelName,
                                                                                              ChannelId channelId, String productId, String channelSessionID){
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.IdentifierV2)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRefV2)
                .channelSessionId(channelSessionID)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .productId(productId) //917
                .amount(ReserveAndTransactClient.PurchaseAmount10000)
                .channelId(channelId.getChannelId()) //7
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV2)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV2)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV2DataAuthCodeMaxLimit(String clientId, ChannelName channelName,
                                                                                                  ChannelId channelId, String productId, String authCode){
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.IdentifierV2)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRefV2)
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV2)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .productId(productId) //917
                .amount(ReserveAndTransactClient.PurchaseAmount10000)
                .channelId(channelId.getChannelId()) //7
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV2)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV2)
                .authCode(authCode)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV2DataTimeStampMaxLimit(String clientId, ChannelName channelName,
                                                                                          ChannelId channelId, String productId, String timeStamp){
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.IdentifierV2)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRefV2)
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV2)
                .timestamp(timeStamp)
                .clientId(clientId) //3
                .productId(productId) //917
                .amount(ReserveAndTransactClient.PurchaseAmount10000)
                .channelId(channelId.getChannelId()) //7
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV2)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV2)
                .build();
    }


    public static ReserveAndTransactRequest setUpReserveAndTransactV2Data(String clientId, ChannelName channelName,
                                                                          ChannelId channelId, String productId, String ReserveFundTxn) {
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.IdentifierV2)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRefV2)
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV2)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .productId(productId) //917
                .amount(ReserveAndTransactClient.PurchaseAmount10000)
                .channelId(channelId.getChannelId()) //7
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV2)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV2)
                .reserveFundsTxnRef(ReserveFundTxn)
                .build();
    }

    // V4 Target Identifier expected to be an MSISDN based on the product type
    public static ReserveAndTransactRequest setUpReserveAndTransactV4DataMSISDNProducType(String clientId, CurrencyCode currencyCode, ChannelName channelName,
                                                                                          ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String identifier) {
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.AccountIdentifier)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRef)
                .channelSessionId(ReserveAndTransactClient.channelSessionId)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .fundingSourceId(ReserveAndTransactClient.fundingSourceId)
                .productId(productId) //100
                .purchaseAmount(purchaseAmount) // 10000
                .feeAmount(feeAmount) //0
                .currencyCode(currencyCode.getCurrencyCode()) //NGN
                .channelId(channelId.getChannelId()) //7
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier(NotificationClient.Identifier_6)
                .targetIdentifier(ReserveAndTransactClient.Identifier_11)
                .build();
    }

    // V3 Target Identifier expected to be an MSISDN based on the product type

    public static ReserveAndTransactRequest setUpReserveAndTransactV3DataDataMSISDNProducType(String clientId, ChannelName channelName,
                                                                                              ChannelId channelId, String productId) {
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.Identifier_12)
                .authCode(null)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRefV3)
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV3)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .productId(productId) //917
                .purchaseAmount(ReserveAndTransactClient.PurchaseAmount10000)
                .feeAmount("0")
                .channelId(channelId.getChannelId()) //3
                .channelName(channelName.getChannelName()) //Mobile
                .sourceIdentifier(NotificationClient.Identifier_6)
                .targetIdentifier(ReserveAndTransactClient.Identifier_11)
                .build();
    }

    // V2 Target Identifier expected to be an MSISDN based on the product type

    public static ReserveAndTransactRequest setUpReserveAndTransactV2DataDataMSISDNProducType(String clientId, ChannelName channelName,
                                                                                              ChannelId channelId, String productId) {
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.IdentifierV2)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRefV2)
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV2)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .productId(productId) //917
                .amount("10000")
                .channelId(channelId.getChannelId()) //7
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier(NotificationClient.Identifier_6)
                .targetIdentifier(ReserveAndTransactClient.Identifier_11)
                .build();
    }

    // V1 Target Identifier expected to be an MSISDN based on the product type
    public static TransactRequest setUpTransactV1DataMSISDNProducType(String clientId, ChannelName channelName, ChannelId channelId,
                                                                      String productId) {
        return TransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.IdentifierV1)
                .authCode(null)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRefV1)
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV1)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .productId(productId) //917
                .amount(ReserveAndTransactClient.PurchaseAmount10000)
                .channelId(channelId.getChannelId()) //7
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier(NotificationClient.Identifier_6)
                .targetIdentifier(ReserveAndTransactClient.Identifier_11)
                .build();
    }

    // When Product is not linked with the clientID (TECH-74601)
    public static ReserveAndTransactRequest setUpReserveAndTransactV4DataWhenProductNotLinkedWithClient(String clientId, CurrencyCode currencyCode, ChannelName channelName,
                                                                          ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String identifier) {
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.AccountIdentifier)
                .authCode(null)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRef)
                .channelSessionId(ReserveAndTransactClient.channelSessionId)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .fundingSourceId(ReserveAndTransactClient.fundingSourceId)
                .productId(productId) //100
                .purchaseAmount(purchaseAmount) // 10000
                .feeAmount(feeAmount) //0
                .currencyCode(currencyCode.getCurrencyCode()) //NGN
                .channelId(channelId.getChannelId()) //7
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier(identifier)
                .targetIdentifier(identifier)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV4DataToTestServiceUnavailable(String clientId, CurrencyCode currencyCode, ChannelName channelName,
                                                                          ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String identifier) {
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.AccountIdentifier1)
                .authCode(null)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRef)
                .channelSessionId(ReserveAndTransactClient.channelSessionId)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .fundingSourceId(ReserveAndTransactClient.fundingSourceID_50)
                .productId(productId) //100
                .purchaseAmount(purchaseAmount) // 10000
                .feeAmount(feeAmount) //0
                .currencyCode(currencyCode.getCurrencyCode()) //NGN
                .channelId(channelId.getChannelId()) //7
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier(identifier)
                .targetIdentifier(identifier)
                .build();
    }
}
