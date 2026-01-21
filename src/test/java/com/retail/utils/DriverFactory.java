package com.retail.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.time.Duration;
import java.util.Properties;

public class DriverFactory {

    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    public WebDriver init_driver(Properties prop) {
        String browserName = prop.getProperty("browser").trim();

        System.out.println("Browser name is: " + browserName);

        if (browserName.equalsIgnoreCase("chrome")) {
            tlDriver.set(new ChromeDriver());
        } else if (browserName.equalsIgnoreCase("firefox")) {
            tlDriver.set(new FirefoxDriver());
        } else if (browserName.equalsIgnoreCase("edge")) {
            tlDriver.set(new EdgeDriver());
        } else {
            System.out.println("Please pass the correct browser name: " + browserName);
        }

        getDriver().manage().deleteAllCookies();
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.parseLong(prop.getProperty("implicitWait"))));
        getDriver().get(prop.getProperty("url"));

        return getDriver();
    }

    // Synchronized method to get driver
    public static synchronized WebDriver getDriver() {
        return tlDriver.get();
    }
}