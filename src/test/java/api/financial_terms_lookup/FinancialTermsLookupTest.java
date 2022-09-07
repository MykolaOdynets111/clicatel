package api.financial_terms_lookup;

import api.clients.ProductLookupClient;
import api.clients.ReserveAndTransactClient;
import api.domains.financialTermsLookup.repo.model.FinancialTermsLookupResponse;
import api.domains.reserve_and_transact.model.ReserveAndTransactResponse;
import api.enums.ChannelId;
import api.enums.Port;
import api.enums.Version;
import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import util.base_test.BaseApiTest;

import java.util.Arrays;
import java.util.List;

import static api.clients.FinancialTermsLookupClient.*;
import static api.clients.FinancialTermsLookupClient.*;
import static api.clients.MnoLookupClient.CountryCodeNGN;
import static api.clients.NotificationClient.Identifier_6;
import static api.clients.ProductLookupClient.*;
import static api.clients.ProductLookupClient.fundingSourceId_1206;
import static api.clients.ReserveAndTransactClient.*;
import static api.clients.ReserveAndTransactDBChecksClient.settlementAmount;
import static api.clients.TokenLookupClient.*;
import static api.clients.VendorManagementClient.ProductTypeData_5;
import static api.clients.VendorManagementClient.*;
import static api.domains.financialTermsLookup.repo.FinancialTermsLookupTestRepo.*;
import static api.domains.reserve_and_transact.repo.ReserveAndTransactRequestRepo.setUpReserveAndTransactV4Data;
import static api.enums.ChannelName.USSD;
import static api.enums.CurrencyCode.NGN;
import static db.clients.HibernateBaseClient.executeCustomQuery;
import static db.clients.HibernateBaseClient.executeCustomQueryAndReturnValues;
import static db.custom_queries.FinancialTermsQueries.*;
import static db.custom_queries.FundingSourceQueries.DELETE_FUNDING_SOURCE_COUNTRY_BY_FUNDING_SOURCE_ID;
import static db.custom_queries.FundingSourceQueries.SELECT_FUNDING_SOURCE_BY_FUNDING_SOURCE_ID;
import static db.custom_queries.ProductLookupQueries.*;
import static db.custom_queries.ReserveAndTransactQueries.*;
import static db.custom_queries.VendorRoutingServiceQueries.*;
import static db.enums.Sessions.POSTGRES_SQL;
import static java.lang.String.format;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;


public class FinancialTermsLookupTest extends BaseApiTest {

    @Test(groups = {"smokeTest"})
    @Description("30309 :: financial-terms-lookup :: public internal :: GET /financialTerms :: Financial Terms Lookup API (1.0)")
    @TmsLink("TECH-50897")
    public void testFinancialTermsLookupSuccess() {

        executeCustomQuery(POSTGRES_SQL, format(DELETE_VENDOR_DISCOUNT_PERCENTAGE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_TPV_CLIENT_SHARE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_MODEL_SELECTION_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_CLIENT_PRODUCT_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_VENDOR_PRODUCT_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_COUNTRY_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_ATTRIBUTE_VALUE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_PURCHASE_MEDIUM_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_TOP_SELLER_PLATFORM_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_SUBSCRIBER_TYPE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_PRODUCT_ID, ResponseCode_206));

        String json = "'" + "{\"type\":\"range\",\"minimumAmount\":1,\"maximumAmount\":100000,\"increment\":10}" + "'" + "::::json";

        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT_BY_PRODUCT_ID,ResponseCode_206, ProductTypeData_5,Product_Desc_206, true,json,true,Product_Desc_206,Product_Insert_Timestamp,true ));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_VENDOR_PRODUCT_BY_PRODUCT_ID,ResponseCode_206, Vendor21,null,null,ResponseCode_206, Vendor_Product_Insert_Timestamp,ResponseCode_206,true,Vendor21));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,TestClient3,ResponseCode_206, UssdID,Client_Product_Insert_Timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_VENDOR_DISCOUNT_PERCENTAGE_BY_PRODUCT_ID,vendorDiscountPercentageId, Vendor21,value_0point1,valid_from_TimeStamp,valid_to_TimeStamp,valid_from_TimeStamp,created_TimeStamp,ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(Insert_Financial_Terms_Model_Selection,vendorDiscountPercentageId,TestClient3,ResponseCode_206,TestClient2,valid_from_TimeStamp,valid_to_TimeStamp,validTo_TimeStamp,valid_from_TimeStamp,Vendor21));
        executeCustomQuery(POSTGRES_SQL, format(Insert_Financial_Terms_TPV_Client_Share,vendorDiscountPercentageId,TestClient3,value_0point1,valid_from_TimeStamp,valid_to_TimeStamp,valid_from_TimeStamp,valid_from_TimeStamp,ResponseCode_206));

        //get financial terms
        val lookupReference= getFinancialTermDetails(Integer.parseInt(ReserveAndTransactClient.TestClient3), Integer.parseInt(ResponseCode_206), Integer.parseInt(ReserveAndTransactClient.PurchaseAmount10000))
                .then().assertThat().statusCode(SC_OK)
                .body("clientId", Matchers.is(Integer.parseInt(ReserveAndTransactClient.TestClient3)))
                .body("productId", Matchers.is(Integer.parseInt(ReserveAndTransactClient.ResponseCode_206)))
                .body("currencyCode",Matchers.is(CountryCodeNGN))
                .body("purchaseAmount", Matchers.is(Integer.parseInt(ReserveAndTransactClient.PurchaseAmount10000)))
                .body("breakdown.vendAmount", Matchers.is(Integer.parseInt(PurchaseAmount10000)))
                .body("breakdown.clientShareAmount", Matchers.is(Integer.parseInt(PurchaseAmount1000)))
                .body("breakdown.reserveAmount", Matchers.is(Integer.parseInt(ReserveAndTransactClient.PurchaseAmount10000)))
                .body("breakdown.settlementAmount", Matchers.is(Integer.parseInt(settlementAmount)))
                .body("breakdown.feeAmount",Matchers.is(Integer.parseInt(FeeAmount0)))
                .body("lookupReference",Matchers.notNullValue())
                        .extract().body().as(FinancialTermsLookupResponse.class).getLookupReference();


      val SELECT_ClientId_Financial_Terms_Lookup_Audit_Log=  executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_Client_id_Financial_Terms_Audit_Log,lookupReference));
      System.out.println(SELECT_ClientId_Financial_Terms_Lookup_Audit_Log);

        List<String> stringClientID = Lists.transform(SELECT_ClientId_Financial_Terms_Lookup_Audit_Log, Functions.toStringFunction());
        assertThat(stringClientID.get(0))
                .contains(TestClient3);

        val SELECT_Product_Id_Financial_Terms_Lookup_Audit_Log=  executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_Product_Id_Financial_Terms_Audit_Log,lookupReference));
        System.out.println(SELECT_Product_Id_Financial_Terms_Lookup_Audit_Log);

        List<String> stringProductID = Lists.transform(SELECT_Product_Id_Financial_Terms_Lookup_Audit_Log, Functions.toStringFunction());
        assertThat(stringProductID.get(0))
                .contains(ResponseCode_206);

        val SELECT_Reserve_Amount_Financial_Terms_Lookup_Audit_Log=  executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_Reserve_Amount_Financial_Terms_Audit_Log,lookupReference));
        System.out.println(SELECT_Reserve_Amount_Financial_Terms_Lookup_Audit_Log);

        List<String> stringReserveAmount = Lists.transform(SELECT_Reserve_Amount_Financial_Terms_Lookup_Audit_Log, Functions.toStringFunction());
        assertThat(stringReserveAmount.get(0))
                .contains(PurchaseAmount10000);

        val SELECT_Client_Share_Amount_Financial_Terms_Lookup_Audit_Log=  executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_Client_Share_Amount_Financial_Terms_Audit_Log,lookupReference));
        System.out.println(SELECT_Client_Share_Amount_Financial_Terms_Lookup_Audit_Log);

        List<String> stringClientShareAmount = Lists.transform(SELECT_Client_Share_Amount_Financial_Terms_Lookup_Audit_Log, Functions.toStringFunction());
        assertThat(stringClientShareAmount.get(0))
                .contains(PurchaseAmount1000);

        val SELECT_Settlement_Amount_Financial_Terms_Lookup_Audit_Log=  executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_Settlement_Amount_Financial_Terms_Audit_Log,lookupReference));
        System.out.println(SELECT_Settlement_Amount_Financial_Terms_Lookup_Audit_Log);

        List<String> stringSettlementAmount = Lists.transform(SELECT_Settlement_Amount_Financial_Terms_Lookup_Audit_Log, Functions.toStringFunction());
        assertThat(stringSettlementAmount.get(0))
                .contains(settlementAmount);

        val SELECT_Fee_Amount_Financial_Terms_Lookup_Audit_Log=  executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_Fee_Amount_Financial_Terms_Audit_Log,lookupReference));
        System.out.println(SELECT_Fee_Amount_Financial_Terms_Lookup_Audit_Log);

        List<String> stringFeeAmount = Lists.transform(SELECT_Fee_Amount_Financial_Terms_Lookup_Audit_Log, Functions.toStringFunction());
        assertThat(stringFeeAmount.get(0))
                .contains(FeeAmount0);

        val SELECT_Vendor_Share_Amount_Financial_Terms_Lookup_Audit_Log=  executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_vendor_Share_Amount_Financial_Terms_Audit_Log,lookupReference));
        System.out.println(SELECT_Vendor_Share_Amount_Financial_Terms_Lookup_Audit_Log);

        List<String> stringVendorShareAmount = Lists.transform(SELECT_Vendor_Share_Amount_Financial_Terms_Lookup_Audit_Log, Functions.toStringFunction());
        assertThat(stringVendorShareAmount.get(0))
                .contains(FeeAmount0);

        val SELECT_Vend_Amount_Financial_Terms_Lookup_Audit_Log=  executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_Vend_Amount_Financial_Terms_Audit_Log,lookupReference));
        System.out.println(SELECT_Vend_Amount_Financial_Terms_Lookup_Audit_Log);

        List<String> stringVendAmount = Lists.transform(SELECT_Vend_Amount_Financial_Terms_Lookup_Audit_Log, Functions.toStringFunction());
        assertThat(stringVendAmount.get(0))
                .contains(PurchaseAmount10000);


        executeCustomQuery(POSTGRES_SQL, format(DELETE_VENDOR_DISCOUNT_PERCENTAGE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_TPV_CLIENT_SHARE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_MODEL_SELECTION_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_CLIENT_PRODUCT_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_VENDOR_PRODUCT_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_COUNTRY_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_ATTRIBUTE_VALUE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_PURCHASE_MEDIUM_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_TOP_SELLER_PLATFORM_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_SUBSCRIBER_TYPE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_PRODUCT_ID, ResponseCode_206));
    }

    @Test()
    @Description("30309-financial-terms-lookup :: POST \u200B/financial-terms\u200B/financialModels\u200B/configureFinTermsForClient :: happy path")
    @TmsLink("TECH-156845")
    public void testFinancialTermsConfigureFinTermsForClient() throws InterruptedException {

        //Pre conditions
        executeCustomQuery(POSTGRES_SQL, format(DELETE_VENDOR_DISCOUNT_PERCENTAGE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_TPV_CLIENT_SHARE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_MODEL_SELECTION_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_CLIENT_PRODUCT_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_VENDOR_PRODUCT_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_COUNTRY_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_ATTRIBUTE_VALUE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_PURCHASE_MEDIUM_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_TOP_SELLER_PLATFORM_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_SUBSCRIBER_TYPE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_PRODUCT_ID, ResponseCode_206));

        String json = "'" + "{\"type\":\"range\",\"minimumAmount\":1,\"maximumAmount\":100000,\"increment\":10}" + "'" + "::::json";

        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT_BY_PRODUCT_ID,ResponseCode_206, ProductTypeData_5,Product_Desc_206, true,json,true,Product_Desc_206,Product_Insert_Timestamp,true ));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_VENDOR_PRODUCT_BY_PRODUCT_ID,ResponseCode_206, Vendor21,null,null,ResponseCode_206, Vendor_Product_Insert_Timestamp,ResponseCode_206,true,Vendor21));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,TestClient3,ResponseCode_206, UssdID,Client_Product_Insert_Timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_VENDOR_DISCOUNT_PERCENTAGE_BY_PRODUCT_ID,vendorDiscountPercentageId, Vendor21,value_0point1,valid_from_TimeStamp,valid_to_TimeStamp,valid_from_TimeStamp,created_TimeStamp,ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(Insert_Financial_Terms_Model_Selection,vendorDiscountPercentageId,TestClient3,ResponseCode_206,TestClient2,valid_from_TimeStamp,valid_to_TimeStamp,validTo_TimeStamp,valid_from_TimeStamp,Vendor21));
        executeCustomQuery(POSTGRES_SQL, format(Insert_Financial_Terms_TPV_Client_Share,vendorDiscountPercentageId,TestClient3,value_0point1,valid_from_TimeStamp,valid_to_TimeStamp,valid_from_TimeStamp,valid_from_TimeStamp,ResponseCode_206));


        val body  = setUpConfigureFinTermsForClientData(TestClient3,Vendor21,Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,FeeAmount10,validFrom_TimeStamp, Arrays.asList(ResponseCode_206));

        PostConfigureFinTermsForClient(body)
                .then().assertThat().statusCode(SC_OK);


        getFinancialTermsCalculateAtTime(ResponseCode_206,TestClient3,PurchaseAmount10000,dateAndTime)
                .then().assertThat().statusCode(SC_OK)
                .body("clientId",Matchers.is(Integer.parseInt(TestClient3)))
                .body("productId",Matchers.is(Integer.parseInt(ResponseCode_206)))
                .body("currencyCode",Matchers.is(CountryCodeNGN))
                .body("purchaseAmount",Matchers.is(Integer.parseInt(PurchaseAmount10000)))
                .body("terms.reserveAmount",Matchers.is(Integer.parseInt(PurchaseAmount10000)))
                .body("terms.vendAmount",Matchers.is(Integer.parseInt(PurchaseAmount10000)))
                .body("terms.feeAmount",Matchers.is(Integer.parseInt(FeeAmount0)))
                .body("terms.settlementAmount",Matchers.is(Integer.parseInt(settlementAmount)))
                .body("terms.clientShareAmount",Matchers.is(Integer.parseInt(PurchaseAmount1000)))
                .body("terms.vendorShareAmount",Matchers.is(Integer.parseInt(FeeAmount0)))
                .body("variables.CLIENT_SHARE_OF_TPV",Matchers.is(Float.parseFloat(value_0point1)));

        val SELECT_MODEL_SELECTION_BY_PRODUCT_ID_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_MODEL_SELECTION_BY_PRODUCT_ID, TestClient3,ResponseCode_206));
        System.out.println(SELECT_MODEL_SELECTION_BY_PRODUCT_ID_VALUE);
        List<String> strings_1 = Lists.transform(SELECT_MODEL_SELECTION_BY_PRODUCT_ID_VALUE, Functions.toStringFunction());
        assertThat(strings_1.get(0))
                .contains(Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2);

        val SELECT_TPV_CLIENT_SHARE_BY_PRODUCT_ID_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_TPV_CLIENT_SHARE_BY_PRODUCT_ID, TestClient3,ResponseCode_206));
        System.out.println(SELECT_TPV_CLIENT_SHARE_BY_PRODUCT_ID_VALUE);
        List<String> strings_2 = Lists.transform(SELECT_TPV_CLIENT_SHARE_BY_PRODUCT_ID_VALUE, Functions.toStringFunction());
        assertThat(strings_2.get(0))
                .contains(value_0point1);

        executeCustomQuery(POSTGRES_SQL, format(DELETE_VENDOR_DISCOUNT_PERCENTAGE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_TPV_CLIENT_SHARE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_MODEL_SELECTION_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_CLIENT_PRODUCT_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_VENDOR_PRODUCT_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_COUNTRY_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_ATTRIBUTE_VALUE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_PURCHASE_MEDIUM_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_TOP_SELLER_PLATFORM_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_SUBSCRIBER_TYPE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_PRODUCT_ID, ResponseCode_206));


    }

    @Test()
    @Description("30309-financial-terms-lookup :: GET /terms :: SMOKE test case for the service")
    @TmsLink("TECH-197217")
    public void testFinancialTermsGETTermsSmokeCase() {

        getTerms(TestClient3,ProductAirtel_917)
                .then().assertThat().statusCode(SC_OK);
    }

    @Test()
    @Description("30309-financial-terms-lookup :: POST /financial-terms/vendorDiscount :: happy path")
    @TmsLink("TECH-171707")
    public void testPostFinancialTermsVendorDiscount() {

        executeCustomQuery(POSTGRES_SQL, format(DELETE_VENDOR_DISCOUNT_PERCENTAGE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_TPV_CLIENT_SHARE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_MODEL_SELECTION_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_CLIENT_PRODUCT_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_VENDOR_PRODUCT_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_COUNTRY_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_ATTRIBUTE_VALUE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_PURCHASE_MEDIUM_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_TOP_SELLER_PLATFORM_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_SUBSCRIBER_TYPE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_PRODUCT_ID, ResponseCode_206));

        String json = "'" + "{\"type\":\"range\",\"minimumAmount\":1,\"maximumAmount\":100000,\"increment\":10}" + "'" + "::::json";

        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT_BY_PRODUCT_ID,ResponseCode_206, ProductTypeData_5,Product_Desc_206, true,json,true,Product_Desc_206,Product_Insert_Timestamp,true ));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_VENDOR_PRODUCT_BY_PRODUCT_ID,ResponseCode_206, Vendor21,null,null,ResponseCode_206, Vendor_Product_Insert_Timestamp,ResponseCode_206,true,Vendor21));


       val body = setUpFinancialTermsVendorDiscountData(Arrays.asList(ResponseCode_206),financialTermsValue10,validFrom_TimeStamp,fundingSourceId_1206);
        postFinancialTermsVendorDiscount(body)
                .then().assertThat().statusCode(SC_OK)
                .body("vendorName",Matchers.is(Vendor_1206))
                .body("vendorId",Matchers.is(Integer.parseInt(fundingSourceId_1206)))
                .body("value",Matchers.is(Integer.parseInt(financialTermsValue10)))
                .body("validFrom",Matchers.is(validFrom_Response))
                .body("validTo",Matchers.is(validTo_TimeStamp))
                .body("productIds",Matchers.hasItem(Integer.parseInt(ResponseCode_206)));

        val SELECT_VENDOR_DISCOUNT_BY_VENDOR_ID = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_VENDOR_DISCOUNT_PERCENTAGE_BY_VENDOR_ID, fundingSourceId_1206,ResponseCode_206));
        System.out.println(SELECT_VENDOR_DISCOUNT_BY_VENDOR_ID);

        List<String> strings_1 = Lists.transform(SELECT_VENDOR_DISCOUNT_BY_VENDOR_ID, Functions.toStringFunction());
        assertThat(strings_1.get(0))
                .contains(value_0point1);

        executeCustomQuery(POSTGRES_SQL, format(DELETE_VENDOR_DISCOUNT_PERCENTAGE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_TPV_CLIENT_SHARE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_MODEL_SELECTION_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_CLIENT_PRODUCT_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_VENDOR_PRODUCT_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_COUNTRY_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_ATTRIBUTE_VALUE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_PURCHASE_MEDIUM_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_TOP_SELLER_PLATFORM_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_SUBSCRIBER_TYPE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_PRODUCT_ID, ResponseCode_206));

    }

    @Test()
    @Description("30309-financial-terms-lookup :: GET /financial-terms/calculate :: happy path")
    @TmsLink("TECH-149973")
    public void testGETFinancialTermsCalculate() {

        executeCustomQuery(POSTGRES_SQL, format(DELETE_VENDOR_DISCOUNT_PERCENTAGE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_TPV_CLIENT_SHARE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_MODEL_SELECTION_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_CLIENT_PRODUCT_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_VENDOR_PRODUCT_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_COUNTRY_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_ATTRIBUTE_VALUE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_PURCHASE_MEDIUM_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_TOP_SELLER_PLATFORM_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_SUBSCRIBER_TYPE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_PRODUCT_ID, ResponseCode_206));

        String json = "'" + "{\"type\":\"range\",\"minimumAmount\":1,\"maximumAmount\":100000,\"increment\":10}" + "'" + "::::json";

        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT_BY_PRODUCT_ID,ResponseCode_206, ProductTypeData_5,Product_Desc_206, true,json,true,Product_Desc_206,Product_Insert_Timestamp,true ));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_VENDOR_PRODUCT_BY_PRODUCT_ID,ResponseCode_206, Vendor21,null,null,ResponseCode_206, Vendor_Product_Insert_Timestamp,ResponseCode_206,true,Vendor21));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,TestClient3,ResponseCode_206, UssdID,Client_Product_Insert_Timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_VENDOR_DISCOUNT_PERCENTAGE_BY_PRODUCT_ID,vendorDiscountPercentageId, Vendor21,value_0point1,valid_from_TimeStamp,valid_to_TimeStamp,valid_from_TimeStamp,created_TimeStamp,ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(Insert_Financial_Terms_Model_Selection,vendorDiscountPercentageId,TestClient3,ResponseCode_206,TestClient2,valid_from_TimeStamp,valid_to_TimeStamp,validTo_TimeStamp,valid_from_TimeStamp,Vendor21));
        executeCustomQuery(POSTGRES_SQL, format(Insert_Financial_Terms_TPV_Client_Share,vendorDiscountPercentageId,TestClient3,value_0point1,valid_from_TimeStamp,valid_to_TimeStamp,valid_from_TimeStamp,valid_from_TimeStamp,ResponseCode_206));

        getFinancialTermsCalculate(ResponseCode_206,TestClient3,PurchaseAmount10000)
                .then().assertThat().statusCode(SC_OK)
                .body("clientId",Matchers.is(Integer.parseInt(TestClient3)))
                .body("productId",Matchers.is(Integer.parseInt(ResponseCode_206)))
                .body("currencyCode",Matchers.is(CountryCodeNGN))
                .body("purchaseAmount",Matchers.is(Integer.parseInt(PurchaseAmount10000)))
                .body("terms.reserveAmount",Matchers.is(Integer.parseInt(PurchaseAmount10000)))
                .body("terms.vendAmount",Matchers.is(Integer.parseInt(PurchaseAmount10000)))
                .body("terms.feeAmount",Matchers.is(Integer.parseInt(FeeAmount0)))
                .body("terms.settlementAmount",Matchers.is(Integer.parseInt(settlementAmount)))
                .body("terms.clientShareAmount",Matchers.is(Integer.parseInt(PurchaseAmount1000)))
                .body("terms.vendorShareAmount",Matchers.is(Integer.parseInt(FeeAmount0)))
                .body("variables.CLIENT_SHARE_OF_TPV",Matchers.is(Float.parseFloat(value_0point1)));


        executeCustomQuery(POSTGRES_SQL, format(DELETE_VENDOR_DISCOUNT_PERCENTAGE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_TPV_CLIENT_SHARE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_MODEL_SELECTION_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_CLIENT_PRODUCT_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_VENDOR_PRODUCT_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_COUNTRY_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_ATTRIBUTE_VALUE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_PURCHASE_MEDIUM_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_TOP_SELLER_PLATFORM_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_SUBSCRIBER_TYPE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_PRODUCT_ID, ResponseCode_206));
    }

    @Test()
    @Description("30309-financial-terms-lookup :: GET \u200B/financial-terms/calculateAtTime :: happy path")
    @TmsLink("TECH-149970")
    public void testGETFinancialTermsCalculateAtTime() {

        executeCustomQuery(POSTGRES_SQL, format(DELETE_VENDOR_DISCOUNT_PERCENTAGE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_TPV_CLIENT_SHARE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_MODEL_SELECTION_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_CLIENT_PRODUCT_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_VENDOR_PRODUCT_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_COUNTRY_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_ATTRIBUTE_VALUE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_PURCHASE_MEDIUM_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_TOP_SELLER_PLATFORM_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_SUBSCRIBER_TYPE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_PRODUCT_ID, ResponseCode_206));

        String json = "'" + "{\"type\":\"range\",\"minimumAmount\":1,\"maximumAmount\":100000,\"increment\":10}" + "'" + "::::json";

        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT_BY_PRODUCT_ID,ResponseCode_206, ProductTypeData_5,Product_Desc_206, true,json,true,Product_Desc_206,Product_Insert_Timestamp,true ));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_VENDOR_PRODUCT_BY_PRODUCT_ID,ResponseCode_206, Vendor21,null,null,ResponseCode_206, Vendor_Product_Insert_Timestamp,ResponseCode_206,true,Vendor21));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,TestClient3,ResponseCode_206, UssdID,Client_Product_Insert_Timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_VENDOR_DISCOUNT_PERCENTAGE_BY_PRODUCT_ID,vendorDiscountPercentageId, Vendor21,value_0point1,valid_from_TimeStamp,valid_to_TimeStamp,valid_from_TimeStamp,created_TimeStamp,ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(Insert_Financial_Terms_Model_Selection,vendorDiscountPercentageId,TestClient3,ResponseCode_206,TestClient2,valid_from_TimeStamp,valid_to_TimeStamp,validTo_TimeStamp,valid_from_TimeStamp,Vendor21));
        executeCustomQuery(POSTGRES_SQL, format(Insert_Financial_Terms_TPV_Client_Share,vendorDiscountPercentageId,TestClient3,value_0point1,valid_from_TimeStamp,valid_to_TimeStamp,valid_from_TimeStamp,valid_from_TimeStamp,ResponseCode_206));

        getFinancialTermsCalculateAtTime(ResponseCode_206,TestClient3,PurchaseAmount10000,dateAndTime)
                .then().assertThat().statusCode(SC_OK)
                .body("clientId",Matchers.is(Integer.parseInt(TestClient3)))
                .body("productId",Matchers.is(Integer.parseInt(ResponseCode_206)))
                .body("purchaseAmount",Matchers.is(Integer.parseInt(PurchaseAmount10000)))
                .body("terms.reserveAmount",Matchers.is(Integer.parseInt(PurchaseAmount10000)))
                .body("terms.vendAmount",Matchers.is(Integer.parseInt(PurchaseAmount10000)))
                .body("terms.feeAmount",Matchers.is(Integer.parseInt(FeeAmount0)))
                .body("terms.settlementAmount",Matchers.is(Integer.parseInt(settlementAmount)))
                .body("terms.clientShareAmount",Matchers.is(Integer.parseInt(PurchaseAmount1000)))
                .body("terms.vendorShareAmount",Matchers.is(Integer.parseInt(FeeAmount0)))
                .body("variables.CLIENT_SHARE_OF_TPV",Matchers.is(Float.parseFloat(value_0point1)));

        executeCustomQuery(POSTGRES_SQL, format(DELETE_VENDOR_DISCOUNT_PERCENTAGE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_TPV_CLIENT_SHARE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_MODEL_SELECTION_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_CLIENT_PRODUCT_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_VENDOR_PRODUCT_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_COUNTRY_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_ATTRIBUTE_VALUE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_PURCHASE_MEDIUM_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_TOP_SELLER_PLATFORM_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_SUBSCRIBER_TYPE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_PRODUCT_ID, ResponseCode_206));
    }



    @Test()
    @Description("30309-financial-terms-lookup :: REGRESSION :: POST /raas/v4/reserveAndTransact")
    @TmsLink("TECH-149976")
    public void testPOSTV4ReserveAndTransactFinancialTermsLookup() {

        executeCustomQuery(POSTGRES_SQL, format(DELETE_VENDOR_DISCOUNT_PERCENTAGE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_TPV_CLIENT_SHARE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_MODEL_SELECTION_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_CLIENT_PRODUCT_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_VENDOR_PRODUCT_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_COUNTRY_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_ATTRIBUTE_VALUE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_PURCHASE_MEDIUM_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_TOP_SELLER_PLATFORM_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_SUBSCRIBER_TYPE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_PRODUCT_ID, ResponseCode_206));

        String json = "'" + "{\"type\":\"range\",\"minimumAmount\":1,\"maximumAmount\":100000,\"increment\":10}" + "'" + "::::json";

        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT_BY_PRODUCT_ID,ResponseCode_206, ProductTypeData_5,Product_Desc_206, true,json,true,Product_Desc_206,Product_Insert_Timestamp,true ));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_VENDOR_PRODUCT_BY_PRODUCT_ID,ResponseCode_206, Vendor21,null,null,ResponseCode_206, Vendor_Product_Insert_Timestamp,ResponseCode_206,true,Vendor21));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT,TestClient3,ResponseCode_206, UssdID,Client_Product_Insert_Timestamp));
        executeCustomQuery(POSTGRES_SQL, format(INSERT_VENDOR_DISCOUNT_PERCENTAGE_BY_PRODUCT_ID,vendorDiscountPercentageId, Vendor21,value_0point1,valid_from_TimeStamp,valid_to_TimeStamp,valid_from_TimeStamp,created_TimeStamp,ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(Insert_Financial_Terms_Model_Selection,vendorDiscountPercentageId,TestClient3,ResponseCode_206,TestClient2,valid_from_TimeStamp,valid_to_TimeStamp,validTo_TimeStamp,valid_from_TimeStamp,Vendor21));
        executeCustomQuery(POSTGRES_SQL, format(Insert_Financial_Terms_TPV_Client_Share,vendorDiscountPercentageId,TestClient3,value_0point1,valid_from_TimeStamp,valid_to_TimeStamp,valid_from_TimeStamp,valid_from_TimeStamp,ResponseCode_206));

        val jsonBody = setUpReserveAndTransactV4Data(ReserveAndTransactClient.TestClient3, NGN, USSD, ChannelId.USSD, ReserveAndTransactClient.ResponseCode_206, ReserveAndTransactClient.PurchaseAmount10000, ReserveAndTransactClient.FeeAmount0, Identifier_6);

        val raasTxnRef = executeReserveAndTransact(jsonBody, Port.TRANSACTIONS, Version.V4)
                .then().assertThat().statusCode(SC_OK)
                .body("responseCode", Matchers.containsString(ReserveAndTransactClient.responseCode0000))
                .body("responseMessage", Matchers.containsString(ReserveAndTransactClient.responseMessageFundsReserved))
                .body("raasTxnRef", Matchers.notNullValue())
                .extract().body().as(ReserveAndTransactResponse.class).getRaasTxnRef();

        System.out.println(raasTxnRef);

        val SELECT_Reserve_Amount_BY_RAAS_TXN_Reference=  executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_Reserve_Amount_BY_RAAS_TXN_REF,raasTxnRef));
        System.out.println(SELECT_Reserve_Amount_BY_RAAS_TXN_Reference);

        List<String> stringReserveAmount = Lists.transform(SELECT_Reserve_Amount_BY_RAAS_TXN_Reference, Functions.toStringFunction());
        assertThat(stringReserveAmount.get(0))
                .contains(PurchaseAmount10000);

        val SELECT_Client_Share_Amount_BY_RAAS_TXN_Reference=  executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_Client_Share_Amount_BY_RAAS_TXN_REF,raasTxnRef));
        System.out.println(SELECT_Client_Share_Amount_BY_RAAS_TXN_Reference);

        List<String> stringClientShareAmount = Lists.transform(SELECT_Client_Share_Amount_BY_RAAS_TXN_Reference, Functions.toStringFunction());
        assertThat(stringClientShareAmount.get(0))
                .contains(PurchaseAmount1000);

        val SELECT_Settlement_Amount_BY_RAAS_TXN_Reference=  executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_Settlement_Amount_BY_RAAS_TXN_REF,raasTxnRef));
        System.out.println(SELECT_Settlement_Amount_BY_RAAS_TXN_Reference);

        List<String> stringSettlementAmount = Lists.transform(SELECT_Settlement_Amount_BY_RAAS_TXN_Reference, Functions.toStringFunction());
        assertThat(stringSettlementAmount.get(0))
                .contains(settlementAmount);

        val SELECT_Fee_Amount_BY_RAAS_TXN_Reference=  executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_FEE_AMOUNT_BY_RAAS_TXN_REF,raasTxnRef));
        System.out.println(SELECT_Fee_Amount_BY_RAAS_TXN_Reference);

        List<String> stringFeeAmount = Lists.transform(SELECT_Fee_Amount_BY_RAAS_TXN_Reference, Functions.toStringFunction());
        assertThat(stringFeeAmount.get(0))
                .contains(FeeAmount0);

        val SELECT_Vendor_Share_Amount_BY_RAAS_TXN_Reference=  executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_Vendor_Share_Amount_BY_RAAS_TXN_REF,raasTxnRef));
        System.out.println(SELECT_Vendor_Share_Amount_BY_RAAS_TXN_Reference);

        List<String> stringVendorShareAmount = Lists.transform(SELECT_Vendor_Share_Amount_BY_RAAS_TXN_Reference, Functions.toStringFunction());
        assertThat(stringVendorShareAmount.get(0))
                .contains(FeeAmount0);

        val SELECT_Vend_Amount_BY_RAAS_TXN_Reference=  executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_Vend_Amount_BY_RAAS_TXN_REF,raasTxnRef));
        System.out.println(SELECT_Vend_Amount_BY_RAAS_TXN_Reference);

        List<String> stringVendAmount = Lists.transform(SELECT_Vend_Amount_BY_RAAS_TXN_Reference, Functions.toStringFunction());
        assertThat(stringVendAmount.get(0))
                .contains(PurchaseAmount10000);

        executeCustomQuery(POSTGRES_SQL, format(DELETE_VENDOR_DISCOUNT_PERCENTAGE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_TPV_CLIENT_SHARE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_MODEL_SELECTION_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_CLIENT_PRODUCT_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_VENDOR_PRODUCT_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_COUNTRY_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_ATTRIBUTE_VALUE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_PURCHASE_MEDIUM_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_TOP_SELLER_PLATFORM_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_SUBSCRIBER_TYPE_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_PRODUCT_ID, ResponseCode_206));

    }
}
