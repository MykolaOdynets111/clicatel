package db.custom_queries;

public class CTXQueries extends BaseCustomQuery {
    public static final String SELECT_SYSTEM_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID =
            "select systemResponseCode from " + dBSuffix + ".tran_log where clientTransactionId ='%s'";
    public static final String SELECT_TRANSACTION_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID =
            "select transactionResponseCode from " + dBSuffix + ".tran_log where clientTransactionId ='%s'";
    public static final String SELECT_VENDOR_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID =
            "select vendorResponseCode from " + dBSuffix + ".tran_log where clientTransactionId ='%s'";

    public static final String SELECT_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select transactionId from " + dBSuffix + ".tran_log where clientTransactionId = '%s'";
    public static final String SELECT_ORIGIN_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select originId from " + dBSuffix + ".tran_log where clientTransactionId = '%s'";
    public static final String SELECT_PURCHASE_AMOUNT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select purchaseAmount from " + dBSuffix + ".tran_log where clientTransactionId = '%s'";
    public static final String SELECT_TRANSACTION_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select transactionResponseCode from " + dBSuffix + ".tran_log where clientTransactionId = '%s'";
    public static final String SELECT_START_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select startDate from " + dBSuffix + ".tran_log where clientTransactionId = '%s'";
    public static final String SELECT_START_VENDOR_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select startVendorDate from " + dBSuffix + ".tran_log where clientTransactionId = '%s'";
    public static final String SELECT_PRODUCT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select product_id from " + dBSuffix + ".tran_log where clientTransactionId = '%s'";
    public static final String SELECT_CHANNEL_ID_BY_CLIENT_TRANSACTION_ID_FROM_CTX_CGPTX_TRAN_LOG =
            "select channel_id from " + dBSuffix + ".tran_log where clientTransactionId = '%s'";
    public static final String SELECT_CLIENT_STAN_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select clientStan from " + dBSuffix + ".tran_log where clientTransactionId = '%s'";
    public static final String SELECT_END_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select endDate from " + dBSuffix + ".tran_log where clientTransactionId = '%s'";
    public static final String SELECT_END_VENDOR_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select endVendorDate from " + dBSuffix + ".tran_log where clientTransactionId = '%s'";
    public static final String SELECT_INTERNAL_RESPONSE_REASON_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select internalResponseReason from " + dBSuffix + ".tran_log where clientTransactionId = '%s'";
    public static final String SELECT_MESSAGE_IDENTIFIER_FROM_CGPTX_TRAN_LOG =
            "select messageIdentifier from " + dBSuffix + ".tran_log where clientTransactionId = '%s'";
    public static final String SELECT_ORIGINATING_SERVICE_FROM_CGPTX_TRAN_LOG =
            "select originatingService from " + dBSuffix + ".tran_log where clientTransactionId = '%s'";
    public static final String SELECT_CTX_PURCHASE_AMOUNT_FROM_CGPTX_TRAN_LOG =
            "select purchaseAmount from " + dBSuffix + ".tran_log where clientTransactionId = '%s'";
    public static final String SELECT_QUANTITY_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select quantity from " + dBSuffix + ".tran_log where clientTransactionId = '%s'";
    public static final String SELECT_REVERSAL_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select reversalTransactionId from " + dBSuffix + ".tran_log where clientTransactionId = '%s'";
    public static final String SELECT_START_ACCOUNTING_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select startAccountingDate from " + dBSuffix + ".tran_log where clientTransactionId = '%s'";
    public static final String SELECT_TRANSACTION_STATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select transactionState from " + dBSuffix + ".tran_log where clientTransactionId = '%s'";
    public static final String SELECT_TRANSACTION_TYPE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select transactionType from " + dBSuffix + ".tran_log where clientTransactionId = '%s'";
    public static final String SELECT_VENDOR_REFERENCE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select vendorReference from " + dBSuffix + ".tran_log where clientTransactionId = '%s'";
    public static final String SELECT_VENDOR_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select vendorResponseCode from " + dBSuffix + ".tran_log where clientTransactionId = '%s'";
    public static final String SELECT_CLIENT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select client_id from " + dBSuffix + ".tran_log where clientTransactionId = '%s'";
    public static final String SELECT_VENDOR_STAN_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select vendorStan from " + dBSuffix + ".tran_log where clientTransactionId = '%s'";
    public static final String SELECT_VENDOR_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select vendor_id from " + dBSuffix + ".tran_log where clientTransactionId = '%s'";
    public static final String SELECT_SYSTEM_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select systemResponseCode from " + dBSuffix + ".tran_log where clientTransactionId = '%s'";
    public static final String SELECT_RESPONSE_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select responseCode from " + dBSuffix + ".tran_log where clientTransactionId = '%s'";
    public static final String SELECT_FEE_AMOUNT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select feeAmount from " + dBSuffix + ".tran_log where clientTransactionId = '%s'";
    public static final String SELECT_RECON_STATE_CLIENT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select reconStateClient from " + dBSuffix + ".tran_log where clientTransactionId = '%s'";
    public static final String SELECT_RECON_STATE_VENDOR_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select reconStateVendor from " + dBSuffix + ".tran_log where clientTransactionId = '%s'";
    public static final String SELECT_RECON_NOTE_VENDOR_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select reconNoteVendor from " + dBSuffix + ".tran_log where clientTransactionId = '%s'";

    public static final String SELECT_Commission_Amount_VENDOR_BY_CLIENT_TRANSACTION_ID_CTX_FROM_CGPTX_TRAN_LOG =
            "select commissionAmount from " + dBSuffix + ".tran_log where clientTransactionId = '%s'";

    public static final String SELECT_CTX_Global_Reason_VENDOR_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select global_reason from " + dBSuffix + ".tran_log where clientTransactionId = '%s'";

    public static final String SELECT_CTX_API_ID_VENDOR_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select api_id from " + dBSuffix + ".tran_log where clientTransactionId = '%s'";
    public static final String SELECT_CTX_Commission_Amount_Internal_VENDOR_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select commissionAmountInternal from " + dBSuffix + ".tran_log where clientTransactionId = '%s'";

    public static final String SELECT_CTX_START_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select startDate from " + dBSuffix + ".tran_log where clientTransactionId = '%s'";

    public static final String SELECT_CTX_START_VENDOR_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select startVendorDate from " + dBSuffix + ".tran_log where clientTransactionId = '%s'";

    public static final String SELECT_CTX_LAST_UPDATED_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select last_updated from " + dBSuffix + ".tran_log where clientTransactionId = '%s'";
}
