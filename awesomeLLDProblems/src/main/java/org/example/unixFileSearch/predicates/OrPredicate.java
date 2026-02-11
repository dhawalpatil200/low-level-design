package org.example.unixFileSearch.predicates;

import org.example.unixFileSearch.files.File;

import java.util.List;

public class OrPredicate implements CompositePredicate{
    private final List<Predicate> operands;

    public OrPredicate(List<Predicate> operands) {
        this.operands = operands;
    }

    @Override
    public boolean isMatch(File inputFile) {
        return operands.stream().anyMatch(predicate -> predicate.isMatch(inputFile));
    }
}
