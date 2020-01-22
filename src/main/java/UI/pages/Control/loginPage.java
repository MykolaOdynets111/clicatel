package UI.pages.Control;


import api.testUtilities.testConfig;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class loginPage extends testConfig {

    String url ="http://control.refresh.dev.za01.payd.co/control/";


    public loginPage() {
    }


    public void enterUsername(String username){
//        $(By.id("username")).setValue("adminuser").setValue(username);;
        $(By.xpath("/html//input[@id='username']")).setValue(username);

    }

    public void enterPassword(String password){
        $(By.id("password")).setValue("admin");

    }

    public void clickLogin(){

        $(By.id("kc-login")).click();
    }

    public void forgotPassword(){

        $(By.id("kc-form-options")).click();
    }

    public controlBasePage successfulLogin(String username, String password){
        open(url);
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        return new controlBasePage();

    }
}

