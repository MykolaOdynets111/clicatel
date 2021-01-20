package db.custom_queries;

public class ReserveAndTransactQueries extends BaseCustomQuery {

    public static final String GET_TRANSACTION_STATUS =
            "select status from " + dBSuffixPostgres + ".raas.transaction_log where raas_txn_ref='%s'";

    public static final String GET_CLIENT_BY_CLIENT_ID =
            "select name from " + dBSuffix + ".client where id=3";

}
