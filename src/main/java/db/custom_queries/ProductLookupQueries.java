package db.custom_queries;

import static db.custom_queries.BaseCustomQuery.dBSuffix;

public class ProductLookupQueries extends BaseCustomQuery {

    //todo query is an example needs to be override against Product Lookup logic
    public static final String GET_CLIENT_DETAILS_BY_CLIENT_ID =
            "select name from " + dBSuffixPostgres + ".payd_common.client where client_id='%d'";

    public static final String GET_CLIENT_BY_CLIENT_ID =
            "select name from " + dBSuffix + ".client where id=3";

    //todo query is an example needs to be override against Product Lookup logic
//    public static final String GET_ACCOUNT_ID_BY_LONG_NUMBER_QUERY =
//            "select " + dBSuffix + ".subscription.account_id\n" +
//                    "from " + dBSuffix + ".subscription \n" +
//                    "join " + dBSuffix + ".two_way_number\n" +
//                    "on " + dBSuffix + ".subscription.number=demo_mc2_platform.two_way_number.number\n" +
//                    "where " + dBSuffix + ".two_way_number.number='26456890'";
}
