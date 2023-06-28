package com.vh.caramel.automation.base;

import com.vh.caramel.automation.driver.DriverManager;
import com.vh.caramel.automation.factories.ExplicitWaitFactory;
import com.vh.caramel.automation.factories.ExplicitWaitFactory.WaitStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

import java.util.Base64;

public class BasePage {

    private static final By BURGER_MENU_BUTTON = By.cssSelector("#react-burger-menu-btn");

    private static final Logger log = LogManager.getLogger(BasePage.class.getName());

    public BasePage() {

    }

    public String getPageTitle() {
        return DriverManager.getDriver().getTitle();
    }

    public void refreshPage() {
        DriverManager.getDriver().navigate().refresh();
    }

    public String getCurrentURL() {
        return DriverManager.getDriver().getCurrentUrl();
    }

    public void navigateToURL(String url) {
        DriverManager.getDriver().navigate().to(url);
    }

    public void thread_sleep(int seconds) {
        try {
            log.info(String.format("Waiting for %d seconds...", seconds));
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    public String decodeText(String text) {
        byte[] actualByte = Base64.getDecoder().decode(text);
        return new String(actualByte);
    }

    public void clearText(By by, WaitStrategy waitStrategy) {
        try {
            WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
            element.clear();
        } catch (Exception e) {
            log.error("Could NOT clear text for element {}", by, e);
        }
    }

    public void click(By by, WaitStrategy waitStrategy) {
        try {
            WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
            scrollIntoView(element);
            element.click();
        } catch (Exception e) {
            log.error("Could NOT click on element {}", by, e);
            Assert.fail(String.format("Could NOT click on element '%s'", by));
        }
    }

    public void jsClick(By by, WaitStrategy waitStrategy) {
        WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
        JavascriptExecutor executor = (JavascriptExecutor) DriverManager.getDriver();
        log.info("Click on element " + element + " by javascript");
        executor.executeScript("arguments[0].click();", element);
    }

    public void submit(By by, WaitStrategy waitStrategy) {
        try {
            WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
            element.submit();
        } catch (Exception e) {
            log.error("Could NOT submit form {}", by, e);
            Assert.fail(String.format("Could NOT submit form for element '%s'", by));
        }
    }

    public void setText(By by, String text, WaitStrategy waitStrategy) {
        try {
            WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
            scrollIntoView(element);
            element.clear();
            element.sendKeys(text);
        } catch (Exception e) {
            log.error("Could NOT set text {} for element {}", text, by, e);
            Assert.fail(String.format("Could NOT set text '%s' for element '%s'", text, by));
        }
    }

    public String getText(By by, WaitStrategy waitStrategy) {
        try {
            WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
            scrollIntoView(element);
            return element.getText().trim();
        } catch (Exception e) {
            log.error("Could NOT get text of element {}", by, e);
            Assert.fail(String.format("Could NOT get text for element '%s'", by));
            return "-1";
        }
    }

    public boolean isDisplayed(By by, WaitStrategy waitStrategy) {
        WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
        scrollIntoView(element);
        return element.isDisplayed();
    }

    public boolean isNotDisplayed(By by) {
        log.info("Waiting for invisibility of element {} ", by);
        return ExplicitWaitFactory.waitForElementNotVisible(by);
    }

    public void acceptAlert() {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = DriverManager.getDriver().switchTo().alert();
        log.info("Accepting Alert");
        alert.accept();
    }

    public String getAttribute(By by, String attribute, WaitStrategy waitStrategy) {
        try {
            WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
            scrollIntoView(element);
            return element.getAttribute(attribute);
        } catch (Exception e) {
            log.error("Could NOT get attribute {} of element {}", attribute, by, e);
            return "-1";
        }
    }

    public String getCssValue(By by, String attribute, WaitStrategy waitStrategy) {
        try {
            WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
            scrollIntoView(element);
            return element.getCssValue(attribute).trim();
        } catch (Exception e) {
            log.error("Could NOT get CSS value {} of element {}", attribute, by, e);
            return "-1";
        }
    }

    public String getAttribute(String text, String attribute, String index, WaitStrategy waitStrategy) {
        try {
            String xpath = String.format("(//*[normalize-space()='%s']|//*[@id='%s']|//a[@data-filter-key='%s']|//a[contains(@href,'%s')])[%s]", text, text, text, text, index);
            WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, By.xpath(xpath));
            scrollIntoView(element);
            return element.getAttribute(attribute);
        }catch (Exception e){
            log.error("Could NOT get attribute {} of element {}", attribute, text, e);
            return "-1";
        }
    }

    public void switchToFrame(By by, WaitStrategy waitStrategy) {
        WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
        DriverManager.getDriver().switchTo().frame(element);
    }

    public void switchToFrame(String string) {
        DriverManager.getDriver().switchTo().frame(string);
    }

    public void switchBackToParentFrame() {
        DriverManager.getDriver().switchTo().parentFrame();
    }

    public void switchBackToDefaultFrame() {
        DriverManager.getDriver().switchTo().defaultContent();
    }


    public boolean isLoggedIn() {
        return isDisplayed(BURGER_MENU_BUTTON, WaitStrategy.VISIBLE);
    }

    public String getPageHeaderTitle(){
        String xpath = "(//h1|//h2|//h3|//h4)[1]";
        return getText(By.xpath(xpath), WaitStrategy.VISIBLE);
    }

    // ############################################ Generic xPath Expressions ############################################

    public void setTextInputForLabel(String label_name, String value, WaitStrategy waitStrategy) {

        String xpath = String.format("(//input[@id='%s']|//input[@placeholder='%s'])[1]", label_name, label_name);
        setText(By.xpath(xpath), value, waitStrategy);
    }

    public void setTextInputForLabel(String label_name, String index, String value, WaitStrategy waitStrategy) {

        String xpath = String.format("(//input[@id='%s']|//input[@placeholder='%s'])[%s]", label_name, label_name, index);
        setText(By.xpath(xpath), value, waitStrategy);
    }

    public void clickOnSuggestTextForInput(String label_name, String value, WaitStrategy waitStrategy) {

        String xpath = String.format("(//input[@type='text' and @id='%s']|//input[@type='text' and @placeholder='%s'])[1]", label_name, label_name);
        try {
            setText(By.xpath(xpath), value, waitStrategy);
            Thread.sleep(500);
            clickOnNormalizeSpace(value, waitStrategy);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public void clickOnSuggestTextForInput(String label_name, String index, String value, WaitStrategy waitStrategy) {

        String xpath = String.format("(//input[@type='text' and @id='%s']|//input[@type='text' and @placeholder='%s'])[%s]", label_name, label_name, index);
        try {
            setText(By.xpath(xpath), value, waitStrategy);
            Thread.sleep(500);
            clickOnNormalizeSpace(value, waitStrategy);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setTextAreaForLabel(String label_name, String value, WaitStrategy waitStrategy) {

        String xpath = "//label[contains(text(),'" + label_name + "')]/following::textarea[1]";
        setText(By.xpath(xpath), value, waitStrategy);
    }

    public void setTextAreaForLabel(String label_name, String index, String value, WaitStrategy waitStrategy) {

        String xpath = "(//label[contains(text(),'" + label_name + "')])[" + index + "]/following::textarea[1]";
        setText(By.xpath(xpath), value, waitStrategy);
    }

    public void clickOnButtonByName(String text, WaitStrategy waitStrategy) {
        String xpath = String.format("(//button[contains(normalize-space(),'%s')]|//button[@id='%s'])[1]", text, text);
        click(By.xpath(xpath), waitStrategy);
    }

    public void clickOnButtonByName(String text, String index, WaitStrategy waitStrategy) {
        String xpath = String.format("(//button[contains(normalize-space(),'%s')]|//button[@id='%s'])[%s]", text, text, index);
        click(By.xpath(xpath), waitStrategy);
    }

    public void clickOnLinkByName(String text, WaitStrategy waitStrategy) {
        String xpath = String.format("(//a[contains(normalize-space(),'%s')]|//a[contains(@href,'%s')])[1]", text, text);
        click(By.xpath(xpath), waitStrategy);
    }

    public void clickOnLinkByName(String text, String index, WaitStrategy waitStrategy) {
        String xpath = String.format("(//a[contains(normalize-space(),'%s')]|//a[contains(@href,'%s')])[%s]", text, text, index);
        click(By.xpath(xpath), waitStrategy);
    }

    public boolean isLinkDisplayedByName(String text, String index, WaitStrategy waitStrategy) {
        String xpath = String.format("(//a[contains(normalize-space(),'%s')]|//a[contains(@href,'%s')])[%s]", text, text, index);
        return isDisplayed(By.xpath(xpath), waitStrategy);
    }

    public boolean isDisplayedInNormalizeSpace(String text, WaitStrategy waitStrategy) {
        String xpath = "//*[normalize-space()='" + text + "']";
        return isDisplayed(By.xpath(xpath), waitStrategy);
    }

    public boolean isDisplayedInNormalizeSpace(String text, String index, WaitStrategy waitStrategy) {
        String xpath = "(//*[normalize-space()='" + text + "'])[" + index + "]";
        return isDisplayed(By.xpath(xpath), waitStrategy);
    }

    public void clickOnNormalizeSpace(String text, WaitStrategy waitStrategy) {
        String xpath = String.format("(//*[normalize-space()='%s'])[1]", text);
        click(By.xpath(xpath), waitStrategy);
    }

    public void clickOnNormalizeSpace(String text, String index, WaitStrategy waitStrategy) {
        String xpath = String.format("(//*[normalize-space()='%s'])[%s]", text, index);
        click(By.xpath(xpath), waitStrategy);
    }

    public void selectFromDropdownByVisibleText(By by, String visibleText, WaitStrategy waitStrategy) {

        WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
        Select select = new Select(element);
        select.selectByVisibleText(visibleText);
    }

    public void selectFromDropdownByVisibleText(String label_name, String visibleText, WaitStrategy waitStrategy) {

        String xpath = String.format("(//*[contains(text(),'%s')]/following::select|//select[@id='%s'])[1]", label_name, label_name);
        WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, By.xpath(xpath));
        Select select = new Select(element);
        select.selectByVisibleText(visibleText);
    }

    public void selectFromDropdownByVisibleText(String label_name, String element_index, String visibleText, WaitStrategy waitStrategy) {

        String xpath = String.format("(//*[contains(text(),'%s')]/following::select|//select[@id='%s'])[%s]", label_name, label_name, element_index);
        WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, By.xpath(xpath));
        Select select = new Select(element);
        select.selectByVisibleText(visibleText);
    }

    public void selectFromDropdownByValue(String label_name, String value, WaitStrategy waitStrategy) {

        String xpath = String.format("(//*[contains(text(),'%s')]/following::select|//select[@id='%s'])[1]", label_name, label_name);
        WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, By.xpath(xpath));
        Select select = new Select(element);
        select.selectByValue(value);
    }


    public void selectFromDropdownByValue(String label_name, String element_index, String value, WaitStrategy waitStrategy) {

        String xpath = String.format("(//*[contains(text(),'%s')]/following::select|//select[@id='%s'])[%s]", label_name, label_name, element_index);
        WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, By.xpath(xpath));
        scrollIntoView(element);
        Select select = new Select(element);
        select.selectByValue(value);
    }

    public void selectFromDropdownByIndex(String label_name, int index, WaitStrategy waitStrategy) {

        String xpath = "//label[contains(text(),'" + label_name + "')]/following::select";
        WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, By.xpath(xpath));
        scrollIntoView(element);
        Select select = new Select(element);
        select.selectByIndex(index);
    }


    public void selectFromDropdownByIndex(String label_name, String element_index, int index, WaitStrategy waitStrategy) {

        String xpath = "(//label[contains(text(),'" + label_name + "')])[" + element_index + "]/following::select";
        WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, By.xpath(xpath));
        Select select = new Select(element);
        select.selectByIndex(index);
    }

    public static void scrollIntoView(WebElement element) {
        try {
            Actions actions = new Actions(DriverManager.getDriver());
            actions.moveToElement(element).perform();

        } catch (MoveTargetOutOfBoundsException e) {
            log.error(String.format("Fail to scroll element '%s' into view due to error: %s", element, e.getMessage()));
        }
    }

}
