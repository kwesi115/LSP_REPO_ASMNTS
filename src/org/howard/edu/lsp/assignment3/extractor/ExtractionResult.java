package org.howard.edu.lsp.assignment3.extractor;

import java.util.Collections;
import java.util.List;

/**
 * Wraps extracted records and counters for reporting.
 * 
 * @param <T> record type
 */
public class ExtractionResult<T> {
    private final List<T> records;
    private final int rowsRead; // data rows (excludes header)
    private final int rowsSkipped; // malformed rows

    public ExtractionResult(List<T> records, int rowsRead, int rowsSkipped) {
        this.records = records;
        this.rowsRead = rowsRead;
        this.rowsSkipped = rowsSkipped;
    }

    public List<T> getRecords() {
        return Collections.unmodifiableList(records);
    }

    public int getRowsRead() {
        return rowsRead;
    }

    public int getRowsSkipped() {
        return rowsSkipped;
    }
}