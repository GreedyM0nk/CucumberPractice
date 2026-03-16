package com.retail.utils;

/**
 * Java 25 Pattern Matching Utilities
 * 
 * Demonstrates Java 25 enhancements in pattern matching:
 * - Enhanced instanceof patterns
 * - Improved switch pattern matching
 * - Guard patterns
 * - Record patterns
 * 
 * These utilities provide cleaner, more expressive code for common
 * test automation checks and validations.
 * 
 * @since Java 25
 * @author Automation Framework Team
 */
public class PatternMatchingUtils {

    /**
     * Java 25: Enhanced pattern matching for element state verification
     * 
     * Example:
     * <pre>
     * Object element = webDriver.findElement(By.id("button"));
     * String message = PatternMatchingUtils.describeElementState(element);
     * </pre>
     */
    public static String describeElementState(Object obj) {
        // Java 25 pattern matching with guards
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
        // Java 25 enhanced switch pattern matching
        return switch (result) {
            case TestResultPattern.Pass p -> 
                "✓ PASS - " + p.description();
            
            case TestResultPattern.Fail f -> 
                "✗ FAIL - " + f.reason() + " (" + f.errorCode() + ")";
            
            case TestResultPattern.Skip s -> 
                "⊘ SKIP - " + s.reason();
            
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
            
            // Null pattern (Java 25)
            case null -> false;
            
            default -> false;
        };
    }

    /**
     * Java 25: Sealed class example for test report types
     * Restricts inheritance to defined subtypes for type safety
     */
    public sealed interface TestResultPattern {
        record Pass(String description) implements TestResultPattern {}
        record Fail(String reason, String errorCode) implements TestResultPattern {}
        record Skip(String reason) implements TestResultPattern {}
    }

    /**
     * Java 25: Record pattern matching for structured data
     * 
     * Example of record destructuring in Java 25
     */
    public record TestMetrics(String testName, long duration, boolean passed) {}

    public static String analyzeTestMetrics(Object obj) {
        // Java 25 record pattern matching
        return switch (obj) {
            case TestMetrics(var name, var duration, boolean passed) when duration > 5000 && passed ->
                name + ": Slow but successful (" + duration + "ms)";
            
            case TestMetrics(var name, var duration, boolean passed) when !passed ->
                name + ": Failed after " + duration + "ms";
            
            case TestMetrics(var name, var duration, true) ->
                name + ": Fast pass (" + duration + "ms)";
            
            default -> "Unknown metrics format";
        };
    }

    /**
     * Java 25: Multi-level pattern matching
     * Combine records and sealed classes for powerful type discrimination
     */
    public static void processTestEvent(Object event) {
        switch (event) {
            // Pattern matching on nested records (Java 25)
            case TestEvent.Started(TestMetrics(var name, _, _)) ->
                System.out.println("Test started: " + name);
            
            case TestEvent.Completed(TestMetrics(var name, var duration, var passed)) ->
                System.out.println("Test completed: " + name + 
                    (passed ? " (PASSED)" : " (FAILED)") + 
                    " in " + duration + "ms");
            
            case null ->
                System.out.println("Null event received");
            
            default ->
                System.out.println("Unknown event type");
        }
    }

    /**
     * Java 25: Sealed interface for test events
     */
    public sealed interface TestEvent {
        record Started(TestMetrics metrics) implements TestEvent {}
        record Completed(TestMetrics metrics) implements TestEvent {}
    }

    /**
     * Java 25: Pattern matching with complex conditions
     */
    public static String evaluateElementState(int visibility, boolean enabled, String text) {
        return switch ((visibility, enabled, text)) {
            case (100, true, _) when !text.isEmpty() ->
                "Fully visible, enabled, with content";
            
            case (0, _, _) ->
                "Element not visible";
            
            case (var v, false, _) ->
                "Element disabled at " + v + "% visibility";
            
            case (var v, true, var t) when v > 0 ->
                "Partial visibility (" + v + "%) - " + t;
            
            default ->
                "Unknown state";
        };
    }
}
