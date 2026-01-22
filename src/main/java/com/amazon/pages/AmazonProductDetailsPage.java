package com.amazon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.amazon.utilities.BrowserInteractionUtility;

/**
 * Page Object for Amazon Product Details Page
 */
public class AmazonProductDetailsPage {
    private WebDriver driver;
    private BrowserInteractionUtility browserInteraction;

    // Locators
    private By productTitle = By.id("productTitle");
    private By addToCartBtn = By.id("add-to-cart-button");
    private By buyNowBtn = By.id("buy-now-button");
    private By quantity = By.id("quantity");
    private By priceTag = By.id("a-autoid-0-announce");
    private By cartPopup = By.xpath("//span[contains(text(), 'Added to Basket')]");

    public AmazonProductDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.browserInteraction = new BrowserInteractionUtility(driver);
    }

    /**
     * Get product title
     */
    public String getProductTitle() {
        return browserInteraction.getText(productTitle);
    }

    /**
     * Add product to cart
     */
    public void addToCart() {
        browserInteraction.click(addToCartBtn);
    }

    /**
     * Wait for cart confirmation message
     */
    public void waitForCartConfirmation() {
        try {
            browserInteraction.waitForElementToBeVisible(cartPopup);
            browserInteraction.waitForSeconds(2);
        } catch (Exception e) {
            System.out.println("Cart confirmation popup not found");
        }
    }

    /**
     * Set product quantity
     */
    public void setQuantity(String qty) {
        browserInteraction.clearAndType(quantity, qty);
    }

    /**
     * Get product price
     */
    public String getProductPrice() {
        try {
            return browserInteraction.getText(priceTag);
        } catch (Exception e) {
            return "Price not available";
        }
    }

    /**
     * Check if add to cart button is displayed
     */
    public boolean isAddToCartButtonDisplayed() {
        return browserInteraction.isElementDisplayed(addToCartBtn);
    }
}
