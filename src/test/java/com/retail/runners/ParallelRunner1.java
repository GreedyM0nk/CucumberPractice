package com.retail.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Parallel Runner 1 – Scenario: Add first product to cart from catalogue page (@S1)
 * Runs in its own JVM fork via Surefire (forkCount=4, reuseForks=false).
 *
 * Run individually in IntelliJ: right-click → Run 'ParallelRunner1'
 * Run via Maven:  mvn test -Dtest=ParallelRunner1
 * Run on SIT:     mvn test -Dtest=ParallelRunner1 -Psit
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        glue    = {"com.retail.stepdefinitions"},
        tags    = "@S1",
        plugin  = {
                "pretty",
                "html:target/cucumber-reports/parallel-runner1.html",
                "json:target/cucumber-reports/parallel-runner1.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true
)
public class ParallelRunner1 {
    // Empty – Cucumber uses the annotations above
}

