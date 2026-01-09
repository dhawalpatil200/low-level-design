package org.example.predicates;

import org.example.files.File;

import java.util.List;

public class AndPredicate implements CompositePredicate{
    private final List<Predicate> operands;

    public AndPredicate(List<Predicate> operands) {
        this.operands = operands;
    }

    @Override
    public boolean isMatch(File inputFile) {
        return operands.stream().allMatch(predicate -> predicate.isMatch(inputFile));
    }
}
