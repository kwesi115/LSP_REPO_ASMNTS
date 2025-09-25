package org.howard.edu.lsp.assignment3.extractor;

import java.nio.file.Path;

/**
 * Extracts typed records from a source.
 * @param <T> record type
 */
public interface Extractor<T> {
    /**
     * Extracts records from the given input path.
     * @param inputPath path to input
     * @return ExtractionResult containing parsed records and stats
     * @throws Exception for IO errors
     */
    ExtractionResult<T> extract(Path inputPath) throws Exception;
}