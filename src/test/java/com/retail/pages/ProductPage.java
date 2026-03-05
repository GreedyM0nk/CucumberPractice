package com.retail.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * ProductPage class represents the product catalogue page
 * Contains methods to interact with products and add them to cart
 */
public class ProductPage extends BasePage {

    // Page Locators
    @FindBy(css = "a[id^='product-']")
    private List<WebElement> productLinks;

    @FindBy(id = "product-1")
    private WebElement firstProduct;

    @FindBy(xpath = "//a[@id='product-1']//h3")
    private WebElement firstProductName;

    @FindBy(xpath = "//a[@id='product-1']//h4")
    private WebElement firstProductPrice;

    @FindBy(css = "input[type='submit'][value='Add to Cart'], button[name='add']")
    private WebElement addToCartButton;

    @FindBy(css = ".cart-target, #cart-target-desktop")
    private WebElement cartCount;

    @FindBy(id = "cart-animation")
    private WebElement cartAnimation;

    @FindBy(css = "select#product-select, select[name='id']")
    private WebElement productVariantSelect;

    // Constructor
    public ProductPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigate to the product catalogue (home) page.
     * URL is read from the active environment profile via DriverFactory — never hardcoded.
     */
    public void navigateToCatalogue() {
        driver.get(com.retail.utils.DriverFactory.getBaseUrl());
    }

    /**
     * Get the total number of products displayed
     * @return Number of products
     */
    public int getProductCount() {
        return productLinks.size();
    }

    /**
     * Click on the first product
     */
    public void clickFirstProduct() {
        waitForElementToBeClickable(firstProduct);
        scrollToElement(firstProduct);
        clickElement(firstProduct);
    }

    /**
     * Get the name of the first product
     * @return Product name
     */
    public String getFirstProductName() {
        waitForElementToBeVisible(firstProductName);
        return getText(firstProductName);
    }

    /**
     * Get the price of the first product
     * @return Product price
     */
    public String getFirstProductPrice() {
        waitForElementToBeVisible(firstProductPrice);
        return getText(firstProductPrice);
    }

    /**
     * Add product to cart from product details page
     */
    public void addToCart() {
        waitForElementToBeClickable(addToCartButton);
        clickElement(addToCartButton);
    }

    /**
     * Get the cart count, waiting for the badge element to be stable first.
     * Unlike getCartCount(), this explicitly waits up to 5s for the element
     * to be visible before reading — prevents reading mid-animation empty text.
     * @return Cart count as string (empty string if badge not visible)
     */
    public String getStableCartCount() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement badge = shortWait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    org.openqa.selenium.By.cssSelector(".cart-target, #cart-target-desktop")));
            String countText = badge.getText().replaceAll("[^0-9]", "");
            return countText.isEmpty() ? "0" : countText;
        } catch (Exception e) {
            // Badge not visible = cart is empty
            return "0";
        }
    }

    /**
     * Get the cart count
     * @return Cart count as string
     */
    public String getCartCount() {
        try {
            waitForElementToBeVisible(cartCount);
            String countText = getText(cartCount);
            // Extract number from text like "(1)" or "1"
            return countText.replaceAll("[^0-9]", "");
        } catch (Exception e) {
            return "0";
        }
    }

    /**
     * Check if cart count is updated
     * @param expectedCount Expected count
     * @return true if cart count matches expected count
     */
    public boolean isCartCountUpdated(String expectedCount) {
        try {
            // Use 10s wait to match the default implicit wait timeout
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(10));
            shortWait.until(ExpectedConditions.textMatches(
                    org.openqa.selenium.By.cssSelector(".cart-target, #cart-target-desktop"),
                    java.util.regex.Pattern.compile(".*" + java.util.regex.Pattern.quote(expectedCount) + ".*")
            ));
            return true;
        } catch (Exception e) {
            return false;
        }
    }



    /**
     * Check if product is displayed on catalogue page
     * @return true if at least one product is displayed
     */
    public boolean isProductDisplayed() {
        return productLinks.size() > 0 && isElementDisplayed(firstProduct);
    }
}
