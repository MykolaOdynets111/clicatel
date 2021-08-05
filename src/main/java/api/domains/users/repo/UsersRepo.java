package api.domains.users.repo;

import java.util.LinkedHashMap;
import java.util.Map;

public class UsersRepo {

    public static Map<String, Object> setUpUsersPostData(String firstName, String middleName,
                                                         String lastName, String language, String country, String msisdn) {

        Map<String,Object> jsonObjectPayload = new LinkedHashMap<>();
        jsonObjectPayload.put("firstName", firstName);
        jsonObjectPayload.put("middleName", middleName);
        jsonObjectPayload.put("lastName", lastName);
        jsonObjectPayload.put("language", language);
        jsonObjectPayload.put("country", country);

        Map<String,Object> identifiers = new LinkedHashMap<>();
        identifiers.put("msisdn", msisdn);

        jsonObjectPayload.put("identifiers", identifiers);

        return jsonObjectPayload;
    }
}
