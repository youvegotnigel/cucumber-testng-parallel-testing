package com.vh.caramel.automation.pageobjects;

import com.vh.caramel.automation.base.BasePage;
import com.vh.caramel.automation.factories.ExplicitWaitFactory.WaitStrategy;
import org.openqa.selenium.By;

public class CheckoutPage extends BasePage {

    private static final By button_continue = By.xpath("//input[@id='continue']");
    private static final By input_first_name = By.xpath("//input[@id='first-name']");
    private static final By input_last_name = By.xpath("//input[@id='last-name']");
    private static final By input_zip_code = By.xpath("//input[@id='postal-code']");
    private static final By button_finish = By.xpath("//button[@id='finish']");
    private static final By img_dilevery_logo = By.xpath("//img[@alt='Pony Express']");


    public void clickOnContinueButton(){
        click(button_continue, WaitStrategy.CLICKABLE);
    }

    public boolean verifyErrorMessage(String error){
        String xpath = "//*[contains(text(),'" + error + "')]";
        return isDisplayed(By.xpath(xpath), WaitStrategy.VISIBLE);
    }

    public void inputFirstName(String value){
        setText(input_first_name, value, WaitStrategy.VISIBLE);
    }

    public void inputLastName(String value){
        setText(input_last_name, value, WaitStrategy.VISIBLE);
    }

    public void inputZipCode(String value){
        setText(input_zip_code, value, WaitStrategy.VISIBLE);
    }

    public boolean textIsDisplayed(String text){
        return isDisplayedInNormalizeSpace(text, WaitStrategy.VISIBLE);
    }

    public void clickOnFinishButton(){
        click(button_finish, WaitStrategy.CLICKABLE);
    }

    public boolean logoIsDisplayed(){
        return isDisplayed(img_dilevery_logo, WaitStrategy.VISIBLE);
    }

}
