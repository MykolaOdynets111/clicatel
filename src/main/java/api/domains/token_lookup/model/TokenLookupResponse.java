package api.domains.token_lookup.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class TokenLookupResponse {

    @JsonProperty("listItem")
    private String listItem;

    @JsonProperty("targetIdentifier")
    private String targetIdentifier;

    @JsonProperty("clientId")
    private int clientId;

    @JsonProperty("productId")
    private int productId;

    @JsonProperty("description")
    private String description;

    @JsonProperty("attributes")
    private List attributes;

    @JsonProperty("raasTxnRef")
    private String raasTxnRef;

    @JsonProperty("transactionDate")
    private String transactionDate;

    @JsonProperty("channelId")
    private int channelId;

    @JsonProperty("productTypeId")
    private int productTypeId;

    @JsonProperty("purchaseAmount")
    private int purchaseAmount;

    @JsonProperty("token")
    private String token;
}