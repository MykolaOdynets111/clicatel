package api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChannelId {
    POS ("1"),
    INTERNET ("2"),
    MOBILE ("3"),
    SMS ("6"),
    ATM ("4"),
    WHATSAPP ("8"),
    APPLEBUSINESSCHAT ("13"),
    USSD ("7"),
    INVALID ("100"),
    MAXLIMIT("12345678901");

    private final String channelId;
}
