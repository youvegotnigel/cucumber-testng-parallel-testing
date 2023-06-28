package com.vh.caramel.automation.factories;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Objects;

public class TestEnvFactory {

    private static final Logger log = LogManager.getLogger(TestEnvFactory.class.getName());

    private static final TestEnvFactory UNIQUE_INSTANCE = new TestEnvFactory();

    private Config config;

    /**
     * Private constructor to avoid external instantiation
     */
    private TestEnvFactory() {
        config = setConfig();
    }

    public static TestEnvFactory getInstance() {
        return UNIQUE_INSTANCE;
    }

    public Config getConfig() {
        return config;
    }

    private Config setConfig() {
        log.info("Calling setConfig");

        // Standard config load behavior (loads common config from application.conf file)
        // https://github.com/lightbend/config#standard-behavior
        config = ConfigFactory.load();

        Config referenceConfig = ConfigFactory.load("reference");
        config = config.withFallback(referenceConfig);

        config = getAllConfigFromFilesInTheResourcePath("common");

        TestEnv testEnv = config.getEnum(TestEnv.class, "TEST_ENV");
        return getAllConfigFromFilesInTheResourcePath(testEnv.getValue());
    }

    private Config getAllConfigFromFilesInTheResourcePath(String resourceBasePath) {
        try {
            String path = String.format("src/test/resources/conf/%s", resourceBasePath);
            log.info("path: {}", path);

            File testEnvDir = new File(path);
            for (File file : Objects.requireNonNull(testEnvDir.listFiles())) {
                String resourceFileBasePath = String.format("conf/%s/%s", resourceBasePath, file.getName());
                log.info("resourceFileBasePath: {}", resourceFileBasePath);

                Config childConfig = ConfigFactory.load(resourceFileBasePath);
                config = config.withFallback(childConfig);
            }

            return config;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new IllegalStateException("Could not parse config");
        }
    }
}
