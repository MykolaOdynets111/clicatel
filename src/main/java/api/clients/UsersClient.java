package api.clients;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import lombok.Getter;

import java.util.Map;
import java.util.Random;

import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;
import static util.readers.PropertiesReader.getProperty;

@Getter

public class UsersClient  extends BasedAPIClient  {

    public static String firstName;
    public static String middleName;
    public static String lastName;
    public static String language;
    public static String country;

    static {
        firstName= getProperty("firstName");
        middleName= getProperty("middleName");
        lastName= getProperty("lastName");
        language= getProperty("language");
        country= getProperty("country");

    }

    public static Response executeUsers(Map body) {
        return basedAPIClient.get()
                .post(new RequestSpecBuilder()
                        .setUrlEncodingEnabled(false)
                        .setBaseUri(String.format("%s/user",Users))
                        .setBody(body)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

    public static String getSaltString() {
        String SALTCHARS = "1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 13) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    public static String getRandomNumbers(String Numbers, int Length) {
        String SALTCHARS = Numbers;
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < Length) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }


}
