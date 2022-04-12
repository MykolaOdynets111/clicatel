package db.custom_queries;

public class MnoLookupQueries extends BaseCustomQuery {

    public static final String GET_LOOKUP_RESPONSE_CODE =
            "select lookup_response_code from " + dBSuffixPostgres + ".mnp_lookup.client_lookups where lookup_ref='%s'";
}
