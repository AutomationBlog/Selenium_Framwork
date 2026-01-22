package com.amazon.stepdefinitions;

import com.amazon.base.ExtentReportBase;
import com.amazon.utilities.BrowserInteractionUtility;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import com.amazon.base.BaseTest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Cucumber Hooks for test setup, teardown, and reporting
 * Handles browser initialization, reporting, screenshots, and logging
 */
public class Hooks extends BaseTest {
    private static BrowserInteractionUtility browserInteraction;
    private WebDriver driver;
    private Scenario scenario;
    private static final DateTimeFormatter dateFormatter = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Before Hook - Runs before each scenario
     * Initializes WebDriver and Extent Reports
     */
    @Before
    public void setUp(Scenario scenario) {
        this.scenario = scenario;
        try {
            // Initialize WebDriver
            initializeDriver(null);
            this.driver = BaseTest.driver;
            
            // Initialize BrowserInteractionUtility
            browserInteraction = new BrowserInteractionUtility(driver);
            
            // Initialize Extent Reports
            ExtentReportBase.initializeExtentReport();
            
            // Create test node in report
            ExtentReportBase.createTest(
                scenario.getName(), 
                "Scenario: " + scenario.getName()
            );
            
            // Log scenario start
            ExtentReportBase.logInfo(
                "Scenario Started: " + scenario.getName() + 
                " at " + LocalDateTime.now().format(dateFormatter)
            );
            
            System.out.println("\n" + "=".repeat(80));
            System.out.println("🚀 SCENARIO START: " + scenario.getName());
            System.out.println("🏷️  Tags: " + scenario.getSourceTagNames());
            System.out.println("⏰ Time: " + LocalDateTime.now().format(dateFormatter));
            System.out.println("=".repeat(80) + "\n");
            
        } catch (Exception e) {
            System.err.println("Error in setUp hook: " + e.getMessage());
            e.printStackTrace();
            ExtentReportBase.logFail("Setup failed: " + e.getMessage());
        }
    }

    /**
     * BeforeStep Hook - Runs before each step
     * Logs step information
     */
    @BeforeStep
    public void beforeStep(Scenario scenario) {
        try {
            ExtentReportBase.logInfo("Step: " + scenario.getName());
            System.out.println("► Step executing: " + scenario.getName());
        } catch (Exception e) {
            System.err.println("Error in beforeStep hook: " + e.getMessage());
        }
    }

    /**
     * AfterStep Hook - Runs after each step
     * Takes screenshot on step failure for debugging
     */
    @AfterStep
    public void afterStep(Scenario scenario) {
        try {
            // Check if step failed
            if (scenario.isFailed()) {
                takeScreenshot("Step_Failed_" + scenario.getName());
                ExtentReportBase.logFail("Step failed: " + scenario.getName());
            }
        } catch (Exception e) {
            System.err.println("Error in afterStep hook: " + e.getMessage());
        }
    }

    /**
     * After Hook - Runs after each scenario
     * Generates reports, takes screenshots, and closes browser
     */
    @After
    public void tearDown(Scenario scenario) {
        try {
            System.out.println("\n" + "=".repeat(80));
            System.out.println("✓ SCENARIO END: " + scenario.getName());
            System.out.println("📊 Status: " + (scenario.isFailed() ? "FAILED ❌" : "PASSED ✅"));
            System.out.println("⏰ Time: " + LocalDateTime.now().format(dateFormatter));
            
            // Take final screenshot
            if (!scenario.isFailed()) {
                takeScreenshot("Scenario_Passed_" + scenario.getName());
                ExtentReportBase.logPass("Scenario passed successfully");
            } else {
                takeScreenshot("Scenario_Failed_" + scenario.getName());
                ExtentReportBase.logFail("Scenario failed");
            }
            
            // Log scenario completion
            ExtentReportBase.logInfo(
                "Scenario Completed: " + scenario.getName() + 
                " | Status: " + (scenario.isFailed() ? "FAILED" : "PASSED")
            );
            
            // Clear cookies for next test
            if (driver != null) {
                try {
                    browserInteraction.deleteAllCookies();
                } catch (Exception e) {
                    System.out.println("Note: Could not clear cookies");
                }
            }
            
            System.out.println("=".repeat(80) + "\n");
            
            // Close browser
            closeBrowser();
            
            // Flush and generate report
            ExtentReportBase.flushReport();
            
        } catch (Exception e) {
            System.err.println("Error in tearDown hook: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Takes screenshot of current browser state
     * Useful for debugging and reporting
     */
    private void takeScreenshot(String fileName) {
        try {
            if (driver != null) {
                TakesScreenshot screenshot = (TakesScreenshot) driver;
                byte[] screenshotData = screenshot.getScreenshotAs(OutputType.BYTES);
                
                // Add screenshot to report
                ExtentReportBase.attachScreenshot(fileName, screenshotData);
                
                System.out.println("📸 Screenshot taken: " + fileName);
            }
        } catch (Exception e) {
            System.err.println("Error taking screenshot: " + e.getMessage());
        }
    }

    /**
     * Get BrowserInteractionUtility instance
     * Can be used by step definitions if needed
     */
    public static BrowserInteractionUtility getBrowserInteraction() {
        return browserInteraction;
    }

    /**
     * Get Scenario instance
     * Useful for accessing scenario information in steps
     */
    public Scenario getScenario() {
        return scenario;
    }
}
