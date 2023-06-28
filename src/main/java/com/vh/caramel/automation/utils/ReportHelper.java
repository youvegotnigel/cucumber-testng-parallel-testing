package com.vh.caramel.automation.utils;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;


public class ReportHelper {

    private static final Logger log = LogManager.getLogger(ReportHelper.class.getName());

    //TODO: Need to remove the hardcoded values here
    public static void generateCucumberReport() {

        File reportOutputDirectory = new File("target");
        ArrayList<String> jsonFiles = new ArrayList<String>();
        jsonFiles.add("target/cucumber-reports/cucumber.json");

        String workingDir = System.getProperty("user.dir");
        String projectName = workingDir.substring(workingDir.lastIndexOf(File.separator)+1);

        Configuration configuration = new Configuration(reportOutputDirectory, projectName);
        configuration.addClassifications("Platform", "Windows 10");
        configuration.addClassifications("Browser", "Chrome");
        configuration.addClassifications("Browser Version", "114.0");

        try{
            ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
            reportBuilder.generateReports();
        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
        }

    }
}
