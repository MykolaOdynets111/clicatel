package api.transaction_lookup;

import api.clients.ReserveAndTransactClient;
import api.clients.TransactionLookupClient;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import java.util.Hashtable;
import java.util.Map;
import static api.clients.TransactionLookupClient.*;
import static org.apache.http.HttpStatus.SC_OK;
import static util.DateProvider.*;

public class PortalTransactionLookup {

    @Test
    @Description("GET /lookupservice/transaction-lookup-core :: happy path")
    @TmsLink("TECH-47963")
    public void testTransactionLookupCoreHappyPath() {
        Map<String, String> map = new Hashtable<>();
        map.put("clientId", TransactionLookupClient.TestClient143);
        map.put("clientTransactionId", TransactionLookupClient.ClientTxnID);
        map.put("currentPage", ReserveAndTransactClient.FeeAmount0);
        map.put("dateRangeFrom", TransactionLookupClient.DateRangeFrom);
        map.put("dateRangeTo", TransactionLookupClient.DateRangeTo);
        map.put("pageSize", TransactionLookupClient.Limit_1);


        getPortalTransactionLookup(map)
                .then().assertThat().statusCode(SC_OK)
                .body("pageSize", Matchers.is(Integer.parseInt(TransactionLookupClient.Limit_1)))
                .body("currentPage", Matchers.is(Integer.parseInt(ReserveAndTransactClient.FeeAmount0)))
                .body("transactionLookupResponse.clientId", Matchers.hasItem((Integer.parseInt(TransactionLookupClient.TestClient143))))
                .body("transactionLookupResponse.transactionResultAcknowledgement", Matchers.hasItem((ReserveAndTransactClient.responseCode202)))
                .body("transactionLookupResponse.transactionStatus", Matchers.hasItem((ReserveAndTransactClient.Success)))
                .body("transactionLookupResponse.reserveFundsResponseCode", Matchers.hasItem((ReserveAndTransactClient.ZeroTransactionCode)))
                .body("transactionLookupResponse.responseCode", Matchers.hasItem((ReserveAndTransactClient.ZeroTransactionCode)))
                .body("transactionLookupResponse.clientTransactionReference", Matchers.hasItem((TransactionLookupClient.ClientTxnID)))
                .body("transactionLookupResponse.responseDescription", Matchers.hasItem((ReserveAndTransactClient.responseMessageFundsReserved)));

    }

    @Test
    @Description("GET /lookupservice/validDateRanges :: happy path")
    @TmsLink("TECH-54651")
    public void testTransactionLookupValidDateRangesHappyPath() {
//        Map<String, String> map = new Hashtable<>();
//        map.put("clientId", TransactionLookupClient.TestClient143);
//        map.put("clientTransactionId", TransactionLookupClient.ClientTxnID);
//        map.put("currentPage", ReserveAndTransactClient.FeeAmount0);
//        map.put("dateRangeFrom", TransactionLookupClient.DateRangeFrom);
//        map.put("dateRangeTo", TransactionLookupClient.DateRangeTo);
//        map.put("pageSize", TransactionLookupClient.Limit_1);


        getPortalTransactionLookupValidDateRanges()
                .then().assertThat().statusCode(SC_OK)
                .body("rangeDescription[0]", Matchers.is("Last 5 minutes"))
                .body("fromDate[0]", Matchers.is(getCurrentTimeStamp()))
                .body("toDate[0]", Matchers.is(getCurrentIsoDateTime()))
                .body("transactionLookupResponse.transactionResultAcknowledgement", Matchers.hasItem((ReserveAndTransactClient.responseCode202)))
                .body("transactionLookupResponse.transactionStatus", Matchers.hasItem((ReserveAndTransactClient.Success)))
                .body("transactionLookupResponse.reserveFundsResponseCode", Matchers.hasItem((ReserveAndTransactClient.ZeroTransactionCode)))
                .body("transactionLookupResponse.responseCode", Matchers.hasItem((ReserveAndTransactClient.ZeroTransactionCode)))
                .body("transactionLookupResponse.clientTransactionReference", Matchers.hasItem((TransactionLookupClient.ClientTxnID)))
                .body("transactionLookupResponse.responseDescription", Matchers.hasItem((ReserveAndTransactClient.responseMessageFundsReserved)));

    }
}
