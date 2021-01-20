package api.domains.product_lookup;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.lang.reflect.Array;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ProductLookupRequest {

    @JsonProperty("clientId")
    private int clientId;

    @JsonProperty("productId")
    private int productId;

    @JsonProperty("productTypeId")
    private int productTypeId;

    @JsonProperty("includeInactive")
    private boolean includeInactive;

    @JsonProperty("channelId")
    private int channelId;

    @JsonProperty("vendorId")
    private int vendorId;

    @JsonProperty("targetIdentifier")
    private String targetIdentifier;

    @JsonProperty("topSeller")
    private boolean topSeller;

    @JsonProperty("subscriberType")
    private String subscriberType;

    @JsonProperty("topSellerPlatform")
    private String topSellerPlatform;

    @JsonProperty("purchaseMedium")
    private String purchaseMedium;

    @JsonProperty("sortBy")
    private Array sortBy;

}
