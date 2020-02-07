/* The testDataFactory class is used to wrap data from a json datasource in order to use in our data driven tests.
*  The class is robust and should be updated as soon as another datasource (.json) file is added.
*  This is a once of step as any test can consume the datafactory in order to use data from any given datasource.
*
*  Confluence link: https://confluence.clickatell.com/display/BIG/Data+Layer
*
* Author: Juan-Claude Botha
*/

package api.testUtilities.dataBuilders;

// Json parsers
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

// File IO parsers
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

// Xml parsers
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;


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

    // this method needs some work. on pause for now due to time constraints
    public static String getXmlTestData(String attribute ,String xmlElement) throws ParserConfigurationException, IOException, SAXException {

        // Create a DocumentBuilder
        DocumentBuilderFactory xmlFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = xmlFactory.newDocumentBuilder();

        // Create a Document from a file or stream
        StringBuilder xmlStringBuilder = new StringBuilder();
        xmlStringBuilder.append("<?xml version="+'"'+1.0+'"'+"?> <class> </class>");
        ByteArrayInputStream input = new ByteArrayInputStream(
                xmlStringBuilder.toString().getBytes("UTF-8"));
        Document doc = builder.parse(input);

        // Extract the root element
        Element root = doc.getDocumentElement();

        // Examine attributes
        //returns specific attribute
        root.getAttribute(attribute);

        //returns a Map (table) of names/values
        root.getAttributes();

        // Examine sub elements
        //returns a list of subelements of specified name
        String xmlElementName = root.getElementsByTagName(xmlElement).toString();

        //returns a list of all child nodes
        //String nodes = root.getChildNodes().toString();

        return xmlElementName;
    }


}
