/*
Some Request don't require a request body but instead require us to build up urls with parameters
Clients can call the MNO Lookup endpoint to derive the specific MNO for the specified MSISDN
 */
package api.parameterizedUrlBuilder;

import api.testUtilities.propertyConfigWrapper.configWrapper;
import java.util.Properties;

public class getClientMNOLookup {

    public String getRequest(String clientID, String msisdn, String countryCallingCode ){

        Properties properties = configWrapper.loadPropertiesFile("config.properties");

        String requestURL = "http://docker-minion01.dev.za01.payd.co:30049/mnp/mnpLookup?clientId="
                + clientID + "&msisdn="
                + msisdn
                + "&countryCallingCode="
                + countryCallingCode;
        return requestURL;
    }

}




