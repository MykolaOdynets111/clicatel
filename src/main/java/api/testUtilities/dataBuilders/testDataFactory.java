/* The testDataFactory class is used to wrap data from a json datasource in order to use in our data driven tests.
*  The class is robust and should be updated as soon as another datasource (.json) file is added.
*  This is a once of step as any test can consume the datafactory in order to use data from any given datasource.
*
*  Confluence link: https://confluence.clickatell.com/display/BIG/Data+Layer
*
* Author: Juan-Claude Botha
*/

package api.testUtilities.dataBuilders;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;


public class testDataFactory
{

    // get json field from datasource
    public static String getTestData(String dataSource, String testsuite, String testscenario, String testfield) throws IOException, ParseException {

        String vendorJsonFile = System.getProperty("user.dir") + "/src/main/java/api/testUtilities/dataBuilders/"+dataSource;

        JSONParser parser = new JSONParser();
        Reader reader = new FileReader(vendorJsonFile);
        JSONObject JsonData = (JSONObject) parser.parse(reader);

        JSONObject testItem = (JSONObject)JsonData.get(testsuite);
        JSONObject testVendor = (JSONObject)testItem.get(testscenario);
        String testField = (String) testVendor.get(testfield);

        return testField;

    }

}
