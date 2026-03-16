# BrowserStack Integration Guide

Quick reference for running tests on BrowserStack cloud.

## ☁️ Quick Start - Run Tests on Cloud

### Set Environment Variables
```bash
export BROWSERSTACK_USERNAME=souvikdutta_kjl3bT
export BROWSERSTACK_ACCESS_KEY=pk6zemKnxPqzhh4MRevy
```

### Run All Tests
```bash
mvn clean test
```

Tests will automatically execute on BrowserStack cloud (Windows 10 Chrome, OS X Safari, iPhone 13).

---

## 🎯 Run Single Feature on Cloud

### Using Feature Tags

The framework has 4 scenarios with tags:

| Tag | Scenario | Description |
|-----|----------|-------------|
| `@S1` | Add first product to cart | Smoke test |
| `@S2` | View product details | Regression test |
| `@S3` | Verify products | Standard test |
| `@S4` | Add from details page | Smoke test |

### Run Specific Scenario

```bash
# Set credentials
export BROWSERSTACK_USERNAME=souvikdutta_kjl3bT
export BROWSERSTACK_ACCESS_KEY=pk6zemKnxPqzhh4MRevy

# Run @S1 scenario
mvn test -Dtest=ParallelRunner1

# Run all @Smoke tests
mvn test -Dcucumber.filter.tags="@Smoke"

# Run @Product AND @Cart tests
mvn test -Dcucumber.filter.tags="@Product and @Cart"
```

---

## 📊 Monitor Tests

### BrowserStack Dashboard

After running tests, view results:

**URL:** https://app-automate.browserstack.com/

**Login:**
- Username: `souvikdutta_kjl3bT`
- Password: `pk6zemKnxPqzhh4MRevy`

**You'll see:**
- ✅ Live execution on multiple browsers
- ✅ Video recordings
- ✅ Console logs
- ✅ Network activity logs
- ✅ Screenshots on failure
- ✅ Test duration and status

---

## 🏠 Run Locally (No Cloud)

Tests run locally if `BROWSERSTACK_USERNAME` is NOT set:

```bash
# Unset the variable (if it was set)
unset BROWSERSTACK_USERNAME

# Run tests locally
mvn clean test
```

---

## ⚙️ Configuration

Edit `browserstack.yml` to customize:
- Browser versions
- Operating systems
- Mobile devices
- Build tags
- Network/console logging

### Example: Add New Browser

```yaml
platforms:
  - os: Windows
    osVersion: 11
    browserName: Firefox
    browserVersion: 120.0
```

---

## 🚨 Troubleshooting

**Tests still running locally?**
```bash
# Verify env vars
echo $BROWSERSTACK_USERNAME

# Set them again
export BROWSERSTACK_USERNAME=souvikdutta_kjl3bT
export BROWSERSTACK_ACCESS_KEY=pk6zemKnxPqzhh4MRevy
```

**Tests fail on cloud?**
- Check BrowserStack dashboard for video/logs
- Verify `browserstack.yml` capabilities
- Check network connectivity

**Want faster feedback?**
- Run locally with `mvn clean test`
- Use cloud for cross-browser validation before commit

---

## 📚 Related Files

- **README.md** - Full framework documentation
- **browserstack.yml** - Cloud configuration
- **.github/workflows/tests.yml** - GitHub Actions CI/CD
- **src/test/resources/features/product/addToCart.feature** - Test scenarios

---

**Need more help?** See `README.md` for complete documentation.

