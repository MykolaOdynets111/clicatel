package api.domains.token_lookup.model;

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

public class TokenLookupRequest {

    @JsonProperty("sourceIdentifier")
    private String sourceIdentifier;

    @JsonProperty("clientId")
    private int clientId;

    @JsonProperty("channelId")
    private int channelId;

    @JsonProperty("productTypeId")
    private int productTypeId;

    @JsonProperty("limit")
    private int limit;
}
