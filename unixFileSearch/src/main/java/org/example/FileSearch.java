package org.example;

import org.example.files.File;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class FileSearch {
    public List<File> search(final File root, final FileSearchCriteria criteria) {
        final List<File> result = new ArrayList<>();
        final ArrayDeque<File> recursionStack = new ArrayDeque<>();
        recursionStack.add(root);

        while(!recursionStack.isEmpty()) {
            File next = recursionStack.pop();
            if(criteria.isMatch(next)) {
                result.add(next);
            }

            recursionStack.addAll(next.getEntries());
        }

        return result;
    }
}
