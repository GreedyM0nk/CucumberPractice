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

    /**
     * Combined @Before: load properties then launch browser in a single hook.
     * Using a single method with all setup avoids any shared-field race between
     * two separate @Before hooks when scenarios run in parallel threads.
     * order=0 → runs first before any other @Before hooks.
     */
    @Before(order = 0)
    public void setUp() {
        // Local variable — never shared across threads or scenarios
        Properties prop = new ConfigReader().init_prop();
        new DriverFactory().init_driver(prop);
    }

    /**
     * Take screenshot BEFORE quitting the browser.
     * In Cucumber @After hooks, HIGHER order number runs FIRST.
     * order=1 → runs first (screenshot), order=0 → runs last (quit).
     */
    @After(order = 1)
    public void takeScreenshotOnFailure(Scenario scenario) {
        WebDriver driver = DriverFactory.getDriver();
        if (scenario.isFailed() && driver != null) {
            String screenshotName = scenario.getName().replaceAll(" ", "_");
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", screenshotName);
        }
    }

    /**
     * Quit the browser and clean up the ThreadLocal slot.
     * order=0 → runs last, after the screenshot has been captured.
     */
    @After(order = 0)
    public void quitBrowser() {
        WebDriver driver = DriverFactory.getDriver();
        if (driver != null) {
            driver.quit();
            DriverFactory.removeDriver(); // prevent ThreadLocal memory leak
        }
    }
}

