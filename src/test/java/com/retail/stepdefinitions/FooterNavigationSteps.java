package com.retail.stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.retail.utils.DriverFactory;

import static org.junit.Assert.*;

import java.time.Duration;
import java.util.List;

/**
 * Footer Navigation and Content Step Definitions
 * 
 * Tests for footer sections including:
 * - Footer navigation links
 * - About Us section
 * - Payment icons
 * - Bottom bar links and copyright
 */
public class FooterNavigationSteps {

    private WebDriver driver;
    private WebDriverWait wait;

    public FooterNavigationSteps() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ─────────────────────────────────────────────
    // FOOTER NAVIGATION SECTION STEPS
    // ─────────────────────────────────────────────

    @Then("I should see the heading {string} in the footer navigation section")
    public void verifyFooterNavigationHeading(String heading) {
        WebElement footerNav = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.cssSelector("footer nav"))
        );
        WebElement headingElement = footerNav.findElement(By.xpath(".//h3[text()='" + heading + "']"));
        assertTrue("Footer navigation heading '" + heading + "' should be visible", headingElement.isDisplayed());
    }

    @Then("I should see the link {string} in the footer navigation")
    public void verifyLinkInFooterNavigation(String linkText) {
        WebElement footerNav = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.cssSelector("footer nav"))
        );
        WebElement link = footerNav.findElement(By.linkText(linkText));
        assertTrue("Link '" + linkText + "' should be visible in footer navigation", link.isDisplayed());
    }

    @When("I click the {string} link in the footer navigation")
    public void clickLinkInFooterNavigation(String linkText) {
        WebElement footerNav = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.cssSelector("footer nav"))
        );
        WebElement link = footerNav.findElement(By.linkText(linkText));
        link.click();
        // Wait for page to load
        wait.until(ExpectedConditions.urlContains(getExpectedUrlFragment(linkText)));
    }

    // ─────────────────────────────────────────────
    // FOOTER ABOUT US SECTION STEPS
    // ─────────────────────────────────────────────

    @Then("I should see the heading {string} in the footer")
    public void verifyFooterHeading(String heading) {
        WebElement footer = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.tagName("footer"))
        );
        WebElement headingElement = footer.findElement(By.xpath(".//h3[text()='" + heading + "']"));
        assertTrue("Footer heading '" + heading + "' should be visible", headingElement.isDisplayed());
    }

    @Then("the footer {string} section should contain the text {string}")
    public void verifyFooterSectionText(String sectionName, String expectedText) {
        WebElement footer = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.tagName("footer"))
        );
        // Find the section heading and then its parent container
        WebElement sectionHeading = footer.findElement(By.xpath(".//h3[text()='" + sectionName + "']"));
        WebElement sectionContainer = sectionHeading.findElement(By.xpath("./ancestor::div[@class='footer-section' or @class='footer-about']"));
        
        String sectionText = sectionContainer.getText();
        assertTrue("Footer " + sectionName + " section should contain '" + expectedText + "'", 
                   sectionText.contains(expectedText));
    }

    @When("I click the {string} link in the footer About Us section")
    public void clickLinkInFooterAboutUs(String linkText) {
        WebElement footer = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.tagName("footer"))
        );
        WebElement aboutUsSection = footer.findElement(By.xpath(".//h3[text()='About Us']/ancestor::div[@class='footer-section' or @class='footer-about']"));
        WebElement link = aboutUsSection.findElement(By.linkText(linkText));
        link.click();
    }

    // ─────────────────────────────────────────────
    // FOOTER PAYMENT ICONS STEPS
    // ─────────────────────────────────────────────

    @Then("I should see the payment icon with alt text {string}")
    public void verifyPaymentIcon(String altText) {
        WebElement footer = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.tagName("footer"))
        );
        WebElement paymentIcon = footer.findElement(By.xpath(".//img[@alt='" + altText + "']"));
        assertTrue("Payment icon with alt text '" + altText + "' should be displayed", paymentIcon.isDisplayed());
    }

    // ─────────────────────────────────────────────
    // FOOTER BOTTOM BAR STEPS
    // ─────────────────────────────────────────────

    @Then("I should see the text {string} in the footer bottom bar")
    public void verifyTextInFooterBottomBar(String expectedText) {
        WebElement footerBottom = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.cssSelector("footer .footer-bottom, footer .footer-bar"))
        );
        String footerText = footerBottom.getText();
        assertTrue("Footer bottom bar should contain '" + expectedText + "'", footerText.contains(expectedText));
    }

    @Then("I should see the link {string} in the footer bottom bar")
    public void verifyLinkInFooterBottomBar(String linkText) {
        WebElement footerBottom = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.cssSelector("footer .footer-bottom, footer .footer-bar"))
        );
        WebElement link = footerBottom.findElement(By.linkText(linkText));
        assertTrue("Link '" + linkText + "' should be visible in footer bottom bar", link.isDisplayed());
    }

    @When("I click the {string} link in the footer bottom bar")
    public void clickLinkInFooterBottomBar(String linkText) {
        WebElement footerBottom = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.cssSelector("footer .footer-bottom, footer .footer-bar"))
        );
        WebElement link = footerBottom.findElement(By.linkText(linkText));
        link.click();
        // Wait for page to load
        wait.until(ExpectedConditions.urlContains(getExpectedUrlFragment(linkText)));
    }

    @Then("the link destination should contain {string}")
    public void verifyLinkDestinationContains(String urlFragment) {
        String currentUrl = wait.until(driver -> driver.getCurrentUrl());
        assertTrue("Link destination should contain '" + urlFragment + "'", currentUrl.contains(urlFragment));
    }

    // ─────────────────────────────────────────────
    // HELPER METHODS
    // ─────────────────────────────────────────────

    /**
     * Maps link text to expected URL fragment
     */
    private String getExpectedUrlFragment(String linkText) {
        return switch (linkText) {
            case "Search" -> "/search";
            case "About Us" -> "/pages/about-us";
            case "Shopping Cart by Shopify" -> "shopify.co.uk";
            default -> "/";
        };
    }
}
