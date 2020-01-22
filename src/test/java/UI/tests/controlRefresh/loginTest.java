package UI.tests.controlRefresh;


import UI.pages.Control.controlBasePage;
import UI.pages.Control.loginPage;
import org.testng.annotations.Test;
public class loginTest extends controlBasePage {

    @Test
    public void successfulLogInTest(){

        loginPage loginPage = new loginPage();
        controlBasePage controlBasePage = loginPage.successfulLogin("adminuser","admin");
        controlBasePage.topBar();



    }
}
