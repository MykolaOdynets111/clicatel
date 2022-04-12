package api.domains.user_transaction_lookup.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class UserTransactionLookupRequest {

    @JsonProperty("userIdentifier ")
    private String userIdentifier ;

    @JsonProperty("clientId")
    private int clientId;

    @JsonProperty("channelId")
    private int channelId;

    @JsonProperty("productId")
    private int productId;

    @JsonProperty("productTypeId")
    private int productTypeId;

    @JsonProperty("limit")
    private int limit;
}
