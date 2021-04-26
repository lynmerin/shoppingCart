package com.noths;


import com.noths.model.FinalOfferPromotion;
import com.noths.model.ProductBasedPromotion;
import com.noths.model.Product;
import com.noths.model.PromotionRule;
import com.noths.service.Checkout;
import com.noths.service.impl.CheckoutImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Application {

    public static void main(String args[]) {

        Product product1 = new Product("1001", "Travel Card Holder", 9.25);
        Product product2 = new Product("1002", "Personalised cufflinks", 45.00);
        Product product3 = new Product("1003", "Kids T-shirt", 19.95);

        Set<PromotionRule> rules = new HashSet<>();
        PromotionRule rule1 = new ProductBasedPromotion(product1, LocalDate.now(), LocalDate.now().plusDays(10), 16.22, 2 );
        PromotionRule rule3 = new FinalOfferPromotion(LocalDate.now(),LocalDate.now().plusDays(15),10.00,60.00 );

        rules.add(rule1);
        rules.add(rule3);

        List<Product> shoppingBag = new ArrayList<>();
        double customerHaveToPay;

        System.out.println("---- Customer 1  ------- \n");
        shoppingBag.add(product1);
        shoppingBag.add(product2);
        shoppingBag.add(product3);
        customerHaveToPay = checkoutPlan(rules , shoppingBag);
        System.out.println("---- customerHaveToPay  = " + customerHaveToPay + "\n");
        System.out.println("--------------------------------------------------------- \n");

        System.out.println("---- Customer 2  ------- \n");
        shoppingBag.clear();
        shoppingBag.add(product1);
        shoppingBag.add(product3);
        shoppingBag.add(product1);
        customerHaveToPay = checkoutPlan(rules , shoppingBag);
        System.out.println("---- customerHaveToPay  = " + customerHaveToPay + "\n");
        System.out.println("--------------------------------------------------------- \n");

        System.out.println("---- Customer 3  ------- \n");
        shoppingBag.clear();
        shoppingBag.add(product1);
        shoppingBag.add(product2);
        shoppingBag.add(product1);
        shoppingBag.add(product3);
        customerHaveToPay = checkoutPlan(rules , shoppingBag);
        System.out.println("---- customerHaveToPay  = " + customerHaveToPay + "\n");
        System.out.println("---------------------------------------------------------- \n");
    }

    private static double checkoutPlan(Set<PromotionRule> rules, List<Product> products ){
        Checkout co = new CheckoutImpl(rules);
        System.out.println("---- Shopping Bag ------- \n");
        products.forEach(product ->{co.scan(product);
            System.out.println(" - "+product.getProductId()+" - "+product.getPrice() +" \n");
        });

        return  co.total();
    }
}