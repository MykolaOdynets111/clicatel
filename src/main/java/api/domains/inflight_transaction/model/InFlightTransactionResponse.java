package api.domains.inflight_transaction.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Collection;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class InFlightTransactionResponse {

    @JsonProperty("hasPendingTransactions")
    private Boolean hasPendingTransactions;

    @JsonProperty("transactions")
    private Collection transactions;

}

//{
//        "hasPendingTransactions": true,
//        "transactions": [
//        {
//        "raasTxnRef": "701038cf-feb3-4759-8a05-425685509120",
//        "created": "2017-09-04T12:00:05.01Z"
//        }
//        ]
//}
