package org.howard.edu.lsp.assignment3.loader;

import java.nio.file.Path;
import java.util.List;

/**
 * Loads (writes) a collection of records to a destination.
 * 
 * @param <T> record type
 */
public interface Loader<T> {
    /**
     * Writes the provided records to the destination at outputPath.
     * Implementations must write a header row when applicable.
     * 
     * @param outputPath destination path
     * @param records    list of records to write
     * @throws Exception for IO errors
     */
    void load(Path outputPath, List<T> records) throws Exception;
}
