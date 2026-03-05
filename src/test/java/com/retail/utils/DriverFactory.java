package com.retail.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.time.Duration;
import java.util.Properties;

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
     * WebDriverManager.setup() calls are synchronized to prevent concurrent
     * binary-download races when multiple threads initialise at the same time.
     */
    public WebDriver init_driver(Properties prop) {
        String browserName = prop.getProperty("browser").trim();
        boolean headless = Boolean.parseBoolean(prop.getProperty("headless", "false"));

        System.out.println("Browser name is: " + browserName);
        System.out.println("Headless mode: " + headless);

        if (browserName.equalsIgnoreCase("chrome")) {
            synchronized (DriverFactory.class) {
                WebDriverManager.chromedriver().setup();
            }
            ChromeOptions options = new ChromeOptions();
            // Always-on stability flags
            options.addArguments("--disable-blink-features=AutomationControlled");
            options.addArguments("--disable-extensions");
            if (headless) {
                // Required for CI/CD environments (e.g. GitHub Actions / Linux with no display)
                options.addArguments("--headless=new");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--window-size=1920,1080");
            }
            tlDriver.set(new ChromeDriver(options));

        } else if (browserName.equalsIgnoreCase("firefox")) {
            synchronized (DriverFactory.class) {
                WebDriverManager.firefoxdriver().setup();
            }
            FirefoxOptions options = new FirefoxOptions();
            if (headless) options.addArguments("--headless");
            tlDriver.set(new FirefoxDriver(options));

        } else if (browserName.equalsIgnoreCase("edge")) {
            synchronized (DriverFactory.class) {
                WebDriverManager.edgedriver().setup();
            }
            EdgeOptions options = new EdgeOptions();
            if (headless) options.addArguments("--headless=new");
            tlDriver.set(new EdgeDriver(options));

        } else {
            throw new IllegalArgumentException(
                "Unsupported browser: '" + browserName + "'. Use chrome, firefox, or edge.");
        }

        WebDriver driver = getDriver();
        String baseUrl = prop.getProperty("url");
        tlBaseUrl.set(baseUrl);          // store so page objects can use getBaseUrl()

        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(
            Duration.ofSeconds(Long.parseLong(prop.getProperty("implicitWait", "10"))));
        driver.get(baseUrl);

        return driver;
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

