# Retail Automation Framework 🛒

A comprehensive **Behavior Driven Development (BDD)** test automation framework for **UI** and **API** testing with **BrowserStack Cloud Integration** and industry best practices.

## 🎯 Features

- ✅ **UI Automation** - Selenium WebDriver with Cucumber BDD
- ✅ **API Automation** - REST Assured for endpoint testing
- ✅ **BrowserStack Integration** - Cloud-based cross-browser testing
- ✅ **Page Object Model** - Scalable and maintainable architecture
- ✅ **Multi-Environment Support** - SIT, UAT, PROD configurations
- ✅ **Advanced Reporting** - Extent Reports & Allure Reports
- ✅ **Parallel Execution** - 4 runners with multi-browser support
- ✅ **Comprehensive Logging** - SLF4J with Log4j 2.x

## 🛠 Tech Stack

| Component | Version |
|-----------|---------|
| Java | JDK 21 |
| Selenium WebDriver | 4.41.0 |
| Cucumber | 7.15.0 |
| REST Assured | 5.4.0 |
| BrowserStack SDK | LATEST |
| Maven | 3.8.1+ |
| Log4j 2.x | 2.24.1 |
| Allure Reports | 2.24.0 |

## 📂 Project Structure

```
RetailAutomation
├── src/test/java
│   ├── com.retail.pages           # Page Objects (Locators & Actions)
│   ├── com.retail.stepdefinitions # Cucumber Step Definitions
│   ├── com.retail.runners         # JUnit Test Runners (ParallelRunner1-4)
│   ├── com.retail.utils           # DriverFactory, ConfigReader
│   └── restAssured                # REST Assured API Tests
├── src/test/resources
│   ├── features/product           # Gherkin Feature Files
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
| `@S1` | Add first product to cart (Smoke) | ParallelRunner1 |
| `@S2` | View product details (Regression) | ParallelRunner2 |
| `@S3` | Verify products displayed | ParallelRunner3 |
| `@S4` | Add from details page (Smoke) | ParallelRunner4 |

#### Running Multiple Scenarios on Cloud

```bash
# Set credentials
export BROWSERSTACK_USERNAME=souvikdutta_kjl3bT
export BROWSERSTACK_ACCESS_KEY=pk6zemKnxPqzhh4MRevy

# Run all @Smoke tests
mvn test -Dcucumber.filter.tags="@Smoke"

# Run all @Product tests
mvn test -Dcucumber.filter.tags="@Product"

# Run @Product AND @Cart tests
mvn test -Dcucumber.filter.tags="@Product and @Cart"

# Run all tests (default)
mvn clean test
```

#### Understanding the Parallel Runners

Your framework has 4 parallel runners:
- **ParallelRunner1** - Executes @S1 tags
- **ParallelRunner2** - Executes @S2 tags
- **ParallelRunner3** - Executes @S3 tags
- **ParallelRunner4** - Executes @S4 tags

Each runner can target different browsers/devices via `browserstack.yml`.

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

## 🌐 BrowserStack Integration Details

### How It Works

The framework uses BrowserStack Java SDK with a javaagent that automatically detects environment variables:

- **If `BROWSERSTACK_USERNAME` is NOT set** → Tests run locally
- **If `BROWSERSTACK_USERNAME` is set** → Tests route to BrowserStack cloud

**No code changes needed!** The same test code works everywhere.

### Configuration File

Edit `browserstack.yml` to customize cloud execution:

```yaml
userName: souvikdutta_kjl3bT
accessKey: pk6zemKnxPqzhh4MRevy

platforms:
  - os: Windows
    osVersion: 10
    browserName: Chrome
    browserVersion: 120.0
  
  - os: OS X
    osVersion: Monterey
    browserName: Safari
    browserVersion: 15.6
  
  - deviceName: iPhone 13
    osVersion: 15
    browserName: Chromium
    deviceOrientation: portrait

buildName: bstack-demo
debug: true
networkLogs: true
consoleLogs: info
```

### Troubleshooting BrowserStack

**Tests still running locally?**
```bash
# Check if env vars are set
echo $BROWSERSTACK_USERNAME
echo $BROWSERSTACK_ACCESS_KEY

# Set them (temporary for this session)
export BROWSERSTACK_USERNAME=souvikdutta_kjl3bT
export BROWSERSTACK_ACCESS_KEY=pk6zemKnxPqzhh4MRevy

# Verify
echo $BROWSERSTACK_USERNAME
```

**Tests fail on cloud but pass locally?**
- Check BrowserStack dashboard for video and detailed logs
- Verify `browserstack.yml` has correct browser capabilities
- Check network connectivity to BrowserStack servers
- Review console logs in dashboard

**Slow execution on cloud?**
- Cloud tests may be slower due to network latency
- This is normal behavior
- Run local tests for fast feedback during development

**Want to see results on BrowserStack dashboard?**
- Login to: https://app-automate.browserstack.com/
- Credentials: `souvikdutta_kjl3bT` / `pk6zemKnxPqzhh4MRevy`
- Look for sessions with your build name
- Click on any session to view video, logs, and screenshots

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

