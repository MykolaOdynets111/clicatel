package UI.tests.controlRefresh.flows;

import UI.pages.Control.controlBasePage;
import UI.pages.Control.loginPage;
import api.testUtilities.testConfig;
import com.codeborne.selenide.WebDriverRunner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class openExistingFlowTest extends testConfig {
    /**
     *  .Control Redesign :: Flow :: High-lightening selected flow
     *  https://jira.clickatell.com/browse/PPEN-2355
     *  Author: Adam Bethlehem
     * @throws InterruptedException
     */

    //Todo split this up into one test to check if up can open and one test to check if it is highlighted
    @Test
    public void openExistingFlowCheckForHighLitesTest() throws InterruptedException {

        loginPage loginPage = new loginPage();
        controlBasePage controlBasePage = loginPage.successfulLogin("adminuser","admin");
        controlBasePage.openExistingFLow();
        Assert.assertEquals(WebDriverRunner.url(),"http://control.refresh.dev.za01.payd.co/control/flow/dev/CLIENT/42/25,1");
        Assert.assertTrue(controlBasePage.selectedAttributes().contains("rgba(41, 194, 236,"));

    }


}
