/**
 * ConfigWrapper has been created to easily reference config files by passing the property name, the class will handle the rest by allocating the respective environment when executing
 * the all tests or any given test suite
 * Author: Juan-Claude Botha
 * Confluence Page: https://confluence.clickatell.com/display/BIG/Profiling
 */
package api.testUtilities.propertyConfigWrapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class configWrapper {

    public static Properties loadPropertiesFile(String fileName) {
        Properties prop = new Properties();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(fileName);
            prop.load(inputStream);
        } catch (IOException e) {
            System.err.println("Unable to load properties file : " + fileName);
        }

        return prop;

    }
}
