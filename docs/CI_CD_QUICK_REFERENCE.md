# CI/CD Pipeline Quick Reference

## 🚀 Quick Start

### Access Your Reports
```
https://<your-github-username>.github.io/<your-repo-name>/
```

### Trigger Pipeline
- **Automatic**: Push to `main` branch or create PR
- **Manual**: Actions tab → "Retail Automation CI/CD" → "Run workflow"

## 📊 What Happens When Pipeline Runs

```
┌─────────────────────────────────────────────┐
│ 1. Code Change (Push/PR/Manual)             │
└─────────────────────────────────────────────┘
           │
           ▼
┌─────────────────────────────────────────────┐
│ 2. Tests Execute (4 parallel runners)       │
│    • Cucumber features run (@S1, @S2, etc) │
│    • Android/iOS/Web browsers tested       │
│    • Generates JSON results                │
└─────────────────────────────────────────────┘
           │
           ▼
┌─────────────────────────────────────────────┐
│ 3. Report Generated                         │
│    • Test counts calculated ✓               │
│    • Trends included ✓                      │
│    • Screenshots attached ✓                 │
└─────────────────────────────────────────────┘
           │
           ▼
┌─────────────────────────────────────────────┐
│ 4. Deploy to GitHub Pages                   │
│    • Live immediately                       │
│    • Share via URL                          │
│    • 30-day retention                       │
└─────────────────────────────────────────────┘
```

## ✅ Expected Report Contents

When you open the report, you'll see:

```
Allure Report
├── Summary
│   ├── Total: 4 tests
│   ├── Passed: 3 ✓
│   ├── Failed: 1 ✗
│   └── Duration: 2m 45s
├── Timeline (when each test ran)
├── Tests (detailed results with steps)
├── Graphs (pass/fail trends)
└── History (previous runs)
```

## 🔍 Troubleshooting

### Report shows "0 test cases" or "Unknown - Unknown"
1. Did tests actually run? Check workflow logs for `PASSED` / `FAILED`
2. Verify `target/allure-results/` has JSON files
3. Check feature files have proper `@` tags

### Report not on GitHub Pages
1. Go to repo Settings → Pages
2. Ensure Source = `gh-pages` branch
3. Wait 1-2 minutes for deploy

### Tests failing in pipeline but pass locally
1. Check BrowserStack credentials (Settings → Secrets)
2. Verify network connectivity to test environment
3. Review test logs in workflow UI

## 📁 Key Locations

| File/Folder | Purpose |
|-------------|---------|
| `.github/workflows/tests.yml` | Pipeline definition |
| `pom.xml` | Build config + Allure setup |
| `src/test/java/com/retail/runners/` | Test runners with @tags |
| `src/test/resources/features/` | Cucumber feature files |
| `target/allure-results/` | Test results (local) |
| `target/site/allure-report/` | Generated HTML report (local) |

## 🎯 Using the Pipeline

### Run specific tests locally
```bash
# Run only feature with @S1 tag
mvn test -Dtest=ParallelRunner1

# Run tests on SIT environment
mvn test -Psit

# Run tests and view report
mvn clean test && mvn allure:serve
```

### Common Commands
```bash
mvn clean              # Clean build artifacts
mvn test               # Execute all tests
mvn allure:report      # Generate HTML report
mvn allure:serve       # Generate + open in browser
mvn test -DskipTests   # Just compile, no tests
```

## 🔐 Required Setup

### GitHub Secrets (if using BrowserStack)
Set these in Settings → Secrets and variables:
```
BROWSERSTACK_USERNAME   = your_user@example.com
BROWSERSTACK_ACCESS_KEY = abc123def456
```

### GitHub Pages (required)
1. Go to Settings → Pages
2. Set Source = `gh-pages` branch
3. Select any theme (optional)
4. Save

## 📈 What Gets Tracked

✅ **Per Test Run:**
- Pass/fail status
- Execution time
- Full test logs
- Screenshots
- Steps executed

✅ **Historical:**
- Trend charts
- Pass rate over time
- Execution metrics
- Known flaky tests

## 💡 Pro Tips

1. **Share reports** - Send GitHub Pages URL to stakeholders (no login needed)
2. **Compare runs** - Click "History" tab to see trends
3. **Debug failures** - Click failing test → View screenshots + logs
4. **Tag your tests** - Use `@S1`, `@S2` tags for categorization
5. **Monitor trends** - Review weekly reports in Slack/Teams

## 🛠️ Customization

**Change test environment:**
```yaml
# In .github/workflows/tests.yml
run: mvn clean test -Denv=sit  # Change uat→sit or uat→prod
```

**Add more tests:**
1. Create new ParallelRunner*.java
2. Add @CucumberOptions with tags
3. Update pom.xml exclude list

**Adjust parallel execution:**
```xml
<!-- In pom.xml -->
<forkCount>4</forkCount>      <!-- Number of parallel runners -->
<threadCount>4</threadCount>  <!-- Threads per runner -->
```

## 📞 Support

- **Workflow issues?** Check `.github/workflows/tests.yml`
- **Test failures?** Review feature files in `src/test/resources/features/`
- **Report not showing?** Verify GitHub Pages settings
- **Need details?** See `docs/ALLURE_REPORTS.md`

---

**Last Updated:** GitHub Actions CI/CD Pipeline v2 (with Allure Reports)
