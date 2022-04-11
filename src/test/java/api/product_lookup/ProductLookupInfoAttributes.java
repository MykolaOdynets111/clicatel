package api.product_lookup;

import api.clients.ProductLookupClient;
import api.clients.ReserveAndTransactClient;
import api.clients.UsersClient;
import api.enums.Port;
import api.enums.Version;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.*;

import static api.clients.ProductLookupClient.*;
import static api.clients.ProductLookupClient.Product_1600;
import static api.clients.ProductLookupInfoAttributesClient.GetAttributesByAttributeId;
import static api.clients.ProductLookupInfoV4Client.putProductInfo;
import static api.clients.ReserveAndTransactClient.Airtel_delay_3000;
import static api.clients.ReserveAndTransactClient.ResponseCode_206;
import static api.clients.TokenLookupClient.Limit_1;
import static api.clients.VendorManagementClient.ProductTypeData_5;
import static api.clients.VendorManagementClient.Vendor21;
import static api.clients.VendorRoutingServiceClient.*;
import static api.domains.product_lookup.repo.ProductLookupInfoAttributesRepo.GetAttributes99And100;
import static api.domains.product_lookup.repo.ProductLookupInfoV4Repo.GetAttributes;
import static api.domains.product_lookup.repo.ProductLookupRequestRepo.*;
import static db.clients.HibernateBaseClient.executeCustomQuery;
import static db.custom_queries.ProductLookupQueries.DELETE_CLIENT_PRODUCT_BY_PRODUCT_ID;
import static db.custom_queries.ProductLookupQueries.DELETE_VENDOR_PRODUCT_BY_PRODUCT_ID;
import static db.custom_queries.VendorRoutingServiceQueries.*;
import static db.custom_queries.VendorRoutingServiceQueries.INSERT_PRODUCT;
import static db.enums.Sessions.POSTGRES_SQL;
import static java.lang.String.format;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
import static org.testng.AssertJUnit.fail;

public class ProductLookupInfoAttributes {
    @Test
    @Description("32000-payd-product-lookup :: GET \u200B/attributes\u200B/distinctAttributeValues\u200B/{attributeId} :: if product_attribute_value is assigned it should be returned in the response")
    @TmsLink("TECH-141142")
    public void testAttributeValueAssignedAndReturned() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String, String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes99And100("MB 1600", null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                "Q33", Product_1600, map_Pricing, Product_1601, NewClientName, false, true,
                timeStamp, Vendor103, "1", "1", "1", Vendor103);
        System.out.println(body);
        PutUpdateProduct(body)
                .then().assertThat().statusCode(SC_OK);

        GetAttributesByAttributeId("99")
                .then().assertThat().statusCode(SC_OK)
                .body(Matchers.containsString("MB 1600"));
    }
    @Test
    @Description("32000-payd-product-lookup :: GET \u200B/attributes\u200B/distinctAttributeValues\u200B/{attributeId} :: if the same product_attribute_value is assigned to the two products it should be returned in the response only once")
    @TmsLink("TECH-141157")
    public void testSameAttributeValueAssignedToTwoProducts() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String, String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes99And100("MB", null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                "Q33", Product_1600, map_Pricing, Product_1601, NewClientName, false, true,
                timeStamp, Vendor103, "1", "1", "1", Vendor103);
        System.out.println(body);
        PutUpdateProduct(body)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1601 = new Hashtable<>();
        map_1601.put("productId", ProductLookupClient.Product_1601);

        String timeStamp_1601 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1601)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1601);

        List<Map<String,String>> jsonArrayPayload_1601 = new ArrayList<>();
        jsonArrayPayload_1601 = GetAttributes99And100("MB",null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, Product_1601, NewClientName, false, true,
                timeStamp_1601, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        GetAttributesByAttributeId("99")
                .then().assertThat().statusCode(SC_OK)
                .body(Matchers.containsString("MB"));
    }
    @Test
    @Description("32000-payd-product-lookup :: GET \u200B/attributes\u200B/distinctAttributeValues\u200B/{attributeId} :: if the two different product_attribute_value are assigned to the two products both should be returned in the response")
    @TmsLink("TECH-141158")
    public void testDifferentAttributeValueAssignedToTwoProducts() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String, String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes99And100("MB 1600", null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                "Q33", Product_1600, map_Pricing, Product_1601, NewClientName, false, true,
                timeStamp, Vendor103, "1", "1", "1", Vendor103);
        System.out.println(body);
        PutUpdateProduct(body)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1601 = new Hashtable<>();
        map_1601.put("productId", ProductLookupClient.Product_1601);

        String timeStamp_1601 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1601)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1601);

        List<Map<String,String>> jsonArrayPayload_1601 = new ArrayList<>();
        jsonArrayPayload_1601 = GetAttributes99And100("MB 1601",null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, Product_1601, NewClientName, false, true,
                timeStamp_1601, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        GetAttributesByAttributeId("99")
                .then().assertThat().statusCode(SC_OK)
                .body(Matchers.containsString("MB 1600"))
                .body(Matchers.containsString("MB 1601"));

}
    @Test
    @Description("32000-payd-product-lookup :: GET \u200B/attributes\u200B/distinctAttributeValues\u200B/{attributeId} :: if client id is specified, then only product_attribute_value which are assigned to client the should be returned in the response")
    @TmsLink("TECH-141168")
    public void testDifferentAttributeValueAssignedToDifferentClients() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String, String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes99And100("MB 1600", null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                "Q33", Product_1600, map_Pricing, Product_1601, NewClientName, false, true,
                timeStamp, Vendor103, "1", "1", "1", Vendor103);
        System.out.println(body);
        PutUpdateProduct(body)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1601 = new Hashtable<>();
        map_1601.put("productId", ProductLookupClient.Product_1601);

        String timeStamp_1601 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1601)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1601);

        List<Map<String,String>> jsonArrayPayload_1601 = new ArrayList<>();
        jsonArrayPayload_1601 = GetAttributes99And100("MB 1601",null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, Product_1601, NewClientName, false, true,
                timeStamp_1601, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        DeleteProductAssociationWithClient(Product_1600,Product_1601)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> PathParameters = new Hashtable<>();
        PathParameters.put("clientid", ProductLookupClient.Product_1600);

        GetAttributesByAttributeId("99",PathParameters)
                .then().assertThat().statusCode(SC_OK)
                .body(Matchers.containsString("MB 1600"))
                .body(Matchers.containsString("MB 1601"));

        //Post Conditions

        Map<Object, Object> body_ProdAssociation = new Hashtable<>();
        body_ProdAssociation.put("channelIds", Arrays.asList("2","3"));
        body_ProdAssociation.put("productId", ProductLookupClient.Product_1601);

        PostClientProductAssociation(body_ProdAssociation,ProductLookupClient.Product_1600)
                .then().assertThat().statusCode(SC_OK);

    }
    @Test
    @Description("32000-payd-product-lookup :: GET \u200B/attributes\u200B/distinctAttributeValues\u200B/{attributeId} :: if the \"vendor id\" is specified, then only the \"product_attribute_value\" which are assigned to the \"vendor\" should be returned in the response")
    @TmsLink("TECH-141169")
    public void testDifferentAttributeValueAssignedToDifferentVendors() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String, String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes99And100("MB 1600", null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                "Q33", Product_1600, map_Pricing, Product_1601, NewClientName, false, true,
                timeStamp, Product_1600, "1", "1", "1", Product_1600);
        System.out.println(body);
        PutUpdateProduct(body)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1601 = new Hashtable<>();
        map_1601.put("productId", ProductLookupClient.Product_1601);

        String timeStamp_1601 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1601)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1601);

        List<Map<String,String>> jsonArrayPayload_1601 = new ArrayList<>();
        jsonArrayPayload_1601 = GetAttributes99And100("MB 1601",null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, Product_1601, NewClientName, false, true,
                timeStamp_1601, Vendor21,"1","1","1",Vendor21);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> PathParameters = new Hashtable<>();
        PathParameters.put("vendorId", ProductLookupClient.Product_1600);

        val response = GetAttributesByAttributeId("99",PathParameters)
                .then().assertThat().statusCode(SC_OK)
                .body("$", everyItem(is("MB 1600")));
        System.out.println(response);

    }
    @Test
    @Description("32000-payd-product-lookup :: GET \u200B/attributes\u200B/distinctAttributeValues\u200B/{attributeId} :: if the \"active true\" is specified, then only the \"product_attribute_value\" which are assigned to the active products should be returned in the response")
    @TmsLink("TECH-141170")
    public void testDifferentAttributeValueAssignedToOneActiveOneInactive() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String, String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes99And100("MB 1600", null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                "Q33", Product_1600, map_Pricing, Product_1601, NewClientName, false, true,
                timeStamp, Product_1600, "1", "1", "1", Product_1600);
        System.out.println(body);
        PutUpdateProduct(body)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1601 = new Hashtable<>();
        map_1601.put("productId", ProductLookupClient.Product_1601);

        String timeStamp_1601 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1601)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1601);

        List<Map<String,String>> jsonArrayPayload_1601 = new ArrayList<>();
        jsonArrayPayload_1601 = GetAttributes99And100("MB 1601",null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(false, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, Product_1601, NewClientName, false, true,
                timeStamp_1601, Vendor21,"1","1","1",Vendor21);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map<String, Boolean> PathParameters = new Hashtable<>();
        PathParameters.put("productIsActive", true);

        val response = GetAttributesByAttributeId("99",PathParameters)
                .then().assertThat().statusCode(SC_OK)
                .body("$", everyItem(is("MB 1600")));
        System.out.println(response);

    }
    @Test
    @Description("32000-payd-product-lookup :: GET \u200B/attributes\u200B/distinctAttributeValues\u200B/{attributeId} :: if the \"active false\" is specified, then only the \"product_attribute_value\" which are assigned to the inactive products should be returned in the response")
    @TmsLink("TECH-141171")
    public void testDifferentAttributeValueAssignedToOneInactiveOneActive() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String, String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes99And100("MB 1600", null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                "Q33", Product_1600, map_Pricing, Product_1601, NewClientName, false, true,
                timeStamp, Product_1600, "1", "1", "1", Product_1600);
        System.out.println(body);
        PutUpdateProduct(body)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1601 = new Hashtable<>();
        map_1601.put("productId", ProductLookupClient.Product_1601);

        String timeStamp_1601 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1601)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1601);

        List<Map<String,String>> jsonArrayPayload_1601 = new ArrayList<>();
        jsonArrayPayload_1601 = GetAttributes99And100("MB 1601",null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(false, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, Product_1601, NewClientName, false, true,
                timeStamp_1601, Vendor21,"1","1","1",Vendor21);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map<String, Boolean> PathParameters = new Hashtable<>();
        PathParameters.put("productIsActive", false);

        val response = GetAttributesByAttributeId("99",PathParameters)
                .then().assertThat().statusCode(SC_OK)
                .body("$", everyItem(is("MB 1601")));
        System.out.println(response);

    }
    @Test
    @Description("32000-payd-product-lookup :: GET \u200B/attributes\u200B/distinctAttributeValues\u200B/{attributeId} :: check the \"client id\" parameter works as and query with other parameters")
    @TmsLink("TECH-141172")
    public void testDifferentAttributeValueAssignedToDifferentClientsClientIDQueryParameter() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String, String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes99And100("MB 1600", null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                "Q33", Product_1600, map_Pricing, Product_1601, NewClientName, false, true,
                timeStamp, Product_1600, "1", "1", "1", Product_1600);
        System.out.println(body);
        PutUpdateProduct(body)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1601 = new Hashtable<>();
        map_1601.put("productId", ProductLookupClient.Product_1601);

        String timeStamp_1601 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1601)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1601);

        List<Map<String,String>> jsonArrayPayload_1601 = new ArrayList<>();
        jsonArrayPayload_1601 = GetAttributes99And100("MB 1601",null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                "Q33",Product_1601, map_Pricing, Product_1601, NewClientName, false, true,
                timeStamp_1601, Product_1600,"1","1","1",Product_1600);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        DeleteProductAssociationWithClient(Product_1600,Product_1601)
                .then().assertThat().statusCode(SC_OK);

        DeleteProductAssociationWithClient(Product_1601,Product_1601)
                .then().assertThat().statusCode(SC_OK);

        Map<Object, Object> body_ProdAssociation = new Hashtable<>();
        body_ProdAssociation.put("channelIds", Arrays.asList("2","3"));
        body_ProdAssociation.put("productId", ProductLookupClient.Product_1601);

        PostClientProductAssociation(body_ProdAssociation,ProductLookupClient.Product_1601 )
                .then().assertThat().statusCode(SC_OK);

        Map<Object, Object> PathParameters = new Hashtable<>();
        PathParameters.put("clientid", ProductLookupClient.Product_1600);
        PathParameters.put("vendorId", ProductLookupClient.Product_1600);
        PathParameters.put("productIsActive", true);

        GetAttributesByAttributeId("99",PathParameters)
                .then().assertThat().statusCode(SC_OK)
                .body("$", everyItem(is("MB 1600")));

        PostClientProductAssociation(body_ProdAssociation,ProductLookupClient.Product_1601 )
                .then().assertThat().statusCode(SC_OK);

        DeleteProductAssociationWithClient(Product_1601,Product_1601)
                .then().assertThat().statusCode(SC_OK);
    }
    @Test
    @Description("32000-payd-product-lookup :: GET \u200B/attributes\u200B/distinctAttributeValues\u200B/{attributeId} :: check the \"vendor id\" parameter works as and query with other parameters")
    @TmsLink("TECH-141174")
    public void testDifferentAttributeValueAssignedToDifferentVendorsQueryVendorParameter() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String, String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes99And100("MB 1600", null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                "Q33", Product_1600, map_Pricing, Product_1601, NewClientName, false, true,
                timeStamp, Product_1600, "1", "1", "1", Product_1600);
        System.out.println(body);
        PutUpdateProduct(body)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1601 = new Hashtable<>();
        map_1601.put("productId", ProductLookupClient.Product_1601);

        String timeStamp_1601 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1601)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1601);

        List<Map<String,String>> jsonArrayPayload_1601 = new ArrayList<>();
        jsonArrayPayload_1601 = GetAttributes99And100("MB 1601",null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(false, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, Product_1601, NewClientName, false, true,
                timeStamp_1601, Product_1600,"1","1","1",Product_1600);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map<String, Object> PathParameters = new Hashtable<>();
        PathParameters.put("clientid", Product_1600);
        PathParameters.put("vendorId", Product_1600);
        PathParameters.put("productIsActive", true);

        val response = GetAttributesByAttributeId("99",PathParameters)
                .then().assertThat().statusCode(SC_OK)
                .body("$", everyItem(is("MB 1600")));
        System.out.println(response);

    }
    @Test
    @Description("32000-payd-product-lookup :: PUT /public/v4/productInfo :: check sorting based on the \"decimal attribute\" value with char value by default sorting")
    @TmsLink("TECH-139031")
    public void testCheckSortingBasedOnDecimalAttribute() {

        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_CLIENTID, Product_1600));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1600,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1602,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_ATTRIBUTE_VALUE_BY_PRODUCTIDS, Product_1600,Product_1601,Product_1602));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT_ATTRIBUTES_VALUES,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2, Product_1600,"100.5"));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT_ATTRIBUTES_VALUES,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2, Product_1601,"Uncapped"));

        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId", Product_1600);
        queryParams.put("sortBy", "quantity");

        putProductInfo(Port.PRODUCT_LOOKUP, Version.V4, queryParams)
                .then().assertThat().statusCode(SC_OK)
                .body("[0].publicProduct.id", Matchers.is(Integer.parseInt(Product_1600)))
                .body("[1].publicProduct.id", Matchers.is(Integer.parseInt(Product_1602)))
                .body("[2].publicProduct.id", Matchers.is(Integer.parseInt(Product_1601)));

        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_CLIENTID, Product_1600));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1600,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ProductTypeAirtime_3,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1602,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1603,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1604,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
    }

    @Test
    @Description("32000-payd-product-lookup :: PUT /public/v4/productInfo :: if \"sortBy\" parameter isn't specified then products should be sorted based on the productId asc")
    @TmsLink("TECH-139017")
    public void testCheckSortingBasedOnProductIdAsc() {

        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_CLIENTID, Product_1600));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1600,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1602,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));

        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId", Product_1600);

        putProductInfo(Port.PRODUCT_LOOKUP, Version.V4, queryParams)
                .then().assertThat().statusCode(SC_OK)
                .body("[0].publicProduct.id", Matchers.is(Integer.parseInt(Product_1600)))
                .body("[1].publicProduct.id", Matchers.is(Integer.parseInt(Product_1601)))
                .body("[2].publicProduct.id", Matchers.is(Integer.parseInt(Product_1602)));

        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_CLIENTID, Product_1600));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1600,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ProductTypeAirtime_3,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1602,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1603,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1604,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
    }

    @Test
    @Description("32000-payd-product-lookup :: PUT /public/v4/productInfo :: check sorting based on the \"productTypeId\" value by default sroting")
    @TmsLink("TECH-139020")
    public void testCheckSortingBasedOnProductTypeIdDefault() {

        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_CLIENTID, Product_1600));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1600,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1602,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));

        executeCustomQuery(POSTGRES_SQL, format(UPDATE_PRODUCT_TYPE,ProductTypeData_5 , Product_1600));
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_PRODUCT_TYPE,ProductTypeAirtime_3 , Product_1601));
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_PRODUCT_TYPE,Product_1600 , Product_1602));


        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId", Product_1600);
        queryParams.put("sortBy", "productTypeId");

        putProductInfo(Port.PRODUCT_LOOKUP, Version.V4, queryParams)
                .then().assertThat().statusCode(SC_OK)
                .body("[0].publicProduct.id", Matchers.is(Integer.parseInt(Product_1601)))
                .body("[1].publicProduct.id", Matchers.is(Integer.parseInt(Product_1600)))
                .body("[2].publicProduct.id", Matchers.is(Integer.parseInt(Product_1602)));

        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_CLIENTID, Product_1600));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1600,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ProductTypeAirtime_3,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1602,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1603,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1604,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
    }

    @Test
    @Description("32000-payd-product-lookup :: PUT /public/v4/productInfo :: check sorting based on the \"productTypeId\" value by desc sorting")
    @TmsLink("TECH-150582")
    public void testCheckSortingBasedOnProductTypeIdByDesc() {

        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_CLIENTID, Product_1600));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1600,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1602,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));

        executeCustomQuery(POSTGRES_SQL, format(UPDATE_PRODUCT_TYPE,ProductTypeData_5 , Product_1600));
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_PRODUCT_TYPE,ProductTypeAirtime_3 , Product_1601));
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_PRODUCT_TYPE,Product_1600 , Product_1602));


        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId", Product_1600);
        queryParams.put("sortBy", "productTypeId");
        queryParams.put("sortOrder", "desc");

        putProductInfo(Port.PRODUCT_LOOKUP, Version.V4, queryParams)
                .then().assertThat().statusCode(SC_OK)
                .body("[0].publicProduct.id", Matchers.is(Integer.parseInt(Product_1602)))
                .body("[1].publicProduct.id", Matchers.is(Integer.parseInt(Product_1600)))
                .body("[2].publicProduct.id", Matchers.is(Integer.parseInt(Product_1601)));

        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_CLIENTID, Product_1600));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1600,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ProductTypeAirtime_3,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1602,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1603,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1604,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
    }

    @Test
    @Description("32000-payd-product-lookup :: PUT /public/v4/productInfo :: check sorting based on the \"productTypeId\" value by asc sorting")
    @TmsLink("TECH-150581")
    public void testCheckSortingBasedOnProductTypeIdByAsc() {

        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_CLIENTID, Product_1600));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1600,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1602,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));

        executeCustomQuery(POSTGRES_SQL, format(UPDATE_PRODUCT_TYPE,ProductTypeData_5 , Product_1600));
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_PRODUCT_TYPE,ProductTypeAirtime_3 , Product_1601));
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_PRODUCT_TYPE,Product_1600 , Product_1602));


        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId", Product_1600);
        queryParams.put("sortBy", "productTypeId");
        queryParams.put("sortOrder", "asc");

        putProductInfo(Port.PRODUCT_LOOKUP, Version.V4, queryParams)
                .then().assertThat().statusCode(SC_OK)
                .body("[0].publicProduct.id", Matchers.is(Integer.parseInt(Product_1601)))
                .body("[1].publicProduct.id", Matchers.is(Integer.parseInt(Product_1600)))
                .body("[2].publicProduct.id", Matchers.is(Integer.parseInt(Product_1602)));

        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_CLIENTID, Product_1600));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1600,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ProductTypeAirtime_3,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1602,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1603,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1604,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
    }

    @Test
    @Description("32000-payd-product-lookup :: PUT /public/v4/productInfo :: check sorting based on the \"singlePrice\" value by desc sorting")
    @TmsLink("TECH-150603")
    public void testCheckSortingBasedOnSinglePriceByDesc() {

        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_CLIENTID, Product_1600));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1600,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1602,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));

        executeCustomQuery(POSTGRES_SQL, format(UPDATE_PRODUCT_PRICING, ProductType_Single, Airtel_delay_3000, Product_1600));
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_PRODUCT_PRICING, ProductType_Single, Vendor100, Product_1601));
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_PRODUCT_PRICING, ProductType_Single, "99900", Product_1602));


        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId", Product_1600);
        queryParams.put("sortBy", "singlePrice");
        queryParams.put("sortOrder", "desc");

        putProductInfo(Port.PRODUCT_LOOKUP, Version.V4, queryParams)
                .then().assertThat().statusCode(SC_OK)
                .body("[0].publicProduct.id", Matchers.is(Integer.parseInt(Product_1602)))
                .body("[1].publicProduct.id", Matchers.is(Integer.parseInt(Product_1600)))
                .body("[2].publicProduct.id", Matchers.is(Integer.parseInt(Product_1601)));

        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_CLIENTID, Product_1600));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1600,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ProductTypeAirtime_3,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1602,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1603,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1604,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
    }
    @Test
    @Description("32000-payd-product-lookup :: PUT /public/v4/productInfo :: check sorting based on the \"string attribute\" value by desc sorting")
    @TmsLink("TECH-150587")
    public void testCheckSortingBasedOnSingleAttributeDesc() {

        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_CLIENTID, Product_1600));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1600,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1602,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));

        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_ATTRIBUTE_VALUE_BY_PRODUCTIDS, Product_1600,Product_1601,Product_1602));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT_ATTRIBUTES_VALUES,Limit_1, Product_1600,"a1 + b"));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT_ATTRIBUTES_VALUES,Limit_1, Product_1601,"a1 + 1"));


        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId", Product_1600);
        queryParams.put("sortBy", "unit");
        queryParams.put("sortOrder", "desc");

        putProductInfo(Port.PRODUCT_LOOKUP, Version.V4, queryParams)
                .then().assertThat().statusCode(SC_OK)
                .body("[0].publicProduct.id", Matchers.is(Integer.parseInt(Product_1600)))
                .body("[1].publicProduct.id", Matchers.is(Integer.parseInt(Product_1601)))
                .body("[2].publicProduct.id", Matchers.is(Integer.parseInt(Product_1602)));

        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_CLIENTID, Product_1600));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1600,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ProductTypeAirtime_3,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1602,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1603,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1604,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
    }

    @Test
    @Description("32000-payd-product-lookup :: PUT /public/v4/productInfo :: check sorting based on the \"string attribute\" value by default sorting")
    @TmsLink("TECH-139028")
    public void testCheckSortingBasedOnSingleAttributeDefaultSorting() {

        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_CLIENTID, Product_1600));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1600,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1602,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));

        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_ATTRIBUTE_VALUE_BY_PRODUCTIDS, Product_1600,Product_1601,Product_1602));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT_ATTRIBUTES_VALUES,Limit_1, Product_1600,"a1 + b"));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT_ATTRIBUTES_VALUES,Limit_1, Product_1601,"a1 + 1"));


        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId", Product_1600);
        queryParams.put("sortBy", "unit");

        putProductInfo(Port.PRODUCT_LOOKUP, Version.V4, queryParams)
                .then().assertThat().statusCode(SC_OK)
                .body("[0].publicProduct.id", Matchers.is(Integer.parseInt(Product_1601)))
                .body("[1].publicProduct.id", Matchers.is(Integer.parseInt(Product_1600)))
                .body("[2].publicProduct.id", Matchers.is(Integer.parseInt(Product_1602)));

        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_CLIENTID, Product_1600));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1600,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ProductTypeAirtime_3,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1602,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1603,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1604,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
    }

    @Test
    @Description("32000-payd-product-lookup :: PUT /public/v4/productInfo :: check sorting based on the \"string attribute\" value by asc sorting")
    @TmsLink("TECH-150585")
    public void testCheckSortingBasedOnSingleAttributeASC() {

        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_CLIENTID, Product_1600));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1600,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1602,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));

        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_ATTRIBUTE_VALUE_BY_PRODUCTIDS, Product_1600,Product_1601,Product_1602));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT_ATTRIBUTES_VALUES,Limit_1, Product_1600,"a1 + b"));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT_ATTRIBUTES_VALUES,Limit_1, Product_1601,"a1 + 1"));


        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId", Product_1600);
        queryParams.put("sortBy", "unit");
        queryParams.put("sortOrder", "asc");

        putProductInfo(Port.PRODUCT_LOOKUP, Version.V4, queryParams)
                .then().assertThat().statusCode(SC_OK)
                .body("[0].publicProduct.id", Matchers.is(Integer.parseInt(Product_1601)))
                .body("[1].publicProduct.id", Matchers.is(Integer.parseInt(Product_1600)))
                .body("[2].publicProduct.id", Matchers.is(Integer.parseInt(Product_1602)));

        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_CLIENTID, Product_1600));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1600,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ProductTypeAirtime_3,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1602,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1603,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1604,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
    }

    @Test
    @Description("32000-payd-product-lookup :: PUT /public/v4/productInfo :: check sorting based on the \"singlePrice\" value by asc sorting")
    @TmsLink("TECH-150602")
    public void testCheckSortingBasedOnSinglePriceByASC() {

        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_CLIENTID, Product_1600));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1600,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1602,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));

        executeCustomQuery(POSTGRES_SQL, format(UPDATE_PRODUCT_PRICING, ProductType_Single, Airtel_delay_3000, Product_1600));
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_PRODUCT_PRICING, ProductType_Single, Vendor100, Product_1601));
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_PRODUCT_PRICING, ProductType_Single, "99900", Product_1602));


        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId", Product_1600);
        queryParams.put("sortBy", "singlePrice");
        queryParams.put("sortOrder", "asc");

        putProductInfo(Port.PRODUCT_LOOKUP, Version.V4, queryParams)
                .then().assertThat().statusCode(SC_OK)
                .body("[0].publicProduct.id", Matchers.is(Integer.parseInt(Product_1601)))
                .body("[1].publicProduct.id", Matchers.is(Integer.parseInt(Product_1600)))
                .body("[2].publicProduct.id", Matchers.is(Integer.parseInt(Product_1602)));

        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_CLIENTID, Product_1600));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1600,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ProductTypeAirtime_3,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1602,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1603,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1604,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
    }
    @Test
    @Description("32000-payd-product-lookup :: PUT /public/v4/productInfo :: check sorting based on the \"singlePrice\" value by default sorting")
    @TmsLink("TECH-139026")
    public void testCheckSortingBasedOnSinglePriceByDefault() {

        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_CLIENTID, Product_1600));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1600,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1602,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));

        executeCustomQuery(POSTGRES_SQL, format(UPDATE_PRODUCT_PRICING, ProductType_Single, Airtel_delay_3000, Product_1600));
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_PRODUCT_PRICING, ProductType_Single, Vendor100, Product_1601));
        executeCustomQuery(POSTGRES_SQL, format(UPDATE_PRODUCT_PRICING, ProductType_Single, "99900", Product_1602));


        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId", Product_1600);
        queryParams.put("sortBy", "singlePrice");

        putProductInfo(Port.PRODUCT_LOOKUP, Version.V4, queryParams)
                .then().assertThat().statusCode(SC_OK)
                .body("[0].publicProduct.id", Matchers.is(Integer.parseInt(Product_1601)))
                .body("[1].publicProduct.id", Matchers.is(Integer.parseInt(Product_1600)))
                .body("[2].publicProduct.id", Matchers.is(Integer.parseInt(Product_1602)));

        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_CLIENTID, Product_1600));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1600,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ProductTypeAirtime_3,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1602,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1603,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1604,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
    }

    @Test
    @Description("32000-payd-product-lookup :: PUT /public/v4/productInfo :: check sorting based on the \"decimal attribute\" value with char value by asc sorting")
    @TmsLink("TECH-150596")
    public void testCheckSortingBasedOnDecimalAttributeByAscCharVal() {
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_CLIENTID, Product_1600));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1600,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1602,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_ATTRIBUTE_VALUE_BY_PRODUCTIDS, Product_1600,Product_1601,Product_1602));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT_ATTRIBUTES_VALUES,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2, Product_1600,"100.5"));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT_ATTRIBUTES_VALUES,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2, Product_1601,"Uncapped"));

        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId", Product_1600);
        queryParams.put("sortBy", "quantity");
        queryParams.put("sortOrder", "asc");

        putProductInfo(Port.PRODUCT_LOOKUP, Version.V4, queryParams)
                .then().assertThat().statusCode(SC_OK)
                .body("[0].publicProduct.id", Matchers.is(Integer.parseInt(Product_1600)))
                .body("[1].publicProduct.id", Matchers.is(Integer.parseInt(Product_1602)))
                .body("[2].publicProduct.id", Matchers.is(Integer.parseInt(Product_1601)));

        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_CLIENTID, Product_1600));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1600,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ProductTypeAirtime_3,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1602,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1603,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1604,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
    }

    @Test
    @Description("32000-payd-product-lookup :: PUT /public/v4/productInfo :: check sorting based on the \"decimal attribute\" value with char value by desc sorting")
    @TmsLink("TECH-150597")
    public void testCheckSortingBasedOnDecimalAttributeByDescCharVal() {
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_CLIENTID, Product_1600));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1600,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1602,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_ATTRIBUTE_VALUE_BY_PRODUCTIDS, Product_1600,Product_1601,Product_1602));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT_ATTRIBUTES_VALUES,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2, Product_1600,"100.5"));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT_ATTRIBUTES_VALUES,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2, Product_1601,"Uncapped"));

        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId", Product_1600);
        queryParams.put("sortBy", "quantity");
        queryParams.put("sortOrder", "desc");

        putProductInfo(Port.PRODUCT_LOOKUP, Version.V4, queryParams)
                .then().assertThat().statusCode(SC_OK)
                .body("[0].publicProduct.id", Matchers.is(Integer.parseInt(Product_1600)))
                .body("[1].publicProduct.id", Matchers.is(Integer.parseInt(Product_1602)))
                .body("[2].publicProduct.id", Matchers.is(Integer.parseInt(Product_1601)));

        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_CLIENTID, Product_1600));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1600,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ProductTypeAirtime_3,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1602,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1603,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1604,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
    }

    @Test
    @Description("32000-payd-product-lookup :: PUT /public/v4/productInfo :: check sorting based on the \"decimal attribute\" value by asc sorting")
    @TmsLink("TECH-150592")
    public void testCheckSortingBasedOnDecimalAttributeByAsc() {
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_CLIENTID, Product_1600));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1600,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1602,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_ATTRIBUTE_VALUE_BY_PRODUCTIDS, Product_1600,Product_1601,Product_1602));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT_ATTRIBUTES_VALUES,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2, Product_1600,"100.5"));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT_ATTRIBUTES_VALUES,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2, Product_1601,"100"));

        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId", Product_1600);
        queryParams.put("sortBy", "quantity");
        queryParams.put("sortOrder", "asc");

        putProductInfo(Port.PRODUCT_LOOKUP, Version.V4, queryParams)
                .then().assertThat().statusCode(SC_OK)
                .body("[0].publicProduct.id", Matchers.is(Integer.parseInt(Product_1601)))
                .body("[1].publicProduct.id", Matchers.is(Integer.parseInt(Product_1600)))
                .body("[2].publicProduct.id", Matchers.is(Integer.parseInt(Product_1602)));

        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_CLIENTID, Product_1600));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1600,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ProductTypeAirtime_3,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1602,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1603,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1604,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
    }
    @Test
    @Description("32000-payd-product-lookup :: PUT /public/v4/productInfo :: check sorting based on the \"decimal attribute\" value by desc sorting")
    @TmsLink("TECH-150593")
    public void testCheckSortingBasedOnDecimalAttributeByDesc() {
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_CLIENTID, Product_1600));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1600,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1602,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_ATTRIBUTE_VALUE_BY_PRODUCTIDS, Product_1600,Product_1601,Product_1602));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT_ATTRIBUTES_VALUES,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2, Product_1600,"100.5"));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT_ATTRIBUTES_VALUES,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2, Product_1601,"100"));

        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId", Product_1600);
        queryParams.put("sortBy", "quantity");
        queryParams.put("sortOrder", "desc");

        putProductInfo(Port.PRODUCT_LOOKUP, Version.V4, queryParams)
                .then().assertThat().statusCode(SC_OK)
                .body("[0].publicProduct.id", Matchers.is(Integer.parseInt(Product_1600)))
                .body("[1].publicProduct.id", Matchers.is(Integer.parseInt(Product_1601)))
                .body("[2].publicProduct.id", Matchers.is(Integer.parseInt(Product_1602)));

        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_CLIENTID, Product_1600));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1600,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ProductTypeAirtime_3,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1602,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1603,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1604,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
    }

    @Test
    @Description("32000-payd-product-lookup :: PUT /public/v4/productInfo :: check sorting based on the \"decimal attribute\" value by default sorting")
    @TmsLink("TECH-139029")
    public void testCheckSortingBasedOnDecimalAttributeDefaultSorting() {
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_CLIENTID, Product_1600));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1600,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1602,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_ATTRIBUTE_VALUE_BY_PRODUCTIDS, Product_1600,Product_1601,Product_1602));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT_ATTRIBUTES_VALUES,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2, Product_1600,"100.5"));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT_ATTRIBUTES_VALUES,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2, Product_1601,"100"));

        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId", Product_1600);
        queryParams.put("sortBy", "quantity");

        putProductInfo(Port.PRODUCT_LOOKUP, Version.V4, queryParams)
                .then().assertThat().statusCode(SC_OK)
                .body("[0].publicProduct.id", Matchers.is(Integer.parseInt(Product_1601)))
                .body("[1].publicProduct.id", Matchers.is(Integer.parseInt(Product_1600)))
                .body("[2].publicProduct.id", Matchers.is(Integer.parseInt(Product_1602)));

        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_CLIENTID, Product_1600));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1600,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1601,ProductTypeAirtime_3,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1602,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1603,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,Product_1600,Product_1604,ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,cdc_update_timestamp));
    }

    @Test
    @Description("32000-payd-product-lookup :: POST \u200B/clients\u200B/{clientId}\u200B/products :: happy path")
    @TmsLink("TECH-171726")
    public void testCreateProductWithVendorHappyPath() {

        executeCustomQuery(POSTGRES_SQL, format(DELETE_VENDOR_PRODUCT_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_CLIENT_PRODUCT_BY_PRODUCT_ID,ResponseCode_206));



    }


}
