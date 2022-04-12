package util;

import lombok.experimental.UtilityClass;

import java.io.FileWriter;
import java.io.IOException;

import static java.lang.String.format;
import static util.readers.PropertiesReader.getProperty;

@UtilityClass
public class ConfigurationProvider {

    private static FileWriter fileWriter;
    private static final String ENVIRONMENT_PATH = "src/test/resources/environment.properties";

    public static void setUiConfiguration() {
        try {
            fileWriter = new FileWriter(ENVIRONMENT_PATH);
            fileWriter.write(format("Environment: %s \n", getProperty("environment")));
            fileWriter.write(format("Driver: %s \n", getProperty("browser.type")));
            fileWriter.write(format("TestPlan: %s \n", System.getProperty("jira.test.plan")));
            fileWriter.write(format("OSName: %s \n", System.getProperty("os.name")));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setApiConfiguration() {
        try {
            fileWriter = new FileWriter(ENVIRONMENT_PATH);
            fileWriter.write(format("Environment: %s \n", getProperty("environment")));
            fileWriter.write(format("TestPlan: %s \n", System.getProperty("jira.test.plan")));
            fileWriter.write(format("OSName: %s \n", System.getProperty("os.name")));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
