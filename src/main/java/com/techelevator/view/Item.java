package com.techelevator.view;

public abstract class Item implements Purchasable {
    public static String [] itemTypes = {"Candy","Drink","Gum","Chip"};
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
    abstract void purchaseConfirmation();
    abstract String getName();
}
