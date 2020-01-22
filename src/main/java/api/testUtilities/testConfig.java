/**
 * Represent a base test object
 * All API test should extent this class
 * Set up all Request in this class
 * Author: Adam Bethlehem
 */
package api.testUtilities;
import api.testUtilities.propertyConfigWrapper.configWrapper;


import com.codeborne.selenide.Configuration;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class testConfig {

    /**
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
    public static RequestSpecification MWM_VendLookUp_RequestSpec;



    public static RequestSpecification FM_GetClients_CLIENTS;
    public static RequestSpecification FM_GetClients_VENDORS;
    public static RequestSpecification FM_GetClients_FUNDINGSOURCES;
    public static RequestSpecification FM_GetFlows_CLIENTS;
    public static RequestSpecification FM_GetFlows_VENDORS;
    public static RequestSpecification FM_GetFlows_FUNDING_SOURCE;
    public static RequestSpecification FM_GetFlowSingle_CLIENTS;
    public static RequestSpecification FM_GetFlowSingle_VENDOR;
    public static RequestSpecification FM_GetFlowSingle_FUNDING_SOURCE;

    public static RequestSpecification FM_getChannels_FUNDING_SOURCE;
    public static RequestSpecification FM_getChannels_CLIENT;
    public static RequestSpecification FM_getChannels_VENDOR;

    public static RequestSpecification FM_getFlowSteps_CLIENT;
    public static RequestSpecification FM_getFlowSteps_FUNDINGSOURCE;
    public static RequestSpecification FM_getFlowSteps_VENDOR;

    public static RequestSpecification FM_getEndPoints_CLIENT;
    public static RequestSpecification FM_getEndPoints_VENDOR;
    public static RequestSpecification FM_getEndPoints_FUNDINGSOURCE;

    public static RequestSpecification FM_getFlowName_CLIENT;
    public static RequestSpecification FM_getFlowName_VENDOR;
    public static RequestSpecification FM_getFlowName_FUNDINGSOURCE;

    public static RequestSpecification FM_getKeyWords_CLIENT;
    public static RequestSpecification FM_getKeyWords_VENDOR;
    public static RequestSpecification FM_getKeyWords_FUNDINGSOURCE;

    public static RequestSpecification FM_getChannelConfig_CLIENT;
    public static RequestSpecification FM_getChannelConfig_VENDOR;
    public static RequestSpecification FM_getChannelConfig_FUNDINGSOURCE;

    /**
     *  All dotCore test specifications
     */

    public static RequestSpecification CORE_Transact_V4_RequestSpec;

    /**
     * Runs before the TestNG class
     * used to read in configs adn set the environments
     * Author: Adam Bethlehem
     */
    @BeforeClass
    public void setUp() {
        Properties properties = configWrapper.loadPropertiesFile("config.properties");
        String qa_refresh_Backend = properties.getProperty("QA_Refresh_BackEnd");
        String qa_minion = properties.getProperty("QA_MINION");

        /**
         * https://www.telerik.com/fiddler
         * To capture traffic through a proxy, run fiddler.exe and uncomment the below line
         */
        // RestAssured.proxy("localhost",8888);

        FM_getChannelConfig_FUNDINGSOURCE = new RequestSpecBuilder()
                .setBaseUri(qa_refresh_Backend)
                .setBasePath("/api/dev/FUNDING_SOURCE/249/channelConfig")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Host", "control-ui-backend.qa.za01.payd.co")
                .addHeader("Accept-Encoding", "Accept-Encoding")
                .addHeader("Connection", "keep-alive").build();

        FM_getChannelConfig_VENDOR = new RequestSpecBuilder()
                .setBaseUri(qa_refresh_Backend)
                .setBasePath("/api/dev/VENDOR/21/channelConfig")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Host", "control-ui-backend.qa.za01.payd.co")
                .addHeader("Accept-Encoding", "Accept-Encoding")
                .addHeader("Connection", "keep-alive").build();

        FM_getChannelConfig_CLIENT = new RequestSpecBuilder()
                .setBaseUri(qa_refresh_Backend)
                .setBasePath("/api/dev/CLIENT/42/channelConfig")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Host", "control-ui-backend.qa.za01.payd.co")
                .addHeader("Accept-Encoding", "Accept-Encoding")
                .addHeader("Connection", "keep-alive").build();


        FM_getKeyWords_FUNDINGSOURCE = new RequestSpecBuilder()
                .setBaseUri(qa_refresh_Backend)
                .setBasePath("/api/dev/FUNDING_SOURCE/249/keyword/8")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Host", "control-ui-backend.qa.za01.payd.co")
                .addHeader("Accept-Encoding", "Accept-Encoding")
                .addHeader("Connection", "keep-alive").build();

        FM_getKeyWords_VENDOR = new RequestSpecBuilder()
                .setBaseUri(qa_refresh_Backend)
                .setBasePath("/api/dev/VENDOR/21/keyword/8")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Host", "control-ui-backend.qa.za01.payd.co")
                .addHeader("Accept-Encoding", "Accept-Encoding")
                .addHeader("Connection", "keep-alive").build();
        FM_getKeyWords_CLIENT = new RequestSpecBuilder()
                .setBaseUri(qa_refresh_Backend)
                .setBasePath("/api/dev/CLIENT/42/keyword/8")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Host", "control-ui-backend.qa.za01.payd.co")
                .addHeader("Accept-Encoding", "Accept-Encoding")
                .addHeader("Connection", "keep-alive").build();


        FM_getFlowName_FUNDINGSOURCE = new RequestSpecBuilder()
                .setBaseUri(qa_refresh_Backend)
                .setBasePath("/api/dev/FUNDING_SOURCE/249/flow")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Host", "control-ui-backend.qa.za01.payd.co")
                .addHeader("Accept-Encoding", "Accept-Encoding")
                .addHeader("Connection", "keep-alive").build();

        FM_getFlowName_VENDOR = new RequestSpecBuilder()
                .setBaseUri(qa_refresh_Backend)
                .setBasePath("/api/dev/VENDOR/21/flow")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Host", "control-ui-backend.qa.za01.payd.co")
                .addHeader("Accept-Encoding", "Accept-Encoding")
                .addHeader("Connection", "keep-alive").build();

        FM_getFlowName_CLIENT = new RequestSpecBuilder()
                .setBaseUri(qa_refresh_Backend)
                .setBasePath("/api/dev/CLIENT/42/flow")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Host", "control-ui-backend.qa.za01.payd.co")
                .addHeader("Accept-Encoding", "Accept-Encoding")
                .addHeader("Connection", "keep-alive").build();

        FM_getEndPoints_FUNDINGSOURCE = new RequestSpecBuilder()
                .setBaseUri(qa_refresh_Backend)
                .setBasePath("/api/dev/FUNDING_SOURCE/249/endpoints")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Host", "control-ui-backend.qa.za01.payd.co")
                .addHeader("Accept-Encoding", "Accept-Encoding")
                .addHeader("Connection", "keep-alive").build();


        FM_getEndPoints_VENDOR = new RequestSpecBuilder()
                .setBaseUri(qa_refresh_Backend)
                .setBasePath("/api/dev/VENDOR/21/endpoints")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Host", "control-ui-backend.qa.za01.payd.co")
                .addHeader("Accept-Encoding", "Accept-Encoding")
                .addHeader("Connection", "keep-alive").build();

        FM_getEndPoints_CLIENT = new RequestSpecBuilder()
                .setBaseUri(properties.getProperty("QA_Refresh_BackEnd"))
                .setBasePath("/api/dev/CLIENT/42/endpoints")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Host", "control-ui-backend.qa.za01.payd.co")
                .addHeader("Accept-Encoding", "Accept-Encoding")
                .addHeader("Connection", "keep-alive").build();


        FM_getFlowSteps_FUNDINGSOURCE = new RequestSpecBuilder()
                .setBaseUri(qa_refresh_Backend)
                .setBasePath("/api/dev/FUNDING_SOURCE/249/steps/")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Host", "control-ui-backend.qa.za01.payd.co")
                .addHeader("Accept-Encoding", "Accept-Encoding")
                .addHeader("Connection", "keep-alive").build();
        FM_getFlowSteps_CLIENT = new RequestSpecBuilder()
                .setBaseUri(qa_refresh_Backend)
                .setBasePath("/api/dev/CLIENT/42/steps/")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Host", "control-ui-backend.qa.za01.payd.co")
                .addHeader("Accept-Encoding", "Accept-Encoding")
                .addHeader("Connection", "keep-alive").build();

        FM_getChannels_VENDOR = new RequestSpecBuilder()
                .setBaseUri(qa_refresh_Backend)
                .setBasePath("/api/dev/VENDOR/21/channels")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Host", "control-ui-backend.qa.za01.payd.co")
                .addHeader("Accept-Encoding", "Accept-Encoding")
                .addHeader("Connection", "keep-alive").build();


        FM_getChannels_CLIENT = new RequestSpecBuilder()
                .setBaseUri(qa_refresh_Backend)
                .setBasePath("/api/dev/CLIENT/42/channels")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Host", "control-ui-backend.qa.za01.payd.co")
                .addHeader("Accept-Encoding", "Accept-Encoding")
                .addHeader("Connection", "keep-alive").build();

        FM_getChannels_FUNDING_SOURCE = new RequestSpecBuilder()
                .setBaseUri(qa_refresh_Backend)
                .setBasePath("/api/dev/FUNDING_SOURCE/249/channels")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Host", "control-ui-backend.qa.za01.payd.co")
                .addHeader("Accept-Encoding", "Accept-Encoding")
                .addHeader("Connection", "keep-alive").build();
        FM_GetFlowSingle_FUNDING_SOURCE = new RequestSpecBuilder()
                .setBaseUri(qa_refresh_Backend)
                .setBasePath("/api/dev/FUNDING_SOURCE/249/flows")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Host", "control-ui-backend.qa.za01.payd.co")
                .addHeader("Accept-Encoding", "Accept-Encoding")
                .addHeader("Connection", "keep-alive").build();

        FM_GetFlowSingle_VENDOR = new RequestSpecBuilder()
                .setBaseUri(qa_refresh_Backend)
                .setBasePath("/api/dev/VENDOR/21/flows")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Host", "control-ui-backend.qa.za01.payd.co")
                .addHeader("Accept-Encoding", "Accept-Encoding")
                .addHeader("Connection", "keep-alive").build();

        FM_GetFlowSingle_CLIENTS = new RequestSpecBuilder()
                .setBaseUri(qa_refresh_Backend)
                .setBasePath("/api/dev/CLIENT/101/flows")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Host", "control-ui-backend.qa.za01.payd.co")
                .addHeader("Accept-Encoding", "Accept-Encoding")
                .addHeader("Connection", "keep-alive").build();


        FM_GetFlows_FUNDING_SOURCE = new RequestSpecBuilder()
                .setBaseUri(qa_refresh_Backend)
                .setBasePath("/api/dev/FUNDING_SOURCE/249/flows")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Host", "control-ui-backend.qa.za01.payd.co")
                .addHeader("Accept-Encoding", "Accept-Encoding")
                .addHeader("Connection", "keep-alive").build();

        FM_GetFlows_VENDORS = new RequestSpecBuilder()
                .setBaseUri(qa_refresh_Backend)
                .setBasePath("/api/dev/VENDOR/21/flows")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Host", "control-ui-backend.qa.za01.payd.co")
                .addHeader("Accept-Encoding", "Accept-Encoding")
                .addHeader("Connection", "keep-alive").build();


        FM_GetFlows_CLIENTS = new RequestSpecBuilder()
                .setBaseUri(qa_refresh_Backend)
                .setBasePath("/api/dev/CLIENT/42/flows")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Host", "control-ui-backend.qa.za01.payd.co")
                .addHeader("Accept-Encoding", "Accept-Encoding")
                .addHeader("Connection", "keep-alive").build();

        FM_GetClients_FUNDINGSOURCES = new RequestSpecBuilder()
                .setBaseUri(qa_refresh_Backend)
                .setBasePath(properties.getProperty("FM_GetClients_FUNDINGSOURCES_BasePath"))
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
//                .addHeader("Host", properties.getProperty("FM_GetClients_FUNDINGSOURCES_Host"))
                .addHeader("Accept-Encoding", "Accept-Encoding")
                .addHeader("Connection", "keep-alive").build();

        FM_GetClients_CLIENTS = new RequestSpecBuilder()
                .setBaseUri(qa_refresh_Backend)
                .setBasePath(properties.getProperty("FM_GetClients_CLIENTS_BasePath"))
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Accept-Encoding", "Accept-Encoding")
                .addHeader("Connection", "keep-alive").build();

        FM_GetClients_VENDORS = new RequestSpecBuilder()
                .setBaseUri(qa_refresh_Backend)
                .setBasePath(properties.getProperty("FM_GetClients_VENDORS_BasePath"))
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Connection", "keep-alive").build();


        PWM_ReserveFunds_RequestSpec = new RequestSpecBuilder().
                setBaseUri(qa_minion).
                setPort(Integer.parseInt(properties.getProperty("PWM_ReserveFunds_RequestSpec_Port"))).
                setBasePath(properties.getProperty("PWM_ReserveFunds_RequestSpec_BasePath")).
                setContentType(ContentType.JSON).
                addHeader("Content-Type", "json").
                addHeader("fundingSourceId", "249").
                build().log().ifValidationFails();

        PWM_ReserveFunds_Behaviour_RequestSpec = new RequestSpecBuilder().
                setBaseUri(qa_minion).
                setPort(Integer.parseInt(properties.getProperty("PWM_ReserveFunds_Behaviour_RequestSpec_Port"))).
                setBasePath(properties.getProperty("PWM_ReserveFunds_Behaviour_RequestSpec_BasePath")).
                setContentType(ContentType.JSON).
                addHeader("Content-Type", "json").
                build();

        PWM_Confirmation_RequestSpec = new RequestSpecBuilder().
                setBaseUri(qa_minion).
                setPort(Integer.parseInt(properties.getProperty("PWM_Confirmation_RequestSpec_Port"))).
                setBasePath(properties.getProperty("PWM_Confirmation_RequestSpec_BasePath")).
                setContentType(ContentType.JSON).
                addHeader("Content-Type", "json").
                addHeader("fundingSourceId", "249").
                build();

        PWM_Confirmation_Behaviour_RequestSpec = new RequestSpecBuilder().
                setBaseUri(qa_minion).
                setPort(Integer.parseInt(properties.getProperty("PWM_Confirmation_Behaviour_RequestSpec_Port"))).
                setBasePath(properties.getProperty("PWM_Confirmation_Behaviour_RequestSpec_BasePath")).
                setContentType(ContentType.JSON).
                addHeader("Content-Type", "json").
                build();

        PWM_Lookup_RequestSpec = new RequestSpecBuilder().
                setBaseUri(qa_minion).
                setPort(Integer.parseInt(properties.getProperty("PWM_Lookup_RequestSpec_Port"))).
                setBasePath(properties.getProperty("PWM_Lookup_RequestSpec_BasePath")).
                setContentType(ContentType.JSON).
                addHeader("Content-Type", "json").
                build();

        PWM_Lookup_Behaviour_RequestSpec = new RequestSpecBuilder().
                setBaseUri(qa_minion).
                setPort(Integer.parseInt(properties.getProperty("PWM_Lookup_Behaviour_RequestSpec_Port"))).
                setBasePath(properties.getProperty("PWM_Lookup_Behaviour_RequestSpec_BasePath")).
                setContentType(ContentType.JSON).
                addHeader("Content-Type", "json").
                build();

        MWM_Ping_RequestSpec = new RequestSpecBuilder().
                setBaseUri(qa_minion).
                setPort(Integer.parseInt(properties.getProperty("MWM_Ping_RequestSpec_Port"))).
                setBasePath(properties.getProperty("MWM_Ping_RequestSpec_BasePath")).
                setContentType(ContentType.JSON).
                addHeader("Content-Type", "json").
                build();

        MWM_Vend_RequestSpec = new RequestSpecBuilder().
                setBaseUri(qa_minion).
                setPort(Integer.parseInt(properties.getProperty("MWM_Vend_RequestSpec_Port"))).
                setBasePath(properties.getProperty("MWM_Vend_RequestSpec_BasePath")).
                setContentType(ContentType.JSON).
                addHeader("Content-Type", "json").
                build();

        MWM_VendLookUp_RequestSpec = new RequestSpecBuilder().
                setBaseUri(qa_minion).
                setPort(Integer.parseInt(properties.getProperty("MWM_VendLookUp_RequestSpec_Port"))).
                setBasePath(properties.getProperty("MWM_VendLookUp_RequestSpec_BasePath")).
                setContentType(ContentType.JSON).
                addHeader("Content-Type", "json").
                build();

        /**
         *  dot Core request specs
         */

        CORE_Transact_V4_RequestSpec = new RequestSpecBuilder().
                setBaseUri(qa_minion)
                .setPort(Integer.parseInt(properties.getProperty("CORE_Transact_V4_RequestSpec_Port")))
                .setBasePath(properties.getProperty("CORE_Transact_V4_RequestSpec_BasePath"))
                .setContentType(ContentType.JSON)
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Host", properties.getProperty("CORE_Transact_V4_RequestSpec_BasePath"))
                .addHeader("Accept-Encoding", "Accept-Encoding")
                .addHeader("Connection", "keep-alive").build();


    }





}

