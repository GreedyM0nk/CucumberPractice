# Run Single Feature on BrowserStack - Quick Guide

## 🎯 The Goal
Run one specific feature scenario from `addToCart.feature` on BrowserStack cloud.

---

## 📋 Available Scenarios & Tags

The feature file contains 4 scenarios:

```
@S1 - Add first product to cart from catalogue page (Smoke)
@S2 - View first product details (Regression)
@S3 - Verify products are displayed on catalogue page
@S4 - Add product to cart from product details page (Smoke)
```

Each mapped to a parallel runner:
- `@S1` → ParallelRunner1
- `@S2` → ParallelRunner2
- `@S3` → ParallelRunner3
- `@S4` → ParallelRunner4

---

## ⚡ 3-Step Quick Start

### Step 1️⃣ Set BrowserStack Credentials
```bash
export BROWSERSTACK_USERNAME=souvikdutta_kjl3bT
export BROWSERSTACK_ACCESS_KEY=pk6zemKnxPqzhh4MRevy
```

Verify they're set:
```bash
echo $BROWSERSTACK_USERNAME
echo $BROWSERSTACK_ACCESS_KEY
```

### Step 2️⃣ Run Your Feature

**Option A: Run specific scenario (@S1)**
```bash
mvn test -Dtest=ParallelRunner1
```

**Option B: Run by tag filter**
```bash
# @S1 scenario
mvn test -Dcucumber.filter.tags="@S1"

# All @Smoke tests (@S1 and @S4)
mvn test -Dcucumber.filter.tags="@Smoke"

# All @Product tests
mvn test -Dcucumber.filter.tags="@Product"

# Combined: @Product AND @Cart
mvn test -Dcucumber.filter.tags="@Product and @Cart"
```

### Step 3️⃣ Monitor on BrowserStack Dashboard

1. Go to: https://app-automate.browserstack.com/
2. Login with:
   - Username: `souvikdutta_kjl3bT`
   - Password: `pk6zemKnxPqzhh4MRevy`
3. You'll see:
   - 🎬 Live execution video
   - 📊 Test status (passing/failing)
   - 📝 Console logs
   - 🖼️ Screenshots
   - ⏱️ Execution time

---

## 📌 Command Examples

### Example 1: Run @S1 Scenario Only
```bash
# Step 1: Set credentials (if not already set)
export BROWSERSTACK_USERNAME=souvikdutta_kjl3bT
export BROWSERSTACK_ACCESS_KEY=pk6zemKnxPqzhh4MRevy

# Step 2: Run @S1
mvn test -Dtest=ParallelRunner1

# Step 3: Go to dashboard and watch!
# https://app-automate.browserstack.com/
```

### Example 2: Run All @Smoke Tests
```bash
export BROWSERSTACK_USERNAME=souvikdutta_kjl3bT
export BROWSERSTACK_ACCESS_KEY=pk6zemKnxPqzhh4MRevy

# Runs @S1 and @S4
mvn test -Dcucumber.filter.tags="@Smoke"
```

### Example 3: Run All @Regression Tests
```bash
export BROWSERSTACK_USERNAME=souvikdutta_kjl3bT
export BROWSERSTACK_ACCESS_KEY=pk6zemKnxPqzhh4MRevy

# Runs @S2
mvn test -Dcucumber.filter.tags="@Regression"
```

### Example 4: Run Multiple Runners on Cloud
```bash
export BROWSERSTACK_USERNAME=souvikdutta_kjl3bT
export BROWSERSTACK_ACCESS_KEY=pk6zemKnxPqzhh4MRevy

# Run all scenarios
mvn clean test
```

---

## 🔍 Understanding the Output

When you run a test, you'll see Maven output like:

```
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.retail.runners.ParallelRunner1
[INFO] 
[INFO] Scenario: Add first product to cart from catalogue page
[INFO] ✓ Given user is on the product catalogue page (0ms)
[INFO] ✓ When user adds the first product to cart (2.5s)
[INFO] ✓ Then cart count should be updated (1.2s)
[INFO] ✓ And product should be visible in cart (0.8s)
[INFO]
[INFO] 1 passed (5.5s)
[INFO] -------------------------------------------------------
[INFO] BUILD SUCCESS
```

Then check the BrowserStack dashboard for video, logs, and detailed results!

---

## 🏠 Local Testing (No Cloud)

If you want to run locally instead of on cloud:

```bash
# Unset the environment variables
unset BROWSERSTACK_USERNAME
unset BROWSERSTACK_ACCESS_KEY

# Run tests locally
mvn test -Dtest=ParallelRunner1
```

Tests will run on your local Chrome browser instead of the cloud.

---

## ⚙️ Configure Which Browsers Run on Cloud

Edit `browserstack.yml` to change which browsers run:

```yaml
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
```

Each platform runs your feature independently in parallel!

---

## 🐛 Troubleshooting

**Tests still running locally?**
- Verify env vars: `echo $BROWSERSTACK_USERNAME`
- Set them again: `export BROWSERSTACK_USERNAME=souvikdutta_kjl3bT`

**Can't access dashboard?**
- Go to: https://app-automate.browserstack.com/
- Login: `souvikdutta_kjl3bT` / `pk6zemKnxPqzhh4MRevy`
- Check network connectivity

**Test failed on cloud but passes locally?**
- View the video on BrowserStack dashboard
- Check console logs for error details
- Compare local vs cloud differences

---

## 📚 More Information

- **Full guide:** See `README.md`
- **All commands:** See `BROWSERSTACK.md`
- **Code examples:** See `ALL_UPDATED_CODE.md`
- **Architecture:** See `ARCHITECTURE_DIAGRAMS.md`

---

## 🎯 Summary

To run a single feature on BrowserStack:

```bash
# 1. Set credentials
export BROWSERSTACK_USERNAME=souvikdutta_kjl3bT
export BROWSERSTACK_ACCESS_KEY=pk6zemKnxPqzhh4MRevy

# 2. Run your feature (pick one)
mvn test -Dtest=ParallelRunner1           # @S1 scenario
mvn test -Dcucumber.filter.tags="@Smoke"  # All smoke tests
mvn clean test                             # All tests

# 3. Monitor on dashboard
# https://app-automate.browserstack.com/
```

**That's it!** 🚀

