package org.example.unixFileSearch.predicates;

import org.example.unixFileSearch.files.File;

public interface Predicate {
    boolean isMatch(final File inputFile);
}
