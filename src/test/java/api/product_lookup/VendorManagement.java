package api.product_lookup;

import api.clients.ProductLookupClient;
import api.clients.ReserveAndTransactClient;
import api.clients.UsersClient;
import api.clients.VendorManagementClient;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import java.util.Hashtable;
import java.util.Map;
import static api.clients.ProductLookupClient.*;
import static api.clients.VendorManagementClient.*;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNPROCESSABLE_ENTITY;

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
                .body("externalProductTypeId", Matchers.hasItem(AirTimeAirtelProductTypeId))
                .body("internalProductTypeId", Matchers.hasItem(Integer.parseInt(ReserveAndTransactClient.fundingSourceId)))
                .body("vendorId", Matchers.hasItem(Integer.parseInt(Vendor103)))
                .body("description", Matchers.hasItem(AirTimeAirtelProductTypeId));
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
    @Test
    @Description("GET /vendorProductType/getByInternalProductTypeId :: if \"Vendor Product Type\" is assigned to the \"Product Type\" then it should be returned in the response")
    @TmsLink("TECH-116097")
    public void testVendorProductTypAssignedToProductType() {
        Map<String, String> map = new Hashtable<>();
        map.put("internalProductTypeId", VendorManagementClient.ProductTypeData_5);
        GetVendorProductTypeGetByInternalTypeId(map)
                .then().assertThat().statusCode(SC_OK)
                .body("externalProductTypeId", Matchers.hasItem(DataAirtelProductTypeId))
                .body("internalProductTypeId[0]", Matchers.is(Integer.parseInt(VendorManagementClient.ProductTypeData_5)))
                .body("vendorId", Matchers.hasItem(Integer.parseInt(Vendor103)))
                .body("description", Matchers.hasItem(DataAirtelProductTypeId));
    }
    @Test
    @Description("POST /vendorProductType/ :: user can't assign more then one \"Vendor Product Type\" to the \"Product Type\" for the same vendor")
    @TmsLink("TECH-116094")
    public void testVendorProductTypCannotBeAssignedToSameVendor() {
        Map<String, String> body = new Hashtable<>();
        body.put("externalProductTypeId", VendorManagementClient.ProductTypeData_5);
        body.put("internalProductTypeId", VendorManagementClient.ProductTypeData_5);
        body.put("vendorId", VendorManagementClient.ProductTypeData_5);
        body.put("description", VendorManagementClient.ProductTypeData_5);
        PostVendorProductType(body)
                .then().assertThat().statusCode(SC_UNPROCESSABLE_ENTITY)
                .body("message", Matchers.is(VendorManagementClient.responseMessageSameVendorProductType));
    }
    @Test
    @Description("GET /vendorProductType/getVendorProductTypeByIds :: happy path")
    @TmsLink("TECH-62344")
    public void testVendorProductTypeHappyPath() {
        Map<String, String> body = new Hashtable<>();
        body.put("externalProductTypeId", VendorManagementClient.ProductTypeData_5);
        body.put("internalProductTypeId", VendorManagementClient.ProductTypeData_5);
        body.put("vendorId", VendorManagementClient.ProductTypeData_5);
        body.put("description", VendorManagementClient.ProductTypeData_5);
        PostVendorProductType(body)
                .then().assertThat().statusCode(SC_UNPROCESSABLE_ENTITY)
                .body("message", Matchers.is(VendorManagementClient.responseMessageSameVendorProductType));
    }
    @Test
    @Description("GET /vendorProductType/getByInternalProductTypeId :: if \"Vendor Product Type\" is assigned to the \"Product Type\" then it should be returned in the response")
    @TmsLink("TECH-116097")
    public void testVendorProductTypeAssignedToProductType() {
        Map<String, String> map = new Hashtable<>();
        map.put("internalProductTypeId", ProductTypeData_5);
        GetVendorProductTypesByInternalProductTypeId(map)
                .then().assertThat().statusCode(SC_OK)
                .body("externalProductTypeId", Matchers.hasItem(DataAirtelProductTypeId))
                .body("internalProductTypeId", Matchers.hasItem(Integer.parseInt(ProductTypeData_5)))
                .body("vendorId", Matchers.hasItem(Integer.parseInt(Vendor103)));
    }
    @Test
    @Description("POST /vendorProductType/ :: happy path")
    @TmsLink("TECH-62333")
    public void testVendorProductTypeCreate() {
        Map<String, String> map = new Hashtable<>();
        map.put("externalProductTypeId", NewClientName);
        map.put("internalProductTypeId", ReserveAndTransactClient.FeeAmount10);
        map.put("vendorId", Vendor21);
        map.put("description", NewClientName);
        PostVendorProductType(map)
                .then().assertThat().statusCode(SC_OK)
                .body("externalProductTypeId", Matchers.is(NewClientName))
                .body("internalProductTypeId", Matchers.is(Integer.parseInt(ReserveAndTransactClient.FeeAmount10)))
                .body("vendorId", Matchers.is(Integer.parseInt(Vendor21)));
    }
    @Test
    @Description("PUT /vendorProductType/ :: happy path")
    @TmsLink("TECH-62348")
    public void testVendorProductTypeUpdate() {
        Map<String, String> map = new Hashtable<>();
        map.put("externalProductTypeId", UpdatedClientName);
        map.put("internalProductTypeId", ReserveAndTransactClient.FeeAmount10);
        map.put("vendorId", Vendor21);
        map.put("description", UpdatedClientName);
        PutVendorProductType(map)
                .then().assertThat().statusCode(SC_OK)
                .body(Matchers.is("true"));
    }
    @Test
    @Description("DELETE /vendorProductType/ :: happy path")
    @TmsLink("TECH-62349")
    public void testVendorProductTypeDelete() {
        Map<String, String> map = new Hashtable<>();
        map.put("internalProductTypeId", ReserveAndTransactClient.FeeAmount10);
        map.put("vendorId", Vendor21);
        DeleteVendorProductType(map)
                .then().assertThat().statusCode(SC_OK)
                .body(Matchers.is("true"));
    }
    @Test
    @Description("GET /vendorProductType/getByInternalProductTypeId :: if \"Vendor Product Type\" is assigned to the \"Product Type\" then it should be returned in the response")
    @TmsLink("TECH-115829")
    public void testVendorProductTypeNotAssignedToProductType() {
        Map<String, String> map = new Hashtable<>();
        map.put("internalProductTypeId", ReserveAndTransactClient.PurchaseAmount20000);
        GetVendorProductTypeGetByInternalTypeId(map)
                .then().assertThat().statusCode(SC_OK);
    }
}
