package api.clients;

import api.domains.notification.model.NotificationRequest;
import api.enums.Port;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import lombok.Getter;

import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;
import static util.readers.PropertiesReader.getProperty;

@Getter
public class NotificationClient extends BasedAPIClient {
    public static String Identifier_6;
    public static String SmsID;
    public static String NotificationSuccessResponse;

    static {
        Identifier_6 = getProperty("Identifier_6");
        SmsID = getProperty("SmsID");
        NotificationSuccessResponse = getProperty("NotificationSuccessResponse");

    }

    public static Response sendNotification(NotificationRequest body, Port port) {
        return basedAPIClient.get()
                .post(new RequestSpecBuilder()
                        .setUrlEncodingEnabled(false)
                        .setBaseUri(String.format("%s:%d/notification-service/notification",notificationUrl,port.getPort()))
                        .setBody(body)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }
}
