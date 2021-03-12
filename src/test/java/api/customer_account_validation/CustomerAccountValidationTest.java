package api.customer_account_validation;

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
        val body = setUpCustomerAccountDataV1(3,718,"57100039965",35000);

        validateCustomerAccount(body,Port.CUSTOMER_ACCOUNT_VALIDATION)
                .then().assertThat().statusCode(SC_ACCEPTED)
                .body("customerName", Matchers.notNullValue())
                .body("customerInfo.accountNumber", Matchers.notNullValue())
                .body("responseCode", Matchers.is("0000"))
                .body("responseMessage", Matchers.is("Request successful"));

    }

    @Test
    @Description("32050 :: vendor-routing-service :: public internal :: POST /validate :: Customer Account Validation (2.0)")
    @TmsLink("TECH-54473")
    public void testValidateCustomerAccountV2Success() {

        //validate customers account number
        val body = setUpCustomerAccountDataV2(3,917,"234710898268",10000);

        validateCustomerAccountV2(body,Port.CUSTOMER_ACCOUNT_VALIDATION_V2)
                .then().assertThat().statusCode(SC_OK)
                .body("status", Matchers.is("0000"))
                .body("responseCode", Matchers.is("0000"))
                .body("message", Matchers.contains("Success"))
                .body("accountInfo.accountNumber", Matchers.notNullValue());
    }

}
