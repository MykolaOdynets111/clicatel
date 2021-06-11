package api.customer_account_validation;

import api.clients.CustomerAccountValidationClient;
import api.clients.ReserveAndTransactClient;
import api.enums.Port;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import util.base_test.BaseApiTest;

import static api.clients.CustomerAccountValidationClient.*;
import static api.domains.customer_account_validation.repo.CustomerAccountValidationRequestRepo.*;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;


public class CustomerAccountValidationTest extends BaseApiTest {

    @Test
    @Description("30091 :: magtipon3lineng-rest :: public internal :: POST /magtipon3lineng/validateTransaction :: Customer Account Validation (1.0)")
    @TmsLink("TECH-54463")
    public void testValidateCustomerAccountSuccess() {

        //validate customers account number
        val body = setUpCustomerAccountDataV1(Integer.parseInt(ReserveAndTransactClient.TestClient3), Integer.parseInt(CustomerAccountValidationClient.ProductClickatelBiller3_718),CustomerAccountValidationClient.Identifier_7, Integer.parseInt(ReserveAndTransactClient.PurchaseAmount1000));

        validateCustomerAccount(body,Port.CUSTOMER_ACCOUNT_VALIDATION)
                .then().assertThat().statusCode(SC_ACCEPTED)
                .body("customerName", Matchers.notNullValue())
                .body("customerInfo.accountNumber", Matchers.notNullValue())
                .body("responseCode", Matchers.is(ReserveAndTransactClient.ZeroTransactionCode))
                .body("responseMessage", Matchers.is(CustomerAccountValidationClient.responseMessageSuccessfulResult));

    }

    @Test
    @Description("32050 :: vendor-routing-service :: public internal :: POST /validate :: Customer Account Validation (2.0)")
    @TmsLink("TECH-54473")
    public void testValidateCustomerAccountV2Success() {

        //validate customers account number
        val body = setUpCustomerAccountDataV2(Integer.parseInt(ReserveAndTransactClient.TestClient3), Integer.parseInt(ReserveAndTransactClient.ProductAirtel_917),CustomerAccountValidationClient.Identifier_8, Integer.parseInt(ReserveAndTransactClient.PurchaseAmount10000));

        validateCustomerAccountV2(body,Port.CUSTOMER_ACCOUNT_VALIDATION_V2)
                .then().assertThat().statusCode(SC_OK)
                .body("status", Matchers.is(ReserveAndTransactClient.ZeroTransactionCode))
                .body("responseCode", Matchers.is(ReserveAndTransactClient.ZeroTransactionCode))
                .body("message", Matchers.contains(ReserveAndTransactClient.Success))
                .body("accountInfo.accountNumber", Matchers.notNullValue());
    }

}
