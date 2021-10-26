package api.product_lookup;

import api.clients.ReserveAndTransactClient;
import api.clients.UsersClient;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static api.clients.ProductLookupClient.*;
import static api.domains.product_lookup.repo.ProductLookupRequestRepo.*;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;

//As these test cases are creating new clients hence they are commented out:

//public class Clients {
//
//    @Test
//    @Description("POST /clients :: happy path")
//    @TmsLink("TECH-34251")
//    public void testCreateNewClient() {
//
//        List<Integer> response2 = getClients()
//                .then().assertThat().statusCode(SC_OK)
//                .extract().jsonPath().get("clientId");
//
//        val maxCLientID = Collections.max(response2);
//        System.out.println(maxCLientID);
//        val AutomationClientID = maxCLientID + 1;
//
//
//        val body = setUpPostNewClientData(true, Arrays.asList("3", "2", "1"), String.valueOf(AutomationClientID), NewClientName + AutomationClientID, UsersClient.country,
//                ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2
//                , String.valueOf(AutomationClientID),ctxLimitTotal,false );
//
//        val response1 = PostNewClient(body)
//                .then().assertThat().statusCode(SC_CREATED)
//                .body("clientId", Matchers.is(AutomationClientID))
//                .body("clientName", Matchers.containsString( NewClientName + String.valueOf(AutomationClientID)))
//                .body("active", Matchers.is( true))
//                .body("timezoneId", Matchers.is(Integer.parseInt(ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2)))
//                .body("countryCode", Matchers.is(Integer.parseInt(UsersClient.country)))
//                .body("clickatellAccountId", Matchers.containsString(String.valueOf(AutomationClientID)))
//                .body("signatureRequired", Matchers.is( false))
//                .extract().body();
//        //System.out.println("Response Body size is: " + body.size());
//
//    }
//    @Test
//    @Description("PUT /clients/{clientId} :: happy path")
//    @TmsLink("TECH-51115")
//    public void testUpdateNewClient() { // marko feedback required
//
//        List<Integer> response2 = getClients()
//                .then().assertThat().statusCode(SC_OK)
//                .extract().jsonPath().get("clientId");
//
//        val maxCLientID = Collections.max(response2);
//        System.out.println(maxCLientID);
//        val AutomationClientID = maxCLientID + 1;
//
//
//        val body = setUpPostNewClientData(true, Arrays.asList("3", "2", "1"), String.valueOf(AutomationClientID), NewClientName + AutomationClientID + "Update", UsersClient.country,
//                ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2
//                , String.valueOf(AutomationClientID),ctxLimitTotal,false );
//
//        val response1 = PostUpdateClient(body)
//                .then().assertThat().statusCode(SC_CREATED)
//                .body("clientId", Matchers.is(AutomationClientID))
//                .body("clientName", Matchers.containsString( NewClientName + String.valueOf(AutomationClientID)))
//                .body("active", Matchers.is( true))
//                .body("timezoneId", Matchers.is(Integer.parseInt(ReserveAndTransactClient.Clickatell_Test_ZA_2_PaydWhitelistFundingSource_2)))
//                .body("countryCode", Matchers.is(Integer.parseInt(UsersClient.country)))
//                .body("clickatellAccountId", Matchers.containsString(String.valueOf(AutomationClientID)))
//                .body("signatureRequired", Matchers.is( false))
//                .extract().body();
//        //System.out.println("Response Body size is: " + body.size());
//
//    }
//}
