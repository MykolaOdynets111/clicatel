package api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Port {

    ACCOUNT_VALIDATION (30091),
    INFLIGHT_TRANSACTIONS (30433),
    MNO_LOOKUP (30049),
    MTN_SIMULATOR (31943),
    NOTIFICATIONS (30116),
    PRODUCT_LOOKUP (32000),
    RAAS_FLOW (31000),
    TOKEN_LOOKUP (30065),
    TRANSACTIONS (30100);

    private final int port;

}
