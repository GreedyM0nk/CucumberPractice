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
        driver.navigate().to("https://sauce-demo.myshopify.com/");
        // Wait for page to load
        wait.until(ExpectedConditions.urlContains("sauce-demo"));
    }

    @Given("I am on the Sauce Demo homepage {string}")
    public void navigateToSauceDemoHomepageWithUrl(String url) {
        driver.navigate().to(url);
        wait.until(ExpectedConditions.urlContains("sauce-demo"));
    }

    @Given("I am on the {string} page")
    public void navigateToPage(String pagePath) {
        String baseUrl = "https://sauce-demo.myshopify.com";
        String fullUrl = baseUrl + pagePath;
        driver.navigate().to(fullUrl);
        wait.until(ExpectedConditions.urlContains(pagePath.substring(0, Math.min(5, pagePath.length()))));
    }

    @Given("I refresh the current page")
    public void refreshPage() {
        driver.navigate().refresh();
    }

    // ─────────────────────────────────────────────
    // COMMON VERIFICATION STEPS
    // ─────────────────────────────────────────────

    @Then("the page title should be {string}")
    public void verifyPageTitle(String expectedTitle) {
        String actualTitle = driver.getTitle();
        assertEquals("Page title mismatch", expectedTitle, actualTitle);
    }

    @Then("the URL should contain {string}")
    public void verifyUrlContains(String urlPart) {
        String currentUrl = driver.getCurrentUrl();
        assertTrue("URL does not contain '" + urlPart + "'. Current URL: " + currentUrl,
                currentUrl.contains(urlPart));
    }

    @Then("the URL should be {string}")
    public void verifyUrlEquals(String expectedUrl) {
        String currentUrl = driver.getCurrentUrl();
        assertEquals("URL mismatch", expectedUrl, currentUrl);
    }
}
