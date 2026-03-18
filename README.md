# Retail Automation Framework 🛒

A comprehensive **Behavior Driven Development (BDD)** test automation framework for **UI** and **API** testing with **BrowserStack Cloud Integration** and industry best practices.

## 🎯 Features

- ✅ **UI Automation** - Selenium WebDriver with Cucumber BDD
- ✅ **API Automation** - REST Assured for endpoint testing
- ✅ **BrowserStack Integration** - Cloud-based cross-browser testing
- ✅ **Page Object Model** - Scalable and maintainable architecture
- ✅ **Multi-Environment Support** - SIT, UAT, PROD configurations
- ✅ **Advanced Reporting** - Allure Reports with GitHub Pages deployment
- ✅ **Parallel Execution** - 5 test runners executing simultaneously
- ✅ **Comprehensive Logging** - SLF4J with Log4j 2.x
- ✅ **Security Patched** - CVE-2025-4641, GHSA-72hv-8253-57qq fixed

## 🛠 Tech Stack

| Component | Version |
|-----------|---------|
| Java | Java 25 |
| Selenium WebDriver | 4.41.0 |
| Cucumber | 7.15.0 |
| REST Assured | 5.4.0 |
| BrowserStack SDK | LATEST |
| Maven | 3.8.1+ |
| Log4j 2.x | 2.24.2 |
| Allure Reports | 2.28.0 |

## 📂 Project Structure

```
RetailAutomation
├── src/test/java
│   ├── com.retail.pages           # Page Objects (Locators & Actions)
│   ├── com.retail.stepdefinitions # Cucumber Step Definitions
│   ├── com.retail.runners         # JUnit Test Runners (ParallelRunner1-5)
│   ├── com.retail.utils           # DriverFactory, ConfigReader
│   └── restAssured                # REST Assured API Tests
├── src/test/resources
│   ├── features/
│   │   ├── authentication/        # Login & authentication tests
│   │   ├── product/               # Product browsing tests
│   │   └── header/                # Header/footer component tests
│   ├── config/                    # Environment Properties (SIT, UAT, PROD)
│   ├── testdata/                  # JSON test data files
│   └── *.xml, *.properties        # Logging & Report Config
├── .github/workflows/tests.yml    # GitHub Actions CI/CD
├── browserstack.yml               # BrowserStack configuration
├── pom.xml                        # Maven Dependencies & Plugins
└── README.md                      # This file
```

## 🚀 Getting Started

### Prerequisites

- **Java JDK 21** (Microsoft OpenJDK or Oracle JDK)
- **Maven 3.8.1+** - Build and dependency management
- **Git** - Version control
- **Chrome/Firefox Browser** - For local UI testing (optional for cloud)
- **IntelliJ IDEA** (optional) - IDE with Cucumber plugin

### Installation

1. **Clone the repository:**
```bash
git clone https://github.com/GreedyM0nk/CucumberPractice.git
cd RetailAutomation
```

2. **Install dependencies:**
```bash
mvn clean install -DskipTests
```

## 🏃 Running Tests

### 🏠 Run Tests Locally (No Cloud)

```bash
# Run all tests locally
mvn clean test

# Run on specific environment
mvn clean test -Denv=sit   # SIT Environment
mvn clean test -Denv=uat   # UAT Environment (Default)
mvn clean test -Denv=prod  # PROD Environment

# Run specific runner
mvn test -Dtest=ParallelRunner1

# Run specific API test
mvn test -Dtest=MyFirstRestAssuredTest
```

### ☁️ Run Tests on BrowserStack Cloud

#### Quick Start: Run All Tests on Cloud

```bash
# Set environment variables
export BROWSERSTACK_USERNAME=souvikdutta_kjl3bT
export BROWSERSTACK_ACCESS_KEY=pk6zemKnxPqzhh4MRevy

# Run all tests on BrowserStack cloud
mvn clean test
```

✅ Tests automatically route to BrowserStack cloud  
✅ Runs on Windows 10 Chrome, OS X Safari, iPhone 13  
✅ Results visible on BrowserStack dashboard  
✅ Test videos and logs captured automatically  

#### Run Single Feature on BrowserStack Cloud

The feature file contains 4 scenarios with different tags:

```gherkin
@S1 - Add first product to cart (Smoke)
@S2 - View product details (Regression)
@S3 - Verify 3 products displayed
@S4 - Add from product details page (Smoke)
```

**Step 1: Set BrowserStack credentials**
```bash
export BROWSERSTACK_USERNAME=souvikdutta_kjl3bT
export BROWSERSTACK_ACCESS_KEY=pk6zemKnxPqzhh4MRevy
```

**Step 2: Run specific scenario using tag**
```bash
# Run @S1 scenario: Add first product to cart
mvn test -Dtest=ParallelRunner1 -Dcucumber.filter.tags="@S1"

# Or simply run the runner which executes the matching tag
mvn test -Dtest=ParallelRunner1
```

**Step 3: Monitor execution on BrowserStack Dashboard**
- Go to: https://app-automate.browserstack.com/
- Login with: `souvikdutta_kjl3bT` / `pk6zemKnxPqzhh4MRevy`
- View real-time execution on multiple browsers
- Watch video recordings
- Access console logs and network logs

#### Feature Scenarios & Tags

| Tag | Scenario | Runner |
|-----|----------|--------|
| `@S1` | Add first product to cart | ParallelRunner1 |
| `@S2` | Product filtering & details | ParallelRunner2 |
| `@S3` | Checkout flow & payment | ParallelRunner3 |
| `@S4` | Login & authentication | ParallelRunner4 |
| `@S5` | Footer navigation & links | ParallelRunner5 |

#### Parallel Test Runners

Your framework has 5 parallel runners executing simultaneously:

- **ParallelRunner1 (@S1)** - Add products to cart tests
- **ParallelRunner2 (@S2)** - Product filtering & browsing tests
- **ParallelRunner3 (@S3)** - Checkout flow tests
- **ParallelRunner4 (@S4)** - Login & authentication tests
- **ParallelRunner5 (@S5)** - Footer & header component tests

Each runner executes in its own JVM fork with independent browser instances.

#### Running Specific Tests

```bash
# Run specific parallel runner
mvn test -Dtest=ParallelRunner1    # Run @S1 tests
mvn test -Dtest=ParallelRunner5    # Run @S5 footer tests

# Run all tests in parallel
mvn clean test

# Run on specific environment
mvn clean test -Puat   # UAT (default)
mvn clean test -Psit   # SIT
mvn clean test -Pprod  # PROD
```

### 🤖 Run Tests via GitHub Actions (CI/CD)

Tests automatically run on BrowserStack when you push to main:

```bash
git add .
git commit -m "Update tests"
git push origin main
```

✅ GitHub Actions triggered automatically  
✅ Tests execute on BrowserStack cloud  
✅ Results visible on BrowserStack dashboard  
✅ Allure report deployed to GitHub Pages  

**Prerequisites:** GitHub Secrets configured:
- `BROWSERSTACK_USERNAME`
- `BROWSERSTACK_ACCESS_KEY`

### 🎯 Run via IntelliJ IDE

**Option 1: Run Feature File Directly**
1. Navigate to: `src/test/resources/features/product/addToCart.feature`
2. Right-click on specific scenario
3. Select **Run 'Scenario'**
4. Set env vars if running on cloud

**Option 2: Run Test Runner with Tags**
1. Navigate to: `src/test/java/com/retail/runners/ParallelRunner1.java`
2. Right-click → **Edit Configuration**
3. Add VM option: `-Dcucumber.filter.tags=@S1`
4. Set environment variables for BrowserStack (if cloud)
5. Click **Run**

**Option 3: Run All from Test Class**
1. Navigate to: `src/test/java/com/retail/runners/ParallelRunner1.java`
2. Right-click → **Run 'ParallelRunner1'**

## 📊 Test Reports

### Local Execution Reports

```bash
# Generate and view Allure report
mvn allure:serve
```

- **Allure Report:** `target/allure-results/`
- **Cucumber HTML:** `target/cucumber-reports/parallel-runnerX.html`
- **Logs:** `target/logs/automation.log`

### BrowserStack Cloud Reports

After running tests with BrowserStack credentials:

1. **BrowserStack Dashboard** - https://app-automate.browserstack.com/
   - Live execution monitoring
   - Video recordings of each test
   - Console logs and network activity
   - Detailed failure analysis with screenshots
   - Test duration and status

2. **Allure Report** - `target/allure-results/`
   - Same as local with added cloud metadata
   - Browser & OS information
   - Session IDs for correlation

3. **GitHub Pages** (if using CI/CD)
   - Historical trend analysis
   - Previous test reports
   - Build-over-build comparison

## 🔌 REST Assured API Testing

### Running API Tests

```bash
# Run all tests (includes API tests)
mvn test

# Run specific API test
mvn test -Dtest=MyFirstRestAssuredTest
```

### API Test Example

```java
@Test
public void validateLoginAPI() {
    given()
        .queryParam("CUSTOMER_ID", "68195")
        .queryParam("PASSWORD", "1234!")
    .when()
        .get("https://api.example.com/endpoint")
    .then()
        .statusCode(200)
        .log().body();
}
```

### Key Capabilities

- ✅ GET, POST, PUT, DELETE requests
- ✅ SSL certificate handling
- ✅ Request/Response validation
- ✅ Query parameters and headers
- ✅ JSON/XML response parsing

## 🧩 Adding New Tests

### Creating a UI Test (Cucumber BDD)

1. **Create Feature File** - `src/test/resources/features/NewFeature.feature`
```gherkin
Feature: New Feature Tests
  
  @NewTag
  Scenario: Test new functionality
    Given user is on the login page
    When user enters username "valid_user"
    And user enters password "password123"
    Then user sees dashboard
```

2. **Create Page Object** - `src/test/java/com/retail/pages/NewPage.java`
```java
public class NewPage {
    @FindBy(id = "element_id")
    private WebElement element;
    
    public void performAction() {
        element.click();
    }
}
```

3. **Create Step Definition** - `src/test/java/com/retail/stepdefinitions/NewSteps.java`
```java
@Given("user is on the page")
public void userIsOnPage() {
    // Implementation
}
```

4. **Run your test locally**
```bash
mvn test -Dcucumber.filter.tags="@NewTag"
```

5. **Run on BrowserStack cloud**
```bash
export BROWSERSTACK_USERNAME=souvikdutta_kjl3bT
export BROWSERSTACK_ACCESS_KEY=pk6zemKnxPqzhh4MRevy
mvn test -Dcucumber.filter.tags="@NewTag"
```

### Creating an API Test (REST Assured)

1. **Create Test Class** - `src/test/java/restAssured/NewAPITest.java`
```java
public class NewAPITest {
    @Test
    public void testNewEndpoint() {
        given()
            .baseUri("https://api.example.com")
        .when()
            .get("/endpoint")
        .then()
            .statusCode(200);
    }
}
```

## 🔐 Authentication Testing - Login & Signup Feature

The framework now includes comprehensive authentication testing for the **Shopify Sauce Demo** store at `https://sauce-demo.myshopify.com/`

### Running Login & Signup Tests Locally

#### Run All Authentication Tests

```bash
mvn clean test -Dcucumber.filter.tags="@Authentication"
```

#### Run Only Signup Scenario

```bash
mvn clean test -Dcucumber.filter.tags="@Signup"
```

#### Run Only Login Scenario

```bash
mvn clean test -Dcucumber.filter.tags="@Login"
```

#### Run Single Feature File

```bash
# Run only the login feature file
mvn test -Dtest=ParallelRunner1 -Dcucumber.filter.tags="@Authentication"
```

### Running Login Tests on BrowserStack Cloud

#### Quick Setup

1. **Set your BrowserStack credentials:**
```bash
export BROWSERSTACK_USERNAME=your_username
export BROWSERSTACK_ACCESS_KEY=your_access_key
```

2. **Run all authentication tests on cloud:**
```bash
mvn clean test -Dcucumber.filter.tags="@Authentication"
```

3. **Run signup only on cloud:**
```bash
mvn test -Dcucumber.filter.tags="@Signup"
```

4. **Run login only on cloud:**
```bash
mvn test -Dcucumber.filter.tags="@Login"
```

#### Monitor on BrowserStack Dashboard

- **URL:** https://app-automate.browserstack.com/
- **Username:** Your BrowserStack username
- **Features:**
  - ✅ Real-time execution monitoring
  - ✅ Video recordings of signup/login flows
  - ✅ Screenshots at each step
  - ✅ Browser console logs
  - ✅ Network activity logs
  - ✅ Test session details and metadata

### Understanding the Login Feature

#### Feature File Location
```
src/test/resources/features/authentication/login.feature
```

#### Test Scenarios

**Scenario 1: User signs up with a new account**
- ✅ Navigates to home page
- ✅ Clicks signup link
- ✅ Enters unique email (auto-generated)
- ✅ Enters password
- ✅ Confirms password
- ✅ Submits signup form
- ✅ Verifies account creation and login

**Scenario 2: User logs in with existing credentials**
- ✅ Navigates to home page
- ✅ Clicks login link
- ✅ Enters email
- ✅ Enters password
- ✅ Submits login form
- ✅ Verifies successful login and account access

#### Key Locators (Sauce Demo Shopify Store)

| Element | Locator | Type |
|---------|---------|------|
| Login Link | `id="customer_login_link"` | HTML ID |
| Signup Link | `id="customer_register_link"` | HTML ID |
| Login Email | `name="email"` | Input Name |
| Login Password | `name="password"` | Input Name |
| Login Button | `value="Sign In"` | Submit Button |
| Signup Email | `name="customer[email]"` | Input Name |
| Signup Password | `name="customer[password]"` | Input Name |
| Confirm Password | `name="customer[password_confirmation]"` | Input Name |
| Create Account | `value="Create"` | Submit Button |
| Logout Link | `href="/account/logout"` | Link |

### Page Object & Step Definitions

#### Page Object Class
```
src/test/java/com/retail/pages/LoginPage.java
```
- Extends `BasePage` for reusable wait/action methods
- Contains all locators as `@FindBy` annotations
- Implements methods for:
  - Navigation (home, login, signup pages)
  - Form interactions (enter email, password, click buttons)
  - Verification (check login status, account access)

#### Step Definitions Class
```
src/test/java/com/retail/stepdefinitions/AuthenticationSteps.java
```
- Implements all Gherkin steps from the feature file
- Auto-generates unique test emails to prevent conflicts
- Includes proper waits and delays for page loads
- Provides clear console output for debugging

### Test Data Generation

The framework automatically generates unique test emails using timestamps:

```java
testEmail = "newuser" + System.currentTimeMillis() + "@test.com";
// Example: newuser1710854123456@test.com
```

**Benefits:**
- ✅ Prevents "email already exists" errors
- ✅ Allows multiple test runs without cleanup
- ✅ Ensures test isolation
- ✅ No hardcoded test data needed

### Running via IntelliJ IDEA

#### Option 1: Run Feature File Directly

1. Navigate to: `src/test/resources/features/authentication/login.feature`
2. Right-click on a scenario
3. Select **Run 'Scenario'** or **Run 'Signup' Scenario**
4. Watch test execution in the IDE
5. View step-by-step output

#### Option 2: Run with Environment Variables (Cloud)

1. Right-click feature file → **Edit Run Configuration**
2. Add environment variables:
   ```
   BROWSERSTACK_USERNAME=your_username
   BROWSERSTACK_ACCESS_KEY=your_access_key
   ```
3. Click **Run** to execute on BrowserStack cloud

#### Option 3: Run via Test Runner Class

1. Navigate to: `src/test/java/com/retail/runners/ParallelRunner1.java`
2. Right-click → **Edit Configuration**
3. Add VM option:
   ```
   -Dcucumber.filter.tags=@Signup
   ```
4. Add environment variables for cloud (optional)
5. Click **Run**

### Debugging Failed Tests

#### Check Console Output

The framework provides detailed logging:

```
✓ User navigated to home page
✓ Account menu navigation ready (direct links available)
✓ User clicked on signup link
✓ User entered email for signup: newuser1710854123456@test.com
✓ User entered password for signup
✓ User confirmed password
✓ User clicked the create account button
✓ User account created successfully. Current URL: https://sauce-demo.myshopify.com/account
✓ User is successfully logged in
```

#### Common Issues & Solutions

| Issue | Solution |
|-------|----------|
| "Element not found" | Check if page loaded correctly. Add more wait time. |
| "Password fields not visible" | Ensure JavaScript is enabled and form is not hidden. |
| "Email already exists" | Auto-generation uses timestamps, should be unique. |
| "Login fails but form submitted" | Check if account was actually created. Try with known email. |
| "Account page not accessible" | Verify logout link is present (login indicator). |

#### Check Logs

```bash
# View detailed execution logs
tail -f target/logs/automation.log

# View with grep for errors
grep -i "error\|failed" target/logs/automation.log
```

### Extending the Authentication Tests

#### Add a New Scenario (Password Reset)

1. Edit: `src/test/resources/features/authentication/login.feature`
2. Add new scenario:
```gherkin
@Authentication @PasswordReset
Scenario: User resets forgotten password
  When user clicks on the account menu
  And user clicks on login option
  And user clicks on forgot password link
  And user enters email for password reset
  And user clicks the reset button
  Then password reset email should be sent
```

3. Add step definitions in: `src/test/java/com/retail/stepdefinitions/AuthenticationSteps.java`
4. Add methods in: `src/test/java/com/retail/pages/LoginPage.java`
5. Run: `mvn test -Dcucumber.filter.tags="@PasswordReset"`

#### Add Steps for Logout Testing

```java
@When("user clicks the logout button")
public void user_clicks_the_logout_button() {
    getLoginPage().logout();
}

@Then("user should be logged out")
public void user_should_be_logged_out() {
    boolean isLoggedOut = !getLoginPage().isUserLoggedIn();
    Assert.assertTrue("User should be logged out", isLoggedOut);
}
```

### CI/CD Integration

Authentication tests run automatically on GitHub Actions:

**Push to trigger:**
```bash
git add .
git commit -m "Add authentication tests"
git push origin main
```

**GitHub Actions will:**
1. ✅ Execute all authentication tests on BrowserStack cloud
2. ✅ Capture screenshots and videos
3. ✅ Generate Allure reports
4. ✅ Deploy reports to GitHub Pages
5. ✅ Send results to BrowserStack dashboard

### Best Practices for Authentication Testing

1. **Use unique test emails** - Framework auto-generates via timestamps
2. **Don't hardcode passwords** - Use environment variables in production
3. **Test both success and failure paths** - Valid and invalid credentials
4. **Clean up test data** - Or use timestamps for automatic isolation
5. **Test on real browsers** - Use BrowserStack for cross-browser coverage
6. **Monitor test execution** - Watch BrowserStack dashboard for issues
7. **Capture screenshots** - Useful for debugging failed assertions
8. **Include proper waits** - Let forms load before interactions

### Performance Metrics

When running on BrowserStack, monitor:

- **Signup flow time:** Typically 8-12 seconds
- **Login flow time:** Typically 6-10 seconds
- **Account page load:** Should be < 3 seconds after login
- **Form submission:** Should complete within 2-3 seconds

Use BrowserStack logs to identify slow operations.

## 🤝 Contributing

1. Create a feature branch: `git checkout -b feature/YourFeature`
2. Add tests with meaningful tags: `@Feature @Smoke`
3. Verify locally: `mvn test`
4. Verify on cloud:
   ```bash
   export BROWSERSTACK_USERNAME=souvikdutta_kjl3bT
   export BROWSERSTACK_ACCESS_KEY=pk6zemKnxPqzhh4MRevy
   mvn test
   ```
5. Commit: `git commit -m "Add new feature"`
6. Push: `git push origin feature/YourFeature`
7. Create Pull Request

## 📚 Additional Documentation

For reference documentation:
- **Quick Start:** `00_START_HERE.md`
- **Architecture:** `ARCHITECTURE_DIAGRAMS.md`
- **Code Examples:** `ALL_UPDATED_CODE.md`
- **Verification:** `FINAL_CHECKLIST.md`

## 📞 Support & Resources

**BrowserStack:**
- [Official Documentation](https://www.browserstack.com/docs/)
- [Java SDK GitHub](https://github.com/browserstack/browserstack-java-sdk)

**Testing Frameworks:**
- [Selenium Documentation](https://www.selenium.dev/documentation/)
- [Cucumber Documentation](https://cucumber.io/docs/)
- [Maven Documentation](https://maven.apache.org/)
- [REST Assured Documentation](https://rest-assured.io/)

---

**Happy Testing! 🚀**
