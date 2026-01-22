package com.amazon.runners;

import com.amazon.base.BaseTest;
import com.amazon.base.ExtentReportBase;
import com.amazon.pages.AmazonHomePage;
import com.amazon.pages.AmazonProductDetailsPage;
import com.amazon.pages.AmazonSearchResultsPage;
import com.amazon.utilities.PropertyUtility;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;

/**
 * TestNG Test Runner for Amazon Add to Cart functionality
 */
public class AmazonAddToCartTestNGRunner extends BaseTest {
    private AmazonHomePage homePage;
    private AmazonSearchResultsPage searchResultsPage;
    private AmazonProductDetailsPage productDetailsPage;
    private PropertyUtility propertyUtility;

    @BeforeSuite
    public void initializeReport() {
        ExtentReportBase.initializeExtentReport();
    }

    @BeforeMethod
    public void setUp() throws Exception {
        ExtentReportBase.initializeExtentReport();
        initializeDriver(null);
        homePage = new AmazonHomePage(driver);
        searchResultsPage = new AmazonSearchResultsPage(driver);
        productDetailsPage = new AmazonProductDetailsPage(driver);
    }

    @AfterMethod
    public void tearDown() {
        closeBrowser();
    }

    @AfterSuite
    public void flushReport() {
        ExtentReportBase.flushReport();
    }

    @Test(description = "Test Case 1: User adds laptop to cart")
    public void testAddLaptopToCart() throws Exception {
        try {
            ExtentReportBase.createTest("Add Laptop to Cart", "User adds a laptop to cart from Amazon");
            
            // Navigate to Amazon
            propertyUtility = new PropertyUtility("src/test/resources/config/config.properties");
            String baseURL = propertyUtility.getProperty("baseURL", "https://www.amazon.com");
            homePage.navigateToAmazon(baseURL);
            ExtentReportBase.logPass("Navigated to Amazon home page");

            // Search for laptop
            homePage.searchProduct("laptop");
            ExtentReportBase.logPass("Searched for laptop");
            
            Thread.sleep(2000);
            
            // Verify search results
            String firstProductTitle = searchResultsPage.getFirstProductTitle();
            Assert.assertNotNull(firstProductTitle);
            ExtentReportBase.logPass("First product found: " + firstProductTitle);

            // Click on first product
            searchResultsPage.clickFirstProduct();
            ExtentReportBase.logPass("Clicked on first product");
            
            Thread.sleep(3000);

            // Add to cart
            if (productDetailsPage.isAddToCartButtonDisplayed()) {
                productDetailsPage.addToCart();
                ExtentReportBase.logPass("Clicked Add to Cart button");
                
                productDetailsPage.waitForCartConfirmation();
                ExtentReportBase.logPass("Product added to cart successfully");
            } else {
                ExtentReportBase.logFail("Add to cart button not available");
                Assert.fail("Add to cart button not available");
            }

        } catch (Exception e) {
            ExtentReportBase.logFail("Test failed with exception: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(description = "Test Case 2: User adds mobile phone to cart")
    public void testAddMobilePhoneToCart() throws Exception {
        try {
            ExtentReportBase.createTest("Add Mobile Phone to Cart", "User adds a mobile phone to cart from Amazon");
            
            // Navigate to Amazon
            propertyUtility = new PropertyUtility("src/test/resources/config/config.properties");
            String baseURL = propertyUtility.getProperty("baseURL", "https://www.amazon.com");
            homePage.navigateToAmazon(baseURL);
            ExtentReportBase.logPass("Navigated to Amazon home page");

            // Search for mobile phone
            homePage.searchProduct("mobile phone");
            ExtentReportBase.logPass("Searched for mobile phone");
            
            Thread.sleep(2000);

            // Verify search results
            String firstProductTitle = searchResultsPage.getFirstProductTitle();
            Assert.assertNotNull(firstProductTitle);
            ExtentReportBase.logPass("First product found: " + firstProductTitle);

            // Click on first product
            searchResultsPage.clickFirstProduct();
            ExtentReportBase.logPass("Clicked on first product");
            
            Thread.sleep(3000);

            // Add to cart
            if (productDetailsPage.isAddToCartButtonDisplayed()) {
                productDetailsPage.addToCart();
                ExtentReportBase.logPass("Clicked Add to Cart button");
                
                productDetailsPage.waitForCartConfirmation();
                ExtentReportBase.logPass("Product added to cart successfully");
            } else {
                ExtentReportBase.logFail("Add to cart button not available");
                Assert.fail("Add to cart button not available");
            }

        } catch (Exception e) {
            ExtentReportBase.logFail("Test failed with exception: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(description = "Test Case 3: User adds headphones to cart with quantity")
    public void testAddHeadphonesToCartWithQuantity() throws Exception {
        try {
            ExtentReportBase.createTest("Add Headphones with Quantity to Cart", "User adds headphones with custom quantity to cart");
            
            // Navigate to Amazon
            propertyUtility = new PropertyUtility("src/test/resources/config/config.properties");
            String baseURL = propertyUtility.getProperty("baseURL", "https://www.amazon.com");
            homePage.navigateToAmazon(baseURL);
            ExtentReportBase.logPass("Navigated to Amazon home page");

            // Search for headphones
            homePage.searchProduct("headphones");
            ExtentReportBase.logPass("Searched for headphones");
            
            Thread.sleep(2000);

            // Verify search results
            String firstProductTitle = searchResultsPage.getFirstProductTitle();
            Assert.assertNotNull(firstProductTitle);
            ExtentReportBase.logPass("First product found: " + firstProductTitle);

            // Click on first product
            searchResultsPage.clickFirstProduct();
            ExtentReportBase.logPass("Clicked on first product");
            
            Thread.sleep(3000);

            // Set quantity
            try {
                productDetailsPage.setQuantity("2");
                ExtentReportBase.logPass("Set quantity to 2");
            } catch (Exception e) {
                ExtentReportBase.logInfo("Quantity field might not be available, proceeding with default");
            }

            // Add to cart
            if (productDetailsPage.isAddToCartButtonDisplayed()) {
                productDetailsPage.addToCart();
                ExtentReportBase.logPass("Clicked Add to Cart button");
                
                productDetailsPage.waitForCartConfirmation();
                ExtentReportBase.logPass("Product added to cart successfully");
            } else {
                ExtentReportBase.logFail("Add to cart button not available");
                Assert.fail("Add to cart button not available");
            }

        } catch (Exception e) {
            ExtentReportBase.logFail("Test failed with exception: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
}
