package api.product_lookup;

import api.clients.*;
import api.enums.Port;
import api.enums.Version;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import util.base_test.BaseApiTest;

import java.util.*;

import static api.clients.ProductLookupClient.*;
import static api.clients.ReserveAndTransactClient.Product_Invalid;
import static api.clients.SimulatorsClient.ValidAirtelMsisdnWithProducts;
import static api.clients.SimulatorsClient.ValidAirtelMsisdnWithoutProducts;
import static api.domains.product_lookup.repo.ProductLookupRequestRepo.*;
import static org.apache.http.HttpStatus.*;
import static org.testng.AssertJUnit.fail;

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
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);
        HashMap<String, String> map_attributes_1 = new HashMap<String, String>();
        map_attributes_1.put("attributeId", "1");
        map_attributes_1.put("value", null);

        HashMap<String, String> map_attributes_2 = new HashMap<String, String>();
        map_attributes_2.put("attributeId", "2");
        map_attributes_2.put("value", null);

        HashMap<String, String> map_attributes_3 = new HashMap<String, String>();
        map_attributes_3.put("attributeId", "3");
        map_attributes_3.put("value", null);

        HashMap<String, String> map_attributes_15 = new HashMap<String, String>();
        map_attributes_15.put("attributeId", "15");
        map_attributes_15.put("value", null);

        HashMap<String, String> map_attributes_30 = new HashMap<String, String>();
        map_attributes_30.put("attributeId", "30");
        map_attributes_30.put("value", null);

        HashMap<String, String> map_attributes_31 = new HashMap<String, String>();
        map_attributes_31.put("attributeId", "31");
        map_attributes_31.put("value", null);

        HashMap<String, String> map_attributes_33 = new HashMap<String, String>();
        map_attributes_33.put("attributeId", "33");
        map_attributes_33.put("value", null);

        HashMap<String, String> map_attributes_34 = new HashMap<String, String>();
        map_attributes_34.put("attributeId", "34");
        map_attributes_34.put("value", null);

        HashMap<String, String> map_attributes_35 = new HashMap<String, String>();
        map_attributes_35.put("attributeId", "35");
        map_attributes_35.put("value", null);

        HashMap<String, String> map_attributes_36 = new HashMap<String, String>();
        map_attributes_36.put("attributeId", "36");
        map_attributes_36.put("value", null);

        HashMap<String, String> map_attributes_4 = new HashMap<String, String>();
        map_attributes_4.put("attributeId", "4");
        map_attributes_4.put("value", null);

        HashMap<String, String> map_attributes_37 = new HashMap<String, String>();
        map_attributes_37.put("attributeId", "37");
        map_attributes_37.put("value", null);


        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();

        jsonArrayPayload_1600.add(map_attributes_1);
        jsonArrayPayload_1600.add(map_attributes_2);
        jsonArrayPayload_1600.add(map_attributes_3);
        jsonArrayPayload_1600.add(map_attributes_15);
        jsonArrayPayload_1600.add(map_attributes_30);
        jsonArrayPayload_1600.add(map_attributes_31);
        jsonArrayPayload_1600.add(map_attributes_33);
        jsonArrayPayload_1600.add(map_attributes_34);
        jsonArrayPayload_1600.add(map_attributes_35);
        jsonArrayPayload_1600.add(map_attributes_36);


        val body = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                null,Product_1600, map_Pricing, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp, Vendor100,Vendor103);
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

        jsonArrayPayload_1601.add(map_attributes_1);
        jsonArrayPayload_1601.add(map_attributes_2);
        jsonArrayPayload_1601.add(map_attributes_3);
        jsonArrayPayload_1601.add(map_attributes_4);
        jsonArrayPayload_1601.add(map_attributes_30);
        jsonArrayPayload_1601.add(map_attributes_31);
        jsonArrayPayload_1601.add(map_attributes_33);
        jsonArrayPayload_1601.add(map_attributes_34);
        jsonArrayPayload_1601.add(map_attributes_35);
        jsonArrayPayload_1601.add(map_attributes_36);
        jsonArrayPayload_1601.add(map_attributes_37);


        val body_1601 = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, VendorManagementClient.ProductTypeData_5, NewClientName, false, false,
                timeStamp_1601, Vendor100,Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1602 = new Hashtable<>();
        map_1602.put("productId", ProductLookupClient.Product_1602);

        String timeStamp_1602 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1602)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1602);


        val body_1602 = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                null,Product_1602, map_Pricing, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp_1602, Vendor103,Vendor100);
        System.out.println(body_1602);
        PutUpdateProduct(body_1602)
                .then().assertThat().statusCode(SC_OK);

        Map <String, String> map = new Hashtable<>();
        map.put("vendorId",Vendor100);
        map.put("targetIdentifier", Identifier_13);
        map.put("clientId",Product_1600);

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(response2.contains(1600)&response2.contains(1601)&!response2.contains(1602))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }


    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: if aritel MSISDN and mtn_ng vendorId are provided then only vendor products should be returned in the response")
    @TmsLink("TECH-117589")
    public void testProductInfoIfAirtellMSISDNAndVendorIdIsProvided() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);
        HashMap<String, String> map_attributes_1 = new HashMap<String, String>();
        map_attributes_1.put("attributeId", "1");
        map_attributes_1.put("value", null);

        HashMap<String, String> map_attributes_2 = new HashMap<String, String>();
        map_attributes_2.put("attributeId", "2");
        map_attributes_2.put("value", null);

        HashMap<String, String> map_attributes_3 = new HashMap<String, String>();
        map_attributes_3.put("attributeId", "3");
        map_attributes_3.put("value", null);

        HashMap<String, String> map_attributes_15 = new HashMap<String, String>();
        map_attributes_15.put("attributeId", "15");
        map_attributes_15.put("value", null);

        HashMap<String, String> map_attributes_30 = new HashMap<String, String>();
        map_attributes_30.put("attributeId", "30");
        map_attributes_30.put("value", null);

        HashMap<String, String> map_attributes_31 = new HashMap<String, String>();
        map_attributes_31.put("attributeId", "31");
        map_attributes_31.put("value", null);

        HashMap<String, String> map_attributes_33 = new HashMap<String, String>();
        map_attributes_33.put("attributeId", "33");
        map_attributes_33.put("value", null);

        HashMap<String, String> map_attributes_34 = new HashMap<String, String>();
        map_attributes_34.put("attributeId", "34");
        map_attributes_34.put("value", null);

        HashMap<String, String> map_attributes_35 = new HashMap<String, String>();
        map_attributes_35.put("attributeId", "35");
        map_attributes_35.put("value", null);

        HashMap<String, String> map_attributes_36 = new HashMap<String, String>();
        map_attributes_36.put("attributeId", "36");
        map_attributes_36.put("value", null);

        HashMap<String, String> map_attributes_4 = new HashMap<String, String>();
        map_attributes_4.put("attributeId", "4");
        map_attributes_4.put("value", null);

        HashMap<String, String> map_attributes_37 = new HashMap<String, String>();
        map_attributes_37.put("attributeId", "37");
        map_attributes_37.put("value", null);


        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();

        jsonArrayPayload_1600.add(map_attributes_1);
        jsonArrayPayload_1600.add(map_attributes_2);
        jsonArrayPayload_1600.add(map_attributes_3);
        jsonArrayPayload_1600.add(map_attributes_15);
        jsonArrayPayload_1600.add(map_attributes_30);
        jsonArrayPayload_1600.add(map_attributes_31);
        jsonArrayPayload_1600.add(map_attributes_33);
        jsonArrayPayload_1600.add(map_attributes_34);
        jsonArrayPayload_1600.add(map_attributes_35);
        jsonArrayPayload_1600.add(map_attributes_36);


        val body = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                "Q33",Product_1600, map_Pricing, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp, Vendor100,Vendor103);
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

        jsonArrayPayload_1601.add(map_attributes_1);
        jsonArrayPayload_1601.add(map_attributes_2);
        jsonArrayPayload_1601.add(map_attributes_3);
        jsonArrayPayload_1601.add(map_attributes_4);
        jsonArrayPayload_1601.add(map_attributes_30);
        jsonArrayPayload_1601.add(map_attributes_31);
        jsonArrayPayload_1601.add(map_attributes_33);
        jsonArrayPayload_1601.add(map_attributes_34);
        jsonArrayPayload_1601.add(map_attributes_35);
        jsonArrayPayload_1601.add(map_attributes_36);
        jsonArrayPayload_1601.add(map_attributes_37);


        val body_1601 = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, VendorManagementClient.ProductTypeData_5, NewClientName, false, false,
                timeStamp_1601, Vendor100,Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1602 = new Hashtable<>();
        map_1602.put("productId", ProductLookupClient.Product_1602);

        String timeStamp_1602 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1602)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1602);


        val body_1602 = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                null,Product_1602, map_Pricing, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp_1602, Vendor103,Vendor100);
        System.out.println(body_1602);
        PutUpdateProduct(body_1602)
                .then().assertThat().statusCode(SC_OK);

        Map <String, String> map = new Hashtable<>();
        map.put("vendorId",Vendor100);
        map.put("targetIdentifier", Identifier_14);
        map.put("clientId",Product_1600);

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(response2.contains(1600)&response2.contains(1601)&!response2.contains(1602))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }

    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: if mtn_ng MSISDN and mtn_ng vendorId is provided then all mtn_ng products should be returned in the response")
    @TmsLink("TECH-117590")
    public void testProductInfoIfmtn_ngMSISDNAndVendorIdIsProvided() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);
        HashMap<String, String> map_attributes_1 = new HashMap<String, String>();
        map_attributes_1.put("attributeId", "1");
        map_attributes_1.put("value", null);

        HashMap<String, String> map_attributes_2 = new HashMap<String, String>();
        map_attributes_2.put("attributeId", "2");
        map_attributes_2.put("value", null);

        HashMap<String, String> map_attributes_3 = new HashMap<String, String>();
        map_attributes_3.put("attributeId", "3");
        map_attributes_3.put("value", null);

        HashMap<String, String> map_attributes_15 = new HashMap<String, String>();
        map_attributes_15.put("attributeId", "15");
        map_attributes_15.put("value", null);

        HashMap<String, String> map_attributes_30 = new HashMap<String, String>();
        map_attributes_30.put("attributeId", "30");
        map_attributes_30.put("value", null);

        HashMap<String, String> map_attributes_31 = new HashMap<String, String>();
        map_attributes_31.put("attributeId", "31");
        map_attributes_31.put("value", null);

        HashMap<String, String> map_attributes_33 = new HashMap<String, String>();
        map_attributes_33.put("attributeId", "33");
        map_attributes_33.put("value", null);

        HashMap<String, String> map_attributes_34 = new HashMap<String, String>();
        map_attributes_34.put("attributeId", "34");
        map_attributes_34.put("value", null);

        HashMap<String, String> map_attributes_35 = new HashMap<String, String>();
        map_attributes_35.put("attributeId", "35");
        map_attributes_35.put("value", null);

        HashMap<String, String> map_attributes_36 = new HashMap<String, String>();
        map_attributes_36.put("attributeId", "36");
        map_attributes_36.put("value", null);

        HashMap<String, String> map_attributes_4 = new HashMap<String, String>();
        map_attributes_4.put("attributeId", "4");
        map_attributes_4.put("value", null);

        HashMap<String, String> map_attributes_37 = new HashMap<String, String>();
        map_attributes_37.put("attributeId", "37");
        map_attributes_37.put("value", null);


        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();

        jsonArrayPayload_1600.add(map_attributes_1);
        jsonArrayPayload_1600.add(map_attributes_2);
        jsonArrayPayload_1600.add(map_attributes_3);
        jsonArrayPayload_1600.add(map_attributes_15);
        jsonArrayPayload_1600.add(map_attributes_30);
        jsonArrayPayload_1600.add(map_attributes_31);
        jsonArrayPayload_1600.add(map_attributes_33);
        jsonArrayPayload_1600.add(map_attributes_34);
        jsonArrayPayload_1600.add(map_attributes_35);
        jsonArrayPayload_1600.add(map_attributes_36);


        val body = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                "Q33",Product_1600, map_Pricing, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp, Vendor100,Vendor103);
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

        jsonArrayPayload_1601.add(map_attributes_1);
        jsonArrayPayload_1601.add(map_attributes_2);
        jsonArrayPayload_1601.add(map_attributes_3);
        jsonArrayPayload_1601.add(map_attributes_4);
        jsonArrayPayload_1601.add(map_attributes_30);
        jsonArrayPayload_1601.add(map_attributes_31);
        jsonArrayPayload_1601.add(map_attributes_33);
        jsonArrayPayload_1601.add(map_attributes_34);
        jsonArrayPayload_1601.add(map_attributes_35);
        jsonArrayPayload_1601.add(map_attributes_36);
        jsonArrayPayload_1601.add(map_attributes_37);


        val body_1601 = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, VendorManagementClient.ProductTypeData_5, NewClientName, false, false,
                timeStamp_1601, Vendor100,Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1602 = new Hashtable<>();
        map_1602.put("productId", ProductLookupClient.Product_1602);

        String timeStamp_1602 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1602)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1602);


        val body_1602 = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                null,Product_1602, map_Pricing, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp_1602, Vendor103,Vendor100);
        System.out.println(body_1602);
        PutUpdateProduct(body_1602)
                .then().assertThat().statusCode(SC_OK);

        Map <String, String> map = new Hashtable<>();
        map.put("vendorId",Vendor100);
        map.put("targetIdentifier", Identifier_15);
        map.put("clientId",Product_1600);

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(response2.contains(1600)&response2.contains(1601)&!response2.contains(1602))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }

    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: if mtn_ng MSISDN is provided and vendor_id isn't provided then all mtn_ng products should be returned in the response")
    @TmsLink("TECH-117594")
    public void testProductInfoIfmtn_ngMSISDNisProvidedAndVendorIdIsNotProvided() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);
        HashMap<String, String> map_attributes_1 = new HashMap<String, String>();
        map_attributes_1.put("attributeId", "1");
        map_attributes_1.put("value", null);

        HashMap<String, String> map_attributes_2 = new HashMap<String, String>();
        map_attributes_2.put("attributeId", "2");
        map_attributes_2.put("value", null);

        HashMap<String, String> map_attributes_3 = new HashMap<String, String>();
        map_attributes_3.put("attributeId", "3");
        map_attributes_3.put("value", null);

        HashMap<String, String> map_attributes_15 = new HashMap<String, String>();
        map_attributes_15.put("attributeId", "15");
        map_attributes_15.put("value", null);

        HashMap<String, String> map_attributes_30 = new HashMap<String, String>();
        map_attributes_30.put("attributeId", "30");
        map_attributes_30.put("value", null);

        HashMap<String, String> map_attributes_31 = new HashMap<String, String>();
        map_attributes_31.put("attributeId", "31");
        map_attributes_31.put("value", null);

        HashMap<String, String> map_attributes_33 = new HashMap<String, String>();
        map_attributes_33.put("attributeId", "33");
        map_attributes_33.put("value", null);

        HashMap<String, String> map_attributes_34 = new HashMap<String, String>();
        map_attributes_34.put("attributeId", "34");
        map_attributes_34.put("value", null);

        HashMap<String, String> map_attributes_35 = new HashMap<String, String>();
        map_attributes_35.put("attributeId", "35");
        map_attributes_35.put("value", null);

        HashMap<String, String> map_attributes_36 = new HashMap<String, String>();
        map_attributes_36.put("attributeId", "36");
        map_attributes_36.put("value", null);

        HashMap<String, String> map_attributes_4 = new HashMap<String, String>();
        map_attributes_4.put("attributeId", "4");
        map_attributes_4.put("value", null);

        HashMap<String, String> map_attributes_37 = new HashMap<String, String>();
        map_attributes_37.put("attributeId", "37");
        map_attributes_37.put("value", null);


        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();

        jsonArrayPayload_1600.add(map_attributes_1);
        jsonArrayPayload_1600.add(map_attributes_2);
        jsonArrayPayload_1600.add(map_attributes_3);
        jsonArrayPayload_1600.add(map_attributes_15);
        jsonArrayPayload_1600.add(map_attributes_30);
        jsonArrayPayload_1600.add(map_attributes_31);
        jsonArrayPayload_1600.add(map_attributes_33);
        jsonArrayPayload_1600.add(map_attributes_34);
        jsonArrayPayload_1600.add(map_attributes_35);
        jsonArrayPayload_1600.add(map_attributes_36);


        val body = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                "Q33",Product_1600, map_Pricing, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp, Vendor100,Vendor103);
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

        jsonArrayPayload_1601.add(map_attributes_1);
        jsonArrayPayload_1601.add(map_attributes_2);
        jsonArrayPayload_1601.add(map_attributes_3);
        jsonArrayPayload_1601.add(map_attributes_4);
        jsonArrayPayload_1601.add(map_attributes_30);
        jsonArrayPayload_1601.add(map_attributes_31);
        jsonArrayPayload_1601.add(map_attributes_33);
        jsonArrayPayload_1601.add(map_attributes_34);
        jsonArrayPayload_1601.add(map_attributes_35);
        jsonArrayPayload_1601.add(map_attributes_36);
        jsonArrayPayload_1601.add(map_attributes_37);


        val body_1601 = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, VendorManagementClient.ProductTypeData_5, NewClientName, false, false,
                timeStamp_1601, Vendor100,Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1602 = new Hashtable<>();
        map_1602.put("productId", ProductLookupClient.Product_1602);

        String timeStamp_1602 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1602)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1602);


        val body_1602 = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                null,Product_1602, map_Pricing, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp_1602, Vendor103,Vendor100);
        System.out.println(body_1602);
        PutUpdateProduct(body_1602)
                .then().assertThat().statusCode(SC_OK);

        Map <String, String> map = new Hashtable<>();
        map.put("targetIdentifier", Identifier_15);
        map.put("clientId",Product_1600);

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(response2.contains(1600)&response2.contains(1601)&!response2.contains(1602))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }

    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: if airtel MSISDN and airtel vendor id are provided then all products should be returned in the response")
    @TmsLink("TECH-117595")
    public void testProductInfoIfMSISDNandAirTelVendorisProvided() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);
        HashMap<String, String> map_attributes_1 = new HashMap<String, String>();
        map_attributes_1.put("attributeId", "1");
        map_attributes_1.put("value", null);

        HashMap<String, String> map_attributes_2 = new HashMap<String, String>();
        map_attributes_2.put("attributeId", "2");
        map_attributes_2.put("value", null);

        HashMap<String, String> map_attributes_3 = new HashMap<String, String>();
        map_attributes_3.put("attributeId", "3");
        map_attributes_3.put("value", null);

        HashMap<String, String> map_attributes_15 = new HashMap<String, String>();
        map_attributes_15.put("attributeId", "15");
        map_attributes_15.put("value", null);

        HashMap<String, String> map_attributes_30 = new HashMap<String, String>();
        map_attributes_30.put("attributeId", "30");
        map_attributes_30.put("value", null);

        HashMap<String, String> map_attributes_31 = new HashMap<String, String>();
        map_attributes_31.put("attributeId", "31");
        map_attributes_31.put("value", null);

        HashMap<String, String> map_attributes_33 = new HashMap<String, String>();
        map_attributes_33.put("attributeId", "33");
        map_attributes_33.put("value", null);

        HashMap<String, String> map_attributes_34 = new HashMap<String, String>();
        map_attributes_34.put("attributeId", "34");
        map_attributes_34.put("value", null);

        HashMap<String, String> map_attributes_35 = new HashMap<String, String>();
        map_attributes_35.put("attributeId", "35");
        map_attributes_35.put("value", null);

        HashMap<String, String> map_attributes_36 = new HashMap<String, String>();
        map_attributes_36.put("attributeId", "36");
        map_attributes_36.put("value", null);

        HashMap<String, String> map_attributes_4 = new HashMap<String, String>();
        map_attributes_4.put("attributeId", "4");
        map_attributes_4.put("value", null);

        HashMap<String, String> map_attributes_37 = new HashMap<String, String>();
        map_attributes_37.put("attributeId", "37");
        map_attributes_37.put("value", null);


        List<Map<String, String>> jsonArrayPayload_1600 = new ArrayList<>();

        jsonArrayPayload_1600.add(map_attributes_1);
        jsonArrayPayload_1600.add(map_attributes_2);
        jsonArrayPayload_1600.add(map_attributes_3);
        jsonArrayPayload_1600.add(map_attributes_4);
        jsonArrayPayload_1600.add(map_attributes_30);
        jsonArrayPayload_1600.add(map_attributes_31);
        jsonArrayPayload_1600.add(map_attributes_33);
        jsonArrayPayload_1600.add(map_attributes_34);
        jsonArrayPayload_1600.add(map_attributes_35);
        jsonArrayPayload_1600.add(map_attributes_36);
        jsonArrayPayload_1600.add(map_attributes_37);


        val body = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                "Q33", Product_1600, map_Pricing, VendorManagementClient.ProductTypeData_5, NewClientName, false, false,
                timeStamp, Vendor103, Vendor103);
        System.out.println(body);
        PutUpdateProduct(body)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1601 = new Hashtable<>();
        map_1601.put("productId", ProductLookupClient.Product_1601);


        String timeStamp_1601 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1601)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1601);

        List<Map<String, String>> jsonArrayPayload_1601 = new ArrayList<>();

        jsonArrayPayload_1601.add(map_attributes_1);
        jsonArrayPayload_1601.add(map_attributes_2);
        jsonArrayPayload_1601.add(map_attributes_3);
        jsonArrayPayload_1601.add(map_attributes_15);
        jsonArrayPayload_1601.add(map_attributes_30);
        jsonArrayPayload_1601.add(map_attributes_31);
        jsonArrayPayload_1601.add(map_attributes_33);
        jsonArrayPayload_1601.add(map_attributes_34);
        jsonArrayPayload_1601.add(map_attributes_35);
        jsonArrayPayload_1601.add(map_attributes_36);


        val body_1601 = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null, Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp_1601, Vendor103, Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1602 = new Hashtable<>();
        map_1602.put("productId", ProductLookupClient.Product_1602);

        String timeStamp_1602 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1602)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1602);


        val body_1602 = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null, Product_1602, map_Pricing, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp_1602, Vendor100, Vendor100);
        System.out.println(body_1602);
        PutUpdateProduct(body_1602)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map = new Hashtable<>();
        map.put("targetIdentifier", ValidAirtelMsisdnWithProducts);
        map.put("vendorId", Vendor103);
        map.put("clientId", Product_1600);

        List<Integer> response2 = getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if (response2.contains(1600) & response2.contains(1601) & !response2.contains(1602)) {
            System.out.println("Only expected products are being appeared");
        } else {
            fail("Expected products do not exist");
        }
    }

    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: if airtel MSISDN is provided and vendor_id isn't provided then all airtel products should be returned in the response")
    @TmsLink("TECH-117596")
    public void testProductInfoIfAirTelMSISDNIsProvidedAndVendorIDisNotProvided() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);
        HashMap<String, String> map_attributes_1 = new HashMap<String, String>();
        map_attributes_1.put("attributeId", "1");
        map_attributes_1.put("value", null);

        HashMap<String, String> map_attributes_2 = new HashMap<String, String>();
        map_attributes_2.put("attributeId", "2");
        map_attributes_2.put("value", null);

        HashMap<String, String> map_attributes_3 = new HashMap<String, String>();
        map_attributes_3.put("attributeId", "3");
        map_attributes_3.put("value", null);

        HashMap<String, String> map_attributes_15 = new HashMap<String, String>();
        map_attributes_15.put("attributeId", "15");
        map_attributes_15.put("value", null);

        HashMap<String, String> map_attributes_30 = new HashMap<String, String>();
        map_attributes_30.put("attributeId", "30");
        map_attributes_30.put("value", null);

        HashMap<String, String> map_attributes_31 = new HashMap<String, String>();
        map_attributes_31.put("attributeId", "31");
        map_attributes_31.put("value", null);

        HashMap<String, String> map_attributes_33 = new HashMap<String, String>();
        map_attributes_33.put("attributeId", "33");
        map_attributes_33.put("value", null);

        HashMap<String, String> map_attributes_34 = new HashMap<String, String>();
        map_attributes_34.put("attributeId", "34");
        map_attributes_34.put("value", null);

        HashMap<String, String> map_attributes_35 = new HashMap<String, String>();
        map_attributes_35.put("attributeId", "35");
        map_attributes_35.put("value", null);

        HashMap<String, String> map_attributes_36 = new HashMap<String, String>();
        map_attributes_36.put("attributeId", "36");
        map_attributes_36.put("value", null);

        HashMap<String, String> map_attributes_4 = new HashMap<String, String>();
        map_attributes_4.put("attributeId", "4");
        map_attributes_4.put("value", null);

        HashMap<String, String> map_attributes_37 = new HashMap<String, String>();
        map_attributes_37.put("attributeId", "37");
        map_attributes_37.put("value", null);


        List<Map<String, String>> jsonArrayPayload_1600 = new ArrayList<>();

        jsonArrayPayload_1600.add(map_attributes_1);
        jsonArrayPayload_1600.add(map_attributes_2);
        jsonArrayPayload_1600.add(map_attributes_3);
        jsonArrayPayload_1600.add(map_attributes_4);
        jsonArrayPayload_1600.add(map_attributes_30);
        jsonArrayPayload_1600.add(map_attributes_31);
        jsonArrayPayload_1600.add(map_attributes_33);
        jsonArrayPayload_1600.add(map_attributes_34);
        jsonArrayPayload_1600.add(map_attributes_35);
        jsonArrayPayload_1600.add(map_attributes_36);
        jsonArrayPayload_1600.add(map_attributes_37);


        val body = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                "Q33", Product_1600, map_Pricing, VendorManagementClient.ProductTypeData_5, NewClientName, false, false,
                timeStamp, Vendor103, Vendor103);
        System.out.println(body);
        PutUpdateProduct(body)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1601 = new Hashtable<>();
        map_1601.put("productId", ProductLookupClient.Product_1601);


        String timeStamp_1601 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1601)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1601);

        List<Map<String, String>> jsonArrayPayload_1601 = new ArrayList<>();

        jsonArrayPayload_1601.add(map_attributes_1);
        jsonArrayPayload_1601.add(map_attributes_2);
        jsonArrayPayload_1601.add(map_attributes_3);
        jsonArrayPayload_1601.add(map_attributes_15);
        jsonArrayPayload_1601.add(map_attributes_30);
        jsonArrayPayload_1601.add(map_attributes_31);
        jsonArrayPayload_1601.add(map_attributes_33);
        jsonArrayPayload_1601.add(map_attributes_34);
        jsonArrayPayload_1601.add(map_attributes_35);
        jsonArrayPayload_1601.add(map_attributes_36);


        val body_1601 = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null, Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp_1601, Vendor103, Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1602 = new Hashtable<>();
        map_1602.put("productId", ProductLookupClient.Product_1602);

        String timeStamp_1602 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1602)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1602);


        val body_1602 = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null, Product_1602, map_Pricing, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp_1602, Vendor100, Vendor100);
        System.out.println(body_1602);
        PutUpdateProduct(body_1602)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map = new Hashtable<>();
        map.put("targetIdentifier", ValidAirtelMsisdnWithProducts);
        map.put("clientId", Product_1600);

        List<Integer> response2 = getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if (response2.contains(1600) & response2.contains(1601) & !response2.contains(1602)) {
            System.out.println("Only expected products are being appeared");
        } else {
            fail("Expected products do not exist");
        }
    }

    @Test
    @Description("GET /public/v3/productInfo :: if topSeller isn't specified then all products with topSeller true or false")
    @TmsLink("TECH-115191")
    public void testProductInfoIfTopSellerIsNotProvided() {
        Map <String, String> map = new Hashtable<>();
        map.put("clientId",ProductLookupClient.TestClient250);
        map.put("includeInactive","true");

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(response2.contains(1500)&response2.contains(1501))
        {
            System.out.println("Products with both true and false are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
        List<Integer> response3 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.topSeller");
        System.out.println(response3);

        if(response3.contains(false)&response3.contains(true))
        {
            System.out.println("Products with both true and false are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
        }

    @Test
    @Description("GET /public/v3/productInfo :: if \"topSeller\" parameter is set to false then only products with \"topSeller\" false are returned in the response")
    @TmsLink("TECH-115273")
    public void testProductInfoIfTopSellerFalse() {
        Map <String, String> map = new Hashtable<>();
        map.put("clientId",ProductLookupClient.TestClient250);
        map.put("includeInactive","true");
        map.put("topSeller","false");

        List<Boolean> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.topSeller");
        System.out.println(response2);

        if (response2.stream().allMatch(t -> t.equals(false)))
        {
            System.out.println("Only products with topseller = false are being appeared");
        }
        else
        {
            fail("Products with top seller other than false are being appeared");
        }
    }
    @Test
    @Description("GET /public/v3/productInfo :: if \"topSellerPlatform\" parameter isn't specified then all the products are returned in the response")
    @TmsLink("TECH-115493")
    public void testProductInfoIfTopSellerPlatformIsNotProvidedAllProducts() {
        Map <String, String> map = new Hashtable<>();
        map.put("clientId",ProductLookupClient.TestClient250);
        map.put("includeInactive","true");

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(response2.contains(1500)&response2.contains(1501)&response2.contains(1502))
        {
            System.out.println("Products with all topsellersplatforms are appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
        List<Integer> response3 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.topSellerPlatform");
        System.out.println(response3);

        if(response3.contains("SMS"))
        {
            System.out.println("Products with all topsellersplatforms are appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("GET /public/v3/productInfo :: if \"topSellerPlatform\" parameter is specified then only products with valid topSellerPlatform are returned in the response")
    @TmsLink("TECH-115623")
    public void testProductInfoIfValidTopSellerPlatformProvided() {
        Map <String, String> map = new Hashtable<>();
        map.put("clientId",ProductLookupClient.TestClient250);
        map.put("includeInactive","true");
        map.put("topSellerPlatform","USSD");

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(response2.contains(1500)&!response2.contains(1501)&!response2.contains(1502))
        {
            System.out.println("Products with only topsellerplatform = USSD are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }

    }
    @Test
    @Description("GET /public/v3/productInfo :: if invalid \"topSellerPlatform\" parameter is specified then none of the products are returned in the response")
    @TmsLink("TECH-115624")
    public void testProductInfoIfInvalidTopSellerPlatformSpecified() {
        Map <String, String> map = new Hashtable<>();
        map.put("clientId",ProductLookupClient.TestClient250);
        map.put("includeInactive","true");
        map.put("topSellerPlatform","invalidTest");

        getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_NO_CONTENT);

    }
    @Test
    @Description("GET /public/v3/productInfo :: if \"subscriberType\" parameter isn't specified then all the products are returned in the response")
    @TmsLink("TECH-115274")
    public void testProductInfoIfSubscriberTypeIsNotProvided() {//Can be added more validations for all subscriber types
        Map <String, String> map = new Hashtable<>();
        map.put("clientId",ProductLookupClient.TestClient250);
        map.put("includeInactive","true");

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(response2.contains(1500)&response2.contains(1501)&response2.contains(1502))
        {
            System.out.println("Products with all subscriber types are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
        List<String> response3 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.subscriberType");
        System.out.println(response3);

        if(response3.contains("Hybrid"))
        {
            System.out.println("Products with all subscriber types are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }

    }
    @Test
    @Description("GET /public/v3/productInfo :: if \"purchaseMedium\" parameter is specified and is invalid then none of the products are returned in the response")
    @TmsLink("TECH-115683")
    public void testProductInfoIfInvalidPurchaseMediumSpecified() {
        Map <String, String> map = new Hashtable<>();
        map.put("clientId",ProductLookupClient.TestClient250);
        map.put("includeInactive","true");
        map.put("purchaseMedium","InvalidTest");

        getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_NO_CONTENT);
    }
    @Test
    @Description("GET /public/v3/productInfo :: if \"subscriberType\" parameter is specified then only products with valid subscriberType are returned in the response")
    @TmsLink("TECH-115276")
    public void testProductInfoIfValidSubscriberTypeIsNotProvided() {//Completed but more validations could be added.
        Map <String, String> map = new Hashtable<>();
        map.put("clientId",ProductLookupClient.TestClient250);
        map.put("includeInactive","true");
        map.put("subscriberType","Prepaid");

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(response2.contains(1500))
        {
            System.out.println("Products with only Prepaid subscriber types are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
//        List<Integer> response3 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
//                .then().assertThat().statusCode(SC_OK)
//                .extract().jsonPath().get("publicProduct.subscriberType");
//        System.out.println(response3);
//
//        if(response3.contains(1500)&response3.contains(1501)&response3.contains(1502))
//        {
//            System.out.println("Products with all subscriber types are being appeared");
//        }
//        else
//        {
//            fail("Expected products do not exist");
//        }
    }
    @Test
    @Description("GET /public/v3/productInfo :: if \"subscriberType\" parameter is specified and is invalid then none of the products are returned in the response")
    @TmsLink("TECH-115277")
    public void testProductInfoIfInvalidSubscriberTypeSpecified() {
        Map <String, String> map = new Hashtable<>();
        map.put("clientId",ProductLookupClient.TestClient250);
        map.put("includeInactive","true");
        map.put("subscriberType","InvalidTest");

        getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_NO_CONTENT);
    }
    @Test
    @Description("GET /public/v3/productInfo :: if \"purchaseMedium\" parameter isn't specified then all the products are returned in the response")
    @TmsLink("TECH-115625")
    public void testProductInfoIfPurchaseMediumIsNotProvided() {
        Map <String, String> map = new Hashtable<>();
        map.put("clientId",ProductLookupClient.TestClient250);
        map.put("includeInactive","true");

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(response2.contains(1500)&response2.contains(1501)&response2.contains(1502))
        {
            System.out.println("Products with all Purchase mediums are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }

    }
    @Test
    @Description("GET /public/v3/productInfo :: if \"purchaseMedium\" parameter is specified then only products with valid purchaseMedium are returned in the response")
    @TmsLink("TECH-115626")
    public void testProductInfoIfValidPurchaseMediumIsProvided() {
        Map <String, String> map = new Hashtable<>();
        map.put("clientId",ProductLookupClient.TestClient250);
        map.put("includeInactive","true");
        map.put("purchaseMedium","Card");

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(!response2.contains(1501)&response2.contains(1500)&!response2.contains(1502))
        {
            System.out.println("Products with all Purchase mediums are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }

    @Test
    @Description("GET /public/v3/productInfo :: if \"includeInactive\" parameter isn't sent then only active products are returned in the response")
    @TmsLink("TECH-116776")
    public void testProductInfoIfIncludeInactiveParameterIsNotProvided() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);
        val body = setUpPutProductData(true, UsersClient.country, NewClientName, NewClientName,
                null,Product_1600, map_Pricing, Product_1600, NewClientName, false, true,
                timeStamp, Product_1600,"1","1","2",Product_1600);
        System.out.println(body);
        PutUpdateProduct(body)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1601 = new Hashtable<>();
        map_1601.put("productId", ProductLookupClient.Product_1601);

        Map<String, String> map_Pricing_1601 = new Hashtable<>();
        map_Pricing_1601.put("type", ProductType_Single);
        map_Pricing_1601.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp_1600 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1601)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1600);
        val body_1601 = setUpPutProductData(false, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing_1601, ProductType_1603, NewClientName, false, false,
                timeStamp_1600, Product_1600,"1","1","2",Product_1600);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map <String, String> map = new Hashtable<>();
        map.put("clientId",Product_1600);

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(response2.contains(1600)&!response2.contains(1601))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: if the valid \"productTypeId\" is specified then product is returned in the response")
    @TmsLink("TECH-116763")
    public void testProductInfoIfProductTypeIdIsProvided() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);
        val body = setUpPutProductData(true, UsersClient.country, NewClientName, NewClientName,
                null,Product_1600, map_Pricing, Product_1600, NewClientName, false, false,
                timeStamp, Product_1600,Product_1600);
        System.out.println(body);
        PutUpdateProduct(body)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1601 = new Hashtable<>();
        map_1601.put("productId", ProductLookupClient.Product_1601);

        Map<String, String> map_Pricing_1601 = new Hashtable<>();
        map_Pricing_1601.put("type", ProductType_Single);
        map_Pricing_1601.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp_1600 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1601)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1600);
        val body_1601 = setUpPutProductData(true, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing_1601, ProductType_1603, NewClientName, false, false,
                timeStamp_1600, Product_1600,Product_1600);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map <String, String> map = new Hashtable<>();
        map.put("clientId",Product_1600);
        map.put("productTypeId",Product_1600);

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(response2.contains(1600)&!response2.contains(1601))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: if the \"product_Id\" is specified for the product which doesn't exist in the system then empty response is returned")
    @TmsLink("TECH-116758")
    public void testProductInfoIfInvalidProductIdIsProvided() {
        Map <String, String> map = new Hashtable<>();
        map.put("clientId",ProductLookupClient.TestClient250);
        map.put("productId",Product_Invalid);

        getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("message", Matchers.containsString(ProductLookupClient.responseMessageInvalidProductIDProductInfoV3));
    }
    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: if the invalid \"vendorId\" is specified then empty response is returned")
    @TmsLink("TECH-116772")
    public void testProductInfoIfInvalidProductTypeId() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);
        val body = setUpPutProductData(true, UsersClient.country, NewClientName, NewClientName,
                null,Product_1600, map_Pricing, Product_1600, NewClientName, false, false,
                timeStamp, Product_1600,Product_1600);
        System.out.println(body);
        PutUpdateProduct(body)
                .then().assertThat().statusCode(SC_OK);

        Map <String, String> map = new Hashtable<>();
        map.put("clientId",Product_1600);
        map.put("vendorId",ReserveAndTransactClient.TestClient3);

        getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_NO_CONTENT);

    }
    @Test
    @Description("GET /public/v3/productInfo :: if \"includeInactive\" parameter is set to false then only active products are returned in the response")
    @TmsLink("TECH-116774")
    public void testProductInfoIfIncludeInactiveParameterIsFalse() {
        Map <String, String> map = new Hashtable<>();
        map.put("clientId",ProductLookupClient.TestClient250);
        map.put("includeInactive","false");

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.active");
        System.out.println(response2);

        if(response2.contains(true)&!response2.contains(false))
        {
            System.out.println("Only True products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("GET /public/v3/productInfo :: if \"includeInactive\" parameter is set to true then active and inactive products are returned in the response")
    @TmsLink("TECH-116775")
    public void testProductInfoIfIncludeInactiveParameterIsTrue() {
        Map <String, String> map = new Hashtable<>();
        map.put("clientId",ReserveAndTransactClient.TestClient3);
        map.put("includeInactive","true");

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.active");
        System.out.println(response2);

        if(response2.contains(true)&response2.contains(false))
        {
            System.out.println("Only True products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: if the valid \"channelId\" is specified then product is returned in the response")
    @TmsLink("TECH-116766")
    public void testProductInfoIfChannelIdIsProvided() {
        Map <String, String> map = new Hashtable<>();
        map.put("clientId",ReserveAndTransactClient.TestClient3);
        map.put("includeInactive","true");
        map.put("channelId","2");

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(response2.contains(1500)&!response2.contains(1501))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: if the \"client_id\" isn't specified then the request should fail")
    @TmsLink("TECH-116754")
    public void testProductInfoIfClientIdIsNotProvided() {
        Map <String, String> map = new Hashtable<>();
        //map.put("clientId",ReserveAndTransactClient.TestClient3);
        map.put("includeInactive","true");

        getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("message", Matchers.containsString(ProductLookupClient.responseMessageClientIdNotProvidedInProductInfo));
    }
    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: if the invalid \"channelId\" is specified then empty response is returned")
    @TmsLink("TECH-116768")
    public void testProductInfoIfInvalidChannelIDIsProvided() {
        Map <String, String> map = new Hashtable<>();
        map.put("clientId",ProductLookupClient.TestClient250);
        map.put("channelId","7444");

        getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_NO_CONTENT);
    }
    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: if the \"product_Id\" isn't specified then all valid products are returned in the response")
    @TmsLink("TECH-116762")
    public void testProductInfoIfProductIdIsNotSpecified() {
        Map <String, String> map = new Hashtable<>();
        map.put("clientId",ReserveAndTransactClient.TestClient3);
        map.put("includeInactive","true");

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(response2.contains(917)&response2.contains(130))
        {
            System.out.println("Expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: if the \"product_Id\" is specified of the product which isn't linked to the client then empty response is returned")
    @TmsLink("TECH-116756")
    public void testProductInfoIfProductIdIsSpecifiedButNotLinkedToClient() {
        Map <String, String> map = new Hashtable<>();
        map.put("clientId",ReserveAndTransactClient.TestClient3);
        map.put("productId","30");
        getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_NO_CONTENT);
    }
    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: if the valid \"product_Id\" is specified then product is returned in the response")
    @TmsLink("TECH-116755")
    public void testProductInfoIfProductIdIsSpecifiedAndLinedToClient() { //Marko feedback required
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);
        val body = setUpPutProductData(true, UsersClient.country, NewClientName, NewClientName,
                null,Product_1600, map_Pricing, Product_1600, NewClientName, false, false,
                timeStamp, Product_1600,Product_1600);
        System.out.println(body);
        PutUpdateProduct(body)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1601 = new Hashtable<>();
        map_1601.put("productId", ProductLookupClient.Product_1601);

        Map<String, String> map_Pricing_1601 = new Hashtable<>();
        map_Pricing_1601.put("type", ProductType_Single);
        map_Pricing_1601.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp_1600 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1601)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1600);
        val body_1601 = setUpPutProductData(true, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing_1601, ProductType_1603, NewClientName, false, false,
                timeStamp_1600, Product_1600,Product_1600);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map <String, String> map = new Hashtable<>();
        map.put("clientId",Product_1600);
        map.put("productId",Product_1600);

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(response2.contains(1600)&!response2.contains(1601))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: if the \"client_id\" is specified and there is not product linked to the client then no product is returned in the response")
    @TmsLink("TECH-116753")
    public void testProductInfoIfClientIdSpecifiedButNoProductLInkedToClient() {
        Map <String, String> map = new Hashtable<>();
        map.put("clientId","1503");
        getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_NO_CONTENT);
    }
    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: if the valid \"client_id\" is specified then product is returned in the response")
    @TmsLink("TECH-116751")
    public void testProductInfoIfValidClientIdIsSpecified() {
        Map <String, String> map = new Hashtable<>();
        map.put("clientId",ReserveAndTransactClient.TestClient3);
        map.put("includeInactive","true");

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(response2.contains(1500)&!response2.contains(1507))
        {
            System.out.println("Expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: if the \"vendorId\" isn't specified then all valid products are returned in the response")
    @TmsLink("TECH-116773")
    public void testProductInfoIfVendorIdIsNotSpecified() {
        Map <String, String> map = new Hashtable<>();
        map.put("clientId",ReserveAndTransactClient.TestClient3);

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(response2.contains(917)&response2.contains(130))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: if aritel MSISDN is provided and product \"external_product_id\" is null then product shouldn't be returned in the response")
    @TmsLink("TECH-117440")
    public void testProductInfoIfAirtelMsisdnIsProvidedAndExternalProductIdIsNull() {
        Map <String, String> map = new Hashtable<>();
        map.put("clientId",ReserveAndTransactClient.TestClient3);
        map.put("targetIdentifier","2348085767001");

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(!response2.contains(1505))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: if aritel MSISDN is provided and product \"external_product_id\" doesn't match hardcoded for the msisdn then product shouldn't be returned in the response")
    @TmsLink("TECH-117439")
    public void testProductInfoIfAirtelMsisdnIsProvidedAndExternalProductIdDoesNotMatchWithHardCodedValue() {
        Map <String, String> map = new Hashtable<>();
        map.put("clientId",ReserveAndTransactClient.TestClient3);
        map.put("targetIdentifier","2348085767001");

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(!response2.contains(1504))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: if the \"productTypeId\" is specified but no products with the \"productTypeId\" is configured to the client then empty response is returned")
    @TmsLink("TECH-116764")
    public void testProductInfoIfProductTypeIdIsSpecifiedButNoTLinkedToClient() {
        Map <String, String> map = new Hashtable<>();
        map.put("clientId",ReserveAndTransactClient.TestClient3);
        map.put("productTypeId","1500");

        getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_NO_CONTENT);
    }
//    @Test
//    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: if not existed \"productTypeId\" is specified then error is returned")
//    @TmsLink("TECH-128071")
//    public void testProductInfoIfInvalidProductTypeIdIsProvided() {// Not decided what to do
//        Map <String, String> map = new Hashtable<>();
//        map.put("clientId",ReserveAndTransactClient.TestClient3);
//        map.put("productId","999");
//
//        getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
//                .then().assertThat().statusCode(SC_BAD_REQUEST)
//                .body("message", Matchers.containsString(ProductLookupClient.responseMessageInvalidProductIDProductInfoV3));
//    }

    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: if the \"productTypeId\" isn't specified then all valid products are returned in the response")
    @TmsLink("TECH-116765")
    public void testProductTypeIsNotSpecified() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);
        val body = setUpPutProductData(true, UsersClient.country, NewClientName, NewClientName,
                null,Product_1600, map_Pricing, Product_1600, NewClientName, false, false,
                timeStamp, Product_1600,Product_1600);
        System.out.println(body);
        PutUpdateProduct(body)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1601 = new Hashtable<>();
        map_1601.put("productId", ProductLookupClient.Product_1601);

        Map<String, String> map_Pricing_1601 = new Hashtable<>();
        map_Pricing_1601.put("type", ProductType_Single);
        map_Pricing_1601.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp_1600 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1601)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1600);
        val body_1601 = setUpPutProductData(true, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing_1601, ProductType_1603, NewClientName, false, false,
                timeStamp_1600, Product_1600,Product_1600);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map <String, String> map = new Hashtable<>();
        map.put("clientId",Product_1600);

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(response2.contains(1600)&response2.contains(1601))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: check the \"topSeller\" works as \"AND\" query parameter with all other parameters")
    @TmsLink("TECH-118337")
    public void testTopSellerAndQueryParameters() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);
        val body = setUpPutProductData(true, UsersClient.country, NewClientName, NewClientName,
                null,Product_1600, map_Pricing, Product_1600, NewClientName, false, true,
                timeStamp, Product_1600, "1", "1", "2",Product_1600);
        System.out.println(body);
        PutUpdateProduct(body)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1601 = new Hashtable<>();
        map_1601.put("productId", ProductLookupClient.Product_1601);

        Map<String, String> map_Pricing_1601 = new Hashtable<>();
        map_Pricing_1601.put("type", ProductType_Single);
        map_Pricing_1601.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp_1601 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1601)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1601);
        val body_1601 = setUpPutProductData(true, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, Product_1600, NewClientName, false, false,
                timeStamp_1601, Product_1600, "1", "1", "2",Product_1600);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map <String, String> map = new Hashtable<>();
        map.put("clientId",Product_1600);
        map.put("productTypeId",Product_1600);
        map.put("includeInactive","true");
        map.put("topSeller","true");
        map.put("subscriberType","Converged");
        map.put("topSellerPlatform","USSD");
        map.put("purchaseMedium","Card");
        map.put("channelId","2");
        map.put("vendorId",Product_1600);

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(response2.contains(1600)&!response2.contains(1601))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: check the \"topSellerPlatform\" works as \"AND\" query parameter with all other parameters")
    @TmsLink("TECH-118339")
    public void testTopSellerPlatformAndQueryParameters() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);
        val body = setUpPutProductData(true, UsersClient.country, NewClientName, NewClientName,
                null,Product_1600, map_Pricing, Product_1600, NewClientName, false, true,
                timeStamp, Product_1600, "1", "1", "2",Product_1600);
        System.out.println(body);
        PutUpdateProduct(body)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1601 = new Hashtable<>();
        map_1601.put("productId", ProductLookupClient.Product_1601);

        Map<String, String> map_Pricing_1601 = new Hashtable<>();
        map_Pricing_1601.put("type", ProductType_Single);
        map_Pricing_1601.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp_1601 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1601)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1601);
        val body_1601 = setUpPutProductData(true, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, Product_1600, NewClientName, false, true,
                timeStamp_1601, Product_1600, "1", "1", "1",Product_1600);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map <String, String> map = new Hashtable<>();
        map.put("clientId",Product_1600);
        map.put("productTypeId",Product_1600);
        map.put("includeInactive","true");
        map.put("topSeller","true");
        map.put("subscriberType","Converged");
        map.put("topSellerPlatform","USSD");
        map.put("purchaseMedium","Card");
        map.put("channelId","2");
        map.put("vendorId",Product_1600);

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(response2.contains(1600)&!response2.contains(1601))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: check the \"channelId\" works as \"AND\" query parameter with all other parameters")
    @TmsLink("TECH-118341")
    public void testChannelIdAndQueryParameters() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);
        val body = setUpPutProductData(true, UsersClient.country, NewClientName, NewClientName,
                null,Product_1600, map_Pricing, Product_1600, NewClientName, false, true,
                timeStamp, Product_1600, "1", "1", "2",Product_1600);
        System.out.println(body);
        PutUpdateProduct(body)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1601 = new Hashtable<>();
        map_1601.put("productId", ProductLookupClient.Product_1601);

        Map<String, String> map_Pricing_1601 = new Hashtable<>();
        map_Pricing_1601.put("type", ProductType_Single);
        map_Pricing_1601.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp_1601 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1601)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1601);
        val body_1601 = setUpPutProductData(true, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing_1601, Product_1600, NewClientName, false, true,
                timeStamp_1601, Product_1600, "1", "1", "2",Product_1600);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map <String, String> map = new Hashtable<>();
        map.put("clientId",Product_1600);
        map.put("productTypeId",Product_1600);
        map.put("includeInactive","true");
        map.put("topSeller","true");
        map.put("subscriberType","Converged");
        map.put("topSellerPlatform","USSD");
        map.put("purchaseMedium","Card");
        map.put("channelId","3");
        map.put("vendorId",Product_1600);

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(!response2.contains(1600)&response2.contains(1601))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: if aritel MSISDN is provided and product \"product_type\" isn't data then product should be returned in the response")
    @TmsLink("TECH-117441")
    public void testAirtelMsisdnProvidedAndProductTypeIsNot() {

        Map<String, String> map_1601 = new Hashtable<>();
        map_1601.put("productId", ProductLookupClient.Product_1601);

        Map<String, String> map_Pricing_1601 = new Hashtable<>();
        map_Pricing_1601.put("type", ProductType_Single);
        map_Pricing_1601.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp_1601 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1601)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1601);

        HashMap<String, String> map_attributes_1 = new HashMap<String, String>();
        map_attributes_1.put("attributeId", "1");
        map_attributes_1.put("value", null);

        HashMap<String, String> map_attributes_2 = new HashMap<String, String>();
        map_attributes_2.put("attributeId", "2");
        map_attributes_2.put("value", null);

        HashMap<String, String> map_attributes_3 = new HashMap<String, String>();
        map_attributes_3.put("attributeId", "3");
        map_attributes_3.put("value", null);

        HashMap<String, String> map_attributes_15 = new HashMap<String, String>();
        map_attributes_15.put("attributeId", "15");
        map_attributes_15.put("value", null);

        HashMap<String, String> map_attributes_30 = new HashMap<String, String>();
        map_attributes_30.put("attributeId", "30");
        map_attributes_30.put("value", null);

        HashMap<String, String> map_attributes_31 = new HashMap<String, String>();
        map_attributes_31.put("attributeId", "31");
        map_attributes_31.put("value", null);

        HashMap<String, String> map_attributes_33 = new HashMap<String, String>();
        map_attributes_33.put("attributeId", "33");
        map_attributes_33.put("value", null);

        HashMap<String, String> map_attributes_34 = new HashMap<String, String>();
        map_attributes_34.put("attributeId", "34");
        map_attributes_34.put("value", null);

        HashMap<String, String> map_attributes_35 = new HashMap<String, String>();
        map_attributes_35.put("attributeId", "35");
        map_attributes_35.put("value", null);

        HashMap<String, String> map_attributes_36 = new HashMap<String, String>();
        map_attributes_36.put("attributeId", "36");
        map_attributes_36.put("value", null);

        List<Map<String,String>> jsonArrayPayload = new ArrayList<>();

        jsonArrayPayload.add(map_attributes_1);
        jsonArrayPayload.add(map_attributes_2);
        jsonArrayPayload.add(map_attributes_3);
        jsonArrayPayload.add(map_attributes_15);
        jsonArrayPayload.add(map_attributes_30);
        jsonArrayPayload.add(map_attributes_31);
        jsonArrayPayload.add(map_attributes_33);
        jsonArrayPayload.add(map_attributes_34);
        jsonArrayPayload.add(map_attributes_35);
        jsonArrayPayload.add(map_attributes_36);

        val body_1601 = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing_1601, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp_1601, Vendor103,Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map <String, String> map = new Hashtable<>();
        map.put("clientId",Product_1600);
        map.put("targetIdentifier", ValidAirtelMsisdnWithProducts);

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(!response2.contains(1600)&response2.contains(1601))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: if aritel MSISDN without hardcoded data products is provided then only not data product should be returned in the response")
    @TmsLink("TECH-117443")
    public void testAirtelMsisdnProvidedWithoutHardCodedData() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);
        HashMap<String, String> map_attributes_1 = new HashMap<String, String>();
        map_attributes_1.put("attributeId", "1");
        map_attributes_1.put("value", null);

        HashMap<String, String> map_attributes_2 = new HashMap<String, String>();
        map_attributes_2.put("attributeId", "2");
        map_attributes_2.put("value", null);

        HashMap<String, String> map_attributes_3 = new HashMap<String, String>();
        map_attributes_3.put("attributeId", "3");
        map_attributes_3.put("value", null);

        HashMap<String, String> map_attributes_15 = new HashMap<String, String>();
        map_attributes_15.put("attributeId", "15");
        map_attributes_15.put("value", null);

        HashMap<String, String> map_attributes_30 = new HashMap<String, String>();
        map_attributes_30.put("attributeId", "30");
        map_attributes_30.put("value", null);

        HashMap<String, String> map_attributes_31 = new HashMap<String, String>();
        map_attributes_31.put("attributeId", "31");
        map_attributes_31.put("value", null);

        HashMap<String, String> map_attributes_33 = new HashMap<String, String>();
        map_attributes_33.put("attributeId", "33");
        map_attributes_33.put("value", null);

        HashMap<String, String> map_attributes_34 = new HashMap<String, String>();
        map_attributes_34.put("attributeId", "34");
        map_attributes_34.put("value", null);

        HashMap<String, String> map_attributes_35 = new HashMap<String, String>();
        map_attributes_35.put("attributeId", "35");
        map_attributes_35.put("value", null);

        HashMap<String, String> map_attributes_36 = new HashMap<String, String>();
        map_attributes_36.put("attributeId", "36");
        map_attributes_36.put("value", null);

        HashMap<String, String> map_attributes_4 = new HashMap<String, String>();
        map_attributes_4.put("attributeId", "4");
        map_attributes_4.put("value", null);

        HashMap<String, String> map_attributes_37 = new HashMap<String, String>();
        map_attributes_37.put("attributeId", "37");
        map_attributes_37.put("value", null);


        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();

        jsonArrayPayload_1600.add(map_attributes_1);
        jsonArrayPayload_1600.add(map_attributes_2);
        jsonArrayPayload_1600.add(map_attributes_3);
        jsonArrayPayload_1600.add(map_attributes_30);
        jsonArrayPayload_1600.add(map_attributes_31);
        jsonArrayPayload_1600.add(map_attributes_33);
        jsonArrayPayload_1600.add(map_attributes_34);
        jsonArrayPayload_1600.add(map_attributes_35);
        jsonArrayPayload_1600.add(map_attributes_36);
        jsonArrayPayload_1600.add(map_attributes_4);
        jsonArrayPayload_1600.add(map_attributes_37);

        val body = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                null,Product_1600, map_Pricing, VendorManagementClient.ProductTypeData_5, NewClientName, false, false,
                timeStamp, Vendor103,Vendor103);
        System.out.println(body);
        PutUpdateProduct(body)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1601 = new Hashtable<>();
        map_1601.put("productId", ProductLookupClient.Product_1601);

        Map<String, String> map_Pricing_1601 = new Hashtable<>();
        map_Pricing_1601.put("type", ProductType_Single);
        map_Pricing_1601.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp_1601 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1601)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1601);

        List<Map<String,String>> jsonArrayPayload_1601 = new ArrayList<>();

        jsonArrayPayload_1601.add(map_attributes_1);
        jsonArrayPayload_1601.add(map_attributes_2);
        jsonArrayPayload_1601.add(map_attributes_3);
        jsonArrayPayload_1601.add(map_attributes_15);
        jsonArrayPayload_1601.add(map_attributes_30);
        jsonArrayPayload_1601.add(map_attributes_31);
        jsonArrayPayload_1601.add(map_attributes_33);
        jsonArrayPayload_1601.add(map_attributes_34);
        jsonArrayPayload_1601.add(map_attributes_35);
        jsonArrayPayload_1601.add(map_attributes_36);


        val body_1601 = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing_1601, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp_1601, Vendor103,Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map <String, String> map = new Hashtable<>();
        map.put("clientId",Product_1600);
        map.put("targetIdentifier", ValidAirtelMsisdnWithoutProducts);

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(!response2.contains(1600)&response2.contains(1601))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: if mtn_ng MSISDN and Airtel vendor id are provided then only not data product should be returned in the response")
    @TmsLink("TECH-117580")
    public void testMtnNgAndAirtelMsisdnProvided() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);
        HashMap<String, String> map_attributes_1 = new HashMap<String, String>();
        map_attributes_1.put("attributeId", "1");
        map_attributes_1.put("value", null);

        HashMap<String, String> map_attributes_2 = new HashMap<String, String>();
        map_attributes_2.put("attributeId", "2");
        map_attributes_2.put("value", null);

        HashMap<String, String> map_attributes_3 = new HashMap<String, String>();
        map_attributes_3.put("attributeId", "3");
        map_attributes_3.put("value", null);

        HashMap<String, String> map_attributes_15 = new HashMap<String, String>();
        map_attributes_15.put("attributeId", "15");
        map_attributes_15.put("value", null);

        HashMap<String, String> map_attributes_30 = new HashMap<String, String>();
        map_attributes_30.put("attributeId", "30");
        map_attributes_30.put("value", null);

        HashMap<String, String> map_attributes_31 = new HashMap<String, String>();
        map_attributes_31.put("attributeId", "31");
        map_attributes_31.put("value", null);

        HashMap<String, String> map_attributes_33 = new HashMap<String, String>();
        map_attributes_33.put("attributeId", "33");
        map_attributes_33.put("value", null);

        HashMap<String, String> map_attributes_34 = new HashMap<String, String>();
        map_attributes_34.put("attributeId", "34");
        map_attributes_34.put("value", null);

        HashMap<String, String> map_attributes_35 = new HashMap<String, String>();
        map_attributes_35.put("attributeId", "35");
        map_attributes_35.put("value", null);

        HashMap<String, String> map_attributes_36 = new HashMap<String, String>();
        map_attributes_36.put("attributeId", "36");
        map_attributes_36.put("value", null);

        HashMap<String, String> map_attributes_4 = new HashMap<String, String>();
        map_attributes_4.put("attributeId", "4");
        map_attributes_4.put("value", null);

        HashMap<String, String> map_attributes_37 = new HashMap<String, String>();
        map_attributes_37.put("attributeId", "37");
        map_attributes_37.put("value", null);


        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();

        jsonArrayPayload_1600.add(map_attributes_1);
        jsonArrayPayload_1600.add(map_attributes_2);
        jsonArrayPayload_1600.add(map_attributes_3);
        jsonArrayPayload_1600.add(map_attributes_4);
        jsonArrayPayload_1600.add(map_attributes_30);
        jsonArrayPayload_1600.add(map_attributes_31);
        jsonArrayPayload_1600.add(map_attributes_33);
        jsonArrayPayload_1600.add(map_attributes_34);
        jsonArrayPayload_1600.add(map_attributes_35);
        jsonArrayPayload_1600.add(map_attributes_36);
        jsonArrayPayload_1600.add(map_attributes_37);


        val body = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                null,Product_1600, map_Pricing, VendorManagementClient.ProductTypeData_5, NewClientName, false, false,
                timeStamp, Vendor103,Vendor103);
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

        jsonArrayPayload_1601.add(map_attributes_1);
        jsonArrayPayload_1601.add(map_attributes_2);
        jsonArrayPayload_1601.add(map_attributes_3);
        jsonArrayPayload_1601.add(map_attributes_15);
        jsonArrayPayload_1601.add(map_attributes_30);
        jsonArrayPayload_1601.add(map_attributes_31);
        jsonArrayPayload_1601.add(map_attributes_33);
        jsonArrayPayload_1601.add(map_attributes_34);
        jsonArrayPayload_1601.add(map_attributes_35);
        jsonArrayPayload_1601.add(map_attributes_36);


        val body_1601 = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp_1601, Vendor103,Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1602 = new Hashtable<>();
        map_1602.put("productId", ProductLookupClient.Product_1602);

        String timeStamp_1602 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1602)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1602);


        val body_1602 = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1602, map_Pricing, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp_1602, Vendor100,Vendor100);
        System.out.println(body_1602);
        PutUpdateProduct(body_1602)
                .then().assertThat().statusCode(SC_OK);

        Map <String, String> map = new Hashtable<>();
        map.put("vendorId",Vendor103);
        map.put("targetIdentifier", Identifier_15);
        map.put("clientId",Product_1600);

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(!response2.contains(1600)&response2.contains(1601)&!response2.contains(1602))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }

    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: if the valid \"vendorId\" is specified then product is returned in the response")
    @TmsLink("TECH-116771")
    public void testValidVendorIdIsProvided() { //Marko feedback is needed.
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);
        HashMap<String, String> map_attributes_1 = new HashMap<String, String>();
        map_attributes_1.put("attributeId", "1");
        map_attributes_1.put("value", null);

        HashMap<String, String> map_attributes_2 = new HashMap<String, String>();
        map_attributes_2.put("attributeId", "2");
        map_attributes_2.put("value", null);

        HashMap<String, String> map_attributes_3 = new HashMap<String, String>();
        map_attributes_3.put("attributeId", "3");
        map_attributes_3.put("value", null);

        HashMap<String, String> map_attributes_15 = new HashMap<String, String>();
        map_attributes_15.put("attributeId", "15");
        map_attributes_15.put("value", null);

        HashMap<String, String> map_attributes_30 = new HashMap<String, String>();
        map_attributes_30.put("attributeId", "30");
        map_attributes_30.put("value", null);

        HashMap<String, String> map_attributes_31 = new HashMap<String, String>();
        map_attributes_31.put("attributeId", "31");
        map_attributes_31.put("value", null);

        HashMap<String, String> map_attributes_33 = new HashMap<String, String>();
        map_attributes_33.put("attributeId", "33");
        map_attributes_33.put("value", null);

        HashMap<String, String> map_attributes_34 = new HashMap<String, String>();
        map_attributes_34.put("attributeId", "34");
        map_attributes_34.put("value", null);

        HashMap<String, String> map_attributes_35 = new HashMap<String, String>();
        map_attributes_35.put("attributeId", "35");
        map_attributes_35.put("value", null);

        HashMap<String, String> map_attributes_36 = new HashMap<String, String>();
        map_attributes_36.put("attributeId", "36");
        map_attributes_36.put("value", null);

        HashMap<String, String> map_attributes_4 = new HashMap<String, String>();
        map_attributes_4.put("attributeId", "4");
        map_attributes_4.put("value", null);

        HashMap<String, String> map_attributes_37 = new HashMap<String, String>();
        map_attributes_37.put("attributeId", "37");
        map_attributes_37.put("value", null);


        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();

        jsonArrayPayload_1600.add(map_attributes_1);
        jsonArrayPayload_1600.add(map_attributes_2);
        jsonArrayPayload_1600.add(map_attributes_3);
        jsonArrayPayload_1600.add(map_attributes_4);
        jsonArrayPayload_1600.add(map_attributes_30);
        jsonArrayPayload_1600.add(map_attributes_31);
        jsonArrayPayload_1600.add(map_attributes_33);
        jsonArrayPayload_1600.add(map_attributes_34);
        jsonArrayPayload_1600.add(map_attributes_35);
        jsonArrayPayload_1600.add(map_attributes_36);
        jsonArrayPayload_1600.add(map_attributes_37);


        val body = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                null,Product_1600, map_Pricing, VendorManagementClient.ProductTypeData_5, NewClientName, false, false,
                timeStamp, Vendor103,Vendor103);
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

        jsonArrayPayload_1601.add(map_attributes_1);
        jsonArrayPayload_1601.add(map_attributes_2);
        jsonArrayPayload_1601.add(map_attributes_3);
        jsonArrayPayload_1601.add(map_attributes_15);
        jsonArrayPayload_1601.add(map_attributes_30);
        jsonArrayPayload_1601.add(map_attributes_31);
        jsonArrayPayload_1601.add(map_attributes_33);
        jsonArrayPayload_1601.add(map_attributes_34);
        jsonArrayPayload_1601.add(map_attributes_35);
        jsonArrayPayload_1601.add(map_attributes_36);


        val body_1601 = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp_1601, Vendor103,Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1602 = new Hashtable<>();
        map_1602.put("productId", ProductLookupClient.Product_1602);

        String timeStamp_1602 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1602)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1602);

        val body_1602 = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1602, map_Pricing, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp_1602, Vendor100,Vendor100);
        System.out.println(body_1602);
        PutUpdateProduct(body_1602)
                .then().assertThat().statusCode(SC_OK);

        Map <String, String> map = new Hashtable<>();
        map.put("vendorId",Vendor103);
        map.put("clientId",Product_1600);

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(response2.contains(1600)&response2.contains(1601)&!response2.contains(1602))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: if the \"channelId\" isn't specified then all valid products are returned in the response")
    @TmsLink("TECH-116767")
    public void testChannelIdIsNotProvided() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);
        val body = setUpPutProductData(true, UsersClient.country, NewClientName, NewClientName,
                null,Product_1600, map_Pricing, Product_1600, NewClientName, false, true,
                timeStamp, Product_1600, "1", "1", "2",Product_1600);
        System.out.println(body);
        PutUpdateProduct(body)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1601 = new Hashtable<>();
        map_1601.put("productId", ProductLookupClient.Product_1601);

        Map<String, String> map_Pricing_1601 = new Hashtable<>();
        map_Pricing_1601.put("type", ProductType_Single);
        map_Pricing_1601.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp_1601 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1601)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1601);
        val body_1601 = setUpPutProductData(true, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing_1601, Product_1600, NewClientName, false, false,
                timeStamp_1601, Product_1600, "1", "1", "2",Product_1600);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map <String, String> map = new Hashtable<>();
        map.put("clientId",Product_1600);

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(response2.contains(1600)&response2.contains(1601))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: if unknown MSISDN and Airtel vendor id is provided then only not data products should be returned in the response")
    @TmsLink("TECH-117586")
    public void testInValidMsisdnAndAirTelVendorIdIsProvided() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);
        HashMap<String, String> map_attributes_1 = new HashMap<String, String>();
        map_attributes_1.put("attributeId", "1");
        map_attributes_1.put("value", null);

        HashMap<String, String> map_attributes_2 = new HashMap<String, String>();
        map_attributes_2.put("attributeId", "2");
        map_attributes_2.put("value", null);

        HashMap<String, String> map_attributes_3 = new HashMap<String, String>();
        map_attributes_3.put("attributeId", "3");
        map_attributes_3.put("value", null);

        HashMap<String, String> map_attributes_15 = new HashMap<String, String>();
        map_attributes_15.put("attributeId", "15");
        map_attributes_15.put("value", null);

        HashMap<String, String> map_attributes_30 = new HashMap<String, String>();
        map_attributes_30.put("attributeId", "30");
        map_attributes_30.put("value", null);

        HashMap<String, String> map_attributes_31 = new HashMap<String, String>();
        map_attributes_31.put("attributeId", "31");
        map_attributes_31.put("value", null);

        HashMap<String, String> map_attributes_33 = new HashMap<String, String>();
        map_attributes_33.put("attributeId", "33");
        map_attributes_33.put("value", null);

        HashMap<String, String> map_attributes_34 = new HashMap<String, String>();
        map_attributes_34.put("attributeId", "34");
        map_attributes_34.put("value", null);

        HashMap<String, String> map_attributes_35 = new HashMap<String, String>();
        map_attributes_35.put("attributeId", "35");
        map_attributes_35.put("value", null);

        HashMap<String, String> map_attributes_36 = new HashMap<String, String>();
        map_attributes_36.put("attributeId", "36");
        map_attributes_36.put("value", null);

        HashMap<String, String> map_attributes_4 = new HashMap<String, String>();
        map_attributes_4.put("attributeId", "4");
        map_attributes_4.put("value", null);

        HashMap<String, String> map_attributes_37 = new HashMap<String, String>();
        map_attributes_37.put("attributeId", "37");
        map_attributes_37.put("value", null);


        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();

        jsonArrayPayload_1600.add(map_attributes_1);
        jsonArrayPayload_1600.add(map_attributes_2);
        jsonArrayPayload_1600.add(map_attributes_3);
        jsonArrayPayload_1600.add(map_attributes_4);
        jsonArrayPayload_1600.add(map_attributes_30);
        jsonArrayPayload_1600.add(map_attributes_31);
        jsonArrayPayload_1600.add(map_attributes_33);
        jsonArrayPayload_1600.add(map_attributes_34);
        jsonArrayPayload_1600.add(map_attributes_35);
        jsonArrayPayload_1600.add(map_attributes_36);
        jsonArrayPayload_1600.add(map_attributes_37);


        val body = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                null,Product_1600, map_Pricing, VendorManagementClient.ProductTypeData_5, NewClientName, false, false,
                timeStamp, Vendor103,Vendor103);
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

        jsonArrayPayload_1601.add(map_attributes_1);
        jsonArrayPayload_1601.add(map_attributes_2);
        jsonArrayPayload_1601.add(map_attributes_3);
        jsonArrayPayload_1601.add(map_attributes_15);
        jsonArrayPayload_1601.add(map_attributes_30);
        jsonArrayPayload_1601.add(map_attributes_31);
        jsonArrayPayload_1601.add(map_attributes_33);
        jsonArrayPayload_1601.add(map_attributes_34);
        jsonArrayPayload_1601.add(map_attributes_35);
        jsonArrayPayload_1601.add(map_attributes_36);


        val body_1601 = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp_1601, Vendor103,Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1602 = new Hashtable<>();
        map_1602.put("productId", ProductLookupClient.Product_1602);

        String timeStamp_1602 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1602)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1602);


        val body_1602 = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1602, map_Pricing, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp_1602, Vendor100,Vendor100);
        System.out.println(body_1602);
        PutUpdateProduct(body_1602)
                .then().assertThat().statusCode(SC_OK);

        Map <String, String> map = new Hashtable<>();
        map.put("vendorId",Vendor103);
        map.put("clientId",Product_1600);
        map.put("targetIdentifier",Identifier_16);

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(!response2.contains(1600)&response2.contains(1601)&!response2.contains(1602))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: check the \"subscriberType\" works as \"AND\" query parameter with all other parameters")
    @TmsLink("TECH-118338")
    public void testSubscriberTypeAndQueryParameters() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);
        val body = setUpPutProductData(true, UsersClient.country, NewClientName, NewClientName,
                null,Product_1600, map_Pricing, Product_1600, NewClientName, false, true,
                timeStamp, Product_1600, "1", "1", "2",Product_1600);
        System.out.println(body);
        PutUpdateProduct(body)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1601 = new Hashtable<>();
        map_1601.put("productId", ProductLookupClient.Product_1601);

        Map<String, String> map_Pricing_1601 = new Hashtable<>();
        map_Pricing_1601.put("type", ProductType_Single);
        map_Pricing_1601.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp_1601 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1601)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1601);
        val body_1601 = setUpPutProductData(true, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing_1601, Product_1600, NewClientName, false, true,
                timeStamp_1601, Product_1600, "1", "2", "2",Product_1600);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map <String, String> map = new Hashtable<>();
        map.put("clientId",Product_1600);
        map.put("productTypeId",Product_1600);
        map.put("includeInactive","true");
        map.put("topSeller","true");
        map.put("subscriberType","Converged");
        map.put("topSellerPlatform","USSD");
        map.put("purchaseMedium","Card");
        map.put("channelId","2");
        map.put("vendorId",Product_1600);

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(response2.contains(1600)&!response2.contains(1601))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: check the \"vendorId\" works as \"AND\" query parameter with all other parameters")
    @TmsLink("TECH-118342")
    public void testVendorIdAndQueryParameters() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);
        val body = setUpPutProductData(true, UsersClient.country, NewClientName, NewClientName,
                null,Product_1600, map_Pricing, Product_1600, NewClientName, false, true,
                timeStamp, Product_1600, "1", "1", "2",Product_1600);
        System.out.println(body);
        PutUpdateProduct(body)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1601 = new Hashtable<>();
        map_1601.put("productId", ProductLookupClient.Product_1601);

        Map<String, String> map_Pricing_1601 = new Hashtable<>();
        map_Pricing_1601.put("type", ProductType_Single);
        map_Pricing_1601.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp_1601 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1601)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1601);

        val body_1601 = setUpPutProductData(true, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing_1601, Product_1600, NewClientName, false, true,
                timeStamp_1601, Vendor100, "1", "1", "2",Vendor100);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map <String, String> map = new Hashtable<>();
        map.put("clientId",Product_1600);
        map.put("productTypeId",Product_1600);
        map.put("includeInactive","true");
        map.put("topSeller","true");
        map.put("subscriberType","Converged");
        map.put("topSellerPlatform","USSD");
        map.put("purchaseMedium","Card");
        map.put("channelId","2");
        map.put("vendorId",Product_1600);

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(response2.contains(1600)&!response2.contains(1601))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: check the \"productTypeId\" works as \"AND\" query parameter with all other parameters")
    @TmsLink("TECH-118335")
    public void testProductTypeIdAndQueryParameters() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        val body = setUpPutProductData(true, UsersClient.country, NewClientName, NewClientName,
                null,Product_1600, map_Pricing, Product_1600, NewClientName, false, true,
                timeStamp, Product_1600, "1", "1", "2",Product_1600);
        System.out.println(body);
        PutUpdateProduct(body)
                .then().assertThat().statusCode(SC_OK);


        HashMap<String, String> map_attributes_99 = new HashMap<String, String>();
        map_attributes_99.put("attributeId", "99");
        map_attributes_99.put("value", null);

        HashMap<String, String> map_attributes_100 = new HashMap<String, String>();
        map_attributes_100.put("attributeId", "100");
        map_attributes_100.put("value", null);

        List<Map<String,String>> jsonArrayPayload_1601 = new ArrayList<>();

        jsonArrayPayload_1601.add(map_attributes_99);
        jsonArrayPayload_1601.add(map_attributes_100);

        Map<String, String> map_1601 = new Hashtable<>();
        map_1601.put("productId", ProductLookupClient.Product_1601);

        Map<String, String> map_Pricing_1601 = new Hashtable<>();
        map_Pricing_1601.put("type", ProductType_Single);
        map_Pricing_1601.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp_1601 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1601)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1601);
        val body_1601 = setUpPutProductDataWithAttributes(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, Product_1601, NewClientName, false, true,
                timeStamp_1601, Product_1600, "1", "1", "2",Product_1600);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map <String, String> map = new Hashtable<>();
        map.put("clientId",Product_1600);
        map.put("productTypeId",Product_1600);
        map.put("includeInactive","true");
        map.put("topSeller","true");
        map.put("subscriberType","Converged");
        map.put("topSellerPlatform","USSD");
        map.put("purchaseMedium","Card");
        map.put("channelId","2");
        map.put("vendorId",Product_1600);

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(response2.contains(1600)&!response2.contains(1601))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: check the \"includeInactive\" works as \"AND\" query parameter with all other parameters")
    @TmsLink("TECH-118336")
    public void testIncludeInactiveAndQueryParameters() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);
        val body = setUpPutProductData(true, UsersClient.country, NewClientName, NewClientName,
                null,Product_1600, map_Pricing, Product_1600, NewClientName, false, true,
                timeStamp, Product_1600, "1", "1", "2",Product_1600);
        System.out.println(body);
        PutUpdateProduct(body)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1601 = new Hashtable<>();
        map_1601.put("productId", ProductLookupClient.Product_1601);

        Map<String, String> map_Pricing_1601 = new Hashtable<>();
        map_Pricing_1601.put("type", ProductType_Single);
        map_Pricing_1601.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp_1601 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1601)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1601);

        val body_1601 = setUpPutProductData(false, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing_1601, Product_1600, NewClientName, false, true,
                timeStamp_1601, Product_1600, "1", "1", "2",Product_1600);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map <String, String> map = new Hashtable<>();
        map.put("clientId",Product_1600);
        map.put("productTypeId",Product_1600);
        map.put("includeInactive","false");
        map.put("topSeller","true");
        map.put("subscriberType","Converged");
        map.put("topSellerPlatform","USSD");
        map.put("purchaseMedium","Card");
        map.put("channelId","2");
        map.put("vendorId",Product_1600);

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(response2.contains(1600)&!response2.contains(1601))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: check the \"productId\" works as \"AND\" query parameter with all other parameters")
    @TmsLink("TECH-118334")
    public void testProductIdAndQueryParameters() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);
        val body = setUpPutProductData(true, UsersClient.country, NewClientName, NewClientName,
                null,Product_1600, map_Pricing, Product_1600, NewClientName, false, true,
                timeStamp, Product_1600, "1", "1", "2",Product_1600);
        System.out.println(body);
        PutUpdateProduct(body)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1601 = new Hashtable<>();
        map_1601.put("productId", ProductLookupClient.Product_1601);

        Map<String, String> map_Pricing_1601 = new Hashtable<>();
        map_Pricing_1601.put("type", ProductType_Single);
        map_Pricing_1601.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp_1601 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1601)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1601);

        val body_1601 = setUpPutProductData(false, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing_1601, Product_1600, NewClientName, false, true,
                timeStamp_1601, Product_1600, "1", "1", "2",Product_1600);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map <String, String> map = new Hashtable<>();
        map.put("clientId",Product_1600);
        map.put("productId",Product_1600);
        map.put("productTypeId",Product_1600);
        map.put("includeInactive","true");
        map.put("topSeller","true");
        map.put("subscriberType","Converged");
        map.put("topSellerPlatform","USSD");
        map.put("purchaseMedium","Card");
        map.put("channelId","2");
        map.put("vendorId",Product_1600);

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(response2.contains(1600)&!response2.contains(1601))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: if aritel MSISDN and Airtel vendor id are provided then product should be returned in the response")
    @TmsLink("TECH-117442")
    public void testAirtelVendorIdIsProvided() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);
        HashMap<String, String> map_attributes_1 = new HashMap<String, String>();
        map_attributes_1.put("attributeId", "1");
        map_attributes_1.put("value", null);

        HashMap<String, String> map_attributes_2 = new HashMap<String, String>();
        map_attributes_2.put("attributeId", "2");
        map_attributes_2.put("value", null);

        HashMap<String, String> map_attributes_3 = new HashMap<String, String>();
        map_attributes_3.put("attributeId", "3");
        map_attributes_3.put("value", null);

        HashMap<String, String> map_attributes_15 = new HashMap<String, String>();
        map_attributes_15.put("attributeId", "15");
        map_attributes_15.put("value", null);

        HashMap<String, String> map_attributes_30 = new HashMap<String, String>();
        map_attributes_30.put("attributeId", "30");
        map_attributes_30.put("value", null);

        HashMap<String, String> map_attributes_31 = new HashMap<String, String>();
        map_attributes_31.put("attributeId", "31");
        map_attributes_31.put("value", null);

        HashMap<String, String> map_attributes_33 = new HashMap<String, String>();
        map_attributes_33.put("attributeId", "33");
        map_attributes_33.put("value", null);

        HashMap<String, String> map_attributes_34 = new HashMap<String, String>();
        map_attributes_34.put("attributeId", "34");
        map_attributes_34.put("value", null);

        HashMap<String, String> map_attributes_35 = new HashMap<String, String>();
        map_attributes_35.put("attributeId", "35");
        map_attributes_35.put("value", null);

        HashMap<String, String> map_attributes_36 = new HashMap<String, String>();
        map_attributes_36.put("attributeId", "36");
        map_attributes_36.put("value", null);

        HashMap<String, String> map_attributes_4 = new HashMap<String, String>();
        map_attributes_4.put("attributeId", "4");
        map_attributes_4.put("value", null);

        HashMap<String, String> map_attributes_37 = new HashMap<String, String>();
        map_attributes_37.put("attributeId", "37");
        map_attributes_37.put("value", null);


        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();

        jsonArrayPayload_1600.add(map_attributes_1);
        jsonArrayPayload_1600.add(map_attributes_2);
        jsonArrayPayload_1600.add(map_attributes_3);
        jsonArrayPayload_1600.add(map_attributes_4);
        jsonArrayPayload_1600.add(map_attributes_30);
        jsonArrayPayload_1600.add(map_attributes_31);
        jsonArrayPayload_1600.add(map_attributes_33);
        jsonArrayPayload_1600.add(map_attributes_34);
        jsonArrayPayload_1600.add(map_attributes_35);
        jsonArrayPayload_1600.add(map_attributes_36);
        jsonArrayPayload_1600.add(map_attributes_37);


        val body = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                null,Product_1600, map_Pricing, VendorManagementClient.ProductTypeData_5, NewClientName, false, false,
                timeStamp, Vendor103,Vendor103);
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

        jsonArrayPayload_1601.add(map_attributes_1);
        jsonArrayPayload_1601.add(map_attributes_2);
        jsonArrayPayload_1601.add(map_attributes_3);
        jsonArrayPayload_1601.add(map_attributes_15);
        jsonArrayPayload_1601.add(map_attributes_30);
        jsonArrayPayload_1601.add(map_attributes_31);
        jsonArrayPayload_1601.add(map_attributes_33);
        jsonArrayPayload_1601.add(map_attributes_34);
        jsonArrayPayload_1601.add(map_attributes_35);
        jsonArrayPayload_1601.add(map_attributes_36);


        val body_1601 = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp_1601, Vendor103,Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1602 = new Hashtable<>();
        map_1602.put("productId", ProductLookupClient.Product_1602);

        String timeStamp_1602 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1602)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1602);


        val body_1602 = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1602, map_Pricing, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp_1602, Vendor100,Vendor100);
        System.out.println(body_1602);
        PutUpdateProduct(body_1602)
                .then().assertThat().statusCode(SC_OK);

        Map <String, String> map = new Hashtable<>();
        map.put("vendorId",Vendor103);
        map.put("targetIdentifier", Identifier_16);
        map.put("clientId",Product_1600);

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(!response2.contains(1600)&response2.contains(1601)&!response2.contains(1602))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: check the \"clientId\" works as \"AND\" query parameter with all other parameters")
    @TmsLink("TECH-118332")
    public void testClientIdAndQueryParameters() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);
        val body = setUpPutProductData(true, UsersClient.country, NewClientName, NewClientName,
                null,Product_1600, map_Pricing, Product_1600, NewClientName, false, true,
                timeStamp, Product_1600, "1", "1", "2",Product_1600);
        System.out.println(body);
        PutUpdateProduct(body)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1601 = new Hashtable<>();
        map_1601.put("productId", ProductLookupClient.Product_1601);

        Map<String, String> map_Pricing_1601 = new Hashtable<>();
        map_Pricing_1601.put("type", ProductType_Single);
        map_Pricing_1601.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp_1601 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1601)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1601);

        val body_1601 = setUpPutProductData(true, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing_1601, Product_1600, NewClientName, false, true,
                timeStamp_1601, Product_1600, "1", "1", "2",Product_1600);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map <String, String> map = new Hashtable<>();
        map.put("clientId",Product_1601);
        map.put("productTypeId",Product_1600);
        map.put("includeInactive","true");
        map.put("topSeller","true");
        map.put("subscriberType","Converged");
        map.put("topSellerPlatform","USSD");
        map.put("purchaseMedium","Card");
        map.put("channelId","2");
        map.put("vendorId",Product_1600);

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(response2.contains(1600)&!response2.contains(1601))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: check the \"purchaseMedium\" works as \"AND\" query parameter with all other parameters")
    @TmsLink("TECH-118340")
    public void testPurchaseMediumAndQueryParameters() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);
        val body = setUpPutProductData(true, UsersClient.country, NewClientName, NewClientName,
                null,Product_1600, map_Pricing, Product_1600, NewClientName, false, true,
                timeStamp, Product_1600, "1", "1", "2",Product_1600);
        System.out.println(body);
        PutUpdateProduct(body)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1601 = new Hashtable<>();
        map_1601.put("productId", ProductLookupClient.Product_1601);

        Map<String, String> map_Pricing_1601 = new Hashtable<>();
        map_Pricing_1601.put("type", ProductType_Single);
        map_Pricing_1601.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp_1601 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1601)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1601);

        val body_1601 = setUpPutProductData(true, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing_1601, Product_1600, NewClientName, false, true,
                timeStamp_1601, Product_1600, "2", "1", "2",Product_1600);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map <String, String> map = new Hashtable<>();
        map.put("clientId",Product_1600);
        map.put("productTypeId",Product_1600);
        map.put("includeInactive","true");
        map.put("topSeller","true");
        map.put("subscriberType","Converged");
        map.put("topSellerPlatform","USSD");
        map.put("purchaseMedium","Card");
        map.put("channelId","2");
        map.put("vendorId",Product_1600);

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(response2.contains(1600)&!response2.contains(1601))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: if aritel MSISDN is provided and data product \"external_product_id\" matches hardcoded for the msisdn then product should be returned in the response")
    @TmsLink("TECH-117438")
    public void testAirtelMsisdnIsProvidedAndDataProductMatchedWithMsisdn() {//Marko feedback required
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);
        HashMap<String, String> map_attributes_1 = new HashMap<String, String>();
        map_attributes_1.put("attributeId", "1");
        map_attributes_1.put("value", null);

        HashMap<String, String> map_attributes_2 = new HashMap<String, String>();
        map_attributes_2.put("attributeId", "2");
        map_attributes_2.put("value", null);

        HashMap<String, String> map_attributes_3 = new HashMap<String, String>();
        map_attributes_3.put("attributeId", "3");
        map_attributes_3.put("value", null);

        HashMap<String, String> map_attributes_15 = new HashMap<String, String>();
        map_attributes_15.put("attributeId", "15");
        map_attributes_15.put("value", null);

        HashMap<String, String> map_attributes_30 = new HashMap<String, String>();
        map_attributes_30.put("attributeId", "30");
        map_attributes_30.put("value", null);

        HashMap<String, String> map_attributes_31 = new HashMap<String, String>();
        map_attributes_31.put("attributeId", "31");
        map_attributes_31.put("value", null);

        HashMap<String, String> map_attributes_33 = new HashMap<String, String>();
        map_attributes_33.put("attributeId", "33");
        map_attributes_33.put("value", null);

        HashMap<String, String> map_attributes_34 = new HashMap<String, String>();
        map_attributes_34.put("attributeId", "34");
        map_attributes_34.put("value", null);

        HashMap<String, String> map_attributes_35 = new HashMap<String, String>();
        map_attributes_35.put("attributeId", "35");
        map_attributes_35.put("value", null);

        HashMap<String, String> map_attributes_36 = new HashMap<String, String>();
        map_attributes_36.put("attributeId", "36");
        map_attributes_36.put("value", null);

        HashMap<String, String> map_attributes_4 = new HashMap<String, String>();
        map_attributes_4.put("attributeId", "4");
        map_attributes_4.put("value", null);


        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();

        jsonArrayPayload_1600.add(map_attributes_1);
        jsonArrayPayload_1600.add(map_attributes_2);
        jsonArrayPayload_1600.add(map_attributes_3);
        jsonArrayPayload_1600.add(map_attributes_4);
        jsonArrayPayload_1600.add(map_attributes_30);
        jsonArrayPayload_1600.add(map_attributes_31);
        jsonArrayPayload_1600.add(map_attributes_33);
        jsonArrayPayload_1600.add(map_attributes_34);
        jsonArrayPayload_1600.add(map_attributes_35);
        jsonArrayPayload_1600.add(map_attributes_36);


        val body = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                "Q33",Product_1600, map_Pricing, VendorManagementClient.ProductTypeData_5, NewClientName, false, false,
                timeStamp, Vendor103,Vendor103);
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

        jsonArrayPayload_1601.add(map_attributes_1);
        jsonArrayPayload_1601.add(map_attributes_2);
        jsonArrayPayload_1601.add(map_attributes_3);
        jsonArrayPayload_1601.add(map_attributes_15);
        jsonArrayPayload_1601.add(map_attributes_30);
        jsonArrayPayload_1601.add(map_attributes_31);
        jsonArrayPayload_1601.add(map_attributes_33);
        jsonArrayPayload_1601.add(map_attributes_34);
        jsonArrayPayload_1601.add(map_attributes_35);
        jsonArrayPayload_1601.add(map_attributes_36);


        val body_1601 = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp_1601, Vendor103,Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1602 = new Hashtable<>();
        map_1602.put("productId", ProductLookupClient.Product_1602);

        String timeStamp_1602 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1602)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1602);


        val body_1602 = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1602, map_Pricing, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp_1602, Vendor100,Vendor100);
        System.out.println(body_1602);
        PutUpdateProduct(body_1602)
                .then().assertThat().statusCode(SC_OK);

        Map <String, String> map = new Hashtable<>();
        map.put("targetIdentifier", Identifier_17);
        map.put("clientId",Product_1600);

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(response2.contains(1600)&response2.contains(1601)&!response2.contains(1602))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: if the \"vendorId\" isn't specified then all valid products are returned in the response")
    @TmsLink("TECH-116773")
    public void testIfVendorIdIsNotProvided() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);
        HashMap<String, String> map_attributes_1 = new HashMap<String, String>();
        map_attributes_1.put("attributeId", "1");
        map_attributes_1.put("value", null);

        HashMap<String, String> map_attributes_2 = new HashMap<String, String>();
        map_attributes_2.put("attributeId", "2");
        map_attributes_2.put("value", null);

        HashMap<String, String> map_attributes_3 = new HashMap<String, String>();
        map_attributes_3.put("attributeId", "3");
        map_attributes_3.put("value", null);

        HashMap<String, String> map_attributes_15 = new HashMap<String, String>();
        map_attributes_15.put("attributeId", "15");
        map_attributes_15.put("value", null);

        HashMap<String, String> map_attributes_30 = new HashMap<String, String>();
        map_attributes_30.put("attributeId", "30");
        map_attributes_30.put("value", null);

        HashMap<String, String> map_attributes_31 = new HashMap<String, String>();
        map_attributes_31.put("attributeId", "31");
        map_attributes_31.put("value", null);

        HashMap<String, String> map_attributes_33 = new HashMap<String, String>();
        map_attributes_33.put("attributeId", "33");
        map_attributes_33.put("value", null);

        HashMap<String, String> map_attributes_34 = new HashMap<String, String>();
        map_attributes_34.put("attributeId", "34");
        map_attributes_34.put("value", null);

        HashMap<String, String> map_attributes_35 = new HashMap<String, String>();
        map_attributes_35.put("attributeId", "35");
        map_attributes_35.put("value", null);

        HashMap<String, String> map_attributes_36 = new HashMap<String, String>();
        map_attributes_36.put("attributeId", "36");
        map_attributes_36.put("value", null);

        HashMap<String, String> map_attributes_4 = new HashMap<String, String>();
        map_attributes_4.put("attributeId", "4");
        map_attributes_4.put("value", null);


        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();

        jsonArrayPayload_1600.add(map_attributes_1);
        jsonArrayPayload_1600.add(map_attributes_2);
        jsonArrayPayload_1600.add(map_attributes_3);
        jsonArrayPayload_1600.add(map_attributes_4);
        jsonArrayPayload_1600.add(map_attributes_30);
        jsonArrayPayload_1600.add(map_attributes_31);
        jsonArrayPayload_1600.add(map_attributes_33);
        jsonArrayPayload_1600.add(map_attributes_34);
        jsonArrayPayload_1600.add(map_attributes_35);
        jsonArrayPayload_1600.add(map_attributes_36);


        val body = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                null,Product_1600, map_Pricing, VendorManagementClient.ProductTypeData_5, NewClientName, false, false,
                timeStamp, Vendor103,Vendor103);
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

        jsonArrayPayload_1601.add(map_attributes_1);
        jsonArrayPayload_1601.add(map_attributes_2);
        jsonArrayPayload_1601.add(map_attributes_3);
        jsonArrayPayload_1601.add(map_attributes_15);
        jsonArrayPayload_1601.add(map_attributes_30);
        jsonArrayPayload_1601.add(map_attributes_31);
        jsonArrayPayload_1601.add(map_attributes_33);
        jsonArrayPayload_1601.add(map_attributes_34);
        jsonArrayPayload_1601.add(map_attributes_35);
        jsonArrayPayload_1601.add(map_attributes_36);


        val body_1601 = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp_1601, Vendor103,Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1602 = new Hashtable<>();
        map_1602.put("productId", ProductLookupClient.Product_1602);

        String timeStamp_1602 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1602)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1602);


        val body_1602 = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1602, map_Pricing, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp_1602, Vendor100,Vendor100);
        System.out.println(body_1602);
        PutUpdateProduct(body_1602)
                .then().assertThat().statusCode(SC_OK);

        Map <String, String> map = new Hashtable<>();
        map.put("clientId",Product_1600);

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(response2.contains(1600)&response2.contains(1601)&response2.contains(1602))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("GET \u200B/public\u200B/v3\u200B/productInfo :: if unknown MSISDN is provided and vendor id isn't sent then all products configured for the client should be returned in the response")
    @TmsLink("TECH-117585")
    public void testIfUnknownMsisdnProvidedWithoutVendorId() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);
        HashMap<String, String> map_attributes_1 = new HashMap<String, String>();
        map_attributes_1.put("attributeId", "1");
        map_attributes_1.put("value", null);

        HashMap<String, String> map_attributes_2 = new HashMap<String, String>();
        map_attributes_2.put("attributeId", "2");
        map_attributes_2.put("value", null);

        HashMap<String, String> map_attributes_3 = new HashMap<String, String>();
        map_attributes_3.put("attributeId", "3");
        map_attributes_3.put("value", null);

        HashMap<String, String> map_attributes_15 = new HashMap<String, String>();
        map_attributes_15.put("attributeId", "15");
        map_attributes_15.put("value", null);

        HashMap<String, String> map_attributes_30 = new HashMap<String, String>();
        map_attributes_30.put("attributeId", "30");
        map_attributes_30.put("value", null);

        HashMap<String, String> map_attributes_31 = new HashMap<String, String>();
        map_attributes_31.put("attributeId", "31");
        map_attributes_31.put("value", null);

        HashMap<String, String> map_attributes_33 = new HashMap<String, String>();
        map_attributes_33.put("attributeId", "33");
        map_attributes_33.put("value", null);

        HashMap<String, String> map_attributes_34 = new HashMap<String, String>();
        map_attributes_34.put("attributeId", "34");
        map_attributes_34.put("value", null);

        HashMap<String, String> map_attributes_35 = new HashMap<String, String>();
        map_attributes_35.put("attributeId", "35");
        map_attributes_35.put("value", null);

        HashMap<String, String> map_attributes_36 = new HashMap<String, String>();
        map_attributes_36.put("attributeId", "36");
        map_attributes_36.put("value", null);

        HashMap<String, String> map_attributes_4 = new HashMap<String, String>();
        map_attributes_4.put("attributeId", "4");
        map_attributes_4.put("value", null);

        HashMap<String, String> map_attributes_37 = new HashMap<String, String>();
        map_attributes_37.put("attributeId", "37");
        map_attributes_37.put("value", null);


        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();

        jsonArrayPayload_1600.add(map_attributes_1);
        jsonArrayPayload_1600.add(map_attributes_2);
        jsonArrayPayload_1600.add(map_attributes_3);
        jsonArrayPayload_1600.add(map_attributes_4);
        jsonArrayPayload_1600.add(map_attributes_30);
        jsonArrayPayload_1600.add(map_attributes_31);
        jsonArrayPayload_1600.add(map_attributes_33);
        jsonArrayPayload_1600.add(map_attributes_34);
        jsonArrayPayload_1600.add(map_attributes_35);
        jsonArrayPayload_1600.add(map_attributes_36);
        jsonArrayPayload_1600.add(map_attributes_37);


        val body = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                null,Product_1600, map_Pricing, VendorManagementClient.ProductTypeData_5, NewClientName, false, false,
                timeStamp, Vendor103,Vendor103);
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

        jsonArrayPayload_1601.add(map_attributes_1);
        jsonArrayPayload_1601.add(map_attributes_2);
        jsonArrayPayload_1601.add(map_attributes_3);
        jsonArrayPayload_1601.add(map_attributes_15);
        jsonArrayPayload_1601.add(map_attributes_30);
        jsonArrayPayload_1601.add(map_attributes_31);
        jsonArrayPayload_1601.add(map_attributes_33);
        jsonArrayPayload_1601.add(map_attributes_34);
        jsonArrayPayload_1601.add(map_attributes_35);
        jsonArrayPayload_1601.add(map_attributes_36);


        val body_1601 = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp_1601, Vendor103,Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1602 = new Hashtable<>();
        map_1602.put("productId", ProductLookupClient.Product_1602);

        String timeStamp_1602 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1602)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1602);


        val body_1602 = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1602, map_Pricing, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp_1602, Vendor100,Vendor100);
        System.out.println(body_1602);
        PutUpdateProduct(body_1602)
                .then().assertThat().statusCode(SC_OK);

        Map <String, String> map = new Hashtable<>();
        map.put("clientId",Product_1600);
        map.put("targetIdentifier",Identifier_16);

        List<Integer> response2 =  getProductInfo(Port.PRODUCT_LOOKUP, Version.V3, map)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(response2.contains(1600)&response2.contains(1601)&response2.contains(1602))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }

}



