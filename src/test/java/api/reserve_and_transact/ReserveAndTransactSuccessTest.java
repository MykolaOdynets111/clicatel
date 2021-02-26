package api.reserve_and_transact;

import api.domains.reserve_and_transact.model.ReserveAndTransactResponse;
import api.domains.transact.model.TransactResponse;
import api.enums.ChannelId;
import api.enums.Port;
import api.enums.Version;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import util.base_test.BaseApiTest;

import static api.clients.ReserveAndTransactClient.executeReserveAndTransact;
import static api.clients.SupportUiClient.getRaasFlow;
import static api.clients.TransactClient.executeTransact;
import static api.controls.TransactControl.getTransactionStatus;
import static api.domains.reserve_and_transact.repo.ReserveAndTransactRequestRepo.*;
import static api.domains.transact.repo.TransactRequestRepo.setUpTransactV1Data;
import static api.enums.ChannelName.MOBILE;
import static api.enums.ChannelName.USSD;
import static api.enums.CurrencyCode.NGN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

public class ReserveAndTransactSuccessTest extends BaseApiTest {

    //SUCCESS :: v1-v4
    @Test
    @Description("30100 :: payd-raas-gateway :: POST v4/reserveAndTransact :: SUCCESS :: Reserve and Transact API (4.0)")
    @TmsLink("TECH-68538")
    public void testReserveAndTransactV4Success() {
        val jsonBody = setUpReserveAndTransactV4Data("3", NGN, USSD, ChannelId.USSD, "100", "10000", "0", "2348038382067");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        //raas db check --- Verify transaction status is "SUCCESS"
        assertThat(getTransactionStatus(raasTxnRef))
                .as("Postgres SQL query : Transaction Status incorrect")
                .isTrue();

        //Verify against support tool API
        getRaasFlow(Port.RAAS_FLOW, raasTxnRef)
                .then().assertThat().statusCode(SC_OK)
            //Verify funds were successfully reserved (response_code equals to 0000)
                .body("reserve_fund_response.responseCode", Matchers.is("0000"))
            //AND ctx response code is SUCCESSFUL (0)
                .body("ctx_response[0].responseCode", Matchers.is(0))
            //AND successful transaction result is sent (0000)
                .body("transaction_result_request.responseCode", Matchers.is("0000"))
            //AND success response code is received from the funding source (202)
                .body("transaction_result_response.responseCode", Matchers.is("202"))
            //AND transaction wasn't retried (no records found in the db)
                .body("ctx_response.clientTransactionId", Matchers.not(raasTxnRef.concat("-0001")))
            //AND transaction wasn't pending (no records found in the db)
                .body("ctx_lookup_response.clientTransactionId", Matchers.not(raasTxnRef.concat("-0000")));

    }


    @Test
    @Description("30100 :: payd-raas-gateway :: POST v3/reserveAndTransact :: SUCCESS")
    @TmsLink("TECH-54334")
    public void testReserveAndTransactV3Success() {
        val jsonBody = setUpReserveAndTransactV3Data("3", MOBILE, ChannelId.MOBILE, "917");

        val raasTxnRef = executeReserveAndTransact(jsonBody,Port.TRANSACTIONS,Version.V3)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        //raas db check --- Verify transaction status is "SUCCESS"
        assertThat(getTransactionStatus(raasTxnRef))
                .as("Postgres SQL query : Transaction Status incorrect")
                .isTrue();

        //Verify against support tool API
        getRaasFlow(Port.RAAS_FLOW, raasTxnRef)
                .then().assertThat().statusCode(SC_OK)
            //Verify funds were successfully reserved (response_code equals to 0000)
                .body("reserve_fund_response.responseCode", Matchers.is("0000"))
            //AND ctx response code is SUCCESSFUL (0)
                .body("ctx_response[0].responseCode", Matchers.is(0))
            //AND successful transaction result is sent (0000)
                .body("transaction_result_request.responseCode", Matchers.is("0000"))
            //AND success response code is received from the funding source (202)
                .body("transaction_result_response.responseCode", Matchers.is("202"))
            //AND transaction wasn't retried (no records found in the db)
                .body("ctx_response.clientTransactionId", Matchers.not(raasTxnRef.concat("-0001")))
            //AND transaction wasn't pending (no records found in the db)
                .body("ctx_lookup_response.clientTransactionId", Matchers.not(raasTxnRef.concat("-0000")));

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: POST v2/reserveAndTransact :: SUCCESS")
    @TmsLink("TECH-54335")
    public void testReserveAndTransactV2Success() {
        val jsonBody = setUpReserveAndTransactV2Data("3", USSD, ChannelId.USSD, "917");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        //raas db check --- Verify transaction status is "SUCCESS"
        assertThat(getTransactionStatus(raasTxnRef))
                .as("Postgres SQL query : Transaction Status incorrect")
                .isTrue();

        //Verify against support tool API
        getRaasFlow(Port.RAAS_FLOW, raasTxnRef)
                .then().assertThat().statusCode(SC_OK)
            //Verify funds were successfully reserved (response_code equals to 0000)
                .body("reserve_fund_response.responseCode", Matchers.is("0000"))
            //AND ctx response code is SUCCESSFUL (0)
                .body("ctx_response[0].responseCode", Matchers.is(0))
            //AND successful transaction result is sent (0000)
                .body("transaction_result_request.responseCode", Matchers.is("0000"))
            //AND success response code is received from the funding source (202)
                .body("transaction_result_response.responseCode", Matchers.is("202"))
            //AND transaction wasn't retried (no records found in the db)
                .body("ctx_response.clientTransactionId", Matchers.not(raasTxnRef.concat("-0001")))
            //AND transaction wasn't pending (no records found in the db)
                .body("ctx_lookup_response.clientTransactionId", Matchers.not(raasTxnRef.concat("-0000")));

    }

    @Test
    @Description("30100 :: payd-raas-gateway :: POST v1/reserveAndTransact :: SUCCESS")
    @TmsLink("TECH-58612")
    public void testReserveAndTransactV1Success() {
        val jsonBody = setUpTransactV1Data("3", USSD, ChannelId.USSD, "924");

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

        //raas db check --- Verify transaction status is "SUCCESS"
        assertThat(getTransactionStatus(raasTxnRef))
                .as("Postgres SQL query : Transaction Status incorrect")
                .isTrue();

        //Verify against support tool API
        getRaasFlow(Port.RAAS_FLOW, "blwdeowtsedqkeflgyckdn76")
                .then().assertThat().statusCode(SC_OK)
            //Verify funds were successfully reserved (response_code equals to 0000)
                .body("reserve_fund_response.responseCode", Matchers.is("0000"))
            //AND ctx response code is SUCCESSFUL (0)
                .body("ctx_response[0].responseCode", Matchers.is(0))
            //AND successful transaction result is sent (0000)
                .body("transaction_result_request.responseCode", Matchers.is("0000"))
            //AND success response code is received from the funding source (202)
                .body("transaction_result_response.responseCode", Matchers.is("202"))
            //AND transaction wasn't retried (no records found in the db)
                .body("ctx_response.clientTransactionId", Matchers.not("blwdeowtsedqkeflgyckdn76".concat("-0001")))
            //AND transaction wasn't pending (no records found in the db)
                .body("ctx_lookup_response.clientTransactionId", Matchers.not("blwdeowtsedqkeflgyckdn76".concat("-0000")));

    }

}