package com.sacom.integrateservice.model;

public class Product {
    private String gtin;
    private String description;
    private Price price;
    private String supplier;

    public Product() {
    }

    public Product(String gtin, String description, Price price, String supplier) {
        this.gtin = gtin;
        this.description = description;
        this.price = price;
        this.supplier = supplier;
    }

    public String getGtin() {
        return gtin;
    }

    public void setGtin(String gtin) {
        this.gtin = gtin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
}
