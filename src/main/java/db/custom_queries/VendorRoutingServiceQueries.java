package db.custom_queries;

public class VendorRoutingServiceQueries extends BaseCustomQuery{

    public static final String DELETE_VENDOR_PRODUCT =
            "delete from " + dBSuffixPostgres + ".payd_common.vendor_product where product_id in (select id from payd_common.product where description like '%testSyncProduct%')";
    public static final String DELETE_PRODUCT_COUNTRY =
            "delete from " + dBSuffixPostgres + ".payd_common.product_country where product_id in (select id from payd_common.product where description like '%testSyncProduct%')";
    public static final String DELETE_PRODUCT_ATTRIBUTE_VALUE =
            "delete from " + dBSuffixPostgres + ".payd_common.product_attribute_value where product_id in (select id from payd_common.product where description like '%testSyncProduct%')";
    public static final String DELETE_PRODUCT_PURCHASE_MEDIUM =
            "delete from " + dBSuffixPostgres + ".payd_common.product_purchase_medium where product_id in (select id from payd_common.product where description like '%testSyncProduct%')";
    public static final String DELETE_PRODUCT_TOP_SELLER_PLATFORM =
            "delete from " + dBSuffixPostgres + ".payd_common.product_topseller_platform where product_id in (select id from payd_common.product where description like '%testSyncProduct%')";
    public static final String DELETE_PRODUCT_SUBSCRIBER_TYPE =
            "delete from " + dBSuffixPostgres + ".payd_common.product_subscriber_type where product_id in (select id from payd_common.product where description like '%testSyncProduct%')";
    public static final String DELETE_PRODUCT_CHARGE_TYPE =
            "delete from " + dBSuffixPostgres + ".payd_common.product_charge_type where product_id in (select id from payd_common.product where description like '%testSyncProduct%')";
    public static final String DELETE_PRODUCT =
            "delete from " + dBSuffixPostgres + ".payd_common.product where description like '%testSyncProduct%'";
    public static final String SELECT_PRODUCT =
            "select distinct id from " + dBSuffixPostgres + ".payd_common.product join payd_common.product_attribute_value on payd_common.product.id = payd_common.product_attribute_value.product_id where description like '%testSyncProduct%'";
    public static final String SELECT_PRODUCT_ATTRIBUTE_ID =
            "select distinct product_attribute_id from " + dBSuffixPostgres + ".payd_common.product join payd_common.product_attribute_value on payd_common.product.id = payd_common.product_attribute_value.product_id where description like '%testSyncProduct%'";
    public static final String SELECT_PRODUCT_TopSeller =
            "select topseller from " + dBSuffixPostgres + ".payd_common.product where description like '%testSyncProduct%'";
    public static final String SELECT_PRODUCT_TopSellerOn =
            "select topseller_platform_id from " + dBSuffixPostgres + ".payd_common.product join payd_common.product_topseller_platform on payd_common.product_topseller_platform.product_id = payd_common.product.id where description like '%testSyncProduct%'";
    public static final String SELECT_PRODUCT_PURCHASE_MEDIUM =
            "select purchase_medium_id from " + dBSuffixPostgres + ".payd_common.product join payd_common.product_purchase_medium on payd_common.product_purchase_medium.product_id = payd_common.product.id where description like '%testSyncProduct%'";
    public static final String SELECT_PRODUCT_SHORT_DESCRIPTION =
            "select short_description from " + dBSuffixPostgres + ".payd_common.product where description like '%testSyncProduct%'";
    public static final String SELECT_PRODUCT_VALUE =
            "select value from " + dBSuffixPostgres + ".payd_common.product join payd_common.product_attribute_value on payd_common.product.id = payd_common.product_attribute_value.product_id where description = 'productDescription :: testSyncProduct' and product_attribute_id='%s'";
    public static final String SELECT_CHARGETYPE=
            "select chargetype_id from " + dBSuffixPostgres + ".payd_common.product join payd_common.product_charge_type on payd_common.product.id = payd_common.product_charge_type.product_id where description like '%testSyncProduct%'";
    public static final String DELETE_PRODUCT_BY_CLIENTID=
            "delete from " + dBSuffixPostgres + ".payd_common.client_product where client_id = '%s'";
    public static final String INSERT_PRODUCT=
            "insert into " + dBSuffixPostgres + ".payd_common.client_product (client_id, product_id, channel_id, cdc_update_timestamp) values('%s', '%s', '%s', '%s');";
    public static final String DELETE_PRODUCT_ATTRIBUTE_VALUE_BY_PRODUCTIDS =
            "delete from " + dBSuffixPostgres + ".payd_common.product_attribute_value where product_id in ('%s','%s','%s')";
    public static final String INSERT_PRODUCT_ATTRIBUTES_VALUES=
            "insert into " + dBSuffixPostgres + ".payd_common.product_attribute_value (product_attribute_id, product_id, value) values ('%s', '%s', '%s');";
    public static final String UPDATE_PRODUCT_TYPE=
            "update " + dBSuffixPostgres + ".payd_common.product set product_type_id = '%s' where id = '%s'";
    public static final String UPDATE_PRODUCT_PRICING=
            "update " + dBSuffixPostgres + ".payd_common.product set pricing =" + "'{"+ '"' + "type" + '"'  + ":" + '"' +"%s" +  '"' + "," +  '"' + "amount" + '"' + ":%s" + "}'" + "where id = '%s';";
//    public static final String DELETE_VENDOR_PRODUCT_BY_PRODUCT_ID =
//            "delete from " + dBSuffixPostgres + ".payd_common.vendor_product where product_id = '%s'";
//    public static final String DELETE_PRODUCT_ATTRIBUTE_VALUE_BY_PRODUCT_ID =
//            "delete from " + dBSuffixPostgres + ".payd_common.product_attribute_value product_id = '%s')";

}
