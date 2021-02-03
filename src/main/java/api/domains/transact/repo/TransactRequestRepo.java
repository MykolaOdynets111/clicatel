package api.domains.transact.repo;

import api.domains.transact.model.TransactRequest;
import api.enums.ChannelId;
import api.enums.ChannelName;
import api.enums.CurrencyCode;

import static util.DateProvider.getCurrentIsoDateTime;


public class TransactRequestRepo {
    //V4
    public static TransactRequest setUpTransactV4Data(String clientId, CurrencyCode currencyCode,
                                                                          ChannelName channelName, ChannelId channelId, String productId){
        return TransactRequest.builder()
                .timestamp(getCurrentIsoDateTime())
                .accountIdentifier("0000XXXX0004")
                .clientTxnRef("1598914290004")
                .channelSessionId("20200831230000004")
                .reserveFundsTxnRef("200831235610408740004")
                .clientId(clientId)
                .fundingSourceId("3")
                .productId(productId)
                .amount("10000")
                .feeAmount("0")
                .currencyCode(currencyCode.getCurrencyCode())
                .channelId(channelId.getChannelId())
                .channelName(channelName.getChannelName())
                .sourceIdentifier("2348038382068")
                .targetIdentifier("2348038382068")
                .build();
    }

    //V3
    public static TransactRequest setUpTransactV3Data(String clientId, ChannelName channelName,
                                                                          ChannelId channelId, String productId){
        return TransactRequest.builder()
                .timestamp(getCurrentIsoDateTime())
                .accountIdentifier(null)
                .reserveFundsTxnRef("90b91ada-df60-4a03-b0c3-c6bbf8381d09-0003")
                .channelSessionId("channelSessionId-0003")
                .clientTxnRef("toxR9F-0003")
                .clientId(clientId)
                .productId(productId)
                .amount("10000")
                .feeAmount("0")
                .channelId(channelId.getChannelId())
                .channelName(channelName.getChannelName())
                .sourceIdentifier("2348038382068")
                .targetIdentifier("2348038382068")
                .build();
    }

    //V2
    public static TransactRequest setUpTransactV2Data(String clientId, ChannelName channelName,
                                                                          ChannelId channelId, String productId){
        return TransactRequest.builder()
                .accountIdentifier("222222xxxxxx0003")
                .clientTxnRef("30234241786MgAUs-0002")
                .channelSessionId("d5d65725c1414446b8546c5fcd5-0002")
                .timestamp(getCurrentIsoDateTime())
                .reserveFundsTxnRef("601502164444630590-0002")
                .clientId(clientId)
                .productId(productId)
                .amount("10000")
                .channelId(channelId.getChannelId())
                .channelName(channelName.getChannelName())
                .sourceIdentifier("2348038382069")
                .targetIdentifier("2348038382069")
                .build();
    }

    //V1
    public static TransactRequest setUpTransactV1Data(String clientId, ChannelName channelName, ChannelId channelId,
                                                                                                    String productId){
        return TransactRequest.builder()
                .accountIdentifier(null)
                .authCode(null)
                .clientTxnRef("113-2348057670126-15989115-0001")
                .channelSessionId("714890001")
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .productId(productId) //917
                .amount("20000") //could possibly add amount as a parameter as well?
                .channelId(channelId.getChannelId()) //7
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier("2348038382068")
                .targetIdentifier("2348038382068")
                .build();
    }

}
