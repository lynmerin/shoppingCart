package com.noths.model;

import java.awt.color.ProfileDataException;
import java.time.LocalDate;

public class PromotionRule {
    private Product product;
    private LocalDate validFrom;
    private LocalDate validTo;
    private double discountPercent;

    public PromotionRule(LocalDate validFrom, LocalDate validTo, double discountPercent, Product product) {
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.discountPercent = discountPercent;
        this.product= product;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
