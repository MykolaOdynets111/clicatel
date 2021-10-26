package api.product_lookup;


import api.clients.ProductLookupClient;
import api.clients.ReserveAndTransactClient;
import api.clients.TokenLookupClient;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import java.util.List;

import static api.clients.ProductLookupClient.*;
import static api.domains.product_lookup.repo.ProductLookupRequestRepo.*;
import static org.apache.http.HttpStatus.*;

public class ClientController {
    @Test
    @Description("POST /clients/createClientForTouchFlow :: if unknown timezoneId code is sent then valid error message should be returned")
    @TmsLink("TECH-120826")
    public void testInvalidTimezoneTouchFlowClient() {
        val body = setUpPostClientTouchFLow(ProductLookupClient.ChannelID_8,ProductLookupClient.NewClientName,ReserveAndTransactClient.AccountIdentifier,ProductLookupClient.CountryCode_710,ProductLookupClient.clickatellAccountId_TouchFlowClient);

        PostTouchFlowClient(body)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("message", Matchers.containsString(ProductLookupClient.responseMessageInvalidTimeZoneTouchClient));
   }
    @Test
    @Description("POST /clients/createClientForTouchFlow :: if unknown country code is sent then valid error message should be returned")
    @TmsLink("TECH-120825")
    public void testInvalidCountryCodeTouchFlowClient() {
        val body = setUpPostClientTouchFLow(ProductLookupClient.ChannelID_8,ProductLookupClient.NewClientName,ProductLookupClient.TimeZone_UTC, TokenLookupClient.Limit_1,ProductLookupClient.clickatellAccountId_TouchFlowClient);

        PostTouchFlowClient(body)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("message", Matchers.containsString(ProductLookupClient.responseMessageInvalidCountryCodeTouchClient));
    }
    @Test
    @Description("POST /clients/createClientForTouchFlow :: if not suported channel is sent then valid error message should be returned")
    @TmsLink("TECH-120806")
    public void testInvalidChannelTouchFlowClient() {
        val body = setUpPostClientTouchFLow(ProductLookupClient.CountryCode_710,ProductLookupClient.NewClientName,ProductLookupClient.TimeZone_UTC, TokenLookupClient.Limit_1,ProductLookupClient.clickatellAccountId_TouchFlowClient);

        PostTouchFlowClient(body)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("message", Matchers.containsString(ProductLookupClient.responseMessageInvalidChannelIdTouchClient));
    }
    @Test
    @Description("POST /clients/createClientForTouchFlow :: if required field isn't sent then valid error message should be returned")
    @TmsLink("TECH-120824")
    public void testRequiredFieldsTouchFlowClient() {
        //NUllClientName
        val body = setUpPostClientTouchFLowNullClientName(ProductLookupClient.CountryCode_710,ProductLookupClient.TimeZone_UTC, TokenLookupClient.Limit_1,ProductLookupClient.clickatellAccountId_TouchFlowClient);

        PostTouchFlowClient(body)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("message", Matchers.containsString(ProductLookupClient.responseMessageNullClientNameTouchClient));
        //NUllTimeZone
        val bodyTimeZone = setUpPostClientTouchFLowNullTimeZone(ProductLookupClient.CountryCode_710,ProductLookupClient.TimeZone_UTC, TokenLookupClient.Limit_1,ProductLookupClient.clickatellAccountId_TouchFlowClient);

        PostTouchFlowClient(bodyTimeZone)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("message", Matchers.containsString(ProductLookupClient.responseMessageNullTimeZoneITouchClient));
        //NUllCountryCode
        val bodyCountryCode = setUpPostClientTouchFLowNullCountryCode(ProductLookupClient.CountryCode_710,ProductLookupClient.TimeZone_UTC, TokenLookupClient.Limit_1,ProductLookupClient.clickatellAccountId_TouchFlowClient);

        PostTouchFlowClient(bodyCountryCode)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("message", Matchers.containsString(ProductLookupClient.responseMessageNullCountryCodeTouchClient));
        //NUllChannelId
        val bodyChannelId = setUpPostClientTouchFLowNullChannelId(ProductLookupClient.CountryCode_710,ProductLookupClient.TimeZone_UTC, TokenLookupClient.Limit_1,ProductLookupClient.clickatellAccountId_TouchFlowClient);

        PostTouchFlowClient(bodyChannelId)
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .body("message", Matchers.containsString(ProductLookupClient.responseMessageNullChannelIdTouchClient));

    }
    @Test
    @Description("POST /clients/createClientForTouchFlow :: if not unique client name is sent then valid error message should be returned")
    @TmsLink("TECH-120821")
    public void testUniqueClientNameTouchFlowClient() {
        val body = setUpPostClientTouchFLow(ProductLookupClient.ChannelID_8,ProductLookupClient.NewClientName,ProductLookupClient.TimeZone_UTC, ProductLookupClient.CountryCode_710,ProductLookupClient.clickatellAccountId_TouchFlowClient);

        PostTouchFlowClient(body)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("message", Matchers.containsString("Failed to create new client in Rolling back...Database syncronization Failed, Duplicate client name"));
    }
    //The following test cases are commented out because they are creating clients in the system and we are not confident in our system to load test the clients.
//    @Test
//    @Description("POST /clients/createClientForTouchFlow :: happy path")
//    @TmsLink("TECH-35342")
//    public void testCreateTouchFlowClient() {
//        List<Integer> response2 = getClients()
//                .then().assertThat().statusCode(SC_OK)
//                .extract().jsonPath().get("clientId");
//
//        val maxClientID = Collections.max(response2);
//        //System.out.println(maxClientID);
//        val AutomationClientID = maxClientID + 1;
//
//        val body = setUpPostClientTouchFLow(ProductLookupClient.ChannelID_8,NewClientName + AutomationClientID,ProductLookupClient.TimeZone_UTC,ProductLookupClient.CountryCode_710,ProductLookupClient.clickatellAccountId_TouchFlowClient);
//
//        PostTouchFlowClient(body)
//                .then().assertThat().statusCode(SC_OK)
//                .body("clientId", Matchers.is(AutomationClientID));
//    }
//    @Test
//    @Description("POST /clients/addNewTouchflowClientChannels :: happy path")
//    @TmsLink("TECH-34231")
//    public void testCreateChannelsForTouchFlowClient() {
//        List<Integer> response2 = getClients()
//                .then().assertThat().statusCode(SC_OK)
//                .extract().jsonPath().get("clientId");
//
//        val maxClientID = Collections.max(response2);
//        //System.out.println(maxClientID);
//        val AutomationClientID = maxClientID + 1;
//
//        val body = setUpPostChannelsForClientTouchFLow(ProductLookupClient.ChannelID_7, String.valueOf(maxClientID));
//
//        PostChannelForTouchFlowClient(body)
//                .then().assertThat().statusCode(SC_OK)
//                .body("clientId", Matchers.is(maxClientID))
//                .body("channelIds[0]", Matchers.is(Integer.parseInt(ProductLookupClient.ChannelID_7)))
//                .body("channelIds[1]", Matchers.is(Integer.parseInt(ProductLookupClient.ChannelID_8)));
//    }
    @Test
    @Description("POST /clients/addNewTouchflowClientChannels :: check if the request with the invalid clientId is sent, then error is returned")
    @TmsLink("TECH-131848")
    public void testCreateChannelsForInvalidTouchFlowClient() {

        val body = setUpPostChannelsForClientTouchFLow(ProductLookupClient.ChannelID_7, ReserveAndTransactClient.ClientIdInvalid);

        PostChannelForTouchFlowClient(body)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("message", Matchers.containsString(responseMessageInvalidTouchFlowClientCreation));
    }
    @Test
    @Description("POST /clients/addNewTouchflowClientChannels :: check if the request with the invalid channel is sent, then error is returned")
    @TmsLink("TECH-131847")
    public void testCreateChannelsForInvalidTouchFlowChannelId() {

        val body = setUpPostChannelsForClientTouchFLow(ReserveAndTransactClient.PurchaseAmount99, Product_1600);

        PostChannelForTouchFlowClient(body)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("message", Matchers.containsString(responseMessageInvalidTouchFlowChannelId));
    }
    @Test
    @Description("POST /clients/addNewTouchflowClientChannels :: check if the request with the onboarded channel is sent, then success response if returned")
    @TmsLink("TECH-131845")
    public void testCreateChannelsForOnboardedChannel() {
        List <String> response2 = getClientsFromBackend(Product_1600)
                .then().assertThat().statusCode(SC_OK)
                .extract().jsonPath().get("channelIds");

        val body = setUpPostChannelsForClientTouchFLow(response2, Product_1600);

        PostChannelForTouchFlowClient(body)
                .then().assertThat().statusCode(SC_OK)
                .body("clientId", Matchers.is(Integer.parseInt(Product_1600)))
                .body("channelIds", Matchers.is(response2));
    }
}
