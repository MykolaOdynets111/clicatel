package api.product_lookup;

import api.clients.MnoLookupClient;
import api.clients.ProductLookupClient;
import api.clients.ReserveAndTransactClient;
import api.clients.UsersClient;
import api.domains.mno_lookup.model.MnoLookupResponse;
import api.enums.Port;
import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import lombok.ToString;
import lombok.val;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.*;

import static api.clients.MnoLookupClient.getMnoInfo;
import static api.clients.ProductLookupClient.*;
import static api.clients.ReserveAndTransactClient.*;
import static api.clients.VendorManagementClient.ProductTypeData_5;
import static api.domains.product_lookup.repo.ProductLookupRequestRepo.setUpPostCreateFundingSource;
import static api.domains.product_lookup.repo.ProductLookupRequestRepo.setUpPostUpdateFundingSource;
import static db.clients.HibernateBaseClient.*;
import static db.custom_queries.FundingSourceQueries.*;
import static db.custom_queries.FundingSourceQueries.SELECT_FUNDING_SOURCE_NAME_BY_FUNDING_SOURCE_ID;
import static db.enums.Sessions.POSTGRES_SQL;
import static java.lang.String.format;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

public class FundingSourceController {
    @Test()
    @Description("POST \u200B/fundingSources\u200B/create :: happy path")
    @TmsLink("MKP-619")
    public void testCreateFundingSource() {

        //Delete funding source country and funding source // Pre conditions
        executeCustomQuery(POSTGRES_SQL, format(DELETE_FUNDING_SOURCE_COUNTRY_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_FUNDING_SOURCE_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        //Create new funding source
        List<String> CountryCodes = new ArrayList<String>();
        CountryCodes.add(UsersClient.country);
        CountryCodes.add(ProductLookupClient.CountryCode_710);
        val body = setUpPostCreateFundingSource(true, "",ProductLookupClient.confirmationWindowSizeSeconds, CountryCodes,
                ProductLookupClient.fundingSourceName, ProductLookupClient.fundingSourceId_1206,ReserveAndTransactClient.FeeAmount0, TestClient3,"",false, ProductLookupClient.reservationTimeout,
                ProductLookupClient.reserveFundsEndpoint, ProductLookupClient.retryDelay,ProductLookupClient.serviceWindowSizeSeconds,false,
                ProductLookupClient.transactionEndpoint, "", null, false);
        //Verify the response
        executePostCreateFundingSource(body)
                .then().assertThat().statusCode(SC_OK)
                .body("active",Matchers.is(true))
                .body("authenticationType",Matchers.containsString(""))
                .body("confirmationWindowSizeSeconds", Matchers.is(Integer.parseInt(ProductLookupClient.confirmationWindowSizeSeconds)))
                .body("fundingSourceName", Matchers.containsString(ProductLookupClient.fundingSourceName))
                .body("id",Matchers.is(Integer.parseInt(ProductLookupClient.fundingSourceId_1206)))
                .body("initialRetryDelay", Matchers.is(Integer.parseInt(ReserveAndTransactClient.FeeAmount0)))
                .body("maxRetries",Matchers.is(Integer.parseInt(TestClient3)))
                .body("password", Matchers.containsString(""))
                .body("raasTransactionHistoryEnabled",Matchers.is(false))
                .body("reservationTimeout",Matchers.is(Integer.parseInt(ProductLookupClient.reservationTimeout)))
                .body("reserveFundsEndpoint",Matchers.containsString(ProductLookupClient.reserveFundsEndpoint))
                .body("retryDelay",Matchers.is(Integer.parseInt(ProductLookupClient.retryDelay)))
                .body("serviceWindowSizeSeconds",Matchers.is(Integer.parseInt(ProductLookupClient.serviceWindowSizeSeconds)))
                .body("supportToken", Matchers.is(false))
                .body("transactionEndpoint", Matchers.containsString(ProductLookupClient.transactionEndpoint))
                .body("username",Matchers.containsString(""))
                .body("statusLookupEndpoint",Matchers.nullValue())
                .body("signatureRequired", Matchers.is(false));
        //Database checks
        val FUNDING_SOURCE_NAME_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_FUNDING_SOURCE_NAME_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(FUNDING_SOURCE_NAME_VALUE)
                .contains(ProductLookupClient.fundingSourceName);
        val TRANSACTION_RESULT_ENDPOINT_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_TRANSACTION_RESULT_ENDPOINT_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(TRANSACTION_RESULT_ENDPOINT_VALUE)
                .contains(ProductLookupClient.transactionEndpoint);
        val MAXIMUM_RETRIES_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_MAXIMUM_RETRIES_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(MAXIMUM_RETRIES_VALUE)
                .contains(TestClient3);
        val RESERVE_FUND_ENDPOINT_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_RESERVE_FUND_ENDPOINT_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(RESERVE_FUND_ENDPOINT_VALUE)
                .contains(ProductLookupClient.reserveFundsEndpoint);
        val RESERVATION_TIMEOUT_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_RESERVATION_TIMEOUT_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(RESERVATION_TIMEOUT_VALUE)
                .contains(ProductLookupClient.reservationTimeout);
        val RETRY_DELAY_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_RETRY_DELAY_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(RETRY_DELAY_VALUE)
                .contains(ProductLookupClient.retryDelay);
        val INITIAL_RETRY_DELAY_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_INITIAL_RETRY_DELAY_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(INITIAL_RETRY_DELAY_VALUE)
                .contains(ReserveAndTransactClient.FeeAmount0);
        val SERVICE_WINDOW_SIZE_SECONDS_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_SERVICE_WINDOW_SIZE_SECONDS_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(SERVICE_WINDOW_SIZE_SECONDS_VALUE)
                .contains(ProductLookupClient.serviceWindowSizeSeconds);
        val CONFIRMATION_WINDOW_SIZE_SECONDS_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_CONFIRMATION_WINDOW_SIZE_SECONDS_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(CONFIRMATION_WINDOW_SIZE_SECONDS_VALUE)
                .contains(ProductLookupClient.confirmationWindowSizeSeconds);
        val RAAS_TXN_HISTORY_ENABLED_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_RAAS_TXN_HISTORY_ENABLED_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(RAAS_TXN_HISTORY_ENABLED_VALUE)
                .contains("false");
        val ACTIVE_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_ACTIVE_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(ACTIVE_VALUE)
                .contains("true");
        val SUPPORT_TOKEN_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_SUPPORT_TOKEN_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(SUPPORT_TOKEN_VALUE)
                .contains("false");
        val SIGNATURE_REQUIRED_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_SIGNATURE_REQUIRED_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(SIGNATURE_REQUIRED_VALUE)
                .contains("false");
        val COUNTRY_CODES_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_COUNTRY_CODES_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        List<String> strings = Lists.transform(COUNTRY_CODES_VALUE, Functions.toStringFunction());
        System.out.println(strings.get(1));
        assertThat(strings.get(1))
                .contains(ProductLookupClient.CountryCode_710);
        assertThat(strings.get(0))
                .contains(UsersClient.country);
        //Post conditions: Delete the funding source
        executeCustomQuery(POSTGRES_SQL, format(DELETE_FUNDING_SOURCE_COUNTRY_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_FUNDING_SOURCE_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
    }
    @Test()
    @Description("POST \u200B/fundingSources\u200B/create :: happy path")
    @TmsLink("MKP-688")
    public void testCreateAndGetFundingSource() {

        //Delete funding source country and funding source // Pre conditions
        executeCustomQuery(POSTGRES_SQL, format(DELETE_FUNDING_SOURCE_COUNTRY_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_FUNDING_SOURCE_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        //Create new funding source
        List<String> CountryCodes = new ArrayList<String>();
        CountryCodes.add(UsersClient.country);
        CountryCodes.add(ProductLookupClient.CountryCode_710);
        val body = setUpPostCreateFundingSource(true, "",ProductLookupClient.confirmationWindowSizeSeconds, CountryCodes,
                ProductLookupClient.fundingSourceName, ProductLookupClient.fundingSourceId_1206,ReserveAndTransactClient.FeeAmount0, TestClient3,"",false, ProductLookupClient.reservationTimeout,
                ProductLookupClient.reserveFundsEndpoint, ProductLookupClient.retryDelay,ProductLookupClient.serviceWindowSizeSeconds,false,
                ProductLookupClient.transactionEndpoint, "", null, false);
        //Verify the response
        executePostCreateFundingSource(body)
                .then().assertThat().statusCode(SC_OK)
                .body("active",Matchers.is(true))
                .body("authenticationType",Matchers.containsString(""))
                .body("confirmationWindowSizeSeconds", Matchers.is(Integer.parseInt(ProductLookupClient.confirmationWindowSizeSeconds)))
                .body("fundingSourceName", Matchers.containsString(ProductLookupClient.fundingSourceName))
                .body("id",Matchers.is(Integer.parseInt(ProductLookupClient.fundingSourceId_1206)))
                .body("initialRetryDelay", Matchers.is(Integer.parseInt(ReserveAndTransactClient.FeeAmount0)))
                .body("maxRetries",Matchers.is(Integer.parseInt(TestClient3)))
                .body("password", Matchers.containsString(""))
                .body("raasTransactionHistoryEnabled",Matchers.is(false))
                .body("reservationTimeout",Matchers.is(Integer.parseInt(ProductLookupClient.reservationTimeout)))
                .body("reserveFundsEndpoint",Matchers.containsString(ProductLookupClient.reserveFundsEndpoint))
                .body("retryDelay",Matchers.is(Integer.parseInt(ProductLookupClient.retryDelay)))
                .body("serviceWindowSizeSeconds",Matchers.is(Integer.parseInt(ProductLookupClient.serviceWindowSizeSeconds)))
                .body("supportToken", Matchers.is(false))
                .body("transactionEndpoint", Matchers.containsString(ProductLookupClient.transactionEndpoint))
                .body("username",Matchers.containsString(""))
                .body("statusLookupEndpoint",Matchers.nullValue())
                .body("signatureRequired", Matchers.is(false));
        //Get Funding source and verify the parameters
        GetFundingSourceByFundingSourceID(ProductLookupClient.fundingSourceId_1206)
                .then().assertThat().statusCode(SC_OK)
                .body("active",Matchers.is(true))
                .body("authenticationType",Matchers.containsString(""))
                .body("confirmationWindowSizeSeconds", Matchers.is(Integer.parseInt(ProductLookupClient.confirmationWindowSizeSeconds)))
                .body("fundingSourceName", Matchers.containsString(ProductLookupClient.fundingSourceName))
                .body("id",Matchers.is(Integer.parseInt(ProductLookupClient.fundingSourceId_1206)))
                .body("initialRetryDelay", Matchers.is(Integer.parseInt(ReserveAndTransactClient.FeeAmount0)))
                .body("maxRetries",Matchers.is(Integer.parseInt(TestClient3)))
                .body("password", Matchers.containsString(""))
                .body("raasTransactionHistoryEnabled",Matchers.is(false))
                .body("reservationTimeout",Matchers.is(Integer.parseInt(ProductLookupClient.reservationTimeout)))
                .body("reserveFundsEndpoint",Matchers.containsString(ProductLookupClient.reserveFundsEndpoint))
                .body("retryDelay",Matchers.is(Integer.parseInt(ProductLookupClient.retryDelay)))
                .body("serviceWindowSizeSeconds",Matchers.is(Integer.parseInt(ProductLookupClient.serviceWindowSizeSeconds)))
                .body("supportToken", Matchers.is(false))
                .body("transactionEndpoint", Matchers.containsString(ProductLookupClient.transactionEndpoint))
                .body("username",Matchers.containsString(""))
                .body("statusLookupEndpoint",Matchers.nullValue())
                .body("signatureRequired", Matchers.is(false));
        //Database checks
        val FUNDING_SOURCE_NAME_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_FUNDING_SOURCE_NAME_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(FUNDING_SOURCE_NAME_VALUE)
                .contains(ProductLookupClient.fundingSourceName);
        val TRANSACTION_RESULT_ENDPOINT_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_TRANSACTION_RESULT_ENDPOINT_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(TRANSACTION_RESULT_ENDPOINT_VALUE)
                .contains(ProductLookupClient.transactionEndpoint);
        val MAXIMUM_RETRIES_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_MAXIMUM_RETRIES_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(MAXIMUM_RETRIES_VALUE)
                .contains(TestClient3);
        val RESERVE_FUND_ENDPOINT_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_RESERVE_FUND_ENDPOINT_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(RESERVE_FUND_ENDPOINT_VALUE)
                .contains(ProductLookupClient.reserveFundsEndpoint);
        val RESERVATION_TIMEOUT_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_RESERVATION_TIMEOUT_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(RESERVATION_TIMEOUT_VALUE)
                .contains(ProductLookupClient.reservationTimeout);
        val RETRY_DELAY_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_RETRY_DELAY_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(RETRY_DELAY_VALUE)
                .contains(ProductLookupClient.retryDelay);
        val INITIAL_RETRY_DELAY_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_INITIAL_RETRY_DELAY_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(INITIAL_RETRY_DELAY_VALUE)
                .contains(ReserveAndTransactClient.FeeAmount0);
        val SERVICE_WINDOW_SIZE_SECONDS_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_SERVICE_WINDOW_SIZE_SECONDS_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(SERVICE_WINDOW_SIZE_SECONDS_VALUE)
                .contains(ProductLookupClient.serviceWindowSizeSeconds);
        val CONFIRMATION_WINDOW_SIZE_SECONDS_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_CONFIRMATION_WINDOW_SIZE_SECONDS_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(CONFIRMATION_WINDOW_SIZE_SECONDS_VALUE)
                .contains(ProductLookupClient.confirmationWindowSizeSeconds);
        val RAAS_TXN_HISTORY_ENABLED_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_RAAS_TXN_HISTORY_ENABLED_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(RAAS_TXN_HISTORY_ENABLED_VALUE)
                .contains("false");
        val ACTIVE_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_ACTIVE_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(ACTIVE_VALUE)
                .contains("true");
        val SUPPORT_TOKEN_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_SUPPORT_TOKEN_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(SUPPORT_TOKEN_VALUE)
                .contains("false");
        val SIGNATURE_REQUIRED_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_SIGNATURE_REQUIRED_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(SIGNATURE_REQUIRED_VALUE)
                .contains("false");
        val COUNTRY_CODES_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_COUNTRY_CODES_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        List<String> strings = Lists.transform(COUNTRY_CODES_VALUE, Functions.toStringFunction());
        System.out.println(strings.get(1));
        assertThat(strings.get(1))
                .contains(ProductLookupClient.CountryCode_710);
        assertThat(strings.get(0))
                .contains(UsersClient.country);
        //Post conditions: Delete the funding source
        executeCustomQuery(POSTGRES_SQL, format(DELETE_FUNDING_SOURCE_COUNTRY_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_FUNDING_SOURCE_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
    }
    @Test()
    @Description("PUT \u200B/fundingSources\u200B/update :: happy path")
    @TmsLink("MKP-668")
    public void testCreateGetAndUpdateFundingSource() {

        //Delete funding source country and funding source // Pre conditions
        executeCustomQuery(POSTGRES_SQL, format(DELETE_FUNDING_SOURCE_COUNTRY_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_FUNDING_SOURCE_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        //Create new funding source
        List<String> CountryCodes = new ArrayList<String>();
        CountryCodes.add(UsersClient.country);
        CountryCodes.add(ProductLookupClient.CountryCode_710);
        val body = setUpPostCreateFundingSource(true, "",ProductLookupClient.confirmationWindowSizeSeconds, CountryCodes,
                ProductLookupClient.fundingSourceName, ProductLookupClient.fundingSourceId_1206,ReserveAndTransactClient.FeeAmount0, TestClient3,"",false, ProductLookupClient.reservationTimeout,
                ProductLookupClient.reserveFundsEndpoint, ProductLookupClient.retryDelay,ProductLookupClient.serviceWindowSizeSeconds,false,
                ProductLookupClient.transactionEndpoint, "", null, false);
        //Verify the response
        executePostCreateFundingSource(body)
                .then().assertThat().statusCode(SC_OK)
                .body("active",Matchers.is(true))
                .body("authenticationType",Matchers.containsString(""))
                .body("confirmationWindowSizeSeconds", Matchers.is(Integer.parseInt(ProductLookupClient.confirmationWindowSizeSeconds)))
                .body("fundingSourceName", Matchers.containsString(ProductLookupClient.fundingSourceName))
                .body("id",Matchers.is(Integer.parseInt(ProductLookupClient.fundingSourceId_1206)))
                .body("initialRetryDelay", Matchers.is(Integer.parseInt(ReserveAndTransactClient.FeeAmount0)))
                .body("maxRetries",Matchers.is(Integer.parseInt(TestClient3)))
                .body("password", Matchers.containsString(""))
                .body("raasTransactionHistoryEnabled",Matchers.is(false))
                .body("reservationTimeout",Matchers.is(Integer.parseInt(ProductLookupClient.reservationTimeout)))
                .body("reserveFundsEndpoint",Matchers.containsString(ProductLookupClient.reserveFundsEndpoint))
                .body("retryDelay",Matchers.is(Integer.parseInt(ProductLookupClient.retryDelay)))
                .body("serviceWindowSizeSeconds",Matchers.is(Integer.parseInt(ProductLookupClient.serviceWindowSizeSeconds)))
                .body("supportToken", Matchers.is(false))
                .body("transactionEndpoint", Matchers.containsString(ProductLookupClient.transactionEndpoint))
                .body("username",Matchers.containsString(""))
                .body("statusLookupEndpoint",Matchers.nullValue())
                .body("signatureRequired", Matchers.is(false));
        //Get Funding source and verify the parameters
        String timestamp = GetFundingSourceByFundingSourceID(ProductLookupClient.fundingSourceId_1206)
                .then().assertThat().statusCode(SC_OK)
                .body("active",Matchers.is(true))
                .body("authenticationType",Matchers.containsString(""))
                .body("confirmationWindowSizeSeconds", Matchers.is(Integer.parseInt(ProductLookupClient.confirmationWindowSizeSeconds)))
                .body("fundingSourceName", Matchers.containsString(ProductLookupClient.fundingSourceName))
                .body("id",Matchers.is(Integer.parseInt(ProductLookupClient.fundingSourceId_1206)))
                .body("initialRetryDelay", Matchers.is(Integer.parseInt(ReserveAndTransactClient.FeeAmount0)))
                .body("maxRetries",Matchers.is(Integer.parseInt(TestClient3)))
                .body("password", Matchers.containsString(""))
                .body("raasTransactionHistoryEnabled",Matchers.is(false))
                .body("reservationTimeout",Matchers.is(Integer.parseInt(ProductLookupClient.reservationTimeout)))
                .body("reserveFundsEndpoint",Matchers.containsString(ProductLookupClient.reserveFundsEndpoint))
                .body("retryDelay",Matchers.is(Integer.parseInt(ProductLookupClient.retryDelay)))
                .body("serviceWindowSizeSeconds",Matchers.is(Integer.parseInt(ProductLookupClient.serviceWindowSizeSeconds)))
                .body("supportToken", Matchers.is(false))
                .body("transactionEndpoint", Matchers.containsString(ProductLookupClient.transactionEndpoint))
                .body("username",Matchers.containsString(""))
                .body("statusLookupEndpoint",Matchers.nullValue())
                .body("signatureRequired", Matchers.is(false))
                .extract().response().path("timestamp");
        //Update Funding Source
        val body_Update_fundingSource = setUpPostUpdateFundingSource(true, "",ProductLookupClient.confirmationWindowSizeSeconds, CountryCodes,
                ProductLookupClient.fundingSourceNameUpdate, ProductLookupClient.fundingSourceId_1206,ReserveAndTransactClient.FeeAmount0, TestClient3,"",false, ProductLookupClient.reservationTimeout,
                ProductLookupClient.reserveFundsEndpoint, ProductLookupClient.retryDelay,ProductLookupClient.serviceWindowSizeSeconds,false,
                ProductLookupClient.transactionEndpoint, "", null, false,timestamp);
        executePostUpdateFundingSource(body_Update_fundingSource)
                .then().assertThat().statusCode(SC_OK)
                .body("active",Matchers.is(true))
                .body("authenticationType",Matchers.containsString(""))
                .body("confirmationWindowSizeSeconds", Matchers.is(Integer.parseInt(ProductLookupClient.confirmationWindowSizeSeconds)))
                .body("fundingSourceName", Matchers.containsString(ProductLookupClient.fundingSourceNameUpdate))
                .body("id",Matchers.is(Integer.parseInt(ProductLookupClient.fundingSourceId_1206)))
                .body("initialRetryDelay", Matchers.is(Integer.parseInt(ReserveAndTransactClient.FeeAmount0)))
                .body("maxRetries",Matchers.is(Integer.parseInt(TestClient3)))
                .body("password", Matchers.containsString(""))
                .body("raasTransactionHistoryEnabled",Matchers.is(false))
                .body("reservationTimeout",Matchers.is(Integer.parseInt(ProductLookupClient.reservationTimeout)))
                .body("reserveFundsEndpoint",Matchers.containsString(ProductLookupClient.reserveFundsEndpoint))
                .body("retryDelay",Matchers.is(Integer.parseInt(ProductLookupClient.retryDelay)))
                .body("serviceWindowSizeSeconds",Matchers.is(Integer.parseInt(ProductLookupClient.serviceWindowSizeSeconds)))
                .body("supportToken", Matchers.is(false))
                .body("transactionEndpoint", Matchers.containsString(ProductLookupClient.transactionEndpoint))
                .body("username",Matchers.containsString(""))
                .body("statusLookupEndpoint",Matchers.nullValue())
                .body("timestamp",Matchers.notNullValue())
                .body("signatureRequired", Matchers.is(false));

        //Database checks
        val FUNDING_SOURCE_NAME_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_FUNDING_SOURCE_NAME_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(FUNDING_SOURCE_NAME_VALUE)
                .contains(ProductLookupClient.fundingSourceNameUpdate);
        val TRANSACTION_RESULT_ENDPOINT_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_TRANSACTION_RESULT_ENDPOINT_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(TRANSACTION_RESULT_ENDPOINT_VALUE)
                .contains(ProductLookupClient.transactionEndpoint);
        val MAXIMUM_RETRIES_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_MAXIMUM_RETRIES_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(MAXIMUM_RETRIES_VALUE)
                .contains(TestClient3);
        val RESERVE_FUND_ENDPOINT_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_RESERVE_FUND_ENDPOINT_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(RESERVE_FUND_ENDPOINT_VALUE)
                .contains(ProductLookupClient.reserveFundsEndpoint);
        val RESERVATION_TIMEOUT_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_RESERVATION_TIMEOUT_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(RESERVATION_TIMEOUT_VALUE)
                .contains(ProductLookupClient.reservationTimeout);
        val RETRY_DELAY_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_RETRY_DELAY_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(RETRY_DELAY_VALUE)
                .contains(ProductLookupClient.retryDelay);
        val INITIAL_RETRY_DELAY_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_INITIAL_RETRY_DELAY_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(INITIAL_RETRY_DELAY_VALUE)
                .contains(ReserveAndTransactClient.FeeAmount0);
        val SERVICE_WINDOW_SIZE_SECONDS_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_SERVICE_WINDOW_SIZE_SECONDS_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(SERVICE_WINDOW_SIZE_SECONDS_VALUE)
                .contains(ProductLookupClient.serviceWindowSizeSeconds);
        val CONFIRMATION_WINDOW_SIZE_SECONDS_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_CONFIRMATION_WINDOW_SIZE_SECONDS_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(CONFIRMATION_WINDOW_SIZE_SECONDS_VALUE)
                .contains(ProductLookupClient.confirmationWindowSizeSeconds);
        val RAAS_TXN_HISTORY_ENABLED_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_RAAS_TXN_HISTORY_ENABLED_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(RAAS_TXN_HISTORY_ENABLED_VALUE)
                .contains("false");
        val ACTIVE_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_ACTIVE_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(ACTIVE_VALUE)
                .contains("true");
        val SUPPORT_TOKEN_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_SUPPORT_TOKEN_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(SUPPORT_TOKEN_VALUE)
                .contains("false");
        val SIGNATURE_REQUIRED_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_SIGNATURE_REQUIRED_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(SIGNATURE_REQUIRED_VALUE)
                .contains("false");
        val COUNTRY_CODES_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_COUNTRY_CODES_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        List<String> strings = Lists.transform(COUNTRY_CODES_VALUE, Functions.toStringFunction());
        System.out.println(strings.get(1));
        assertThat(strings.get(1))
                .contains(ProductLookupClient.CountryCode_710);
        assertThat(strings.get(0))
                .contains(UsersClient.country);
        //Post conditions: Delete the funding source
        executeCustomQuery(POSTGRES_SQL, format(DELETE_FUNDING_SOURCE_COUNTRY_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_FUNDING_SOURCE_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
    }

    @Test()
    @Description("32000-payd-product-lookup :: POST \u200B/clients\u200B/{clientId}\u200B/fundingSource\u200B/{fundingSourceId} :: happy path")
    @TmsLink("MKP-570")
    public void testCreateFundingSourceHappyPath() {

        //Pre conditions
        executeCustomQuery(POSTGRES_SQL, format(DELETE_FUNDING_SOURCE_COUNTRY_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_CLIENT_FUNDING_SOURCE_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_FUNDING_SOURCE_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_VELOCITY_CONFIG_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));

        String json = "'" + "{\"enabled\":false}" + "'" + "::::json";

        executeCustomQuery(POSTGRES_SQL, format(INSERT_VELOCITY_CONFIG_BY_FUNDING_SOURCE_ID,fundingSourceId_1206,json,Product_Insert_Timestamp));
        //Create new funding source
        List<String> CountryCodes = new ArrayList<String>();
        CountryCodes.add(UsersClient.country);
        CountryCodes.add(ProductLookupClient.CountryCode_710);
        val body = setUpPostCreateFundingSource(true, "",ProductLookupClient.confirmationWindowSizeSeconds, CountryCodes,
                ProductLookupClient.fundingSourceName, ProductLookupClient.fundingSourceId_1206,ReserveAndTransactClient.FeeAmount0, TestClient3,"",false, ProductLookupClient.reservationTimeout,
                ProductLookupClient.reserveFundsEndpoint, ProductLookupClient.retryDelay,ProductLookupClient.serviceWindowSizeSeconds,false,
                ProductLookupClient.transactionEndpoint, "", null, false);
        //Verify the response
        executePostCreateFundingSource(body)
                .then().assertThat().statusCode(SC_OK)
                .body("active",Matchers.is(true))
                .body("authenticationType",Matchers.containsString(""))
                .body("confirmationWindowSizeSeconds", Matchers.is(Integer.parseInt(ProductLookupClient.confirmationWindowSizeSeconds)))
                .body("fundingSourceName", Matchers.containsString(ProductLookupClient.fundingSourceName))
                .body("id",Matchers.is(Integer.parseInt(ProductLookupClient.fundingSourceId_1206)))
                .body("initialRetryDelay", Matchers.is(Integer.parseInt(ReserveAndTransactClient.FeeAmount0)))
                .body("maxRetries",Matchers.is(Integer.parseInt(TestClient3)))
                .body("password", Matchers.containsString(""))
                .body("raasTransactionHistoryEnabled",Matchers.is(false))
                .body("reservationTimeout",Matchers.is(Integer.parseInt(ProductLookupClient.reservationTimeout)))
                .body("reserveFundsEndpoint",Matchers.containsString(ProductLookupClient.reserveFundsEndpoint))
                .body("retryDelay",Matchers.is(Integer.parseInt(ProductLookupClient.retryDelay)))
                .body("serviceWindowSizeSeconds",Matchers.is(Integer.parseInt(ProductLookupClient.serviceWindowSizeSeconds)))
                .body("supportToken", Matchers.is(false))
                .body("transactionEndpoint", Matchers.containsString(ProductLookupClient.transactionEndpoint))
                .body("username",Matchers.containsString(""))
                .body("statusLookupEndpoint",Matchers.nullValue())
                .body("signatureRequired", Matchers.is(false));

        PostFundingSourceByClientId(TestClient3,fundingSourceId_1206)
                .then().assertThat().statusCode(SC_OK)
                .body("fundingSourceId",Matchers.hasItem(Integer.parseInt(fundingSourceId_1206)))
                .body("fundingSourceName",Matchers.hasItem(fundingSourceName));
        //Database checks
        val SELECT_PRODUCT_ID_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_FUNDING_SOURCE_BY_FUNDING_SOURCE_ID, fundingSourceId_1206,TestClient3));
        System.out.println(SELECT_PRODUCT_ID_VALUE);
        List<String> strings_1 = Lists.transform(SELECT_PRODUCT_ID_VALUE, Functions.toStringFunction());
        assertThat(strings_1.get(0))
                .contains(fundingSourceId_1206);
        //Post conditions
        executeCustomQuery(POSTGRES_SQL, format(DELETE_FUNDING_SOURCE_COUNTRY_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_CLIENT_FUNDING_SOURCE_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_FUNDING_SOURCE_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_VELOCITY_CONFIG_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));

    }
}
