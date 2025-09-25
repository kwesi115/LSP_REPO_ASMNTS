package org.howard.edu.lsp.assignment3.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents a product record flowing through the ETL pipeline.
 * Encapsulates both original and final fields used by transformation rules.
 */
public class Product {
    private final int productId;
    private String name; // transformed to UPPERCASE per spec
    private BigDecimal price; // may be discounted & rounded per spec
    private final String originalCategory; // original category from input
    private String category; // may be recategorized
    private String priceRange; // derived from final price

    public Product(int productId, String name, BigDecimal price, String category) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.originalCategory = category;
        this.category = category;
    }

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getOriginalCategory() {
        return originalCategory;
    }

    public String getCategory() {
        return category;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    /**
     * Renders this product as a CSV line in the required column order.
     * 
     * @return CSV row string: ProductID,Name,Price,Category,PriceRange
     */
    public String toCsvRow() {
        return productId + "," +
                safe(name) + "," +
                price.toPlainString() + "," +
                safe(category) + "," +
                safe(priceRange);
    }

    private static String safe(String s) {
        return (s == null) ? "" : s.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Product))
            return false;
        Product that = (Product) o;
        return productId == that.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}
