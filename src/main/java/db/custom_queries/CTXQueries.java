package db.custom_queries;

public class CTXQueries extends BaseCustomQuery {
    public static final String SELECT_SYSTEM_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID =
            "select systemResponseCode from " + dBSuffix + ".tran_log where clientTransactionId ='%s'";
    public static final String SELECT_TRANSACTION_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID =
            "select transactionResponseCode from " + dBSuffix + ".tran_log where clientTransactionId ='%s'";
    public static final String SELECT_VENDOR_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID =
            "select vendorResponseCode from " + dBSuffix + ".tran_log where clientTransactionId ='%s'";
}
