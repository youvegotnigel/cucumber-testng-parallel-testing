package com.vh.caramel.automation.pageobjects;

import com.vh.caramel.automation.base.BasePage;
import com.vh.caramel.automation.factories.ExplicitWaitFactory.WaitStrategy;
import org.openqa.selenium.By;

public class CartPage extends BasePage {

    private static final By pageHeader = By.xpath("//span[@class='title']");
    private static final By button_checkout = By.xpath("//button[@id='checkout']");


    //Methods
    public String getPageHeader(){
        return getText(pageHeader, WaitStrategy.VISIBLE);
    }

    public boolean textDisplayed(String text){
        return isDisplayedInNormalizeSpace(text, WaitStrategy.VISIBLE);
    }

    public void clickOnCheckout(){
        click(button_checkout, WaitStrategy.CLICKABLE);
    }

}
