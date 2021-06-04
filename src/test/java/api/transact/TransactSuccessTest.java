package api.transact;

import api.domains.transact.model.TransactResponse;
import api.enums.*;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import util.base_test.BaseApiTest;
import java.util.Hashtable;
import java.util.Map;
import static api.clients.TransactClient.executeTransact;
import static api.clients.TransactionLookupClient.findTransaction;
import static api.domains.transact.repo.TransactRequestRepo.*;
import static api.enums.ChannelName.USSD;
import static org.apache.http.HttpStatus.SC_OK;

public class TransactSuccessTest extends BaseApiTest {

    @Test
    @Description("30100 :: payd-raas-gateway :: POST v4/transact :: SUCCESS :: Transact API (4.0)")
    @TmsLink("TECH-54338")
    public void testTransactV4Success() throws InterruptedException {
        val jsonBody = setUpTransactV4Data("3", CurrencyCode.NGN, ChannelName.INTERNET, ChannelId.INTERNET, "917");

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    //Verify transaction status is "SUCCESS"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        Thread.sleep(10000);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, 3, queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString("SUCCESS"));

        //TODO: Verify against support tool API
//        getRaasFlow(Port.RAAS_FLOW, raasTxnRef)
//                .then().assertThat().statusCode(SC_OK)
//            //Verify funds were successfully reserved (response_code equals to 0000)
//                .body("reserve_fund_response.responseCode", Matchers.is("0000"))
//            //AND ctx response code is SUCCESSFUL (0)
//                .body("ctx_response[0].responseCode", Matchers.is(0))
//            //AND successful transaction result is sent (0000)
//                .body("transaction_result_request.responseCode", Matchers.is("0000"))
//            //AND success response code is received from the funding source (202)
//                .body("transaction_result_response.responseCode", Matchers.is("202"))
//            //AND transaction wasn't retried (no records found in the db)
//                .body("ctx_response.clientTransactionId", Matchers.not(raasTxnRef.concat("-0001")))
//            //AND transaction wasn't pending (no records found in the db)
//                .body("ctx_lookup_response.clientTransactionId", Matchers.not(raasTxnRef.concat("-0000")));
    }


    @Test
    @Description("30100 :: payd-raas-gateway :: POST v3/transact :: SUCCESS")
    @TmsLink("TECH-54339")
    public void testTransactV3Success() throws InterruptedException {
        val jsonBody = setUpTransactV3Data("3", ChannelName.INTERNET, ChannelId.INTERNET, "917");

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V3)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    //Verify transaction status is "SUCCESS"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        Thread.sleep(10000);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, 3, queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString("SUCCESS"));

    //TODO: Verify against support tool API
    }


    @Test
    @Description("30100 :: payd-raas-gateway :: POST v2/transact :: SUCCESS")
    @TmsLink("TECH-54340")
    public void testTransactV2Success() throws InterruptedException {
        val jsonBody = setUpTransactV2Data("3", ChannelName.USSD, ChannelId.USSD, "917");

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    //Verify transaction status is "SUCCESS"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        Thread.sleep(10000);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, 3, queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString("SUCCESS"));

        //TODO: Verify against support tool API
   }


    @Test
    @Description("30100 :: payd-raas-gateway :: POST v1/transact SUCCESS")
    @TmsLink("TECH-54341")
    public void testTransactV1Success() throws InterruptedException {
        val jsonBody = setUpTransactV1Data("3", USSD, ChannelId.USSD, "917");

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V1)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("Processing request"))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

    //Verify transaction status is "SUCCESS"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        Thread.sleep(10000);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, 3, queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString("SUCCESS"));

        //TODO: Verify against support tool API

    }

}