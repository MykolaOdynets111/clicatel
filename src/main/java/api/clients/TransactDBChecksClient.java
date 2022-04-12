package api.clients;

import static util.readers.PropertiesReader.getProperty;

public class TransactDBChecksClient  extends BasedAPIClient {
    public static String ReserveFundsTxnRef;
    static
    {
        ReserveFundsTxnRef = getProperty("ReserveFundsTxnRef");
    }

}
