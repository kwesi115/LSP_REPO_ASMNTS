package org.howard.edu.lsp.assignment3.model;

import java.math.BigDecimal;

/**
 * Price buckets computed from the FINAL price after all transformations.
 */
public enum PriceRange {
    Low, Medium, High, Premium;

    /**
     * Computes the PriceRange from a final price using the assignment’s thresholds.
     * 
     * @param price final price (2-decimal, HALF_UP)
     * @return PriceRange bucket
     */
    public static PriceRange fromPrice(BigDecimal price) {
        if (price.compareTo(new BigDecimal("10.00")) <= 0) {
            return Low; // $0.00–$10.00
        } else if (price.compareTo(new BigDecimal("100.00")) <= 0) {
            return Medium; // $10.01–$100.00
        } else if (price.compareTo(new BigDecimal("500.00")) <= 0) {
            return High; // $100.01–$500.00
        } else {
            return Premium; // $500.01+
        }
    }
}