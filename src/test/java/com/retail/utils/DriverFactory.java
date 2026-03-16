package com.retail.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.time.Duration;
import java.util.Properties;
import java.util.HashMap;
import java.util.Map;

public class DriverFactory {

    /**
     * Private ThreadLocal – one WebDriver instance per thread.
     * Access only through getDriver() and removeDriver().
     */
    private static final ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    /**
     * Stores the base URL for the current thread's environment.
     * Page objects use getBaseUrl() so the URL is never hardcoded in page classes.
     */
    private static final ThreadLocal<String> tlBaseUrl = new ThreadLocal<>();

    /**
     * Creates a new WebDriver for the current thread and stores it in ThreadLocal.
     *
     * Checks for BrowserStack environment variables and routes to cloud if present.
     * Falls back to local WebDriver instantiation if not.
     */
    public WebDriver init_driver(Properties prop) {
        String browserName = prop.getProperty("browser").trim();
        boolean headless = Boolean.parseBoolean(prop.getProperty("headless", "false"));

        System.out.println("Browser name is: " + browserName);
        System.out.println("Headless mode: " + headless);

        // Check if BrowserStack credentials are available (Priority order):
        // 1. Environment variables (CI/CD preference)
        // 2. browserstack.yml file (local development)
        String bsUsername = System.getenv("BROWSERSTACK_USERNAME");
        String bsAccessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");

        // If env vars are not set, try reading from browserstack.yml
        if ((bsUsername == null || bsUsername.isEmpty()) || (bsAccessKey == null || bsAccessKey.isEmpty())) {
            java.util.Map<String, String> bsConfig = BrowserStackConfigReader.getBrowserStackCredentials();
            if (!bsConfig.isEmpty()) {
                bsUsername = bsConfig.getOrDefault("userName", bsUsername);
                bsAccessKey = bsConfig.getOrDefault("accessKey", bsAccessKey);
            }
        }

        if (bsUsername != null && bsAccessKey != null && !bsUsername.isEmpty() && !bsAccessKey.isEmpty()) {
            System.out.println("✓ BrowserStack credentials found. Routing to BrowserStack cloud...");
            tlDriver.set(createBrowserStackDriver(browserName, bsUsername, bsAccessKey));
        } else {
            System.out.println("✗ BrowserStack credentials not found. Using local WebDriver...");
            tlDriver.set(createLocalDriver(browserName, headless));
        }

        WebDriver driver = getDriver();
        String baseUrl = prop.getProperty("url");
        tlBaseUrl.set(baseUrl);

        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(
            Duration.ofSeconds(Long.parseLong(prop.getProperty("implicitWait", "10"))));
        driver.get(baseUrl);

        return driver;
    }

    /**
     * Creates a BrowserStack RemoteWebDriver instance
     */
    private WebDriver createBrowserStackDriver(String browserName, String username, String accessKey) {
        try {
            Map<String, Object> browserstackOptions = new HashMap<>();
            browserstackOptions.put("userName", username);
            browserstackOptions.put("accessKey", accessKey);
            browserstackOptions.put("buildName", "Retail-Automation-Build");
            browserstackOptions.put("sessionName", "BDD Test - " + browserName);
            browserstackOptions.put("debug", true);
            browserstackOptions.put("networkLogs", true);
            browserstackOptions.put("consoleLogs", "info");

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-blink-features=AutomationControlled");
            options.addArguments("--disable-extensions");

            if (browserName.equalsIgnoreCase("chrome")) {
                options.setCapability("os", "Windows");
                options.setCapability("osVersion", "10");
                options.setCapability("browserVersion", "120.0");
            } else if (browserName.equalsIgnoreCase("firefox")) {
                options = new ChromeOptions(); // Use Chrome options structure for BrowserStack
                options.setCapability("browserName", "Firefox");
                options.setCapability("os", "Windows");
                options.setCapability("osVersion", "10");
                options.setCapability("browserVersion", "120.0");
            }

            options.setCapability("bstack:options", browserstackOptions);

            String hubURL = "https://" + username + ":" + accessKey + "@hub-cloud.browserstack.com/wd/hub";
            return new RemoteWebDriver(new URL(hubURL), options);

        } catch (Exception e) {
            System.err.println("Failed to create BrowserStack driver: " + e.getMessage());
            System.out.println("Falling back to local WebDriver...");
            return createLocalDriver(browserName, false);
        }
    }

    /**
     * Creates a local WebDriver instance
     */
    private WebDriver createLocalDriver(String browserName, boolean headless) {
        if (browserName.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-blink-features=AutomationControlled");
            options.addArguments("--disable-extensions");
            if (headless) {
                options.addArguments("--headless=new");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--window-size=1920,1080");
            }
            return new ChromeDriver(options);

        } else if (browserName.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            if (headless) options.addArguments("--headless");
            return new FirefoxDriver(options);

        } else if (browserName.equalsIgnoreCase("edge")) {
            EdgeOptions options = new EdgeOptions();
            if (headless) options.addArguments("--headless=new");
            return new EdgeDriver(options);

        } else {
            throw new IllegalArgumentException(
                "Unsupported browser: '" + browserName + "'. Use chrome, firefox, or edge.");
        }
    }

    /**
     * Returns the WebDriver for the current thread.
     * ThreadLocal.get() is inherently thread-safe — no synchronisation needed.
     */
    public static WebDriver getDriver() {
        return tlDriver.get();
    }

    /**
     * Returns the base URL for the current thread's environment.
     * Use this in page objects instead of hardcoding URLs.
     */
    public static String getBaseUrl() {
        return tlBaseUrl.get();
    }

    /**
     * Removes the WebDriver and baseUrl from the current thread's ThreadLocal slots.
     * MUST be called after driver.quit() to prevent memory leaks in thread pools.
     */
    public static void removeDriver() {
        tlDriver.remove();
        tlBaseUrl.remove();
    }
}

