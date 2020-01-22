package api.testUtilities.authentication;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class keyCloakProvider {


    public String getAccessToken() {
        String grant_type = "password";
        String clientId = ".control-portal";
        String username = "adminuser";
        String oauthPassword = "admin";
        String url = "https://accountsqa.payd.co/auth/realms/ClientPortal/protocol/openid-connect/token";

        String token;
        Response tokenResponce = given().
                header("Content-Type", "application/x-www-form-urlencoded").
                header("Cache-Control", "no-cache").
                formParam("grant_type", grant_type).
                formParam("client_id", clientId)
                .formParam("username", username)
                .formParam("password", oauthPassword)
                .when()
                .post(url).then().extract().response();

        token = tokenResponce.path("access_token");


        return token;
    }


}
