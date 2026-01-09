package org.example;

import org.example.files.File;
import org.example.predicates.Predicate;

public class FileSearchCriteria {
    private final Predicate predicate;

    public FileSearchCriteria(Predicate predicate) {
        this.predicate = predicate;
    }

    public boolean isMatch(final File inputFile) {
        return predicate.isMatch(inputFile);
    }
}
