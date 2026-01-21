Feature: Login Page Feature

  Background:
    Given user is on the login page

  @SIT
  Scenario: Login with correct credentials
    When user enters username "test@gmail.com"
    And user enters password "password123"
    And user clicks on login button
    Then user gets the title of the page