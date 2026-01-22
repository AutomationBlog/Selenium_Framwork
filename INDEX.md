# 📚 Complete Framework Documentation Index

## 🎯 Where to Start

### New to Framework?
👉 **Start here:** [QUICK_START.md](QUICK_START.md) - Get running in 5 minutes

### Want to Run Tests?
👉 **Go here:** [EXECUTION_GUIDE.md](EXECUTION_GUIDE.md) - Detailed execution instructions

### Need Code Examples?
👉 **Check here:** [UTILITIES_REFERENCE.md](UTILITIES_REFERENCE.md) - Code examples for all utilities

### Want Full Details?
👉 **Read here:** [README.md](README.md) - Complete documentation

---

## 📖 All Documentation Files

| File | Purpose | Read Time |
|------|---------|-----------|
| **QUICK_START.md** | 5-minute quick start guide | 5 min |
| **EXECUTION_GUIDE.md** | Detailed execution instructions | 10 min |
| **README.md** | Complete framework documentation | 20 min |
| **UTILITIES_REFERENCE.md** | Code examples and quick reference | 15 min |
| **PROJECT_STRUCTURE.md** | Complete file structure overview | 10 min |
| **SETUP_SUMMARY.md** | What was created and features | 10 min |
| **INDEX.md** (this file) | Documentation navigation | 5 min |

---

## 🏗️ Framework Structure

### Source Code Organization
```
src/main/java/com/amazon/
├── base/           → WebDriver initialization & Reporting
├── pages/          → Page Object Models (3 classes)
└── utilities/      → File handling utilities (4 classes)

src/test/java/com/amazon/
├── stepdefinitions/ → Cucumber steps
└── runners/         → TestNG & Cucumber runners

src/test/resources/
├── features/        → Cucumber feature files
├── config/          → Configuration files
└── testdata/        → Test data (JSON, TXT)
```

---

## 🛠️ Utility Classes Guide

### ExcelUtility
**Purpose:** Read and write Excel files (.xlsx)

**Key Methods:**
- `getCellData(sheetName, rowIndex, colIndex)` - Read cell
- `getSheetData(sheetName)` - Read entire sheet
- `writeCellData(sheetName, rowIndex, colIndex, data)` - Write cell

**Example:**
```java
ExcelUtility excel = new ExcelUtility("data.xlsx");
String value = excel.getCellData("Sheet1", 0, 0);
List<Map<String, String>> data = excel.getSheetData("Sheet1");
```

👉 See [UTILITIES_REFERENCE.md](UTILITIES_REFERENCE.md#excelutility) for more examples

---

### JsonUtility
**Purpose:** Read and write JSON files

**Key Methods:**
- `getValueByKey(key)` - Get value by key
- `getNestedValue(parentKey, childKey)` - Get nested value
- `jsonArrayToList()` - Convert JSON array to list

**Example:**
```java
JsonUtility json = new JsonUtility("config.json");
String value = json.getValueByKey("username");
List<Map<String, String>> data = json.jsonArrayToList();
```

👉 See [UTILITIES_REFERENCE.md](UTILITIES_REFERENCE.md#jsonutility) for more examples

---

### PropertyUtility
**Purpose:** Read and write .properties files

**Key Methods:**
- `getProperty(key)` - Read property
- `getProperty(key, defaultValue)` - With default
- `setProperty(key, value)` - Set property
- `getAllProperties()` - Get all properties

**Example:**
```java
PropertyUtility prop = new PropertyUtility("config.properties");
String browser = prop.getProperty("browser");
String url = prop.getProperty("baseURL", "https://www.amazon.com");
```

👉 See [UTILITIES_REFERENCE.md](UTILITIES_REFERENCE.md#propertyutility) for more examples

---

### TextFileUtility
**Purpose:** Read and write text files (.txt)

**Key Methods:**
- `readTextFile(path)` - Read all lines
- `writeToTextFile(path, content, append)` - Write content
- `appendToFile(path, content)` - Append to file
- `searchInFile(path, text)` - Search text

**Example:**
```java
List<String> lines = TextFileUtility.readTextFile("data.txt");
TextFileUtility.writeToTextFile("output.txt", "content", false);
TextFileUtility.appendToFile("log.txt", "new entry");
```

👉 See [UTILITIES_REFERENCE.md](UTILITIES_REFERENCE.md#textfileutility) for more examples

---

## 📄 Page Objects Guide

### AmazonHomePage
**Purpose:** Amazon home page actions

**Key Methods:**
- `navigateToAmazon(url)` - Navigate to URL
- `searchProduct(productName)` - Search product
- `getPageTitle()` - Get page title

### AmazonSearchResultsPage
**Purpose:** Search results page actions

**Key Methods:**
- `getFirstProductTitle()` - Get first product
- `clickFirstProduct()` - Click product
- `getCartCount()` - Get cart count

### AmazonProductDetailsPage
**Purpose:** Product details page actions

**Key Methods:**
- `getProductTitle()` - Get product title
- `addToCart()` - Add to cart
- `setQuantity(qty)` - Set quantity
- `getProductPrice()` - Get price

👉 See [README.md](README.md#page-objects) for all page object methods

---

## ✅ Test Cases

### TestNG Tests (3 Tests)
1. **testAddLaptopToCart**
   - Search for laptop
   - Add to cart
   - Verify in cart

2. **testAddMobilePhoneToCart**
   - Search for mobile phone
   - Add to cart
   - Verify in cart

3. **testAddHeadphonesToCartWithQuantity**
   - Search for headphones
   - Set quantity to 2
   - Add to cart
   - Verify in cart

### Cucumber Scenarios (2 Scenarios)
1. **Add Single Product to Cart**
   - Search for laptop
   - Add to cart

2. **Add Multiple Products to Cart**
   - Search for mobile phone
   - Add to cart

👉 See [EXECUTION_GUIDE.md](EXECUTION_GUIDE.md#test-scenarios) for detailed test steps

---

## 🚀 How to Run

### Quick Commands

**Run all tests:**
```bash
mvn clean test
```

**Run specific test:**
```bash
mvn clean test -Dtest=AmazonAddToCartTestNGRunner#testAddLaptopToCart
```

**Run TestNG only:**
```bash
mvn clean test -Dtest=AmazonAddToCartTestNGRunner
```

**Run Cucumber only:**
```bash
mvn clean test -Dtest=CucumberRunnerTest
```

👉 See [EXECUTION_GUIDE.md](EXECUTION_GUIDE.md) for more commands and options

---

## 📊 Reports

### Extent Report (Spark) - Main Report
**Location:** `test-output/ExtentReports/AutomationReport_[timestamp].html`

**Includes:**
- Test status overview
- Detailed step logs
- Pass/Fail information
- System information
- Execution timeline

### Cucumber Report
**Location:** `test-output/CucumberReports/cucumber-report.html`

**Includes:**
- Feature and scenario details
- Step execution status
- Duration metrics

👉 See [EXECUTION_GUIDE.md](EXECUTION_GUIDE.md#viewing-test-reports) for report viewing

---

## ⚙️ Configuration

### config.properties
Located at: `src/test/resources/config/config.properties`

**Key Settings:**
```properties
browser=chrome                  # Browser type
baseURL=https://www.amazon.com # Base URL
implicitWait=10                # Implicit wait (seconds)
explicitWait=10                # Explicit wait (seconds)
```

👉 See [README.md](README.md#configuration) for all configuration options

---

## 🐛 Troubleshooting

### Common Issues

**Maven not found:**
```bash
mvn -version
```
Download from: https://maven.apache.org

**WebDriver errors:**
```bash
mvn clean install -U
```

**Can't find tests:**
- Check folder structure
- Verify class names match

**Browser doesn't open:**
- Check internet (WebDriverManager needs it)
- Close other browser instances

👉 See [EXECUTION_GUIDE.md](EXECUTION_GUIDE.md#common-issues--solutions) for detailed troubleshooting

---

## 📋 Checklist for First Run

- [ ] Java 11+ installed
- [ ] Maven 3.6+ installed
- [ ] Chrome/Firefox/Edge installed
- [ ] Internet connection active
- [ ] Project cloned
- [ ] Run `mvn clean install`
- [ ] Run `mvn clean test`
- [ ] View `test-output/ExtentReports/` report
- [ ] All tests passed ✅

---

## 💡 Quick Tips

1. **Always run clean before test:**
   ```bash
   mvn clean test
   ```

2. **Update dependencies with -U:**
   ```bash
   mvn clean install -U
   ```

3. **Check report after every run:**
   - Navigate to `test-output/ExtentReports/`
   - Open latest `.html` file

4. **Modify configuration before running:**
   - Edit `src/test/resources/config/config.properties`
   - Change browser, timeout, URL as needed

5. **Add custom tests easily:**
   - Add new test method to `AmazonAddToCartTestNGRunner`
   - Follow existing test pattern
   - Run tests

👉 See [QUICK_START.md](QUICK_START.md) for even quicker tips

---

## 📚 Learning Resources

### For Beginners
1. Start with [QUICK_START.md](QUICK_START.md)
2. Run your first test
3. Review the [README.md](README.md)

### For Developers
1. Explore [PROJECT_STRUCTURE.md](PROJECT_STRUCTURE.md)
2. Review [UTILITIES_REFERENCE.md](UTILITIES_REFERENCE.md)
3. Check page objects and utilities code

### For DevOps/CI-CD
1. See [EXECUTION_GUIDE.md](EXECUTION_GUIDE.md#continuous-integration-ci)
2. GitHub Actions example provided
3. Customize for your pipeline

---

## 🔗 File Navigation

| I want to... | Go to... |
|---|---|
| Get started quickly | [QUICK_START.md](QUICK_START.md) |
| Run tests | [EXECUTION_GUIDE.md](EXECUTION_GUIDE.md) |
| See code examples | [UTILITIES_REFERENCE.md](UTILITIES_REFERENCE.md) |
| Understand structure | [PROJECT_STRUCTURE.md](PROJECT_STRUCTURE.md) |
| Full documentation | [README.md](README.md) |
| Troubleshoot issues | [EXECUTION_GUIDE.md](EXECUTION_GUIDE.md#troubleshooting-checklist) |
| See what was created | [SETUP_SUMMARY.md](SETUP_SUMMARY.md) |

---

## 🎯 Popular Workflows

### Workflow 1: Quick Test Run
```
1. Open terminal
2. cd d:\Automation\Selenium_Framwork
3. mvn clean test
4. Open test-output/ExtentReports/ → view report
```
**Time:** 5-10 minutes

### Workflow 2: Run Specific Test
```
1. mvn clean test -Dtest=AmazonAddToCartTestNGRunner#testAddLaptopToCart
2. Review report
```
**Time:** 3-5 minutes

### Workflow 3: Add New Test Case
```
1. Open AmazonAddToCartTestNGRunner.java
2. Copy existing test method
3. Modify for new scenario
4. Run mvn clean test
5. Review report
```
**Time:** 10 minutes

### Workflow 4: Use Utilities in Custom Test
```
1. Review UTILITIES_REFERENCE.md
2. Import utility class
3. Use in your test
4. Test your code
```
**Time:** 15 minutes

---

## 📞 Support

**Having issues?**

1. Check [EXECUTION_GUIDE.md](EXECUTION_GUIDE.md#troubleshooting-checklist)
2. Review [README.md](README.md)
3. Check [UTILITIES_REFERENCE.md](UTILITIES_REFERENCE.md)

**Still stuck?**

- Review error messages carefully
- Check internet connection
- Verify installations
- See troubleshooting section

---

## ✨ Framework Features Summary

✅ **8 Java Classes** - Ready to use
✅ **4 Utility Classes** - Excel, JSON, Properties, Text
✅ **3 Page Objects** - Amazon pages
✅ **2 Test Runners** - TestNG & Cucumber
✅ **5 Test Cases** - Laptop, Phone, Headphones
✅ **Beautiful Reports** - Spark HTML reports
✅ **Complete Docs** - 7 documentation files
✅ **Production Ready** - Industry best practices

---

## 🎓 Learning Path

### Beginner (Day 1)
- [ ] Read QUICK_START.md
- [ ] Run first test
- [ ] View report

### Intermediate (Day 2-3)
- [ ] Read README.md
- [ ] Explore page objects
- [ ] Review utilities
- [ ] Understand test structure

### Advanced (Day 4-5)
- [ ] Read UTILITIES_REFERENCE.md
- [ ] Study PAGE objects pattern
- [ ] Write custom tests
- [ ] Integrate with CI/CD

---

## 🚀 Next Steps

1. **Read:** [QUICK_START.md](QUICK_START.md)
2. **Run:** `mvn clean test`
3. **Review:** Test report in `test-output/`
4. **Explore:** Source code structure
5. **Customize:** For your needs
6. **Share:** With your team

---

**Happy Testing! 🎉**

For detailed help, choose your guide:
- Quick Start → [QUICK_START.md](QUICK_START.md)
- Execution → [EXECUTION_GUIDE.md](EXECUTION_GUIDE.md)
- Code Examples → [UTILITIES_REFERENCE.md](UTILITIES_REFERENCE.md)
- Full Details → [README.md](README.md)
