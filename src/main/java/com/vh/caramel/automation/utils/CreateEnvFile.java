package com.vh.caramel.automation.utils;

import com.vh.caramel.automation.factories.TestEnvFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public final class CreateEnvFile {

    private static Properties properties = new Properties();
    private static final Logger log = LogManager.getLogger(CreateEnvFile.class.getName());

    /**
     * Private constructor to avoid external instantiation
     */
    private CreateEnvFile() {}

    public static void createFile() {

        //properties.setProperty("Branch", FrameworkConstants.getGitBranchName());
        properties.setProperty("Platform", "Windows");
        properties.setProperty("Browser", "Chrome");
        properties.setProperty("Browser Version", "114.0");
        properties.setProperty("AUT", TestEnvFactory.getInstance().getConfig().getString("LOGIN_URL"));

        FileWriter writer = null;
        try {
            writer = new FileWriter("allure-results\\environment.properties");
            properties.store(writer, "youvegotnigel");
        } catch (IOException ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}