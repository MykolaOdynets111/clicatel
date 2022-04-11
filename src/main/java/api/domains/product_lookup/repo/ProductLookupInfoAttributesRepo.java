package api.domains.product_lookup.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductLookupInfoAttributesRepo {
    public static List<Map<String, String>> GetAttributes99And100 (String value99, String value100)
    {

        HashMap<String, String> map_attributes_99 = new HashMap<String, String>();
        map_attributes_99.put("attributeId", "99");
        map_attributes_99.put("value", value99);

        HashMap<String, String> map_attributes_100 = new HashMap<String, String>();
        map_attributes_100.put("attributeId", "100");
        map_attributes_100.put("value", value100);

        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();

        jsonArrayPayload_1600.add(map_attributes_99);
        jsonArrayPayload_1600.add(map_attributes_100);

        return jsonArrayPayload_1600;

    }
}
