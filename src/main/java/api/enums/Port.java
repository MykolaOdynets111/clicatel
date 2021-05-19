package api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Port {

    MNO_LOOKUP (80),
    USER_TRANSACTIONS (80),
    CUSTOMER_ACCOUNT_VALIDATION (80),
    TRANSACTIONS (80),
    NOTIFICATION (80),
    AIRTEL_SIMULATOR (80),
    FINANCIAL_TERMS (80),
    TRANSACTION_LOOKUP_SERVICE (80),
    RAAS_FLOW (80),
    MTN_SIMULATOR (80),
    PRODUCT_LOOKUP (80),
    CUSTOMER_ACCOUNT_VALIDATION_V2 (80);


    private final int port;

}
