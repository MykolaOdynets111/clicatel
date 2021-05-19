package api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Port {

    MNO_LOOKUP (30049),
    USER_TRANSACTIONS (30065),
    CUSTOMER_ACCOUNT_VALIDATION (30091),
    TRANSACTIONS (80),
    NOTIFICATION (30116),
    AIRTEL_SIMULATOR (80),
    FINANCIAL_TERMS (30309),
    TRANSACTION_LOOKUP_SERVICE (80),
    RAAS_FLOW (80),
    MTN_SIMULATOR (80),
    PRODUCT_LOOKUP (32000),
    CUSTOMER_ACCOUNT_VALIDATION_V2 (32050);


    private final int port;

}
