package com.vh.caramel.automation.driver;

import com.typesafe.config.Config;
import com.vh.caramel.automation.factories.TestEnvFactory;
import com.vh.caramel.automation.factories.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;

import java.time.Duration;
import java.util.Objects;

public final class Driver {

    private static final Logger log = LogManager.getLogger(Driver.class.getName());

    /**
     * Private constructor to avoid external instantiation
     */
    private Driver() {}

    /**
     * Gets the browser value and initialise the browser based on that
     */
    public static void initDriver(){

        final Config CONFIG = TestEnvFactory.getInstance().getConfig();

        if(Objects.isNull(DriverManager.getDriver())) {
            try {
                DriverManager.setDriver(DriverFactory.getDriver());
            } catch (Exception e) {
                log.error("Please check the capabilities of browser");
                log.error(e.getMessage());
            }

            if(CONFIG.getString("BROWSER_TYPE").contains("_HEADLESS")){
                int window_width = CONFIG.getInt("WINDOW_WIDTH");
                int window_height = CONFIG.getInt("WINDOW_HEIGHT");

                log.info("Set Window Height : {}", window_height);
                log.info("Set Window Width  : {}", window_width);

                // Set new size
                Dimension dimension = new Dimension(window_width, window_height);
                DriverManager.getDriver().manage().window().setSize(dimension);

            }else {
                log.info("Maximizing Browser Window");
                DriverManager.getDriver().manage().window().maximize();
            }

            log.info("Deleting All Cookies");
            DriverManager.getDriver().manage().deleteAllCookies();
            DriverManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

            DriverManager.getDriver().get(CONFIG.getString("LOGIN_URL"));
            log.info("Navigating to Application URL = {}", CONFIG.getString("LOGIN_URL"));
        }
    }

    /**
     * Terminates the browser instance.
     * Sets the thread local to default value, i.e null.
     */
    public static void quitDriver() {
        if(Objects.nonNull(DriverManager.getDriver())) {
            DriverManager.getDriver().quit();
            DriverManager.unload();
        }
    }

}
