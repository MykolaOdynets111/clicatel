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

    //V4 R&T with Null
    public static ReserveAndTransactRequest setUpReserveAndTransactV4DataWithChannelSessionIDNull(String clientId, CurrencyCode currencyCode, ChannelName channelName,
                                                                          ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String identifier, String CSID) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(CSID)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId)
                .fundingSourceId(ReserveAndTransactClient.fundingSourceId)
                .productId(productId)
                .purchaseAmount(purchaseAmount)
                .feeAmount(feeAmount)
                .currencyCode(currencyCode.getCurrencyCode())
                .channelId(channelId.getChannelId())
                .channelName(channelName.getChannelName())
                .sourceIdentifier(identifier)
                .targetIdentifier(identifier)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV4DataWithtimestampIDNull(String clientId, CurrencyCode currencyCode, ChannelName channelName,
                                                                                                  ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String identifier, String TS) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(ReserveAndTransactClient.channelSessionId)
                .timestamp(TS)
                .clientId(clientId)
                .fundingSourceId(ReserveAndTransactClient.fundingSourceId)
                .productId(productId)
                .purchaseAmount(purchaseAmount)
                .feeAmount(feeAmount)
                .currencyCode(currencyCode.getCurrencyCode())
                .channelId(channelId.getChannelId())
                .channelName(channelName.getChannelName())
                .sourceIdentifier(identifier)
                .targetIdentifier(identifier)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV4DataWithclientIdNull(String clientId, CurrencyCode currencyCode, ChannelName channelName,
                                                                                                  ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String identifier) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(ReserveAndTransactClient.channelSessionId)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId)
                .fundingSourceId(ReserveAndTransactClient.fundingSourceId)
                .productId(productId)
                .purchaseAmount(purchaseAmount)
                .feeAmount(feeAmount)
                .currencyCode(currencyCode.getCurrencyCode())
                .channelId(channelId.getChannelId())
                .channelName(channelName.getChannelName())
                .sourceIdentifier(identifier)
                .targetIdentifier(identifier)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV4DataWithfundingSourceIdNull(String clientId, CurrencyCode currencyCode, ChannelName channelName,
                                                                                                  ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String identifier, String FS) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(ReserveAndTransactClient.channelSessionId)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId)
                .fundingSourceId(FS)
                .productId(productId)
                .purchaseAmount(purchaseAmount)
                .feeAmount(feeAmount)
                .currencyCode(currencyCode.getCurrencyCode())
                .channelId(channelId.getChannelId())
                .channelName(channelName.getChannelName())
                .sourceIdentifier(identifier)
                .targetIdentifier(identifier)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV4DataWithproductIdNull(String clientId, CurrencyCode currencyCode, ChannelName channelName,
                                                                                                  ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String identifier) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(ReserveAndTransactClient.channelSessionId)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId)
                .fundingSourceId(ReserveAndTransactClient.fundingSourceId)
                .productId(productId)
                .purchaseAmount(purchaseAmount)
                .feeAmount(feeAmount)
                .currencyCode(currencyCode.getCurrencyCode())
                .channelId(channelId.getChannelId())
                .channelName(channelName.getChannelName())
                .sourceIdentifier(identifier)
                .targetIdentifier(identifier)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV4DataWithpurchaseAmountNull(String clientId, CurrencyCode currencyCode, ChannelName channelName,
                                                                                                  ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String identifier) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(ReserveAndTransactClient.channelSessionId)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId)
                .fundingSourceId(ReserveAndTransactClient.fundingSourceId)
                .productId(productId)
                .purchaseAmount(purchaseAmount)
                .feeAmount(feeAmount)
                .currencyCode(currencyCode.getCurrencyCode())
                .channelId(channelId.getChannelId())
                .channelName(channelName.getChannelName())
                .sourceIdentifier(identifier)
                .targetIdentifier(identifier)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV4DataWithfeeAmountNull(String clientId, CurrencyCode currencyCode, ChannelName channelName,
                                                                                                  ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String identifier) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(ReserveAndTransactClient.channelSessionId)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId)
                .fundingSourceId(ReserveAndTransactClient.fundingSourceId)
                .productId(productId)
                .purchaseAmount(purchaseAmount)
                .feeAmount(feeAmount)
                .currencyCode(currencyCode.getCurrencyCode())
                .channelId(channelId.getChannelId())
                .channelName(channelName.getChannelName())
                .sourceIdentifier(identifier)
                .targetIdentifier(identifier)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV4DataWithcurrencyCodeNull(String clientId, String currencyCode, ChannelName channelName,
                                                                                                  ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String identifier) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(ReserveAndTransactClient.channelSessionId)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId)
                .fundingSourceId(ReserveAndTransactClient.fundingSourceId)
                .productId(productId)
                .purchaseAmount(purchaseAmount)
                .feeAmount(feeAmount)
                .currencyCode(null)
                .channelId(channelId.getChannelId())
                .channelName(channelName.getChannelName())
                .sourceIdentifier(identifier)
                .targetIdentifier(identifier)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV4DataWithchannelIdisNull(String clientId, CurrencyCode currencyCode, ChannelName channelName,
                                                                                                  String channelId, String productId, String purchaseAmount, String feeAmount, String identifier) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(ReserveAndTransactClient.channelSessionId)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId)
                .fundingSourceId(ReserveAndTransactClient.fundingSourceId)
                .productId(productId)
                .purchaseAmount(purchaseAmount)
                .feeAmount(feeAmount)
                .currencyCode(currencyCode.getCurrencyCode())
                .channelId(channelId)
                .channelName(channelName.getChannelName())
                .sourceIdentifier(identifier)
                .targetIdentifier(identifier)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV4DataWithchannelNameNull(String clientId, CurrencyCode currencyCode, String channelName,
                                                                                                  ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String identifier) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(ReserveAndTransactClient.channelSessionId)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId)
                .fundingSourceId(ReserveAndTransactClient.fundingSourceId)
                .productId(productId)
                .purchaseAmount(purchaseAmount)
                .feeAmount(feeAmount)
                .currencyCode(currencyCode.getCurrencyCode())
                .channelId(channelId.getChannelId())
                .channelName(channelName)
                .sourceIdentifier(identifier)
                .targetIdentifier(identifier)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV4DataWithsourceIdentifierNull(String clientId, CurrencyCode currencyCode, ChannelName channelName,
                                                                                                  ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String identifier, String Sidentifier) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(ReserveAndTransactClient.channelSessionId)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId)
                .fundingSourceId(ReserveAndTransactClient.fundingSourceId)
                .productId(productId)
                .purchaseAmount(purchaseAmount)
                .feeAmount(feeAmount)
                .currencyCode(currencyCode.getCurrencyCode())
                .channelId(channelId.getChannelId())
                .channelName(channelName.getChannelName())
                .sourceIdentifier(Sidentifier)
                .targetIdentifier(identifier)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV4DataWithTargetIdentifierNull(String clientId, CurrencyCode currencyCode, ChannelName channelName,
                                                                                                  ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String identifier, String Tidentifier) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(ReserveAndTransactClient.channelSessionId)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId)
                .fundingSourceId(ReserveAndTransactClient.fundingSourceId)
                .productId(productId)
                .purchaseAmount(purchaseAmount)
                .feeAmount(feeAmount)
                .currencyCode(currencyCode.getCurrencyCode())
                .channelId(channelId.getChannelId())
                .channelName(channelName.getChannelName())
                .sourceIdentifier(identifier)
                .targetIdentifier(Tidentifier)
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

    public static ReserveAndTransactRequest setUpReserveAndTransactV4DataClientAndFundingDiff(String clientId, CurrencyCode currencyCode, ChannelName channelName,
                                                                          ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String identifier, String fundingSourceId) {
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
// R&T V4 Empty Cases
    public static ReserveAndTransactRequest setUpReserveAndTransactV4DataWithEmptyChannelSessionID(String clientId, CurrencyCode currencyCode, ChannelName channelName,
                                                                          ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String identifier, String channelSessionId) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(channelSessionId)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .fundingSourceId(ReserveAndTransactClient.fundingSourceId)
                .productId(productId)
                .purchaseAmount(purchaseAmount)
                .feeAmount(feeAmount)
                .currencyCode(currencyCode.getCurrencyCode())
                .channelId(channelId.getChannelId())
                .channelName(channelName.getChannelName())
                .sourceIdentifier(identifier)
                .targetIdentifier(identifier)
                .build();
    }

        public static ReserveAndTransactRequest setUpReserveAndTransactV4DataWithEmptyTS(String clientId, CurrencyCode currencyCode, ChannelName channelName,
                                                                          ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String identifier, String TS) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(ReserveAndTransactClient.channelSessionId)
                .timestamp(TS)
                .clientId(clientId) //3
                .fundingSourceId(ReserveAndTransactClient.fundingSourceId)
                .productId(productId)
                .purchaseAmount(purchaseAmount)
                .feeAmount(feeAmount)
                .currencyCode(currencyCode.getCurrencyCode())
                .channelId(channelId.getChannelId())
                .channelName(channelName.getChannelName())
                .sourceIdentifier(identifier)
                .targetIdentifier(identifier)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV4DataWithEmptyCID(String clientId, CurrencyCode currencyCode, ChannelName channelName,
                                                                                     ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String identifier) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(ReserveAndTransactClient.channelSessionId)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .fundingSourceId(ReserveAndTransactClient.fundingSourceId)
                .productId(productId)
                .purchaseAmount(purchaseAmount)
                .feeAmount(feeAmount)
                .currencyCode(currencyCode.getCurrencyCode())
                .channelId(channelId.getChannelId())
                .channelName(channelName.getChannelName())
                .sourceIdentifier(identifier)
                .targetIdentifier(identifier)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV4DataWithEmptyFSID(String clientId, CurrencyCode currencyCode, ChannelName channelName,
                                                                                      ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String identifier, String FSID) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(ReserveAndTransactClient.channelSessionId)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .fundingSourceId(FSID)
                .productId(productId)
                .purchaseAmount(purchaseAmount)
                .feeAmount(feeAmount)
                .currencyCode(currencyCode.getCurrencyCode())
                .channelId(channelId.getChannelId())
                .channelName(channelName.getChannelName())
                .sourceIdentifier(identifier)
                .targetIdentifier(identifier)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV4DataWithEmptyPID(String clientId, CurrencyCode currencyCode, ChannelName channelName,
                                                                                       ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String identifier) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(ReserveAndTransactClient.channelSessionId)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .fundingSourceId(ReserveAndTransactClient.fundingSourceId)
                .productId(productId)
                .purchaseAmount(purchaseAmount)
                .feeAmount(feeAmount)
                .currencyCode(currencyCode.getCurrencyCode())
                .channelId(channelId.getChannelId())
                .channelName(channelName.getChannelName())
                .sourceIdentifier(identifier)
                .targetIdentifier(identifier)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV4DataWithEmptyPA(String clientId, CurrencyCode currencyCode, ChannelName channelName,
                                                                                      ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String identifier) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(ReserveAndTransactClient.channelSessionId)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .fundingSourceId(ReserveAndTransactClient.fundingSourceId)
                .productId(productId)
                .purchaseAmount(purchaseAmount)
                .feeAmount(feeAmount)
                .currencyCode(currencyCode.getCurrencyCode())
                .channelId(channelId.getChannelId())
                .channelName(channelName.getChannelName())
                .sourceIdentifier(identifier)
                .targetIdentifier(identifier)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV4DataWithEmptyFA(String clientId, CurrencyCode currencyCode, ChannelName channelName,
                                                                                     ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String identifier) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(ReserveAndTransactClient.channelSessionId)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .fundingSourceId(ReserveAndTransactClient.fundingSourceId)
                .productId(productId)
                .purchaseAmount(purchaseAmount)
                .feeAmount(feeAmount)
                .currencyCode(currencyCode.getCurrencyCode())
                .channelId(channelId.getChannelId())
                .channelName(channelName.getChannelName())
                .sourceIdentifier(identifier)
                .targetIdentifier(identifier)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV4DataWithEmptyCC(String clientId, String CC, ChannelName channelName,
                                                                                     ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String identifier) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(ReserveAndTransactClient.channelSessionId)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .fundingSourceId(ReserveAndTransactClient.fundingSourceId)
                .productId(productId)
                .purchaseAmount(purchaseAmount)
                .feeAmount(feeAmount)
                .currencyCode(CC)
                .channelId(channelId.getChannelId())
                .channelName(channelName.getChannelName())
                .sourceIdentifier(identifier)
                .targetIdentifier(identifier)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV4DataWithEmptyChannelID(String clientId, CurrencyCode currencyCode, ChannelName channelName,
                                                                                     String channelId, String productId, String purchaseAmount, String feeAmount, String identifier) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(ReserveAndTransactClient.channelSessionId)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .fundingSourceId(ReserveAndTransactClient.fundingSourceId)
                .productId(productId)
                .purchaseAmount(purchaseAmount)
                .feeAmount(feeAmount)
                .currencyCode(currencyCode.getCurrencyCode())
                .channelId(channelId)
                .channelName(channelName.getChannelName())
                .sourceIdentifier(identifier)
                .targetIdentifier(identifier)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV4DataWithEmptyChannelN(String clientId, CurrencyCode currencyCode, String channelName,
                                                                                           ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String identifier) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(ReserveAndTransactClient.channelSessionId)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .fundingSourceId(ReserveAndTransactClient.fundingSourceId)
                .productId(productId)
                .purchaseAmount(purchaseAmount)
                .feeAmount(feeAmount)
                .currencyCode(currencyCode.getCurrencyCode())
                .channelId(channelId.getChannelId())
                .channelName(channelName)
                .sourceIdentifier(identifier)
                .targetIdentifier(identifier)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV4DataWithEmptySI(String clientId, CurrencyCode currencyCode, ChannelName channelName,
                                                                                           ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String identifier, String SI) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(ReserveAndTransactClient.channelSessionId)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .fundingSourceId(ReserveAndTransactClient.fundingSourceId)
                .productId(productId)
                .purchaseAmount(purchaseAmount)
                .feeAmount(feeAmount)
                .currencyCode(currencyCode.getCurrencyCode())
                .channelId(channelId.getChannelId())
                .channelName(channelName.getChannelName())
                .sourceIdentifier(SI)
                .targetIdentifier(identifier)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV4DataWithEmptyTI(String clientId, CurrencyCode currencyCode, ChannelName channelName,
                                                                                     ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String identifier, String TI) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(ReserveAndTransactClient.channelSessionId)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .fundingSourceId(ReserveAndTransactClient.fundingSourceId)
                .productId(productId)
                .purchaseAmount(purchaseAmount)
                .feeAmount(feeAmount)
                .currencyCode(currencyCode.getCurrencyCode())
                .channelId(channelId.getChannelId())
                .channelName(channelName.getChannelName())
                .sourceIdentifier(identifier)
                .targetIdentifier(TI)
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
                                                                                       ChannelId channelId, String productId, String accIdentifier) {
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
                                                                                      ChannelId channelId, String productId, String clientTxnRef) {
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
                                                                                          ChannelId channelId, String productId, String channelSessionID) {
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
                                                                                  ChannelId channelId, String productId, String authCode) {
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
                                                                                   ChannelId channelId, String productId, String timeStamp) {
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

    //V3 R&T NULL
    public static ReserveAndTransactRequest setUpReserveAndTransactV3DataChannelSessionIDNull(String clientId, ChannelName channelName,
                                                                          ChannelId channelId, String productId, String CSI) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(CSI)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId)
                .productId(productId)
                .purchaseAmount(ReserveAndTransactClient.PurchaseAmount10000)
                .feeAmount(ReserveAndTransactClient.FeeAmount0)
                .channelId(channelId.getChannelId())
                .channelName(channelName.getChannelName())
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV3)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV3)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV3DataTimeStampNull(String clientId, ChannelName channelName,
                                                                                              ChannelId channelId, String productId,String TS) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV3)
                .timestamp(TS)
                .clientId(clientId)
                .productId(productId)
                .purchaseAmount(ReserveAndTransactClient.PurchaseAmount10000)
                .feeAmount(ReserveAndTransactClient.FeeAmount0)
                .channelId(channelId.getChannelId())
                .channelName(channelName.getChannelName())
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV3)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV3)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV3DataClientIDNull(String clientId, ChannelName channelName,
                                                                                       ChannelId channelId, String productId) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV3)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId)
                .productId(productId)
                .purchaseAmount(ReserveAndTransactClient.PurchaseAmount10000)
                .feeAmount(ReserveAndTransactClient.FeeAmount0)
                .channelId(channelId.getChannelId())
                .channelName(channelName.getChannelName())
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV3)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV3)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV3DataProductIDNull(String clientId, ChannelName channelName,
                                                                                      ChannelId channelId, String productId) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV3)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId)
                .productId(productId)
                .purchaseAmount(ReserveAndTransactClient.PurchaseAmount10000)
                .feeAmount(ReserveAndTransactClient.FeeAmount0)
                .channelId(channelId.getChannelId())
                .channelName(channelName.getChannelName())
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV3)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV3)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV3DataPurchaseAmountNull(String clientId, ChannelName channelName,
                                                                                       ChannelId channelId, String productId, String PA) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV3)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId)
                .productId(productId)
                .purchaseAmount(PA)
                .feeAmount(ReserveAndTransactClient.FeeAmount0)
                .channelId(channelId.getChannelId())
                .channelName(channelName.getChannelName())
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV3)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV3)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV3DataFeeAmountNull(String clientId, ChannelName channelName,
                                                                                            ChannelId channelId, String productId, String FA) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV3)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId)
                .productId(productId)
                .purchaseAmount(ReserveAndTransactClient.PurchaseAmount10000)
                .feeAmount(FA)
                .channelId(channelId.getChannelId())
                .channelName(channelName.getChannelName())
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV3)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV3)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV3DataChannelIDNull(String clientId, ChannelName channelName,
                                                                                       String channelId, String productId) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV3)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId)
                .productId(productId)
                .purchaseAmount(ReserveAndTransactClient.PurchaseAmount10000)
                .feeAmount(ReserveAndTransactClient.FeeAmount0)
                .channelId(channelId)
                .channelName(channelName.getChannelName())
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV3)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV3)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV3DataChannelNameNull(String clientId, String channelName,
                                                                                       ChannelId channelId, String productId) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV3)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId)
                .productId(productId)
                .purchaseAmount(ReserveAndTransactClient.PurchaseAmount10000)
                .feeAmount(ReserveAndTransactClient.FeeAmount0)
                .channelId(channelId.getChannelId())
                .channelName(channelName)
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV3)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV3)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV3DataSourceIdentifierNull(String clientId, ChannelName channelName,
                                                                                              ChannelId channelId, String productId, String SI) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV3)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId)
                .productId(productId)
                .purchaseAmount(ReserveAndTransactClient.PurchaseAmount10000)
                .feeAmount(ReserveAndTransactClient.FeeAmount0)
                .channelId(channelId.getChannelId())
                .channelName(channelName.getChannelName())
                .sourceIdentifier(SI)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV3)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV3DataTargetIdentifierNull(String clientId, ChannelName channelName,
                                                                                              ChannelId channelId, String productId, String TI) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV3)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId)
                .productId(productId)
                .purchaseAmount(ReserveAndTransactClient.PurchaseAmount10000)
                .feeAmount(ReserveAndTransactClient.FeeAmount0)
                .channelId(channelId.getChannelId())
                .channelName(channelName.getChannelName())
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV3)
                .targetIdentifier(TI)
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

    //V2 NULL

    public static ReserveAndTransactRequest setUpReserveAndTransactV2DataWithCSIDNULL(String clientId, ChannelName channelName,
                                                                          ChannelId channelId, String productId, String CSI) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(CSI)
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
    public static ReserveAndTransactRequest setUpReserveAndTransactV2DataWithTSNULL(String clientId, ChannelName channelName,
                                                                                      ChannelId channelId, String productId, String TS) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV2)
                .timestamp(TS)
                .clientId(clientId) //3
                .productId(productId) //917
                .amount(ReserveAndTransactClient.PurchaseAmount10000)
                .channelId(channelId.getChannelId()) //7
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV2)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV2)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV2DataWithClientIDNULL(String clientId, ChannelName channelName,
                                                                                    ChannelId channelId, String productId) {
        return ReserveAndTransactRequest.builder()
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

    public static ReserveAndTransactRequest setUpReserveAndTransactV2DataWithProductIDNULL(String clientId, ChannelName channelName,
                                                                                          ChannelId channelId, String productId) {
        return ReserveAndTransactRequest.builder()
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

    public static ReserveAndTransactRequest setUpReserveAndTransactV2DataWithPurchaseAmountNULL(String clientId, ChannelName channelName,
                                                                                           ChannelId channelId, String productId, String PurchaseAmount) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV2)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .productId(productId) //917
                .amount(PurchaseAmount)
                .channelId(channelId.getChannelId()) //7
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV2)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV2)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV2DataWithChannelIDNULL(String clientId, ChannelName channelName,
                                                                                                String channelId, String productId) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV2)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .productId(productId) //917
                .amount(ReserveAndTransactClient.PurchaseAmount10000)
                .channelId(channelId) //7
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV2)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV2)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV2DataWithChannelNameNULL(String clientId, String channelName,
                                                                                             ChannelId channelId, String productId) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV2)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .productId(productId) //917
                .amount(ReserveAndTransactClient.PurchaseAmount10000)
                .channelId(channelId.getChannelId()) //7
                .channelName(channelName) //USSD
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV2)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV2)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV2DataWithSourceIdentifierNULL(String clientId, ChannelName channelName,
                                                                                             ChannelId channelId, String productId, String SI) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV2)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .productId(productId) //917
                .amount(ReserveAndTransactClient.PurchaseAmount10000)
                .channelId(channelId.getChannelId()) //7
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier(SI)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV2)
                .build();
    }

    public static ReserveAndTransactRequest setUpReserveAndTransactV2DataWithTargetIdentifierNULL(String clientId, ChannelName channelName,
                                                                                             ChannelId channelId, String productId, String TI) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV2)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .productId(productId) //917
                .amount(ReserveAndTransactClient.PurchaseAmount10000)
                .channelId(channelId.getChannelId()) //7
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV2)
                .targetIdentifier(TI)
                .build();
    }


    public static ReserveAndTransactRequest setUpReserveAndTransactV2DataSourceIdentifier(String clientId, ChannelName channelName,
                                                                                          ChannelId channelId, String productId, String sourceIdentifier) {
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
                                                                                          ChannelId channelId, String productId, String targetIdentifier) {
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
                                                                                                ChannelId channelId, String productId, String purchaseAmount) {
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
                                                                                               ChannelId channelId, String productId, String accountIdentifier) {
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
                                                                                              ChannelId channelId, String productId, String clientTxnRef) {
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
                                                                                                  ChannelId channelId, String productId, String channelSessionID) {
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
                                                                                          ChannelId channelId, String productId, String authCode) {
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
                                                                                           ChannelId channelId, String productId, String timeStamp) {
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

    //R&T V3 Empty Value for ChannelSessionID
/*    public static ReserveAndTransactRequest setUpReserveAndTransactV4DataWithEmptyValueCSID(String clientId, CurrencyCode currencyCode, ChannelName channelName,
                                                                          ChannelId channelId, String productId, String purchaseAmount, String feeAmount, String identifier, String CSID) {
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.AccountIdentifier)
                .authCode(null)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRef)
                .channelSessionId(CSID)
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
    }*/
    public static ReserveAndTransactRequest setUpReserveAndTransactV3DataWithEmptyValueCSID(String clientId, ChannelName channelName,
                                                                                            ChannelId channelId, String productId, String CSID) {
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.AccountIdentifierV3)
                .authCode(null)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRefV3)
                .channelSessionId(CSID)
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

    //R&T V3 Empty Value for TS
    public static ReserveAndTransactRequest setUpReserveAndTransactV3DataWithEmptyValueTS(String clientId, ChannelName channelName,
                                                                                          ChannelId channelId, String productId, String TS) {
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.AccountIdentifierV3)
                .authCode(null)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRefV3)
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV3)
                .timestamp(TS)
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

    //R&T V3 Empty Value for PA
    public static ReserveAndTransactRequest setUpReserveAndTransactV3DataWithEmptyValuePA(String clientId, ChannelName channelName,
                                                                                          ChannelId channelId, String productId, String PA) {
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.AccountIdentifierV3)
                .authCode(null)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRefV3)
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV3)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .productId(productId) //917
                .purchaseAmount(PA)
                .feeAmount(ReserveAndTransactClient.FeeAmount0)
                .channelId(channelId.getChannelId()) //3
                .channelName(channelName.getChannelName()) //internet
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV3)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV3)
                .build();
    }

    //R&T V3 Empty Value for FA
    public static ReserveAndTransactRequest setUpReserveAndTransactV3DataWithEmptyValueFA(String clientId, ChannelName channelName,
                                                                                          ChannelId channelId, String productId, String FA) {
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.AccountIdentifierV3)
                .authCode(null)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRefV3)
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV3)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .productId(productId) //917
                .purchaseAmount(ReserveAndTransactClient.PurchaseAmount10000)
                .feeAmount(FA)
                .channelId(channelId.getChannelId()) //3
                .channelName(channelName.getChannelName()) //internet
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV3)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV3)
                .build();
    }

    //R&T V3 Empty Value for ChannelID
    public static ReserveAndTransactRequest setUpReserveAndTransactV3DataWithEmptyValueChannelID(String clientId, ChannelName channelName,
                                                                                                 String channelId, String productId) {
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
                .channelId(channelId) //3
                .channelName(channelName.getChannelName()) //internet
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV3)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV3)
                .build();
    }

    //R&T V3 Empty Value for ChannelName
    public static ReserveAndTransactRequest setUpReserveAndTransactV3DataWithEmptyValueChannelName(String clientId, String channelName,
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
                .channelName(channelName)
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV3)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV3)
                .build();
    }

    //R&T V3 Empty Value for Source Identifier
    public static ReserveAndTransactRequest setUpReserveAndTransactV3DataWithEmptyValueSI(String clientId, ChannelName channelName,
                                                                                          ChannelId channelId, String productId, String SI, String TS) {
        return ReserveAndTransactRequest.builder()
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV3)
                .timestamp(TS)
                .clientId(clientId) //3
                .productId(productId) //917
                .purchaseAmount(ReserveAndTransactClient.PurchaseAmount10000)
                .feeAmount("0")//ReserveAndTransactClient.FeeAmount0
                .channelId(channelId.getChannelId()) //3
                .channelName(channelName.getChannelName())
                .sourceIdentifier(SI)
                .targetIdentifier(NotificationClient.Identifier_6)
                .build();
    }

    //R&T V3 Empty Value for Target Identifier
    public static ReserveAndTransactRequest setUpReserveAndTransactV3DataWithEmptyValueTI(String clientId, ChannelName channelName,
                                                                                          ChannelId channelId, String productId, String TI) {
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
                .channelName(channelName.getChannelName())
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV3)
                .targetIdentifier(TI)
                .build();
    }

    //R&T V2 Empty Value for ChannelSessionID
    public static ReserveAndTransactRequest setUpReserveAndTransactV2DataWithEmptyValueCSID(String clientId, ChannelName channelName,
                                                                          ChannelId channelId, String productId, String CSID) {
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.IdentifierV2)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRefV2)
                .channelSessionId(CSID)
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

    //R&T V2 Empty Value for ChannelSessionID
    public static ReserveAndTransactRequest setUpReserveAndTransactV2DataWithEmptyValueTS(String clientId, ChannelName channelName,
                                                                                            ChannelId channelId, String productId, String TS) {
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.IdentifierV2)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRefV2)
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV3)
                .timestamp(TS)
                .clientId(clientId) //3
                .productId(productId) //917
                .amount(ReserveAndTransactClient.PurchaseAmount10000)
                .channelId(channelId.getChannelId()) //7
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV2)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV2)
                .build();
    }

    //R&T V2 Empty Value for ClientID
    public static ReserveAndTransactRequest setUpReserveAndTransactV2DataWithEmptyValueCID(String clientId, ChannelName channelName,
                                                                                          ChannelId channelId, String productId) {
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.IdentifierV2)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRefV2)
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV3)
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

    //R&T V2 Empty Value for ClientID
    public static ReserveAndTransactRequest setUpReserveAndTransactV2DataWithEmptyValuePA(String clientId, ChannelName channelName,
                                                                                           ChannelId channelId, String productId) {
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.IdentifierV2)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRefV2)
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV3)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .productId(productId) //917
                .amount("")
                .channelId(channelId.getChannelId()) //7
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV2)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV2)
                .build();
    }


    //R&T V2 Empty Value for ChannelID
    public static ReserveAndTransactRequest setUpReserveAndTransactV2DataWithEmptyValueChannelID(String clientId, ChannelName channelName,
                                                                                           String channelId, String productId) {
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.IdentifierV2)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRefV2)
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV3)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .productId(productId) //917
                .amount(ReserveAndTransactClient.PurchaseAmount10000)
                .channelId(channelId) //7
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV2)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV2)
                .build();
    }

    //R&T V2 Empty Value for ChannelName
    public static ReserveAndTransactRequest setUpReserveAndTransactV2DataWithEmptyValueChannelName(String clientId, String channelName,
                                                                                                   ChannelId channelId, String productId) {
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.IdentifierV2)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRefV2)
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV3)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .productId(productId) //917
                .amount(ReserveAndTransactClient.PurchaseAmount10000)
                .channelId(channelId.getChannelId()) //7
                .channelName(channelName) //USSD
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV2)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV2)
                .build();
    }

    //R&T V2 Empty Value for ChannelName
    public static ReserveAndTransactRequest setUpReserveAndTransactV2DataWithEmptyValueSI(String clientId, ChannelName channelName,
                                                                                                   ChannelId channelId, String productId, String SI) {
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.IdentifierV2)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRefV2)
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV3)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .productId(productId) //917
                .amount(ReserveAndTransactClient.PurchaseAmount10000)
                .channelId(channelId.getChannelId()) //7
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier(SI)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV2)
                .build();
    }

    //R&T V2 Empty Value for ChannelName
    public static ReserveAndTransactRequest setUpReserveAndTransactV2DataWithEmptyValueTI(String clientId, ChannelName channelName,
                                                                                                   ChannelId channelId, String productId,String TI) {
        return ReserveAndTransactRequest.builder()
                .accountIdentifier(ReserveAndTransactClient.IdentifierV2)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRefV2)
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV3)
                .timestamp(getCurrentIsoDateTime())
                .clientId(clientId) //3
                .productId(productId) //917
                .amount(ReserveAndTransactClient.PurchaseAmount10000)
                .channelId(channelId.getChannelId()) //7
                .channelName(channelName.getChannelName()) //USSD
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV2)
                .targetIdentifier(TI)
                .build();
    }

    // V3 R&T with Signature body creation
    public static ReserveAndTransactRequest setUpReserveAndTransactV3DataWtihSignature(String clientId, ChannelName channelName,
                                                                                       ChannelId channelId, String productId) {
        return ReserveAndTransactRequest.builder()
                .timestamp("2023-03-03T00:00:00.000+02:00")
                .accountIdentifier(ReserveAndTransactClient.AccountIdentifier4)
                .authCode(null)
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV3)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRefV3)
                .clientId(clientId)
                .productId(productId)
                .purchaseAmount(ReserveAndTransactClient.PurchaseAmount10000)
                .feeAmount(ReserveAndTransactClient.FeeAmount0)
                .channelId(channelId.getChannelId())
                .channelName(channelName.getChannelName())
                .currencyCode(CurrencyCode.NGN.getCurrencyCode())
                .fundingSourceId(ReserveAndTransactClient.TestClient1003)
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV3)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV3)
                .build();
    }
// V3 R&T with Signature body creation to execute with invalid body
    public static ReserveAndTransactRequest setUpReserveAndTransactV3DataWtihSignaturetoExecuteWithInvalidBody(String clientId, ChannelName channelName,
                                                                                       ChannelId channelId, String productId) {
        return ReserveAndTransactRequest.builder()
                .timestamp("2023-03-03T00:00:00.000+02:00")
                .accountIdentifier(ReserveAndTransactClient.AccountIdentifier4)
                .authCode(null)
                .channelSessionId(ReserveAndTransactClient.channelSessionIdV3)
                .clientTxnRef(ReserveAndTransactClient.clientTxnRefV3)
                .clientId(clientId)
                .productId(productId)
                .purchaseAmount(ReserveAndTransactClient.PurchaseAmount10000)
                .feeAmount(ReserveAndTransactClient.FeeAmount10) //Invalid fee amount
                .channelId(channelId.getChannelId())
                .channelName(channelName.getChannelName())
                .currencyCode(CurrencyCode.NGN.getCurrencyCode())
                .fundingSourceId(ReserveAndTransactClient.fundingSourceId)
                .sourceIdentifier(ReserveAndTransactClient.IdentifierV3)
                .targetIdentifier(ReserveAndTransactClient.IdentifierV3)
                .build();
    }
}