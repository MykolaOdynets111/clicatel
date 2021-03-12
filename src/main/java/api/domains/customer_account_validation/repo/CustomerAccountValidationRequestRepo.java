package api.domains.customer_account_validation.repo;

import api.domains.customer_account_validation.model.CustomerAccountValidationRequest;

public class CustomerAccountValidationRequestRepo {

    public static CustomerAccountValidationRequest setUpCustomerAccountDataV1(int clientId, int productId, String identifier, int purchaseAmount){
        return CustomerAccountValidationRequest.builder()
                .clientId(clientId)
                .productId(productId)
                .targetIdentifier(identifier)
                .purchaseAmount(purchaseAmount)
                .build();
    }

    public static CustomerAccountValidationRequest setUpCustomerAccountDataV2(int clientId, int productId, String identifier, int purchaseAmount){
        return CustomerAccountValidationRequest.builder()
                .clientId(clientId)
                .productId(productId)
                .purchaseAmount(purchaseAmount)
                .sourceIdentifier(identifier)
                .targetIdentifier(identifier)
                .timestamp("2020-06-20T19:00:00.000+00:00")
                .build();
    }
}
