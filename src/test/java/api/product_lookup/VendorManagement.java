package api.product_lookup;

import api.clients.ProductLookupClient;
import api.clients.ReserveAndTransactClient;
import api.clients.UsersClient;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import java.util.Hashtable;
import java.util.Map;
import static api.clients.ProductLookupClient.*;
import static org.apache.http.HttpStatus.SC_OK;

public class VendorManagement {

    @Test
    @Description("GET /vendors/vendorByIdExpanded :: happy path")
    @TmsLink("TECH-34258")
    public void testVendorIdByExpandedHappyPath() {

        Map<String, String> map = new Hashtable<>();
        map.put("id", ProductLookupClient.Vendor100);
        GetVendorByIdExpanded(map)
                .then().assertThat().statusCode(SC_OK)
                .body("vendor.id", Matchers.is(Integer.parseInt(ProductLookupClient.Vendor100)))
                .body("vendor.name", Matchers.is(ProductLookupClient.Vendor_MTN_NG))
                .body("vendor.active", Matchers.is(true))
                .body("vendor.currencyId", Matchers.is(UsersClient.country));
    }

    @Test
    @Description("GET /vendors/vendorByIdExpanded :: happy path")
    @TmsLink("TECH-62347")
    public void testVendorProductTypeListAllHappyPath() {

        GetVendorProductTypes()
                .then().assertThat().statusCode(SC_OK)
                .body("externalProductTypeId[0]", Matchers.is(AirTimeAirtelProductTypeId))
                .body("internalProductTypeId[0]", Matchers.is(Integer.parseInt(ReserveAndTransactClient.fundingSourceId)))
                .body("vendorId[0]", Matchers.is(Integer.parseInt(Vendor103)))
                .body("description[0]", Matchers.is(AirTimeAirtelProductTypeId));
    }

    @Test
    @Description("GET /ctx/vendorProduct :: check if 'externalProductTypeId' is returned in the response")
    @TmsLink("TECH-118377")
    public void testVendorCtxVendorProduct() {

        Map<String, String> map = new Hashtable<>();
        map.put("id", ProductAirtel_130);
        GetVendorCtxProductById(map)
                .then().assertThat().statusCode(SC_OK)
                .body("productId", Matchers.is(Integer.parseInt(ProductAirtel_130)))
                .body("vendorProductId", Matchers.is(Integer.parseInt(ProductAirtel_130)))
                .body("vendorId", Matchers.is(Integer.parseInt(Vendor103)))
                .body("externalProductTypeId", Matchers.is(AirTimeAirtelProductTypeId));
    }
}
