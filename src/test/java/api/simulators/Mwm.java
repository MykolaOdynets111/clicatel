package api.simulators;

import api.clients.*;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

import static api.clients.SimulatorsClient.*;
import static org.apache.http.HttpStatus.SC_OK;

public class Mwm {
    @Test
    @Description("32051-mwm-simulator :: POST /validaterequest :: happy path")
    @TmsLink("TECH-146593")
    public void testValidRequestHappyPath() {

        Map<String,Object> jsonObjectPayload = new LinkedHashMap<>();
        jsonObjectPayload.put("message", Arrays.asList("FAILED"));
        jsonObjectPayload.put("responseCode", "400");
        jsonObjectPayload.put("status", "string");

        Map<String,Object> accountInfo = new LinkedHashMap<>();
        jsonObjectPayload.put("accountInfo", accountInfo);

        Map<String,Object> customerInfo = new LinkedHashMap<>();
        jsonObjectPayload.put("customerInfo", customerInfo);

        PostMWMSimulator(jsonObjectPayload, NotificationClient.Identifier_6)
                .then().assertThat().statusCode(SC_OK);

        Map<String,Object> jsonObjectPayload_Validator_Request = new LinkedHashMap<>();
        jsonObjectPayload_Validator_Request.put("purchaseAmount", ReserveAndTransactClient.PurchaseAmount10000);
        jsonObjectPayload_Validator_Request.put("targetIdentifier", NotificationClient.Identifier_6);

        Map<String,Object> product = new LinkedHashMap<>();
        product.put("description", mwm_description);
        product.put("id", ReserveAndTransactClient.ProductAirtel_917);
        product.put("productTypeId", ReserveAndTransactClient.TestClient3);
        product.put("productTypeName", ProductLookupClient.ProductTypeNameAirtime);
        product.put("shortDescription", mwm_description);
        jsonObjectPayload_Validator_Request.put("product", product);

        Map<String,Object> vendorDetail = new LinkedHashMap<>();
        vendorDetail.put("externalProductId", ReserveAndTransactClient.ProductAirtel_917);
        vendorDetail.put("vendorId", VendorManagementClient.Vendor21);
        vendorDetail.put("vendorName", Vendor_NG_21);
        vendorDetail.put("vendorProductId", ReserveAndTransactClient.ProductAirtel_917);
        jsonObjectPayload_Validator_Request.put("vendorDetail", vendorDetail);

        PostMWMSimulatorValidatorRequest(jsonObjectPayload_Validator_Request)
                .then().assertThat().statusCode(SC_OK)
                .body("status", Matchers.is(mwmStatus))
                .body("responseCode", Matchers.is(mwmStatusCode))
                .body("message", Matchers.hasItem(mwmMessage))
                .body("customerInfo", Matchers.notNullValue())
                .body("accountInfo", Matchers.notNullValue());

        PostMWMSimulatorDelete(NotificationClient.Identifier_6)
                .then().assertThat().statusCode(SC_OK);

        PostMWMSimulatorValidatorRequest(jsonObjectPayload_Validator_Request)
                .then().assertThat().statusCode(SC_OK)
                .body("status", Matchers.is(ReserveAndTransactClient.responseCode0000))
                .body("responseCode", Matchers.is(ReserveAndTransactClient.responseCode0000))
                .body("message", Matchers.hasItem(mwmSuccess))
                .body("customerInfo.address", Matchers.notNullValue())
                .body("customerInfo.phone", Matchers.notNullValue())
                .body("customerInfo.district", Matchers.notNullValue())
                .body("customerInfo.name", Matchers.notNullValue())
                .body("accountInfo.amount", Matchers.notNullValue())
                .body("accountInfo.targetIdentifier", Matchers.notNullValue())
                .body("accountInfo.arrears", Matchers.notNullValue())
                .body("accountInfo.minimumAmount", Matchers.notNullValue())
                .body("accountInfo.tariff", Matchers.notNullValue())
                .body("accountInfo.accountNumber", Matchers.notNullValue())
                .body("accountInfo.providerName", Matchers.notNullValue());

    }

}
