package api.reserve_and_transact;

import api.clients.ReserveAndTransactClient;
import api.clients.TransactClient;
import api.domains.transact.model.TransactResponse;
import api.enums.ChannelId;
import api.enums.Port;
import api.enums.Version;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static api.clients.TransactClient.executeTransact;
import static api.domains.transact.repo.TransactRequestRepo.*;
import static api.enums.ChannelName.USSD;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.apache.http.HttpStatus.SC_OK;

public class ReserveAndTransactCharLimitForRequiredParamTest {

    //V1
    @Test
    @Description("30100 :: payd-raas-gateway :: v1/reserveAndTransact :: null value for required parameters")
    @TmsLink("TECH-93137")
    public void V1testReserveClientIDNull() throws InterruptedException {
        val jsonBody = setUpTransactV1DataWithCIDNull(ReserveAndTransactClient.TestClient3, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.ResponseCode_4000))
                .body("responseMessage", Matchers.containsString(TransactClient.responseMessageServiceTUnavailable))
                .body("raasTxnRef", Matchers.nullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();
    }
}
