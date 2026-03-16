package com.retail.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.retail.utils.DriverFactory;

import static org.junit.Assert.assertEquals;
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

    private WebDriver driver;  // Not initialized in constructor - lazy initialization

    /**
     * Lazy getter for WebDriver to ensure it's retrieved after @Before hook
     */
    private WebDriver getDriver() {
        if (driver == null) {
            driver = DriverFactory.getDriver();
        }
        return driver;
    }

    /**
     * Lazy getter for WebDriverWait
     */
    private WebDriverWait getWait() {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(10));
    }

    // ─────────────────────────────────────────────
    // COMMON NAVIGATION STEPS
    // ─────────────────────────────────────────────

    @Given("I am on the Sauce Demo homepage")
    public void navigateToSauceDemoHomepage() {
        String baseUrl = DriverFactory.getBaseUrl();  // Load from config
        getDriver().navigate().to(baseUrl);
        // Wait for page to load
        getWait().until(ExpectedConditions.urlContains("sauce-demo"));
    }

    @Given("I am on the Sauce Demo homepage {string}")
    public void navigateToSauceDemoHomepageWithUrl(String url) {
        // Override with parameter but use config if empty
        String targetUrl = url != null && !url.isEmpty() ? url : DriverFactory.getBaseUrl();
        getDriver().navigate().to(targetUrl);
        getWait().until(ExpectedConditions.urlContains("sauce-demo"));
    }

    @Given("I am on the {string} page")
    public void navigateToPage(String pagePath) {
        String baseUrl = DriverFactory.getBaseUrl();  // Load from config
        String fullUrl = baseUrl + pagePath;
        getDriver().navigate().to(fullUrl);
        getWait().until(ExpectedConditions.urlContains(pagePath.substring(0, Math.min(5, pagePath.length()))));
    }

    @Given("I refresh the current page")
    public void refreshPage() {
        getDriver().navigate().refresh();
    }

    // ─────────────────────────────────────────────
    // COMMON VERIFICATION STEPS
    // ─────────────────────────────────────────────

    @Then("the page title should be {string}")
    public void verifyPageTitle(String expectedTitle) {
        String actualTitle = getDriver().getTitle();
        assertEquals("Page title mismatch", expectedTitle, actualTitle);
    }

    @Then("the URL should contain {string}")
    public void verifyUrlContains(String urlPart) {
        String currentUrl = getDriver().getCurrentUrl();
        assertTrue("URL does not contain '" + urlPart + "'. Current URL: " + currentUrl,
                currentUrl.contains(urlPart));
    }

    @Then("the URL should be {string}")
    public void verifyUrlEquals(String expectedUrl) {
        String currentUrl = getDriver().getCurrentUrl();
        assertEquals("URL mismatch", expectedUrl, currentUrl);
    }

    @Then("the link destination should be {string}")
    public void verifyLinkDestination(String expectedUrl) {
        String currentUrl = getDriver().getCurrentUrl();
        assertTrue("Link destination does not match. Expected: " + expectedUrl + ", Got: " + currentUrl,
                currentUrl.contains(expectedUrl) || currentUrl.equals(expectedUrl));
    }
}
