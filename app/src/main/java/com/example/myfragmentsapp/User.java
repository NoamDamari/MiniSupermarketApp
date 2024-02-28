package com.example.myfragmentsapp;

import java.util.ArrayList;

public class User {

    private String username;
    private String password;
    private String phoneNumber;
    private ArrayList<Product> user_products;

    public User(String username, String password, String phoneNumber, ArrayList<Product> user_products) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.user_products = user_products;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ArrayList<Product> getUser_products() {
        return user_products;
    }

    public void setUser_products(ArrayList<Product> user_products) {
        this.user_products = user_products;
    }
}
