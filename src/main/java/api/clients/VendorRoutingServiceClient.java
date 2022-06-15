package api.clients;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;
import static util.readers.PropertiesReader.getProperty;

public class VendorRoutingServiceClient extends BasedAPIClient{
    public static String Scenario_TECH106758;
    public static String externalIdTECH106758;
    public static String MtnProductSyncDesc;
    public static String chargeType_OnceOff;
    public static String bundleType_Data;
    public static String responseMessageStatusMessage;
    public static String transactionId_MtN_Sync;
    public static String Scenario_sample;
    public static String Scenario_min;
    public static String externalId_DATA_001_min;
    public static String MtnProductMinScenario;
    public static String Scenario_testScenario;
    public static String externalId_testSyncProduct;
    public static String ProductDescription;
    public static String bundleUom;
    public static String productPrompt;
    public static String chargeable;
    public static String me2uAllowed;
    public static String recurringVasCode;
    public static String allPlatforms;
    public static String onceOffVasCode;
    public static String bundleIndicator;
    public static String isTopSeller;
    public static String purchaseMedium;
    public static String topSellerOn;
    public static String chargeType;
    public static String bundleCardType;
    public static String period;
    public static String ProductDescription_Updated;
    public static String chargeType_Duplicates;
    public static String cdc_update_timestamp;
    public static String Product_1603;
    public static String Product_1604;
    public static String Pending;
    public static String vendorTransactionReference;



    static {
        Scenario_TECH106758 = getProperty("Scenario_TECH106758");
        externalIdTECH106758 = getProperty("externalIdTECH106758");
        MtnProductSyncDesc = getProperty("MtnProductSyncDesc");
        chargeType_OnceOff = getProperty("chargeType_OnceOff");
        bundleType_Data = getProperty("bundleType_Data");
        responseMessageStatusMessage = getProperty("responseMessageStatusMessage");
        transactionId_MtN_Sync = getProperty("transactionId_MtN_Sync");
        Scenario_sample = getProperty("Scenario_sample");
        Scenario_min = getProperty("Scenario_min");
        externalId_DATA_001_min = getProperty("externalId_DATA_001_min");
        MtnProductMinScenario = getProperty("MtnProductMinScenario");
        Scenario_testScenario = getProperty("Scenario_testScenario");
        externalId_testSyncProduct = getProperty("externalId_testSyncProduct");
        ProductDescription = getProperty("ProductDescription");
        bundleUom = getProperty("bundleUom");
        productPrompt = getProperty("productPrompt");
        chargeable = getProperty("chargeable");
        me2uAllowed = getProperty("me2uAllowed");
        recurringVasCode = getProperty("recurringVasCode");
        allPlatforms = getProperty("allPlatforms");
        onceOffVasCode = getProperty("onceOffVasCode");
        bundleIndicator = getProperty("bundleIndicator");
        isTopSeller = getProperty("isTopSeller");
        purchaseMedium = getProperty("purchaseMedium");
        topSellerOn = getProperty("topSellerOn");
        chargeType = getProperty("chargeType");
        bundleCardType = getProperty("bundleCardType");
        period = getProperty("period");
        ProductDescription_Updated = getProperty("ProductDescription_Updated");
        chargeType_Duplicates = getProperty("chargeType_Duplicates");
        cdc_update_timestamp = getProperty("cdc_update_timestamp");
        Product_1603 = getProperty("Product_1603");
        Product_1604 = getProperty("Product_1604");
        Pending = getProperty("Pending");
        vendorTransactionReference = getProperty("vendorTransactionReference");
    }
    public static Response DeleteScenario(String ScenarioId) {
        return basedAPIClient.get()
                .delete(new RequestSpecBuilder()
                        .setUrlEncodingEnabled(false)
                        .setBaseUri(String.format("%s/mtn/test-scenario/"+ScenarioId,mtnUrl))
                        .setContentType(JSON)
                        .addHeader("Authorization","bearer")
                        .log(ALL)
                        .build());
    }
    public static Response PostScenario(Map body) {
        return basedAPIClient.get()
                .post(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/mtn/test-scenario/",mtnUrl))
                        .setBody(body)
                        .setContentType(JSON)
                        .log(ALL)
                        .setRelaxedHTTPSValidation()
                        .build());
    }
    public static Response PutCurrentScenario(Map body) {
        return basedAPIClient.get()
                .put(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/mtn/current-test-scenario/",mtnUrl))
                        .setBody(body)
                        .setContentType(JSON)
                        .log(ALL)
                        .setRelaxedHTTPSValidation()
                        .build());
    }
    public static Response SyncInvoke(String TimeStamp) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        //.setUrlEncodingEnabled(false)
                        .setBaseUri(String.format("%s/mtn/sync/invoke", cAccountValidation2))
                        .addHeader("transactionId",TimeStamp)
                        //.setContentType(JSON)
                        .log(ALL)
                        .build());

    }
    public static Response GetAllScenarios() {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setUrlEncodingEnabled(false)
                        .setBaseUri(String.format("%s/mtn/test-scenarios",mtnUrl))
                        .setContentType(JSON)
                        .log(ALL)
                        .build());

    }
    public static Response GetCurrentScenarios() {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setUrlEncodingEnabled(false)
                        .setBaseUri(String.format("%s/mtn/current-test-scenario",mtnUrl))
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }
    public static Response GetSpecificScenario(String ScenarioID) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setUrlEncodingEnabled(false)
                        .setBaseUri(String.format("%s/mtn/test-scenario/"+ ScenarioID,mtnUrl))
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

    public static Map<String, Object> CreateScenarioWithOneProduct(String saeeId,String bundleDescription, String cost, String bundleUom, String value, String period,
                                                                   String productPrompt, String chargeable,String me2uAllowed,String recurringVasCode,String allPlatforms,
                                                                   String onceOffVasCode,String bundleIndicator,String isTopSeller, String purchaseMedium, String topSellerOn,
                                                                   String chargeType, String bundleCardType, String periodExtension, String shareable, String intellectualProperty,
                                                                   String expandedDescription, String imageUrl, String collapsedDescription, String costUom,
                                                                   String specification, String extraInfo,String partnerId,String customerFacingName, String partnerPlatform,
                                                                   String bundleType, String statusCode,String statusMessage,String supportMessage, String transactionId,String httpStatusCode,
                                                                   String statusCode_Prepaid, String statusMessage_Prepaid,String supportMessage_Prepaid, String transactionId_Prepaid,String httpStatusCode_Prepaid,
                                                                   String statusCode_Contract, String statusMessage_Contract,String supportMessage_Contract, String transactionId_Contract,String httpStatusCode_Contract,
                                                                   String statusCode_Hybrid, String statusMessage_Hybrid,String supportMessage_Hybrid, String transactionId_Hybrid,String httpStatusCode_Hybrid) {

        //Converged
        Map<String, Object> vasServices = new LinkedHashMap<>();
        vasServices.put("saeeId", saeeId);
        vasServices.put("bundleDescription", bundleDescription);
        vasServices.put("cost", cost);
        vasServices.put("bundleUom", bundleUom);
        vasServices.put("value", value);
        vasServices.put("period", period);
        vasServices.put("productPrompt", productPrompt);
        vasServices.put("chargeable", chargeable);
        vasServices.put("me2uAllowed", me2uAllowed);
        vasServices.put("recurringVasCode", recurringVasCode);
        vasServices.put("allPlatforms", allPlatforms);
        vasServices.put("onceOffVasCode", onceOffVasCode);
        vasServices.put("bundleIndicator", bundleIndicator);
        vasServices.put("isTopSeller", isTopSeller);
        vasServices.put("purchaseMedium", purchaseMedium);
        vasServices.put("topSellerOn", topSellerOn);
        vasServices.put("chargeType", chargeType);
        vasServices.put("bundleCardType", bundleCardType);
        vasServices.put("periodExtension", periodExtension);
        vasServices.put("shareable", shareable);
        vasServices.put("intellectualProperty", intellectualProperty);
        vasServices.put("expandedDescription", expandedDescription);
        vasServices.put("imageUrl", imageUrl);
        vasServices.put("collapsedDescription", collapsedDescription);
        vasServices.put("costUom", costUom);
        vasServices.put("specification", specification);
        vasServices.put("extraInfo", extraInfo);
        vasServices.put("partnerId", partnerId);
        vasServices.put("customerFacingName", customerFacingName);
        vasServices.put("partnerPlatform", partnerPlatform);

        List<Map<String, Object>> VacServices = new ArrayList<>();
        VacServices.add(vasServices);

        Map<String, Object> vas_converged = new LinkedHashMap<>();
        vas_converged.put("bundleType", bundleType);
        vas_converged.put("vasServices", VacServices);

        List<Map<String, Object>> vas_converged_List = new ArrayList<>();
        vas_converged_List.add(vas_converged);

        List<Map<String, Object>> topSellingBundlesList_Converged = new ArrayList<>();

        Map<String, Object> payload_converged = new LinkedHashMap<>();
        payload_converged.put("statusCode", statusCode);
        payload_converged.put("statusMessage", statusMessage);
        payload_converged.put("supportMessage", supportMessage);
        payload_converged.put("transactionId", transactionId);
        payload_converged.put("vas", vas_converged_List);
        payload_converged.put("topSellingBundles", topSellingBundlesList_Converged);

        Map<String, Object> subscriberResponse_converged = new LinkedHashMap<>();
        subscriberResponse_converged.put("httpStatusCode", httpStatusCode);
        subscriberResponse_converged.put("payload", payload_converged);

        Map<String, Object> scenario_converged = new LinkedHashMap<>();
        scenario_converged.put("subscriberType", "Converged");
        scenario_converged.put("subscriberResponse", subscriberResponse_converged);

//Prepaid

        List<Map<String, Object>> vas_prepaid_List = new ArrayList<>();

        List<Map<String, Object>> topSellingBundlesList_Prepaid = new ArrayList<>();

        Map<String, Object> payload_prepaid = new LinkedHashMap<>();
        payload_prepaid.put("statusCode", statusCode_Prepaid);
        payload_prepaid.put("statusMessage", statusMessage_Prepaid);
        payload_prepaid.put("supportMessage", supportMessage_Prepaid);
        payload_prepaid.put("transactionId", transactionId_Prepaid);
        payload_prepaid.put("vas", vas_prepaid_List);
        payload_prepaid.put("topSellingBundles", topSellingBundlesList_Prepaid);

        Map<String, Object> subscriberResponse_prepaid = new LinkedHashMap<>();
        subscriberResponse_prepaid.put("httpStatusCode", httpStatusCode_Prepaid);
        subscriberResponse_prepaid.put("payload", payload_prepaid);

        Map<String, Object> scenario_prepaid = new LinkedHashMap<>();
        scenario_prepaid.put("subscriberType", "Prepaid");
        scenario_prepaid.put("subscriberResponse", subscriberResponse_prepaid);
//Contract

        List<Map<String, Object>> vas_contract_List = new ArrayList<>();

        List<Map<String, Object>> topSellingBundlesList_contract = new ArrayList<>();

        Map<String, Object> payload_contract = new LinkedHashMap<>();
        payload_contract.put("statusCode", statusCode_Contract);
        payload_contract.put("statusMessage", statusMessage_Contract);
        payload_contract.put("supportMessage", supportMessage_Contract);
        payload_contract.put("transactionId", transactionId_Contract);
        payload_contract.put("vas", vas_contract_List);
        payload_contract.put("topSellingBundles", topSellingBundlesList_contract);

        Map<String, Object> subscriberResponse_contract = new LinkedHashMap<>();
        subscriberResponse_contract.put("httpStatusCode", httpStatusCode_Contract);
        subscriberResponse_contract.put("payload", payload_contract);

        Map<String, Object> scenario_contract = new LinkedHashMap<>();
        scenario_contract.put("subscriberType", "Contract");
        scenario_contract.put("subscriberResponse", subscriberResponse_contract);
//Hybrid
        List<Map<String, Object>> vas_hybrid_List = new ArrayList<>();

        List<Map<String, Object>> topSellingBundlesList_hybrid = new ArrayList<>();

        Map<String, Object> payload_hybrid = new LinkedHashMap<>();
        payload_hybrid.put("statusCode", statusCode_Hybrid);
        payload_hybrid.put("statusMessage", statusMessage_Hybrid);
        payload_hybrid.put("supportMessage", supportMessage_Hybrid);
        payload_hybrid.put("transactionId", transactionId_Hybrid);
        payload_hybrid.put("vas", vas_hybrid_List);
        payload_hybrid.put("topSellingBundles", topSellingBundlesList_hybrid);

        Map<String, Object> subscriberResponse_hybrid = new LinkedHashMap<>();
        subscriberResponse_hybrid.put("httpStatusCode", httpStatusCode_Hybrid);
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
        Scenarios.put("scenarioId", Scenario_testScenario);
        Scenarios.put("scenarios", ScenariosList);

        return Scenarios;
    }

    public static Response GetApplicationManagementV6(Map<String,String> queryParams) {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setUrlEncodingEnabled(false)
                        .setBaseUri(String.format("%s/application-management/bundle-catalogue/enterprise/proxy/api/v6/vas",mtnUrl))
                        .setContentType(JSON)
                        .addQueryParams(queryParams)
                        .log(ALL)
                        .build());
    }


}
