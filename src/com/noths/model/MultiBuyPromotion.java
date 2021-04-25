package com.noths.model;

import java.time.LocalDate;

public class MultiBuyPromotion extends PromotionRule{
    private int quantity;

    public MultiBuyPromotion(Product product, LocalDate validFrom, LocalDate validTo, double discountPercent, int quantity) {
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
