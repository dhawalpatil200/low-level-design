package org.example;

import org.example.files.File;
import org.example.files.FileAttribute;
import org.example.operators.EqualsOperator;
import org.example.operators.RegexMatchOperator;
import org.example.predicates.AndPredicate;
import org.example.predicates.SimplePredicate;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileSearchTest {

    @Test
    public void testFileSearch() {
        final File root = new File(true, 0, "adam", "root");
        final File a = new File(false, 2000, "adam", "a");
        final File b = new File(false, 3000, "george", "b");
        root.addEntry(a);
        root.addEntry(b);

        final FileSearchCriteria criteria = new FileSearchCriteria(
                new AndPredicate(List.of(
                        new SimplePredicate<>(FileAttribute.IS_DIRECTORY, new EqualsOperator<>(), false),
                        new SimplePredicate<>(FileAttribute.OWNER, new RegexMatchOperator<>(), "ge.*")
                ))
        );

        final FileSearch fileSearch = new FileSearch();
        final List<File> result = fileSearch.search(root, criteria);

        assertEquals(1, result.size());
        assertEquals("b", result.getFirst().getFileName());
    }

}