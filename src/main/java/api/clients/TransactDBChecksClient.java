package api.clients;

import static util.readers.PropertiesReader.getProperty;

public class TransactDBChecksClient  extends BasedAPIClient {
    public static String ReserveFundsTxnRef;
    public static String Api_Call_Transact;
    static
    {
        ReserveFundsTxnRef = getProperty("ReserveFundsTxnRef");
        Api_Call_Transact = getProperty("Api_Call_Transact");
    }

}
