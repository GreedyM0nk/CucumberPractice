Feature: Add Product to Cart

  @Smoke @Product @Cart
  Scenario: Add first product to cart from catalogue page
    Given user is on the product catalogue page
    When user adds the first product to cart
    Then cart count should be updated
    And product should be visible in cart

  @Regression @Product
  Scenario: View first product details
    Given user is on the product catalogue page
    When user clicks on the first product
    Then user should see product details page
    And product details should include price and description

  @Product
  Scenario: Verify products are displayed on catalogue page
    Given user is on the product catalogue page
    Then user should see 3 product in the catalogue
    And first product name should be displayed
    And first product price should be displayed

  @Smoke @Product @Cart
  Scenario: Add product to cart from product details page
    Given user is on the product catalogue page
    When user clicks on the first product
    And user adds product to cart
    Then cart count should be updated
