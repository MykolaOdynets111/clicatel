package api.domains.velocity.repo;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class VelocityRepo {
    public static Map<String, Object> setUpRaasVerifyAndRecordData(String fundingSourceId, String purchaseAmount,
                                                                       String sourceMsisdn, String transactionId) {

        Map<String,Object> jsonObjectPayload = new LinkedHashMap<>();
        jsonObjectPayload.put("fundingSourceId", fundingSourceId);
        jsonObjectPayload.put("purchaseAmount", purchaseAmount);
        jsonObjectPayload.put("sourceMsisdn", sourceMsisdn);
        jsonObjectPayload.put("transactionId", transactionId);

        return jsonObjectPayload;
    }
}
