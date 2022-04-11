package api.domains.product_lookup.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductLookupInfoV4Repo {
    public static List<Map<String, String>> GetAttributes (String value1, String value2, String value3,String value4, String value15, String value30,
    String value31,String value33,String value34, String value35, String value36, String value37)
    {
    HashMap<String, String> map_attributes_1 = new HashMap<String, String>();
        map_attributes_1.put("attributeId", "1");
        map_attributes_1.put("value", value1);

    HashMap<String, String> map_attributes_2 = new HashMap<String, String>();
        map_attributes_2.put("attributeId", "2");
        map_attributes_2.put("value", value2);

    HashMap<String, String> map_attributes_3 = new HashMap<String, String>();
        map_attributes_3.put("attributeId", "3");
        map_attributes_3.put("value", value3);

    HashMap<String, String> map_attributes_15 = new HashMap<String, String>();
        map_attributes_15.put("attributeId", "15");
        map_attributes_15.put("value", value15);

    HashMap<String, String> map_attributes_30 = new HashMap<String, String>();
        map_attributes_30.put("attributeId", "30");
        map_attributes_30.put("value", value30);

    HashMap<String, String> map_attributes_31 = new HashMap<String, String>();
        map_attributes_31.put("attributeId", "31");
        map_attributes_31.put("value", value31);

    HashMap<String, String> map_attributes_33 = new HashMap<String, String>();
        map_attributes_33.put("attributeId", "33");
        map_attributes_33.put("value", value33);

    HashMap<String, String> map_attributes_34 = new HashMap<String, String>();
        map_attributes_34.put("attributeId", "34");
        map_attributes_34.put("value", value34);

    HashMap<String, String> map_attributes_35 = new HashMap<String, String>();
        map_attributes_35.put("attributeId", "35");
        map_attributes_35.put("value", value35);

    HashMap<String, String> map_attributes_36 = new HashMap<String, String>();
        map_attributes_36.put("attributeId", "36");
        map_attributes_36.put("value", value36);

    HashMap<String, String> map_attributes_4 = new HashMap<String, String>();
        map_attributes_4.put("attributeId", "4");
        map_attributes_4.put("value", value4);

    HashMap<String, String> map_attributes_37 = new HashMap<String, String>();
        map_attributes_37.put("attributeId", "37");
        map_attributes_37.put("value", value37);

    List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();

        jsonArrayPayload_1600.add(map_attributes_1);
        jsonArrayPayload_1600.add(map_attributes_2);
        jsonArrayPayload_1600.add(map_attributes_3);
        jsonArrayPayload_1600.add(map_attributes_4);
        jsonArrayPayload_1600.add(map_attributes_30);
        jsonArrayPayload_1600.add(map_attributes_31);
        jsonArrayPayload_1600.add(map_attributes_33);
        jsonArrayPayload_1600.add(map_attributes_34);
        jsonArrayPayload_1600.add(map_attributes_35);
        jsonArrayPayload_1600.add(map_attributes_36);
        jsonArrayPayload_1600.add(map_attributes_37);

        return jsonArrayPayload_1600;

    }
    public static List<Map<String, String>> GetAttributes (String value1, String value2, String value3, String value15, String value30,
                                                           String value31,String value33,String value34, String value35, String value36)
    {
        HashMap<String, String> map_attributes_1 = new HashMap<String, String>();
        map_attributes_1.put("attributeId", "1");
        map_attributes_1.put("value", value1);

        HashMap<String, String> map_attributes_2 = new HashMap<String, String>();
        map_attributes_2.put("attributeId", "2");
        map_attributes_2.put("value", value2);

        HashMap<String, String> map_attributes_3 = new HashMap<String, String>();
        map_attributes_3.put("attributeId", "3");
        map_attributes_3.put("value", value3);

        HashMap<String, String> map_attributes_15 = new HashMap<String, String>();
        map_attributes_15.put("attributeId", "15");
        map_attributes_15.put("value", value15);

        HashMap<String, String> map_attributes_30 = new HashMap<String, String>();
        map_attributes_30.put("attributeId", "30");
        map_attributes_30.put("value", value30);

        HashMap<String, String> map_attributes_31 = new HashMap<String, String>();
        map_attributes_31.put("attributeId", "31");
        map_attributes_31.put("value", value31);

        HashMap<String, String> map_attributes_33 = new HashMap<String, String>();
        map_attributes_33.put("attributeId", "33");
        map_attributes_33.put("value", value33);

        HashMap<String, String> map_attributes_34 = new HashMap<String, String>();
        map_attributes_34.put("attributeId", "34");
        map_attributes_34.put("value", value34);

        HashMap<String, String> map_attributes_35 = new HashMap<String, String>();
        map_attributes_35.put("attributeId", "35");
        map_attributes_35.put("value", value35);

        HashMap<String, String> map_attributes_36 = new HashMap<String, String>();
        map_attributes_36.put("attributeId", "36");
        map_attributes_36.put("value", value36);

        List<Map<String,String>> jsonArrayPayload_1600 = new ArrayList<>();

        jsonArrayPayload_1600.add(map_attributes_1);
        jsonArrayPayload_1600.add(map_attributes_2);
        jsonArrayPayload_1600.add(map_attributes_3);
        jsonArrayPayload_1600.add(map_attributes_15);
        jsonArrayPayload_1600.add(map_attributes_30);
        jsonArrayPayload_1600.add(map_attributes_31);
        jsonArrayPayload_1600.add(map_attributes_33);
        jsonArrayPayload_1600.add(map_attributes_34);
        jsonArrayPayload_1600.add(map_attributes_35);
        jsonArrayPayload_1600.add(map_attributes_36);

        return jsonArrayPayload_1600;

    }
}
