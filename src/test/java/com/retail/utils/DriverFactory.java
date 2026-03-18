package com.retail.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Properties;

public class DriverFactory {

    private static final Logger logger = LoggerFactory.getLogger(DriverFactory.class);

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
     * Runs local WebDriver (Chrome, Firefox, or Edge).
     * Auto-detects CI environment and enables headless mode accordingly.
     */
    public WebDriver init_driver(Properties prop) {
        String browserName = prop.getProperty("browser").trim();
        
        // Check if running in CI environment (GitHub Actions, etc.)
        boolean isCIEnvironment = Boolean.parseBoolean(System.getenv("CI")) || 
                                  Boolean.parseBoolean(System.getenv("HEADLESS"));
        
        // Read headless from properties, but override with CI environment
        boolean headless = isCIEnvironment || Boolean.parseBoolean(prop.getProperty("headless", "false"));

        logger.info("Browser name is: {}", browserName);
        logger.info("CI Environment: {}", isCIEnvironment);
        logger.info("Headless mode: {}", headless);

        tlDriver.set(createLocalDriver(browserName, headless));

        WebDriver driver = getDriver();
        String baseUrl = prop.getProperty("url");
        tlBaseUrl.set(baseUrl);

        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        // Implicit waits are DISABLED (set to ZERO) - we use explicit waits only in page objects
        // This prevents conflicts between implicit and explicit wait strategies
        // All waits must use WebDriverWait with ExpectedConditions in BasePage
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        driver.get(baseUrl);

        return driver;
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
                options.addArguments("--disable-gpu");
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

