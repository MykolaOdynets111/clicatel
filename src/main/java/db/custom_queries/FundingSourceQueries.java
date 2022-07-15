package db.custom_queries;

public class FundingSourceQueries extends BaseCustomQuery{
    public static final String DELETE_FUNDING_SOURCE_COUNTRY_BY_FUNDING_SOURCE_ID =
            "delete from " + dBSuffixPostgres + ".payd_common.funding_source_country where funding_source_id='%s'";
    public static final String DELETE_FUNDING_SOURCE_BY_FUNDING_SOURCE_ID =
            "delete from " + dBSuffixPostgres + ".payd_common.funding_source where funding_source_id='%s'";
    public static final String SELECT_FUNDING_SOURCE_NAME_BY_FUNDING_SOURCE_ID =
            "select funding_source_name from " + dBSuffixPostgres + ".payd_common.funding_source where funding_source_id='%s'";
    public static final String SELECT_TRANSACTION_RESULT_ENDPOINT_BY_FUNDING_SOURCE_ID =
            "select transact_result_endpoint from " + dBSuffixPostgres + ".payd_common.funding_source where funding_source_id='%s'";
    public static final String SELECT_MAXIMUM_RETRIES_BY_FUNDING_SOURCE_ID =
            "select max_retries from " + dBSuffixPostgres + ".payd_common.funding_source where funding_source_id='%s'";
    public static final String SELECT_RESERVE_FUND_ENDPOINT_BY_FUNDING_SOURCE_ID =
            "select reserve_funds_endpoint from " + dBSuffixPostgres + ".payd_common.funding_source where funding_source_id='%s'";
    public static final String SELECT_RESERVATION_TIMEOUT_BY_FUNDING_SOURCE_ID =
            "select reservation_timeout from " + dBSuffixPostgres + ".payd_common.funding_source where funding_source_id='%s'";
    public static final String SELECT_RETRY_DELAY_BY_FUNDING_SOURCE_ID =
            "select retry_delay from " + dBSuffixPostgres + ".payd_common.funding_source where funding_source_id='%s'";
    public static final String SELECT_INITIAL_RETRY_DELAY_BY_FUNDING_SOURCE_ID =
            "select initial_retry_delay from " + dBSuffixPostgres + ".payd_common.funding_source where funding_source_id='%s'";
    public static final String SELECT_SERVICE_WINDOW_SIZE_SECONDS_BY_FUNDING_SOURCE_ID =
            "select service_window_size_seconds from " + dBSuffixPostgres + ".payd_common.funding_source where funding_source_id='%s'";
    public static final String SELECT_CONFIRMATION_WINDOW_SIZE_SECONDS_BY_FUNDING_SOURCE_ID =
            "select confirmation_window_size_seconds from " + dBSuffixPostgres + ".payd_common.funding_source where funding_source_id='%s'";
    public static final String SELECT_RAAS_TXN_HISTORY_ENABLED_BY_FUNDING_SOURCE_ID =
            "select raas_txn_history_enabled from " + dBSuffixPostgres + ".payd_common.funding_source where funding_source_id='%s'";
    public static final String SELECT_ACTIVE_BY_FUNDING_SOURCE_ID =
            "select active from " + dBSuffixPostgres + ".payd_common.funding_source where funding_source_id='%s'";
    public static final String SELECT_SUPPORT_TOKEN_BY_FUNDING_SOURCE_ID =
            "select support_token from " + dBSuffixPostgres + ".payd_common.funding_source where funding_source_id='%s'";
    public static final String SELECT_STATUS_LOOKUP_ENDPOINT_BY_FUNDING_SOURCE_ID =
            "select status_lookup_endpoint from " + dBSuffixPostgres + ".payd_common.funding_source where funding_source_id='%s'";
    public static final String SELECT_SIGNATURE_REQUIRED_BY_FUNDING_SOURCE_ID =
            "select signature_required from " + dBSuffixPostgres + ".payd_common.funding_source where funding_source_id='%s'";
    public static final String SELECT_COUNTRY_CODES_BY_FUNDING_SOURCE_ID =
            "select country_code from " + dBSuffixPostgres + ".payd_common.funding_source_country where funding_source_id='%s'";
    public static final String DELETE_CLIENT_FUNDING_SOURCE_BY_FUNDING_SOURCE_ID =
            "delete from " + dBSuffixPostgres + ".payd_common.client_funding_source where funding_source_id='%s'";
    public static final String DELETE_VELOCITY_CONFIG_BY_FUNDING_SOURCE_ID =
            "delete from " + dBSuffixPostgres + ".velocity.velocity_config where funding_source_id='%s'";
    public static final String INSERT_VELOCITY_CONFIG_BY_FUNDING_SOURCE_ID =
            "insert into " + dBSuffixPostgres + ".velocity.velocity_config (funding_source_id, properties, cdc_update_timestamp) values ('%s', %s, '%s')";
    public static final String SELECT_FUNDING_SOURCE_BY_FUNDING_SOURCE_ID =
            "select funding_source_id from " + dBSuffixPostgres + ".payd_common.client_funding_source where funding_source_id='%s' and client_id ='%s'";
    public static final String SELECT_FUNDING_SOURCE_NAME_BY_ACTIVE = "select funding_source_name from " + dBSuffixPostgres + ".payd_common.funding_source where active ='%s'";

}
