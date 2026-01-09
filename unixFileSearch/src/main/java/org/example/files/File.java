package org.example.files;

import java.util.HashSet;
import java.util.Set;

public class File {
    private final boolean isDirectory;
    private final int size;
    private final String owner;
    private final String fileName;
    private final Set<File> entries = new HashSet<>();

    public File(boolean isDirectory, int size, String owner, String fileName) {
        this.isDirectory = isDirectory;
        this.size = size;
        this.owner = owner;
        this.fileName = fileName;
    }

    public Object extract(final FileAttribute attributeName) {
        switch (attributeName) {
            case SIZE -> {
                return size;
            }
            case OWNER -> {
                return owner;
            }
            case IS_DIRECTORY -> {
                return isDirectory;
            }
            case FILENAME -> {
                return fileName;
            }
        }

        throw new IllegalArgumentException("Invalid filter criteria type");
    }

    public void addEntry(final File entry) {
        entries.add(entry);
    }

    public Set<File> getEntries() {
        return entries;
    }

    public String getFileName() {
        return fileName;
    }
}
