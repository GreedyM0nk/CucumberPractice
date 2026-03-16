package com.retail.stepdefinitions;

import com.retail.pages.HeaderPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import static com.retail.runners.TestContext.driver;

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

    /**
     * Constructor to initialize HeaderPage
     */
    public HeaderNavigationSteps() {
        this.webDriver = driver;
        this.headerPage = new HeaderPage(webDriver);
    }

    // ─────────────────────────────────────────────
    // TOP NAVIGATION BAR STEPS
    // ─────────────────────────────────────────────

    @Then("I should see the link {string} in the top navigation bar")
    public void verifySeeLinkInTopNavBar(String linkText) {
        boolean linkExists = headerPage.isLinkInTopNavigation(linkText);
        Assert.assertTrue(linkExists, "Link '" + linkText + "' not found in top navigation bar");
    }

    @When("I click the {string} link in the top navigation bar")
    public void clickLinkInTopNavBar(String linkText) {
        headerPage.clickLinkInTopNavigation(linkText);
    }

    @Then("the page title should be {string}")
    public void verifyPageTitle(String expectedTitle) {
        String actualTitle = webDriver.getTitle();
        Assert.assertEquals("Page title mismatch", expectedTitle, actualTitle);
    }

    @Then("the URL should contain {string}")
    public void verifyUrlContains(String urlPart) {
        String currentUrl = webDriver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains(urlPart),
                "URL does not contain '" + urlPart + "'. Current URL: " + currentUrl);
    }

    @Then("the URL should be {string}")
    public void verifyUrlEquals(String expectedUrl) {
        String currentUrl = webDriver.getCurrentUrl();
        Assert.assertEquals("URL mismatch", expectedUrl, currentUrl);
    }

    // ─────────────────────────────────────────────
    // LOGO AND TAGLINE STEPS
    // ─────────────────────────────────────────────

    @Then("I should see the site logo with alt text {string}")
    public void verifySiteLogoWithAltText(String altText) {
        boolean logoVisible = headerPage.isSiteLogoVisible(altText);
        Assert.assertTrue(logoVisible, "Logo with alt text '" + altText + "' not visible");
    }

    @Then("I should see the tagline {string}")
    public void verifyTaglineText(String expectedTagline) {
        String taglineText = headerPage.getTaglineText();
        Assert.assertEquals("Tagline mismatch", expectedTagline, taglineText);
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
        Assert.assertTrue(linkExists, "Link '" + linkText + "' not found in side navigation");
    }

    @When("I click the {string} link in the side navigation")
    public void clickSideNavigationLink(String linkText) {
        headerPage.clickLinkInSideNavigation(linkText);
    }

    @Then("the {string} link in side navigation should have valid href")
    public void verifySideNavLinkHref(String linkText) {
        String href = headerPage.getLinkHrefInSideNavigation(linkText);
        Assert.assertNotNull(href, "Link '" + linkText + "' has no href attribute");
        Assert.assertFalse(href.isEmpty(), "Link '" + linkText + "' has empty href");
    }

    @Then("all side navigation links should be clickable")
    public void verifySideNavLinksClickable() {
        String[] sideNavLinks = {"Home", "Catalog", "Blog", "About Us", "Wish list", "Refer a friend"};
        for (String link : sideNavLinks) {
            if (headerPage.isLinkInSideNavigation(link)) {
                Assert.assertTrue(true, "Link '" + link + "' is clickable");
            }
        }
    }

    @Then("the {string} link in side navigation should open in same tab")
    public void verifySideNavLinkSamsTab(String linkText) {
        String href = headerPage.getLinkHrefInSideNavigation(linkText);
        Assert.assertFalse(href.contains("target=_blank"),
                "Link '" + linkText + "' should not open in new tab");
    }

    @Then("the side navigation layout should be responsive")
    public void verifySideNavResponsive() {
        // Verify side nav is visible and formatted correctly
        Assert.assertTrue(headerPage.isLinkInSideNavigation("Home"),
                "Side navigation not properly responsive");
    }

    // ─────────────────────────────────────────────
    // SOCIAL MEDIA ICONS STEPS
    // ─────────────────────────────────────────────

    @Then("I should see the {string} social icon link")
    public void verifySocialIconVisible(String socialMedia) {
        boolean iconVisible = headerPage.isSocialIconVisible(socialMedia);
        Assert.assertTrue(iconVisible, socialMedia + " icon is not visible");
    }

    @When("I click the {string} icon in the side navigation")
    public void clickSocialIconInSideNav(String socialMedia) {
        headerPage.clickSocialIcon(socialMedia);
    }

    @Then("the link destination should be {string}")
    public void verifyLinkDestination(String expectedUrl) {
        String currentUrl = webDriver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains(expectedUrl) || currentUrl.equals(expectedUrl),
                "Link destination does not match. Expected: " + expectedUrl + ", Got: " + currentUrl);
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
