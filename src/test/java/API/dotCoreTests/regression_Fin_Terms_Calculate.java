package API.dotCoreTests;

import api.testUtilities.sqlDataAccessLayer.sqlDataAccess;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import api.testUtilities.dataBuilders.testDataFactory;

import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

import api.testUtilities.propertyConfigWrapper.configWrapper;

import javax.sql.DataSource;

public class regression_Fin_Terms_Calculate {

    // Create properties object in order to inject environment specific variables upon build
    Properties properties = configWrapper.loadPropertiesFile("config.properties");

    // Data staging for use in test
    @DataProvider(name = "FinTermsCalculateModelATestCases", parallel = true)
    public Object[] CalcFinTermsModelATestData() throws IOException, ParseException {

        return new String[][]{

                //Model A - Percentage Share on MNO Discount
                {testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelAClientId3ProductId100PurchaseAmount15000","clientId"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelAClientId3ProductId100PurchaseAmount15000","productId"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelAClientId3ProductId100PurchaseAmount15000","expectedCurrencyCode"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelAClientId3ProductId100PurchaseAmount15000","purchaseAmount"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelAClientId3ProductId100PurchaseAmount15000","expectedClientId"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelAClientId3ProductId100PurchaseAmount15000","expectedProductId"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelAClientId3ProductId100PurchaseAmount15000","expectedPurchaseAmount"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelAClientId3ProductId100PurchaseAmount15000","expectedTermsreserveAmount"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelAClientId3ProductId100PurchaseAmount15000","expectedTermsVendAmount"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelAClientId3ProductId100PurchaseAmount15000","expectedTermsFeeAmount"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelAClientId3ProductId100PurchaseAmount15000","expectedTermsSettlementAmount"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelAClientId3ProductId100PurchaseAmount15000","expectedTermsClientShareAmount"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelAClientId3ProductId100PurchaseAmount15000","expectedTermsVendorShareAmount"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelAClientId3ProductId100PurchaseAmount15000","expectedVariablesPAYD_SHARE_AMOUNT"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelAClientId3ProductId100PurchaseAmount15000","expectedVariablesREQUESTED_PURCHASE_VALUE"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelAClientId3ProductId100PurchaseAmount15000","expectedVariablesPAYD_VAT_LIABILITY"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelAClientId3ProductId100PurchaseAmount15000","expectedVariablesVAT_PERC"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelAClientId3ProductId100PurchaseAmount15000","expectedVariablesPAYD_REVENUE"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelAClientId3ProductId100PurchaseAmount15000","expectedVariablesPAYD_GROSS_PROFIT"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelAClientId3ProductId100PurchaseAmount15000","expectedVariablesMNO_DISCOUNT_PERC"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelAClientId3ProductId100PurchaseAmount15000","expectedVariablesCLIENT_REVENUE"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelAClientId3ProductId100PurchaseAmount15000","expectedVariablesPAYD_COST_OF_GOODS_SOLD"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelAClientId3ProductId100PurchaseAmount15000","expectedVariablesTPV"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelAClientId3ProductId100PurchaseAmount15000","expectedVariablesPAYD_SHARE_PERC"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelAClientId3ProductId100PurchaseAmount15000","expectedVariablesCLIENT_SHARE_OF_MNO_DISCOUNT"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelAClientId3ProductId100PurchaseAmount15000","expectedVariablesCLIENT_SHARE_AMOUNT")
                },

        };
    }

    @Test(dataProvider = "FinTermsCalculateModelATestCases")
    public void FinTermsModelASuccessTest(String clientId,
                                         String productId,
                                         String expectedCurrencyCode,
                                         String purchaseAmount,
                                         String expectedClientId,
                                         String expectedProductId,
                                         String expectedPurchaseAmount,
                                         String expectedTermsreserveAmount,
                                         String expectedTermsVendAmount,
                                         String expectedTermsFeeAmount,
                                         String expectedTermsSettlementAmount,
                                         String expectedTermsClientShareAmount,
                                         String expectedTermsVendorShareAmount,
                                         String expectedVariablesPAYD_SHARE_AMOUNT,
                                         String expectedVariablesREQUESTED_PURCHASE_VALUE,
                                         String expectedVariablesPAYD_VAT_LIABILITY,
                                         String expectedVariablesVAT_PERC,
                                         String expectedVariablesPAYD_REVENUE,
                                         String expectedVariablesPAYD_GROSS_PROFIT,
                                         String expectedVariablesMNO_DISCOUNT_PERC,
                                         String expectedVariablesCLIENT_REVENUE,
                                         String expectedVariablesPAYD_COST_OF_GOODS_SOLD,
                                         String expectedVariablesTPV,
                                         String expectedVariablesPAYD_SHARE_PERC,
                                         String expectedVariablesCLIENT_SHARE_OF_MNO_DISCOUNT,
                                         String expectedVariablesCLIENT_SHARE_AMOUNT){

        // Financial Terms Calculate GET method call
        Response finTermsCalculateResponse =
                given()
                        .param("clientId", clientId)
                        .param("productId", productId)
                        .param("purchaseAmount", purchaseAmount)
                        .when()
                        .get(properties.getProperty("QA_MINION") + ":" + properties.getProperty("CORE_FinTermsCalc_Port") + properties.getProperty("CORE_FinTermsCalc_BasePath"))
                        .then()
                        .extract()
                        .response();


        // assertions: Request / input values
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "");
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "null");
        Assert.assertEquals(finTermsCalculateResponse.path("clientId").toString(), expectedClientId);
        Assert.assertEquals(finTermsCalculateResponse.path("productId").toString(), expectedProductId);
        Assert.assertEquals(finTermsCalculateResponse.path("currencyCode"), expectedCurrencyCode);
        Assert.assertEquals(finTermsCalculateResponse.path("purchaseAmount").toString(), expectedPurchaseAmount);

        // assertions: Terms
        Assert.assertEquals(finTermsCalculateResponse.path("terms.reserveAmount").toString(), expectedTermsreserveAmount);
        Assert.assertEquals(finTermsCalculateResponse.path("terms.vendAmount").toString(), expectedTermsVendAmount);
        Assert.assertEquals(finTermsCalculateResponse.path("terms.feeAmount").toString(), expectedTermsFeeAmount);
        Assert.assertEquals(finTermsCalculateResponse.path("terms.settlementAmount").toString(), expectedTermsSettlementAmount);
        Assert.assertEquals(finTermsCalculateResponse.path("terms.clientShareAmount").toString(), expectedTermsClientShareAmount);
        Assert.assertEquals(finTermsCalculateResponse.path("terms.vendorShareAmount").toString(), expectedTermsVendorShareAmount);

        // assertions: fin term variables
        Assert.assertEquals(finTermsCalculateResponse.path("variables.PAYD_SHARE_AMOUNT").toString(), expectedVariablesPAYD_SHARE_AMOUNT);
        Assert.assertEquals(finTermsCalculateResponse.path("variables.REQUESTED_PURCHASE_VALUE").toString(), expectedVariablesREQUESTED_PURCHASE_VALUE);
        Assert.assertEquals(finTermsCalculateResponse.path("variables.PAYD_VAT_LIABILITY").toString(), expectedVariablesPAYD_VAT_LIABILITY);
        Assert.assertEquals(finTermsCalculateResponse.path("variables.VAT_PERC").toString(), expectedVariablesVAT_PERC);
        Assert.assertEquals(finTermsCalculateResponse.path("variables.PAYD_REVENUE").toString(), expectedVariablesPAYD_REVENUE);
        Assert.assertEquals(finTermsCalculateResponse.path("variables.PAYD_GROSS_PROFIT").toString(), expectedVariablesPAYD_GROSS_PROFIT);
        Assert.assertEquals(finTermsCalculateResponse.path("variables.MNO_DISCOUNT_PERC").toString(), expectedVariablesMNO_DISCOUNT_PERC);
        Assert.assertEquals(finTermsCalculateResponse.path("variables.CLIENT_REVENUE").toString(), expectedVariablesCLIENT_REVENUE);
        Assert.assertEquals(finTermsCalculateResponse.path("variables.PAYD_COST_OF_GOODS_SOLD").toString(), expectedVariablesPAYD_COST_OF_GOODS_SOLD);
        Assert.assertEquals(finTermsCalculateResponse.path("variables.TPV").toString(), expectedVariablesTPV);
        Assert.assertEquals(finTermsCalculateResponse.path("variables.PAYD_SHARE_PERC").toString(), expectedVariablesPAYD_SHARE_PERC);
        Assert.assertEquals(finTermsCalculateResponse.path("variables.CLIENT_SHARE_OF_MNO_DISCOUNT").toString(), expectedVariablesCLIENT_SHARE_OF_MNO_DISCOUNT);
        Assert.assertEquals(finTermsCalculateResponse.path("variables.CLIENT_SHARE_AMOUNT").toString(), expectedVariablesCLIENT_SHARE_AMOUNT);

    }

    @DataProvider(name = "FinTermsCalculateModelBTestCases", parallel = true)
    public Object[] CalculateFinTermsModelBTestData() throws IOException, ParseException {

        return new String[][]{

                //Model A - Percentage Share on MNO Discount
                {testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelBClientId103ProductId903PurchaseAmount3000000","clientId"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelBClientId103ProductId903PurchaseAmount3000000","productId"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelBClientId103ProductId903PurchaseAmount3000000","expectedCurrencyCode"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelBClientId103ProductId903PurchaseAmount3000000","purchaseAmount"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelBClientId103ProductId903PurchaseAmount3000000","expectedClientId"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelBClientId103ProductId903PurchaseAmount3000000","expectedProductId"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelBClientId103ProductId903PurchaseAmount3000000","expectedPurchaseAmount"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelBClientId103ProductId903PurchaseAmount3000000","expectedTermsreserveAmount"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelBClientId103ProductId903PurchaseAmount3000000","expectedTermsVendAmount"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelBClientId103ProductId903PurchaseAmount3000000","expectedTermsFeeAmount"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelBClientId103ProductId903PurchaseAmount3000000","expectedTermsSettlementAmount"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelBClientId103ProductId903PurchaseAmount3000000","expectedTermsClientShareAmount"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelBClientId103ProductId903PurchaseAmount3000000","expectedTermsVendorShareAmount"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelBClientId103ProductId903PurchaseAmount3000000","expectedVariablesREQUESTED_PURCHASE_VALUE"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelBClientId103ProductId903PurchaseAmount3000000","expectedVariablesVAT_PERC"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelBClientId103ProductId903PurchaseAmount3000000","expectedVariablesMNO_DISCOUNT_PERC"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelBClientId103ProductId903PurchaseAmount3000000","expectedVariablesTPV"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelBClientId103ProductId903PurchaseAmount3000000","expectedVariablesCLIENT_SHARE_OF_TPV"),
                        testDataFactory.getTestData("FinTermsCalculateDataSource.json","financialTermsCalculateTestSuite","successcaseModelBClientId103ProductId903PurchaseAmount3000000","expectedVariablesCLIENT_MARKETING_COMMISSION")
                },

        };
    }

    @Test(dataProvider = "FinTermsCalculateModelBTestCases")
    public void FinTermsModelBSuccessTest(String clientId,
                                          String productId,
                                          String expectedCurrencyCode,
                                          String purchaseAmount,
                                          String expectedClientId,
                                          String expectedProductId,
                                          String expectedPurchaseAmount,
                                          String expectedTermsreserveAmount,
                                          String expectedTermsVendAmount,
                                          String expectedTermsFeeAmount,
                                          String expectedTermsSettlementAmount,
                                          String expectedTermsClientShareAmount,
                                          String expectedTermsVendorShareAmount,
                                          String expectedVariablesREQUESTED_PURCHASE_VALUE,
                                          String expectedVariablesVAT_PERC,
                                          String expectedVariablesMNO_DISCOUNT_PERC,
                                          String expectedVariablesTPV,
                                          String expectedVariablesCLIENT_SHARE_OF_TPV,
                                          String expectedVariablesCLIENT_MARKETING_COMMISSION){


        // Financial Terms Calculate GET method call
        Response finTermsCalculateResponse =
                given()
                        .param("clientId", clientId)
                        .param("productId", productId)
                        .param("purchaseAmount", purchaseAmount)
                        .when()
                        .get(properties.getProperty("QA_MINION") + ":" + properties.getProperty("CORE_FinTermsCalc_Port") + properties.getProperty("CORE_FinTermsCalc_BasePath"))
                        .then()
                        .extract()
                        .response();

        // assertions: Request / input values
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "");
        Assert.assertNotEquals(finTermsCalculateResponse.path("clientId"), "null");
        Assert.assertEquals(finTermsCalculateResponse.path("clientId").toString(), expectedClientId);
        Assert.assertEquals(finTermsCalculateResponse.path("productId").toString(), expectedProductId);
        Assert.assertEquals(finTermsCalculateResponse.path("currencyCode"), expectedCurrencyCode);
        Assert.assertEquals(finTermsCalculateResponse.path("purchaseAmount").toString(), expectedPurchaseAmount);

        // assertions: Terms
        Assert.assertEquals(finTermsCalculateResponse.path("terms.reserveAmount").toString(), expectedTermsreserveAmount);
        Assert.assertEquals(finTermsCalculateResponse.path("terms.vendAmount").toString(), expectedTermsVendAmount);
        Assert.assertEquals(finTermsCalculateResponse.path("terms.feeAmount").toString(), expectedTermsFeeAmount);
        Assert.assertEquals(finTermsCalculateResponse.path("terms.settlementAmount").toString(), expectedTermsSettlementAmount);
        Assert.assertEquals(finTermsCalculateResponse.path("terms.clientShareAmount").toString(), expectedTermsClientShareAmount);
        Assert.assertEquals(finTermsCalculateResponse.path("terms.vendorShareAmount").toString(), expectedTermsVendorShareAmount);

        // assertions: fin term variables
        Assert.assertEquals(finTermsCalculateResponse.path("variables.REQUESTED_PURCHASE_VALUE").toString(), expectedVariablesREQUESTED_PURCHASE_VALUE);
        Assert.assertEquals(finTermsCalculateResponse.path("variables.VAT_PERC").toString(), expectedVariablesVAT_PERC);
        Assert.assertEquals(finTermsCalculateResponse.path("variables.MNO_DISCOUNT_PERC").toString(), expectedVariablesMNO_DISCOUNT_PERC);
        Assert.assertEquals(finTermsCalculateResponse.path("variables.TPV").toString(), expectedVariablesTPV);
        Assert.assertEquals(finTermsCalculateResponse.path("variables.CLIENT_SHARE_OF_TPV").toString(), expectedVariablesCLIENT_SHARE_OF_TPV);
        Assert.assertEquals(finTermsCalculateResponse.path("variables.CLIENT_MARKETING_COMMISSION").toString(), expectedVariablesCLIENT_MARKETING_COMMISSION);

    }

}
