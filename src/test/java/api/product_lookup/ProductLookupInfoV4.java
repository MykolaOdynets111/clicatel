package api.product_lookup;

import api.clients.ProductLookupClient;
import api.clients.ReserveAndTransactClient;
import api.clients.UsersClient;
import api.clients.VendorManagementClient;
import api.enums.Port;
import api.enums.Version;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.testng.annotations.Test;

import java.util.*;

import static api.clients.ProductLookupClient.*;
import static api.clients.ProductLookupInfoV4Client.putProductInfo;
import static api.domains.product_lookup.repo.ProductLookupInfoV4Repo.GetAttributes;
import static api.domains.product_lookup.repo.ProductLookupRequestRepo.setUpPutProductDataWithAttributesWithPurchaseMedium;
import static api.domains.product_lookup.repo.ProductLookupRequestRepo.setUpPutProductDataWithAttributesWithoutPurchaseMedium;
import static org.apache.http.HttpStatus.*;
import static org.testng.AssertJUnit.fail;

public class ProductLookupInfoV4 {
    @Test
    @Description("32000-payd-product-lookup :: PUT \u200B/public\u200B/v4\u200B/productInfo :: check the \"attributesFilter\" param with char value works as \"AND\" query with the another \"attributesFilter\" param with dec value with null value products and \"include\" \"false\" filter")
    @TmsLink("TECH-150503")
    public void testAttributeFilterWithFalseFilterWithDecValue() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes("MB","1",null,null,null,null,null,null,null,null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                "Q33",Product_1600, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp, Vendor103,"1","1","1",Vendor103);
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
        jsonArrayPayload_1601 = GetAttributes("GB","1",null,null,null,null,null,null,null,null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp_1601, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1602 = new Hashtable<>();
        map_1602.put("productId", ProductLookupClient.Product_1602);

        String timeStamp_1602 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1602)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1602);

        List<Map<String,String>> jsonArrayPayload_1602 = new ArrayList<>();
        jsonArrayPayload_1602 = GetAttributes(null,null,null,null,null,null,null,null,null,null);

        val body_1602 = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1602, UsersClient.country, NewClientName, NewClientName,
                null,Product_1602, map_Pricing, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp_1602, Vendor100,Vendor100);
        System.out.println(body_1602);
        PutUpdateProduct(body_1602)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1603 = new Hashtable<>();
        map_1603.put("productId", "1603");

        String timeStamp_1603 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1603)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1603);

        List<Map<String,String>> jsonArrayPayload_1603 = new ArrayList<>();
        jsonArrayPayload_1603 = GetAttributes(null,"1",null,null,null,null,null,null,null,null);

        val body_1603 = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1603, UsersClient.country, NewClientName, NewClientName,
                null,"1603", map_Pricing, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp_1603, Vendor100,Vendor100);
        System.out.println(body_1603);
        PutUpdateProduct(body_1603)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1604 = new Hashtable<>();
        map_1604.put("productId", "1604");

        String timeStamp_1604 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1604)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1604);

        List<Map<String,String>> jsonArrayPayload_1604 = new ArrayList<>();
        jsonArrayPayload_1604 = GetAttributes("mb",null,null,null,null,null,null,null,null,null);

        val body_1604 = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1604, UsersClient.country, NewClientName, NewClientName,
                null,"1604", map_Pricing, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp_1604, Vendor100,Vendor100);
        System.out.println(body_1604);
        PutUpdateProduct(body_1604)
                .then().assertThat().statusCode(SC_OK);

        HashMap<String, String> pair1 = new HashMap<String, String>();
        pair1.put("name", "unit");
        pair1.put("value", "mb");

        HashMap<String, String> pair2 = new HashMap<String, String>();
        pair2.put("name", "quantity");
        pair2.put("value", "1");

        List<Map<String,String>> attributeNameValuePairList = new ArrayList<>();

        attributeNameValuePairList.add(pair1);
        attributeNameValuePairList.add(pair2);

        HashMap<String, Object> attributeNameValuePairListObject = new HashMap<String, Object>();
        attributeNameValuePairListObject.put("attributeNameValuePairList", attributeNameValuePairList);
        attributeNameValuePairListObject.put("include", false);

        Map <String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId",Product_1600);
        queryParams.put("sortBy","unit");
        queryParams.put("sortOrder","asc");

        List<Integer> response2 =  putProductInfo(Port.PRODUCT_LOOKUP, Version.V4,queryParams, attributeNameValuePairListObject)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(!response2.contains(1600)&response2.contains(1601)&response2.contains(1602)&response2.contains(1603)&response2.contains(1604))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("32000-payd-product-lookup :: PUT \u200B/public\u200B/v4\u200B/productInfo :: check the \"attributesFilter\" param with char value works as \"AND\" query with the another \"attributesFilter\" param with null value with null value products and \"include\" \"false\" filter")
    @TmsLink("TECH-150508")
    public void testAttributeFilterWithFalseFilterWithNullValue() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes("MB","1",null,null,null,null,null,null,null,null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                "Q33",Product_1600, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp, Vendor103,"1","1","1",Vendor103);
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
        jsonArrayPayload_1601 = GetAttributes("GB","1",null,null,null,null,null,null,null,null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp_1601, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1602 = new Hashtable<>();
        map_1602.put("productId", ProductLookupClient.Product_1602);

        String timeStamp_1602 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1602)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1602);

        List<Map<String,String>> jsonArrayPayload_1602 = new ArrayList<>();
        jsonArrayPayload_1602 = GetAttributes(null,null,null,null,null,null,null,null,null,null);

        val body_1602 = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1602, UsersClient.country, NewClientName, NewClientName,
                null,Product_1602, map_Pricing, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp_1602, Vendor100,Vendor100);
        System.out.println(body_1602);
        PutUpdateProduct(body_1602)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1603 = new Hashtable<>();
        map_1603.put("productId", "1603");

        String timeStamp_1603 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1603)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1603);

        List<Map<String,String>> jsonArrayPayload_1603 = new ArrayList<>();
        jsonArrayPayload_1603 = GetAttributes(null,"1",null,null,null,null,null,null,null,null);

        val body_1603 = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1603, UsersClient.country, NewClientName, NewClientName,
                null,"1603", map_Pricing, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp_1603, Vendor100,Vendor100);
        System.out.println(body_1603);
        PutUpdateProduct(body_1603)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1604 = new Hashtable<>();
        map_1604.put("productId", "1604");

        String timeStamp_1604 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1604)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1604);

        List<Map<String,String>> jsonArrayPayload_1604 = new ArrayList<>();
        jsonArrayPayload_1604 = GetAttributes("mb",null,null,null,null,null,null,null,null,null);

        val body_1604 = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1604, UsersClient.country, NewClientName, NewClientName,
                null,"1604", map_Pricing, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp_1604, Vendor100,Vendor100);
        System.out.println(body_1604);
        PutUpdateProduct(body_1604)
                .then().assertThat().statusCode(SC_OK);

        HashMap<String, String> pair1 = new HashMap<String, String>();
        pair1.put("name", "unit");
        pair1.put("value", "mb");

        HashMap<String, String> pair2 = new HashMap<String, String>();
        pair2.put("name", "quantity");
        pair2.put("value", "");

        List<Map<String,String>> attributeNameValuePairList = new ArrayList<>();

        attributeNameValuePairList.add(pair1);
        attributeNameValuePairList.add(pair2);

        HashMap<String, Object> attributeNameValuePairListObject = new HashMap<String, Object>();
        attributeNameValuePairListObject.put("attributeNameValuePairList", attributeNameValuePairList);
        attributeNameValuePairListObject.put("include", false);

        Map <String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId",Product_1600);
        queryParams.put("sortBy","unit");
        queryParams.put("sortOrder","asc");

        List<Integer> response2 =  putProductInfo(Port.PRODUCT_LOOKUP, Version.V4,queryParams, attributeNameValuePairListObject)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(response2.contains(1600)&response2.contains(1601)&response2.contains(1602)&response2.contains(1603)&!response2.contains(1604))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("32000-payd-product-lookup :: PUT \u200B/public\u200B/v4\u200B/productInfo :: check the \"attributesFilter\" with null value works as \"AND\" query with the another \"attributesFilter\" with null value with null value products and \"include\" \"false\" filter")
    @TmsLink("TECH-150512")
    public void testAttributeFilterWithNullFilterWithNullValueWithIncludeFalse() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes("MB","1",null,null,null,null,null,null,null,null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                "Q33",Product_1600, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp, Vendor103,"1","1","1",Vendor103);
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
        jsonArrayPayload_1601 = GetAttributes("GB","1",null,null,null,null,null,null,null,null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp_1601, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1602 = new Hashtable<>();
        map_1602.put("productId", ProductLookupClient.Product_1602);

        String timeStamp_1602 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1602)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1602);

        List<Map<String,String>> jsonArrayPayload_1602 = new ArrayList<>();
        jsonArrayPayload_1602 = GetAttributes(null,null,null,null,null,null,null,null,null,null);

        val body_1602 = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1602, UsersClient.country, NewClientName, NewClientName,
                null,Product_1602, map_Pricing, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp_1602, Vendor100,Vendor100);
        System.out.println(body_1602);
        PutUpdateProduct(body_1602)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1603 = new Hashtable<>();
        map_1603.put("productId", "1603");

        String timeStamp_1603 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1603)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1603);

        List<Map<String,String>> jsonArrayPayload_1603 = new ArrayList<>();
        jsonArrayPayload_1603 = GetAttributes(null,"1",null,null,null,null,null,null,null,null);

        val body_1603 = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1603, UsersClient.country, NewClientName, NewClientName,
                null,"1603", map_Pricing, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp_1603, Vendor100,Vendor100);
        System.out.println(body_1603);
        PutUpdateProduct(body_1603)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1604 = new Hashtable<>();
        map_1604.put("productId", "1604");

        String timeStamp_1604 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1604)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1604);

        List<Map<String,String>> jsonArrayPayload_1604 = new ArrayList<>();
        jsonArrayPayload_1604 = GetAttributes("mb",null,null,null,null,null,null,null,null,null);

        val body_1604 = setUpPutProductDataWithAttributesWithoutPurchaseMedium(true, jsonArrayPayload_1604, UsersClient.country, NewClientName, NewClientName,
                null,"1604", map_Pricing, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp_1604, Vendor100,Vendor100);
        System.out.println(body_1604);
        PutUpdateProduct(body_1604)
                .then().assertThat().statusCode(SC_OK);

        HashMap<String, String> pair1 = new HashMap<String, String>();
        pair1.put("name", "unit");
        pair1.put("value", "");

        HashMap<String, String> pair2 = new HashMap<String, String>();
        pair2.put("name", "quantity");
        pair2.put("value", "");

        List<Map<String,String>> attributeNameValuePairList = new ArrayList<>();

        attributeNameValuePairList.add(pair1);
        attributeNameValuePairList.add(pair2);

        HashMap<String, Object> attributeNameValuePairListObject = new HashMap<String, Object>();
        attributeNameValuePairListObject.put("attributeNameValuePairList", attributeNameValuePairList);
        attributeNameValuePairListObject.put("include", false);

        Map <String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId",Product_1600);
        queryParams.put("sortBy","unit");
        queryParams.put("sortOrder","asc");

        List<Integer> response2 =  putProductInfo(Port.PRODUCT_LOOKUP, Version.V4,queryParams, attributeNameValuePairListObject)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(response2.contains(1600)&response2.contains(1601)&!response2.contains(1602)&response2.contains(1603)&response2.contains(1604))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("32000-payd-product-lookup :: PUT \u200B/public\u200B/v4\u200B/productInfo :: check the \"attributesFilter\" param with dec value works as \"AND\" query with the another \"attributesFilter\" param with null value with null value products and \"include\" \"false\" filter")
    @TmsLink("TECH-150610")
    public void testAttributeFilterWithDescFilterWithNullValueWithIncludeFalse() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes("MB","1",null,null,null,null,null,null,null,null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                "Q33",Product_1600, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp, Vendor103,"1","1","1",Vendor103);
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
        jsonArrayPayload_1601 = GetAttributes("GB","2",null,null,null,null,null,null,null,null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1601, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1602 = new Hashtable<>();
        map_1602.put("productId", ProductLookupClient.Product_1602);

        String timeStamp_1602 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1602)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1602);

        List<Map<String,String>> jsonArrayPayload_1602 = new ArrayList<>();
        jsonArrayPayload_1602 = GetAttributes(null,null,null,null,null,null,null,null,null,null);

        val body_1602 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1602, UsersClient.country, NewClientName, NewClientName,
                null,Product_1602, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1602, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1602);
        PutUpdateProduct(body_1602)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1603 = new Hashtable<>();
        map_1603.put("productId", "1603");

        String timeStamp_1603 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1603)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1603);

        List<Map<String,String>> jsonArrayPayload_1603 = new ArrayList<>();
        jsonArrayPayload_1603 = GetAttributes(null,"1",null,null,null,null,null,null,null,null);

        val body_1603 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1603, UsersClient.country, NewClientName, NewClientName,
                null,"1603", map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1603, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1603);
        PutUpdateProduct(body_1603)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1604 = new Hashtable<>();
        map_1604.put("productId", "1604");

        String timeStamp_1604 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1604)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1604);

        List<Map<String,String>> jsonArrayPayload_1604 = new ArrayList<>();
        jsonArrayPayload_1604 = GetAttributes("MB",null,null,null,null,null,null,null,null,null);

        val body_1604 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1604, UsersClient.country, NewClientName, NewClientName,
                null,"1604", map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1604, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1604);
        PutUpdateProduct(body_1604)
                .then().assertThat().statusCode(SC_OK);

        HashMap<String, String> pair1 = new HashMap<String, String>();
        pair1.put("name", "unit");
        pair1.put("value", "");

        HashMap<String, String> pair2 = new HashMap<String, String>();
        pair2.put("name", "quantity");
        pair2.put("value", "1");

        List<Map<String,String>> attributeNameValuePairList = new ArrayList<>();

        attributeNameValuePairList.add(pair1);
        attributeNameValuePairList.add(pair2);

        HashMap<String, Object> attributeNameValuePairListObject = new HashMap<String, Object>();
        attributeNameValuePairListObject.put("attributeNameValuePairList", attributeNameValuePairList);
        attributeNameValuePairListObject.put("include", false);

        Map <String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId",Product_1600);
        queryParams.put("sortBy","unit");
        queryParams.put("sortOrder","asc");

        List<Integer> response2 =  putProductInfo(Port.PRODUCT_LOOKUP, Version.V4,queryParams, attributeNameValuePairListObject)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(!response2.contains(1600)&response2.contains(1601)&!response2.contains(1602)&!response2.contains(1603)&!response2.contains(1604))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("32000-payd-product-lookup :: PUT \u200B/public\u200B/v4\u200B/productInfo :: check the \"attributesFilter\" param with dec value works as \"AND\" query with the another \"attributesFilter\" param with null value with null value products and \"include\" \"false\" filter")
    @TmsLink("TECH-150609")
    public void testAttributeFilterWithDescFilterWithNullValueWithIncludeTrue() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes("MB","1",null,null,null,null,null,null,null,null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                "Q33",Product_1600, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp, Vendor103,"1","1","1",Vendor103);
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
        jsonArrayPayload_1601 = GetAttributes("GB","2",null,null,null,null,null,null,null,null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1601, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1602 = new Hashtable<>();
        map_1602.put("productId", ProductLookupClient.Product_1602);

        String timeStamp_1602 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1602)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1602);

        List<Map<String,String>> jsonArrayPayload_1602 = new ArrayList<>();
        jsonArrayPayload_1602 = GetAttributes(null,null,null,null,null,null,null,null,null,null);

        val body_1602 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1602, UsersClient.country, NewClientName, NewClientName,
                null,Product_1602, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1602, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1602);
        PutUpdateProduct(body_1602)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1603 = new Hashtable<>();
        map_1603.put("productId", "1603");

        String timeStamp_1603 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1603)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1603);

        List<Map<String,String>> jsonArrayPayload_1603 = new ArrayList<>();
        jsonArrayPayload_1603 = GetAttributes(null,"1",null,null,null,null,null,null,null,null);

        val body_1603 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1603, UsersClient.country, NewClientName, NewClientName,
                null,"1603", map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1603, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1603);
        PutUpdateProduct(body_1603)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1604 = new Hashtable<>();
        map_1604.put("productId", "1604");

        String timeStamp_1604 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1604)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1604);

        List<Map<String,String>> jsonArrayPayload_1604 = new ArrayList<>();
        jsonArrayPayload_1604 = GetAttributes("MB",null,null,null,null,null,null,null,null,null);

        val body_1604 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1604, UsersClient.country, NewClientName, NewClientName,
                null,"1604", map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1604, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1604);
        PutUpdateProduct(body_1604)
                .then().assertThat().statusCode(SC_OK);

        HashMap<String, String> pair1 = new HashMap<String, String>();
        pair1.put("name", "unit");
        pair1.put("value", "");

        HashMap<String, String> pair2 = new HashMap<String, String>();
        pair2.put("name", "quantity");
        pair2.put("value", "1");

        List<Map<String,String>> attributeNameValuePairList = new ArrayList<>();

        attributeNameValuePairList.add(pair1);
        attributeNameValuePairList.add(pair2);

        HashMap<String, Object> attributeNameValuePairListObject = new HashMap<String, Object>();
        attributeNameValuePairListObject.put("attributeNameValuePairList", attributeNameValuePairList);
        attributeNameValuePairListObject.put("include", true);

        Map <String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId",Product_1600);
        queryParams.put("sortBy","unit");
        queryParams.put("sortOrder","asc");

        List<Integer> response2 =  putProductInfo(Port.PRODUCT_LOOKUP, Version.V4,queryParams, attributeNameValuePairListObject)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(!response2.contains(1600)&!response2.contains(1601)&!response2.contains(1602)&response2.contains(1603)&!response2.contains(1604))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("32000-payd-product-lookup :: PUT \u200B/public\u200B/v4\u200B/productInfo :: check the \"attributesFilter\" param with char value works as \"AND\" query with the another \"attributesFilter\" param with char value with null value products and \"include\" \"true\" filter")
    @TmsLink("TECH-150607")
    public void testAttributeFilterWithCharFilterWithNullValueWithIncludeTrue() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes("MB",null,"30 days",null,null,null,null,null,null,null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                "Q33",Product_1600, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp, Vendor103,"1","1","1",Vendor103);
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
        jsonArrayPayload_1601 = GetAttributes("GB",null,"30 days",null,null,null,null,null,null,null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1601, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1602 = new Hashtable<>();
        map_1602.put("productId", ProductLookupClient.Product_1602);

        String timeStamp_1602 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1602)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1602);

        List<Map<String,String>> jsonArrayPayload_1602 = new ArrayList<>();
        jsonArrayPayload_1602 = GetAttributes(null,null,null,null,null,null,null,null,null,null);

        val body_1602 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1602, UsersClient.country, NewClientName, NewClientName,
                null,Product_1602, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1602, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1602);
        PutUpdateProduct(body_1602)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1603 = new Hashtable<>();
        map_1603.put("productId", "1603");

        String timeStamp_1603 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1603)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1603);

        List<Map<String,String>> jsonArrayPayload_1603 = new ArrayList<>();
        jsonArrayPayload_1603 = GetAttributes(null,null,"30 days",null,null,null,null,null,null,null);

        val body_1603 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1603, UsersClient.country, NewClientName, NewClientName,
                null,"1603", map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1603, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1603);
        PutUpdateProduct(body_1603)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1604 = new Hashtable<>();
        map_1604.put("productId", "1604");

        String timeStamp_1604 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1604)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1604);

        List<Map<String,String>> jsonArrayPayload_1604 = new ArrayList<>();
        jsonArrayPayload_1604 = GetAttributes("MB",null,null,null,null,null,null,null,null,null);

        val body_1604 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1604, UsersClient.country, NewClientName, NewClientName,
                null,"1604", map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1604, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1604);
        PutUpdateProduct(body_1604)
                .then().assertThat().statusCode(SC_OK);

        HashMap<String, String> pair1 = new HashMap<String, String>();
        pair1.put("name", "unit");
        pair1.put("value", "MB");

        HashMap<String, String> pair2 = new HashMap<String, String>();
        pair2.put("name", "validityPeriod");
        pair2.put("value", "30 days");

        List<Map<String,String>> attributeNameValuePairList = new ArrayList<>();

        attributeNameValuePairList.add(pair1);
        attributeNameValuePairList.add(pair2);

        HashMap<String, Object> attributeNameValuePairListObject = new HashMap<String, Object>();
        attributeNameValuePairListObject.put("attributeNameValuePairList", attributeNameValuePairList);
        attributeNameValuePairListObject.put("include", true);

        Map <String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId",Product_1600);
        queryParams.put("sortBy","unit");
        queryParams.put("sortOrder","asc");

        List<Integer> response2 =  putProductInfo(Port.PRODUCT_LOOKUP, Version.V4,queryParams, attributeNameValuePairListObject)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(response2.contains(1600)&!response2.contains(1601)&!response2.contains(1602)&!response2.contains(1603)&!response2.contains(1604))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("32000-payd-product-lookup :: PUT \u200B/public\u200B/v4\u200B/productInfo :: check the \"attributesFilter\" param with char value works as \"AND\" query with the another \"attributesFilter\" param with char value with null value products and \"include\" \"false\" filter")
    @TmsLink("TECH-150608")
    public void testAttributeFilterWithCharFilterWithNullValueWithIncludeFalse() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes("MB",null,"30 days",null,null,null,null,null,null,null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                "Q33",Product_1600, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp, Vendor103,"1","1","1",Vendor103);
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
        jsonArrayPayload_1601 = GetAttributes("GB",null,"30 days",null,null,null,null,null,null,null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1601, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1602 = new Hashtable<>();
        map_1602.put("productId", ProductLookupClient.Product_1602);

        String timeStamp_1602 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1602)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1602);

        List<Map<String,String>> jsonArrayPayload_1602 = new ArrayList<>();
        jsonArrayPayload_1602 = GetAttributes(null,null,null,null,null,null,null,null,null,null);

        val body_1602 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1602, UsersClient.country, NewClientName, NewClientName,
                null,Product_1602, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1602, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1602);
        PutUpdateProduct(body_1602)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1603 = new Hashtable<>();
        map_1603.put("productId", "1603");

        String timeStamp_1603 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1603)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1603);

        List<Map<String,String>> jsonArrayPayload_1603 = new ArrayList<>();
        jsonArrayPayload_1603 = GetAttributes(null,null,"30 days",null,null,null,null,null,null,null);

        val body_1603 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1603, UsersClient.country, NewClientName, NewClientName,
                null,"1603", map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1603, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1603);
        PutUpdateProduct(body_1603)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1604 = new Hashtable<>();
        map_1604.put("productId", "1604");

        String timeStamp_1604 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1604)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1604);

        List<Map<String,String>> jsonArrayPayload_1604 = new ArrayList<>();
        jsonArrayPayload_1604 = GetAttributes("MB",null,null,null,null,null,null,null,null,null);

        val body_1604 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1604, UsersClient.country, NewClientName, NewClientName,
                null,"1604", map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1604, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1604);
        PutUpdateProduct(body_1604)
                .then().assertThat().statusCode(SC_OK);

        HashMap<String, String> pair1 = new HashMap<String, String>();
        pair1.put("name", "unit");
        pair1.put("value", "MB");

        HashMap<String, String> pair2 = new HashMap<String, String>();
        pair2.put("name", "validityPeriod");
        pair2.put("value", "30 days");

        List<Map<String,String>> attributeNameValuePairList = new ArrayList<>();

        attributeNameValuePairList.add(pair1);
        attributeNameValuePairList.add(pair2);

        HashMap<String, Object> attributeNameValuePairListObject = new HashMap<String, Object>();
        attributeNameValuePairListObject.put("attributeNameValuePairList", attributeNameValuePairList);
        attributeNameValuePairListObject.put("include", false);

        Map <String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId",Product_1600);
        queryParams.put("sortBy","unit");
        queryParams.put("sortOrder","asc");

        List<Integer> response2 =  putProductInfo(Port.PRODUCT_LOOKUP, Version.V4,queryParams, attributeNameValuePairListObject)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(!response2.contains(1600)&response2.contains(1601)&response2.contains(1602)&response2.contains(1603)&response2.contains(1604))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("32000-payd-product-lookup :: PUT \u200B/public\u200B/v4\u200B/productInfo :: check the \"attributesFilter\" param with char value works as \"AND\" query with the another \"attributesFilter\" param with null value with null value products and \"include\" \"false\" filter")
    @TmsLink("TECH-150506")
    public void testAttributeFilterWithCharFilterWithQuantityNullValueWithIncludeFalse() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes("MB","1",null,null,null,null,null,null,null,null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                "Q33",Product_1600, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp, Vendor103,"1","1","1",Vendor103);
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
        jsonArrayPayload_1601 = GetAttributes("GB","1",null,null,null,null,null,null,null,null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1601, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1602 = new Hashtable<>();
        map_1602.put("productId", ProductLookupClient.Product_1602);

        String timeStamp_1602 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1602)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1602);

        List<Map<String,String>> jsonArrayPayload_1602 = new ArrayList<>();
        jsonArrayPayload_1602 = GetAttributes(null,null,null,null,null,null,null,null,null,null);

        val body_1602 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1602, UsersClient.country, NewClientName, NewClientName,
                null,Product_1602, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1602, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1602);
        PutUpdateProduct(body_1602)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1603 = new Hashtable<>();
        map_1603.put("productId", "1603");

        String timeStamp_1603 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1603)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1603);

        List<Map<String,String>> jsonArrayPayload_1603 = new ArrayList<>();
        jsonArrayPayload_1603 = GetAttributes(null,"1",null,null,null,null,null,null,null,null);

        val body_1603 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1603, UsersClient.country, NewClientName, NewClientName,
                null,"1603", map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1603, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1603);
        PutUpdateProduct(body_1603)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1604 = new Hashtable<>();
        map_1604.put("productId", "1604");

        String timeStamp_1604 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1604)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1604);

        List<Map<String,String>> jsonArrayPayload_1604 = new ArrayList<>();
        jsonArrayPayload_1604 = GetAttributes("MB",null,null,null,null,null,null,null,null,null);

        val body_1604 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1604, UsersClient.country, NewClientName, NewClientName,
                null,"1604", map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1604, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1604);
        PutUpdateProduct(body_1604)
                .then().assertThat().statusCode(SC_OK);

        HashMap<String, String> pair1 = new HashMap<String, String>();
        pair1.put("name", "unit");
        pair1.put("value", "MB");

        HashMap<String, String> pair2 = new HashMap<String, String>();
        pair2.put("name", "quantity");
        pair2.put("value", "");

        List<Map<String,String>> attributeNameValuePairList = new ArrayList<>();

        attributeNameValuePairList.add(pair1);
        attributeNameValuePairList.add(pair2);

        HashMap<String, Object> attributeNameValuePairListObject = new HashMap<String, Object>();
        attributeNameValuePairListObject.put("attributeNameValuePairList", attributeNameValuePairList);
        attributeNameValuePairListObject.put("include", true);

        Map <String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId",Product_1600);
        queryParams.put("sortBy","unit");
        queryParams.put("sortOrder","asc");

        List<Integer> response2 =  putProductInfo(Port.PRODUCT_LOOKUP, Version.V4,queryParams, attributeNameValuePairListObject)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(!response2.contains(1600)&!response2.contains(1601)&!response2.contains(1602)&!response2.contains(1603)&response2.contains(1604))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("32000-payd-product-lookup :: PUT \u200B/public\u200B/v4\u200B/productInfo :: check the \"attributesFilter\" param with char value works as \"AND\" query with the another \"attributesFilter\" param with dec value with null value products and \"include\" \"true\" filter")
    @TmsLink("TECH-150409")
    public void testAttributeFilterWithDecFilterWithQuantityNullValueWithIncludeTrue() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes("MB","1",null,null,null,null,null,null,null,null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                "Q33",Product_1600, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp, Vendor103,"1","1","1",Vendor103);
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
        jsonArrayPayload_1601 = GetAttributes("GB","1",null,null,null,null,null,null,null,null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1601, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1602 = new Hashtable<>();
        map_1602.put("productId", ProductLookupClient.Product_1602);

        String timeStamp_1602 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1602)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1602);

        List<Map<String,String>> jsonArrayPayload_1602 = new ArrayList<>();
        jsonArrayPayload_1602 = GetAttributes(null,null,null,null,null,null,null,null,null,null);

        val body_1602 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1602, UsersClient.country, NewClientName, NewClientName,
                null,Product_1602, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1602, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1602);
        PutUpdateProduct(body_1602)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1603 = new Hashtable<>();
        map_1603.put("productId", "1603");

        String timeStamp_1603 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1603)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1603);

        List<Map<String,String>> jsonArrayPayload_1603 = new ArrayList<>();
        jsonArrayPayload_1603 = GetAttributes(null,"1",null,null,null,null,null,null,null,null);

        val body_1603 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1603, UsersClient.country, NewClientName, NewClientName,
                null,"1603", map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1603, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1603);
        PutUpdateProduct(body_1603)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1604 = new Hashtable<>();
        map_1604.put("productId", "1604");

        String timeStamp_1604 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1604)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1604);

        List<Map<String,String>> jsonArrayPayload_1604 = new ArrayList<>();
        jsonArrayPayload_1604 = GetAttributes("MB",null,null,null,null,null,null,null,null,null);

        val body_1604 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1604, UsersClient.country, NewClientName, NewClientName,
                null,"1604", map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1604, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1604);
        PutUpdateProduct(body_1604)
                .then().assertThat().statusCode(SC_OK);

        HashMap<String, String> pair1 = new HashMap<String, String>();
        pair1.put("name", "unit");
        pair1.put("value", "MB");

        HashMap<String, String> pair2 = new HashMap<String, String>();
        pair2.put("name", "quantity");
        pair2.put("value", "1");

        List<Map<String,String>> attributeNameValuePairList = new ArrayList<>();

        attributeNameValuePairList.add(pair1);
        attributeNameValuePairList.add(pair2);

        HashMap<String, Object> attributeNameValuePairListObject = new HashMap<String, Object>();
        attributeNameValuePairListObject.put("attributeNameValuePairList", attributeNameValuePairList);
        attributeNameValuePairListObject.put("include", true);

        Map <String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId",Product_1600);
        queryParams.put("sortBy","unit");
        queryParams.put("sortOrder","asc");

        List<Integer> response2 =  putProductInfo(Port.PRODUCT_LOOKUP, Version.V4,queryParams, attributeNameValuePairListObject)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(response2.contains(1600)&!response2.contains(1601)&!response2.contains(1602)&!response2.contains(1603)&!response2.contains(1604))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("32000-payd-product-lookup :: PUT \u200B/public\u200B/v4\u200B/productInfo :: check the \"attributesFilter\" \"include\": false works as \"AND\" query parameter with all other parameters")
    @TmsLink("TECH-150617")
    public void testAttributeFilterWithAllParametersIncludeFalse() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes("GB",null,null,null,null,null,null,null,null,null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                null,Product_1600, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp, Vendor103,"1","1","1",Vendor103);
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
        jsonArrayPayload_1601 = GetAttributes("MB",null,null,null,null,null,null,null,null,null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1601, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        HashMap<String, String> pair1 = new HashMap<String, String>();
        pair1.put("name", "unit");
        pair1.put("value", "GB");

        HashMap<String, String> pair2 = new HashMap<String, String>();
        pair2.put("name", "quantity");
        pair2.put("value", "1");

        List<Map<String,String>> attributeNameValuePairList = new ArrayList<>();

        attributeNameValuePairList.add(pair1);
        //attributeNameValuePairList.add(pair2);

        HashMap<String, Object> attributeNameValuePairListObject = new HashMap<String, Object>();
        attributeNameValuePairListObject.put("attributeNameValuePairList", attributeNameValuePairList);
        attributeNameValuePairListObject.put("include", false);

        Map <String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId",Product_1600);
        queryParams.put("productTypeId","3");
        queryParams.put("includeInactive","true");
        queryParams.put("topSeller","true");
        queryParams.put("subscriberType","Converged");
        queryParams.put("topSellerPlatform","SMS");
        queryParams.put("purchaseMedium","Card");
        queryParams.put("channelId","2");
        queryParams.put("vendorId","103");

        List<Integer> response2 =  putProductInfo(Port.PRODUCT_LOOKUP, Version.V4,queryParams, attributeNameValuePairListObject)
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
    @Description("32000-payd-product-lookup :: PUT \u200B/public\u200B/v4\u200B/productInfo :: check the \"attributesFilter\" \"include\": false works as \"AND\" query parameter with all other parameters")
    @TmsLink("TECH-150617")
    public void testAttributeFilterWithAllParametersIncludeTrue() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes("GB",null,null,null,null,null,null,null,null,null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                null,Product_1600, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp, Vendor103,"1","1","1",Vendor103);
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
        jsonArrayPayload_1601 = GetAttributes("MB",null,null,null,null,null,null,null,null,null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1601, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        HashMap<String, String> pair1 = new HashMap<String, String>();
        pair1.put("name", "unit");
        pair1.put("value", "GB");

        HashMap<String, String> pair2 = new HashMap<String, String>();
        pair2.put("name", "quantity");
        pair2.put("value", "1");

        List<Map<String,String>> attributeNameValuePairList = new ArrayList<>();

        attributeNameValuePairList.add(pair1);
        //attributeNameValuePairList.add(pair2);

        HashMap<String, Object> attributeNameValuePairListObject = new HashMap<String, Object>();
        attributeNameValuePairListObject.put("attributeNameValuePairList", attributeNameValuePairList);
        attributeNameValuePairListObject.put("include", true);

        Map <String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId",Product_1600);
        queryParams.put("productTypeId","3");
        queryParams.put("includeInactive","true");
        queryParams.put("topSeller","true");
        queryParams.put("subscriberType","Converged");
        queryParams.put("topSellerPlatform","SMS");
        queryParams.put("purchaseMedium","Card");
        queryParams.put("channelId","2");
        queryParams.put("vendorId","103");

        List<Integer> response2 =  putProductInfo(Port.PRODUCT_LOOKUP, Version.V4,queryParams, attributeNameValuePairListObject)
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
    @Description("32000-payd-product-lookup :: PUT \u200B/public\u200B/v4\u200B/productInfo :: check the \"vendorId\" works as \"AND\" query parameter with all other parameters")
    @TmsLink("TECH-138983")
    public void testVendorIdWithAllParameters() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes("GB",null,null,null,null,null,null,null,null,null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                null,Product_1600, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp, Vendor103,"1","1","1",Vendor103);
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
        jsonArrayPayload_1601 = GetAttributes("GB",null,null,null,null,null,null,null,null,null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1601, Vendor100,"1","1","1",Vendor100);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        HashMap<String, String> pair1 = new HashMap<String, String>();
        pair1.put("name", "unit");
        pair1.put("value", "GB");

        HashMap<String, String> pair2 = new HashMap<String, String>();
        pair2.put("name", "quantity");
        pair2.put("value", "1");

        List<Map<String,String>> attributeNameValuePairList = new ArrayList<>();

        attributeNameValuePairList.add(pair1);
        //attributeNameValuePairList.add(pair2);

        HashMap<String, Object> attributeNameValuePairListObject = new HashMap<String, Object>();
        attributeNameValuePairListObject.put("attributeNameValuePairList", attributeNameValuePairList);
        attributeNameValuePairListObject.put("include", true);

        Map <String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId",Product_1600);
        queryParams.put("productTypeId","3");
        queryParams.put("includeInactive","true");
        queryParams.put("topSeller","true");
        queryParams.put("subscriberType","Converged");
        queryParams.put("topSellerPlatform","SMS");
        queryParams.put("purchaseMedium","Card");
        queryParams.put("channelId","2");
        queryParams.put("vendorId","103");

        List<Integer> response2 =  putProductInfo(Port.PRODUCT_LOOKUP, Version.V4,queryParams, attributeNameValuePairListObject)
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
    @Description("32000-payd-product-lookup :: PUT \u200B/public\u200B/v4\u200B/productInfo :: check the \"channelId\" works as \"AND\" query parameter with all other parameters")
    @TmsLink("TECH-138982")
    public void testChannelIdWithAllParameters() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes("GB",null,null,null,null,null,null,null,null,null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                null,Product_1600, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp, Vendor103,"1","1","1",Vendor103);
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
        jsonArrayPayload_1601 = GetAttributes("GB",null,null,null,null,null,null,null,null,null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1601, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        HashMap<String, String> pair1 = new HashMap<String, String>();
        pair1.put("name", "unit");
        pair1.put("value", "GB");

        HashMap<String, String> pair2 = new HashMap<String, String>();
        pair2.put("name", "quantity");
        pair2.put("value", "1");

        List<Map<String,String>> attributeNameValuePairList = new ArrayList<>();

        attributeNameValuePairList.add(pair1);
        //attributeNameValuePairList.add(pair2);

        HashMap<String, Object> attributeNameValuePairListObject = new HashMap<String, Object>();
        attributeNameValuePairListObject.put("attributeNameValuePairList", attributeNameValuePairList);
        attributeNameValuePairListObject.put("include", true);

        Map <String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId",Product_1600);
        queryParams.put("productTypeId","3");
        queryParams.put("includeInactive","true");
        queryParams.put("topSeller","true");
        queryParams.put("subscriberType","Converged");
        queryParams.put("topSellerPlatform","SMS");
        queryParams.put("purchaseMedium","Card");
        queryParams.put("channelId","3");
        queryParams.put("vendorId","103");
        queryParams.put("targetIdentifier","2348085767001");

        List<Integer> response2 =  putProductInfo(Port.PRODUCT_LOOKUP, Version.V4,queryParams, attributeNameValuePairListObject)
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
    @Description("32000-payd-product-lookup :: PUT \u200B/public\u200B/v4\u200B/productInfo :: check the \"purchaseMedium\" works as \"AND\" query parameter with all other parameters")
    @TmsLink("TECH-138981")
    public void testPurchaseMediumWithAllParameters() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes("GB",null,null,null,null,null,null,null,null,null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                null,Product_1600, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp, Vendor103,"1","1","1",Vendor103);
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
        jsonArrayPayload_1601 = GetAttributes("GB",null,null,null,null,null,null,null,null,null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1601, Vendor103,"2","1","1",Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        HashMap<String, String> pair1 = new HashMap<String, String>();
        pair1.put("name", "unit");
        pair1.put("value", "GB");

        HashMap<String, String> pair2 = new HashMap<String, String>();
        pair2.put("name", "quantity");
        pair2.put("value", "1");

        List<Map<String,String>> attributeNameValuePairList = new ArrayList<>();

        attributeNameValuePairList.add(pair1);
        //attributeNameValuePairList.add(pair2);

        HashMap<String, Object> attributeNameValuePairListObject = new HashMap<String, Object>();
        attributeNameValuePairListObject.put("attributeNameValuePairList", attributeNameValuePairList);
        attributeNameValuePairListObject.put("include", true);

        Map <String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId",Product_1600);
        queryParams.put("productTypeId","3");
        queryParams.put("includeInactive","true");
        queryParams.put("topSeller","true");
        queryParams.put("subscriberType","Converged");
        queryParams.put("topSellerPlatform","SMS");
        queryParams.put("purchaseMedium","Card");
        queryParams.put("channelId","2");
        queryParams.put("vendorId","103");
        queryParams.put("targetIdentifier","2348085767001");

        List<Integer> response2 =  putProductInfo(Port.PRODUCT_LOOKUP, Version.V4,queryParams, attributeNameValuePairListObject)
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
    @Description("32000-payd-product-lookup :: PUT \u200B/public\u200B/v4\u200B/productInfo :: check the \"topSellerPlatform\" works as \"AND\" query parameter with all other parameters")
    @TmsLink("TECH-138980")
    public void testTopSellerPlatformWithAllParameters() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes("GB",null,null,null,null,null,null,null,null,null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                null,Product_1600, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp, Vendor103,"1","1","1",Vendor103);
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
        jsonArrayPayload_1601 = GetAttributes("GB",null,null,null,null,null,null,null,null,null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1601, Vendor103,"1","1","2",Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        HashMap<String, String> pair1 = new HashMap<String, String>();
        pair1.put("name", "unit");
        pair1.put("value", "GB");

        HashMap<String, String> pair2 = new HashMap<String, String>();
        pair2.put("name", "quantity");
        pair2.put("value", "1");

        List<Map<String,String>> attributeNameValuePairList = new ArrayList<>();

        attributeNameValuePairList.add(pair1);
        //attributeNameValuePairList.add(pair2);

        HashMap<String, Object> attributeNameValuePairListObject = new HashMap<String, Object>();
        attributeNameValuePairListObject.put("attributeNameValuePairList", attributeNameValuePairList);
        attributeNameValuePairListObject.put("include", true);

        Map <String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId",Product_1600);
        queryParams.put("productTypeId","3");
        queryParams.put("includeInactive","true");
        queryParams.put("topSeller","true");
        queryParams.put("subscriberType","Converged");
        queryParams.put("topSellerPlatform","SMS");
        queryParams.put("purchaseMedium","Card");
        queryParams.put("channelId","2");
        queryParams.put("vendorId","103");
        queryParams.put("targetIdentifier","2348085767001");

        List<Integer> response2 =  putProductInfo(Port.PRODUCT_LOOKUP, Version.V4,queryParams, attributeNameValuePairListObject)
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
    @Description("32000-payd-product-lookup :: PUT \u200B/public\u200B/v4\u200B/productInfo :: check the \"subscriberType\" works as \"AND\" query parameter with all other parameters")
    @TmsLink("TECH-138977")
    public void testSubscriberTypeWithAllParameters() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes("GB",null,null,null,null,null,null,null,null,null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                null,Product_1600, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp, Vendor103,"1","1","1",Vendor103);
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
        jsonArrayPayload_1601 = GetAttributes("GB",null,null,null,null,null,null,null,null,null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1601, Vendor103,"1","2","1",Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        HashMap<String, String> pair1 = new HashMap<String, String>();
        pair1.put("name", "unit");
        pair1.put("value", "GB");

        HashMap<String, String> pair2 = new HashMap<String, String>();
        pair2.put("name", "quantity");
        pair2.put("value", "1");

        List<Map<String,String>> attributeNameValuePairList = new ArrayList<>();

        attributeNameValuePairList.add(pair1);
        //attributeNameValuePairList.add(pair2);

        HashMap<String, Object> attributeNameValuePairListObject = new HashMap<String, Object>();
        attributeNameValuePairListObject.put("attributeNameValuePairList", attributeNameValuePairList);
        attributeNameValuePairListObject.put("include", true);

        Map <String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId",Product_1600);
        queryParams.put("productTypeId","3");
        queryParams.put("includeInactive","true");
        queryParams.put("topSeller","true");
        queryParams.put("subscriberType","Converged");
        queryParams.put("topSellerPlatform","SMS");
        queryParams.put("purchaseMedium","Card");
        queryParams.put("channelId","2");
        queryParams.put("vendorId","103");
        queryParams.put("targetIdentifier","2348085767001");

        List<Integer> response2 =  putProductInfo(Port.PRODUCT_LOOKUP, Version.V4,queryParams, attributeNameValuePairListObject)
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
    @Description("32000-payd-product-lookup :: PUT \u200B/public\u200B/v4\u200B/productInfo :: check the \"topSeller\" works as \"AND\" query parameter with all other parameters")
    @TmsLink("TECH-138976")
    public void testTopSellerWithAllParameters() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes("GB",null,null,null,null,null,null,null,null,null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                null,Product_1600, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp, Vendor103,"1","1","1",Vendor103);
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
        jsonArrayPayload_1601 = GetAttributes("GB",null,null,null,null,null,null,null,null,null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, false,
                timeStamp_1601, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        HashMap<String, String> pair1 = new HashMap<String, String>();
        pair1.put("name", "unit");
        pair1.put("value", "GB");

        HashMap<String, String> pair2 = new HashMap<String, String>();
        pair2.put("name", "quantity");
        pair2.put("value", "1");

        List<Map<String,String>> attributeNameValuePairList = new ArrayList<>();

        attributeNameValuePairList.add(pair1);
        //attributeNameValuePairList.add(pair2);

        HashMap<String, Object> attributeNameValuePairListObject = new HashMap<String, Object>();
        attributeNameValuePairListObject.put("attributeNameValuePairList", attributeNameValuePairList);
        attributeNameValuePairListObject.put("include", true);

        Map <String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId",Product_1600);
        queryParams.put("productTypeId","3");
        queryParams.put("includeInactive","true");
        queryParams.put("topSeller","true");
        queryParams.put("subscriberType","Converged");
        queryParams.put("topSellerPlatform","SMS");
        queryParams.put("purchaseMedium","Card");
        queryParams.put("channelId","2");
        queryParams.put("vendorId","103");
        queryParams.put("targetIdentifier","2348085767001");

        List<Integer> response2 =  putProductInfo(Port.PRODUCT_LOOKUP, Version.V4,queryParams, attributeNameValuePairListObject)
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
    @Description("32000-payd-product-lookup :: PUT \u200B/public\u200B/v4\u200B/productInfo :: check the \"includeInactive\" works as \"AND\" query parameter with all other parameters")
    @TmsLink("TECH-138975")
    public void testIncludeInactiveWithAllParameters() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes("GB",null,null,null,null,null,null,null,null,null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                null,Product_1600, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp, Vendor103,"1","1","1",Vendor103);
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
        jsonArrayPayload_1601 = GetAttributes("GB",null,null,null,null,null,null,null,null,null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(false, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1601, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        HashMap<String, String> pair1 = new HashMap<String, String>();
        pair1.put("name", "unit");
        pair1.put("value", "GB");

        HashMap<String, String> pair2 = new HashMap<String, String>();
        pair2.put("name", "quantity");
        pair2.put("value", "1");

        List<Map<String,String>> attributeNameValuePairList = new ArrayList<>();

        attributeNameValuePairList.add(pair1);
        //attributeNameValuePairList.add(pair2);

        HashMap<String, Object> attributeNameValuePairListObject = new HashMap<String, Object>();
        attributeNameValuePairListObject.put("attributeNameValuePairList", attributeNameValuePairList);
        attributeNameValuePairListObject.put("include", true);

        Map <String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId",Product_1600);
        queryParams.put("productTypeId","3");
        queryParams.put("includeInactive","false");
        queryParams.put("topSeller","true");
        queryParams.put("subscriberType","Converged");
        queryParams.put("topSellerPlatform","SMS");
        queryParams.put("purchaseMedium","Card");
        queryParams.put("channelId","2");
        queryParams.put("vendorId","103");
        queryParams.put("targetIdentifier","2348085767001");

        List<Integer> response2 =  putProductInfo(Port.PRODUCT_LOOKUP, Version.V4,queryParams, attributeNameValuePairListObject)
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
    @Description("32000-payd-product-lookup :: PUT \u200B/public\u200B/v4\u200B/productInfo :: check the \"productTypeId\" works as \"AND\" query parameter with all other parameters")
    @TmsLink("TECH-138973")
    public void testProductTypeIdWithAllParameters() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes("GB",null,null,null,null,null,null,null,null,null,null,null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                "Q33",Product_1600, map_Pricing, VendorManagementClient.ProductTypeData_5, NewClientName, false, true,
                timeStamp, Vendor103,"1","1","1",Vendor103);
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
        jsonArrayPayload_1601 = GetAttributes("GB",null,null,null,null,null,null,null,null,null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1601, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        HashMap<String, String> pair1 = new HashMap<String, String>();
        pair1.put("name", "unit");
        pair1.put("value", "GB");

        HashMap<String, String> pair2 = new HashMap<String, String>();
        pair2.put("name", "quantity");
        pair2.put("value", "1");

        List<Map<String,String>> attributeNameValuePairList = new ArrayList<>();

        attributeNameValuePairList.add(pair1);
        //attributeNameValuePairList.add(pair2);

        HashMap<String, Object> attributeNameValuePairListObject = new HashMap<String, Object>();
        attributeNameValuePairListObject.put("attributeNameValuePairList", attributeNameValuePairList);
        attributeNameValuePairListObject.put("include", true);

        Map <String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId",Product_1600);
        queryParams.put("productTypeId","5");
        queryParams.put("includeInactive","true");
        queryParams.put("topSeller","true");
        queryParams.put("subscriberType","Converged");
        queryParams.put("topSellerPlatform","SMS");
        queryParams.put("purchaseMedium","Card");
        queryParams.put("channelId","2");
        queryParams.put("vendorId","103");
        queryParams.put("targetIdentifier","2348085767001");

        List<Integer> response2 =  putProductInfo(Port.PRODUCT_LOOKUP, Version.V4,queryParams, attributeNameValuePairListObject)
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
    @Description("32000-payd-product-lookup :: PUT \u200B/public\u200B/v4\u200B/productInfo :: check the \"productId\" works as \"AND\" query parameter with all other parameters")
    @TmsLink("TECH-138970")
    public void testProductIdWithAllParameters() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes("GB",null,null,null,null,null,null,null,null,null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                null,Product_1600, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp, Vendor103,"1","1","1",Vendor103);
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
        jsonArrayPayload_1601 = GetAttributes("GB",null,null,null,null,null,null,null,null,null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1601, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        HashMap<String, String> pair1 = new HashMap<String, String>();
        pair1.put("name", "unit");
        pair1.put("value", "GB");

        HashMap<String, String> pair2 = new HashMap<String, String>();
        pair2.put("name", "quantity");
        pair2.put("value", "1");

        List<Map<String,String>> attributeNameValuePairList = new ArrayList<>();

        attributeNameValuePairList.add(pair1);
        //attributeNameValuePairList.add(pair2);

        HashMap<String, Object> attributeNameValuePairListObject = new HashMap<String, Object>();
        attributeNameValuePairListObject.put("attributeNameValuePairList", attributeNameValuePairList);
        attributeNameValuePairListObject.put("include", true);

        Map <String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId",Product_1600);
        queryParams.put("productTypeId","3");
        queryParams.put("includeInactive","true");
        queryParams.put("topSeller","true");
        queryParams.put("subscriberType","Converged");
        queryParams.put("topSellerPlatform","SMS");
        queryParams.put("purchaseMedium","Card");
        queryParams.put("channelId","2");
        queryParams.put("vendorId","103");
        queryParams.put("targetIdentifier","2348085767001");
        queryParams.put("productId",Product_1600);

        List<Integer> response2 =  putProductInfo(Port.PRODUCT_LOOKUP, Version.V4,queryParams, attributeNameValuePairListObject)
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
    @Description("32000-payd-product-lookup :: PUT \u200B/public\u200B/v4\u200B/productInfo :: check the \"targetIdentifier\" works as \"AND\" query parameter with all other parameters")
    @TmsLink("TECH-138967")
    public void testTargetIdentifierWithAllParameters() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes("GB",null,null,null,null,null,null,null,null,null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                "Q33",Product_1600, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp, Vendor103,"1","1","1",Vendor103);
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
        jsonArrayPayload_1601 = GetAttributes("GB",null,null,null,null,null,null,null,null,null,null,null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                "UB31",Product_1601, map_Pricing, VendorManagementClient.ProductTypeData_5, NewClientName, false, true,
                timeStamp_1601, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        HashMap<String, String> pair1 = new HashMap<String, String>();
        pair1.put("name", "unit");
        pair1.put("value", "GB");

        HashMap<String, String> pair2 = new HashMap<String, String>();
        pair2.put("name", "quantity");
        pair2.put("value", "1");

        List<Map<String,String>> attributeNameValuePairList = new ArrayList<>();

        attributeNameValuePairList.add(pair1);
        //attributeNameValuePairList.add(pair2);

        HashMap<String, Object> attributeNameValuePairListObject = new HashMap<String, Object>();
        attributeNameValuePairListObject.put("attributeNameValuePairList", attributeNameValuePairList);
        attributeNameValuePairListObject.put("include", true);

        Map <String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId",Product_1600);
        queryParams.put("productTypeId","3");
        queryParams.put("includeInactive","true");
        queryParams.put("topSeller","true");
        queryParams.put("subscriberType","Converged");
        queryParams.put("topSellerPlatform","SMS");
        queryParams.put("purchaseMedium","Card");
        queryParams.put("channelId","2");
        queryParams.put("vendorId","103");
        queryParams.put("targetIdentifier","2348085767001");

        List<Integer> response2 =  putProductInfo(Port.PRODUCT_LOOKUP, Version.V4,queryParams, attributeNameValuePairListObject)
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
    @Description("32000-payd-product-lookup :: PUT \u200B/public\u200B/v4\u200B/productInfo :: check the \"clientId\" works as \"AND\" query parameter with all other parameters")
    @TmsLink("TECH-138966")
    public void testClientIdWithAllParameters() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes("GB",null,null,null,null,null,null,null,null,null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                "Q33",Product_1600, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp, Vendor103,"1","1","1",Vendor103);
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
        jsonArrayPayload_1601 = GetAttributes("GB",null,null,null,null,null,null,null,null,null,null,null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                "UB31",Product_1601, map_Pricing, VendorManagementClient.ProductTypeData_5, NewClientName, false, true,
                timeStamp_1601, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        HashMap<String, String> pair1 = new HashMap<String, String>();
        pair1.put("name", "unit");
        pair1.put("value", "GB");

        HashMap<String, String> pair2 = new HashMap<String, String>();
        pair2.put("name", "quantity");
        pair2.put("value", "1");

        List<Map<String,String>> attributeNameValuePairList = new ArrayList<>();

        attributeNameValuePairList.add(pair1);
        //attributeNameValuePairList.add(pair2);

        HashMap<String, Object> attributeNameValuePairListObject = new HashMap<String, Object>();
        attributeNameValuePairListObject.put("attributeNameValuePairList", attributeNameValuePairList);
        attributeNameValuePairListObject.put("include", true);

        Map <String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId",Product_1601);
        queryParams.put("productTypeId","3");
        queryParams.put("includeInactive","true");
        queryParams.put("topSeller","true");
        queryParams.put("subscriberType","Converged");
        queryParams.put("topSellerPlatform","SMS");
        queryParams.put("purchaseMedium","Card");
        queryParams.put("channelId","2");
        queryParams.put("vendorId","103");

        List<Integer> response2 =  putProductInfo(Port.PRODUCT_LOOKUP, Version.V4,queryParams, attributeNameValuePairListObject)
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
    @Description("32000-payd-product-lookup :: PUT \u200B/public\u200B/v4\u200B/productInfo :: check the \"attributesFilter\" with valid value works as \"OR\" query with the same \"attributesFilter\" param with valid value and \"include\" filter is set to \"false\"")
    @TmsLink("TECH-150570")
    public void testAttributeFilterValidValueWithParamWithValidIncludeFalse() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes("MB","1",null,null,null,null,null,null,null,null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                "Q33",Product_1600, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp, Vendor103,"1","1","1",Vendor103);
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
        jsonArrayPayload_1601 = GetAttributes("GB","1",null,null,null,null,null,null,null,null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1601, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1602 = new Hashtable<>();
        map_1602.put("productId", ProductLookupClient.Product_1602);

        String timeStamp_1602 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1602)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1602);

        List<Map<String,String>> jsonArrayPayload_1602 = new ArrayList<>();
        jsonArrayPayload_1602 = GetAttributes(null,null,null,null,null,null,null,null,null,null);

        val body_1602 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1602, UsersClient.country, NewClientName, NewClientName,
                null,Product_1602, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1602, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1602);
        PutUpdateProduct(body_1602)
                .then().assertThat().statusCode(SC_OK);

        HashMap<String, String> pair1 = new HashMap<String, String>();
        pair1.put("name", "unit");
        pair1.put("value", "mb");

        HashMap<String, String> pair2 = new HashMap<String, String>();
        pair2.put("name", "unit");
        pair2.put("value", "gb");

        List<Map<String,String>> attributeNameValuePairList = new ArrayList<>();

        attributeNameValuePairList.add(pair1);
        attributeNameValuePairList.add(pair2);

        HashMap<String, Object> attributeNameValuePairListObject = new HashMap<String, Object>();
        attributeNameValuePairListObject.put("attributeNameValuePairList", attributeNameValuePairList);
        attributeNameValuePairListObject.put("include", false);

        Map <String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId",Product_1600);
        queryParams.put("sortBy","unit");
        queryParams.put("sortOrder","asc");
        queryParams.put("vendorId","103");
        queryParams.put("topSeller","true");
        queryParams.put("subscriberType","Converged");
        queryParams.put("topSellerPlatform","SMS");
        queryParams.put("purchaseMedium","Card");
        queryParams.put("channelId","2");
        queryParams.put("productTypeId","3");

        List<Integer> response2 =  putProductInfo(Port.PRODUCT_LOOKUP, Version.V4,queryParams, attributeNameValuePairListObject)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(!response2.contains(1600)&!response2.contains(1601)&response2.contains(1602))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("32000-payd-product-lookup :: PUT \u200B/public\u200B/v4\u200B/productInfo :: check the \"attributesFilter\" with valid value works as \"OR\" query with the same \"attributesFilter\" param with valid value and \"include\" filter is set to \"true\"")
    @TmsLink("TECH-150571")
    public void testAttributeFilterValidValueWithParamWithValidIncludeTrue() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes("MB","1",null,null,null,null,null,null,null,null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                "Q33",Product_1600, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp, Vendor103,"1","1","1",Vendor103);
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
        jsonArrayPayload_1601 = GetAttributes("GB","1",null,null,null,null,null,null,null,null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1601, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1602 = new Hashtable<>();
        map_1602.put("productId", ProductLookupClient.Product_1602);

        String timeStamp_1602 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1602)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1602);

        List<Map<String,String>> jsonArrayPayload_1602 = new ArrayList<>();
        jsonArrayPayload_1602 = GetAttributes(null,null,null,null,null,null,null,null,null,null);

        val body_1602 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1602, UsersClient.country, NewClientName, NewClientName,
                null,Product_1602, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1602, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1602);
        PutUpdateProduct(body_1602)
                .then().assertThat().statusCode(SC_OK);

        HashMap<String, String> pair1 = new HashMap<String, String>();
        pair1.put("name", "unit");
        pair1.put("value", "mb");

        HashMap<String, String> pair2 = new HashMap<String, String>();
        pair2.put("name", "unit");
        pair2.put("value", "gb");

        List<Map<String,String>> attributeNameValuePairList = new ArrayList<>();

        attributeNameValuePairList.add(pair1);
        attributeNameValuePairList.add(pair2);

        HashMap<String, Object> attributeNameValuePairListObject = new HashMap<String, Object>();
        attributeNameValuePairListObject.put("attributeNameValuePairList", attributeNameValuePairList);
        attributeNameValuePairListObject.put("include", true);

        Map <String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId",Product_1600);
        queryParams.put("sortBy","unit");
        queryParams.put("sortOrder","asc");
        queryParams.put("vendorId","103");
        queryParams.put("topSeller","true");
        queryParams.put("subscriberType","Converged");
        queryParams.put("topSellerPlatform","SMS");
        queryParams.put("purchaseMedium","Card");
        queryParams.put("channelId","2");
        queryParams.put("productTypeId","3");

        List<Integer> response2 =  putProductInfo(Port.PRODUCT_LOOKUP, Version.V4,queryParams, attributeNameValuePairListObject)
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
    @Description("32000-payd-product-lookup :: PUT \u200B/public\u200B/v4\u200B/productInfo :: check the \"attributesFilter\" with valid value works as \"OR\" query with the same \"attributesFilter\" param with null value and \"include\" filter is set to \"true\"")
    @TmsLink("TECH-150405")
    public void testAttributeFilterValidValueWithParamWithNullIncludeTrue() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes("MB","1",null,null,null,null,null,null,null,null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                "Q33",Product_1600, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp, Vendor103,"1","1","1",Vendor103);
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
        jsonArrayPayload_1601 = GetAttributes("GB","1",null,null,null,null,null,null,null,null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1601, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1602 = new Hashtable<>();
        map_1602.put("productId", ProductLookupClient.Product_1602);

        String timeStamp_1602 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1602)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1602);

        List<Map<String,String>> jsonArrayPayload_1602 = new ArrayList<>();
        jsonArrayPayload_1602 = GetAttributes(null,null,null,null,null,null,null,null,null,null);

        val body_1602 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1602, UsersClient.country, NewClientName, NewClientName,
                null,Product_1602, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1602, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1602);
        PutUpdateProduct(body_1602)
                .then().assertThat().statusCode(SC_OK);

        HashMap<String, String> pair1 = new HashMap<String, String>();
        pair1.put("name", "unit");
        pair1.put("value", "mb");

        HashMap<String, String> pair2 = new HashMap<String, String>();
        pair2.put("name", "unit");
        pair2.put("value", "");

        List<Map<String,String>> attributeNameValuePairList = new ArrayList<>();

        attributeNameValuePairList.add(pair1);
        attributeNameValuePairList.add(pair2);

        HashMap<String, Object> attributeNameValuePairListObject = new HashMap<String, Object>();
        attributeNameValuePairListObject.put("attributeNameValuePairList", attributeNameValuePairList);
        attributeNameValuePairListObject.put("include", true);

        Map <String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId",Product_1600);
        queryParams.put("sortBy","unit");
        queryParams.put("sortOrder","asc");
        queryParams.put("vendorId","103");
        queryParams.put("topSeller","true");
        queryParams.put("subscriberType","Converged");
        queryParams.put("topSellerPlatform","SMS");
        queryParams.put("purchaseMedium","Card");
        queryParams.put("channelId","2");
        queryParams.put("productTypeId","3");

        List<Integer> response2 =  putProductInfo(Port.PRODUCT_LOOKUP, Version.V4,queryParams, attributeNameValuePairListObject)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(response2.contains(1600)&!response2.contains(1601)&response2.contains(1602))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("32000-payd-product-lookup :: PUT \u200B/public\u200B/v4\u200B/productInfo :: check the \"attributesFilter\" with valid value works as \"OR\" query with the same \"attributesFilter\" param with null value and \"include\" filter is set to \"false\"")
    @TmsLink("TECH-150407")
    public void testAttributeFilterValidValueWithParamWithNullIncludeFalse() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes("MB","1",null,null,null,null,null,null,null,null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                "Q33",Product_1600, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp, Vendor103,"1","1","1",Vendor103);
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
        jsonArrayPayload_1601 = GetAttributes("GB","1",null,null,null,null,null,null,null,null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1601, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1602 = new Hashtable<>();
        map_1602.put("productId", ProductLookupClient.Product_1602);

        String timeStamp_1602 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1602)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1602);

        List<Map<String,String>> jsonArrayPayload_1602 = new ArrayList<>();
        jsonArrayPayload_1602 = GetAttributes(null,null,null,null,null,null,null,null,null,null);

        val body_1602 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1602, UsersClient.country, NewClientName, NewClientName,
                null,Product_1602, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1602, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1602);
        PutUpdateProduct(body_1602)
                .then().assertThat().statusCode(SC_OK);

        HashMap<String, String> pair1 = new HashMap<String, String>();
        pair1.put("name", "unit");
        pair1.put("value", "mb");

        HashMap<String, String> pair2 = new HashMap<String, String>();
        pair2.put("name", "unit");
        pair2.put("value", "");

        List<Map<String,String>> attributeNameValuePairList = new ArrayList<>();

        attributeNameValuePairList.add(pair1);
        attributeNameValuePairList.add(pair2);

        HashMap<String, Object> attributeNameValuePairListObject = new HashMap<String, Object>();
        attributeNameValuePairListObject.put("attributeNameValuePairList", attributeNameValuePairList);
        attributeNameValuePairListObject.put("include", false);

        Map <String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId",Product_1600);
        queryParams.put("sortBy","unit");
        queryParams.put("sortOrder","asc");
        queryParams.put("vendorId","103");
        queryParams.put("topSeller","true");
        queryParams.put("subscriberType","Converged");
        queryParams.put("topSellerPlatform","SMS");
        queryParams.put("purchaseMedium","Card");
        queryParams.put("channelId","2");
        queryParams.put("productTypeId","3");

        List<Integer> response2 =  putProductInfo(Port.PRODUCT_LOOKUP, Version.V4,queryParams, attributeNameValuePairListObject)
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
    @Description("32000-payd-product-lookup :: PUT \u200B/public\u200B/v4\u200B/productInfo :: happy path")
    @TmsLink("TECH-139376")
    public void testProductInfoV4HappyPath() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes("MB","1",null,null,null,null,null,null,null,null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                "Q33",Product_1600, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp, Vendor103,"1","1","1",Vendor103);
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
        jsonArrayPayload_1601 = GetAttributes("GB","1",null,null,null,null,null,null,null,null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1601, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1602 = new Hashtable<>();
        map_1602.put("productId", ProductLookupClient.Product_1602);

        String timeStamp_1602 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1602)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1602);

        List<Map<String,String>> jsonArrayPayload_1602 = new ArrayList<>();
        jsonArrayPayload_1602 = GetAttributes(null,null,null,null,null,null,null,null,null,null);

        val body_1602 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1602, UsersClient.country, NewClientName, NewClientName,
                null,Product_1602, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1602, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1602);
        PutUpdateProduct(body_1602)
                .then().assertThat().statusCode(SC_OK);

        HashMap<String, String> pair1 = new HashMap<String, String>();
        pair1.put("name", "unit");
        pair1.put("value", "");

        HashMap<String, String> pair2 = new HashMap<String, String>();
        pair2.put("name", "unit");
        pair2.put("value", "gb");

        HashMap<String, String> pair3 = new HashMap<String, String>();
        pair3.put("name", "quantity");
        pair3.put("value", "1");

        HashMap<String, String> pair4 = new HashMap<String, String>();
        pair4.put("name", "quantity");
        pair4.put("value", "");

        List<Map<String,String>> attributeNameValuePairList = new ArrayList<>();

        attributeNameValuePairList.add(pair1);
        attributeNameValuePairList.add(pair2);
        attributeNameValuePairList.add(pair3);
        attributeNameValuePairList.add(pair4);

        HashMap<String, Object> attributeNameValuePairListObject = new HashMap<String, Object>();
        attributeNameValuePairListObject.put("attributeNameValuePairList", attributeNameValuePairList);
        attributeNameValuePairListObject.put("include", true);

        Map <String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId",Product_1600);
        queryParams.put("sortBy","unit");
        queryParams.put("sortOrder","asc");
        queryParams.put("vendorId","103");
        queryParams.put("topSeller","true");
        queryParams.put("subscriberType","Converged");
        queryParams.put("topSellerPlatform","SMS");
        queryParams.put("purchaseMedium","Card");
        queryParams.put("channelId","2");
        queryParams.put("productTypeId","3");

        List<Integer> response2 =  putProductInfo(Port.PRODUCT_LOOKUP, Version.V4,queryParams, attributeNameValuePairListObject)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(!response2.contains(1600)&response2.contains(1601)&response2.contains(1602))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("32000-payd-product-lookup :: PUT /public/v4/productInfo :: if invalid \"attributesFilter\" parameter is specified then none of the products are returned in the response")
    @TmsLink("TECH-138963")
    public void testAttributeFilterValidValueWithInvalidParamNoResponse() {
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
        jsonArrayPayload_1600 = GetAttributes("MB", "1", null, null, null, null, null, null, null, null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                "Q33", Product_1600, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
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

        List<Map<String, String>> jsonArrayPayload_1601 = new ArrayList<>();
        jsonArrayPayload_1601 = GetAttributes("GB", "1", null, null, null, null, null, null, null, null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null, Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1601, Vendor103, "1", "1", "1", Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1602 = new Hashtable<>();
        map_1602.put("productId", ProductLookupClient.Product_1602);

        String timeStamp_1602 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1602)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1602);

        List<Map<String, String>> jsonArrayPayload_1602 = new ArrayList<>();
        jsonArrayPayload_1602 = GetAttributes(null, null, null, null, null, null, null, null, null, null);

        val body_1602 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1602, UsersClient.country, NewClientName, NewClientName,
                null, Product_1602, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1602, Vendor103,"1","1","1", Vendor103);
        System.out.println(body_1602);
        PutUpdateProduct(body_1602)
                .then().assertThat().statusCode(SC_OK);

        HashMap<String, String> pair1 = new HashMap<String, String>();
        pair1.put("name", "unit");
        pair1.put("value", "byte");

        HashMap<String, String> pair2 = new HashMap<String, String>();
        pair2.put("name", "unit");
        pair2.put("value", "");

        List<Map<String, String>> attributeNameValuePairList = new ArrayList<>();

        attributeNameValuePairList.add(pair1);
//        attributeNameValuePairList.add(pair2);

        HashMap<String, Object> attributeNameValuePairListObject = new HashMap<String, Object>();
        attributeNameValuePairListObject.put("attributeNameValuePairList", attributeNameValuePairList);
        attributeNameValuePairListObject.put("include", true);

        Map<String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId", Product_1600);
        queryParams.put("sortBy", "unit");
        queryParams.put("sortOrder", "asc");

        putProductInfo(Port.PRODUCT_LOOKUP, Version.V4, queryParams, attributeNameValuePairListObject)
                .then().assertThat().statusCode(SC_NO_CONTENT);
    }
    @Test
    @Description("32000-payd-product-lookup :: PUT /public/v4/productInfo :: if \"attributesFilter\" isn't specified then all the products are returned in the response")
    @TmsLink("TECH-138327")
    public void testAttributeFilterValidValueWithAttributeParamsNotSpecifiedAllProducts() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes("MB","1",null,null,null,null,null,null,null,null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                "Q33",Product_1600, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp, Vendor103,"1","1","1",Vendor103);
        System.out.println(body);
        PutUpdateProduct(body)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1602 = new Hashtable<>();
        map_1602.put("productId", ProductLookupClient.Product_1602);

        String timeStamp_1602 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1602)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1602);

        List<Map<String,String>> jsonArrayPayload_1602 = new ArrayList<>();
        jsonArrayPayload_1602 = GetAttributes(null,null,null,null,null,null,null,null,null,null);

        val body_1602 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1602, UsersClient.country, NewClientName, NewClientName,
                null,Product_1602, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1602, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1602);
        PutUpdateProduct(body_1602)
                .then().assertThat().statusCode(SC_OK);

        Map <String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId",Product_1600);
        queryParams.put("sortBy","unit");
        queryParams.put("sortOrder","asc");

        List<Integer> response2 =  putProductInfo(Port.PRODUCT_LOOKUP, Version.V4,queryParams)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(response2.contains(1600)&response2.contains(1602))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("32000-payd-product-lookup :: PUT \u200B/public\u200B/v4\u200B/productInfo :: if the \"include\" parameter is set to \"false\" then all the \"attributeNameValuePairList\" parameters works as the \"NOT IN\" query with null values")
    @TmsLink("TECH-150304")
    public void testIncludeFalseAttributeNameValuePairListNotInQueryWithNullValues() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes("MB","1",null,null,null,null,null,null,null,null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                "Q33",Product_1600, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp, Vendor103,"1","1","1",Vendor103);
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
        jsonArrayPayload_1601 = GetAttributes("GB","1",null,null,null,null,null,null,null,null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1601, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1602 = new Hashtable<>();
        map_1602.put("productId", ProductLookupClient.Product_1602);

        String timeStamp_1602 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1602)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1602);

        List<Map<String,String>> jsonArrayPayload_1602 = new ArrayList<>();
        jsonArrayPayload_1602 = GetAttributes(null,null,null,null,null,null,null,null,null,null);

        val body_1602 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1602, UsersClient.country, NewClientName, NewClientName,
                null,Product_1602, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1602, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1602);
        PutUpdateProduct(body_1602)
                .then().assertThat().statusCode(SC_OK);

        HashMap<String, String> pair1 = new HashMap<String, String>();
        pair1.put("name", "unit");
        pair1.put("value", "mb");

        HashMap<String, String> pair2 = new HashMap<String, String>();
        pair2.put("name", "unit");
        pair2.put("value", "");

        List<Map<String,String>> attributeNameValuePairList = new ArrayList<>();

//        attributeNameValuePairList.add(pair1);
        attributeNameValuePairList.add(pair2);

        HashMap<String, Object> attributeNameValuePairListObject = new HashMap<String, Object>();
        attributeNameValuePairListObject.put("attributeNameValuePairList", attributeNameValuePairList);
        attributeNameValuePairListObject.put("include", false);

        Map <String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId",Product_1600);
        queryParams.put("sortBy","unit");
        queryParams.put("sortOrder","asc");

        List<Integer> response2 =  putProductInfo(Port.PRODUCT_LOOKUP, Version.V4,queryParams, attributeNameValuePairListObject)
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
    @Description("32000-payd-product-lookup :: PUT \u200B/public\u200B/v4\u200B/productInfo :: if the \"include\" parameter is set to \"true\" then all the \"attributeNameValuePairList\" parameters works as the \"AND\" query with null values")
    @TmsLink("TECH-150305")
    public void testIncludeTrueAttributeNameValuePairListAndQueryWithNullValues() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes("MB","1",null,null,null,null,null,null,null,null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                "Q33",Product_1600, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp, Vendor103,"1","1","1",Vendor103);
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
        jsonArrayPayload_1601 = GetAttributes("GB","1",null,null,null,null,null,null,null,null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1601, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1602 = new Hashtable<>();
        map_1602.put("productId", ProductLookupClient.Product_1602);

        String timeStamp_1602 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1602)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1602);

        List<Map<String,String>> jsonArrayPayload_1602 = new ArrayList<>();
        jsonArrayPayload_1602 = GetAttributes(null,null,null,null,null,null,null,null,null,null);

        val body_1602 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1602, UsersClient.country, NewClientName, NewClientName,
                null,Product_1602, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1602, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1602);
        PutUpdateProduct(body_1602)
                .then().assertThat().statusCode(SC_OK);

        HashMap<String, String> pair1 = new HashMap<String, String>();
        pair1.put("name", "unit");
        pair1.put("value", "mb");

        HashMap<String, String> pair2 = new HashMap<String, String>();
        pair2.put("name", "unit");
        pair2.put("value", "");

        List<Map<String,String>> attributeNameValuePairList = new ArrayList<>();

//        attributeNameValuePairList.add(pair1);
        attributeNameValuePairList.add(pair2);

        HashMap<String, Object> attributeNameValuePairListObject = new HashMap<String, Object>();
        attributeNameValuePairListObject.put("attributeNameValuePairList", attributeNameValuePairList);
        attributeNameValuePairListObject.put("include", true);

        Map <String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId",Product_1600);
        queryParams.put("sortBy","unit");
        queryParams.put("sortOrder","asc");
        queryParams.put("vendorId","103");
        queryParams.put("topSeller","true");
        queryParams.put("subscriberType","Converged");
        queryParams.put("topSellerPlatform","SMS");
        queryParams.put("purchaseMedium","Card");
        queryParams.put("channelId","2");
        queryParams.put("productTypeId","3");

        List<Integer> response2 =  putProductInfo(Port.PRODUCT_LOOKUP, Version.V4,queryParams, attributeNameValuePairListObject)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(!response2.contains(1600)&!response2.contains(1601)&response2.contains(1602))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("32000-payd-product-lookup :: PUT \u200B/public\u200B/v4\u200B/productInfo :: if the \"include\" parameter is set to \"true\" then all the \"attributeNameValuePairList\" parameters works as the \"AND\" query with valid values")
    @TmsLink("TECH-150302")
    public void testIncludeTrueAttributeNameValuePairListAndQueryWithValidValues() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes("MB","1",null,null,null,null,null,null,null,null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                "Q33",Product_1600, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp, Vendor103,"1","1","1",Vendor103);
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
        jsonArrayPayload_1601 = GetAttributes("GB","1",null,null,null,null,null,null,null,null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1601, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1602 = new Hashtable<>();
        map_1602.put("productId", ProductLookupClient.Product_1602);

        String timeStamp_1602 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1602)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1602);

        List<Map<String,String>> jsonArrayPayload_1602 = new ArrayList<>();
        jsonArrayPayload_1602 = GetAttributes(null,null,null,null,null,null,null,null,null,null);

        val body_1602 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1602, UsersClient.country, NewClientName, NewClientName,
                null,Product_1602, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1602, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1602);
        PutUpdateProduct(body_1602)
                .then().assertThat().statusCode(SC_OK);

        HashMap<String, String> pair1 = new HashMap<String, String>();
        pair1.put("name", "unit");
        pair1.put("value", "mb");

        HashMap<String, String> pair2 = new HashMap<String, String>();
        pair2.put("name", "unit");
        pair2.put("value", "MB");

        List<Map<String,String>> attributeNameValuePairList = new ArrayList<>();

//        attributeNameValuePairList.add(pair1);
        attributeNameValuePairList.add(pair2);

        HashMap<String, Object> attributeNameValuePairListObject = new HashMap<String, Object>();
        attributeNameValuePairListObject.put("attributeNameValuePairList", attributeNameValuePairList);
        attributeNameValuePairListObject.put("include", true);

        Map <String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId",Product_1600);
        queryParams.put("sortBy","unit");
        queryParams.put("sortOrder","asc");
        queryParams.put("vendorId","103");
        queryParams.put("topSeller","true");
        queryParams.put("subscriberType","Converged");
        queryParams.put("topSellerPlatform","SMS");
        queryParams.put("purchaseMedium","Card");
        queryParams.put("channelId","2");
        queryParams.put("productTypeId","3");

        List<Integer> response2 =  putProductInfo(Port.PRODUCT_LOOKUP, Version.V4,queryParams, attributeNameValuePairListObject)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(response2.contains(1600)&!response2.contains(1601)&!response2.contains(1602))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("32000-payd-product-lookup :: PUT \u200B/public\u200B/v4\u200B/productInfo :: if the \"include\" parameter is set to \"false\" then all the \"attributeNameValuePairList\" parameters works as the \"NOT IN\" query with valid values")
    @TmsLink("TECH-150303")
    public void testIncludeFalseAttributeNameValuePairListNotInQueryWithValidValues() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes("MB","1",null,null,null,null,null,null,null,null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                "Q33",Product_1600, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp, Vendor103,"1","1","1",Vendor103);
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
        jsonArrayPayload_1601 = GetAttributes("GB","1",null,null,null,null,null,null,null,null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1601, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1602 = new Hashtable<>();
        map_1602.put("productId", ProductLookupClient.Product_1602);

        String timeStamp_1602 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1602)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1602);

        List<Map<String,String>> jsonArrayPayload_1602 = new ArrayList<>();
        jsonArrayPayload_1602 = GetAttributes(null,null,null,null,null,null,null,null,null,null);

        val body_1602 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1602, UsersClient.country, NewClientName, NewClientName,
                null,Product_1602, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1602, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1602);
        PutUpdateProduct(body_1602)
                .then().assertThat().statusCode(SC_OK);

        HashMap<String, String> pair1 = new HashMap<String, String>();
        pair1.put("name", "unit");
        pair1.put("value", "mb");

        HashMap<String, String> pair2 = new HashMap<String, String>();
        pair2.put("name", "unit");
        pair2.put("value", "MB");

        List<Map<String,String>> attributeNameValuePairList = new ArrayList<>();

//        attributeNameValuePairList.add(pair1);
        attributeNameValuePairList.add(pair2);

        HashMap<String, Object> attributeNameValuePairListObject = new HashMap<String, Object>();
        attributeNameValuePairListObject.put("attributeNameValuePairList", attributeNameValuePairList);
        attributeNameValuePairListObject.put("include", false);

        Map <String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId",Product_1600);
        queryParams.put("sortBy","unit");
        queryParams.put("sortOrder","asc");
        queryParams.put("vendorId","103");
        queryParams.put("topSeller","true");
        queryParams.put("subscriberType","Converged");
        queryParams.put("topSellerPlatform","SMS");
        queryParams.put("purchaseMedium","Card");
        queryParams.put("channelId","2");
        queryParams.put("productTypeId","3");

        List<Integer> response2 =  putProductInfo(Port.PRODUCT_LOOKUP, Version.V4,queryParams, attributeNameValuePairListObject)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(!response2.contains(1600)&response2.contains(1601)&response2.contains(1602))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("32000-payd-product-lookup :: PUT \u200B/public\u200B/v4\u200B/productInfo :: check the \"attributesFilter\" with null value works as \"AND\" query with the another \"attributesFilter\" with null value with null value products and \"include\" \"true\" filter")
    @TmsLink("TECH-150511")
    public void testIncludeTrueAttributeNameValueNullPairListAndQueryWithNullValues() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes("MB","1",null,null,null,null,null,null,null,null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                "Q33",Product_1600, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp, Vendor103,"1","1","1",Vendor103);
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
        jsonArrayPayload_1601 = GetAttributes("GB","1",null,null,null,null,null,null,null,null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1601, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1602 = new Hashtable<>();
        map_1602.put("productId", ProductLookupClient.Product_1602);

        String timeStamp_1602 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1602)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1602);

        List<Map<String,String>> jsonArrayPayload_1602 = new ArrayList<>();
        jsonArrayPayload_1602 = GetAttributes(null,null,null,null,null,null,null,null,null,null);

        val body_1602 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1602, UsersClient.country, NewClientName, NewClientName,
                null,Product_1602, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1602, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1602);
        PutUpdateProduct(body_1602)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1603 = new Hashtable<>();
        map_1603.put("productId", "1603");

        String timeStamp_1603 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1603)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1603);

        List<Map<String,String>> jsonArrayPayload_1603 = new ArrayList<>();
        jsonArrayPayload_1603 = GetAttributes(null,"1",null,null,null,null,null,null,null,null);

        val body_1603 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1603, UsersClient.country, NewClientName, NewClientName,
                null,"1603", map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1603, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1603);
        PutUpdateProduct(body_1603)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1604 = new Hashtable<>();
        map_1604.put("productId", "1604");

        String timeStamp_1604 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1604)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1604);

        List<Map<String,String>> jsonArrayPayload_1604 = new ArrayList<>();
        jsonArrayPayload_1604 = GetAttributes("mb",null,null,null,null,null,null,null,null,null);

        val body_1604 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1604, UsersClient.country, NewClientName, NewClientName,
                null,"1604", map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1604, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1604);
        PutUpdateProduct(body_1604)
                .then().assertThat().statusCode(SC_OK);

        HashMap<String, String> pair1 = new HashMap<String, String>();
        pair1.put("name", "quantity");
        pair1.put("value", "");

        HashMap<String, String> pair2 = new HashMap<String, String>();
        pair2.put("name", "unit");
        pair2.put("value", "");

        List<Map<String,String>> attributeNameValuePairList = new ArrayList<>();

        attributeNameValuePairList.add(pair1);
        attributeNameValuePairList.add(pair2);

        HashMap<String, Object> attributeNameValuePairListObject = new HashMap<String, Object>();
        attributeNameValuePairListObject.put("attributeNameValuePairList", attributeNameValuePairList);
        attributeNameValuePairListObject.put("include", true);

        Map <String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId",Product_1600);
        queryParams.put("sortBy","unit");
        queryParams.put("sortOrder","asc");

        List<Integer> response2 =  putProductInfo(Port.PRODUCT_LOOKUP, Version.V4,queryParams, attributeNameValuePairListObject)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("publicProduct.id");
        System.out.println(response2);

        if(!response2.contains(1600)&!response2.contains(1601)&response2.contains(1602)&!response2.contains(1603)&!response2.contains(1604))
        {
            System.out.println("Only expected products are being appeared");
        }
        else
        {
            fail("Expected products do not exist");
        }
    }
    @Test
    @Description("32000-payd-product-lookup :: PUT \u200B/public\u200B/v4\u200B/productInfo :: check the \"attributesFilter\" works as \"AND\" query parameter with all other parameters")
    @TmsLink("TECH-138331")
    public void testAttributeFiltersAndQueryParametersWithAllParameters() {
        Map<String, String> map_1600 = new Hashtable<>();
        map_1600.put("productId", ProductLookupClient.Product_1600);

        Map<String, String> map_Pricing = new Hashtable<>();
        map_Pricing.put("type", ProductType_Single);
        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);

        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp);

        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();
        jsonArrayPayload_1600 = GetAttributes("MB","1",null,null,null,null,null,null,null,null);

        val body = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1600, UsersClient.country, NewClientName, NewClientName,
                "Q33",Product_1600, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp, Vendor103,"1","1","1",Vendor103);
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
        jsonArrayPayload_1601 = GetAttributes("GB","1",null,null,null,null,null,null,null,null);

        val body_1601 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1601, UsersClient.country, NewClientName, NewClientName,
                null,Product_1601, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1601, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1601);
        PutUpdateProduct(body_1601)
                .then().assertThat().statusCode(SC_OK);

        Map<String, String> map_1602 = new Hashtable<>();
        map_1602.put("productId", ProductLookupClient.Product_1602);

        String timeStamp_1602 = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1602)
                .then().assertThat().statusCode(SC_OK)
                .extract().response().path("product.timeStamp");
        System.out.println(timeStamp_1602);

        List<Map<String,String>> jsonArrayPayload_1602 = new ArrayList<>();
        jsonArrayPayload_1602 = GetAttributes(null,null,null,null,null,null,null,null,null,null);

        val body_1602 = setUpPutProductDataWithAttributesWithPurchaseMedium(true, jsonArrayPayload_1602, UsersClient.country, NewClientName, NewClientName,
                null,Product_1602, map_Pricing, ProductTypeAirtime_3, NewClientName, false, true,
                timeStamp_1602, Vendor103,"1","1","1",Vendor103);
        System.out.println(body_1602);
        PutUpdateProduct(body_1602)
                .then().assertThat().statusCode(SC_OK);

        HashMap<String, String> pair1 = new HashMap<String, String>();
        pair1.put("name", "unit");
        pair1.put("value", "mb");

        HashMap<String, String> pair2 = new HashMap<String, String>();
        pair2.put("name", "unit");
        pair2.put("value", "GB");

        List<Map<String,String>> attributeNameValuePairList = new ArrayList<>();

//        attributeNameValuePairList.add(pair1);
        attributeNameValuePairList.add(pair2);

        HashMap<String, Object> attributeNameValuePairListObject = new HashMap<String, Object>();
        attributeNameValuePairListObject.put("attributeNameValuePairList", attributeNameValuePairList);
        attributeNameValuePairListObject.put("include", false);

        Map <String, String> queryParams = new Hashtable<>();
        queryParams.put("clientId",Product_1600);
        queryParams.put("productTypeId","3");
        queryParams.put("includeInactive","true");
        queryParams.put("topSeller","true");
        queryParams.put("subscriberType","Converged");
        queryParams.put("topSellerPlatform","SMS");
        queryParams.put("purchaseMedium","Card");
        queryParams.put("channelId","2");
        queryParams.put("vendorId","103");
        queryParams.put("targetIdentifier","2348085767001");


        List<Integer> response2 =  putProductInfo(Port.PRODUCT_LOOKUP, Version.V4,queryParams, attributeNameValuePairListObject)
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
}
