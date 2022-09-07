package api.domains.financialTermsLookup.repo.model;

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

public class FinancialTermsLookupResponse {

 @JsonProperty("clientId")
 private String clientId;

 @JsonProperty("productId")
 private String productId;

 @JsonProperty("currencyCode")
 private String currencyCode;

 @JsonProperty("purchaseAmount")
 private String purchaseAmount;

 @JsonProperty("lookupReference")
 private String lookupReference;

 @JsonProperty("reserveAmount")
 private String reserveAmount;

 @JsonProperty("vendAmount")
 private String vendAmount;

 @JsonProperty("feeAmount")
 private String feeAmount;

 @JsonProperty("settlementAmount")
 private String settlementAmount;

 @JsonProperty("clientShareAmount")
 private String clientShareAmount;
}
