package com.retail.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Loads the two-layer configuration:
 *   1. config.properties        – browser, implicitWait, headless, etc. (shared across envs)
 *   2. config-<env>.properties  – url, username, password  (environment-specific)
 *
 * The active environment is resolved in this priority order:
 *   1. JVM system property  -Denv=<name>   (set by Maven Surefire from the active profile)
 *   2. The `env` key inside config.properties  (fallback, defaults to "uat")
 *
 * To switch environments from the command line:
 *   mvn test -Puat    →  loads config-uat.properties
 *   mvn test -Psit    →  loads config-sit.properties
 *   mvn test -Pprod   →  loads config-prod.properties
 *   mvn test -Denv=sit  →  same as -Psit (direct override)
 */
public class ConfigReader {

    /**
     * Thread-safe: all variables are local — a new Properties object is
     * created and returned on every call; nothing is shared across threads.
     */
    public Properties init_prop() {
        Properties properties = new Properties();
        try {
            // 1. Load base config (browser, implicitWait, headless, env default …)
            try (FileInputStream base = new FileInputStream(
                    "./src/test/resources/config/config.properties")) {
                properties.load(base);
            }

            // 2. Resolve environment name:
            //    • JVM system property wins (set by Surefire <systemPropertyVariables><env>)
            //    • fall back to value in config.properties
            //    • ultimate fallback: "uat"
            String envName = System.getProperty("env");
            if (envName == null || envName.isBlank()) {
                envName = properties.getProperty("env", "uat");
            }
            System.out.println("[ConfigReader] Active environment: " + envName);

            // 3. Load environment-specific config (url, username, password …)
            try (FileInputStream envFile = new FileInputStream(
                    "./src/test/resources/config/config-" + envName + ".properties")) {
                properties.load(envFile);
            }

        } catch (IOException e) {
            throw new RuntimeException(
                "[ConfigReader] Failed to load configuration: " + e.getMessage(), e);
        }
        return properties;
    }
}

