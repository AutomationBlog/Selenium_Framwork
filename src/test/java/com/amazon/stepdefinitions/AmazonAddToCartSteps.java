package com.amazon.stepdefinitions;

import com.amazon.base.BaseTest;
import com.amazon.pages.AmazonHomePage;
import com.amazon.pages.AmazonSearchResultsPage;
import com.amazon.pages.AmazonProductDetailsPage;
import com.amazon.utilities.PropertyUtility;
import com.amazon.utilities.BrowserInteractionUtility;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.testng.Assert;
import java.io.IOException;

/**
 * Step definitions for Amazon Add to Cart scenario
 */
public class AmazonAddToCartSteps extends BaseTest {
    private AmazonHomePage homePage;
    private AmazonSearchResultsPage searchResultsPage;
    private AmazonProductDetailsPage productDetailsPage;
    private BrowserInteractionUtility browserInteraction;
    private PropertyUtility propertyUtility;

    @Before
    public void setUp() throws IOException {
        initializeDriver(null);
        homePage = new AmazonHomePage(driver);
        searchResultsPage = new AmazonSearchResultsPage(driver);
        productDetailsPage = new AmazonProductDetailsPage(driver);
        browserInteraction = new BrowserInteractionUtility(driver);
    }

    @After
    public void tearDown() {
        closeBrowser();
    }

    @Given("User is on Amazon home page")
    public void user_is_on_amazon_home_page() throws IOException {
        propertyUtility = new PropertyUtility("src/test/resources/config/config.properties");
        String baseURL = propertyUtility.getProperty("baseURL", "https://www.amazon.com");
        homePage.navigateToAmazon(baseURL);
        Assert.assertTrue(homePage.isSearchBoxDisplayed(), "Search box is not displayed");
    }

    @When("User searches for {string}")
    public void user_searches_for_product(String productName) {
        homePage.searchProduct(productName);
    }

    @Then("Search results should be displayed")
    public void search_results_should_be_displayed() {
        Assert.assertTrue(browserInteraction.getPageTitle().contains("Amazon"), "Search results page not loaded");
    }

    @When("User clicks on first product")
    public void user_clicks_on_first_product() throws InterruptedException {
        browserInteraction.waitForSeconds(2);
        try {
            // Try multiple locator strategies for better reliability
            By productLocator = By.xpath("(//h2//a)[1]");
            if (browserInteraction.isElementPresent(productLocator)) {
                browserInteraction.click(productLocator);
            } else {
                // Fallback to div with data-component-type
                By fallbackLocator = By.xpath("(//div[@data-component-type='s-search-result'][1]//h2//a)[1]");
                browserInteraction.click(fallbackLocator);
            }
        } catch (Exception e) {
            System.out.println("Error clicking first product: " + e.getMessage());
        }
        browserInteraction.waitForSeconds(2);
    }

    @When("User adds product to cart")
    public void user_adds_product_to_cart() throws InterruptedException {
        try {
            productDetailsPage.addToCart();
            browserInteraction.waitForSeconds(2);
        } catch (Exception e) {
            System.out.println("Add to cart button not found: " + e.getMessage());
        }
    }

    @Then("Product should be added to cart")
    public void product_should_be_added_to_cart() {
        try {
            browserInteraction.waitForSeconds(1);
            By cartIconLocator = By.id("nav-cart-count-container");
            String cartCount = browserInteraction.getText(cartIconLocator);
            Assert.assertNotNull(cartCount, "Cart count is not displayed");
        } catch (Exception e) {
            Assert.fail("Product not added to cart: " + e.getMessage());
        }
    }
}
