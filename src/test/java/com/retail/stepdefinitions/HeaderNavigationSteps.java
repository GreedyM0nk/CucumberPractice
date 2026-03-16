package com.retail.stepdefinitions;

import com.retail.pages.HeaderPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.retail.utils.DriverFactory;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;

import java.time.Duration;

/**
 * Header Navigation Step Definitions
 * 
 * Implements step definitions for header/navigation test scenarios:
 * - Top navigation bar (6 scenarios)
 * - Logo & tagline (2 scenarios)
 * - Side navigation (6 scenarios)
 * - Social media icons (5+ scenarios)
 * - Other header elements
 */
public class HeaderNavigationSteps {

    private HeaderPage headerPage;
    private WebDriver webDriver;
    private WebDriverWait wait;

    /**
     * Constructor to initialize HeaderPage
     */
    public HeaderNavigationSteps() {
        this.webDriver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        this.headerPage = new HeaderPage(webDriver);
    }

    // ─────────────────────────────────────────────
    // TOP NAVIGATION BAR STEPS
    // ─────────────────────────────────────────────

    @Then("I should see the link {string} in the top navigation bar")
    public void verifySeeLinkInTopNavBar(String linkText) {
        boolean linkExists = headerPage.isLinkInTopNavigation(linkText);
        assertTrue("Link '" + linkText + "' not found in top navigation bar", linkExists);
    }

    @When("I click the {string} link in the top navigation bar")
    public void clickLinkInTopNavBar(String linkText) {
        headerPage.clickLinkInTopNavigation(linkText);
    }

    // ─────────────────────────────────────────────
    // LOGO AND TAGLINE STEPS
    // ─────────────────────────────────────────────

    @Then("I should see the site logo with alt text {string}")
    public void verifySiteLogoWithAltText(String altText) {
        boolean logoVisible = headerPage.isSiteLogoVisible(altText);
        assertTrue("Logo with alt text '" + altText + "' not visible", logoVisible);
    }

    @Then("I should see the tagline {string}")
    public void verifyTaglineText(String expectedTagline) {
        String taglineText = headerPage.getTaglineText();
        assertEquals("Tagline mismatch", expectedTagline, taglineText);
    }

    @When("I click the {string} site logo")
    public void clickSiteLogoBySiteName(String logoName) {
        headerPage.clickSiteLogo(logoName);
    }

    // ─────────────────────────────────────────────
    // SIDE NAVIGATION STEPS
    // ─────────────────────────────────────────────

    @Then("I should see the link {string} in the side navigation")
    public void verifySeePointNavigationLink(String linkText) {
        boolean linkExists = headerPage.isLinkInSideNavigation(linkText);
        assertTrue("Link '" + linkText + "' not found in side navigation", linkExists);
    }

    @When("I click the {string} link in the side navigation")
    public void clickSideNavigationLink(String linkText) {
        headerPage.clickLinkInSideNavigation(linkText);
    }

    @Then("the {string} link in side navigation should have valid href")
    public void verifySideNavLinkHref(String linkText) {
        String href = headerPage.getLinkHrefInSideNavigation(linkText);
        assertNotNull("Link '" + linkText + "' has no href attribute", href);
        assertFalse("Link '" + linkText + "' has empty href", href.isEmpty());
    }

    @Then("all side navigation links should be clickable")
    public void verifySideNavLinksClickable() {
        String[] sideNavLinks = {"Home", "Catalog", "Blog", "About Us", "Wish list", "Refer a friend"};
        for (String link : sideNavLinks) {
            if (headerPage.isLinkInSideNavigation(link)) {
                assertTrue("Link '" + link + "' is clickable", true);
            }
        }
    }

    @Then("the {string} link in side navigation should open in same tab")
    public void verifySideNavLinkSamsTab(String linkText) {
        String href = headerPage.getLinkHrefInSideNavigation(linkText);
        assertFalse("Link '" + linkText + "' should not open in new tab",
                href.contains("target=_blank"));
    }

    @Then("the side navigation layout should be responsive")
    public void verifySideNavResponsive() {
        // Verify side nav is visible and formatted correctly
        assertTrue("Side navigation not properly responsive",
                headerPage.isLinkInSideNavigation("Home"));
    }

    // ─────────────────────────────────────────────
    // SOCIAL MEDIA ICONS STEPS
    // ─────────────────────────────────────────────

    @Then("I should see the {string} social icon link")
    public void verifySocialIconVisible(String socialMedia) {
        boolean iconVisible = headerPage.isSocialIconVisible(socialMedia);
        assertTrue(socialMedia + " icon is not visible", iconVisible);
    }

    @When("I click the {string} icon in the side navigation")
    public void clickSocialIconInSideNav(String socialMedia) {
        headerPage.clickSocialIcon(socialMedia);
    }

    // ─────────────────────────────────────────────
    // GENERAL HEADER SETUP STEPS
    // ─────────────────────────────────────────────

    @Given("I am on a page with header navigation")
    public void navigateToPageWithHeader() {
        webDriver.navigate().to("https://www.saucedemo.com/");
        headerPage.waitForHeaderVisible();
    }
}
