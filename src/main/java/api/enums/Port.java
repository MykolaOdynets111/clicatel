package api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Port {

    MNO_LOOKUP (30049),
    USER_TRANSACTIONS (30065),
    CUSTOMER_ACCOUNT_VALIDATION (30091),
    TRANSACTIONS (30100),
    NOTIFICATION (30116),
    FINANCIAL_TERMS (30309),
    TRANSACTION_LOOKUP_SERVICE (30433),
    RAAS_FLOW (31000),
    MTN_SIMULATOR (31943),
    PRODUCT_LOOKUP (32000),
    CUSTOMER_ACCOUNT_VALIDATION_V2 (32050);


    private final int port;

}
