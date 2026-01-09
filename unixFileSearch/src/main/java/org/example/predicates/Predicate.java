package org.example.predicates;

import org.example.files.File;

public interface Predicate {
    boolean isMatch(final File inputFile);
}
