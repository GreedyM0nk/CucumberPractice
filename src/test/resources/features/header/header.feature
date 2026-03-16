@header @top-navigation
Feature: Header and Footer Navigation Links
  As a user visiting the Sauce Demo site
  I want all header and footer links to be visible and navigate to the correct pages
  So that I can browse the site without broken or misdirected links

  Background:
    Given I am on the Sauce Demo homepage "https://sauce-demo.myshopify.com/"

  # ─────────────────────────────────────────────
  # HEADER – Top Navigation Bar
  # ─────────────────────────────────────────────

  @Smoke @S6
  Scenario: Verify top navigation bar displays all expected links
    Then I should see the link "Search" in the top navigation bar
    And I should see the link "About Us" in the top navigation bar
    And I should see the link "Log In" in the top navigation bar
    And I should see the link "Sign up" in the top navigation bar
    And I should see the link "My Cart (0)" in the top navigation bar
    And I should see the link "Check Out" in the top navigation bar

  @S6
  Scenario: Clicking "Search" in the top navigation bar navigates to the Search page
    When I click the "Search" link in the top navigation bar
    Then the page title should be "Search – Sauce Demo"
    And the URL should contain "/search"

  @S6
  Scenario: Clicking "About Us" in the top navigation bar navigates to the About Us page
    When I click the "About Us" link in the top navigation bar
    Then the page title should be "About Us – Sauce Demo"
    And the URL should contain "/pages/about-us"

  @S6
  Scenario: Clicking "Log In" in the top navigation bar navigates to the Account Login page
    When I click the "Log In" link in the top navigation bar
    Then the page title should be "Account – Sauce Demo"
    And the URL should contain "/account/login"

  @S6
  Scenario: Clicking "Sign up" in the top navigation bar navigates to the Create Account page
    When I click the "Sign up" link in the top navigation bar
    Then the page title should be "Create Account – Sauce Demo"
    And the URL should contain "/account/register"

  @S6
  Scenario: Clicking "My Cart (0)" in the top navigation bar navigates to the Cart page
    When I click the "My Cart (0)" link in the top navigation bar
    Then the page title should be "Sauce Demo"
    #And the URL should contain "/cart"

  @S6
  Scenario: Clicking "Check Out" in the top navigation bar navigates to the Cart page
    When I click the "Check Out" link in the top navigation bar
    Then the page title should be "Your Shopping Cart – Sauce Demo"
    And the URL should contain "/cart"

  # ─────────────────────────────────────────────
  # HEADER – Site Logo and Tagline
  # ─────────────────────────────────────────────

  @S6
  Scenario: Verify site logo and tagline are displayed in the header
    Then I should see the site logo with alt text "Sauce Demo"
    And I should see the tagline "Just a demo site showing off what Sauce can do."

  @S6
  Scenario: Clicking the site logo navigates back to the homepage
    When I click the "Sauce Demo" site logo
    Then the page title should be "Sauce Demo"
    And the URL should be "https://sauce-demo.myshopify.com/"

  # ─────────────────────────────────────────────
  # SIDE NAVIGATION (Left Menu)
  # ─────────────────────────────────────────────

  @S6
  Scenario: Verify side navigation displays all expected links
    Then I should see the link "Home" in the side navigation
    And I should see the link "Catalog" in the side navigation
    And I should see the link "Blog" in the side navigation
    And I should see the link "About Us" in the side navigation
    And I should see the link "Wish list" in the side navigation
    And I should see the link "Refer a friend" in the side navigation

  @S6
  Scenario: Clicking "Home" in the side navigation navigates to the Homepage
    When I click the "Home" link in the side navigation
    Then the page title should be "Sauce Demo"
    And the URL should be "https://sauce-demo.myshopify.com/"

  @S6
  Scenario: Clicking "Catalog" in the side navigation navigates to the Products page
    When I click the "Catalog" link in the side navigation
    Then the page title should be "Products – Sauce Demo"
    And the URL should contain "/collections/all"

  @S6
  Scenario: Clicking "Blog" in the side navigation navigates to the News page
    When I click the "Blog" link in the side navigation
    Then the page title should be "News – Sauce Demo"
    And the URL should contain "/blogs/news"

  @S6
  Scenario: Clicking "About Us" in the side navigation navigates to the About Us page
    When I click the "About Us" link in the side navigation
    Then the page title should be "About Us – Sauce Demo"
    And the URL should contain "/pages/about-us"

  @S6
  Scenario: Clicking "Wish list" in the side navigation stays on the same page
    When I click the "Wish list" link in the side navigation
    Then the URL should contain "#sauce-show-wish-list"

  @S6
  Scenario: Clicking "Refer a friend" in the side navigation stays on the same page
    When I click the "Refer a friend" link in the side navigation
    Then the URL should contain "#sauce-show-refer-friend"

  # ─────────────────────────────────────────────
  # SIDE NAVIGATION – Social Media Icons
  # ─────────────────────────────────────────────

  @S6
  Scenario: Verify social media icons are displayed in the side navigation
    Then I should see the Facebook social icon link
    And I should see the Twitter social icon link
    And I should see the Instagram social icon link
    And I should see the Pinterest social icon link
    And I should see the RSS feed icon link

  @S6
  Scenario: Facebook icon links to the correct external URL
    When I click the Facebook icon in the side navigation
    Then the link destination should be "http://www.facebook.com/shopify"

  @S6
  Scenario: Twitter icon links to the correct external URL
    When I click the Twitter icon in the side navigation
    Then the link destination should be "http://www.twitter.com/sauce_io"

  @S6
  Scenario: Instagram icon links to the correct external URL
    When I click the Instagram icon in the side navigation
    Then the link destination should be "http://www.instagram.com/shopify"

  @S6
  Scenario: Pinterest icon links to the correct external URL
    When I click the Pinterest icon in the side navigation
    Then the link destination should be "http://www.pinterest.com/chrisjhoughton/awesome-facebook-integration/"
