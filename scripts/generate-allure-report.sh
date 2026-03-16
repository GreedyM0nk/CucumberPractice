#!/bin/bash

# Allure Report Generation Script for CI/CD
# This script ensures Allure reports are properly generated with all test metadata

set -e

PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
RESULTS_DIR="${PROJECT_DIR}/target/allure-results"
REPORT_DIR="${PROJECT_DIR}/target/site/allure-report"
HISTORY_DIR="${PROJECT_DIR}/target/site/allure-report/history"

echo "================================"
echo "Allure Report Generation"
echo "================================"

# Check if results directory exists
if [ ! -d "$RESULTS_DIR" ]; then
    echo "✗ ERROR: No Allure results directory found at $RESULTS_DIR"
    echo "  Please run tests first: mvn clean test"
    exit 1
fi

# Check if results are present
RESULT_COUNT=$(find "$RESULTS_DIR" -name "*.json" | wc -l)
if [ "$RESULT_COUNT" -eq 0 ]; then
    echo "✗ ERROR: No test results found in $RESULTS_DIR"
    exit 1
fi

echo "✓ Found $RESULT_COUNT result files"

# Generate Allure report
echo ""
echo "Generating Allure report..."
mvn allure:report \
    -Dallure.results.directory="$RESULTS_DIR" \
    -q || {
    echo "✗ Report generation failed"
    exit 1
}

# Verify report was created
if [ ! -f "$REPORT_DIR/index.html" ]; then
    echo "✗ ERROR: Report index.html was not generated"
    exit 1
fi

echo "✓ Report generated successfully at $REPORT_DIR"

# Display report statistics
echo ""
echo "Report Details:"
if [ -f "$REPORT_DIR/data/attachments.json" ] || [ -f "$REPORT_DIR/data/test-cases.json" ]; then
    echo "✓ Test metadata found"
else
    echo "⚠ Warning: Test metadata files not found (test counts may not display)"
fi

# Verify history structure
if [ -d "$HISTORY_DIR" ]; then
    HISTORY_COUNT=$(find "$HISTORY_DIR" -name "*.json" 2>/dev/null | wc -l)
    echo "✓ History preserved with $HISTORY_COUNT historical entries"
fi

echo ""
echo "================================"
echo "Report ready at: $REPORT_DIR"
echo "================================"
