# Retail Automation Framework 🛒

A comprehensive **Behavior Driven Development (BDD)** test automation framework for **UI** and **API** testing with industry best practices.

## 🎯 Features

- ✅ **UI Automation** - Selenium WebDriver with Cucumber BDD
- ✅ **API Automation** - REST Assured for endpoint testing
- ✅ **Page Object Model** - Scalable and maintainable architecture
- ✅ **Multi-Environment Support** - SIT, UAT, PROD configurations
- ✅ **Advanced Reporting** - Extent Reports & Allure Reports
- ✅ **Parallel Execution Ready** - Maven configuration for CI/CD
- ✅ **Comprehensive Logging** - SLF4J with Log4j

## 🛠 Tech Stack

| Component | Version |
|-----------|---------|
| Java | JDK 21 |
| Selenium WebDriver | 4.27.0 |
| Cucumber | 7.15.0 |
| REST Assured | 5.4.0 |
| JUnit | 4.13.2 |
| Maven | 3.8.1+ |
| Hamcrest | 2.2 |
| Log4j | 1.2.17 |
| SLF4J | 1.7.36 |

## 📂 Project Structure

```
RetailAutomation
├── src/test/java
│   ├── com.retail.pages           # Page Objects (Locators & Actions)
│   ├── com.retail.stepdefinitions # Cucumber Step Definitions
│   ├── com.retail.runners         # JUnit Test Runners
│   ├── com.retail.utils           # Utilities (DriverFactory, ConfigReader)
│   └── restAssured                # REST Assured API Tests
├── src/test/resources
│   ├── features                   # Gherkin Feature Files
│   ├── config                     # Environment Properties (SIT, UAT)
│   ├── log4j.properties           # Logging Configuration
│   ├── extent.properties          # Extent Report Configuration
│   └── allure.properties          # Allure Report Configuration
├── pom.xml                        # Maven Dependencies
└── README.md                      # Documentation
```

## 🚀 Getting Started

### Prerequisites

- **Java JDK 21** (Microsoft OpenJDK or Oracle JDK)
- **Maven 3.8.1+** - Build and dependency management
- **Git** - Version control
- **Chrome/Firefox Browser** - For UI testing
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

### Run All Tests
```bash
mvn test
```

### Run Tests on Specific Environment
```bash
mvn test -Denv=sit   # SIT Environment
mvn test -Denv=uat   # UAT Environment (Default)
```

### Run Specific Test Class
```bash
mvn test -Dtest=MyTestRunner
```

### Run API Tests Only
```bash
mvn test -Dtest=MyFirstRestAssuredTest
```

### Run via IntelliJ
1. Navigate to `src/test/java/com/retail/runners/MyTestRunner.java`
2. Right-click → **Run 'MyTestRunner'**

## 📊 Test Reports

Reports are generated automatically after test execution:

- **Spark HTML Report:** `test-output/SparkReport/Index.html`
- **Cucumber Report:** `target/cucumber-reports/cucumber.html`
- **Allure Report:** `target/allure-results/`

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

## 🤝 Contributing

1. Create a feature branch: `git checkout -b feature/YourFeature`
2. Commit your changes: `git commit -m "Add new feature"`
3. Push to branch: `git push origin feature/YourFeature`
4. Open a Pull Request

## 📝 Configuration

### Environment Configuration

Update `src/test/resources/config/config-{env}.properties`:

```properties
browser=chrome
headless=false
wait_time=10
base_url=https://sauce-demo.myshopify.com
```

### Logging Configuration

Configure log levels in `src/test/resources/log4j.properties`:

```properties
log4j.rootLogger=INFO, console
log4j.logger.org.seleniumhq.selenium=WARN
log4j.logger.io.cucumber=INFO
```

## 🐛 Troubleshooting

| Issue | Solution |
|-------|----------|
| Tests not running | Ensure Maven is installed: `mvn -version` |
| Chrome driver issues | WebDriver Manager handles this automatically |
| SLF4J warnings | log4j.properties is configured in resources |
| CDP version mismatch | Updated with selenium-devtools-v145 |

## 📚 Learning Resources

- [Cucumber Documentation](https://cucumber.io/docs/cucumber/)
- [Selenium Documentation](https://www.selenium.dev/documentation/)
- [REST Assured Guide](https://rest-assured.io/)
- [Maven Documentation](https://maven.apache.org/guides/)

## 🔒 Security & Best Practices

- ✅ All dependencies updated with security patches
- ✅ No hardcoded credentials - use properties files
- ✅ Sensitive data excluded via .gitignore
- ✅ SSL validation properly handled

## 📄 License

This project is licensed under the MIT License.

---

**Maintainer:** Souvik Dutta  
**Last Updated:** February 2026
