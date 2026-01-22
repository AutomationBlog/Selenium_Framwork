# Cucumber Hooks Quick Reference Card

**For Developers Using the Amazon Automation Framework**

---

## 🎣 What are Cucumber Hooks?

Hooks are methods that run automatically before/after scenarios and steps. They handle setup, logging, and cleanup without being called from feature files.

---

## 📍 Hook Types & Timing

```
┌─────────────────────────────────────────┐
│ SCENARIO START                          │
├─────────────────────────────────────────┤
│ @Before ◄──── Runs once at start       │
├─────────────────────────────────────────┤
│ For Each Step:                          │
│   ├─ @BeforeStep ◄── Before step       │
│   ├─ [STEP EXECUTES]                   │
│   └─ @AfterStep ◄──── After step       │
├─────────────────────────────────────────┤
│ @After ◄───── Runs once at end         │
├─────────────────────────────────────────┤
│ SCENARIO END                            │
└─────────────────────────────────────────┘
```

---

## 🔧 The 4 Hooks at a Glance

| Hook | When | Purpose | Example |
|------|------|---------|---------|
| **@Before** | Before scenario | Initialize WebDriver, Extent Reports | Setup test environment |
| **@BeforeStep** | Before each step | Log step info | Track test execution |
| **@AfterStep** | After each step | Capture screenshot on failure | Debug failures |
| **@After** | After scenario | Cleanup resources | Close browser, flush report |

---

## 📝 Hook Signatures (What to Use in Code)

```java
// In Hooks.java class

@Before
public void setUp(Scenario scenario) {
    // Initialize WebDriver
    // Initialize Extent Reports
    // Create test node
}

@BeforeStep
public void beforeStep(Scenario scenario) {
    // Log step execution
}

@AfterStep
public void afterStep(Scenario scenario) {
    // Capture screenshot on failure
}

@After
public void tearDown(Scenario scenario) {
    // Close WebDriver
    // Flush reports
}
```

---

## 🚀 Using Hooks in Step Definitions

### Access WebDriver (Initialized in @Before)
```java
// WebDriver is initialized by @Before hook
// Access it directly from BaseTest
private WebDriver driver = BaseTest.driver;
```

### Access BrowserInteractionUtility
```java
// Get from Hooks static method
BrowserInteractionUtility interaction = Hooks.getBrowserInteraction();
interaction.click(By.id("button"));
```

### Log to Extent Report
```java
// Available anywhere in steps
ExtentReportBase.logPass("Action completed successfully");
ExtentReportBase.logFail("Action failed");
ExtentReportBase.logInfo("Step info message");
```

### Access Current Scenario
```java
// Get from Hooks static method
Scenario scenario = Hooks.getScenario();
String scenarioName = scenario.getName();
```

---

## 💡 Common Patterns

### Pattern 1: Log Step Execution
```java
@When("user searches for {string}")
public void userSearchesFor(String product) {
    ExtentReportBase.logInfo("Searching for: " + product);
    
    // Your step code here
    
    ExtentReportBase.logPass("Search completed for: " + product);
}
```

### Pattern 2: Error Handling with Logging
```java
@Then("product should be displayed")
public void verifyProduct() {
    try {
        assert productPage.isProductDisplayed() : "Product not found";
        ExtentReportBase.logPass("✓ Product is displayed");
    } catch (AssertionError e) {
        ExtentReportBase.logFail("✗ " + e.getMessage());
        throw e;
    }
}
```

### Pattern 3: Using BrowserInteractionUtility
```java
@When("user clicks the add to cart button")
public void addToCart() {
    BrowserInteractionUtility browserUtil = Hooks.getBrowserInteraction();
    browserUtil.click(AmazonProductDetailsPage.addToCartButton);
    ExtentReportBase.logPass("✓ Clicked Add to Cart");
}
```

---

## 📍 Locations You Need to Know

```
Hooks.java
└─ Location: src/test/java/com/amazon/stepdefinitions/Hooks.java
   Size: 182 lines
   Contains: 4 hooks + utilities

AmazonAddToCartSteps.java
└─ Location: src/test/java/com/amazon/stepdefinitions/AmazonAddToCartSteps.java
   Contains: 10 step definitions
   Uses: Hooks for setup/teardown

ExtentReportBase.java
└─ Location: src/main/java/com/amazon/base/ExtentReportBase.java
   Contains: 12 methods for reporting
   Used by: Hooks and step definitions

Reports
└─ Location: test-output/ExtentReports/
   Generated after each test run
   Contains: HTML report + screenshots
```

---

## 🎯 Key Methods You'll Use

### From Hooks
```java
Hooks.getBrowserInteraction()      // Get browser utility
Hooks.getScenario()                // Get current scenario
```

### From ExtentReportBase
```java
ExtentReportBase.logPass(String)      // Log success
ExtentReportBase.logFail(String)      // Log failure
ExtentReportBase.logInfo(String)      // Log info
ExtentReportBase.logWarning(String)   // Log warning
ExtentReportBase.attachScreenshot()   // Attach screenshot (auto in hooks)
```

### From BrowserInteractionUtility
```java
interaction.click(By)                  // Click element
interaction.clearAndType(By, String)   // Type text
interaction.getText(By)                // Get text
interaction.waitForElementToBeVisible(By)  // Wait for element
```

---

## ✅ Checklist When Writing New Steps

- [ ] Import ExtentReportBase
- [ ] Add @Given/@When/@Then annotation
- [ ] Add ExtentReportBase.logPass/logFail calls
- [ ] Use BrowserInteractionUtility for interactions
- [ ] Add meaningful log messages
- [ ] Handle assertions with try-catch
- [ ] Test step before committing

---

## ⚠️ Common Mistakes to Avoid

### ❌ Wrong: Initializing WebDriver in steps
```java
@When("user navigates to site")
public void navigate() {
    WebDriver driver = new ChromeDriver(); // ❌ WRONG
}
```

### ✅ Right: Using WebDriver from BaseTest
```java
@When("user navigates to site")
public void navigate() {
    driver.get("https://amazon.com"); // ✅ CORRECT - driver from BaseTest
}
```

---

### ❌ Wrong: Logging to console only
```java
@Then("product displayed")
public void verify() {
    System.out.println("Product shown"); // ❌ Missing report
}
```

### ✅ Right: Logging to ExtentReportBase
```java
@Then("product displayed")
public void verify() {
    ExtentReportBase.logPass("✓ Product shown"); // ✅ Logs to report
}
```

---

### ❌ Wrong: Using vanilla Selenium
```java
WebElement element = driver.findElement(By.id("btn"));
element.click(); // ❌ No wait, no error handling
```

### ✅ Right: Using BrowserInteractionUtility
```java
BrowserInteractionUtility util = Hooks.getBrowserInteraction();
util.click(By.id("btn")); // ✅ Has wait + error handling
```

---

## 🚦 Test Execution Flow

```
Feature File (AddToCart.feature)
        │
        ▼
Cucumber Scenario Starts
        │
        ▼
@Before Hook (Hooks.setUp)
  • Initialize WebDriver (Chrome/Firefox/Edge)
  • Initialize Extent Reports
  • Create test node in report
  • Log "Scenario started: ..."
        │
        ▼
Step Definition 1 Executes
  @BeforeStep → Step Code → @AfterStep
        │
        ▼
Step Definition 2 Executes
  @BeforeStep → Step Code → @AfterStep
        │
        ▼
Step Definition 3 Executes
  @BeforeStep → Step Code → @AfterStep
        │
        ▼
@After Hook (Hooks.tearDown)
  • Log scenario status
  • Capture final screenshot
  • Delete cookies
  • Close WebDriver (driver.quit())
  • Flush Extent Reports
        │
        ▼
Extent Report Generated
  Location: test-output/ExtentReports/AutomationReport_[timestamp].html
```

---

## 📊 Report Structure

After test execution, you get:

```
test-output/ExtentReports/
├── AutomationReport_22_01_2025_19_30_15.html  ← Open this in browser
├── screenshots/
│   ├── Failed_Step_1705933415000.png          ← Failure screenshots
│   └── Final_Screenshot_1705933420000.png     ← Final screenshots
└── (other report files)
```

---

## 🔍 How to Debug

### Check Console Output
```
Look for messages like:
======================================================================
▶ START: User adds product to cart
Tags: [@smoke, @regression]
======================================================================
✓ Scenario initialized: User adds product to cart
⏳ Step executing...
✓ Step completed successfully
...
======================================================================
▶ END: User adds product to cart
======================================================================
```

### Check Extent Report
1. Open `test-output/ExtentReports/AutomationReport_[timestamp].html` in browser
2. Look for red (failed) tests
3. View attached screenshots for failures
4. Read log messages for context

### Check Screenshots
```
Failed steps automatically have screenshots
Look in: test-output/ExtentReports/screenshots/
```

---

## 🎓 Learning Resources

### For Quick Start
📖 [QUICK_START.md](QUICK_START.md) - 5-minute guide

### For Hooks Details
📖 [CUCUMBER_HOOKS_GUIDE.md](CUCUMBER_HOOKS_GUIDE.md) - Complete guide (1,000+ lines)
📖 [HOOKS_IMPLEMENTATION_SUMMARY.md](HOOKS_IMPLEMENTATION_SUMMARY.md) - Implementation details

### For Framework Overview
📖 [README.md](README.md) - Full documentation
📖 [INDEX.md](INDEX.md) - Documentation index

### For Utilities Reference
📖 [UTILITIES_REFERENCE.md](UTILITIES_REFERENCE.md) - Quick reference
📖 [BROWSER_INTERACTION_GUIDE.md](BROWSER_INTERACTION_GUIDE.md) - Browser interactions

---

## 🆘 Quick Help

### Q: Where is WebDriver initialized?
**A:** In the @Before hook (setUp method) in Hooks.java

### Q: How do I use BrowserInteractionUtility?
**A:** Call `Hooks.getBrowserInteraction()` in your step definition

### Q: Where are screenshots saved?
**A:** In `test-output/ExtentReports/screenshots/` directory

### Q: How do I see test logs?
**A:** Open `test-output/ExtentReports/AutomationReport_[timestamp].html` in browser

### Q: What if a step fails?
**A:** @AfterStep automatically captures screenshot, @After cleans up and flushes report

### Q: How do I add logging?
**A:** Use `ExtentReportBase.logPass()`, `logFail()`, or `logInfo()`

### Q: Can I run specific scenarios?
**A:** Yes: `mvn test -Dcucumber.filter.tags="@smoke"`

### Q: What should I log?
**A:** Log each important action:
- Before action: `logInfo("Performing action...")`
- After success: `logPass("✓ Action completed")`
- On failure: `logFail("✗ Action failed: reason")`

---

## 📋 Code Template for New Steps

```java
@When("user performs some action")
public void userPerformsAction() {
    try {
        // Log what we're doing
        ExtentReportBase.logInfo("Starting user action...");
        
        // Get utilities
        BrowserInteractionUtility util = Hooks.getBrowserInteraction();
        
        // Perform action
        util.click(By.id("elementId"));
        
        // Log success
        ExtentReportBase.logPass("✓ User action completed successfully");
        
    } catch (Exception e) {
        // Log failure
        ExtentReportBase.logFail("✗ User action failed: " + e.getMessage());
        throw e;
    }
}
```

---

## 🎯 Pro Tips

1. **Always log your steps** - Helps with debugging
2. **Use try-catch for assertions** - Logs failures properly
3. **Keep steps small and focused** - One action per step
4. **Use meaningful messages** - Make it clear what happened
5. **Check reports after runs** - Review screenshots for issues
6. **Use @smoke tags for quick tests** - Run fast feedback tests
7. **Use BrowserInteractionUtility** - Built-in waits and error handling
8. **Never hardcode waits (Thread.sleep)** - Use WebDriverWait instead

---

## 📞 Still Need Help?

1. **Check CUCUMBER_HOOKS_GUIDE.md** - Comprehensive guide
2. **Look at examples** - In BROWSER_INTERACTION_GUIDE.md
3. **Check existing steps** - In AmazonAddToCartSteps.java
4. **Read troubleshooting** - In CUCUMBER_HOOKS_GUIDE.md

---

**Remember:** Hooks handle all your setup/teardown. Just focus on writing clear, well-logged step definitions!

---

*Last Updated: January 2025*
*Status: ✅ Ready for Use*
