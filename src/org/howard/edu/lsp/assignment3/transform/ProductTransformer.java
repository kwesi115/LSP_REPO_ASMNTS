package org.howard.edu.lsp.assignment3.transform;

import org.howard.edu.lsp.assignment3.model.PriceRange;
import org.howard.edu.lsp.assignment3.model.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ProductTransformer implements Transformer<Product> {
    private static final BigDecimal DISCOUNT = new BigDecimal("0.90");
    private static final BigDecimal FIVE_HUNDRED = new BigDecimal("500.00");

    @Override
    public Product transform(Product p) {
        p.setName(p.getName() == null ? "" : p.getName().toUpperCase());

        boolean wasElectronics = "Electronics".equalsIgnoreCase(p.getOriginalCategory());
        BigDecimal finalPrice = p.getPrice();
        if (wasElectronics) {
            finalPrice = finalPrice.multiply(DISCOUNT);
        }
        finalPrice = finalPrice.setScale(2, RoundingMode.HALF_UP);
        p.setPrice(finalPrice);

        if (wasElectronics && finalPrice.compareTo(FIVE_HUNDRED) > 0) {
            p.setCategory("Premium Electronics");
        } else {
            p.setCategory(p.getOriginalCategory());
        }

        p.setPriceRange(PriceRange.fromPrice(finalPrice).name());
        return p;
    }
}