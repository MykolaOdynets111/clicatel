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
import static api.clients.ChannelsClient.*;
import static api.clients.FinancialTermsLookupClient.*;
import static api.clients.ProductLookupClient.ChannelID_7;
import static org.apache.http.HttpStatus.SC_OK;

public class Channels{

    @Test
    @Description("30254 :: payd-channel-lookup :: GET /channels")
    @TmsLink("MKP-1041")
    public void testGetChannelsSuccess() {

        //get all channels
        getChannels()
                .then().assertThat().statusCode(SC_OK)
                .body("description", Matchers.hasItem(String.valueOf(ChannelName.POS)))
                .body("id", Matchers.hasItem((Integer.parseInt(ChannelId.POS.getChannelId()))))
                .body("description", Matchers.hasItem(String.valueOf(ChannelName.INTERNET.getChannelName())))
                .body("id", Matchers.hasItem((Integer.parseInt(ChannelId.INTERNET.getChannelId()))))
                .body("description", Matchers.hasItem(String.valueOf(ChannelName.MOBILE.getChannelName())))
                .body("id", Matchers.hasItem((Integer.parseInt(ChannelId.MOBILE.getChannelId()))))
                .body("description", Matchers.hasItem(String.valueOf(ChannelName.ATM)))
                .body("id", Matchers.hasItem((Integer.parseInt(ChannelId.ATM.getChannelId()))))
                .body("description", Matchers.hasItem(String.valueOf(ChannelName.SMS)));
    }

    @Test
    @Description("GET /channels/{id} :: happy path")
    @TmsLink("MKP-594")
    public void testGetChannelByID() {

        //get all channels
        String ChannelID = ChannelID_7;
        getChannelsById(ChannelID)
                .then().assertThat().statusCode(SC_OK)
                .body("description", Matchers.is(String.valueOf(ChannelName.USSD)))
                .body("id", Matchers.is((Integer.parseInt(ChannelId.USSD.getChannelId()))));
    }
}

