package com.amazon.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import com.amazon.utilities.PropertyUtility;
import java.io.IOException;
import java.time.Duration;

/**
 * Base class for setting up WebDriver and browser configuration
 */
public class BaseTest {
    public static WebDriver driver;
    protected PropertyUtility propertyUtility;

    /**
     * Initialize WebDriver based on browser type
     */
    public void initializeDriver(String browserType) throws IOException {
        propertyUtility = new PropertyUtility("src/test/resources/config/config.properties");
        String browser = browserType != null ? browserType : propertyUtility.getProperty("browser", "chrome");
        
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
    }

    /**
     * Get WebDriver instance
     */
    public static WebDriver getDriver() {
        return driver;
    }

    /**
     * Close browser
     */
    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Get base URL
     */
    public String getBaseURL() throws IOException {
        return propertyUtility.getProperty("baseURL", "https://www.amazon.com");
    }
}
