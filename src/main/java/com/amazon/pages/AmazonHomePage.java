package com.amazon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.amazon.utilities.BrowserInteractionUtility;

/**
 * Page Object for Amazon Home Page
 */
public class AmazonHomePage {
    private WebDriver driver;
    private BrowserInteractionUtility browserInteraction;

    // Locators
    private By searchBox = By.id("twotabsearchtextbox");
    private By searchButton = By.id("nav-search-submit-button");
    private By accountList = By.id("nav-link-accountList");

    public AmazonHomePage(WebDriver driver) {
        this.driver = driver;
        this.browserInteraction = new BrowserInteractionUtility(driver);
    }

    /**
     * Navigate to Amazon home page
     */
    public void navigateToAmazon(String url) {
        browserInteraction.navigateTo(url);
    }

    /**
     * Search for product
     */
    public void searchProduct(String productName) {
        browserInteraction.clearAndType(searchBox, productName);
        browserInteraction.click(searchButton);
    }

    /**
     * Get page title
     */
    public String getPageTitle() {
        return browserInteraction.getPageTitle();
    }

    /**
     * Check if search box is displayed
     */
    public boolean isSearchBoxDisplayed() {
        return browserInteraction.isElementDisplayed(searchBox);
    }
}
