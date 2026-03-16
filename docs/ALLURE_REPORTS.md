# Allure Report Generation & Deployment Guide

## Overview

This project is configured to automatically generate detailed Allure test reports and deploy them to **GitHub Pages** for easy access and trend analysis.

## What Gets Deployed

When tests run (via push to `main`, pull requests, or manual trigger), the pipeline:

1. ✅ **Executes all test cases** using Maven parallel runners
2. 📊 **Generates Allure test reports** with:
   - Pass/fail statistics
   - Test execution timeline
   - Historical trend charts
   - Detailed test logs
   - Screenshot attachments
3. 📈 **Maintains execution history** for trending
4. 🚀 **Deploys to GitHub Pages** for public access

## Accessing the Report

### Where is the report published?

After tests run, the Allure report is published to GitHub Pages:

```
https://<github-username>.github.io/<repository-name>/
```

**Example:**
```
https://john-doe.github.io/RetailAutomation/
```

### How to find the link

1. **In GitHub Actions workflow:**
   - Go to Actions → Click on workflow run → Scroll to "Report Generation Summary"
   - Copy the link to your report

2. **In Repository Settings:**
   - Go to Settings → Pages → View deployment history
   - Click the "Visit site" button

## Pipeline Configuration

### Trigger Events

The pipeline runs automatically on:

- ✅ **Push to `main` branch** - Full test suite executes
- ✅ **Pull requests to `main`** - Validates changes before merge
- ✅ **Manual trigger** - Via `workflow_dispatch` (Actions tab → Run workflow)

### Test Execution Flow

```
┌─────────────────────────────────────────┐
│ Code pushed or PR created               │
└─────────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────────┐
│ mvn clean test -Denv=uat                │
│ (Runs 4 parallel test runners)          │
└─────────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────────┐
│ Generate Allure JSON results            │
│ (from Cucumber features)                │
└─────────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────────┐
│ mvn allure:report                       │
│ (Creates HTML dashboard)                │
└─────────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────────┐
│ Deploy to gh-pages branch               │
│ (Publish to GitHub Pages)               │
└─────────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────────┐
│ Report Live at GitHub Pages URL         │
└─────────────────────────────────────────┘
```

## Troubleshooting

### Issue: "Unknown - Unknown (Unknown)" / "0 test cases"

**Cause:** Allure results aren't being generated properly

**Solution:**
1. Check workflow logs for test errors
2. Verify `target/allure-results/` contains JSON files
3. Ensure `ParallelRunner*.java` have Allure plugin configured:
   ```java
   plugin = {
       "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
   }
   ```

### Issue: Report not appearing on GitHub Pages

**Cause:** GitHub Pages branch (`gh-pages`) not configured or permissions missing

**Solution:**
1. Go to Settings → Pages
2. Ensure "Source" is set to `gh-pages` branch
3. Verify workflow has `contents: write` permission (already configured)

### Issue: No test counts displayed

**Cause:** Test results not captured before report generation

**Solution:**
1. Verify tests actually ran: `mvn test -DskipTests=false`
2. Check that `target/allure-results/` directory exists
3. Ensure feature files have proper tags (@S1, @S2, etc.)

## Local Testing

To generate Allure reports locally before deployment:

```bash
# Run tests with Allure reporting
mvn clean test -Denv=uat

# Generate HTML report
mvn allure:report

# View report (opens in browser)
mvn allure:serve
```

## Environment Variables Required

For BrowserStack integration, set these secrets in GitHub Actions:

```
BROWSERSTACK_USERNAME   → Your BrowserStack username
BROWSERSTACK_ACCESS_KEY → Your BrowserStack access key
```

Configure in: Settings → Secrets and variables → Actions

## Report Contents

Each report includes:

| Section | Details |
|---------|---------|
| **Overview** | Total tests, pass/fail counts, duration |
| **Timeline** | Test execution sequence and timing |
| **Tests** | Individual test details, steps, assertions |
| **Graphs** | Pass/fail trends over time |
| **History** | Previous execution records |
| **Attachments** | Screenshots, logs, test data |

## Customizing Report Generation

### Change test environment

Edit `.github/workflows/tests.yml`:
```yaml
- name: Run All Tests
  run: mvn clean test -Denv=sit  # Change 'uat' to 'sit' or 'prod'
```

### Modify test tags executed

Edit `src/test/java/com/retail/runners/ParallelRunner*.java`:
```java
@CucumberOptions(
    tags = "@S1 or @S2"  // Change to execute specific tags
)
```

### Adjust report retention

Edit `.github/workflows/tests.yml`:
```yaml
retention_days: 30  # Keep reports for 30 days (change as needed)
```

## Best Practices

1. ✅ **Commit feature files** with proper tags (@S1, @S2, etc.)
2. ✅ **Use descriptive test names** for clarity in reports
3. ✅ **Add assertions** to generate meaningful pass/fail metrics
4. ✅ **Review reports** after each deployment
5. ✅ **Monitor trends** using historical charts

## Related Documentation

- [GitHub Actions Setup](https://github.com/features/actions)
- [Allure Report Guide](https://docs.qameta.io/allure/)
- [Maven Plugin Configuration](https://github.com/allure-framework/allure-maven)
- [GitHub Pages Configuration](https://docs.github.com/en/pages)
