package api.clients;

import static util.readers.PropertiesReader.getProperty;

public class ProductManagementClient  extends BasedAPIClient{
    public static String ProductAirtel_130;

    static {
        ProductAirtel_130 = getProperty("ProductAirtel_130");
}

}
