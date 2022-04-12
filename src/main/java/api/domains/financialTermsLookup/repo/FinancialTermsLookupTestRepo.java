package api.domains.financialTermsLookup.repo;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FinancialTermsLookupTestRepo {
    public static Map<String, Object> setUpConfigureFinTermsForClientData(String clientId, String vendorId,
                                                                   String modelId, String clientShare, String validFrom, List productIds ) {

        Map<String,Object> jsonObjectPayload = new LinkedHashMap<>();
        jsonObjectPayload.put("clientId", clientId);
        jsonObjectPayload.put("vendorId", vendorId);
        jsonObjectPayload.put("modelId", modelId);
        jsonObjectPayload.put("clientShare", clientShare);
        jsonObjectPayload.put("validFrom", validFrom);
        jsonObjectPayload.put("productIds", productIds);

        return jsonObjectPayload;
    }
}
