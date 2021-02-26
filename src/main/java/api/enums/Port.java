package api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Port {

    TRANSACTIONS (30100),
    RAAS_FLOW (31000),
    PRODUCT_LOOKUP (32000),
    INFLIGHT_TRANSACTIONS (30433),
    ACCOUNT_VALIDATION (30091),
    TOKEN_LOOKUP (30065),
    MTN_SIMULATOR (31943);

    private final int port;

}
