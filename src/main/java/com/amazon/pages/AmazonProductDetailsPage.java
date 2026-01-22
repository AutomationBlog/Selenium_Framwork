package com.amazon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * Page Object for Amazon Product Details Page
 */
public class AmazonProductDetailsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By productTitle = By.id("productTitle");
    private By addToCartBtn = By.id("add-to-cart-button");
    private By buyNowBtn = By.id("buy-now-button");
    private By quantity = By.id("quantity");
    private By priceTag = By.id("a-autoid-0-announce");
    private By cartPopup = By.xpath("//span[contains(text(), 'Added to Basket')]");

    public AmazonProductDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Get product title
     */
    public String getProductTitle() {
        WebElement titleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(productTitle));
        return titleElement.getText();
    }

    /**
     * Add product to cart
     */
    public void addToCart() {
        WebElement addBtn = wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn));
        addBtn.click();
    }

    /**
     * Wait for cart confirmation message
     */
    public void waitForCartConfirmation() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(cartPopup));
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Cart confirmation popup not found");
        }
    }

    /**
     * Set product quantity
     */
    public void setQuantity(String qty) {
        WebElement quantityElement = wait.until(ExpectedConditions.visibilityOfElementLocated(quantity));
        quantityElement.clear();
        quantityElement.sendKeys(qty);
    }

    /**
     * Get product price
     */
    public String getProductPrice() {
        try {
            WebElement priceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(priceTag));
            return priceElement.getText();
        } catch (Exception e) {
            return "Price not available";
        }
    }

    /**
     * Check if add to cart button is displayed
     */
    public boolean isAddToCartButtonDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(addToCartBtn)).isDisplayed();
    }
}
