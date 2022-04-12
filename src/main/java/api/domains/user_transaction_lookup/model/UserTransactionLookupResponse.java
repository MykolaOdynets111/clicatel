package api.domains.user_transaction_lookup.model;

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

public class UserTransactionLookupResponse {

    @JsonProperty("channelId")
    private int channelId;

    @JsonProperty("clientId")
    private int clientId;

    @JsonProperty("raasTxnRef")
    private String raasTxnRef;

    @JsonProperty("transactionDate")
    private String transactionDate;

    @JsonProperty("productId")
    private int productId;

    @JsonProperty("description")
    private String description;

    @JsonProperty("purchaseAmount")
    private int purchaseAmount;

    @JsonProperty("targetIdentifier")
    private String targetIdentifier;
}