package api.product_lookup;

import api.clients.ProductLookupClient;
import api.clients.ReserveAndTransactClient;
import api.clients.TokenLookupClient;
import api.clients.UsersClient;
import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static api.clients.ProductLookupClient.*;
import static api.domains.product_lookup.repo.ProductLookupRequestRepo.*;
import static db.clients.HibernateBaseClient.*;
import static db.custom_queries.ClientQueries.*;
import static db.custom_queries.FundingSourceQueries.*;
import static db.engine.HibernateConnection.closeDBConnection;
import static db.enums.Sessions.MY_SQL;
import static db.enums.Sessions.POSTGRES_SQL;
import static java.lang.String.format;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

//As these test cases are creating new clients hence they are commented out:

public class Clients {

    @Test
    @Description("POST /clients :: happy path")
    @TmsLink("TECH-34251")
    public void testCreateNewClient() {
    //Pre conditions: Deleting client configurations
        executeCustomQuery(MY_SQL, format(DELETE_CLIENT_MODULE_CONFIG, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(MY_SQL, format(DELETE_CLIENT_CREDIT_LIMIT, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(MY_SQL, format(DELETE_CLIENT_STAN, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(MY_SQL, format(DELETE_CLIENT_COMMISSION_MODEL, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(MY_SQL, format(DELETE_CLIENT, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(MY_SQL, format(DELETE_ACCOUNT, ProductLookupClient.Client_1206));
        closeDBConnection();
        executeCustomQuery(POSTGRES_SQL, format(DELETE_CLIENT_SYSTEM_ACCESS, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_CLIENT_COMMON, ProductLookupClient.fundingSourceId_1206));

    //Client Creation and API response verifications
        val body = setUpPostNewClientData(true, Arrays.asList("3", "1"), ProductLookupClient.fundingSourceId_1206, Client_1206, UsersClient.country,
                ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2
                , ClickatellAccountId,ctxLimitTotal,false );

        PostNewClient(body)
                .then().assertThat().statusCode(SC_CREATED)
                .body("clientId", Matchers.is(Integer.parseInt(ProductLookupClient.fundingSourceId_1206)))
                .body("clientName", Matchers.containsString(Client_1206))
                .body("active", Matchers.is( true))
                .body("timezoneId", Matchers.is(Integer.parseInt(ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2)))
                .body("countryCode", Matchers.is(Integer.parseInt(UsersClient.country)))
                .body("clickatellAccountId", Matchers.containsString(ClickatellAccountId))
                .body("signatureRequired", Matchers.is( false))
                .body("timestamp", Matchers.notNullValue())
                .extract().body();
    //Database Checks
        val SELECT_CLIENT_NAME_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_CLIENT_NAME, ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_CLIENT_NAME_VALUE)
                .contains(ProductLookupClient.Client_1206);
        val SELECT_ACTIVE_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_ACTIVE, ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_ACTIVE_VALUE)
                .contains("true");
        val SELECT_TIMEZONE_ID_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_TIMEZONE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_TIMEZONE_ID_VALUE)
                .contains(ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2);
        val SELECT_COUNTRY_CODE_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_COUNTRY_CODE, ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_COUNTRY_CODE_VALUE)
                .contains(UsersClient.country);
        val SELECT_CLICKATELL_ACCOUNT_ID_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_CLICKATELL_ACCOUNT_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_CLICKATELL_ACCOUNT_ID_VALUE)
                .contains(ClickatellAccountId);
        val SELECT_SIGNATURE_REQUIRED_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_SIGNATURE_REQUIRED, ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_SIGNATURE_REQUIRED_VALUE)
                .contains("false");
        val SELECT_CDC_TIMESTAMP_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_CDC_TIMESTAMP, ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_CDC_TIMESTAMP_VALUE)
                .isNotNull();
        val SELECT_SYSTEM_ID_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_SYSTEM_ID, ProductLookupClient.fundingSourceId_1206));
        List<String> strings = Lists.transform(SELECT_SYSTEM_ID_VALUE, Functions.toStringFunction());
        System.out.println(strings.get(1));
        assertThat(strings.get(1))
                .contains(ReserveAndTransactClient.TestClient3);
        assertThat(strings.get(0))
                .contains(TokenLookupClient.Limit_1);
        closeDBConnection();
        val SELECT_CLIENT_CLIENTROLE_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_CLIENT_CLIENTROLE, ProductLookupClient.fundingSourceId_1206));
        System.out.println(SELECT_CLIENT_CLIENTROLE_VALUE);
        assertThat(SELECT_CLIENT_CLIENTROLE_VALUE)
                .contains(Client_Role_1206);
        val SELECT_CLIENT_NAME_CPGTX_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_CLIENT_NAME_CPGTX, ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_CLIENT_NAME_CPGTX_VALUE)
                .contains(Client_1206);
        val SELECT_CLIENT_CURRENCY_ID_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_CLIENT_CURRENCY_ID, ProductLookupClient.fundingSourceId_1206));
        System.out.println(SELECT_CLIENT_CURRENCY_ID_VALUE);
        assertThat(SELECT_CLIENT_CURRENCY_ID_VALUE)
                .contains(TokenLookupClient.Limit_1);
        val SELECT_CLIENT_TIMEZONE_ID_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_CLIENT_TIMEZONE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_CLIENT_TIMEZONE_ID_VALUE)
                .contains(ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2);
        val SELECT_CLIENT_ACTIVE_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_CLIENT_ACTIVE, ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_CLIENT_ACTIVE_VALUE)
                .contains("true");
        val SELECT_CLIENT_CREDIT_LIMIT_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_CLIENT_CREDIT_LIMIT, ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_CLIENT_CREDIT_LIMIT_VALUE)
                .contains(ctxLimitTotal);
        val SELECT_CLIENT_MODULE_CONFIG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_CLIENT_MODULE_CONFIG, ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_CLIENT_MODULE_CONFIG_VALUE)
                .isNotNull();
        val SELECT_CLIENT_STAN_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_CLIENT_STAN, ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_CLIENT_STAN_VALUE)
                .isNotNull();
        val SELECT_CLIENT_COMMISSION_MODEL_TYPE_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_CLIENT_COMMISSION_MODEL_TYPE , ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_CLIENT_COMMISSION_MODEL_TYPE_VALUE)
                .isNotNull();
        val SELECT_ACCOUNT_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_ACCOUNT , ProductLookupClient.Client_1206));
        assertThat(SELECT_ACCOUNT_VALUE)
                .contains(ProductLookupClient.Client_1206);
    //Post Conditions: Deleting client configuration
        executeCustomQuery(MY_SQL, format(DELETE_CLIENT_MODULE_CONFIG, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(MY_SQL, format(DELETE_CLIENT_CREDIT_LIMIT, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(MY_SQL, format(DELETE_CLIENT_STAN, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(MY_SQL, format(DELETE_CLIENT_COMMISSION_MODEL, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(MY_SQL, format(DELETE_CLIENT, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(MY_SQL, format(DELETE_ACCOUNT, ProductLookupClient.Client_1206));
        closeDBConnection();
        executeCustomQuery(POSTGRES_SQL, format(DELETE_CLIENT_SYSTEM_ACCESS, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_CLIENT_COMMON, ProductLookupClient.fundingSourceId_1206));

    }

    @Test
    @Description("GET \u200B/clients\u200B/{clientId} :: happy path")
    @TmsLink("TECH-143710")
    public void testGetNewClient() {

//        List<Integer> response2 = getClients()
//                .then().assertThat().statusCode(SC_OK)
//                .extract().jsonPath().get("clientId");
//
//        val maxClientID = Collections.max(response2);
//        System.out.println(maxClientID);
//        val AutomationClientID = maxClientID + 1;


//        val body = setUpPostNewClientData(true, Arrays.asList("3", "2", "1"), String.valueOf(AutomationClientID), NewClientName + AutomationClientID + "Update", UsersClient.country,
//                ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2
//                , String.valueOf(AutomationClientID),ctxLimitTotal,false );

//        val response1 = PostUpdateClient(body)
//                .then().assertThat().statusCode(SC_CREATED)
//                .body("clientId", Matchers.is(AutomationClientID))
//                .body("clientName", Matchers.containsString( NewClientName + String.valueOf(AutomationClientID)))
//                .body("active", Matchers.is( true))
//                .body("timezoneId", Matchers.is(Integer.parseInt(ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2)))
//                .body("countryCode", Matchers.is(Integer.parseInt(UsersClient.country)))
//                .body("clickatellAccountId", Matchers.containsString(String.valueOf(AutomationClientID)))
//                .body("signatureRequired", Matchers.is( false))
//                .extract().body();
//        //System.out.println("Response Body size is: " + body.size());

        //Pre conditions: Deleting client configurations
        executeCustomQuery(MY_SQL, format(DELETE_CLIENT_MODULE_CONFIG, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(MY_SQL, format(DELETE_CLIENT_CREDIT_LIMIT, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(MY_SQL, format(DELETE_CLIENT_STAN, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(MY_SQL, format(DELETE_CLIENT_COMMISSION_MODEL, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(MY_SQL, format(DELETE_CLIENT, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(MY_SQL, format(DELETE_ACCOUNT, ProductLookupClient.Client_1206));
        closeDBConnection();
        executeCustomQuery(POSTGRES_SQL, format(DELETE_CLIENT_SYSTEM_ACCESS, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_CLIENT_COMMON, ProductLookupClient.fundingSourceId_1206));

        //Client Creation and API response verifications
        val body = setUpPostNewClientData(true, Arrays.asList("3", "1"), ProductLookupClient.fundingSourceId_1206, Client_1206, UsersClient.country,
                ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2
                , ClickatellAccountId,ctxLimitTotal,false );

        PostNewClient(body)
                .then().assertThat().statusCode(SC_CREATED)
                .body("clientId", Matchers.is(Integer.parseInt(ProductLookupClient.fundingSourceId_1206)))
                .body("clientName", Matchers.containsString(Client_1206))
                .body("active", Matchers.is( true))
                .body("timezoneId", Matchers.is(Integer.parseInt(ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2)))
                .body("countryCode", Matchers.is(Integer.parseInt(UsersClient.country)))
                .body("clickatellAccountId", Matchers.containsString(ClickatellAccountId))
                .body("signatureRequired", Matchers.is( false))
                .body("timestamp", Matchers.notNullValue())
                .extract().body();

        //Getting Client
        getClientsByClientId(ProductLookupClient.fundingSourceId_1206)
                .then().assertThat().statusCode(SC_OK)
                .body("clientId", Matchers.is(Integer.parseInt(ProductLookupClient.fundingSourceId_1206)))
                .body("clientName", Matchers.containsString(Client_1206))
                .body("active", Matchers.is( true))
                .body("timezoneId", Matchers.is(Integer.parseInt(ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2)))
                .body("countryCode", Matchers.is(Integer.parseInt(UsersClient.country)))
                .body("clickatellAccountId", Matchers.containsString(ClickatellAccountId))
                .body("signatureRequired", Matchers.is( false))
                .body("timestamp", Matchers.notNullValue())
                .extract().body();

        //Database Checks
        val SELECT_CLIENT_NAME_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_CLIENT_NAME, ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_CLIENT_NAME_VALUE)
                .contains(ProductLookupClient.Client_1206);
        val SELECT_ACTIVE_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_ACTIVE, ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_ACTIVE_VALUE)
                .contains("true");
        val SELECT_TIMEZONE_ID_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_TIMEZONE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_TIMEZONE_ID_VALUE)
                .contains(ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2);
        val SELECT_COUNTRY_CODE_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_COUNTRY_CODE, ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_COUNTRY_CODE_VALUE)
                .contains(UsersClient.country);
        val SELECT_CLICKATELL_ACCOUNT_ID_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_CLICKATELL_ACCOUNT_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_CLICKATELL_ACCOUNT_ID_VALUE)
                .contains(ClickatellAccountId);
        val SELECT_SIGNATURE_REQUIRED_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_SIGNATURE_REQUIRED, ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_SIGNATURE_REQUIRED_VALUE)
                .contains("false");
        val SELECT_CDC_TIMESTAMP_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_CDC_TIMESTAMP, ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_CDC_TIMESTAMP_VALUE)
                .isNotNull();
        val SELECT_SYSTEM_ID_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_SYSTEM_ID, ProductLookupClient.fundingSourceId_1206));
        List<String> strings = Lists.transform(SELECT_SYSTEM_ID_VALUE, Functions.toStringFunction());
        System.out.println(strings.get(1));
        assertThat(strings.get(1))
                .contains(ReserveAndTransactClient.TestClient3);
        assertThat(strings.get(0))
                .contains(TokenLookupClient.Limit_1);
        closeDBConnection();
        val SELECT_CLIENT_CLIENTROLE_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_CLIENT_CLIENTROLE, ProductLookupClient.fundingSourceId_1206));
        System.out.println(SELECT_CLIENT_CLIENTROLE_VALUE);
        assertThat(SELECT_CLIENT_CLIENTROLE_VALUE)
                .contains(Client_Role_1206);
        val SELECT_CLIENT_NAME_CPGTX_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_CLIENT_NAME_CPGTX, ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_CLIENT_NAME_CPGTX_VALUE)
                .contains(Client_1206);
        val SELECT_CLIENT_CURRENCY_ID_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_CLIENT_CURRENCY_ID, ProductLookupClient.fundingSourceId_1206));
        System.out.println(SELECT_CLIENT_CURRENCY_ID_VALUE);
        assertThat(SELECT_CLIENT_CURRENCY_ID_VALUE)
                .contains(TokenLookupClient.Limit_1);
        val SELECT_CLIENT_TIMEZONE_ID_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_CLIENT_TIMEZONE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_CLIENT_TIMEZONE_ID_VALUE)
                .contains(ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2);
        val SELECT_CLIENT_ACTIVE_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_CLIENT_ACTIVE, ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_CLIENT_ACTIVE_VALUE)
                .contains("true");
        val SELECT_CLIENT_CREDIT_LIMIT_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_CLIENT_CREDIT_LIMIT, ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_CLIENT_CREDIT_LIMIT_VALUE)
                .contains(ctxLimitTotal);
        val SELECT_CLIENT_MODULE_CONFIG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_CLIENT_MODULE_CONFIG, ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_CLIENT_MODULE_CONFIG_VALUE)
                .isNotNull();
        val SELECT_CLIENT_STAN_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_CLIENT_STAN, ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_CLIENT_STAN_VALUE)
                .isNotNull();
        val SELECT_CLIENT_COMMISSION_MODEL_TYPE_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_CLIENT_COMMISSION_MODEL_TYPE , ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_CLIENT_COMMISSION_MODEL_TYPE_VALUE)
                .isNotNull();
        val SELECT_ACCOUNT_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_ACCOUNT , ProductLookupClient.Client_1206));
        assertThat(SELECT_ACCOUNT_VALUE)
                .contains(ProductLookupClient.Client_1206);
        //Post Conditions: Deleting client configuration
        executeCustomQuery(MY_SQL, format(DELETE_CLIENT_MODULE_CONFIG, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(MY_SQL, format(DELETE_CLIENT_CREDIT_LIMIT, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(MY_SQL, format(DELETE_CLIENT_STAN, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(MY_SQL, format(DELETE_CLIENT_COMMISSION_MODEL, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(MY_SQL, format(DELETE_CLIENT, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(MY_SQL, format(DELETE_ACCOUNT, ProductLookupClient.Client_1206));
        closeDBConnection();
        executeCustomQuery(POSTGRES_SQL, format(DELETE_CLIENT_SYSTEM_ACCESS, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_CLIENT_COMMON, ProductLookupClient.fundingSourceId_1206));

    }
    @Test
    @Description("PUT \u200B/clients\u200B/{clientId} :: happy path")
    @TmsLink("TECH-143694")
    public void testUpdateNewClient() {

        //Pre conditions: Deleting client configurations
        executeCustomQuery(MY_SQL, format(DELETE_CLIENT_MODULE_CONFIG, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(MY_SQL, format(DELETE_CLIENT_CREDIT_LIMIT, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(MY_SQL, format(DELETE_CLIENT_STAN, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(MY_SQL, format(DELETE_CLIENT_COMMISSION_MODEL, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(MY_SQL, format(DELETE_CLIENT, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(MY_SQL, format(DELETE_ACCOUNT, ProductLookupClient.Client_1206));
        closeDBConnection();
        executeCustomQuery(POSTGRES_SQL, format(DELETE_CLIENT_SYSTEM_ACCESS, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_CLIENT_COMMON, ProductLookupClient.fundingSourceId_1206));

        //Client Creation and API response verifications
        val body = setUpPostNewClientData(true, Arrays.asList("3", "1"), ProductLookupClient.fundingSourceId_1206, Client_1206, UsersClient.country,
                ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2
                , ClickatellAccountId,ctxLimitTotal,false );

        PostNewClient(body)
                .then().assertThat().statusCode(SC_CREATED)
                .body("clientId", Matchers.is(Integer.parseInt(ProductLookupClient.fundingSourceId_1206)))
                .body("clientName", Matchers.containsString(Client_1206))
                .body("active", Matchers.is( true))
                .body("timezoneId", Matchers.is(Integer.parseInt(ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2)))
                .body("countryCode", Matchers.is(Integer.parseInt(UsersClient.country)))
                .body("clickatellAccountId", Matchers.containsString(ClickatellAccountId))
                .body("signatureRequired", Matchers.is( false))
                .body("timestamp", Matchers.notNullValue())
                .extract().body();

        //Getting Client
        String timestamp = getClientsByClientId(ProductLookupClient.fundingSourceId_1206)
                .then().assertThat().statusCode(SC_OK)
                .body("clientId", Matchers.is(Integer.parseInt(ProductLookupClient.fundingSourceId_1206)))
                .body("clientName", Matchers.containsString(Client_1206))
                .body("active", Matchers.is( true))
                .body("timezoneId", Matchers.is(Integer.parseInt(ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2)))
                .body("countryCode", Matchers.is(Integer.parseInt(UsersClient.country)))
                .body("clickatellAccountId", Matchers.containsString(ClickatellAccountId))
                .body("signatureRequired", Matchers.is( false))
                .body("timestamp", Matchers.notNullValue())
                .extract().response().path("timestamp");
        //Update Client
        val body_Update = setUpPostNewClientData(true, Arrays.asList("3", "1"), ProductLookupClient.fundingSourceId_1206, Client_1206_update, CountryCode_710,
                TokenLookupClient.Limit_1
                , ClickatellAccountId,ctxLimitTotal_Update,false, timestamp);
        PostUpdateClient(body_Update, ProductLookupClient.fundingSourceId_1206)
                .then().assertThat().statusCode(SC_OK)
                .body("clientId", Matchers.is(Integer.parseInt(ProductLookupClient.fundingSourceId_1206)))
                .body("clientName", Matchers.containsString(Client_1206_update))
                .body("active", Matchers.is( true))
                .body("timezoneId", Matchers.is(Integer.parseInt(TokenLookupClient.Limit_1)))
                .body("countryCode", Matchers.is(Integer.parseInt(CountryCode_710)))
                .body("clickatellAccountId", Matchers.containsString(ClickatellAccountId))
                .body("signatureRequired", Matchers.is( false))
                .body("timestamp", Matchers.notNullValue());

        //Database Checks
        val SELECT_CLIENT_NAME_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_CLIENT_NAME, ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_CLIENT_NAME_VALUE)
                .contains(ProductLookupClient.Client_1206);
        val SELECT_ACTIVE_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_ACTIVE, ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_ACTIVE_VALUE)
                .contains("true");
        val SELECT_TIMEZONE_ID_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_TIMEZONE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_TIMEZONE_ID_VALUE)
                .contains(TokenLookupClient.Limit_1);
        val SELECT_COUNTRY_CODE_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_COUNTRY_CODE, ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_COUNTRY_CODE_VALUE)
                .contains(CountryCode_710);
        val SELECT_CLICKATELL_ACCOUNT_ID_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_CLICKATELL_ACCOUNT_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_CLICKATELL_ACCOUNT_ID_VALUE)
                .contains(ClickatellAccountId);
        val SELECT_SIGNATURE_REQUIRED_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_SIGNATURE_REQUIRED, ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_SIGNATURE_REQUIRED_VALUE)
                .contains("false");
        val SELECT_CDC_TIMESTAMP_VALUE = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(SELECT_CDC_TIMESTAMP, ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_CDC_TIMESTAMP_VALUE)
                .isNotNull();
        val SELECT_SYSTEM_ID_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_SYSTEM_ID, ProductLookupClient.fundingSourceId_1206));
        List<String> strings = Lists.transform(SELECT_SYSTEM_ID_VALUE, Functions.toStringFunction());
        System.out.println(strings.get(1));
        assertThat(strings.get(1))
                .contains(ReserveAndTransactClient.fundingSourceId);
        assertThat(strings.get(0))
                .contains(TokenLookupClient.Limit_1);
        closeDBConnection();
        val SELECT_CLIENT_CLIENTROLE_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_CLIENT_CLIENTROLE, ProductLookupClient.fundingSourceId_1206));
        System.out.println(SELECT_CLIENT_CLIENTROLE_VALUE);
        assertThat(SELECT_CLIENT_CLIENTROLE_VALUE)
                .contains(Client_Role_1206);
        val SELECT_CLIENT_NAME_CPGTX_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_CLIENT_NAME_CPGTX, ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_CLIENT_NAME_CPGTX_VALUE)
                .contains(Client_1206_update);
        val SELECT_CLIENT_CURRENCY_ID_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_CLIENT_CURRENCY_ID, ProductLookupClient.fundingSourceId_1206));
        System.out.println(SELECT_CLIENT_CURRENCY_ID_VALUE);
        assertThat(SELECT_CLIENT_CURRENCY_ID_VALUE)
                .contains(ReserveAndTransactClient.TestClient3);
        val SELECT_CLIENT_TIMEZONE_ID_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_CLIENT_TIMEZONE_ID, ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_CLIENT_TIMEZONE_ID_VALUE)
                .contains(TokenLookupClient.Limit_1);
        val SELECT_CLIENT_ACTIVE_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_CLIENT_ACTIVE, ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_CLIENT_ACTIVE_VALUE)
                .contains("true");
//        val SELECT_CLIENT_CREDIT_LIMIT_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_CLIENT_CREDIT_LIMIT, ProductLookupClient.fundingSourceId_1206));
//        assertThat(SELECT_CLIENT_CREDIT_LIMIT_VALUE)
//                .contains(ctxLimitTotal_Update);
        val SELECT_CLIENT_MODULE_CONFIG_VALUE = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_CLIENT_MODULE_CONFIG, ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_CLIENT_MODULE_CONFIG_VALUE)
                .isNotNull();
        val SELECT_CLIENT_STAN_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_CLIENT_STAN, ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_CLIENT_STAN_VALUE)
                .isNotNull();
        val SELECT_CLIENT_COMMISSION_MODEL_TYPE_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_CLIENT_COMMISSION_MODEL_TYPE , ProductLookupClient.fundingSourceId_1206));
        assertThat(SELECT_CLIENT_COMMISSION_MODEL_TYPE_VALUE)
                .isNotNull();
        val SELECT_ACCOUNT_VALUE = executeCustomQueryAndReturnValue(MY_SQL, format(SELECT_ACCOUNT , ProductLookupClient.Client_1206));
        assertThat(SELECT_ACCOUNT_VALUE)
                .contains(ProductLookupClient.Client_1206);
        //Post Conditions: Deleting client configuration
        executeCustomQuery(MY_SQL, format(DELETE_CLIENT_MODULE_CONFIG, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(MY_SQL, format(DELETE_CLIENT_CREDIT_LIMIT, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(MY_SQL, format(DELETE_CLIENT_STAN, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(MY_SQL, format(DELETE_CLIENT_COMMISSION_MODEL, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(MY_SQL, format(DELETE_CLIENT, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(MY_SQL, format(DELETE_ACCOUNT, ProductLookupClient.Client_1206));
        closeDBConnection();
        executeCustomQuery(POSTGRES_SQL, format(DELETE_CLIENT_SYSTEM_ACCESS, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_CLIENT_COMMON, ProductLookupClient.fundingSourceId_1206));

    }
}
