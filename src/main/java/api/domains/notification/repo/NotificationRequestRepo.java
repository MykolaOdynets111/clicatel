package api.domains.notification.repo;

import api.domains.notification.model.NotificationRequest;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

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
    public static Map<String, Object> setUpRestMessageNotifierPostData(String to, String from,
                                                         String text) {

        Map<String,Object> jsonObjectPayload = new LinkedHashMap<>();
        jsonObjectPayload.put("to", Arrays.asList(to));
        jsonObjectPayload.put("from", from);
        jsonObjectPayload.put("text", text);

        return jsonObjectPayload;
    }
}
