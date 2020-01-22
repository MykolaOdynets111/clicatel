package UI.pages.Control.FlowChartSteps.UserFacingNodes;

import UI.pages.Control.controlBasePage;
import org.openqa.selenium.By;

public class otpMenu extends controlBasePage {

    /**
     * OTP Menu Side bar element mapping
     */
    //TODO replace all xpath with id when I can
    By otpSave = By.xpath("///mat-icon[@role='img']");
    By otpTrash =By.cssSelector("delete-button ng-star-inserted");
    By otpMenuHeader = By.id("menuHeader");
    By otpRetries = By.id("retries");
    By otpResends = By.id("resends");
    By otpLength = By.id("otpLength");
    By otpMessageText = By.id("otpMessageText");
    By otpResendLimitReachedMessage = By.id("resendLimitReachedMessage");
    By otpRetryTextToPrepend = By.id("retryTextToPrepend");
    By otpResendTextToPrepend = By.id("resendTextToPrepend");
}
