package api.product_lookup;

import api.clients.ProductLookupClient;
import api.clients.ReserveAndTransactClient;
import api.enums.Port;
import api.enums.Version;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import java.util.Hashtable;
import java.util.Map;
import static api.clients.ProductLookupClient.getProductDetails;
import static api.clients.ProductLookupClient.getProductInfo;
import static org.apache.http.HttpStatus.SC_OK;

public class ProductManagement {

    @Test
    @Description("GET /product_management/productDetails :: for the airtime airtel products and externalVendorProductTypeId should be hardcoded")
    @TmsLink("TECH-116734")
    public void testAirtimeAirTelProductsExternalVendorProductTypeId() {
        Map<String, String> map = new Hashtable<>();
        map.put("productId", ProductLookupClient.ProductAirtel_130);

        getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map)
                .then().assertThat().statusCode(SC_OK)
                .body("externalVendorProductTypeId", Matchers.is(ProductLookupClient.AirTimeAirtelProductTypeId));

    }

    @Test
    @Description("GET /product_management/productDetails :: for the data airtel products and externalVendorProductTypeId should be hardcoded")
    @TmsLink("TECH-116736")
    public void testDataAirTelProductsExternalVendorProductTypeId() {
        Map<String, String> map = new Hashtable<>();
        map.put("productId", ReserveAndTransactClient.Product_Airtel_189);

        getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map)
                .then().assertThat().statusCode(SC_OK)
                .body("externalVendorProductTypeId", Matchers.is(ProductLookupClient.DataAirtelProductTypeId));

    }
}
