package org.example.unixFileSearch.predicates;


import org.example.unixFileSearch.files.File;
import org.example.unixFileSearch.files.FileAttribute;
import org.example.unixFileSearch.operators.ComparisonOperator;

public class SimplePredicate<T> implements Predicate {
    private final FileAttribute attributeName;
    private final ComparisonOperator<T> operator;

    T expectedValue;

    public SimplePredicate(FileAttribute attributeName, ComparisonOperator<T> operator, T expectedValue) {
        this.attributeName = attributeName;
        this.operator = operator;
        this.expectedValue = expectedValue;
    }

    @Override
    public boolean isMatch(File inputFile) {
        Object actualValue = inputFile.extract(attributeName);
        if(expectedValue.getClass().isInstance(actualValue)) {
            return operator.isMatch((T) actualValue, expectedValue);
        } else {
            return false;
        }
    }
}
