package com.vh.caramel.automation.runner;

import com.vh.caramel.automation.driver.Driver;
import com.vh.caramel.automation.driver.DriverManager;
import com.vh.caramel.automation.utils.CreateEnvFile;
import io.cucumber.java.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ServiceHooks {

    private static final Logger log = LogManager.getLogger(ServiceHooks.class.getName());

    @Before
    public void initializeTest() {
        Driver.initDriver();
    }

    @Before
    public void beforeStartScenario(Scenario scenario) {
        log.debug("✰ Started scenario : " + scenario.getName());
    }

    @AfterStep
    public void takeScreenshotAfterEachStep(Scenario scenario) {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) DriverManager.getDriver();
            byte[] data = screenshot.getScreenshotAs(OutputType.BYTES);
            scenario.attach(data, "image/png", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy_HH:mm:ss")));
        } catch (WebDriverException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    @After
    public void endTest(Scenario scenario) {

        if (!scenario.isFailed()) {
            log.info("\u2705 Passed scenario : " + scenario.getName());
        }
        if (scenario.isFailed()) {
            log.info("\u274C Failed scenario : " + scenario.getName());

            //scenario.attach(tableContent, "text/plain", "Table Content");
            analyzeLog(scenario);
        }

        try {
            //TODO: This file only needs to be created once
            CreateEnvFile.createFile();
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            e.printStackTrace();
        } finally {
            Driver.quitDriver();
        }
    }

    public void analyzeLog(Scenario scenario) {
        LogEntries logEntries = DriverManager.getDriver().manage().logs().get(LogType.BROWSER);

        Set<String> messages = new HashSet<>();
        StringBuilder sb = new StringBuilder();
        for (LogEntry entry : logEntries) {
            if (entry.getLevel().toString().equals("SEVERE")) {
                String message = entry.getMessage();
                if (!messages.contains(message)) {
                    log.debug(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + message);
                    log.debug(message);
                    sb.append(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + message + "\n");
                    messages.add(message);
                }
            }
        }
        scenario.attach(sb.toString(), "text/plain", "CONSOLE LOGS");
    }



}
