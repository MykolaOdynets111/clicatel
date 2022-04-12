package api.product_lookup;

import api.clients.BasedAPIClient;
import api.clients.VendorManagementClient;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import java.util.Hashtable;
import java.util.Map;
import static api.clients.ProductLookupClient.*;
import static api.clients.ReserveAndTransactClient.*;
import static api.clients.UsersClient.country;
import static org.apache.http.HttpStatus.SC_OK;

public class ProductLookupController extends BasedAPIClient {

    @Test
    @Description("GET /products/{clientId}/{vendorId} :: happy path")
    @TmsLink("TECH-138810")
    public void testGetProductsClientVendor() {
        Map <String, String > map = new Hashtable<>();
        map.put("clientId", TestClient3);
        map.put("vendorId", VendorManagementClient.Vendor21);

        GetProductsClientVendor(map)
                .then().assertThat().statusCode(SC_OK)
                .body("id", Matchers.hasItem(Integer.parseInt(ProductAirtel_917)))
                .body("id", Matchers.not(Matchers.hasItem(Integer.parseInt(Product_MTN_ZA_Clickatell_30))));
    }

    @Test
    @Description("GET /productsdetails/client/{clientId}/product/{productId} :: happy path")
    @TmsLink("TECH-138811")
    public void testGetProductsdetailsClientIdProductId() {
        Map <String, String > map = new Hashtable<>();
        map.put("clientId", TestClient3);
        map.put("productId", ProductAirtel_917);

        GetProductsdetailsClientIdProductId(map)
                .then().assertThat().statusCode(SC_OK)
                .body("product.id", Matchers.equalTo(Integer.parseInt(ProductAirtel_917)));
    }

    @Test
    @Description("GET /vendor/{clientId} :: happy path")
    @TmsLink("TECH-138812")
    public void testGetVendorClientId() {
        Map <String, String > map = new Hashtable<>();
        map.put("clientId", TestClient3);

        GetVendorClientId(map)
                .then().assertThat().statusCode(SC_OK)
                .body("id", Matchers.hasItem(Integer.parseInt(VendorManagementClient.Vendor21)));
    }

    @Test
    @Description("GET /productType/{productId} :: happy path")
    @TmsLink("TECH-138813")
    public void testGetProductTypeProductId() {
        Map <String, String > map = new Hashtable<>();
        map.put("productId", ProductAirtel_917);

        GetProductTypeProductId(map)
                .then().assertThat().statusCode(SC_OK)
                .body(Matchers.equalTo((ProductTypeAirtime_3)));
    }

    @Test
    @Description("GET /getActiveProductsByCountryCode :: happy path")
    @TmsLink("TECH-138814")
    public void testGetActiveProductsByCountryCode() {
        Map <String, String> map = new Hashtable<>();
        map.put("countryCode", country);

        GetActiveProductsByCountryCode(map)
                .then().assertThat().statusCode(SC_OK)
                .body("id", Matchers.hasItem(Integer.parseInt(ProductAirtel_917)));
    }

    @Test
    @Description("GET /products/ :: happy path")
    @TmsLink("TECH-138815")
    public void testGetProducts() {

        GetProducts()
                .then().assertThat().statusCode(SC_OK)
                .body("productId", Matchers.hasItem(Integer.parseInt(ProductAirtel_917)));
    }

    @Test
    @Description("GET /productbyclientidproductid/{clientId},{productId} :: happy path")
    @TmsLink("TECH-138816")
    public void testGetProductByClientIdProductId() {
        Map <String, String > map = new Hashtable<>();
        map.put("clientId", TestClient3);
        map.put("productId", ProductAirtel_917);

        GetProductByClientIdProductId(map)
                .then().assertThat().statusCode(SC_OK)
                .body("id", Matchers.equalTo(Integer.parseInt(ProductAirtel_917)));
    }

    @Test
    @Description("GET /toppick/{clientId},{vendorId} :: happy path")
    @TmsLink("TECH-138817")
    public void testGetToppickClientIdVendorId() {
        Map <String, String > map = new Hashtable<>();
        map.put("clientId", TestClient3);
        map.put("vendorId", VendorManagementClient.Vendor21);

        GetToppickClientIdVendorId(map)
                .then().assertThat().statusCode(SC_OK)
                .body("", Matchers.hasSize(0));

    }

    @Test
    @Description("GET /GetgetProduct :: happy path")
    @TmsLink("TECH-138818")
    public void testGetgetProduct() {
        Map <String, String> map = new Hashtable<>();
        map.put("id", ProductAirtel_917);

        GetGetProduct(map)
                .then().assertThat().statusCode(SC_OK)
                .body("id", Matchers.equalTo(Integer.parseInt(ProductAirtel_917)));
    }

    @Test
    @Description("GET /productsdetails/client/{clientId}/product/{productId} :: happy path")
    @TmsLink("TECH-138819")
    public void testGetProductsClientIdVendorIdProductId() {
        Map <String, String > map = new Hashtable<>();
        map.put("clientId", TestClient3);
        map.put("vendorId", VendorManagementClient.Vendor21);
        map.put("productId", ProductAirtel_917);

        GetProductsClientIdVendorIdProductId(map)
                .then().assertThat().statusCode(SC_OK)
                .body("id", Matchers.equalTo(Integer.parseInt(ProductAirtel_917)));
    }

    @Test
    @Description("GET /vendorsMap :: happy path")
    @TmsLink("TECH-138820")
    public void testGetVendorsMap() {

        GetVendorsMap()
                .then().assertThat().statusCode(SC_OK)
                .body("21.vendorId", Matchers.equalTo(Integer.parseInt(VendorManagementClient.Vendor21)));
    }

    @Test
    @Description("GET /clientsMap :: happy path")
    @TmsLink("TECH-138821")
    public void testClientsMap() {

        GetClientsMap()
                .then().assertThat().statusCode(SC_OK)
                .body("3.clientId", Matchers.equalTo(Integer.parseInt(TestClient3)));
    }

    @Test
    @Description("GET /products/productType/{productTypeId}/{clientId}/{vendorId} :: happy path")
    @TmsLink("TECH-138822")
    public void testGetProductsProductTypeIDClientIdVendorId() {
        Map <String, String > map = new Hashtable<>();
        map.put("productTypeId", ProductTypeAirtime_3);
        map.put("clientId", TestClient3);
        map.put("vendorId", VendorManagementClient.Vendor21);

        GetProductsProductTypeIDClientIdVendorId(map)
                .then().assertThat().statusCode(SC_OK)
                .body("id", Matchers.hasItem(Integer.parseInt(ProductAirtel_917)));
    }

    @Test
    @Description("GET /products/{productTypeId} :: happy path")
    @TmsLink("TECH-138826")
    public void testGetProductsProductTypeId() {
        Map <String, String > map = new Hashtable<>();
        map.put("productTypeId", ProductTypeAirtime_3);

        GetProductsProductTypeId(map)
                .then().assertThat().statusCode(SC_OK)
                .body("id", Matchers.hasItem(Integer.parseInt(ProductAirtel_917)));
    }

    @Test
    @Description("GET /products/vendorProductIds/{vendorId} :: happy path")
    @TmsLink("TECH-138827")
    public void testGetProductsVendorProductIdsVendorId() {
        Map <String, String > map = new Hashtable<>();
        map.put("vendorId", VendorManagementClient.Vendor21);

        GetProductsVendorProductIdsVendorId(map)
                .then().assertThat().statusCode(SC_OK)
                .body("", Matchers.hasItem(Integer.parseInt(ProductAirtel_917)));
    }

    @Test
    @Description("GET  /products/vendorProductsByProductType/{vendorId}/{productTypeId}/ :: happy path")
    @TmsLink("TECH-138828")
    public void testGetVendorProductsByProductType() {
        Map <String, String > map = new Hashtable<>();
        map.put("vendorId", VendorManagementClient.Vendor21);
        map.put("productTypeId", ProductTypeAirtime_3);

        GetVendorProductsByProductType(map)
                .then().assertThat().statusCode(SC_OK)
                .body("id", Matchers.hasItem(Integer.parseInt(ProductAirtel_917)));
    }

    @Test
    @Description("GET /products/clientProductAssociation/{countryCode}/{clientId} :: happy path")
    @TmsLink("TECH-138829")
    public void testGetClientProductAssociationCountryCode() {
        Map <String, String > map = new Hashtable<>();
        map.put("countryCode", CountryCode_710);
        map.put("clientId", TestClient3);

        GetClientProductAssociationCountryCode(map)
                .then().assertThat().statusCode(SC_OK)
                .body("productId", Matchers.hasItem(Integer.parseInt(ProductMTN_ZA_400)));
    }

    @Test
    @Description("GET /products/clientVendorProductIds/{clientId}/{vendorId} :: happy path")
    @TmsLink("TECH-138830")
    public void testProductsClientVendorProductIds() {
        Map <String, String > map = new Hashtable<>();
        map.put("clientId", TestClient3);
        map.put("vendorId", VendorManagementClient.Vendor21);

        GetProductsClientVendorProductIds(map)
                .then().assertThat().statusCode(SC_OK)
                .body("", Matchers.hasItem(Integer.parseInt(ProductAirtel_917)));
    }

    @Test
    @Description("GET /productsdetails/{clientId},{vendorId} :: happy path")
    @TmsLink("TECH-138831")
    public void testGetProductsdetailsClientIdVendorId() {
        Map <String, String > map = new Hashtable<>();
        map.put("clientId", TestClient3);
        map.put("vendorId", VendorManagementClient.Vendor21);

        GetProductsdetailsClientIdVendorId(map)
                .then().assertThat().statusCode(SC_OK)
                .body("product.id", Matchers.hasItem(Integer.parseInt(ProductAirtel_917)));
    }

    @Test
    @Description("GET /clientProducts/{clientId} :: happy path")
    @TmsLink("TECH-138832")
    public void testGetClientProducts() {
        Map <String, String > map = new Hashtable<>();
        map.put("clientId", TestClient3);

        GetClientProducts(map)
                .then().assertThat().statusCode(SC_OK)
                .body("id", Matchers.hasItem(Integer.parseInt(ProductAirtel_917)));
    }

    @Test
    @Description("GET /productsdetails/client/{clientId}/product/{vendorId}/product/{productId} :: happy path")
    @TmsLink("TECH-138834")
    public void testGetProductsdetailsClientIdCendorIdProductId() {
        Map <String, String > map = new Hashtable<>();
        map.put("clientId", TestClient3);
        map.put("vendorId", VendorManagementClient.Vendor21);
        map.put("productId", ProductAirtel_917);

        GetProductsdetailsClientIdCendorIdProductId(map)
                .then().assertThat().statusCode(SC_OK)
                .body("product.id", Matchers.equalTo(Integer.parseInt(ProductAirtel_917)));
    }


}

