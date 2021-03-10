package api.notification;

import api.enums.Port;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.testng.annotations.Test;
import util.base_test.BaseApiTest;


import static api.clients.NotificationClient.sendNotification;
import static api.domains.notification.repo.NotificationRequestRepo.setUpNotificationData;
import static org.apache.http.HttpStatus.SC_ACCEPTED;


public class NotificationTest extends BaseApiTest {

    @Test
    @Description("30116 :: message-notifier :: public internal :: POST /notification :: Notifications API (1.0)")
    @TmsLink("TECH-53236")
    public void testSendNotificationSuccess() {

        //send notification
        val body = setUpNotificationData("2341000000000", 6, "This is a test", 3);

        sendNotification(body,Port.NOTIFICATION)
                .then().assertThat().statusCode(SC_ACCEPTED);

    }

}
