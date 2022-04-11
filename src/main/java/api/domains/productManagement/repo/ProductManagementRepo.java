package api.domains.productManagement.repo;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class ProductManagementRepo {
    public static Map<Object, Object> CreateDataForProductWithVendor (String ChannelIds, String ProductId)
    {

        Map<Object, Object> jsonObjectPayload = new LinkedHashMap<>();
        jsonObjectPayload.put("channelIds", Arrays.asList(ChannelIds));
        jsonObjectPayload.put("productId", ProductId);

//        Map<String,Object> properties = new LinkedHashMap<>();
//
//        jsonObjectPayload.put("properties", properties);

        return jsonObjectPayload;
    }
}
