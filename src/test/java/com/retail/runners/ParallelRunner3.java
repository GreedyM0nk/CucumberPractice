package com.retail.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Parallel Runner 3 – Scenario: Verify products are displayed on catalogue page (@S3)
 * Runs in its own JVM fork via Surefire (forkCount=4, reuseForks=false).
 *
 * Run individually in IntelliJ: right-click → Run 'ParallelRunner3'
 * Run via Maven:  mvn test -Dtest=ParallelRunner3
 * Run on SIT:     mvn test -Dtest=ParallelRunner3 -Psit
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        glue    = {"com.retail.stepdefinitions"},
        tags    = "@S3",
        plugin  = {
                "pretty",
                "html:target/cucumber-reports/parallel-runner3.html",
                "json:target/cucumber-reports/parallel-runner3.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true
)
public class ParallelRunner3 {
    // Empty – Cucumber uses the annotations above
}

