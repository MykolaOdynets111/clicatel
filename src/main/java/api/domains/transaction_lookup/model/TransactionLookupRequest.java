package api.domains.transaction_lookup.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class TransactionLookupRequest {

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("accountIdentifier")
    private String accountIdentifier;

    @JsonProperty("reserveFundsTxnRef")
    private String reserveFundsTxnRef;

    @JsonProperty("authCode")
    private String authCode;

    @JsonProperty("clientTxnRef")
    private String clientTxnRef;

    @JsonProperty("channelSessionId")
    private String channelSessionId;

    @JsonProperty("clientId")
    private String clientId;

    @JsonProperty("fundingSourceId")
    private String fundingSourceId;

    @JsonProperty("productId")
    private String productId;

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("feeAmount")
    private String feeAmount;

    @JsonProperty("currencyCode")
    private String currencyCode;

    @JsonProperty("channelId")
    private String channelId;

    @JsonProperty("channelName")
    private String channelName;

    @JsonProperty("sourceIdentifier")
    private String sourceIdentifier;

    @JsonProperty("targetIdentifier")
    private String targetIdentifier;

}
