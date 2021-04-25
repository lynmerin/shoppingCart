package com.noths.service;

import com.noths.model.Product;

public interface Checkout {
    void scan(Product prod);
    Double total();
}
