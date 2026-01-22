# Utility Classes Quick Reference

## BrowserInteractionUtility

Comprehensive utility class for common browser interactions, element manipulation, and Selenium operations.

### Import
```java
import com.amazon.utilities.BrowserInteractionUtility;
```

### Initialization
```java
// Default 10-second timeout
BrowserInteractionUtility browserInteraction = new BrowserInteractionUtility(driver);

// Custom timeout
BrowserInteractionUtility browserInteraction = new BrowserInteractionUtility(driver, 15);
```

### Click Operations

#### Simple Click
```java
browserInteraction.click(By.id("buttonId"));
```

#### Click WebElement
```java
WebElement element = driver.findElement(By.id("element"));
browserInteraction.click(element);
```

#### Double Click
```java
browserInteraction.doubleClick(By.id("element"));
```

#### Right Click
```java
browserInteraction.rightClick(By.id("element"));
```

---

### Text Input Operations

#### Clear and Type Text
```java
browserInteraction.clearAndType(By.id("searchBox"), "laptop");
```

#### Type Without Clearing
```java
browserInteraction.type(By.id("textField"), "additional text");
```

#### Type With Character Delay
```java
browserInteraction.typeWithDelay(By.id("passwordField"), "password123", 100); // 100ms delay
```

#### Clear Element
```java
browserInteraction.clearElement(By.id("textField"));
```

---

### Text Retrieval Operations

#### Get Element Text
```java
String text = browserInteraction.getText(By.id("heading"));
```

#### Get Text from WebElement
```java
WebElement element = driver.findElement(By.id("element"));
String text = browserInteraction.getText(element);
```

#### Get Attribute Value
```java
String href = browserInteraction.getAttribute(By.xpath("//a"), "href");
String placeholder = browserInteraction.getAttribute(By.id("input"), "placeholder");
```

#### Get CSS Value
```java
String color = browserInteraction.getCssValue(By.id("button"), "background-color");
String fontSize = browserInteraction.getCssValue(By.id("text"), "font-size");
```

---

### Dropdown/Select Operations

#### Select by Visible Text
```java
browserInteraction.selectByVisibleText(By.id("dropdown"), "Option 1");
```

#### Select by Value
```java
browserInteraction.selectByValue(By.id("dropdown"), "value1");
```

#### Select by Index
```java
browserInteraction.selectByIndex(By.id("dropdown"), 0);
```

#### Get All Options
```java
List<String> options = browserInteraction.getDropdownOptions(By.id("dropdown"));
for (String option : options) {
    System.out.println(option);
}
```

#### Get Selected Option
```java
String selected = browserInteraction.getSelectedOption(By.id("dropdown"));
```

---

### Wait Operations

#### Wait for Element Visible
```java
WebElement element = browserInteraction.waitForElementToBeVisible(By.id("element"));
```

#### Wait for Element Clickable
```java
WebElement element = browserInteraction.waitForElementToBeClickable(By.id("button"));
```

#### Wait for Element Present
```java
WebElement element = browserInteraction.waitForElementToBePresent(By.id("element"));
```

#### Wait for Element Invisible
```java
boolean result = browserInteraction.waitForElementToBeInvisible(By.id("loader"));
```

#### Wait for Element to Disappear
```java
boolean result = browserInteraction.waitForElementToDisappear(By.id("popup"), 15); // 15 seconds
```

#### Wait for Text in Element
```java
boolean result = browserInteraction.waitForTextInElement(By.id("message"), "Success");
```

#### Wait for Seconds
```java
browserInteraction.waitForSeconds(3);  // 3 seconds
```

---

### Element Visibility & Presence Check

#### Check if Element Displayed
```java
if (browserInteraction.isElementDisplayed(By.id("element"))) {
    System.out.println("Element is visible");
}
```

#### Check if Element Exists
```java
if (browserInteraction.isElementPresent(By.id("element"))) {
    System.out.println("Element exists in DOM");
}
```

#### Check if Element Enabled
```java
if (browserInteraction.isElementEnabled(By.id("button"))) {
    System.out.println("Button is enabled");
}
```

---

### Scrolling Operations

#### Scroll to Element
```java
browserInteraction.scrollToElement(By.id("element"));
```

#### Scroll to Element and Wait
```java
browserInteraction.scrollToElementAndWait(By.id("element"));
```

#### Scroll to Top
```java
browserInteraction.scrollToTop();
```

#### Scroll to Bottom
```java
browserInteraction.scrollToBottom();
```

#### Scroll by Pixels
```java
browserInteraction.scrollByPixels(500);  // Scroll down 500 pixels
browserInteraction.scrollByPixels(-300); // Scroll up 300 pixels
```

---

### Mouse Operations

#### Hover Over Element
```java
browserInteraction.hoverOverElement(By.id("menuItem"));
```

#### Drag and Drop
```java
browserInteraction.dragAndDrop(By.id("source"), By.id("target"));
```

#### Drag Element by Offset
```java
browserInteraction.dragElementByOffset(By.id("element"), 100, 50); // x: 100, y: 50
```

---

### Keyboard Operations

#### Press Enter
```java
browserInteraction.pressEnter(By.id("searchBox"));
```

#### Press Tab
```java
browserInteraction.pressTab();
```

#### Press Escape
```java
browserInteraction.pressEscape();
```

#### Select All (Ctrl+A)
```java
browserInteraction.selectAll();
```

#### Copy (Ctrl+C)
```java
browserInteraction.copy();
```

#### Paste (Ctrl+V)
```java
browserInteraction.paste();
```

---

### JavaScript Operations

#### Execute JavaScript
```java
browserInteraction.executeScript("window.scrollTo(0, 0);");
```

#### Execute JavaScript with Arguments
```java
Object result = browserInteraction.executeScript("return arguments[0].text;", element);
```

#### Execute Async JavaScript
```java
browserInteraction.executeAsyncScript("var callback = arguments[0]; setTimeout(callback, 2000);");
```

#### Get Page Title via JS
```java
String title = browserInteraction.getPageTitleViaJS();
```

#### Get Page URL via JS
```java
String url = browserInteraction.getPageURLViaJS();
```

#### Highlight Element (Debugging)
```java
browserInteraction.highlightElement(By.id("element"));
// Code...
browserInteraction.unhighlightElement(By.id("element"));
```

---

### Alert Operations

#### Accept Alert
```java
browserInteraction.acceptAlert();
```

#### Dismiss Alert
```java
browserInteraction.dismissAlert();
```

#### Get Alert Text
```java
String alertText = browserInteraction.getAlertText();
```

#### Type in Alert
```java
browserInteraction.typeInAlert("text");
browserInteraction.acceptAlert();
```

---

### Window/Tab Operations

#### Get Current Window Handle
```java
String windowHandle = browserInteraction.getCurrentWindowHandle();
```

#### Get All Window Handles
```java
Set<String> handles = browserInteraction.getAllWindowHandles();
```

#### Switch to Window by Index
```java
browserInteraction.switchToWindowByIndex(1);  // Switch to second window
```

#### Switch to Window by Title
```java
browserInteraction.switchToWindowByTitle("Google");
```

#### Switch to Frame
```java
browserInteraction.switchToFrame(By.id("iframeId"));
```

#### Switch to Frame by Index
```java
browserInteraction.switchToFrame(0);
```

#### Switch to Parent Frame
```java
browserInteraction.switchToParentFrame();
```

#### Switch to Default Content
```java
browserInteraction.switchToDefaultContent();
```

---

### Find Elements Operations

#### Find Single Element
```java
WebElement element = browserInteraction.findElement(By.id("element"));
```

#### Find Multiple Elements
```java
List<WebElement> elements = browserInteraction.findElements(By.className("item"));
```

#### Get Element Count
```java
int count = browserInteraction.getElementCount(By.className("item"));
```

---

### Navigation Operations

#### Navigate to URL
```java
browserInteraction.navigateTo("https://www.amazon.com");
```

#### Navigate Back
```java
browserInteraction.navigateBack();
```

#### Navigate Forward
```java
browserInteraction.navigateForward();
```

#### Refresh Page
```java
browserInteraction.refreshPage();
```

#### Get Current URL
```java
String url = browserInteraction.getCurrentURL();
```

#### Get Page Title
```java
String title = browserInteraction.getPageTitle();
```

---

### Cookie Operations

#### Add Cookie
```java
browserInteraction.addCookie("sessionId", "12345");
```

#### Get Cookie
```java
Cookie cookie = browserInteraction.getCookie("sessionId");
```

#### Delete Cookie
```java
browserInteraction.deleteCookie("sessionId");
```

#### Delete All Cookies
```java
browserInteraction.deleteAllCookies();
```

---

## Complete Page Object Example with BrowserInteractionUtility

```java
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.amazon.utilities.BrowserInteractionUtility;

public class LoginPage {
    private WebDriver driver;
    private BrowserInteractionUtility browserInteraction;
    
    // Locators
    private By usernameField = By.id("username");
    private By passwordField = By.id("password");
    private By loginButton = By.id("loginBtn");
    private By errorMessage = By.className("error");
    
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.browserInteraction = new BrowserInteractionUtility(driver);
    }
    
    public void login(String username, String password) {
        browserInteraction.clearAndType(usernameField, username);
        browserInteraction.clearAndType(passwordField, password);
        browserInteraction.click(loginButton);
        browserInteraction.waitForSeconds(2);
    }
    
    public String getErrorMessage() {
        return browserInteraction.getText(errorMessage);
    }
    
    public boolean isLoginButtonDisplayed() {
        return browserInteraction.isElementDisplayed(loginButton);
    }
    
    public void navigateToLoginPage(String url) {
        browserInteraction.navigateTo(url);
        browserInteraction.waitForElementToBeVisible(usernameField);
    }
}
```

---

## Complete Step Definition Example

```java
import com.amazon.utilities.BrowserInteractionUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginSteps {
    private WebDriver driver;
    private BrowserInteractionUtility browserInteraction;
    private LoginPage loginPage;
    
    @Before
    public void setUp() {
        driver = new ChromeDriver();
        browserInteraction = new BrowserInteractionUtility(driver);
        loginPage = new LoginPage(driver);
    }
    
    @Given("User is on login page")
    public void userOnLoginPage() {
        loginPage.navigateToLoginPage("https://example.com/login");
    }
    
    @When("User enters {string} and {string}")
    public void userEntersCredentials(String username, String password) {
        loginPage.login(username, password);
    }
    
    @Then("User should see error message")
    public void verifiesErrorMessage() {
        String errorMsg = loginPage.getErrorMessage();
        Assert.assertEquals(errorMsg, "Invalid credentials");
    }
    
    @After
    public void tearDown() {
        driver.quit();
    }
}
```

---

## ExcelUtility

### Import
```java
import com.amazon.utilities.ExcelUtility;
```

### Usage Examples

#### Read Single Cell
```java
ExcelUtility excel = new ExcelUtility("src/test/resources/testdata/data.xlsx");
String value = excel.getCellData("Sheet1", 0, 0);  // Row 0, Column 0
String value = excel.getCellData("Sheet1", 1, 2);  // Row 1, Column 2
```

#### Read Entire Sheet as List of Maps
```java
List<Map<String, String>> data = excel.getSheetData("Sheet1");
for (Map<String, String> row : data) {
    String name = row.get("Name");
    String email = row.get("Email");
    System.out.println(name + " : " + email);
}
```

#### Write to Cell
```java
excel.writeCellData("Sheet1", 0, 0, "Test Data");
```

#### Get Row/Column Count
```java
int rowCount = excel.getRowCount("Sheet1");
int colCount = excel.getColumnCount("Sheet1");
```

---

## JsonUtility

### Import
```java
import com.amazon.utilities.JsonUtility;
```

### Usage Examples

#### Read Value by Key
```java
JsonUtility json = new JsonUtility("src/test/resources/testdata/config.json");
String value = json.getValueByKey("username");  // {"username": "admin"}
```

#### Read Nested Value
```java
String value = json.getNestedValue("user", "email");  // {"user": {"email": "test@example.com"}}
```

#### Convert Array to List of Maps
```java
List<Map<String, String>> data = json.jsonArrayToList();
// JSON: [{"name": "John", "age": "30"}, {"name": "Jane", "age": "25"}]
for (Map<String, String> item : data) {
    System.out.println(item.get("name"));
}
```

#### Write JSON to File
```java
Map<String, Object> data = new HashMap<>();
data.put("username", "admin");
data.put("password", "password123");
JsonUtility.writeJsonFile("config.json", data);
```

#### Get Entire JSON Object
```java
JsonObject json = json.getJsonObject();
```

---

## PropertyUtility

### Import
```java
import com.amazon.utilities.PropertyUtility;
```

### Usage Examples

#### Read Property
```java
PropertyUtility prop = new PropertyUtility("src/test/resources/config/config.properties");
String browser = prop.getProperty("browser");  // Returns "chrome"
String url = prop.getProperty("baseURL");      // Returns "https://www.amazon.com"
```

#### Read with Default Value
```java
String timeout = prop.getProperty("timeout", "10");  // Returns "10" if not found
```

#### Set Property
```java
prop.setProperty("newKey", "newValue");
```

#### Save Properties
```java
prop.setProperty("lastRun", "2026-01-22");
prop.saveProperties("src/test/resources/config/config.properties");
```

#### Get All Properties
```java
Map<String, String> allProps = prop.getAllProperties();
for (Map.Entry<String, String> entry : allProps.entrySet()) {
    System.out.println(entry.getKey() + " = " + entry.getValue());
}
```

#### Check Property Exists
```java
if (prop.containsProperty("baseURL")) {
    System.out.println("Property exists");
}
```

#### Remove Property
```java
prop.removeProperty("oldKey");
```

---

## TextFileUtility

### Import
```java
import com.amazon.utilities.TextFileUtility;
```

### Usage Examples

#### Read All Lines
```java
List<String> lines = TextFileUtility.readTextFile("src/test/resources/testdata/data.txt");
for (String line : lines) {
    System.out.println(line);
}
```

#### Read Single Line
```java
String line = TextFileUtility.readLine("src/test/resources/testdata/data.txt", 1);  // Line 1
```

#### Write to File (Overwrite)
```java
TextFileUtility.writeToTextFile("output.txt", "Hello World", false);
```

#### Write to File (Append)
```java
TextFileUtility.writeToTextFile("output.txt", "New Content", true);
```

#### Write Multiple Lines
```java
List<String> lines = new ArrayList<>();
lines.add("Line 1");
lines.add("Line 2");
lines.add("Line 3");
TextFileUtility.writeLines("output.txt", lines, false);
```

#### Search in File
```java
if (TextFileUtility.searchInFile("data.txt", "searchText")) {
    System.out.println("Text found");
}
```

#### Append to File
```java
TextFileUtility.appendToFile("log.txt", "New log entry");
```

#### Get Line Count
```java
int count = TextFileUtility.getLineCount("data.txt");
```

#### Clear File
```java
TextFileUtility.clearFile("file.txt");
```

#### Check File Exists
```java
if (TextFileUtility.fileExists("data.txt")) {
    System.out.println("File exists");
}
```

#### Delete File
```java
TextFileUtility.deleteFile("file.txt");
```

---

## BaseTest Class

### Import
```java
import com.amazon.base.BaseTest;
```

### Usage in Test Class
```java
public class MyTest extends BaseTest {
    @BeforeMethod
    public void setUp() throws IOException {
        initializeDriver("chrome");  // or null for default browser
    }
    
    @AfterMethod
    public void tearDown() {
        closeBrowser();
    }
    
    @Test
    public void testSomething() {
        WebDriver driver = getDriver();
        driver.navigate().to("https://www.example.com");
    }
}
```

### Methods
- `initializeDriver(String browserType)` - Initialize WebDriver
- `getDriver()` - Get WebDriver instance
- `closeBrowser()` - Close browser
- `getBaseURL()` - Get base URL from config

---

## ExtentReportBase Class

### Import
```java
import com.amazon.base.ExtentReportBase;
```

### Usage in Test Class
```java
public class MyTest extends BaseTest {
    
    @BeforeSuite
    public void initReport() {
        ExtentReportBase.initializeExtentReport();
    }
    
    @BeforeMethod
    public void createTest() {
        ExtentReportBase.createTest("Test Name", "Test Description");
    }
    
    @Test
    public void testSomething() {
        try {
            // Test code
            ExtentReportBase.logPass("Step passed");
        } catch (Exception e) {
            ExtentReportBase.logFail("Test failed: " + e.getMessage());
        }
    }
    
    @AfterSuite
    public void flushReport() {
        ExtentReportBase.flushReport();
    }
}
```

### Methods
- `initializeExtentReport()` - Initialize Extent Reports
- `createTest(String name, String description)` - Create test node
- `logPass(String message)` - Log pass
- `logFail(String message)` - Log fail
- `logSkip(String message)` - Log skip
- `logInfo(String message)` - Log info
- `flushReport()` - Generate report

---

## Sample Test Data Files

### testdata.json
```json
[
  {
    "productName": "laptop",
    "quantity": 1,
    "category": "Electronics"
  },
  {
    "productName": "mobile phone",
    "quantity": 2,
    "category": "Electronics"
  }
]
```

### config.properties
```properties
browser=chrome
baseURL=https://www.amazon.com
implicitWait=10
explicitWait=10
```

### testdata.txt
```
Line 1 of test data
Line 2 of test data
Line 3 of test data
```

---

## Complete Integration Example

```java
import com.amazon.base.BaseTest;
import com.amazon.base.ExtentReportBase;
import com.amazon.utilities.*;
import org.testng.annotations.*;

public class CompleteIntegrationTest extends BaseTest {
    
    @BeforeSuite
    public void setUp() throws Exception {
        ExtentReportBase.initializeExtentReport();
        initializeDriver(null);
    }
    
    @BeforeMethod
    public void createTest() {
        ExtentReportBase.createTest("Complete Integration Test", "Test all utilities");
    }
    
    @Test
    public void testAllUtilities() throws Exception {
        try {
            // Read from JSON
            JsonUtility json = new JsonUtility("src/test/resources/testdata/testdata.json");
            List<Map<String, String>> jsonData = json.jsonArrayToList();
            ExtentReportBase.logPass("Read JSON data: " + jsonData.size() + " items");
            
            // Read from Properties
            PropertyUtility prop = new PropertyUtility("src/test/resources/config/config.properties");
            String browser = prop.getProperty("browser");
            ExtentReportBase.logPass("Browser from properties: " + browser);
            
            // Read from Text file
            List<String> lines = TextFileUtility.readTextFile("src/test/resources/testdata/testdata.txt");
            ExtentReportBase.logPass("Read text file: " + lines.size() + " lines");
            
            // Use WebDriver
            driver.navigate().to(prop.getProperty("baseURL"));
            ExtentReportBase.logPass("Navigated to base URL");
            
        } catch (Exception e) {
            ExtentReportBase.logFail("Test failed: " + e.getMessage());
            throw e;
        }
    }
    
    @AfterSuite
    public void tearDown() {
        ExtentReportBase.flushReport();
        closeBrowser();
    }
}
```

---

## Tips & Best Practices

1. **Always use try-catch** for file operations
2. **Close resources** properly to avoid file locks
3. **Use relative paths** for test data files
4. **Validate data** before using in tests
5. **Log all important steps** for debugging
6. **Keep test data organized** in testdata folder
7. **Use meaningful names** for test data files
8. **Version control** test data carefully
