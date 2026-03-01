package com.retail.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * CartPage class represents the shopping cart page
 * Contains methods to interact with cart items and perform cart operations
 */
public class CartPage extends BasePage {

    // Page Locators
    @FindBy(css = ".cart-target, #cart-target-desktop, #cart-target-mobile")
    private WebElement cartCount;

    @FindBy(css = "a.toggle-drawer.cart, a[href='/cart']")
    private WebElement cartLink;

    @FindBy(css = "a.checkout, a[href*='checkout']")
    private WebElement checkoutButton;

    @FindBy(css = "#drawer .container, .cart-wrapper")
    private WebElement cartDrawer;

    @FindBy(css = ".empty, .cart-empty")
    private WebElement emptyCartMessage;

    @FindBy(css = ".line-item, .cart-item, tbody tr")
    private List<WebElement> cartItems;

    @FindBy(css = ".item-title, .cart-item-title")
    private WebElement firstItemName;

    @FindBy(css = ".item-price, .cart-item-price")
    private WebElement firstItemPrice;

    @FindBy(css = ".subtotal, .cart-subtotal")
    private WebElement cartSubtotal;

    @FindBy(id = "drawer")
    private WebElement drawerElement;

    // Constructor
    public CartPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Open cart (click on cart link)
     */
    public void openCart() {
        waitForElementToBeClickable(cartLink);
        clickElement(cartLink);
    }

    /**
     * Get cart count
     * @return Cart count as integer
     */
    public int getCartItemCount() {
        try {
            waitForElementToBeVisible(cartCount);
            String countText = getText(cartCount);
            // Extract number from text like "(1)" or "1"
            String numberOnly = countText.replaceAll("[^0-9]", "");
            return numberOnly.isEmpty() ? 0 : Integer.parseInt(numberOnly);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Check if cart is empty
     * @return true if cart is empty
     */
    public boolean isCartEmpty() {
        try {
            return isElementDisplayed(emptyCartMessage);
        } catch (Exception e) {
            return getCartItemCount() == 0;
        }
    }

    /**
     * Get number of items in cart
     * @return Number of cart items
     */
    public int getNumberOfItemsInCart() {
        try {
            return cartItems.size();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Navigate to cart page
     */
    public void navigateToCart() {
        driver.get("https://sauce-demo.myshopify.com/cart");
    }

    /**
     * Proceed to checkout
     */
    public void proceedToCheckout() {
        waitForElementToBeClickable(checkoutButton);
        clickElement(checkoutButton);
    }

    /**
     * Verify cart count is updated
     * @param expectedCount Expected cart count
     * @return true if count matches
     */
    public boolean verifyCartCount(int expectedCount) {
        try {
            // Wait for cart animation to complete
            Thread.sleep(2000);
            return getCartItemCount() == expectedCount;
        } catch (InterruptedException e) {
            return false;
        }
    }

    /**
     * Check if cart drawer is displayed
     * @return true if cart drawer is visible
     */
    public boolean isCartDrawerDisplayed() {
        try {
            return isElementDisplayed(drawerElement);
        } catch (Exception e) {
            return false;
        }
    }
}
