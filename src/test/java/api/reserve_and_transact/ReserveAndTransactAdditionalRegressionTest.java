package api.reserve_and_transact;

import api.domains.reserve_and_transact.model.ReserveAndTransactResponse;
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
import static api.clients.ReserveAndTransactClient.executeReserveAndTransactWithSignature;
import static api.domains.reserve_and_transact.repo.ReserveAndTransactRequestRepo.*;
import static api.enums.ChannelName.MOBILE;
import static api.enums.ChannelName.USSD;
import static api.enums.CurrencyCode.NGN;
import static db.clients.HibernateBaseClient.executeCustomQueryAndReturnValue;
import static db.custom_queries.ReserveAndTransactQueries.GET_TRANSACTION_STATUS;
import static db.enums.Sessions.POSTGRES_SQL;
import static java.lang.String.format;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

public class ReserveAndTransactAdditionalRegressionTest extends BaseApiTest {

    //ADDITIONAL REGRESSION CHECKS

    @Test
    @Description("30100 :: vendor103 (Airtel) :: \"Data\" purchase :: SUCCESS")
    @TmsLink("TECH-57989")
    public void testReserveAndTransactVendor103AirtelDataSuccess() {
        val jsonBody = setUpReserveAndTransactV4Data("3", NGN, USSD, ChannelId.USSD, "189", "9900", "0", "2348038382067");

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        //raas db check --- transaction status is "SUCCESS"
        val status = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(GET_TRANSACTION_STATUS, raasTxnRef));
        assertThat(status)
                .as("Postgres SQL query result should not be empty")
                .contains("SUCCESS");
    }


    @Test
    @Description("30100 :: client 3 (test client) :: SUCCESS :: Purchase with valid payload and signature should be successful")
    @TmsLink("TECH-56890")
    public void testReserveAndTransactWithSignatureSuccess() {
        //UAT: client 1003 (secret value Ajd7dsJD1), funding source 1003
        val jsonBody = setUpReserveAndTransactSignatureData("1003", MOBILE, ChannelId.MOBILE, "2348038382068");

//        val raasTxnRef = executeReserveAndTransactWithSignature(jsonBody, Port.TRANSACTIONS, Version.V4, "pN85n+uUSa6GpRNPOwODKgWw63aE7Q8fwEb8QRUux5A=")
//                .then().assertThat().statusCode(SC_OK)
//                .body("responseCode", Matchers.containsString("0000"))
//                .body("responseMessage", Matchers.containsString("Processing request (funds reserved)"))
//                .body("raasTxnRef", Matchers.notNullValue())
//                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        //raas db check --- transaction status is "SUCCESS"
//        val status = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(GET_TRANSACTION_STATUS, raasTxnRef));
//        assertThat(status)
//                .as("Postgres SQL query result should not be empty")
//                .contains("SUCCESS");
    }

}