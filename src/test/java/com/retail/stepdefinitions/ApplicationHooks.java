package com.retail.stepdefinitions;

import java.util.Properties;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.retail.utils.ConfigReader;
import com.retail.utils.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class ApplicationHooks {

    private DriverFactory driverFactory;
    private ConfigReader configReader;
    Properties prop;

    @Before(order = 0)
    public void getProperty() {
        configReader = new ConfigReader();
        prop = configReader.init_prop();
    }

    @Before(order = 1)
    public void launchBrowser() {
        driverFactory = new DriverFactory();
        driverFactory.init_driver(prop);
    }

    /**
     * Take screenshot BEFORE quitting the browser.
     * Lower order number runs FIRST in @After, so order=1 runs before order=0.
     */
    @After(order = 1)
    public void tearDown(Scenario scenario) {
        WebDriver driver = DriverFactory.getDriver();
        if (scenario.isFailed() && driver != null) {
            // take screenshot before browser is closed
            String screenshotName = scenario.getName().replaceAll(" ", "_");
            byte[] sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(sourcePath, "image/png", screenshotName);
        }
    }

    /**
     * Quit browser last (order=0 runs after order=1 in @After hooks).
     */
    @After(order = 0)
    public void quitBrowser() {
        WebDriver driver = DriverFactory.getDriver();
        if (driver != null) {
            driver.quit();
            DriverFactory.tlDriver.remove(); // clean up ThreadLocal to prevent leaks
        }
    }
}