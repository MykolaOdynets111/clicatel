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

public class TransactionLookupResponse {

    @JsonProperty("responseCode")
    private String responseCode;

    @JsonProperty("responseMessage")
    private String responseMessage;

    @JsonProperty("raasTxnRef")
    private String raasTxnRef;

    @JsonProperty("clientTxnRef")
    private String clientTxnRef;

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("productId")
    private String productId;

    @JsonProperty("transactionStatus")
    private String transactionStatus;

    @JsonProperty("reserveFundsResponseCode")
    private String reserveFundsResponseCode;

    @JsonProperty("reserveAmount")
    private String reserveAmount;

    @JsonProperty("transactResultResponseCode")
    private String transactResultResponseCode;

    @JsonProperty("lookupRef")
    private String lookupRef;
}
