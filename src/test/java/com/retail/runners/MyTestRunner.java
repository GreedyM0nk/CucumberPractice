package com.retail.runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features"}, // Location of Feature files
        glue = {"com.retail.stepdefinitions", "com.retail.runners"}, // Location of Step Definitions & Hooks
        plugin = {
                "pretty", // Prints readable logs in console
                "html:target/cucumber-reports/cucumber.html", // Standard HTML report
                "json:target/cucumber-reports/cucumber.json", // JSON report for CI/CD
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" // Extent Reports
        },
        monochrome = true // Clean console output
)
public class MyTestRunner {
    // This class must remain empty
}