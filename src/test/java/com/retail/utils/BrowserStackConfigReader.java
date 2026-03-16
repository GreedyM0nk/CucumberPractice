package com.retail.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Reads BrowserStack configuration from browserstack.yml file
 * Returns credentials and other settings needed for cloud testing
 */
public class BrowserStackConfigReader {

    private static final String BROWSERSTACK_CONFIG_FILE = "./browserstack.yml";

    /**
     * Reads browserstack.yml and extracts credentials
     * @return Map containing userName and accessKey, or empty map if file not found
     */
    public static Map<String, String> getBrowserStackCredentials() {
        Map<String, String> credentials = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(BROWSERSTACK_CONFIG_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // Parse YAML key: value format
                if (line.contains(":") && !line.startsWith("#")) {
                    String[] parts = line.split(":", 2);
                    if (parts.length == 2) {
                        String key = parts[0].trim();
                        String value = parts[1].trim();

                        if (key.equals("userName")) {
                            credentials.put("userName", value);
                        } else if (key.equals("accessKey")) {
                            credentials.put("accessKey", value);
                        }
                    }
                }
            }

            if (credentials.isEmpty()) {
                System.out.println("[BrowserStackConfigReader] No valid credentials found in browserstack.yml");
            } else {
                System.out.println("[BrowserStackConfigReader] ✓ BrowserStack credentials loaded from browserstack.yml");
            }

        } catch (IOException e) {
            System.out.println("[BrowserStackConfigReader] Could not read browserstack.yml: " + e.getMessage());
        }

        return credentials;
    }
}
