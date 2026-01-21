package com.retail.stepdefinitions;

import com.retail.pages.LoginPage;
import com.retail.utils.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class LoginPageSteps {

    private LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
    private static String title;

    @Given("user is on the login page")
    public void user_is_on_the_login_page() {
        DriverFactory.getDriver().get("https://sauce-demo.myshopify.com/account/login");
        // Or call loginPage.navigateToLoginPage() if starting from home
    }

    @When("user enters username {string}")
    public void user_enters_username(String username) {
        loginPage.enterUserName(username);
    }

    @When("user enters password {string}")
    public void user_enters_password(String password) {
        loginPage.enterPassword(password);
    }

    @When("user clicks on login button")
    public void user_clicks_on_login_button() {
        loginPage.clickOnLogin();
    }

    @Then("user gets the title of the page")
    public void user_gets_the_title_of_the_page() {
        title = loginPage.getLoginPageTitle();
        System.out.println("Page title is: " + title);
        // You can add an assertion here, e.g., Assert.assertTrue(title.contains("Account"));
    }
}