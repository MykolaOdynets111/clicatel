package api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChannelName {
    POS ("POS"),
    INTERNET ("Internet"),
    MOBILE ("Mobile"),
    SMS ("SMS"),
    ATM ("ATM"),
    WHATSAPP ("WhatsApp"),
    APPLEBUSINESSCHAT ("Apple Business Chat"),
    USSD ("USSD"),
    INVALID ("64-3456789012345678902121321321111112345678901234567890123456-641");

    private final String channelName;
}
