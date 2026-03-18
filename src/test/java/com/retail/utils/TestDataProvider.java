package com.retail.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test Data Provider Utility
 * 
 * Loads test data from testdata.properties file and provides
 * thread-safe access to test data throughout the test suite.
 * This eliminates hardcoded test data in step definitions.
 * 
 * Usage:
 * String email = TestDataProvider.get("testuser.email");
 * String password = TestDataProvider.get("testuser.password");
 * 
 * @author Automation Framework Team
 */
public class TestDataProvider {

    private static final Logger logger = LoggerFactory.getLogger(TestDataProvider.class);

    private static Properties testData;
    private static boolean initialized = false;

    static {
        loadTestData();
    }

    /**
     * Load test data from testdata.properties file
     * Called automatically during class initialization
     */
    private static synchronized void loadTestData() {
        if (initialized) {
            return;
        }

        testData = new Properties();
        try (InputStream input = TestDataProvider.class.getClassLoader()
                .getResourceAsStream("testdata.properties")) {

            if (input == null) {
                throw new RuntimeException(
                        "testdata.properties file not found in src/test/resources/");
            }

            testData.load(input);
            initialized = true;
            logger.info("Loaded {} test data properties", testData.size());

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to load testdata.properties: " + e.getMessage(), e);
        }
    }

    /**
     * Get test data property by key
     * 
     * @param key The property key (e.g., "testuser.email")
     * @return The property value
     * @throws RuntimeException if key not found
     */
    public static String get(String key) {
        String value = testData.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Test data key not found: '" + key + "'");
        }
        return value;
    }

    /**
     * Get test data property with default value
     * 
     * @param key          The property key
     * @param defaultValue Value to return if key not found
     * @return The property value or default if not found
     */
    public static String get(String key, String defaultValue) {
        return testData.getProperty(key, defaultValue);
    }

    /**
     * Get test data as integer
     * 
     * @param key The property key
     * @return The property value as int
     */
    public static int getInt(String key) {
        String value = get(key);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid integer value for key '" + key + "': " + value);
        }
    }

    /**
     * Get test data as double
     * 
     * @param key The property key
     * @return The property value as double
     */
    public static double getDouble(String key) {
        String value = get(key);
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid double value for key '" + key + "': " + value);
        }
    }

    /**
     * Get test data as boolean
     * 
     * @param key The property key
     * @return The property value as boolean
     */
    public static boolean getBoolean(String key) {
        String value = get(key);
        return Boolean.parseBoolean(value);
    }

    /**
     * Common getters for frequently used test data
     */

    public static String getValidEmail() {
        return get("testuser.email");
    }

    public static String getValidPassword() {
        return get("testuser.password");
    }

    public static String getInvalidEmail() {
        return get("invalid.email");
    }

    public static String getInvalidPassword() {
        return get("invalid.password");
    }

    public static String getProductName(int productNumber) {
        return get("product" + productNumber + ".name");
    }

    public static double getProductPrice(int productNumber) {
        return getDouble("product" + productNumber + ".price");
    }

    public static String getCardNumber() {
        return get("card.number");
    }

    public static String getCardCVV() {
        return get("card.cvv");
    }

    public static String getShippingAddress() {
        return get("shipping.address.street") + ", " +
                get("shipping.address.city") + ", " +
                get("shipping.address.state") + " " +
                get("shipping.address.zip");
    }

    /**
     * Reload test data (useful for testing or property file changes)
     */
    public static synchronized void reload() {
        initialized = false;
        loadTestData();
    }

    /**
     * Get all test data properties (for debugging)
     * 
     * @return Properties object containing all test data
     */
    public static Properties getAllData() {
        return new Properties(testData); // Return copy to prevent external modification
    }
}
