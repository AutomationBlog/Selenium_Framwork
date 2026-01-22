# BrowserInteractionUtility - Complete Guide

## Overview

`BrowserInteractionUtility` is a comprehensive wrapper around Selenium WebDriver that provides reusable methods for common browser interactions. It eliminates the need for repetitive code in page objects and test classes while providing better error handling and explicit waits.

## Table of Contents

1. [Initialization](#initialization)
2. [Click Operations](#click-operations)
3. [Text Input](#text-input)
4. [Text Retrieval](#text-retrieval)
5. [Dropdowns & Selects](#dropdowns--selects)
6. [Wait Operations](#wait-operations)
7. [Element Checks](#element-checks)
8. [Scrolling](#scrolling)
9. [Mouse Operations](#mouse-operations)
10. [Keyboard Operations](#keyboard-operations)
11. [JavaScript](#javascript)
12. [Alerts](#alerts)
13. [Windows & Frames](#windows--frames)
14. [Navigation](#navigation)
15. [Cookies](#cookies)
16. [Best Practices](#best-practices)

---

## Initialization

### Basic Initialization
```java
BrowserInteractionUtility browserInteraction = new BrowserInteractionUtility(driver);
```

### With Custom Timeout
```java
// Sets explicit wait timeout to 15 seconds
BrowserInteractionUtility browserInteraction = new BrowserInteractionUtility(driver, 15);
```

### In Page Objects
```java
public class AmazonHomePage {
    private WebDriver driver;
    private BrowserInteractionUtility browserInteraction;
    
    // Locators
    private By searchBox = By.id("twotabsearchtextbox");
    private By searchButton = By.id("nav-search-submit-button");
    
    public AmazonHomePage(WebDriver driver) {
        this.driver = driver;
        this.browserInteraction = new BrowserInteractionUtility(driver);
    }
    
    public void searchProduct(String product) {
        browserInteraction.clearAndType(searchBox, product);
        browserInteraction.click(searchButton);
    }
}
```

---

## Click Operations

### Simple Click
Clicks element with explicit wait for clickability
```java
browserInteraction.click(By.id("submitButton"));
```

### Click WebElement
```java
WebElement button = driver.findElement(By.id("button"));
browserInteraction.click(button);
```

### Double Click
Performs double-click action
```java
browserInteraction.doubleClick(By.id("checkbox"));
```

### Right Click (Context Menu)
```java
browserInteraction.rightClick(By.xpath("//div[@class='item']"));
```

### Real-World Example
```java
public class ShoppingPage {
    private By addToCartButton = By.xpath("//button[@class='add-cart']");
    private By quantityField = By.id("qty");
    
    public void addItemToCart(int quantity) {
        // Clear and set quantity
        browserInteraction.clearAndType(quantityField, String.valueOf(quantity));
        
        // Click add to cart button
        browserInteraction.click(addToCartButton);
        
        // Wait for confirmation
        browserInteraction.waitForSeconds(1);
    }
}
```

---

## Text Input

### Clear and Type
Clears existing text and types new text
```java
browserInteraction.clearAndType(By.id("searchBox"), "laptop");
```

### Type Without Clearing
Appends text to existing content
```java
browserInteraction.type(By.id("textField"), " additional text");
```

### Type With Delay
Useful for slow-loading fields or triggering autocomplete
```java
// Types with 100ms delay between characters
browserInteraction.typeWithDelay(By.id("passwordField"), "SecurePassword123", 100);

// Typing slower for autocomplete suggestion triggers
browserInteraction.typeWithDelay(By.id("searchField"), "amazon", 200);
```

### Clear Element
```java
browserInteraction.clearElement(By.id("textField"));
```

### Real-World Example
```java
public class SearchPage {
    private By searchInput = By.id("search");
    private By autoSuggest = By.xpath("//div[@class='suggestions']//li");
    
    public void searchWithAutoSuggest(String keyword) {
        // Type keyword slowly to trigger autocomplete
        browserInteraction.typeWithDelay(searchInput, keyword, 150);
        
        // Wait for suggestions to appear
        browserInteraction.waitForElementToBeVisible(autoSuggest);
        
        // Click first suggestion
        browserInteraction.click(autoSuggest);
    }
}
```

---

## Text Retrieval

### Get Element Text
```java
String heading = browserInteraction.getText(By.xpath("//h1[@class='title']"));
System.out.println(heading);  // Output: "Product Details"
```

### Get Element Text from WebElement
```java
WebElement element = driver.findElement(By.id("message"));
String message = browserInteraction.getText(element);
```

### Get Attribute Value
```java
// Get href from link
String link = browserInteraction.getAttribute(By.xpath("//a[@class='product']"), "href");

// Get placeholder from input
String placeholder = browserInteraction.getAttribute(By.id("search"), "placeholder");

// Get data-attribute
String productId = browserInteraction.getAttribute(By.className("product"), "data-id");
```

### Get CSS Property
```java
// Check background color
String bgColor = browserInteraction.getCssValue(By.id("button"), "background-color");

// Check font size
String fontSize = browserInteraction.getCssValue(By.className("heading"), "font-size");

// Check visibility
String display = browserInteraction.getCssValue(By.id("modal"), "display");
```

### Real-World Example
```java
public class ProductPage {
    private By productTitle = By.id("productTitle");
    private By priceTag = By.xpath("//span[@class='price']");
    private By reviewCount = By.id("acrCustomerReviewCount");
    private By productImage = By.xpath("//img[@class='product-image']");
    
    public String getProductInfo() {
        String title = browserInteraction.getText(productTitle);
        String price = browserInteraction.getText(priceTag);
        String reviews = browserInteraction.getAttribute(reviewCount, "data-reviews");
        String imageUrl = browserInteraction.getAttribute(productImage, "src");
        
        return String.format("Product: %s, Price: %s, Reviews: %s", title, price, reviews);
    }
}
```

---

## Dropdowns & Selects

### Select by Visible Text
```java
browserInteraction.selectByVisibleText(By.id("country"), "United States");
```

### Select by Value Attribute
```java
browserInteraction.selectByValue(By.id("status"), "active");
```

### Select by Index
```java
// Select first option (index 0)
browserInteraction.selectByIndex(By.id("month"), 0);

// Select third option (index 2)
browserInteraction.selectByIndex(By.id("month"), 2);
```

### Get All Options
```java
List<String> options = browserInteraction.getDropdownOptions(By.id("country"));
// Output: [United States, Canada, Mexico, ...]

// Check if option exists
if (options.contains("Canada")) {
    browserInteraction.selectByVisibleText(By.id("country"), "Canada");
}
```

### Get Selected Option
```java
String selected = browserInteraction.getSelectedOption(By.id("country"));
System.out.println(selected);  // Output: "United States"
```

### Real-World Example
```java
public class FilterPage {
    private By categoryDropdown = By.id("category");
    private By priceRangeDropdown = By.id("price");
    private By sortDropdown = By.id("sort");
    
    public void applyFilters(String category, String priceRange, String sortBy) {
        browserInteraction.selectByVisibleText(categoryDropdown, category);
        browserInteraction.waitForSeconds(1);  // Wait for items to reload
        
        browserInteraction.selectByVisibleText(priceRangeDropdown, priceRange);
        browserInteraction.waitForSeconds(1);
        
        browserInteraction.selectByVisibleText(sortDropdown, sortBy);
        browserInteraction.waitForSeconds(1);
    }
    
    public void selectFirstCategoryOption() {
        List<String> options = browserInteraction.getDropdownOptions(categoryDropdown);
        if (!options.isEmpty()) {
            browserInteraction.selectByVisibleText(categoryDropdown, options.get(0));
        }
    }
}
```

---

## Wait Operations

### Wait for Element Visible
```java
// Waits up to 10 seconds for element to be visible
WebElement element = browserInteraction.waitForElementToBeVisible(By.id("loader-complete"));
```

### Wait for Element Clickable
```java
// Waits until element is both visible and enabled
WebElement button = browserInteraction.waitForElementToBeClickable(By.id("submitBtn"));
```

### Wait for Element Present
```java
// Waits until element exists in DOM (may not be visible)
WebElement element = browserInteraction.waitForElementToBePresent(By.xpath("//div[@id='content']"));
```

### Wait for Element Invisible
```java
// Waits for element to disappear
boolean isGone = browserInteraction.waitForElementToBeInvisible(By.id("loadingSpinner"));
```

### Wait for Element to Disappear
With custom timeout
```java
// Waits up to 20 seconds for element to disappear
boolean removed = browserInteraction.waitForElementToDisappear(By.className("popup"), 20);
```

### Wait for Text in Element
```java
// Waits for text to appear in element
boolean textFound = browserInteraction.waitForTextInElement(
    By.id("message"), 
    "Order Placed Successfully"
);
```

### Wait for Seconds
```java
// Wait 3 seconds (use sparingly)
browserInteraction.waitForSeconds(3);
```

### Real-World Example
```java
public class CheckoutPage {
    private By loadingSpinner = By.id("spinner");
    private By orderConfirmation = By.id("confirmation");
    private By confirmationMessage = By.xpath("//span[@class='success-msg']");
    
    public String completeCheckout() {
        // Wait for any loading spinners to disappear
        browserInteraction.waitForElementToDisappear(loadingSpinner, 30);
        
        // Wait for confirmation message
        browserInteraction.waitForTextInElement(
            confirmationMessage, 
            "Thank you for your purchase"
        );
        
        // Get confirmation text
        return browserInteraction.getText(confirmationMessage);
    }
}
```

---

## Element Checks

### Check if Element Displayed
```java
if (browserInteraction.isElementDisplayed(By.id("successMessage"))) {
    System.out.println("Success message is visible");
}
```

### Check if Element Exists
```java
if (browserInteraction.isElementPresent(By.xpath("//div[@class='optional']"))) {
    System.out.println("Optional element found in DOM");
}
```

### Check if Element Enabled
```java
if (browserInteraction.isElementEnabled(By.id("submitButton"))) {
    browserInteraction.click(By.id("submitButton"));
} else {
    System.out.println("Submit button is disabled");
}
```

### Real-World Example
```java
public class FormPage {
    private By submitButton = By.id("submit");
    private By errorMessage = By.className("error");
    private By successMessage = By.className("success");
    
    public boolean submitForm() {
        if (!browserInteraction.isElementEnabled(submitButton)) {
            System.out.println("Cannot submit: button is disabled");
            return false;
        }
        
        browserInteraction.click(submitButton);
        browserInteraction.waitForSeconds(2);
        
        if (browserInteraction.isElementDisplayed(successMessage)) {
            return true;
        } else if (browserInteraction.isElementDisplayed(errorMessage)) {
            String error = browserInteraction.getText(errorMessage);
            System.out.println("Form submission failed: " + error);
            return false;
        }
        
        return false;
    }
}
```

---

## Scrolling

### Scroll to Element
```java
browserInteraction.scrollToElement(By.id("bottomContent"));
```

### Scroll to Element and Wait
```java
browserInteraction.scrollToElementAndWait(By.xpath("//button[@class='lazy-load']"));
```

### Scroll to Top
```java
browserInteraction.scrollToTop();
```

### Scroll to Bottom
```java
browserInteraction.scrollToBottom();
```

### Scroll by Pixels
```java
// Scroll down 500 pixels
browserInteraction.scrollByPixels(500);

// Scroll up 300 pixels
browserInteraction.scrollByPixels(-300);
```

### Real-World Example
```java
public class InfiniteScrollPage {
    private By nextPageItems = By.xpath("//div[@class='product-item']");
    
    public void loadAllProducts() {
        int previousCount = 0;
        
        while (true) {
            // Scroll to bottom
            browserInteraction.scrollToBottom();
            
            // Wait for new items to load
            browserInteraction.waitForSeconds(2);
            
            // Count items
            int currentCount = browserInteraction.getElementCount(nextPageItems);
            
            if (currentCount == previousCount) {
                // No new items loaded, reached end
                break;
            }
            
            previousCount = currentCount;
        }
        
        System.out.println("Total items loaded: " + previousCount);
    }
}
```

---

## Mouse Operations

### Hover Over Element
```java
browserInteraction.hoverOverElement(By.xpath("//div[@class='menu-item']"));
```

### Drag and Drop
```java
browserInteraction.dragAndDrop(
    By.id("draggable-item"),
    By.id("drop-zone")
);
```

### Drag by Offset
```java
// Drag element 100px to the right and 50px down
browserInteraction.dragElementByOffset(By.id("slider"), 100, 50);
```

### Real-World Example
```java
public class DragDropPage {
    private By sourceItem = By.id("item-1");
    private By dropZone = By.id("container");
    private By confirmation = By.id("success-msg");
    
    public void dragItemToContainer() {
        // Hover to reveal drop zone if needed
        browserInteraction.hoverOverElement(dropZone);
        browserInteraction.waitForSeconds(1);
        
        // Drag item
        browserInteraction.dragAndDrop(sourceItem, dropZone);
        
        // Verify success
        if (browserInteraction.isElementDisplayed(confirmation)) {
            System.out.println("Item dropped successfully");
        }
    }
}
```

---

## Keyboard Operations

### Press Enter
```java
browserInteraction.pressEnter(By.id("searchBox"));
```

### Press Tab
```java
browserInteraction.pressTab();
```

### Press Escape
```java
browserInteraction.pressEscape();
```

### Select All (Ctrl+A)
```java
browserInteraction.selectAll();
```

### Copy (Ctrl+C)
```java
browserInteraction.copy();
```

### Paste (Ctrl+V)
```java
browserInteraction.paste();
```

### Real-World Example
```java
public class LoginPage {
    private By usernameField = By.id("username");
    private By passwordField = By.id("password");
    private By loginButton = By.id("login");
    
    public void loginWithTab(String username, String password) {
        // Type username
        browserInteraction.clearAndType(usernameField, username);
        
        // Press Tab to move to password field
        browserInteraction.pressTab();
        
        // Type password
        browserInteraction.type(passwordField, password);
        
        // Press Enter to submit
        browserInteraction.pressEnter(passwordField);
    }
}
```

---

## JavaScript

### Execute JavaScript
```java
browserInteraction.executeScript("window.scrollTo(0, 0);");
```

### Execute with Arguments
```java
WebElement element = driver.findElement(By.id("element"));
String text = (String) browserInteraction.executeScript(
    "return arguments[0].innerText;", 
    element
);
```

### Async JavaScript
```java
browserInteraction.executeAsyncScript(
    "var callback = arguments[0]; " +
    "setTimeout(function() { callback('done'); }, 2000);"
);
```

### Get Page Title via JS
```java
String title = browserInteraction.getPageTitleViaJS();
```

### Get Page URL via JS
```java
String url = browserInteraction.getPageURLViaJS();
```

### Highlight Element (Debugging)
```java
// Highlight element with red border
browserInteraction.highlightElement(By.id("importantElement"));

// Your test code...

// Remove highlight
browserInteraction.unhighlightElement(By.id("importantElement"));
```

### Real-World Example
```java
public class DynamicContentPage {
    public String getRenderedText(By locator) {
        // Get text using JavaScript to handle dynamic content
        WebElement element = driver.findElement(locator);
        return (String) browserInteraction.executeScript(
            "return arguments[0].textContent;", 
            element
        );
    }
    
    public void hideElementForTesting(By locator) {
        browserInteraction.executeScript(
            "arguments[0].style.display = 'none';",
            driver.findElement(locator)
        );
    }
}
```

---

## Alerts

### Accept Alert
```java
browserInteraction.acceptAlert();
```

### Dismiss Alert
```java
browserInteraction.dismissAlert();
```

### Get Alert Text
```java
String alertMessage = browserInteraction.getAlertText();
System.out.println(alertMessage);
```

### Type in Alert
```java
browserInteraction.typeInAlert("username");
browserInteraction.acceptAlert();
```

### Real-World Example
```java
public class AlertPage {
    public void handleDeleteConfirmation(String itemName) {
        // Click delete button
        browserInteraction.click(By.xpath("//button[text()='Delete']"));
        
        // Get alert message
        String message = browserInteraction.getAlertText();
        System.out.println("Alert message: " + message);
        
        if (message.contains(itemName)) {
            browserInteraction.acceptAlert();
        } else {
            browserInteraction.dismissAlert();
        }
    }
}
```

---

## Windows & Frames

### Get Current Window Handle
```java
String mainWindow = browserInteraction.getCurrentWindowHandle();
```

### Get All Window Handles
```java
Set<String> allWindows = browserInteraction.getAllWindowHandles();
System.out.println("Number of windows: " + allWindows.size());
```

### Switch to Window by Index
```java
browserInteraction.switchToWindowByIndex(1);  // Switch to second window
```

### Switch to Window by Title
```java
browserInteraction.switchToWindowByTitle("Google");
```

### Switch to Frame
```java
browserInteraction.switchToFrame(By.id("iframeId"));
```

### Switch to Frame by Index
```java
browserInteraction.switchToFrame(0);  // First frame
```

### Switch to Parent Frame
```java
browserInteraction.switchToParentFrame();
```

### Switch to Default Content
```java
browserInteraction.switchToDefaultContent();
```

### Real-World Example
```java
public class MultiWindowPage {
    private String mainWindow;
    private By openInNewWindowLink = By.xpath("//a[@target='_blank']");
    
    public void handleMultipleWindows() {
        // Save main window handle
        mainWindow = browserInteraction.getCurrentWindowHandle();
        
        // Click link that opens in new window
        browserInteraction.click(openInNewWindowLink);
        browserInteraction.waitForSeconds(2);
        
        // Get all window handles
        Set<String> allWindows = browserInteraction.getAllWindowHandles();
        
        // Switch to new window
        for (String window : allWindows) {
            if (!window.equals(mainWindow)) {
                browserInteraction.switchToWindowByIndex(
                    new ArrayList<>(allWindows).indexOf(window)
                );
                break;
            }
        }
        
        // Do something in new window...
        
        // Switch back to main window
        driver.switchTo().window(mainWindow);
    }
}
```

---

## Navigation

### Navigate to URL
```java
browserInteraction.navigateTo("https://www.amazon.com");
```

### Navigate Back
```java
browserInteraction.navigateBack();
```

### Navigate Forward
```java
browserInteraction.navigateForward();
```

### Refresh Page
```java
browserInteraction.refreshPage();
```

### Get Current URL
```java
String currentUrl = browserInteraction.getCurrentURL();
```

### Get Page Title
```java
String pageTitle = browserInteraction.getPageTitle();
```

---

## Cookies

### Add Cookie
```java
browserInteraction.addCookie("sessionId", "abc123");
```

### Get Cookie
```java
Cookie cookie = browserInteraction.getCookie("sessionId");
System.out.println(cookie.getValue());
```

### Delete Cookie
```java
browserInteraction.deleteCookie("sessionId");
```

### Delete All Cookies
```java
browserInteraction.deleteAllCookies();
```

### Real-World Example
```java
public class AuthPage {
    public void loginWithSavedSession(String sessionToken) {
        // Add session cookie
        browserInteraction.addCookie("sessionToken", sessionToken);
        
        // Refresh page to apply cookie
        browserInteraction.refreshPage();
        
        // Verify user is logged in
        // ...
    }
}
```

---

## Best Practices

### 1. Use Meaningful Wait Strategies
```java
// Good - Explicit wait for specific condition
browserInteraction.waitForElementToBeVisible(By.id("content"));

// Avoid - Random waits
// browserInteraction.waitForSeconds(5);
```

### 2. Encapsulate in Page Objects
```java
public class ProductPage {
    private BrowserInteractionUtility browserInteraction;
    private By addToCartButton = By.id("add-to-cart");
    
    public void addProductToCart() {
        browserInteraction.click(addToCartButton);
    }
}
```

### 3. Handle Exceptions Gracefully
```java
public boolean isOptionalElementPresent(By locator) {
    try {
        return browserInteraction.isElementDisplayed(locator);
    } catch (Exception e) {
        return false;
    }
}
```

### 4. Use Clear and Meaningful Assertions
```java
@Test
public void testProductAddedToCart() {
    String productName = "Laptop";
    productPage.addProductToCart(productName);
    
    Assert.assertTrue(
        cartPage.isProductInCart(productName),
        "Product " + productName + " not found in cart"
    );
}
```

### 5. Avoid Hard-Coded Timeouts
```java
// Instead of arbitrary waits, use proper wait conditions
browserInteraction.waitForElementToBeVisible(By.id("confirmation"));

// Initialize with custom timeout if needed
BrowserInteractionUtility interaction = new BrowserInteractionUtility(driver, 20);
```

### 6. Clean Up Resources
```java
@AfterMethod
public void tearDown() {
    browserInteraction.deleteAllCookies();
    driver.quit();
}
```

---

## Common Patterns

### Login Flow
```java
public void login(String username, String password) {
    browserInteraction.navigateTo("https://example.com/login");
    browserInteraction.clearAndType(By.id("username"), username);
    browserInteraction.clearAndType(By.id("password"), password);
    browserInteraction.click(By.id("loginButton"));
    browserInteraction.waitForElementToBeVisible(By.id("dashboard"));
}
```

### Form Submission
```java
public void submitForm(Map<String, String> formData) {
    for (Map.Entry<String, String> entry : formData.entrySet()) {
        By locator = By.name(entry.getKey());
        browserInteraction.clearAndType(locator, entry.getValue());
    }
    browserInteraction.click(By.xpath("//button[@type='submit']"));
    browserInteraction.waitForSeconds(2);
}
```

### Data Verification
```java
public Map<String, String> getPageData() {
    Map<String, String> data = new HashMap<>();
    data.put("title", browserInteraction.getText(By.id("title")));
    data.put("price", browserInteraction.getText(By.id("price")));
    data.put("rating", browserInteraction.getAttribute(By.id("rating"), "data-value"));
    return data;
}
```

---

## Troubleshooting

### Element Not Found
```java
// Check if element exists before interacting
if (browserInteraction.isElementPresent(By.id("element"))) {
    browserInteraction.click(By.id("element"));
}
```

### Stale Element
```java
// Re-find element for each interaction
browserInteraction.click(By.id("button"));  // Element found and clicked
browserInteraction.waitForSeconds(2);       // Page updates
browserInteraction.click(By.id("button"));  // Element found again (fresh)
```

### Timeout Issues
```java
// Use custom timeout for slow elements
BrowserInteractionUtility slowInteraction = new BrowserInteractionUtility(driver, 30);
slowInteraction.waitForElementToBeVisible(By.id("slowLoadingElement"));
```

---

## Summary

`BrowserInteractionUtility` provides a clean, reusable API for Selenium automation. Key benefits:

✅ Reduces code duplication
✅ Built-in wait strategies
✅ Better error handling
✅ Easier to maintain
✅ Improves test reliability
✅ Cleaner page object code

Use this utility in all your page objects and test classes for consistent, reliable automation!
