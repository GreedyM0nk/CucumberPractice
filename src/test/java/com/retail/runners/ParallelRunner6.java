package com.retail.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * ParallelRunner6 – Scenario: Header Navigation Tests (@S6)
 * Runs in its own JVM fork via Surefire (forkCount=6, reuseForks=false).
 *
 * Tests header and top navigation, logo, tagline, side navigation, and social icons
 *
 * Run individually in IntelliJ: right-click → Run 'ParallelRunner6'
 * Run via Maven:  mvn test -Dtest=ParallelRunner6
 * Run on SIT:     mvn test -Dtest=ParallelRunner6 -Psit
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        glue    = {"com.retail.stepdefinitions"},
        tags    = "@S6",
        plugin  = {
                "pretty",
                "html:target/cucumber-reports/parallel-runner6.html",
                "json:target/cucumber-reports/parallel-runner6.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true
)
public class ParallelRunner6 {
    // Empty – Cucumber uses the annotations above
}
