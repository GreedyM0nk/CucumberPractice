# Retail Automation - Complete Project Structure Analysis

## Overview
This is a comprehensive Cucumber-based Test Automation Framework for testing the Shopify Sauce Demo store (`https://sauce-demo.myshopify.com/`). The project uses Selenium 4, Cucumber 7, and supports parallel execution with multiple reporting formats (Allure, ExtentReports).

**Target Application**: Shopify Sauce Demo Store
**Testing Framework**: Cucumber (BDD)
**Language**: Java 21
**Browser Support**: Chrome, Firefox, Edge
**CI/CD**: GitHub Actions ready (headless mode support)

---

## 1. FEATURE FILES (src/test/resources/features)

### Directory Structure
```
src/test/resources/features/
├── authentication/
│   └── login.feature
├── footer/
│   └── footer.feature
├── header/
│   └── header.feature
└── product/
    └── addToCart.feature
```

### 1.1 authentication/login.feature
**Purpose**: Tests user authentication flows (login and signup)
**Target URL**: https://sauce-demo.myshopify.com/account/

**Scenarios Implemented** (2):
- **@Smoke @Authentication @Signup - "User signs up with a new account"**
  - Steps: Navigate home → Click signup → Enter first/last name → Enter email/password → Create account
  - Verifications: Account created successfully, User logged in
  
- **@Smoke @Authentication @Login - "User logs in with existing credentials"**
  - Steps: Navigate home → Click login → Enter email/password → Click login button
  - Verifications: User logged in successfully, Profile accessible

**Key Implementation Details**:
- Uses LoginPage object model
- Generates dynamic test emails with timestamp to avoid conflicts
- Includes explicit waits and assertion validations

---

### 1.2 header/header.feature
**Purpose**: Comprehensive tests for header navigation bar, logo, tagline, and side navigation
**Target URL**: https://sauce-demo.myshopify.com/

**Scenarios Implemented** (14+):
- **Top Navigation Bar** (6 scenarios)
  - @S6: Verify all links visible (Search, About Us, Log In, Sign up, My Cart, Check Out)
  - @S6: Click navigation links and verify page transitions
  - Validates: Page titles and URL fragments

- **Site Logo and Tagline** (2 scenarios)
  - @S6: Verify logo with alt text "Sauce Demo"
  - @S6: Verify tagline "Just a demo site showing off what Sauce can do."
  - @S6: Clicking logo navigates back to homepage

- **Side Navigation** (6+ scenarios)
  - @S6: Verify all side nav links (Home, Catalog, Blog, About Us, Wish list, Refer a friend)
  - @S6: Click side nav links and verify page transitions

**Key Implementation Details**:
- Uses HeaderPage object model with dynamic link finding
- Supports multiple navigation structures
- Validates both visible links and navigation destinations

---

### 1.3 footer/footer.feature
**Purpose**: Comprehensive footer section testing including navigation, content, and payment icons
**Target URL**: https://sauce-demo.myshopify.com/ (footer tested on multiple pages)

**Scenarios Implemented** (14+):
- **Footer Navigation Section** (3 scenarios)
  - @S5: Verify "Footer" heading visible
  - @S5: Verify footer nav links (Search, About Us)
  - @S5: Click footer nav links and verify page transitions

- **About Us Section** (3 scenarios)
  - @S5: Verify "About Us" heading in footer
  - @S5: Verify descriptive text about Sauce Demo
  - @S5: Verify "Sauce" external link points to http://sauceapp.io

- **Payment Method Icons** (1 scenario)
  - @S5: Verify payment icons visible (Amex, Visa, Mastercard)

- **Footer Bottom Bar** (5 scenarios)
  - @S5: Verify copyright text "Copyright © 2026 Sauce Demo."
  - @S5: Verify bottom bar navigation links
  - @S5: Verify "Shopping Cart by Shopify" external link
  - @S5: Click bottom bar links and verify transitions

**Key Implementation Details**:
- Uses FooterPage object model
- Tests multiple sections within footer
- Validates external link destinations

---

### 1.4 product/addToCart.feature
**Purpose**: Product catalog browsing and shopping cart functionality
**Target URL**: https://sauce-demo.myshopify.com/collections/all

**Scenarios Implemented** (4):
- **@Smoke @Product @Cart @S1 - "Add first product to cart from catalogue page"**
  - Steps: Navigate to catalog → Add first product to cart
  - Verifications: Cart count updated, Product visible in cart

- **@Regression @Product @S2 - "View first product details"**
  - Steps: Navigate to catalog → Click first product
  - Verifications: Product details page displayed, Price and description visible

- **@Product @S3 - "Verify products are displayed on catalogue page"**
  - Steps: Navigate to catalog
  - Verifications: 3 products displayed, First product name visible, First product price visible

- **@Smoke @Product @Cart @S4 - "Add product to cart from product details page"**
  - Steps: Navigate to catalog → Click first product → Add to cart
  - Verifications: Cart count updated

**Key Implementation Details**:
- Uses ProductPage and CartPage object models
- Tests both catalog and product detail pages
- Validates cart count updates with explicit waits
- Dynamic product list handling

---

## 2. PAGE OBJECT CLASSES (src/test/java/com/retail/pages)

### 2.1 BasePage.java
**Purpose**: Base class providing common WebDriver operations and waits
**Extends**: None (standalone base)
**Key Methods**:

#### Wait Methods
- `waitForElementToBeVisible(WebElement)` - Waits for element visibility (10s default)
- `waitForElementToBeClickable(WebElement)` - Waits for element to be clickable
- `getCustomWait(int timeoutInSeconds)` - Creates custom wait with specified timeout

#### Interaction Methods
- `clickElement(WebElement)` - Click with explicit wait
- `sendKeys(WebElement, String)` - Send text input with clear
- `getText(WebElement)` - Get text with wait
- `isElementDisplayed(WebElement)` - Safe visibility check

#### Utility Methods
- `scrollToElement(WebElement)` - JavaScript scroll
- `clickElementByJS(WebElement)` - JavaScript click (for hidden elements)
- `getPageTitle()` - Get page title
- `getCurrentUrl()` - Get current URL

**Default Wait Time**: 10 seconds (can be customized)

---

### 2.2 LoginPage.java
**Purpose**: Authentication page interactions (login/signup)
**Extends**: BasePage
**Target URL**: https://sauce-demo.myshopify.com/account/

**Locators** (organized by section):

#### Account Navigation Links
- `loginLink` → `a[href='/account/login']`
- `signupLink` → `a[href='/account/register']`

#### Login Form (page: /account/login)
- `loginEmailInput` → `input[name='customer[email]']`
- `loginPasswordInput` → `input[name='customer[password]']`
- `loginButton` → `input[type='submit'][value='Sign In']`

#### Signup Form (page: /account/register)
- `signupFirstNameInput` → `input[name='customer[first_name]']`
- `signupLastNameInput` → `input[name='customer[last_name]']`
- `signupEmailInput` → `input[name='customer[email]']`
- `signupPasswordInput` → `input[name='customer[password]']`
- `createAccountButton` → `input[type='submit'][value='Create']`

#### Account Indicators
- `logoutLink` → `a[href='/account/logout']` or similar
- `accountPageElement` → CSS classes: `.account-page`, `.account-welcome`, `h1.account-title`

**Key Methods**:
- `navigateToHome()` - Navigate to base URL
- `clickLoginLink()` - Click login link or navigate directly
- `clickSignupLink()` - Click signup link or navigate directly
- `enterLoginEmail(String)` - Enter email for login
- `enterLoginPassword(String)` - Enter password for login
- `clickLoginButton()` - Submit login form
- `enterSignupFirstName(String)` - Enter first name
- `enterSignupLastName(String)` - Enter last name
- `enterSignupEmail(String)` - Enter email for signup
- `enterSignupPassword(String)` - Enter password for signup
- `clickCreateAccountButton()` - Submit signup form
- `isUserLoggedIn()` - Check if logout link visible (indicates logged-in state)

**Error Handling**: 
- Gracefully handles missing element and navigates directly via URL if link not found

---

### 2.3 ProductPage.java
**Purpose**: Product catalog and product detail page interactions
**Extends**: BasePage
**Target URL**: https://sauce-demo.myshopify.com/collections/all (catalog)

**Locators**:

#### Product Elements
- `productLinks` → `a[id^='product-']` (List of all products)
- `firstProduct` → `a#product-1`
- `firstProductName` → `//a[@id='product-1']//h3`
- `firstProductPrice` → `//a[@id='product-1']//h4`

#### Cart Elements
- `addToCartButton` → `input[type='submit'][value='Add to Cart']` or `button[name='add']`
- `cartCount` → `.cart-target` or `#cart-target-desktop`
- `cartAnimation` → `#cart-animation`
- `productVariantSelect` → `select#product-select` or `select[name='id']`

**Key Methods**:

#### Navigation
- `navigateToCatalogue()` - Navigate to product catalog (uses DriverFactory base URL)

#### Product Information
- `getProductCount()` - Returns number of products displayed
- `getFirstProductName()` - Returns first product name (with wait)
- `getFirstProductPrice()` - Returns first product price (with wait)

#### Cart Operations
- `addToCart()` - Click add to cart button
- `getCartCount()` - Get current cart count as string
- `getStableCartCount()` - Get cart count with wait for animation (5s)
- `isCartCountUpdated(String expectedCount)` - Verify cart count matches expected (10s wait)

#### Product Interaction
- `clickFirstProduct()` - Click first product with scroll

**Special Implementation Details**:
- `getStableCartCount()` waits up to 5 seconds for cart badge element, preventing reads during animation
- URL always derived from DriverFactory base URL (environment-specific, never hardcoded)
- Handles CSS selector variants for different screen sizes

---

### 2.4 CartPage.java
**Purpose**: Shopping cart page interactions
**Extends**: BasePage
**Target URL**: https://sauce-demo.myshopify.com/cart

**Locators**:

#### Cart Display
- `cartCount` → `.cart-target`, `#cart-target-desktop`, `#cart-target-mobile`
- `cartLink` → `a.toggle-drawer.cart` or `a[href='/cart']`
- `cartDrawer` → `#drawer .container` or `.cart-wrapper`

#### Cart Content
- `emptyCartMessage` → `.empty` or `.cart-empty`
- `cartItems` → `.line-item`, `.cart-item`, or `tbody tr` (List)
- `firstItemName` → `.item-title` or `.cart-item-title`
- `firstItemPrice` → `.item-price` or `.cart-item-price`

#### Cart Operations
- `checkoutButton` → `a.checkout` or `a[href*='checkout']`
- `cartSubtotal` → `.subtotal` or `.cart-subtotal`
- `drawerElement` → `#drawer`

**Key Methods**:

#### Navigation
- `navigateToCart()` - Navigate to cart page (URL from DriverFactory base URL)
- `openCart()` - Click cart link to open drawer

#### Cart Information
- `getCartItemCount()` - Get number of items as integer
- `getNumberOfItemsInCart()` - Get item count from cart items list
- `isCartEmpty()` - Check if cart is empty

#### Cart Verification
- `verifyCartCount(int expectedCount)` - Wait 10s for cart count to match expected value
- `isCartDrawerDisplayed()` - Check if cart drawer is visible

#### Checkout
- `proceedToCheckout()` - Click checkout button

**Special Implementation Details**:
- Handles responsive design (desktop and mobile cart selectors)
- Uses regex pattern matching for cart count extraction
- Safe integer conversion with fallback to 0

---

### 2.5 HeaderPage.java
**Purpose**: Header navigation including top nav, logo, tagline, and side navigation
**Constructor**: Requires WebDriver, initializes PageFactory and WebDriverWait

**Locators Organized by Section**:

#### Top Navigation Bar
- `topNavigation` → `nav`, `header nav`, `.header-nav`
- `topNavLinks` → `header a`, `nav a` (List)

#### Logo & Tagline
- `siteLogo` → `header .logo img`, `.site-logo img`, `header img[alt*='logo']`
- `tagline` → `.tagline`, `.site-tagline`, `header .tagline`, `h1 + p`

#### Side Navigation
- `sideNavigation` → `.sidebar`, `.side-nav`, `.left-menu`, `aside nav`
- `sideNavLinks` → `.sidebar a`, `.side-nav a`, `.left-menu a`, `aside nav a` (List)

#### Social Media Icons
- `facebookIcon`, `twitterIcon`, `instagramIcon`, `pinterestIcon`, `rssIcon`
- Locators: `a[href*='facebook.com']`, etc., or `.social-icon.facebook`, etc.

**Key Methods**:

#### Top Navigation
- `isLinkInTopNavigation(String linkText)` - Check if link visible in top nav
- `clickLinkInTopNavigation(String linkText)` - Click top nav link by text

#### Logo & Tagline
- `isSiteLogoVisible(String altText)` - Verify logo visible with alt text
- `getSiteLogoAltText()` - Get logo alt text
- `isTaglineVisible()` - Check if tagline visible
- `getTaglineText()` - Get tagline text
- `clickSiteLogo(String logoName)` - Click logo by name

#### Side Navigation
- `isLinkInSideNavigation(String linkText)` - Check if link visible in side nav
- `clickLinkInSideNavigation(String linkText)` - Click side nav link by text
- `getLinkHrefInSideNavigation(String linkText)` - Get href attribute of side nav link

**Exception Handling**:
- Throws RuntimeException with descriptive message if element not found
- Uses stream API for dynamic link finding

---

### 2.6 FooterPage.java
**Purpose**: Footer section interactions including navigation, content verification, and payment icons
**Constructor**: Requires WebDriver, initializes PageFactory and WebDriverWait

**Locators Organized by Section**:

#### Footer Structure
- `footerElement` → `footer` (main footer tag)
- `footerNavigation` → `footer nav`, `footer .footer-navigation`
- `footerBottomBar` → `footer .footer-bottom`, `footer .footer-bar`
- `footerSections` → `footer .footer-section`, `footer .footer-about` (List)

#### Footer Navigation
- `footerNavHeading` → `footer nav h3`
- `footerNavLinks` → `footer nav a` (List)

#### Section Content
- `footerSectionHeadings` → `footer .footer-section h3`, `footer .footer-about h3` (List)

#### Payment Methods
- `paymentIcons` → `footer .footer-payments img`, `footer .payment-icons img` (List)

#### Bottom Bar
- `bottomBarLinks` → `footer .footer-bottom a`, `footer .footer-bar a` (List)

**Key Methods**:

#### Navigation Section
- `isFooterNavigationHeadingVisible()` - Check if "Footer" heading visible
- `getFooterNavigationHeadingText()` - Get footer navigation heading text
- `isLinkInFooterNavigation(String linkText)` - Check if link exists in footer nav
- `clickLinkInFooterNavigation(String linkText)` - Click footer nav link

#### About Us Section
- `getFooterSection(String sectionName)` - Get footer section by name (returns WebElement)
- `footerSectionContainsText(String sectionName, String text)` - Verify section contains text

#### Payment Icons
- `arePaymentIconsDisplayed()` - Check if payment icons visible
- `getPaymentIconCount()` - Get number of payment icons

#### Bottom Bar
- `isLinkInFooterBottomBar(String linkText)` - Check if link in bottom bar
- `clickLinkInFooterBottomBar(String linkText)` - Click bottom bar link
- `getFooterBottomBarText()` - Get bottom bar text (copyright, etc.)

**Exception Handling**:
- Throws RuntimeException if element/section not found
- Stream-based filtering for dynamic element finding

---

## 3. STEP DEFINITION CLASSES (src/test/java/com/retail/stepdefinitions)

### 3.1 ApplicationHooks.java
**Purpose**: Cucumber hooks for test setup, teardown, and reporting
**Framework**: Cucumber 7 lifecycle hooks with @Before and @After

**Hooks Implemented** (3, with execution order):

#### 1. @Before(order = 0) - setUp()
**Purpose**: Initialize configuration and browser for each scenario
**Execution Order**: Runs FIRST before any other @Before hooks

**Implementation**:
```
1. Load properties via ConfigReader.init_prop()
2. Initialize WebDriver via DriverFactory.init_driver(prop)
```

**Local Variable Design**: Properties and driver references are local to method, preventing ThreadLocal race conditions in parallel execution

---

#### 2. @After(order = 2) - takeScreenshotOnFailure(Scenario)
**Purpose**: Capture screenshot when test fails and attach to Cucumber report
**Execution Order**: Runs FIRST among @After hooks (higher order = earlier execution)

**Implementation**:
```
1. Get driver from ThreadLocal (DriverFactory.getDriver())
2. Check if scenario failed (scenario.isFailed())
3. Take screenshot using TakesScreenshot interface
4. Attach screenshot to scenario with image/png MIME type
5. Screenshot filename: scenario name with underscores (spaces replaced)
```

**Output**: Screenshot attached to Cucumber/Allure/ExtentReports in HTML report

---

#### 3. @After(order = 1) - reportStatusToBrowserStack(Scenario)
**Purpose**: Report test status to BrowserStack dashboard (if using BrowserStack)
**Execution Order**: Runs SECOND (between screenshot and quit)

**Implementation**:
```
1. Get driver from ThreadLocal
2. Determine status: "passed" or "failed"
3. Set failure reason if failed: scenario name + " - Test Failed"
4. Execute BrowserStack SDK script via JavascriptExecutor:
   browserstack_executor: {"action": "setSessionStatus", ...}
5. Silent catch if BrowserStack not active (local execution)
```

**Usage**: Only active when running on BrowserStack cloud platform; silently skipped for local execution

---

#### 4. @After(order = 0) - quitBrowser()
**Purpose**: Clean up WebDriver and prevent ThreadLocal memory leaks
**Execution Order**: Runs LAST after all other @After hooks

**Implementation**:
```
1. Get driver from ThreadLocal (DriverFactory.getDriver())
2. If driver exists: driver.quit()
3. Clean ThreadLocal: DriverFactory.removeDriver()
```

**ThreadLocal Cleanup**: Critical for preventing memory leaks in parallel execution

---

### 3.2 CommonSteps.java
**Purpose**: Shared step definitions used across multiple test suites
**Locators**: None (uses direct WebDriver and waits)

**Steps Implemented**:

#### Navigation Steps
```gherkin
@Given("I am on the Sauce Demo homepage")
  → Navigate to https://sauce-demo.myshopify.com/
  → Wait for "sauce-demo" in URL

@Given("I am on the Sauce Demo homepage {string}")
  → Navigate to provided URL
  → Wait for URL validation

@Given("I am on the {string} page")
  → Build full URL from base + page path
  → Navigate and wait

@Given("I refresh the current page")
  → driver.navigate().refresh()
```

#### Verification Steps
```gherkin
@Then("the page title should be {string}")
  → Get page title via driver.getTitle()
  → Assert equals expected

@Then("the URL should contain {string}")
  → Get current URL
  → Assert contains substring

@Then("the URL should be {string}")
  → Get current URL
  → Assert exact match

@Then("the link destination should be {string}")
  → Get current URL after clicking link
  → Assert contains or exact match
```

**Assertions**: Uses JUnit 4 assertions (assertEquals, assertTrue)

---

### 3.3 AuthenticationSteps.java
**Purpose**: Step definitions for login/signup test scenarios
**Page Object**: LoginPage

**Lazy Initialization Pattern**:
```java
private LoginPage loginPage;
private LoginPage getLoginPage() {
    if (loginPage == null) {
        loginPage = new LoginPage(DriverFactory.getDriver());
    }
    return loginPage;
}
```

**Reason**: Prevents NullPointerException when field is initialized before @Before hook creates driver

**Steps Implemented**:

#### Navigation
```gherkin
@Given("user is on the home page")
  → Reset loginPage to null (fresh instance)
  → Call getLoginPage().navigateToHome()
```

#### Login Flow
```gherkin
@When("user clicks on login link")
  → Click login link
  → Thread.sleep(1000) - stabilize form

@When("user enters email for login")
  → Generate testEmail: "testuser{timestamp}@test.com"
  → Enter into loginEmailInput
  
@When("user enters password for login")
  → Enter "Test@123456"
  → Store in step execution

@When("user clicks the login button")
  → Click login button
  → Thread.sleep(2000) - wait for navigation
```

#### Signup Flow
```gherkin
@When("user clicks on sign up link")
  → Click signup link via LoginPage

@When("user enters first name for signup")
  → Enter "Test"

@When("user enters last name for signup")
  → Enter "User"

@When("user enters email for signup")
  → Generate newUser{timestamp}@test.com

@When("user enters password for signup")
  → Enter "Test@123456"

@When("user clicks the create account button")
  → Click create account button
  → Thread.sleep(2000)
```

#### Assertions
```gherkin
@Then("user should be logged in")
  → Assert isUserLoggedIn() returns true

@Then("user should be logged in successfully")
  → Assert isUserLoggedIn() returns true

@Then("user account should be created successfully")
  → Assert current URL contains "/account"

@Then("user profile should be accessible")
  → Assert profile link/element visible
```

**Dynamic Test Data**: Generates unique emails with timestamp to avoid duplicate account conflicts

---

### 3.4 ProductPageSteps.java
**Purpose**: Step definitions for product catalog and shopping cart interactions
**Page Objects**: ProductPage, CartPage

**Lazy Initialization Pattern**:
```java
private ProductPage productPage;
private CartPage cartPage;
private ProductPage getProductPage() { ... }
private CartPage getCartPage() { ... }
```

**Reason**: Same as AuthenticationSteps - prevents driver null issues in parallel execution

**Steps Implemented**:

#### Product Catalog Navigation
```gherkin
@Given("user is on the product catalogue page")
  → Reset page objects to null (fresh instances)
  → Navigate to catalog via ProductPage.navigateToCatalogue()
  → Assert product is displayed
```

#### Product Interaction
```gherkin
@When("user clicks on the first product")
  → Get first product name
  → Click firstProduct element
  → Scroll into view

@When("user adds the first product to cart")
  → Get stable cart count (before action)
  → Click first product (navigate to detail page)
  → Call addToCart()

@When("user adds product to cart")
  → Capture stable count if null
  → Call addToCart()
```

#### Verifications
```gherkin
@Then("user should see product details page")
  → Wait for URL containing "/products/"
  → Assert URL contains product path

@Then("product details should include price and description")
  → Verify price element visible
  → Verify description element visible

@Then("cart count should be updated")
  → Wait for cart count to increment
  → Assert new count > previous count (10s wait)

@Then("product should be visible in cart")
  → Navigate to cartpage
  → Assert number of items > 0

@Then("user should see 3 product in the catalogue")
  → Assert getProductCount() == 3

@Then("first product name should be displayed")
  → Assert firstProductName visible

@Then("first product price should be displayed")
  → Assert firstProductPrice visible
```

**Cart Count Tracking**:
- `initialCartCount` stored before adding to cart
- Used for validating increment
- Handles timing issues with `getStableCartCount()`

---

### 3.5 HeaderNavigationSteps.java
**Purpose**: Step definitions for header and top navigation testing
**Page Object**: HeaderPage

**Initialization**:
```java
public HeaderNavigationSteps() {
    this.webDriver = DriverFactory.getDriver();
    this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
    this.headerPage = new HeaderPage(webDriver);
}
```

**Note**: Direct initialization (not lazy) - step definition constructor called after @Before hook

**Steps Implemented**:

#### Top Navigation Bar
```gherkin
@Then("I should see the link {string} in the top navigation bar")
  → Call headerPage.isLinkInTopNavigation(linkText)
  → Assert true

@When("I click the {string} link in the top navigation bar")
  → Call headerPage.clickLinkInTopNavigation(linkText)
```

#### Logo & Tagline
```gherkin
@Then("I should see the site logo with alt text {string}")
  → Call headerPage.isSiteLogoVisible(altText)
  → Assert true

@Then("I should see the tagline {string}")
  → Get tagline text
  → Assert equals expected

@When("I click the {string} site logo")
  → Click logo by site name
```

#### Side Navigation
```gherkin
@Then("I should see the link {string} in the side navigation")
  → Call headerPage.isLinkInSideNavigation(linkText)
  → Assert true

@When("I click the {string} link in the side navigation")
  → Call headerPage.clickLinkInSideNavigation(linkText)

@Then("the {string} link in side navigation should have valid href")
  → Get href attribute
  → Assert not null
  → Assert not empty
```

---

### 3.6 FooterNavigationSteps.java
**Purpose**: Step definitions for footer content and navigation testing
**Page Object**: FooterPage (implied, uses direct locators)

**Initialization**:
```java
public FooterNavigationSteps() {
    this.driver = DriverFactory.getDriver();
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
}
```

**Steps Implemented**:

#### Footer Navigation Section
```gherkin
@Then("I should see the heading {string} in the footer navigation section")
  → Wait for footer nav presence
  → Find heading by XPath
  → Assert displayed

@Then("I should see the link {string} in the footer navigation")
  → Find footer nav element
  → Find link by text
  → Assert displayed

@When("I click the {string} link in the footer navigation")
  → Find footer nav and link
  → Click link
  → Wait for URL change via getExpectedUrlFragment()
```

#### Footer Content
```gherkin
@Then("I should see the heading {string} in the footer")
  → Wait for footer element
  → Find heading by XPath
  → Assert displayed

@Then("the footer {string} section should contain the text {string}")/
  → Find section by heading name
  → Get ancestor container
  → Assert section text contains expected text

@When("I click the {string} link in the footer About Us section")
  → Find footer footer element
  → Find link in About Us section
  → Click link
```

#### Payment Icons
```gherkin
@Then("I should see the payment icon with alt text {string}")
  → Find payment icons
  → Check alt text matches
  → Assert found and displayed
```

#### Bottom Bar
```gherkin
@Then("I should see the text {string} in the footer bottom bar")
  → Find footer bottom bar
  → Get text
  → Assert contains expected text

@Then("I should see the link {string} in the footer bottom bar")
  → Find bottom bar links
  → Find link by text
  → Assert displayed

@When("I click the {string} link in the footer bottom bar")
  → Find bottom bar
  → Find and click link
  → Wait for URL change

@Then("the link destination should contain {string}")
  → Get current URL
  → Assert contains or exact match
```

---

## 4. TEST UTILITIES (src/test/java/com/retail/utils)

### 4.1 DriverFactory.java
**Purpose**: WebDriver instance management with ThreadLocal for parallel execution
**Pattern**: Thread-safe singleton via ThreadLocal

**Static ThreadLocal Variables**:
```java
private static final ThreadLocal<WebDriver> tlDriver
    → One WebDriver per thread

private static final ThreadLocal<String> tlBaseUrl
    → Base URL for environment (prevents hardcoding)
```

**Key Methods**:

#### init_driver(Properties prop)
**Purpose**: Create and configure WebDriver for current thread

**Process**:
1. Get browser name from properties (chrome, firefox, edge)
2. Detect CI environment: 
   - Check `$CI` environment variable
   - Check `$HEADLESS` environment variable
3. Resolve headless mode priority:
   - CI environment detection wins
   - Otherwise uses headless property
   - System property `-Dheadless=true` can override
4. Create browser-specific WebDriver via createLocalDriver()
5. Init driver to ThreadLocal
6. Delete all cookies
7. Maximize window
8. Set implicit wait (from properties, default 10s)
9. Navigate to base URL

**Output**: WebDriver stored in ThreadLocal, ready for use

---

#### createLocalDriver(String browserName, boolean headless)
**Purpose**: Create browser-specific WebDriver instance

**Chrome Implementation** (default):
```
Options:
  - --disable-blink-features=AutomationControlled (avoid detection)
  - --disable-extensions
  - --headless=new (if headless mode)
  - --no-sandbox (if headless)
  - --disable-dev-shm-usage (if headless)
  - --disable-gpu (if headless)
  - --window-size=1920,1080 (if headless)
```

**Firefox Implementation**:
```
Options:
  - --headless (if headless mode)
```

**Edge Implementation**:
```
Options:
  - --headless=new (if headless mode)
```

**Exception Handling**: Throws IllegalArgumentException for unsupported browser

---

#### Static Access Methods
```java
public static WebDriver getDriver()
  → Returns driver from current thread ThreadLocal
  → Thread-safe: no synchronization needed

public static String getBaseUrl()
  → Returns base URL from current thread ThreadLocal
  → Used by all page objects to build dynamic URLs

public static void removeDriver()
  → Cleans up ThreadLocal entry
  → CRITICAL: Prevents memory leaks in parallel execution
```

**Thread Safety**: ThreadLocal.get() is inherently thread-safe - no synchronization needed

---

### 4.2 ConfigReader.java
**Purpose**: Load two-layer configuration (base + environment-specific)
**Pattern**: Two-layer properties system with environment resolution

**Configuration Files Loaded**:
1. `config.properties` - Base config (browser, waits, headless toggle, env default)
2. `config-{env}.properties` - Environment-specific (url, username, password)

**Environment Resolution Order**:
```
Priority 1: JVM system property -Denv=<name>
            (set by Maven Surefire from active profile)

Priority 2: env property in config.properties
            (fallback default)

Ultimate Fallback: "uat"
```

**Maven Profile Integration**:
```
mvn test -Puat     → Loads config-uat.properties
mvn test -Psit     → Loads config-sit.properties
mvn test -Pprod    → Loads config-prod.properties
mvn test -Denv=sit → Direct override without profile
```

**Process** (init_prop() method):
1. Create new Properties object (thread-safe, local variable)
2. Load base config from `config/config.properties`
3. Resolve environment name (system property → config property → "uat")
4. Load environment-specific config from `config/config-{env}.properties`
5. Allow system property `-Dheadless=true` to override
6. Print debug messages to console
7. Return merged Properties object

**System Property Overrides**:
```
-Denv=sit              → Override environment
-Dheadless=true        → Force headless mode (for CI/CD)
-Dheadless=false       → Force headed mode (override CI)
```

**Thread Safety**: All local variables - no shared state, safe for parallel execution

---

### 4.3 TestDataReader.java
**Purpose**: Read test data from JSON files in src/test/resources/testdata/
**Dependency**: Gson 2.10.1 for JSON parsing

**Path Configuration**:
```
private static final String TEST_DATA_PATH = "src/test/resources/testdata/"
```

**Static Utility Methods**:

#### readTestData(String fileName)
```java
Purpose: Read JSON file and return as JsonObject
Parameter: fileName (e.g., "users.json")
Returns: JsonObject -raw parsing
Throws: RuntimeException if file not found
```

**Usage Example**:
```java
JsonObject testData = TestDataReader.readTestData("users.json");
```

---

#### getUserByType(String userType)
```java
Purpose: Get user credentials by type (standard, admin, guest)
Parameter: userType (e.g., "standard", "admin", "guest")
Returns: JsonObject with user credentials
Expected JSON: {
  "users": [
    {"type": "standard", "email": "...", "password": "..."},
    {"type": "admin", ...},
    {"type": "guest", ...}
  ]
}
Throws: RuntimeException if type not found
```

**Usage Example**:
```java
JsonObject user = TestDataReader.getUserByType("admin");
String email = user.get("email").getAsString();
```

---

#### getProductById(String productId)
```java
Purpose: Get product by ID
Parameter: productId (e.g., "prod-001")
Returns: JsonObject with product details
Expected JSON: {
  "products": [
    {"id": "prod-001", "name": "...", "price": "..."},
    ...
  ]
}
Throws: RuntimeException if product not found
```

---

#### getProductByName(String productName)
```java
Purpose: Get product by name
Parameter: productName (e.g., "Backpack")
Returns: JsonObject with product details
Case-Insensitive: Uses equalsIgnoreCase()
Throws: RuntimeException if not found
```

**Future Use Cases**:
- Test data externalization reduces hardcoding
- Enables parameterized tests
- Supports multi-environment test data

---

## 5. TEST RUNNERS (src/test/java/com/retail/runners)

### 5.1 Runner Architecture Overview

**Total Runners**: 11
**Execution Strategies**:
- **Sequential**: MyTestRunner, UITestRunner, APITestRunner
- **Smoke**: SmokeTestRunner (@Smoke tag)
- **Regression**: RegressionTestRunner (@Regression or @Smoke)
- **Parallel**: ParallelRunner1-6 (each @Sx tag in parallel JVM thread)

**Key Note**: Runners contain ONLY step definition glue path, NOT runner classes itself (anti-pattern)

---

### 5.2 MyTestRunner.java
**Purpose**: Main test runner - executes all features
**Execution**: Sequential
**Features**: All (no tag filter)
**Glue**: `com.retail.stepdefinitions` (step definitions & hooks only)

**Plugin Configuration**:
```
1. pretty → Console output
2. html:target/cucumber-reports/cucumber.html → Standard HTML report
3. json:target/cucumber-reports/cucumber.json → JSON for CI/CD
4. com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter → ExtentReports HTML
5. io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm → Allure report
```

**Monochrome**: true (removes ANSI colors for CI/CD compatibility)

---

### 5.3 SmokeTestRunner.java
**Purpose**: Smoke tests - critical path scenarios
**Filter**: `tags = "@Smoke"`
**Output**: 
- `target/cucumber-reports/smoke-report.html`
- `target/cucumber-reports/smoke-report.json`

**Execution**: Sequential via `mvn test -Dtest=SmokeTestRunner`

**Expected Scenarios**: Login, Add product to cart (critical paths)

---

### 5.4 RegressionTestRunner.java
**Purpose**: Full regression suite
**Filter**: `tags = "@Regression or @Smoke"`
**Output**: `target/cucumber-reports/regression-report.{html,json}`

**Includes**: All @Regression and @Smoke scenarios

**Execution**: `mvn test -Dtest=RegressionTestRunner`

---

### 5.5 UITestRunner.java, APITestRunner.java
**Purpose**: Specialized test suites
- **UITestRunner**: UI-specific tests
- **APITestRunner**: API-specific tests (if backend API testing)

**Filters**: To be defined by project requirements

---

### 5.6 ParallelRunner1 through ParallelRunner6
**Purpose**: Distributed test execution
**Strategy**: Each runner handles one scenario tag (@S1 through @S6)
**Execution Model**: Each runs in separate JVM fork via Maven Surefire

**Configuration** (ParallelRunner1 example):
```java
@CucumberOptions(
    features = {"src/test/resources/features"},
    glue = {"com.retail.stepdefinitions"},
    tags = "@S1",  // Only scenario tagged @S1
    plugin = {
        "pretty",
        "html:target/cucumber-reports/parallel-runner1.html",
        "json:target/cucumber-reports/parallel-runner1.json",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
    }
)
```

**Mapping**:
- ParallelRunner1 → @S1 (Add product to cart from catalog - addToCart.feature)
- ParallelRunner2 → @S2 (View product details - addToCart.feature)
- ParallelRunner3 → @S3 (Verify products displayed - addToCart.feature)
- ParallelRunner4 → @S4 (Add product to cart from detail - addToCart.feature)
- ParallelRunner5 → @S5 (Footer tests - footer.feature)
- ParallelRunner6 → @S6 (Header tests - header.feature)

**Parallel Execution Command**:
```bash
mvn test -DparallelRun=true
# or individual runner
mvn test -Dtest=ParallelRunner1
```

**Benefits**:
- 6x faster execution (in different threads)
- Isolated WebDriver per scenario
- No state sharing via ThreadLocal (isolated tlDriver)

---

## 6. CONFIGURATION FILES (src/test/resources/config)

### 6.1 config.properties (Base Configuration)
**Purpose**: Common browser and wait settings
**Usage**: Loaded first by ConfigReader, then environment-specific overrides

```properties
browser=chrome
# Supported: chrome, firefox, edge

env=uat
# Default environment if not specified via -Denv or Maven profile

headless=false
# Set true for CI/CD. Override via -Dheadless=true

implicitWait=10
# Implicit wait (seconds) - set on driver.manage().timeouts()

explicitWait=10
# Explicit wait (seconds) - used in WebDriverWait (DEFAULT_WAIT_TIME)

pageLoadTimeout=30
# Page load timeout (seconds)

scriptTimeout=30
# Script execution timeout (seconds)
```

---

### 6.2 config-uat.properties (UAT Environment)
**Purpose**: UAT-specific configuration (URL, credentials)
**When Loaded**: Via Maven profile `-Puat` or system property `-Denv=uat`

**Expected Properties**:
```properties
url=https://sauce-demo.myshopify.com/
# Base URL for all tests (used by DriverFactory and page objects)

username=uat_user
password=uat_password
# If needed by future authentication tests
```

---

### 6.3 config-sit.properties (SIT Environment)
**Purpose**: System Integration Test environment configuration
**When Loaded**: Via Maven profile `-Psit` or system property `-Denv=sit`

**Expected Properties**:
```properties
url=https://sauce-demo-sit.myshopify.com/
# (or similar SIT instance URL)

username=sit_user
password=sit_password
```

---

### 6.4 config-prod.properties (Production Environment)
**Purpose**: Production environment configuration
**When Loaded**: Via Maven profile `-Pprod` or system property `-Denv=prod`

**Expected Properties**:
```properties
url=https://sauce-demo-prod.myshopify.com/
# (or actual production URL)

username=prod_user
password=prod_password
```

**Caution**: Use carefully - production tests should be read-only

---

## 7. PROJECT BUILD CONFIGURATION

### pom.xml Key Dependencies

**Selenium 4.41.0**
```xml
<groupId>org.seleniumhq.selenium</groupId>
<artifactId>selenium-java</artifactId>
```

**Cucumber 7.15.0**
```xml
<groupId>io.cucumber</groupId>
<artifactId>cucumber-java</artifactId>
<artifactId>cucumber-junit</artifactId>
```

**Testing**:
- JUnit 4.13.2 (test runner)
- Hamcrest 2.2 (assertions)

**REST API Testing**:
- RestAssured 5.4.0 (with commons-lang3 and commons-codec exclusions)

**WebDriver Management**:
- WebDriverManager 6.1.0 (auto-download drivers)

**Reporting**:
- Allure Cucumber7 2.24.0
- ExtentReports Cucumber7 1.14.0 (with io, compress, poi-ooxml exclusions)

**JSON Processing**:
- Gson 2.10.1

**Java Version**: 21 (maven.compiler.source/target)

---

## 8. Test Execution Examples

### Run All Tests
```bash
mvn test
```

### Run Smoke Tests Only
```bash
mvn test -Dtest=SmokeTestRunner
```

### Run on UAT Environment
```bash
mvn test -Puat
```

### Run on SIT with Headless Mode
```bash
mvn test -Psit -Dheadless=true
```

### Run ParallelRunner1 (product add to cart @S1)
```bash
mvn test -Dtest=ParallelRunner1
```

### Run Regression Tests with Firefox
```bash
mvn test -Dtest=RegressionTestRunner -Dbrowser=firefox
```

### Run with Environment Override
```bash
mvn test -Denv=prod
```

---

## 9. Report Generation

### Allure Reports
```bash
# Run tests
mvn test

# Generate Allure report
mvn allure:report

# Serve Allure report
mvn allure:serve
```

### ExtentReports
- Automatically generated at `target/cucumber-reports/ExtentReport/`

### Standard Cucumber Reports
- HTML: `target/cucumber-reports/cucumber.html`
- JSON: `target/cucumber-reports/cucumber.json`

---

## 10. Project Strengths & Architecture Patterns

### ✅ Strengths
1. **ThreadLocal-based parallel execution** - Isolated WebDriver per thread
2. **Two-layer configuration** - Base + environment-specific
3. **Page Object Model** - Clean separation of concerns
4. **Lazy initialization** - Prevents null pointer issues in parallel execution
5. **BDD approach** - Human-readable Gherkin scenarios
6. **Multiple reporting** - Allure, ExtentReports, standard HTML
7. **CI/CD ready** - Headless mode, JSON reports, system property overrides
8. **Dynamic page object locators** - Handles responsive design variants
9. **Explicit waits** - Prevents flaky tests from timing issues
10. **Environment abstraction** - No hardcoded URLs in page objects

### 🏗️ Architecture Patterns
- **Page Object Model** (POM) - BasePage, LoginPage, ProductPage, etc.
- **Factory Pattern** - DriverFactory for WebDriver creation
- **ThreadLocal Pattern** - Thread-safe WebDriver management
- **Lazy Initialization** - Page object and step definition lazy loading
- **Builder Pattern** - ChromeOptions, FirefoxOptions configuration
- **Template Method** - BasePage common methods inherited by all pages

---

## Summary Statistics

| Component | Count | Status |
|-----------|-------|--------|
| Feature Files | 4 | ✅ Complete |
| Page Objects | 6 | ✅ Complete |
| Step Definitions | 6 | ✅ Complete |
| Test Scenarios | 35+ | ✅ Implemented |
| Test Runners | 11 | ✅ Configured |
| Configuration Files | 4 | ✅ Set Up |
| Utility Classes | 3 | ✅ Ready |

---

## Next Steps for Development

1. **Expand header/footer test coverage** - Add more link validation scenarios
2. **Add data-driven tests** - Use TestDataReader for parameterized tests
3. **Implement API testing** - Use APITestRunner with RestAssured
4. **Add performance tests** - Measure page load times and element response
5. **Implement visual regression** - Compare screenshots across environments
6. **Add accessibility testing** - ARIA attributes, contrast, keyboard navigation
7. **Expand product tests** - Multiple products, filters, sorting
8. **Add checkout flow** - Payment, shipping, order confirmation

