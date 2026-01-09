package org.example.predicates;

import org.example.files.File;

public class NotPredicate implements CompositePredicate{
    private final Predicate operand;

    public NotPredicate(Predicate predicate) {
        this.operand = predicate;
    }

    @Override
    public boolean isMatch(File inputFile) {
        return !operand.isMatch(inputFile);
    }
}
