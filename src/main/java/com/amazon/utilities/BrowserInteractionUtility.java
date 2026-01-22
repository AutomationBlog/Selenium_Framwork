package com.amazon.utilities;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Utility class for common browser interaction methods
 * Provides reusable methods for element interactions, waits, scrolling, and
 * browser operations
 */
public class BrowserInteractionUtility {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final int DEFAULT_TIMEOUT = 10;

    /**
     * Constructor to initialize WebDriver and WebDriverWait
     */
    public BrowserInteractionUtility(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
    }

    /**
     * Constructor with custom timeout
     */
    public BrowserInteractionUtility(WebDriver driver, int timeoutInSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
    }

    // ============ Click Operations ============

    /**
     * Click on element with explicit wait
     */
    public void click(By locator) {
        waitForElementToBeClickable(locator);
        driver.findElement(locator).click();
    }

    /**
     * Click on WebElement with explicit wait
     */
    public void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    /**
     * Double click on element
     */
    public void doubleClick(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        new Actions(driver).doubleClick(element).perform();
    }

    /**
     * Right click on element
     */
    public void rightClick(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        new Actions(driver).contextClick(element).perform();
    }

    // ============ Text Input Operations ============

    /**
     * Clear text and send keys to element
     */
    public void clearAndType(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Send keys to element without clearing
     */
    public void type(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.sendKeys(text);
    }

    /**
     * Type text with delay between characters
     */
    public void typeWithDelay(By locator, String text, long delayMs) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.click();
        for (char character : text.toCharArray()) {
            element.sendKeys(String.valueOf(character));
            try {
                Thread.sleep(delayMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Clear element text
     */
    public void clearElement(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
    }

    // ============ Text Retrieval Operations ============

    /**
     * Get text from element
     */
    public String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    /**
     * Get text from WebElement
     */
    public String getText(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element)).getText();
    }

    /**
     * Get attribute value from element
     */
    public String getAttribute(By locator, String attributeName) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator))
                .getAttribute(attributeName);
    }

    /**
     * Get CSS value from element
     */
    public String getCssValue(By locator, String propertyName) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator))
                .getCssValue(propertyName);
    }

    // ============ Dropdown/Select Operations ============

    /**
     * Select option by visible text
     */
    public void selectByVisibleText(By locator, String visibleText) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        new Select(element).selectByVisibleText(visibleText);
    }

    /**
     * Select option by value attribute
     */
    public void selectByValue(By locator, String value) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        new Select(element).selectByValue(value);
    }

    /**
     * Select option by index
     */
    public void selectByIndex(By locator, int index) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        new Select(element).selectByIndex(index);
    }

    /**
     * Get all options from dropdown
     */
    public List<String> getDropdownOptions(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        Select select = new Select(element);
        List<String> options = new ArrayList<>();
        for (WebElement option : select.getOptions()) {
            options.add(option.getText());
        }
        return options;
    }

    /**
     * Get selected option text
     */
    public String getSelectedOption(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return new Select(element).getFirstSelectedOption().getText();
    }

    // ============ Wait Operations ============

    /**
     * Wait for element to be visible
     */
    public WebElement waitForElementToBeVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait for element to be clickable
     */
    public WebElement waitForElementToBeClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Wait for element to be present
     */
    public WebElement waitForElementToBePresent(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Wait for element to be invisible
     */
    public boolean waitForElementToBeInvisible(By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Wait for element to disappear
     */
    public boolean waitForElementToDisappear(By locator, int timeoutInSeconds) {
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return customWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Wait for text to be present in element
     */
    public boolean waitForTextInElement(By locator, String text) {
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    /**
     * Wait for custom time in seconds
     */
    public void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // ============ Element Visibility & Presence Check ============

    /**
     * Check if element is displayed
     */
    public boolean isElementDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Check if element exists in DOM
     */
    public boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Check if element is enabled
     */
    public boolean isElementEnabled(By locator) {
        try {
            return driver.findElement(locator).isEnabled();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    // ============ Scrolling Operations ============

    /**
     * Scroll to element using JavaScript
     */
    public void scrollToElement(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Scroll to element and wait for visibility
     */
    public void scrollToElementAndWait(By locator) {
        scrollToElement(locator);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Scroll to top of page
     */
    public void scrollToTop() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
    }

    /**
     * Scroll to bottom of page
     */
    public void scrollToBottom() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    /**
     * Scroll by pixel amount
     */
    public void scrollByPixels(int pixels) {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, " + pixels + ");");
    }

    // ============ Mouse Operations ============

    /**
     * Hover over element
     */
    public void hoverOverElement(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        new Actions(driver).moveToElement(element).perform();
    }

    /**
     * Drag and drop element
     */
    public void dragAndDrop(By sourceLocator, By targetLocator) {
        WebElement source = wait.until(ExpectedConditions.presenceOfElementLocated(sourceLocator));
        WebElement target = wait.until(ExpectedConditions.presenceOfElementLocated(targetLocator));
        new Actions(driver).dragAndDrop(source, target).perform();
    }

    /**
     * Drag element by offset
     */
    public void dragElementByOffset(By locator, int xOffset, int yOffset) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        new Actions(driver).dragAndDropBy(element, xOffset, yOffset).perform();
    }

    // ============ Keyboard Operations ============

    /**
     * Press Enter key on element
     */
    public void pressEnter(By locator) {
        driver.findElement(locator).sendKeys(Keys.ENTER);
    }

    /**
     * Press Tab key
     */
    public void pressTab() {
        new Actions(driver).sendKeys(Keys.TAB).perform();
    }

    /**
     * Press Escape key
     */
    public void pressEscape() {
        new Actions(driver).sendKeys(Keys.ESCAPE).perform();
    }

    /**
     * Select all text (Ctrl+A)
     */
    public void selectAll() {
        new Actions(driver).keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform();
    }

    /**
     * Copy to clipboard (Ctrl+C)
     */
    public void copy() {
        new Actions(driver).keyDown(Keys.CONTROL).sendKeys("c").keyUp(Keys.CONTROL).perform();
    }

    /**
     * Paste from clipboard (Ctrl+V)
     */
    public void paste() {
        new Actions(driver).keyDown(Keys.CONTROL).sendKeys("v").keyUp(Keys.CONTROL).perform();
    }

    // ============ JavaScript Operations ============

    /**
     * Execute JavaScript
     */
    public Object executeScript(String script) {
        return ((JavascriptExecutor) driver).executeScript(script);
    }

    /**
     * Execute JavaScript with arguments
     */
    public Object executeScript(String script, Object... args) {
        return ((JavascriptExecutor) driver).executeScript(script, args);
    }

    /**
     * Execute async JavaScript
     */
    public Object executeAsyncScript(String script, Object... args) {
        return ((JavascriptExecutor) driver).executeAsyncScript(script, args);
    }

    /**
     * Get page title using JavaScript
     */
    public String getPageTitleViaJS() {
        return (String) ((JavascriptExecutor) driver).executeScript("return document.title;");
    }

    /**
     * Get page URL using JavaScript
     */
    public String getPageURLViaJS() {
        return (String) ((JavascriptExecutor) driver).executeScript("return window.location.href;");
    }

    /**
     * Highlight element (for debugging)
     */
    public void highlightElement(By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].style.border='3px solid red';", element);
    }

    /**
     * Unhighlight element
     */
    public void unhighlightElement(By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].style.border='';", element);
    }

    // ============ Alert Operations ============

    /**
     * Accept alert
     */
    public void acceptAlert() {
        wait.until(ExpectedConditions.alertIsPresent()).accept();
    }

    /**
     * Dismiss alert
     */
    public void dismissAlert() {
        wait.until(ExpectedConditions.alertIsPresent()).dismiss();
    }

    /**
     * Get alert text
     */
    public String getAlertText() {
        return wait.until(ExpectedConditions.alertIsPresent()).getText();
    }

    /**
     * Type in alert
     */
    public void typeInAlert(String text) {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.sendKeys(text);
    }

    // ============ Window/Tab Operations ============

    /**
     * Get current window handle
     */
    public String getCurrentWindowHandle() {
        return driver.getWindowHandle();
    }

    /**
     * Get all window handles
     */
    public Set<String> getAllWindowHandles() {
        return driver.getWindowHandles();
    }

    /**
     * Switch to window by index
     */
    public void switchToWindowByIndex(int index) {
        List<String> windows = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(windows.get(index));
    }

    /**
     * Switch to window by title
     */
    public void switchToWindowByTitle(String title) {
        for (String window : driver.getWindowHandles()) {
            driver.switchTo().window(window);
            if (driver.getTitle().equals(title)) {
                break;
            }
        }
    }

    /**
     * Switch to frame by locator
     */
    public void switchToFrame(By locator) {
        driver.switchTo().frame(driver.findElement(locator));
    }

    /**
     * Switch to frame by index
     */
    public void switchToFrame(int index) {
        driver.switchTo().frame(index);
    }

    /**
     * Switch to parent frame
     */
    public void switchToParentFrame() {
        driver.switchTo().parentFrame();
    }

    /**
     * Switch to default content
     */
    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    // ============ Find Elements Operations ============

    /**
     * Find single element
     */
    public WebElement findElement(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Find multiple elements
     */
    public List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }

    /**
     * Get count of elements
     */
    public int getElementCount(By locator) {
        return findElements(locator).size();
    }

    // ============ Navigation Operations ============

    /**
     * Navigate to URL
     */
    public void navigateTo(String url) {
        driver.navigate().to(url);
    }

    /**
     * Navigate back
     */
    public void navigateBack() {
        driver.navigate().back();
    }

    /**
     * Navigate forward
     */
    public void navigateForward() {
        driver.navigate().forward();
    }

    /**
     * Refresh page
     */
    public void refreshPage() {
        driver.navigate().refresh();
    }

    /**
     * Get current URL
     */
    public String getCurrentURL() {
        return driver.getCurrentUrl();
    }

    /**
     * Get page title
     */
    public String getPageTitle() {
        return driver.getTitle();
    }

    // ============ Cookie Operations ============

    /**
     * Add cookie
     */
    public void addCookie(String name, String value) {
        driver.manage().addCookie(new Cookie(name, value));
    }

    /**
     * Get cookie by name
     */
    public Cookie getCookie(String name) {
        return driver.manage().getCookieNamed(name);
    }

    /**
     * Delete cookie by name
     */
    public void deleteCookie(String name) {
        driver.manage().deleteCookie(driver.manage().getCookieNamed(name));
    }

    /**
     * Delete all cookies
     */
    public void deleteAllCookies() {
        driver.manage().deleteAllCookies();
    }
}
