@footer @header
Feature: Footer Navigation and Content Verification

  Background:
    Given I am on the Sauce Demo homepage

  # ─────────────────────────────────────────────
  # FOOTER – Footer Navigation Section
  # ─────────────────────────────────────────────

  @S1
  Scenario: Verify footer displays the "Footer" section heading
    Then I should see the heading "Footer" in the footer navigation section

  @S2
  Scenario: Verify footer navigation displays all expected links
    Then I should see the link "Search" in the footer navigation
    And I should see the link "About Us" in the footer navigation

  @S3
  Scenario: Clicking "Search" in the footer navigates to the Search page
    When I click the "Search" link in the footer navigation
    Then the page title should be "Search – Sauce Demo"
    And the URL should contain "/search"

  @S4
  Scenario: Clicking "About Us" in the footer navigates to the About Us page
    When I click the "About Us" link in the footer navigation
    Then the page title should be "About Us – Sauce Demo"
    And the URL should contain "/pages/about-us"

  # ─────────────────────────────────────────────
  # FOOTER – About Us Section
  # ─────────────────────────────────────────────

  @S5
  Scenario: Verify footer displays the "About Us" section heading
    Then I should see the heading "About Us" in the footer

  @S1
  Scenario: Verify footer About Us section contains descriptive text about Sauce Demo
    Then the footer "About Us" section should contain the text "This is a demo site created for"
    And the footer "About Us" section should contain the text "an awesome new way to make your Shopify site social"

  @S2
  Scenario: Clicking the "Sauce" link in the footer About Us section navigates to the Sauce external site
    When I click the "Sauce" link in the footer About Us section
    Then the link destination should be "http://sauceapp.io"

  # ─────────────────────────────────────────────
  # FOOTER – Payment Icons
  # ─────────────────────────────────────────────

  @S3
  Scenario: Verify accepted payment method icons are displayed in the footer
    Then I should see the payment icon with alt text "We accept Amex"
    And I should see the payment icon with alt text "We accept Visa"
    And I should see the payment icon with alt text "We accept Mastercard"

  # ─────────────────────────────────────────────
  # FOOTER – Bottom Bar
  # ─────────────────────────────────────────────

  @S4
  Scenario: Verify footer bottom bar displays the correct copyright text
    Then I should see the text "Copyright © 2026 Sauce Demo." in the footer bottom bar

  @S5
  Scenario: Verify footer bottom bar displays bottom navigation links
    Then I should see the link "Search" in the footer bottom bar
    And I should see the link "About Us" in the footer bottom bar

  @S1
  Scenario: Verify "Shopping Cart by Shopify" link is displayed in the footer bottom bar
    Then I should see the link "Shopping Cart by Shopify" in the footer bottom bar

  @S2
  Scenario: Clicking "Shopping Cart by Shopify" link navigates to the Shopify external page
    When I click the "Shopping Cart by Shopify" link in the footer bottom bar
    Then the link destination should contain "shopify.co.uk/tour/shopping-cart"

  @S3
  Scenario: Clicking "Search" in the footer bottom bar navigates to the Search page
    When I click the "Search" link in the footer bottom bar
    Then the page title should be "Search – Sauce Demo"
    And the URL should contain "/search"

  @S4
  Scenario: Clicking "About Us" in the footer bottom bar navigates to the About Us page
    When I click the "About Us" link in the footer bottom bar
    Then the page title should be "About Us – Sauce Demo"
    And the URL should contain "/pages/about-us"
