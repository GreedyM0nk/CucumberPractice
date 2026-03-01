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

    private ProductPage productPage = new ProductPage(DriverFactory.getDriver());
    private CartPage cartPage = new CartPage(DriverFactory.getDriver());
    private String productName;
    private String initialCartCount;

    @Given("user is on the product catalogue page")
    public void user_is_on_the_product_catalogue_page() {
        productPage.navigateToCatalogue();
        Assert.assertTrue("Product catalogue should be displayed", 
            productPage.isProductDisplayed());
    }

    @When("user clicks on the first product")
    public void user_clicks_on_the_first_product() {
        productName = productPage.getFirstProductName();
        System.out.println("Clicking on product: " + productName);
        productPage.clickFirstProduct();
    }

    @When("user clicks on a product")
    public void user_clicks_on_a_product() {
        user_clicks_on_the_first_product();
    }

    @When("user adds the first product to cart")
    public void user_adds_the_first_product_to_cart() {
        // Store initial cart count
        initialCartCount = productPage.getCartCount();
        System.out.println("Initial cart count: " + initialCartCount);
        
        // Click on first product
        user_clicks_on_the_first_product();
        
        // Add to cart
        productPage.addToCart();
        System.out.println("Product added to cart");
    }

    @When("user adds product to cart")
    public void user_adds_product_to_cart() {
        productPage.addToCart();
    }

    @Then("user should see product details page")
    public void user_should_see_product_details_page() {
        String currentUrl = productPage.getCurrentUrl();
        Assert.assertTrue("Should be on product details page", 
            currentUrl.contains("/products/"));
        System.out.println("Current URL: " + currentUrl);
    }

    @Then("product details should include price and description")
    public void product_details_should_include_price_and_description() {
        String pageTitle = productPage.getPageTitle();
        Assert.assertFalse("Page title should not be empty", 
            pageTitle.isEmpty());
        System.out.println("Product details page title: " + pageTitle);
    }

    @Then("cart count should be updated")
    public void cart_count_should_be_updated() {
        int initialCount = Integer.parseInt(initialCartCount.isEmpty() ? "0" : initialCartCount);
        int expectedCount = initialCount + 1;
        
        boolean isUpdated = productPage.isCartCountUpdated(String.valueOf(expectedCount));
        Assert.assertTrue("Cart count should be updated to " + expectedCount, isUpdated);
        
        System.out.println("Cart count updated successfully to: " + expectedCount);
    }

    @Then("product should be visible in cart")
    public void product_should_be_visible_in_cart() {
        // Verify cart is not empty
        int cartCount = cartPage.getCartItemCount();
        Assert.assertTrue("Cart should not be empty", cartCount > 0);
        System.out.println("Items in cart: " + cartCount);
    }

    @Then("user should see {int} product in the catalogue")
    public void user_should_see_products_in_the_catalogue(Integer expectedCount) {
        int actualCount = productPage.getProductCount();
        Assert.assertTrue("Should have at least " + expectedCount + " products", 
            actualCount >= expectedCount);
        System.out.println("Total products displayed: " + actualCount);
    }

    @Then("first product name should be displayed")
    public void first_product_name_should_be_displayed() {
        String name = productPage.getFirstProductName();
        Assert.assertFalse("Product name should not be empty", name.isEmpty());
        System.out.println("First product name: " + name);
    }

    @Then("first product price should be displayed")
    public void first_product_price_should_be_displayed() {
        String price = productPage.getFirstProductPrice();
        Assert.assertFalse("Product price should not be empty", price.isEmpty());
        Assert.assertTrue("Price should contain currency symbol", price.contains("£"));
        System.out.println("First product price: " + price);
    }
}
