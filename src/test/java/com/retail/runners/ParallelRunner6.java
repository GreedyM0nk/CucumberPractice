package com.retail.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * ParallelRunner6 - Header Navigation Tests (@S6)
 * 
 * Executes header and top navigation test scenarios in parallel.
 * 
 * Tag: @S6 (Header navigation tests)
 * Feature: src/test/resources/features/header/header.feature
 * 
 * Usage:
 *   mvn test -Dtest=ParallelRunner6
 * 
 * Test Execution Strategy:
 *   - Parallel execution: true (one test per JVM fork)
 *   - Report format: JSON for Allure integration
 *   - Cucumber scenarios: 25 header navigation scenarios
 */
@CucumberOptions(
    features = "src/test/resources/features/header",
    glue = "com.retail.stepdefinitions",
    tags = "@S6",
    plugin = {
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
        "json:target/cucumber-reports/parallel-runner6.json",
        "html:target/cucumber-reports/parallel-runner6.html",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
    },
    monochrome = true,
    publish = false,
    dryRun = false
)
public class ParallelRunner6 extends AbstractTestNGCucumberTests {

    /**
     * DataProvider required for TestNG parallel execution
     * 
     * Returns each scenario as a separate test case
     * Allows TestNG to distribute scenarios across parallel threads
     */
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
