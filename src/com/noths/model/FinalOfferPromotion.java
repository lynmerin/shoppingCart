package com.noths.model;

import java.time.LocalDate;

public class FinalOfferPromotion extends PromotionRule{

    private double limit;

    public FinalOfferPromotion( LocalDate validFrom, LocalDate validTo, double discountPercent, double limit) {
        super( validFrom, validTo, discountPercent, null);
        this.limit= limit;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }
}
