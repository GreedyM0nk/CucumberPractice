package com.retail.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.time.Duration;
import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * Virtual Thread Wait Utilities for Java 21+ 
 * 
 * Demonstrates non-blocking waits using virtual threads for improved
 * scalability in test automation, especially for parallel test execution.
 * 
 * Uses Executors.newVirtualThreadPerTaskExecutor() which is available
 * in Java 21+ for lightweight, scalable thread management.
 * 
 * @author Automation Framework Team
 */
public class VirtualThreadWaitUtils {

    private static final ExecutorService virtualThreadExecutor = Executors.newVirtualThreadPerTaskExecutor();

    /**
     * Execute an action with virtual thread (non-blocking)
     * 
     * @param action Supplier that returns result or throws exception on failure
     * @param timeout Duration to wait before timing out
     * @param <T> Return type
     * @return Result from the supplier
     * @throws Exception if timeout occurs or action fails
     */
    public static <T> T waitWithVirtualThread(Supplier<T> action, Duration timeout) throws Exception {
        CompletableFuture<T> future = CompletableFuture.supplyAsync(
            action,
            virtualThreadExecutor
        );
        
        try {
            return future.orTimeout(timeout.toMillis(), TimeUnit.MILLISECONDS).get();
        } catch (ExecutionException e) {
            if (e.getCause() instanceof TimeoutException) {
                throw (TimeoutException) e.getCause();
            }
            throw e;
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
            virtualThreadExecutor
        );
        
        try {
            return future.get();
        } catch (Exception e) {
            throw new RuntimeException("Virtual thread wait failed: " + e.getMessage(), e);
        }
    }

    /**
     * Parallel wait for multiple elements using virtual threads
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
                virtualThreadExecutor
            );
        }
        
        try {
            CompletableFuture.allOf(futures)
                .orTimeout(timeout.toMillis(), TimeUnit.MILLISECONDS)
                .get();
            
            Object[] results = new Object[futures.length];
            for (int i = 0; i < futures.length; i++) {
                results[i] = futures[i].getNow(null);
            }
            return results;
        } catch (Exception e) {
            throw new RuntimeException("Parallel virtual thread waits failed: " + e.getMessage(), e);
        }
    }

    /**
     * Create a virtual thread factory for custom thread creation
     * 
     * @return ThreadFactory for creating virtual threads
     */
    public static ThreadFactory createWaitThreadBuilder() {
        return Thread.ofVirtual().factory();
    }

    /**
     * Execute action in a virtual thread
     * 
     * @param action Action to execute
     * @param <T> Return type
     * @return Result from the action
     */
    public static <T> T executeInVirtualThread(Supplier<T> action) {
        CompletableFuture<T> future = CompletableFuture.supplyAsync(action, virtualThreadExecutor);
        try {
            return future.get();
        } catch (Exception e) {
            throw new RuntimeException("Virtual thread execution failed: " + e.getMessage(), e);
        }
    }

    /**
     * Shutdown virtual thread executor gracefully
     * Call this in test cleanup/teardown
     */
    public static void shutdown() {
        virtualThreadExecutor.shutdown();
        try {
            if (!virtualThreadExecutor.awaitTermination(10, TimeUnit.SECONDS)) {
                virtualThreadExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            virtualThreadExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
