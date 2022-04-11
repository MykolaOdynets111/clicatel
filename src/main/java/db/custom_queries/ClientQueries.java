package db.custom_queries;

public class ClientQueries extends BaseCustomQuery {
    public static final String DELETE_CLIENT_MODULE_CONFIG =
            "delete from " + dBSuffix + ".module_config where client_id ='%s'";
    public static final String DELETE_CLIENT_CREDIT_LIMIT =
            "delete from " + dBSuffix + ".client_credit_limit where id ='%s'";
    public static final String DELETE_CLIENT_STAN =
            "delete from " + dBSuffix + ".stan  where client_id  ='%s'";
    public static final String DELETE_CLIENT_COMMISSION_MODEL =
            "delete from " + dBSuffix + ".commission_model where client_id  ='%s'";
    public static final String DELETE_CLIENT =
            "delete from " + dBSuffix + ".client where id  ='%s'";
    public static final String DELETE_ACCOUNT =
            "delete from " + dBSuffix + ".account where description ='%s'";
    public static final String DELETE_CLIENT_SYSTEM_ACCESS =
            "delete from " + dBSuffixPostgres + ".payd_common.client_system_access where client_id='%s'";
    public static final String DELETE_CLIENT_COMMON =
            "delete from " + dBSuffixPostgres + ".payd_common.client where client_id='%s'";
    public static final String SELECT_CLIENT_NAME =
            "select name from " + dBSuffixPostgres + ".payd_common.client where client_id='%s'";
    public static final String SELECT_ACTIVE =
            "select active from " + dBSuffixPostgres + ".payd_common.client where client_id='%s'";
    public static final String SELECT_TIMEZONE_ID =
            "select timezone_id from " + dBSuffixPostgres + ".payd_common.client where client_id='%s'";
    public static final String SELECT_COUNTRY_CODE =
            "select country_code from " + dBSuffixPostgres + ".payd_common.client where client_id='%s'";
    public static final String SELECT_CLICKATELL_ACCOUNT_ID =
            "select clickatell_account_id from " + dBSuffixPostgres + ".payd_common.client where client_id='%s'";
    public static final String SELECT_SIGNATURE_REQUIRED =
            "select signature_required from " + dBSuffixPostgres + ".payd_common.client where client_id='%s'";
    public static final String SELECT_CDC_TIMESTAMP =
            "select signature_required from " + dBSuffixPostgres + ".payd_common.client where client_id='%s'";
    public static final String SELECT_SYSTEM_ID =
            "select system_id from " + dBSuffixPostgres + ".payd_common.client_system_access where client_id ='%s'";
    public static final String SELECT_CLIENT_CLIENTROLE =
            "select clientRole from " + dBSuffix + ".client where id  ='%s'";
    public static final String SELECT_CLIENT_NAME_CPGTX =
            "select name from " + dBSuffix + ".client where id  ='%s'";
    public static final String SELECT_CLIENT_CURRENCY_ID=
            "select currency_id from " + dBSuffix + ".client where id  ='%s'";
    public static final String SELECT_CLIENT_TIMEZONE_ID=
            "select timezone_id from " + dBSuffix + ".client where id  ='%s'";
    public static final String SELECT_CLIENT_ACTIVE=
            "select active from " + dBSuffix + ".client where id  ='%s'";
    public static final String SELECT_CLIENT_CREDIT_LIMIT=
            "select limit_total from " + dBSuffix + ".client_credit_limit where id  ='%s'";
    public static final String SELECT_CLIENT_MODULE_CONFIG=
            "select config_key from " + dBSuffix + ".module_config where client_id ='%s'";
    public static final String SELECT_CLIENT_STAN=
            "select increment_value from " + dBSuffix + ".stan where client_id ='%s'";
    public static final String SELECT_CLIENT_COMMISSION_MODEL_TYPE=
            "select commissionModelType from " + dBSuffix + ".commission_model where client_id ='%s'";
    public static final String SELECT_ACCOUNT =
            "select description from " + dBSuffix + ".account where description ='%s'";
}

