package com.vh.caramel.automation.stepdefs;

import com.vh.caramel.automation.base.BasePage;
import com.vh.caramel.automation.factories.ExplicitWaitFactory.WaitStrategy;
import io.cucumber.java.en.And;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;


public class BasePageStepDefinitions {

    private static final Logger log = LogManager.getLogger(BasePageStepDefinitions.class.getName());

    BasePage basePage = new BasePage();

    @And("I wait for {int} seconds")
    public void wait_time(int time) {
        basePage.thread_sleep(time);
    }

    @And("^(?:|I|User) refresh the page$")
    public void refresh_page() {

        basePage.refreshPage();
        log.info("Refreshing page...");
        wait_time(2);
    }

    @And("User has navigated {string} page")
    public void navigate_to_page(String page) {

        String url = basePage.getCurrentURL() + page;
        basePage.navigateToURL(url);
        log.info("Navigating to page ::: " + url);
    }

    @And("^I accept alert$")
    public void accept_alert() {
        basePage.acceptAlert();
    }

    @And("I click on {string} button")
    public void click_on_button(String text) {
        basePage.clickOnButtonByName(text, WaitStrategy.CLICKABLE);
    }

    @And("I click on {string} link")
    public void click_on_link(String text) {
        basePage.clickOnLinkByName(text, WaitStrategy.CLICKABLE);
    }

    @And("^I click on the '(.+)' (?:|button|link|text|label)$")
    public void click_on_normalize_space(String text) {
        basePage.clickOnNormalizeSpace(text, WaitStrategy.CLICKABLE);
    }

    @And("I should see the text {string} displayed")
    public void text_is_displayed(String text) {
        Assert.assertTrue(basePage.isDisplayedInNormalizeSpace(text, WaitStrategy.VISIBLE), "Not found text ::: " + text);
    }

    @And("I should not see the text {string} displayed")
    public void text_is_not_displayed(String text) {
        Assert.assertFalse(basePage.isDisplayedInNormalizeSpace(text, WaitStrategy.VISIBLE), "Found text ::: " + text);
    }

    @And("^I set value \"(.+)\" for \"(.+)\"$")
    public void set_text_for_label(String answer, String question) {
        basePage.setTextInputForLabel(question, answer, WaitStrategy.VISIBLE);
    }

    @And("^I type and select value \"(.+)\" for \"(.+)\"$")
    public void suggest_text_for_label(String answer, String question) {
        basePage.clickOnSuggestTextForInput(question, answer, WaitStrategy.VISIBLE);
    }

    @And("^I set value \"(.+)\" for textarea \"(.+)\"$")
    public void set_text_for_textarea(String answer, String question) {
        basePage.setTextAreaForLabel(question, answer, WaitStrategy.VISIBLE);
    }

    @And("^I select visible text from dropdown \"(.+)\" for label \"(.+)\"$")
    public void select_from_dropdown_by_visible_text(String answer, String question) {
        basePage.selectFromDropdownByVisibleText(question, answer, WaitStrategy.CLICKABLE);
    }

    @And("^I select value from dropdown \"(.+)\" for label \"(.+)\"$")
    public void select_from_dropdown_by_value(String answer, String question) {
        basePage.selectFromDropdownByValue(question, answer, WaitStrategy.CLICKABLE);
    }

    @And("I select index from dropdown {int} for label {string}")
    public void select_from_dropdown_by_index(int answer, String question) {
        basePage.selectFromDropdownByIndex(question, answer, WaitStrategy.CLICKABLE);
    }


}
