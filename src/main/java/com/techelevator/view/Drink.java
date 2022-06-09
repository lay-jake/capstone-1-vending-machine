package com.techelevator.view;

public class Drink extends Item {
    public Drink(String slotLocation, String productName, Double price, int stock) {
        super(slotLocation, productName, price, stock);
    }
    @Override
    public void purchaseConfirmation(){
        System.out.println("Glug Glug, Yum!");
    }
    @Override
    public String getName(){
        return "Drink";
    }
}
