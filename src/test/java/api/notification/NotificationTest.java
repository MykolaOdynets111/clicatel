package api.notification;

import api.clients.NotificationClient;
import api.clients.ReserveAndTransactClient;
import api.clients.UsersClient;
import api.enums.Port;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import util.base_test.BaseApiTest;


import static api.clients.NotificationClient.*;
import static api.clients.UsersClient.executeUsers;
import static api.domains.notification.repo.NotificationRequestRepo.setUpNotificationData;
import static api.domains.notification.repo.NotificationRequestRepo.*;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;


public class NotificationTest extends BaseApiTest {

    @Test
    @Description("30116 :: message-notifier :: public internal :: POST /notification :: Notifications API (1.0)")
    @TmsLink("TECH-53236")
    public void testSendNotificationSuccess() {

        //send notification
        val body = setUpNotificationData(NotificationClient.Identifier_6, Integer.parseInt(NotificationClient.SmsID), NotificationClient.NotificationSuccessResponse, Integer.parseInt(ReserveAndTransactClient.TestClient3));

        sendNotification(body,Port.NOTIFICATION)
                .then().assertThat().statusCode(SC_ACCEPTED);

    }

    @Test
    @Description("30299 :: payd-sim-sms :: POST /rest/messageNotifier")
    @TmsLink("TECH-77508")
    public void testRestMessageNotifierSuccess() {

        val body = setUpRestMessageNotifierPostData(NotificationClient.RestMessageNotifiers_to, NotificationClient.RestMessageNotifiers_from, NotificationClient.RestMessageNotifiers_text);

        val response1 = executeMessageNotifier(body)
                .then().assertThat().statusCode(SC_OK)
                .body("data.message.accepted", Matchers.hasItem(true))
                .body("data.message.to", Matchers.hasItem(NotificationClient.RestMessageNotifiers_to))
        .extract().body();

    }

}
