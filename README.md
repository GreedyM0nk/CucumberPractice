# Retail Automation Framework 🛒

## Overview

This project is a Behavior Driven Development (BDD) test automation framework built to automate the [Sauce Demo Retail Site](https://sauce-demo.myshopify.com/). It uses **Java**, **Selenium WebDriver**, and **Cucumber**, following the **Page Object Model (POM)** design pattern for scalability and maintainability.

## 🛠 Tech Stack

* **Language:** Java (JDK 11+)
* **Automation Tool:** Selenium WebDriver (v4.x)
* **BDD Framework:** Cucumber
* **Test Runner:** JUnit 4
* **Build Tool:** Maven
* **Reporting:** Extent Reports (Spark Adapter)
* **IDE:** IntelliJ IDEA

## 📂 Project Structure

```text
RetailAutomation
│
├── src/test/java
│   ├── com.retail.pages           # Page Object Classes (Locators & Actions)
│   ├── com.retail.stepdefinitions # Cucumber Step Definitions (Glue Code)
│   ├── com.retail.runners         # JUnit Test Runners
│   └── com.retail.utils           # Utilities (DriverFactory, ConfigReader)
│
├── src/test/resources
│   ├── features                   # .feature files (Gherkin Scenarios)
│   ├── config                     # Configuration files (SIT, UAT properties)
│   └── extent.properties          # Reporting configuration
│
├── pom.xml                        # Maven Dependencies
└── README.md                      # Documentation

```

## 🚀 Prerequisites

Before running the tests, ensure you have the following installed:

1. **Java JDK 11** or higher.
2. **Maven** (Apache Maven).
3. **IntelliJ IDEA** (with Cucumber for Java plugin installed).
4. **Git**.

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

## 🧩 Adding New Tests

To add a new test scenario, follow this flow:

1. **Feature File:** Create a new `.feature` file in `src/test/resources/features`.
2. **Page Object:** Create a Java class in `com.retail.pages`. Define WebElements using `@FindBy`.
3. **Step Definition:** Create a Step Definition class in `com.retail.stepdefinitions` and call the methods from the Page Object.
4. **Run:** Execute `MyTestRunner` to verify.

## 🤝 Contribution

1. Create a new branch: `git checkout -b feature/NewScenario`
2. Commit your changes: `git commit -m "Added checkout test"`
3. Push to branch: `git push origin feature/NewScenario`
4. Create a Pull Request.

---

**Maintainer:** Souvik Dutta