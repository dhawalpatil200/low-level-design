package org.example.unixFileSearch.operators;

import java.util.regex.Pattern;

public class RegexMatchOperator<T extends String> implements ComparisonOperator<T> {
    @Override
    public boolean isMatch(T attributeValue, T expectedValue) {
        final Pattern p = Pattern.compile(expectedValue);
        return p.matcher(attributeValue).matches();
    }
}
