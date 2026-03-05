package com.retail.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Parallel Runner 4 – Scenario: Add product to cart from product details page (@S4)
 * Runs in its own JVM fork via Surefire (forkCount=4, reuseForks=false).
 *
 * Run individually in IntelliJ: right-click → Run 'ParallelRunner4'
 * Run via Maven:  mvn test -Dtest=ParallelRunner4
 * Run on SIT:     mvn test -Dtest=ParallelRunner4 -Psit
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        glue    = {"com.retail.stepdefinitions"},
        tags    = "@S4",
        plugin  = {
                "pretty",
                "html:target/cucumber-reports/parallel-runner4.html",
                "json:target/cucumber-reports/parallel-runner4.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true
)
public class ParallelRunner4 {
    // Empty – Cucumber uses the annotations above
}

