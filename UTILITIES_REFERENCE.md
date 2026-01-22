# Utility Classes Quick Reference

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
