package com.vh.caramel.automation.utils;

import com.vh.caramel.automation.driver.DriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.listener.StepLifecycleListener;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.events.WebDriverListener;
import org.testng.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class ListenerClass implements WebDriverListener, ITestListener, ISuiteListener, StepLifecycleListener {

    private static final Logger log = LogManager.getLogger(ListenerClass.class.getName());

    @Override
    public void onStart(ISuite suite) {

        log.info("****************************************************************************************");
        log.info("****************************************************************************************");
        log.info("$$$$$$$$$$$$$$$$$$  TEST SUITE : " + suite.getName() + " HAS STARTED  $$$$$$$$$$$$$$$$$$");
        log.info("****************************************************************************************");
        log.info("****************************************************************************************");

    }

    @Override
    public void onFinish(ISuite suite) {

        log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        log.info("**************** TEST SUITE : " + suite.getName() + " HAS COMPLETED *******************");
        log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

    }

    @Override
    public void onTestStart(ITestResult result) {

        //log.info("The test case " + result.getName() + " has been started");
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        if (result.getStatus() == ITestResult.SUCCESS) {
            //log.info(result.getMethod().getMethodName() + " is passed!");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {

        if (result.getStatus() == ITestResult.FAILURE) {

            log.warn("💥 Exception Found !!!");
            //log.warn(result.getMethod().getMethodName() + " is failed!");
            log.error("Caused by : " + result.getThrowable());
            log.error(Arrays.toString(result.getThrowable().getStackTrace()));
        }

    }

    @Override
    public void onTestSkipped(ITestResult result) {

        if (result.getStatus() == ITestResult.SKIP) {
            log.info("The test case " + result.getName() + " has been skipped");
        }

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void afterRefresh(WebDriver.Navigation navigation) {
        log.info("Browser is refreshed...");
    }

    @Override
    public void beforeFindElement(WebDriver driver, By locator) {
        log.debug("Trying to find element : " + locator.toString());
    }

    @Override
    public void afterFindElement(WebDriver driver, By locator, WebElement result) {
        highlightElement(driver, result);
        log.debug("Found element : " + locator.toString());
    }

    @Override
    public void afterTo(WebDriver.Navigation navigation, String url) {
        log.debug("After navigating to : " + url);
    }

    @Override
    public void afterGetCurrentUrl(String result, WebDriver driver) {
        log.debug("Navigated to : " + driver.getCurrentUrl());
    }

    @Override
    public void beforeClick(WebElement element) {
        String path = element.toString().split("->")[1];
        log.debug("Trying to find element to click on :" + path);
    }

    @Override
    public void afterClick(WebElement element) {
        String path = element.toString().split("->")[1];
        log.debug("Found element to click on :" + path);
    }

    @Override
    public void afterIsDisplayed(WebElement element, boolean result) {
        String path = element.toString().split("->")[1];
        log.debug("Found element displayed :" + path);
    }

    @Override
    public void beforeGetText(WebElement element) {
        log.debug("Trying to find element with text: " + element.getText());
    }

    @Override
    public void afterGetText(WebElement element, String result) {
        log.debug("Found element with text : " + element.getText());
    }

    @Override
    public void onError(Object target, Method method, Object[] args, InvocationTargetException e) {
        log.warn("💥 Exception Found !!!");
        log.error("Error while invoking method " + method.getName() + " on target " + target, e);
    }

    @Override
    public void afterAnyTimeoutsCall(WebDriver.Timeouts timeouts, Method method, Object[] args, Object result) {
        log.debug("WebDriver timeout after {} seconds for {} with {}", timeouts.getScriptTimeout(), method.getName(), args);
        log.debug("Could NOT find {} ", result.toString());
    }

    @Override
    public void beforeQuit(WebDriver driver) {
        log.info("Quitting WebDriver...");
    }

    @Override
    public void afterStepUpdate(StepResult result){
        Status stepStatus = result.getStatus();

        if(stepStatus == Status.PASSED){
            log.info("\u2705 Step: {} is passed...", result.getName());

        } else if (stepStatus == Status.FAILED) {
            log.info("\u274C Step: {} is failed...", result.getName());

        } else if (stepStatus == Status.SKIPPED) {
            log.info("\u26A0 Step: {} is skipped...", result.getName());

        }else if (stepStatus == Status.BROKEN) {
            log.info("\u26D4 Step: {} is broken...", result.getName());

        }
        attachAllureScreenshot();
    }

    @Override
    public void beforeStepStart(StepResult result) {
        log.info("Starting step: {}", result.getName());
    }

    @Override
    public void beforeStepStop(StepResult result) {
        log.info("Finishing step: {}", result.getName());
    }


    //------------------------------------------------------------------------------------------------------------------
    private static void attachAllureScreenshot() {
        Allure.getLifecycle().addAttachment(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy_HH:mm:ss")),
                "image/png",
                "png",
                getWebDriverScreenshot()
        );
    }

    private static byte[] getWebDriverScreenshot() {
        return (((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES));
    }

    private void highlightElement(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red'", element);
    }
}
