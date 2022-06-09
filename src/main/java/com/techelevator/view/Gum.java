package com.techelevator.view;

public class Gum extends Item {
    public Gum (String slotLocation, String productName, Double price, int stock) {
        super(slotLocation, productName, price, stock);
    }
    @Override
    public void purchaseConfirmation(){
        System.out.println("Chew Chew, Yum!");
    }
    @Override
    public String getName(){
        return "Gum";
    }

}
