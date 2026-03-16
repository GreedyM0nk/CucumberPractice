package com.retail.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Footer Page Object
 * 
 * Encapsulates all footer-related elements and interactions:
 * - Footer navigation section
 * - About Us section
 * - Payment method icons
 * - Bottom bar with copyright and links
 */
public class FooterPage extends BasePage {

    // ─────────────────────────────────────────────
    // FOOTER STRUCTURE LOCATORS
    // ─────────────────────────────────────────────
    // Preferred (if available): [data-testid='footer-section'], [data-testid='footer-nav'], etc.

    @FindBy(tagName = "footer")
    private WebElement footerElement;

    @FindBy(css = "[data-testid='footer-nav'], footer nav, footer .footer-navigation")
    private WebElement footerNavigation;

    @FindBy(css = "[data-testid='footer-bottom'], footer .footer-bottom, footer .footer-bar")
    private WebElement footerBottomBar;

    @FindBy(css = "[data-testid='footer-sections'], footer .footer-section, footer .footer-about")
    private List<WebElement> footerSections;

    // ─────────────────────────────────────────────
    // FOOTER NAVIGATION LOCATORS
    // ─────────────────────────────────────────────
    // Preferred: [data-testid='footer-nav-heading'], [data-testid='footer-nav-links']

    @FindBy(css = "[data-testid='footer-nav-heading'], footer nav h3, footer nav h2")
    private WebElement footerNavHeading;

    @FindBy(css = "[data-testid='footer-nav-links'], footer nav a")
    private List<WebElement> footerNavLinks;

    // ─────────────────────────────────────────────
    // ABOUT US SECTION LOCATORS
    // ─────────────────────────────────────────────
    // Preferred: [data-testid='footer-section-heading']

    @FindBy(css = "[data-testid='footer-section-heading'], footer .footer-section h3, footer .footer-about h3, footer h2")
    private List<WebElement> footerSectionHeadings;

    // ─────────────────────────────────────────────
    // PAYMENT ICONS LOCATORS
    // ─────────────────────────────────────────────
    // Preferred: [data-testid='payment-icon']

    @FindBy(css = "[data-testid='payment-icon'], footer .footer-payments img, footer .payment-icons img, footer .payments img")
    private List<WebElement> paymentIcons;

    // ─────────────────────────────────────────────
    // BOTTOM BAR LOCATORS
    // ─────────────────────────────────────────────
    // Preferred: [data-testid='footer-bottom-link']

    @FindBy(css = "[data-testid='footer-bottom-link'], footer .footer-bottom a, footer .footer-bar a")
    private List<WebElement> bottomBarLinks;

    // ─────────────────────────────────────────────
    // CONSTRUCTOR & INITIALIZATION
    // ─────────────────────────────────────────────

    public FooterPage(WebDriver driver) {
        super(driver);  // Initialize BasePage with WebDriver and waits
    }

    // ─────────────────────────────────────────────
    // FOOTER NAVIGATION SECTION METHODS
    // ─────────────────────────────────────────────

    /**
     * Verify footer navigation section heading is visible
     */
    public boolean isFooterNavigationHeadingVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOf(footerNavHeading));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get footer navigation heading text
     */
    public String getFooterNavigationHeadingText() {
        wait.until(ExpectedConditions.visibilityOf(footerNavHeading));
        return footerNavHeading.getText();
    }

    /**
     * Verify link exists in footer navigation
     */
    public boolean isLinkInFooterNavigation(String linkText) {
        return footerNavLinks.stream()
                .anyMatch(link -> link.getText().equalsIgnoreCase(linkText) && link.isDisplayed());
    }

    /**
     * Click link in footer navigation
     */
    public void clickLinkInFooterNavigation(String linkText) {
        WebElement link = footerNavLinks.stream()
                .filter(l -> l.getText().equalsIgnoreCase(linkText))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Link '" + linkText + "' not found in footer navigation"));
        wait.until(ExpectedConditions.elementToBeClickable(link)).click();
    }

    // ─────────────────────────────────────────────
    // ABOUT US SECTION METHODS
    // ─────────────────────────────────────────────

    /**
     * Get footer section by name - finds heading and returns containing section
     */
    public WebElement getFooterSection(String sectionName) {
        try {
            // Try to find heading in footer first
            WebElement footer = driver.findElement(By.cssSelector("footer, [role='contentinfo']"));
            List<WebElement> headings = footer.findElements(By.cssSelector("h2, h3, h4, h5, h6"));
            
            WebElement foundHeading = headings.stream()
                    .filter(h -> h.getText().equalsIgnoreCase(sectionName))
                    .findFirst()
                    .orElse(null);
            
            if (foundHeading != null) {
                // Return the parent section containing this heading
                return foundHeading.findElement(By.xpath("./ancestor::*[contains(@class, 'section') or contains(@class, 'footer-') or contains(@class, 'about')] | ./.."));
            }
            
            // Fallback: search entire page for heading with this text
            List<WebElement> allHeadings = driver.findElements(By.xpath(
                "//h2[normalize-space()='" + sectionName + "'] | "
                + "//h3[normalize-space()='" + sectionName + "'] | "
                + "//h4[contains(normalize-space(), '" + sectionName + "')] | "
                + "//h2[contains(normalize-space(), '" + sectionName + "')] | "
                + "//h3[contains(normalize-space(), '" + sectionName + "')]"
            ));
            
            if (!allHeadings.isEmpty()) {
                WebElement heading = allHeadings.get(0);
                return heading.findElement(By.xpath("./ancestor::div[contains(@class, 'footer')] | ./.."));
            }
            
            throw new RuntimeException("Footer section '" + sectionName + "' not found");
        } catch (Exception e) {
            System.out.println("DEBUG: Footer section '" + sectionName + "' lookup failed: " + e.getMessage());
            throw new RuntimeException("Footer section '" + sectionName + "' not found", e);
        }
    }

    /**
     * Verify footer section contains text
     */
    public boolean footerSectionContainsText(String sectionName, String expectedText) {
        try {
            WebElement section = getFooterSection(sectionName);
            String sectionText = section.getText();
            return sectionText.contains(expectedText);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click link in footer about us section
     */
    public void clickLinkInAboutUsSection(String linkText) {
        WebElement aboutUsSection = getFooterSection("About Us");
        WebElement link = aboutUsSection.findElement(By.linkText(linkText));
        wait.until(ExpectedConditions.elementToBeClickable(link)).click();
    }

    /**
     * Get link href attribute in About Us section
     */
    public String getLinkHrefInAboutUsSection(String linkText) {
        WebElement aboutUsSection = getFooterSection("About Us");
        WebElement link = aboutUsSection.findElement(By.linkText(linkText));
        return link.getAttribute("href");
    }

    // ─────────────────────────────────────────────
    // PAYMENT ICONS METHODS
    // ─────────────────────────────────────────────

    /**
     * Verify payment icon is displayed by alt text
     */
    public boolean isPaymentIconDisplayed(String altText) {
        return paymentIcons.stream()
                .anyMatch(icon -> {
                    String alt = icon.getAttribute("alt");
                    return alt != null && alt.contains(altText) && icon.isDisplayed();
                });
    }

    /**
     * Get all payment icon alt texts
     */
    public List<String> getPaymentIconAltTexts() {
        return paymentIcons.stream()
                .map(icon -> icon.getAttribute("alt"))
                .toList();
    }

    // ─────────────────────────────────────────────
    // FOOTER BOTTOM BAR METHODS
    // ─────────────────────────────────────────────

    /**
     * Get footer bottom bar text - returns empty string if not found
     */
    public String getFooterBottomBarText() {
        try {
            // Try to find footer with more generic selector
            WebElement footer = driver.findElement(By.cssSelector("footer, div[role='contentinfo'], [class*='footer']"));
            wait.until(ExpectedConditions.visibilityOf(footer));
            return footer.getText();
        } catch (Exception e) {
            System.out.println("DEBUG: Footer bottom bar not found: " + e.getMessage());
            return "";
        }
    }

    /**
     * Verify bottom bar contains text
     */
    public boolean bottomBarContainsText(String expectedText) {
        return getFooterBottomBarText().contains(expectedText);
    }

    /**
     * Verify link exists in footer bottom bar
     */
    public boolean isLinkInFooterBottomBar(String linkText) {
        try {
            WebElement footer = driver.findElement(By.cssSelector("footer, div[role='contentinfo']"));
            List<WebElement> links = footer.findElements(By.partialLinkText(linkText));
            return !links.isEmpty() && links.stream().anyMatch(WebElement::isDisplayed);
        } catch (Exception e) {
            System.out.println("DEBUG: Footer link '" + linkText + "' not found");
            return false;
        }
    }

    /**
     * Click link in footer bottom bar
     */
    public void clickLinkInFooterBottomBar(String linkText) {
        try {
            WebElement footer = driver.findElement(By.cssSelector("footer, div[role='contentinfo']"));
            WebElement link = footer.findElement(By.partialLinkText(linkText));
            wait.until(ExpectedConditions.elementToBeClickable(link)).click();
        } catch (Exception e) {
            System.out.println("DEBUG: Could not click footer link '" + linkText + "': " + e.getMessage());
            // Try alternate selectors with robust XPath
            try {
                String xpath = "//a[contains(normalize-space(), '" + linkText + "')] | "
                             + "//a[contains(text(), '" + linkText + "')] | "
                             + "//footer//a[contains(normalize-space(), '" + linkText + "')]";
                WebElement link = driver.findElement(By.xpath(xpath));
                wait.until(ExpectedConditions.elementToBeClickable(link)).click();
            } catch (Exception ex) {
                throw new RuntimeException("Link '" + linkText + "' not found in footer bottom bar");
            }
        }
    }

    /**
     * Get link href attribute in footer bottom bar
     */
    public String getLinkHrefInFooterBottomBar(String linkText) {
        WebElement link = bottomBarLinks.stream()
                .filter(l -> l.getText().equalsIgnoreCase(linkText))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Link '" + linkText + "' not found in footer bottom bar"));
        return link.getAttribute("href");
    }

    // ─────────────────────────────────────────────
    // GENERAL FOOTER METHODS
    // ─────────────────────────────────────────────

    /**
     * Wait for footer to be visible
     */
    public void waitForFooterVisible() {
        wait.until(ExpectedConditions.visibilityOf(footerElement));
    }

    /**
     * Verify footer is visible
     */
    public boolean isFooterVisible() {
        try {
            waitForFooterVisible();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Scroll to footer
     */
    public void scrollToFooter() {
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", footerElement);
    }

    /**
     * Get footer section heading text - returns null if not found
     */
    public String getFooterHeadingText(String heading) {
        try {
            // Use robust XPath with normalize-space() to handle whitespace and formatting variations
            WebElement headingElement = driver.findElement(By.xpath(
                ".//footer//h3[normalize-space()='" + heading + "'] | "
                + ".//footer//h2[normalize-space()='" + heading + "'] | "
                + ".//footer//h4[normalize-space()='" + heading + "'] | "
                + ".//footer//h3[contains(normalize-space(), '" + heading + "')] | "
                + ".//footer//h2[contains(normalize-space(), '" + heading + "')]"
            ));
            return headingElement.getText();
        } catch (Exception e) {
            System.out.println("DEBUG: Footer heading '" + heading + "' not found");
            return null;
        }
    }
}
