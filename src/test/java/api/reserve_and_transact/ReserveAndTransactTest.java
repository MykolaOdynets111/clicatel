package api.reserve_and_transact;
import api.domains.reserve_and_transact.model.ReserveAndTransactRequest;
import api.domains.reserve_and_transact.model.ReserveAndTransactResponse;
import api.domains.transact.model.TransactRequest;
import api.enums.Port;
import api.enums.Version;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import util.base_test.BaseApiTest;

import static api.clients.ReserveAndTransactClient.executeReserveAndTransact;

import static api.clients.TransactClient.executeTransact;
import static db.clients.HibernateBaseClient.executeCustomQueryAndReturnValue;
import static db.custom_queries.ReserveAndTransactQueries.GET_TRANSACTION_STATUS;
import static db.enums.Sessions.POSTGRES_SQL;
import static java.lang.String.format;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;
import static util.DateProvider.getCurrentIsoDateTime;

public class ReserveAndTransactTest extends BaseApiTest {

    @Test
    @Description("30100 :: POST v4/reserveAndTransact :: SUCCESS :: Reserve and Transact API (4.0)")
    @TmsLink("TECH-69504")
    public void testReserveAndTransactV4Success() {
        val jsonBody = ReserveAndTransactRequest.builder()
                .accountIdentifier("4000******0004")
                .authCode(null)
                .clientTxnRef("4ba4c3-3wsf8cf-f0004")
                .channelSessionId("400074970004")
                .timestamp(getCurrentIsoDateTime())
                .clientId("3")
                .fundingSourceId("3")
                .productId("100")
                .purchaseAmount("10000")
                .feeAmount("0")
                .currencyCode("NGN")
                .channelId("7")
                .channelName("USSD")
                .sourceIdentifier("2348038382067")
                .targetIdentifier("2348038382067")
                .build();

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        //raas db checks --- to complete the rest
        val postgres = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(GET_TRANSACTION_STATUS, raasTxnRef));

        assertThat(postgres)
                .as("Postgres SQL query result should not be empty")
                .contains("SUCCESS");
    }


    @Test
    @Description("30100 :: POST v3/reserveAndTransact :: SUCCESS")
    @TmsLink("TECH-69480")
    public void testReserveAndTransactV3Success() {
        val jsonBody = ReserveAndTransactRequest.builder()
                .accountIdentifier("000XXX0311-0003")
                .authCode(null)
                .clientTxnRef("010002441811llim-0003")
                .channelSessionId("714890809-0003")
                .timestamp(getCurrentIsoDateTime()) // pattern "timestamp":"2020-04-04T04:04:04.000+02:00"
                .clientId("3")
                .productId("917")
                .purchaseAmount("10000")
                .feeAmount("0")
                .channelId("3")
                .channelName("Mobile")
                .sourceIdentifier("2348038382068")
                .targetIdentifier("2348038382068")
                .build();


        executeReserveAndTransact(jsonBody,Port.TRANSACTIONS,Version.V3)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue());

//TO DO: raas db checks
    }

    @Test
    @Description("30100 :: POST v2/reserveAndTransact :: SUCCESS")
    @TmsLink("TECH-69490")
    public void testReserveAndTransactV2Success() {
        val jsonBody = ReserveAndTransactRequest.builder()
                .accountIdentifier("222222xxxxxx0002")
                .clientTxnRef("30234241786MgAUs-0002")
                .channelSessionId("d5d65725c1414446b8546c5fcd5-0002")
                .timestamp(getCurrentIsoDateTime()) // pattern "timestamp":"2020-04-04T04:04:04.000+02:00"
                .clientId("3")
                .productId("917")
                .amount("10000")
                .channelId("7")
                .channelName("USSD")
                .sourceIdentifier("2348038382069")
                .targetIdentifier("2348038382069")
                .build();


        executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue());

//TO DO: raas db checks
    }

    @Test
    @Description("30100 :: POST v1/reserveAndTransact :: SUCCESS")
    @TmsLink("TECH-69508")
    public void testReserveAndTransactV1Success() {
        val jsonBody = TransactRequest.builder()
                .accountIdentifier(null)
                .authCode(null)
                .clientTxnRef("113-2348057670126-15989115-0001")
                .channelSessionId("714890001")
                .timestamp(getCurrentIsoDateTime())
                .clientId("3")
                .productId("924")
                .amount("20000")
                .channelId("7")
                .channelName("USSD")
                .sourceIdentifier("2348038382068")
                .targetIdentifier("2348038382068")
                .build();


        executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request"))
                .body("raasTxnRef", Matchers.notNullValue());

        //TO DO: raas db checks

    }
}