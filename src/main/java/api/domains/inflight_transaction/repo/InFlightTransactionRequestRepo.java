package api.domains.inflight_transaction.repo;

import api.domains.inflight_transaction.model.InFlightTransactionRequest;

public class InFlightTransactionRequestRepo {

    public static InFlightTransactionRequest setUpInFlightTransactionData(String identifier, int clientId, int productId, int purchaseAmount){
        return InFlightTransactionRequest.builder()
                .sourceIdentifier(identifier)
                .targetIdentifier(identifier)
                .clientId(clientId)
                .productId(productId)
                .purchaseAmount(purchaseAmount)
                .build();
    }
}
