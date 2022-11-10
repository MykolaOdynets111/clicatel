package api.clients;

import api.domains.notification.model.NotificationRequest;
import api.enums.Port;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import lombok.Getter;

import java.util.Map;

import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;
import static util.readers.PropertiesReader.getProperty;

@Getter
public class NotificationClient extends BasedAPIClient {
    public static String Identifier_6;
    public static String SmsID;
    public static String NotificationSuccessResponse;
    public static String RestMessageNotifiers_to;
    public static String RestMessageNotifiers_from;
    public static String RestMessageNotifiers_text;

    static {
        Identifier_6 = getProperty("Identifier_6");
        SmsID = getProperty("SmsID");
        NotificationSuccessResponse = getProperty("NotificationSuccessResponse");
        RestMessageNotifiers_to = getProperty("RestMessageNotifiers_to");
        RestMessageNotifiers_from = getProperty("RestMessageNotifiers_from");
        RestMessageNotifiers_text = getProperty("RestMessageNotifiers_text");

    }

    public static Response sendNotification(NotificationRequest body, Port port) {
        return basedAPIClient.get()
                .post(new RequestSpecBuilder()
                        .setUrlEncodingEnabled(false)
                        .setBaseUri(String.format("%s/notification-service/notification",notificationUrl))
                        .setBody(body)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }
    public static Response executeMessageNotifier(Map body) {
        return basedAPIClient.get()
                .post(new RequestSpecBuilder()
                        .setUrlEncodingEnabled(false)
                        .setBaseUri(String.format("%s/rest/messageNotifier",RestMessageNotifier))
                        .setBody(body)
                        .setContentType(JSON)
                        .addHeader("Authorization","bearer")
                        .log(ALL)
                        .build());
    }
}
