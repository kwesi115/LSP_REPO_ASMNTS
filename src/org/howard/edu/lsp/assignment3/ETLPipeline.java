package org.howard.edu.lsp.assignment3;

import org.howard.edu.lsp.assignment3.extractor.Extractor;
import org.howard.edu.lsp.assignment3.extractor.ExtractionResult;
import org.howard.edu.lsp.assignment3.loader.Loader;
import org.howard.edu.lsp.assignment3.model.Product;
import org.howard.edu.lsp.assignment3.transform.Transformer;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Coordinates the ETL flow: extract -> transform -> load.
 * Prints a run summary matching the assignment spec.
 */
public class ETLPipeline {

    /**
     * Executes the pipeline.
     * 
     * @param inputPath   path to data/products.csv
     * @param outputPath  path to data/transformed_products.csv
     * @param extractor   data extractor
     * @param transformer product transformer
     * @param loader      data loader
     * @throws Exception on IO failures
     */
    public void run(Path inputPath,
            Path outputPath,
            Extractor<Product> extractor,
            Transformer<Product> transformer,
            Loader<Product> loader) throws Exception {

        // Extract
        ExtractionResult<Product> result = extractor.extract(inputPath);
        int rowsRead = result.getRowsRead();
        int rowsSkipped = result.getRowsSkipped();

        // Transform
        List<Product> transformed = new ArrayList<>(result.getRecords().size());
        for (Product p : result.getRecords()) {
            transformed.add(transformer.transform(p));
        }
        int rowsTransformed = transformed.size();

        // Load (write header always; if empty input, writes just header)
        loader.load(outputPath, transformed);

        // Summary
        printSummary(rowsRead, rowsTransformed, rowsSkipped, outputPath.toString());
    }

    private void printSummary(int read, int transformed, int skipped, String outPath) {
        System.out.println("ETL Run Summary");
        System.out.println("----------------");
        System.out.println("Rows read:        " + read);
        System.out.println("Rows transformed: " + transformed);
        System.out.println("Rows skipped:     " + skipped);
        System.out.println("Output written:   " + outPath);
    }
}
