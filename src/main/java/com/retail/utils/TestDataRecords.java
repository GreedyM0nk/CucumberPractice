package com.retail.utils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Java 21+ Records for Test Automation Data Models
 * 
 * Java Records provide (available since Java 16+):
 * - Concise syntax for immutable data classes
 * - Automatic equals(), hashCode(), toString()
 * - Better performance than traditional classes
 * - Type-safe data handling
 * 
 * These records replace boilerplate code for test data structures
 * while providing all the necessary functionality.
 * 
 * @since Java 16+ (Records), Java 21+ (All features used here)
 * @author Automation Framework Team
 */
public class TestDataRecords {

    /**
     * Test Configuration Record
     * Replaces traditional TestConfig class with 10+ lines of boilerplate code
     */
    public record TestConfig(
            String baseUrl,
            String browserType,
            boolean headless,
            int implicitWaitSeconds,
            int explicitWaitSeconds,
            int parallelThreads,
            boolean enableVirtualThreads,
            String environment) {
        /**
         * Custom compact constructor for validation (Java 21+ feature)
         */
        public TestConfig {
            if (baseUrl == null || baseUrl.isEmpty()) {
                throw new IllegalArgumentException("Base URL cannot be null or empty");
            }
            if (implicitWaitSeconds < 0 || explicitWaitSeconds < 0) {
                throw new IllegalArgumentException("Wait times must be non-negative");
            }
            if (parallelThreads < 1) {
                throw new IllegalArgumentException("Parallel threads must be at least 1");
            }
        }

        /**
         * Convenience method to check if using virtual threads
         */
        public boolean usingVirtualThreads() {
            return enableVirtualThreads;
        }
    }

    /**
     * Test Execution Metrics Record
     * Tracks performance and execution data
     */
    public record TestMetrics(
            String testName,
            String testClass,
            LocalDateTime startTime,
            LocalDateTime endTime,
            long durationMs,
            boolean passed,
            String failureReason,
            int retryAttempts,
            String environment,
            String browserVersion,
            String osVersion) {
        /**
         * Calculate percentage of total suite time
         */
        public double getPercentageOfTotalTime(long totalSuiteTime) {
            return (durationMs * 100.0) / totalSuiteTime;
        }

        /**
         * Check if test was slow (more than 5 seconds)
         */
        public boolean wasSlowTest() {
            return durationMs > 5000;
        }

        /**
         * Get formatted duration string
         */
        public String getFormattedDuration() {
            if (durationMs < 1000) {
                return durationMs + "ms";
            } else if (durationMs < 60000) {
                return (durationMs / 1000.0) + "s";
            } else {
                long minutes = durationMs / 60000;
                long seconds = (durationMs % 60000) / 1000;
                return minutes + "m " + seconds + "s";
            }
        }
    }

    /**
     * Element Locator Record
     * Type-safe representation of element selectors
     */
    public record ElementLocator(
            String locatorType,
            String locatorValue,
            String elementName,
            boolean optional,
            boolean waitForVisibility) {
        /**
         * Validate locator values
         */
        public ElementLocator {
            if (locatorType == null || locatorValue == null) {
                throw new IllegalArgumentException("Locator type and value cannot be null");
            }
        }

        /**
         * Generate human-readable description
         */
        public String getDescription() {
            return elementName + " (" + locatorType + ": " + locatorValue + ")";
        }
    }

    /**
     * Test Report Record
     * Comprehensive test execution report
     */
    public record TestReport(
            String reportId,
            LocalDateTime executionDate,
            String reportTitle,
            int totalTests,
            int passedTests,
            int failedTests,
            int skippedTests,
            long totalExecutionTime,
            String environment,
            String javaVersion,
            List<TestMetrics> testMetrics,
            boolean parallelExecutionEnabled,
            int parallellThreadCount) {
        /**
         * Calculate pass rate percentage
         */
        public double getPassRate() {
            if (totalTests == 0)
                return 0;
            return (passedTests * 100.0) / totalTests;
        }

        /**
         * Get average test duration
         */
        public long getAverageTestDuration() {
            if (testMetrics == null || testMetrics.isEmpty())
                return 0;
            return testMetrics.stream()
                    .mapToLong(TestMetrics::durationMs)
                    .sum() / testMetrics.size();
        }

        /**
         * Get slowest test
         */
        public TestMetrics getSlowestTest() {
            if (testMetrics == null || testMetrics.isEmpty())
                return null;
            return testMetrics.stream()
                    .max((t1, t2) -> Long.compare(t1.durationMs(), t2.durationMs()))
                    .orElse(null);
        }

        /**
         * Get fastest test
         */
        public TestMetrics getFastestTest() {
            if (testMetrics == null || testMetrics.isEmpty())
                return null;
            return testMetrics.stream()
                    .min((t1, t2) -> Long.compare(t1.durationMs(), t2.durationMs()))
                    .orElse(null);
        }
    }

    /**
     * Browser Capability Record
     * Type-safe browser configuration
     */
    public record BrowserCapability(
            String browserName,
            String browserVersion,
            String platformName,
            boolean acceptInsecureCerts,
            int pageLoadStrategy, // 0: normal, 1: eager, 2: none
            boolean strictFileInteractability) {
        /**
         * Convenience static factories (Java 21+ feature)
         */
        public static BrowserCapability chromeHeadless() {
            return new BrowserCapability("chrome", "latest", "linux", true, 0, false);
        }

        public static BrowserCapability firefoxHeadless() {
            return new BrowserCapability("firefox", "latest", "linux", true, 0, false);
        }

        public static BrowserCapability chromeInteractive() {
            return new BrowserCapability("chrome", "latest", "linux", false, 1, false);
        }
    }

    /**
     * Wait Strategy Record
     * Encapsulates wait configuration
     */
    public record WaitStrategy(
            String strategyType, // element, url, javascript, condition
            long timeoutMs,
            long pollIntervalMs,
            String conditionDescription) {
        /**
         * Standard wait strategies as static fields (Java 21+)
         */
        public static final WaitStrategy QUICK = new WaitStrategy("element", 5000, 500, "Quick 5-second wait");

        public static final WaitStrategy STANDARD = new WaitStrategy("element", 10000, 500, "Standard 10-second wait");

        public static final WaitStrategy LONG = new WaitStrategy("element", 30000, 1000, "Long 30-second wait");

        /**
         * Convert timeout to seconds
         */
        public double getTimeoutSeconds() {
            return timeoutMs / 1000.0;
        }
    }

    /**
     * Test Failure Details Record
     * Comprehensive failure information
     */
    public record FailureDetails(
            String testName,
            String failureType, // assertion, timeout, exception, navigation
            String failureMessage,
            String stackTrace,
            String screenshotPath,
            String pageSourcLog,
            LocalDateTime failureTime,
            int lineNumber,
            String sourceFile) {
        /**
         * Check if failure is recoverable (retry-able)
         */
        public boolean isRecoverable() {
            return failureType.equalsIgnoreCase("timeout") ||
                    failureType.equalsIgnoreCase("temporary_error");
        }
    }
}
