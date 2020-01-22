package UI.tests.controlRefresh.flows;
import UI.pages.Control.controlBasePage;
import UI.pages.Control.loginPage;
import api.testUtilities.testConfig;
import org.testng.annotations.Test;

public class createNewFlowTest extends testConfig {
    /**
     *  .Control redesign:: Flows :: Open existing flow
     *  https://jira.clickatell.com/browse/PPEN-2353
     *  Author: Adam Bethlehem
     *  Test to check
     */


    @Test
    public void simpleCreateFlowTest(){
        loginPage loginPage = new loginPage();
        controlBasePage controlBasePage = loginPage.successfulLogin("adminuser","admin");
        controlBasePage.createNewFlow();
        controlBasePage.createNewFlowSave("a");


    }

}
