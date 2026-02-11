package org.example.unixFileSearch;


import org.example.unixFileSearch.files.File;
import org.example.unixFileSearch.predicates.Predicate;

public class FileSearchCriteria {
    private final Predicate predicate;

    public FileSearchCriteria(Predicate predicate) {
        this.predicate = predicate;
    }

    public boolean isMatch(final File inputFile) {
        return predicate.isMatch(inputFile);
    }
}
