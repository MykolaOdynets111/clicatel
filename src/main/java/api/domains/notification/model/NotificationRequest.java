package api.domains.notification.model;

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

public class NotificationRequest {
    @JsonProperty("destinationIdentifier")
    private String destinationIdentifier;

    @JsonProperty("channelId")
    private int channelId;

    @JsonProperty("message")
    private String message;

    @JsonProperty("transactionRef")
    private String transactionRef;

    @JsonProperty("clientNotificationRef")
    private String clientNotificationRef;

    @JsonProperty("clientId")
    private int clientId;

    @JsonProperty("sourceIdentifier")
    private String sourceIdentifier;

    @JsonProperty("channelRouteId")
    private String channelRouteId;

}
