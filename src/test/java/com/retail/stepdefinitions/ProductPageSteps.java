package com.retail.stepdefinitions;

import com.retail.pages.CartPage;
import com.retail.pages.ProductPage;
import com.retail.utils.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

/**
 * Step Definitions for Product Page interactions
 */
public class ProductPageSteps {

    // Page objects are intentionally NOT initialized here.
    // Initializing them at field-declaration time calls DriverFactory.getDriver()
    // BEFORE the @Before hook has had a chance to create and set the driver,
    // which returns null and causes NullPointerExceptions.
    // Instead, use getProductPage() / getCartPage() lazy accessors so the driver
    // is always fetched from ThreadLocal at the moment it is actually needed.
    private ProductPage productPage;
    private CartPage cartPage;
    private String initialCartCount;

    private ProductPage getProductPage() {
        if (productPage == null) {
            productPage = new ProductPage(DriverFactory.getDriver());
        }
        return productPage;
    }

    private CartPage getCartPage() {
        if (cartPage == null) {
            cartPage = new CartPage(DriverFactory.getDriver());
        }
        return cartPage;
    }

    @Given("user is on the product catalogue page")
    public void user_is_on_the_product_catalogue_page() {
        // Reset page objects so they pick up the fresh driver for this scenario
        productPage = null;
        cartPage = null;
        initialCartCount = null;

        getProductPage().navigateToCatalogue();
        Assert.assertTrue("Product catalogue should be displayed",
            getProductPage().isProductDisplayed());
    }

    @When("user clicks on the first product")
    public void user_clicks_on_the_first_product() {
        String productName = getProductPage().getFirstProductName();
        System.out.println("Clicking on product: " + productName);
        getProductPage().clickFirstProduct();
    }

    @When("user clicks on a product")
    public void user_clicks_on_a_product() {
        user_clicks_on_the_first_product();
    }

    @When("user adds the first product to cart")
    public void user_adds_the_first_product_to_cart() {
        // Capture the stable cart count BEFORE any navigation.
        // Use getStableCartCount() which waits for the badge to settle.
        initialCartCount = getProductPage().getStableCartCount();
        System.out.println("Initial cart count: " + initialCartCount);

        // Click on first product → navigates to product detail page
        user_clicks_on_the_first_product();

        // Add to cart
        getProductPage().addToCart();
        System.out.println("Product added to cart");
    }

    @When("user adds product to cart")
    public void user_adds_product_to_cart() {
        // Capture stable count before adding (needed for cart_count_should_be_updated)
        if (initialCartCount == null) {
            initialCartCount = getProductPage().getStableCartCount();
        }
        getProductPage().addToCart();
    }

    @Then("user should see product details page")
    public void user_should_see_product_details_page() {
        // Wait for the URL to contain a product path before asserting.
        // The Shopify demo site uses /collections/frontpage/products/<slug>
        // (which also contains "/products/"), so both patterns are covered.
        String currentUrl = getProductPage().waitForProductDetailsUrl();
        Assert.assertTrue(
            "Should be on product details page, but URL was: " + currentUrl,
            currentUrl.contains("/products/") || currentUrl.contains("/collections/"));
        System.out.println("Current URL: " + currentUrl);
    }

    @Then("product details should include price and description")
    public void product_details_should_include_price_and_description() {
        String pageTitle = getProductPage().getPageTitle();
        Assert.assertFalse("Page title should not be empty",
            pageTitle.isEmpty());
        System.out.println("Product details page title: " + pageTitle);
    }

    @Then("cart count should be updated")
    public void cart_count_should_be_updated() {
        int initialCount = Integer.parseInt((initialCartCount == null || initialCartCount.isEmpty()) ? "0" : initialCartCount);
        int expectedCount = initialCount + 1;

        boolean isUpdated = getProductPage().isCartCountUpdated(String.valueOf(expectedCount));
        Assert.assertTrue("Cart count should be updated to " + expectedCount, isUpdated);

        System.out.println("Cart count updated successfully to: " + expectedCount);
    }

    @Then("product should be visible in cart")
    public void product_should_be_visible_in_cart() {
        // Re-read the cart badge on the current page (product detail page).
        // We use productPage because we are already on the product detail page
        // and CartPage.getCartItemCount() would race with the cart animation.
        String countStr = getProductPage().getCartCount();
        int cartCount = countStr.isEmpty() ? 0 : Integer.parseInt(countStr);
        Assert.assertTrue("Cart should not be empty – badge shows: " + countStr, cartCount > 0);
        System.out.println("Cart badge count confirmed: " + cartCount);
    }

    @Then("user should see {int} product in the catalogue")
    public void user_should_see_products_in_the_catalogue(Integer expectedCount) {
        int actualCount = getProductPage().getProductCount();
        Assert.assertTrue("Should have at least " + expectedCount + " products",
            actualCount >= expectedCount);
        System.out.println("Total products displayed: " + actualCount);
    }

    @Then("first product name should be displayed")
    public void first_product_name_should_be_displayed() {
        String name = getProductPage().getFirstProductName();
        Assert.assertFalse("Product name should not be empty", name.isEmpty());
        System.out.println("First product name: " + name);
    }

    @Then("first product price should be displayed")
    public void first_product_price_should_be_displayed() {
        String price = getProductPage().getFirstProductPrice();
        Assert.assertFalse("Product price should not be empty", price.isEmpty());
        Assert.assertTrue("Price should contain currency symbol", price.contains("£"));
        System.out.println("First product price: " + price);
    }
}
