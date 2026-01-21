package com.retail.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private Properties properties;

    public Properties init_prop() {
        properties = new Properties();
        try {
            // 1. Load the Base Config (Browser, etc.)
            FileInputStream ip = new FileInputStream("./src/test/resources/config/config.properties");
            properties.load(ip);

            // 2. Check if "env" is passed from Command Line (Maven), else take from config file
            String envName = System.getProperty("env");
            if (envName == null) {
                envName = properties.getProperty("env");
            }

            // 3. Load Environment Specific Config
            FileInputStream envIp = new FileInputStream("./src/test/resources/config/config-" + envName + ".properties");
            properties.load(envIp);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}