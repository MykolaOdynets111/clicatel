package db.custom_queries;

public class FinancialTermsQueries extends BaseCustomQuery{
    public static final String DELETE_VENDOR_DISCOUNT_PERCENTAGE_BY_PRODUCT_ID =
            "delete from " + dBSuffixPostgres + ".financialterms.vendor_discount_percentage where product_id='%s'";
    public static final String DELETE_TPV_CLIENT_SHARE_BY_PRODUCT_ID =
            "delete from " + dBSuffixPostgres + ".financialterms.tpv_client_share where product_id='%s'";
    public static final String DELETE_MODEL_SELECTION_BY_PRODUCT_ID =
            "delete from " + dBSuffixPostgres + ".financialterms.model_selection where product_id='%s'";
    public static final String INSERT_VENDOR_DISCOUNT_PERCENTAGE_BY_PRODUCT_ID =
            "insert into " + dBSuffixPostgres + ".financialterms.vendor_discount_percentage (id, vendor_id, value,valid_from,valid_to,cdc_update_timestamp,created,product_id) values ('%s', '%s', '%s','%s','%s','%s','%s','%s');";
    public static final String SELECT_MODEL_SELECTION_BY_PRODUCT_ID =
            "select model_id from " + dBSuffixPostgres + ".financialterms.model_selection where client_id = '%s' and product_id='%s'";
    public static final String SELECT_TPV_CLIENT_SHARE_BY_PRODUCT_ID =
            "select value from " + dBSuffixPostgres + ".financialterms.tpv_client_share where client_id = '%s' and product_id='%s'";
    public static final String UPDATE_VENDOR_DISCOUNT_PERCENTAGE_BY_VENDOR_ID =
            "update " + dBSuffixPostgres + ".financialterms.vendor_discount_percentage set value = %s where vendor_id = %s";
    public static final String UPDATE_MODEL_SELECTION =
            "update " + dBSuffixPostgres + ".financialterms.model_selection set model_id = %s where vendor_id = %s and client_id = %s and product_id = %s";
    public static final String UPDATE_TPV_CLIENT_SHARE =
            "update " + dBSuffixPostgres + ".financialterms.tpv_client_share set value = %s where client_id = %s and product_id = %s";

}
