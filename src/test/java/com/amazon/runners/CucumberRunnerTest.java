package com.amazon.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import com.amazon.stepdefinitions.*;

/**
 * Cucumber Test Runner
 */
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.amazon.stepdefinitions"},
        plugin = {
                "pretty",
                "html:test-output/CucumberReports/cucumber-report.html",
        },
        monochrome = true
        // tags = "@smoke or @regression"
)
public class CucumberRunnerTest extends AbstractTestNGCucumberTests {
}
