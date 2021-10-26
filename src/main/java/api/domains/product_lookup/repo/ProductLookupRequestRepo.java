package api.domains.product_lookup.repo;

import api.clients.BasedAPIClient;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static api.clients.BasedAPIClient.*;
import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;

public class ProductLookupRequestRepo {

    public static Map<String, Object> setUpPostNewClientData(boolean active, List clickatellSystem,
                                                             String clientId, String clientName, String countryCode,
                                                             String timezoneId, String clickatellAccountId,
                                                             String ctxLimitTotal, boolean signatureRequired) {

        Map<String,Object> jsonObjectPayload = new LinkedHashMap<>();
        jsonObjectPayload.put("active", active);
        jsonObjectPayload.put("clickatellSystem", clickatellSystem);
        jsonObjectPayload.put("clientId", clientId);
        jsonObjectPayload.put("clientName", clientName);
        jsonObjectPayload.put("countryCode", countryCode);
        jsonObjectPayload.put("properties", "");
        jsonObjectPayload.put("timezoneId", timezoneId);
        jsonObjectPayload.put("clickatellAccountId", clickatellAccountId);
        jsonObjectPayload.put("ctxLimitTotal", ctxLimitTotal);
        jsonObjectPayload.put("signatureRequired", signatureRequired);

        Map<String,Object> properties = new LinkedHashMap<>();


        jsonObjectPayload.put("properties", properties);

        return jsonObjectPayload;
    }
    public static Map<String, Object> setUpPostClientTouchFLow(String channelID, String ClientName, String Timezone, String CountryCode, String ClickaTellAccountId) {

        Map<String,Object> jsonObjectPayload = new LinkedHashMap<>();
        jsonObjectPayload.put("channelIds", Arrays.asList(channelID));
        jsonObjectPayload.put("clientName", ClientName);
        jsonObjectPayload.put("timezone", Timezone);
        jsonObjectPayload.put("countryCode", CountryCode);
        jsonObjectPayload.put("clickatellAccountId", ClickaTellAccountId);
        return jsonObjectPayload;
    }
    public static Map<String, Object> setUpPostClientTouchFLowNullClientName(String channelID, String Timezone, String CountryCode, String ClickaTellAccountId) {

        Map<String,Object> jsonObjectPayload = new LinkedHashMap<>();
        jsonObjectPayload.put("channelIds", Arrays.asList(channelID));
        jsonObjectPayload.put("timezone", Timezone);
        jsonObjectPayload.put("countryCode", CountryCode);
        jsonObjectPayload.put("clickatellAccountId", ClickaTellAccountId);
        return jsonObjectPayload;
    }
    public static Map<String, Object> setUpPostClientTouchFLowNullTimeZone(String channelID, String ClientName, String CountryCode, String ClickaTellAccountId) {

        Map<String,Object> jsonObjectPayload = new LinkedHashMap<>();
        jsonObjectPayload.put("channelIds", Arrays.asList(channelID));
        jsonObjectPayload.put("clientName", ClientName);
        jsonObjectPayload.put("countryCode", CountryCode);
        jsonObjectPayload.put("clickatellAccountId", ClickaTellAccountId);
        return jsonObjectPayload;
    }
    public static Map<String, Object> setUpPostClientTouchFLowNullCountryCode(String channelID, String ClientName, String Timezone,String ClickaTellAccountId) {

        Map<String,Object> jsonObjectPayload = new LinkedHashMap<>();
        jsonObjectPayload.put("channelIds", Arrays.asList(channelID));
        jsonObjectPayload.put("clientName", ClientName);
        jsonObjectPayload.put("timezone", Timezone);
        jsonObjectPayload.put("clickatellAccountId", ClickaTellAccountId);
        return jsonObjectPayload;
    }
    public static Map<String, Object> setUpPostClientTouchFLowNullChannelId(String ClientName, String Timezone, String CountryCode, String ClickaTellAccountId) {

        Map<String,Object> jsonObjectPayload = new LinkedHashMap<>();
        jsonObjectPayload.put("clientName", ClientName);
        jsonObjectPayload.put("timezone", Timezone);
        jsonObjectPayload.put("countryCode", CountryCode);
        jsonObjectPayload.put("clickatellAccountId", ClickaTellAccountId);
        return jsonObjectPayload;
    }
    public static Map<String, Object> setUpPostChannelsForClientTouchFLow(String channelID, String clientId) {

        Map<String,Object> jsonObjectPayload = new LinkedHashMap<>();
        jsonObjectPayload.put("channelIds", Arrays.asList(channelID));
        jsonObjectPayload.put("clientId", clientId);
        return jsonObjectPayload;
    }
    public static Map<String, Object> setUpPostChannelsForClientTouchFLow(List channelID, String clientId) {

        Map<String,Object> jsonObjectPayload = new LinkedHashMap<>();
        jsonObjectPayload.put("channelIds", channelID);
        jsonObjectPayload.put("clientId", clientId);
        return jsonObjectPayload;
    }
    public static Map<String, Object> setUpPutProductData(boolean active,
                                                             String countryCode, String description, String shortDescription,
                                                             String externalId, String id, Map pricing,
                                                             String productTypeId, String productTypeName, boolean supportToken, boolean isTopSeller,
                                                          String timestamp, String vendorId, String originalVendorId) {

        Map<String,Object> jsonObjectPayload = new LinkedHashMap<>();
        jsonObjectPayload.put("active", active);
        jsonObjectPayload.put("attributes", Arrays.asList());
        jsonObjectPayload.put("countryCode", countryCode);
        jsonObjectPayload.put("description", description);
        jsonObjectPayload.put("shortDescription", shortDescription);
        jsonObjectPayload.put("externalId", externalId);
        jsonObjectPayload.put("id", id);
        jsonObjectPayload.put("pricing", pricing);
        jsonObjectPayload.put("productTypeId", productTypeId);
        jsonObjectPayload.put("productTypeName", productTypeName);
        jsonObjectPayload.put("supportToken", supportToken);
        jsonObjectPayload.put("isTopSeller", isTopSeller);
        jsonObjectPayload.put("timestamp", timestamp);
        jsonObjectPayload.put("vendorId", vendorId);
        jsonObjectPayload.put("purchaseMediumIds", Arrays.asList());
        jsonObjectPayload.put("subscriberTypeIds", Arrays.asList());
        jsonObjectPayload.put("topSellerPlatformIds", Arrays.asList());
        jsonObjectPayload.put("originalVendorId", originalVendorId);

        return jsonObjectPayload;
    }
    public static Map<String, Object> setUpPutProductData(boolean active,
                                                          String countryCode, String description, String shortDescription,
                                                          String externalId, String id, Map pricing,
                                                          String productTypeId, String productTypeName, boolean supportToken, boolean isTopSeller,
                                                          String timestamp, String vendorId, String purchaseMediumIds, String subscriberTypeIds, String topSellerPlatformIds,
                                                          String originalVendorId) {

        Map<String,Object> jsonObjectPayload = new LinkedHashMap<>();
        jsonObjectPayload.put("active", active);
        jsonObjectPayload.put("attributes", Arrays.asList());
        jsonObjectPayload.put("countryCode", countryCode);
        jsonObjectPayload.put("description", description);
        jsonObjectPayload.put("shortDescription", shortDescription);
        jsonObjectPayload.put("externalId", externalId);
        jsonObjectPayload.put("id", id);
        jsonObjectPayload.put("pricing", pricing);
        jsonObjectPayload.put("productTypeId", productTypeId);
        jsonObjectPayload.put("productTypeName", productTypeName);
        jsonObjectPayload.put("supportToken", supportToken);
        jsonObjectPayload.put("isTopSeller", isTopSeller);
        jsonObjectPayload.put("timestamp", timestamp);
        jsonObjectPayload.put("vendorId", vendorId);
        jsonObjectPayload.put("purchaseMediumIds", Arrays.asList(purchaseMediumIds));
        jsonObjectPayload.put("subscriberTypeIds", Arrays.asList(subscriberTypeIds));
        jsonObjectPayload.put("topSellerPlatformIds", Arrays.asList(topSellerPlatformIds));
        jsonObjectPayload.put("originalVendorId", originalVendorId);

        return jsonObjectPayload;
    }
    public static Map<String, Object> setUpPutProductDataWithAttributes(boolean active, List attributes,
                                                          String countryCode, String description, String shortDescription,
                                                          String externalId, String id, Map pricing,
                                                          String productTypeId, String productTypeName, boolean supportToken, boolean isTopSeller,
                                                          String timestamp, String vendorId, String purchaseMediumIds, String subscriberTypeIds, String topSellerPlatformIds,
                                                          String originalVendorId) {

        Map<String,Object> jsonObjectPayload = new LinkedHashMap<>();
        jsonObjectPayload.put("active", active);
        jsonObjectPayload.put("attributes", attributes);
        jsonObjectPayload.put("countryCode", countryCode);
        jsonObjectPayload.put("description", description);
        jsonObjectPayload.put("shortDescription", shortDescription);
        jsonObjectPayload.put("externalId", externalId);
        jsonObjectPayload.put("id", id);
        jsonObjectPayload.put("pricing", pricing);
        jsonObjectPayload.put("productTypeId", productTypeId);
        jsonObjectPayload.put("productTypeName", productTypeName);
        jsonObjectPayload.put("supportToken", supportToken);
        jsonObjectPayload.put("isTopSeller", isTopSeller);
        jsonObjectPayload.put("timestamp", timestamp);
        jsonObjectPayload.put("vendorId", vendorId);
        jsonObjectPayload.put("purchaseMediumIds", Arrays.asList(purchaseMediumIds));
        jsonObjectPayload.put("subscriberTypeIds", Arrays.asList(subscriberTypeIds));
        jsonObjectPayload.put("topSellerPlatformIds", Arrays.asList(topSellerPlatformIds));
        jsonObjectPayload.put("originalVendorId", originalVendorId);

        return jsonObjectPayload;
    }
    public static Map<String, Object> setUpPutProductDataWithAttributesWithoutPurchaseMedium(boolean active, List attributes,
                                                                        String countryCode, String description, String shortDescription,
                                                                        String externalId, String id, Map pricing,
                                                                        String productTypeId, String productTypeName, boolean supportToken, boolean isTopSeller,
                                                                        String timestamp, String vendorId,
                                                                        String originalVendorId) {

        Map<String,Object> jsonObjectPayload = new LinkedHashMap<>();
        jsonObjectPayload.put("active", active);
        jsonObjectPayload.put("attributes", attributes);
        jsonObjectPayload.put("countryCode", countryCode);
        jsonObjectPayload.put("description", description);
        jsonObjectPayload.put("shortDescription", shortDescription);
        jsonObjectPayload.put("externalId", externalId);
        jsonObjectPayload.put("id", id);
        jsonObjectPayload.put("pricing", pricing);
        jsonObjectPayload.put("productTypeId", productTypeId);
        jsonObjectPayload.put("productTypeName", productTypeName);
        jsonObjectPayload.put("supportToken", supportToken);
        jsonObjectPayload.put("isTopSeller", isTopSeller);
        jsonObjectPayload.put("timestamp", timestamp);
        jsonObjectPayload.put("vendorId", vendorId);
        jsonObjectPayload.put("purchaseMediumIds", Arrays.asList());
        jsonObjectPayload.put("subscriberTypeIds", Arrays.asList());
        jsonObjectPayload.put("topSellerPlatformIds", Arrays.asList());
        jsonObjectPayload.put("originalVendorId", originalVendorId);

        return jsonObjectPayload;
    }


}
