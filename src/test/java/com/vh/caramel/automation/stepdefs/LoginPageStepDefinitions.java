package com.vh.caramel.automation.stepdefs;

import com.typesafe.config.Config;
import com.vh.caramel.automation.base.BasePage;
import com.vh.caramel.automation.factories.TestEnvFactory;
import com.vh.caramel.automation.pageobjects.LoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;


public class LoginPageStepDefinitions {

    private static final Logger log = LogManager.getLogger(LoginPageStepDefinitions.class.getName());
    private static final Config CONFIG = TestEnvFactory.getInstance().getConfig();

    LoginPage loginPage = new LoginPage();

    @Given("^The Application has been launched$")
    public void application_is_launched() {
        Assert.assertEquals(loginPage.getPageTitle(), "Swag Labs");
        Assert.assertTrue(loginPage.logoIsDisplayed());
    }

    @And("^I log in as standard user$")
    public void login_as_valid_user() {
        loginPage.setUsername(CONFIG.getString("USERNAME"));
        loginPage.setPassword(loginPage.decodeText(CONFIG.getString("PASSWORD")));
        loginPage.clickLoginButton();
        Assert.assertTrue(loginPage.isLoggedIn());
    }

    @And("^I log in as '(.+)' user$")
    public void login_as_any_user(String username) {
        loginPage.setUsername(username);
        loginPage.setPassword(loginPage.decodeText(CONFIG.getString("PASSWORD")));
        loginPage.clickLoginButton();
        Assert.assertTrue(loginPage.isLoggedIn());
    }

    @And("I enter {string} in Username text box")
    public void enter_username(String string) {
        loginPage.setUsername(string);
    }

    @And("I enter {string} in Password text box")
    public void enter_password(String string) {
        loginPage.setPassword(string);
    }

    @And("^I click on login button$")
    public void click_login_button() {
        loginPage.clickLoginButton();
    }

    @Then("^I should be able to login successfully$")
    public void user_should_be_logged_in(){
        Assert.assertTrue(loginPage.isLoggedIn());
    }

    @And("System should display {string} Error Message")
    public void display_error_message(String errorMsg) {
        Assert.assertEquals(loginPage.getErrorMsg(), errorMsg);
    }
}
