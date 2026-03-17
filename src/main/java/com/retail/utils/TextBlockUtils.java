package com.retail.utils;

/**
 * Java 21+ Text Blocks and String Formatting Utilities
 * 
 * Demonstrates modern Java improvements in text blocks:
 * - Multi-line string literals (Java 15+)
 * - String formatting with .formatted() (Java 21+)
 * - Improved formatting for HTML, JSON, and other text formats
 * 
 * Text blocks improve readability for multi-line string content like
 * error messages, JSON payloads, and HTML templates.
 * 
 * @since Java 15+ (Text Blocks), Java 21+ (Formatting)
 * @author Automation Framework Team
 */
public class TextBlockUtils {

    /**
     * Generate a formatted test report using Java 25 text blocks
     */
    public static String generateTestReportHeader(String testName, String environment, String timestamp) {
        return """
            ╔════════════════════════════════════════════════════════════╗
            ║              TEST EXECUTION REPORT                        ║
            ╠════════════════════════════════════════════════════════════╣
            ║ Test Name      : %s
            ║ Environment    : %s
            ║ Timestamp      : %s
            ║ Java Version   : 25
            ║ Framework      : Retail Automation (Java 25)
            ╚════════════════════════════════════════════════════════════╝""".formatted(testName, environment, timestamp);
    }

    /**
     * HTML template using Java 25 text blocks
     * Much more readable than concatenated strings
     */
    public static String generateAllureReportTemplate(String reportTitle, String version) {
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>%s</title>
                <style>
                    body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; }
                    .header { background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%); }
                    .metrics { display: grid; grid-template-columns: repeat(4, 1fr); gap: 20px; }
                </style>
            </head>
            <body>
                <div class="header">
                    <h1>%s</h1>
                    <p>Framework Version: %s</p>
                </div>
                <div class="content">
                    <!-- Report content goes here -->
                </div>
            </body>
            </html>""".formatted(reportTitle, reportTitle, version);
    }

    /**
     * JSON configuration template using text blocks
     */
    public static String generateTestConfigJson(String baseUrl, String browserType, boolean headless) {
        return """
            {
              "testConfiguration": {
                "baseUrl": "%s",
                "browser": {
                  "type": "%s",
                  "headless": %b,
                  "javaVersion": "25"
                },
                "timeouts": {
                  "implicitWait": 0,
                  "explicitWait": 10,
                  "pageLoadTimeout": 300
                },
                "parallelExecution": {
                  "enabled": true,
                  "threads": 6,
                  "virtualThreads": true
                }
              }
            }""".formatted(baseUrl, browserType, headless);
    }

    /**
     * Multi-line error message template
     */
    public static String formatElementNotFoundError(String locatorType, String locatorValue, long waitTime) {
        return """
            ╔════════════════════════════════════════════════════════════╗
            ║                   ELEMENT NOT FOUND ERROR                  ║
            ╠════════════════════════════════════════════════════════════╣
            ║ Locator Type  : %s
            ║ Locator Value : %s
            ║ Wait Time     : %d seconds
            ║ Java Version  : 25 (Virtual Threads enabled)
            ║ Status        : TIMEOUT - Element not found within timeout
            ║
            ║ Recommendations:
            ║   1. Verify element exists on current page
            ║   2. Check locator selector accuracy
            ║   3. Inspect page DOM using browser DevTools
            ║   4. Review page navigation flow
            ╚════════════════════════════════════════════════════════════╝""".formatted(
            locatorType, locatorValue, waitTime
        );
    }

    /**
     * Test summary report using text blocks
     */
    public static String generateTestSummary(int passed, int failed, int skipped, long totalDuration) {
        int total = passed + failed + skipped;
        double passPercentage = (passed * 100.0) / total;
        
        return """
            ═══════════════════════════════════════════════════════════════════
                                  TEST SUMMARY
            ═══════════════════════════════════════════════════════════════════
            
            Total Tests    : %d
            ✓ Passed       : %d (%.1f%%)
            ✗ Failed       : %d (%.1f%%)
            ⊘ Skipped      : %d (%.1f%%)
            
            Duration       : %d seconds
            
            Framework      : Retail Automation
            Java Version   : 25
            Features Used  : Pattern Matching, Text Blocks, Virtual Threads
            
            ═══════════════════════════════════════════════════════════════════""".formatted(
                total, 
                passed, passPercentage,
                failed, (failed * 100.0 / total),
                skipped, (skipped * 100.0 / total),
                totalDuration / 1000
            );
    }

    /**
     * Escaped sequences in text blocks (Java 25 improvement)
     */
    public static String getFrameworkFeatures() {
        return """
            Retail Automation Framework - Java 25 Features:
            
            🔹 Virtual Threads (Project Loom)
               - Lightweight, millions of concurrent threads
               - Improved parallel test execution scalability
               - Reduced thread contention
            
            🔹 Pattern Matching Enhancements
               - Sealed classes for type-safe test reports
               - Record pattern matching for data
               - Guard patterns for complex conditions
            
            🔹 Text Blocks
               - Multi-line strings for readability
               - Cleaner error messages and templates
               - Improved maintainability
            
            🔹 Enhanced Data Model
               - Records for immutable data classes
               - Improved performance
               - Type safety for test metrics
            
            🔹 Performance Improvements
               - Optimized GC for large test suites
               - Better memory management
               - Faster startup for CI/CD pipelines
            """;
    }

    /**
     * Unicode escape sequences in text blocks
     */
    public static String getStatusIndicators() {
        return """
            Status Indicators:
            ✓ PASS    - Test executed successfully
            ✗ FAIL    - Test execution failed
            ⊘ SKIP    - Test skipped
            ⚠ WARN    - Warning occurred
            ℹ INFO    - Informational message
            ⚡ PERF    - Performance concern
            """;
    }
}
