package api.mno_lookup;
import api.clients.MnoLookupClient;
import api.clients.ReserveAndTransactClient;
import api.domains.mno_lookup.model.MnoLookupResponse;
import api.enums.CountryCode;
import api.enums.Port;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import util.base_test.BaseApiTest;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;
import static api.clients.MnoLookupClient.getMnoCountryInfo;
import static api.clients.MnoLookupClient.getMnoInfo;
import static db.clients.HibernateBaseClient.executeCustomQueryAndReturnValue;
import static db.custom_queries.MnoLookupQueries.GET_LOOKUP_RESPONSE_CODE;
import static db.enums.Sessions.POSTGRES_SQL;
import static java.lang.String.format;
import static org.apache.http.HttpStatus.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.INT_ARRAY;

public class MnoLookupTest extends BaseApiTest {

    @Test(groups = {"smokeTest"})
    @Description("30049 :: client-mno-lookup-service :: public internal :: GET /mnp/mnpLookup :: MNO Lookup (1.0)")
    @TmsLink("TECH-54461")
    public void testMnoLookupSuccess() {
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId", ReserveAndTransactClient.TestClient3);
        queryParams.put("msisdn", ReserveAndTransactClient.IdentifierV1);
        queryParams.put("countryCallingCode", MnoLookupClient.Nigeria_CC);

        val lookupRef = getMnoInfo(Port.MNO_LOOKUP, queryParams)
                .then().assertThat().statusCode(SC_OK)
                .body("msisdn", Matchers.containsString(ReserveAndTransactClient.IdentifierV1))
                .body("countryCallingCode", Matchers.containsString(MnoLookupClient.Nigeria_CC))
                .body("operatorCode", Matchers.containsString(MnoLookupClient.OperatorCode_MTN_NG))
                .body("operatorName", Matchers.containsString(MnoLookupClient.OperatorName_Nigeria))
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.Success))
                .extract().body().as(MnoLookupResponse.class).getLookupRef();

        //Verify record is added to the db - response_code = 0000
//        val responseCode = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(GET_LOOKUP_RESPONSE_CODE, lookupRef));
//        assertThat(responseCode)
//                .as("No record in DB")
//                .contains("0000");
    }

    @Test
    @Description("30049 :: client-mno-lookup-service :: GET  /mnp/lookupCountryPrefix")
    @TmsLink("TECH-43153")
    public void testMnoLookupCountryPrefix() {
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("countryCode", String.valueOf(CountryCode.NG));

        val lookupRef = getMnoCountryInfo(Port.MNO_LOOKUP, queryParams)
                .then().assertThat().statusCode(SC_OK)
                .body("countryName", Matchers.equalTo(Arrays.asList(MnoLookupClient.Nigeria)))
                .body("dialingPrefix", Matchers.equalTo(Arrays.asList(Integer.parseInt(MnoLookupClient.Nigeria_CC))))
                .body("countryCode", Matchers.equalTo(Arrays.asList(String.valueOf(CountryCode.NG))));
    }

    @Test
    @Description("30049 :: client-mno-lookup-service :: GET /mnp/mnpLookup :: 9020 'Unable to identify operator' scenario")
    @TmsLink("TECH-79997")
    public void testMnoLookupInvalidMsisdn() {
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId", ReserveAndTransactClient.TestClient3);
        queryParams.put("msisdn", MnoLookupClient.InValidMnoMsisdn);
        queryParams.put("countryCallingCode", MnoLookupClient.Nigeria_CC);

        val lookupRef = getMnoInfo(Port.MNO_LOOKUP, queryParams)
                .then().assertThat().statusCode(SC_NOT_FOUND)
                .body("responseCode", Matchers.containsString(MnoLookupClient.ResponseCode_9020))
                .body("responseMessage", Matchers.containsString(MnoLookupClient.responseMessageInvalidMsisdnMnoLookup))
                .extract().body().as(MnoLookupResponse.class).getLookupRef();

    }

    @Test
    @Description("30049-client-mno-lookup-service :: GET \u200B/mnp\u200B/mnpLookup :: 9010 \"Incorrect msisdn country combination\" scenario")
    @TmsLink("TECH-169530")
    public void testMnoLookupIncorrectMsisdnCountryCombination() {
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId", ReserveAndTransactClient.TestClient3);
        queryParams.put("msisdn", ReserveAndTransactClient.IdentifierV3);
        queryParams.put("countryCallingCode", MnoLookupClient.countryCallingCode_27);

        val lookupRef = getMnoInfo(Port.MNO_LOOKUP, queryParams)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(MnoLookupClient.ResponseCode_9010))
                .body("responseMessage", Matchers.containsString(MnoLookupClient.responseMessageIncorrectMsisdnCountryCombination))
                .extract().body().as(MnoLookupResponse.class).getLookupRef();
    }

    @Test
    @Description("30049-client-mno-lookup-service :: GET \u200B/mnp\u200B/mnpLookup :: 9090 \"Unknown country code/prefix\" scenario")
    @TmsLink("TECH-169533")
    public void testMnoLookupUnknownCountryCode() {
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId", ReserveAndTransactClient.TestClient3);
        queryParams.put("msisdn", ReserveAndTransactClient.IdentifierV3);
        queryParams.put("countryCallingCode", MnoLookupClient.countryCallingCode_999);

        val lookupRef = getMnoInfo(Port.MNO_LOOKUP, queryParams)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("responseCode", Matchers.containsString(MnoLookupClient.ResponseCode_9090))
                .body("responseMessage", Matchers.containsString(MnoLookupClient.responseMessageUnknownCountryCode))
                .extract().body().as(MnoLookupResponse.class).getLookupRef();
    }

    @Test
    @Description("30049-client-mno-lookup-service :: GET \u200B/mnp\u200B/mnpLookup :: 0000 success for the \"MTN\" operatorCode")
    @TmsLink("TECH-169546")
    public void testMnoLookup0000SuccessForMtnOperatorCode() {
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId", ReserveAndTransactClient.TestClient3);
        queryParams.put("msisdn", MnoLookupClient.ValidMsisdn);
        queryParams.put("countryCallingCode", MnoLookupClient.countryCallingCode_27);

        val lookupRef = getMnoInfo(Port.MNO_LOOKUP, queryParams)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("msisdn", Matchers.containsString(MnoLookupClient.ValidMsisdn))
                .body("countryCallingCode", Matchers.containsString(MnoLookupClient.countryCallingCode_27))
                .body("operatorCode", Matchers.containsString(MnoLookupClient.OperatorCode_SOUMFB))
                .body("operatorName", Matchers.containsString(MnoLookupClient.OperatorName_MTN))
                .extract().body().as(MnoLookupResponse.class).getLookupRef();
    }

    @Test
    @Description("30049-client-mno-lookup-service :: GET \u200B/mnp\u200B/mnpLookup :: 9090 \"Unknown country code/prefix\" scenario")
    @TmsLink("TECH-169540")
    public void testMnoLookupCountryCodeNotPresentInParams() {
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId", ReserveAndTransactClient.TestClient3);
        queryParams.put("msisdn", ReserveAndTransactClient.IdentifierV3);

        val lookupRef = getMnoInfo(Port.MNO_LOOKUP, queryParams)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("status", Matchers.is(Integer.parseInt(ReserveAndTransactClient.ProductMTN_ZA_400)))
                .body("error", Matchers.containsString(MnoLookupClient.error_BadRequest))
                .extract().body().as(MnoLookupResponse.class).getLookupRef();
    }

    @Test
    @Description("30049-client-mno-lookup-service :: GET \u200B/mnp\u200B/mnpLookup :: 400 \"Required String parameter 'msisdn' is not present\" scenario ")
    @TmsLink("TECH-169537")
    public void testMnoLookupMsisdnNotPresentInParams() {
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId", ReserveAndTransactClient.TestClient3);
        queryParams.put("countryCallingCode", MnoLookupClient.countryCallingCode_999);

        val lookupRef = getMnoInfo(Port.MNO_LOOKUP, queryParams)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("status", Matchers.is(Integer.parseInt(ReserveAndTransactClient.ProductMTN_ZA_400)))
                .body("error", Matchers.containsString(MnoLookupClient.error_BadRequest))
                .extract().body().as(MnoLookupResponse.class).getLookupRef();
    }

    @Test
    @Description("30049-client-mno-lookup-service :: GET \u200B/mnp\u200B/mnpLookup :: 400 \"Required int parameter 'clientId' is not present\" scenario")
    @TmsLink("TECH-169535")
    public void testMnoLookupClientIdNotPresent() {
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("msisdn", MnoLookupClient.ValidMsisdn);
        queryParams.put("countryCallingCode", MnoLookupClient.countryCallingCode_27);

        val lookupRef = getMnoInfo(Port.MNO_LOOKUP, queryParams)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("status", Matchers.is(Integer.parseInt(ReserveAndTransactClient.ProductMTN_ZA_400)))
                .body("error", Matchers.containsString(MnoLookupClient.error_BadRequest))
                .extract().body().as(MnoLookupResponse.class).getLookupRef();
    }

    @Test
    @Description("30049-client-mno-lookup-service :: GET \u200B/mnp\u200B/mnpLookup :: 0000 success for the \"Vodacom\" operatorCode")
    @TmsLink("TECH-169545")
    public void testMnoLookup0000SuccessForVodacomOperatorCode() {
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId", ReserveAndTransactClient.TestClient3);
        queryParams.put("msisdn", MnoLookupClient.ValidMsisdnVodacom);
        queryParams.put("countryCallingCode", MnoLookupClient.countryCallingCode_27);

        val lookupRef = getMnoInfo(Port.MNO_LOOKUP, queryParams)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("msisdn", Matchers.containsString(MnoLookupClient.ValidMsisdnVodacom))
                .body("countryCallingCode", Matchers.containsString(MnoLookupClient.countryCallingCode_27))
                .body("operatorCode", Matchers.containsString(MnoLookupClient.OperatorCode_SOUVXP))
                .body("operatorName", Matchers.containsString(MnoLookupClient.OperatorName_Vodacom))
                .extract().body().as(MnoLookupResponse.class).getLookupRef();
    }

    @Test
    @Description("30049-client-mno-lookup-service :: GET \u200B/mnp\u200B/mnpLookup :: 0000 success for the \"AIRTEL\" operatorCode")
    @TmsLink("TECH-169544")
    public void testMnoLookup0000SuccessForAirTelOperatorCode() {
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId", ReserveAndTransactClient.TestClient3);
        queryParams.put("msisdn", MnoLookupClient.ValidMsisdnAirTel);
        queryParams.put("countryCallingCode", MnoLookupClient.countryCallingCode_234);

        val lookupRef = getMnoInfo(Port.MNO_LOOKUP, queryParams)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("msisdn", Matchers.containsString(MnoLookupClient.ValidMsisdnAirTel))
                .body("countryCallingCode", Matchers.containsString(MnoLookupClient.countryCallingCode_234))
                .body("operatorCode", Matchers.containsString(MnoLookupClient.OperatorCode_AIRTEL))
                .body("operatorName", Matchers.containsString(MnoLookupClient.OperatorName_BhartiAirtelTelecommunicationsCompany))
                .extract().body().as(MnoLookupResponse.class).getLookupRef();
    }

    @Test
    @Description("30049-client-mno-lookup-service :: GET \u200B/mnp\u200B/mnpLookup :: 0000 success for the \"MTN-NG\" operatorCode")
    @TmsLink("TECH-169541")
    public void testMnoLookup0000SuccessForMTNNGOperatorCode() {
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId", ReserveAndTransactClient.TestClient3);
        queryParams.put("msisdn", MnoLookupClient.ValidMsisdnMTNNG);
        queryParams.put("countryCallingCode", MnoLookupClient.countryCallingCode_234);

        val lookupRef = getMnoInfo(Port.MNO_LOOKUP, queryParams)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("msisdn", Matchers.containsString(MnoLookupClient.ValidMsisdnMTNNG))
                .body("countryCallingCode", Matchers.containsString(MnoLookupClient.countryCallingCode_234))
                .body("operatorCode", Matchers.containsString(MnoLookupClient.OperatorCode_MTNNG))
                .body("operatorName", Matchers.containsString(MnoLookupClient.OperatorName_MobileTelecommunicationsNetworkNigeria))
                .extract().body().as(MnoLookupResponse.class).getLookupRef();
    }
    @Test
    @Description("30049-client-mno-lookup-service :: GET \u200B/mnp\u200B/mnpLookup :: 0000 success for the \"9MOBILE\" operatorCode")
    @TmsLink("TECH-169542")
    public void testMnoLookup0000SuccessFor9MobileOperatorCode() {
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId", ReserveAndTransactClient.TestClient3);
        queryParams.put("msisdn", MnoLookupClient.ValidMsisdn9Mobile);
        queryParams.put("countryCallingCode", MnoLookupClient.countryCallingCode_234);

        val lookupRef = getMnoInfo(Port.MNO_LOOKUP, queryParams)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("msisdn", Matchers.containsString(MnoLookupClient.ValidMsisdn9Mobile))
                .body("countryCallingCode", Matchers.containsString(MnoLookupClient.countryCallingCode_234))
                .body("operatorCode", Matchers.containsString(MnoLookupClient.OperatorCode_9MOBILE))
                .body("operatorName", Matchers.containsString(MnoLookupClient.OperatorName_9MobileTelecoms))
                .extract().body().as(MnoLookupResponse.class).getLookupRef();
    }

    @Test
    @Description("30049-client-mno-lookup-service :: GET \u200B/mnp\u200B/mnpLookup :: 0000 success for the \"GLO\" operatorCode")
    @TmsLink("TECH-169543")
    public void testMnoLookup0000SuccessForGLOOperatorCode() {
        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId", ReserveAndTransactClient.TestClient3);
        queryParams.put("msisdn", MnoLookupClient.ValidMsisdnGLO);
        queryParams.put("countryCallingCode", MnoLookupClient.countryCallingCode_234);

        val lookupRef = getMnoInfo(Port.MNO_LOOKUP, queryParams)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("msisdn", Matchers.containsString(MnoLookupClient.ValidMsisdnGLO))
                .body("countryCallingCode", Matchers.containsString(MnoLookupClient.countryCallingCode_234))
                .body("operatorCode", Matchers.containsString(MnoLookupClient.OperatorCode_GLO))
                .body("operatorName", Matchers.containsString(MnoLookupClient.OperatorName_GlobaComTelecommunicationsCompany))
                .extract().body().as(MnoLookupResponse.class).getLookupRef();
    }
}

