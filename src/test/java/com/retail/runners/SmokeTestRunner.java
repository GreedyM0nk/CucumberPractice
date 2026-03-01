package com.retail.runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

/**
 * Smoke Test Runner - Executes critical path scenarios
 * Run with: mvn test -Dtest=SmokeTestRunner
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        glue = {"com.retail.stepdefinitions"},
        tags = "@Smoke",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/smoke-report.html",
                "json:target/cucumber-reports/smoke-report.json",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true,
        dryRun = false
)
public class SmokeTestRunner {
    // This class must remain empty
}
