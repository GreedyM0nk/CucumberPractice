package com.retail.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Header Page Object
 * 
 * Encapsulates all header-related elements and interactions:
 * - Top navigation bar links
 * - Site logo and tagline
 * - Side navigation menu
 * - Social media icons
 */
public class HeaderPage extends BasePage {

    // ─────────────────────────────────────────────
    // TOP NAVIGATION BAR LOCATORS
    // ─────────────────────────────────────────────

    @FindBy(css = "nav, header nav, .header-nav")
    private WebElement topNavigation;

    @FindBy(css = "header a, nav a")
    private List<WebElement> topNavLinks;

    // ─────────────────────────────────────────────
    // SITE LOGO AND TAGLINE LOCATORS
    // ─────────────────────────────────────────────

    @FindBy(css = "header .logo img, .site-logo img, header img[alt*='logo'], header img[alt*='Sauce']")
    private WebElement siteLogo;

    @FindBy(css = ".tagline, .site-tagline, header .tagline, h1 + p")
    private WebElement tagline;

    // ─────────────────────────────────────────────
    // SIDE NAVIGATION LOCATORS
    // ─────────────────────────────────────────────

    @FindBy(css = ".sidebar, .side-nav, .left-menu, aside nav")
    private WebElement sideNavigation;

    @FindBy(css = ".sidebar a, .side-nav a, .left-menu a, aside nav a")
    private List<WebElement> sideNavLinks;

    // ─────────────────────────────────────────────
    // SOCIAL ICONS LOCATORS
    // ─────────────────────────────────────────────

    @FindBy(css = "a[href*='facebook.com'], .social-icon.facebook, a .fa-facebook")
    private WebElement facebookIcon;

    @FindBy(css = "a[href*='twitter.com'], .social-icon.twitter, a .fa-twitter")
    private WebElement twitterIcon;

    @FindBy(css = "a[href*='instagram.com'], .social-icon.instagram, a .fa-instagram")
    private WebElement instagramIcon;

    @FindBy(css = "a[href*='pinterest.com'], .social-icon.pinterest, a .fa-pinterest")
    private WebElement pinterestIcon;

    @FindBy(css = "a[href*='feed'], a[href*='rss'], .social-icon.rss, a .fa-rss, [role='link'][aria-label*='RSS'], a[title*='RSS']")
    private WebElement rssIcon;

    // ─────────────────────────────────────────────
    // CONSTRUCTOR & INITIALIZATION
    // ─────────────────────────────────────────────

    public HeaderPage(WebDriver driver) {
        super(driver);  // Initialize BasePage with WebDriver and waits
    }

    // ─────────────────────────────────────────────
    // TOP NAVIGATION BAR METHODS
    // ─────────────────────────────────────────────

    /**
     * Check if link exists in top navigation bar
     */
    public boolean isLinkInTopNavigation(String linkText) {
        try {
            wait.until(ExpectedConditions.visibilityOf(topNavigation));
            return topNavLinks.stream()
                    .anyMatch(link -> link.getText().equalsIgnoreCase(linkText) && link.isDisplayed());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click link in top navigation bar
     */
    public void clickLinkInTopNavigation(String linkText) {
        WebElement link = topNavLinks.stream()
                .filter(l -> l.getText().equalsIgnoreCase(linkText))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Link '" + linkText + "' not found in top navigation"));
        wait.until(ExpectedConditions.elementToBeClickable(link)).click();
    }

    // ─────────────────────────────────────────────
    // SITE LOGO AND TAGLINE METHODS
    // ─────────────────────────────────────────────

    /**
     * Verify site logo is visible
     */
    public boolean isSiteLogoVisible(String altText) {
        try {
            WebElement logo = driver.findElement(By.xpath("//img[@alt='" + altText + "']"));
            return wait.until(ExpectedConditions.visibilityOf(logo)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get site logo alt text
     */
    public String getSiteLogoAltText() {
        return siteLogo.getAttribute("alt");
    }

    /**
     * Verify tagline is visible
     */
    public boolean isTaglineVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOf(tagline));
            return true;
        } catch (Exception e) {
            System.out.println("DEBUG: Tagline element not found: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get tagline text - returns null if not found
     */
    public String getTaglineText() {
        try {
            WebElement taglineElement = driver.findElement(By.cssSelector(".tagline, .site-tagline, header .tagline, h1 + p"));
            return taglineElement.getText();
        } catch (Exception e) {
            System.out.println("DEBUG: Tagline not found, trying alternate selectors");
            try {
                // Try to find any paragraph in header
                WebElement headerParagraph = driver.findElement(By.xpath("//header//p | //header//span[@class*='tagline']"));
                return headerParagraph.getText();
            } catch (Exception ex) {
                System.out.println("DEBUG: Tagline element not available on this page");
                return null;
            }
        }
    }

    /**
     * Click site logo
     */
    public void clickSiteLogo(String logoName) {
        WebElement logo = driver.findElement(By.xpath("//img[@alt='" + logoName + "']"));
        wait.until(ExpectedConditions.elementToBeClickable(logo)).click();
    }

    // ─────────────────────────────────────────────
    // SIDE NAVIGATION METHODS
    // ─────────────────────────────────────────────

    /**
     * Check if link exists in side navigation
     */
    public boolean isLinkInSideNavigation(String linkText) {
        try {
            // Special case for Search - might be an input field
            if (linkText.equalsIgnoreCase("search")) {
                List<WebElement> searchInputs = driver.findElements(By.xpath(
                    "//input[contains(@placeholder, 'search') or contains(@placeholder, 'Search') or @name='q' or @type='search'] | //button[contains(text(), 'Search')] | //a[contains(text(), 'Search')]"
                ));
                return !searchInputs.isEmpty() && searchInputs.stream().anyMatch(WebElement::isDisplayed);
            }
            
            // Try to find using multiple selectors
            List<WebElement> links = driver.findElements(By.xpath(
                ".//nav//a[contains(text(), '" + linkText + "')] | .//aside//a[contains(text(), '" + linkText + "')] | .//a[contains(text(), '" + linkText + "')] | .//button[contains(text(), '" + linkText + "')]"
            ));
            if (!links.isEmpty()) {
                return links.stream().anyMatch(WebElement::isDisplayed);
            }
            return false;
        } catch (Exception e) {
            System.out.println("DEBUG: Side navigation link '" + linkText + "' not found");
            return false;
        }
    }

    /**
     * Click link in side navigation
     */
    public void clickLinkInSideNavigation(String linkText) {
        try {
            WebElement link = driver.findElement(By.xpath(
                ".//nav//a[contains(text(), '" + linkText + "')] | .//aside//a[contains(text(), '" + linkText + "')] | .//a[contains(text(), '" + linkText + "')]"
            ));
            wait.until(ExpectedConditions.elementToBeClickable(link)).click();
        } catch (Exception e) {
            System.out.println("DEBUG: Could not click link '" + linkText + "' in side navigation: " + e.getMessage());
            throw new RuntimeException("Link '" + linkText + "' not found in side navigation");
        }
    }

    /**
     * Get link href in side navigation
     */
    public String getLinkHrefInSideNavigation(String linkText) {
        try {
            WebElement link = driver.findElement(By.xpath(
                ".//nav//a[contains(text(), '" + linkText + "')] | .//aside//a[contains(text(), '" + linkText + "')] | .//a[contains(text(), '" + linkText + "')]"
            ));
            return link.getAttribute("href");
        } catch (Exception e) {
            System.out.println("DEBUG: Link '" + linkText + "' href not found");
            return null;
        }
    }

    // ─────────────────────────────────────────────
    // SOCIAL ICONS METHODS
    // ─────────────────────────────────────────────

    /**
     * Check if social icon is visible
     */
    public boolean isSocialIconVisible(String socialMedia) {
        try {
            WebElement icon = getSocialIconByName(socialMedia);
            wait.until(ExpectedConditions.visibilityOf(icon));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click social media icon and handle external links
     */
    public void clickSocialIcon(String socialMedia) {
        try {
            WebElement icon = getSocialIconByName(socialMedia);
            wait.until(ExpectedConditions.elementToBeClickable(icon));
            String href = getSocialIconHref(socialMedia);
            
            if (href != null && !href.isEmpty() && 
                (href.contains("facebook.com") || href.contains("twitter.com") || 
                 href.contains("instagram.com") || href.contains("pinterest.com"))) {
                ((JavascriptExecutor) driver).executeScript("window.location.href = '" + href + "';");
            } else {
                icon.click();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to click " + socialMedia + " icon", e);
        }
    }

    /**
     * Get social icon href/url
     */
    public String getSocialIconHref(String socialMedia) {
        WebElement icon = getSocialIconByName(socialMedia);
        if (icon.getTagName().equals("a")) {
            return icon.getAttribute("href");
        } else {
            return icon.findElement(By.xpath("./..")).getAttribute("href");
        }
    }

    /**
     * Helper to get social icon by name
     */
    private WebElement getSocialIconByName(String socialMedia) {
        return switch (socialMedia.toLowerCase()) {
            case "facebook" -> facebookIcon;
            case "twitter" -> twitterIcon;
            case "instagram" -> instagramIcon;
            case "pinterest" -> pinterestIcon;
            case "rss" -> rssIcon;
            default -> throw new RuntimeException("Unknown social media: " + socialMedia);
        };
    }

    // ─────────────────────────────────────────────
    // GENERAL HEADER METHODS
    // ─────────────────────────────────────────────

    /**
     * Scroll to header (top of page)
     */
    public void scrollToHeader() {
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, 0);");
    }

    /**
     * Wait for header to be visible
     */
    public void waitForHeaderVisible() {
        wait.until(ExpectedConditions.visibilityOfAllElements(topNavigation, sideNavigation));
    }
}
