# 🚀 Quick Start Guide - 5 Minutes to Run Your First Test

## Step 1: Open Terminal (1 min)
Open command prompt and navigate to project:
```bash
cd d:\Automation\Selenium_Framwork
```

## Step 2: Build Project (2-3 min)
```bash
mvn clean install
```

Wait for `BUILD SUCCESS` message.

## Step 3: Run Tests (1-2 min)
```bash
mvn clean test
```

## Step 4: View Report (1 min)
Open the generated report in browser:
```
test-output/ExtentReports/AutomationReport_[timestamp].html
```

---

## Command Cheat Sheet

### Run All Tests
```bash
mvn clean test
```

### Run Specific Test
```bash
mvn clean test -Dtest=AmazonAddToCartTestNGRunner#testAddLaptopToCart
```

### Run Only TestNG
```bash
mvn clean test -Dtest=AmazonAddToCartTestNGRunner
```

### Run Only Cucumber
```bash
mvn clean test -Dtest=CucumberRunnerTest
```

### View Help
```bash
mvn help:active-profiles
```

---

## Expected Output

When tests run successfully, you'll see:
```
[TestNG] Running:
  testng.xml

Tests run: 5, Failures: 0, Skips: 0
[INFO] BUILD SUCCESS
```

---

## What Gets Created After Running Tests

```
test-output/
├── ExtentReports/
│   └── AutomationReport_[timestamp].html    ← OPEN THIS IN BROWSER
├── CucumberReports/
│   ├── cucumber-report.html
│   ├── cucumber-report.json
│   └── cucumber-report.xml
└── surefire-reports/
    └── TEST-*.xml
```

---

## Test Cases That Will Run

### TestNG Tests (3 tests)
1. ✅ testAddLaptopToCart
2. ✅ testAddMobilePhoneToCart  
3. ✅ testAddHeadphonesToCartWithQuantity

### Cucumber Scenarios (2 scenarios)
1. ✅ User adds a product to cart (laptop)
2. ✅ User adds multiple products (phone)

**Total: 5 test executions**

---

## Project Contents Summary

```
✅ 8 Java Classes (utilities + base + pages)
✅ 2 Test Runners (TestNG + Cucumber)
✅ 2 Feature Files (Cucumber scenarios)
✅ 4 Documentation Files
✅ All Dependencies Pre-configured
✅ Ready to Run Tests
```

---

## Quick Configuration Changes

Edit `src/test/resources/config/config.properties`:

```properties
# Change browser (chrome/firefox/edge)
browser=chrome

# Change timeout (in seconds)
implicitWait=10

# Change base URL
baseURL=https://www.amazon.com
```

---

## Troubleshooting - 30 Second Solutions

### Maven not found?
```bash
java -version
mvn -version
```
Download from: https://maven.apache.org/download.cgi

### Tests fail with WebDriver error?
```bash
mvn clean install -U
```
The -U forces Maven to update dependencies

### Can't see report?
Check folder: `test-output/ExtentReports/`
Look for `.html` file and open in Chrome

### Timeout errors?
Increase in config.properties:
```properties
implicitWait=15
explicitWait=15
pageLoadTimeout=30
```

---

## File Locations Cheat Sheet

```
Source Code     → src/main/java/com/amazon/
Tests           → src/test/java/com/amazon/
Features        → src/test/resources/features/
Config          → src/test/resources/config/
Test Data       → src/test/resources/testdata/
Reports         → test-output/ (after running tests)
Maven Config    → pom.xml
TestNG Config   → testng.xml
```

---

## What Each Utility Class Does

```
ExcelUtility        → Read/Write Excel files (.xlsx)
JsonUtility         → Read/Write JSON files
PropertyUtility     → Read/Write .properties files
TextFileUtility     → Read/Write Text files (.txt)
BaseTest            → Setup WebDriver
ExtentReportBase    → Generate beautiful HTML reports
```

---

## Test Scenario Flow

```
1. Open Amazon website
   ↓
2. Search for product
   ↓
3. Click product from results
   ↓
4. Click "Add to Cart" button
   ↓
5. Verify product in cart
   ↓
6. ✅ TEST PASSED
```

---

## Next Steps After First Run

1. ✅ Review the HTML report
2. ✅ Check test execution logs
3. ✅ Explore page objects in `src/main/java/com/amazon/pages/`
4. ✅ Explore utilities in `src/main/java/com/amazon/utilities/`
5. ✅ Modify tests for your needs

---

## Documentation Quick Links

| Document | Purpose |
|----------|---------|
| README.md | Complete guide with all details |
| EXECUTION_GUIDE.md | How to run tests (detailed) |
| UTILITIES_REFERENCE.md | Code examples for utilities |
| PROJECT_STRUCTURE.md | Full file structure |
| SETUP_SUMMARY.md | What was created |

---

## Key Points to Remember

- ✅ All dependencies are in pom.xml
- ✅ WebDriverManager handles browser drivers automatically
- ✅ Tests use explicit waits (more reliable)
- ✅ Reports are beautiful and detailed
- ✅ Utilities handle all file operations
- ✅ Framework is ready to extend

---

## Support & Help

**Still having issues?**

1. Check internet connection (needed for WebDriverManager)
2. Close other browser windows
3. Clear browser cache
4. Ensure Chrome/Firefox/Edge is installed
5. Review EXECUTION_GUIDE.md

---

## Success Checklist

- [ ] Java 11+ installed
- [ ] Maven 3.6+ installed
- [ ] Project cloned/extracted
- [ ] `mvn clean install` successful
- [ ] `mvn clean test` successful
- [ ] Report generated in test-output/
- [ ] All 5 tests passed ✅

---

**You're all set! Run tests with:**
```bash
mvn clean test
```

**Good luck! 🎉**
