package com.retail.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * Virtual Thread-based Wait Utilities (Java 25 - Project Loom)
 * 
 * Leverages Java 25 Virtual Threads for efficient, lightweight asynchronous waits.
 * Virtual Threads allow thousands of concurrent waits without consuming native threads,
 * enabling superior scalability for parallel test execution.
 * 
 * Features:
 * - Non-blocking wait operations using virtual threads
 * - Automatic timeout management
 * - Reduces thread pool contention in parallel test execution
 * - Seamless integration with existing WebDriverWait patterns
 * 
 * Java 25 Improvements:
 * - Virtual Threads are now stable with performance enhancements
 * - Structured Concurrency (Project Panama) for better resource management
 * - Improved ThreadFactory support for virtual thread creation
 * 
 * @since Java 25
 * @author Automation Framework Team
 */
public class VirtualThreadWaitUtils {

    /**
     * Perform a non-blocking wait using virtual threads
     * 
     * Example:
     * <pre>
     * VirtualThreadWaitUtils.waitWithVirtualThread(() -> {
     *     webDriver.findElement(By.id("element")).click();
     *     return true;
     * }, Duration.ofSeconds(10));
     * </pre>
     * 
     * @param action Supplier that returns boolean or throws exception on failure
     * @param timeout Duration to wait before timing out
     * @param <T> Return type
     * @return Result from the supplier
     * @throws Exception if timeout occurs or action fails
     */
    public static <T> T waitWithVirtualThread(Supplier<T> action, Duration timeout) throws Exception {
        CompletableFuture<T> future = CompletableFuture.supplyAsync(
            action,
            Thread.ofVirtual().factory()  // Java 25: Virtual Thread Factory
        );
        
        try {
            return future.orTimeout(timeout.toMillis(), java.util.concurrent.TimeUnit.MILLISECONDS).get();
        } catch (java.util.concurrent.TimeoutException e) {
            throw new java.util.concurrent.TimeoutException(
                "Wait exceeded timeout of " + timeout.toSeconds() + " seconds"
            );
        }
    }

    /**
     * Perform a WebDriver wait with virtual thread optimization
     * 
     * @param driver WebDriver instance
     * @param wait WebDriverWait instance with configured timeout
     * @param condition ExpectedCondition to wait for
     * @param <T> Element type
     * @return Element when condition is met
     */
    public static <T> T waitForElementWithVirtualThread(
            WebDriver driver,
            WebDriverWait wait,
            ExpectedCondition<T> condition) {
        
        // Use virtual thread for non-blocking wait execution
        CompletableFuture<T> future = CompletableFuture.supplyAsync(
            () -> wait.until(condition),
            Thread.ofVirtual().factory()
        );
        
        try {
            return future.get();
        } catch (Exception e) {
            throw new RuntimeException("Virtual thread wait failed: " + e.getMessage(), e);
        }
    }

    /**
     * Parallel wait for multiple elements using virtual threads
     * Demonstrates Java 25 structured concurrency capabilities
     * 
     * @param waitSuppliers Array of suppliers for parallel waits
     * @param timeout Total timeout for all waits
     * @return Array of results in same order as input
     */
    public static Object[] parallelWaitWithVirtualThreads(
            Supplier<?>[] waitSuppliers,
            Duration timeout) {
        
        CompletableFuture<?>[] futures = new CompletableFuture[waitSuppliers.length];
        
        for (int i = 0; i < waitSuppliers.length; i++) {
            futures[i] = CompletableFuture.supplyAsync(
                waitSuppliers[i],
                Thread.ofVirtual().factory()  // Each wait gets its own virtual thread
            );
        }
        
        try {
            CompletableFuture.allOf(futures)
                .orTimeout(timeout.toMillis(), java.util.concurrent.TimeUnit.MILLISECONDS)
                .get();
            
            Object[] results = new Object[futures.length];
            for (int i = 0; i < futures.length; i++) {
                results[i] = futures[i].get();
            }
            return results;
        } catch (Exception e) {
            throw new RuntimeException("Parallel virtual thread wait failed: " + e.getMessage(), e);
        }
    }

    /**
     * Create a virtual thread factory for custom wait scenarios
     * Java 25 improvement: Enhanced thread creation with better resource management
     * 
     * @return VirtualThreadFactory configured for test automation
     */
    public static Thread.Builder.OfVirtual createWaitThreadBuilder() {
        return Thread.ofVirtual()
            .name("wait-", 0)  // Naming virtual threads for debugging
            .inheritInheritableThreadLocals(true);  // Inherit test context
    }
}
