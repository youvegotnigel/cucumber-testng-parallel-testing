package com.vh.caramel.automation.factories;

import com.typesafe.config.Config;
import com.vh.caramel.automation.utils.ListenerClass;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class DriverFactory {

    private static final Logger log = LogManager.getLogger(DriverFactory.class.getName());

    /**
     * Private constructor to avoid external instantiation
     */
    private DriverFactory() {
    }

    /**
     * @return WebDriver
     */
    public static WebDriver getDriver() {

        WebDriver driver;

        final Config CONFIG = TestEnvFactory.getInstance().getConfig();

        DriverType driverType = CONFIG.getEnum(DriverType.class, "BROWSER_TYPE");
        log.info("Starting {} browser", driverType);

        switch (driverType){
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                log.debug("Initializing firefox real browser mode");
                break;

            case FIREFOX_HEADLESS:
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("-headless");
                driver = new FirefoxDriver(firefoxOptions);
                log.debug("Initializing firefox headless mode");
                break;

            case EDGE:
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                log.debug("Initializing edge real browser mode");
                break;

            case CHROME:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                Map<String, Object> chromePrefs = new HashMap<>();
                chromePrefs.put("credentials_enable_service", false);
                chromePrefs.put("profile.password_manager_enabled", false);

                chromeOptions.addArguments("--start-maximized");
                //chromeOptions.addArguments("--remote-allow-origins=*");
                chromeOptions.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
                chromeOptions.setExperimentalOption("prefs", chromePrefs);
                driver = new ChromeDriver(chromeOptions);
                log.debug("Initializing chrome real browser mode");
                break;

            case CHROME_HEADLESS:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeHeadlessOptions = new ChromeOptions();
                Map<String, Object> chromeheadlessPrefs = new HashMap<>();
                chromeheadlessPrefs.put("credentials_enable_service", false);
                chromeheadlessPrefs.put("profile.password_manager_enabled", false);

                chromeHeadlessOptions.addArguments("--headless=new");
                chromeHeadlessOptions.addArguments("--start-maximized");
                //chromeHeadlessOptions.addArguments("--remote-allow-origins=*");
                //chromeHeadlessOptions.addArguments("--window-size=1920,1080");
                //chromeHeadlessOptions.addArguments("force-device-scale-factor=0.9");
                chromeHeadlessOptions.setExperimentalOption("prefs", chromeheadlessPrefs);
                chromeHeadlessOptions.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
                driver = new ChromeDriver(chromeHeadlessOptions);
                log.debug("Initializing chrome headless mode");
                break;

            case SAFARI:
                //TODO: Test if working in safari browser
                WebDriverManager.safaridriver().setup();
                SafariOptions safariOptions = new SafariOptions();
                driver = new SafariDriver(safariOptions);
                log.debug("Initializing safari driver");
                break;

            default:
                throw new IllegalArgumentException("Undefined value passed for BROWSER_TYPE");
        }

        WebDriverListener listener = new ListenerClass();
        return new EventFiringDecorator<>(listener).decorate(driver);
    }

}
