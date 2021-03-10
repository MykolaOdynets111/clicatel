package api.domains.notification.repo;

import api.domains.notification.model.NotificationRequest;

public class NotificationRequestRepo {

    public static NotificationRequest setUpNotificationData(String identifier, int channelId,
                                                            String message, int clientId){
        return NotificationRequest.builder()
                .destinationIdentifier(identifier)
                .channelId(channelId)
                .message(message)
                .transactionRef("string")
                .clientNotificationRef("string")
                .clientId(clientId)
                .sourceIdentifier(identifier)
                .channelRouteId("string")
                .build();
    }
}
