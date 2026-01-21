package com.retail.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    private WebDriver driver;

    // 1. By Locators (Page Factory)
    @FindBy(id = "CustomerEmail")
    private WebElement emailId;

    @FindBy(id = "CustomerPassword")
    private WebElement password;

    @FindBy(xpath = "//button[contains(text(), 'Sign in')]")
    // Note: Verify this xpath on the specific Shopify theme, it might vary.
    private WebElement signInButton;

    @FindBy(xpath = "//a[@href='/account/login']")
    private WebElement navLoginLink;

    // 2. Constructor of the page class
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this); // Essential for @FindBy
    }

    // 3. Page Actions
    public String getLoginPageTitle() {
        return driver.getTitle();
    }

    public void navigateToLoginPage() {
        // If the home page doesn't have a direct login form, we click the nav link first
        navLoginLink.click();
    }

    public void enterUserName(String username) {
        emailId.sendKeys(username);
    }

    public void enterPassword(String pwd) {
        password.sendKeys(pwd);
    }

    public void clickOnLogin() {
        signInButton.click();
    }
}