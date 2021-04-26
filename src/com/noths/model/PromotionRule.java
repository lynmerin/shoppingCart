package com.noths.model;

import java.time.LocalDate;

/**
 * Pojo class which holds common details of all promotion types
 * ValidFrom and ValidTo determines for what period that promotion is valid.
 * ValidFrom is considered as mandatory.
 */
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
