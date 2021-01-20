package api.product_lookup;

import api.enums.Port;
import api.enums.Version;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import util.base_test.BaseApiTest;

import static api.clients.ProductLookupClient.getProductInfo;
import static db.clients.HibernateBaseClient.executeCustomQueryAndReturnValue;
import static db.custom_queries.ProductLookupQueries.GET_CLIENT_DETAILS_BY_CLIENT_ID;
import static db.enums.Sessions.POSTGRES_SQL;
import static java.lang.String.format;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

public class ProductLookupInfoTest extends BaseApiTest {

    @Test
    @Description("32000 :: Product Lookup :: Smoke")
    @TmsLink("TECH-60228")
    public void testProductLookupInformation() {
        getProductInfo(Port.PRODUCT_LOOKUP, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("[0].publicProduct.id", Matchers.is(130))
                .body("[0].publicProduct.productTypeId", Matchers.is(3))
                .body("[0].publicProduct.productTypeName", Matchers.containsString("Airtime"));

        getProductInfo(Port.PRODUCT_LOOKUP, Version.V2)
                .then().assertThat().statusCode(SC_OK)
                .body("[0].publicProduct.id", Matchers.is(130),
                        "[0].publicProduct.productTypeId", Matchers.is(3),
                        "[0].publicProduct.productTypeName", Matchers.containsString("Airtime"));

    }

    @Test
    @Description("")
    @TmsLink("")
    public void testSomeDBValidation() {
       //val mySql = executeCustomQueryAndReturnValue(MY_SQL, GET_CLIENT_BY_CLIENT_ID);
       val postgres = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(GET_CLIENT_DETAILS_BY_CLIENT_ID, 100));

        assertSoftly(soft -> {
//            soft.assertThat(mySql)
//                    .as("My SQL query result should not be empty")
//                    .contains("Clickatell Test NG 3");

            soft.assertThat(postgres)
                    .as("Postgres SQL query result should not be empty")
                    .contains("Eco Bank 100");
        });
    }

}
