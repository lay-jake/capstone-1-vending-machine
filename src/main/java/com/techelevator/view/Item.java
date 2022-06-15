package com.techelevator.view;

public abstract class Item {
    //setting array of available item types
    public static String [] itemTypes = {"Candy","Drink","Gum","Chip"};

    public Double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getProductName() {
        return productName;
    }
    public void lowerStock(){
        stock--;
    }

    //constructor to make a new item in the machine
    public Item(String productName, Double price, int stock) {
        this.productName = productName;
        this.price = price;
        this.stock = stock;
    }

    private String productName;
    private Double price;
    private int stock;
    //abstract classes to be inherited by children
    abstract void purchaseConfirmation();
    abstract String getName();
}
