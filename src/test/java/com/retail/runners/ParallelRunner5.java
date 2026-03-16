package com.retail.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Parallel Runner 5 – Scenario: Footer Navigation Tests (@S5)
 * Runs in its own JVM fork via Surefire (forkCount=4, reuseForks=false).
 *
 * Tests footer navigation, About Us section, payment icons, and bottom bar links
 *
 * Run individually in IntelliJ: right-click → Run 'ParallelRunner5'
 * Run via Maven:  mvn test -Dtest=ParallelRunner5
 * Run on SIT:     mvn test -Dtest=ParallelRunner5 -Psit
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        glue    = {"com.retail.stepdefinitions"},
        tags    = "@S5",
        plugin  = {
                "pretty",
                "html:target/cucumber-reports/parallel-runner5.html",
                "json:target/cucumber-reports/parallel-runner5.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true
)
public class ParallelRunner5 {
    // Empty – Cucumber uses the annotations above
}
