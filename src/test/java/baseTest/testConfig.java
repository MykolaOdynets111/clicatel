package baseTest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.ITestContext;
import org.testng.annotations.*;


public class testConfig {
    /*
    All .Control Request Specification
     */
    public static RequestSpecification PWM_ReserveFunds_RequestSpec;
    public static RequestSpecification PWM_ReserveFunds_Behaviour_RequestSpec;
    public static RequestSpecification PWM_Confirmation_RequestSpec;
    public static RequestSpecification PWM_Confirmation_Behaviour_RequestSpec;
    public static RequestSpecification PWM_Lookup_RequestSpec;
    public static RequestSpecification PWM_Lookup_Behaviour_RequestSpec;

    public static RequestSpecification MWM_Ping_RequestSpec;
    public static RequestSpecification MWM_Vend_RequestSpec;



    @BeforeClass

    public void setUp() {


        PWM_ReserveFunds_RequestSpec = new RequestSpecBuilder().
                setBaseUri("http://docker-minion01.dev.za01.payd.co").
                setPort(31915).
                setBasePath("/fundingsource/reserve").
                setContentType(ContentType.JSON).
                addHeader("Content-Type", "json").
                build();

        PWM_ReserveFunds_Behaviour_RequestSpec = new RequestSpecBuilder().
                setBaseUri("http://docker-minion01.dev.za01.payd.co").
                setPort(31914).
                setBasePath("/api/FUNDING_SOURCE/249/apiBehaviour/EJ-Fc5kR9HemVCuM/set/reserve").
                setContentType(ContentType.JSON).
                addHeader("Content-Type", "json").
                build();

        PWM_Confirmation_RequestSpec = new RequestSpecBuilder().
                setBaseUri("http://docker-minion01.dev.za01.payd.co").
                setPort(31915).
                setBasePath("/fundingsource/confirm").
                setContentType(ContentType.JSON).
                addHeader("Content-Type", "json").
                build();

        PWM_Confirmation_Behaviour_RequestSpec = new RequestSpecBuilder().
                setBaseUri("http://docker-minion01.dev.za01.payd.co").
                setPort(31915).
                setBasePath("/api/FUNDING_SOURCE/249/apiBehaviour/EJ-Fc5kR9HemVCuM/set/confirm").
                setContentType(ContentType.JSON).
                addHeader("Content-Type", "json").
                build();

        PWM_Lookup_RequestSpec = new RequestSpecBuilder().
                setBaseUri("http://docker-minion01.dev.za01.payd.co").
                setPort(31914).
                setBasePath("/api/FUNDING_SOURCE/249/apiTxnLookup/-aESIfw1mwedxvA-/lookup").
                setContentType(ContentType.JSON).
                addHeader("Content-Type", "json").
                build();

        PWM_Lookup_Behaviour_RequestSpec = new RequestSpecBuilder().
                setBaseUri("http://docker-minion01.dev.za01.payd.co").
                setPort(31914).
                setBasePath("/api/FUNDING_SOURCE/249/apiBehaviour/EJ-Fc5kR9HemVCuM/set/lookup").
                setContentType(ContentType.JSON).
                addHeader("Content-Type", "json").
                build();

        MWM_Ping_RequestSpec = new RequestSpecBuilder().
                setBaseUri("http://docker-minion01.dev.za01.payd.co").
                setPort(31910).
                setBasePath("/vendor/ping").
                setContentType(ContentType.JSON).
                addHeader("Content-Type", "json").
                build();

        MWM_Vend_RequestSpec = new RequestSpecBuilder().
                setBaseUri("http://docker-minion01.dev.za01.payd.co").
                setPort(31910).
                setBasePath("/vendor/vend").
                setContentType(ContentType.JSON).
                addHeader("Content-Type", "json").
                build();


    }



}

