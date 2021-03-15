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

    @JsonProperty("reserveFundsTxnRef")
    private String reserveFundsTxnRef;

    @JsonProperty("clientId")
    private int clientId;


}
