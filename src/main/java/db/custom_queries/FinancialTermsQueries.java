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
    public static final String SELECT_VENDOR_DISCOUNT_PERCENTAGE_BY_VENDOR_ID=
            "select value from " + dBSuffixPostgres + ".financialterms.vendor_discount_percentage where vendor_id = '%s' and product_id='%s'";
    public static final String SELECT_Client_id_Financial_Terms_Audit_Log=
            "select client_id from " + dBSuffixPostgres + ".financialterms.lookup_audit_log where id = '%s'";
    public static final String SELECT_Product_Id_Financial_Terms_Audit_Log=
            "select product_id from " + dBSuffixPostgres + ".financialterms.lookup_audit_log where id = '%s'";
    public static final String SELECT_Reserve_Amount_Financial_Terms_Audit_Log=
            "select reserve_amount from " + dBSuffixPostgres + ".financialterms.lookup_audit_log where id = '%s'";
    public static final String SELECT_Client_Share_Amount_Financial_Terms_Audit_Log=
            "select client_share_amount from " + dBSuffixPostgres + ".financialterms.lookup_audit_log where id = '%s'";
    public static final String SELECT_Settlement_Amount_Financial_Terms_Audit_Log=
            "select settlement_amount from " + dBSuffixPostgres + ".financialterms.lookup_audit_log where id = '%s'";
    public static final String SELECT_Fee_Amount_Financial_Terms_Audit_Log=
            "select fee_amount from " + dBSuffixPostgres + ".financialterms.lookup_audit_log where id = '%s'";
    public static final String SELECT_vendor_Share_Amount_Financial_Terms_Audit_Log=
            "select vendor_share_amount from " + dBSuffixPostgres + ".financialterms.lookup_audit_log where id = '%s'";
    public static final String SELECT_Vend_Amount_Financial_Terms_Audit_Log=
            "select vend_amount from " + dBSuffixPostgres + ".financialterms.lookup_audit_log where id = '%s'";
    public static final String UPDATE_VENDOR_DISCOUNT_PERCENTAGE_BY_VENDOR_ID =
            "update " + dBSuffixPostgres + ".financialterms.vendor_discount_percentage set value = %s where vendor_id = %s";
    public static final String UPDATE_MODEL_SELECTION =
            "update " + dBSuffixPostgres + ".financialterms.model_selection set model_id = %s where vendor_id = %s and client_id = %s and product_id = %s";
    public static final String UPDATE_TPV_CLIENT_SHARE =
            "update " + dBSuffixPostgres + ".financialterms.tpv_client_share set value = %s where client_id = %s and product_id = %s";
    public static final String Insert_Financial_Terms_Model_Selection =
            "insert into " + dBSuffixPostgres + ".financialterms.model_selection(id, client_id, product_id, model_id, valid_from, valid_to, cdc_update_timestamp, created, vendor_id)values('%s', '%s', '%s','%s','%s','%s','%s','%s','%s');";
    public static final String Insert_Financial_Terms_TPV_Client_Share =
            "insert into " + dBSuffixPostgres + ".financialterms.tpv_client_share(id, client_id, value, valid_from, valid_to, cdc_update_timestamp, created, product_id)values('%s', '%s', '%s','%s','%s','%s','%s','%s');";

}
