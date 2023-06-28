package com.vh.caramel.automation.pageobjects;

import com.vh.caramel.automation.base.BasePage;
import com.vh.caramel.automation.factories.ExplicitWaitFactory.WaitStrategy;
import org.openqa.selenium.By;
public class InventoryPage extends BasePage {

    private static final By pageHeader = By.xpath("//span[@class='title']");
    private static final By item_names = By.xpath("//div[@class='inventory_item_name']");
    private static final By item_prices = By.xpath("//div[@class='inventory_item_price']");
    private static final By item_images = By.xpath("//img[@class='inventory_item_img']");
    private static final By item_description = By.xpath("//div[@class='inventory_item_desc']");
    private static final By filter_icon = By.xpath("//select[@class='product_sort_container']");
    private static final By shopping_cart_badge = By.xpath("//div[@id='shopping_cart_container']");
    private static final By cart_item_count = By.xpath("//span[@class='shopping_cart_badge']");
    private static final By add_to_cart_button = By.xpath("//button[text()='Add to cart']");

    //Methods
    public void clickOnFilterIcon(){
        click(filter_icon, WaitStrategy.CLICKABLE);
    }

    public boolean shoppingCartIsDisplayed(){
        return isDisplayed(shopping_cart_badge, WaitStrategy.VISIBLE);
    }

    public void clickOnShoppingCart(){
        click(shopping_cart_badge, WaitStrategy.VISIBLE);
    }

    public String getCartItemCount(){
        return getText(cart_item_count, WaitStrategy.VISIBLE);
    }

    public String getPageHeader(){
        return getText(pageHeader, WaitStrategy.VISIBLE);
    }

    public void clickOnAddCartOrRemove(String item_name){
        String  xpath = "//div[@class='inventory_item_name' and text()="+ item_name + "]/../../following-sibling::div/button";
        click(By.xpath(xpath), WaitStrategy.CLICKABLE);
    }





}
