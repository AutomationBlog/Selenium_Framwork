package com.amazon.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Base class for Extent Reports configuration
 * Handles test reporting, logging, and screenshot attachment
 */
public class ExtentReportBase {
    public static ExtentReports extentReports;
    public static ExtentTest extentTest;
    private static ExtentSparkReporter sparkReporter;
    private static final String SCREENSHOT_PATH = "test-output/ExtentReports/screenshots/";

    /**
     * Initialize Extent Reports
     */
    public static void initializeExtentReport() {
        String reportPath = "test-output/ExtentReports/";
        new File(reportPath).mkdirs();
        new File(SCREENSHOT_PATH).mkdirs();

        String reportName = "AutomationReport_" + 
                new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss").format(new Date()) + ".html";

        sparkReporter = new ExtentSparkReporter(reportPath + reportName);
        sparkReporter.config().setReportName("Amazon Automation Test Report");
        sparkReporter.config().setDocumentTitle("Test Execution Report");

        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
        extentReports.setSystemInfo("OS", System.getProperty("os.name"));
        extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
        extentReports.setSystemInfo("Browser", "Chrome");
        extentReports.setSystemInfo("Execution Date", new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
    }

    /**
     * Create test node in extent report
     */
    public static void createTest(String testName, String description) {
        extentTest = extentReports.createTest(testName, description);
    }

    /**
     * Log pass in extent report
     */
    public static void logPass(String message) {
        if (extentTest != null) {
            extentTest.pass(message);
        }
    }

    /**
     * Log fail in extent report
     */
    public static void logFail(String message) {
        if (extentTest != null) {
            extentTest.fail(message);
        }
    }

    /**
     * Log skip in extent report
     */
    public static void logSkip(String message) {
        if (extentTest != null) {
            extentTest.skip(message);
        }
    }

    /**
     * Log info in extent report
     */
    public static void logInfo(String message) {
        if (extentTest != null) {
            extentTest.info(message);
        }
    }

    /**
     * Log warning in extent report
     */
    public static void logWarning(String message) {
        if (extentTest != null) {
            extentTest.warning(message);
        }
    }

    /**
     * Attach screenshot to extent report
     * @param fileName Name of the screenshot file
     * @param screenshotData Byte array of screenshot
     */
    public static void attachScreenshot(String fileName, byte[] screenshotData) {
        try {
            // Create screenshot file
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String screenshotPath = SCREENSHOT_PATH + fileName + "_" + timestamp + ".png";
            
            FileOutputStream fileOutputStream = new FileOutputStream(screenshotPath);
            fileOutputStream.write(screenshotData);
            fileOutputStream.close();
            
            // Attach to report
            if (extentTest != null) {
                extentTest.addScreenCaptureFromPath(screenshotPath, fileName);
            }
            
            System.out.println("Screenshot saved: " + screenshotPath);
        } catch (Exception e) {
            System.err.println("Error attaching screenshot: " + e.getMessage());
        }
    }

    /**
     * Attach screenshot from file path
     * @param filePath Path to screenshot file
     * @param fileName Display name for screenshot
     */
    public static void attachScreenshot(String filePath, String fileName) {
        try {
            if (extentTest != null) {
                extentTest.addScreenCaptureFromPath(filePath, fileName);
            }
        } catch (Exception e) {
            System.err.println("Error attaching screenshot: " + e.getMessage());
        }
    }

    /**
     * Flush extent reports
     */
    public static void flushReport() {
        if (extentReports != null) {
            extentReports.flush();
            System.out.println("\n✓ Extent Report generated successfully");
        }
    }

    /**
     * Get current test node
     */
    public static ExtentTest getTest() {
        return extentTest;
    }
}
