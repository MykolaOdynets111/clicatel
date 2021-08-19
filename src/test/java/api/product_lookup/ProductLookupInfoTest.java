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
    @TmsLink("TECH-60228") //Not in JIRA
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
        map.put("productId",ProductLookupClient.ProductAirtel_130);

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



//    @Test
//    @Description("DB Test")
//    @TmsLink("")
//    public void testSomeDBValidation() {
//       //val mySql = executeCustomQueryAndReturnValue(MY_SQL, GET_CLIENT_BY_CLIENT_ID);
//       val postgres = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(GET_CLIENT_DETAILS_BY_CLIENT_ID, 100));
//
//        assertSoftly(soft -> {
////            soft.assertThat(mySql)
////                    .as("My SQL query result should not be empty")
////                    .contains("Clickatell Test NG 3");
//
//            soft.assertThat(postgres)
//                    .as("Postgres SQL query result should not be empty")
//                    .contains("Eco Bank 100");
//        });
//    }

    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: if unknown MSISDN and mtn_ng vendor id is provided then all vendor products should be returned in the response")
    @TmsLink("TECH-117587")
    public void testProductLookupIfUnknownMSISDNAndVendorIdIsProvided() {
        Map <String, String> map = new Hashtable<>();
        map.put("clientId",ProductLookupClient.TestClient250);
        map.put("vendorId",ProductLookupClient.Vendor100);
        map.put("targetIdentifier",ProductLookupClient.Identifier_13);

        getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .body("[0].publicProduct.id", Matchers.is(Integer.parseInt(ReserveAndTransactClient.ProductAirtel_100)))
                .body("[0].publicProduct.id", Matchers.not(ProductLookupClient.Product_854))
                .body("[0].publicProduct.id", Matchers.not(ProductLookupClient.Product_130));
    }


    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: if aritel MSISDN and mtn_ng vendorId are provided then only vendor products should be returned in the response")
    @TmsLink("TECH-117589")
    public void testProductInfoIfAirtellMSISDNAndVendorIdIsProvided() {
        Map <String, String> map = new Hashtable<>();
        map.put("clientId",ProductLookupClient.TestClient250);
        map.put("vendorId",ProductLookupClient.Vendor100);
        map.put("targetIdentifier",ProductLookupClient.Identifier_14);

        getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .body("[0].publicProduct.id", Matchers.is(Integer.parseInt(ReserveAndTransactClient.ProductAirtel_100)))
                .body("[0].publicProduct.id", Matchers.not(ProductLookupClient.Product_854))
                .body("[0].publicProduct.id", Matchers.not(ProductLookupClient.Product_130));
    }

    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: if mtn_ng MSISDN and mtn_ng vendorId is provided then all mtn_ng products should be returned in the response")
    @TmsLink("TECH-117590")
    public void testProductInfoIfmtn_ngMSISDNAndVendorIdIsProvided() {
        Map <String, String> map = new Hashtable<>();
        map.put("clientId",ProductLookupClient.TestClient250);
        map.put("vendorId",ProductLookupClient.Vendor100);
        map.put("targetIdentifier",ProductLookupClient.Identifier_15);

        getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .body("[0].publicProduct.id", Matchers.is(Integer.parseInt(ReserveAndTransactClient.ProductAirtel_100)))
                .body("[0].publicProduct.id", Matchers.not(ProductLookupClient.Product_854))
                .body("[0].publicProduct.id", Matchers.not(ProductLookupClient.Product_130));
    }

    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: if mtn_ng MSISDN is provided and vendor_id isn't provided then all mtn_ng products should be returned in the response")
    @TmsLink("TECH-117594")
    public void testProductInfoIfmtn_ngMSISDNisProvidedAndVendorIdIsNotProvided() {
        Map <String, String> map = new Hashtable<>();
        map.put("clientId",ProductLookupClient.TestClient250);
        map.put("vendorId",ProductLookupClient.Vendor100);
        map.put("targetIdentifier",ProductLookupClient.Identifier_15);

        getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .body("[0].publicProduct.id", Matchers.is(Integer.parseInt(ReserveAndTransactClient.ProductAirtel_100)))
                .body("[0].publicProduct.id", Matchers.not(ProductLookupClient.Product_854))
                .body("[0].publicProduct.id", Matchers.not(ProductLookupClient.Product_130));
    }

    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: if airtel MSISDN and airtel vendor id are provided then all products should be returned in the response")
    @TmsLink("TECH-117595")
    public void testProductInfoIfMSISDNandAirTelVendorisProvided() {
        Map <String, String> map = new Hashtable<>();
        map.put("clientId",ProductLookupClient.TestClient250);
        map.put("vendorId",ProductLookupClient.Vendor103);
        map.put("targetIdentifier",ProductLookupClient.Identifier_14);

        getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .body("[0].publicProduct.id", Matchers.is(Integer.parseInt(ProductLookupClient.Product_130)))
                .body("[1].publicProduct.id", Matchers.is(Integer.parseInt(ProductLookupClient.Product_854)))
                .body("[0].publicProduct.id",Matchers.not(ReserveAndTransactClient.ProductAirtel_100));
    }

    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: if airtel MSISDN is provided and vendor_id isn't provided then all airtel products should be returned in the response")
    @TmsLink("TECH-117596")
    public void testProductInfoIfAierwllMSISDNIsProvidedandVendorIDisNotProvided() {
        Map <String, String> map = new Hashtable<>();
        map.put("clientId",ProductLookupClient.TestClient250);
        map.put("targetIdentifier",ProductLookupClient.Identifier_14);

        getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .body("[0].publicProduct.id", Matchers.is(Integer.parseInt(ProductLookupClient.Product_130)))
                .body("[1].publicProduct.id", Matchers.is(Integer.parseInt(ProductLookupClient.Product_854)))
                .body("[0].publicProduct.id",Matchers.not(ReserveAndTransactClient.ProductAirtel_100));
    }


}
