package api.transact;

import api.domains.transact.model.TransactResponse;
import api.enums.*;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import util.base_test.BaseApiTest;

import static api.clients.TransactClient.executeTransact;
import static api.controls.TransactControl.getTransactionStatus;
import static api.domains.transact.repo.TransactRequestRepo.*;
import static api.enums.ChannelName.USSD;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;
import static util.Waits.waitForSystemLoading;

public class TransactSuccessTest extends BaseApiTest {

    @Test
    @Description("30100 :: payd-raas-gateway :: POST v4/transact :: SUCCESS :: Transact API (4.0)")
    @TmsLink("TECH-54338")
    public void testTransactV4Success() {
        val jsonBody = setUpTransactV4Data("3", CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, "917");

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

        //Verify transaction status is "SUCCESS"
//        assertThat(getTransactionStatus(raasTxnRef))
//                .as("Postgres SQL query : Transaction Status incorrect")
//                .isTrue();
//        waitForSystemLoading(30);

        //TODO: Verify against support tool API
    }


    @Test
    @Description("30100 :: payd-raas-gateway :: POST v3/transact :: SUCCESS")
    @TmsLink("TECH-54339")
    public void testTransactV3Success() {
        val jsonBody = setUpTransactV3Data("3", ChannelName.INTERNET, ChannelId.INTERNET, "917");

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

        //Verify transaction status is "SUCCESS"
//        assertThat(getTransactionStatus(raasTxnRef))
//                .as("Postgres SQL query : Transaction Status incorrect")
//                .isTrue();
//        waitForSystemLoading(30);
        //TODO: Verify against support tool API
    }


    @Test
    @Description("30100 :: payd-raas-gateway :: POST v2/transact :: SUCCESS")
    @TmsLink("TECH-54340")
    public void testTransactV2Success() {
        val jsonBody = setUpTransactV2Data("3", ChannelName.USSD, ChannelId.USSD, "917");

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

        //Verify transaction status is "SUCCESS"
//        assertThat(getTransactionStatus(raasTxnRef))
//                .as("Postgres SQL query : Transaction Status incorrect")
//                .isTrue();
//        waitForSystemLoading(30);

        //TODO: Verify against support tool API
    }


    @Test
    @Description("30100 :: payd-raas-gateway :: POST v1/transact SUCCESS")
    @TmsLink("TECH-54341")
    public void testTransactV1Success() {
        val jsonBody = setUpTransactV1Data("3", USSD, ChannelId.USSD, "917");

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

        //Verify transaction status is "SUCCESS"
//        assertThat(getTransactionStatus(raasTxnRef))
//                .as("Postgres SQL query : Transaction Status incorrect")
//                .isTrue();
//        waitForSystemLoading(30);

        //TODO: raas db checks or verify against support tool API

    }

}