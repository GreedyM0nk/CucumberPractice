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
public class FooterPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // ─────────────────────────────────────────────
    // FOOTER STRUCTURE LOCATORS
    // ─────────────────────────────────────────────

    @FindBy(tagName = "footer")
    private WebElement footerElement;

    @FindBy(css = "footer nav, footer .footer-navigation")
    private WebElement footerNavigation;

    @FindBy(css = "footer .footer-bottom, footer .footer-bar")
    private WebElement footerBottomBar;

    @FindBy(css = "footer .footer-section, footer .footer-about")
    private List<WebElement> footerSections;

    // ─────────────────────────────────────────────
    // FOOTER NAVIGATION LOCATORS
    // ─────────────────────────────────────────────

    @FindBy(css = "footer nav h3")
    private WebElement footerNavHeading;

    @FindBy(css = "footer nav a")
    private List<WebElement> footerNavLinks;

    // ─────────────────────────────────────────────
    // ABOUT US SECTION LOCATORS
    // ─────────────────────────────────────────────

    @FindBy(css = "footer .footer-section h3, footer .footer-about h3")
    private List<WebElement> footerSectionHeadings;

    // ─────────────────────────────────────────────
    // PAYMENT ICONS LOCATORS
    // ─────────────────────────────────────────────

    @FindBy(css = "footer .footer-payments img, footer .payment-icons img")
    private List<WebElement> paymentIcons;

    // ─────────────────────────────────────────────
    // BOTTOM BAR LOCATORS
    // ─────────────────────────────────────────────

    @FindBy(css = "footer .footer-bottom a, footer .footer-bar a")
    private List<WebElement> bottomBarLinks;

    // ─────────────────────────────────────────────
    // CONSTRUCTOR & INITIALIZATION
    // ─────────────────────────────────────────────

    public FooterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
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
     * Get footer section by name
     */
    public WebElement getFooterSection(String sectionName) {
        return footerSectionHeadings.stream()
                .filter(heading -> heading.getText().equalsIgnoreCase(sectionName))
                .map(heading -> heading.findElement(By.xpath("./ancestor::div[@class='footer-section' or @class='footer-about']")))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Footer section '" + sectionName + "' not found"));
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
     * Get footer bottom bar text
     */
    public String getFooterBottomBarText() {
        wait.until(ExpectedConditions.visibilityOf(footerBottomBar));
        return footerBottomBar.getText();
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
        return bottomBarLinks.stream()
                .anyMatch(link -> link.getText().equalsIgnoreCase(linkText) && link.isDisplayed());
    }

    /**
     * Click link in footer bottom bar
     */
    public void clickLinkInFooterBottomBar(String linkText) {
        WebElement link = bottomBarLinks.stream()
                .filter(l -> l.getText().equalsIgnoreCase(linkText))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Link '" + linkText + "' not found in footer bottom bar"));
        wait.until(ExpectedConditions.elementToBeClickable(link)).click();
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
}
