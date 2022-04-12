package db.custom_queries;

import static db.custom_queries.BaseCustomQuery.dBSuffix;

public class ProductLookupQueries extends BaseCustomQuery {

    public static final String GET_CLIENT_DETAILS_BY_CLIENT_ID =
            "select name from " + dBSuffixPostgres + ".payd_common.client where client_id='%d'";

    public static final String GET_CLIENT_BY_CLIENT_ID =
            "select name from " + dBSuffix + ".client where id=3";

    public static final String DELETE_VENDOR_PRODUCT_BY_PRODUCT_ID=
            "delete from " + dBSuffixPostgres + ".payd_common.vendor_product where product_id = '%s'";

    public static final String DELETE_PRODUCT_COUNTRY_BY_PRODUCT_ID =
            "delete from " + dBSuffixPostgres + ".payd_common.product_country where product_id = '%s'";

    public static final String DELETE_PRODUCT_ATTRIBUTE_VALUE_BY_PRODUCT_ID =
            "delete from " + dBSuffixPostgres + ".payd_common.product_attribute_value where product_id = '%s'";

    public static final String DELETE_PRODUCT_PURCHASE_MEDIUM_BY_PRODUCT_ID =
            "delete from " + dBSuffixPostgres + ".payd_common.product_purchase_medium where product_id = '%s'";

    public static final String DELETE_PRODUCT_TOP_SELLER_PLATFORM_BY_PRODUCT_ID =
            "delete from " + dBSuffixPostgres + ".payd_common.product_topseller_platform where product_id = '%s'";

    public static final String DELETE_PRODUCT_SUBSCRIBER_TYPE_BY_PRODUCT_ID =
            "delete from " + dBSuffixPostgres + ".payd_common.product_subscriber_type where product_id = '%s'";

    public static final String DELETE_PRODUCT_CHARGE_TYPE_BY_PRODUCT_ID =
            "delete from " + dBSuffixPostgres + ".payd_common.product_charge_type where product_id = '%s'";

    public static final String DELETE_PRODUCT_BY_PRODUCT_ID =
            "delete from " + dBSuffixPostgres + ".payd_common.product where id = '%s'";

    public static final String DELETE_CLIENT_PRODUCT_BY_PRODUCT_ID =
            "delete from " + dBSuffixPostgres + ".payd_common.client_product where product_id = '%s'";

    public static final String INSERT_PRODUCT_BY_PRODUCT_ID=
            "insert into " + dBSuffixPostgres + ".payd_common.product (id, product_type_id, description, active, pricing, support_token, short_description, cdc_update_timestamp, topseller) values (%s, %s, '%s', %s, %s, %s,'%s','%s',%s);";

    public static final String SELECT_CLIENT_PRODUCT_BY_PRODUCT_AND_CLIENTID =
            "select product_id from " + dBSuffixPostgres + ".payd_common.client_product  where product_id  = '%s' and client_id ='%s'";

    public static final String INSERT_VENDOR_PRODUCT_BY_PRODUCT_ID=
            "insert into " + dBSuffixPostgres + ".payd_common.vendor_product (product_id, vendor_id, rating, properties, vendor_product_id, cdc_update_timestamp, external_product_id, active, original_vendor_id) values (%s, %s, %s, %s, %s, '%s','%s',%s,%s);";

    //todo query is an example needs to be override against Product Lookup logic
//    public static final String GET_ACCOUNT_ID_BY_LONG_NUMBER_QUERY =
//            "select " + dBSuffix + ".subscription.account_id\n" +
//                    "from " + dBSuffix + ".subscription \n" +
//                    "join " + dBSuffix + ".two_way_number\n" +
//                    "on " + dBSuffix + ".subscription.number=demo_mc2_platform.two_way_number.number\n" +
//                    "where " + dBSuffix + ".two_way_number.number='26456890'";
}
