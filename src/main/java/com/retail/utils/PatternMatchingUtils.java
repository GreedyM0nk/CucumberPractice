package com.retail.utils;

/**
 * Java 21 LTS Pattern Matching Utilities
 * 
 * Demonstrates Java 21 LTS features in pattern matching:
 * - Enhanced instanceof patterns
 * - Improved switch pattern matching with guards
 * - Records for data validation
 * 
 * These utilities provide cleaner, more expressive code for common
 * test automation checks and validations using Java 21 LTS features.
 * 
 * @since Java 21 LTS (Pattern Matching & Advanced features)
 * @author Automation Framework Team
 */
public class PatternMatchingUtils {

    /**
     * Java 21 LTS: Enhanced pattern matching for element state verification
     * 
     * Example:
     * 
     * <pre>
     * Object element = webDriver.findElement(By.id("button"));
     * String message = PatternMatchingUtils.describeElementState(element);
     * </pre>
     */
    public static String describeElementState(Object obj) {
        // Java 21 LTS pattern matching with guards
        return switch (obj) {
            case String s when s.isEmpty() -> "Empty text content";
            case String s when s.length() > 100 -> "Long text content (" + s.length() + " chars)";
            case String s -> "Text: " + s;

            case Integer i when i < 0 -> "Negative number: " + i;
            case Integer i when i == 0 -> "Zero";
            case Integer i -> "Positive number: " + i;

            case Boolean b -> b ? "Enabled" : "Disabled";

            case Throwable e -> "Error: " + e.getClass().getSimpleName();

            case null -> "Null element";
            default -> "Unknown element type: " + obj.getClass().getSimpleName();
        };
    }

    /**
     * Java 25: Pattern matching for test result classification
     * 
     * More readable than instanceof chains or multiple if statements
     */
    public static String classifyTestResult(Object result) {
        // Java 25 enhanced switch pattern matching with records
        return switch (result) {
            case TestResultPass p ->
                "✓ PASS - " + p.description;

            case TestResultFail f ->
                "✗ FAIL - " + f.reason + " (" + f.errorCode + ")";

            case TestResultSkip s ->
                "⊘ SKIP - " + s.reason;

            case Throwable t ->
                "✗ ERROR - " + t.getMessage();

            default ->
                "? UNKNOWN - " + result.getClass().getSimpleName();
        };
    }

    /**
     * Java 25: Type-safe validation using pattern matching
     * 
     * Eliminates verbose instanceof checks and casts
     */
    public static boolean isValidTestData(Object data) {
        return switch (data) {
            // Pattern for String data
            case String s when s.matches("[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+") -> true;

            // Pattern for numeric data
            case Number n when n.doubleValue() > 0 && n.doubleValue() < 1000000 -> true;

            // Pattern for boolean
            case Boolean b -> b != null;

            // Null pattern (Java 21)
            case null -> false;

            default -> false;
        };
    }

    /**
     * Java 25: Records for test result types
     * Provides immutable data classes with automatic equals/hashCode/toString
     */
    public record TestResultPass(String description) {
    }

    public record TestResultFail(String reason, String errorCode) {
    }

    public record TestResultSkip(String reason) {
    }

    /**
     * Java 25: Record pattern matching for structured data
     * 
     * Example of implicit pattern matching with records
     */
    public record TestMetrics(String testName, long duration, boolean passed) {
        /**
         * Validate metrics consistency
         */
        public boolean isValid() {
            return testName != null && !testName.isEmpty() && duration >= 0;
        }

        /**
         * Categorize performance
         */
        public String getPerformanceCategory() {
            if (duration < 1000)
                return "Fast";
            if (duration < 5000)
                return "Normal";
            if (duration < 10000)
                return "Slow";
            return "Very Slow";
        }
    }

    /**
     * Java 25: Analyze metrics with pattern matching
     */
    public static String analyzeTestMetrics(TestMetrics metrics) {
        // Simple validation pattern
        if (metrics == null) {
            return "Invalid metrics: null";
        }

        // String building with conditional logic (Java 25 compatible)
        if (metrics.passed && metrics.duration > 5000) {
            return metrics.testName + ": Slow but successful (" + metrics.duration + "ms)";
        }

        if (!metrics.passed) {
            return metrics.testName + ": Failed after " + metrics.duration + "ms";
        }

        if (metrics.passed && metrics.duration <= 5000) {
            return metrics.testName + ": Fast pass (" + metrics.duration + "ms)";
        }

        return "Unknown metrics format";
    }

    /**
     * Java 25: Event handling with records (without sealed interfaces)
     */
    public static class TestEventHandler {
        public record TestStarted(TestMetrics metrics) {
        }

        public record TestCompleted(TestMetrics metrics) {
        }

        /**
         * Handle different event types
         */
        public static String handleEvent(Object event) {
            if (event instanceof TestStarted started) {
                return "Test started: " + started.metrics.testName;
            }

            if (event instanceof TestCompleted completed) {
                String status = completed.metrics.passed ? "PASSED" : "FAILED";
                return "Test completed: " + completed.metrics.testName +
                        " (" + status + ") in " + completed.metrics.duration + "ms";
            }

            return "Unknown event type";
        }
    }

    /**
     * Java 25: Element state validation
     * Simple alternative to pattern matching on tuples (Java 25 feature)
     */
    public static String evaluateElementState(int visibility, boolean enabled, String text) {
        // Conditional logic (Java 25 compatible)
        if (visibility == 100 && enabled && !text.isEmpty()) {
            return "Fully visible, enabled, with content";
        }

        if (visibility == 0) {
            return "Element not visible";
        }

        if (!enabled) {
            return "Element disabled at " + visibility + "% visibility";
        }

        if (visibility > 0 && enabled) {
            return "Partial visibility (" + visibility + "%) - " + text;
        }

        return "Unknown state";
    }

    /**
     * Java 25: Pattern matching with instanceof for type checking
     */
    public static boolean isNavigationElement(Object element) {
        return element instanceof NavLink navLink && navLink.isActive &&
                !navLink.href.isEmpty();
    }

    /**
     * Navigation link record
     */
    public record NavLink(String href, String text, boolean isActive) {
    }
}
