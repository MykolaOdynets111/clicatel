package api.domains.reserve_and_transact.repo;

import api.domains.reserve_and_transact.model.ReserveAndTransactRequest;
import api.enums.ChannelId;
import api.enums.ChannelName;
import api.enums.CurrencyCode;

import static util.DateProvider.getCurrentIsoDateTime;


public class ReserveAndTransactRequestRepo {
    //V4
    public static ReserveAndTransactRequest setUpReserveAndTransactV4Data(String clientId, CurrencyCode currencyCode, ChannelName channelName,
                                                                          ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String identifier){
        return ReserveAndTransactRequest.builder()
                .accountIdentifier("4000******0004")
                .authCode(null)
                .clientTxnRef("4ba4c3-3wsf8cf-f0004")
                .channelSessionId("400074970004")
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .fundingSourceId("3")
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

    //V4
    public static ReserveAndTransactRequest setUpReserveAndTransactSignatureData(String clientId, String fundingSourceId, CurrencyCode currencyCode, ChannelName channelName,
                                                                                   ChannelId channelId,String identifier){
        return ReserveAndTransactRequest.builder()
                .accountIdentifier("000XXX0311-0003")
                .clientTxnRef("010002441811llim-0003")
                .channelSessionId("714890809-0003")
                .timestamp("2023-03-03T00:00:00.000+02:00")
                .clientId(clientId) //1003
                .fundingSourceId(fundingSourceId) //1003
                .productId("917")
                .purchaseAmount("10000")
                .feeAmount("0")
                .currencyCode(currencyCode.getCurrencyCode()) //NGN
                .channelId(channelId.getChannelId()) //7
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier(identifier)
                .targetIdentifier(identifier)
                .build();
    }

    //V3
    public static ReserveAndTransactRequest setUpReserveAndTransactV3Data(String clientId, ChannelName channelName,
                                                                          ChannelId channelId, String productId){
        return ReserveAndTransactRequest.builder()
                .accountIdentifier("000XXX0311-0003")
                .authCode(null)
                .clientTxnRef("010002441811llim-0003")
                .channelSessionId("714890809-0003")
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .productId(productId) //917
                .purchaseAmount("10000")
                .feeAmount("0")
                .channelId(channelId.getChannelId()) //3
                .channelName(channelName.getChannelName()) //internet
                .sourceIdentifier("2348038382068")
                .targetIdentifier("2348038382068")
                .build();
    }

    //V2
    public static ReserveAndTransactRequest setUpReserveAndTransactV2Data(String clientId, ChannelName channelName,
                                                                          ChannelId channelId, String productId){
        return ReserveAndTransactRequest.builder()
                .accountIdentifier("222222xxxxxx0002")
                .clientTxnRef("30234241786MgAUs-0002")
                .channelSessionId("d5d65725c1414446b8546c5fcd5-0002")
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .productId(productId) //917
                .amount("10000")
                .channelId(channelId.getChannelId()) //7
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier("2348038382069")
                .targetIdentifier("2348038382069")
                .build();
    }

}
