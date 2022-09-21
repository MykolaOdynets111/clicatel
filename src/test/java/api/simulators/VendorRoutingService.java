package api.simulators;

import api.clients.*;
import api.enums.Port;
import api.enums.Version;
import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.joda.time.DateTime;
import org.testng.annotations.Test;

import java.lang.reflect.Array;
import java.util.*;

import static api.clients.ProductLookupClient.*;
import static api.clients.VendorRoutingServiceClient.*;
import static api.domains.product_lookup.repo.ProductLookupRequestRepo.setUpPutProductData;
import static api.domains.simulator.repo.VendorRoutingServiceRequestRepo.GetMinScenario;
import static db.clients.HibernateBaseClient.executeCustomQuery;
import static db.clients.HibernateBaseClient.executeCustomQueryAndReturnValues;
import static db.custom_queries.ClientQueries.DELETE_CLIENT_MODULE_CONFIG;
import static db.custom_queries.ClientQueries.SELECT_SYSTEM_ID;
import static db.custom_queries.VendorRoutingServiceQueries.*;
import static db.enums.Sessions.MY_SQL;
import static db.enums.Sessions.POSTGRES_SQL;
import static java.lang.String.format;
import static org.apache.http.HttpStatus.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.AssertJUnit.fail;
import static api.clients.ReserveAndTransactClient.*;

public class VendorRoutingService {
    @Test
    @Description("32050-vendor-routing-service :: GET \u200B/mtn\u200B/sync\u200B/invoke :: happy path")
    @TmsLink("MKP-1562")
    public void testMTNSyncInvokeHappyPath() {
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
                externalIdTECH106758, Product_1600, map_Pricing, Product_1600, NewClientName, true, true,
                timeStamp, ReserveAndTransactClient.TestClient3, ReserveAndTransactClient.TestClient3);
        System.out.println(body);
        PutUpdateProduct(body)
                .then().assertThat().statusCode(SC_OK);
        DeleteScenario(VendorRoutingServiceClient.Scenario_TECH106758)
                .then().assertThat().statusCode(SC_OK);
//Converged
        Map<String, Object> vasServices = new LinkedHashMap<>();
        vasServices.put("saeeId", externalIdTECH106758);
        vasServices.put("bundleDescription", MtnProductSyncDesc);
        vasServices.put("cost", Vendor100);
        vasServices.put("bundleUom", null);
        vasServices.put("value", null);
        vasServices.put("period", null);
        vasServices.put("productPrompt", null);
        vasServices.put("chargeable", null);
        vasServices.put("me2uAllowed", null);
        vasServices.put("recurringVasCode", null);
        vasServices.put("allPlatforms", null);
        vasServices.put("onceOffVasCode", null);
        vasServices.put("bundleIndicator", null);
        vasServices.put("isTopSeller", null);
        vasServices.put("purchaseMedium", null);
        vasServices.put("topSellerOn", null);
        vasServices.put("chargeType", chargeType_OnceOff);
        vasServices.put("bundleCardType", null);
        vasServices.put("periodExtension", null);
        vasServices.put("shareable", null);
        vasServices.put("intellectualProperty", null);
        vasServices.put("expandedDescription", null);
        vasServices.put("imageUrl", null);
        vasServices.put("collapsedDescription", null);
        vasServices.put("costUom", null);
        vasServices.put("specification", null);
        vasServices.put("extraInfo", null);
        vasServices.put("partnerId", null);
        vasServices.put("customerFacingName", null);
        vasServices.put("partnerPlatform", null);

        List<Map<String, Object>> VacServices = new ArrayList<>();
        VacServices.add(vasServices);

        Map<String, Object> vas_converged = new LinkedHashMap<>();
        vas_converged.put("bundleType", bundleType_Data);
        vas_converged.put("vasServices", VacServices);

        List<Map<String, Object>> vas_converged_List = new ArrayList<>();
        vas_converged_List.add(vas_converged);

        List<Map<String, Object>> topSellingBundlesList_Converged = new ArrayList<>();

        Map<String, Object> payload_converged = new LinkedHashMap<>();
        payload_converged.put("statusCode", ReserveAndTransactClient.FeeAmount0);
        payload_converged.put("statusMessage", responseMessageStatusMessage);
        payload_converged.put("supportMessage", responseMessageStatusMessage);
        payload_converged.put("transactionId", transactionId_MtN_Sync);
        payload_converged.put("vas", vas_converged_List);
        payload_converged.put("topSellingBundles", topSellingBundlesList_Converged);

        Map<String, Object> subscriberResponse_converged = new LinkedHashMap<>();
        subscriberResponse_converged.put("httpStatusCode", ReserveAndTransactClient.PurchaseAmount200);
        subscriberResponse_converged.put("payload", payload_converged);

        Map<String, Object> scenario_converged = new LinkedHashMap<>();
        scenario_converged.put("subscriberType", "Converged");
        scenario_converged.put("subscriberResponse", subscriberResponse_converged);

//Prepaid

        List<Map<String, Object>> vas_prepaid_List = new ArrayList<>();

        List<Map<String, Object>> topSellingBundlesList_Prepaid = new ArrayList<>();

        Map<String, Object> payload_prepaid = new LinkedHashMap<>();
        payload_prepaid.put("statusCode", ReserveAndTransactClient.FeeAmount0);
        payload_prepaid.put("statusMessage", responseMessageStatusMessage);
        payload_prepaid.put("supportMessage", responseMessageStatusMessage);
        payload_prepaid.put("transactionId", transactionId_MtN_Sync);
        payload_prepaid.put("vas", vas_prepaid_List);
        payload_prepaid.put("topSellingBundles", topSellingBundlesList_Prepaid);

        Map<String, Object> subscriberResponse_prepaid = new LinkedHashMap<>();
        subscriberResponse_prepaid.put("httpStatusCode", ReserveAndTransactClient.PurchaseAmount200);
        subscriberResponse_prepaid.put("payload", payload_prepaid);

        Map<String, Object> scenario_prepaid = new LinkedHashMap<>();
        scenario_prepaid.put("subscriberType", "Prepaid");
        scenario_prepaid.put("subscriberResponse", subscriberResponse_prepaid);
//Contract

        List<Map<String, Object>> vas_contract_List = new ArrayList<>();

        List<Map<String, Object>> topSellingBundlesList_contract = new ArrayList<>();

        Map<String, Object> payload_contract = new LinkedHashMap<>();
        payload_contract.put("statusCode", ReserveAndTransactClient.FeeAmount0);
        payload_contract.put("statusMessage", responseMessageStatusMessage);
        payload_contract.put("supportMessage", responseMessageStatusMessage);
        payload_contract.put("transactionId", transactionId_MtN_Sync);
        payload_contract.put("vas", vas_contract_List);
        payload_contract.put("topSellingBundles", topSellingBundlesList_contract);

        Map<String, Object> subscriberResponse_contract = new LinkedHashMap<>();
        subscriberResponse_contract.put("httpStatusCode", ReserveAndTransactClient.PurchaseAmount200);
        subscriberResponse_contract.put("payload", payload_contract);

        Map<String, Object> scenario_contract = new LinkedHashMap<>();
        scenario_contract.put("subscriberType", "Contract");
        scenario_contract.put("subscriberResponse", subscriberResponse_contract);
//Hybrid
        List<Map<String, Object>> vas_hybrid_List = new ArrayList<>();

        List<Map<String, Object>> topSellingBundlesList_hybrid = new ArrayList<>();

        Map<String, Object> payload_hybrid = new LinkedHashMap<>();
        payload_hybrid.put("statusCode", ReserveAndTransactClient.FeeAmount0);
        payload_hybrid.put("statusMessage", responseMessageStatusMessage);
        payload_hybrid.put("supportMessage", responseMessageStatusMessage);
        payload_hybrid.put("transactionId", transactionId_MtN_Sync);
        payload_hybrid.put("vas", vas_hybrid_List);
        payload_hybrid.put("topSellingBundles", topSellingBundlesList_hybrid);

        Map<String, Object> subscriberResponse_hybrid = new LinkedHashMap<>();
        subscriberResponse_hybrid.put("httpStatusCode", ReserveAndTransactClient.PurchaseAmount200);
        subscriberResponse_hybrid.put("payload", payload_hybrid);

        Map<String, Object> scenario_hybrid = new LinkedHashMap<>();
        scenario_hybrid.put("subscriberType", "Hybrid");
        scenario_hybrid.put("subscriberResponse", subscriberResponse_hybrid);
//Add all scenarios together

        List<Map<String, Object>> ScenariosList = new ArrayList<>();
        ScenariosList.add(scenario_converged);
        ScenariosList.add(scenario_prepaid);
        ScenariosList.add(scenario_contract);
        ScenariosList.add(scenario_hybrid);

        Map<String, Object> Scenarios = new LinkedHashMap<>();
        Scenarios.put("scenarioId", Scenario_TECH106758);
        Scenarios.put("scenarios", ScenariosList);

        PostScenario(Scenarios)
                .then().assertThat().statusCode(SC_CREATED);

        Map<String, Object> CurrentScenarioPre = new LinkedHashMap<>();
        CurrentScenarioPre.put("currentScenarioId", Scenario_TECH106758);

        PutCurrentScenario(CurrentScenarioPre)
                .then().assertThat().statusCode(SC_OK);

        SyncInvoke(String.valueOf(DateTime.now()))
                .then().assertThat().statusCode(SC_OK);

        String description = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
                .then().assertThat().statusCode(SC_OK)
                .body("product.product.description", Matchers.containsString(MtnProductSyncDesc))
                .extract().response().path("product.product.description");
        System.out.println(description);

        Map<String, Object> CurrentScenarioPost = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_sample);

        PutCurrentScenario(CurrentScenarioPost)
                .then().assertThat().statusCode(SC_OK);

    }

    @Test
    @Description("31943 :: mtn-simulator :: POST /mtn/test-scenario :: happy path")
    @TmsLink("MKP-1091")
    public void testPostMTNSyncHappyPath() {
//        Map<String, String> map_1600 = new Hashtable<>();
//        map_1600.put("productId", ProductLookupClient.Product_1600);
//
//        Map<String, String> map_Pricing = new Hashtable<>();
//        map_Pricing.put("type", ProductType_Single);
//        map_Pricing.put("amount", ReserveAndTransactClient.PurchaseAmount10000);
//
//        String timeStamp = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
//                .then().assertThat().statusCode(SC_OK)
//                .extract().response().path("product.timeStamp");
//        System.out.println(timeStamp);
//        val body = setUpPutProductData(true, UsersClient.country, NewClientName, NewClientName,
//                externalIdTECH106758, Product_1600, map_Pricing, Product_1600, NewClientName, true, true,
//                timeStamp, ReserveAndTransactClient.TestClient3, ReserveAndTransactClient.TestClient3);
//        System.out.println(body);
//        PutUpdateProduct(body)
//                .then().assertThat().statusCode(SC_OK);
        DeleteScenario(VendorRoutingServiceClient.Scenario_TECH106758)
                .then().assertThat().statusCode(SC_OK);
//Converged
        Map<String, Object> vasServices = new LinkedHashMap<>();
        vasServices.put("saeeId", externalIdTECH106758);
        vasServices.put("bundleDescription", MtnProductSyncDesc);
        vasServices.put("cost", Vendor100);
        vasServices.put("bundleUom", null);
        vasServices.put("value", null);
        vasServices.put("period", null);
        vasServices.put("productPrompt", null);
        vasServices.put("chargeable", null);
        vasServices.put("me2uAllowed", null);
        vasServices.put("recurringVasCode", null);
        vasServices.put("allPlatforms", null);
        vasServices.put("onceOffVasCode", null);
        vasServices.put("bundleIndicator", null);
        vasServices.put("isTopSeller", null);
        vasServices.put("purchaseMedium", null);
        vasServices.put("topSellerOn", null);
        vasServices.put("chargeType", chargeType_OnceOff);
        vasServices.put("bundleCardType", null);
        vasServices.put("periodExtension", null);
        vasServices.put("shareable", null);
        vasServices.put("intellectualProperty", null);
        vasServices.put("expandedDescription", null);
        vasServices.put("imageUrl", null);
        vasServices.put("collapsedDescription", null);
        vasServices.put("costUom", null);
        vasServices.put("specification", null);
        vasServices.put("extraInfo", null);
        vasServices.put("partnerId", null);
        vasServices.put("customerFacingName", null);
        vasServices.put("partnerPlatform", null);

        List<Map<String, Object>> VacServices = new ArrayList<>();
        VacServices.add(vasServices);

        Map<String, Object> vas_converged = new LinkedHashMap<>();
        vas_converged.put("bundleType", bundleType_Data);
        vas_converged.put("vasServices", VacServices);

        List<Map<String, Object>> vas_converged_List = new ArrayList<>();
        vas_converged_List.add(vas_converged);

        List<Map<String, Object>> topSellingBundlesList_Converged = new ArrayList<>();

        Map<String, Object> payload_converged = new LinkedHashMap<>();
        payload_converged.put("statusCode", ReserveAndTransactClient.FeeAmount0);
        payload_converged.put("statusMessage", responseMessageStatusMessage);
        payload_converged.put("supportMessage", responseMessageStatusMessage);
        payload_converged.put("transactionId", transactionId_MtN_Sync);
        payload_converged.put("vas", vas_converged_List);
        payload_converged.put("topSellingBundles", topSellingBundlesList_Converged);

        Map<String, Object> subscriberResponse_converged = new LinkedHashMap<>();
        subscriberResponse_converged.put("httpStatusCode", ReserveAndTransactClient.PurchaseAmount200);
        subscriberResponse_converged.put("payload", payload_converged);

        Map<String, Object> scenario_converged = new LinkedHashMap<>();
        scenario_converged.put("subscriberType", "Converged");
        scenario_converged.put("subscriberResponse", subscriberResponse_converged);

//Prepaid

        List<Map<String, Object>> vas_prepaid_List = new ArrayList<>();

        List<Map<String, Object>> topSellingBundlesList_Prepaid = new ArrayList<>();

        Map<String, Object> payload_prepaid = new LinkedHashMap<>();
        payload_prepaid.put("statusCode", ReserveAndTransactClient.FeeAmount0);
        payload_prepaid.put("statusMessage", responseMessageStatusMessage);
        payload_prepaid.put("supportMessage", responseMessageStatusMessage);
        payload_prepaid.put("transactionId", transactionId_MtN_Sync);
        payload_prepaid.put("vas", vas_prepaid_List);
        payload_prepaid.put("topSellingBundles", topSellingBundlesList_Prepaid);

        Map<String, Object> subscriberResponse_prepaid = new LinkedHashMap<>();
        subscriberResponse_prepaid.put("httpStatusCode", ReserveAndTransactClient.PurchaseAmount200);
        subscriberResponse_prepaid.put("payload", payload_prepaid);

        Map<String, Object> scenario_prepaid = new LinkedHashMap<>();
        scenario_prepaid.put("subscriberType", "Prepaid");
        scenario_prepaid.put("subscriberResponse", subscriberResponse_prepaid);
//Contract

        List<Map<String, Object>> vas_contract_List = new ArrayList<>();

        List<Map<String, Object>> topSellingBundlesList_contract = new ArrayList<>();

        Map<String, Object> payload_contract = new LinkedHashMap<>();
        payload_contract.put("statusCode", ReserveAndTransactClient.FeeAmount0);
        payload_contract.put("statusMessage", responseMessageStatusMessage);
        payload_contract.put("supportMessage", responseMessageStatusMessage);
        payload_contract.put("transactionId", transactionId_MtN_Sync);
        payload_contract.put("vas", vas_contract_List);
        payload_contract.put("topSellingBundles", topSellingBundlesList_contract);

        Map<String, Object> subscriberResponse_contract = new LinkedHashMap<>();
        subscriberResponse_contract.put("httpStatusCode", ReserveAndTransactClient.PurchaseAmount200);
        subscriberResponse_contract.put("payload", payload_contract);

        Map<String, Object> scenario_contract = new LinkedHashMap<>();
        scenario_contract.put("subscriberType", "Contract");
        scenario_contract.put("subscriberResponse", subscriberResponse_contract);
//Hybrid
        List<Map<String, Object>> vas_hybrid_List = new ArrayList<>();

        List<Map<String, Object>> topSellingBundlesList_hybrid = new ArrayList<>();

        Map<String, Object> payload_hybrid = new LinkedHashMap<>();
        payload_hybrid.put("statusCode", ReserveAndTransactClient.FeeAmount0);
        payload_hybrid.put("statusMessage", responseMessageStatusMessage);
        payload_hybrid.put("supportMessage", responseMessageStatusMessage);
        payload_hybrid.put("transactionId", transactionId_MtN_Sync);
        payload_hybrid.put("vas", vas_hybrid_List);
        payload_hybrid.put("topSellingBundles", topSellingBundlesList_hybrid);

        Map<String, Object> subscriberResponse_hybrid = new LinkedHashMap<>();
        subscriberResponse_hybrid.put("httpStatusCode", ReserveAndTransactClient.PurchaseAmount200);
        subscriberResponse_hybrid.put("payload", payload_hybrid);

        Map<String, Object> scenario_hybrid = new LinkedHashMap<>();
        scenario_hybrid.put("subscriberType", "Hybrid");
        scenario_hybrid.put("subscriberResponse", subscriberResponse_hybrid);
//Add all scenarios together

        List<Map<String, Object>> ScenariosList = new ArrayList<>();
        ScenariosList.add(scenario_converged);
        ScenariosList.add(scenario_prepaid);
        ScenariosList.add(scenario_contract);
        ScenariosList.add(scenario_hybrid);

        Map<String, Object> Scenarios = new LinkedHashMap<>();
        Scenarios.put("scenarioId", Scenario_TECH106758);
        Scenarios.put("scenarios", ScenariosList);

        PostScenario(Scenarios)
                .then().assertThat().statusCode(SC_CREATED);

//        Map<String, Object> CurrentScenarioPre = new LinkedHashMap<>();
//        CurrentScenarioPre.put("currentScenarioId", Scenario_TECH106758);
//
//        PutCurrentScenario(CurrentScenarioPre)
//                .then().assertThat().statusCode(SC_OK);
//
//        SyncInvoke(String.valueOf(DateTime.now()))
//                .then().assertThat().statusCode(SC_OK);
//
//        String description = getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map_1600)
//                .then().assertThat().statusCode(SC_OK)
//                .body("product.product.description", Matchers.containsString(MtnProductSyncDesc))
//                .extract().response().path("product.product.description");
//        System.out.println(description);

        DeleteScenario(VendorRoutingServiceClient.Scenario_TECH106758)
                .then().assertThat().statusCode(SC_OK);

        Map<String, Object> CurrentScenarioPost = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_sample);

        PutCurrentScenario(CurrentScenarioPost)
                .then().assertThat().statusCode(SC_OK);


    }

    @Test
    @Description("31943 :: mtn-simulator :: GET /mtn/test-scenario/{scenarioId} :: happy path")
    @TmsLink("MKP-1057")
    public void testGetTestScenarioByScenarioId() {

        DeleteScenario("min")
                .then().assertThat().statusCode(SC_OK);

        Map Scenarios = GetMinScenario();

        PostScenario(Scenarios)
                .then().assertThat().statusCode(SC_CREATED)
                .body("scenarioId", Matchers.is(Scenario_min))
                .body("scenarios.subscriberResponse.payload.vas.vasServices.saeeId[0]", Matchers.is(Arrays.asList(Arrays.asList(externalId_DATA_001_min))))
                .body("scenarios.subscriberResponse.payload.vas.vasServices.bundleDescription[0]", Matchers.is(Arrays.asList(Arrays.asList(MtnProductMinScenario))));

        GetSpecificScenario(Scenario_min)
                .then().assertThat().statusCode(SC_OK)
                .body("scenarioId", Matchers.is(Scenario_min))
                .body("scenarios.subscriberResponse.payload.vas.vasServices.saeeId[0]", Matchers.is(Arrays.asList(Arrays.asList(externalId_DATA_001_min))))
                .body("scenarios.subscriberResponse.payload.vas.vasServices.bundleDescription[0]", Matchers.is(Arrays.asList(Arrays.asList(MtnProductMinScenario))));

        DeleteScenario(Scenario_min)
                .then().assertThat().statusCode(SC_OK);

        Map<String, Object> CurrentScenarioPost = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_sample);

        PutCurrentScenario(CurrentScenarioPost)
                .then().assertThat().statusCode(SC_OK);

    }

    @Test
    @Description("31943 :: mtn-simulator :: DELETE /mtn/test-scenario/{scenarioId} :: happy path")
    @TmsLink("MKP-974")
    public void testDeleteTestScenarioByScenarioId() {

        DeleteScenario("min")
                .then().assertThat().statusCode(SC_OK);

        Map Scenarios = GetMinScenario();

        PostScenario(Scenarios)
                .then().assertThat().statusCode(SC_CREATED)
                .body("scenarioId", Matchers.is(Scenario_min))
                .body("scenarios.subscriberResponse.payload.vas.vasServices.saeeId[0]", Matchers.is(Arrays.asList(Arrays.asList(externalId_DATA_001_min))))
                .body("scenarios.subscriberResponse.payload.vas.vasServices.bundleDescription[0]", Matchers.is(Arrays.asList(Arrays.asList(MtnProductMinScenario))));

        GetSpecificScenario(Scenario_min)
                .then().assertThat().statusCode(SC_OK)
                .body("scenarioId", Matchers.is(Scenario_min))
                .body("scenarios.subscriberResponse.payload.vas.vasServices.saeeId[0]", Matchers.is(Arrays.asList(Arrays.asList(externalId_DATA_001_min))))
                .body("scenarios.subscriberResponse.payload.vas.vasServices.bundleDescription[0]", Matchers.is(Arrays.asList(Arrays.asList(MtnProductMinScenario))));

        DeleteScenario(Scenario_min)
                .then().assertThat().statusCode(SC_OK);

        GetSpecificScenario(Scenario_min)
                .then().assertThat().statusCode(SC_NOT_FOUND);

        Map<String, Object> CurrentScenarioPost = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_sample);

        PutCurrentScenario(CurrentScenarioPost)
                .then().assertThat().statusCode(SC_OK);

    }

    @Test
    @Description("32050-vendor-routing-service :: GET \u200B/mtn\u200B/sync\u200B/invoke :: the \"period\"(attribute 3)(optional) the \"create\" scenario for the \"empty\" value and the \"update\" scenario for the \"valid\" value")
    @TmsLink("MKP-682")
    public void testAttribute3CreateForEmptyAndUpdateForValid() throws InterruptedException {

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

//        DeleteScenario(Scenario_testScenario)
//                .then().assertThat().statusCode(SC_OK);
//
//        val scenarioOneProduct_null = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,null,productPrompt,chargeable,me2uAllowed,recurringVasCode,
//                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
//                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
//                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
//                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
//                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
//                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
//        PostScenario(scenarioOneProduct_null)
//                .then().assertThat().statusCode(SC_CREATED);
//
//        Map<String, Object> CurrentScenarioPost = new LinkedHashMap<>();
//        CurrentScenarioPost.put("currentScenarioId", Scenario_testScenario);
//
//        PutCurrentScenario(CurrentScenarioPost)
//                .then().assertThat().statusCode(SC_OK);
//
//        SyncInvoke(String.valueOf(DateTime.now()));
//        SyncInvoke(String.valueOf(DateTime.now()));
//
//        val SELECT_PRODUCT_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT);
//        List<String> strings = Lists.transform(SELECT_PRODUCT_VALUES, Functions.toStringFunction());
//        System.out.println(strings);
//
//        val SELECT_PRODUCT_ATTRIBUTE_ID_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_ATTRIBUTE_ID);
//        List<String> ProductAttributesIds = Lists.transform(SELECT_PRODUCT_ATTRIBUTE_ID_VALUES, Functions.toStringFunction());
//        System.out.println(ProductAttributesIds);
//        assertThat(ProductAttributesIds)
//                .doesNotContain("3");
//
//        DeleteScenario(Scenario_testScenario)
//                .then().assertThat().statusCode(SC_OK);
//
//        val scenarioOneProduct_30Days = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,me2uAllowed,recurringVasCode,
//                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
//                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
//                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
//                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
//                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
//                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
//        PostScenario(scenarioOneProduct_30Days)
//                .then().assertThat().statusCode(SC_CREATED);
//
//        Thread.sleep(20000);
//
//        SyncInvoke(String.valueOf(DateTime.now()))
//                .then().assertThat().statusCode(SC_OK);
//
//        val SELECT_PRODUCT_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_PRODUCT_VALUE, "3"));
//        List<String> strings_1 = Lists.transform(SELECT_PRODUCT_VALUES_1, Functions.toStringFunction());
//        System.out.println(strings_1);
//        assertThat(SELECT_PRODUCT_VALUES_1)
//                .contains("30 days");
//
//        val SELECT_PRODUCT_ATTRIBUTE_ID_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_ATTRIBUTE_ID);
//        List<String> ProductAttributesIds_1 = Lists.transform(SELECT_PRODUCT_ATTRIBUTE_ID_VALUES_1, Functions.toStringFunction());
//        System.out.println(ProductAttributesIds_1);
//        assertThat(ProductAttributesIds_1)
//                .contains("3");
//
//        //Cleanup
//
//        DeleteScenario(Scenario_testScenario)
//                .then().assertThat().statusCode(SC_OK);
//
//        Map<String, Object> CurrentScenarioPost_Sample = new LinkedHashMap<>();
//        CurrentScenarioPost.put("currentScenarioId", Scenario_sample);
//
//        PutCurrentScenario(CurrentScenarioPost_Sample)
//                .then().assertThat().statusCode(SC_OK);
//
//        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
//        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
//        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
//        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
//        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
//        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
//        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
//        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

    }

    @Test
    @Description("32050-vendor-routing-service :: GET \u200B/mtn\u200B/sync\u200B/invoke :: (ChargeType) Verify that duplicate values get transformed and saved")
    @TmsLink("MKP-562")
    public void testChargeTypeDuplicateValuesGetTransformedAndSaved() throws InterruptedException {

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_null = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,null,productPrompt,chargeable,me2uAllowed,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType_Duplicates,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_null)
                .then().assertThat().statusCode(SC_CREATED);

        Map<String, Object> CurrentScenarioPost = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_testScenario);

        PutCurrentScenario(CurrentScenarioPost)
                .then().assertThat().statusCode(SC_OK);


        SyncInvoke(String.valueOf(DateTime.now()));
        List chargetype = SyncInvoke(String.valueOf(DateTime.now()))
                .then()
                .extract().response().path("clickatellProduct.chargeType[0]");
        System.out.println(chargetype.size());
        int maxValue = chargetype.size();
        System.out.println(maxValue);

        if(maxValue<3)
            fail("Duplicates were not removed");

        System.out.println(chargetype);

        val SELECT_CHARGETYPE_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_CHARGETYPE);
        List<String> strings = Lists.transform(SELECT_CHARGETYPE_VALUES, Functions.toStringFunction());
        System.out.println(strings);
        assertThat(strings.get(0))
                .contains("2");
        assertThat(strings.get(1))
                .contains("3");
        assertThat(strings.get(2))
                .contains("1");
        assertThat(strings.get(3))
                .contains("2");
        assertThat(strings.get(4))
                .contains("3");
        assertThat(strings.get(5))
                .contains("1");

        //Cleanup

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        Map<String, Object> CurrentScenarioPost_Sample = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_sample);

        PutCurrentScenario(CurrentScenarioPost_Sample)
                .then().assertThat().statusCode(SC_OK);

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

    }
    @Test
    @Description("32050-vendor-routing-service :: GET \u200B/mtn\u200B/sync\u200B/invoke :: (ChargeType) Verify that values provided without hyphen in Payload for (Autorenew , Onceoff) should get transformed and saved with hyphen in DB For ex: (Auto-renew , Once-off)")
    @TmsLink("MKP-1098")
    public void testChargeTypeWithoutHyphenValuesGetTransformedAndSaved() throws InterruptedException {

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_null = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,null,productPrompt,chargeable,me2uAllowed,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_null)
                .then().assertThat().statusCode(SC_CREATED);

        Map<String, Object> CurrentScenarioPost = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_testScenario);

        PutCurrentScenario(CurrentScenarioPost)
                .then().assertThat().statusCode(SC_OK);


        SyncInvoke(String.valueOf(DateTime.now()));
        List chargetype = SyncInvoke(String.valueOf(DateTime.now()))
                .then()
                .extract().response().path("clickatellProduct.chargeType[0]");
        System.out.println(chargetype);
        List<String> ChargeTypeString = Lists.transform(chargetype, Functions.toStringFunction());
        System.out.println(ChargeTypeString);
        assertThat(ChargeTypeString.get(0))
                .contains("Recurring");
        assertThat(ChargeTypeString.get(1))
                .contains("Auto-renew");
        assertThat(ChargeTypeString.get(2))
                .contains("Once-Off");

        val SELECT_CHARGETYPE_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_CHARGETYPE);
        List<String> strings = Lists.transform(SELECT_CHARGETYPE_VALUES, Functions.toStringFunction());
        System.out.println(strings);
        assertThat(strings.get(0))
                .contains("2");
        assertThat(strings.get(1))
                .contains("3");
        assertThat(strings.get(2))
                .contains("1");
        assertThat(strings.get(3))
                .contains("2");
        assertThat(strings.get(4))
                .contains("3");
        assertThat(strings.get(5))
                .contains("1");

        //Cleanup

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        Map<String, Object> CurrentScenarioPost_Sample = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_sample);

        PutCurrentScenario(CurrentScenarioPost_Sample)
                .then().assertThat().statusCode(SC_OK);

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

    }

    @Test
    @Description("32050-vendor-routing-service :: GET \u200B/mtn\u200B/sync\u200B/invoke :: the \"period\"(attribute 3)(optional) the \"create\" scenario for the \"valid\" value and the \"update\" scenario for the \"empty\" value")
    @TmsLink("MKP-1106")
    public void testAttribute3CreateForValidAndUpdateForEmpty() throws InterruptedException {

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_null = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,me2uAllowed,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_null)
                .then().assertThat().statusCode(SC_CREATED);

        Map<String, Object> CurrentScenarioPost = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_testScenario);

        PutCurrentScenario(CurrentScenarioPost)
                .then().assertThat().statusCode(SC_OK);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()));

        val SELECT_PRODUCT_ATTRIBUTE_ID_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_ATTRIBUTE_ID);
        List<String> ProductAttributesIds_1 = Lists.transform(SELECT_PRODUCT_ATTRIBUTE_ID_VALUES_1, Functions.toStringFunction());
        System.out.println(ProductAttributesIds_1);
        assertThat(ProductAttributesIds_1)
                .contains("3");

        val SELECT_PRODUCT_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_PRODUCT_VALUE, "3"));
        List<String> strings_1 = Lists.transform(SELECT_PRODUCT_VALUES_1, Functions.toStringFunction());
        System.out.println(strings_1);
        assertThat(SELECT_PRODUCT_VALUES_1)
                .contains("30 days");

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_30Days = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,null,productPrompt,chargeable,me2uAllowed,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_30Days)
                .then().assertThat().statusCode(SC_CREATED);

        SyncInvoke(String.valueOf(DateTime.now()))
                .then().assertThat().statusCode(SC_OK);

        val SELECT_PRODUCT_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT);
        List<String> strings = Lists.transform(SELECT_PRODUCT_VALUES, Functions.toStringFunction());
        System.out.println(strings.get(1));
        assertThat(strings);

        val SELECT_PRODUCT_ATTRIBUTE_ID_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_ATTRIBUTE_ID);
        List<String> ProductAttributesIds = Lists.transform(SELECT_PRODUCT_ATTRIBUTE_ID_VALUES, Functions.toStringFunction());
        System.out.println(ProductAttributesIds);
        assertThat(ProductAttributesIds)
                .doesNotContain("3");
//Cleanup

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        Map<String, Object> CurrentScenarioPost_Sample = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_sample);

        PutCurrentScenario(CurrentScenarioPost_Sample)
                .then().assertThat().statusCode(SC_OK);

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

    }
    @Test
    @Description("32050-vendor-routing-service :: GET \u200B/mtn\u200B/sync\u200B/invoke :: the value(attribute 2)(optional) the \"create\" scenario for the \"valid\" value and the \"update\" scenario for the \"empty\" value")
    @TmsLink("MKP-939")
    public void testAttribute2CreateForValidAndUpdateForEmpty() throws InterruptedException {

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_null = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,me2uAllowed,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_null)
                .then().assertThat().statusCode(SC_CREATED);

        Map<String, Object> CurrentScenarioPost = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_testScenario);

        PutCurrentScenario(CurrentScenarioPost)
                .then().assertThat().statusCode(SC_OK);

        SyncInvoke(String.valueOf(DateTime.now()));

        val SELECT_PRODUCT_ATTRIBUTE_ID_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_ATTRIBUTE_ID);
        List<String> ProductAttributesIds_1 = Lists.transform(SELECT_PRODUCT_ATTRIBUTE_ID_VALUES_1, Functions.toStringFunction());
        System.out.println(ProductAttributesIds_1);
        assertThat(ProductAttributesIds_1)
                .contains("2");

        val SELECT_PRODUCT_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_PRODUCT_VALUE, "2"));
        List<String> strings_1 = Lists.transform(SELECT_PRODUCT_VALUES_1, Functions.toStringFunction());
        System.out.println(strings_1);
        assertThat(SELECT_PRODUCT_VALUES_1)
                .contains("100");

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_30Days = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,null,period,productPrompt,chargeable,me2uAllowed,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_30Days)
                .then().assertThat().statusCode(SC_CREATED);

        SyncInvoke(String.valueOf(DateTime.now()));

        val SELECT_PRODUCT_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT);
        List<String> strings = Lists.transform(SELECT_PRODUCT_VALUES, Functions.toStringFunction());
        System.out.println(strings.get(1));
        assertThat(strings);

        val SELECT_PRODUCT_ATTRIBUTE_ID_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_ATTRIBUTE_ID);
        List<String> ProductAttributesIds = Lists.transform(SELECT_PRODUCT_ATTRIBUTE_ID_VALUES, Functions.toStringFunction());
        System.out.println(ProductAttributesIds);
        assertThat(ProductAttributesIds)
                .doesNotContain("2");
//Cleanup

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        Map<String, Object> CurrentScenarioPost_Sample = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_sample);

        PutCurrentScenario(CurrentScenarioPost_Sample)
                .then().assertThat().statusCode(SC_OK);

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

    }

    @Test
    @Description("32050-vendor-routing-service :: GET \u200B/mtn\u200B/sync\u200B/invoke :: the value(attribute 2)(optional) the \"create\" scenario for the \"empty\" value and the \"update\" scenario for the \"valid\" value")
    @TmsLink("MKP-553")
    public void testAttribute2CreateForEmptyAndUpdateForValid() throws InterruptedException {

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_null = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,null,period,productPrompt,chargeable,me2uAllowed,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_null)
                .then().assertThat().statusCode(SC_CREATED);

        Map<String, Object> CurrentScenarioPost = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_testScenario);

        PutCurrentScenario(CurrentScenarioPost)
                .then().assertThat().statusCode(SC_OK);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()));

        val SELECT_PRODUCT_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT);
        List<String> strings = Lists.transform(SELECT_PRODUCT_VALUES, Functions.toStringFunction());
        System.out.println(strings.get(1));
        assertThat(strings);

        val SELECT_PRODUCT_ATTRIBUTE_ID_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_ATTRIBUTE_ID);
        List<String> ProductAttributesIds = Lists.transform(SELECT_PRODUCT_ATTRIBUTE_ID_VALUES, Functions.toStringFunction());
        System.out.println(ProductAttributesIds);
        assertThat(ProductAttributesIds)
                .doesNotContain("2");

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_30Days = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,me2uAllowed,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_30Days)
                .then().assertThat().statusCode(SC_CREATED);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()));

        val SELECT_PRODUCT_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_PRODUCT_VALUE, "2"));
        List<String> strings_1 = Lists.transform(SELECT_PRODUCT_VALUES_1, Functions.toStringFunction());
        System.out.println(strings_1);
        assertThat(SELECT_PRODUCT_VALUES_1)
                .contains("100");

        val SELECT_PRODUCT_ATTRIBUTE_ID_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_ATTRIBUTE_ID);
        List<String> ProductAttributesIds_1 = Lists.transform(SELECT_PRODUCT_ATTRIBUTE_ID_VALUES_1, Functions.toStringFunction());
        System.out.println(ProductAttributesIds_1);
        assertThat(ProductAttributesIds_1)
                .contains("2");

        //Cleanup

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        Map<String, Object> CurrentScenarioPost_Sample = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_sample);

        PutCurrentScenario(CurrentScenarioPost_Sample)
                .then().assertThat().statusCode(SC_OK);

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

    }

    @Test
    @Description("32050-vendor-routing-service :: GET \u200B/mtn\u200B/sync\u200B/invoke :: the bundleUOM(attribute 1)(optional) the \"create\" scenario for the \"empty\" value and the \"update\" scenario for the \"valid\" value")
    @TmsLink("MKP-589")
    public void testAttribute1CreateForEmptyAndUpdateForValid() throws InterruptedException {

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_null = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,null,null,period,productPrompt,chargeable,me2uAllowed,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_null)
                .then().assertThat().statusCode(SC_CREATED);

        Map<String, Object> CurrentScenarioPost = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_testScenario);

        PutCurrentScenario(CurrentScenarioPost)
                .then().assertThat().statusCode(SC_OK);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()));

        val SELECT_PRODUCT_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT);
        List<String> strings = Lists.transform(SELECT_PRODUCT_VALUES, Functions.toStringFunction());
        System.out.println(strings.get(1));
        assertThat(strings);

        val SELECT_PRODUCT_ATTRIBUTE_ID_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_ATTRIBUTE_ID);
        List<String> ProductAttributesIds = Lists.transform(SELECT_PRODUCT_ATTRIBUTE_ID_VALUES, Functions.toStringFunction());
        System.out.println(ProductAttributesIds);
        assertThat(ProductAttributesIds)
                .doesNotContain("1");

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_30Days = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,me2uAllowed,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_30Days)
                .then().assertThat().statusCode(SC_CREATED);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()))
                .then()
                .body("clickatellProduct.attributes.bundleUom",Matchers.hasItem(bundleUom));

        val SELECT_PRODUCT_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_PRODUCT_VALUE, "1"));
        List<String> strings_1 = Lists.transform(SELECT_PRODUCT_VALUES_1, Functions.toStringFunction());
        System.out.println(strings_1);
        assertThat(SELECT_PRODUCT_VALUES_1)
                .contains("GB+30GB");

        val SELECT_PRODUCT_ATTRIBUTE_ID_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_ATTRIBUTE_ID);
        List<String> ProductAttributesIds_1 = Lists.transform(SELECT_PRODUCT_ATTRIBUTE_ID_VALUES_1, Functions.toStringFunction());
        System.out.println(ProductAttributesIds_1);
        assertThat(ProductAttributesIds_1)
                .contains("1");

        //Cleanup

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        Map<String, Object> CurrentScenarioPost_Sample = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_sample);

        PutCurrentScenario(CurrentScenarioPost_Sample)
                .then().assertThat().statusCode(SC_OK);

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

    }
    @Test
    @Description("32050-vendor-routing-service :: GET \u200B/mtn\u200B/sync\u200B/invoke :: the bundleUOM(attribute 1)(optional) the \"create\" scenario for the \"valid\" value and the \"update\" scenario for the \"empty\" value")
    @TmsLink("MKP-983")
    public void testAttribute1CreateForValidAndUpdateForEmpty() throws InterruptedException {

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_null = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,me2uAllowed,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_null)
                .then().assertThat().statusCode(SC_CREATED);

        Map<String, Object> CurrentScenarioPost = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_testScenario);

        PutCurrentScenario(CurrentScenarioPost)
                .then().assertThat().statusCode(SC_OK);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()))
                .then()
                .body("clickatellProduct.attributes.bundleUom",Matchers.hasItem(bundleUom));

        val SELECT_PRODUCT_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_PRODUCT_VALUE, "1"));
        List<String> strings_1 = Lists.transform(SELECT_PRODUCT_VALUES_1, Functions.toStringFunction());
        System.out.println(strings_1);
        assertThat(SELECT_PRODUCT_VALUES_1)
                .contains("GB+30GB");

        val SELECT_PRODUCT_ATTRIBUTE_ID_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_ATTRIBUTE_ID);
        List<String> ProductAttributesIds_1 = Lists.transform(SELECT_PRODUCT_ATTRIBUTE_ID_VALUES_1, Functions.toStringFunction());
        System.out.println(ProductAttributesIds_1);
        assertThat(ProductAttributesIds_1)
                .contains("1");

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_30Days = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,null,null,period,productPrompt,chargeable,me2uAllowed,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_30Days)
                .then().assertThat().statusCode(SC_CREATED);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()));

        val SELECT_PRODUCT_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT);
        List<String> strings = Lists.transform(SELECT_PRODUCT_VALUES, Functions.toStringFunction());
        System.out.println(strings);

        val SELECT_PRODUCT_ATTRIBUTE_ID_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_ATTRIBUTE_ID);
        List<String> ProductAttributesIds = Lists.transform(SELECT_PRODUCT_ATTRIBUTE_ID_VALUES, Functions.toStringFunction());
        System.out.println(ProductAttributesIds);
        assertThat(ProductAttributesIds)
                .doesNotContain("1");
//Cleanup

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        Map<String, Object> CurrentScenarioPost_Sample = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_sample);

        PutCurrentScenario(CurrentScenarioPost_Sample)
                .then().assertThat().statusCode(SC_OK);

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

    }
    @Test
    @Description("32050-vendor-routing-service :: GET \u200B/mtn\u200B/sync\u200B/invoke :: the \"productPrompt\"(attribute 30)(optional) the \"create\" scenario for the \"valid\" value and the \"update\" scenario for the \"empty\" value")
    @TmsLink("MKP-969")
    public void testAttribute30CreateForValidAndUpdateForEmpty() throws InterruptedException {

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_null = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,me2uAllowed,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_null)
                .then().assertThat().statusCode(SC_CREATED);

        Map<String, Object> CurrentScenarioPost = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_testScenario);

        PutCurrentScenario(CurrentScenarioPost)
                .then().assertThat().statusCode(SC_OK);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()))
                .then()
                .body("clickatellProduct.attributes.productPrompt",Matchers.hasItem(productPrompt));

        val SELECT_PRODUCT_ATTRIBUTE_ID_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_ATTRIBUTE_ID);
        List<String> ProductAttributesIds_1 = Lists.transform(SELECT_PRODUCT_ATTRIBUTE_ID_VALUES_1, Functions.toStringFunction());
        System.out.println(ProductAttributesIds_1);
        assertThat(ProductAttributesIds_1)
                .contains("30");

        val SELECT_PRODUCT_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_PRODUCT_VALUE, "30"));
        List<String> strings_1 = Lists.transform(SELECT_PRODUCT_VALUES_1, Functions.toStringFunction());
        System.out.println(strings_1);
        assertThat(strings_1.get(1))
                .contains("3GB+3GB for R199");
        assertThat(strings_1.get(0))
                .contains("valid for 30 days");

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_30Days = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,null,chargeable,me2uAllowed,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_30Days)
                .then().assertThat().statusCode(SC_CREATED);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()));

        val SELECT_PRODUCT_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT);
        List<String> strings = Lists.transform(SELECT_PRODUCT_VALUES, Functions.toStringFunction());
        System.out.println(strings.get(1));
        assertThat(strings);

        val SELECT_PRODUCT_ATTRIBUTE_ID_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_ATTRIBUTE_ID);
        List<String> ProductAttributesIds = Lists.transform(SELECT_PRODUCT_ATTRIBUTE_ID_VALUES, Functions.toStringFunction());
        System.out.println(ProductAttributesIds);
        assertThat(ProductAttributesIds)
                .doesNotContain("30");
//Cleanup

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        Map<String, Object> CurrentScenarioPost_Sample = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_sample);

        PutCurrentScenario(CurrentScenarioPost_Sample)
                .then().assertThat().statusCode(SC_OK);

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

    }
    @Test
    @Description("32050-vendor-routing-service :: GET \u200B/mtn\u200B/sync\u200B/invoke :: the \"productPrompt\"(attribute 30)(optional) the \"create\" scenario for the \"empty\" value and the \"update\" scenario for the \"valid\" value")
    @TmsLink("MKP-571")
    public void testAttribute30CreateForEmptyAndUpdateForValid() throws InterruptedException {

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_null = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,null,chargeable,me2uAllowed,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_null)
                .then().assertThat().statusCode(SC_CREATED);

        Map<String, Object> CurrentScenarioPost = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_testScenario);

        PutCurrentScenario(CurrentScenarioPost)
                .then().assertThat().statusCode(SC_OK);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()));

        val SELECT_PRODUCT_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT);
        List<String> strings = Lists.transform(SELECT_PRODUCT_VALUES, Functions.toStringFunction());
        System.out.println(strings);

        val SELECT_PRODUCT_ATTRIBUTE_ID_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_ATTRIBUTE_ID);
        List<String> ProductAttributesIds = Lists.transform(SELECT_PRODUCT_ATTRIBUTE_ID_VALUES, Functions.toStringFunction());
        System.out.println(ProductAttributesIds);
        assertThat(ProductAttributesIds)
                .doesNotContain("30");

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_30Days = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,me2uAllowed,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_30Days)
                .then().assertThat().statusCode(SC_CREATED);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()))
                .then()
                .body("clickatellProduct.attributes.productPrompt",Matchers.hasItem(productPrompt));

        val SELECT_PRODUCT_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_PRODUCT_VALUE, "30"));
        List<String> strings_1 = Lists.transform(SELECT_PRODUCT_VALUES_1, Functions.toStringFunction());
        System.out.println(strings_1);
        assertThat(strings_1.get(1))
                .contains("3GB+3GB for R199");
        assertThat(strings_1.get(0))
                .contains("valid for 30 days");

        val SELECT_PRODUCT_ATTRIBUTE_ID_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_ATTRIBUTE_ID);
        List<String> ProductAttributesIds_1 = Lists.transform(SELECT_PRODUCT_ATTRIBUTE_ID_VALUES_1, Functions.toStringFunction());
        System.out.println(ProductAttributesIds_1);
        assertThat(ProductAttributesIds_1)
                .contains("30");

        //Cleanup

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        Map<String, Object> CurrentScenarioPost_Sample = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_sample);

        PutCurrentScenario(CurrentScenarioPost_Sample)
                .then().assertThat().statusCode(SC_OK);

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

    }
    @Test
    @Description("32050-vendor-routing-service :: GET \u200B/mtn\u200B/sync\u200B/invoke :: the \"chargeable\"(attribute 31)(optional) the \"create\" scenario for the \"valid\" value and the \"update\" scenario for the \"empty\" value")
    @TmsLink("MKP-949")
    public void testAttribute31CreateForValidAndUpdateForEmpty() throws InterruptedException {

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_null = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,me2uAllowed,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_null)
                .then().assertThat().statusCode(SC_CREATED);

        Map<String, Object> CurrentScenarioPost = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_testScenario);

        PutCurrentScenario(CurrentScenarioPost)
                .then().assertThat().statusCode(SC_OK);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()))
                .then()
                .body("clickatellProduct.attributes.chargeable",Matchers.hasItem(chargeable));

        val SELECT_PRODUCT_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_PRODUCT_VALUE, "31"));
        List<String> strings_1 = Lists.transform(SELECT_PRODUCT_VALUES_1, Functions.toStringFunction());
        System.out.println(strings_1);
        assertThat(strings_1)
                .contains("Y");

        val SELECT_PRODUCT_ATTRIBUTE_ID_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_ATTRIBUTE_ID);
        List<String> ProductAttributesIds_1 = Lists.transform(SELECT_PRODUCT_ATTRIBUTE_ID_VALUES_1, Functions.toStringFunction());
        System.out.println(ProductAttributesIds_1);
        assertThat(ProductAttributesIds_1)
                .contains("31");

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_30Days = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,null,me2uAllowed,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_30Days)
                .then().assertThat().statusCode(SC_CREATED);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()));

        val SELECT_PRODUCT_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT);
        List<String> strings = Lists.transform(SELECT_PRODUCT_VALUES, Functions.toStringFunction());
        System.out.println(strings);

        val SELECT_PRODUCT_ATTRIBUTE_ID_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_ATTRIBUTE_ID);
        List<String> ProductAttributesIds = Lists.transform(SELECT_PRODUCT_ATTRIBUTE_ID_VALUES, Functions.toStringFunction());
        System.out.println(ProductAttributesIds);
        assertThat(ProductAttributesIds)
                .doesNotContain("31");
//Cleanup

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        Map<String, Object> CurrentScenarioPost_Sample = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_sample);

        PutCurrentScenario(CurrentScenarioPost_Sample)
                .then().assertThat().statusCode(SC_OK);

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

    }
    @Test
    @Description("32050-vendor-routing-service :: GET \u200B/mtn\u200B/sync\u200B/invoke :: the \"chargeable\"(attribute 31)(optional) the \"create\" scenario for the \"empty\" value and the \"update\" scenario for the \"valid\" value")
    @TmsLink("MKP-546")
    public void testAttribute31CreateForEmptyAndUpdateForValid() throws InterruptedException {

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_null = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,null,me2uAllowed,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_null)
                .then().assertThat().statusCode(SC_CREATED);

        Map<String, Object> CurrentScenarioPost = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_testScenario);

        PutCurrentScenario(CurrentScenarioPost)
                .then().assertThat().statusCode(SC_OK);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()));

        val SELECT_PRODUCT_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT);
        List<String> strings = Lists.transform(SELECT_PRODUCT_VALUES, Functions.toStringFunction());
        System.out.println(strings);

        val SELECT_PRODUCT_ATTRIBUTE_ID_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_ATTRIBUTE_ID);
        List<String> ProductAttributesIds = Lists.transform(SELECT_PRODUCT_ATTRIBUTE_ID_VALUES, Functions.toStringFunction());
        System.out.println(ProductAttributesIds);
        assertThat(ProductAttributesIds)
                .doesNotContain("31");

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_30Days = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,me2uAllowed,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_30Days)
                .then().assertThat().statusCode(SC_CREATED);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()))
                .then()
                .body("clickatellProduct.attributes.chargeable",Matchers.hasItem(chargeable));

        val SELECT_PRODUCT_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_PRODUCT_VALUE, "31"));
        List<String> strings_1 = Lists.transform(SELECT_PRODUCT_VALUES_1, Functions.toStringFunction());
        System.out.println(strings_1);
        assertThat(strings_1)
                .contains("Y");

        val SELECT_PRODUCT_ATTRIBUTE_ID_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_ATTRIBUTE_ID);
        List<String> ProductAttributesIds_1 = Lists.transform(SELECT_PRODUCT_ATTRIBUTE_ID_VALUES_1, Functions.toStringFunction());
        System.out.println(ProductAttributesIds_1);
        assertThat(ProductAttributesIds_1)
                .contains("31");

        //Cleanup

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        Map<String, Object> CurrentScenarioPost_Sample = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_sample);

        PutCurrentScenario(CurrentScenarioPost_Sample)
                .then().assertThat().statusCode(SC_OK);

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

    }
    @Test
    @Description("32050-vendor-routing-service :: GET \u200B/mtn\u200B/sync\u200B/invoke :: the \"me2uAllowed\"(attribute 33)(optional) the \"create\" scenario for the \"empty\" value and the \"update\" scenario for the \"valid\" value")
    @TmsLink("MKP-624")
    public void testAttribute33CreateForEmptyAndUpdateForValid() throws InterruptedException {

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_null = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,null,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_null)
                .then().assertThat().statusCode(SC_CREATED);

        Map<String, Object> CurrentScenarioPost = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_testScenario);

        PutCurrentScenario(CurrentScenarioPost)
                .then().assertThat().statusCode(SC_OK);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()));

        val SELECT_PRODUCT_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT);
        List<String> strings = Lists.transform(SELECT_PRODUCT_VALUES, Functions.toStringFunction());
        System.out.println(strings);

        val SELECT_PRODUCT_ATTRIBUTE_ID_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_ATTRIBUTE_ID);
        List<String> ProductAttributesIds = Lists.transform(SELECT_PRODUCT_ATTRIBUTE_ID_VALUES, Functions.toStringFunction());
        System.out.println(ProductAttributesIds);
        assertThat(ProductAttributesIds)
                .doesNotContain("33");

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_30Days = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,me2uAllowed,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_30Days)
                .then().assertThat().statusCode(SC_CREATED);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()))
                .then()
                .body("clickatellProduct.attributes.me2uAllowed",Matchers.hasItem(me2uAllowed));

        val SELECT_PRODUCT_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_PRODUCT_VALUE, "33"));
        List<String> strings_1 = Lists.transform(SELECT_PRODUCT_VALUES_1, Functions.toStringFunction());
        System.out.println(strings_1);
        assertThat(strings_1)
                .contains("N");

        val SELECT_PRODUCT_ATTRIBUTE_ID_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_ATTRIBUTE_ID);
        List<String> ProductAttributesIds_1 = Lists.transform(SELECT_PRODUCT_ATTRIBUTE_ID_VALUES_1, Functions.toStringFunction());
        System.out.println(ProductAttributesIds_1);
        assertThat(ProductAttributesIds_1)
                .contains("33");

        //Cleanup

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        Map<String, Object> CurrentScenarioPost_Sample = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_sample);

        PutCurrentScenario(CurrentScenarioPost_Sample)
                .then().assertThat().statusCode(SC_OK);

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

    }
    @Test
    @Description("32050-vendor-routing-service :: GET \u200B/mtn\u200B/sync\u200B/invoke :: the \"me2uAllowed\"(attribute 33)(optional) the \"create\" scenario for the \"valid\" value and the \"update\" scenario for the \"empty\" value")
    @TmsLink("MKP-956")
    public void testAttribute33CreateForValidAndUpdateForEmpty() throws InterruptedException {

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_null = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,me2uAllowed,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_null)
                .then().assertThat().statusCode(SC_CREATED);

        Map<String, Object> CurrentScenarioPost = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_testScenario);

        PutCurrentScenario(CurrentScenarioPost)
                .then().assertThat().statusCode(SC_OK);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()))
                .then()
                .body("clickatellProduct.attributes.me2uAllowed",Matchers.hasItem(me2uAllowed));

        val SELECT_PRODUCT_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_PRODUCT_VALUE, "33"));
        List<String> strings_1 = Lists.transform(SELECT_PRODUCT_VALUES_1, Functions.toStringFunction());
        System.out.println(strings_1);
        assertThat(strings_1)
                .contains("N");

        val SELECT_PRODUCT_ATTRIBUTE_ID_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_ATTRIBUTE_ID);
        List<String> ProductAttributesIds_1 = Lists.transform(SELECT_PRODUCT_ATTRIBUTE_ID_VALUES_1, Functions.toStringFunction());
        System.out.println(ProductAttributesIds_1);
        assertThat(ProductAttributesIds_1)
                .contains("33");

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_30Days = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,null,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_30Days)
                .then().assertThat().statusCode(SC_CREATED);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()));

        val SELECT_PRODUCT_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT);
        List<String> strings = Lists.transform(SELECT_PRODUCT_VALUES, Functions.toStringFunction());
        System.out.println(strings);

        val SELECT_PRODUCT_ATTRIBUTE_ID_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_ATTRIBUTE_ID);
        List<String> ProductAttributesIds = Lists.transform(SELECT_PRODUCT_ATTRIBUTE_ID_VALUES, Functions.toStringFunction());
        System.out.println(ProductAttributesIds);
        assertThat(ProductAttributesIds)
                .doesNotContain("33");
//Cleanup

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        Map<String, Object> CurrentScenarioPost_Sample = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_sample);

        PutCurrentScenario(CurrentScenarioPost_Sample)
                .then().assertThat().statusCode(SC_OK);

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

    }
    @Test
    @Description("32050-vendor-routing-service :: GET \u200B/mtn\u200B/sync\u200B/invoke :: the \"recurringVasCode\"(attribute 34)(optional) the \"create\" scenario for the \"valid\" value and the \"update\" scenario for the \"empty\" value")
    @TmsLink("MKP-967")
    public void testAttribute34CreateForValidAndUpdateForEmpty() throws InterruptedException {

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_null = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,me2uAllowed,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_null)
                .then().assertThat().statusCode(SC_CREATED);

        Map<String, Object> CurrentScenarioPost = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_testScenario);

        PutCurrentScenario(CurrentScenarioPost)
                .then().assertThat().statusCode(SC_OK);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()))
                .then()
                .body("clickatellProduct.attributes.recurringVasCode",Matchers.hasItem(recurringVasCode));

        val SELECT_PRODUCT_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_PRODUCT_VALUE, "34"));
        List<String> strings_1 = Lists.transform(SELECT_PRODUCT_VALUES_1, Functions.toStringFunction());
        System.out.println(strings_1);
        assertThat(strings_1)
                .contains("3117");

        val SELECT_PRODUCT_ATTRIBUTE_ID_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_ATTRIBUTE_ID);
        List<String> ProductAttributesIds_1 = Lists.transform(SELECT_PRODUCT_ATTRIBUTE_ID_VALUES_1, Functions.toStringFunction());
        System.out.println(ProductAttributesIds_1);
        assertThat(ProductAttributesIds_1)
                .contains("34");

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_30Days = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,me2uAllowed,null,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_30Days)
                .then().assertThat().statusCode(SC_CREATED);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()));

        val SELECT_PRODUCT_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT);
        List<String> strings = Lists.transform(SELECT_PRODUCT_VALUES, Functions.toStringFunction());
        System.out.println(strings);

        val SELECT_PRODUCT_ATTRIBUTE_ID_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_ATTRIBUTE_ID);
        List<String> ProductAttributesIds = Lists.transform(SELECT_PRODUCT_ATTRIBUTE_ID_VALUES, Functions.toStringFunction());
        System.out.println(ProductAttributesIds);
        assertThat(ProductAttributesIds)
                .doesNotContain("34");
//Cleanup

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        Map<String, Object> CurrentScenarioPost_Sample = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_sample);

        PutCurrentScenario(CurrentScenarioPost_Sample)
                .then().assertThat().statusCode(SC_OK);

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

    }

    @Test
    @Description("32050-vendor-routing-service :: GET \u200B/mtn\u200B/sync\u200B/invoke :: the \"recurringVasCode\"(attribute 34)(optional) the \"create\" scenario for the \"empty\" value and the \"update\" scenario for the \"valid\" value")
    @TmsLink("MKP-673")
    public void testAttribute34CreateForEmptyAndUpdateForValid() throws InterruptedException {

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_null = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,me2uAllowed,null,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_null)
                .then().assertThat().statusCode(SC_CREATED);

        Map<String, Object> CurrentScenarioPost = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_testScenario);

        PutCurrentScenario(CurrentScenarioPost)
                .then().assertThat().statusCode(SC_OK);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()));

        val SELECT_PRODUCT_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT);
        List<String> strings = Lists.transform(SELECT_PRODUCT_VALUES, Functions.toStringFunction());
        System.out.println(strings);

        val SELECT_PRODUCT_ATTRIBUTE_ID_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_ATTRIBUTE_ID);
        List<String> ProductAttributesIds = Lists.transform(SELECT_PRODUCT_ATTRIBUTE_ID_VALUES, Functions.toStringFunction());
        System.out.println(ProductAttributesIds);
        assertThat(ProductAttributesIds)
                .doesNotContain("34");

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_30Days = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,me2uAllowed,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_30Days)
                .then().assertThat().statusCode(SC_CREATED);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()))
                .then()
                .body("clickatellProduct.attributes.recurringVasCode",Matchers.hasItem(recurringVasCode));

        val SELECT_PRODUCT_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_PRODUCT_VALUE, "34"));
        List<String> strings_1 = Lists.transform(SELECT_PRODUCT_VALUES_1, Functions.toStringFunction());
        System.out.println(strings_1);
        assertThat(strings_1)
                .contains("3117");

        val SELECT_PRODUCT_ATTRIBUTE_ID_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_ATTRIBUTE_ID);
        List<String> ProductAttributesIds_1 = Lists.transform(SELECT_PRODUCT_ATTRIBUTE_ID_VALUES_1, Functions.toStringFunction());
        System.out.println(ProductAttributesIds_1);
        assertThat(ProductAttributesIds_1)
                .contains("34");

        //Cleanup

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        Map<String, Object> CurrentScenarioPost_Sample = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_sample);

        PutCurrentScenario(CurrentScenarioPost_Sample)
                .then().assertThat().statusCode(SC_OK);

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

    }
    @Test
    @Description("32050-vendor-routing-service :: GET \u200B/mtn\u200B/sync\u200B/invoke :: the \"allPlatforms\"(attribute 35)(optional) the \"create\" scenario for the \"valid\" value and the \"update\" scenario for the \"empty\" value")
    @TmsLink("MKP-1122")
    public void testAttribute35CreateForValidAndUpdateForEmpty() throws InterruptedException {

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_null = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,me2uAllowed,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_null)
                .then().assertThat().statusCode(SC_CREATED);

        Map<String, Object> CurrentScenarioPost = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_testScenario);

        PutCurrentScenario(CurrentScenarioPost)
                .then().assertThat().statusCode(SC_OK);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()))
                .then()
                .body("clickatellProduct.attributes.platformType",Matchers.hasItem(allPlatforms));

        val SELECT_PRODUCT_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_PRODUCT_VALUE, "35"));
        List<String> strings_1 = Lists.transform(SELECT_PRODUCT_VALUES_1, Functions.toStringFunction());
        System.out.println(strings_1);
        assertThat(strings_1)
                .contains("Self Service");

        val SELECT_PRODUCT_ATTRIBUTE_ID_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_ATTRIBUTE_ID);
        List<String> ProductAttributesIds_1 = Lists.transform(SELECT_PRODUCT_ATTRIBUTE_ID_VALUES_1, Functions.toStringFunction());
        System.out.println(ProductAttributesIds_1);
        assertThat(ProductAttributesIds_1)
                .contains("35");

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_30Days = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,me2uAllowed,null,
                null,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_30Days)
                .then().assertThat().statusCode(SC_CREATED);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()));

        val SELECT_PRODUCT_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT);
        List<String> strings = Lists.transform(SELECT_PRODUCT_VALUES, Functions.toStringFunction());
        System.out.println(strings);

        val SELECT_PRODUCT_ATTRIBUTE_ID_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_ATTRIBUTE_ID);
        List<String> ProductAttributesIds = Lists.transform(SELECT_PRODUCT_ATTRIBUTE_ID_VALUES, Functions.toStringFunction());
        System.out.println(ProductAttributesIds);
        assertThat(ProductAttributesIds)
                .contains("35");
//Cleanup

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        Map<String, Object> CurrentScenarioPost_Sample = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_sample);

        PutCurrentScenario(CurrentScenarioPost_Sample)
                .then().assertThat().statusCode(SC_OK);

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

    }

    @Test
    @Description("32050-vendor-routing-service :: GET \u200B/mtn\u200B/sync\u200B/invoke :: the \"allPlatforms\"(attribute 35)(optional) the \"create\" scenario for the \"empty\" value and the \"update\" scenario for the \"valid\" value")
    @TmsLink("MKP-676")
    public void testAttribute35CreateForEmptyAndUpdateForValid() throws InterruptedException {

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_null = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,me2uAllowed,recurringVasCode,
                null,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data, FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync, PurchaseAmount200, FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync, PurchaseAmount200, FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync, PurchaseAmount200, FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync, PurchaseAmount200);
        PostScenario(scenarioOneProduct_null)
                .then().assertThat().statusCode(SC_CREATED);

        Map<String, Object> CurrentScenarioPost = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_testScenario);

        PutCurrentScenario(CurrentScenarioPost)
                .then().assertThat().statusCode(SC_OK);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()));

        val SELECT_PRODUCT_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_PRODUCT_VALUE, "35"));
        List<String> strings_1 = Lists.transform(SELECT_PRODUCT_VALUES_1, Functions.toStringFunction());
        System.out.println(strings_1);
        assertThat(strings_1)
                .contains("Self Service");

        val SELECT_PRODUCT_ATTRIBUTE_ID_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_ATTRIBUTE_ID);
        List<String> ProductAttributesIds = Lists.transform(SELECT_PRODUCT_ATTRIBUTE_ID_VALUES, Functions.toStringFunction());
        System.out.println(ProductAttributesIds);
        assertThat(ProductAttributesIds)
                .contains("35");

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_30Days = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,me2uAllowed,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data, FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync, PurchaseAmount200, FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync, PurchaseAmount200, FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync, PurchaseAmount200, FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync, PurchaseAmount200);
        PostScenario(scenarioOneProduct_30Days)
                .then().assertThat().statusCode(SC_CREATED);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()))
                .then()
                .body("clickatellProduct.attributes.allPlatforms",Matchers.hasItem(allPlatforms));

        val SELECT_PRODUCT_ATTRIBUTE_ID_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_ATTRIBUTE_ID);
        List<String> ProductAttributesIds_1 = Lists.transform(SELECT_PRODUCT_ATTRIBUTE_ID_VALUES_1, Functions.toStringFunction());
        System.out.println(ProductAttributesIds_1);
        assertThat(ProductAttributesIds_1)
                .contains("35");

        //Cleanup

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        Map<String, Object> CurrentScenarioPost_Sample = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_sample);

        PutCurrentScenario(CurrentScenarioPost_Sample)
                .then().assertThat().statusCode(SC_OK);

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

    }
    @Test
    @Description("32050-vendor-routing-service :: GET \u200B/mtn\u200B/sync\u200B/invoke :: the \"onceOffVasCode\"(attribute 36)(optional) the \"create\" scenario for the \"valid\" value and the \"update\" scenario for the \"empty\" value")
    @TmsLink("MKP-970")
    public void testAttribute36CreateForValidAndUpdateForEmpty() throws InterruptedException {

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_null = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,me2uAllowed,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_null)
                .then().assertThat().statusCode(SC_CREATED);

        Map<String, Object> CurrentScenarioPost = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_testScenario);

        PutCurrentScenario(CurrentScenarioPost)
                .then().assertThat().statusCode(SC_OK);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()))
                .then()
                .body("clickatellProduct.attributes.onceOffVasCode",Matchers.hasItem(onceOffVasCode));

        val SELECT_PRODUCT_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_PRODUCT_VALUE, "36"));
        List<String> strings_1 = Lists.transform(SELECT_PRODUCT_VALUES_1, Functions.toStringFunction());
        System.out.println(strings_1);
        assertThat(strings_1)
                .contains("1926");

        val SELECT_PRODUCT_ATTRIBUTE_ID_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_ATTRIBUTE_ID);
        List<String> ProductAttributesIds_1 = Lists.transform(SELECT_PRODUCT_ATTRIBUTE_ID_VALUES_1, Functions.toStringFunction());
        System.out.println(ProductAttributesIds_1);
        assertThat(ProductAttributesIds_1)
                .contains("36");

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_30Days = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,me2uAllowed,null,
                allPlatforms,null,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_30Days)
                .then().assertThat().statusCode(SC_CREATED);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()));

        val SELECT_PRODUCT_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT);
        List<String> strings = Lists.transform(SELECT_PRODUCT_VALUES, Functions.toStringFunction());
        System.out.println(strings);

        val SELECT_PRODUCT_ATTRIBUTE_ID_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_ATTRIBUTE_ID);
        List<String> ProductAttributesIds = Lists.transform(SELECT_PRODUCT_ATTRIBUTE_ID_VALUES, Functions.toStringFunction());
        System.out.println(ProductAttributesIds);
        assertThat(ProductAttributesIds)
                .doesNotContain("36");
//Cleanup

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        Map<String, Object> CurrentScenarioPost_Sample = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_sample);

        PutCurrentScenario(CurrentScenarioPost_Sample)
                .then().assertThat().statusCode(SC_OK);

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

    }
    @Test
    @Description("32050-vendor-routing-service :: GET \u200B/mtn\u200B/sync\u200B/invoke :: the \"onceOffVasCode\"(attribute 36)(optional) the \"create\" scenario for the \"empty\" value and the \"update\" scenario for the \"valid\" value")
    @TmsLink("MKP-613")
    public void testAttribute36CreateForEmptyAndUpdateForValid() throws InterruptedException {

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_null = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,me2uAllowed,recurringVasCode,
                allPlatforms,null,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_null)
                .then().assertThat().statusCode(SC_CREATED);

        Map<String, Object> CurrentScenarioPost = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_testScenario);

        PutCurrentScenario(CurrentScenarioPost)
                .then().assertThat().statusCode(SC_OK);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()));

        val SELECT_PRODUCT_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT);
        List<String> strings = Lists.transform(SELECT_PRODUCT_VALUES, Functions.toStringFunction());
        System.out.println(strings);

        val SELECT_PRODUCT_ATTRIBUTE_ID_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_ATTRIBUTE_ID);
        List<String> ProductAttributesIds = Lists.transform(SELECT_PRODUCT_ATTRIBUTE_ID_VALUES, Functions.toStringFunction());
        System.out.println(ProductAttributesIds);
        assertThat(ProductAttributesIds)
                .doesNotContain("36");

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_30Days = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,me2uAllowed,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_30Days)
                .then().assertThat().statusCode(SC_CREATED);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()))
                .then()
                .body("clickatellProduct.attributes.onceOffVasCode",Matchers.hasItem(onceOffVasCode));

        val SELECT_PRODUCT_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_PRODUCT_VALUE, "36"));
        List<String> strings_1 = Lists.transform(SELECT_PRODUCT_VALUES_1, Functions.toStringFunction());
        System.out.println(strings_1);
        assertThat(strings_1)
                .contains("1926");

        val SELECT_PRODUCT_ATTRIBUTE_ID_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_ATTRIBUTE_ID);
        List<String> ProductAttributesIds_1 = Lists.transform(SELECT_PRODUCT_ATTRIBUTE_ID_VALUES_1, Functions.toStringFunction());
        System.out.println(ProductAttributesIds_1);
        assertThat(ProductAttributesIds_1)
                .contains("36");

        //Cleanup

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        Map<String, Object> CurrentScenarioPost_Sample = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_sample);

        PutCurrentScenario(CurrentScenarioPost_Sample)
                .then().assertThat().statusCode(SC_OK);

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

    }
    @Test
    @Description("32050-vendor-routing-service :: GET \u200B/mtn\u200B/sync\u200B/invoke :: \"bundleIndicator\" (attribute 37)(optional) the \"create\" scenario for the \"empty\" value and the \"update\" scenario for the \"valid\" value")
    @TmsLink("MKP-723")
    public void testAttribute37CreateForEmptyAndUpdateForValid() throws InterruptedException {

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_null = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,me2uAllowed,recurringVasCode,
                allPlatforms,onceOffVasCode,null,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_null)
                .then().assertThat().statusCode(SC_CREATED);

        Map<String, Object> CurrentScenarioPost = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_testScenario);

        PutCurrentScenario(CurrentScenarioPost)
                .then().assertThat().statusCode(SC_OK);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()));

        val SELECT_PRODUCT_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT);
        List<String> strings = Lists.transform(SELECT_PRODUCT_VALUES, Functions.toStringFunction());
        System.out.println(strings);

        val SELECT_PRODUCT_ATTRIBUTE_ID_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_ATTRIBUTE_ID);
        List<String> ProductAttributesIds = Lists.transform(SELECT_PRODUCT_ATTRIBUTE_ID_VALUES, Functions.toStringFunction());
        System.out.println(ProductAttributesIds);
        assertThat(ProductAttributesIds)
                .doesNotContain("37");

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_30Days = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,me2uAllowed,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_30Days)
                .then().assertThat().statusCode(SC_CREATED);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()))
                .then()
                .body("clickatellProduct.attributes.bundleIndicator",Matchers.hasItem(bundleIndicator));

        val SELECT_PRODUCT_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, format(SELECT_PRODUCT_VALUE, "37"));
        List<String> strings_1 = Lists.transform(SELECT_PRODUCT_VALUES_1, Functions.toStringFunction());
        System.out.println(strings_1);
        assertThat(strings_1)
                .contains("Work and Study From Home");

        val SELECT_PRODUCT_ATTRIBUTE_ID_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_ATTRIBUTE_ID);
        List<String> ProductAttributesIds_1 = Lists.transform(SELECT_PRODUCT_ATTRIBUTE_ID_VALUES_1, Functions.toStringFunction());
        System.out.println(ProductAttributesIds_1);
        assertThat(ProductAttributesIds_1)
                .contains("37");

        //Cleanup

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        Map<String, Object> CurrentScenarioPost_Sample = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_sample);

        PutCurrentScenario(CurrentScenarioPost_Sample)
                .then().assertThat().statusCode(SC_OK);

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

    }
    @Test
    @Description("32050-vendor-routing-service :: GET \u200B/mtn\u200B/sync\u200B/invoke :: the \"isTopSeller\"(optional) the \"create\" scenario for the \"valid\" value and the \"update\" scenario for the \"empty\" value")
    @TmsLink("MKP-989")
    public void testIsTopSellerCreateForValidAndUpdateForEmpty() throws InterruptedException {

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_null = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,me2uAllowed,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_null)
                .then().assertThat().statusCode(SC_CREATED);

        Map<String, Object> CurrentScenarioPost = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_testScenario);

        PutCurrentScenario(CurrentScenarioPost)
                .then().assertThat().statusCode(SC_OK);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()))
                .then()
                .body("clickatellProduct.isTopSeller",Matchers.hasItem(true));

        val SELECT_PRODUCT_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT);
        List<String> strings_1 = Lists.transform(SELECT_PRODUCT_VALUES_1, Functions.toStringFunction());
        System.out.println(strings_1);

        val SELECT_PRODUCT_ATTRIBUTE_ID_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_TopSeller);
        List<String> ProductAttributesIds_1 = Lists.transform(SELECT_PRODUCT_ATTRIBUTE_ID_VALUES_1, Functions.toStringFunction());
        System.out.println(ProductAttributesIds_1);
        assertThat(ProductAttributesIds_1.get(0))
                .contains("true");
        assertThat(ProductAttributesIds_1.get(1))
                .contains("true");

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_30Days = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,me2uAllowed,null,
                allPlatforms,null,bundleIndicator,null,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_30Days)
                .then().assertThat().statusCode(SC_CREATED);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()));

        val SELECT_PRODUCT_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT);
        List<String> strings = Lists.transform(SELECT_PRODUCT_VALUES, Functions.toStringFunction());
        System.out.println(strings);

        val SELECT_PRODUCT_ATTRIBUTE_ID_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_TopSeller);
        List<String> ProductAttributesIds = Lists.transform(SELECT_PRODUCT_ATTRIBUTE_ID_VALUES, Functions.toStringFunction());
        System.out.println(ProductAttributesIds);
        assertThat(ProductAttributesIds.get(0))
            .isEqualTo("false");
        assertThat(ProductAttributesIds.get(1))
            .isEqualTo("false");
//Cleanup

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        Map<String, Object> CurrentScenarioPost_Sample = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_sample);

        PutCurrentScenario(CurrentScenarioPost_Sample)
                .then().assertThat().statusCode(SC_OK);

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

    }
    @Test
    @Description("32050-vendor-routing-service :: GET \u200B/mtn\u200B/sync\u200B/invoke :: the \"isTopSeller\"(optional) the \"create\" scenario for the \"empty\" value and the \"update\" scenario for the \"valid\" value")
    @TmsLink("MKP-654")
    public void testIsTopSellerCreateForEmptyAndUpdateForValid() throws InterruptedException {

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_null = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,me2uAllowed,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,null,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_null)
                .then().assertThat().statusCode(SC_CREATED);

        Map<String, Object> CurrentScenarioPost = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_testScenario);

        PutCurrentScenario(CurrentScenarioPost)
                .then().assertThat().statusCode(SC_OK);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()));

        val SELECT_PRODUCT_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT);
        List<String> strings = Lists.transform(SELECT_PRODUCT_VALUES, Functions.toStringFunction());
        System.out.println(strings);

        val SELECT_PRODUCT_TopSeller_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_TopSeller);
        List<String> ProductAttributesIds = Lists.transform(SELECT_PRODUCT_TopSeller_VALUES, Functions.toStringFunction());
        System.out.println(ProductAttributesIds);
        assertThat(ProductAttributesIds.get(0))
                .isEqualTo("false");
        assertThat(ProductAttributesIds.get(1))
                .isEqualTo("false");

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_30Days = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,me2uAllowed,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_30Days)
                .then().assertThat().statusCode(SC_CREATED);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()))
                .then()
                .body("clickatellProduct.isTopSeller",Matchers.hasItem(true));

        val SELECT_PRODUCT_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT);
        List<String> strings_1 = Lists.transform(SELECT_PRODUCT_VALUES_1, Functions.toStringFunction());
        System.out.println(strings_1);

        val SELECT_PRODUCT_TopSeller_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_TopSeller);
        List<String> ProductAttributesIds_1 = Lists.transform(SELECT_PRODUCT_TopSeller_VALUES_1, Functions.toStringFunction());
        System.out.println(ProductAttributesIds_1);
        assertThat(ProductAttributesIds_1)
                .contains("true");
        assertThat(ProductAttributesIds_1.get(1))
                .contains("true");

        //Cleanup

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        Map<String, Object> CurrentScenarioPost_Sample = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_sample);

        PutCurrentScenario(CurrentScenarioPost_Sample)
                .then().assertThat().statusCode(SC_OK);

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

    }
    @Test
    @Description("32050-vendor-routing-service :: GET \u200B/mtn\u200B/sync\u200B/invoke :: the \"topSellerOn\"(optional) the \"create\" scenario for the \"valid\" value and the \"update\" scenario for the \"empty\" value")
    @TmsLink("MKP-971")
    public void testTopSellerOnCreateForValidAndUpdateForEmpty() throws InterruptedException {

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_null = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,me2uAllowed,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_null)
                .then().assertThat().statusCode(SC_CREATED);

        Map<String, Object> CurrentScenarioPost = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_testScenario);

        PutCurrentScenario(CurrentScenarioPost)
                .then().assertThat().statusCode(SC_OK);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()))
                .then()
                .body("clickatellProduct.topSellerPlatform", Matchers.is(Arrays.asList(Arrays.asList("USSD","SMS"))));

        val SELECT_PRODUCT_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT);
        List<String> strings_1 = Lists.transform(SELECT_PRODUCT_VALUES_1, Functions.toStringFunction());
        System.out.println(strings_1);

        val SELECT_PRODUCT_TopSellerOn_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_TopSellerOn);
        List<String> ProductAttributesIds_1 = Lists.transform(SELECT_PRODUCT_TopSellerOn_VALUES_1, Functions.toStringFunction());
        System.out.println(ProductAttributesIds_1);
        assertThat(ProductAttributesIds_1.get(0))
                .contains("1");
        assertThat(ProductAttributesIds_1.get(1))
                .contains("2");
        assertThat(ProductAttributesIds_1.get(2))
                .contains("1");
        assertThat(ProductAttributesIds_1.get(3))
                .contains("2");

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_30Days = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,me2uAllowed,null,
                allPlatforms,null,bundleIndicator,isTopSeller,purchaseMedium,null,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_30Days)
                .then().assertThat().statusCode(SC_CREATED);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()));

        val SELECT_PRODUCT_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT);
        List<String> strings = Lists.transform(SELECT_PRODUCT_VALUES, Functions.toStringFunction());
        System.out.println(strings);

        val SELECT_PRODUCT_TopSellerOn_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_TopSellerOn);
        List<String> ProductAttributesIds = Lists.transform(SELECT_PRODUCT_TopSellerOn_VALUES, Functions.toStringFunction());
        System.out.println(ProductAttributesIds);
        assertThat(ProductAttributesIds)
                .isEmpty();
//Cleanup

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        Map<String, Object> CurrentScenarioPost_Sample = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_sample);

        PutCurrentScenario(CurrentScenarioPost_Sample)
                .then().assertThat().statusCode(SC_OK);

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

    }
    @Test
    @Description("32050-vendor-routing-service :: GET \u200B/mtn\u200B/sync\u200B/invoke :: the \"topSellerOn\"(optional) the \"create\" scenario for the \"empty\" value and the \"update\" scenario for the \"valid\" value")
    @TmsLink("MKP-633")
    public void testTopSellerOnCreateForEmptyAndUpdateForValid() throws InterruptedException {

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_null = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,me2uAllowed,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,null,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_null)
                .then().assertThat().statusCode(SC_CREATED);

        Map<String, Object> CurrentScenarioPost = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_testScenario);

        PutCurrentScenario(CurrentScenarioPost)
                .then().assertThat().statusCode(SC_OK);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()));

        val SELECT_PRODUCT_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT);
        List<String> strings = Lists.transform(SELECT_PRODUCT_VALUES, Functions.toStringFunction());
        System.out.println(strings);

        val SELECT_PRODUCT_TopSellerOn_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_TopSellerOn);
        List<String> ProductAttributesIds = Lists.transform(SELECT_PRODUCT_TopSellerOn_VALUES, Functions.toStringFunction());
        System.out.println(ProductAttributesIds);
        assertThat(ProductAttributesIds)
                .isEmpty();

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_30Days = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,me2uAllowed,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_30Days)
                .then().assertThat().statusCode(SC_CREATED);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()))
                .then()
                .body("clickatellProduct.topSellerPlatform", Matchers.is(Arrays.asList(Arrays.asList("USSD","SMS"))));

        val SELECT_PRODUCT_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT);
        List<String> strings_1 = Lists.transform(SELECT_PRODUCT_VALUES_1, Functions.toStringFunction());
        System.out.println(strings_1);

        val SELECT_PRODUCT_TopSellerOn_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_TopSellerOn);
        List<String> ProductAttributesIds_1 = Lists.transform(SELECT_PRODUCT_TopSellerOn_VALUES_1, Functions.toStringFunction());
        System.out.println(ProductAttributesIds_1);
        assertThat(ProductAttributesIds_1.get(0))
                .contains("1");
        assertThat(ProductAttributesIds_1.get(1))
                .contains("2");
        assertThat(ProductAttributesIds_1.get(2))
                .contains("1");
        assertThat(ProductAttributesIds_1.get(3))
                .contains("2");

        //Cleanup

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        Map<String, Object> CurrentScenarioPost_Sample = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_sample);

        PutCurrentScenario(CurrentScenarioPost_Sample)
                .then().assertThat().statusCode(SC_OK);

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

    }
    @Test
    @Description("32050-vendor-routing-service :: GET \u200B/mtn\u200B/sync\u200B/invoke :: the \"purchaseMedium\"(optional) the \"create\" scenario for the \"valid\" value and the \"update\" scenario for the \"empty\" value")
    @TmsLink("MKP-1059")
    public void testPurchaseMediumCreateForValidAndUpdateForEmpty() throws InterruptedException {

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_null = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,me2uAllowed,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_null)
                .then().assertThat().statusCode(SC_CREATED);

        Map<String, Object> CurrentScenarioPost = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_testScenario);

        PutCurrentScenario(CurrentScenarioPost)
                .then().assertThat().statusCode(SC_OK);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()))
                .then()
                .body("clickatellProduct.purchaseMedium", Matchers.is(Arrays.asList(Arrays.asList("Card","Airtime","Momo","Loyalty","All"))));

        val SELECT_PRODUCT_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT);
        List<String> strings_1 = Lists.transform(SELECT_PRODUCT_VALUES_1, Functions.toStringFunction());
        System.out.println(strings_1);

        val SELECT_PRODUCT_PURCHASE_MEDIUM_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_PURCHASE_MEDIUM);
        List<String> ProductAttributesIds_1 = Lists.transform(SELECT_PRODUCT_PURCHASE_MEDIUM_VALUES_1, Functions.toStringFunction());
        System.out.println(ProductAttributesIds_1);
        assertThat(ProductAttributesIds_1.get(0))
                .contains("1");
        assertThat(ProductAttributesIds_1.get(1))
                .contains("2");
        assertThat(ProductAttributesIds_1.get(2))
                .contains("3");
        assertThat(ProductAttributesIds_1.get(3))
                .contains("4");
        assertThat(ProductAttributesIds_1.get(4))
                .contains("5");
        assertThat(ProductAttributesIds_1.get(5))
                .contains("1");
        assertThat(ProductAttributesIds_1.get(6))
                .contains("2");
        assertThat(ProductAttributesIds_1.get(7))
                .contains("3");
        assertThat(ProductAttributesIds_1.get(8))
                .contains("4");
        assertThat(ProductAttributesIds_1.get(9))
                .contains("5");

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_30Days = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,me2uAllowed,null,
                allPlatforms,null,bundleIndicator,isTopSeller,null,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_30Days)
                .then().assertThat().statusCode(SC_CREATED);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()));

        val SELECT_PRODUCT_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT);
        List<String> strings = Lists.transform(SELECT_PRODUCT_VALUES, Functions.toStringFunction());
        System.out.println(strings);

        val SELECT_PRODUCT_PURCHASE_MEDIUM_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_PURCHASE_MEDIUM);
        List<String> ProductAttributesIds = Lists.transform(SELECT_PRODUCT_PURCHASE_MEDIUM_VALUES, Functions.toStringFunction());
        System.out.println(ProductAttributesIds);
        assertThat(ProductAttributesIds)
                .isEmpty();
//Cleanup

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        Map<String, Object> CurrentScenarioPost_Sample = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_sample);

        PutCurrentScenario(CurrentScenarioPost_Sample)
                .then().assertThat().statusCode(SC_OK);

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

    }
    @Test
    @Description("32050-vendor-routing-service :: GET \u200B/mtn\u200B/sync\u200B/invoke :: GET \u200B/mtn\u200B/sync\u200B/invoke :: the \"purchaseMedium\"(optional) the \"create\" scenario for the \"empty\" value and the \"update\" scenario for the \"valid\" value")
    @TmsLink("MKP-691")
    public void testPurchaseMediumCreateForEmptyAndUpdateForValid() throws InterruptedException {

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_null = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,me2uAllowed,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,null,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_null)
                .then().assertThat().statusCode(SC_CREATED);

        Map<String, Object> CurrentScenarioPost = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_testScenario);

        PutCurrentScenario(CurrentScenarioPost)
                .then().assertThat().statusCode(SC_OK);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()));

        val SELECT_PRODUCT_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT);
        List<String> strings = Lists.transform(SELECT_PRODUCT_VALUES, Functions.toStringFunction());
        System.out.println(strings);

        val SELECT_PRODUCT_PURCHASE_MEDIUM_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_PURCHASE_MEDIUM);
        List<String> ProductAttributesIds = Lists.transform(SELECT_PRODUCT_PURCHASE_MEDIUM_VALUES, Functions.toStringFunction());
        System.out.println(ProductAttributesIds);
        assertThat(ProductAttributesIds)
                .isEmpty();

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_30Days = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,me2uAllowed,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_30Days)
                .then().assertThat().statusCode(SC_CREATED);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()))
                .then()
                .body("clickatellProduct.purchaseMedium", Matchers.is(Arrays.asList(Arrays.asList("Card","Airtime","Momo","Loyalty","All"))));

        val SELECT_PRODUCT_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT);
        List<String> strings_1 = Lists.transform(SELECT_PRODUCT_VALUES_1, Functions.toStringFunction());
        System.out.println(strings_1.get(1));

        val SELECT_PRODUCT_PURCHASE_MEDIUM_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_PURCHASE_MEDIUM);
        List<String> ProductAttributesIds_1 = Lists.transform(SELECT_PRODUCT_PURCHASE_MEDIUM_VALUES_1, Functions.toStringFunction());
        System.out.println(ProductAttributesIds_1);
        assertThat(ProductAttributesIds_1.get(0))
                .contains("5");
        assertThat(ProductAttributesIds_1.get(1))
                .contains("3");
        assertThat(ProductAttributesIds_1.get(2))
                .contains("2");
        assertThat(ProductAttributesIds_1.get(3))
                .contains("4");
        assertThat(ProductAttributesIds_1.get(4))
                .contains("1");
        assertThat(ProductAttributesIds_1.get(5))
                .contains("5");
        assertThat(ProductAttributesIds_1.get(6))
                .contains("3");
        assertThat(ProductAttributesIds_1.get(7))
                .contains("2");
        assertThat(ProductAttributesIds_1.get(8))
                .contains("4");
        assertThat(ProductAttributesIds_1.get(9))
                .contains("1");

        //Cleanup

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        Map<String, Object> CurrentScenarioPost_Sample = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_sample);

        PutCurrentScenario(CurrentScenarioPost_Sample)
                .then().assertThat().statusCode(SC_OK);

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

    }
    @Test
    @Description("32050-vendor-routing-service :: GET \u200B/mtn\u200B/sync\u200B/invoke :: Verify that BundleDescription from MTN response is mapped to \"shortDescription\"")
    @TmsLink("MKP-1768")
    public void testBundleDescriptionCreateForValidAndUpdateForValid() throws InterruptedException {

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_null = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,me2uAllowed,recurringVasCode,
                allPlatforms,onceOffVasCode,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_null)
                .then().assertThat().statusCode(SC_CREATED);

        Map<String, Object> CurrentScenarioPost = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_testScenario);

        PutCurrentScenario(CurrentScenarioPost)
                .then().assertThat().statusCode(SC_OK);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()))
                .then()
                .body("clickatellProduct.shortDescription", Matchers.hasItem(ProductDescription));

        val SELECT_PRODUCT_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT);
        List<String> strings_1 = Lists.transform(SELECT_PRODUCT_VALUES_1, Functions.toStringFunction());
        System.out.println(strings_1);

        val SELECT_PRODUCT_SHORT_DESCRIPTION_VALUES_1 = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_SHORT_DESCRIPTION);
        List<String> ProductAttributesIds_1 = Lists.transform(SELECT_PRODUCT_SHORT_DESCRIPTION_VALUES_1, Functions.toStringFunction());
        System.out.println(ProductAttributesIds_1);
        assertThat(ProductAttributesIds_1.get(0))
                .contains(ProductDescription);
        assertThat(ProductAttributesIds_1.get(1))
                .contains(ProductDescription);

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        val scenarioOneProduct_30Days = CreateScenarioWithOneProduct(externalId_testSyncProduct,ProductDescription_Updated, FeeAmount10,bundleUom,Vendor100,period,productPrompt,chargeable,me2uAllowed,null,
                allPlatforms,null,bundleIndicator,isTopSeller,purchaseMedium,topSellerOn,chargeType,bundleCardType, null, me2uAllowed, null,null,me2uAllowed,null,
                null,null,null,null,null,null,bundleType_Data,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200,ReserveAndTransactClient.FeeAmount0,responseMessageStatusMessage,responseMessageStatusMessage,
                transactionId_MtN_Sync,ReserveAndTransactClient.PurchaseAmount200);
        PostScenario(scenarioOneProduct_30Days)
                .then().assertThat().statusCode(SC_CREATED);

        SyncInvoke(String.valueOf(DateTime.now()));
        SyncInvoke(String.valueOf(DateTime.now()));

        val SELECT_PRODUCT_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT);
        List<String> strings = Lists.transform(SELECT_PRODUCT_VALUES, Functions.toStringFunction());
        System.out.println(strings);

        val SELECT_PRODUCT_SHORT_DESCRIPTION_VALUES = executeCustomQueryAndReturnValues(MY_SQL, SELECT_PRODUCT_SHORT_DESCRIPTION);
        List<String> ProductAttributesIds = Lists.transform(SELECT_PRODUCT_SHORT_DESCRIPTION_VALUES, Functions.toStringFunction());
        System.out.println(ProductAttributesIds);
        assertThat(ProductAttributesIds.get(0))
                .contains(ProductDescription_Updated);
        assertThat(ProductAttributesIds.get(1))
                .contains(ProductDescription_Updated);
//Cleanup

        DeleteScenario(Scenario_testScenario)
                .then().assertThat().statusCode(SC_OK);

        Map<String, Object> CurrentScenarioPost_Sample = new LinkedHashMap<>();
        CurrentScenarioPost.put("currentScenarioId", Scenario_sample);

        PutCurrentScenario(CurrentScenarioPost_Sample)
                .then().assertThat().statusCode(SC_OK);

        executeCustomQuery(POSTGRES_SQL, DELETE_VENDOR_PRODUCT);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_COUNTRY);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_ATTRIBUTE_VALUE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_PURCHASE_MEDIUM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_TOP_SELLER_PLATFORM);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_SUBSCRIBER_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT_CHARGE_TYPE);
        executeCustomQuery(POSTGRES_SQL, DELETE_PRODUCT);

    }
}
