package com.vh.caramel.automation.pageobjects;

import com.vh.caramel.automation.base.BasePage;

import com.vh.caramel.automation.factories.ExplicitWaitFactory.WaitStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

public class LoginPage extends BasePage {

    private static final By USERNAME_TEXT_BOX = By.xpath("//*[@id='user-name']");
    private static final By PASSWORD_TEXT_BOX = By.xpath("//*[@id='password']");
    private static final By ERROR_MESSAGE = By.xpath("//div[@class='error-message-container error']/h3");
    private static final By LOGIN_BUTTON = By.xpath("//*[@id='login-button']");
    private static final By LOGO = By.xpath("//div[@class='login_logo']");

    public static final Logger log = LogManager.getLogger(LoginPage.class.getName());


    //Methods
    public void setUsername(String username) {
        setText(USERNAME_TEXT_BOX, username, WaitStrategy.CLICKABLE);
    }

    public void setPassword(String password) {
        setText(PASSWORD_TEXT_BOX, password, WaitStrategy.CLICKABLE);
    }

    public void clickLoginButton() {
        click(LOGIN_BUTTON, WaitStrategy.CLICKABLE);
    }

    public boolean logoIsDisplayed() {
        return isDisplayed(LOGO, WaitStrategy.VISIBLE);
    }

    public String getErrorMsg() {
        return getText(ERROR_MESSAGE, WaitStrategy.VISIBLE);
    }

}
