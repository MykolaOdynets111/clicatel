package api.channels;

import api.clients.ReserveAndTransactClient;
import api.enums.ChannelId;
import api.enums.ChannelName;
import api.enums.Port;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static api.clients.ChannelsClient.getChannels;
import static api.clients.FinancialTermsLookupClient.getFinancialTermDetails;
import static org.apache.http.HttpStatus.SC_OK;

public class Channels{

    @Test
    @Description("30254 :: payd-channel-lookup :: GET /channels")
    @TmsLink("TECH-45212")
    public void testGetChannelsSuccess() {

        //get all channels
        getChannels()
                .then().assertThat().statusCode(SC_OK)
                .body("description[0]", Matchers.containsString(String.valueOf(ChannelName.POS)))
                .body("id[0]", Matchers.is((Integer.parseInt(ChannelId.POS.getChannelId()))))
                .body("description[1]", Matchers.containsString(String.valueOf(ChannelName.INTERNET.getChannelName())))
                .body("id[1]", Matchers.is((Integer.parseInt(ChannelId.INTERNET.getChannelId()))))
                .body("description[2]", Matchers.containsString(String.valueOf(ChannelName.MOBILE.getChannelName())))
                .body("id[2]", Matchers.is((Integer.parseInt(ChannelId.MOBILE.getChannelId()))))
                .body("description[3]", Matchers.containsString(String.valueOf(ChannelName.ATM)))
                .body("id[3]", Matchers.is((Integer.parseInt(ChannelId.ATM.getChannelId()))))
                .body("description[5]", Matchers.containsString(String.valueOf(ChannelName.SMS)));
    }
}
