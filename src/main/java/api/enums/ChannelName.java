package api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChannelName {
    POS ("POS"),
    INTERNET ("INTERNET"),
    MOBILE ("MOBILE"),
    SMS ("SMS"),
    ATM ("ATM"),
    WHATSAPP ("WHATSAPP"),
    APPLEBUSINESSCHAT ("APPLEBUSINESSCHAT"),
    USSD ("USSD"),
    INVALID ("323456789012345678901234567890323");

    private final String channelName;
}
