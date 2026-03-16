package com.retail.stepdefinitions;

import java.util.Properties;
import org.openqa.selenium.JavascriptExecutor;
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
     * order=2 → runs first (screenshot), order=1 → runs second (status reporting), order=0 → runs last (quit).
     */
    @After(order = 2)
    public void takeScreenshotOnFailure(Scenario scenario) {
        WebDriver driver = DriverFactory.getDriver();
        if (scenario.isFailed() && driver != null) {
            String screenshotName = scenario.getName().replaceAll(" ", "_");
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", screenshotName);
        }
    }

    /**
     * Report test status to BrowserStack using JavascriptExecutor.
     * This hook executes BEFORE quitBrowser (order=0) so the driver is still alive.
     * order=1 ensures it runs after takeScreenshotOnFailure (order=2) and before quitBrowser (order=0).
     *
     * The BrowserStack SDK captures the scenario status and session metadata
     * to update the BrowserStack dashboard with:
     *   - status: "passed" or "failed"
     *   - reason: failure message (if failed)
     *   - sessionName: scenario name for traceability
     */
    @After(order = 1)
    public void reportStatusToBrowserStack(Scenario scenario) {
        WebDriver driver = DriverFactory.getDriver();
        if (driver == null) {
            return; // BrowserStack not in use or driver already closed
        }

        try {
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

            // Determine status and reason
            boolean isFailed = scenario.isFailed();
            String status = isFailed ? "failed" : "passed";
            String reason = isFailed ? scenario.getName() + " - Test Failed" : "";

            // Send BrowserStack update with test metadata
            jsExecutor.executeScript(
                "browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"" + status + "\", \"reason\": \"" + reason + "\"}}"
            );

            System.out.println("[BrowserStack] Test '" + scenario.getName() + "' marked as " + status);
        } catch (Exception e) {
            // Silently ignore errors (e.g., local execution, BrowserStack SDK not active)
            System.out.println("[BrowserStack] Could not report status: " + e.getMessage());
        }
    }

    /**
     * Quit the browser and clean up the ThreadLocal slot.
     * order=0 → runs last, after all @After hooks with higher order values.
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

