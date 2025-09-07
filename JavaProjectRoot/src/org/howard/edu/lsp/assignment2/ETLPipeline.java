package org.howard.edu.lsp.assignment2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ETLPipeline {
    private static final String INPUT_PATH = "data/products.csv";
    private static final String OUTPUT_PATH = "data/transformed_products.csv";
    private static final String OUTPUT_HEADER = "ProductID,Name,Price,Category,PriceRange";

    public static void main(String[] args) {
        Path in = Paths.get(INPUT_PATH);
        Path out = Paths.get(OUTPUT_PATH);

        // Missing input file
        if (!Files.exists(in)) {
            System.err.println("ERROR: Input file not found at " + INPUT_PATH + ". Aborting.");
            System.exit(1);
        }

        // Ensure output directory exists
        try {
            Path parent = out.getParent();
            if (parent != null)
                Files.createDirectories(parent);
        } catch (IOException e) {
            System.err.println("ERROR: Could not create output directory: " + e.getMessage());
            System.exit(1);
        }

        int rowsRead = 0; // number of data rows read (excludes input header)
        int rowsTransformed = 0; // number of valid rows written
        int rowsSkipped = 0; // invalid/malformed rows skipped

        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_PATH));
                BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_PATH))) {

            // Header
            bw.write(OUTPUT_HEADER);
            bw.newLine();

            // Read and ignore input header (if present)
            String header = br.readLine();
            if (header == null) {
                // No header, treat as empty; we already wrote output header
                printSummary(rowsRead, rowsTransformed, rowsSkipped, OUTPUT_PATH);
                return;
            }

            String line;
            while ((line = br.readLine()) != null) {
                rowsRead++;

                // Split with -1 to retain empty fields if any
                String[] parts = line.split(",", -1);
                if (parts.length != 4) {
                    rowsSkipped++;
                    continue;
                }

                String productIdStr = parts[0].trim();
                String name = parts[1].trim();
                String priceStr = parts[2].trim();
                String category = parts[3].trim();

                // Parse productId & price
                Integer productId;
                BigDecimal price;
                try {
                    productId = Integer.valueOf(productIdStr);
                    price = new BigDecimal(priceStr);
                } catch (Exception ex) {
                    rowsSkipped++;
                    continue;
                }

                // ------------------ TRANSFORM (strict order) ------------------

                // (1) Uppercase name
                String finalName = name.toUpperCase();

                // (2) 10% discount for Electronics
                boolean wasElectronics = "Electronics".equalsIgnoreCase(category);
                BigDecimal finalPrice = price;
                if (wasElectronics) {
                    finalPrice = price.multiply(new BigDecimal("0.90"));
                }
                // round HALF_UP to 2 decimals
                finalPrice = finalPrice.setScale(2, RoundingMode.HALF_UP);

                // (3) Recategorize if post-discount price > 500.00 AND original category was
                // Electronics
                String finalCategory = category;
                if (wasElectronics && finalPrice.compareTo(new BigDecimal("500.00")) > 0) {
                    finalCategory = "Premium Electronics";
                }

                // (4) Compute PriceRange from FINAL price
                String priceRange = computePriceRange(finalPrice);

                // ------------------ LOAD (write) ------------------
                String outLine = productId + ","
                        + clean(finalName) + ","
                        + finalPrice.toPlainString() + ","
                        + clean(finalCategory) + ","
                        + priceRange;

                bw.write(outLine);
                bw.newLine();
                rowsTransformed++;
            }

            bw.flush();
        } catch (FileNotFoundException e) {
            System.err.println("ERROR: Input file not found during processing: " + INPUT_PATH);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("ERROR: IO error: " + e.getMessage());
            System.exit(1);
        }

        // Summary
        printSummary(rowsRead, rowsTransformed, rowsSkipped, OUTPUT_PATH);
    }

    private static String computePriceRange(BigDecimal price) {
        // $0.00–$10.00 → Low
        if (price.compareTo(new BigDecimal("10.00")) <= 0) {
            return "Low";
        }
        // $10.01–$100.00 → Medium
        if (price.compareTo(new BigDecimal("10.00")) > 0 && price.compareTo(new BigDecimal("100.00")) <= 0) {
            return "Medium";
        }
        // $100.01–$500.00 → High
        if (price.compareTo(new BigDecimal("100.00")) > 0 && price.compareTo(new BigDecimal("500.00")) <= 0) {
            return "High";
        }
        // $500.01 and above → Premium
        return "Premium";
    }

    // Per spec, fields won't contain commas/quotes. Tidy trim anyway.
    private static String clean(String s) {
        return (s == null) ? "" : s.trim();
    }

    private static void printSummary(int read, int transformed, int skipped, String outPath) {
        System.out.println("ETL Run Summary");
        System.out.println("----------------");
        System.out.println("Rows read:        " + read);
        System.out.println("Rows transformed: " + transformed);
        System.out.println("Rows skipped:     " + skipped);
        System.out.println("Output written:   " + outPath);
    }
}
