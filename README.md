# Retail Automation Framework 🛒

## Overview

This project is a comprehensive **Behavior Driven Development (BDD)** test automation framework that includes:
- **UI Automation** using Selenium WebDriver with Cucumber (for [Sauce Demo Retail Site](https://sauce-demo.myshopify.com/))
- **API Automation** using REST Assured for backend testing

The framework follows the **Page Object Model (POM)** design pattern for scalability and maintainability.

## 🛠 Tech Stack

* **Language:** Java (JDK 21)
* **Automation Tools:** 
  - Selenium WebDriver (v4.27.0) - UI Automation
  - REST Assured (v5.4.0) - API Automation
* **BDD Framework:** Cucumber (v7.15.0)
* **Test Runners:** JUnit 4, Cucumber
* **Build Tool:** Maven
* **Reporting:** 
  - Extent Reports (Spark Adapter) - UI Tests
  - Allure Reports - All Tests
* **IDE:** IntelliJ IDEA
* **Logging:** SLF4J with Log4j

## 📂 Project Structure

```text
RetailAutomation
│
├── src/test/java
│   ├── com.retail.pages           # Page Object Classes (Locators & Actions)
│   ├── com.retail.stepdefinitions # Cucumber Step Definitions (Glue Code)
│   ├── com.retail.runners         # JUnit Test Runners
│   ├── com.retail.utils           # Utilities (DriverFactory, ConfigReader)
│   └── restAssured                # REST Assured API Tests
│
├── src/test/resources
│   ├── features                   # .feature files (Gherkin Scenarios)
│   ├── config                     # Configuration files (SIT, UAT properties)
│   ├── log4j.properties           # Logging configuration
│   ├── extent.properties          # Extent Reporting configuration
│   └── allure.properties          # Allure Reporting configuration
│
├── pom.xml                        # Maven Dependencies & Build Configuration
└── README.md                      # Documentation

```

## 🚀 Prerequisites

Before running the tests, ensure you have the following installed:

1. **Java JDK 21** (Microsoft OpenJDK or Oracle JDK)
2. **Maven** (Apache Maven 3.8.1 or higher)
3. **IntelliJ IDEA** (with Cucumber for Java plugin installed)
4. **Git**
5. **Chrome/Firefox Browser** (for Selenium tests)
6. **ChromeDriver** (automatically managed by Selenium WebDriver Manager)

## ⚙️ Setup & Installation

1. **Clone the repository:**
```bash
git clone https://github.com/GreedyM0nk/CucumberPractice.git

```


2. **Open in IntelliJ:**
   Open the project folder. IntelliJ should detect the `pom.xml` automatically.
3. **Install Dependencies:**
   Run the following command in the terminal to download all libraries:
```bash
mvn clean install -DskipTests

```



## 🏃 how to Run Tests

### Option 1: Run via Command Line (CI/CD Style)

To run all tests using the default configuration (UAT):

```bash
mvn test

```

### Option 2: Run via IntelliJ

1. Navigate to `src/test/java/com/retail/runners/MyTestRunner.java`.
2. Right-click the file and select **Run 'MyTestRunner'**.

### Option 3: Run Specific Environments

The framework is designed to support multiple environments (SIT, UAT, PROD).
To switch environments dynamically, pass the `env` variable:

**Run on SIT:**

```bash
mvn test -Denv=sit

```

*(Note: Ensure `config-sit.properties` exists in `src/test/resources/config/`)*

**Run on UAT (Default):**

```bash
mvn test -Denv=uat

```

## 📊 Reporting

After execution, reports are automatically generated in the `test-output` folder.

* **Spark HTML Report:** `test-output/SparkReport/Index.html`
* *Right-click -> Open In -> Browser* to view a dashboard with charts and screenshots of failed steps.


* **Cucumber Default Report:** `target/cucumber-reports/cucumber.html`

## 🔌 REST Assured API Testing

The framework includes REST Assured tests located in `src/test/java/restAssured/`.

### Running API Tests

```bash
# Run all tests including API tests
mvn test

# Run specific REST Assured test
mvn test -Dtest=MyFirstRestAssuredTest
```

### Example: API Test Structure

```java
@Test
public void getResponseBody(){
    given()
        .queryParam("CUSTOMER_ID","68195")
        .queryParam("PASSWORD","1234!")
        .when()
        .get("https://api.example.com/endpoint")
        .then()
        .log().body();
}
```

### Key Features:
- ✅ Request/Response validation
- ✅ SSL certificate validation handling
- ✅ Query parameters and headers support
- ✅ Response status code assertions
- ✅ Response body parsing and validation

## 🧩 Adding New Tests

### For UI Tests (Cucumber/BDD):

To add a new test scenario, follow this flow:

1. **Feature File:** Create a new `.feature` file in `src/test/resources/features`.
2. **Page Object:** Create a Java class in `com.retail.pages`. Define WebElements using `@FindBy`.
3. **Step Definition:** Create a Step Definition class in `com.retail.stepdefinitions` and call the methods from the Page Object.
4. **Run:** Execute `MyTestRunner` to verify.

### For API Tests (REST Assured):

1. **Test Class:** Create a new test class in `src/test/java/restAssured/`
2. **Test Method:** Add `@Test` method with REST Assured assertions
3. **Run:** Execute the test class or use Maven to run it
4. **Reports:** Check Allure and Extent reports for results

## 🤝 Contribution

1. Create a new branch: `git checkout -b feature/NewScenario`
2. Commit your changes: `git commit -m "Added checkout test"`
3. Push to branch: `git push origin feature/NewScenario`
4. Create a Pull Request.

---

**Maintainer:** Souvik Dutta