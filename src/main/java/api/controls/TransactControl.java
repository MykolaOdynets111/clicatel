package api.controls;

import lombok.extern.java.Log;
import lombok.val;
import lombok.var;

import javax.persistence.NoResultException;
import java.time.LocalTime;

import static db.clients.HibernateBaseClient.executeCustomQueryAndReturnValue;
import static db.custom_queries.ReserveAndTransactQueries.GET_TRANSACTION_STATUS;
import static db.enums.Sessions.POSTGRES_SQL;
import static java.lang.String.format;
import static util.Waits.waitForSystemLoading;

@Log
public class TransactControl {

    public static Boolean getTransactionStatus(final String raasTxnRef) {
        val beginTime = LocalTime.now();
        val finishTime = beginTime.plusMinutes(1);

        for (var i = beginTime; i.isBefore(finishTime); i = LocalTime.now()) {
            try {
                val status = executeCustomQueryAndReturnValue(POSTGRES_SQL, format(GET_TRANSACTION_STATUS, raasTxnRef));

                if (status.equals("SUCCESS")) {
                    return true;
                }
            } catch (NoResultException e) {
                log.warning(e.getMessage());
                if (e.getCause() != null) {
                    log.warning(String.valueOf(e.getCause()));
                }
            }
            waitForSystemLoading(20);
        }
        throw new IllegalStateException("The transaction status is incorrect ");
    }
}
