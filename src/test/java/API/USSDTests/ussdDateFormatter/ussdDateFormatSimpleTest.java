package API.USSDTests.ussdDateFormatter;

import api.testUtilities.baseUSSD;
import org.testng.annotations.DataProvider;

import java.util.UUID;

public class ussdDateFormatSimpleTest extends baseUSSD {

    //TODO - add test

    @DataProvider(name = "DateFormatSimpleData", parallel = true)
    public Object[] createUSSDTestData() {
        String message = "DateFormat/SimpleTest";
        return new String[][]{

                {getRandomDoubleBetweenRange(), UUID.randomUUID().toString(), "1", "1", message,"20190405"},




        };
    }


}
