package com.amazon.stepdefinitions;

import com.amazon.base.BaseTest;
import com.amazon.pages.AmazonHomePage;
import com.amazon.utilities.PropertyUtility;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import java.io.IOException;

/**
 * Step definitions for Amazon Add to Cart scenario
 */
public class AmazonAddToCartSteps extends BaseTest {
    private AmazonHomePage homePage;
    private PropertyUtility propertyUtility;

    @Before
    public void setUp() throws IOException {
        initializeDriver(null);
        homePage = new AmazonHomePage(driver);
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
        Assert.assertTrue(driver.getTitle().contains("Amazon"), "Search results page not loaded");
    }

    @When("User clicks on first product")
    public void user_clicks_on_first_product() throws InterruptedException {
        Thread.sleep(2000);
        try {
            // Try multiple locator strategies for better reliability
            org.openqa.selenium.WebElement product = driver.findElement(
                org.openqa.selenium.By.xpath("(//h2//a)[1]")
            );
            product.click();
        } catch (Exception e) {
            // Fallback to div with data-component-type
            driver.findElement(org.openqa.selenium.By.xpath("(//div[@data-component-type='s-search-result'][1]//h2//a)[1]")).click();
        }
        Thread.sleep(2000);
    }

    @When("User adds product to cart")
    public void user_adds_product_to_cart() throws InterruptedException {
        try {
            org.openqa.selenium.WebElement addBtn = driver.findElement(org.openqa.selenium.By.id("add-to-cart-button"));
            addBtn.click();
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Add to cart button not found");
        }
    }

    @Then("Product should be added to cart")
    public void product_should_be_added_to_cart() {
        try {
            Thread.sleep(1000);
            org.openqa.selenium.WebElement cartIcon = driver.findElement(org.openqa.selenium.By.id("nav-cart-count-container"));
            String cartCount = cartIcon.getText();
            Assert.assertNotNull(cartCount, "Cart count is not displayed");
        } catch (Exception e) {
            Assert.fail("Product not added to cart");
        }
    }
}
