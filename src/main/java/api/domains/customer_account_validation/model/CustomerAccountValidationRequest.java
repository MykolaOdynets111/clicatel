package api.domains.customer_account_validation.model;

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

public class CustomerAccountValidationRequest {
    @JsonProperty("clientId")
    private int clientId;

    @JsonProperty("productId")
    private int productId;

    @JsonProperty("sourceIdentifier")
    private String sourceIdentifier;

    @JsonProperty("targetIdentifier")
    private String targetIdentifier;

    @JsonProperty("purchaseAmount")
    private int purchaseAmount;

    @JsonProperty("timestamp")
    private String timestamp;

}
