package com.retail.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * LoginPage class represents the authentication pages (login/signup)
 * Contains methods to interact with login and signup forms
 * Locators are specific to Shopify Sauce Demo store: sauce-demo.myshopify.com
 */
public class LoginPage extends BasePage {

    // ─────────────────────────────────────────────
    // ACCOUNT NAVIGATION LOCATORS
    // ─────────────────────────────────────────────
    // Preferred: [data-testid='account-login-link'], [data-testid='account-signup-link']

    @FindBy(css = "[data-testid='account-login-link'], a[href='/account/login']")
    private WebElement loginLink;

    @FindBy(css = "[data-testid='account-signup-link'], a[href='/account/register']")
    private WebElement signupLink;

    // ─────────────────────────────────────────────
    // LOGIN FORM LOCATORS
    // ─────────────────────────────────────────────
    // Preferred: [data-testid='login-email'], [data-testid='login-password'], [data-testid='login-button']

    @FindBy(name = "customer[email]")
    private WebElement loginEmailInput;

    @FindBy(name = "customer[password]")
    private WebElement loginPasswordInput;

    @FindBy(css = "[data-testid='login-button'], input[type='submit'][value='Sign In']")
    private WebElement loginButton;

    // ─────────────────────────────────────────────
    // SIGNUP FORM LOCATORS
    // ─────────────────────────────────────────────
    // Preferred: [data-testid='signup-firstname'], [data-testid='signup-lastname'], [data-testid='signup-email'], etc.

    @FindBy(name = "customer[first_name]")
    private WebElement signupFirstNameInput;

    @FindBy(name = "customer[last_name]")
    private WebElement signupLastNameInput;

    @FindBy(name = "customer[email]")
    private WebElement signupEmailInput;

    @FindBy(name = "customer[password]")
    private WebElement signupPasswordInput;

    @FindBy(css = "[data-testid='create-account-button'], input[type='submit'][value='Create']")
    private WebElement createAccountButton;

    // ─────────────────────────────────────────────
    // ACCOUNT/LOGGED IN INDICATORS
    // ─────────────────────────────────────────────
    // Preferred: [data-testid='account-logout-link'], [data-testid='account-page']

    @FindBy(css = "[data-testid='account-logout-link'], a[href='/account/logout'], a[href*='account/logout']")
    private WebElement logoutLink;

    @FindBy(css = "[data-testid='account-page'], .account-page, .account-welcome, h1.account-title")
    private WebElement accountPageElement;

    // Constructor
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigate to the home page (entry point)
     */
    public void navigateToHome() {
        driver.get(com.retail.utils.DriverFactory.getBaseUrl());
    }

    /**
     * Click on the login link (from home page)
     */
    public void clickLoginLink() {
        try {
            waitForElementToBeClickable(loginLink);
            clickElement(loginLink);
            System.out.println("Clicked login link");
        } catch (Exception e) {
            System.out.println("Login link not found, navigating directly to login page");
            driver.get(com.retail.utils.DriverFactory.getBaseUrl() + "account/login");
        }
    }

    /**
     * Click on the signup/create account link (from home page)
     */
    public void clickSignupLink() {
        try {
            waitForElementToBeClickable(signupLink);
            clickElement(signupLink);
            System.out.println("Clicked signup link");
        } catch (Exception e) {
            System.out.println("Signup link not found, navigating directly to signup page");
            driver.get(com.retail.utils.DriverFactory.getBaseUrl() + "account/register");
        }
    }

    /**
     * Enter email for login
     * @param email Email address
     */
    public void enterLoginEmail(String email) {
        waitForElementToBeVisible(loginEmailInput);
        sendKeys(loginEmailInput, email);
    }

    /**
     * Enter password for login
     * @param password Password
     */
    public void enterLoginPassword(String password) {
        waitForElementToBeVisible(loginPasswordInput);
        sendKeys(loginPasswordInput, password);
    }

    /**
     * Click the login button
     */
    public void clickLoginButton() {
        waitForElementToBeClickable(loginButton);
        clickElement(loginButton);
    }

    /**
     * Enter first name for signup
     * @param firstName First name
     */
    public void enterSignupFirstName(String firstName) {
        waitForElementToBeVisible(signupFirstNameInput);
        sendKeys(signupFirstNameInput, firstName);
    }

    /**
     * Enter last name for signup
     * @param lastName Last name
     */
    public void enterSignupLastName(String lastName) {
        waitForElementToBeVisible(signupLastNameInput);
        sendKeys(signupLastNameInput, lastName);
    }

    /**
     * Enter email for signup
     * @param email Email address
     */
    public void enterSignupEmail(String email) {
        waitForElementToBeVisible(signupEmailInput);
        sendKeys(signupEmailInput, email);
    }

    /**
     * Enter password for signup
     * @param password Password
     */
    public void enterSignupPassword(String password) {
        waitForElementToBeVisible(signupPasswordInput);
        sendKeys(signupPasswordInput, password);
    }

    /**
     * Confirm password for signup
     * Note: The Sauce Demo store does not have a separate password confirmation field.
     * This method is here to maintain API compatibility with the feature file.
     * @param password Password (not used as confirmation field doesn't exist)
     */
    public void confirmSignupPassword(String password) {
        // Password confirmation field does not exist in Sauce Demo signup form
        // The form only requires password once
        System.out.println("Note: Password confirmation not required by Sauce Demo form");
    }

    /**
     * Click the create account button
     */
    public void clickCreateAccountButton() {
        waitForElementToBeClickable(createAccountButton);
        clickElement(createAccountButton);
    }

    /**
     * Check if user is logged in by checking for logout link or account page element
     * @return true if user is logged in
     */
    public boolean isUserLoggedIn() {
        try {
            // Check for logout link (indicates logged-in state)
            return isElementDisplayed(logoutLink);
        } catch (Exception e1) {
            try {
                // Fallback: check for account page element
                return isElementDisplayed(accountPageElement);
            } catch (Exception e2) {
                return false;
            }
        }
    }

    /**
     * Wait for URL to contain account path
     * @return Current URL after navigation
     */
    public String waitForAccountPage() {
        try {
            wait.until(ExpectedConditions.urlContains("/account"));
        } catch (Exception e) {
            // Return current URL even if wait times out
        }
        return driver.getCurrentUrl();
    }

}

