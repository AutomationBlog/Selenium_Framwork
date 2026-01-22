package com.amazon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * Page Object for Amazon Search Results Page
 */
public class AmazonSearchResultsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By firstProductTitle = By.xpath("(//span[@class='a-size-medium a-color-base a-text-normal'])[1]");
    private By addToCartButton = By.xpath("(//span[text()='Add to basket'])[1]");
    private By cartIcon = By.id("nav-cart-count-container");

    public AmazonSearchResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Get first product title
     */
    public String getFirstProductTitle() {
        WebElement productElement = wait.until(ExpectedConditions.visibilityOfElementLocated(firstProductTitle));
        return productElement.getText();
    }

    /**
     * Click on first product
     */
    public void clickFirstProduct() {
        WebElement productElement = wait.until(ExpectedConditions.elementToBeClickable(firstProductTitle));
        productElement.click();
    }

    /**
     * Add first product to cart from search results
     */
    public void addFirstProductToCart() {
        try {
            WebElement addBtn = wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
            addBtn.click();
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Add to cart button not available on search results page");
        }
    }

    /**
     * Get cart count
     */
    public String getCartCount() {
        WebElement cartCount = wait.until(ExpectedConditions.visibilityOfElementLocated(cartIcon));
        return cartCount.getText();
    }

    /**
     * Check if add to cart button is displayed
     */
    public boolean isAddToCartButtonDisplayed() {
        try {
            return driver.findElement(addToCartButton).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
