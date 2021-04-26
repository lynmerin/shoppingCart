package com.noths.service.impl;

import com.noths.model.FinalOfferPromotion;
import com.noths.model.ProductBasedPromotion;
import com.noths.model.Product;
import com.noths.model.PromotionRule;
import com.noths.service.Checkout;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * Checkout Implementation class
 * promotionRules - holds all the available promotions
 * products - holds list of products that the user is trying to checkout (checkout basket)
 */
public class CheckoutImpl implements Checkout {

    Set<PromotionRule> promotionRules;
    List<Product> products = new ArrayList<>();

    /**
     * Constructor that sets all the promotion rules
     * @param promotionRules
     */
    public CheckoutImpl(Set<PromotionRule> promotionRules){
        this.promotionRules= promotionRules;
    }

    /**
     * Method to add products to the basket
     * @param prod
     */
    @Override
    public void scan(Product prod) {
        products.add(prod);
    }

    /**
     * Method to calculate the final amount user have to pay after applying all the valid promotions
     * @return
     */
    @Override
    public Double total() {
        // Creating a Map with Product as Key and the valid promotion available equivalent to that product
        Map<Product, ? extends PromotionRule> activeRules = promotionRules.stream().filter(rule -> rule.getValidFrom().isBefore(LocalDate.now().plusDays(1)) && (Objects.isNull(rule.getValidTo()) || rule.getValidTo().isAfter(LocalDate.now().minusDays(1)))).collect(Collectors.toMap(PromotionRule::getProduct, promotionRule -> promotionRule));
        // count of each products in the basket
        Map<String, Long> productCounts = products.stream().collect(Collectors.groupingBy(Product::getProductId, Collectors.counting()) );

        AtomicReference<Double> discountAmt = new AtomicReference<>(0.0);
        AtomicReference<Double> totalProductAmt = new AtomicReference<>(0.0);

        // iterating through products to apply the product specific promotions
        products.parallelStream().forEach(product -> {
            ProductBasedPromotion productPromotion = (ProductBasedPromotion) activeRules.get(product);
            // if the product have a valid promotion present
            if(Objects.nonNull(productPromotion)){
               int promotionQty=  productPromotion.getQuantity();
               long productQty = productCounts.get(product.getProductId());
               // if count of product in basket matches the quantity required for promotion to apply
               while (productQty >= promotionQty){
                   //calculating the promotion discount
                   discountAmt.accumulateAndGet((productPromotion.getDiscountPercent() * product.getPrice()) / 100.0,  (d1, d2) -> d1 + d2);
                   //if promotion needs to be applied multiple times
                   productQty= productQty- promotionQty;
               }
               // removing the count form the Map to avoid duplicate iteration
                productCounts.put(product.getProductId(), 0L);
            }
            // total of all product price - without applying any discount
            totalProductAmt.accumulateAndGet(product.getPrice(), (d1, d2) -> d1 + d2);
        });
        // reducing the product based discounts from total
        totalProductAmt.accumulateAndGet(discountAmt.get() , (d1, d2) -> d1 - d2 );
        totalProductAmt.set(Math.round(totalProductAmt.get()*100.0)/100.0);

        // calculate the final offer promotion if available
        FinalOfferPromotion promotionOnTotal = (FinalOfferPromotion) activeRules.get(null);
        // check if total amount reaches the required limit for applying the promotion
        if(totalProductAmt.get()>= promotionOnTotal.getLimit())
            totalProductAmt.accumulateAndGet((promotionOnTotal.getDiscountPercent() * totalProductAmt.get()) / 100.0 , (d1, d2) -> d1 - d2 );
        // return the final amount after all deductions.
        return Math.round(totalProductAmt.get()*100.0)/100.0;
    }
}
