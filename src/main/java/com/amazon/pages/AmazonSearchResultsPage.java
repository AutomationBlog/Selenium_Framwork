package com.amazon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.amazon.utilities.BrowserInteractionUtility;

/**
 * Page Object for Amazon Search Results Page
 */
public class AmazonSearchResultsPage {
    private WebDriver driver;
    private BrowserInteractionUtility browserInteraction;

    // Locators
    private By firstProductTitle = By.xpath("(//span[@class='a-size-medium a-color-base a-text-normal'])[1]");
    private By addToCartButton = By.xpath("(//span[text()='Add to basket'])[1]");
    private By cartIcon = By.id("nav-cart-count-container");

    public AmazonSearchResultsPage(WebDriver driver) {
        this.driver = driver;
        this.browserInteraction = new BrowserInteractionUtility(driver);
    }

    /**
     * Get first product title
     */
    public String getFirstProductTitle() {
        return browserInteraction.getText(firstProductTitle);
    }

    /**
     * Click on first product
     */
    public void clickFirstProduct() {
        browserInteraction.click(firstProductTitle);
    }

    /**
     * Add first product to cart from search results
     */
    public void addFirstProductToCart() {
        try {
            browserInteraction.click(addToCartButton);
            browserInteraction.waitForSeconds(2);
        } catch (Exception e) {
            System.out.println("Add to cart button not available on search results page");
        }
    }

    /**
     * Get cart count
     */
    public String getCartCount() {
        return browserInteraction.getText(cartIcon);
    }

    /**
     * Check if add to cart button is displayed
     */
    public boolean isAddToCartButtonDisplayed() {
        return browserInteraction.isElementDisplayed(addToCartButton);
    }
}
