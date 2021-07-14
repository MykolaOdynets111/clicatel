package api.domains.transact.repo;

import api.clients.ReserveAndTransactClient;
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

    public static TransactRequest setUpTransactV4DataWithCSIDEmpty(String clientId, CurrencyCode currencyCode,
                                                                   ChannelName channelName, ChannelId channelId, String productId){
        return TransactRequest.builder()
                .timestamp(getCurrentIsoDateTime())
                .accountIdentifier("0000XXXX0004")
                .clientTxnRef("1598914290004")
                .channelSessionId("")
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

    public static TransactRequest setUpTransactV4DataWithTSEmpty(String clientId, CurrencyCode currencyCode,
                                                                 ChannelName channelName, ChannelId channelId, String productId){
        return TransactRequest.builder()
                .timestamp("")
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
    public static TransactRequest setUpTransactV4DataWithRFTREmpty(String clientId, CurrencyCode currencyCode,
                                                                   ChannelName channelName, ChannelId channelId, String productId){
        return TransactRequest.builder()
                .timestamp(getCurrentIsoDateTime())
                .accountIdentifier("0000XXXX0004")
                .clientTxnRef("1598914290004")
                .channelSessionId("20200831230000004")
                .reserveFundsTxnRef("")
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

    public static TransactRequest setUpTransactV4DataWithRCIEmpty(String clientId, CurrencyCode currencyCode,
                                                                  ChannelName channelName, ChannelId channelId, String productId){
        return TransactRequest.builder()
                .timestamp(getCurrentIsoDateTime())
                .accountIdentifier("0000XXXX0004")
                .clientTxnRef("1598914290004")
                .channelSessionId("20200831230000004")
                .reserveFundsTxnRef("200831235610408740004")
                .clientId("")
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

    public static TransactRequest setUpTransactV4DataWithFSEmpty(String clientId, CurrencyCode currencyCode,
                                                                 ChannelName channelName, ChannelId channelId, String productId){
        return TransactRequest.builder()
                .timestamp(getCurrentIsoDateTime())
                .accountIdentifier("0000XXXX0004")
                .clientTxnRef("1598914290004")
                .channelSessionId("20200831230000004")
                .reserveFundsTxnRef("200831235610408740004")
                .clientId(clientId)
                .fundingSourceId("")
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

    public static TransactRequest setUpTransactV4DataWithPIDEmpty(String clientId, CurrencyCode currencyCode,
                                                                  ChannelName channelName, ChannelId channelId, String productId){
        return TransactRequest.builder()
                .timestamp(getCurrentIsoDateTime())
                .accountIdentifier("0000XXXX0004")
                .clientTxnRef("1598914290004")
                .channelSessionId("20200831230000004")
                .reserveFundsTxnRef("200831235610408740004")
                .clientId(clientId)
                .fundingSourceId("3")
                .productId("")
                .amount("10000")
                .feeAmount("0")
                .currencyCode(currencyCode.getCurrencyCode())
                .channelId(channelId.getChannelId())
                .channelName(channelName.getChannelName())
                .sourceIdentifier("2348038382068")
                .targetIdentifier("2348038382068")
                .build();
    }

    public static TransactRequest setUpTransactV4DataWithPAEmpty(String clientId, CurrencyCode currencyCode,
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
                .amount("")
                .feeAmount("0")
                .currencyCode(currencyCode.getCurrencyCode())
                .channelId(channelId.getChannelId())
                .channelName(channelName.getChannelName())
                .sourceIdentifier("2348038382068")
                .targetIdentifier("2348038382068")
                .build();
    }

    public static TransactRequest setUpTransactV4DataWithFAEmpty(String clientId, CurrencyCode currencyCode,
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
                .feeAmount("")
                .currencyCode(currencyCode.getCurrencyCode())
                .channelId(channelId.getChannelId())
                .channelName(channelName.getChannelName())
                .sourceIdentifier("2348038382068")
                .targetIdentifier("2348038382068")
                .build();
    }

    public static TransactRequest setUpTransactV4DataWithCCEmpty(String clientId, CurrencyCode currencyCode,
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
                .currencyCode("")
                .channelId(channelId.getChannelId())
                .channelName(channelName.getChannelName())
                .sourceIdentifier("2348038382068")
                .targetIdentifier("2348038382068")
                .build();
    }

    public static TransactRequest setUpTransactV4DataWithCNEmpty(String clientId, CurrencyCode currencyCode,
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
                .channelName("")
                .sourceIdentifier("2348038382068")
                .targetIdentifier("2348038382068")
                .build();
    }

    public static TransactRequest setUpTransactV4DataWithCIDEmpty(String clientId, CurrencyCode currencyCode,
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
                .channelId("")
                .channelName(channelName.getChannelName())
                .sourceIdentifier("2348038382068")
                .targetIdentifier("2348038382068")
                .build();
    }

    public static TransactRequest setUpTransactV4DataWithSIEmpty(String clientId, CurrencyCode currencyCode,
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
                .sourceIdentifier("")
                .targetIdentifier("2348038382068")
                .build();
    }

    public static TransactRequest setUpTransactV4DataWithCTIEmpty(String clientId, CurrencyCode currencyCode,
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
                .targetIdentifier("")
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
                .clientTxnRef(ReserveAndTransactClient.clientTxnRefV1)
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV1)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .productId(productId) //917
                .amount(ReserveAndTransactClient.PurchaseAmount10000) //could possibly add amount as a parameter as well?
                .channelId(channelId.getChannelId()) //7
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV1)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV1)
                .build();
    }

}
