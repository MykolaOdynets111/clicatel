package api.domains.simulator.repo;

import api.clients.BasedAPIClient;
import api.clients.ReserveAndTransactClient;
import static api.clients.VendorRoutingServiceClient.*;

import java.util.*;

public class VendorRoutingServiceRequestRepo extends BasedAPIClient {
    public static Map<String, Object> GetMinScenario() {
        //Converged
        Map<String, Object> vasServices = new LinkedHashMap<>();
        vasServices.put("saeeId", externalId_DATA_001_min);
        vasServices.put("bundleDescription", "productDescription :: test001 :: converged prepaid contract hybrid :: data :: min :: 001");
        vasServices.put("cost", "10");
        vasServices.put("bundleUom", "1 :: bundleUom :: GB");
        vasServices.put("value", "2 :: value");
        vasServices.put("period", "3 :: period :: 7 nights");
        vasServices.put("productPrompt", "30 :: productPrompt ::60 mins MTNtoMTN for R5, valid for 7 nights");
        vasServices.put("chargeable", "31 :: chargeable :: Y");
        vasServices.put("me2uAllowed", "33 :: me2uAllowed :: N");
        vasServices.put("recurringVasCode", "34 :: recurringVasCode :: 1577");
        vasServices.put("allPlatforms", "35 :: Self Service");
        vasServices.put("onceOffVasCode", "36 :: onceOffVasCode :: 1580");
        vasServices.put("bundleIndicator", null);
        vasServices.put("isTopSeller", "Yes");
        vasServices.put("purchaseMedium", "Card|Airtime|Momo|Loyalty|All");
        vasServices.put("topSellerOn", "USSD|SMS|All");
        vasServices.put("chargeType", "Once-Off|Recurring|Auto-renew|OnceOff|Autorenew");
        vasServices.put("bundleCardType", "Simple");
        vasServices.put("periodExtension", null);
        vasServices.put("shareable", "N");
        vasServices.put("intellectualProperty", null);
        vasServices.put("expandedDescription", null);
        vasServices.put("imageUrl", "N");
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
        Map<String, Object> vasServices_Prepaid = new LinkedHashMap<>();
        vasServices_Prepaid.put("saeeId", externalId_DATA_001_min);
        vasServices_Prepaid.put("bundleDescription", "productDescription :: test001 :: converged prepaid contract hybrid :: data :: min :: 001");
        vasServices_Prepaid.put("cost", "10");
        vasServices_Prepaid.put("bundleUom", "1 :: bundleUom :: GB");
        vasServices_Prepaid.put("value", "2 :: value");
        vasServices_Prepaid.put("period", "3 :: period :: 7 nights");
        vasServices_Prepaid.put("productPrompt", "30 :: productPrompt ::60 mins MTNtoMTN for R5, valid for 7 nights");
        vasServices_Prepaid.put("chargeable", "31 :: chargeable :: Y");
        vasServices_Prepaid.put("me2uAllowed", "33 :: me2uAllowed :: N");
        vasServices_Prepaid.put("recurringVasCode", "34 :: recurringVasCode :: 1577");
        vasServices_Prepaid.put("allPlatforms", "35 :: Self Service");
        vasServices_Prepaid.put("onceOffVasCode", "36 :: onceOffVasCode :: 1580");
        vasServices_Prepaid.put("bundleIndicator", null);
        vasServices_Prepaid.put("isTopSeller", "Yes");
        vasServices_Prepaid.put("purchaseMedium", "Card|Airtime|Momo|Loyalty|All");
        vasServices_Prepaid.put("topSellerOn", "USSD|SMS|All");
        vasServices_Prepaid.put("chargeType", "Once-Off|Recurring|Auto-renew|OnceOff|Autorenew");
        vasServices_Prepaid.put("bundleCardType", "Simple");
        vasServices_Prepaid.put("periodExtension", null);
        vasServices_Prepaid.put("shareable", "N");
        vasServices_Prepaid.put("intellectualProperty", null);
        vasServices_Prepaid.put("expandedDescription", null);
        vasServices_Prepaid.put("imageUrl", "N");
        vasServices_Prepaid.put("collapsedDescription", null);
        vasServices_Prepaid.put("costUom", null);
        vasServices_Prepaid.put("specification", null);
        vasServices_Prepaid.put("extraInfo", null);
        vasServices_Prepaid.put("partnerId", null);
        vasServices_Prepaid.put("customerFacingName", null);
        vasServices_Prepaid.put("partnerPlatform", null);

        List<Map<String, Object>> VacServices_Prepaid = new ArrayList<>();
        VacServices_Prepaid.add(vasServices_Prepaid);

        Map<String, Object> vas_Prepaid = new LinkedHashMap<>();
        vas_Prepaid.put("bundleType", bundleType_Data);
        vas_Prepaid.put("vasServices", VacServices_Prepaid);

        List<Map<String, Object>> vas_prepaid_List = new ArrayList<>();
        vas_prepaid_List.add(vas_Prepaid);

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
        ////Contract
        Map<String, Object> vasServices_Contract = new LinkedHashMap<>();
        vasServices_Contract.put("saeeId", externalId_DATA_001_min);
        vasServices_Contract.put("bundleDescription", "productDescription :: test001 :: converged prepaid contract hybrid :: data :: min :: 001");
        vasServices_Contract.put("cost", "10");
        vasServices_Contract.put("bundleUom", "1 :: bundleUom :: GB");
        vasServices_Contract.put("value", "2 :: value");
        vasServices_Contract.put("period", "3 :: period :: 7 nights");
        vasServices_Contract.put("productPrompt", "30 :: productPrompt ::60 mins MTNtoMTN for R5, valid for 7 nights");
        vasServices_Contract.put("chargeable", "31 :: chargeable :: Y");
        vasServices_Contract.put("me2uAllowed", "33 :: me2uAllowed :: N");
        vasServices_Contract.put("recurringVasCode", "34 :: recurringVasCode :: 1577");
        vasServices_Contract.put("allPlatforms", "35 :: Self Service");
        vasServices_Contract.put("onceOffVasCode", "36 :: onceOffVasCode :: 1580");
        vasServices_Contract.put("bundleIndicator", null);
        vasServices_Contract.put("isTopSeller", "Yes");
        vasServices_Contract.put("purchaseMedium", "Card|Airtime|Momo|Loyalty|All");
        vasServices_Contract.put("topSellerOn", "USSD|SMS|All");
        vasServices_Contract.put("chargeType", "Once-Off|Recurring|Auto-renew|OnceOff|Autorenew");
        vasServices_Contract.put("bundleCardType", "Simple");
        vasServices_Contract.put("periodExtension", null);
        vasServices_Contract.put("shareable", "N");
        vasServices_Contract.put("intellectualProperty", null);
        vasServices_Contract.put("expandedDescription", null);
        vasServices_Contract.put("imageUrl", "N");
        vasServices_Contract.put("collapsedDescription", null);
        vasServices_Contract.put("costUom", null);
        vasServices_Contract.put("specification", null);
        vasServices_Contract.put("extraInfo", null);
        vasServices_Contract.put("partnerId", null);
        vasServices_Contract.put("customerFacingName", null);
        vasServices_Contract.put("partnerPlatform", null);

        List<Map<String, Object>> VacServices_Contract = new ArrayList<>();
        VacServices_Contract.add(vasServices_Contract);

        Map<String, Object> vas_contract = new LinkedHashMap<>();
        vas_contract.put("bundleType", bundleType_Data);
        vas_contract.put("vasServices", VacServices_Contract);

        List<Map<String, Object>> vas_contract_List = new ArrayList<>();
        vas_contract_List.add(vas_contract);

        List<Map<String, Object>> topSellingBundlesList_Contract = new ArrayList<>();

        Map<String, Object> payload_contract = new LinkedHashMap<>();
        payload_contract.put("statusCode", ReserveAndTransactClient.FeeAmount0);
        payload_contract.put("statusMessage", responseMessageStatusMessage);
        payload_contract.put("supportMessage", responseMessageStatusMessage);
        payload_contract.put("transactionId", transactionId_MtN_Sync);
        payload_contract.put("vas", vas_contract_List);
        payload_contract.put("topSellingBundles", topSellingBundlesList_Contract);

        Map<String, Object> subscriberResponse_contract = new LinkedHashMap<>();
        subscriberResponse_contract.put("httpStatusCode", ReserveAndTransactClient.PurchaseAmount200);
        subscriberResponse_contract.put("payload", payload_contract);

        Map<String, Object> scenario_contract = new LinkedHashMap<>();
        scenario_contract.put("subscriberType", "Contract");
        scenario_contract.put("subscriberResponse", subscriberResponse_contract);
        //Hybrid
        Map<String, Object> vasServices_hybrid = new LinkedHashMap<>();
        vasServices_hybrid.put("saeeId", externalId_DATA_001_min);
        vasServices_hybrid.put("bundleDescription", "productDescription :: test001 :: converged prepaid contract hybrid :: data :: min :: 001");
        vasServices_hybrid.put("cost", "10");
        vasServices_hybrid.put("bundleUom", "1 :: bundleUom :: GB");
        vasServices_hybrid.put("value", "2 :: value");
        vasServices_hybrid.put("period", "3 :: period :: 7 nights");
        vasServices_hybrid.put("productPrompt", "30 :: productPrompt ::60 mins MTNtoMTN for R5, valid for 7 nights");
        vasServices_hybrid.put("chargeable", "31 :: chargeable :: Y");
        vasServices_hybrid.put("me2uAllowed", "33 :: me2uAllowed :: N");
        vasServices_hybrid.put("recurringVasCode", "34 :: recurringVasCode :: 1577");
        vasServices_hybrid.put("allPlatforms", "35 :: Self Service");
        vasServices_hybrid.put("onceOffVasCode", "36 :: onceOffVasCode :: 1580");
        vasServices_hybrid.put("bundleIndicator", null);
        vasServices_hybrid.put("isTopSeller", "Yes");
        vasServices_hybrid.put("purchaseMedium", "Card|Airtime|Momo|Loyalty|All");
        vasServices_hybrid.put("topSellerOn", "USSD|SMS|All");
        vasServices_hybrid.put("chargeType", "Once-Off|Recurring|Auto-renew|OnceOff|Autorenew");
        vasServices_hybrid.put("bundleCardType", "Simple");
        vasServices_hybrid.put("periodExtension", null);
        vasServices_hybrid.put("shareable", "N");
        vasServices_hybrid.put("intellectualProperty", null);
        vasServices_hybrid.put("expandedDescription", null);
        vasServices_hybrid.put("imageUrl", "N");
        vasServices_hybrid.put("collapsedDescription", null);
        vasServices_hybrid.put("costUom", null);
        vasServices_hybrid.put("specification", null);
        vasServices_hybrid.put("extraInfo", null);
        vasServices_hybrid.put("partnerId", null);
        vasServices_hybrid.put("customerFacingName", null);
        vasServices_hybrid.put("partnerPlatform", null);

        List<Map<String, Object>> VacServices_Hybrid = new ArrayList<>();
        VacServices_Hybrid.add(vasServices_hybrid);

        Map<String, Object> vas_hybrid = new LinkedHashMap<>();
        vas_hybrid.put("bundleType", bundleType_Data);
        vas_hybrid.put("vasServices", VacServices);

        List<Map<String, Object>> vas_Hybrid_List = new ArrayList<>();
        vas_Hybrid_List.add(vas_hybrid);

        List<Map<String, Object>> topSellingBundlesList_Hybrid = new ArrayList<>();

        Map<String, Object> payload_hybrid = new LinkedHashMap<>();
        payload_hybrid.put("statusCode", ReserveAndTransactClient.FeeAmount0);
        payload_hybrid.put("statusMessage", responseMessageStatusMessage);
        payload_hybrid.put("supportMessage", responseMessageStatusMessage);
        payload_hybrid.put("transactionId", transactionId_MtN_Sync);
        payload_hybrid.put("vas", vas_Hybrid_List);
        payload_hybrid.put("topSellingBundles", topSellingBundlesList_Hybrid);

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
        Scenarios.put("scenarioId", Scenario_min);
        Scenarios.put("scenarios", ScenariosList);

        return Scenarios;
    }

    public static Map<Object, Object> SetupSetVendData(String sourceIdentifier, String targetIdentifier, String ctxSystemResponseCode, String ctxTransactionResponseCode, String delayMs,
                                                       String vendorResponseCode, String vendorResponseMessage, String vendorTransactionReference)
    {

        Map<Object, Object> jsonObjectPayload = new LinkedHashMap<>();
        jsonObjectPayload.put("sourceIdentifier", sourceIdentifier);
        jsonObjectPayload.put("targetIdentifier", targetIdentifier);
        jsonObjectPayload.put("ctxSystemResponseCode", ctxSystemResponseCode);
        jsonObjectPayload.put("ctxTransactionResponseCode", ctxTransactionResponseCode);
        jsonObjectPayload.put("delayMs", delayMs);
        jsonObjectPayload.put("vendorResponseCode", vendorResponseCode);
        jsonObjectPayload.put("vendorResponseMessage", vendorResponseMessage);
        jsonObjectPayload.put("vendorTransactionReference", vendorTransactionReference);

        return jsonObjectPayload;

    }

}
