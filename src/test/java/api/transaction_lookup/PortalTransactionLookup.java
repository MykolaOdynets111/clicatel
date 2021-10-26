package api.transaction_lookup;

import api.clients.ReserveAndTransactClient;
import api.clients.TransactionLookupClient;
import api.enums.ChannelName;
import com.google.common.base.CaseFormat;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import org.hamcrest.Matchers;
import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Hashtable;
import java.util.List;
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
//
//    @Test
//    @Description("GET /lookupservice/validDateRanges :: happy path")
//    @TmsLink("TECH-54651")
//    public void testTransactionLookupValidDateRangesHappyPath() {
//
//        getPortalTransactionLookupValidDateRanges()
//                .then().assertThat().statusCode(SC_OK)
//                .body("rangeDescription[0]", Matchers.is("Last 5 minutes"))
//                .body("fromDate[0]", Matchers.is(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ss"))))
//                .body("fromDate[0]", Matchers.is(getCurrentTimeStamp() + 6))
//                .body("toDate[0]", Matchers.is(getCurrentIsoDateTime()))
//                .body("transactionLookupResponse.transactionResultAcknowledgement", Matchers.hasItem((ReserveAndTransactClient.responseCode202)))
//                .body("transactionLookupResponse.transactionStatus", Matchers.hasItem((ReserveAndTransactClient.Success)))
//                .body("transactionLookupResponse.reserveFundsResponseCode", Matchers.hasItem((ReserveAndTransactClient.ZeroTransactionCode)))
//                .body("transactionLookupResponse.responseCode", Matchers.hasItem((ReserveAndTransactClient.ZeroTransactionCode)))
//                .body("transactionLookupResponse.clientTransactionReference", Matchers.hasItem((TransactionLookupClient.ClientTxnID)))
//                .body("transactionLookupResponse.responseDescription", Matchers.hasItem((ReserveAndTransactClient.responseMessageFundsReserved)));
//
//    }

    @Test
    @Description("GET /lookupservice/transaction-lookup-ctx :: happy path")
    @TmsLink("TECH-50087")
    public void testTransactionLookupServiceLookupCtx() {

        Map<String, String> map = new Hashtable<>();
        map.put("clientId", TransactionLookupClient.TestClient101);
        map.put("currentPage", Limit_1);
        map.put("dateRangeFrom", DateRangeFrom_transaction_lookup_ctx);
        map.put("dateRangeTo", DateRangeTo_transaction_lookup_ctx);
        map.put("pageSize", ReserveAndTransactClient.FeeAmount10);

        getPortalTransactionLookupCtx(map)
                .then().assertThat().statusCode(SC_OK)
                .body("pageSize", Matchers.is(Integer.parseInt(ReserveAndTransactClient.FeeAmount10)))
                .body("currentPage", Matchers.is(Integer.parseInt(Limit_1)))
                .body("solutionType", Matchers.is("COMMERCE_LEGACY"))
                .body("transactionLookupResponse[0].clientId", Matchers.is((Integer.parseInt(TestClient101))));
    }

    @Test
    @Description("GET /lookupservice/list-aws-files :: happy path")
    @TmsLink("TECH-121288")
    public void testTransactionLookupListAwsFiles() {

        Map<String, String> map = new Hashtable<>();
        map.put("clientId", TransactionLookupClient.TestClient113);

        getPortalTransactionLookupListAwsFiles(map)
                .then().assertThat().statusCode(SC_OK);
    }
    @Test
    @Description("GET /lookupservice/download-aws-file-stream :: happy path")
    @TmsLink("TECH-121290")
    public void testTransactionLookupDownloadAwsFiles() {

        Map<String, String> map = new Hashtable<>();
        map.put("fileName", "113/CLICKATELL_TRANSACTION_ID-2019-11-20T14:01:15.692.txt");

        getPortalTransactionLookupDownloadAwsFiles(map)
                .then().assertThat().statusCode(SC_OK);
    }
    @Test
    @Description("GET /lookupservice/channels :: happy path")
    @TmsLink("TECH-121286")
    public void testTransactionChannels() {

        List<String> values = getPortalTransactionLookupChannels()
                .then().extract().jsonPath()
                .getList("description");
        System.out.println(values);
        Assert.assertTrue(values.contains(String.valueOf(ChannelName.POS)));
        Assert.assertTrue(values.contains(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, String.valueOf(ChannelName.INTERNET))));
        Assert.assertTrue(values.contains(String.valueOf(ChannelName.SMS)));
        Assert.assertTrue(values.contains("WhatsApp"));
        Assert.assertTrue(values.contains(String.valueOf(ChannelName.ATM)));
        Assert.assertTrue(values.contains(String.valueOf(ChannelName.USSD)));
        Assert.assertTrue(values.contains(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, String.valueOf(ChannelName.MOBILE))));
        Assert.assertTrue(values.contains(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, String.valueOf(ChannelName.INTERNET))));
    }
}
