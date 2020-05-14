/*
Description: The SetReportEnvironmentProperties file is used to update properties within certain files, for now
             the reason for this class is because we need a solution in order to change the allure reporting environment variables.
Author: Juan-Claude Botha
*/
package util;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import api.testUtilities.propertyConfigWrapper.configWrapper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class SetReportEnvironmentProperties {

    // Create properties object in order to inject environment specific variables upon build
    Properties properties = configWrapper.loadPropertiesFile("config.properties");

    public void setEnvironment() throws ConfigurationException {

        // You have to create config.properties file under resources folder or anywhere you want :)
        // Here I'm updating file which already exists
        PropertiesConfiguration config = new PropertiesConfiguration("/src/test/resources/environment.properties");
        config.setProperty("Environment", properties.getProperty("ENVIRONMENT"));
        config.save();

        System.out.println("Config Property Successfully Updated..");
    }


}
