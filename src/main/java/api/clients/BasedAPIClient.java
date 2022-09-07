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
    protected static String RaasTokenService;
    protected static String RestMessageNotifier;
    protected static String PaydVelocity;
    protected static String ControlBackend;
    protected static String PwmSimulator;
    protected static String ControlApiAdaptor;
    protected static String EnvPort;
    protected static String CtxUrl;
    protected static String CtxPort;
    protected static String VendorApiBehavior;
    protected static String fundingSourceConfig;
    protected static String AirtelEnvPort;
    protected static String MTNEnvPort;
    protected static String RAndTPort;

    static {
        baseUrl = getProperty("api.base.url");
        supportUrl = getProperty("api.support.url");
        transactionUrl = getProperty("api.transaction.url");
        airtelUrl= getProperty(("api.airtel.url"));
        mtnUrl= getProperty("api.mtnSimulator.url");
        cAccountValidation= getProperty("api.cAccountValidation.url");
        cAccountValidation2= getProperty("api.cAccountValidationv2.url");
        financialTerms= getProperty("api.financialterms.url");
        fundingSourceConfig = getProperty("api.fundingSourceConfig.url");
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
        RaasTokenService= getProperty("api.RaasTokenService.url");
        RestMessageNotifier= getProperty("api.RestMessageNotifier.url");
        PaydVelocity= getProperty("api.PaydVelocity.url");
        ControlBackend= getProperty("api.ControlBackend.url");
        PwmSimulator= getProperty("api.PwmSimulator.url");
        ControlApiAdaptor= getProperty("api.ControlApiAdaptor.url");
        EnvPort=getProperty("EnvPort");
        CtxUrl=getProperty("api.CtxUrl.url");
        CtxPort=getProperty("CtxPort");
        VendorApiBehavior=getProperty("VendorApiBehavior");
        AirtelEnvPort=getProperty("AirtelEnvPort");
        MTNEnvPort=getProperty("MTNEnvPort");
        RAndTPort=getProperty("RAndTPort");
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
    //marko
    public final Response get(final RequestSpecification requestSpecification, String pathParameters) {
        response = given()
                .filter(new ClickatellAllureRestAssured())
                .spec(requestSpecification)
                .get(pathParameters);
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
