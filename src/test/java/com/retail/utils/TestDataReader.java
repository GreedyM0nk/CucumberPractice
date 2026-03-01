package com.retail.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.FileReader;
import java.io.IOException;

/**
 * TestDataReader - Utility class to read test data from JSON files
 */
public class TestDataReader {

    private static final String TEST_DATA_PATH = "src/test/resources/testdata/";
    private static final Gson gson = new Gson();

    /**
     * Read JSON file and return as JsonObject
     * @param fileName Name of the JSON file (e.g., "users.json")
     * @return JsonObject containing test data
     */
    public static JsonObject readTestData(String fileName) {
        try (FileReader reader = new FileReader(TEST_DATA_PATH + fileName)) {
            return gson.fromJson(reader, JsonObject.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read test data file: " + fileName, e);
        }
    }

    /**
     * Get user credentials by type (standard, admin, guest)
     * @param userType Type of user
     * @return JsonObject containing user credentials
     */
    public static JsonObject getUserByType(String userType) {
        JsonObject testData = readTestData("users.json");
        return testData.getAsJsonArray("users")
                .asList()
                .stream()
                .map(element -> element.getAsJsonObject())
                .filter(user -> user.get("type").getAsString().equalsIgnoreCase(userType))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User type not found: " + userType));
    }

    /**
     * Get product by ID
     * @param productId Product ID
     * @return JsonObject containing product details
     */
    public static JsonObject getProductById(String productId) {
        JsonObject testData = readTestData("products.json");
        return testData.getAsJsonArray("products")
                .asList()
                .stream()
                .map(element -> element.getAsJsonObject())
                .filter(product -> product.get("id").getAsString().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found: " + productId));
    }

    /**
     * Get product by name
     * @param productName Product name
     * @return JsonObject containing product details
     */
    public static JsonObject getProductByName(String productName) {
        JsonObject testData = readTestData("products.json");
        return testData.getAsJsonArray("products")
                .asList()
                .stream()
                .map(element -> element.getAsJsonObject())
                .filter(product -> product.get("name").getAsString().equalsIgnoreCase(productName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found: " + productName));
    }
}
