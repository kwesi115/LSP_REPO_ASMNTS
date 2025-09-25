package org.howard.edu.lsp.assignment3;

import org.howard.edu.lsp.assignment3.extractor.CsvProductExtractor;
import org.howard.edu.lsp.assignment3.extractor.Extractor;
import org.howard.edu.lsp.assignment3.loader.CsvProductLoader;
import org.howard.edu.lsp.assignment3.loader.Loader;
import org.howard.edu.lsp.assignment3.model.Product;
import org.howard.edu.lsp.assignment3.transform.ProductTransformer;
import org.howard.edu.lsp.assignment3.transform.Transformer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Entry point for Assignment 3. Preserves A2 behavior:
 * - Reads data/products.csv (relative).
 * - If missing, prints clear error and exits (no output).
 * - If empty (header only), writes just the header to output.
 * - Writes data/transformed_products.csv with required columns and order.
 */
public class ETLRunner {
    private static final String INPUT = "data/products.csv";
    private static final String OUTPUT = "data/transformed_products.csv";

    /**
     * Main entry point.
     * 
     * @param args ignored
     */
    public static void main(String[] args) {
        Path inputPath = Paths.get(INPUT);
        Path outputPath = Paths.get(OUTPUT);

        // A3 requirement: handle missing input by printing a clear error and stopping.
        if (!Files.exists(inputPath)) {
            System.err.println("ERROR: Input file not found at " + INPUT + ". Aborting.");
            System.exit(1);
        }

        Extractor<Product> extractor = new CsvProductExtractor();
        Transformer<Product> transformer = new ProductTransformer();
        Loader<Product> loader = new CsvProductLoader();

        ETLPipeline pipeline = new ETLPipeline();
        try {
            pipeline.run(inputPath, outputPath, extractor, transformer, loader);
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            System.exit(1);
        }
    }
}