package com.noths.model;

import java.util.Objects;

/**
 * Pojo class which will hold the product details
 *
 */

public class Product {
    private String productId;
    private String name ;
    private double price;

    public Product(String productId, String name, double price) {
        this.productId = productId;
        this.name = name;
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Equals method overriden to compare the productId, price and name
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 && Objects.equals(productId, product.productId) && Objects.equals(name, product.name);
    }

    /**
     * Hashcode generated using productId, name and price
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(productId, name, price);
    }
}
