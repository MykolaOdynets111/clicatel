/**
 * TestSuite: Product Regression test suite.
 * Includes Product test cases
 * Author: Juan-Claude Botha
 */

package API.dotCoreTests;

import api.requestLibary.CORE.Attributes;
import api.requestLibary.CORE.Pricing;
import api.requestLibary.CORE.corePostProductPOJO;
import api.testUtilities.testConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import api.testUtilities.dataBuilders.testDataFactory;
import api.testUtilities.sqlDataAccessLayer.sqlDataAccess;

import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

import api.testUtilities.propertyConfigWrapper.configWrapper;

public class regression_product {

    // Create properties object in order to inject environment specific variables upon build
    Properties properties = configWrapper.loadPropertiesFile("config.properties");

    // Data staging for use in test
    @DataProvider(name = "SuccessPOSTProductTestData", parallel = true)
    public Object[] SuccessPOSTProductTestData() throws IOException, ParseException {

        return new String[][]{

                {testDataFactory.getTestData("ProductDataSource.json","ProductTestSuite","successcase1","productId"),
                        testDataFactory.getTestData("ProductDataSource.json","ProductTestSuite","successcase1","active"),
                        testDataFactory.getTestData("ProductDataSource.json","ProductTestSuite","successcase1","description"),
                        testDataFactory.getTestData("ProductDataSource.json","ProductTestSuite","successcase1","supportToken"),
                        testDataFactory.getTestData("ProductDataSource.json","ProductTestSuite","successcase1","shortDescription"),
                        testDataFactory.getTestData("ProductDataSource.json","ProductTestSuite","successcase1","pricing"),
                        testDataFactory.getTestData("ProductDataSource.json","ProductTestSuite","successcase1","productTypeId"),
                        },


        };
    }

    // Action step
    @Test
    public void SuccessPOSTProductTestCase() throws IOException, InterruptedException {

        // Create transactV4 payload object - contains transactV4 request body
//        corePostProductPOJO productPayload = new corePostProductPOJO(productId,
//                active,
//                description,
//                supportToken,
//                attribute,
//                shortDescription,
//                pricing,
//                productTypeId);

        // product payload staging
        JSONObject productPOSTPayload = new JSONObject();
        productPOSTPayload.put("active", "true");
        productPOSTPayload.put("description", "AUTOMATION_PRODUCT5631");
        productPOSTPayload.put("productId", "5631");
        productPOSTPayload.put("productTypeId", "3");
        productPOSTPayload.put("shortDescription", "AUTO_PROD_SHORT5631");
        productPOSTPayload.put("supportToken", "false");
        JSONArray attributesArr = new JSONArray();
        JSONObject attribute1 = new JSONObject();
        attribute1.put("attributeId", "1");
        attribute1.put("value", "string");
        attributesArr.add(attribute1);
        JSONObject attribute2 = new JSONObject();
        attribute2.put("attributeId", "2");
        attribute2.put("value", "string");
        attributesArr.add(attribute2);
        JSONObject attribute29 = new JSONObject();
        attribute29.put("attributeId", "29");
        attribute29.put("value", "string");
        attributesArr.add(attribute29);
        JSONObject attribute14 = new JSONObject();
        attribute14.put("attributeId", "14");
        attribute14.put("value", "string");
        attributesArr.add(attribute14);
        JSONObject attribute15 = new JSONObject();
        attribute15.put("attributeId", "15");
        attribute15.put("value", "string");
        attributesArr.add(attribute15);
        productPOSTPayload.put("attributes", attributesArr);
        JSONObject pricing = new JSONObject();
        pricing.put("type", "single");
        pricing.put("amount", "100");
        productPOSTPayload.put("pricing", pricing);


        System.out.println("productJsonObject........................................" + productPOSTPayload);

        Gson gson = new GsonBuilder().create();
        Attributes x = new Attributes("1","String");

        System.out.println(x);
        Pricing p = new Pricing("10000","500","1","range");
        corePostProductPOJO productPayload = new corePostProductPOJO("7894","true", "Description","false",x,"short",p,"3");
        System.out.println("String:        "+productPayload);
//        Gson gson = new Gson();
//        String payloadString= "{"+productPayload+"]";
//        System.out.println(payloadString);
//       productPayload = gson.fromJson(payloadString.toString(), corePostProductPOJO.class);

String json = gson.toJson(productPayload);
JsonObject object=new JsonObject();
JsonObject y = object.getAsJsonObject(json);
        System.out.println(y);

        System.out.println("JSON:          "+productPayload);



        // Create transactV4 response body object - contains api response data for use in assertions or other calls
        Response POSTProductResponse =
                given()
                        .queryParam("vendorId", "22")
                        .queryParam("externalId", "")
                        .queryParam("countryCode","566")
                        .queryParam("originalVendorId", "")
                        .contentType(ContentType.JSON)
                        .body(productPOSTPayload)
                        .when()
                        .post(properties.getProperty("QA_MINION")+":"+properties.getProperty("CORE_Product_Port")+properties.getProperty("CORE_Product_BasePath"))
                        .then()
                        .extract()
                        .response();

        System.out.println(POSTProductResponse.prettyPrint());

        // Assertions
        //Assert.assertEquals(POSTProductResponse.path("test"), "");


    }

}
