package com.noths.service.impl;

import com.noths.model.FinalOfferPromotion;
import com.noths.model.MultiBuyPromotion;
import com.noths.model.Product;
import com.noths.model.PromotionRule;
import com.noths.service.Checkout;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class CheckoutImpl implements Checkout {

    Set<PromotionRule> promotionRules;
    List<Product> products = new ArrayList<>();

    public CheckoutImpl(Set<PromotionRule> promotionRules){
        this.promotionRules= promotionRules;
    }
    @Override
    public void scan(Product prod) {
        products.add(prod);
    }

    @Override
    public Double total() {
        Map<Product, ? extends PromotionRule> activeRules = promotionRules.stream().filter(rule -> rule.getValidFrom().isBefore(LocalDate.now().plusDays(1)) && (Objects.isNull(rule.getValidTo()) || rule.getValidTo().isAfter(LocalDate.now().minusDays(1)))).collect(Collectors.toMap(PromotionRule::getProduct, promotionRule -> promotionRule));
        Map<String, Long> productCounts = products.stream().collect(Collectors.groupingBy(Product::getProductId, Collectors.counting()) );
        AtomicReference<Double> discountAmt = new AtomicReference<>(0.0);
        AtomicReference<Double> totalProductAmt = new AtomicReference<>(0.0);
        products.parallelStream().forEach(product -> {
            MultiBuyPromotion productPromotion = (MultiBuyPromotion) activeRules.get(product);
            if(Objects.nonNull(productPromotion)){
               int promotionQty=  productPromotion.getQuantity();
               long productQty = productCounts.get(product.getProductId());
               while (productQty >= promotionQty){
                   discountAmt.accumulateAndGet((productPromotion.getDiscountPercent() * product.getPrice()) / 100.0,  (d1, d2) -> d1 + d2);
                   productQty= productQty- promotionQty;
               }
                productCounts.put(product.getProductId(), 0L);
            }
            totalProductAmt.accumulateAndGet(product.getPrice(), (d1, d2) -> d1 + d2);
        });
        totalProductAmt.accumulateAndGet(discountAmt.get() , (d1, d2) -> d1 - d2 );
        totalProductAmt.set(Math.round(totalProductAmt.get()*100.0)/100.0);
        FinalOfferPromotion promotionOnTotal = (FinalOfferPromotion) activeRules.get(null);
        if(totalProductAmt.get()>= promotionOnTotal.getLimit())
            totalProductAmt.accumulateAndGet((promotionOnTotal.getDiscountPercent() * totalProductAmt.get()) / 100.0 , (d1, d2) -> d1 - d2 );
        return Math.round(totalProductAmt.get()*100.0)/100.0;
    }
}
