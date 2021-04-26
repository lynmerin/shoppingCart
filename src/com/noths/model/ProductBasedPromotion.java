package com.noths.model;

import java.time.LocalDate;

/**
 * Pojo class for product based promotion.
 * Quantity field is used to determine the number of items need to be there for promotion to apply
 */
public class ProductBasedPromotion extends PromotionRule{
    private int quantity;

    public ProductBasedPromotion(Product product, LocalDate validFrom, LocalDate validTo, double discountPercent, int quantity) {
        super(validFrom, validTo, discountPercent, product);
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
