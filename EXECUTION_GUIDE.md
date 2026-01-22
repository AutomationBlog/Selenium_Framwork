# Execution Guide

## Prerequisites

Before running the tests, ensure you have:

1. **Java 11 or Higher**
   ```bash
   java -version
   ```
   Expected output: `java version "11" or higher`

2. **Maven 3.6 or Higher**
   ```bash
   mvn -version
   ```
   Expected output: `Apache Maven 3.6 or higher`

3. **Google Chrome Browser** (or Firefox/Edge)
   - Latest version recommended
   - WebDriverManager will handle driver download

4. **Internet Connection**
   - Required for WebDriverManager to download drivers
   - Required for accessing amazon.com

## Installation Steps

### Step 1: Download/Clone Project
```bash
cd d:\Automation\Selenium_Framwork
```

### Step 2: Install Dependencies
```bash
mvn clean install
```
This will:
- Download all Maven dependencies
- Compile the project
- Install in local repository

### Step 3: Verify Installation
```bash
mvn clean compile
```
Expected output: `BUILD SUCCESS`

## Running Tests

### Option 1: Run All Tests
```bash
mvn clean test
```
This runs all test classes defined in testng.xml

**Output Locations:**
- TestNG Reports: `test-output/`
- Extent Reports: `test-output/ExtentReports/`
- Cucumber Reports: `test-output/CucumberReports/`

### Option 2: Run TestNG Tests Only
```bash
mvn clean test -Dtest=AmazonAddToCartTestNGRunner
```

This runs 3 test cases:
1. testAddLaptopToCart
2. testAddMobilePhoneToCart
3. testAddHeadphonesToCartWithQuantity

### Option 3: Run Specific Test Case
```bash
mvn clean test -Dtest=AmazonAddToCartTestNGRunner#testAddLaptopToCart
```

### Option 4: Run Cucumber Tests
```bash
mvn clean test -Dtest=CucumberRunnerTest
```

### Option 5: Run Using TestNG XML
```bash
mvn clean test -DsuiteXmlFile=testng.xml
```

### Option 6: Run with Custom Settings
```bash
mvn clean test -Dtest=AmazonAddToCartTestNGRunner -DargLine="-Dbrowser=firefox"
```

## Configuration Before Running

### Update Browser in config.properties

Edit: `src/test/resources/config/config.properties`

```properties
# Options: chrome, firefox, edge
browser=chrome

# Set to true for headless mode
headless=false

# Amazon website
baseURL=https://www.amazon.com

# Timeouts in seconds
implicitWait=10
explicitWait=10
pageLoadTimeout=20
```

## Viewing Test Reports

### 1. Extent Report (Spark - Recommended)

After running tests, open:
```
test-output/ExtentReports/AutomationReport_[timestamp].html
```

Features:
- Beautiful dashboard
- Test status overview
- Detailed step logs
- System information
- Execution timeline

### 2. Cucumber Report

Open:
```
test-output/CucumberReports/cucumber-report.html
```

Shows:
- Feature and scenario details
- Step pass/fail status
- Execution duration

### 3. Console Output

Maven will display test results in terminal:
```
[TestNG] Running:
  testng.xml

Tests run: 5, Failures: 0, Skips: 0
```

## Understanding Test Results

### Pass Status ✅
- All assertions passed
- Steps executed successfully
- Product added to cart

### Fail Status ❌
- Assertion failed
- Element not found
- Unexpected behavior

### Skip Status ⊘
- Test was skipped intentionally
- Dependency test failed

## Common Issues & Solutions

### Issue 1: Build Fails - Cannot find symbol
**Solution:**
```bash
mvn clean install -U
```
The `-U` flag forces Maven to update all dependencies.

### Issue 2: WebDriver Manager Error
**Solution:**
- Ensure internet connection is active
- WebDriverManager needs to download ChromeDriver
- Check firewall settings

### Issue 3: "Element not found" Exception
**Possible Causes:**
- Amazon page structure changed
- Element locators need update
- Page not fully loaded

**Solution:**
- Update locators in page object classes
- Increase wait times in config.properties
- Check if Amazon requires login

### Issue 4: Port Already in Use
**Solution:**
```bash
mvn clean test -DargLine="-Dserver.port=9091"
```

### Issue 5: Tests Timeout
**Solution:**
Increase timeout in config.properties:
```properties
explicitWait=20
pageLoadTimeout=30
```

## Best Practices for Running Tests

1. **Run Tests in Sequence**
   - Tests should be independent
   - Don't run multiple tests in parallel initially

2. **Check Internet Connection**
   - Tests need to access amazon.com
   - WebDriverManager needs internet

3. **Use Fresh Browser**
   - Close other browser instances
   - Clear browser cache for first run

4. **Review Reports**
   - Always check Extent Report after execution
   - Log important steps for debugging

5. **Keep Configuration Updated**
   - Update config.properties for your environment
   - Use correct base URL

## Continuous Integration (CI)

### GitHub Actions Example
Create `.github/workflows/test.yml`:

```yaml
name: Automation Tests

on: [push, pull_request]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v2
    - name: Set up JDK 11
      uses: actions/setup-java@v2
      with:
        java-version: '11'
    - name: Run tests
      run: mvn clean test
    - name: Upload reports
      uses: actions/upload-artifact@v2
      if: always()
      with:
        name: test-reports
        path: test-output/
```

## Performance Tips

1. **Run Tests in Parallel** (Advanced)
   Edit testng.xml:
   ```xml
   <suite name="Amazon Automation Suite" verbose="2" thread-count="3">
   ```

2. **Skip Unnecessary Waits**
   - Reduce explicit wait time if possible
   - Use waits only where needed

3. **Cache Dependencies**
   ```bash
   mvn clean test -o
   ```
   The `-o` flag runs offline (after first successful build)

## Debugging Failed Tests

### Enable Debug Mode
```bash
mvn clean test -X
```
Shows detailed debug information

### Add Breakpoints in IDE
1. Open test file in IDE
2. Click line number to add breakpoint
3. Run test in debug mode
4. Step through code

### Check Logs
All important steps are logged in:
- Console output
- Extent Report
- extenttest.log

## Troubleshooting Checklist

- [ ] Java 11+ installed
- [ ] Maven 3.6+ installed
- [ ] Internet connection active
- [ ] Chrome/Firefox/Edge installed
- [ ] config.properties updated
- [ ] No port conflicts
- [ ] Firewall allows downloads
- [ ] Amazon website accessible
- [ ] test-output folder created
- [ ] Reports generated successfully

## Next Steps

1. **Run your first test:**
   ```bash
   mvn clean test -Dtest=AmazonAddToCartTestNGRunner#testAddLaptopToCart
   ```

2. **Check the report:**
   - Open `test-output/ExtentReports/` folder
   - View the HTML file in browser

3. **Explore the code:**
   - Review page objects in `src/main/java/com/amazon/pages/`
   - Review utilities in `src/main/java/com/amazon/utilities/`
   - Review test cases in `src/test/java/com/amazon/runners/`

4. **Customize for your needs:**
   - Add more test scenarios
   - Update page objects for different products
   - Extend utility classes as needed

## Support

For more help, refer to:
- README.md - Complete documentation
- UTILITIES_REFERENCE.md - Utility classes guide
- SETUP_SUMMARY.md - Framework summary

Happy Testing! 🚀
