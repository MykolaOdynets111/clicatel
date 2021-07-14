package api.domains.reserve_and_transact.repo;

import api.clients.ReserveAndTransactClient;
import api.domains.reserve_and_transact.model.ReserveAndTransactRequest;
import api.enums.ChannelId;
import api.enums.ChannelName;
import api.enums.CurrencyCode;
import static util.readers.PropertiesReader.getProperty;
import api.clients.*;

import static util.DateProvider.getCurrentIsoDateTime;


public class ReserveAndTransactRequestRepo {
    //V4
    public static ReserveAndTransactRequest setUpReserveAndTransactV4Data(String clientId, CurrencyCode currencyCode, ChannelName channelName,
                                                                          ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String identifier){
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
                .build();}

        public static ReserveAndTransactRequest setUpReserveAndTransactV4Data(String clientId, CurrencyCode currencyCode, ChannelName channelName,
                ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String identifier, String ReserveFundTxn){
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
                                                                          ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String identifier, String ReserveFundTxn, String fundingSourceId){
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
                                                                                   ChannelId channelId,String identifier){
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
                                                                          ChannelId channelId, String productId){
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.AccountIdentifierV3)
                .authCode(null)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRefV3)
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV3)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .productId(productId) //917
                .purchaseAmount(ReserveAndTransactClient.PurchaseAmount10000)
                .feeAmount("0")
                .channelId(channelId.getChannelId()) //3
                .channelName(channelName.getChannelName()) //internet
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV3)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV3)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV3Data(String clientId, ChannelName channelName,
                                                                          ChannelId channelId, String productId,String ReserveFundTxn){
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.AccountIdentifierV3)
                .authCode(null)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRefV3)
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV3)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .productId(productId) //917
                .purchaseAmount(ReserveAndTransactClient.PurchaseAmount10000)
                .feeAmount("0")
                .channelId(channelId.getChannelId()) //3
                .channelName(channelName.getChannelName()) //internet
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV3)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV3)
                .reserveFundsTxnRef(ReserveFundTxn)
                .build();
    }

    //V2
    public static ReserveAndTransactRequest setUpReserveAndTransactV2Data(String clientId, ChannelName channelName,
                                                                          ChannelId channelId, String productId){
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
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV2)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV2)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV2Data(String clientId, ChannelName channelName,
                                                                          ChannelId channelId, String productId, String ReserveFundTxn){
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
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV2)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV2)
                .reserveFundsTxnRef(ReserveFundTxn)
                .build();
    }

}
