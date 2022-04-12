package util.base_test;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import util.listeners.AllureApiTestListener;

import static api.controls.JiraControl.setTestFloResults;
import static java.lang.Boolean.parseBoolean;
import static util.ConfigurationProvider.setApiConfiguration;
import static util.listeners.AllureApiTestListener.apiTestsResults;
import static util.readers.PropertiesReader.getProperty;

@Listeners(AllureApiTestListener.class)
public class BaseApiTest {

    @BeforeSuite(alwaysRun = true)
    public void setEnvironmentConfiguration() {
        setApiConfiguration();
    }

    @AfterSuite(alwaysRun = true)
    public void setResultsAndCleanUp() {
        if (parseBoolean(getProperty("jira.logging"))) {
            if (!apiTestsResults.get().isEmpty())
                setTestFloResults(apiTestsResults.get());
        }
    }
}
