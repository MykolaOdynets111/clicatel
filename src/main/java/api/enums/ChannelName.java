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
    USSD ("USSD");

    private final String channelName;
}
