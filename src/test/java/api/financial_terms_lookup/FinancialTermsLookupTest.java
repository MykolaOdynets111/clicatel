package api.financial_terms_lookup;

import api.clients.ReserveAndTransactClient;
import api.enums.Port;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import util.base_test.BaseApiTest;

import static api.clients.FinancialTermsLookupClient.getFinancialTermDetails;
import static org.apache.http.HttpStatus.SC_OK;


public class FinancialTermsLookupTest extends BaseApiTest {

    @Test
    @Description("30309 :: financial-terms-lookup :: public internal :: GET /financialTerms :: Financial Terms Lookup API (1.0)")
    @TmsLink("TECH-50897")
    public void testFinancialTermsLookupSuccess() {

        //get financial terms
        getFinancialTermDetails(Port.FINANCIAL_TERMS, Integer.parseInt(ReserveAndTransactClient.TestClient3), Integer.parseInt(ReserveAndTransactClient.ProductAirtel_100), Integer.parseInt(ReserveAndTransactClient.PurchaseAmount10000))
                .then().assertThat().statusCode(SC_OK)
                .body("clientId", Matchers.is(Integer.parseInt(ReserveAndTransactClient.TestClient3)))
                .body("productId", Matchers.is(Integer.parseInt(ReserveAndTransactClient.ProductAirtel_100)))
                .body("purchaseAmount", Matchers.is(Integer.parseInt(ReserveAndTransactClient.PurchaseAmount10000)))
                .body("breakdown.vendAmount", Matchers.notNullValue())
                .body("breakdown.clientShareAmount", Matchers.notNullValue())
                .body("breakdown.reserveAmount", Matchers.notNullValue())
                .body("breakdown.settlementAmount", Matchers.notNullValue());
    }

}
