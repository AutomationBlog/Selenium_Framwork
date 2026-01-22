# Amazon Automation Framework

A comprehensive Maven-based Selenium automation framework built with Java, Cucumber, and TestNG for testing Amazon's add-to-cart functionality.

## Features

✅ **Selenium WebDriver** - Web browser automation
✅ **Cucumber BDD** - Behavior-driven development scenarios
✅ **TestNG** - Unit testing framework with reporting
✅ **Spark Reporting** - Beautiful HTML test reports
✅ **Utility Classes** - Handle Excel, JSON, Property files, and Text files
✅ **Page Object Model** - Maintainable and scalable test structure
✅ **WebDriverManager** - Automatic WebDriver management

## Project Structure

```
SeleniumFramework/
├── src/
│   ├── main/
│   │   └── java/com/amazon/
│   │       ├── base/
│   │       │   ├── BaseTest.java              # WebDriver initialization
│   │       │   └── ExtentReportBase.java      # Report configuration
│   │       ├── pages/
│   │       │   ├── AmazonHomePage.java        # Home page object
│   │       │   ├── AmazonSearchResultsPage.java
│   │       │   └── AmazonProductDetailsPage.java
│   │       └── utilities/
│   │           ├── ExcelUtility.java          # Excel file handling
│   │           ├── JsonUtility.java           # JSON file handling
│   │           ├── PropertyUtility.java       # Properties file handling
│   │           └── TextFileUtility.java       # Text file handling
│   └── test/
│       ├── java/com/amazon/
│       │   ├── stepdefinitions/
│       │   │   └── AmazonAddToCartSteps.java  # Cucumber step definitions
│       │   └── runners/
│       │       ├── CucumberRunnerTest.java    # Cucumber test runner
│       │       └── AmazonAddToCartTestNGRunner.java # TestNG runner
│       └── resources/
│           ├── features/
│           │   └── AddToCart.feature          # Cucumber scenarios
│           ├── config/
│           │   └── config.properties          # Configuration file
│           └── testdata/
│               ├── testdata.json
│               └── testdata.txt
├── pom.xml                                     # Maven configuration
├── testng.xml                                  # TestNG suite configuration
└── README.md
```

## Dependencies

### Core Dependencies
- **Selenium WebDriver** 4.15.0 - Web automation
- **Cucumber Java** 7.14.0 - BDD framework
- **Cucumber TestNG** 7.14.0 - Cucumber TestNG integration
- **TestNG** 7.8.1 - Testing framework
- **Extent Reports** 5.1.1 - Spark HTML reporting

### File Handling
- **Apache POI** 5.0.0 - Excel file handling
- **Gson** 2.10.1 - JSON processing

### Utilities
- **WebDriverManager** 5.6.3 - Automatic WebDriver management
- **SLF4J** 2.0.7 - Logging

## Installation & Setup

### Prerequisites
- Java 11 or higher
- Maven 3.6 or higher
- Chrome/Firefox/Edge browser

### Steps

1. **Clone the project**
   ```bash
   git clone <repository-url>
   cd Selenium_Framwork
   ```

2. **Install dependencies**
   ```bash
   mvn clean install
   ```

3. **Build the project**
   ```bash
   mvn clean compile
   ```

## Running Tests

### Run All Tests (TestNG)
```bash
mvn clean test
```

### Run Specific TestNG Class
```bash
mvn clean test -Dtest=AmazonAddToCartTestNGRunner
```

### Run Cucumber Tests
```bash
mvn clean test -Dtest=CucumberRunnerTest
```

### Run Tests with TestNG XML
```bash
mvn clean test -DsuiteXmlFile=testng.xml
```

## Test Cases

### TestNG Test Cases

1. **testAddLaptopToCart** - Add laptop to cart
   - Navigate to Amazon
   - Search for "laptop"
   - Click first product
   - Add to cart
   - Verify product in cart

2. **testAddMobilePhoneToCart** - Add mobile phone to cart
   - Navigate to Amazon
   - Search for "mobile phone"
   - Click first product
   - Add to cart
   - Verify product in cart

3. **testAddHeadphonesToCartWithQuantity** - Add headphones with custom quantity
   - Navigate to Amazon
   - Search for "headphones"
   - Click first product
   - Set quantity to 2
   - Add to cart
   - Verify product in cart

### Cucumber Scenarios

**Feature: Amazon Add to Cart Functionality**

Scenario 1: User adds a product to cart from Amazon website
- Given User is on Amazon home page
- When User searches for "laptop"
- Then Search results should be displayed
- When User clicks on first product
- And User adds product to cart
- Then Product should be added to cart

Scenario 2: User adds multiple products to cart
- Given User is on Amazon home page
- When User searches for "mobile phone"
- Then Search results should be displayed
- When User clicks on first product
- And User adds product to cart
- Then Product should be added to cart

## Configuration

### config.properties

Located in `src/test/resources/config/config.properties`

```properties
# Browser Configuration
browser=chrome                           # Options: chrome, firefox, edge
headless=false                          # Run in headless mode

# Amazon Base URL
baseURL=https://www.amazon.com

# Timeouts (in seconds)
implicitWait=10
explicitWait=10
pageLoadTimeout=20

# Test Data Path
testDataPath=src/test/resources/testdata/

# Report Configuration
reportPath=test-output/
reportName=AutomationReport

# Logging Level
logLevel=INFO
```

## Utility Classes

### ExcelUtility
```java
ExcelUtility excel = new ExcelUtility("path/to/file.xlsx");
String data = excel.getCellData("SheetName", 0, 0);
List<Map<String, String>> data = excel.getSheetData("SheetName");
```

### JsonUtility
```java
JsonUtility json = new JsonUtility("path/to/file.json");
String value = json.getValueByKey("key");
List<Map<String, String>> list = json.jsonArrayToList();
```

### PropertyUtility
```java
PropertyUtility prop = new PropertyUtility("path/to/config.properties");
String value = prop.getProperty("key");
String value = prop.getProperty("key", "defaultValue");
```

### TextFileUtility
```java
List<String> lines = TextFileUtility.readTextFile("path/to/file.txt");
TextFileUtility.writeToTextFile("path/to/file.txt", "content", false);
TextFileUtility.appendToFile("path/to/file.txt", "content");
```

## Page Objects

### AmazonHomePage
- `navigateToAmazon(String url)` - Navigate to Amazon
- `searchProduct(String productName)` - Search for product
- `getPageTitle()` - Get page title
- `isSearchBoxDisplayed()` - Check search box visibility

### AmazonSearchResultsPage
- `getFirstProductTitle()` - Get first product title
- `clickFirstProduct()` - Click first product
- `addFirstProductToCart()` - Add first product to cart
- `getCartCount()` - Get cart count

### AmazonProductDetailsPage
- `getProductTitle()` - Get product title
- `addToCart()` - Add product to cart
- `waitForCartConfirmation()` - Wait for cart confirmation
- `setQuantity(String qty)` - Set product quantity
- `getProductPrice()` - Get product price

## Reports

### Spark HTML Report
After test execution, HTML report is generated at:
```
test-output/ExtentReports/AutomationReport_[timestamp].html
```

### Cucumber Report
Cucumber reports are generated at:
```
test-output/CucumberReports/cucumber-report.html
```

## Logging

The framework uses SLF4J for logging. Logs are printed to console with INFO level.

To change log level, modify `config.properties`:
```properties
logLevel=DEBUG
```

## Best Practices

1. **Page Object Model** - All web elements are encapsulated in page objects
2. **Wait Strategies** - Explicit waits used for better reliability
3. **Configuration Management** - External configuration in properties file
4. **Test Data Separation** - Test data in JSON/Excel/Text files
5. **Comprehensive Reporting** - Detailed Spark HTML reports with screenshots
6. **Reusable Utilities** - Utility classes for common operations

## Troubleshooting

### WebDriver Not Found
```bash
mvn clean install -U
```

### Tests Not Running
- Ensure Java 11+ is installed: `java -version`
- Ensure Maven is installed: `mvn -version`
- Clear Maven cache: `mvn clean`

### Browser Not Loading
- Check internet connection
- Ensure browser is installed
- WebDriverManager will automatically download the correct driver version

## Contributing

1. Follow the existing code structure
2. Add tests for new features
3. Update documentation
4. Follow naming conventions

## License

This project is licensed under the MIT License.

## Contact

For issues and support, please raise an issue in the repository.

---

**Happy Testing! 🚀**
