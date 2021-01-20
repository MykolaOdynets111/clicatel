package api.transact;

import api.domains.transact.model.TransactRequest;
import api.enums.Port;
import api.enums.Version;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import util.base_test.BaseApiTest;

import static api.clients.TransactClient.executeTransact;
import static org.apache.http.HttpStatus.SC_OK;
import static util.DateProvider.getCurrentIsoDateTime;

public class TransactTest extends BaseApiTest {

    @Test
    @Description("30100 :: POST v4/transact :: SUCCESS :: Transact API (4.0)")
    @TmsLink("TECH-54338")
    public void testTransactV4Success() {
        val jsonBody = TransactRequest.builder()
                .timestamp(getCurrentIsoDateTime())
                .accountIdentifier("0000XXXX0004")
                .clientTxnRef("1598914290004")
                .channelSessionId("20200831230000004")
                .reserveFundsTxnRef("200831235610408740004")
                .clientId("3")
                .fundingSourceId("3")
                .productId("917")
                .amount("10000")
                .feeAmount("0")
                .currencyCode("NGN")
                .channelId("2")
                .channelName("Internet")
                .sourceIdentifier("2348038382068")
                .targetIdentifier("2348038382068")
                .build();

        executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request"))
                .body("raasTxnRef", Matchers.notNullValue());
    }


    @Test
    @Description("30100 :: POST v3/transact :: SUCCESS")
    @TmsLink("TECH-54339")
    public void testTransactV3Success() {
        val jsonBody = TransactRequest.builder()
                .timestamp(getCurrentIsoDateTime())
                .accountIdentifier(null)
                .reserveFundsTxnRef("90b91ada-df60-4a03-b0c3-c6bbf8381d09-0003")
                .channelSessionId("channelSessionId-0003")
                .clientTxnRef("toxR9F-0003")
                .clientId("3")
                .productId("917")
                .amount("10000")
                .feeAmount("0")
                .channelId("2")
                .channelName("Internet")
                .sourceIdentifier("2348038382068")
                .targetIdentifier("2348038382068")
                .build();

        executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request"))
                .body("raasTxnRef", Matchers.notNullValue());
    }


    @Test
    @Description("30100 :: POST v2/transact :: SUCCESS")
    @TmsLink("TECH-54340")
    public void testTransactV2Success() {
        val jsonBody = TransactRequest.builder()
                .accountIdentifier("222222xxxxxx0003")
                .clientTxnRef("30234241786MgAUs-0002")
                .channelSessionId("d5d65725c1414446b8546c5fcd5-0002")
                .timestamp(getCurrentIsoDateTime())
                .reserveFundsTxnRef("601502164444630590-0002")
                .clientId("3")
                .productId("917")
                .amount("10000")
                .channelId("7")
                .channelName("USSD")
                .sourceIdentifier("2348038382069")
                .targetIdentifier("2348038382069")
                .build();

        executeTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request"))
                .body("raasTxnRef", Matchers.notNullValue());
    }


    @Test
    @Description("30100 :: POST v1/transact :: SUCCESS")
    @TmsLink("TECH-54341")
    public void testTransactV1Success() {
        val jsonBody = TransactRequest.builder()
                .timestamp(getCurrentIsoDateTime())
                .accountIdentifier(null)
                .authCode(null)
                .clientTxnRef("113-2348057670126-15989115-0001")
                .channelSessionId("714890001")
                .reserveFundsTxnRef("601502164444630590-0002")
                .clientId("3")
                .productId("917")
                .amount("10000")
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
    }

}