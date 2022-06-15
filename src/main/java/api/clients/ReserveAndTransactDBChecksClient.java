package api.clients;

import static util.readers.PropertiesReader.getProperty;

public class ReserveAndTransactDBChecksClient extends BasedAPIClient{
    public static String Event_Type;
    public static String Event_Type_Raas_Request;
    public static String Api_Call;
    public static String Event_Type_Raas_Response;
    public static String settlementAmount;
    public static String PAYD_VAT_LIABILITY;
    public static String PAYD_REVENUE;
    public static String VAT_PERC;
    public static String MNO_DISC_AMT;
    public static String PAYD_GROSS_PROFIT;
    public static String MNO_DISCOUNT_PERC;
    public static String Event_Type_Raas_Res_Fund_Response;
    public static String Event_Type_Ctx_Request;
    public static String Event_Type_Ctx_Response;
    public static String Event_Type_Transaction_Request;
    public static String Event_Type_Transaction_Response;
    public static String CommissionModel;
    public static String Event_Type_Ctx_Lookup_Request;
    public static String Event_Type_Ctx_Lookup_Response;

    static {
        Event_Type = getProperty("Event_Type");
        Event_Type_Raas_Request = getProperty("Event_Type_Raas_Request");
        Api_Call = getProperty("Api_Call");
        Event_Type_Raas_Response = getProperty("Event_Type_Raas_Response");
        settlementAmount = getProperty("settlementAmount");
        PAYD_VAT_LIABILITY = getProperty("PAYD_VAT_LIABILITY");
        PAYD_REVENUE = getProperty("PAYD_REVENUE");
        VAT_PERC = getProperty("VAT_PERC");
        MNO_DISC_AMT = getProperty("MNO_DISC_AMT");
        PAYD_GROSS_PROFIT = getProperty("PAYD_GROSS_PROFIT");
        MNO_DISCOUNT_PERC = getProperty("MNO_DISCOUNT_PERC");
        Event_Type_Raas_Res_Fund_Response = getProperty("Event_Type_Raas_Res_Fund_Response");
        Event_Type_Ctx_Request = getProperty("Event_Type_Ctx_Request");
        Event_Type_Ctx_Response = getProperty("Event_Type_Ctx_Response");
        Event_Type_Transaction_Request = getProperty("Event_Type_Transaction_Request");
        Event_Type_Transaction_Response = getProperty("Event_Type_Transaction_Response");
        CommissionModel = getProperty("CommissionModel");
        Event_Type_Ctx_Lookup_Request = getProperty("Event_Type_Ctx_Lookup_Request");
        Event_Type_Ctx_Lookup_Response = getProperty("Event_Type_Ctx_Lookup_Response");
    }
    }
