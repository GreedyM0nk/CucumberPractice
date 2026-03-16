# Footer Navigation and Content Test Suite

## Overview

This test suite automates comprehensive testing of the footer section of the Sauce Demo application, including:

- **Footer Navigation Section** - Links like Search and About Us
- **About Us Section** - Section heading and descriptive text
- **Payment Icons** - Accepted payment method icons
- **Footer Bottom Bar** - Copyright text and additional navigation links

## Test Coverage

### Footer Test Scenarios (17 test cases)

#### 1. Footer Navigation Section (@S5)
- ✅ Verify footer displays the "Footer" section heading
- ✅ Verify footer navigation displays all expected links (Search, About Us)
- ✅ Clicking "Search" in footer navigates to the Search page
- ✅ Clicking "About Us" in footer navigates to the About Us page

#### 2. Footer About Us Section (@S5)
- ✅ Verify footer displays the "About Us" section heading
- ✅ Verify footer About Us section contains descriptive text
- ✅ Verify link to Sauce external site is correct

#### 3. Footer Payment Icons (@S5)
- ✅ Verify payment method icons are displayed (Amex, Visa, Mastercard)

#### 4. Footer Bottom Bar (@S5)
- ✅ Verify copyright text is displayed
- ✅ Verify bottom bar navigation links are present
- ✅ Verify "Shopping Cart by Shopify" link exists
- ✅ Clicking "Shopping Cart by Shopify" navigates to Shopify page
- ✅ Clicking footer bottom bar "Search" navigates to Search page
- ✅ Clicking footer bottom bar "About Us" navigates to About Us page

## File Structure

```
src/test/resources/features/
└── header/
    └── footer.feature          # All footer test scenarios

src/test/java/com/retail/
├── pages/
│   └── FooterPage.java         # Footer Page Object
├── runners/
│   └── ParallelRunner5.java    # Test runner for @S5 tag
└── stepdefinitions/
    └── FooterNavigationSteps.java   # Step implementations
```

## Running the Tests

### Run all footer tests (tag @S5)
```bash
mvn test -Dtest=ParallelRunner5
```

### Run tests on specific environment
```bash
mvn test -Dtest=ParallelRunner5 -Psit          # SIT environment
mvn test -Dtest=ParallelRunner5 -Pprod         # Production environment
mvn test -Dtest=ParallelRunner5 -Puat          # UAT (default)
```

### Run tests with gradle (if configured)
```bash
./gradlew test --tests ParallelRunner5
```

### Run in IDE (IntelliJ)
1. Right-click on `ParallelRunner5.java`
2. Select "Run 'ParallelRunner5'"

### View Allure Report
```bash
mvn allure:serve
```

## Page Object Model - FooterPage

The `FooterPage` class encapsulates all footer-related interactions:

### Key Methods

#### Footer Navigation
```java
boolean isFooterNavigationHeadingVisible()        // Check if "Footer" heading visible
String getFooterNavigationHeadingText()           // Get heading text
boolean isLinkInFooterNavigation(String linkText) // Check if link exists
void clickLinkInFooterNavigation(String linkText) // Click navigation link
```

#### About Us Section
```java
WebElement getFooterSection(String sectionName)           // Get section element
boolean footerSectionContainsText(String section, String text)  // Check section text
void clickLinkInAboutUsSection(String linkText)           // Click link in section
String getLinkHrefInAboutUsSection(String linkText)       // Get link href
```

#### Payment Icons
```java
boolean isPaymentIconDisplayed(String altText)  // Check icon by alt text
List<String> getPaymentIconAltTexts()          // Get all payment icon texts
```

#### Bottom Bar
```java
String getFooterBottomBarText()                 // Get bottom bar content
boolean bottomBarContainsText(String text)      // Check for text
boolean isLinkInFooterBottomBar(String linkText) // Check if link exists
void clickLinkInFooterBottomBar(String linkText) // Click bottom bar link
String getLinkHrefInFooterBottomBar(String linkText)  // Get link href
```

#### General
```java
void waitForFooterVisible()   // Wait for footer to load
boolean isFooterVisible()     // Check footer visibility
void scrollToFooter()         // Scroll to footer element
```

## Step Definitions - FooterNavigationSteps

Implements BDD steps for footer testing:

### Navigation Steps
```gherkin
@Then("I should see the heading {string} in the footer navigation section")
@Then("I should see the link {string} in the footer navigation")
@When("I click the {string} link in the footer navigation")
```

### Page Verification Steps
```gherkin
@Then("the page title should be {string}")
@Then("the URL should contain {string}")
```

### About Us Section Steps
```gherkin
@Then("I should see the heading {string} in the footer")
@Then("the footer {string} section should contain the text {string}")
@When("I click the {string} link in the footer About Us section")
```

### Payment Icons Steps
```gherkin
@Then("I should see the payment icon with alt text {string}")
```

### Bottom Bar Steps
```gherkin
@Then("I should see the text {string} in the footer bottom bar")
@Then("I should see the link {string} in the footer bottom bar")
@When("I click the {string} link in the footer bottom bar")
@Then("the link destination should contain {string}")
```

## Feature File - footer.feature

Located at: `src/test/resources/features/header/footer.feature`

All scenarios are tagged with `@S5` for parallel execution and `@footer @header` for grouping.

### Feature Structure
```gherkin
@footer @header
Feature: Footer Navigation and Content Verification
  
  Background:
    Given I am on the Sauce Demo homepage
  
  @S5
  Scenario: Verify footer displays the "Footer" section heading
    # Test implementation
```

## Test Execution Flow

```
1. Start Test
   ↓
2. Navigate to Sauce Demo homepage (Background)
   ↓
3. Verify footer elements are displayed
   ↓
4. Click footer links
   ↓
5. Verify navigation to expected pages
   ↓
6. Assert page titles and URLs
   ↓
7. Complete Test
```

## Expected Test Results

### Success Criteria
- ✅ All footer navigation links are clickable
- ✅ Links navigate to correct pages
- ✅ Page titles match expected values
- ✅ URLs contain expected fragments
- ✅ All payment icons are displayed
- ✅ Footer text content is visible

### Failure Scenarios
- ❌ Footer elements not visible
- ❌ Links return 404 errors
- ❌ Page title doesn't match expected
- ❌ URL doesn't contain expected fragment
- ❌ Payment icons missing alt text

## Parallel Execution

The footer tests run in parallel with other test suites:

```
Parallel Execution (@S5)
├── ParallelRunner1 (@S1 - Add to Cart)
├── ParallelRunner2 (@S2 - Product Filter)
├── ParallelRunner3 (@S3 - Checkout)
├── ParallelRunner4 (@S4 - Login Flow)
└── ParallelRunner5 (@S5 - Footer) ← Running simultaneously
```

Configuration in `pom.xml`:
```xml
<forkCount>5</forkCount>        <!-- 5 parallel runners -->
<threadCount>5</threadCount>     <!-- 5 threads per runner -->
<parallel>classes</parallel>     <!-- Parallel by class -->
```

## Extending the Tests

### Add New Footer Scenario

1. **Add feature scenario** to `footer.feature`:
```gherkin
@S5
Scenario: Verify new footer feature
  When I perform an action
  Then I should see expected result
```

2. **Implement step definitions** in `FooterNavigationSteps.java`:
```java
@Then("I should see expected result")
public void verifyExpectedResult() {
    // Implementation
}
```

3. **Add page object methods** in `FooterPage.java`:
```java
public void performAction() {
    // Implementation
}
```

4. **Run tests**:
```bash
mvn test -Dtest=ParallelRunner5
```

## Troubleshooting

### Issue: Footer elements not found
**Solution**: Verify footer CSS selectors match actual DOM structure
```java
// Debug: Print page source
System.out.println(driver.getPageSource());
```

### Issue: Locators timeout
**Solution**: Increase WebDriverWait timeout
```java
wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Increase from 10
```

### Issue: Links navigate to wrong pages
**Solution**: Verify link href attributes
```java
String href = footerLink.getAttribute("href");
System.out.println("Link href: " + href);
```

### Issue: Allure report shows 0 tests
**Solution**: Verify @S5 tags are present on scenarios
```gherkin
@S5  ← Required for ParallelRunner5
Scenario: Your scenario
```

## Best Practices

1. ✅ **Use Page Objects** - Encapsulate footer interactions in FooterPage
2. ✅ **Explicit Waits** - Use WebDriverWait instead of Thread.sleep()
3. ✅ **Clear Assertions** - Use descriptive assertion messages
4. ✅ **Maintenance** - Update locators when footer HTML changes
5. ✅ **Parallel Safety** - Avoid shared state between test runners
6. ✅ **Meaningful Tags** - Use @S5 for proper grouping

## CI/CD Integration

These tests are automatically executed by GitHub Actions on:
- Push to `main` branch
- Pull requests to `main`
- Manual workflow dispatch

View reports at: `https://<username>.github.io/<repo-name>/`

## Related Documentation

- [Cucumber Feature File Format](https://cucumber.io/docs/gherkin/)
- [Selenium WebDriver Documentation](https://www.selenium.dev/documentation/)
- [Page Object Model Pattern](https://www.selenium.dev/documentation/webdriver/pom/)
- [Allure Report Guide](https://docs.qameta.io/allure/)
