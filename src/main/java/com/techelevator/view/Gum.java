package com.techelevator.view;

public class Gum extends Item {
    public Gum (String slotLocation, String productName, Double price, int stock) {
        super(slotLocation, productName, price, stock);
    }
    @Override
    //inherited from super, defines the output for purchase confirmation.
    public void purchaseConfirmation(){
        System.out.println("Chew Chew, Yum!");
    }
    @Override
    //getter for name
    public String getName(){
        return "Gum";
    }

}
