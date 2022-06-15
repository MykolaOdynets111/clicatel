package api.financial_terms_lookup;

import api.clients.ProductLookupClient;
import api.clients.ReserveAndTransactClient;
import api.enums.Port;
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
import static api.clients.ProductLookupClient.*;
import static api.clients.ProductLookupClient.fundingSourceId_1206;
import static api.clients.ReserveAndTransactClient.*;
import static api.clients.TokenLookupClient.*;
import static api.clients.VendorManagementClient.ProductTypeData_5;
import static api.clients.VendorManagementClient.*;
import static api.domains.financialTermsLookup.repo.FinancialTermsLookupTestRepo.*;
import static db.clients.HibernateBaseClient.executeCustomQuery;
import static db.clients.HibernateBaseClient.executeCustomQueryAndReturnValues;
import static db.custom_queries.FinancialTermsQueries.*;
import static db.custom_queries.FundingSourceQueries.DELETE_FUNDING_SOURCE_COUNTRY_BY_FUNDING_SOURCE_ID;
import static db.custom_queries.FundingSourceQueries.SELECT_FUNDING_SOURCE_BY_FUNDING_SOURCE_ID;
import static db.custom_queries.ProductLookupQueries.*;
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

        //get financial terms
        getFinancialTermDetails(Port.FINANCIAL_TERMS, Integer.parseInt(ReserveAndTransactClient.TestClient3), Integer.parseInt(ReserveAndTransactClient.ProductAirtel_100), Integer.parseInt(ReserveAndTransactClient.PurchaseAmount10000))
                .then().assertThat().statusCode(SC_OK)
                .body("clientId", Matchers.is(Integer.parseInt(ReserveAndTransactClient.TestClient3)))
                .body("productId", Matchers.is(Integer.parseInt(ReserveAndTransactClient.ProductAirtel_100)))
                .body("purchaseAmount", Matchers.is(Integer.parseInt(ReserveAndTransactClient.PurchaseAmount10000)))
                .body("breakdown.vendAmount", Matchers.notNullValue())
                .body("breakdown.clientShareAmount", Matchers.notNullValue())
                .body("breakdown.reserveAmount", Matchers.is(Integer.parseInt(ReserveAndTransactClient.PurchaseAmount10000)))
                .body("breakdown.settlementAmount", Matchers.notNullValue());
    }

    @Test()
    @Description("30309-financial-terms-lookup :: POST \u200B/financial-terms\u200B/financialModels\u200B/configureFinTermsForClient :: happy path")
    @TmsLink("TECH-156845")
    public void testFinancialTermsConfigureFinTermsForClient() {

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

        val body  = setUpConfigureFinTermsForClientData(TestClient3,Vendor21,Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2,FeeAmount10,validFrom_TimeStamp, Arrays.asList(ResponseCode_206));

        PostConfigureFinTermsForClient(body)
                .then().assertThat().statusCode(SC_OK);

        getFinancialTermsCalculateAtTime(ResponseCode_206,TestClient3,PurchaseAmount10000,dateAndTime)
                .then().assertThat().statusCode(SC_OK)
                .body("clientId", Matchers.is(Integer.parseInt(TestClient3)))
                .body("productId", Matchers.is(Integer.parseInt(ResponseCode_206)))
                .body("purchaseAmount", Matchers.is(Integer.parseInt(ReserveAndTransactClient.PurchaseAmount10000)))
                .body("breakdown.vendAmount", Matchers.notNullValue())
                .body("breakdown.clientShareAmount", Matchers.notNullValue())
                .body("breakdown.reserveAmount", Matchers.is(Integer.parseInt(ReserveAndTransactClient.PurchaseAmount10000)));

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
}
