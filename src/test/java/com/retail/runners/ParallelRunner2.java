package com.retail.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Parallel Runner 2 – Scenario: View first product details (@S2)
 * Runs in its own JVM fork via Surefire (forkCount=4, reuseForks=false).
 *
 * Run individually in IntelliJ: right-click → Run 'ParallelRunner2'
 * Run via Maven:  mvn test -Dtest=ParallelRunner2
 * Run on SIT:     mvn test -Dtest=ParallelRunner2 -Psit
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        glue    = {"com.retail.stepdefinitions"},
        tags    = "@S2",
        plugin  = {
                "pretty",
                "html:target/cucumber-reports/parallel-runner2.html",
                "json:target/cucumber-reports/parallel-runner2.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true
)
public class ParallelRunner2 {
    // Empty – Cucumber uses the annotations above
}

