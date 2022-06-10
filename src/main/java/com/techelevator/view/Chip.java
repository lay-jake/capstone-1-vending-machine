package com.techelevator.view;

public class Chip extends Item {
    public Chip(String slotLocation, String productName, Double price, int stock) {
        super(slotLocation, productName, price, stock);
    }
    @Override
    //inherited from super, defines the output for purchase confirmation.
    public void purchaseConfirmation(){
        System.out.println("Crunch Crunch, Yum!");
    }
    @Override
    //getter for name
    public String getName(){
        return "Chip";
    }
}
