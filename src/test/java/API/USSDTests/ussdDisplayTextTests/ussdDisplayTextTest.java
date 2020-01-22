package API.USSDTests.ussdDisplayTextTests;

import api.testUtilities.baseUSSD;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import api.requestLibary.USSD.ussdGeneric;
import api.testUtilities.ussd.ussdGenericProvider;

public class ussdDisplayTextTest extends baseUSSD {

    @DataProvider(name = "ussdDisplayTextData", parallel = true)
    public Object[] createUSSDTestData() {
        String message = "DisplayText/PlainText";
        String respType = "3";
        String respoMsg = "How are you today";

        return new String[][]{

                {message,"1","1",respType,respoMsg},
                {message,"1","1",respType,respoMsg},
                {message,"1","1",respType,respoMsg},
                {message,"1","1",respType,respoMsg},
                {message,"1","1",respType,respoMsg},


        };
    }

    @Test(dataProvider = "ussdDisplayTextData")
    public void ussdSimpleMenu(String message, String type, String network, String responceType, String responceMsg) {
        ussdGenericProvider x = new ussdGenericProvider();
        ussdGeneric payload;
        payload = x.getGenericUSSD(message,type,network,responceType,responceMsg);


    }

}
