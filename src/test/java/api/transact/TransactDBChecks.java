package api.transact;

import api.clients.ReserveAndTransactClient;
import api.domains.reserve_and_transact.model.ReserveAndTransactResponse;
import api.domains.transact.model.TransactResponse;
import api.enums.*;
import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import util.base_test.BaseApiTest;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import static api.clients.FinancialTermsLookupClient.getFinancialTermsCalculate;
import static api.clients.FinancialTermsLookupClient.value_0point1;
import static api.clients.ReserveAndTransactClient.*;
import static api.clients.ReserveAndTransactDBChecksClient.*;
import static api.clients.TransactClient.executeTransact;
import static api.clients.TransactDBChecksClient.*;
import static api.clients.TransactionLookupClient.findTransaction;
import static api.clients.VendorManagementClient.Vendor21;
import static api.domains.reserve_and_transact.repo.ReserveAndTransactRequestRepo.setUpReserveAndTransactV4DataAdditionalDataWithProductFundSourceClientID;
import static api.domains.transact.repo.TransactRequestRepo.setUpTransactV4Data;
import static api.domains.transact.repo.TransactRequestRepo.*;
import static api.enums.ChannelName.USSD;
import static api.enums.CurrencyCode.NGN;
import static db.clients.HibernateBaseClient.executeCustomQuery;
import static db.clients.HibernateBaseClient.executeCustomQueryAndReturnValues;
import static db.custom_queries.FinancialTermsQueries.*;
import static db.custom_queries.ReserveAndTransactQueries.*;
import static db.custom_queries.ReserveAndTransactQueries.SELECT_TRANSACT_RESULT_RESPONSE_CREATED_BY_RAAS_TXN_REF;
import static db.enums.Sessions.POSTGRES_SQL;
import static java.lang.String.format;
import static java.time.LocalDateTime.now;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

public class TransactDBChecks extends BaseApiTest {
    @Test()
    @Description("30100-payd-raas-gateway :: POST v4/transact :: raas.transaction_log all fields")
    @TmsLink("TECH-174330")
    public void testTransactV4RaasTransactionLogAllFields() throws InterruptedException {
        //Failing because of https://jira.clickatell.com/browse/TECH-176071
//        executeCustomQuery(POSTGRES_SQL, format(UPDATE_VENDOR_DISCOUNT_PERCENTAGE_BY_VENDOR_ID, value_0point1,Vendor21));
//        executeCustomQuery(POSTGRES_SQL, format(UPDATE_MODEL_SELECTION, Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2, Vendor21, TestClient3, ProductAirtel_917));
//        executeCustomQuery(POSTGRES_SQL, format(UPDATE_TPV_CLIENT_SHARE, value_0point1, TestClient3, ProductAirtel_917));
        val jsonBody = setUpTransactV4DataWithAdditionalDataWithProductFundSourceClientID(fundingSourceId_1500,AccountIdentifier,clientTxnRef,fundingSourceId,channelSessionId,TestClient3, CurrencyCode.NGN, USSD, ChannelId.USSD,ProductAirtel_917, PurchaseAmount10000, FeeAmount0,Identifier,ReserveFundsTxnRef);

        val raasTxnRef = executeTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageProcessingRequest))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(TransactResponse.class).getRaasTxnRef();

        //Verify transaction status is "SUCCESS"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        Thread.sleep(10000);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, Integer.parseInt(ReserveAndTransactClient.TestClient3), queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString(ReserveAndTransactClient.Success));

//        val SELECT_ACCOUNT_IDENTIFIER_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_ACCOUNT_IDENTIFIER_BY_RAAS_TXN_REF,raasTxnRef));
//        System.out.println(SELECT_ACCOUNT_IDENTIFIER_BY_RAAS_TXN_REF_VALUE);
//        assertThat(SELECT_ACCOUNT_IDENTIFIER_BY_RAAS_TXN_REF_VALUE)
//                .contains(AccountIdentifier);
//        val SELECT_AMOUNT_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_AMOUNT_BY_RAAS_TXN_REF,raasTxnRef));
//        System.out.println(SELECT_AMOUNT_BY_RAAS_TXN_REF_VALUE);
//        List<String> SELECT_AMOUNT_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_AMOUNT_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
//        assertThat(SELECT_AMOUNT_BY_RAAS_TXN_REF_VALUE_STRING.get(0))
//                .contains(PurchaseAmount10000);
//        val SELECT_CHANNEL_ID_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CHANNEL_ID_BY_RAAS_TXN_REF,raasTxnRef));
//        List<String> SELECT_CHANNEL_ID_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_CHANNEL_ID_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
//        assertThat(SELECT_CHANNEL_ID_BY_RAAS_TXN_REF_VALUE_STRING)
//                .contains("7");
//        val SELECT_CHANNEL_SESSION_ID_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CHANNEL_SESSION_ID_BY_RAAS_TXN_REF,raasTxnRef));
//        List<String> SELECT_CHANNEL_SESSION_ID_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_CHANNEL_SESSION_ID_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
//        System.out.println(SELECT_CHANNEL_SESSION_ID_BY_RAAS_TXN_REF_VALUE_STRING);
//        assertThat(SELECT_CHANNEL_SESSION_ID_BY_RAAS_TXN_REF_VALUE_STRING)
//                .contains(channelSessionId);
//        val SELECT_CLIENT_ID_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CLIENT_ID_BY_RAAS_TXN_REF,raasTxnRef));
//        List<String> SELECT_CLIENT_ID_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_CLIENT_ID_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
//        System.out.println(SELECT_CLIENT_ID_BY_RAAS_TXN_REF_VALUE_STRING);
//        assertThat(SELECT_CLIENT_ID_BY_RAAS_TXN_REF_VALUE_STRING)
//                .contains(TestClient3);
//        val SELECT_CLIENT_TXN_REF_ID_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CLIENT_TXN_REF_ID_BY_RAAS_TXN_REF,raasTxnRef));
//        System.out.println(SELECT_CLIENT_TXN_REF_ID_BY_RAAS_TXN_REF_VALUE);
//        List<String> SELECT_CLIENT_TXN_REF_ID_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_CLIENT_TXN_REF_ID_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
//        assertThat(SELECT_CLIENT_TXN_REF_ID_BY_RAAS_TXN_REF_VALUE_STRING)
//                .contains(clientTxnRef);
//        val SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF,raasTxnRef));
//        System.out.println(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_VALUE);
//        List<String> SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
//        assertThat(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_VALUE_STRING)
//                .contains(Event_Type);
//        val SELECT_PRODUCT_ID_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_PRODUCT_ID_BY_RAAS_TXN_REF,raasTxnRef));
//        System.out.println(SELECT_PRODUCT_ID_BY_RAAS_TXN_REF_VALUE);
//        List<String> SELECT_PRODUCT_ID_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_PRODUCT_ID_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
//        assertThat(SELECT_PRODUCT_ID_BY_RAAS_TXN_REF_VALUE_STRING)
//                .contains(ProductAirtel_917);
//        val SELECT_SOURCE_IDENTIFIER_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_SOURCE_IDENTIFIER_BY_RAAS_TXN_REF,raasTxnRef));
//        System.out.println(SELECT_SOURCE_IDENTIFIER_BY_RAAS_TXN_REF_VALUE);
//        List<String> SELECT_SOURCE_IDENTIFIER_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_SOURCE_IDENTIFIER_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
//        assertThat(SELECT_SOURCE_IDENTIFIER_BY_RAAS_TXN_REF_VALUE_STRING)
//                .contains(Identifier);
//        val SELECT_TARGET_IDENTIFIER_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_TARGET_IDENTIFIER_BY_RAAS_TXN_REF,raasTxnRef));
//        System.out.println(SELECT_TARGET_IDENTIFIER_BY_RAAS_TXN_REF_VALUE);
//        List<String> SELECT_TARGET_IDENTIFIER_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_TARGET_IDENTIFIER_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
//        assertThat(SELECT_TARGET_IDENTIFIER_BY_RAAS_TXN_REF_VALUE_STRING)
//                .contains(Identifier);
//        val SELECT_RESERVE_FUNDS_TXN_REF_BY_RAAS_TXN_REF_FROM_TXN_LOGS_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_RESERVE_FUNDS_TXN_REF_BY_RAAS_TXN_REF_FROM_TXN_LOGS,raasTxnRef));
//        System.out.println(SELECT_RESERVE_FUNDS_TXN_REF_BY_RAAS_TXN_REF_FROM_TXN_LOGS_VALUE);
//        List<String> SELECT_RESERVE_FUNDS_TXN_REF_BY_RAAS_TXN_REF_FROM_TXN_LOGS_VALUE_STRING = Lists.transform(SELECT_RESERVE_FUNDS_TXN_REF_BY_RAAS_TXN_REF_FROM_TXN_LOGS_VALUE, Functions.toStringFunction());
//        assertThat(SELECT_RESERVE_FUNDS_TXN_REF_BY_RAAS_TXN_REF_FROM_TXN_LOGS_VALUE_STRING)
//                .contains(ReserveFundsTxnRef);
//        val SELECT_CHANNEL_NAME_BY_ID_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CHANNEL_NAME_BY_ID,"7"));
//        System.out.println(SELECT_CHANNEL_NAME_BY_ID_VALUE);
//        List<String> SELECT_CHANNEL_NAME_BY_ID_VALUE_STRING = Lists.transform(SELECT_CHANNEL_NAME_BY_ID_VALUE, Functions.toStringFunction());
//        assertThat(SELECT_CHANNEL_NAME_BY_ID_VALUE_STRING)
//                .contains(String.valueOf(USSD));
//        val SELECT_FEE_AMOUNT_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_FEE_AMOUNT_BY_RAAS_TXN_REF,raasTxnRef));
//        System.out.println(SELECT_FEE_AMOUNT_BY_RAAS_TXN_REF_VALUE);
//        List<String> SELECT_FEE_AMOUNT_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_FEE_AMOUNT_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
//        assertThat(SELECT_FEE_AMOUNT_BY_RAAS_TXN_REF_VALUE_STRING)
//                .contains(FeeAmount0);
//        val SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF,raasTxnRef));
//        System.out.println(SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_VALUE);
//        List<String> SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
//        assertThat(SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_VALUE_STRING)
//                .contains(responseCode0000);
//        val SELECT_RESPONSE_MESSAGE_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_RESPONSE_MESSAGE_BY_RAAS_TXN_REF,raasTxnRef));
//        System.out.println(SELECT_RESPONSE_MESSAGE_BY_RAAS_TXN_REF_VALUE);
//        List<String> SELECT_RESPONSE_MESSAGE_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_RESPONSE_MESSAGE_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
//        assertThat(SELECT_RESPONSE_MESSAGE_BY_RAAS_TXN_REF_VALUE_STRING)
//                .contains(responseMessageFundsReserved);
//        val SELECT_RESERVE_FUNDS_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_TXN_LOGS_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_RESERVE_FUNDS_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_TXN_LOGS,raasTxnRef));
//        System.out.println(SELECT_RESERVE_FUNDS_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_TXN_LOGS_VALUE);
//        List<String> SELECT_RESERVE_FUNDS_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_TXN_LOGS_VALUE_STRING = Lists.transform(SELECT_RESERVE_FUNDS_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_TXN_LOGS_VALUE, Functions.toStringFunction());
//        assertThat(SELECT_RESERVE_FUNDS_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_TXN_LOGS_VALUE)
//                .containsNull();
//        val SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ,raasTxnRef));
//        System.out.println(SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_VALUE);
//        List<String> SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_VALUE_STRING = Lists.transform(SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_VALUE, Functions.toStringFunction());
//        assertThat(SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_VALUE_STRING)
//                .contains(responseCode0000);
//        val SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES,raasTxnRef));
//        System.out.println(SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES_VALUE);
//        List<String> SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES_VALUE_STRING = Lists.transform(SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES_VALUE, Functions.toStringFunction());
//        assertThat(SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES_VALUE_STRING)
//                .contains(responseCode202);
//        val SELECT_STATUS_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_STATUS_BY_RAAS_TXN_REF,raasTxnRef));
//        System.out.println(SELECT_STATUS_BY_RAAS_TXN_REF_VALUE);
//        List<String> SELECT_STATUS_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_STATUS_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
//        assertThat(SELECT_STATUS_BY_RAAS_TXN_REF_VALUE_STRING)
//                .contains(Success);
//        val SELECT_ADD_DATA_CLIENT_ID_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_ADD_DATA_CLIENT_ID_BY_RAAS_TXN_REF,raasTxnRef));
//        System.out.println(SELECT_ADD_DATA_CLIENT_ID_BY_RAAS_TXN_REF_VALUE);
//        List<String> SELECT_ADD_DATA_CLIENT_ID_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_ADD_DATA_CLIENT_ID_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
//        System.out.println(SELECT_ADD_DATA_CLIENT_ID_BY_RAAS_TXN_REF_VALUE_STRING);
//        assertThat(SELECT_ADD_DATA_CLIENT_ID_BY_RAAS_TXN_REF_VALUE_STRING)
//                .contains("{\"clientId\":\"3\"}");
//        val SELECT_ADD_DATA_FUN_SOURCE_ID_ID_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_ADD_DATA_FUN_SOURCE_ID_ID_BY_RAAS_TXN_REF,raasTxnRef));
//        System.out.println(SELECT_ADD_DATA_FUN_SOURCE_ID_ID_BY_RAAS_TXN_REF_VALUE);
//        List<String> SELECT_ADD_DATA_FUN_SOURCE_ID_ID_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_ADD_DATA_FUN_SOURCE_ID_ID_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
//        assertThat(SELECT_ADD_DATA_FUN_SOURCE_ID_ID_BY_RAAS_TXN_REF_VALUE_STRING)
//                .contains("{\"fundingSourceId\":\"3\"}");
//        val SELECT_ADD_DATA_PROD_ID_ID_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_ADD_DATA_PROD_ID_ID_BY_RAAS_TXN_REF,raasTxnRef));
//        System.out.println(SELECT_ADD_DATA_PROD_ID_ID_BY_RAAS_TXN_REF_VALUE);
//        List<String> SELECT_ADD_DATA_PROD_ID_ID_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_ADD_DATA_PROD_ID_ID_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
//        assertThat(SELECT_ADD_DATA_PROD_ID_ID_BY_RAAS_TXN_REF_VALUE_STRING)
//                .contains("{\"productId\":\"917\"}");
//        val SELECT_CURRENCY_CODE_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CURRENCY_CODE_BY_RAAS_TXN_REF,raasTxnRef));
//        System.out.println(SELECT_CURRENCY_CODE_BY_RAAS_TXN_REF_VALUE);
//        List<String> SELECT_CURRENCY_CODE_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_CURRENCY_CODE_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
//        assertThat(SELECT_CURRENCY_CODE_BY_RAAS_TXN_REF_VALUE_STRING)
//                .contains(String.valueOf(NGN));
//        val SELECT_FUNDING_SOURCE_ID_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_FUNDING_SOURCE_ID_BY_RAAS_TXN_REF,raasTxnRef));
//        System.out.println(SELECT_FUNDING_SOURCE_ID_BY_RAAS_TXN_REF_VALUE);
//        List<String> SELECT_FUNDING_SOURCE_ID_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_FUNDING_SOURCE_ID_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
//        assertThat(SELECT_FUNDING_SOURCE_ID_BY_RAAS_TXN_REF_VALUE_STRING)
//                .contains(fundingSourceId);
//        val SELECT_CREATED_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CREATED_BY_RAAS_TXN_REF,raasTxnRef));
//        String SELECT_CREATED_BY_RAAS_TXN_REF_VALUE_STRING = SELECT_CREATED_BY_RAAS_TXN_REF_VALUE.toString();
//        System.out.println(SELECT_CREATED_BY_RAAS_TXN_REF_VALUE_STRING);
//        val SELECT_CREATED_BY_RAAS_TXN_REF_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
//        System.out.println(SELECT_CREATED_BY_RAAS_TXN_REF_TIMESTAMP_STRING);
//        assertThat(SELECT_CREATED_BY_RAAS_TXN_REF_VALUE_STRING)
//                .contains(SELECT_CREATED_BY_RAAS_TXN_REF_TIMESTAMP_STRING);
//        val SELECT_TIMESTAMP_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_TIMESTAMP_BY_RAAS_TXN_REF,raasTxnRef));
//        String TimeStamp = SELECT_TIMESTAMP_BY_RAAS_TXN_REF_VALUE.toString();
//        System.out.println(TimeStamp);
//        val timestamp_string = now().format(ofPattern("yyyy-MM-dd HH"));
//        System.out.println(timestamp_string);
//        assertThat(TimeStamp)
//                .contains(timestamp_string);
//        val SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF,raasTxnRef));
//        String SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_VALUE_STRING = SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_VALUE.toString();
//        System.out.println(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_VALUE_STRING);
//        val SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_VALUE_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
//        System.out.println(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_VALUE_TIMESTAMP_STRING);
//        assertThat(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_VALUE_STRING)
//                .contains(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_VALUE_TIMESTAMP_STRING);
//        val SELECT_RAAS_REQ_CREATED_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_RAAS_REQ_CREATED_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST,raasTxnRef));
//        String SELECT_RAAS_REQ_CREATED_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE_STRING = SELECT_RAAS_REQ_CREATED_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE.toString();
//        System.out.println(SELECT_RAAS_REQ_CREATED_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE_STRING);
//        val SELECT_RAAS_REQ_CREATED_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
//        System.out.println(SELECT_RAAS_REQ_CREATED_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE_TIMESTAMP_STRING);
//        assertThat(SELECT_RAAS_REQ_CREATED_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE_STRING)
//                .contains(SELECT_RAAS_REQ_CREATED_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE_TIMESTAMP_STRING);
//        val SELECT_RAAS_RES_CREATED_BY_RAAS_TXN_REF_FROM_RAAS_RESPONSE_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_RAAS_RES_CREATED_BY_RAAS_TXN_REF_FROM_RAAS_RESPONSE,raasTxnRef));
//        String SELECT_RAAS_RES_CREATED_BY_RAAS_TXN_REF_FROM_RAAS_RESPONSE_VALUE_STRING = SELECT_RAAS_RES_CREATED_BY_RAAS_TXN_REF_FROM_RAAS_RESPONSE_VALUE.toString();
//        System.out.println(SELECT_RAAS_RES_CREATED_BY_RAAS_TXN_REF_FROM_RAAS_RESPONSE_VALUE_STRING);
//        val SELECT_RAAS_RES_CREATED_BY_RAAS_TXN_REF_FROM_RAAS_RESPONSE_VALUE_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
//        System.out.println(SELECT_RAAS_RES_CREATED_BY_RAAS_TXN_REF_FROM_RAAS_RESPONSE_VALUE_TIMESTAMP_STRING);
//        assertThat(SELECT_RAAS_RES_CREATED_BY_RAAS_TXN_REF_FROM_RAAS_RESPONSE_VALUE_STRING)
//                .contains(SELECT_RAAS_RES_CREATED_BY_RAAS_TXN_REF_FROM_RAAS_RESPONSE_VALUE_TIMESTAMP_STRING);
//        val SELECT_RESERVE_FUND_REQUEST_CREATED_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_RESERVE_FUND_REQUEST_CREATED_BY_RAAS_TXN_REF,raasTxnRef));
//        System.out.println(SELECT_RESERVE_FUND_REQUEST_CREATED_BY_RAAS_TXN_REF_VALUE);
//        assertThat(SELECT_RESERVE_FUND_REQUEST_CREATED_BY_RAAS_TXN_REF_VALUE)
//                .containsNull();
//        val SELECT_RESERVE_FUND_RESPONSE_CREATED_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_RESERVE_FUND_RESPONSE_CREATED_BY_RAAS_TXN_REF,raasTxnRef));
//        System.out.println(SELECT_RESERVE_FUND_RESPONSE_CREATED_BY_RAAS_TXN_REF_VALUE);
//        assertThat(SELECT_RESERVE_FUND_RESPONSE_CREATED_BY_RAAS_TXN_REF_VALUE)
//                .containsNull();
//        val SELECT_CREATED_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ, raasTxnRef));
//        String SELECT_CREATED_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_VALUE_STRING = SELECT_CREATED_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_VALUE.toString();
//        System.out.println(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_VALUE_STRING);
//        val SELECT_CREATED_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
//        System.out.println(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_TIMESTAMP_STRING);
//        assertThat(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_VALUE_STRING)
//                .contains(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_TIMESTAMP_STRING);
//        val SELECT_CREATED_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES, raasTxnRef));
//        String SELECT_CREATED_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES_VALUE_STRING = SELECT_CREATED_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES_VALUE.toString();
//        System.out.println(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES_VALUE_STRING);
//        val SELECT_CREATED_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
//        System.out.println(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES_TIMESTAMP_STRING);
//        assertThat(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES_VALUE_STRING)
//                .contains(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES_TIMESTAMP_STRING);

        getFinancialTermsCalculate(ProductAirtel_917,TestClient3,PurchaseAmount10000)
                .then().assertThat().statusCode(SC_OK)
                .body("terms.reserveAmount", Matchers.is(Integer.parseInt(PurchaseAmount10000)))
                .body("terms.vendAmount", Matchers.is(Integer.parseInt(PurchaseAmount10000)))
                .body("terms.feeAmount", Matchers.is(Integer.parseInt(FeeAmount0)))
                .body("terms.settlementAmount", Matchers.is(Integer.parseInt(settlementAmount)))
                .body("terms.clientShareAmount", Matchers.is(Integer.parseInt(PurchaseAmount1000)))
                .body("terms.vendorShareAmount", Matchers.is(Integer.parseInt(FeeAmount0)))
                .body("variables.PAYD_SHARE_AMOUNT", Matchers.is(Integer.parseInt(FeeAmount0)))
                .body("variables.REQUESTED_PURCHASE_VALUE", Matchers.is(Integer.parseInt(PurchaseAmount10000)))
                .body("variables.FEE_AMOUNT", Matchers.is(Integer.parseInt(FeeAmount0)))
                .body("variables.PAYD_VAT_LIABILITY", Matchers.is(Float.parseFloat(PAYD_VAT_LIABILITY)))
                .body("variables.PAYD_REVENUE", Matchers.is(Float.parseFloat(PAYD_REVENUE)))
                .body("variables.VAT_PERC", Matchers.is(Float.parseFloat(VAT_PERC)))
                //.body("variables.MNO_DISC_AMT", Matchers.is(Integer.parseInt(MNO_DISC_AMT)))
                .body("variables.CLIENT_MARKETING_COMMISSION", Matchers.is(Integer.parseInt(FeeAmount0)))
                .body("variables.PAYD_GROSS_PROFIT", Matchers.is(Float.parseFloat(PAYD_GROSS_PROFIT)))
                //.body("variables.RESERVE_AMOUNT", Matchers.is(Integer.parseInt(PurchaseAmount10000)))
                //.body("variables.SETTLEMENT_AMOUNT", Matchers.is(Integer.parseInt(settlementAmount)))
                .body("variables.MNO_DISCOUNT_PERC", Matchers.is(Float.parseFloat(value_0point1)))
                //.body("variables.VEND_AMOUNT", Matchers.is(Integer.parseInt(PurchaseAmount10000)))
                //.body("variables.CLIENT_REVENUE", Matchers.is(Integer.parseInt(PurchaseAmount1000)))
                .body("variables.VENDOR_SHARE_AMOUNT", Matchers.is(Integer.parseInt(FeeAmount0)))
                //.body("variables.PAYD_COST_OF_GOODS_SOLD", Matchers.is(Integer.parseInt(PurchaseAmount1000)))
                //.body("variables.TPV", Matchers.is(Integer.parseInt(PurchaseAmount10000)))
                .body("variables.CLIENT_SHARE_OF_TPV", Matchers.is(Float.parseFloat(value_0point1)));
                //.body("variables.CLIENT_SHARE_AMOUNT", Matchers.is(Integer.parseInt(PurchaseAmount1000)));

    }
}
