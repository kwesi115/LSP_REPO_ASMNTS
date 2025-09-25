package org.howard.edu.lsp.assignment3.loader;

import org.howard.edu.lsp.assignment3.model.Product;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * CSV loader for Product records. Always writes a header row.
 * Header: ProductID,Name,Price,Category,PriceRange
 */
public class CsvProductLoader implements Loader<Product> {
    private static final String HEADER = "ProductID,Name,Price,Category,PriceRange";

    @Override
    public void load(Path outputPath, List<Product> records) throws Exception {
        if (outputPath.getParent() != null) {
            Files.createDirectories(outputPath.getParent());
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath.toFile()))) {
            bw.write(HEADER);
            bw.newLine();

            for (Product p : records) {
                bw.write(p.toCsvRow());
                bw.newLine();
            }
            bw.flush();
        }
    }
}