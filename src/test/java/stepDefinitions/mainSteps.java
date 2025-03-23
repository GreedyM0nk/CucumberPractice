package stepDefinitions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
public class mainSteps {




        @Given("user is on netbanking login page")
        public void user_is_on_netbanking_login_page() {
            // Write code here that turns the phrase above into concrete actions
            System.out.println("Login page");
        }
        @When("User login into application")
        public void user_login_into_application() {
            // Write code here that turns the phrase above into concrete actions
            System.out.println("Logged in application");
        }
        @Then("Home page is displayed")
        public void home_page_is_displayed() {
            // Write code here that turns the phrase above into concrete actions
            System.out.println("Home page");
        }
        @Then("cards are displayed")
        public void cards_are_displayed() {
            // Write code here that turns the phrase above into concrete actions
            System.out.println("cards");
        }

    }


