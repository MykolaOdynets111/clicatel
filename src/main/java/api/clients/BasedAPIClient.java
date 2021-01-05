package api.clients;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import util.listeners.ClickatellAllureRestAssured;

import java.util.concurrent.atomic.AtomicReference;

import static io.restassured.RestAssured.given;
import static java.lang.Boolean.getBoolean;
import static util.readers.PropertiesReader.getProperty;

@Getter
public class BasedAPIClient {

    public static AtomicReference<BasedAPIClient> basedAPIClient = new AtomicReference<>(new BasedAPIClient());
    private Response response;
    protected static String baseUrl;

    static {
        baseUrl = getProperty("api.base.url");
    }

    private void logResponse(final Response response) {
        response
                .then()
                .log().all(getBoolean(getProperty("api.logs.print")));
    }

    public final Response get(final RequestSpecification requestSpecification) {
        response = given()
                .filter(new ClickatellAllureRestAssured())
                .spec(requestSpecification)
                .get();
        logResponse(response);
        return response;
    }

    public final Response post(final RequestSpecification requestSpecification) {
        response = given()
                .filter(new ClickatellAllureRestAssured())
                .spec(requestSpecification)
                .post();
        logResponse(response);
        return response;
    }

    public final Response put(final RequestSpecification requestSpecification) {
        response = given()
                .filter(new ClickatellAllureRestAssured())
                .spec(requestSpecification)
                .put();
        logResponse(response);
        return response;
    }

    public final Response delete(final RequestSpecification requestSpecification) {
        response = given()
                .filter(new ClickatellAllureRestAssured())
                .spec(requestSpecification)
                .delete();
        logResponse(response);
        return response;
    }
}
