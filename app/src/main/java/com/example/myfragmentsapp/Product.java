package com.example.myfragmentsapp;

public class Product {

    private String productName;
    private int image;
    public Product(String productName, int image) {
        this.productName = productName;
        this.image = image;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
