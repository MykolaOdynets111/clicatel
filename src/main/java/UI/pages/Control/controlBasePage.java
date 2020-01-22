package UI.pages.Control;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

/**
 * Control landing POM
 * All functions and methods need in a test should be defined here and called from the test
 * Author: Adam Bethlehem
 */
public class controlBasePage extends loginPage {
    //page elements
    By logo = By.xpath("//header[@id='topBar']//span[@class='topBar_logo']");
    By entityDropDown = By.id("mat-select-2");
    By createNewFlow = By.xpath("/html//div[@id='leftSidebarFlow']//span[@class='createFlowLabel']");
    By cancelBtn = By.xpath("/html//mat-dialog-container[@id='mat-dialog-0']//button[.='Cancel']");
    By saveBtn = By.xpath("/html//mat-dialog-container[@id='mat-dialog-0']//button[.='Save ']");
    By saveAsNameField = By.xpath("/html//mat-dialog-container[@id='mat-dialog-0']//input[@placeholder='[ New Flow ]']");
    By flowParent = By.xpath("//div[@id='leftSidebarFlow']/clickatell-tree//cdk-tree[@role='tree']/cdk-nested-tree-node[1]//div[@class='folderBtn']/span[1]");
    By flow = By.xpath("//div[@id='leftSidebarFlow']//cdk-tree[@role='tree']/cdk-nested-tree-node[1]/div/cdk-nested-tree-node[1]//button");

    //page constructor
    public controlBasePage() {
    }

    //TopBar logo present
    public void topBar() {

        $(logo).shouldBe(Condition.visible);

    }

    public void clickEntityDropDown() {

        $(entityDropDown).click();
    }

    public void clickConfigurationsDropDown() {

        $(By.id("mat-expansion-panel-header-0")).click();
    }

    public void clickKeywords() {

        $(By.xpath("//a[@title='keywords']")).click();
    }

    public void clickRouting() {
        $(By.xpath("//a[@id='routes']")).click();
    }

    public void clickAPIIntegration() {

        $(By.xpath("//a[@id='endpoints']")).click();
    }

    public void createNewFlow() {

        $(createNewFlow).click();
        $(createNewFlow).click();
    }

    public void createNewFlowSave(String flowName) {

        $(saveAsNameField).sendKeys(flowName);
        clickSave();

    }

    public void clearInputField() {

        $(saveAsNameField).clear();

    }

    public void clickCancel() {

        $(cancelBtn).click();

    }

    public void clickSave() {

        $(saveBtn).click();
    }

    public void openExistingFLow() {

        $(flowParent).click();
        $(flow).doubleClick();

    }

    public String selectedAttributes() {

        String attribute = $(flow).getCssValue("Background-color");

        return attribute;
    }

}
