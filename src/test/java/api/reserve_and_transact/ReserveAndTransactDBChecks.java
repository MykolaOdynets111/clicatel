package api.reserve_and_transact;

import api.clients.ReserveAndTransactClient;
import api.domains.reserve_and_transact.model.ReserveAndTransactResponse;
import api.enums.ChannelId;
import api.enums.ChannelName;
import api.enums.Port;
import api.enums.Version;
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

import static api.clients.FinancialTermsLookupClient.*;
import static api.clients.InFlightTransactionLookupClient.ResponseCode_200;
import static api.clients.ProductLookupClient.*;
import static api.clients.ReserveAndTransactClient.*;
import static api.clients.ReserveAndTransactDBChecksClient.*;
import static api.clients.SimulatorsClient.*;
import static api.clients.SimulatorsClient.mwmSuccess;
import static api.clients.TokenLookupClient.*;
import static api.clients.TransactionLookupClient.findTransaction;
import static api.clients.VendorManagementClient.*;
import static api.clients.VendorRoutingServiceClient.*;
import static api.domains.reserve_and_transact.repo.ReserveAndTransactRequestRepo.*;
import static api.domains.simulator.repo.VendorRoutingServiceRequestRepo.*;
import static api.enums.ChannelName.USSD;
import static api.enums.CurrencyCode.NGN;
import static db.clients.HibernateBaseClient.executeCustomQuery;
import static db.clients.HibernateBaseClient.executeCustomQueryAndReturnValues;
import static db.custom_queries.FinancialTermsQueries.*;
import static db.custom_queries.ReserveAndTransactQueries.*;
import static db.custom_queries.VendorRoutingServiceQueries.*;
import static db.engine.HibernateConnection.closeDBConnection;
import static db.enums.Sessions.MY_SQL;
import static db.enums.Sessions.POSTGRES_SQL;
import static java.lang.String.format;
import static java.time.LocalDateTime.now;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;
import static util.DateProvider.getCurrentIsoDateTime;
import static util.DateProvider.getCurrentTimeStamp;

public class ReserveAndTransactDBChecks extends BaseApiTest {
    @Test()
    @Description("30100-payd-raas-gateway :: POST v4/reserveAndTransact :: raas.transaction_log all fields")
    @TmsLink("TECH-173965")
    public void testReserveAndTransactV4RaasTransactionLogAllFields() throws InterruptedException {
        //Failing because of https://jira.clickatell.com/browse/TECH-176071
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_VENDOR_DISCOUNT_PERCENTAGE_BY_VENDOR_ID, value_0point1,Vendor21));
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_MODEL_SELECTION, Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2, Vendor21, TestClient3, ProductAirtel_917));
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_TPV_CLIENT_SHARE, value_0point1, TestClient3, ProductAirtel_917));
        val jsonBody = setUpReserveAndTransactV4DataAdditionalDataWithProductFundSourceClientID(fundingSourceId_1500,AccountIdentifier,clientTxnRef,fundingSourceId,channelSessionId,ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundsReserved))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
        //Verify transaction status is "SUCCESS"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        Thread.sleep(20000);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, Integer.parseInt(ReserveAndTransactClient.TestClient3), queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString(ReserveAndTransactClient.Success));

        val SELECT_ACCOUNT_IDENTIFIER_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_ACCOUNT_IDENTIFIER_BY_RAAS_TXN_REF,raasTxnRef));
        System.out.println(SELECT_ACCOUNT_IDENTIFIER_BY_RAAS_TXN_REF_VALUE);
        assertThat(SELECT_ACCOUNT_IDENTIFIER_BY_RAAS_TXN_REF_VALUE)
                .contains(AccountIdentifier);
        val SELECT_AMOUNT_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_AMOUNT_BY_RAAS_TXN_REF,raasTxnRef));
        System.out.println(SELECT_AMOUNT_BY_RAAS_TXN_REF_VALUE);
        List<String> SELECT_AMOUNT_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_AMOUNT_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
        assertThat(SELECT_AMOUNT_BY_RAAS_TXN_REF_VALUE_STRING.get(0))
                .contains(PurchaseAmount10000);
        val SELECT_CHANNEL_ID_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CHANNEL_ID_BY_RAAS_TXN_REF,raasTxnRef));
        List<String> SELECT_CHANNEL_ID_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_CHANNEL_ID_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
        assertThat(SELECT_CHANNEL_ID_BY_RAAS_TXN_REF_VALUE_STRING)
                .contains("7");
        val SELECT_CHANNEL_SESSION_ID_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CHANNEL_SESSION_ID_BY_RAAS_TXN_REF,raasTxnRef));
        List<String> SELECT_CHANNEL_SESSION_ID_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_CHANNEL_SESSION_ID_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
        System.out.println(SELECT_CHANNEL_SESSION_ID_BY_RAAS_TXN_REF_VALUE_STRING);
        assertThat(SELECT_CHANNEL_SESSION_ID_BY_RAAS_TXN_REF_VALUE_STRING)
                .contains(channelSessionId);
        val SELECT_CLIENT_ID_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CLIENT_ID_BY_RAAS_TXN_REF,raasTxnRef));
        List<String> SELECT_CLIENT_ID_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_CLIENT_ID_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
        System.out.println(SELECT_CLIENT_ID_BY_RAAS_TXN_REF_VALUE_STRING);
        assertThat(SELECT_CLIENT_ID_BY_RAAS_TXN_REF_VALUE_STRING)
                .contains(TestClient3);
        val SELECT_CLIENT_TXN_REF_ID_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CLIENT_TXN_REF_ID_BY_RAAS_TXN_REF,raasTxnRef));
        System.out.println(SELECT_CLIENT_TXN_REF_ID_BY_RAAS_TXN_REF_VALUE);
        List<String> SELECT_CLIENT_TXN_REF_ID_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_CLIENT_TXN_REF_ID_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
        assertThat(SELECT_CLIENT_TXN_REF_ID_BY_RAAS_TXN_REF_VALUE_STRING)
                .contains(clientTxnRef);
        val SELECT_PRODUCT_ID_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_PRODUCT_ID_BY_RAAS_TXN_REF,raasTxnRef));
        System.out.println(SELECT_PRODUCT_ID_BY_RAAS_TXN_REF_VALUE);
        List<String> SELECT_PRODUCT_ID_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_PRODUCT_ID_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
        assertThat(SELECT_PRODUCT_ID_BY_RAAS_TXN_REF_VALUE_STRING)
                .contains(ProductAirtel_917);
        val SELECT_SOURCE_IDENTIFIER_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_SOURCE_IDENTIFIER_BY_RAAS_TXN_REF,raasTxnRef));
        System.out.println(SELECT_SOURCE_IDENTIFIER_BY_RAAS_TXN_REF_VALUE);
        List<String> SELECT_SOURCE_IDENTIFIER_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_SOURCE_IDENTIFIER_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
        assertThat(SELECT_SOURCE_IDENTIFIER_BY_RAAS_TXN_REF_VALUE_STRING)
                .contains(Identifier);
        val SELECT_TARGET_IDENTIFIER_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_TARGET_IDENTIFIER_BY_RAAS_TXN_REF,raasTxnRef));
        System.out.println(SELECT_TARGET_IDENTIFIER_BY_RAAS_TXN_REF_VALUE);
        List<String> SELECT_TARGET_IDENTIFIER_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_TARGET_IDENTIFIER_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
        assertThat(SELECT_TARGET_IDENTIFIER_BY_RAAS_TXN_REF_VALUE_STRING)
                .contains(Identifier);
        val SELECT_CURRENCY_CODE_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CURRENCY_CODE_BY_RAAS_TXN_REF,raasTxnRef));
        System.out.println(SELECT_CURRENCY_CODE_BY_RAAS_TXN_REF_VALUE);
        List<String> SELECT_CURRENCY_CODE_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_CURRENCY_CODE_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
        assertThat(SELECT_CURRENCY_CODE_BY_RAAS_TXN_REF_VALUE_STRING)
                .contains(String.valueOf(NGN));
        val SELECT_FUNDING_SOURCE_ID_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_FUNDING_SOURCE_ID_BY_RAAS_TXN_REF,raasTxnRef));
        System.out.println(SELECT_FUNDING_SOURCE_ID_BY_RAAS_TXN_REF_VALUE);
        List<String> SELECT_FUNDING_SOURCE_ID_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_FUNDING_SOURCE_ID_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
        assertThat(SELECT_FUNDING_SOURCE_ID_BY_RAAS_TXN_REF_VALUE_STRING)
                .contains(fundingSourceId);
        val SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF,raasTxnRef));
        System.out.println(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_VALUE);
        List<String> SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
        assertThat(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_VALUE_STRING)
                .contains(Event_Type);
        val SELECT_STATUS_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_STATUS_BY_RAAS_TXN_REF,raasTxnRef));
        System.out.println(SELECT_STATUS_BY_RAAS_TXN_REF_VALUE);
        List<String> SELECT_STATUS_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_STATUS_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
        assertThat(SELECT_STATUS_BY_RAAS_TXN_REF_VALUE_STRING)
                .contains(Success);
        val SELECT_CHANNEL_NAME_BY_ID_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CHANNEL_NAME_BY_ID,"7"));
        System.out.println(SELECT_CHANNEL_NAME_BY_ID_VALUE);
        List<String> SELECT_CHANNEL_NAME_BY_ID_VALUE_STRING = Lists.transform(SELECT_CHANNEL_NAME_BY_ID_VALUE, Functions.toStringFunction());
        assertThat(SELECT_CHANNEL_NAME_BY_ID_VALUE_STRING)
                .contains(String.valueOf(USSD));
        val SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF,raasTxnRef));
        System.out.println(SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_VALUE);
        List<String> SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
        assertThat(SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_VALUE_STRING)
                .contains(responseCode0000);
        val SELECT_RESPONSE_MESSAGE_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_RESPONSE_MESSAGE_BY_RAAS_TXN_REF,raasTxnRef));
        System.out.println(SELECT_RESPONSE_MESSAGE_BY_RAAS_TXN_REF_VALUE);
        List<String> SELECT_RESPONSE_MESSAGE_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_RESPONSE_MESSAGE_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
        assertThat(SELECT_RESPONSE_MESSAGE_BY_RAAS_TXN_REF_VALUE_STRING)
                .contains(responseMessageFundsReserved);
        val SELECT_FUND_RESPONSE_CODE_BY_RAAS_TXN_REF_RAAS_RES_FUND_RES_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_FUND_RESPONSE_CODE_BY_RAAS_TXN_REF_RAAS_RES_FUND_RES,raasTxnRef));
        System.out.println(SELECT_FUND_RESPONSE_CODE_BY_RAAS_TXN_REF_RAAS_RES_FUND_RES_VALUE);
        List<String> SELECT_FUND_RESPONSE_CODE_BY_RAAS_TXN_REF_RAAS_RES_FUND_RES_VALUE_STRING = Lists.transform(SELECT_FUND_RESPONSE_CODE_BY_RAAS_TXN_REF_RAAS_RES_FUND_RES_VALUE, Functions.toStringFunction());
        assertThat(SELECT_FUND_RESPONSE_CODE_BY_RAAS_TXN_REF_RAAS_RES_FUND_RES_VALUE_STRING)
                .contains(responseCode0000);
        val SELECT_RESULT_REQUEST_RESPONSE_CODE_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_RESULT_REQUEST_RESPONSE_CODE_BY_RAAS_TXN_REF,raasTxnRef));
        System.out.println(SELECT_RESULT_REQUEST_RESPONSE_CODE_BY_RAAS_TXN_REF_VALUE);
        List<String> SELECT_RESULT_REQUEST_RESPONSE_CODE_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_RESULT_REQUEST_RESPONSE_CODE_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
        assertThat(SELECT_RESULT_REQUEST_RESPONSE_CODE_BY_RAAS_TXN_REF_VALUE_STRING)
                .contains(responseCode0000);
        val SELECT_RESULT_RESPONSE_CODE_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_RESULT_RESPONSE_CODE_BY_RAAS_TXN_REF,raasTxnRef));
        System.out.println(SELECT_RESULT_RESPONSE_CODE_BY_RAAS_TXN_REF_VALUE);
        List<String> SELECT_RESULT_RESPONSE_CODE_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_RESULT_RESPONSE_CODE_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
        assertThat(SELECT_RESULT_RESPONSE_CODE_BY_RAAS_TXN_REF_VALUE_STRING)
                .contains(responseCode202);
        val SELECT_FEE_AMOUNT_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_FEE_AMOUNT_BY_RAAS_TXN_REF,raasTxnRef));
        System.out.println(SELECT_FEE_AMOUNT_BY_RAAS_TXN_REF_VALUE);
        List<String> SELECT_FEE_AMOUNT_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_FEE_AMOUNT_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
        assertThat(SELECT_FEE_AMOUNT_BY_RAAS_TXN_REF_VALUE_STRING)
                .contains(FeeAmount0);
        val SELECT_ADD_DATA_CLIENT_ID_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_ADD_DATA_CLIENT_ID_BY_RAAS_TXN_REF,raasTxnRef));
        System.out.println(SELECT_ADD_DATA_CLIENT_ID_BY_RAAS_TXN_REF_VALUE);
        List<String> SELECT_ADD_DATA_CLIENT_ID_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_ADD_DATA_CLIENT_ID_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
        System.out.println(SELECT_ADD_DATA_CLIENT_ID_BY_RAAS_TXN_REF_VALUE_STRING);
        assertThat(SELECT_ADD_DATA_CLIENT_ID_BY_RAAS_TXN_REF_VALUE_STRING)
                .contains("{\"clientId\":\"3\"}");
        val SELECT_ADD_DATA_FUN_SOURCE_ID_ID_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_ADD_DATA_FUN_SOURCE_ID_ID_BY_RAAS_TXN_REF,raasTxnRef));
        System.out.println(SELECT_ADD_DATA_FUN_SOURCE_ID_ID_BY_RAAS_TXN_REF_VALUE);
        List<String> SELECT_ADD_DATA_FUN_SOURCE_ID_ID_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_ADD_DATA_FUN_SOURCE_ID_ID_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
        assertThat(SELECT_ADD_DATA_FUN_SOURCE_ID_ID_BY_RAAS_TXN_REF_VALUE_STRING)
                .contains("{\"fundingSourceId\":\"3\"}");
        val SELECT_ADD_DATA_PROD_ID_ID_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_ADD_DATA_PROD_ID_ID_BY_RAAS_TXN_REF,raasTxnRef));
        System.out.println(SELECT_ADD_DATA_PROD_ID_ID_BY_RAAS_TXN_REF_VALUE);
        List<String> SELECT_ADD_DATA_PROD_ID_ID_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_ADD_DATA_PROD_ID_ID_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
        assertThat(SELECT_ADD_DATA_PROD_ID_ID_BY_RAAS_TXN_REF_VALUE_STRING)
                .contains("{\"productId\":\"917\"}");
        val SELECT_TIMESTAMP_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_TIMESTAMP_BY_RAAS_TXN_REF,raasTxnRef));
        String TimeStamp = SELECT_TIMESTAMP_BY_RAAS_TXN_REF_VALUE.toString();
        System.out.println(TimeStamp);
        val timestamp_string = now().format(ofPattern("yyyy-MM-dd HH"));
        System.out.println(timestamp_string);
        assertThat(TimeStamp)
                .contains(timestamp_string);
        val SELECT_CREATED_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CREATED_BY_RAAS_TXN_REF,raasTxnRef));
        String SELECT_CREATED_BY_RAAS_TXN_REF_VALUE_STRING = SELECT_CREATED_BY_RAAS_TXN_REF_VALUE.toString();
        System.out.println(SELECT_CREATED_BY_RAAS_TXN_REF_VALUE_STRING);
        val SELECT_CREATED_BY_RAAS_TXN_REF_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
        System.out.println(SELECT_CREATED_BY_RAAS_TXN_REF_TIMESTAMP_STRING);
        assertThat(SELECT_CREATED_BY_RAAS_TXN_REF_VALUE_STRING)
                .contains(SELECT_CREATED_BY_RAAS_TXN_REF_TIMESTAMP_STRING);
        val SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF,raasTxnRef));
        String SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_VALUE_STRING = SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_VALUE.toString();
        System.out.println(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_VALUE_STRING);
        val SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_VALUE_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
        System.out.println(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_VALUE_TIMESTAMP_STRING);
        assertThat(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_VALUE_STRING)
                .contains(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_VALUE_TIMESTAMP_STRING);
        val SELECT_RAAS_REQUEST_CREATED_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_RAAS_REQUEST_CREATED_BY_RAAS_TXN_REF,raasTxnRef));
        String SELECT_RAAS_REQUEST_CREATED_BY_RAAS_TXN_REF_VALUE_STRING = SELECT_RAAS_REQUEST_CREATED_BY_RAAS_TXN_REF_VALUE.toString();
        System.out.println(SELECT_RAAS_REQUEST_CREATED_BY_RAAS_TXN_REF_VALUE_STRING);
        val SELECT_RAAS_REQUEST_CREATED_BY_RAAS_TXN_REF_VALUE_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
        System.out.println(SELECT_RAAS_REQUEST_CREATED_BY_RAAS_TXN_REF_VALUE_TIMESTAMP_STRING);
        assertThat(SELECT_RAAS_REQUEST_CREATED_BY_RAAS_TXN_REF_VALUE_STRING)
                .contains(SELECT_RAAS_REQUEST_CREATED_BY_RAAS_TXN_REF_VALUE_TIMESTAMP_STRING);
        val SELECT_RAAS_RESPONSE_CREATED_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_RAAS_RESPONSE_CREATED_BY_RAAS_TXN_REF,raasTxnRef));
        String SELECT_RAAS_RESPONSE_CREATED_BY_RAAS_TXN_REF_VALUE_STRING = SELECT_RAAS_RESPONSE_CREATED_BY_RAAS_TXN_REF_VALUE.toString();
        System.out.println(SELECT_RAAS_RESPONSE_CREATED_BY_RAAS_TXN_REF_VALUE_STRING);
        val SELECT_RAAS_RESPONSE_CREATED_BY_RAAS_TXN_REF_VALUE_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
        System.out.println(SELECT_RAAS_RESPONSE_CREATED_BY_RAAS_TXN_REF_VALUE_TIMESTAMP_STRING);
        assertThat(SELECT_RAAS_RESPONSE_CREATED_BY_RAAS_TXN_REF_VALUE_STRING)
                .contains(SELECT_RAAS_RESPONSE_CREATED_BY_RAAS_TXN_REF_VALUE_TIMESTAMP_STRING);
        val SELECT_RESERVE_FUND_REQUEST_CREATED_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_RESERVE_FUND_REQUEST_CREATED_BY_RAAS_TXN_REF,raasTxnRef));
        String SELECT_RESERVE_FUND_REQUEST_CREATED_BY_RAAS_TXN_REF_VALUE_STRING = SELECT_RESERVE_FUND_REQUEST_CREATED_BY_RAAS_TXN_REF_VALUE.toString();
        System.out.println(SELECT_RESERVE_FUND_REQUEST_CREATED_BY_RAAS_TXN_REF_VALUE_STRING);
        val SELECT_RESERVE_FUND_REQUEST_CREATED_BY_RAAS_TXN_REF_VALUE_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
        System.out.println(SELECT_RESERVE_FUND_REQUEST_CREATED_BY_RAAS_TXN_REF_VALUE_TIMESTAMP_STRING);
        assertThat(SELECT_RESERVE_FUND_REQUEST_CREATED_BY_RAAS_TXN_REF_VALUE_STRING)
                .contains(SELECT_RESERVE_FUND_REQUEST_CREATED_BY_RAAS_TXN_REF_VALUE_TIMESTAMP_STRING);
        val SELECT_RESERVE_FUND_RESPONSE_CREATED_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_RESERVE_FUND_RESPONSE_CREATED_BY_RAAS_TXN_REF,raasTxnRef));
        String SELECT_RESERVE_FUND_RESPONSE_CREATED_BY_RAAS_TXN_REF_VALUE_STRING = SELECT_RESERVE_FUND_RESPONSE_CREATED_BY_RAAS_TXN_REF_VALUE.toString();
        System.out.println(SELECT_RESERVE_FUND_RESPONSE_CREATED_BY_RAAS_TXN_REF_VALUE_STRING);
        val SELECT_RESERVE_FUND_RESPONSE_CREATED_BY_RAAS_TXN_REF_VALUE_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
        System.out.println(SELECT_RESERVE_FUND_RESPONSE_CREATED_BY_RAAS_TXN_REF_VALUE_TIMESTAMP_STRING);
        assertThat(SELECT_RESERVE_FUND_RESPONSE_CREATED_BY_RAAS_TXN_REF_VALUE_STRING)
                .contains(SELECT_RESERVE_FUND_RESPONSE_CREATED_BY_RAAS_TXN_REF_VALUE_TIMESTAMP_STRING);
        val SELECT_TRANSACT_RESULT_REQUEST_CREATED_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_TRANSACT_RESULT_REQUEST_CREATED_BY_RAAS_TXN_REF,raasTxnRef));
        String SELECT_TRANSACT_RESULT_REQUEST_CREATED_BY_RAAS_TXN_REF_VALUE_STRING = SELECT_TRANSACT_RESULT_REQUEST_CREATED_BY_RAAS_TXN_REF_VALUE.toString();
        System.out.println(SELECT_TRANSACT_RESULT_REQUEST_CREATED_BY_RAAS_TXN_REF_VALUE_STRING);
        val SELECT_TRANSACT_RESULT_REQUEST_CREATED_BY_RAAS_TXN_REF_VALUE_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
        System.out.println(SELECT_TRANSACT_RESULT_REQUEST_CREATED_BY_RAAS_TXN_REF_VALUE_TIMESTAMP_STRING);
        assertThat(SELECT_TRANSACT_RESULT_REQUEST_CREATED_BY_RAAS_TXN_REF_VALUE_STRING)
                .contains(SELECT_TRANSACT_RESULT_REQUEST_CREATED_BY_RAAS_TXN_REF_VALUE_TIMESTAMP_STRING);
        val SELECT_TRANSACT_RESULT_RESPONSE_CREATED_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_TRANSACT_RESULT_RESPONSE_CREATED_BY_RAAS_TXN_REF,raasTxnRef));
        String SELECT_TRANSACT_RESULT_RESPONSE_CREATED_BY_RAAS_TXN_REF_VALUE_STRING = SELECT_TRANSACT_RESULT_RESPONSE_CREATED_BY_RAAS_TXN_REF_VALUE.toString();
        System.out.println(SELECT_TRANSACT_RESULT_RESPONSE_CREATED_BY_RAAS_TXN_REF_VALUE);
        val SELECT_TRANSACT_RESULT_RESPONSE_CREATED_BY_RAAS_TXN_REF_TIMESTAMP_VALUE = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
        System.out.println(SELECT_TRANSACT_RESULT_RESPONSE_CREATED_BY_RAAS_TXN_REF_TIMESTAMP_VALUE);
        assertThat(SELECT_TRANSACT_RESULT_RESPONSE_CREATED_BY_RAAS_TXN_REF_VALUE_STRING)
                .contains(SELECT_TRANSACT_RESULT_RESPONSE_CREATED_BY_RAAS_TXN_REF_TIMESTAMP_VALUE);
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
    @Test()
    @Description("30100-payd-raas-gateway :: POST v4/reserveAndTransact :: raas.raas_request all fields")
    @TmsLink("TECH-174321")
    public void testReserveAndTransactV4RaasRequestAllFields() throws InterruptedException {
        //Failing because of https://jira.clickatell.com/browse/TECH-176071
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_VENDOR_DISCOUNT_PERCENTAGE_BY_VENDOR_ID, value_0point1,Vendor21));
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_MODEL_SELECTION, Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2, Vendor21, TestClient3, ProductAirtel_917));
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_TPV_CLIENT_SHARE, value_0point1, TestClient3, ProductAirtel_917));
        val jsonBody = setUpReserveAndTransactV4DataAdditionalDataWithProductFundSourceClientID(fundingSourceId_1500,AccountIdentifier,clientTxnRef,fundingSourceId,channelSessionId,ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundsReserved))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
        //Verify transaction status is "SUCCESS"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        Thread.sleep(20000);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, Integer.parseInt(ReserveAndTransactClient.TestClient3), queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString(ReserveAndTransactClient.Success));

        val SELECT_ACCOUNT_IDENTIFIER_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_ACCOUNT_IDENTIFIER_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST,raasTxnRef));
        System.out.println(SELECT_ACCOUNT_IDENTIFIER_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE);
        assertThat(SELECT_ACCOUNT_IDENTIFIER_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE)
                .contains(AccountIdentifier);
        val SELECT_AMOUNT_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_AMOUNT_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST,raasTxnRef));
        System.out.println(SELECT_AMOUNT_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE);
        List<String> SELECT_AMOUNT_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE_STRING = Lists.transform(SELECT_AMOUNT_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE, Functions.toStringFunction());
        assertThat(SELECT_AMOUNT_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE_STRING.get(0))
                .contains(PurchaseAmount10000);
        val SELECT_CHANNEL_ID_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CHANNEL_ID_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST,raasTxnRef));
        List<String> SELECT_CHANNEL_ID_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE_STRING = Lists.transform(SELECT_CHANNEL_ID_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE, Functions.toStringFunction());
        assertThat(SELECT_CHANNEL_ID_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE_STRING)
                .contains("7");
        val SELECT_CHANNEL_SESSSION_ID_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CHANNEL_SESSSION_ID_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST,raasTxnRef));
        List<String> SELECT_CHANNEL_SESSSION_ID_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE_STRING = Lists.transform(SELECT_CHANNEL_SESSSION_ID_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE, Functions.toStringFunction());
        System.out.println(SELECT_CHANNEL_SESSSION_ID_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE_STRING);
        assertThat(SELECT_CHANNEL_SESSSION_ID_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE_STRING)
                .contains(channelSessionId);
        val SELECT_CLIENT_ID_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CLIENT_ID_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST,raasTxnRef));
        List<String> SELECT_CLIENT_ID_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE_STRING = Lists.transform(SELECT_CLIENT_ID_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE, Functions.toStringFunction());
        System.out.println(SELECT_CLIENT_ID_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE_STRING);
        assertThat(SELECT_CLIENT_ID_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE_STRING)
                .contains(TestClient3);
        val SELECT_CLIENT_TXN_REF_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CLIENT_TXN_REF_ID_BY_RAAS_TXN_REF,raasTxnRef));
        System.out.println(SELECT_CLIENT_TXN_REF_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE);
        List<String> SELECT_CLIENT_TXN_REF_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE_STRING = Lists.transform(SELECT_CLIENT_TXN_REF_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE, Functions.toStringFunction());
        assertThat(SELECT_CLIENT_TXN_REF_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE_STRING)
                .contains(clientTxnRef);
        val SELECT_PRODUCT_ID_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_PRODUCT_ID_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST,raasTxnRef));
        System.out.println(SELECT_PRODUCT_ID_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE);
        List<String> SELECT_PRODUCT_ID_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE_STRING = Lists.transform(SELECT_PRODUCT_ID_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE, Functions.toStringFunction());
        assertThat(SELECT_PRODUCT_ID_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE_STRING)
                .contains(ProductAirtel_917);
        val SELECT_SOURCE_IDENTIFIER_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_SOURCE_IDENTIFIER_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST,raasTxnRef));
        System.out.println(SELECT_SOURCE_IDENTIFIER_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE);
        List<String> SELECT_SOURCE_IDENTIFIER_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE_STRING = Lists.transform(SELECT_SOURCE_IDENTIFIER_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE, Functions.toStringFunction());
        assertThat(SELECT_SOURCE_IDENTIFIER_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE_STRING)
                .contains(Identifier);
        val SELECT_TARGET_IDENTIFIER_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_TARGET_IDENTIFIER_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST,raasTxnRef));
        System.out.println(SELECT_TARGET_IDENTIFIER_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE);
        List<String> SELECT_TARGET_IDENTIFIER_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE_STRING = Lists.transform(SELECT_TARGET_IDENTIFIER_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE, Functions.toStringFunction());
        assertThat(SELECT_TARGET_IDENTIFIER_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE_STRING)
                .contains(Identifier);
        val SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST,raasTxnRef));
        System.out.println(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE);
        List<String> SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE_STRING = Lists.transform(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE, Functions.toStringFunction());
        assertThat(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE_STRING)
                .contains(Event_Type_Raas_Request);
        val SELECT_CHANNEL_NAME_BY_ID_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CHANNEL_NAME_BY_ID,"7"));
        System.out.println(SELECT_CHANNEL_NAME_BY_ID_VALUE);
        List<String> SELECT_CHANNEL_NAME_BY_ID_VALUE_STRING = Lists.transform(SELECT_CHANNEL_NAME_BY_ID_VALUE, Functions.toStringFunction());
        assertThat(SELECT_CHANNEL_NAME_BY_ID_VALUE_STRING)
                .contains(String.valueOf(USSD));
        val SELECT_FEE_AMOUNT_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_FEE_AMOUNT_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST,raasTxnRef));
        System.out.println(SELECT_FEE_AMOUNT_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE);
        List<String> SELECT_FEE_AMOUNT_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE_STRING = Lists.transform(SELECT_FEE_AMOUNT_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE, Functions.toStringFunction());
        assertThat(SELECT_FEE_AMOUNT_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE_STRING)
                .contains(FeeAmount0);
        val SELECT_API_CALL_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_API_CALL_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST,raasTxnRef));
        System.out.println(SELECT_API_CALL_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE);
        List<String> SELECT_API_CALL_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE_STRING = Lists.transform(SELECT_API_CALL_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE, Functions.toStringFunction());
        assertThat(SELECT_API_CALL_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE_STRING)
                .contains(Api_Call);
        val SELECT_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST,raasTxnRef));
        String TimeStamp = SELECT_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE.toString();
        System.out.println(TimeStamp);
        val timestamp_string = now().format(ofPattern("yyyy-MM-dd HH"));
        System.out.println(timestamp_string);
        assertThat(TimeStamp)
                .contains(timestamp_string);
        val SELECT_CREATED_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST,raasTxnRef));
        String SELECT_CREATED_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE_STRING = SELECT_CREATED_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE.toString();
        System.out.println(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE_STRING);
        val SELECT_CREATED_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
        System.out.println(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_TIMESTAMP_STRING);
        assertThat(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE_STRING)
                .contains(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_TIMESTAMP_STRING);
        val SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST,raasTxnRef));
        String SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE_STRING = SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE.toString();
        System.out.println(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE_STRING);
        val SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
        System.out.println(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE_TIMESTAMP_STRING);
        assertThat(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE_STRING)
                .contains(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST_VALUE_TIMESTAMP_STRING);

    }
    @Test()
    @Description("30100-payd-raas-gateway :: POST v4/reserveAndTransact :: raas.raas_response all fields")
    @TmsLink("TECH-174322")
    public void testReserveAndTransactV4RaasResponseAllFields() throws InterruptedException {
        //Failing because of https://jira.clickatell.com/browse/TECH-176071
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_VENDOR_DISCOUNT_PERCENTAGE_BY_VENDOR_ID, value_0point1,Vendor21));
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_MODEL_SELECTION, Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2, Vendor21, TestClient3, ProductAirtel_917));
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_TPV_CLIENT_SHARE, value_0point1, TestClient3, ProductAirtel_917));
        val jsonBody = setUpReserveAndTransactV4DataAdditionalDataWithProductFundSourceClientID(fundingSourceId_1500,AccountIdentifier,clientTxnRef,fundingSourceId,channelSessionId,ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundsReserved))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
        //Verify transaction status is "SUCCESS"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        Thread.sleep(20000);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, Integer.parseInt(ReserveAndTransactClient.TestClient3), queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString(ReserveAndTransactClient.Success));


        val SELECT_CREATED_BY_RAAS_TXN_REF_FROM_RAAS_RESPONSE_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_RAAS_RESPONSE,raasTxnRef));
        String SELECT_CREATED_BY_RAAS_TXN_REF_FROM_RAAS_RESPONSE_VALUE_STRING = SELECT_CREATED_BY_RAAS_TXN_REF_FROM_RAAS_RESPONSE_VALUE.toString();
        System.out.println(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_RAAS_RESPONSE_VALUE_STRING);
        val SELECT_CREATED_BY_RAAS_TXN_REF_FROM_RAAS_RESPONSE_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
        System.out.println(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_RAAS_RESPONSE_TIMESTAMP_STRING);
        assertThat(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_RAAS_RESPONSE_VALUE_STRING)
                .contains(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_RAAS_RESPONSE_TIMESTAMP_STRING);
        val SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_RAAS_RESPONSE_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_RAAS_RESPONSE,raasTxnRef));
        System.out.println(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_RAAS_RESPONSE_VALUE);
        List<String> SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_RAAS_RESPONSE_VALUE_STRING = Lists.transform(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_RAAS_RESPONSE_VALUE, Functions.toStringFunction());
        assertThat(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_RAAS_RESPONSE_VALUE_STRING)
                .contains(Event_Type_Raas_Response);
        val SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF,raasTxnRef));
        System.out.println(SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_VALUE);
        List<String> SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
        assertThat(SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_VALUE_STRING)
                .contains(responseCode0000);
        val SELECT_RESPONSE_MESSAGE_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_RESPONSE_MESSAGE_BY_RAAS_TXN_REF,raasTxnRef));
        System.out.println(SELECT_RESPONSE_MESSAGE_BY_RAAS_TXN_REF_VALUE);
        List<String> SELECT_RESPONSE_MESSAGE_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_RESPONSE_MESSAGE_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
        assertThat(SELECT_RESPONSE_MESSAGE_BY_RAAS_TXN_REF_VALUE_STRING)
                .contains(responseMessageFundsReserved);
        val SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_RESPONSE_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_RESPONSE,raasTxnRef));
        String SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_RESPONSE_VALUE_STRING = SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_RESPONSE_VALUE.toString();
        System.out.println(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_RESPONSE_VALUE_STRING);
        val SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_RESPONSE_VALUE_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
        System.out.println(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_RESPONSE_VALUE_TIMESTAMP_STRING);
        assertThat(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_RESPONSE_VALUE_STRING)
                .contains(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_RESPONSE_VALUE_TIMESTAMP_STRING);
    }
    @Test()
    @Description("30100-payd-raas-gateway :: POST v4/reserveAndTransact :: raas.reserve_fund_request all fields")
    @TmsLink("TECH-174323")
    public void testReserveAndTransactV4RaasReserveFundRequestAllFields() throws InterruptedException {
        //Failing because of https://jira.clickatell.com/browse/TECH-176071
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_VENDOR_DISCOUNT_PERCENTAGE_BY_VENDOR_ID, value_0point1,Vendor21));
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_MODEL_SELECTION, Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2, Vendor21, TestClient3, ProductAirtel_917));
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_TPV_CLIENT_SHARE, value_0point1, TestClient3, ProductAirtel_917));
        val jsonBody = setUpReserveAndTransactV4DataAdditionalDataWithProductFundSourceClientID(fundingSourceId_1500,AccountIdentifier,clientTxnRef,fundingSourceId,channelSessionId,ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundsReserved))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
        //Verify transaction status is "SUCCESS"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        Thread.sleep(20000);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, Integer.parseInt(ReserveAndTransactClient.TestClient3), queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString(ReserveAndTransactClient.Success));

        val SELECT_AMOUNT_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_AMOUNT_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ,raasTxnRef));
        System.out.println(SELECT_AMOUNT_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ_VALUE);
        List<String> SELECT_AMOUNT_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ_VALUE_STRING = Lists.transform(SELECT_AMOUNT_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ_VALUE, Functions.toStringFunction());
        assertThat(SELECT_AMOUNT_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ_VALUE_STRING.get(0))
                .contains(PurchaseAmount10000);
        val SELECT_CHANNEL_ID_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CHANNEL_ID_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ,raasTxnRef));
        List<String> SELECT_CHANNEL_ID_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ_VALUE_STRING = Lists.transform(SELECT_CHANNEL_ID_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ_VALUE, Functions.toStringFunction());
        assertThat(SELECT_CHANNEL_ID_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ_VALUE_STRING)
                .contains("7");
        val SELECT_CHANNEL_NAME_BY_ID_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CHANNEL_NAME_BY_ID,"7"));
        System.out.println(SELECT_CHANNEL_NAME_BY_ID_VALUE);
        List<String> SELECT_CHANNEL_NAME_BY_ID_VALUE_STRING = Lists.transform(SELECT_CHANNEL_NAME_BY_ID_VALUE, Functions.toStringFunction());
        assertThat(SELECT_CHANNEL_NAME_BY_ID_VALUE_STRING)
                .contains(String.valueOf(USSD));
        val SELECT_CHANNEL_SESSSION_ID_BY_RAAS_TXN_REF_RAAS_RES_FUND_REQ_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CHANNEL_SESSSION_ID_BY_RAAS_TXN_REF_RAAS_RES_FUND_REQ,raasTxnRef));
        List<String> SELECT_CHANNEL_SESSSION_ID_BY_RAAS_TXN_REF_RAAS_RES_FUND_REQ_VALUE_STRING = Lists.transform(SELECT_CHANNEL_SESSSION_ID_BY_RAAS_TXN_REF_RAAS_RES_FUND_REQ_VALUE, Functions.toStringFunction());
        System.out.println(SELECT_CHANNEL_SESSSION_ID_BY_RAAS_TXN_REF_RAAS_RES_FUND_REQ_VALUE_STRING);
        assertThat(SELECT_CHANNEL_SESSSION_ID_BY_RAAS_TXN_REF_RAAS_RES_FUND_REQ_VALUE_STRING)
                .contains(channelSessionId);
        val SELECT_PRODUCT_ID_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_PRODUCT_ID_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ,raasTxnRef));
        System.out.println(SELECT_PRODUCT_ID_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ_VALUE);
        List<String> SELECT_PRODUCT_ID_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ_VALUE_STRING = Lists.transform(SELECT_PRODUCT_ID_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ_VALUE, Functions.toStringFunction());
        assertThat(SELECT_PRODUCT_ID_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ_VALUE_STRING)
                .contains(ProductAirtel_917);
        val SELECT_PRODUCT_TYPE_ID_BY_RAAS_TXN_REF_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_PRODUCT_TYPE_ID_BY_RAAS_TXN_REF,ProductAirtel_917));
        System.out.println(SELECT_PRODUCT_TYPE_ID_BY_RAAS_TXN_REF_VALUE);
        List<String> SELECT_PRODUCT_TYPE_ID_BY_RAAS_TXN_REF_VALUE_STRING = Lists.transform(SELECT_PRODUCT_TYPE_ID_BY_RAAS_TXN_REF_VALUE, Functions.toStringFunction());
        assertThat(SELECT_PRODUCT_TYPE_ID_BY_RAAS_TXN_REF_VALUE_STRING)
                .contains(TestClient3);
        val SELECT_SOURCE_IDENTIFIER_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_SOURCE_IDENTIFIER_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ,raasTxnRef));
        System.out.println(SELECT_SOURCE_IDENTIFIER_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ_VALUE);
        List<String> SELECT_SOURCE_IDENTIFIER_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ_VALUE_STRING = Lists.transform(SELECT_SOURCE_IDENTIFIER_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ_VALUE, Functions.toStringFunction());
        assertThat(SELECT_SOURCE_IDENTIFIER_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ_VALUE_STRING)
                .contains(Identifier);
        val SELECT_TARGET_IDENTIFIER_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_TARGET_IDENTIFIER_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ,raasTxnRef));
        System.out.println(SELECT_TARGET_IDENTIFIER_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ_VALUE);
        List<String> SELECT_TARGET_IDENTIFIER_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ_VALUE_STRING = Lists.transform(SELECT_TARGET_IDENTIFIER_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ_VALUE, Functions.toStringFunction());
        assertThat(SELECT_TARGET_IDENTIFIER_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ_VALUE_STRING)
                .contains(Identifier);
        val SELECT_CREATED_BY_RAAS_TXN_REF_RAAS_RES_FUND_REQ_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CREATED_BY_RAAS_TXN_REF_RAAS_RES_FUND_REQ,raasTxnRef));
        String SELECT_CREATED_BY_RAAS_TXN_REF_RAAS_RES_FUND_REQ_VALUE_STRING = SELECT_CREATED_BY_RAAS_TXN_REF_RAAS_RES_FUND_REQ_VALUE.toString();
        System.out.println(SELECT_CREATED_BY_RAAS_TXN_REF_RAAS_RES_FUND_REQ_VALUE_STRING);
        val SELECT_CREATED_BY_RAAS_TXN_REF_RAAS_RES_FUND_REQ_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
        System.out.println(SELECT_CREATED_BY_RAAS_TXN_REF_RAAS_RES_FUND_REQ_TIMESTAMP_STRING);
        assertThat(SELECT_CREATED_BY_RAAS_TXN_REF_RAAS_RES_FUND_REQ_VALUE_STRING)
                .contains(SELECT_CREATED_BY_RAAS_TXN_REF_RAAS_RES_FUND_REQ_TIMESTAMP_STRING);
        val SELECT_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ,raasTxnRef));
        String TimeStamp = SELECT_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ_VALUE.toString();
        System.out.println(TimeStamp);
        val timestamp_string = now().format(ofPattern("yyyy-MM-dd HH"));
        System.out.println(timestamp_string);
        assertThat(TimeStamp)
                .contains(timestamp_string);
        val SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ,raasTxnRef));
        String SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ_VALUE_STRING = SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ_VALUE.toString();
        System.out.println(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ_VALUE_STRING);
        val SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ_VALUE_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
        System.out.println(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ_VALUE_TIMESTAMP_STRING);
        assertThat(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ_VALUE_STRING)
                .contains(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ_VALUE_TIMESTAMP_STRING);
        getFinancialTermsCalculate(ProductAirtel_917,TestClient3,PurchaseAmount10000)
                .then().assertThat().statusCode(SC_OK)
                .body("terms.reserveAmount", Matchers.is(Integer.parseInt(PurchaseAmount10000)))
                .body("terms.vendAmount", Matchers.is(Integer.parseInt(PurchaseAmount10000)))
                .body("terms.feeAmount", Matchers.is(Integer.parseInt(FeeAmount0)))
                .body("terms.settlementAmount", Matchers.is(Integer.parseInt(settlementAmount)))
                .body("terms.clientShareAmount", Matchers.is(Integer.parseInt(PurchaseAmount1000)))
                .body("terms.vendorShareAmount", Matchers.is(Integer.parseInt(FeeAmount0)));
    }
    @Test()
    @Description("30100-payd-raas-gateway :: POST v4/reserveAndTransact :: raas.reserve_fund_response all fields")
    @TmsLink("TECH-174324")
    public void testReserveAndTransactV4RaasReserveFundResponseAllFields() throws InterruptedException {
        //Failing because of https://jira.clickatell.com/browse/TECH-176071
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_VENDOR_DISCOUNT_PERCENTAGE_BY_VENDOR_ID, value_0point1,Vendor21));
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_MODEL_SELECTION, Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2, Vendor21, TestClient3, ProductAirtel_917));
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_TPV_CLIENT_SHARE, value_0point1, TestClient3, ProductAirtel_917));
        val jsonBody = setUpReserveAndTransactV4DataAdditionalDataWithProductFundSourceClientID(fundingSourceId_1500,AccountIdentifier,clientTxnRef,fundingSourceId,channelSessionId,ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundsReserved))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
        //Verify transaction status is "SUCCESS"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        Thread.sleep(20000);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, Integer.parseInt(ReserveAndTransactClient.TestClient3), queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString(ReserveAndTransactClient.Success));

        val SELECT_CREATED_BY_RAAS_TXN_REF_RAAS_RES_FUND_RES_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CREATED_BY_RAAS_TXN_REF_RAAS_RES_FUND_RES,raasTxnRef));
        String SELECT_CREATED_BY_RAAS_TXN_REF_RAAS_RES_FUND_RES_VALUE_STRING = SELECT_CREATED_BY_RAAS_TXN_REF_RAAS_RES_FUND_RES_VALUE.toString();
        System.out.println(SELECT_CREATED_BY_RAAS_TXN_REF_RAAS_RES_FUND_RES_VALUE_STRING);
        val SELECT_CREATED_BY_RAAS_TXN_REF_RAAS_RES_FUND_RES_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
        System.out.println(SELECT_CREATED_BY_RAAS_TXN_REF_RAAS_RES_FUND_RES_TIMESTAMP_STRING);
        assertThat(SELECT_CREATED_BY_RAAS_TXN_REF_RAAS_RES_FUND_RES_VALUE_STRING)
                .contains(SELECT_CREATED_BY_RAAS_TXN_REF_RAAS_RES_FUND_RES_TIMESTAMP_STRING);
        val SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_RAAS_RES_FUND_RES_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_RAAS_RES_FUND_RES,raasTxnRef));
        System.out.println(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_RAAS_RES_FUND_RES_VALUE);
        List<String> SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_RAAS_RES_FUND_RES_VALUE_STRING = Lists.transform(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_RAAS_RES_FUND_RES_VALUE, Functions.toStringFunction());
        assertThat(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_RAAS_RES_FUND_RES_VALUE_STRING)
                .contains(Event_Type_Raas_Res_Fund_Response);
        val SELECT_RESERVE_FUNDS_TXN_REF_BY_RAAS_TXN_REF_RAAS_RES_FUND_RES_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_RESERVE_FUNDS_TXN_REF_BY_RAAS_TXN_REF_RAAS_RES_FUND_RES,raasTxnRef));
        System.out.println(SELECT_RESERVE_FUNDS_TXN_REF_BY_RAAS_TXN_REF_RAAS_RES_FUND_RES_VALUE);
        List<String> SELECT_RESERVE_FUNDS_TXN_REF_BY_RAAS_TXN_REF_RAAS_RES_FUND_RES_VALUE_STRING = Lists.transform(SELECT_RESERVE_FUNDS_TXN_REF_BY_RAAS_TXN_REF_RAAS_RES_FUND_RES_VALUE, Functions.toStringFunction());
        assertThat(SELECT_RESERVE_FUNDS_TXN_REF_BY_RAAS_TXN_REF_RAAS_RES_FUND_RES_VALUE_STRING)
                .isNotNull();
        val SELECT_FUND_RESPONSE_CODE_BY_RAAS_TXN_REF_RAAS_RES_FUND_RES_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_FUND_RESPONSE_CODE_BY_RAAS_TXN_REF_RAAS_RES_FUND_RES,raasTxnRef));
        System.out.println(SELECT_FUND_RESPONSE_CODE_BY_RAAS_TXN_REF_RAAS_RES_FUND_RES_VALUE);
        List<String> SELECT_FUND_RESPONSE_CODE_BY_RAAS_TXN_REF_RAAS_RES_FUND_RES_VALUE_STRING = Lists.transform(SELECT_FUND_RESPONSE_CODE_BY_RAAS_TXN_REF_RAAS_RES_FUND_RES_VALUE, Functions.toStringFunction());
        assertThat(SELECT_FUND_RESPONSE_CODE_BY_RAAS_TXN_REF_RAAS_RES_FUND_RES_VALUE_STRING)
                .contains(responseCode0000);
        val SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_RES_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_RES,raasTxnRef));
        String SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_RES_VALUE_STRING = SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_RES_VALUE.toString();
        System.out.println(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_RES_VALUE_STRING);
        val SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_RES_VALUE_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
        System.out.println(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_RES_VALUE_TIMESTAMP_STRING);
        assertThat(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_RES_VALUE_STRING)
                .contains(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_RES_VALUE_TIMESTAMP_STRING);
    }
    @Test()
    @Description("30100-payd-raas-gateway :: POST v4/reserveAndTransact :: raas.ctx_request all fields")
    @TmsLink("TECH-174325")
    public void testReserveAndTransactV4RaasCtxRequestAllFields() throws InterruptedException {
        //Failing because of https://jira.clickatell.com/browse/TECH-176071
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_VENDOR_DISCOUNT_PERCENTAGE_BY_VENDOR_ID, value_0point1, Vendor21));
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_MODEL_SELECTION, Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2, Vendor21, TestClient3, ProductAirtel_917));
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_TPV_CLIENT_SHARE, value_0point1, TestClient3, ProductAirtel_917));
        val jsonBody = setUpReserveAndTransactV4DataAdditionalDataWithProductFundSourceClientID(fundingSourceId_1500, AccountIdentifier, clientTxnRef, fundingSourceId, channelSessionId, ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundsReserved))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
        //Verify transaction status is "SUCCESS"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        Thread.sleep(20000);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, Integer.parseInt(ReserveAndTransactClient.TestClient3), queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString(ReserveAndTransactClient.Success));

        val SELECT_CLIENT_TRANSACTION_ID_BY_RAAS_TXN_REF_FROM_CTX_REQ_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CLIENT_TRANSACTION_ID_BY_RAAS_TXN_REF_FROM_CTX_REQ, raasTxnRef));
        System.out.println(SELECT_CLIENT_TRANSACTION_ID_BY_RAAS_TXN_REF_FROM_CTX_REQ_VALUE);
        assertThat(SELECT_CLIENT_TRANSACTION_ID_BY_RAAS_TXN_REF_FROM_CTX_REQ_VALUE)
                .contains(raasTxnRef+"-0000");
        val SELECT_AMOUNT_BY_RAAS_TXN_REF_FROM_CTX_REQ_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_AMOUNT_BY_RAAS_TXN_REF_FROM_CTX_REQ, raasTxnRef));
        System.out.println(SELECT_AMOUNT_BY_RAAS_TXN_REF_FROM_CTX_REQ_VALUE);
        List<String> SELECT_AMOUNT_BY_RAAS_TXN_REF_FROM_CTX_REQ_VALUE_STRING = Lists.transform(SELECT_AMOUNT_BY_RAAS_TXN_REF_FROM_CTX_REQ_VALUE, Functions.toStringFunction());
        assertThat(SELECT_AMOUNT_BY_RAAS_TXN_REF_FROM_CTX_REQ_VALUE_STRING.get(0))
                .contains(PurchaseAmount10000);
        val SELECT_CHANNEL_INDICATOR_BY_RAAS_TXN_REF_FROM_CTX_REQ_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CHANNEL_INDICATOR_BY_RAAS_TXN_REF_FROM_CTX_REQ, raasTxnRef));
        List<String> SELECT_CHANNEL_INDICATOR_BY_RAAS_TXN_REF_FROM_CTX_REQ_VALUE_STRING = Lists.transform(SELECT_CHANNEL_INDICATOR_BY_RAAS_TXN_REF_FROM_CTX_REQ_VALUE, Functions.toStringFunction());
        assertThat(SELECT_CHANNEL_INDICATOR_BY_RAAS_TXN_REF_FROM_CTX_REQ_VALUE_STRING)
                .contains("7");
        val SELECT_CHANNEL_NAME_BY_ID_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CHANNEL_NAME_BY_ID, "7"));
        System.out.println(SELECT_CHANNEL_NAME_BY_ID_VALUE);
        List<String> SELECT_CHANNEL_NAME_BY_ID_VALUE_STRING = Lists.transform(SELECT_CHANNEL_NAME_BY_ID_VALUE, Functions.toStringFunction());
        assertThat(SELECT_CHANNEL_NAME_BY_ID_VALUE_STRING)
                .contains(String.valueOf(USSD));
        val SELECT_CHANNEL_SESSION_ID_BY_RAAS_TXN_REF_FROM_CTX_REQ_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CHANNEL_SESSION_ID_BY_RAAS_TXN_REF_FROM_CTX_REQ, raasTxnRef));
        List<String> SELECT_CHANNEL_SESSION_ID_BY_RAAS_TXN_REF_FROM_CTX_REQ_VALUE_STRING = Lists.transform(SELECT_CHANNEL_SESSION_ID_BY_RAAS_TXN_REF_FROM_CTX_REQ_VALUE, Functions.toStringFunction());
        System.out.println(SELECT_CHANNEL_SESSION_ID_BY_RAAS_TXN_REF_FROM_CTX_REQ_VALUE_STRING);
        assertThat(SELECT_CHANNEL_SESSION_ID_BY_RAAS_TXN_REF_FROM_CTX_REQ_VALUE_STRING)
                .contains(channelSessionId);
        val SELECT_CLIENT_ID_BY_RAAS_TXN_REF_FROM_CTX_REQ_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CLIENT_ID_BY_RAAS_TXN_REF_FROM_CTX_REQ, raasTxnRef));
        List<String> SELECT_CLIENT_ID_BY_RAAS_TXN_REF_FROM_CTX_REQ_VALUE_STRING = Lists.transform(SELECT_CLIENT_ID_BY_RAAS_TXN_REF_FROM_CTX_REQ_VALUE, Functions.toStringFunction());
        System.out.println(SELECT_CLIENT_ID_BY_RAAS_TXN_REF_FROM_CTX_REQ_VALUE_STRING);
        assertThat(SELECT_CLIENT_ID_BY_RAAS_TXN_REF_FROM_CTX_REQ_VALUE_STRING)
                .contains(TestClient3);
        val SELECT_ORIGIN_ID_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_ORIGIN_ID_BY_RAAS_TXN_REF_FROM_CTX_REQUEST, raasTxnRef));
        System.out.println(SELECT_ORIGIN_ID_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE);
        List<String> SELECT_ORIGIN_ID_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_STRING = Lists.transform(SELECT_ORIGIN_ID_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE, Functions.toStringFunction());
        assertThat(SELECT_ORIGIN_ID_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_STRING)
                .contains(Identifier);
        val SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_CTX_REQUEST, raasTxnRef));
        System.out.println(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE);
        List<String> SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_STRING = Lists.transform(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE, Functions.toStringFunction());
        assertThat(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_STRING)
                .contains(Event_Type_Ctx_Request);
        val SELECT_PRODUCT_ID_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_PRODUCT_ID_BY_RAAS_TXN_REF_FROM_CTX_REQUEST, raasTxnRef));
        System.out.println(SELECT_PRODUCT_ID_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE);
        List<String> SELECT_PRODUCT_ID_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_STRING = Lists.transform(SELECT_PRODUCT_ID_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE, Functions.toStringFunction());
        assertThat(SELECT_PRODUCT_ID_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_STRING)
                .contains(ProductAirtel_917);
        val SELECT_SOURCE_ID_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_SOURCE_ID_BY_RAAS_TXN_REF_FROM_CTX_REQUEST, raasTxnRef));
        System.out.println(SELECT_SOURCE_ID_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE);
        List<String> SELECT_SOURCE_ID_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_STRING = Lists.transform(SELECT_SOURCE_ID_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE, Functions.toStringFunction());
        assertThat(SELECT_SOURCE_ID_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_STRING)
                .contains(Identifier);
        val SELECT_CREATED_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_CTX_REQUEST, raasTxnRef));
        String SELECT_CREATED_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_STRING = SELECT_CREATED_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE.toString();
        System.out.println(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_STRING);
        val SELECT_CREATED_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
        System.out.println(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_TIMESTAMP_STRING);
        assertThat(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_STRING)
                .contains(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_TIMESTAMP_STRING);
        val SELECT_DATE_LOCAL_TXN_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_DATE_LOCAL_TXN_BY_RAAS_TXN_REF_FROM_CTX_REQUEST, raasTxnRef));
        String SELECT_DATE_LOCAL_TXN_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_STRING = SELECT_DATE_LOCAL_TXN_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE.toString();
        System.out.println(SELECT_DATE_LOCAL_TXN_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_STRING);
        val SELECT_DATE_LOCAL_TXN_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("MMdd"));
        System.out.println(SELECT_DATE_LOCAL_TXN_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_TIMESTAMP_STRING);
        assertThat(SELECT_DATE_LOCAL_TXN_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_STRING)
                .contains(SELECT_DATE_LOCAL_TXN_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_TIMESTAMP_STRING);
//        val SELECT_TIME_LOCAL_TRANSACTION_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_TIME_LOCAL_TRANSACTION_BY_RAAS_TXN_REF_FROM_CTX_REQUEST, raasTxnRef));
//        String SELECT_TIME_LOCAL_TRANSACTION_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_STRING = SELECT_TIME_LOCAL_TRANSACTION_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE.toString();
//        System.out.println(SELECT_TIME_LOCAL_TRANSACTION_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_STRING);
//        val SELECT_TIME_LOCAL_TRANSACTION_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
//        System.out.println(SELECT_TIME_LOCAL_TRANSACTION_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_TIMESTAMP_STRING);
//        assertThat(SELECT_TIME_LOCAL_TRANSACTION_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_STRING)
//                .contains(SELECT_TIME_LOCAL_TRANSACTION_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_TIMESTAMP_STRING);
        val SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_CTX_REQUEST,raasTxnRef));
        String SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_STRING = SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE.toString();
        System.out.println(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_STRING);
        val SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
        System.out.println(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_TIMESTAMP_STRING);
        assertThat(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_STRING)
                .contains(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_TIMESTAMP_STRING);
    }
    @Test()
    @Description("30100-payd-raas-gateway :: POST v4/reserveAndTransact :: raas.ctx_response all fields")
    @TmsLink("TECH-174326")
    public void testReserveAndTransactV4RaasCtxResponseAllFields() throws InterruptedException {
        //Failing because of https://jira.clickatell.com/browse/TECH-176071
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_VENDOR_DISCOUNT_PERCENTAGE_BY_VENDOR_ID, value_0point1, Vendor21));
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_MODEL_SELECTION, Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2, Vendor21, TestClient3, ProductAirtel_917));
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_TPV_CLIENT_SHARE, value_0point1, TestClient3, ProductAirtel_917));
        val jsonBody = setUpReserveAndTransactV4DataAdditionalDataWithProductFundSourceClientID(fundingSourceId_1500, AccountIdentifier, clientTxnRef, fundingSourceId, channelSessionId, ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundsReserved))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
        //Verify transaction status is "SUCCESS"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        Thread.sleep(20000);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, Integer.parseInt(ReserveAndTransactClient.TestClient3), queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString(ReserveAndTransactClient.Success));

        val SELECT_CLIENT_TRANSACTION_ID_BY_RAAS_TXN_REF_FROM_CTX_RES_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CLIENT_TRANSACTION_ID_BY_RAAS_TXN_REF_FROM_CTX_RES, raasTxnRef));
        System.out.println(SELECT_CLIENT_TRANSACTION_ID_BY_RAAS_TXN_REF_FROM_CTX_RES_VALUE);
        assertThat(SELECT_CLIENT_TRANSACTION_ID_BY_RAAS_TXN_REF_FROM_CTX_RES_VALUE)
                .contains(raasTxnRef+"-0000");
        val SELECT_AMOUNT_BY_RAAS_TXN_REF_FROM_CTX_RES_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_AMOUNT_BY_RAAS_TXN_REF_FROM_CTX_RES, raasTxnRef));
        System.out.println(SELECT_AMOUNT_BY_RAAS_TXN_REF_FROM_CTX_RES_VALUE);
        List<String> SELECT_AMOUNT_BY_RAAS_TXN_REF_FROM_CTX_RES_VALUE_STRING = Lists.transform(SELECT_AMOUNT_BY_RAAS_TXN_REF_FROM_CTX_RES_VALUE, Functions.toStringFunction());
        assertThat(SELECT_AMOUNT_BY_RAAS_TXN_REF_FROM_CTX_RES_VALUE_STRING.get(0))
                .contains(PurchaseAmount10000);
        val SELECT_CHANNEL_INDICATOR_BY_RAAS_TXN_REF_FROM_CTX_RES_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CHANNEL_INDICATOR_BY_RAAS_TXN_REF_FROM_CTX_RES, raasTxnRef));
        List<String> SELECT_CHANNEL_INDICATOR_BY_RAAS_TXN_REF_FROM_CTX_RES_VALUE_STRING = Lists.transform(SELECT_CHANNEL_INDICATOR_BY_RAAS_TXN_REF_FROM_CTX_RES_VALUE, Functions.toStringFunction());
        System.out.println(SELECT_CHANNEL_INDICATOR_BY_RAAS_TXN_REF_FROM_CTX_RES_VALUE_STRING);
        assertThat(SELECT_CHANNEL_INDICATOR_BY_RAAS_TXN_REF_FROM_CTX_RES_VALUE_STRING)
                .contains("07");
        val SELECT_CHANNEL_NAME_BY_RAAS_TXN_REF_FROM_CTX_RES_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CHANNEL_NAME_BY_RAAS_TXN_REF_FROM_CTX_RES, "7"));
        System.out.println(SELECT_CHANNEL_NAME_BY_RAAS_TXN_REF_FROM_CTX_RES_VALUE);
        List<String> SELECT_CHANNEL_NAME_BY_RAAS_TXN_REF_FROM_CTX_RES_VALUE_STRING = Lists.transform(SELECT_CHANNEL_NAME_BY_RAAS_TXN_REF_FROM_CTX_RES_VALUE, Functions.toStringFunction());
        assertThat(SELECT_CHANNEL_NAME_BY_RAAS_TXN_REF_FROM_CTX_RES_VALUE_STRING)
                .isEmpty();
        val SELECT_CLIENT_ID_BY_RAAS_TXN_REF_FROM_CTX_RES_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CLIENT_ID_BY_RAAS_TXN_REF_FROM_CTX_RES, raasTxnRef));
        List<String> SELECT_CLIENT_ID_BY_RAAS_TXN_REF_FROM_CTX_RES_VALUE_STRING = Lists.transform(SELECT_CLIENT_ID_BY_RAAS_TXN_REF_FROM_CTX_RES_VALUE, Functions.toStringFunction());
        System.out.println(SELECT_CLIENT_ID_BY_RAAS_TXN_REF_FROM_CTX_RES_VALUE_STRING);
        assertThat(SELECT_CLIENT_ID_BY_RAAS_TXN_REF_FROM_CTX_RES_VALUE_STRING)
                .contains(TestClient3);
        val SELECT_ORIGIN_ID_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_ORIGIN_ID_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE, raasTxnRef));
        System.out.println(SELECT_ORIGIN_ID_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE);
        List<String> SELECT_ORIGIN_ID_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_STRING = Lists.transform(SELECT_ORIGIN_ID_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE, Functions.toStringFunction());
        assertThat(SELECT_ORIGIN_ID_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_STRING)
                .contains(Identifier);
        val SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE, raasTxnRef));
        System.out.println(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE);
        List<String> SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_STRING = Lists.transform(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE, Functions.toStringFunction());
        assertThat(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_STRING)
                .contains(Event_Type_Ctx_Response);
        val SELECT_PRODUCT_ID_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_PRODUCT_ID_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE, raasTxnRef));
        System.out.println(SELECT_PRODUCT_ID_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE);
        List<String> SELECT_PRODUCT_ID_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_STRING = Lists.transform(SELECT_PRODUCT_ID_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE, Functions.toStringFunction());
        assertThat(SELECT_PRODUCT_ID_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_STRING)
                .contains(ProductAirtel_917);
        val SELECT_SOURCE_ID_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_SOURCE_ID_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE, raasTxnRef));
        System.out.println(SELECT_SOURCE_ID_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE);
        List<String> SELECT_SOURCE_ID_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_STRING = Lists.transform(SELECT_SOURCE_ID_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE, Functions.toStringFunction());
        assertThat(SELECT_SOURCE_ID_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_STRING)
                .contains(Identifier);
        val SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE, raasTxnRef));
        System.out.println(SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE);
        List<String> SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_STRING = Lists.transform(SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE, Functions.toStringFunction());
        assertThat(SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_STRING)
                .contains(FeeAmount0);
        val SELECT_VENDOR_REFERENCE_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_VENDOR_REFERENCE_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE, raasTxnRef));
        System.out.println(SELECT_VENDOR_REFERENCE_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE);
        List<String> SELECT_VENDOR_REFERENCE_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_STRING = Lists.transform(SELECT_VENDOR_REFERENCE_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE, Functions.toStringFunction());
        assertThat(SELECT_VENDOR_REFERENCE_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_STRING)
                .isNotNull();
        val SELECT_CREATED_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE, raasTxnRef));
        String SELECT_CREATED_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_STRING = SELECT_CREATED_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE.toString();
        System.out.println(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_STRING);
        val SELECT_CREATED_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
        System.out.println(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_TIMESTAMP_STRING);
        assertThat(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_STRING)
                .contains(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_TIMESTAMP_STRING);
        val SELECT_DATE_LOCAL_TXN_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_DATE_LOCAL_TXN_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE, raasTxnRef));
        String SELECT_DATE_LOCAL_TXN_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_STRING = SELECT_DATE_LOCAL_TXN_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE.toString();
        System.out.println(SELECT_DATE_LOCAL_TXN_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_STRING);
        val SELECT_DATE_LOCAL_TXN_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("MMdd"));
        System.out.println(SELECT_DATE_LOCAL_TXN_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_TIMESTAMP_STRING);
        assertThat(SELECT_DATE_LOCAL_TXN_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_STRING)
                .contains(SELECT_DATE_LOCAL_TXN_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_TIMESTAMP_STRING);
        val SELECT_TRANSMISSION_DATE_TIME_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_TRANSMISSION_DATE_TIME_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE, raasTxnRef));
        String SELECT_TRANSMISSION_DATE_TIME_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_STRING = SELECT_TRANSMISSION_DATE_TIME_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE.toString();
        System.out.println(SELECT_TRANSMISSION_DATE_TIME_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_STRING);
        val SELECT_TRANSMISSION_DATE_TIME_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("MMdd"));
        System.out.println(SELECT_TRANSMISSION_DATE_TIME_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_TIMESTAMP_STRING);
        assertThat(SELECT_TRANSMISSION_DATE_TIME_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_STRING)
                .contains(SELECT_TRANSMISSION_DATE_TIME_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_TIMESTAMP_STRING);
        val SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE,raasTxnRef));
        String SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_STRING = SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE.toString();
        System.out.println(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_STRING);
        val SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
        System.out.println(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_TIMESTAMP_STRING);
        assertThat(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_STRING)
                .contains(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_TIMESTAMP_STRING);
    }
    @Test()
    @Description("30100-payd-raas-gateway :: POST v4/reserveAndTransact :: raas.transaction_result_request all fields")
    @TmsLink("TECH-174327")
    public void testReserveAndTransactV4RaasTransactionResultRequestAllFields() throws InterruptedException {
        //Failing because of https://jira.clickatell.com/browse/TECH-176071
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_VENDOR_DISCOUNT_PERCENTAGE_BY_VENDOR_ID, value_0point1, Vendor21));
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_MODEL_SELECTION, Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2, Vendor21, TestClient3, ProductAirtel_917));
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_TPV_CLIENT_SHARE, value_0point1, TestClient3, ProductAirtel_917));
        val jsonBody = setUpReserveAndTransactV4DataAdditionalDataWithProductFundSourceClientID(fundingSourceId_1500, AccountIdentifier, clientTxnRef, fundingSourceId, channelSessionId, ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundsReserved))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
        //Verify transaction status is "SUCCESS"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        Thread.sleep(20000);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, Integer.parseInt(ReserveAndTransactClient.TestClient3), queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString(ReserveAndTransactClient.Success));


        val SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ, raasTxnRef));
        System.out.println(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_VALUE);
        List<String> SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_VALUE_STRING = Lists.transform(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_VALUE, Functions.toStringFunction());
        assertThat(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_VALUE_STRING)
                .contains(Event_Type_Transaction_Request);
        val SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ, raasTxnRef));
        System.out.println(SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_VALUE);
        List<String> SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_VALUE_STRING = Lists.transform(SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_VALUE, Functions.toStringFunction());
        assertThat(SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_VALUE_STRING)
                .contains(responseCode0000);
        val SELECT_CREATED_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ, raasTxnRef));
        String SELECT_CREATED_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_VALUE_STRING = SELECT_CREATED_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_VALUE.toString();
        System.out.println(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_VALUE_STRING);
        val SELECT_CREATED_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
        System.out.println(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_TIMESTAMP_STRING);
        assertThat(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_VALUE_STRING)
                .contains(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_TIMESTAMP_STRING);
        val SELECT_TIMESTAMP_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_TIMESTAMP_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ,raasTxnRef));
        String TimeStamp = SELECT_TIMESTAMP_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_VALUE.toString();
        System.out.println(TimeStamp);
        val timestamp_string = now().format(ofPattern("yyyy-MM-dd HH"));
        System.out.println(timestamp_string);
        assertThat(TimeStamp)
                .contains(timestamp_string);
        val SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ,raasTxnRef));
        String SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_VALUE_STRING = SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_VALUE.toString();
        System.out.println(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_VALUE_STRING);
        val SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_VALUE_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
        System.out.println(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_VALUE_TIMESTAMP_STRING);
        assertThat(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_VALUE_STRING)
                .contains(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ_VALUE_TIMESTAMP_STRING);
    }
    @Test()
    @Description("30100-payd-raas-gateway :: POST v4/reserveAndTransact :: raas.transaction_result_response all fields")
    @TmsLink("TECH-174328")
    public void testReserveAndTransactV4RaasTransactionResultResponseAllFields() throws InterruptedException {
        //Failing because of https://jira.clickatell.com/browse/TECH-176071
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_VENDOR_DISCOUNT_PERCENTAGE_BY_VENDOR_ID, value_0point1, Vendor21));
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_MODEL_SELECTION, Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2, Vendor21, TestClient3, ProductAirtel_917));
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_TPV_CLIENT_SHARE, value_0point1, TestClient3, ProductAirtel_917));
        val jsonBody = setUpReserveAndTransactV4DataAdditionalDataWithProductFundSourceClientID(fundingSourceId_1500, AccountIdentifier, clientTxnRef, fundingSourceId, channelSessionId, ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundsReserved))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
        //Verify transaction status is "SUCCESS"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        Thread.sleep(20000);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, Integer.parseInt(ReserveAndTransactClient.TestClient3), queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString(ReserveAndTransactClient.Success));


        val SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES, raasTxnRef));
        System.out.println(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES_VALUE);
        List<String> SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES_VALUE_STRING = Lists.transform(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES_VALUE, Functions.toStringFunction());
        assertThat(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES_VALUE_STRING)
                .contains(Event_Type_Transaction_Response);
        val SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES, raasTxnRef));
        System.out.println(SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES_VALUE);
        List<String> SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES_VALUE_STRING = Lists.transform(SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES_VALUE, Functions.toStringFunction());
        assertThat(SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES_VALUE_STRING)
                .contains(responseCode202);
        val SELECT_CREATED_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES, raasTxnRef));
        String SELECT_CREATED_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES_VALUE_STRING = SELECT_CREATED_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES_VALUE.toString();
        System.out.println(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES_VALUE_STRING);
        val SELECT_CREATED_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
        System.out.println(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES_TIMESTAMP_STRING);
        assertThat(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES_VALUE_STRING)
                .contains(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES_TIMESTAMP_STRING);
        val SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES,raasTxnRef));
        String SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES_VALUE_STRING = SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES_VALUE.toString();
        System.out.println(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES_VALUE_STRING);
        val SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES_VALUE_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
        System.out.println(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES_VALUE_TIMESTAMP_STRING);
        assertThat(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES_VALUE_STRING)
                .contains(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES_VALUE_TIMESTAMP_STRING);
    }
    @Test()
    @Description("30100-payd-raas-gateway :: POST v4/reserveAndTransact :: check all fields in the \"ctx\" DB \"tran_log\" table")
    @TmsLink("TECH-176901")
    public void testReserveAndTransactV4CtxTranLogAllFields() throws InterruptedException {
        //Failing because of https://jira.clickatell.com/browse/TECH-176071
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_VENDOR_DISCOUNT_PERCENTAGE_BY_VENDOR_ID, value_0point1, Vendor21));
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_MODEL_SELECTION, Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2, Vendor21, TestClient3, ProductAirtel_917));
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_TPV_CLIENT_SHARE, value_0point1, TestClient3, ProductAirtel_917));
        closeDBConnection();
        val jsonBody = setUpReserveAndTransactV4DataAdditionalDataWithProductFundSourceClientID(fundingSourceId_1500, AccountIdentifier, clientTxnRef, fundingSourceId, channelSessionId, ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundsReserved))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
        //Verify transaction status is "SUCCESS"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        Thread.sleep(20000);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, Integer.parseInt(ReserveAndTransactClient.TestClient3), queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString(ReserveAndTransactClient.Success));

        val SELECT_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, raasTxnRef));
        System.out.println(SELECT_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        assertThat(SELECT_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE)
                .isNotNull();
        val SELECT_CLIENT_STAN_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_CLIENT_STAN_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, raasTxnRef));
        System.out.println(SELECT_CLIENT_STAN_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        List<String> SELECT_CLIENT_STAN_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = Lists.transform(SELECT_CLIENT_STAN_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE, Functions.toStringFunction());
        assertThat(SELECT_CLIENT_STAN_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING.get(0))
                .isNotNull();
        val SELECT_END_ACC_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_END_ACC_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, raasTxnRef));
        System.out.println(SELECT_END_ACC_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        assertThat(SELECT_END_ACC_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE)
                .containsNull();
        //val SELECT_END_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_END_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, raasTxnRef));
//        String SELECT_END_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = SELECT_END_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE.toString();
//        System.out.println(SELECT_END_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING);
        //System.out.println(SELECT_END_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
//        val SELECT_END_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
//        System.out.println(SELECT_END_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_TIMESTAMP_STRING);
//        assertThat(SELECT_END_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING)
//                .contains(SELECT_END_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_TIMESTAMP_STRING);
//        val SELECT_END_VENDOR_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_END_VENDOR_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, raasTxnRef));
//        String SELECT_END_VENDOR_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = SELECT_END_VENDOR_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE.toString();
//        System.out.println(SELECT_END_VENDOR_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING);
//        val SELECT_END_VENDOR_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
//        System.out.println(SELECT_END_VENDOR_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_TIMESTAMP_STRING);
//        assertThat(SELECT_END_VENDOR_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING)
//                .contains(SELECT_END_VENDOR_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_TIMESTAMP_STRING);
        val SELECT_INTERNAL_RES_REASON_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_INTERNAL_RES_REASON_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, raasTxnRef));
        System.out.println(SELECT_INTERNAL_RES_REASON_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        assertThat(SELECT_INTERNAL_RES_REASON_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE)
                .containsNull();
        val SELECT_MESSAGE_IDENTIFIER_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_MESSAGE_IDENTIFIER_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, raasTxnRef));
        List<String> SELECT_MESSAGE_IDENTIFIER_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = Lists.transform(SELECT_MESSAGE_IDENTIFIER_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE, Functions.toStringFunction());
        System.out.println(SELECT_MESSAGE_IDENTIFIER_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING);
        assertThat(SELECT_MESSAGE_IDENTIFIER_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING)
                .contains(FeeAmount0);
        val SELECT_ORIGIN_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_ORIGIN_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, raasTxnRef));
        System.out.println(SELECT_ORIGIN_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        List<String> SELECT_ORIGIN_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = Lists.transform(SELECT_ORIGIN_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE, Functions.toStringFunction());
        assertThat(SELECT_ORIGIN_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING)
                .contains(Identifier);
        val SELECT_ORIGINATING_SERVICE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_ORIGINATING_SERVICE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, raasTxnRef));
        System.out.println(SELECT_ORIGINATING_SERVICE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        List<String> SELECT_ORIGINATING_SERVICE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = Lists.transform(SELECT_ORIGINATING_SERVICE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE, Functions.toStringFunction());
        assertThat(SELECT_ORIGINATING_SERVICE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING)
                .isNotNull();
        val SELECT_PURCHASE_AMOUNT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_PURCHASE_AMOUNT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, raasTxnRef));
        System.out.println(SELECT_PURCHASE_AMOUNT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        List<String> SELECT_PURCHASE_AMOUNT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = Lists.transform(SELECT_PURCHASE_AMOUNT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE, Functions.toStringFunction());
        assertThat(SELECT_PURCHASE_AMOUNT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING)
                .contains(PurchaseAmount10000);
        val SELECT_QUANTITY_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_QUANTITY_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, raasTxnRef));
        System.out.println(SELECT_INTERNAL_RES_REASON_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        assertThat(SELECT_QUANTITY_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE)
                .containsNull();
        val SELECT_TXN_RES_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_TXN_RES_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, raasTxnRef));
        List<String> SELECT_TXN_RES_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = Lists.transform(SELECT_TXN_RES_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE, Functions.toStringFunction());
        System.out.println(SELECT_TXN_RES_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING);
        assertThat(SELECT_TXN_RES_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING)
                .contains(FeeAmount0);
        val SELECT_REV_TXN_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_REV_TXN_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, raasTxnRef));
        System.out.println(SELECT_REV_TXN_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        assertThat(SELECT_REV_TXN_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE)
                .containsNull();
        val SELECT_START_ACCOUNT_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_START_ACCOUNT_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG,raasTxnRef));
        System.out.println(SELECT_START_ACCOUNT_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        assertThat(SELECT_START_ACCOUNT_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE)
                .containsNull();
//        val SELECT_START_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_START_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, raasTxnRef));
//        System.out.println(SELECT_START_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
//        String SELECT_START_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = SELECT_START_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE.toString();
//        System.out.println(SELECT_START_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING);
//        val SELECT_START_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
//        System.out.println(SELECT_START_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_TIMESTAMP_STRING);
//        assertThat(SELECT_START_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING)
//                .contains(SELECT_START_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_TIMESTAMP_STRING);
//        val SELECT_START_VENDOR_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_START_VENDOR_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, raasTxnRef));
//        String SELECT_START_VENDOR_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = SELECT_START_VENDOR_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE.toString();
//        System.out.println(SELECT_START_VENDOR_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING);
//        val SELECT_START_VENDOR_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
//        System.out.println(SELECT_START_VENDOR_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_TIMESTAMP_STRING);
//        assertThat(SELECT_START_VENDOR_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING)
//                .contains(SELECT_START_VENDOR_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_TIMESTAMP_STRING);
        val SELECT_TXN_STATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_TXN_STATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, raasTxnRef));
        System.out.println(SELECT_TXN_STATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        List<String> SELECT_TXN_STATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = Lists.transform(SELECT_TXN_STATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE, Functions.toStringFunction());
        assertThat(SELECT_TXN_STATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING)
                .contains("C");
        val SELECT_TXN_TYPE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_TXN_TYPE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, raasTxnRef));
        System.out.println(SELECT_TXN_TYPE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        List<String> SELECT_TXN_TYPE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = Lists.transform(SELECT_TXN_TYPE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE, Functions.toStringFunction());
        assertThat(SELECT_TXN_TYPE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING)
                .contains("P");
        val SELECT_VENDOR_REF_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_VENDOR_REF_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, raasTxnRef));
        System.out.println(SELECT_VENDOR_REF_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        List<String> SELECT_VENDOR_REF_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = Lists.transform(SELECT_VENDOR_REF_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE, Functions.toStringFunction());
        assertThat(SELECT_VENDOR_REF_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING)
                .isNotNull();
        val SELECT_VENDOR_RES_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_VENDOR_RES_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, raasTxnRef));
        System.out.println(SELECT_VENDOR_RES_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        List<String> SELECT_VENDOR_RES_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = Lists.transform(SELECT_VENDOR_RES_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE, Functions.toStringFunction());
        assertThat(SELECT_VENDOR_RES_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING)
                .contains(PurchaseAmount9900);
        val SELECT_VENDOR_STAN_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_VENDOR_STAN_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, raasTxnRef));
        System.out.println(SELECT_VENDOR_STAN_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        assertThat(SELECT_VENDOR_STAN_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE)
                .containsNull();
        val SELECT_CLIENT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_CLIENT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, raasTxnRef));
        System.out.println(SELECT_CLIENT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        List<String> SELECT_CLIENT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = Lists.transform(SELECT_CLIENT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE, Functions.toStringFunction());
        assertThat(SELECT_CLIENT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING)
                .contains(TestClient3);
        val SELECT_PRODUCT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_PRODUCT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, raasTxnRef));
        System.out.println(SELECT_PRODUCT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        List<String> SELECT_PRODUCT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = Lists.transform(SELECT_PRODUCT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE, Functions.toStringFunction());
        assertThat(SELECT_PRODUCT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING)
                .contains(ProductAirtel_917);
        val SELECT_VENDOR_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_VENDOR_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, raasTxnRef));
        System.out.println(SELECT_VENDOR_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        List<String> SELECT_VENDOR_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = Lists.transform(SELECT_VENDOR_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE, Functions.toStringFunction());
        assertThat(SELECT_VENDOR_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING)
                .contains(Vendor21);
        val SELECT_SYSTEM_RES_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_SYSTEM_RES_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, raasTxnRef));
        List<String> SELECT_SYSTEM_RES_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = Lists.transform(SELECT_SYSTEM_RES_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE, Functions.toStringFunction());
        System.out.println(SELECT_SYSTEM_RES_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING);
        assertThat(SELECT_SYSTEM_RES_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING)
                .contains(FeeAmount0);
        val SELECT_RES_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_RES_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, raasTxnRef));
        System.out.println(SELECT_RES_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        assertThat(SELECT_RES_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE)
                .containsNull();
        val SELECT_FEE_AMOUNT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_FEE_AMOUNT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, raasTxnRef));
        System.out.println(SELECT_FEE_AMOUNT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        assertThat(SELECT_FEE_AMOUNT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE)
                .containsNull();
        val SELECT_RECON_NOTE_CLIENT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_RECON_NOTE_CLIENT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, raasTxnRef));
        System.out.println(SELECT_RECON_NOTE_CLIENT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        assertThat(SELECT_RECON_NOTE_CLIENT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE)
                .containsNull();
        val SELECT_RECON_STATE_CLIENT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_RECON_STATE_CLIENT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, raasTxnRef));
        System.out.println(SELECT_RECON_STATE_CLIENT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        assertThat(SELECT_RECON_STATE_CLIENT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE)
                .containsNull();
        val SELECT_RECON_STATE_VENDOR_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_RECON_STATE_VENDOR_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, raasTxnRef));
        System.out.println(SELECT_RECON_STATE_VENDOR_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        assertThat(SELECT_RECON_STATE_VENDOR_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE)
                .containsNull();
        val SELECT_RECON_NOTE_VENDOR_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_RECON_NOTE_VENDOR_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, raasTxnRef));
        System.out.println(SELECT_RECON_NOTE_VENDOR_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        assertThat(SELECT_RECON_NOTE_VENDOR_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE)
                .containsNull();
//        val SELECT_LAST_UPDATED_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_LAST_UPDATED_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, raasTxnRef));
//        String SELECT_LAST_UPDATED_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = SELECT_LAST_UPDATED_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE.toString();
//        System.out.println(SELECT_LAST_UPDATED_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING);
//        val SELECT_LAST_UPDATED_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
//        System.out.println(SELECT_LAST_UPDATED_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_TIMESTAMP_STRING);
//        assertThat(SELECT_LAST_UPDATED_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING)
//                .contains(SELECT_LAST_UPDATED_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_TIMESTAMP_STRING);
        val SELECT_COMM_MODEL_TYPE_BY_CLIENT_VENDOR_PROD_TYPE_ID_FROM_COMM_MODEL_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_COMM_MODEL_TYPE_BY_CLIENT_VENDOR_PROD_TYPE_ID_FROM_COMM_MODEL, TestClient3, Vendor21, TestClient3));
        List<String> SELECT_COMM_MODEL_TYPE_BY_CLIENT_VENDOR_PROD_TYPE_ID_FROM_COMM_MODEL_VALUE_STRING = Lists.transform(SELECT_COMM_MODEL_TYPE_BY_CLIENT_VENDOR_PROD_TYPE_ID_FROM_COMM_MODEL_VALUE, Functions.toStringFunction());
        System.out.println(SELECT_COMM_MODEL_TYPE_BY_CLIENT_VENDOR_PROD_TYPE_ID_FROM_COMM_MODEL_VALUE_STRING);
        assertThat(SELECT_COMM_MODEL_TYPE_BY_CLIENT_VENDOR_PROD_TYPE_ID_FROM_COMM_MODEL_VALUE_STRING)
                .contains(CommissionModel);
        val SELECT_COMM_AMOUNT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_COMM_AMOUNT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, raasTxnRef));
        System.out.println(SELECT_COMM_AMOUNT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        List<String> SELECT_COMM_AMOUNT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = Lists.transform(SELECT_COMM_AMOUNT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE, Functions.toStringFunction());
        assertThat(SELECT_COMM_AMOUNT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING)
                .contains(FeeAmount0);
        val SELECT_GLOBAL_REASON_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_GLOBAL_REASON_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, raasTxnRef));
        System.out.println(SELECT_GLOBAL_REASON_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        assertThat(SELECT_GLOBAL_REASON_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE)
                .containsNull();
        val SELECT_CHANNEL_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_CHANNEL_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, raasTxnRef));
        System.out.println(SELECT_CHANNEL_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        List<String> SELECT_CHANNEL_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = Lists.transform(SELECT_CHANNEL_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE, Functions.toStringFunction());
        assertThat(SELECT_CHANNEL_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING)
                .contains(ChannelID_7);
        val SELECT_API_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_API_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, raasTxnRef));
        System.out.println(SELECT_API_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        List<String> SELECT_API_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = Lists.transform(SELECT_API_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE, Functions.toStringFunction());
        assertThat(SELECT_API_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING)
                .contains(Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2);
        val SELECT_COMM_AMOUNT_INTERNAL_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_COMM_AMOUNT_INTERNAL_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, raasTxnRef));
        System.out.println(SELECT_COMM_AMOUNT_INTERNAL_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        List<String> SELECT_COMM_AMOUNT_INTERNAL_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = Lists.transform(SELECT_COMM_AMOUNT_INTERNAL_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE, Functions.toStringFunction());
        assertThat(SELECT_COMM_AMOUNT_INTERNAL_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING)
                .contains(FeeAmount0);

    }
    @Test()
    @Description("30100-payd-raas-gateway :: POST v4/reserveAndTransact :: check all fields in the \"ctx\" DB \"tran_log_channel\" table ")
    @TmsLink("TECH-176987")
    public void testReserveAndTransactV4CtxTranLogChannelAllFields() throws InterruptedException {
        //Failing because of https://jira.clickatell.com/browse/TECH-176071
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_VENDOR_DISCOUNT_PERCENTAGE_BY_VENDOR_ID, value_0point1, Vendor21));
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_MODEL_SELECTION, Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2, Vendor21, TestClient3, ProductAirtel_917));
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_TPV_CLIENT_SHARE, value_0point1, TestClient3, ProductAirtel_917));
        closeDBConnection();
        val jsonBody = setUpReserveAndTransactV4DataAdditionalDataWithProductFundSourceClientID(fundingSourceId_1500, AccountIdentifier, clientTxnRef, fundingSourceId, channelSessionId, TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundsReserved))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
        //Verify transaction status is "SUCCESS"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        Thread.sleep(20000);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, Integer.parseInt(ReserveAndTransactClient.TestClient3), queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString(ReserveAndTransactClient.Success));

        val SELECT_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, raasTxnRef));
        System.out.println(SELECT_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        assertThat(SELECT_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE)
                .isNotNull();
        String SELECT_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = SELECT_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE.get(0);
        System.out.println(SELECT_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING);
        val SELECT_TRANSACTION_ID_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_TRANSACTION_ID_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL, SELECT_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING));
        System.out.println(SELECT_TRANSACTION_ID_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL_VALUE);
        List<String> SELECT_TRANSACTION_ID_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL_VALUE_STRING = Lists.transform(SELECT_TRANSACTION_ID_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL_VALUE, Functions.toStringFunction());
        assertThat(SELECT_TRANSACTION_ID_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL_VALUE_STRING.get(0))
                .contains(SELECT_TRANSACTION_ID_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL_VALUE);
        val SELECT_CHANNEL_SESSION_ID_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_CHANNEL_SESSION_ID_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL, SELECT_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING));
        System.out.println(SELECT_CHANNEL_SESSION_ID_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL_VALUE);
        List<String> SELECT_CHANNEL_SESSION_ID_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL_VALUE_STRING = Lists.transform(SELECT_CHANNEL_SESSION_ID_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL_VALUE, Functions.toStringFunction());
        assertThat(SELECT_CHANNEL_SESSION_ID_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL_VALUE_STRING)
                .contains(channelSessionId);
        val SELECT_CHANNEL_NAME_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_CHANNEL_NAME_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL, SELECT_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING));
        System.out.println(SELECT_CHANNEL_NAME_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL_VALUE);
        List<String> SELECT_CHANNEL_NAME_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL_VALUE_STRING = Lists.transform(SELECT_CHANNEL_NAME_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL_VALUE, Functions.toStringFunction());
        assertThat(SELECT_CHANNEL_NAME_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL_VALUE_STRING)
                .contains("USSD");
        val SELECT_CHANNEL_ID_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_CHANNEL_ID_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL, SELECT_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING));
        System.out.println(SELECT_CHANNEL_ID_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL_VALUE);
        List<String> SELECT_CHANNEL_ID_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL_VALUE_STRING = Lists.transform(SELECT_CHANNEL_ID_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL_VALUE, Functions.toStringFunction());
        assertThat(SELECT_CHANNEL_ID_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL_VALUE_STRING)
                .contains(UssdID);
        val SELECT_INSERT_TIMESTAMP_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_INSERT_TIMESTAMP_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL, SELECT_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING));
        String SELECT_INSERT_TIMESTAMP_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL_VALUE_STRING = SELECT_INSERT_TIMESTAMP_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL_VALUE.toString();
        System.out.println(SELECT_INSERT_TIMESTAMP_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL_VALUE_STRING);
        val SELECT_INSERT_TIMESTAMP_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
        System.out.println(SELECT_INSERT_TIMESTAMP_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL_TIMESTAMP_STRING);
        assertThat(SELECT_INSERT_TIMESTAMP_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL_VALUE_STRING)
                .contains(SELECT_INSERT_TIMESTAMP_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL_TIMESTAMP_STRING);

    }
    @Test()
    @Description("30100-payd-raas-gateway :: POST v4/reserveAndTransact :: check all fields in the \"ctx\" DB \"tran_log_ext\" table  ")
    @TmsLink("TECH-176986")
    public void testReserveAndTransactV4CtxTranLogExtAllFields() throws InterruptedException {
        //Failing because of https://jira.clickatell.com/browse/TECH-176071
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_VENDOR_DISCOUNT_PERCENTAGE_BY_VENDOR_ID, value_0point1, Vendor21));
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_MODEL_SELECTION, Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2, Vendor21, TestClient3, ProductAirtel_917));
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_TPV_CLIENT_SHARE, value_0point1, TestClient3, ProductAirtel_917));
        closeDBConnection();
        val jsonBody = setUpReserveAndTransactV4DataAdditionalDataWithProductFundSourceClientID(fundingSourceId_1500, AccountIdentifier, clientTxnRef, fundingSourceId, channelSessionId, ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundsReserved))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
        //Verify transaction status is "SUCCESS"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef);
        Thread.sleep(20000);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, Integer.parseInt(ReserveAndTransactClient.TestClient3), queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef))
                .body("transactionStatus", Matchers.containsString(ReserveAndTransactClient.Success));

        val SELECT_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG, raasTxnRef));
        System.out.println(SELECT_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        assertThat(SELECT_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE)
                .isNotNull();
        String SELECT_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = SELECT_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE.get(0);
        System.out.println(SELECT_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING);
        val SELECT_TRANSACTION_ID_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_TRANSACTION_ID_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_EXT, SELECT_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING));
        System.out.println(SELECT_TRANSACTION_ID_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL_VALUE);
        List<String> SELECT_TRANSACTION_ID_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL_VALUE_STRING = Lists.transform(SELECT_TRANSACTION_ID_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL_VALUE, Functions.toStringFunction());
        assertThat(SELECT_TRANSACTION_ID_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL_VALUE_STRING.get(0))
                .contains(SELECT_TRANSACTION_ID_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL_VALUE);
        val SELECT_TRANSACTION_STATUS_LOOKUP_DATE_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_EXT_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_TRANSACTION_STATUS_LOOKUP_DATE_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_EXT, SELECT_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING));
        System.out.println(SELECT_TRANSACTION_STATUS_LOOKUP_DATE_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_EXT_VALUE);
        assertThat(SELECT_TRANSACTION_STATUS_LOOKUP_DATE_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_EXT_VALUE)
                .containsNull();
        val SELECT_SOURCE_ID_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_EXT_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_SOURCE_ID_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_EXT, SELECT_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING));
        System.out.println(SELECT_SOURCE_ID_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_EXT_VALUE);
        List<String> SELECT_SOURCE_ID_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_EXT_VALUE_STRING = Lists.transform(SELECT_SOURCE_ID_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_EXT_VALUE, Functions.toStringFunction());
        assertThat(SELECT_SOURCE_ID_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_EXT_VALUE_STRING)
                .contains(Identifier);
        val SELECT_ACCOUNT_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_EXT_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_ACCOUNT_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_EXT, SELECT_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING));
        System.out.println(SELECT_ACCOUNT_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_EXT_VALUE);
        assertThat(SELECT_ACCOUNT_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_EXT_VALUE)
                .containsNull();
        val SELECT_INSERT_TIMESTAMP_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_EXT_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_INSERT_TIMESTAMP_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_EXT, SELECT_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING));
        String SELECT_INSERT_TIMESTAMP_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_EXT_VALUE_STRING = SELECT_INSERT_TIMESTAMP_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_EXT_VALUE.toString();
        System.out.println(SELECT_INSERT_TIMESTAMP_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_EXT_VALUE_STRING);
        val SELECT_INSERT_TIMESTAMP_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_EXT_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
        System.out.println(SELECT_INSERT_TIMESTAMP_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_EXT_TIMESTAMP_STRING);
        assertThat(SELECT_INSERT_TIMESTAMP_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_EXT_VALUE_STRING)
                .contains(SELECT_INSERT_TIMESTAMP_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_EXT_TIMESTAMP_STRING);
    }
    @Test()
    @Description("30100-payd-raas-gateway :: POST v4/reserveAndTransact :: raas.ctx_lookup_request all fields")
    @TmsLink("TECH-177008")
    public void testReserveAndTransactV4RaasCtxLookupRequestAllFields() throws InterruptedException {
        //Failing because of https://jira.clickatell.com/browse/TECH-176071
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_VENDOR_DISCOUNT_PERCENTAGE_BY_VENDOR_ID, value_0point1, Vendor21));
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_MODEL_SELECTION, Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2, Vendor21, TestClient3, ProductAirtel_917));
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_TPV_CLIENT_SHARE, value_0point1, TestClient3, ProductAirtel_917));
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0, responseCode2240, FeeAmount0, ResponseCode_200,Pending,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, "31914")
                .then().assertThat().statusCode(SC_OK);
        val jsonBody = setUpReserveAndTransactV4DataAdditionalDataWithProductFundSourceClientID(fundingSourceId_1500, AccountIdentifier, clientTxnRef, fundingSourceId, channelSessionId, ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundsReserved))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
        Thread.sleep(10000);

        val SELECT_CLIENT_ID_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CLIENT_ID_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST,raasTxnRef));
        List<String> SELECT_CLIENT_ID_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE_STRING = Lists.transform(SELECT_CLIENT_ID_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE, Functions.toStringFunction());
        System.out.println(SELECT_CLIENT_ID_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE_STRING);
        assertThat(SELECT_CLIENT_ID_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE_STRING)
                .contains(TestClient3);
        val SELECT_CLIENT_TRANSACTION_ID_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CLIENT_TRANSACTION_ID_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST, raasTxnRef));
        System.out.println(SELECT_CLIENT_TRANSACTION_ID_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE);
        assertThat(SELECT_CLIENT_TRANSACTION_ID_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE)
                .contains(raasTxnRef+"-0000");
        val SELECT_CREATED_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST, raasTxnRef));
        String SELECT_CREATED_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE_STRING = SELECT_CREATED_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE.toString();
        System.out.println(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE_STRING);
        val SELECT_CREATED_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
        System.out.println(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_TIMESTAMP_STRING);
        assertThat(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE_STRING)
                .contains(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_TIMESTAMP_STRING);
        val SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST,raasTxnRef));
        System.out.println(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE);
        List<String> SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE_STRING = Lists.transform(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE, Functions.toStringFunction());
        assertThat(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE_STRING)
                .contains(Event_Type_Ctx_Lookup_Request);
        val SELECT_UPDATED_TIMESTAMP_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_UPDATED_TIMESTAMP_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST,raasTxnRef));
        String SELECT_UPDATED_TIMESTAMP_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE_STRING = SELECT_UPDATED_TIMESTAMP_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE.toString();
        System.out.println(SELECT_UPDATED_TIMESTAMP_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE_STRING);
        val SELECT_UPDATED_TIMESTAMP_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
        System.out.println(SELECT_UPDATED_TIMESTAMP_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE_TIMESTAMP_STRING);
        assertThat(SELECT_UPDATED_TIMESTAMP_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE_STRING)
                .contains(SELECT_UPDATED_TIMESTAMP_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE_TIMESTAMP_STRING);

        Map body_success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_success, Vendor21, "31914")
                .then().assertThat().statusCode(SC_OK);
        val jsonBody_Success = setUpReserveAndTransactV4DataAdditionalDataWithProductFundSourceClientID(fundingSourceId_1500, AccountIdentifier, clientTxnRef, fundingSourceId, channelSessionId, ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef_Success = executeReserveAndTransact(jsonBody_Success, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundsReserved))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
        //Verify transaction status is "SUCCESS"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef_Success);
        Thread.sleep(20000);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, Integer.parseInt(ReserveAndTransactClient.TestClient3), queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef_Success))
                .body("transactionStatus", Matchers.containsString(ReserveAndTransactClient.Success));
    }
    @Test()
    @Description("30100-payd-raas-gateway :: POST v4/reserveAndTransact :: raas.ctx_lookup_response all fields")
    @TmsLink("TECH-177019")
    public void testReserveAndTransactV4RaasCtxLookupResponseAllFields() throws InterruptedException {
        //Failing because of https://jira.clickatell.com/browse/TECH-176071
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_VENDOR_DISCOUNT_PERCENTAGE_BY_VENDOR_ID, value_0point1, Vendor21));
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_MODEL_SELECTION, Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2, Vendor21, TestClient3, ProductAirtel_917));
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_TPV_CLIENT_SHARE, value_0point1, TestClient3, ProductAirtel_917));
        Map body = SetupSetVendData(Identifier, Identifier,FeeAmount0, responseCode2240, FeeAmount0, ResponseCode_200,Pending,vendorTransactionReference);
        PostControlApiBehaviour(body, Vendor21, "31914")
                .then().assertThat().statusCode(SC_OK);
        val jsonBody = setUpReserveAndTransactV4DataAdditionalDataWithProductFundSourceClientID(fundingSourceId_1500, AccountIdentifier, clientTxnRef, fundingSourceId, channelSessionId, ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundsReserved))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
        Map body_success = SetupSetVendData(Identifier, Identifier,FeeAmount0, FeeAmount0, FeeAmount0, ResponseCode_200,mwmSuccess,vendorTransactionReference);
        PostControlApiBehaviour(body_success, Vendor21, "31914")
                .then().assertThat().statusCode(SC_OK);
        val jsonBody_Success = setUpReserveAndTransactV4DataAdditionalDataWithProductFundSourceClientID(fundingSourceId_1500, AccountIdentifier, clientTxnRef, fundingSourceId, channelSessionId, ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ProductAirtel_917, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, ReserveAndTransactClient.Identifier);

        val raasTxnRef_Success = executeReserveAndTransact(jsonBody_Success, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundsReserved))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();
        //Verify transaction status is "SUCCESS"
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("raasTxnRef", raasTxnRef_Success);
        Thread.sleep(20000);
        findTransaction(Port.TRANSACTION_LOOKUP_SERVICE, Integer.parseInt(ReserveAndTransactClient.TestClient3), queryParams, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("raasTxnRef", Matchers.containsString(raasTxnRef_Success))
                .body("transactionStatus", Matchers.containsString(ReserveAndTransactClient.Success));

        val SELECT_PURCHASE_AMOUNT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_AMOUNT_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_RESPONSE, raasTxnRef_Success));
        System.out.println(SELECT_PURCHASE_AMOUNT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        List<String> SELECT_PURCHASE_AMOUNT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = Lists.transform(SELECT_PURCHASE_AMOUNT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE, Functions.toStringFunction());
        assertThat(SELECT_PURCHASE_AMOUNT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING)
                .contains(PurchaseAmount10000);
        val SELECT_CHANNEL_INDICATOR_BY_RAAS_TXN_REF_FROM_CTX_REQ_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CHANNEL_INDICATOR_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_RESPONSE, raasTxnRef_Success));
        List<String> SELECT_CHANNEL_INDICATOR_BY_RAAS_TXN_REF_FROM_CTX_REQ_VALUE_STRING = Lists.transform(SELECT_CHANNEL_INDICATOR_BY_RAAS_TXN_REF_FROM_CTX_REQ_VALUE, Functions.toStringFunction());
        assertThat(SELECT_CHANNEL_INDICATOR_BY_RAAS_TXN_REF_FROM_CTX_REQ_VALUE_STRING)
                .contains("7");
        val SELECT_CHANNEL_NAME_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_RESPONSE_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CHANNEL_NAME_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_RESPONSE, raasTxnRef_Success));
        System.out.println(SELECT_CHANNEL_NAME_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_RESPONSE_VALUE);
        assertThat(SELECT_CHANNEL_NAME_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_RESPONSE_VALUE)
                .containsNull();
        val SELECT_CHANNEL_SESSION_ID_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_RESPONSE_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CHANNEL_SESSION_ID_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_RESPONSE, raasTxnRef_Success));
        System.out.println(SELECT_CHANNEL_SESSION_ID_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_RESPONSE_VALUE);
        assertThat(SELECT_CHANNEL_SESSION_ID_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_RESPONSE_VALUE)
                .containsNull();
        val SELECT_CLIENT_ID_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CLIENT_ID_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST,raasTxnRef_Success));
        List<String> SELECT_CLIENT_ID_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE_STRING = Lists.transform(SELECT_CLIENT_ID_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE, Functions.toStringFunction());
        System.out.println(SELECT_CLIENT_ID_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE_STRING);
        assertThat(SELECT_CLIENT_ID_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE_STRING)
                .contains(TestClient3);
        val SELECT_CLIENT_TRANSACTION_ID_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CLIENT_TRANSACTION_ID_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_RESPONSE, raasTxnRef_Success));
        System.out.println(SELECT_CLIENT_TRANSACTION_ID_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE);
        assertThat(SELECT_CLIENT_TRANSACTION_ID_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE)
                .contains(raasTxnRef+"-0000");
        val SELECT_CREATED_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_RESPONSE, raasTxnRef_Success));
        String SELECT_CREATED_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE_STRING = SELECT_CREATED_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE.toString();
        System.out.println(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE_STRING);
        val SELECT_CREATED_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
        System.out.println(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_TIMESTAMP_STRING);
        assertThat(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE_STRING)
                .contains(SELECT_CREATED_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_TIMESTAMP_STRING);
        val SELECT_DATE_LOCAL_TXN_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_DATE_LOCAL_TXN_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_RESPONSE, raasTxnRef_Success));
        String SELECT_DATE_LOCAL_TXN_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_STRING = SELECT_DATE_LOCAL_TXN_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE.toString();
        System.out.println(SELECT_DATE_LOCAL_TXN_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_STRING);
        val SELECT_DATE_LOCAL_TXN_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("MMdd"));
        System.out.println(SELECT_DATE_LOCAL_TXN_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_TIMESTAMP_STRING);
        assertThat(SELECT_DATE_LOCAL_TXN_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_STRING)
                .contains(SELECT_DATE_LOCAL_TXN_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_TIMESTAMP_STRING);
        val SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_RESPONSE,raasTxnRef_Success));
        System.out.println(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE);
        List<String> SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE_STRING = Lists.transform(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE, Functions.toStringFunction());
        assertThat(SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE_STRING)
                .contains(Event_Type_Ctx_Lookup_Response);
        val SELECT_ORIGIN_ID_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_ORIGIN_ID_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_RESPONSE, raasTxnRef_Success));
        System.out.println(SELECT_ORIGIN_ID_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE);
        List<String> SELECT_ORIGIN_ID_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_STRING = Lists.transform(SELECT_ORIGIN_ID_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE, Functions.toStringFunction());
        assertThat(SELECT_ORIGIN_ID_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_STRING)
                .contains(Identifier);
        val SELECT_PRODUCT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_PRODUCT_ID_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_RESPONSE, raasTxnRef_Success));
        System.out.println(SELECT_PRODUCT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE);
        List<String> SELECT_PRODUCT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING = Lists.transform(SELECT_PRODUCT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE, Functions.toStringFunction());
        assertThat(SELECT_PRODUCT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_VALUE_STRING)
                .contains(ProductAirtel_917);
        val SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_RESPONSE, raasTxnRef_Success));
        System.out.println(SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE);
        List<String> SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_STRING = Lists.transform(SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE, Functions.toStringFunction());
        assertThat(SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_STRING)
                .contains(FeeAmount0);
        val SELECT_SOURCE_ID_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_SOURCE_ID_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_RESPONSE, raasTxnRef_Success));
        System.out.println(SELECT_SOURCE_ID_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE);
        List<String> SELECT_SOURCE_ID_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_STRING = Lists.transform(SELECT_SOURCE_ID_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE, Functions.toStringFunction());
        assertThat(SELECT_SOURCE_ID_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_STRING)
                .contains(Identifier);
//        val SELECT_TIME_LOCAL_TRANSACTION_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_TIME_LOCAL_TXN_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_RESPONSE, raasTxnRef));
//        String SELECT_TIME_LOCAL_TRANSACTION_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_STRING = SELECT_TIME_LOCAL_TRANSACTION_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE.toString();
//        System.out.println(SELECT_TIME_LOCAL_TRANSACTION_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_STRING);
//        val SELECT_TIME_LOCAL_TRANSACTION_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
//        System.out.println(SELECT_TIME_LOCAL_TRANSACTION_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_TIMESTAMP_STRING);
//        assertThat(SELECT_TIME_LOCAL_TRANSACTION_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_STRING)
//                .contains(SELECT_TIME_LOCAL_TRANSACTION_BY_RAAS_TXN_REF_FROM_CTX_REQUEST_VALUE_TIMESTAMP_STRING);
        val SELECT_VENDOR_REFERENCE_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_VENDOR_REFERENCE_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_RESPONSE, raasTxnRef_Success));
        System.out.println(SELECT_VENDOR_REFERENCE_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE);
        List<String> SELECT_VENDOR_REFERENCE_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_STRING = Lists.transform(SELECT_VENDOR_REFERENCE_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE, Functions.toStringFunction());
        assertThat(SELECT_VENDOR_REFERENCE_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE_VALUE_STRING)
                .isNotNull();
        val SELECT_UPDATED_TIMESTAMP_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_RESPONSE,raasTxnRef_Success));
        String SELECT_UPDATED_TIMESTAMP_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE_STRING = SELECT_UPDATED_TIMESTAMP_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE.toString();
        System.out.println(SELECT_UPDATED_TIMESTAMP_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE_STRING);
        val SELECT_UPDATED_TIMESTAMP_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE_TIMESTAMP_STRING = now().plusHours(6).format(ofPattern("yyyy-MM-dd HH"));
        System.out.println(SELECT_UPDATED_TIMESTAMP_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE_TIMESTAMP_STRING);
        assertThat(SELECT_UPDATED_TIMESTAMP_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE_STRING)
                .contains(SELECT_UPDATED_TIMESTAMP_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST_VALUE_TIMESTAMP_STRING);


    }

}
