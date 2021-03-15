package api.domains.transaction_lookup.repo;

import api.domains.transaction_lookup.model.TransactionLookupRequest;
import api.enums.Version;

public class TransactionRequestRepo {

    public static TransactionLookupRequest setUpTransactionData (int clientId, String reserveFundsTxnRef){
        return TransactionLookupRequest.builder()
                .clientId(clientId)
                .reserveFundsTxnRef(reserveFundsTxnRef)
                .build();
    }
}
