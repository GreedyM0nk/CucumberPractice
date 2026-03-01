package com.retail.runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

/**
 * Regression Test Runner - Executes full test suite
 * Run with: mvn test -Dtest=RegressionTestRunner
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        glue = {"com.retail.stepdefinitions"},
        tags = "@Regression or @Smoke",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/regression-report.html",
                "json:target/cucumber-reports/regression-report.json",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true,
        dryRun = false
)
public class RegressionTestRunner {
    // This class must remain empty
}
