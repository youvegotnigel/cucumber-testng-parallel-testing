package com.vh.caramel.automation.factories;

import com.vh.caramel.automation.driver.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class ExplicitWaitFactory {

    private static final Logger log = LogManager.getLogger(ExplicitWaitFactory.class.getName());
    private static final int PAGE_LOAD_TIMEOUT = 15;

    public enum WaitStrategy {
        CLICKABLE, PRESENCE, VISIBLE, FRAME, FLUENT_NESTED, HANDLE_STALE_ELEMENT, NONE
    }

    /**
     * Private constructor to avoid external instantiation
     */
    private ExplicitWaitFactory() {
    }

    /**
     * @param waitstrategy Strategy to be applied to find a web-element
     * @param by           By locator of the web-element
     * @return web-element Locates and return the web-element
     */
    public static WebElement performExplicitWait(WaitStrategy waitstrategy, By by) {

        WebElement element = null;
        switch (waitstrategy) {
            case CLICKABLE:
                log.debug("Wait for Element Clickable...");
                element = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(PAGE_LOAD_TIMEOUT))
                        .until(ExpectedConditions.elementToBeClickable(by));
                break;

            case PRESENCE:
                log.debug("Wait for Element Presence...");
                element = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(PAGE_LOAD_TIMEOUT))
                        .until(ExpectedConditions.presenceOfElementLocated(by));
                break;

            case VISIBLE:
                log.debug("Wait for Element Visible...");
                element = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(PAGE_LOAD_TIMEOUT))
                        .until(ExpectedConditions.visibilityOfElementLocated(by));
                break;

            case FRAME:
                log.debug("Wait for Iframe Visible...");
                element = (WebElement) new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(PAGE_LOAD_TIMEOUT))
                        .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by));
                break;

            case HANDLE_STALE_ELEMENT:
                element = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(PAGE_LOAD_TIMEOUT))
                        .until(d -> {
                            log.debug("Refreshing to Handle Stale Element...");
                            d.navigate().refresh();
                            return d.findElement(by);
                        });
                break;

            case NONE:
                element = DriverManager.getDriver().findElement(by);
                break;
        }

        return element;
    }

    /**
     * @param waitstrategy Strategy to be applied to find a web-element
     * @param parent       Parent locator of the web-element
     * @param child        By locator of the web-element
     * @return web-element Locates and return the web-element
     */
    public static WebElement performExplicitWait(WaitStrategy waitstrategy, WebElement parent, By child) {

        WebElement element = null;
        switch (waitstrategy) {

            case PRESENCE:
                log.debug("Wait for Element Presence...");
                element = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(PAGE_LOAD_TIMEOUT))
                        .until(ExpectedConditions.presenceOfNestedElementLocatedBy(parent, child));
                break;

            case VISIBLE:
                log.debug("Wait for Element Visible...");
                element = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(PAGE_LOAD_TIMEOUT))
                        .until(ExpectedConditions.visibilityOf(parent.findElement(child)));
                break;

            case HANDLE_STALE_ELEMENT:
                log.debug("Handle if element stale...");
                element = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(PAGE_LOAD_TIMEOUT))
                        .until(ExpectedConditions.refreshed(
                                ExpectedConditions.presenceOfNestedElementLocatedBy(parent, child)
                        ));
                break;

            case FLUENT_NESTED:
                log.debug("Fluent Wait for element...");
                FluentWait<WebDriver> wait = new FluentWait<>(DriverManager.getDriver())
                        .withTimeout(Duration.ofSeconds(PAGE_LOAD_TIMEOUT))
                        .pollingEvery(Duration.ofSeconds(2))
                        .ignoring(NoSuchElementException.class);

                element = wait.until(d -> parent.findElement(child));
                break;

            case NONE:
                element = parent.findElement(child);
                break;

        }

        return element;
    }

    public static boolean waitForElementNotVisible(By by) {
        return new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(PAGE_LOAD_TIMEOUT))
                .until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

}
