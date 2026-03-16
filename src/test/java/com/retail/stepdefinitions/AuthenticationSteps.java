package com.retail.stepdefinitions;

import com.retail.pages.LoginPage;
import com.retail.utils.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;

/**
 * Step Definitions for Authentication (Login/Signup)
 * Tests login and signup flows on Shopify Sauce Demo: sauce-demo.myshopify.com
 */
public class AuthenticationSteps {

    private LoginPage loginPage;
    private String testEmail;

    private LoginPage getLoginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage(DriverFactory.getDriver());
        }
        return loginPage;
    }

    @Given("user is on the home page")
    public void user_is_on_the_home_page() {
        loginPage = null;
        getLoginPage().navigateToHome();
        System.out.println("✓ User navigated to home page");
    }

    @When("user clicks on the account menu")
    public void user_clicks_on_the_account_menu() {
        // Account menu no longer exists in UI - direct links are available in navigation
        System.out.println("✓ Navigation links are directly available");
    }

    @When("user clicks on login link")
    public void user_clicks_on_login_link() {
        getLoginPage().clickLoginLink();
        System.out.println("✓ User clicked on login link");
        try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    @When("user clicks on sign up link")
    public void user_clicks_on_sign_up_link() {
        getLoginPage().clickSignupLink();
        System.out.println("✓ User clicked on signup link");
        try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    @When("user clicks on login option")
    public void user_clicks_on_login_option() {
        getLoginPage().clickLoginLink();
        System.out.println("✓ User clicked on login link");
        try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    @When("user clicks on create account option")
    public void user_clicks_on_create_account_option() {
        getLoginPage().clickSignupLink();
        System.out.println("✓ User clicked on signup link");
        try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    @When("user enters email for login")
    public void user_enters_email_for_login() {
        testEmail = "testuser" + System.currentTimeMillis() + "@test.com";
        getLoginPage().enterLoginEmail(testEmail);
        System.out.println("✓ User entered email for login: " + testEmail);
    }

    @When("user enters password for login")
    public void user_enters_password_for_login() {
        String testPassword = "Test@123456";
        getLoginPage().enterLoginPassword(testPassword);
        System.out.println("✓ User entered password for login");
    }

    @When("user clicks the login button")
    public void user_clicks_the_login_button() {
        getLoginPage().clickLoginButton();
        System.out.println("✓ User clicked the login button");
        try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    @When("user enters email for signup")
    public void user_enters_email_for_signup() {
        testEmail = "newuser" + System.currentTimeMillis() + "@test.com";
        getLoginPage().enterSignupEmail(testEmail);
        System.out.println("✓ User entered email for signup: " + testEmail);
    }

    @When("user enters first name for signup")
    public void user_enters_first_name_for_signup() {
        String firstName = "Test";
        getLoginPage().enterSignupFirstName(firstName);
        System.out.println("✓ User entered first name for signup: " + firstName);
    }

    @When("user enters last name for signup")
    public void user_enters_last_name_for_signup() {
        String lastName = "User";
        getLoginPage().enterSignupLastName(lastName);
        System.out.println("✓ User entered last name for signup: " + lastName);
    }

    @When("user enters password for signup")
    public void user_enters_password_for_signup() {
        String testPassword = "Test@123456";
        getLoginPage().enterSignupPassword(testPassword);
        System.out.println("✓ User entered password for signup");
    }

    @When("user confirms password")
    public void user_confirms_password() {
        String testPassword = "Test@123456";
        getLoginPage().confirmSignupPassword(testPassword);
        System.out.println("✓ User confirmed password");
    }

    @When("user clicks the create account button")
    public void user_clicks_the_create_account_button() {
        getLoginPage().clickCreateAccountButton();
        System.out.println("✓ User clicked the create account button");
        try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    @Then("user should be logged in")
    public void user_should_be_logged_in() {
        try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        boolean isLoggedIn = getLoginPage().isUserLoggedIn();
        Assert.assertTrue("User should be logged in after signup", isLoggedIn);
        System.out.println("✓ User is successfully logged in");
    }

    @Then("user should be logged in successfully")
    public void user_should_be_logged_in_successfully() {
        try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        boolean isLoggedIn = getLoginPage().isUserLoggedIn();
        Assert.assertTrue("User should be logged in after login", isLoggedIn);
        System.out.println("✓ User is successfully logged in");
    }

    @Then("user account should be created successfully")
    public void user_account_should_be_created_successfully() {
        String currentUrl = DriverFactory.getDriver().getCurrentUrl();
        Assert.assertTrue("User should be on account page after signup",
            currentUrl.contains("/account"));
        System.out.println("✓ User account created successfully. Current URL: " + currentUrl);
    }

    @Then("user profile should be accessible")
    public void user_profile_should_be_accessible() {
        String currentUrl = getLoginPage().waitForAccountPage();
        Assert.assertTrue("User should be on account page",
            currentUrl.contains("/account"));
        System.out.println("✓ User profile is accessible at: " + currentUrl);
    }
}
