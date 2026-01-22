package com.amazon.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Base class for Extent Reports configuration
 */
public class ExtentReportBase {
    public static ExtentReports extentReports;
    public static ExtentTest extentTest;
    private static ExtentSparkReporter sparkReporter;

    /**
     * Initialize Extent Reports
     */
    public static void initializeExtentReport() {
        String reportPath = "test-output/ExtentReports/";
        new File(reportPath).mkdirs();

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
        extentTest.pass(message);
    }

    /**
     * Log fail in extent report
     */
    public static void logFail(String message) {
        extentTest.fail(message);
    }

    /**
     * Log skip in extent report
     */
    public static void logSkip(String message) {
        extentTest.skip(message);
    }

    /**
     * Log info in extent report
     */
    public static void logInfo(String message) {
        extentTest.info(message);
    }

    /**
     * Flush extent reports
     */
    public static void flushReport() {
        if (extentReports != null) {
            extentReports.flush();
        }
    }
}
