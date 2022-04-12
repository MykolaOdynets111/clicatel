package api.product_lookup;

import api.clients.ProductLookupClient;
import api.clients.ReserveAndTransactClient;
import api.enums.Port;
import api.enums.Version;
import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import static api.clients.ProductLookupClient.*;
import static api.clients.ReserveAndTransactClient.*;
import static api.clients.TokenLookupClient.UssdID;
import static api.clients.VendorManagementClient.*;
import static api.clients.VendorRoutingServiceClient.bundleType_Data;
import static api.domains.productManagement.repo.ProductManagementRepo.*;
import static db.clients.HibernateBaseClient.executeCustomQuery;
import static db.clients.HibernateBaseClient.executeCustomQueryAndReturnValues;
import static db.custom_queries.ProductLookupQueries.*;
import static db.enums.Sessions.POSTGRES_SQL;
import static java.lang.String.format;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductManagement {

    @Test
    @Description("GET /product_management/productDetails :: for the airtime airtel products and externalVendorProductTypeId should be hardcoded")
    @TmsLink("TECH-116734")
    public void testAirtimeAirTelProductsExternalVendorProductTypeId() {
        Map<String, String> map = new Hashtable<>();
        map.put("productId", ProductLookupClient.ProductAirtel_130);

        getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map)
                .then().assertThat().statusCode(SC_OK)
                .body("externalVendorProductTypeId", Matchers.is(ProductLookupClient.AirTimeAirtelProductTypeId));
    }

    @Test
    @Description("GET /product_management/productDetails :: for the data airtel products and externalVendorProductTypeId should be hardcoded")
    @TmsLink("TECH-116736")
    public void testDataAirTelProductsExternalVendorProductTypeId() {
        Map<String, String> map = new Hashtable<>();
        map.put("productId", ReserveAndTransactClient.Product_Airtel_189);

        getProductDetails(Port.PRODUCT_LOOKUP, Version.V2, map)
                .then().assertThat().statusCode(SC_OK)
                .body("externalVendorProductTypeId", Matchers.is(ProductLookupClient.DataAirtelProductTypeId));

    }

    @Test
    @Description("32000-payd-product-lookup :: POST \u200B/clients\u200B/{clientId}\u200B/products :: happy path")
    @TmsLink("TECH-171726")
    public void testCreateProductWithVendorHappyPath() {

        //Pre conditions

        executeCustomQuery(POSTGRES_SQL, format(DELETE_VENDOR_PRODUCT_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_CLIENT_PRODUCT_BY_PRODUCT_ID,ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_COUNTRY_BY_PRODUCT_ID,ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_ATTRIBUTE_VALUE_BY_PRODUCT_ID,ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_PURCHASE_MEDIUM_BY_PRODUCT_ID,ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_TOP_SELLER_PLATFORM_BY_PRODUCT_ID,ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_SUBSCRIBER_TYPE_BY_PRODUCT_ID,ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_CHARGE_TYPE_BY_PRODUCT_ID,ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_PRODUCT_ID,ResponseCode_206));

        String json = "'" + "{\"type\":\"range\",\"minimumAmount\":1,\"maximumAmount\":100000,\"increment\":10}" + "'" + "::::json";

        executeCustomQuery(POSTGRES_SQL, format(INSERT_PRODUCT_BY_PRODUCT_ID,ResponseCode_206, ProductTypeData_5,Product_Desc_206, true,json,true,Product_Desc_206,Product_Insert_Timestamp,true ));

        Map<Object, Object> body = CreateDataForProductWithVendor(UssdID, ResponseCode_206);

        PostClientProductAssociation(body,"3")
                .then().assertThat().statusCode(SC_OK)
                .body("productId",Matchers.hasItem(Integer.parseInt(ResponseCode_206)))
                .body("productType",Matchers.hasItem(bundleType_Data))
                .body("description",Matchers.hasItem(Product_Desc_206));

        val SELECT_PRODUCT_ID_VALUE = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_CLIENT_PRODUCT_BY_PRODUCT_AND_CLIENTID, ResponseCode_206,fundingSourceId));
        System.out.println(SELECT_PRODUCT_ID_VALUE);
        List<String> strings_1 = Lists.transform(SELECT_PRODUCT_ID_VALUE, Functions.toStringFunction());
        assertThat(strings_1.get(0))
                .contains(ResponseCode_206);

        //Post conditions

        executeCustomQuery(POSTGRES_SQL, format(DELETE_VENDOR_PRODUCT_BY_PRODUCT_ID, ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_CLIENT_PRODUCT_BY_PRODUCT_ID,ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_COUNTRY_BY_PRODUCT_ID,ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_ATTRIBUTE_VALUE_BY_PRODUCT_ID,ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_PURCHASE_MEDIUM_BY_PRODUCT_ID,ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_TOP_SELLER_PLATFORM_BY_PRODUCT_ID,ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_SUBSCRIBER_TYPE_BY_PRODUCT_ID,ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_CHARGE_TYPE_BY_PRODUCT_ID,ResponseCode_206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_PRODUCT_BY_PRODUCT_ID,ResponseCode_206));

    }
}
