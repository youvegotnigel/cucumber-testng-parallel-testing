package com.vh.caramel.automation.utils;

import com.vh.caramel.automation.driver.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public final class TabsManager {

    private static final Logger log = LogManager.getLogger(TabsManager.class.getName());

    public enum TabStrategy {
        SWITCH_TO_NEXT_TAB,
        CLOSE_AND_SWITCH_TO_NEXT_TAB,
        SWITCH_TO_PREVIOUS_TAB,
        CLOSE_TAB_AND_RETURN,
        SWITCH_TO_NEXT_TAB_AND_CLOSE
    }

    /**
     * Private constructor to avoid external instantiation
     */
    private TabsManager() {
    }


    public static void performTabSwitch(TabStrategy tabStrategy){

        var driver = DriverManager.getDriver();
        ArrayList<String> tab = new ArrayList<>(driver.getWindowHandles());

        switch (tabStrategy) {

            case SWITCH_TO_NEXT_TAB:
                driver.switchTo().window(tab.get(1));
                break;

            case CLOSE_AND_SWITCH_TO_NEXT_TAB:
                driver.close();
                driver.switchTo().window(tab.get(1));
                break;

            case SWITCH_TO_PREVIOUS_TAB:
                driver.switchTo().window(tab.get(0));
                break;

            case CLOSE_TAB_AND_RETURN:
                driver.close();
                driver.switchTo().window(tab.get(0));
                break;

            case SWITCH_TO_NEXT_TAB_AND_CLOSE:
                driver.switchTo().window(tab.get(1));
                driver.close();
                break;
        }


    }


}
