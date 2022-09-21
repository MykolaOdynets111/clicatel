package api.velocity;

import api.clients.NotificationClient;
import api.clients.ReserveAndTransactClient;
import api.clients.VelocityClients;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static api.clients.NotificationClient.*;
import static api.clients.VelocityClients.executeRaasAndVerifyRecord;
import static api.domains.velocity.repo.VelocityRepo.*;
import static org.apache.http.HttpStatus.SC_OK;

public class RaasVelocity {
    @Test
    @Description("POST /raas/verifyAndRecord :: happy path")
    @TmsLink("MKP-972")
    public void testRaasVerifyRecordHappyPath() {

        val body = setUpRaasVerifyAndRecordData(ReserveAndTransactClient.fundingSourceId_1500, ReserveAndTransactClient.PurchaseAmount1000, VelocityClients.sourceMsisdn, VelocityClients.transactionId);

        val response1 = executeRaasAndVerifyRecord(body)
                .then().assertThat().statusCode(SC_OK)
                .body("type", Matchers.containsString(VelocityClients.success))
                .body("fundingSourceId", Matchers.is(Integer.parseInt(ReserveAndTransactClient.fundingSourceId_1500)))
                .body("purchaseAmount", Matchers.is(Integer.parseInt(ReserveAndTransactClient.PurchaseAmount1000)))
                .body("sourceMsisdn", Matchers.containsString(VelocityClients.sourceMsisdn))
                .extract().body();

    }
}
