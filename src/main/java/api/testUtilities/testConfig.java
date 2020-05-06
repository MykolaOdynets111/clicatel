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

    public static RequestSpecification CORE_getEndPoints_FinancialTermsCalculate;
    public static RequestSpecification CORE_getEndPoints_TransactV4;
    public static RequestSpecification CORE_getEndPoints_TransactV3;
    public static RequestSpecification CORE_getEndPoints_ReserveAndTransactV4;
    public static RequestSpecification CORE_getEndPoints_ReserveAndTransactV3;
    public static RequestSpecification CORE_getEndPoints_ClientPOST;
    public static RequestSpecification CORE_getEndPoints_VendorPOST;
    public static RequestSpecification CORE_getEndPoints_CTX;
    public static RequestSpecification CORE_getEndPoints_AttributePOST;

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
        String qa_ctx = properties.getProperty("CORE_CTX_HOST");
        //String aws_product_lookup = properties.getProperty("AWS_PRODUCT_LOOKUP");
        //String aws_financial_terms = properties.getProperty("AWS_FINANCIAL_TERMS");

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

        CORE_getEndPoints_TransactV4 = new RequestSpecBuilder()
                .setBaseUri(qa_minion)
                .setBasePath(properties.getProperty("CORE_Transact_V4_RequestSpec_BasePath")).setPort(Integer.parseInt(properties.getProperty("CORE_Transact_V4_RequestSpec_Port")))
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("content-type", "application/json")
                .addHeader("Connection", "keep-alive").build();

        CORE_getEndPoints_TransactV3 = new RequestSpecBuilder()
                .setBaseUri(qa_minion)
                .setBasePath(properties.getProperty("CORE_Transact_V3_RequestSpec_BasePath")).setPort(Integer.parseInt(properties.getProperty("CORE_Transact_V3_RequestSpec_Port")))
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("content-type", "application/json")
                .addHeader("Connection", "keep-alive").build();

        CORE_getEndPoints_FinancialTermsCalculate = new RequestSpecBuilder()
                .setBaseUri(qa_minion)
                .setBasePath(properties.getProperty("CORE_FinTermsCalc_BasePath")).setPort(Integer.parseInt(properties.getProperty("CORE_FinTermsCalc_Port")))
                .addHeader("content-type", "application/json")
                .addHeader("transfer-encoding", "chunked")
                .addHeader("Accept", "*/*").build();

        CORE_getEndPoints_ReserveAndTransactV4 = new RequestSpecBuilder()
                .setBaseUri(qa_minion)
                .setBasePath(properties.getProperty("CORE_Reserve_And_Transact_V4_RequestSpec_BasePath")).setPort(Integer.parseInt(properties.getProperty("CORE_Reserve_And_Transact_V4_RequestSpec_Port")))
                .addHeader("content-type", "application/json")
                .addHeader("Accept", "*/*")
                .addHeader("connection", "keep-alive")
                .addHeader("Accept-Encoding", "gzip, deflate, br")
                .build();

        CORE_getEndPoints_ReserveAndTransactV3 = new RequestSpecBuilder()
                .setBaseUri(qa_minion)
                .setBasePath(properties.getProperty("CORE_Reserve_And_Transact_V3_RequestSpec_BasePath")).setPort(Integer.parseInt(properties.getProperty("CORE_Reserve_And_Transact_V3_RequestSpec_Port")))
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("content-type", "application/json")
                .addHeader("Connection", "keep-alive").build();

        CORE_getEndPoints_ClientPOST = new RequestSpecBuilder()
                .setBaseUri(qa_minion)
                .setBasePath(properties.getProperty("CORE_Client_BasePath")).setPort(Integer.parseInt(properties.getProperty("CORE_Client_Port")))
                .addHeader("content-type", "Appliction/json")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Connection", "keep-alive")
                .build();

        CORE_getEndPoints_VendorPOST = new RequestSpecBuilder()
                .setBaseUri(qa_minion)
                .setBasePath(properties.getProperty("CORE_Vendor_BasePath")).setPort(Integer.parseInt(properties.getProperty("CORE_Vendor_Port")))
                .addHeader("content-type", "Appliction/json")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Connection", "keep-alive")
                .build();

        CORE_getEndPoints_CTX = new RequestSpecBuilder()
                .setBaseUri(qa_ctx)
                .setBasePath(properties.getProperty("CORE_CTX_RequestSpec_BasePath")).setPort(Integer.parseInt(properties.getProperty("CORE_CTX_RequestSpec_Port")))
                .addHeader("Server", "Apache-Coyote/1.1")
                .addHeader("Content-Type", "text/xml;charset=ISO-8859-1")
                .build();

        CORE_getEndPoints_AttributePOST = new RequestSpecBuilder()
                .setBaseUri(qa_minion)
                .setBasePath(properties.getProperty("CORE_Attribute_BasePath")).setPort(Integer.parseInt(properties.getProperty("CORE_Attribute_Port")))
                .addHeader("content-type", "Appliction/json")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Connection", "keep-alive")
                .build();

    }

}

