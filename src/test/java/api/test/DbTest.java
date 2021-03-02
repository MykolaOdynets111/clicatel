package api.test;

import api.domains.transact.model.TransactResponse;
import api.enums.*;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import io.restassured.http.ContentType;
import lombok.val;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import util.base_test.BaseApiTest;

import static api.clients.TransactClient.executeTransact;
import static api.controls.TransactControl.getTransactionStatus;
import static api.domains.transact.repo.TransactRequestRepo.*;
import static api.enums.ChannelName.USSD;
import static db.clients.HibernateBaseClient.executeCustomQueryAndReturnValue;
import static db.custom_queries.ReserveAndTransactQueries.GET_TRANSACTION_STATUS;
import static db.enums.Sessions.POSTGRES_SQL;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

public class DbTest extends BaseApiTest {

    @Test
    @Description("30100 :: payd-raas-gateway :: POST v4/transact :: SUCCESS :: Transact API (4.0)")
    @TmsLink("TECH-54338")
    public void testTransactV4Success() {

        //Verify transaction status is "SUCCESS"
        //val status = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(GET_TRANSACTION_STATUS, "lyk6oi5hkqze77tps3aftogz"));
        //System.out.println(status);

        given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .baseUri("http://docker-minion01.uat.ng01.payd.co:30100/raas/v3/reserveAndTransact")
                .header("Signature", "dgUVUVzgBIIl7dtAI7ziy+t2f3qYHv4u593b5lDlkYE=")
                .body("{\n" +
                        "    \"timestamp\": \"2023-03-03T00:00:00.000+02:00\",\n" +
                        "    \"accountIdentifier\": \"000XXX0311-0003\",\n" +
                        "    \"clientTxnRef\": \"010002441811llim-0003\",\n" +
                        "    \"channelSessionId\": \"714890809-0003\",\n" +
                        "    \"clientId\": \"1003\",\n" +
                        "    \"productId\": \"917\",\n" +
                        "    \"purchaseAmount\": \"10000\",\n" +
                        "    \"feeAmount\": \"0\",\n" +
                        "    \"channelId\": \"3\",\n" +
                        "    \"channelName\": \"MOBILE\",\n" +
                        "    \"sourceIdentifier\": \"2348038382068\",\n" +
                        "    \"targetIdentifier\": \"2348038382068\"\n" +
                        "}")
                .post()
        .then()
                .log()
                .all();




    }



}