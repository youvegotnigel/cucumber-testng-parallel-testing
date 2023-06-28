package com.vh.caramel.automation.factories;

public enum TestEnv {

    DEFAULT("default");

    TestEnv(String value){
        this.value = value;
    }

    private String value;

    public String getValue(){
        return value;
    }
}
