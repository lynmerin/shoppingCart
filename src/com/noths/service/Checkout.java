package com.noths.service;

import com.noths.model.Product;

/**
 * Interface for checkout service
 */
public interface Checkout {
    /**
     * Scan method is used to add products to the basket , which is a list of Products.
     *
     * @param prod
     */
    void scan(Product prod);

    /**
     * Total method is used to calculate the final amount the customer have to pay after applying all valid promotions.
     *
     * @return
     */
    Double total();
}
