package api.mno_lookup;

import api.domains.mno_lookup.model.MnoLookupResponse;
import api.enums.Port;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import util.base_test.BaseApiTest;

import java.util.Hashtable;
import java.util.Map;

import static api.clients.MnoLookupClient.getMnoInfo;
import static db.clients.HibernateBaseClient.executeCustomQueryAndReturnValue;
import static db.custom_queries.MnoLookupQueries.GET_LOOKUP_RESPONSE_CODE;
import static db.enums.Sessions.POSTGRES_SQL;
import static java.lang.String.format;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

public class MnoLookupTest extends BaseApiTest {

    @Test
    @Description("30049 :: client-mno-lookup-service :: public internal :: GET /mnp/mnpLookup :: MNO Lookup (1.0)")
    @TmsLink("TECH-54461")
    public void testMnoLookupSuccess() {
        Map <String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId","3");
        queryParams.put("msisdn","2348038382068");
        queryParams.put("countryCallingCode","234");

        val lookupRef = getMnoInfo(Port.MNO_LOOKUP, queryParams)
                .then().assertThat().statusCode(SC_OK)
                .body("msisdn", Matchers.containsString("2348038382068"))
                .body("countryCallingCode", Matchers.containsString("234"))
                .body("responseCode", Matchers.containsString("0000"))
                .body("responseMessage", Matchers.containsString("SUCCESS"))
                .extract().body().as(MnoLookupResponse.class).getLookupRef();

        //Verify record is added to the db - response_code = 0000
        val responseCode = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(GET_LOOKUP_RESPONSE_CODE, lookupRef));
        assertThat(responseCode)
                .as("No record in DB")
                .contains("0000");
    }

}

