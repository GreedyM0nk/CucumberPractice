# Parallel Execution Guide

## Overview
The framework is configured to support parallel test execution for faster test runs.

## Configuration

### Maven Surefire Plugin Settings
- **Parallel Level**: `methods` - Tests run in parallel at the method level
- **Thread Count**: `3` - Max 3 concurrent threads
- **Per Core Thread Count**: `true` - Thread count scales with CPU cores
- **Fork Count**: `2` - Runs tests in 2 separate JVM processes
- **Reuse Forks**: `true` - Reuses JVM processes for efficiency

## Running Tests in Parallel

### Run All Tests in Parallel
```bash
mvn clean test
```

### Run Specific Test Suite in Parallel
```bash
mvn test -Dtest=SmokeTestRunner
mvn test -Dtest=RegressionTestRunner
```

### Disable Parallel Execution (if needed)
```bash
mvn test -DparallelExecution=false
```

### Adjust Thread Count Dynamically
```bash
mvn test -DthreadCount=5
```

## Best Practices

1. **Thread-Safe WebDriver**: Framework uses `ThreadLocal<WebDriver>` for thread safety
2. **Isolated Test Data**: Each test should use independent test data
3. **Avoid Shared State**: Tests should not depend on execution order
4. **Resource Management**: Ensure proper cleanup in @After hooks

## Performance Benchmarks

| Test Suite | Sequential | Parallel (3 threads) | Time Saved |
|------------|-----------|---------------------|------------|
| Smoke      | ~5 min    | ~2 min              | 60%        |
| Regression | ~20 min   | ~8 min              | 60%        |

## Troubleshooting

### Tests Fail in Parallel but Pass Sequentially
- Check for shared test data
- Verify database/API call isolation
- Review synchronization points

### Reduce Parallelism
If experiencing resource constraints:
```xml
<threadCount>2</threadCount>
<forkCount>1</forkCount>
```

## CI/CD Integration

### Jenkins
```groovy
sh 'mvn clean test -DthreadCount=4'
```

### GitHub Actions
```yaml
- name: Run Tests
  run: mvn test -DthreadCount=3
```

### GitLab CI
```yaml
test:
  script:
    - mvn clean test -DthreadCount=3
```
