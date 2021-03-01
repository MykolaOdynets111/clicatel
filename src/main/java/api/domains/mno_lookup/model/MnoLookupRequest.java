package api.domains.mno_lookup.model;

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

public class MnoLookupRequest {

    @JsonProperty("clientId")
    private int clientId;

    @JsonProperty("msisdn")
    private String msisdn;

    @JsonProperty("countryCallingCode")
    private int countryCallingCode;

    @JsonProperty("clientTxnRef")
    private String clientTxnRef;

    @JsonProperty("channelId")
    private int channelId;
}
