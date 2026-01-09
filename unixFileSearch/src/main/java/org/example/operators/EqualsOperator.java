package org.example.operators;

import java.util.Objects;

public class EqualsOperator<T> implements ComparisonOperator<T> {

    @Override
    public boolean isMatch(T attributeValue, T expectedValue) {
        return Objects.equals(attributeValue, expectedValue);
    }
}
