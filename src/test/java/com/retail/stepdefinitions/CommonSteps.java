package com.retail.stepdefinitions;

import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.retail.utils.DriverFactory;
import com.retail.utils.ConfigReader;

import static org.junit.Assert.assertTrue;

import java.time.Duration;

/**
 * Common/General Step Definitions
 * 
 * Shared steps used across multiple test suites:
 * - Navigation to URLs
 * - Common page verifications
 * - General setup and teardown
 */
public class CommonSteps {

    private WebDriver driver;
    private WebDriverWait wait;

    public CommonSteps() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ─────────────────────────────────────────────
    // COMMON NAVIGATION STEPS
    // ─────────────────────────────────────────────

    @Given("I am on the Sauce Demo homepage")
    public void navigateToSauceDemoHomepage() {
        String baseUrl = ConfigReader.getProperty("base.url");
        if (baseUrl == null || baseUrl.isEmpty()) {
            baseUrl = "https://www.saucedemo.com";
        }
        driver.navigate().to(baseUrl);
        // Wait for page to load
        wait.until(ExpectedConditions.urlContains("saucedemo"));
    }

    @Given("I am on the {string} page")
    public void navigateToPage(String pagePath) {
        String baseUrl = ConfigReader.getProperty("base.url");
        if (baseUrl == null || baseUrl.isEmpty()) {
            baseUrl = "https://www.saucedemo.com";
        }
        String fullUrl = baseUrl + pagePath;
        driver.navigate().to(fullUrl);
        wait.until(ExpectedConditions.urlContains(pagePath));
    }

    @Given("I refresh the current page")
    public void refreshPage() {
        driver.navigate().refresh();
    }
}
