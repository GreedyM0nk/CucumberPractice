Feature: Authentication - Login and Signup on Sauce Demo Store
  Testing user authentication flows on the Shopify Sauce Demo store
  URL: https://sauce-demo.myshopify.com/

  Background:
    Given user is on the home page

  @Smoke @Authentication @Signup
  Scenario: User signs up with a new account
    When user clicks on sign up link
    And user enters first name for signup
    And user enters last name for signup
    And user enters email for signup
    And user enters password for signup
    And user clicks the create account button
    Then user account should be created successfully
    And user should be logged in

  @Smoke @Authentication @Login
  Scenario: User logs in with existing credentials
    When user clicks on login link
    And user enters email for login
    And user enters password for login
    And user clicks the login button
    Then user should be logged in successfully
    And user profile should be accessible
