package API.smokeTests;

import api.testUtilities.dataBuilders.testDataFactory;
import api.testUtilities.propertyConfigWrapper.configWrapper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.val;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import util.Listeners.allureApiTestListener;

import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

//TECH-60244: 30141 :: .core funding source config :: smoke

@Listeners(allureApiTestListener.class)
public class smoke_Funding_Source_Config {

    // Create properties object in order to inject environment specific variables upon build
    Properties properties = configWrapper.loadPropertiesFile("config.properties");

    // Data staging for use in test
    @DataProvider(name = "FundingSourceConfigTestCase", parallel = true)
    public Object[] FundingSourceConfigTestData() throws IOException, ParseException {
        System.out.println("----- FundingSourceConfigTestCase -----");
        return new String[][]{

                {testDataFactory.getTestData("fundingSourceConfigDataSource.json","fundingSourceConfigTestSuite","successcase","fundingSourceId"),
                        testDataFactory.getTestData("fundingSourceConfigDataSource.json","fundingSourceConfigTestSuite","successcase","expectedHTTPResponseCode"),
                        testDataFactory.getTestData("fundingSourceConfigDataSource.json","fundingSourceConfigTestSuite","successcase","expectedPaymentProviderId"),
                        testDataFactory.getTestData("fundingSourceConfigDataSource.json","fundingSourceConfigTestSuite","successcase","expectedFundingSourceName"),
                        testDataFactory.getTestData("fundingSourceConfigDataSource.json","fundingSourceConfigTestSuite","successcase","expectedTransactResultEndpoint"),
                        testDataFactory.getTestData("fundingSourceConfigDataSource.json","fundingSourceConfigTestSuite","successcase","expectedMaxRetries"),
                        testDataFactory.getTestData("fundingSourceConfigDataSource.json","fundingSourceConfigTestSuite","successcase","expectedReserveFundsEndpoint"),
                        testDataFactory.getTestData("fundingSourceConfigDataSource.json","fundingSourceConfigTestSuite","successcase","expectedReservationTimeout"),
                        testDataFactory.getTestData("fundingSourceConfigDataSource.json","fundingSourceConfigTestSuite","successcase","expectedRetryDelay"),
                        testDataFactory.getTestData("fundingSourceConfigDataSource.json","fundingSourceConfigTestSuite","successcase","expectedInitialRetryDelay"),
                        testDataFactory.getTestData("fundingSourceConfigDataSource.json","fundingSourceConfigTestSuite","successcase","expectedServiceWindowSizeSeconds"),
                        testDataFactory.getTestData("fundingSourceConfigDataSource.json","fundingSourceConfigTestSuite","successcase","expectedConfirmationWindowSizeSeconds"),
                        testDataFactory.getTestData("fundingSourceConfigDataSource.json","fundingSourceConfigTestSuite","successcase","expectedRaasTransactionHistoryEnabled"),
                        testDataFactory.getTestData("fundingSourceConfigDataSource.json","fundingSourceConfigTestSuite","successcase","expectedActive"),
                        testDataFactory.getTestData("fundingSourceConfigDataSource.json","fundingSourceConfigTestSuite","successcase","expectedSupportToken"),
                        testDataFactory.getTestData("fundingSourceConfigDataSource.json","fundingSourceConfigTestSuite","successcase","expectedTimestamp"),
                },
        };
    }

    @Step("Funding Source Config Success")
    @Test(dataProvider = "FundingSourceConfigTestCase")
    public void FundingSourceConfigSuccessTest(String fundingSourceId,
                                               String expectedHTTPResponseCode,
                                               String expectedPaymentProviderId,
                                               String expectedFundingSourceName,
                                               String expectedTransactResultEndpoint,
                                               String expectedMaxRetries,
                                               String expectedReserveFundsEndpoint,
                                               String expectedReservationTimeout,
                                               String expectedRetryDelay,
                                               String expectedInitialRetryDelay,
                                               String expectedServiceWindowSizeSeconds,
                                               String expectedConfirmationWindowSizeSeconds,
                                               String expectedRaasTransactionHistoryEnabled,
                                               String expectedActive,
                                               String expectedSupportToken,
                                               String expectedTimestamp) {

        // Notification Service GET method call
        Response fundingSourceConfigResponse =
                given()
                        .when()
                        .get(properties.getProperty("QA_MINION") + ":" + properties.getProperty("CORE_Funding_Source_Config_RequestSpec_Port") + properties.getProperty("CORE_Funding_Source_Config_RequestSpec_BasePath") + fundingSourceId)
                        .then()
                        .extract()
                        .response();

        // assertions:
        Assert.assertEquals(fundingSourceConfigResponse.statusCode(), Integer.parseInt(expectedHTTPResponseCode));
        Assert.assertNotNull(fundingSourceConfigResponse.path("paymentProviderId"), expectedPaymentProviderId);
        Assert.assertNotNull(fundingSourceConfigResponse.path("fundingSourceName"), expectedFundingSourceName);
        Assert.assertNotNull(fundingSourceConfigResponse.path("transactResultEndpoint"), expectedTransactResultEndpoint);
        Assert.assertNotNull(fundingSourceConfigResponse.path("maxRetries"), expectedMaxRetries);
        Assert.assertNotNull(fundingSourceConfigResponse.path("reserveFundsEndpoint"), expectedReserveFundsEndpoint);
        Assert.assertNotNull(fundingSourceConfigResponse.path("reservationTimeout"), expectedReservationTimeout);
        Assert.assertNotNull(fundingSourceConfigResponse.path("retryDelay"), expectedRetryDelay);
        Assert.assertNotNull(fundingSourceConfigResponse.path("initialRetryDelay"), expectedInitialRetryDelay);
        Assert.assertNotNull(fundingSourceConfigResponse.path("serviceWindowSizeSeconds"), expectedServiceWindowSizeSeconds);
        Assert.assertNotNull(fundingSourceConfigResponse.path("confirmationWindowSizeSeconds"), expectedConfirmationWindowSizeSeconds);
        Assert.assertNotNull(fundingSourceConfigResponse.path("raasTransactionHistoryEnabled"), expectedRaasTransactionHistoryEnabled);
        Assert.assertNotNull(fundingSourceConfigResponse.path("active"), expectedActive);
        Assert.assertNotNull(fundingSourceConfigResponse.path("supportToken"), expectedSupportToken);
        Assert.assertNotNull(fundingSourceConfigResponse.path("timestamp"), expectedTimestamp);

    }
}
