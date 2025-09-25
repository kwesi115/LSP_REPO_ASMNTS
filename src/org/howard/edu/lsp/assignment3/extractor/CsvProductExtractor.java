package org.howard.edu.lsp.assignment3.extractor;

import org.howard.edu.lsp.assignment3.model.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * CSV extractor for Product records.
 * Assumptions: comma-delimited, header present, no quotes/commas in fields.
 */
public class CsvProductExtractor implements Extractor<Product> {

    @Override
    public ExtractionResult<Product> extract(Path inputPath) throws Exception {
        if (!Files.exists(inputPath)) {
            // Let caller decide how to handle missing input (A3 requires a clear error and
            // exit).
            throw new IllegalStateException("Input file not found at " + inputPath.toString());
        }

        List<Product> records = new ArrayList<>();
        int rowsRead = 0;
        int rowsSkipped = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(inputPath.toFile()))) {
            String header = br.readLine(); // may be null
            if (header == null) {
                // No header -> treat as empty; no records.
                return new ExtractionResult<>(records, rowsRead, rowsSkipped);
            }

            String line;
            while ((line = br.readLine()) != null) {
                rowsRead++;
                String[] parts = line.split(",", -1);
                if (parts.length != 4) {
                    rowsSkipped++;
                    continue;
                }
                String idStr = parts[0].trim();
                String name = parts[1].trim();
                String priceStr = parts[2].trim();
                String category = parts[3].trim();

                try {
                    int id = Integer.parseInt(idStr);
                    BigDecimal price = new BigDecimal(priceStr);
                    records.add(new Product(id, name, price, category));
                } catch (Exception parseEx) {
                    rowsSkipped++;
                }
            }
        }

        return new ExtractionResult<>(records, rowsRead, rowsSkipped);
    }
}
