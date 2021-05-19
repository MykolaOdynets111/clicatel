package api.clients;

import api.domains.customer_account_validation.model.CustomerAccountValidationRequest;
import api.enums.Port;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import lombok.Getter;

import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;

@Getter
public class CustomerAccountValidationClient extends BasedAPIClient {

    //Customer Account Validation (1.0)
    public static Response validateCustomerAccount(CustomerAccountValidationRequest body, Port port) {
        return basedAPIClient.get()
                .post(new RequestSpecBuilder()
                        .setUrlEncodingEnabled(false)
                        .setBaseUri(String.format("%s:%d/magtipon3lineng/validateTransaction", cAccountValidation, port.getPort()))
                        .setBody(body)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());

    }

    //Customer Account Validation (2.0)
    public static Response validateCustomerAccountV2(CustomerAccountValidationRequest body, Port port) {
        return basedAPIClient.get()
                .post(new RequestSpecBuilder()
                        .setUrlEncodingEnabled(false)
                        .setBaseUri(String.format("%s:%d/validate", cAccountValidation2, port.getPort()))
                        .setBody(body)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());

    }
}
