/**
 * TestSuite: Vendor Regression test suite.
 * Includes tests based on the vendor Api endpoints
 * Author: Juan-Claude Botha
 */

package API.dotCoreTests;

import api.requestLibary.CORE.coreTransactV4POJO;
import api.testUtilities.dataBuilders.RandomCharGenerator;
import api.testUtilities.testConfig;
import com.github.javafaker.Bool;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import api.requestLibary.CORE.corePOSTVendorPOJO;

import api.testUtilities.dataBuilders.testDataFactory;
import api.testUtilities.sqlDataAccessLayer.sqlDataAccess;

import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

import api.testUtilities.propertyConfigWrapper.configWrapper;

public class regression_Vendor {


    // Create properties object in order to inject environment specific variables upon build
    Properties properties = configWrapper.loadPropertiesFile("config.properties");

    // Random number for Id generation
    Integer randomnumbers = Integer.parseInt(RandomCharGenerator.getRandomNumbers(10001));

    // Data staging for use in test
    @DataProvider(name = "VendorSuccesstestcases", parallel = true)
    public Object[] VendorSuccesstestcases() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("VendorDataSource.json","vendorsuite","successcaseCreateVendor","backoff"),
                        testDataFactory.getTestData("VendorDataSource.json","vendorsuite","successcaseCreateVendor","active"),
                        testDataFactory.getTestData("VendorDataSource.json","vendorsuite","successcaseCreateVendor","maximumRequestsPerPeriod"),
                        testDataFactory.getTestData("VendorDataSource.json","vendorsuite","successcaseCreateVendor","countryId"),
                        testDataFactory.getTestData("VendorDataSource.json","vendorsuite","successcaseCreateVendor","currencyAlphaCode"),
                        testDataFactory.getTestData("VendorDataSource.json","vendorsuite","successcaseCreateVendor","retryDelay"),
                        testDataFactory.getTestData("VendorDataSource.json","vendorsuite","successcaseCreateVendor","maxRetries"),
                        testDataFactory.getTestData("VendorDataSource.json","vendorsuite","successcaseCreateVendor","retryDelayMax"),
                        testDataFactory.getTestData("VendorDataSource.json","vendorsuite","successcaseCreateVendor","name") + randomnumbers.toString(),
                        testDataFactory.getTestData("VendorDataSource.json","vendorsuite","successcaseCreateVendor","countryName") ,
                        testDataFactory.getTestData("VendorDataSource.json","vendorsuite","successcaseCreateVendor","id") + randomnumbers,
                        testDataFactory.getTestData("VendorDataSource.json","vendorsuite","successcaseCreateVendor","currencyId"),
                        testDataFactory.getTestData("VendorDataSource.json","vendorsuite","successcaseCreateVendor","requestsPeriod")},


        };
    }

    // Action step
    @Test(dataProvider = "VendorSuccesstestcases")
    public void VendorSuccesstestcases(String backoff,
                                String active,
                                String maximumRequestsPerPeriod,
                                String countryId,
                                String currencyAlphaCode,
                                String retryDelay,
                                String maxRetries,
                                String retryDelayMax,
                                String name,
                                String countryName,
                                String id,
                                String currencyId,
                                String requestsPeriod) throws IOException, InterruptedException {


        // Create Vendor payload object - contains Vendor POST request body
        corePOSTVendorPOJO vendorPostPayload = new corePOSTVendorPOJO(backoff,
                active,
                maximumRequestsPerPeriod,
                countryId,
                currencyAlphaCode,
                retryDelay,
                maxRetries,
                retryDelayMax,
                name,
                countryName,
                id,
                currencyId,
                requestsPeriod);

        // Create Vendor POST response body object - contains api response data for use in assertions or other calls
        Response POSTCreateVendorResponse =
                given()
                        .contentType(ContentType.JSON)
                        .body(vendorPostPayload)
                        .when()
                        .post(properties.getProperty("QA_MINION")+":"+properties.getProperty("Core_Vendor_Port")+properties.getProperty("CORE_Vendor_BasePath"))
                        .then()
                        .extract()
                        .response();

        // Transform data
        Integer vendorCountryId = Integer.parseInt(countryId);


        // Assertions

        // Vendor POST response assertions
        Assert.assertNotNull(POSTCreateVendorResponse.path("id"));
        Assert.assertEquals(POSTCreateVendorResponse.path("name"), name);
        Assert.assertEquals(POSTCreateVendorResponse.path("currencyId"), currencyId);
        Assert.assertEquals(POSTCreateVendorResponse.path("countryId"), vendorCountryId);

    }

}
