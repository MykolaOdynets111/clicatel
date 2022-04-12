package api.domains.mno_lookup.model;

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

public class MnoLookupResponse {

    @JsonProperty("msisdn")
    private String msisdn;

    @JsonProperty("countryCallingCode")
    private String countryCallingCode;

    @JsonProperty("countryCode")
    private String countryCode;

    @JsonProperty("operatorCode")
    private String operatorCode;

    @JsonProperty("operatorName")
    private String operatorName;

    @JsonProperty("lookupRef")
    private String lookupRef;

    @JsonProperty("responseCode")
    private String responseCode;

    @JsonProperty("responseMessage")
    private String responseMessage;
}