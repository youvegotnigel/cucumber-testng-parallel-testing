package com.vh.caramel.automation.factories;

public enum DriverType {

    CHROME("chrome"),
    EDGE("edge"),
    FIREFOX("firefox"),
    SAFARI("safari"),
    CHROME_HEADLESS("chrome(headless)"),
    FIREFOX_HEADLESS("firefox(headless)");

    DriverType(String value){
        this.value = value;
    }

    private String value;

    public String getValue(){
        return value;
    }
}
