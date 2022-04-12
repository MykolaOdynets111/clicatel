package api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CurrencyCode {

    NGN ("NGN"),
    ZAR ("ZAR"),
    INVALIDCC("1234");

    private final String currencyCode;
}
