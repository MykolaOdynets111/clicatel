package API.USSDTests.isEmptyTests;

import api.testUtilities.baseUSSD;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import requestLibary.USSD.ussd;
import testUtilities.ussd.createUSSDData;


import java.util.UUID;

public class isEmptySimpleTest extends baseUSSD {

    @DataProvider(name = "isEmptySimpleData", parallel = true)
    public Object[] createUSSDTestData() {
        String message = "IsNull_SimpleTest";
        return new String[][]{

                {getRandomDoubleBetweenRange(), UUID.randomUUID().toString(), "1", "1", message},
                {getRandomDoubleBetweenRange(), UUID.randomUUID().toString(), "1", "1", message},
                {getRandomDoubleBetweenRange(), UUID.randomUUID().toString(), "1", "1", message},
                {getRandomDoubleBetweenRange(), UUID.randomUUID().toString(), "1", "1", message},
                {getRandomDoubleBetweenRange(), UUID.randomUUID().toString(), "1", "1", message},


        };
    }


    @Test(dataProvider = "isEmptySimpleData")
    public void ussdIsEmptySimpleNullTest(String msisdn, String sessionID, String network, String type, String msg) {
        createUSSDData x = new createUSSDData();
        ussd payLoad = x.getIsNullySimpleUSSD(msisdn, sessionID, network, type, msg);


    }

}
