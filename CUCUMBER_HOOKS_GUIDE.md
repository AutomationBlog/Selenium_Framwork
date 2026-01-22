# Cucumber Hooks Implementation Guide

## Overview

Cucumber Hooks are special methods that run before and after scenario execution, enabling lifecycle management, setup, teardown, and test orchestration in BDD frameworks. This guide explains the Cucumber hooks implementation in the Amazon Automation Suite.

**Table of Contents:**
1. [Hooks Concepts](#hooks-concepts)
2. [Hook Types](#hook-types)
3. [Implementation Details](#implementation-details)
4. [Hook Lifecycle](#hook-lifecycle)
5. [Best Practices](#best-practices)
6. [Troubleshooting](#troubleshooting)
7. [Examples](#examples)

---

## Hooks Concepts

### What are Cucumber Hooks?

Hooks are functions that run before and after scenario execution without being explicitly called from feature files. They enable:

- **Test Setup:** Initialize WebDriver, reports, and test environment
- **Pre-Condition Execution:** Set up data or state before test starts
- **Test Execution Logging:** Log step information for debugging
- **Failure Handling:** Capture screenshots, generate reports on failure
- **Test Cleanup:** Close browser, cleanup cookies, flush reports
- **Teardown Operations:** Release resources and finalize reporting

### Benefits of Using Hooks

| Benefit | Description |
|---------|-------------|
| **Centralized Setup/Teardown** | Manage initialization in one place instead of repeating in each step |
| **Automatic Failure Handling** | Capture screenshots automatically on test failure |
| **Consistent Logging** | Log all steps uniformly across all tests |
| **Resource Management** | Properly close WebDriver and cleanup resources |
| **Report Integration** | Automatically attach screenshots and logs to reports |
| **Code Reusability** | Avoid duplicating setup code in step definitions |
| **Cleaner Step Definitions** | Step definitions focus only on test logic |

---

## Hook Types

### 1. @Before Hook
**Purpose:** Executes before each scenario starts

```java
@Before
public void setUp(Scenario scenario) {
    System.out.println("\n" + "=".repeat(70));
    System.out.println("▶ SCENARIO START: " + scenario.getName());
    System.out.println("=" .repeat(70));
    
    // Initialize WebDriver
    initializeWebDriver();
    
    // Initialize Extent Reports
    ExtentReportBase.initializeExtentReport();
    ExtentReportBase.createTest(scenario.getName(), 
        "Tags: " + scenario.getSourceTagNames());
    ExtentReportBase.logInfo("Scenario started: " + scenario.getName());
}
```

**When it executes:** Before any step in the scenario runs
**Common uses:** WebDriver initialization, report creation, test data setup

### 2. @After Hook
**Purpose:** Executes after each scenario completes (pass/fail/skip)

```java
@After
public void tearDown(Scenario scenario) {
    ExtentReportBase.logInfo("Scenario Status: " + scenario.getStatus());
    
    // Final screenshot
    if (!scenario.isFailed()) {
        byte[] screenshot = ((TakesScreenshot) driver)
            .getScreenshotAs(OutputType.BYTES);
        String fileName = "Final_Screenshot_" + scenario.getName();
        ExtentReportBase.attachScreenshot(fileName, screenshot);
    }
    
    // Cleanup
    deleteCookies();
    if (driver != null) {
        driver.quit();
    }
    
    // Flush report
    ExtentReportBase.flushReport();
    
    System.out.println("▶ SCENARIO END: " + scenario.getName());
    System.out.println("=" .repeat(70) + "\n");
}
```

**When it executes:** After all steps in scenario complete (regardless of result)
**Common uses:** Browser cleanup, report finalization, resource cleanup

### 3. @BeforeStep Hook
**Purpose:** Executes before each step

```java
@BeforeStep
public void beforeStep(Scenario scenario) {
    ExtentReportBase.logInfo("Step in progress...");
}
```

**When it executes:** Before each individual step
**Common uses:** Step-level logging, pre-step operations

### 4. @AfterStep Hook
**Purpose:** Executes after each step (pass/fail)

```java
@AfterStep
public void afterStep(Scenario scenario) {
    if (scenario.isFailed()) {
        // Capture screenshot on failure
        byte[] screenshot = ((TakesScreenshot) driver)
            .getScreenshotAs(OutputType.BYTES);
        String fileName = "Failed_Step_" + System.currentTimeMillis();
        ExtentReportBase.attachScreenshot(fileName, screenshot);
    }
}
```

**When it executes:** After each individual step completes
**Common uses:** Failure handling, screenshot capture, step logging

---

## Implementation Details

### Hooks.java Class Structure

**Location:** `src/test/java/com/amazon/stepdefinitions/Hooks.java`

**Class Overview:**
```java
public class Hooks extends BaseTest {
    private static BrowserInteractionUtility browserInteraction;
    private static Scenario currentScenario;
    
    @Before
    public void setUp(Scenario scenario) { ... }
    
    @BeforeStep
    public void beforeStep(Scenario scenario) { ... }
    
    @AfterStep
    public void afterStep(Scenario scenario) { ... }
    
    @After
    public void tearDown(Scenario scenario) { ... }
    
    private byte[] takeScreenshot(String fileName) { ... }
    
    public static BrowserInteractionUtility getBrowserInteraction() { ... }
    
    public static Scenario getScenario() { ... }
}
```

### Hook Methods Explained

#### 1. setUp(Scenario scenario)
**What it does:**
1. Prints scenario start information
2. Initializes WebDriver (Chrome, Firefox, Edge)
3. Initializes Extent Reports
4. Creates test node in report
5. Logs scenario start with tags

**Key Code:**
```java
@Before
public void setUp(Scenario scenario) {
    currentScenario = scenario;
    System.out.println("\n" + "=".repeat(70));
    System.out.println("▶ START: " + scenario.getName());
    System.out.println("Tags: " + scenario.getSourceTagNames());
    System.out.println("=".repeat(70));
    
    initializeWebDriver();
    ExtentReportBase.initializeExtentReport();
    ExtentReportBase.createTest(scenario.getName(), 
        "Scenario: " + scenario.getName());
    ExtentReportBase.logInfo("✓ Scenario initialized: " + scenario.getName());
}
```

#### 2. beforeStep(Scenario scenario)
**What it does:**
1. Logs step information
2. Provides debug context before step execution

**Key Code:**
```java
@BeforeStep
public void beforeStep(Scenario scenario) {
    ExtentReportBase.logInfo("⏳ Step executing...");
}
```

#### 3. afterStep(Scenario scenario)
**What it does:**
1. Checks if step failed
2. Captures screenshot on failure
3. Attaches screenshot to report

**Key Code:**
```java
@AfterStep
public void afterStep(Scenario scenario) {
    if (scenario.isFailed()) {
        byte[] screenshot = takeScreenshot("FailedStep");
        ExtentReportBase.attachScreenshot("Failed Step Screenshot", screenshot);
    }
}
```

#### 4. tearDown(Scenario scenario)
**What it does:**
1. Logs scenario completion status
2. Captures final screenshot
3. Cleans up cookies
4. Closes WebDriver
5. Flushes Extent Reports
6. Prints scenario end information

**Key Code:**
```java
@After
public void tearDown(Scenario scenario) {
    ExtentReportBase.logInfo("Scenario Status: " + scenario.getStatus().name());
    
    byte[] screenshot = takeScreenshot("FinalScreenshot");
    ExtentReportBase.attachScreenshot("Final Screenshot", screenshot);
    
    deleteCookies();
    if (driver != null) {
        driver.quit();
    }
    
    ExtentReportBase.flushReport();
    System.out.println("▶ END: " + scenario.getName());
    System.out.println("=".repeat(70) + "\n");
}
```

#### 5. takeScreenshot(String fileName)
**Purpose:** Helper method to capture screenshots

```java
private byte[] takeScreenshot(String fileName) {
    try {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    } catch (Exception e) {
        ExtentReportBase.logFail("Failed to capture screenshot: " + e.getMessage());
        return new byte[0];
    }
}
```

#### 6. getBrowserInteraction()
**Purpose:** Provides static access to BrowserInteractionUtility

```java
public static BrowserInteractionUtility getBrowserInteraction() {
    if (browserInteraction == null) {
        browserInteraction = new BrowserInteractionUtility(driver);
    }
    return browserInteraction;
}
```

#### 7. getScenario()
**Purpose:** Provides static access to current scenario

```java
public static Scenario getScenario() {
    return currentScenario;
}
```

---

## Hook Lifecycle

### Complete Test Execution Flow

```
┌─────────────────────────────────────────────────────────┐
│ Scenario: "User adds product to cart"                   │
│ Tags: @smoke, @regression                               │
└─────────────────────────────────────────────────────────┘
                           │
                           ▼
    ┌──────────────────────────────────────────┐
    │ @Before Hook (setUp)                      │
    │ ✓ Initialize WebDriver                    │
    │ ✓ Initialize Extent Reports               │
    │ ✓ Create test node in report              │
    │ ✓ Log scenario start                      │
    └──────────────────────────────────────────┘
                           │
                           ▼
    ┌──────────────────────────────────────────┐
    │ STEP 1: Given user is on Amazon homepage  │
    │ ├─ @BeforeStep Hook                       │
    │ │  └─ Log step info                       │
    │ ├─ Step Execution                         │
    │ │  └─ Navigate to Amazon.com              │
    │ └─ @AfterStep Hook                        │
    │    └─ Capture screenshot if failed        │
    └──────────────────────────────────────────┘
                           │
                           ▼
    ┌──────────────────────────────────────────┐
    │ STEP 2: When user searches for product    │
    │ ├─ @BeforeStep Hook                       │
    │ ├─ Step Execution                         │
    │ └─ @AfterStep Hook                        │
    └──────────────────────────────────────────┘
                           │
                           ▼
    ┌──────────────────────────────────────────┐
    │ STEP 3: Then product should be displayed  │
    │ ├─ @BeforeStep Hook                       │
    │ ├─ Step Execution                         │
    │ └─ @AfterStep Hook                        │
    └──────────────────────────────────────────┘
                           │
                           ▼
    ┌──────────────────────────────────────────┐
    │ @After Hook (tearDown)                    │
    │ ✓ Capture final screenshot                │
    │ ✓ Delete cookies                          │
    │ ✓ Close WebDriver                         │
    │ ✓ Flush Extent Reports                    │
    │ ✓ Log scenario end                        │
    └──────────────────────────────────────────┘
```

### Execution Timeline

| Phase | Timing | What Happens |
|-------|--------|--------------|
| **Before Scenario** | Before Step 1 | @Before hook executes - setup |
| **During Each Step** | Before each step | @BeforeStep hook |
| **Step Execution** | During step | Step definition code runs |
| **After Each Step** | After each step | @AfterStep hook, screenshot capture |
| **After Scenario** | After last step | @After hook - cleanup, report |

---

## Best Practices

### 1. Keep Hooks Focused
❌ **Bad:** Multiple responsibilities in one hook
```java
@Before
public void setUp(Scenario scenario) {
    // Don't do too many things
    initializeWebDriver();
    initializeDatabase();
    setupTestData();
    createReport();
    loadProperties();
}
```

✅ **Good:** Single responsibility
```java
@Before
public void setUp(Scenario scenario) {
    initializeWebDriver();
    ExtentReportBase.initializeExtentReport();
}
```

### 2. Use Scenario Object for Filtering
❌ **Bad:** Run hooks for all scenarios
```java
@Before
public void setUp(Scenario scenario) {
    // This runs for every scenario
    initializeWebDriver();
}
```

✅ **Good:** Filter by tags
```java
@Before("@database")
public void setupDatabase(Scenario scenario) {
    // Only runs for scenarios tagged with @database
    initializeDatabase();
}
```

### 3. Handle Exceptions Gracefully
❌ **Bad:** Exceptions propagate and stop teardown
```java
@After
public void tearDown(Scenario scenario) {
    driver.quit(); // If this fails, report won't flush
    ExtentReportBase.flushReport();
}
```

✅ **Good:** Proper exception handling
```java
@After
public void tearDown(Scenario scenario) {
    try {
        if (driver != null) {
            driver.quit();
        }
    } catch (Exception e) {
        System.err.println("Error closing driver: " + e.getMessage());
    } finally {
        ExtentReportBase.flushReport();
    }
}
```

### 4. Use Hooks Inheritance Wisely
✅ **Good:** Hooks extend BaseTest for WebDriver access
```java
public class Hooks extends BaseTest {
    // Has access to driver and other BaseTest members
    @Before
    public void setUp(Scenario scenario) {
        initializeWebDriver(); // Can call BaseTest method
    }
}
```

### 5. Leverage Scenario Information
✅ **Good:** Use scenario object for logging and filtering
```java
@Before
public void setUp(Scenario scenario) {
    String name = scenario.getName();
    List<String> tags = scenario.getSourceTagNames();
    String status = scenario.getStatus().name();
    
    ExtentReportBase.logInfo("Scenario: " + name);
    ExtentReportBase.logInfo("Tags: " + tags);
}
```

### 6. Screenshot Capture Strategy
✅ **Good:** Strategic screenshot capture
```java
@AfterStep
public void captureScreenshotOnFailure(Scenario scenario) {
    if (scenario.isFailed()) {
        byte[] screenshot = takeScreenshot("FailureScreenshot");
        ExtentReportBase.attachScreenshot("Failed Step", screenshot);
        
        // Also capture with timestamp for uniqueness
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
            .format(new Date());
        ExtentReportBase.attachScreenshot(
            "FailedStep_" + timestamp, screenshot);
    }
}
```

### 7. Static Utility Access
✅ **Good:** Provide static methods for step definitions to access hooks data
```java
public class Hooks extends BaseTest {
    private static BrowserInteractionUtility browserInteraction;
    private static Scenario currentScenario;
    
    public static BrowserInteractionUtility getBrowserInteraction() {
        return browserInteraction;
    }
    
    public static Scenario getScenario() {
        return currentScenario;
    }
}
```

---

## Troubleshooting

### Issue 1: Hooks Not Running

**Symptom:** @Before/@After methods not executing

**Solutions:**
1. Verify hooks are in the glue path specified in test runner
   ```java
   @CucumberOptions(
       glue = {"com.amazon.stepdefinitions"} // Include hooks package
   )
   ```

2. Check glue path configuration in pom.xml
   ```xml
   <glue>
       <package>com.amazon.stepdefinitions</package>
   </glue>
   ```

3. Ensure hooks class is in correct package structure
   ```
   src/test/java/com/amazon/stepdefinitions/Hooks.java ✓
   ```

### Issue 2: WebDriver Not Available in Step Definitions

**Symptom:** NullPointerException when accessing driver in steps

**Causes & Solutions:**
1. Driver not initialized in @Before
   ```java
   @Before
   public void setUp(Scenario scenario) {
       initializeWebDriver(); // Must happen here
   }
   ```

2. Driver initialized but not accessible to steps
   ```java
   // In step definitions
   private WebDriver driver = BaseTest.driver; // Access from BaseTest
   ```

3. Hooks not configured properly
   ```java
   public class Hooks extends BaseTest { // Must extend BaseTest
       @Before
       public void setUp(Scenario scenario) {
           initializeWebDriver(); // Sets BaseTest.driver
       }
   }
   ```

### Issue 3: Screenshot Path Issues

**Symptom:** Screenshots not attaching or path errors

**Solutions:**
1. Create screenshot directory
   ```java
   new File(SCREENSHOT_PATH).mkdirs(); // In @Before
   ```

2. Use absolute paths when possible
   ```java
   String screenshotPath = new File("test-output/screenshots")
       .getAbsolutePath() + File.separator + fileName + ".png";
   ```

3. Handle file not found gracefully
   ```java
   try {
       FileOutputStream fos = new FileOutputStream(screenshotPath);
       fos.write(screenshotData);
       fos.close();
   } catch (FileNotFoundException e) {
       System.err.println("Screenshot path not found: " + screenshotPath);
   }
   ```

### Issue 4: Extent Report Not Generating

**Symptom:** Report HTML not created or empty

**Solutions:**
1. Ensure flushReport() called in @After
   ```java
   @After
   public void tearDown(Scenario scenario) {
       ExtentReportBase.flushReport(); // Must be called
   }
   ```

2. Check report path permissions
   ```java
   String reportPath = "test-output/ExtentReports/";
   File reportDir = new File(reportPath);
   if (!reportDir.exists()) {
       reportDir.mkdirs();
   }
   ```

3. Verify ExtentReportBase.createTest() called in @Before
   ```java
   @Before
   public void setUp(Scenario scenario) {
       ExtentReportBase.initializeExtentReport();
       ExtentReportBase.createTest(scenario.getName(), "Description");
   }
   ```

### Issue 5: Hook Order Issues

**Symptom:** Hooks running in unexpected order

**Resolution:** Hooks of same type run in order defined in file
```java
@Before(order = 1)
public void setupFirst(Scenario scenario) { }

@Before(order = 2)
public void setupSecond(Scenario scenario) { }
```

Output:
```
setupFirst() -> setupSecond() -> Steps
```

---

## Examples

### Example 1: Simple Hook Setup

**Feature File:**
```gherkin
Feature: Amazon Product Search
  @smoke
  Scenario: User can search for products
    Given user is on Amazon homepage
    When user searches for "Laptop"
    Then search results should be displayed
```

**Hooks.java:**
```java
@Before
public void setUp(Scenario scenario) {
    System.out.println("Starting test: " + scenario.getName());
    initializeWebDriver();
    ExtentReportBase.initializeExtentReport();
    ExtentReportBase.createTest(scenario.getName(), "BDD Test");
}

@After
public void tearDown(Scenario scenario) {
    System.out.println("Test completed with status: " + scenario.getStatus());
    if (driver != null) {
        driver.quit();
    }
    ExtentReportBase.flushReport();
}
```

**Output:**
```
Starting test: User can search for products
... test execution ...
Test completed with status: PASSED
✓ Extent Report generated successfully
```

---

### Example 2: Tag-Based Hook Filtering

**Feature File:**
```gherkin
Feature: Amazon Payment Processing
  @payment @critical
  Scenario: User completes payment
    Given user has items in cart
    When user proceeds to checkout
    And user enters payment details
    Then payment should be successful
```

**Hooks.java:**
```java
// Only for @payment scenarios
@Before("@payment")
public void setupPayment(Scenario scenario) {
    ExtentReportBase.logInfo("⚠ Payment Test - Extra Validation Enabled");
    setupPaymentEnvironment();
}

// Only for @critical scenarios
@Before("@critical")
public void setupCritical(Scenario scenario) {
    ExtentReportBase.logInfo("🔴 CRITICAL TEST - Enhanced Monitoring");
    enableEnhancedLogging();
}
```

**Execution:**
```
⚠ Payment Test - Extra Validation Enabled
🔴 CRITICAL TEST - Enhanced Monitoring
... test execution ...
```

---

### Example 3: Screenshot Capture on Failure

**Hooks.java:**
```java
@AfterStep
public void captureScreenshotOnFailure(Scenario scenario) {
    if (scenario.isFailed()) {
        try {
            String stepName = scenario.getName();
            byte[] screenshot = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.BYTES);
            
            String fileName = "Failed_" + stepName 
                + "_" + System.currentTimeMillis();
            
            ExtentReportBase.attachScreenshot(fileName, screenshot);
            System.out.println("❌ Screenshot captured for failed step");
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " 
                + e.getMessage());
        }
    }
}
```

**Output in Report:**
```
❌ Screenshot captured for failed step
└─ Failed_User_adds_product_to_cart_1705934521000.png
```

---

### Example 4: Conditional Teardown

**Hooks.java:**
```java
@After
public void tearDown(Scenario scenario) {
    if (scenario.isFailed()) {
        ExtentReportBase.logFail("Test FAILED: " + scenario.getName());
        
        // Keep browser open for debugging failed tests
        System.out.println("\n🔴 TEST FAILED - Browser remains open for inspection");
        System.out.println("Scenario: " + scenario.getName());
        System.out.println("Reason: " + scenario.getSourceTagNames());
        
        // Wait before closing (optional)
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    } else {
        ExtentReportBase.logPass("Test PASSED: " + scenario.getName());
    }
    
    // Always cleanup
    if (driver != null) {
        driver.quit();
    }
    ExtentReportBase.flushReport();
}
```

---

### Example 5: Complex Setup with Data Initialization

**Hooks.java:**
```java
@Before(order = 1)
public void setupWebDriver(Scenario scenario) {
    System.out.println("[STEP 1] Initializing WebDriver...");
    initializeWebDriver();
}

@Before(order = 2)
public void setupReporting(Scenario scenario) {
    System.out.println("[STEP 2] Initializing Extent Reports...");
    ExtentReportBase.initializeExtentReport();
    ExtentReportBase.createTest(
        scenario.getName(),
        "Test: " + scenario.getName()
    );
}

@Before(order = 3)
public void setupTestData(Scenario scenario) {
    System.out.println("[STEP 3] Setting up test data...");
    loadTestDataFromJSON("testdata.json");
    ExtentReportBase.logInfo("✓ Test data loaded successfully");
}

@Before(order = 4)
public void setupEnvironment(Scenario scenario) {
    System.out.println("[STEP 4] Setting up test environment...");
    ConfigReader.loadConfiguration();
    ExtentReportBase.logInfo("✓ Environment configured");
}
```

**Output:**
```
[STEP 1] Initializing WebDriver...
[STEP 2] Initializing Extent Reports...
[STEP 3] Setting up test data...
[STEP 4] Setting up test environment...
✓ Test data loaded successfully
✓ Environment configured
... test execution ...
```

---

## Integration with BrowserInteractionUtility

The Hooks class works seamlessly with BrowserInteractionUtility to provide a complete browser automation framework:

**How They Work Together:**

1. **@Before Hook** - Initializes WebDriver
2. **BrowserInteractionUtility** - Receives initialized WebDriver
3. **Step Definitions** - Use BrowserInteractionUtility for all browser actions
4. **@AfterStep Hook** - Captures screenshots on failures
5. **@After Hook** - Cleans up and generates reports

**Example Usage in Steps:**
```java
@When("user searches for {string}")
public void userSearchesFor(String product) {
    // Access BrowserInteractionUtility from Hooks
    BrowserInteractionUtility interaction = Hooks.getBrowserInteraction();
    
    // Use utility method
    interaction.sendKeys(AmazonHomePage.searchBox, product);
    ExtentReportBase.logPass("Searched for: " + product);
}
```

---

## Summary

| Aspect | Details |
|--------|---------|
| **Hooks Location** | `src/test/java/com/amazon/stepdefinitions/Hooks.java` |
| **Hooks Types** | @Before, @After, @BeforeStep, @AfterStep |
| **Key Methods** | setUp(), tearDown(), beforeStep(), afterStep() |
| **WebDriver Access** | Via BaseTest inheritance |
| **Reporting** | Via ExtentReportBase integration |
| **Screenshot Capture** | On step failure and scenario completion |
| **Best Practice** | Keep focused, handle exceptions, use tags |

---

**Related Documentation:**
- [BROWSER_INTERACTION_GUIDE.md](BROWSER_INTERACTION_GUIDE.md) - BrowserInteractionUtility methods
- [README.md](README.md) - Framework overview
- [UTILITIES_REFERENCE.md](UTILITIES_REFERENCE.md) - All utilities reference
