package org.example.unixFileSearch.operators;

public class GreaterThanOperator<T extends Number> implements ComparisonOperator<T> {
    @Override
    public boolean isMatch(T attributeValue, T expectedValue) {
        return Double.compare(attributeValue.doubleValue(), expectedValue.doubleValue()) > 0;
    }
}
