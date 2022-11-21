package api.domains.fundingSourceProxy.repo;

import java.util.LinkedHashMap;
import java.util.Map;

public class fundingSourceProxyRepo {

    public static Map<String, Object> setUpConfigureFundingSourceV1Data(String clientShareAmount, String clientTxnRef,
                                                                          String feeAmount, String raasTxnRef, String reserveAmount, String reserveFundsTxnRef,String responseCode,String settlementAmount,String timestamp,String token,String vendAmount,String vendorShareAmount ) {

        Map<String,Object> jsonObjectPayload = new LinkedHashMap<>();
        jsonObjectPayload.put("clientShareAmount", clientShareAmount);
        jsonObjectPayload.put("clientTxnRef", clientTxnRef);
        jsonObjectPayload.put("feeAmount", feeAmount);
        jsonObjectPayload.put("raasTxnRef", raasTxnRef);
        jsonObjectPayload.put("reserveFundsTxnRef", reserveFundsTxnRef);
        jsonObjectPayload.put("reserveAmount", reserveAmount);
        jsonObjectPayload.put("responseCode", responseCode);
        jsonObjectPayload.put("settlementAmount", settlementAmount);
        jsonObjectPayload.put("timestamp", timestamp);
        jsonObjectPayload.put("token", token);
        jsonObjectPayload.put("vendAmount", vendAmount);
        jsonObjectPayload.put("vendorShareAmount", vendorShareAmount);

        return jsonObjectPayload;
    }
}