package api.domains.reserve_and_transact.repo;

import api.domains.reserve_and_transact.model.ReserveAndTransactRequest;
import api.enums.ChannelId;
import api.enums.ChannelName;
import api.enums.CurrencyCode;

import static util.DateProvider.getCurrentIsoDateTime;


public class ReserveAndTransactRequestRepo {
    //V4
    public static ReserveAndTransactRequest setUpReserveAndTransactV4Data(String clientId, CurrencyCode currencyCode, ChannelName channelName, ChannelId channelId, String productId){
        return ReserveAndTransactRequest.builder()
                .accountIdentifier("4000******0004")
                .authCode(null)
                .clientTxnRef("4ba4c3-3wsf8cf-f0004")
                .channelSessionId("400074970004")
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .fundingSourceId("3")
                .productId("100")
                .purchaseAmount("10000")
                .feeAmount("0")
                .currencyCode(currencyCode.getCurrencyCode()) //NGN
                .channelId(channelId.getChannelId()) //7
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier("2348038382067")
                .targetIdentifier("2348038382067")
                .build();
    }

    //V3
    public static ReserveAndTransactRequest setUpReserveAndTransactV3Data(String clientId, CurrencyCode currencyCode, ChannelName channelName){
        return ReserveAndTransactRequest.builder()
                .accountIdentifier("4000******0004")
                .authCode(null)
                .clientTxnRef("4ba4c3-3wsf8cf-f0004")
                .channelSessionId("400074970004")
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .fundingSourceId("3")
                .productId("100")
                .purchaseAmount("10000")
                .feeAmount("0")
                .currencyCode(currencyCode.getCurrencyCode()) //NGN
                .channelId("7")
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier("2348038382067")
                .targetIdentifier("2348038382067")
                .build();
    }

    //V2
    public static ReserveAndTransactRequest setUpReserveAndTransactV2Data(String clientId, CurrencyCode currencyCode, ChannelName channelName){
        return ReserveAndTransactRequest.builder()
                .accountIdentifier("4000******0004")
                .authCode(null)
                .clientTxnRef("4ba4c3-3wsf8cf-f0004")
                .channelSessionId("400074970004")
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .fundingSourceId("3")
                .productId("100")
                .purchaseAmount("10000")
                .feeAmount("0")
                .currencyCode(currencyCode.getCurrencyCode()) //NGN
                .channelId("7")
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier("2348038382067")
                .targetIdentifier("2348038382067")
                .build();
    }

    //V1
    public static ReserveAndTransactRequest setUpReserveAndTransactV1Data(String clientId, CurrencyCode currencyCode, ChannelName channelName){
        return ReserveAndTransactRequest.builder()
                .accountIdentifier("4000******0004")
                .authCode(null)
                .clientTxnRef("4ba4c3-3wsf8cf-f0004")
                .channelSessionId("400074970004")
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .fundingSourceId("3")
                .productId("100")
                .purchaseAmount("10000")
                .feeAmount("0")
                .currencyCode(currencyCode.getCurrencyCode()) //NGN
                .channelId("7")
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier("2348038382067")
                .targetIdentifier("2348038382067")
                .build();
    }

}
