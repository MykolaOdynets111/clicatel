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
    protected static String supportUrl;
    protected static String transactionUrl;
    protected static String airtelUrl;
    protected static String mtnUrl;
    protected static String cAccountValidation;
    protected static String cAccountValidation2;
    protected static String financialTerms;
    protected static String lookupTransactionUrl;
    protected static String mnoLookupUrl;
    protected static String notificationUrl;
    protected static String productLookupUrl;
    protected static String userTransactionUrl;
    protected static String ChannelLookup;
    protected static String LineSimulator;
    protected static String RaasBankSimulator;
    protected static String AirTelSimulator;
    protected static String Users;
    protected static String PortalTransactionLookup;
    protected static String MwmSimulator;
    protected static String CtxHistory;
    protected static String secretValue;

    static {
        baseUrl = getProperty("api.base.url");
        supportUrl = getProperty("api.support.url");
        transactionUrl = getProperty("api.transaction.url");
        airtelUrl= getProperty("api.airtel.url");
        mtnUrl= getProperty("api.mtnSimulator.url");
        cAccountValidation= getProperty("api.cAccountValidation.url");
        cAccountValidation2= getProperty("api.cAccountValidationv2.url");
        financialTerms= getProperty("api.financialterms.url");
        lookupTransactionUrl = getProperty("api.lookupTransaction.url");
        mnoLookupUrl= getProperty("api.mnolookup.url");
        notificationUrl= getProperty("api.notification.url");
        productLookupUrl= getProperty("api.productlookup.url");
        userTransactionUrl= getProperty("api.usertransaction.url");
        ChannelLookup= getProperty("api.channellookup.url");
        LineSimulator= getProperty("api.lineSimulator.url");
        RaasBankSimulator= getProperty("api.raasBankSimulator.url");
        AirTelSimulator= getProperty("api.airTelSimulator.url");
        Users= getProperty("api.Users.url");
        PortalTransactionLookup= getProperty("api.portalTransactionLookup.url");
        MwmSimulator= getProperty("api.MwmSimulator.url");
        CtxHistory= getProperty("api.CtxHistory.url");
        secretValue= getProperty("api.secretValue.url");
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
