package api.inflight_transaction_lookup;

import api.enums.Port;
import api.enums.Version;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import util.base_test.BaseApiTest;

import static api.clients.ProductLookupClient.getProductInfo;
import static org.apache.http.HttpStatus.SC_OK;
public class InFlightTransactionLookupTest extends BaseApiTest {

    @Test
    @Description("32000 :: Product Lookup :: Smoke")
    @TmsLink("TECH-60228")
    public void testProductLookupInformation() {
        getProductInfo(Port.PRODUCT_LOOKUP, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("[0].publicProduct.id", Matchers.is(130))
                .body("[0].publicProduct.productTypeId", Matchers.is(3))
                .body("[0].publicProduct.productTypeName", Matchers.containsString("Airtime"));
    }

}
