package org.example.unixFileSearch.operators;

public interface ComparisonOperator<T> {
    boolean isMatch(final T attributeValue, final T expectedValue);
}
