package api.reserve_and_transact;

import api.clients.ReserveAndTransactClient;
import api.domains.reserve_and_transact.model.ReserveAndTransactResponse;
import api.enums.ChannelId;
import api.enums.Port;
import api.enums.Version;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import static api.clients.ReserveAndTransactClient.*;
import static api.domains.reserve_and_transact.repo.ReserveAndTransactRequestRepo.setUpReserveAndTransactV4DataWhenProductNotLinkedWithClient;
import static api.enums.ChannelName.USSD;
import static api.enums.CurrencyCode.NGN;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;

public class ReserveAndTransactProductNotLinkedWithClientTest {
    @Test
    @Description("30100 :: payd-raas-gateway :: 4000 error when product isn't linked to the client\t\n")
    @TmsLink("MKP-930")
    public void testReserveAndTransactV4SuccessWhenProductIsNotLinked() throws InterruptedException {
        val jsonBody = setUpReserveAndTransactV4DataWhenProductNotLinkedWithClient(ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.Product_1585, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.IdentifierV2);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessagePIDNotFound))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
    }
}
