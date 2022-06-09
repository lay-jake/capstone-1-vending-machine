package com.techelevator.view;

public class Chip extends Item {
    public Chip(String slotLocation, String productName, Double price, int stock) {
        super(slotLocation, productName, price, stock);
    }
    @Override
    public void purchaseConfirmation(){
        System.out.println("Crunch Crunch, Yum!");
    }
    @Override
    public String getName(){
        return "Chip";
    }
}
