/**
 * TestSuite: Attribute test suite.
 * Includes all end to end api tests for Attribute CRUD operations
 * Author: Juan-Claude Botha
 */

package API.dotCoreTests;

import api.requestLibary.CORE.corePostAttributesPOJO;
import api.testUtilities.dataBuilders.testDataFactory;
import api.testUtilities.dataBuilders.RandomCharGenerator;
import api.testUtilities.propertyConfigWrapper.configWrapper;
import api.testUtilities.sqlDataAccessLayer.sqlDataAccess;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class regression_product_attributes {

    // Create properties object in order to inject environment specific variables upon build
    Properties properties = configWrapper.loadPropertiesFile("config.properties");

    // Random number for Id generation
    String randomnumbers = RandomCharGenerator.getRandomNumbers(10001);

    // Data staging for use in test
    @DataProvider(name = "Attributetestcases", parallel = true)
    public Object[] createTransactTestData() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("AttributeDatasource.json", "attributeTestSuite", "successcase1", "regex"),
                        testDataFactory.getTestData("AttributeDatasource.json", "attributeTestSuite", "successcase1", "name") + randomnumbers,
                        testDataFactory.getTestData("AttributeDatasource.json", "attributeTestSuite", "successcase1", "id") + randomnumbers,
                        testDataFactory.getTestData("AttributeDatasource.json", "attributeTestSuite", "successcase1", "type")},

        };
    }

    //Action step
    @Test(dataProvider = "Attributetestcases")
    public void successPostAttributes(String regex,
                                      String name,
                                      String id,
                                      String type){

        // Create Attribute POST payload object
        corePostAttributesPOJO AttributePayload = new corePostAttributesPOJO(regex, name, id, type);

        // Set attribute properties
        String attributeProperty = properties.getProperty("QA_MINION") + ":" + properties.getProperty("CORE_Attribute_Port") + properties.getProperty("CORE_Attribute_BasePath");

        // Create Attribute response object
        Response AttributeResponse =
                given()
                .contentType(ContentType.JSON)
                .body(AttributePayload)
                .when()
                .post(attributeProperty)
                .then()
                .extract()
                .response();

        // Assertions
        Assert.assertEquals(AttributeResponse.path("regex"), regex);
        Assert.assertEquals(AttributeResponse.path("name"), name);
        Assert.assertEquals(AttributeResponse.path("id").toString(), id);
        Assert.assertEquals(AttributeResponse.path("type"), type);

    }

}
