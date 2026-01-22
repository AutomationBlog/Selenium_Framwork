package com.amazon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * Page Object for Amazon Home Page
 */
public class AmazonHomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By searchBox = By.id("twotabsearchtextbox");
    private By searchButton = By.id("nav-search-submit-button");
    private By accountList = By.id("nav-link-accountList");

    public AmazonHomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Navigate to Amazon home page
     */
    public void navigateToAmazon(String url) {
        driver.navigate().to(url);
    }

    /**
     * Search for product
     */
    public void searchProduct(String productName) {
        WebElement searchElement = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));
        searchElement.clear();
        searchElement.sendKeys(productName);
        driver.findElement(searchButton).click();
    }

    /**
     * Get page title
     */
    public String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Check if search box is displayed
     */
    public boolean isSearchBoxDisplayed() {
        return driver.findElement(searchBox).isDisplayed();
    }
}
