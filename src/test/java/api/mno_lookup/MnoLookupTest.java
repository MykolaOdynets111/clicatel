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
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.INT_ARRAY;

public class MnoLookupTest extends BaseApiTest {

    @Test
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
}

