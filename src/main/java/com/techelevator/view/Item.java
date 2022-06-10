package com.techelevator.view;

public abstract class Item {
    //setting array of available item types
    public static String [] itemTypes = {"Candy","Drink","Gum","Chip"};
    //constructor to make a new item in the machine
    public Item(String slotLocation, String productName, Double price, int stock) {
        this.slotLocation = slotLocation;
        this.productName = productName;
        this.price = price;
        this.stock = stock;
    }

    String slotLocation;
    String productName;
    Double price;
    int stock;
    //abstract classes to be inherited by children
    abstract void purchaseConfirmation();
    abstract String getName();
}
