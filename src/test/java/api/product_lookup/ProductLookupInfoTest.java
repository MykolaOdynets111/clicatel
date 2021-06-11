package api.product_lookup;

import api.clients.ProductLookupClient;
import api.clients.ReserveAndTransactClient;
import api.enums.Port;
import api.enums.Version;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import util.base_test.BaseApiTest;

import java.util.Hashtable;
import java.util.Map;

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
    public void testProductLookupInformationSmokeSuccess() {
        Map <String, String> map = new Hashtable<>();
        map.put("clientId", ReserveAndTransactClient.TestClient3);
        map.put("productId", ProductLookupClient.ProductAirtel_130);

        getProductInfo(Port.PRODUCT_LOOKUP, Version.V2, map)
                .then().assertThat().statusCode(SC_OK)
                .body("[0].publicProduct.id", Matchers.is(Integer.parseInt(ProductLookupClient.ProductAirtel_130)))
                .body("[0].publicProduct.productTypeId", Matchers.is(Integer.parseInt(ProductLookupClient.ProductTypeAirtime_3)))
                .body("[0].publicProduct.productTypeName", Matchers.containsString(ProductLookupClient.ProductTypeNameAirtime));

        getProductInfo(Port.PRODUCT_LOOKUP, Version.V2, map)
                .then().assertThat().statusCode(SC_OK)
                .body("[0].publicProduct.id", Matchers.is(Integer.parseInt(ProductLookupClient.ProductAirtel_130)),
                        "[0].publicProduct.productTypeId", Matchers.is(Integer.parseInt(ProductLookupClient.ProductTypeAirtime_3)),
                        "[0].publicProduct.productTypeName", Matchers.containsString(ProductLookupClient.ProductTypeNameAirtime));

    }


    @Test
    @Description("32000 :: public internal :: GET /public/productInfo :: Product Lookup API (1.0)")
    @TmsLink("TECH-50880")
    public void testProductLookupInformationV1Success() {
        Map <String, String> map = new Hashtable<>();
        map.put("clientId",ReserveAndTransactClient.TestClient3);
        map.put("productId",ProductLookupClient.ProductAirtel_130);

        getProductInfo(Port.PRODUCT_LOOKUP, map)
                .then().assertThat().statusCode(SC_OK)
                .body("[0].product.id", Matchers.is(Integer.parseInt(ProductLookupClient.ProductAirtel_130)))
                .body("[0].product.productTypeId", Matchers.is(Integer.parseInt(ProductLookupClient.ProductTypeAirtime_3)))
                .body("[0].product.productTypeName", Matchers.containsString(ProductLookupClient.ProductTypeNameAirtime));

        getProductInfo(Port.PRODUCT_LOOKUP, map)
                .then().assertThat().statusCode(SC_OK)
                .body("[0].product.id", Matchers.is(Integer.parseInt(ProductLookupClient.ProductAirtel_130)),
                        "[0].product.productTypeId", Matchers.is(Integer.parseInt(ProductLookupClient.ProductTypeAirtime_3)),
                        "[0].product.productTypeName", Matchers.containsString(ProductLookupClient.ProductTypeNameAirtime));

    }


    @Test
    @Description("32000 :: public internal :: GET /public/v2/productInfo :: Product Lookup API (2.0)")
    @TmsLink("TECH-54434")
    public void testProductLookupInformationV2Success() {
        Map <String, String> map = new Hashtable<>();
        map.put("clientId",ReserveAndTransactClient.TestClient3);
        map.put("productId",ProductLookupClient.ProductAirtel_130);

        getProductInfo(Port.PRODUCT_LOOKUP, Version.V2, map)
                .then().assertThat().statusCode(SC_OK)
                .body("[0].publicProduct.id", Matchers.is(Integer.parseInt(ProductLookupClient.ProductAirtel_130)))
                .body("[0].publicProduct.productTypeId", Matchers.is(Integer.parseInt(ProductLookupClient.ProductTypeAirtime_3)))
                .body("[0].publicProduct.productTypeName", Matchers.containsString(ProductLookupClient.ProductTypeNameAirtime));

        getProductInfo(Port.PRODUCT_LOOKUP, Version.V2, map)
                .then().assertThat().statusCode(SC_OK)
                .body("[0].publicProduct.id", Matchers.is(Integer.parseInt(ProductLookupClient.ProductAirtel_130)),
                        "[0].publicProduct.productTypeId", Matchers.is(Integer.parseInt(ProductLookupClient.ProductTypeAirtime_3)),
                        "[0].publicProduct.productTypeName", Matchers.containsString(ProductLookupClient.ProductTypeNameAirtime));

    }


    @Test
    @Description("32000 :: public internal :: GET /public/v3/productInfo :: Product Lookup API (3.0)")
    @TmsLink("TECH-66575")
    public void testProductLookupInformationV3Success() {
        Map <String, String> map = new Hashtable<>();
        map.put("clientId",ReserveAndTransactClient.TestClient3);
        //map.put("targetIdentifier","2348085767001");
        map.put("productId",ProductLookupClient.ProductAirtel_130);
        //map.put("productTypeId","3");
        //map.put("includeInactive","true");
        //map.put("topSeller","true");
        //map.put("subscriberType","EBU");
        //map.put("topSellerPlatform","SMS");
        //map.put("purchaseMedium","Card");
        //map.put("channelId","2");
        //map.put("vendorId","103");
        //map.put("sortBy","singlePrice,asc");

        getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .body("[0].publicProduct.id", Matchers.is(Integer.parseInt(ProductLookupClient.ProductAirtel_130)))
                .body("[0].publicProduct.productTypeId", Matchers.is(Integer.parseInt(ProductLookupClient.ProductTypeAirtime_3)))
                .body("[0].publicProduct.productTypeName", Matchers.containsString(ProductLookupClient.ProductTypeNameAirtime));

        getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .body("[0].publicProduct.id", Matchers.is(Integer.parseInt(ProductLookupClient.ProductAirtel_130)),
                        "[0].publicProduct.productTypeId", Matchers.is(Integer.parseInt(ProductLookupClient.ProductTypeAirtime_3)),
                        "[0].publicProduct.productTypeName", Matchers.containsString(ProductLookupClient.ProductTypeNameAirtime));

    }



    @Test
    @Description("DB Test")
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
