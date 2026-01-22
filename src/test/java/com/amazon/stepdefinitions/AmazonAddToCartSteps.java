package com.amazon.stepdefinitions;

import com.amazon.base.BaseTest;
import com.amazon.base.ExtentReportBase;
import com.amazon.pages.AmazonHomePage;
import com.amazon.pages.AmazonSearchResultsPage;
import com.amazon.pages.AmazonProductDetailsPage;
import com.amazon.utilities.PropertyUtility;
import com.amazon.utilities.BrowserInteractionUtility;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.openqa.selenium.By;
import org.testng.Assert;
import java.io.IOException;

/**
 * Step definitions for Amazon Add to Cart scenario
 * Hooks are handled separately in Hooks.java class
 */
public class AmazonAddToCartSteps extends BaseTest {
    private AmazonHomePage homePage;
    private AmazonSearchResultsPage searchResultsPage;
    private AmazonProductDetailsPage productDetailsPage;
    private BrowserInteractionUtility browserInteraction;
    private PropertyUtility propertyUtility;

    /**
     * Initialize page objects
     * Called automatically by Hooks class
     */
    public AmazonAddToCartSteps() {
        try {
            this.homePage = new AmazonHomePage(driver);
            this.searchResultsPage = new AmazonSearchResultsPage(driver);
            this.productDetailsPage = new AmazonProductDetailsPage(driver);
            this.browserInteraction = new BrowserInteractionUtility(driver);
        } catch (Exception e) {
            System.out.println("Note: Page objects will be initialized when driver is ready");
        }
    }

    @Given("User is on Amazon home page")
    public void user_is_on_amazon_home_page() throws IOException {
        try {
            // Re-initialize page objects with driver
            homePage = new AmazonHomePage(driver);
            browserInteraction = new BrowserInteractionUtility(driver);
            propertyUtility = new PropertyUtility("src/test/resources/config/config.properties");
            
            String baseURL = propertyUtility.getProperty("baseURL", "https://www.amazon.com");
            homePage.navigateToAmazon(baseURL);
            
            ExtentReportBase.logInfo("Navigated to Amazon home page: " + baseURL);
            
            Assert.assertTrue(homePage.isSearchBoxDisplayed(), "Search box is not displayed");
            ExtentReportBase.logPass("Search box is displayed on home page");
            
        } catch (AssertionError ae) {
            ExtentReportBase.logFail("Assertion failed: " + ae.getMessage());
            throw ae;
        } catch (Exception e) {
            ExtentReportBase.logFail("Failed to navigate to Amazon home page: " + e.getMessage());
            throw e;
        }
    }

    @When("User searches for {string}")
    public void user_searches_for_product(String productName) {
        try {
            ExtentReportBase.logInfo("Searching for product: " + productName);
            homePage.searchProduct(productName);
            ExtentReportBase.logPass("Successfully searched for: " + productName);
            
        } catch (Exception e) {
            ExtentReportBase.logFail("Failed to search for product '" + productName + "': " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Then("Search results should be displayed")
    public void search_results_should_be_displayed() {
        try {
            ExtentReportBase.logInfo("Verifying search results page");
            String pageTitle = browserInteraction.getPageTitle();
            
            Assert.assertTrue(pageTitle.contains("Amazon"), 
                "Search results page not loaded. Current title: " + pageTitle);
            
            ExtentReportBase.logPass("Search results page loaded successfully. Title: " + pageTitle);
            
        } catch (AssertionError ae) {
            ExtentReportBase.logFail("Assertion failed: " + ae.getMessage());
            throw ae;
        } catch (Exception e) {
            ExtentReportBase.logFail("Failed to verify search results: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @When("User clicks on first product")
    public void user_clicks_on_first_product() throws InterruptedException {
        try {
            ExtentReportBase.logInfo("Clicking on first product");
            browserInteraction.waitForSeconds(2);
            
            // Re-initialize search results page
            searchResultsPage = new AmazonSearchResultsPage(driver);
            
            // Try multiple locator strategies for better reliability
            By productLocator = By.xpath("(//h2//a)[1]");
            if (browserInteraction.isElementPresent(productLocator)) {
                browserInteraction.click(productLocator);
                ExtentReportBase.logPass("Clicked on first product using primary locator");
            } else {
                // Fallback to div with data-component-type
                By fallbackLocator = By.xpath("(//div[@data-component-type='s-search-result'][1]//h2//a)[1]");
                browserInteraction.click(fallbackLocator);
                ExtentReportBase.logPass("Clicked on first product using fallback locator");
            }
            
            browserInteraction.waitForSeconds(2);
            
        } catch (Exception e) {
            ExtentReportBase.logFail("Error clicking first product: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @When("User adds product to cart")
    public void user_adds_product_to_cart() throws InterruptedException {
        try {
            ExtentReportBase.logInfo("Adding product to cart");
            
            // Re-initialize product details page
            productDetailsPage = new AmazonProductDetailsPage(driver);
            
            productDetailsPage.addToCart();
            browserInteraction.waitForSeconds(2);
            
            ExtentReportBase.logPass("Product added to cart successfully");
            
        } catch (Exception e) {
            ExtentReportBase.logFail("Failed to add product to cart: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Then("Product should be added to cart")
    public void product_should_be_added_to_cart() {
        try {
            ExtentReportBase.logInfo("Verifying product is in cart");
            browserInteraction.waitForSeconds(1);
            
            By cartIconLocator = By.id("nav-cart-count-container");
            String cartCount = browserInteraction.getText(cartIconLocator);
            
            Assert.assertNotNull(cartCount, "Cart count is not displayed");
            Assert.assertFalse(cartCount.isEmpty(), "Cart count is empty");
            
            ExtentReportBase.logPass("Product verified in cart. Cart count: " + cartCount);
            
        } catch (AssertionError ae) {
            ExtentReportBase.logFail("Assertion failed: " + ae.getMessage());
            throw ae;
        } catch (Exception e) {
            ExtentReportBase.logFail("Failed to verify product in cart: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Additional step definitions for product details verification
     */
    @And("Product title should be displayed")
    public void product_title_should_be_displayed() {
        try {
            ExtentReportBase.logInfo("Verifying product title is displayed");
            
            productDetailsPage = new AmazonProductDetailsPage(driver);
            String productTitle = productDetailsPage.getProductTitle();
            
            Assert.assertNotNull(productTitle, "Product title is null");
            Assert.assertFalse(productTitle.isEmpty(), "Product title is empty");
            
            ExtentReportBase.logPass("Product title displayed: " + productTitle);
            
        } catch (AssertionError ae) {
            ExtentReportBase.logFail("Assertion failed: " + ae.getMessage());
            throw ae;
        } catch (Exception e) {
            ExtentReportBase.logFail("Failed to verify product title: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @And("User sets quantity to {string}")
    public void user_sets_quantity(String quantity) {
        try {
            ExtentReportBase.logInfo("Setting product quantity to: " + quantity);
            
            productDetailsPage = new AmazonProductDetailsPage(driver);
            productDetailsPage.setQuantity(quantity);
            
            ExtentReportBase.logPass("Quantity set to: " + quantity);
            
        } catch (Exception e) {
            ExtentReportBase.logFail("Failed to set quantity: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @And("Wait for cart confirmation")
    public void wait_for_cart_confirmation() {
        try {
            ExtentReportBase.logInfo("Waiting for cart confirmation");
            
            productDetailsPage = new AmazonProductDetailsPage(driver);
            productDetailsPage.waitForCartConfirmation();
            
            ExtentReportBase.logPass("Cart confirmation received");
            
        } catch (Exception e) {
            ExtentReportBase.logFail("Failed waiting for cart confirmation: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
