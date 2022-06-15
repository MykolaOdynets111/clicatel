package db.custom_queries;

public class ReserveAndTransactQueries extends BaseCustomQuery {
//Raas Transaction logs Queries
    public static final String GET_TRANSACTION_STATUS =
            "select status from " + dBSuffixPostgres + ".raas.transaction_log where raas_txn_ref='%s'";
    public static final String SELECT_ACCOUNT_IDENTIFIER_BY_RAAS_TXN_REF =
            "select account_identifier from " + dBSuffixPostgres + ".raas.transaction_log where raas_txn_ref  = '%s'";
    public static final String SELECT_AMOUNT_BY_RAAS_TXN_REF =
            "select amount  from " + dBSuffixPostgres + ".raas.transaction_log where raas_txn_ref  = '%s'";
    public static final String SELECT_CHANNEL_ID_BY_RAAS_TXN_REF =
            "select channel_id   from " + dBSuffixPostgres + ".raas.transaction_log where raas_txn_ref  = '%s'";
    public static final String SELECT_CHANNEL_SESSION_ID_BY_RAAS_TXN_REF =
            "select channel_session_id  from " + dBSuffixPostgres + ".raas.transaction_log where raas_txn_ref  = '%s'";
    public static final String SELECT_CLIENT_ID_BY_RAAS_TXN_REF =
            "select client_id from " + dBSuffixPostgres + ".raas.transaction_log where raas_txn_ref  = '%s'";
    public static final String SELECT_CLIENT_TXN_REF_ID_BY_RAAS_TXN_REF =
            "select client_txn_ref from " + dBSuffixPostgres + ".raas.transaction_log where raas_txn_ref  = '%s'";
    public static final String SELECT_PRODUCT_ID_BY_RAAS_TXN_REF =
            "select product_id from " + dBSuffixPostgres + ".raas.transaction_log where raas_txn_ref  = '%s'";
    public static final String SELECT_SOURCE_IDENTIFIER_BY_RAAS_TXN_REF =
            "select source_identifier from " + dBSuffixPostgres + ".raas.transaction_log where raas_txn_ref  = '%s'";
    public static final String SELECT_TARGET_IDENTIFIER_BY_RAAS_TXN_REF =
            "select target_identifier from " + dBSuffixPostgres + ".raas.transaction_log where raas_txn_ref  = '%s'";
    public static final String SELECT_CURRENCY_CODE_BY_RAAS_TXN_REF =
            "select currency_code from " + dBSuffixPostgres + ".raas.transaction_log where raas_txn_ref  = '%s'";
    public static final String SELECT_FUNDING_SOURCE_ID_BY_RAAS_TXN_REF =
            "select funding_source_id from " + dBSuffixPostgres + ".raas.transaction_log where raas_txn_ref  = '%s'";
    public static final String SELECT_EVENT_TYPE_BY_RAAS_TXN_REF =
            "select event_type from " + dBSuffixPostgres + ".raas.transaction_log where raas_txn_ref  = '%s'";
    public static final String SELECT_STATUS_BY_RAAS_TXN_REF =
            "select status from " + dBSuffixPostgres + ".raas.transaction_log where raas_txn_ref  = '%s'";
    public static final String SELECT_CHANNEL_NAME_BY_ID =
            "select description  from " + dBSuffixPostgres + ".payd_common.channel where id = '%s'";
    public static final String SELECT_FEE_AMOUNT_BY_RAAS_TXN_REF =
            "select fee_amount from " + dBSuffixPostgres + ".raas.transaction_log where raas_txn_ref  = '%s'";
    public static final String SELECT_ADD_DATA_CLIENT_ID_BY_RAAS_TXN_REF =
            "select Cast(additional_data_client as varchar) from " + dBSuffixPostgres + ".raas.transaction_log where raas_txn_ref  = '%s'";
    public static final String SELECT_ADD_DATA_FUN_SOURCE_ID_ID_BY_RAAS_TXN_REF =
            "select Cast(additional_data_funding_source as varchar) from " + dBSuffixPostgres + ".raas.transaction_log where raas_txn_ref  = '%s'";
    public static final String SELECT_ADD_DATA_PROD_ID_ID_BY_RAAS_TXN_REF =
            "select Cast(additional_data_product as varchar) from " + dBSuffixPostgres + ".raas.transaction_log where raas_txn_ref  = '%s'";
    public static final String SELECT_TIMESTAMP_BY_RAAS_TXN_REF =
            "select Cast(timestamp as varchar) from " + dBSuffixPostgres + ".raas.transaction_log where raas_txn_ref  = '%s'";
    public static final String SELECT_CREATED_BY_RAAS_TXN_REF =
            "select Cast(created as varchar) from " + dBSuffixPostgres + ".raas.transaction_log where raas_txn_ref  = '%s'";
    public static final String SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF =
            "select Cast(cdc_update_timestamp as varchar) from " + dBSuffixPostgres + ".raas.transaction_log where raas_txn_ref  = '%s'";
    public static final String SELECT_RAAS_REQUEST_CREATED_BY_RAAS_TXN_REF =
            "select Cast(raas_request_created as varchar) from " + dBSuffixPostgres + ".raas.transaction_log where raas_txn_ref  = '%s'";
    public static final String SELECT_RAAS_RESPONSE_CREATED_BY_RAAS_TXN_REF =
            "select Cast(raas_response_created as varchar) from " + dBSuffixPostgres + ".raas.transaction_log where raas_txn_ref  = '%s'";
    public static final String SELECT_RESERVE_FUND_REQUEST_CREATED_BY_RAAS_TXN_REF =
            "select Cast(reserve_fund_request_created as varchar) from " + dBSuffixPostgres + ".raas.transaction_log where raas_txn_ref  = '%s'";
    public static final String SELECT_RESERVE_FUND_RESPONSE_CREATED_BY_RAAS_TXN_REF =
            "select Cast(reserve_fund_response_created as varchar) from " + dBSuffixPostgres + ".raas.transaction_log where raas_txn_ref  = '%s'";
    public static final String SELECT_TRANSACT_RESULT_REQUEST_CREATED_BY_RAAS_TXN_REF =
            "select Cast(transaction_result_request_created as varchar) from " + dBSuffixPostgres + ".raas.transaction_log where raas_txn_ref  = '%s'";
    public static final String SELECT_TRANSACT_RESULT_RESPONSE_CREATED_BY_RAAS_TXN_REF =
            "select Cast(transaction_result_response_created as varchar) from " + dBSuffixPostgres + ".raas.transaction_log where raas_txn_ref  = '%s'";
    public static final String SELECT_RESERVE_FUNDS_TXN_REF_BY_RAAS_TXN_REF_FROM_TXN_LOGS  =
            "select reserve_funds_txn_ref from " + dBSuffixPostgres + ".raas.transaction_log where raas_txn_ref  = '%s'";
    public static final String SELECT_RESERVE_FUNDS_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_TXN_LOGS  =
            "select reserve_fund_response_response_code from " + dBSuffixPostgres + ".raas.transaction_log where raas_txn_ref  = '%s'";

    //Raas request table queries

    public static final String SELECT_ACCOUNT_IDENTIFIER_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST =
            "select account_identifier from " + dBSuffixPostgres + ".raas.raas_request where raas_txn_ref = '%s'";
    public static final String SELECT_AMOUNT_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST =
            "select amount from " + dBSuffixPostgres + ".raas.raas_request where raas_txn_ref = '%s'";
    public static final String SELECT_CHANNEL_ID_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST =
            "select channel_id from " + dBSuffixPostgres + ".raas.raas_request where raas_txn_ref = '%s'";
    public static final String SELECT_CHANNEL_SESSSION_ID_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST =
            "select channel_session_id from " + dBSuffixPostgres + ".raas.raas_request where raas_txn_ref = '%s'";
    public static final String SELECT_CLIENT_ID_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST =
            "select client_id from " + dBSuffixPostgres + ".raas.raas_request where raas_txn_ref = '%s'";
    public static final String SELECT_CLIENT_TXN_REF_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST =
            "select client_txn_ref from " + dBSuffixPostgres + ".raas.raas_request where raas_txn_ref = '%s'";
    public static final String SELECT_CREATED_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST =
            "select Cast(created as varchar) from " + dBSuffixPostgres + ".raas.raas_request where raas_txn_ref  = '%s'";
    public static final String SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST =
            "select event_type from " + dBSuffixPostgres + ".raas.raas_request where raas_txn_ref = '%s'";
    public static final String SELECT_PRODUCT_ID_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST =
            "select product_id from " + dBSuffixPostgres + ".raas.raas_request where raas_txn_ref = '%s'";
    public static final String SELECT_SOURCE_IDENTIFIER_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST =
            "select source_identifier from " + dBSuffixPostgres + ".raas.raas_request where raas_txn_ref = '%s'";
    public static final String SELECT_TARGET_IDENTIFIER_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST =
            "select target_identifier from " + dBSuffixPostgres + ".raas.raas_request where raas_txn_ref = '%s'";
    public static final String SELECT_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST =
            "select Cast(timestamp as varchar) from " + dBSuffixPostgres + ".raas.raas_request where raas_txn_ref  = '%s'";
    public static final String SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST =
            "select Cast(cdc_update_timestamp as varchar) from " + dBSuffixPostgres + ".raas.raas_request where raas_txn_ref  = '%s'";
    public static final String SELECT_FEE_AMOUNT_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST =
            "select fee_amount from " + dBSuffixPostgres + ".raas.raas_request where raas_txn_ref = '%s'";
    public static final String SELECT_API_CALL_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST =
            "select api_call from " + dBSuffixPostgres + ".raas.raas_request where raas_txn_ref = '%s'";
    public static final String SELECT_RAAS_REQ_CREATED_BY_RAAS_TXN_REF_FROM_RAAS_REQUEST =
            "select Cast(created as varchar) from " + dBSuffixPostgres + ".raas.raas_request where raas_txn_ref  = '%s'";

//Raas response table queries
    public static final String SELECT_CREATED_BY_RAAS_TXN_REF_FROM_RAAS_RESPONSE =
            "select Cast(created as varchar) from " + dBSuffixPostgres + ".raas.raas_response where raas_txn_ref  = '%s'";
    public static final String SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_RAAS_RESPONSE =
            "select event_type from " + dBSuffixPostgres + ".raas.raas_response where raas_txn_ref = '%s'";
    public static final String SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_RESPONSE =
            "select Cast(cdc_update_timestamp as varchar) from " + dBSuffixPostgres + ".raas.raas_response where raas_txn_ref  = '%s'";
    public static final String SELECT_RAAS_RES_CREATED_BY_RAAS_TXN_REF_FROM_RAAS_RESPONSE =
            "select Cast(created as varchar) from " + dBSuffixPostgres + ".raas.raas_response where raas_txn_ref  = '%s'";
    public static final String SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF =
            "select response_code from " + dBSuffixPostgres + ".raas.raas_response where raas_txn_ref = '%s'";
    public static final String SELECT_RESPONSE_MESSAGE_BY_RAAS_TXN_REF =
            "select response_message from " + dBSuffixPostgres + ".raas.raas_response where raas_txn_ref = '%s'";

//Reserve Fund Request table queries

    public static final String SELECT_AMOUNT_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ =
            "select amount  from " + dBSuffixPostgres + ".raas.reserve_fund_request where raas_txn_ref  = '%s'";
    public static final String SELECT_CHANNEL_ID_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ =
            "select channel_id from " + dBSuffixPostgres + ".raas.reserve_fund_request where raas_txn_ref = '%s'";
    public static final String SELECT_CHANNEL_SESSSION_ID_BY_RAAS_TXN_REF_RAAS_RES_FUND_REQ =
            "select channel_session_id from " + dBSuffixPostgres + ".raas.reserve_fund_request where raas_txn_ref = '%s'";
    public static final String SELECT_CREATED_BY_RAAS_TXN_REF_RAAS_RES_FUND_REQ =
            "select Cast(created as varchar) from " + dBSuffixPostgres + ".raas.reserve_fund_request where raas_txn_ref  = '%s'";
    public static final String SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_RAAS_RES_FUND_REQ =
            "select event_type from " + dBSuffixPostgres + ".raas.reserve_fund_request where raas_txn_ref = '%s'";
    public static final String SELECT_PRODUCT_ID_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ =
            "select product_id from " + dBSuffixPostgres + ".raas.reserve_fund_request where raas_txn_ref = '%s'";
    public static final String SELECT_PRODUCT_TYPE_ID_BY_RAAS_TXN_REF =
            "select product_type_id from " + dBSuffixPostgres + ".payd_common.product where id = '%s'";
    public static final String SELECT_SOURCE_IDENTIFIER_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ =
            "select source_identifier from " + dBSuffixPostgres + ".raas.reserve_fund_request where raas_txn_ref = '%s'";
    public static final String SELECT_TARGET_IDENTIFIER_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ =
            "select target_identifier from " + dBSuffixPostgres + ".raas.reserve_fund_request where raas_txn_ref = '%s'";
    public static final String SELECT_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ =
            "select Cast(timestamp as varchar) from " + dBSuffixPostgres + ".raas.reserve_fund_request where raas_txn_ref  = '%s'";
    public static final String SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_REQ =
            "select Cast(cdc_update_timestamp as varchar) from " + dBSuffixPostgres + ".raas.reserve_fund_request where raas_txn_ref  = '%s'";

//Reserve Fund Response table queries
    public static final String SELECT_CREATED_BY_RAAS_TXN_REF_RAAS_RES_FUND_RES =
            "select Cast(created as varchar) from " + dBSuffixPostgres + ".raas.reserve_fund_response where raas_txn_ref  = '%s'";
    public static final String SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_RAAS_RES_FUND_RES =
            "select event_type from " + dBSuffixPostgres + ".raas.reserve_fund_response where raas_txn_ref = '%s'";
    public static final String SELECT_RESERVE_FUNDS_TXN_REF_BY_RAAS_TXN_REF_RAAS_RES_FUND_RES  =
            "select reserve_funds_txn_ref from " + dBSuffixPostgres + ".raas.reserve_fund_response where raas_txn_ref  = '%s'";
    public static final String SELECT_FUND_RESPONSE_CODE_BY_RAAS_TXN_REF_RAAS_RES_FUND_RES  =
            "select response_code from " + dBSuffixPostgres + ".raas.reserve_fund_response where raas_txn_ref = '%s'";
    public static final String SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_RAAS_RES_FUND_RES =
            "select Cast(cdc_update_timestamp as varchar) from " + dBSuffixPostgres + ".raas.reserve_fund_response where raas_txn_ref  = '%s'";

//ctx_request table queries

    public static final String SELECT_CLIENT_TRANSACTION_ID_BY_RAAS_TXN_REF_FROM_CTX_REQ =
            "select client_transaction_id from " + dBSuffixPostgres + ".raas.ctx_request  where raas_txn_ref = '%s'";
    public static final String SELECT_AMOUNT_BY_RAAS_TXN_REF_FROM_CTX_REQ =
            "select amount from " + dBSuffixPostgres + ".raas.ctx_request  where raas_txn_ref = '%s'";
    public static final String SELECT_CHANNEL_INDICATOR_BY_RAAS_TXN_REF_FROM_CTX_REQ =
            "select channel_indicator from " + dBSuffixPostgres + ".raas.ctx_request  where raas_txn_ref = '%s'";
    public static final String SELECT_CHANNEL_SESSION_ID_BY_RAAS_TXN_REF_FROM_CTX_REQ =
            "select channel_session_id from " + dBSuffixPostgres + ".raas.ctx_request  where raas_txn_ref = '%s'";
    public static final String SELECT_CLIENT_ID_BY_RAAS_TXN_REF_FROM_CTX_REQ =
            "select client_id from " + dBSuffixPostgres + ".raas.ctx_request  where raas_txn_ref = '%s'";
    public static final String SELECT_CREATED_BY_RAAS_TXN_REF_FROM_CTX_REQUEST =
            "select Cast(created as varchar) from " + dBSuffixPostgres + ".raas.ctx_request where raas_txn_ref  = '%s'";
    public static final String SELECT_DATE_LOCAL_TXN_BY_RAAS_TXN_REF_FROM_CTX_REQUEST =
            "select date_local_transaction from " + dBSuffixPostgres + ".raas.ctx_request where raas_txn_ref  = '%s'";
    public static final String SELECT_ORIGIN_ID_BY_RAAS_TXN_REF_FROM_CTX_REQUEST =
            "select origin_id from " + dBSuffixPostgres + ".raas.ctx_request where raas_txn_ref  = '%s'";
    public static final String SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_CTX_REQUEST =
            "select event_type from " + dBSuffixPostgres + ".raas.ctx_request where raas_txn_ref  = '%s'";
    public static final String SELECT_PRODUCT_ID_BY_RAAS_TXN_REF_FROM_CTX_REQUEST =
            "select product_id from " + dBSuffixPostgres + ".raas.ctx_request where raas_txn_ref  = '%s'";
    public static final String SELECT_SOURCE_ID_BY_RAAS_TXN_REF_FROM_CTX_REQUEST =
            "select source_id from " + dBSuffixPostgres + ".raas.ctx_request where raas_txn_ref  = '%s'";
    public static final String SELECT_TIME_LOCAL_TRANSACTION_BY_RAAS_TXN_REF_FROM_CTX_REQUEST =
            "select time_local_transaction from " + dBSuffixPostgres + ".raas.ctx_request where raas_txn_ref  = '%s'";
    public static final String SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_CTX_REQUEST =
            "select Cast(cdc_update_timestamp as varchar) from " + dBSuffixPostgres + ".raas.ctx_request where raas_txn_ref  = '%s'";

    //ctx_response table queries

    public static final String SELECT_CLIENT_TRANSACTION_ID_BY_RAAS_TXN_REF_FROM_CTX_RES =
            "select client_transaction_id from " + dBSuffixPostgres + ".raas.ctx_response where raas_txn_ref = '%s'";
    public static final String SELECT_AMOUNT_BY_RAAS_TXN_REF_FROM_CTX_RES =
            "select amount from " + dBSuffixPostgres + ".raas.ctx_response where raas_txn_ref = '%s'";
    public static final String SELECT_CHANNEL_INDICATOR_BY_RAAS_TXN_REF_FROM_CTX_RES =
            "select channel_indicator from " + dBSuffixPostgres + ".raas.ctx_response where raas_txn_ref = '%s'";
    public static final String SELECT_CHANNEL_NAME_BY_RAAS_TXN_REF_FROM_CTX_RES =
            "select channel_name from " + dBSuffixPostgres + ".raas.ctx_response where raas_txn_ref = '%s'";
    public static final String SELECT_CLIENT_ID_BY_RAAS_TXN_REF_FROM_CTX_RES =
            "select client_id from " + dBSuffixPostgres + ".raas.ctx_response where raas_txn_ref = '%s'";
    public static final String SELECT_CREATED_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE =
            "select Cast(created as varchar) from " + dBSuffixPostgres + ".raas.ctx_response where raas_txn_ref  = '%s'";
    public static final String SELECT_DATE_LOCAL_TXN_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE =
            "select date_local_transaction from " + dBSuffixPostgres + ".raas.ctx_response where raas_txn_ref  = '%s'";
    public static final String SELECT_ORIGIN_ID_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE =
            "select origin_id from " + dBSuffixPostgres + ".raas.ctx_response where raas_txn_ref  = '%s'";
    public static final String SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE =
            "select event_type from " + dBSuffixPostgres + ".raas.ctx_response where raas_txn_ref  = '%s'";
    public static final String SELECT_PRODUCT_ID_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE =
            "select product_id from " + dBSuffixPostgres + ".raas.ctx_response where raas_txn_ref  = '%s'";
    public static final String SELECT_SOURCE_ID_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE =
            "select source_id from " + dBSuffixPostgres + ".raas.ctx_response where raas_txn_ref  = '%s'";
    public static final String SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE =
            "select response_code from " + dBSuffixPostgres + ".raas.ctx_response where raas_txn_ref  = '%s'";
    public static final String SELECT_TIME_LOCAL_TRANSACTION_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE =
            "select time_local_transaction from " + dBSuffixPostgres + ".raas.ctx_response where raas_txn_ref  = '%s'";
    public static final String SELECT_TRANSMISSION_DATE_TIME_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE =
            "select transmission_date_time from " + dBSuffixPostgres + ".raas.ctx_response where raas_txn_ref  = '%s'";
    public static final String SELECT_VENDOR_REFERENCE_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE =
            "select vendor_reference from " + dBSuffixPostgres + ".raas.ctx_response where raas_txn_ref  = '%s'";
    public static final String SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_CTX_RESPONSE =
            "select Cast(cdc_update_timestamp as varchar) from " + dBSuffixPostgres + ".raas.ctx_response where raas_txn_ref  = '%s'";

    //raas.transaction_result_request table queries


    public static final String SELECT_CREATED_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ =
            "select Cast(created as varchar) from " + dBSuffixPostgres + ".raas.transaction_result_request where raas_txn_ref  = '%s'";
    public static final String SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ =
            "select event_type from " + dBSuffixPostgres + ".raas.transaction_result_request where raas_txn_ref  = '%s'";
    public static final String SELECT_TIMESTAMP_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ =
            "select Cast(timestamp as varchar) from " + dBSuffixPostgres + ".raas.transaction_result_request where raas_txn_ref  = '%s'";
    public static final String SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ =
            "select response_code from " + dBSuffixPostgres + ".raas.transaction_result_request where raas_txn_ref  = '%s'";
    public static final String SELECT_TRANSACTION_RES_REQ_CREATED_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ =
            "select Cast(transaction_result_request_created as varchar) from " + dBSuffixPostgres + ".raas.transaction_result_request where raas_txn_ref  = '%s'";
    public static final String SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_REQ =
            "select Cast(cdc_update_timestamp as varchar) from " + dBSuffixPostgres + ".raas.transaction_result_request where raas_txn_ref  = '%s'";
    public static final String SELECT_RESULT_REQUEST_RESPONSE_CODE_BY_RAAS_TXN_REF =
            "select response_code from " + dBSuffixPostgres + ".raas.transaction_result_request where raas_txn_ref = '%s'";

    //raas.transaction_result_response table queries


    public static final String SELECT_CREATED_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES =
            "select Cast(created as varchar) from " + dBSuffixPostgres + ".raas.transaction_result_response where raas_txn_ref  = '%s'";
    public static final String SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES =
            "select event_type from " + dBSuffixPostgres + ".raas.transaction_result_response where raas_txn_ref  = '%s'";
    public static final String SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES =
            "select response_code from " + dBSuffixPostgres + ".raas.transaction_result_response where raas_txn_ref  = '%s'";
    public static final String SELECT_TRANSACTION_RES_RE_CREATED_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES =
            "select Cast(transaction_result_response_created as varchar) from " + dBSuffixPostgres + ".raas.transaction_result_response where raas_txn_ref  = '%s'";
    public static final String SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_TRANSACTION_RESULT_RES =
            "select Cast(cdc_update_timestamp as varchar) from " + dBSuffixPostgres + ".raas.transaction_result_response where raas_txn_ref  = '%s'";
    public static final String SELECT_RESULT_RESPONSE_CODE_BY_RAAS_TXN_REF =
            "select response_code from " + dBSuffixPostgres + ".raas.transaction_result_response where raas_txn_ref = '%s'";

    //cpgtx.tran_log table queries
    public static final String SELECT_TRANSACTION_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select transactionid from " + dBSuffix + ".tran_log where clientTransactionId = '%s"+"-0000'";
    public static final String SELECT_CLIENT_STAN_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select clientStan from " + dBSuffix + ".tran_log where clientTransactionId = '%s"+"-0000'";
    public static final String SELECT_END_ACC_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select endAccountingDate from " + dBSuffix + ".tran_log where clientTransactionId = '%s"+"-0000'";
    public static final String SELECT_END_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select endDate from " + dBSuffix + ".tran_log where clientTransactionId = '%s"+"-0000'";
    public static final String SELECT_END_VENDOR_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select endVendorDate from " + dBSuffix + ".tran_log where clientTransactionId = '%s"+"-0000'";
    public static final String SELECT_INTERNAL_RES_REASON_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select internalResponseReason from " + dBSuffix + ".tran_log where clientTransactionId = '%s"+"-0000'";
    public static final String SELECT_MESSAGE_IDENTIFIER_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select messageIdentifier from " + dBSuffix + ".tran_log where clientTransactionId = '%s"+"-0000'";
    public static final String SELECT_ORIGIN_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select originID from " + dBSuffix + ".tran_log where clientTransactionId = '%s"+"-0000'";
    public static final String SELECT_ORIGINATING_SERVICE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select originatingService from " + dBSuffix + ".tran_log where clientTransactionId = '%s"+"-0000'";
    public static final String SELECT_PURCHASE_AMOUNT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select purchaseAmount from " + dBSuffix + ".tran_log where clientTransactionId = '%s"+"-0000'";
    public static final String SELECT_QUANTITY_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select quantity from " + dBSuffix + ".tran_log where clientTransactionId = '%s"+"-0000'";
    public static final String SELECT_TXN_RES_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select transactionResponseCode from " + dBSuffix + ".tran_log where clientTransactionId = '%s"+"-0000'";
    public static final String SELECT_REV_TXN_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select reversalTransactionId from " + dBSuffix + ".tran_log where clientTransactionId = '%s"+"-0000'";
    public static final String SELECT_START_ACCOUNT_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select startAccountingDate from " + dBSuffix + ".tran_log where clientTransactionId = '%s"+"-0000'";
    public static final String SELECT_START_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select startDate from " + dBSuffix + ".tran_log where clientTransactionId = '%s"+"-0000'";
    public static final String SELECT_START_VENDOR_DATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select startVendorDate from " + dBSuffix + ".tran_log where clientTransactionId = '%s"+"-0000'";
    public static final String SELECT_TXN_STATE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select transactionState from " + dBSuffix + ".tran_log where clientTransactionId = '%s"+"-0000'";
    public static final String SELECT_TXN_TYPE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select transactionType from " + dBSuffix + ".tran_log where clientTransactionId = '%s"+"-0000'";
    public static final String SELECT_VENDOR_REF_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select vendorReference from " + dBSuffix + ".tran_log where clientTransactionId = '%s"+"-0000'";
    public static final String SELECT_VENDOR_RES_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select vendorResponseCode from " + dBSuffix + ".tran_log where clientTransactionId = '%s"+"-0000'";
    public static final String SELECT_VENDOR_STAN_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select vendorStan from " + dBSuffix + ".tran_log where clientTransactionId = '%s"+"-0000'";
    public static final String SELECT_CLIENT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select client_id from " + dBSuffix + ".tran_log where clientTransactionId = '%s"+"-0000'";
    public static final String SELECT_PRODUCT_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select product_id from " + dBSuffix + ".tran_log where clientTransactionId = '%s"+"-0000'";
    public static final String SELECT_VENDOR_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select vendor_id from " + dBSuffix + ".tran_log where clientTransactionId = '%s"+"-0000'";
    public static final String SELECT_SYSTEM_RES_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select systemResponseCode from " + dBSuffix + ".tran_log where clientTransactionId = '%s"+"-0000'";
    public static final String SELECT_RES_CODE_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select responseCode from " + dBSuffix + ".tran_log where clientTransactionId = '%s"+"-0000'";
    public static final String SELECT_FEE_AMOUNT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select feeAmount from " + dBSuffix + ".tran_log where clientTransactionId = '%s"+"-0000'";
    public static final String SELECT_RECON_NOTE_CLIENT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select reconNoteClient from " + dBSuffix + ".tran_log where clientTransactionId = '%s"+"-0000'";
    public static final String SELECT_RECON_STATE_CLIENT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select reconStateClient from " + dBSuffix + ".tran_log where clientTransactionId = '%s"+"-0000'";
    public static final String SELECT_RECON_STATE_VENDOR_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select reconStateVendor from " + dBSuffix + ".tran_log where clientTransactionId = '%s"+"-0000'";
    public static final String SELECT_RECON_NOTE_VENDOR_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select reconNoteVendor from " + dBSuffix + ".tran_log where clientTransactionId = '%s"+"-0000'";
    public static final String SELECT_LAST_UPDATED_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select last_updated from " + dBSuffix + ".tran_log where clientTransactionId = '%s"+"-0000'";
    public static final String SELECT_COMM_AMOUNT_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select commissionAmount from " + dBSuffix + ".tran_log where clientTransactionId = '%s"+"-0000'";
    public static final String SELECT_GLOBAL_REASON_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select global_reason from " + dBSuffix + ".tran_log where clientTransactionId = '%s"+"-0000'";
    public static final String SELECT_CHANNEL_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select channel_id from " + dBSuffix + ".tran_log where clientTransactionId = '%s"+"-0000'";
    public static final String SELECT_API_ID_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select api_id from " + dBSuffix + ".tran_log where clientTransactionId = '%s"+"-0000'";
    public static final String SELECT_COMM_AMOUNT_INTERNAL_BY_CLIENT_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG =
            "select commissionAmountInternal from " + dBSuffix + ".tran_log where clientTransactionId = '%s"+"-0000'";

    //cpgtx.commission_model  table queries
    public static final String SELECT_COMM_MODEL_TYPE_BY_CLIENT_VENDOR_PROD_TYPE_ID_FROM_COMM_MODEL =
            "select commissionModelType from " + dBSuffix + ".commission_model where  client_id = %s and vendor_id = %s and product_type_id = %s";

    //cpgtx.tran_log_ext table queries
    public static final String SELECT_TRANSACTION_ID_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_EXT =
            "select transactionid from " + dBSuffix + ".tran_log_ext where transactionId = '%s'";
    public static final String SELECT_TRANSACTION_STATUS_LOOKUP_DATE_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_EXT =
            "select txStatusLookupDate from " + dBSuffix + ".tran_log_ext where transactionId = '%s'";
    public static final String SELECT_SOURCE_ID_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_EXT =
            "select sourceId from " + dBSuffix + ".tran_log_ext where transactionId = '%s'";
    public static final String SELECT_ACCOUNT_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_EXT =
            "select account from " + dBSuffix + ".tran_log_ext where transactionId = '%s'";
    public static final String SELECT_INSERT_TIMESTAMP_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_EXT =
            "select insert_timestamp from " + dBSuffix + ".tran_log_ext where transactionId = '%s'";


    //cpgtx.tran_log_channel table queries
    public static final String SELECT_TRANSACTION_ID_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL =
            "select transactionid from " + dBSuffix + ".tran_log_channel where transactionId = '%s'";
    public static final String SELECT_CHANNEL_SESSION_ID_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL =
            "select channelSessionId  from " + dBSuffix + ".tran_log_channel where transactionId = '%s'";
    public static final String SELECT_CHANNEL_NAME_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL =
            "select channelName from " + dBSuffix + ".tran_log_channel where transactionId = '%s'";
    public static final String SELECT_CHANNEL_ID_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL =
            "select channelId from " + dBSuffix + ".tran_log_channel where transactionId = '%s'";
    public static final String SELECT_INSERT_TIMESTAMP_BY_TRANSACTION_ID_FROM_CGPTX_TRAN_LOG_CHANNEL =
            "select insert_timestamp from " + dBSuffix + ".tran_log_channel where transactionId = '%s'";

    //ctx_lookup_request table queries
    public static final String SELECT_ID_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST =
            "select id from " + dBSuffixPostgres + ".raas.ctx_lookup_request where raas_txn_ref = '%s'";
    public static final String SELECT_CLIENT_ID_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST =
            "select client_id from " + dBSuffixPostgres + ".raas.ctx_lookup_request where raas_txn_ref = '%s' limit 1";
    public static final String SELECT_CLIENT_TRANSACTION_ID_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST =
            "select client_transaction_id from " + dBSuffixPostgres + ".raas.ctx_lookup_request where raas_txn_ref = '%s' limit 1";
    public static final String SELECT_CREATED_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST =
            "select Cast(created as varchar) from " + dBSuffixPostgres + ".raas.ctx_lookup_request where raas_txn_ref  = '%s' limit 1";
    public static final String SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST =
            "select event_type from " + dBSuffixPostgres + ".raas.ctx_lookup_request where raas_txn_ref = '%s' limit 1";
    public static final String SELECT_RAAS_TXN_REF_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST =
            "select raas_txn_ref from " + dBSuffixPostgres + ".raas.ctx_lookup_request where raas_txn_ref = '%s' limit 1";
    public static final String SELECT_UPDATED_TIMESTAMP_RAAS_TXN_REF_FROM_CTX_LOOKUP_REQUEST =
            "select Cast(cdc_update_timestamp as varchar) from " + dBSuffixPostgres + ".raas.ctx_lookup_request where raas_txn_ref  = '%s' limit 1";

    //ctx_lookup_response  table queries
    public static final String SELECT_ID_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_RESPONSE =
            "select id from " + dBSuffixPostgres + ".raas.ctx_lookup_response where raas_txn_ref = '%s' and response_code = 0";
    public static final String SELECT_AMOUNT_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_RESPONSE =
            "select amount from " + dBSuffixPostgres + ".raas.ctx_lookup_response where raas_txn_ref = '%s' and response_code = 0";
    public static final String SELECT_CHANNEL_INDICATOR_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_RESPONSE =
            "select channel_indicator from " + dBSuffixPostgres + ".raas.ctx_lookup_response where raas_txn_ref = '%s' and response_code = 0";
    public static final String SELECT_CHANNEL_NAME_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_RESPONSE =
            "select channel_name from " + dBSuffixPostgres + ".raas.ctx_lookup_response where raas_txn_ref = '%s' and response_code = 0";
    public static final String SELECT_CHANNEL_SESSION_ID_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_RESPONSE =
            "select channel_session_id from " + dBSuffixPostgres + ".raas.ctx_lookup_response where raas_txn_ref = '%s' and response_code = 0";
    public static final String SELECT_CLIENT_ID_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_RESPONSE =
            "select client_id from " + dBSuffixPostgres + ".raas.ctx_lookup_response where raas_txn_ref = '%s' and response_code = 0";
    public static final String SELECT_CLIENT_TRANSACTION_ID_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_RESPONSE =
            "select client_transaction_id from " + dBSuffixPostgres + ".raas.ctx_lookup_response where raas_txn_ref = '%s' and response_code = 0";
    public static final String SELECT_CREATED_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_RESPONSE =
            "select Cast(created as varchar) from " + dBSuffixPostgres + ".raas.ctx_lookup_response where raas_txn_ref  = '%s' and response_code = 0";
    public static final String SELECT_DATE_LOCAL_TXN_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_RESPONSE =
            "select Cast(date_local_transaction as varchar) from " + dBSuffixPostgres + ".raas.ctx_lookup_response where raas_txn_ref  = '%s' and response_code = 0";
    public static final String SELECT_EVENT_TYPE_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_RESPONSE =
            "select event_type from " + dBSuffixPostgres + ".raas.ctx_lookup_response where raas_txn_ref = '%s' and response_code = 0";
    public static final String SELECT_ORIGIN_ID_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_RESPONSE =
            "select origin_id from " + dBSuffixPostgres + ".raas.ctx_lookup_response where raas_txn_ref = '%s' and response_code = 0";
    public static final String SELECT_PRODUCT_ID_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_RESPONSE =
            "select product_id from " + dBSuffixPostgres + ".raas.ctx_lookup_response where raas_txn_ref = '%s' and response_code = 0";
    public static final String SELECT_RESPONSE_CODE_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_RESPONSE =
            "select response_code from " + dBSuffixPostgres + ".raas.ctx_lookup_response where raas_txn_ref = '%s' and response_code = 0";
    public static final String SELECT_SOURCE_ID_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_RESPONSE =
            "select source_id from " + dBSuffixPostgres + ".raas.ctx_lookup_response where raas_txn_ref = '%s' and response_code = 0";
    public static final String SELECT_TIME_LOCAL_TXN_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_RESPONSE =
            "select Cast(time_local_transaction as varchar) from " + dBSuffixPostgres + ".raas.ctx_lookup_response where raas_txn_ref  = '%s' and response_code = 0";
    public static final String SELECT_TRANSMISSION_DATE_TIME_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_RESPONSE =
            "select Cast(transmission_date_time as varchar) from " + dBSuffixPostgres + ".raas.ctx_lookup_response where raas_txn_ref  = '%s' and response_code = 0";
    public static final String SELECT_VENDOR_REFERENCE_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_RESPONSE =
            "select vendor_reference from " + dBSuffixPostgres + ".raas.ctx_lookup_response where raas_txn_ref = '%s' and response_code = 0";
    public static final String SELECT_CDC_UPDATE_TIMESTAMP_BY_RAAS_TXN_REF_FROM_CTX_LOOKUP_RESPONSE =
            "select Cast(cdc_update_timestamp as varchar) from " + dBSuffixPostgres + ".raas.ctx_lookup_response where raas_txn_ref  = '%s' and response_code = 0";

}
